package com.lvmama.tmall.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderMemo;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.tmall.OrdTmallDistributorMap;
import com.lvmama.comm.bee.po.tmall.TmallMemo;
import com.lvmama.comm.bee.po.tmall.TmallPerson;
import com.lvmama.comm.bee.service.DownTmallDistributorOrderInterface;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.tmall.TOPInterface;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.BuyInfo.OrdTimeInfo;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.client.UserClient;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdProductDAO;
import com.lvmama.prd.logic.ProductTimePriceLogic;
import com.lvmama.tmall.dao.OrdTmallDistributorMapDAO;
import com.taobao.api.domain.PurchaseOrder;

public class DownTmallDistributorOrderInterfaceImpl implements DownTmallDistributorOrderInterface {

	private static final Log log = LogFactory.getLog(DownTmallDistributorOrderInterfaceImpl.class);
	private OrderService orderServiceProxy;
	private ProdProductDAO prodProductDAO;
	private ProdProductBranchDAO prodProductBranchDAO;
	private UserClient userClient;
	private UserUserProxy userUserProxy;
	private OrdTmallDistributorMapDAO ordTmallDistributorMapDAO;
	private TopicMessageProducer resourceMessageProducer;
	private PayPaymentService payPaymentService;
	private ProductTimePriceLogic productTimePriceLogic;
	private static final String CHANGLONG="长隆";

	@Override
	public void backDownDistributorOrder(Long fenXiaoId) throws Exception {
		Map<String,Object> param =new HashMap<String,Object>();
		param.put("fenXiaoId", fenXiaoId);
		List<OrdTmallDistributorMap> ordTmallDistributorList = ordTmallDistributorMapDAO.getOrdTmallDistributorMapList(param);
		if(ordTmallDistributorList!=null && ordTmallDistributorList.size()>0){
			//检查或创建用户标记
			Boolean userIdflag=true;
			//默认旗子为蓝色，成功
			long returnFlag = 4L;
			//备注信息对象
			TmallMemo memo = null;
			BuyInfo buyInfo = null;
			//回写备注信息
			StringBuffer callbackMemo = new StringBuffer();
			String sellerMemo=null;
			Long purchaseOrderId=null;
			int paymentTradeNoSufix = 0;
			Integer orderSize=ordTmallDistributorList.size();
			String userId=null;
			for(OrdTmallDistributorMap ordTmallDistributorMap:ordTmallDistributorList){
				paymentTradeNoSufix++;
				//是否注册获取用户id============Begin============
				if(userIdflag){
					userIdflag=false;
					purchaseOrderId=ordTmallDistributorMap.getFenXiaoId();
					//取备注信息
					sellerMemo = getTmallMemo(fenXiaoId, ordTmallDistributorMap);
					if(sellerMemo==null){
						return;
					}
					//获得用户信息
					userId = getUserId(ordTmallDistributorMap);
					if(userId == null){
						return;
					}
				}
				//是否注册获取用户id============End============
				
				Long productId = ordTmallDistributorMap.getProductId();
				Long branchId = ordTmallDistributorMap.getCategoryId();
				
				//下单之前订单状态 success不下单,并对非create状态订单直接跳出本次循环============Begin============
				String orderStatus=ordTmallDistributorMap.getStatus();
				if("failure".equalsIgnoreCase(orderStatus)||"unpay".equalsIgnoreCase(orderStatus)){
					returnFlag = 2L;
				}
				if (!"create".equalsIgnoreCase(orderStatus)){
					continue;
				}
				//下单之前订单状态 success不下单,并对非create状态订单直接跳出本次循环============End=============
				
				//拆分每个子订单寻找关联备注信息=================Begin===================
				memo = checkUserMemo(ordTmallDistributorMap,sellerMemo);
				if (memo == null) {
					returnFlag = 2L;
					continue;
				}
				Date tourDate = memo.getTourDate();// 游玩日期
				String seller = memo.getSeller();// 淘宝客服
				//拆分每个子订单寻找关联备注信息=================End===================
				
				// 产品信息校验=====================Begin=============
				ProdProduct proProduct = checkProductInfo(ordTmallDistributorMap,tourDate);
				if (proProduct == null) {
					returnFlag = 2L;
					continue;
				}
				// 产品信息校验=====================End==============
				
				//组装一个buyInfo=================Begin==============
				buyInfo = getBuyInfo(memo, userId, proProduct, tourDate, ordTmallDistributorMap.getNum(), productId, branchId,ordTmallDistributorMap.getTitle());
				//组装一个buyInfo=================End================
				
				//判断是否可以下单=============Begin=============
				if(!checkIsCanDownOrder(buyInfo,ordTmallDistributorMap)){
					returnFlag = 2L;
					continue;
				}
				//判断是否可以下单=============End=============
				
				//后台下单================Begin======================
				OrdOrder lvorder = remoteBackDownOrder(buyInfo, ordTmallDistributorMap,seller);
				if (lvorder != null) {
					callbackMemo.append("\r\n驴妈妈订单号:").append(lvorder.getOrderId());
					if(lvorder.getPaymentTarget().equals("TOSUPPLIER")){
						returnFlag = 2L;
						continue;
					}
					long payment = PriceUtil.convertToFen(ordTmallDistributorMap.getPrice());
					boolean paymentSuccess = remotePayment(lvorder, ordTmallDistributorMap, payment, paymentTradeNoSufix,orderSize);
					if (!paymentSuccess){
						returnFlag = 2L;
					}
				}else{
					returnFlag = 2L;
				}
				//后台下单================End========================
			}
			//调淘宝接口更改旗帜颜色,并将驴妈妈订单号加上===========Begin=================
			TOPInterface.updateDistributorMemo(purchaseOrderId, sellerMemo+ "  " + callbackMemo.toString(), returnFlag);
			//调淘宝接口更改旗帜颜色,并将驴妈妈订单号加上===========End===================
		}
	}

