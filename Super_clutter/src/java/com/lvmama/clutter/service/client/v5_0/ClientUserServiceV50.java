package com.lvmama.clutter.service.client.v5_0;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.log4j.Logger;

import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.model.JianYiOrderDTO;
import com.lvmama.clutter.model.JianYiProduct;
import com.lvmama.clutter.model.MobileOrder;
import com.lvmama.clutter.model.MobilePayment;
import com.lvmama.clutter.service.client.v4_0_1.ClientUserServiceV401;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.pet.po.money.CashAccount;
import com.lvmama.comm.pet.po.money.CashPay;
import com.lvmama.comm.pet.po.money.CashRecharge;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.po.work.WorkOrder;
import com.lvmama.comm.pet.service.work.WorkOrderService;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.Constant;

/**
 * V5.0
 * @author qinzubo
 *
 */
public class ClientUserServiceV50 extends ClientUserServiceV401 {
	protected Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 工单服务
	 */
	protected WorkOrderService workOrderService;
	
	/**
	 *  充值方式选择页面
	 */
	@Override
	public Map<String,Object> getPaymentTarget(Map<String,Object> params) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		// 充值金额选择 
		Float[] money = {100f,300f,500f,800f,1000f,2000f,3000f,5000f,10000f};
		List<MobilePayment> payList = new ArrayList<MobilePayment>();
		// 支付方式
		MobilePayment mpalipay = new MobilePayment( Constant.PAYMENT_GATEWAY.ALIPAY_APP.name(), Constant.getInstance().getAliPayAppUrl4Recharge());
		//支付宝
		payList.add(mpalipay);
		MobilePayment wapAlipay = new MobilePayment( Constant.PAYMENT_GATEWAY.ALIPAY_WAP.name(), Constant.getInstance().getAliPayWapUrl4Recharge());
		payList.add(wapAlipay);
		resultMap.put("defaultMoney",500f);
		resultMap.put("money", money);
		resultMap.put("payList", payList);
		return resultMap;
		
	}
	
	
	/**
	 * 跳转支付页面  MoneyAccountAction 
	 */
	@Override
	public Map<String,Object> commitPayment(Map<String,Object> params) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		ArgCheckUtils.validataRequiredArgs("userNo","rechargeAmount","paymentGateway",params);
		// 单位是分 . 
		Long rechargeAmount = PriceUtil.convertToFen(params.get("rechargeAmount").toString());
		String userNo = params.get("userNo").toString();
		//String paymentGateway = params.get("paymentGateway").toString();
		
		// 用户校验 
		UserUser user = userUserProxy.getUserUserByUserNo(userNo);
		if(null == user) {
			throw new LogicException("用户不存在");
		} else if(StringUtils.isBlank(user.getMobileNumber())) {
			throw new LogicException("请绑定手机号");
		}
	
		
		//若该用户没有绑定手机,且用户的存款账户余额为0，且没有订单的游玩日期晚于（当下时刻-168小时）,若不满足以上条件,不能充值
		CashAccountVO moneyAccount = cashAccountService.queryMoneyAccountByUserId(user.getId());
		
		// 校验是否可充值
		if(!canRecharge(moneyAccount,userNo,user.getMobileNumber())){
			throw new LogicException("您的账户存在安全问题，为了保证您的资金安全，请联系客服");
		}
		
		//生成充值记录
		CashRecharge cashRecharge = new CashRecharge();
		cashRecharge.setCashAccountId(moneyAccount.getCashAccountId()); // 账号id 
		cashRecharge.setAmount(rechargeAmount); // 充值金额 
		cashRecharge.setStatus(Constant.PAYMENT_SERIAL_STATUS.CREATE.name());
		cashRecharge.setCreateTime(new Date());
		Long cashRechargeId = cashAccountService.insertCashRecharge(cashRecharge);
		/*String objectType = "CASH_RECHARGE";
		String paymentType = Constant.PAYMENT_OPERATE_TYPE.PAY.name();
		String bizType = Constant.PAYMENT_BIZ_TYPE.CASH_ACCOUNT.name();
		String waitPayment = "1";
		String approveTime = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss");
		String visitTime = approveTime;
		String dataStr = cashRechargeId+objectType+rechargeAmount+paymentType+bizType+PaymentConstant.SIG_PRIVATE_KEY;
		log.info("client source: " + dataStr);
		String signature = MD5.md5(dataStr);
		log.info("client md5: " + signature);
	    String rechargeUrl = "?objectId="+cashRechargeId+"&amount="+rechargeAmount+"&objectType="+objectType+"&paymentType="+paymentType+"&bizType="+bizType+"&waitPayment="+waitPayment+"&approveTime=" +approveTime + "&visitTime=" + visitTime + "&signature="+signature;
*/
		String rechargeUrl = "?objectId="+cashRechargeId+"&amount="+rechargeAmount;

	 /*   // 如果是手机app支付
        if(Constant.PAYMENT_GATEWAY.ALIPAY_APP.name().equals(paymentGateway)) {
        	rechargeUrl = Constant.getInstance().getAliPayAppUrl4Recharge()+ rechargeUrl;
        } else if(Constant.PAYMENT_GATEWAY.ALIPAY_WAP.name().equals(paymentGateway)) {
        	rechargeUrl = Constant.getInstance().getAliPayWapUrl4Recharge()+ rechargeUrl;
        }else if(Constant.PAYMENT_GATEWAY.UPOMP.name().equals(paymentGateway)) {
        	rechargeUrl = Constant.getInstance().getUpompPayUrl()+ rechargeUrl;
        }else {
        	rechargeUrl = Constant.getInstance().getAliPayAppUrl()+ rechargeUrl;
        }*/
	    resultMap.put("rechargeUrl",rechargeUrl);
	    
		// 充值金额选择 
		return resultMap;
		
	}
	
	/**
	 * 绑定手机号时是否需要首笔订单验证 . 
	 */
	@Override
	public Map<String, Object> getBindingInfo(Map<String,Object> params) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		ArgCheckUtils.validataRequiredArgs("userNo",params);
		String userNo = params.get("userNo").toString();
		boolean orderValidateMust = false; // 是否需要手机验证
		
		// 判断绑定是否需要首笔订单验证 
		UserUser user = userUserProxy.getUserUserByUserNo(userNo);
		if(null != user) {
			if ( StringUtils.isBlank(user.getMobileNumber()) ) {
				CashAccountVO cashAccount = cashAccountService.queryMoneyAccountByUserId(user.getId());
				Long orderCounts=orderServiceProxy.queryUserOrderVisitTimeGreaterCounts(user.getUserNo());
				//若该用户的存款账户余额为0，且没有订单的游玩日期晚于（当下时刻-168小时）。则可以直接验证并绑定手机。
				if(cashAccount.getTotalMoney()==0 && orderCounts==0){
					orderValidateMust=false;
				}else{//若不满足以上条件，则需要进行订单信息验证，在通过订单信息验证和手机校验码验证之后，方绑定手机成功。
					orderValidateMust=true;//校验首笔订单联系人手机号码
					OrdOrder order = orderServiceProxy.queryUserFirstOrder(user.getUserNo());
					if(null != order ) {
						if(null != order.getMainProduct()) {
							resultMap.put("productName",order.getMainProduct().getProductName());
							resultMap.put("orderId",order.getOrderId());
						}
						resultMap.put("contactName", order.getContact().getName());
						//resultMap.put("mobile", StringUtil.hiddenMobile(order.getContact().getMobile()));
						resultMap.put("mobile",order.getContact().getMobile());
					}
				}
			}
		}
		resultMap.put("needOrderValidate", orderValidateMust);
		return resultMap;
	}
	
	
	/**
	 * 获取用户预订列表 . 
	 * 
	 */
	@Override
	public Map<String, Object> getAdvanceOrder(Map<String,Object> params) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		ArgCheckUtils.validataRequiredArgs("userNo","userId",params);
		String userNo = params.get("userNo").toString();
		String userId = params.get("userId").toString();
		//String firstChannel = null == params.get("firstChannel")?"":params.get("firstChannel").toString();
		//个人简易预订订单列表
		List<JianYiOrderDTO> jianYiOrderList=new ArrayList<JianYiOrderDTO>();
		
		//根据USERNO查询工单对应的工单列表
		Map<String,Object> param =new HashMap<String, Object>();
		// 客户端
		param.put("createUserName4Client", userNo);
		List<WorkOrder> workOrderList = workOrderService.queryWorkOrderByParam(param);
		// wap
		/*param.put("createUserName", "WAP" +userNo);
		List<WorkOrder> workOrderList = workOrderService.queryWorkOrderByParam(param);
		
		if(null != clientWorkOrderList && clientWorkOrderList.size() > 0 ) {
			workOrderList.addAll(clientWorkOrderList);
		}*/
		
		//对查出的工单列表进行数据转换，转换成个人中心预订单显示
		if(workOrderList!=null && workOrderList.size()>0){
			 for(WorkOrder workOrder : workOrderList){
				 JianYiOrderDTO jyo = this.dataTransf(workOrder,userNo,userId);
				 if(null != jyo) {
					 jianYiOrderList.add(jyo);
				 }
			 }
		}
		resultMap.put("datas", jianYiOrderList);
		return resultMap;
	}
	
	/**
	 * 获取用户现金信息.
	 */
	@Override
	public Map<String, Object> getMoneyInfo(Map<String,Object> params) {
		ArgCheckUtils.validataRequiredArgs("userNo","userId",params);
		
		Long userId = Long.valueOf(params.get("userId").toString());
		String userNo = params.get("userNo").toString();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		CashAccountVO moneyAccount = cashAccountService.queryMoneyAccountByUserId(userId);
		if(null == moneyAccount) {
			throw new LogicException("用户账号不存在");
		}
		
		// 所有(ALL), 提现(DRAW) ,付款(PAY) ,充值 (RECHARGE),退款(REFUND)
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMoneyAccountChangeLogRelate().setUserId(userNo);
		//compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.PAY);
		compositeQuery.getMoneyAccountChangeLogRelate().setCashAccountId(moneyAccount.getCashAccountId());
		compositeQuery.getMoneyAccountChangeLogRelate().setUserNo(userNo);
		compositeQuery.setPayFrom(CashPay.PayFrom.MONEY.toString());
		compositeQuery.setBonusRefundment("N"); // 非奖金账户
		
		//账号是否冻结 ,如果没有冻结 
		if("Y".equals(moneyAccount.getValid())) {
			resultMap.put("valid", true);
			// ${moneyAccount.maxPayMoney/100} 可用金额 ,  可支付金额
			resultMap.put("maxPayMoney", moneyAccount.getMaxPayMoneyYuan());
			// 冻结金额 ${(moneyAccount.rechargeBalance+moneyAccount.refundBalance-moneyAccount.maxPayMoney)/100}
			resultMap.put("dongjieMoney", moneyAccount.getRechargeBalanceYuan()+moneyAccount.getRefundBalanceYuan() -moneyAccount.getMaxPayMoneyYuan());
			// ${moneyAccount.maxDrawMoneyYuan} 可提现金额 
			resultMap.put("maxDrawMoney", moneyAccount.getMaxDrawMoneyYuan());
		} else {
			resultMap.put("valid", false);
			// ${moneyAccount.maxPayMoney/100} 可用金额 ,  可支付金额
			resultMap.put("maxPayMoney", moneyAccount.getMaxPayMoneyYuan());
			// 冻结金额 ${(moneyAccount.rechargeBalance+moneyAccount.refundBalance-moneyAccount.maxPayMoney)/100}
			resultMap.put("dongjieMoney", moneyAccount.getMaxPayMoneyYuan());
			// ${moneyAccount.maxDrawMoneyYuan} 可提现金额 
			resultMap.put("maxDrawMoney", 0f);
		}
		
		// ${moneyAccount.rechargeBalanceYuan}  已充值金额 
		resultMap.put("rechargeBalance", moneyAccount.getRechargeBalanceYuan());
		
		// 手机号是否绑定
		resultMap.put("mobileCanChecked", !this.mobileChecked(resultMap,userNo));
		// 各种操作记录的条数 
		this.initMoneyInit(resultMap, userNo, userId, compositeQuery);
		// 是否可充值
		resultMap.put("canRecharge", this.canRecharge(moneyAccount,userNo,String.valueOf(resultMap.get("mobileNumber"))));
		// 是否可以提现
		resultMap.put("canDraw", this.canDraw(moneyAccount));
		resultMap.put("helpUrl", "/app/help_money.html"); // 玩转奖金url
		return resultMap;
	}
	
	/**
	 * 现金账户提现(DRAW),付款(PAY) ,充值 (RECHARGE),退款(REFUND) 的条数. 
	 * @param moneyInit 
	 * @param userNO 
	 * @param userId
	 * @param paramCQ
	 */
	private void initMoneyInit(Map<String,Object> moneyInit,String userNO,Long userId,final CompositeQuery paramCQ){
		CashAccount cashAccount=cashAccountService.queryOrCreateCashAccountByUserId(userId);
		
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMoneyAccountChangeLogRelate().setUserNo(userNO);
		compositeQuery.getMoneyAccountChangeLogRelate().setCashAccountId(cashAccount.getCashAccountId());
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.PAY);
		compositeQuery.setPayFrom(paramCQ.getPayFrom());
		// 付款
		moneyInit.put("payCount", cashAccountService.queryMoneyAccountChangeLogCount(compositeQuery).toString());
		
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.REFUND);
		compositeQuery.setBonusRefundment(paramCQ.getBonusRefundment());
		// 退款
		moneyInit.put("refundCount", cashAccountService.queryMoneyAccountChangeLogCount(compositeQuery).toString());
				
		compositeQuery.getMoneyDrawRelate().setUserNo(userNO);
		compositeQuery.getMoneyDrawRelate().setCashAccountId(cashAccount.getCashAccountId());
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.DRAW);
		// 提现
		moneyInit.put("drawCount", cashAccountService.queryMoneyDrawCount(compositeQuery).toString());
		
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.RECHARGE);
		// 充值
		moneyInit.put("rechargeCount", cashAccountService.queryMoneyAccountChangeLogCount(compositeQuery).toString());
		
		//compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.ALL);
		// 所有 
		//moneyInit.put(Constant.MoneyAccountChangeType.ALL.name(), cashAccountService.queryMoneyAccountChangeLogCount(compositeQuery).toString());
		//moneyInit.put("changeType",paramCQ.getMoneyAccountChangeLogRelate().getMoneyAccountChangeType().getCode());
	}
	
	/**
	 * 若该用户没有绑定手机,且用户的存款账户余额为0，且没有订单的游玩日期晚于（当下时刻-168小时）,若不满足以上条件,不能充值
	 */
	private boolean canRecharge(CashAccountVO moneyAccount,String userNo,String userMobileNumber) {
		Long orderCounts=orderServiceProxy.queryUserOrderVisitTimeGreaterCounts(userNo);
		if((StringUtils.isBlank(userMobileNumber) || "null".equals(userMobileNumber))&&!(moneyAccount.getTotalMoney()==0&&orderCounts==0)){
			return false;
		}
		
		return true;
	}
	
	/**
	 * 是否可以提现 
	 * @return
	 */
	private boolean canDraw(CashAccountVO moneyAccount) {
		if("Y".equals(moneyAccount.getValid()) && moneyAccount.getMaxDrawMoneyYuan() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 手机号是否绑定 . 
	 * @return
	 */
	private boolean mobileChecked(Map<String,Object> resultMap ,String userNo) {
		UserUser user = userUserProxy.getUserUserByUserNo(userNo);
		resultMap.put("mobileNumber", "");
		if(null != user && StringUtils.isNotBlank(user.getMobileNumber())) {
			resultMap.put("mobileNumber", user.getMobileNumber());
			if("Y".equals(user.getIsMobileChecked())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 简易预订数据转换--我的意向单用
	 * @return
	 */
	public JianYiOrderDTO dataTransf(WorkOrder workOrder,String userNo,String userId){
		JianYiOrderDTO jianYiOrderDTO=new JianYiOrderDTO();
		try {
	//		String str = "联系人：wq,手机号码：18616023394,产品名称：【独家自由行】无锡灵山元一温泉，住无锡凯莱大饭店高级间2人1晚【独家赠送欢迎水果一份】,产品列表：双床套餐<101139>￥595x1,大床套餐<101140>￥556x2,游玩日期：2013-11-27";
			String str=workOrder.getContent();
			if(StringUtils.isEmpty(str)) {
				return null;
			}
		    String[] datas=str.split(",");
		    
		    String userName=datas[0].split("：")[1];//联系人姓名
		    String userMobile=datas[1].split("：")[1];//联系人手机
		    String visitTime=datas[datas.length-1].split("：")[1];//游玩时间
		    String productName="";//产品名称
		    String branchList="";//产品列表  
		    String productId="";//产品ID
		    		
		    //产品名称
	        Pattern p=Pattern.compile("产品名称：(.*),产品ID："); 
	        Matcher m=p.matcher(str); 
	        //产品ID
	        Pattern p2=Pattern.compile("产品ID：(.*),产品列表："); 
	        Matcher m2=p2.matcher(str); 
	        //产品列表（短名，价格，数量）
	        Pattern p1=Pattern.compile("产品列表：(.*),游玩日期："); 
	        Matcher m1=p1.matcher(str); 
	        
	        boolean bool=m.find();
	        boolean bool1=m1.find();
	        boolean bool2=m2.find();
	        while(bool){ 
	        	productName=m.group(1);
	            break;
	        }
	        while(bool1){ 
	        	branchList=m1.group(1);
	            break;
	        }
	        while(bool2){ 
	        	productId=m2.group(1);
	            break;
	        }
	        //产品列表
	        String[] branchs=branchList.split(",");
	        List<JianYiProduct> jianYiProductList=new ArrayList<JianYiProduct>();
	        for(String brach : branchs){
	        	String[] brachOne= brach.split("<");
	        	String[] brachTwo= brach.split(">");
	        	JianYiProduct jianYiProduct=new JianYiProduct();
	        	String shortName=brachOne[0];
	        	String priceAndNum=brachTwo[1];
	        	
	        	jianYiProduct.setShortName(shortName);
	        	jianYiProduct.setPriceAndNum(priceAndNum);
	        	
	        	jianYiProductList.add(jianYiProduct);
	        }
	        jianYiOrderDTO.setUserName(userName);
	        jianYiOrderDTO.setUserMobile(userMobile);
	        jianYiOrderDTO.setVisitTime(visitTime);
	        jianYiOrderDTO.setProductName(productName);
	        jianYiOrderDTO.setJianYiProductList(jianYiProductList);
	        jianYiOrderDTO.setCreateTime(DateUtils.formatDate(workOrder.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
	        jianYiOrderDTO.setWorkOrderId(workOrder.getWorkOrderId());
	        jianYiOrderDTO.setOrderJYStatus(Constant.WORK_ORDER_STATUS.UNCOMPLETED.getCnName(workOrder.getStatus()));
	        jianYiOrderDTO.setProductId(productId);
	        jianYiOrderDTO.setOrderJYImg(this.getProductSmallImage(productId,userNo,userId));
	        return jianYiOrderDTO;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * 查询自由行产品详情(这里只用小图片)
	 * @return
	 */
	public String getProductSmallImage(String productId,String userNo,String userId){
		String smallImage="";
		try {
			// 获取产品信息 - 非第二个参数没用，传什么后台都不会调用的。 
			ProdProduct prod = prodProductService.getProdProductById(Long.valueOf(productId), "noUse");
			if(null != prod ) {
				/*if(!StringUtils.isEmpty(mpr.getProductName())){
					mpr.setProductName(ClientUtils.filterQuotationMarks(prod.getProductName()));
				}*/
				if(!StringUtils.isEmpty(prod.getSmallImage())) {
					smallImage=prod.getSmallImage();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smallImage;
		
	}
	
	@Override
	public MobileOrder getOrder(Map<String, Object> param) {
		// TODO Auto-generated method stub
		MobileOrder object = super.getOrder(param);
		/**
		 * 5.0.0的api 一律不周在线签约
		 */
		object.setNeedEContract(false);
		return object;
	}
	
	@Override
	public Map<String, Object> getOrderList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		Map<String,Object> resultMap = super.getOrderList(param);
		List<MobileOrder> orderList = (List<MobileOrder>)resultMap.get("datas");
		if(orderList!=null && orderList.size()!=0){
			for (MobileOrder mobileOrder : orderList) {
				mobileOrder.setNeedEContract(false);
			}
		}
		return resultMap;
	}

	public void setWorkOrderService(WorkOrderService workOrderService) {
		this.workOrderService = workOrderService;
	}


}
