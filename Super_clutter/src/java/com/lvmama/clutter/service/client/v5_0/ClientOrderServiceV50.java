package com.lvmama.clutter.service.client.v5_0;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.model.MobileBranchItem;
import com.lvmama.clutter.model.OrderDataItem;
import com.lvmama.clutter.service.client.v4_0.ClientOrderServiceV40;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.clutter.utils.MobileCopyPropertyUtils;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.money.CashAccount;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.po.work.WorkGroup;
import com.lvmama.comm.pet.po.work.WorkGroupUser;
import com.lvmama.comm.pet.po.work.WorkOrder;
import com.lvmama.comm.pet.po.work.WorkOrderType;
import com.lvmama.comm.pet.po.work.WorkTask;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.work.WorkGroupService;
import com.lvmama.comm.pet.service.work.WorkOrderService;
import com.lvmama.comm.pet.service.work.WorkOrderTypeService;
import com.lvmama.comm.pet.service.work.WorkTaskService;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;

/**
 * 新加预订单提交
 *
 */
public class ClientOrderServiceV50 extends ClientOrderServiceV40 {
	private static final Log log = LogFactory.getLog(ClientOrderServiceV50.class);
	/**
	 * 工单类型服务
	 */
	protected WorkOrderTypeService workOrderTypeService;

	/**
	 * 工单服务
	 */
	protected WorkOrderService workOrderService;
	
	/**
	 * 工单任务服务
	 */
	protected WorkTaskService workTaskService;
	
	/**
	 * 工单组服务
	 */
	protected WorkGroupService workGroupService;
	
	protected CashAccountService cashAccountService;

	protected TopicMessageProducer resourceMessageProducer;
	
	
	/**
	 * 现金账号支付 
	 * @param param
	 * @return
	 */
	@Override
	public Map<String, Object> cashAccountValidateAndPay(Map<String, Object> param) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
        ArgCheckUtils.validataRequiredArgs("signature","orderId","amount",param);
		Long orderId = Long.valueOf(param.get("orderId").toString());
		String amount = param.get("amount").toString(); // 订单金额 单位 元 
		String userNo = param.get("userNo").toString(); // 用户编号  
		// 订单金额 ，需要支付金额 
		Long t_amout = PriceUtil.convertToFen(amount);
		String signature = param.get("signature").toString();
		
		// 校验订支付金额；订单总金额 - 奖金支付后的金额 
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if(null == ordOrder) {
			log.error("..cashAccountValidateAndPay..orderId not extis.="+orderId);
			throw new LogicException("订单不存在");
		}
		// 订单金额 减去 奖金支付金额 bonusPaidAmount
		float oughtPay = ordOrder.getOughtPayYuan() - ordOrder.getBonusPaidAmountYuan();
		Long t_oughtPay = PriceUtil.convertToFen(oughtPay);
		// 如果前台传过来的价格 和 后台计算的价格不一致 
		if(!(t_oughtPay+"").equals(t_amout+"")) {
			log.error("....cashAccountValidateAndPay..ought pay is..="+t_oughtPay+"...but..request pay is..=="+t_amout);
			throw new LogicException("支付失败 ");
		}
		