	//根据手机号检测用户是否渠道注册
	public String getUserId(OrdTmallDistributorMap ordTmallDistributorMap) {
		String mobileNumber = ordTmallDistributorMap.getBuyerMobile();
		String userId = null;
		Long id = 0L;
		UserUser userUser = null;
			if (mobileNumber != null&&StringUtil.validMobileNumber(mobileNumber)) {
				if (mobileNumber.charAt(0) == '0') {
					mobileNumber = mobileNumber.substring(1, mobileNumber.length());
				}
				try {
					String smsContent = "亲，感谢您惠顾驴妈妈淘宝商城！正在为您处理订单，请耐心等待。注意及时查收短信。客服电话：021-60561630. 祝您旅途愉快。";
					id = this.userClient.batchRegisterWithChannel(mobileNumber, null, null, null,
							smsContent, null, null, "010936");
					if (id == null) {
						userUser = userUserProxy.getUsersByMobOrNameOrEmailOrCard(mobileNumber);
						userId = userUser.getUserId();
					} else {
						userUser = userUserProxy.getUserUserByPk(id);
						userId = userUser.getUserId();
					}
				} catch (Exception e) {
					log.error(mobileNumber + "getUserId", e);
					ordTmallDistributorMap.setStatus("failure");
					ordTmallDistributorMap.setFailedReason("注册用户异常");
					ordTmallDistributorMapDAO.updateAllByPrimaryKey(ordTmallDistributorMap);
					TOPInterface.updateDistributorMemo(ordTmallDistributorMap.getFenXiaoId(),"", 2L);// 调淘宝接口修改旗帜颜色
					return null;
				}
			}else{
				log.error("is not mobile number error:"+mobileNumber);
				ordTmallDistributorMap.setStatus("failure");
				ordTmallDistributorMap.setFailedReason("淘宝用户联系方式不是正确的手机号码!");
				ordTmallDistributorMapDAO.updateAllByPrimaryKey(ordTmallDistributorMap);
				TOPInterface.updateDistributorMemo(ordTmallDistributorMap.getFenXiaoId(),"", 2L);// 调淘宝接口修改旗帜颜色
				return null;
			}
		return userId;
	}

	// 备注信息校验 如返回为null,调用程序终止代码执行
	public TmallMemo checkUserMemo(OrdTmallDistributorMap ordTmallDistributorMap,String tmallMemo) {
		// 将备注信息封装为map对象
		TmallMemo memo = null;
		Map<String, TmallMemo> map = TmallMemo.processMemo(tmallMemo);
		if (map != null) {
			memo = (TmallMemo) map.get(ordTmallDistributorMap.getCategoryId().toString());
		}

		String failedReason = "";
		if (map == null){
			failedReason = "没有备注信息或备注信息格式不正确";
		}else if (memo == null){
			failedReason = "备注类别ID和淘宝类别ID不同，或者备注信息不正确";
		}else if(memo.getTourDate() == null ){
			ProdProduct product = prodProductDAO.selectByPrimaryKey(ordTmallDistributorMap.getProductId());
			//非不定期产品才做游玩日期校验
			if(!product.IsAperiodic()) {
				failedReason = "游玩日期不正确";
			}
		}else if(memo.getSeller() == null){
			failedReason = "淘宝客服不正确";
		}
		boolean isMobileNo=true;
		boolean isIdentiNo=true;
		if(memo!=null&&"".equals(failedReason)){
			if(memo.getPersons()!=null && memo.getPersons().size()>0){
				for(TmallPerson pes:memo.getPersons()){
					boolean isMobile=StringUtil.validMobileNumber(pes.getMobile().trim());
					if(isMobile==false){
						isMobileNo=false;
					}
					if(pes.getIdentity()!=null){
						boolean	isIdentity=IDCardValidate(pes.getIdentity().trim());
						if(isIdentity==false){
							isIdentiNo=false;
						}
						}
				}
			}else{
				failedReason="错误的备注信息，没有找到相应的联系人或游玩人信息！";
			}
			if(isMobileNo==false&&isIdentiNo==true){
				failedReason="手机号码不正确";
			}
			if(isMobileNo==true&&isIdentiNo==false){
				failedReason="身份证号码不正确";
			}
			if(isMobileNo==false&&isIdentiNo==false){
				failedReason="手机号码和身份证号码不正确";
			}
		}
		
		
		if (!"".equals(failedReason)) {
			ordTmallDistributorMap.setStatus("failure");
			ordTmallDistributorMap.setFailedReason(failedReason);
			try {
				ordTmallDistributorMapDAO.updateAllByPrimaryKey(ordTmallDistributorMap);
			} catch (Exception e) {
				log.error("Error Info:>>"+e);
				ordTmallDistributorMap.setStatus("failure");
				ordTmallDistributorMap.setFailedReason("插入备注失败信息异常");
				ordTmallDistributorMapDAO.updateAllByPrimaryKey(ordTmallDistributorMap);
			}
			log.error("UserMemo Error!!>>"+ordTmallDistributorMap.getTmallMemo()+"fenXiaoID:"+
					ordTmallDistributorMap.getFenXiaoId());
			return null;
		}

		return memo;

	}

	// 产品信息校验
	public ProdProduct checkProductInfo(OrdTmallDistributorMap ordTmallDistributorMap, Date tourDate) {
		Long productId = ordTmallDistributorMap.getProductId();
		Long branchId = ordTmallDistributorMap.getCategoryId();
		ProdProduct prodProduct = null;
		ProdProductBranch prodProductBranch=null;
		Long maxNum;
		String gtMaxNum="false";
		try {
			prodProduct = prodProductDAO.selectByPrimaryKey(productId);
			prodProductBranch=prodProductBranchDAO.selectByPrimaryKey(branchId);
			//不定期订单,取类别的最后一天有效期做游玩日期
			if(prodProduct != null && prodProductBranch != null && prodProduct.IsAperiodic()) {
				Date validEndTime = prodProductBranch.getValidEndTime();
				if(prodProductBranch.getValidBeginTime() != null && validEndTime != null 
						&& !DateUtil.getDayStart(new Date()).after(validEndTime)) {
					tourDate = validEndTime;
				} else {
					tourDate = null;
				}
			}
			String failedReason = "";
			TimePrice timePrice = null;
			if(prodProduct.IsAperiodic() && tourDate == null) {
				failedReason = "该期票产品不可售";
			} else {
				timePrice = productTimePriceLogic.calcProdTimePrice(branchId,tourDate);
			}
			float lvTimePrice = 0;
			if (timePrice != null) {
				lvTimePrice = PriceUtil.convertToYuan(timePrice.getPrice());
			}
			if (timePrice == null || prodProduct == null||prodProductBranch == null) {
				failedReason = "产品时间价格表不存在";
			}else if(!ordTmallDistributorMap.getPrice().equals(lvTimePrice)){
				failedReason = "淘宝价格与驴妈妈价格不相同";
			}
			
			if(prodProductBranch!=null){
				maxNum=prodProductBranch.getMaximum();
				if(maxNum<ordTmallDistributorMap.getNum()){
					gtMaxNum="true";
				}
			}
			if (!"".equals(failedReason)){
				ordTmallDistributorMap.setGtMaxNum(gtMaxNum);
				ordTmallDistributorMap.setStatus("failure");
				ordTmallDistributorMap.setFailedReason(failedReason);
				ordTmallDistributorMapDAO.updateAllByPrimaryKey(ordTmallDistributorMap);
				return null;
			}else{
				ordTmallDistributorMap.setGtMaxNum(gtMaxNum);
				ordTmallDistributorMapDAO.updateAllByPrimaryKey(ordTmallDistributorMap);
			}
		} catch (Exception e) {
			log.error(this.getClass(), e);
			ordTmallDistributorMap.setGtMaxNum(gtMaxNum);
			ordTmallDistributorMap.setStatus("failure");
			ordTmallDistributorMap.setFailedReason("产品信息校验不通过");
			ordTmallDistributorMapDAO.updateAllByPrimaryKey(ordTmallDistributorMap);
			return null;
		}
		return prodProduct;
	}