		if(checkSignature(signature,orderId,amount,userNo)){
			// 校验支付密码是否正确
			UserUser uu = userUserProxy.getUserUserByUserNo(userNo);
			if(uu==null){
				throw new LogicException("不存在的用户 ");
			}
			CashAccount cashAccount = cashAccountService.queryCashAccountByUserId(uu.getId());
			if(cashAccount==null){
				throw new LogicException("不存在的存款账户 ");
			}
			
			try {
				// 从现金账户支付 
				List<Long> paymentIds = cashAccountService.payFromCashAccount(uu.getId(), orderId,Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name(), t_amout,0L);
				// 发送支付成功消息
				for(Long paymentId:paymentIds) {
					resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(paymentId));
				}
			}catch(Exception e) {
				e.printStackTrace();
				log.error("client pay from cash account failed, order id: " + orderId + ", user id: " + userNo + ", amount: " + t_amout);
				throw new LogicException("支付失败，请检查您的存款账户或者订单金额! ");
			}
		}else{
			log.error("client pay from cash account failed, order id: " + orderId + ", user id: " + userNo + ", amount: " + t_amout);
			throw new LogicException("验证签名失败 ");
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * @param signature 客户端传过来的md5加密后数据 
	 * @param orderId   订单id
	 * @param amount    订单金额
	 * @param bonus     现金支付可用金额
	 * @param userNo    用户编号 
	 * @return
	 */
	protected boolean checkSignature(String signature,Long orderId,String amount,String userNo){
		//如果签名为空则直接返回false
		if(StringUtils.isBlank(signature) || null == orderId){
			return false;
		}
		// MD5.encode(orderId（订单id）+objectType+amount(订单金额)+payMentType+bizType+（充值金额）+userNo(用户编号)+sigKey);
		String dataStr=String.valueOf(orderId)+Constant.OBJECT_TYPE.ORD_ORDER.name()+amount
				+Constant.PAYMENT_TYPE.ORDER.name()+Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name()+userNo+PaymentConstant.SIG_PRIVATE_KEY;
		
		log.info("client2 source: " + dataStr);
		String md5 = MD5.md5(dataStr);
		log.info("client2 md5: " + md5);
		
		return signature.equalsIgnoreCase(md5);
	}
	
	
	@Override
	public Map<String, Object> commitOrder(Map<String, Object> param) {
		// TODO Auto-generated method stub
		// 5.0.0 提交订单修复 
		this.repaireParams(param);
		
		Map<String,Object> map = super.commitOrder(param);
		if(param.get("econtractEmail")!=null){
			map.put("needEContract", false);
			Map<String,Object> signParam = new HashMap<String,Object>();
			signParam.put("orderId", map.get("orderId"));
			signParam.put("optionsCheckBox1", true);
			signParam.put("optionsCheckBox2", true);
			signParam.put("optionsCheckBox3", true);
			signParam.put("contactEmail", param.get("econtractEmail"));
			signParam.put("userNo", param.get("userNo"));
			try {
				String success = super.onlineSign(signParam);
				log.info("签约状态："+success);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map;
	}
	
	private void repaireParams(Map<String,Object> param) {
		try {
			if (param.get("isAndroid") != null) {
				boolean isAndroid = Boolean.valueOf(param.get("isAndroid").toString()); 
				if(isAndroid) {
					// couponCode=B327843153855311econtractEmail=yu@163.com
					if(null != param.get("couponCode") && StringUtils.isNotEmpty(param.get("couponCode").toString())) {
						String couponCode = param.get("couponCode").toString();
						int index= couponCode.indexOf("econtractEmail");
						if(index > 0 ) {
							String couponCodeStr = couponCode.substring(0,index);
							param.put("couponCode", couponCodeStr);
							String email =  couponCode.substring(index,couponCode.length());
							int eIndex = email.indexOf("=");
							if(eIndex > 0) {
								String econtractEmail =  email.substring(eIndex,email.length());
								param.put("econtractEmail", econtractEmail);
							}
							log.info("...repari android for 5.0.0 ......couponCode=="+couponCode+"==couponCodeStr="+couponCodeStr);
						}
					}
				}
			}
		}catch(Exception e ){
			e.printStackTrace();
			log.info("...repari android for 5.0.0 error......");
		}
	}
	
	/**
	 * 提交工单. 
	 * @param param
	 * @return
	 */
	public Map<String,Object> commitAdvanceOrder(Map<String,Object> param) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		ArgCheckUtils.validataRequiredArgs("userNo","mobile","firstChannel","productId","subProductType","secondChannel","branchItem","visitTime","personItem","udid",param);
		
		String subProductType = param.get("subProductType").toString();
		// 填写订单时的手机号 
		String mobile = param.get("mobile").toString();
		// 1，根据产品productId查找工单workOrderTypeId
		WorkOrderType workOrderType = this.getWorkOrderType(subProductType);
		if(null != workOrderType) {
			String commitInfos = this.initSubmitTypes(param);
			//,2，生成工单 
			Long workOrderId = this.initWorkOrder(param,workOrderType.getWorkOrderTypeId(),commitInfos,mobile);
			//,3，获取 工作组 
			WorkGroup workGroup = this.getWorkGroup(subProductType);
			if(null != workGroup) {
				// 4，获取处理工单的人员信息. 
				WorkGroupUser receiveGroupUser = workOrderService.getFitUser( null, workGroup.getWorkGroupId(), null, null, null, null,workOrderType.getTypeCode());
				WorkTask workTask = new WorkTask();
				workTask.setContent(commitInfos+"简易预订单");
				workTask.setCreateTime(new Date());
				workTask.setStatus(Constant.WORK_TASK_STATUS.UNCOMPLETED.getCode());
				workTask.setWorkOrderId(workOrderId);
				workTask.setReceiver(receiveGroupUser.getWorkGroupUserId());
				//5, 提交工单任务 
				Long workTaskId = workTaskService.insert(workTask);
				log.info(commitInfos + "提交预订单=workTaskId=" +workTaskId + "==workOrderId=="+workOrderId+"==workGroupId="+workGroup.getWorkGroupId() );
			}
		}
		
		return resultMap;
		
	}
	
	/**
	 * 获取workGroup
	 * @param subProductType
	 * @return
	 */
	private WorkGroup getWorkGroup(String subProductType) {
		//line 628--646将生成工单分给固定组
		Map<String, Object> params = new HashMap<String, Object>();
		if (subProductType != null && !"".equals(subProductType)) {
			// 长途
			if (subProductType.endsWith("LONG")) {
				params.put("groupName", "长线线路单组");
			}// 出境
			else if (subProductType.endsWith("FOREIGN")) {
				params.put("groupName", "出境线路单组");
			}
		}
		List<WorkGroup> groupList = workGroupService.queryWorkGroupByParam(params);
		WorkGroup workGroup=new WorkGroup();
		if(groupList!=null && groupList.size()>0){
			workGroup=groupList.get(0);
		}
		
		return workGroup;
	}
	
	
	/**
	 * 初始化 initWorkOrder 
	 * @param params
	 * @param workOrderTypeId
	 * @param commitInfos
	 * @return
	 */
	public Long initWorkOrder(Map<String,Object> params,final Long workOrderTypeId,String commitInfos ,String mobile) {
		String userNo = params.get("userNo").toString();
		Long productId = Long.valueOf(params.get("productId").toString());
		String visitTime = params.get("visitTime").toString();
		String firstChannel = null == params.get("firstChannel")?"":params.get("firstChannel").toString();
		// 创建工单
		UserUser user = userUserProxy.getUserUserByUserNo(userNo);
		if(null == user) {
			throw new LogicException("未登录，请重新登录. ");
		}
		WorkOrder workOrder = new WorkOrder();
		workOrder.setWorkOrderTypeId(workOrderTypeId);
		Date date=new Date();
		workOrder.setCreateTime(date);
		workOrder.setLimitTime(MobileCopyPropertyUtils.limitTime(date));// 工单处理时限120分钟
		workOrder.setProductId(productId);
		workOrder.setStatus(Constant.WORK_ORDER_STATUS.UNCOMPLETED.getCode());// 工单状态默认”未处理“
		
		if(null != user ) {
			//workOrder.setCreateUserName(commitInfos +"_"+ userNo);// 工单创建人简易预订（WAP+userId）
			workOrder.setCreateUserName(firstChannel + userNo);
			workOrder.setMobileNumber(mobile); // 
			workOrder.setUserName(this.initUserName(user));
		}
		
		List<OrderDataItem> orderDataList = this.getOrderDataList(params.get("branchItem").toString());
		// 
		
		try {
			workOrder.setContent(this.initSubmitContent(workOrder.getUserName(),workOrder.getMobileNumber(),productId,visitTime,orderDataList));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new LogicException("生成产品列表错误");
			
		}
		
		//数据插入工单系统
		return workOrderService.insert(workOrder);
		
	}
	
	/**
	 * getMobileBranchItem
	 * @param branchItem
	 * @return
	 */
	private  List<MobileBranchItem> getMobileBranchItem(String branchItem) {
		String[] branchArray = branchItem.split("_");
		if (branchArray.length == 0) {
			throw new RuntimeException("类别项构建错误!");
		}
		
		List<MobileBranchItem> branchItemList = new ArrayList<MobileBranchItem>();
		/**
		 * 解析类别对象
		 */
		for (String string : branchArray) {
			String[] itemArray  = string.split("-");
			MobileBranchItem mbi = new MobileBranchItem();
			mbi.setBranchId(Long.valueOf(itemArray[0]));
			Long quantity = Long.valueOf(itemArray[1]);
			if(quantity < 1) {
				continue;
			}
			mbi.setQuantity(quantity);
			branchItemList.add(mbi);
		}
		
		return branchItemList;
	}
	/**
	 * 获取 getOrderDataList 
	 * @param branchItem
	 * @param visitTime
	 * @return
	 */
	private List<OrderDataItem> getOrderDataList(String branchItem) {
		List<MobileBranchItem> branchItemList =  this.getMobileBranchItem(branchItem);
		List<OrderDataItem> orderDataList = new ArrayList<OrderDataItem>();
		/**
		 * 构建订单项
		 */
		for (MobileBranchItem mobileBranchItem : branchItemList) {
			OrderDataItem item = new OrderDataItem();
			ProdProductBranch branch =  this.prodProductService.getProdBranchDetailByProdBranchId(mobileBranchItem.getBranchId(),null, false);
			if(null != branch) {
				item.setBranchId(mobileBranchItem.getBranchId());
				item.setBranchNum(mobileBranchItem.getQuantity());
				item.setSellPriceYuan(branch.getSellPriceYuan()+"");
				item.setShortName(branch.getBranchName());
				orderDataList.add(item);
			}
		}
		return orderDataList;
	}
	
	/**
	 * 初始化内容 . 
	 * @param userName
	 * @param mobile
	 * @param productId
	 * @param visitTime
	 * @return string
	 * @throws UnsupportedEncodingException 
	 */
	private String initSubmitContent(String userName, String mobile, Long productId, String visitTime,List<OrderDataItem> orderDataList) throws UnsupportedEncodingException{
		// 获取产品名称  
		String productName = "";
		ProdProduct pp = prodProductService.getProdProduct(productId);
		if(null != pp) {
			productName = pp.getProductName();
		}
		
		// 更加productId 获取productName 
		StringBuffer content = new StringBuffer();
		content.append("联系人：" + userName+",");
		content.append("手机号码：" + mobile+",");
		content.append("产品名称：" + productName+",");
		content.append("产品ID：" + productId+",");
		content.append("产品列表：" + MobileCopyPropertyUtils.probuceListDatas(orderDataList)+",");
		content.append("游玩日期：" +visitTime);
		return content.toString();
	}
	/**
	 * 初始化createName
	 * @param params
	 * @return
	 */
	private String initSubmitTypes(Map<String,Object> params ) {
		String firstChannel = null == params.get("firstChannel")?"":params.get("firstChannel").toString();
		String secondChannel = null == params.get("secondChannel")?"":params.get("secondChannel").toString();
		String lvversion = null == params.get("lvversion")?"":params.get("lvversion").toString();
		String createUserName = firstChannel+"_" + secondChannel+"_"+lvversion;
		if(createUserName.length() > 100) {
			createUserName.subSequence(0, 99);
		}
		return createUserName;
	}
	
	/**
	 * 获取用户名. 
	 * @param user
	 * @return string
	 */
	private String initUserName(UserUser user) {
		String showName = "";
		if(!StringUtil.isEmptyString(user.getNickName())){
			showName = user.getNickName();
		} else if(!StringUtil.isEmptyString(user.getEmail())){
			showName = user.getEmail();
		} else if(!StringUtil.isEmptyString(user.getMobileNumber())){
			showName = user.getMobileNumber();
		} else {
			showName = user.getUserName();
		}
		showName = MobileCopyPropertyUtils.filterUserName(showName); //客户端过滤掉一些无用字符 。 
		
		return showName;
		
	}
	
	
	/**
	 * 根据TYPECODE查询WorkOrderType
	 * 
	 * @param subProductType
	 * @return
	 */
	public WorkOrderType getWorkOrderType(String subProductType) {
		WorkOrderType workOrderType = null;
		Map<String, Object> map = new HashMap<String, Object>();
		if (subProductType != null && !"".equals(subProductType)) {
			// 长途
			if (subProductType.endsWith("LONG")) {
				map.put("typeCode", "cxxl");
			}// 出境
			else if (subProductType.endsWith("FOREIGN")) {
				map.put("typeCode", "cjxl");
			}
		}
		List<WorkOrderType> WorkOrderTypeList = workOrderTypeService.queryWorkOrderTypeByParam(map);
		if (WorkOrderTypeList != null && WorkOrderTypeList.size() > 0) {
			workOrderType = WorkOrderTypeList.get(0);
		}
		return workOrderType;
	}
	
	public void setWorkOrderTypeService(WorkOrderTypeService workOrderTypeService) {
		this.workOrderTypeService = workOrderTypeService;
	}


	public void setWorkOrderService(WorkOrderService workOrderService) {
		this.workOrderService = workOrderService;
	}


	public void setWorkTaskService(WorkTaskService workTaskService) {
		this.workTaskService = workTaskService;
	}


	public void setWorkGroupService(WorkGroupService workGroupService) {
		this.workGroupService = workGroupService;
	}

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}

	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}

}