	// 远程调用订单服务
	public OrdOrder remoteBackDownOrder(BuyInfo buyer, OrdTmallDistributorMap ordTmallDistributorMap, String seller) {
		OrdOrder lvorder = null;
		// 开始调用后台下单接口进行后台下单
		try {
			lvorder = orderServiceProxy.createOrderWithOperatorId(buyer, "system");
			// 根据后台下单结果 调淘宝备忘更改接口 更改旗帜颜色
			if (lvorder != null) {
				Long id = lvorder.getOrderId();

				OrdOrderMemo orderMemo = new OrdOrderMemo();
				orderMemo.setOrderId(id);
				orderMemo.setType("淘宝订单备注");
				orderMemo.setContent("淘宝商城采购商   " + ordTmallDistributorMap.getDistributorUserName());
				orderMemo.setOperatorName(seller);
				orderMemo.setCreateTime(new Date());
				orderServiceProxy.saveMemo(orderMemo, seller);
				boolean isCertificate=ordTmallDistributorMapDAO.selectCertificateType(id);
				if(isCertificate==true){
					ordTmallDistributorMap.setIsCertificate("true");
				}else{
					ordTmallDistributorMap.setIsCertificate("false");
				}
				ordTmallDistributorMap.setLvOrderId(id);
				ordTmallDistributorMap.setResourceConfirmStatus(lvorder.getResourceConfirmStatus());
				if(lvorder.getPaymentTarget().equals("TOSUPPLIER")){
					ordTmallDistributorMap.setStatus("unpay");
				}else{
					ordTmallDistributorMap.setStatus("success");
				}
				ordTmallDistributorMap.setSeller(seller);
				ordTmallDistributorMap.setOperatorName("system");
				ordTmallDistributorMap.setProcessTime(new Date());
				ordTmallDistributorMapDAO.updateAllByPrimaryKey(ordTmallDistributorMap);
			}
		} catch (Exception e) {
			log.error(ordTmallDistributorMap.getFenXiaoId() + ":" + ordTmallDistributorMap.getProductId() + ":" + ordTmallDistributorMap.getCategoryId()
					+ "  remoteBackDownOrder Error!!!", e);
			ordTmallDistributorMap.setStatus("failure");
			ordTmallDistributorMap.setSeller(seller);
			ordTmallDistributorMap.setFailedReason("调用下单接口失败");
			ordTmallDistributorMapDAO.updateAllByPrimaryKey(ordTmallDistributorMap);
			return null;
		}

		return lvorder;
	}

	// 远程支付接口调用
	public boolean remotePayment(OrdOrder lvorder, OrdTmallDistributorMap ordTmallDistributorMap,Long payment,int loop,Integer orderSize) {
		boolean flag = true;
		try {
			PayPayment payPayment = new PayPayment();
			payPayment.setBizType(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.getCode());
			payPayment.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
			payPayment.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
			payPayment.setObjectId(lvorder.getOrderId());
			payPayment.setSerial(payPayment.geneSerialNo());
			payPayment.setPaymentGateway(Constant.PAYMENT_GATEWAY.ALIPAY_OFFLINE.name());
			payPayment.setAmount(payment);
			payPayment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
			payPayment.setCallbackTime(ordTmallDistributorMap.getPayTime());
			payPayment.setCreateTime(new Date());
			payPayment.setGatewayTradeNo(ordTmallDistributorMap.getAlipayNo());
			payPayment.setRefundSerial(ordTmallDistributorMap.getAlipayNo());
			if (orderSize > 1) {
				payPayment.setPaymentTradeNo(String.valueOf(ordTmallDistributorMap.getFenXiaoId()) + "_" + loop);
			} else {
				payPayment.setPaymentTradeNo(String.valueOf(ordTmallDistributorMap.getFenXiaoId()));
			}
			Long paymentId = payPaymentService.savePayment(payPayment);
			resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(paymentId));
		} catch (Exception e) {
			log.error(this.getClass(), e);
			ordTmallDistributorMap.setStatus("unpay");
			ordTmallDistributorMap.setFailedReason("下单成功,支付失败。");
			ordTmallDistributorMapDAO.updateAllByPrimaryKey(ordTmallDistributorMap);
			flag = false;
		}
		return flag;

	}

    //身份证号码的简单验证
    public boolean IDCardValidate(String IDStr){
    	boolean result=true;
        String Ai = "";  
        // ================ 号码的长度 15位或18位 ================  
        if (IDStr.length() != 15 && IDStr.length() != 18) {  
        	result=false; 
            return result;  
        }  
        // ================ 数字 除最后以为都为数字 ================  
        if (IDStr.length() == 18) {  
            Ai = IDStr.substring(0, 17);  
        } else if (IDStr.length() == 15) {  
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);  
        }  
        if (isNumeric(Ai) == false) {  
        	result=false; 
            return result;  
        }
		return result; 
    }
    
    /** 
     * 功能：判断字符串是否为数字 
     * @param str 
     * @return 
     */ 
    private static boolean isNumeric(String str) {  
        Pattern pattern = Pattern.compile("[0-9]*");  
        Matcher isNum = pattern.matcher(str);  
        if (isNum.matches()) {  
            return true;  
        } else {  
            return false;  
        }  
    }
    /**
     * 组装BuyInfo
     * @param memo
     * @param userId
     * @param proProduct
     * @param tourDate
     * @param num
     * @param productId
     * @param branchId
     * @return
     */
    private BuyInfo getBuyInfo(TmallMemo memo,String userId,ProdProduct proProduct,Date tourDate,Integer num,Long productId,Long branchId,String title){
		// --实例化person
    	List<Person> personList = new ArrayList<Person>();
		List<TmallPerson> persons = memo.getPersons();
		Person person = null;
		boolean isContact = true;
		for (TmallPerson ps : persons) {
			person = new Person();
			if (ps.getName() != null && ps.getName().length() > 0) {
				person.setName(ps.getName());
			}
			if (ps.getMobile() != null && ps.getMobile().length() > 0) {
				person.setMobile(ps.getMobile().trim());
			}
			if (ps.getIdentity() != null && ps.getIdentity().length() > 0) {
				person.setCertNo(ps.getIdentity().trim());
				person.setCertType(Constant.CERTIFICATE_TYPE.ID_CARD.name());
			}
			if(isContact){
				person.setPersonType(Constant.ORD_PERSON_TYPE.CONTACT.name());
				isContact = false;
			}else{
				person.setPersonType(Constant.ORD_PERSON_TYPE.TRAVELLER.name());
			}
			personList.add(person);
		}
		//--实例化ordTimeInfos
		List<OrdTimeInfo> ordTimeInfos = new ArrayList<BuyInfo.OrdTimeInfo>();
		OrdTimeInfo ordTimeInfo = new OrdTimeInfo();
		ordTimeInfo.setProductId(productId);
		ordTimeInfo.setProductBranchId(branchId);
		ordTimeInfo.setMarketPrice(proProduct.getMarketPrice());
		ordTimeInfo.setSellPrice(proProduct.getSellPrice());
		ordTimeInfo.setVisitTime(tourDate);
		ordTimeInfo.setQuantity(num.longValue());
		ordTimeInfos.add(ordTimeInfo);
		//--实例化items
		List<Item> items = new ArrayList<BuyInfo.Item>();
		Item item = new Item();
		item.setVisitTime(tourDate);
		item.setProductId(Long.valueOf(productId));
		item.setProductBranchId(Long.valueOf(branchId));
		item.setQuantity(num);
		item.setTimeInfoList(ordTimeInfos);
		items.add(item);
		
		//--组装BuyInfo
		BuyInfo buyInfo = new BuyInfo();
		if(title.indexOf(CHANGLONG)!=-1){
			buyInfo.setChannel(Constant.CHANNEL.TAOBAO_DISTRIBUTOR_CHANGLONG.getCode());
		}else{
			buyInfo.setChannel(Constant.CHANNEL.TAOBAO_DISTRIBUTOR.getCode());
		}
		
		buyInfo.setUserId(userId);
		buyInfo.setPersonList(personList);
		buyInfo.setItemList(items);
		if(proProduct.getPayToLvmama().equalsIgnoreCase("true")&&proProduct.getPayToSupplier().equalsIgnoreCase("false")){
			buyInfo.setPaymentTarget(Constant.PAYMENT_TARGET.TOLVMAMA.name());
		}
		if(proProduct.getPayToLvmama().equalsIgnoreCase("false")&&proProduct.getPayToSupplier().equalsIgnoreCase("true")){
			buyInfo.setPaymentTarget(Constant.PAYMENT_TARGET.TOSUPPLIER.name());
		}
		return buyInfo;
    }
    /**
     * Buy Info对象下单前的检查
     * @param buyinfo
     * @param ordTmallDistributorMap
     * @return
     */
	public Boolean checkIsCanDownOrder(BuyInfo buyinfo,OrdTmallDistributorMap ordTmallDistributorMap){
		Boolean flag=true;
		ResultHandle result=orderServiceProxy.checkOrderStock(buyinfo); 
		if(result.isFail()){
			flag=false;
			ordTmallDistributorMap.setStatus("failure");
			ordTmallDistributorMap.setFailedReason("BuyInfo检查失败："+result.getMsg());
			ordTmallDistributorMapDAO.updateAllByPrimaryKey(ordTmallDistributorMap);
		}
		return flag;
	}
	/**
	 * 根据分销Id获取备注信息
	 * @param fenXiaoId
	 * @param ordTmallDistributorMap
	 * @return
	 */
	private String getTmallMemo(Long fenXiaoId,OrdTmallDistributorMap ordTmallDistributorMap){
		List<PurchaseOrder> purchaseOrderList=null;
		String tmallMemo=null;
		String failedReason =null;
		try{
			purchaseOrderList=TOPInterface.getPurchaseOrderList(null, null,null,null,fenXiaoId);
			if(purchaseOrderList!=null && purchaseOrderList.size()>0){
				if(purchaseOrderList.size()==1){
					tmallMemo=purchaseOrderList.get(0).getSupplierMemo();
				}else{
					failedReason="获取备注信息时，根据分销ID找到多个对应的分销单，请检查!";
				}
			}else{
				failedReason="获取备注信息时，根据分销ID没能找到对应的分销单，请检查!";
			}
		}catch(Exception e){
			failedReason="获取备注信息出现异常，请检查!";
			log.equals("getTmallMemo=====>:"+e);
		}
		if(failedReason!=null && !"".equals(failedReason)){
			ordTmallDistributorMap.setStatus("failure");
			ordTmallDistributorMap.setFailedReason(failedReason);
			ordTmallDistributorMapDAO.updateAllByPrimaryKey(ordTmallDistributorMap);
		}
		return tmallMemo;
	}

	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}

	public void setProductTimePriceLogic(ProductTimePriceLogic productTimePriceLogic) {
		this.productTimePriceLogic = productTimePriceLogic;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setResourceMessageProducer(TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}

	public void setProdProductBranchDAO(ProdProductBranchDAO prodProductBranchDAO) {
		this.prodProductBranchDAO = prodProductBranchDAO;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public void setOrdTmallDistributorMapDAO(
			OrdTmallDistributorMapDAO ordTmallDistributorMapDAO) {
		this.ordTmallDistributorMapDAO = ordTmallDistributorMapDAO;
	}
	
}
