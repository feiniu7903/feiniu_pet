package com.lvmama.pet.payfront.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.ord.OrdUserOrder;
import com.lvmama.comm.bee.service.ord.IOrdUserOrderService;
import com.lvmama.comm.pet.po.lvmamacard.LvmamaStoredCard;
import com.lvmama.comm.pet.service.lvmamacard.LvmamacardService;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.PaymentUrl;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.payfront.BaseAction;


/**
 * 新支付展示界面
 * 
 * @author zhangjie
 * 
 */
@Results({
	@Result(name = "orderView", location = "/WEB-INF/pages/buy/orderView.jsp"),
	@Result(name = "preSalePowerView", location = "/WEB-INF/pages/buy/preSalePowerView.jsp"),
	@Result(name = "paymentChannelView", location = "/WEB-INF/pages/buy/paymentChannelView.jsp"),
	@Result(name = "orderMergeView", location = "/WEB-INF/pages/buy/orderMergeView.jsp")})
public class FrontOrderViewAction extends BaseAction{
	
	private static final long serialVersionUID = 3764027522329840597L;
	protected Logger log = Logger.getLogger(this.getClass());
	//支付宝快登操作
	private static final String ALIPAY_TOKEN_KEY="alipay_token";
	//盛付通联合登陆Token
	private static final String SNDA_TOKEN_KEY="access_token";


	private boolean hasAlipayLogin=false;
	private boolean isSndaLogin = false;
	
	/**
	 * 订单ID.
	 */
	private Long orderId;
	
	/**
	 * 订单name.
	 */
	private String orderName;

	/**
	 * 订单金额.分
	 */
	private Long orderAmountFen;
	
	/**
	 * 支付金额.分
	 */
	private Long payAmountFen;
	
	/**
	 * 对象类型(订单).
	 */
	private String objectType;
	
	/**
	 * 业务类型
	 */
	private String bizType;
	
	/**
	 * 订单支付 等待时间.
	 */
	private String waitPayment;
	
	/**
	 * 审核通过时间.
	 */
	private String approveTime;

    /**
     * 游玩时间
     */
	private String visitTime;
	
	/**
	 * 支付类型(正常支付/预授权).
	 */
	private String paymentType;

	/**
	 * 分润账号集
	 */
	private String royaltyParameters;
	
	/**
	 * 是否必须预授权支付
	 */
	private boolean hasNeedPrePay = false;
	
	/**
	 * 是否可以预授权支付
	 */
	private boolean prepayAble = true;
	
	/**
	 * 限制支付渠道
	 */
	private String paymentChannel;

	/**
	 * 是否可以分期支付
	 */
	private boolean isCanInstalment = false;
	
	/**
	 * 分期支付银行网关
	 */
	private String instalmentGateway;

	/**
	 * 分期支付页面初始化数据Map.
	 */
	private Map<String,Long> instalmentInfoMap;

	/**
	 * 合并支付时多订单号(逗号","隔开)
	 */
	private String orderIds;
	
	/**
	 * 合并支付封装数据
	 */
	private String mergePayData;

	/**
	 * 是否可以使用奖金账户支付
	 */
	private boolean isCanBounusPay = false;
	
	/**
	 * 奖金账户支付额度.分
	 */
	private Long bounusPayAmountFen;
	
	/**
	 * 签名
	 */
	private String signature;
	
	/**
	 * 
	 */

	private String paymentParams;
	
	/**
	 * 现金账户专用
	 * 
	 */
	private String paymentParamsCashAccount;
	protected CashAccountVO moneyAccount;
	transient CashAccountService cashAccountService;

	private IOrdUserOrderService ordUserOrderService;
	private LvmamacardService lvmamacardService;
	
	private String isBoundLipinka;	//是否绑定过礼品卡
	private String boundLipinkaUsable;	//绑定过的礼品卡是否可用
	private String hadConsumedLipinka; //已消费过得礼品卡
	private String bindCardNo;	//绑定的礼品卡
	private String bindCardPassword;	//绑定的礼品卡
	
	
	public String getIsBoundLipinka() {
		return isBoundLipinka;
	}

	public void setIsBoundLipinka(String isBoundLipinka) {
		this.isBoundLipinka = isBoundLipinka;
	}

	public String getBoundLipinkaUsable() {
		return boundLipinkaUsable;
	}

	public void setBoundLipinkaUsable(String boundLipinkaUsable) {
		this.boundLipinkaUsable = boundLipinkaUsable;
	}

	public String getHadConsumedLipinka() {
		return hadConsumedLipinka;
	}

	public void setHadConsumedLipinka(String hadConsumedLipinka) {
		this.hadConsumedLipinka = hadConsumedLipinka;
	}

	public String getBindCardNo() {
		return bindCardNo;
	}

	public void setBindCardNo(String bindCardNo) {
		this.bindCardNo = bindCardNo;
	}

	public String getBindCardPassword() {
		return bindCardPassword;
	}

	public void setBindCardPassword(String bindCardPassword) {
		this.bindCardPassword = bindCardPassword;
	}

	public LvmamacardService getLvmamacardService() {
		return lvmamacardService;
	}

	public void setLvmamacardService(LvmamacardService lvmamacardService) {
		this.lvmamacardService = lvmamacardService;
	}


	/**
	 * 订单正常支付，参数为空不参与加密，加密顺序按参数名称字母顺序排序
	 * @param orderId				订单ID
	 * @param orderName				订单name.
	 * @param orderAmountFen		订单金额，单位为：分
	 * @param payAmountFen			支付金额.分
	 * @param objectType			对象类型(订单).
	 * @param paymentType			支付类型(正常支付).
	 * @param waitPayment			订单支付 等待时间.单位：分
	 * @param approveTime			审核通过时间.yyyyMMddHHmmss
	 * @param visitTime				游玩时间.yyyyMMddHHmmss
	 * @param bizType				业务类型
	 * @param royaltyParameters		分润账号集
	 * 
	 * @param isCanBounusPay		是否可以使用奖金账户支付
	 * @param bounusPayAmountFen	奖金账户支付额度.分
	 * @param isCanInstalment		是否可以分期支付
	 * @param instalmentGateway		分期支付银行网关
	 * @param instalmentInfoMap		分期支付页面初始化数据Map.
	 * 
	 * @author ZHANG JIE
	 * @return 正常支付页面
	 */
	@Action("/view/orderView")
	public String orderView() throws Exception {
		if(!checkRequestData()){
			return ERROR;
		}
		initPayment();
		return "orderView";
 	}
	
	/**
	 * 银联预授权支付，参数为空不参与加密，加密顺序按参数名称字母顺序排序
	 * @param orderId				订单ID
	 * @param orderName				订单name.
	 * @param orderAmountFen		订单金额，单位为：分
	 * @param payAmountFen			支付金额.分
	 * @param objectType			对象类型(订单).
	 * @param paymentType			支付类型(正常支付).
	 * @param waitPayment			订单支付 等待时间.单位：分
	 * @param approveTime			审核通过时间.yyyyMMddHHmmss
	 * @param visitTime				游玩时间.yyyyMMddHHmmss
	 * @param bizType				业务类型
	 * @param hasNeedPrePay			是否必须预授权支付
	 * @param prepayAble			是否可以预授权支付
	 * @author ZHANG JIE
	 * @return 银联预授权支付页面
	 */
	@Action("/view/preSalePowerView")
	public String preSalePowerView() throws Exception {
		if(!checkRequestData()){
			return ERROR;
		}
		String dataStr = String.valueOf(orderId)+objectType+String.valueOf(payAmountFen)+paymentType+bizType+PaymentConstant.SIG_PRIVATE_KEY;
		log.info("payfront preSalePowerView source: " + dataStr);
		signature = MD5.md5(dataStr).toUpperCase();
		log.info("payfront preSalePowerView md5: " + signature);
		if(StringUtils.isNotBlank(orderName))//从cache中获取订单名称
		{
			Object o=getSession(orderName);
			if(o!=null){
				orderName = o.toString();
			}
		}
		return "preSalePowerView";
	}
	
	/**
	 * 合并支付，参数为空不参与加密，加密顺序按参数名称字母顺序排序
	 * @param orderIds				合并支付时多订单号(逗号","隔开)
	 * @param mergePayData			合并支付封装数据
	 * @param bizType				业务类型(合并支付)
	 * @author ZHANG JIE
	 * @return 合并支付页面
	 */
	@Action("/view/orderMergeView")
	public String orderMergeView() throws Exception {
//		bizType=Constant.PAYMENT_BIZ_TYPE.MERGE_PAY.name();
//		findAlipayToken();
		return "orderMergeView";
	}
	
	/**
	 * 限制支付渠道支付，参数为空不参与加密，加密顺序按参数名称字母顺序排序
	 * @param paymentChannel		限制的支付网关
	 * @param orderId				订单ID
	 * @param payAmountFen			订单金额，单位为：分
	 * @param objectType			对象类型(订单).
	 * @param paymentType			支付类型(正常支付).
	 * @param waitPayment			订单支付 等待时间.单位：分
	 * @param approveTime			审核通过时间.yyyyMMddHHmmss
	 * @param visitTime				游玩时间.yyyyMMddHHmmss
	 * @param bizType				业务类型
	 * @param royaltyParameters		分润账号集
	 * 
	 * @param isCanInstalment		是否可以分期支付
	 * @param instalmentGateway		分期支付银行网关
	 * @param instalmentInfoMap		分期支付页面初始化数据Map.
	 * @author ZHANG JIE
	 * @return 限制支付渠道支付页面
	 */
	@Action("/view/paymentChannelView")
	public String paymentChannelView() throws Exception {
		return "paymentChannelView";
	}
	
	private void initPayment(){
	    
		if(StringUtils.isNotBlank(orderName))//从cache中获取订单名称
		{
			Object o=getSession(orderName);
			if(o!=null){
				orderName = o.toString();
			}
		}
		
		if(StringUtils.isBlank(paymentChannel))//非优先通道时才判断是否有token的存在
		{
			findAlipayToken();
		    findSndaToken();
		}
		if(StringUtils.isBlank(royaltyParameters)){
			royaltyParameters="";
		}
		String dataStr = String.valueOf(orderId)+objectType+String.valueOf(payAmountFen)+paymentType+bizType+royaltyParameters+PaymentConstant.SIG_PRIVATE_KEY;
		log.info("payfront orderView source: " + dataStr);
		signature = MD5.md5(dataStr).toUpperCase();
		log.info("payfront orderView md5: " + signature);

	    moneyAccount = cashAccountService.queryMoneyAccountByUserNo(this.getUserId());
		

		PaymentUrl pu=new PaymentUrl(orderId,Constant.OBJECT_TYPE.ORD_ORDER.name(),bizType,payAmountFen,Constant.PAYMENT_TYPE.ORDER.name(),payAmountFen,this.getUserId());
		paymentParams=pu.getPaymentUrl("");
	    
		if(isCanBounusPay){
			PaymentUrl bounusPu=new PaymentUrl(orderId,Constant.OBJECT_TYPE.ORD_ORDER.name(),bizType,payAmountFen,Constant.PAYMENT_TYPE.ORDER.name(),bounusPayAmountFen,this.getUserId());
			paymentParams=bounusPu.getPaymentUrl("");
		}
		
		if(StringUtils.isNotBlank(this.getUser().getMobileNumber())&&"Y".equalsIgnoreCase(this.getUser().getIsMobileChecked())){
			PaymentUrl accountPu ;
			if(moneyAccount.getMaxPayMoney().longValue() > payAmountFen){
				accountPu = new PaymentUrl(orderId,Constant.OBJECT_TYPE.ORD_ORDER.name(),bizType,payAmountFen,Constant.PAYMENT_TYPE.ORDER.name(),payAmountFen,this.getUserId());
			}else{
				accountPu = new PaymentUrl(orderId,Constant.OBJECT_TYPE.ORD_ORDER.name(),bizType,payAmountFen,Constant.PAYMENT_TYPE.ORDER.name(),moneyAccount.getMaxPayMoney(),this.getUserId());
			}
			paymentParamsCashAccount=accountPu.getPaymentUrlWithBonus("");
		}
	
		//
		if(this.getUser()!=null) {
			//判断是否绑定过礼品卡
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userId", this.getUser().getId());
			param.put("FORREPORT", false);
			List<LvmamaStoredCard> lvmamaStoredCardList = lvmamacardService.queryByParamForLvmamaStoredCard(param);
			if(lvmamaStoredCardList!=null && lvmamaStoredCardList.size()>0) {
				isBoundLipinka = "1";
				LvmamaStoredCard lvmamaStoredCardMaxBalance = null;
				Long maxBalance = 0L;
				for(LvmamaStoredCard lvmamaStoredCard:lvmamaStoredCardList) {
					if(Constant.CARD_STATUS.USED.getCode().equals(lvmamaStoredCard.getStatus()) || Constant.CARD_STATUS.NOTUSED.getCode().equals(lvmamaStoredCard.getStatus())) {	//已使用和未使用的卡可以使用
						if(lvmamaStoredCard.getBalance()!=null && lvmamaStoredCard.getBalance()>maxBalance) {	//取金额最大的卡
							maxBalance = lvmamaStoredCard.getBalance();
							lvmamaStoredCardMaxBalance = lvmamaStoredCard;
						}
					}
				}
				if(lvmamaStoredCardMaxBalance==null || lvmamaStoredCardMaxBalance.getBalance()==0L) {
					boundLipinkaUsable = "0";
				}else {
					boundLipinkaUsable = "1";
					bindCardNo = lvmamaStoredCardMaxBalance.getCardNo();
					bindCardPassword = lvmamaStoredCardMaxBalance.getPassword();	
				}
			}else {
				boundLipinkaUsable = "0";
				isBoundLipinka = "0";
			}
			//获得消费过的礼品卡
			Map<String, Object> param2 = new HashMap<String, Object>();
			param2.put("userId", ""+this.getUser().getId());
			List<LvmamaStoredCard> usedLvmamaStoredCardList = lvmamacardService.queryUsedLvmamaStoredCardByUserId(param2);
			hadConsumedLipinka = "";
			if(usedLvmamaStoredCardList!=null && usedLvmamaStoredCardList.size()>0) {
				for(LvmamaStoredCard usedLvmamaStoredCard:usedLvmamaStoredCardList) {
					if(usedLvmamaStoredCard.getCardNo()!=null && !hadConsumedLipinka.contains(usedLvmamaStoredCard.getCardNo())) {
						hadConsumedLipinka += usedLvmamaStoredCard.getCardNo()+",";
					}
				}
			}	
		}
		
	}
	
	private void findAlipayToken()
	{
		Cookie[] cookies = ServletActionContext.getRequest().getCookies();
		if(!ArrayUtils.isEmpty(cookies))
		{
			for(Cookie c:cookies)
			{
				if(StringUtils.equals(c.getName(), ALIPAY_TOKEN_KEY))
				{
					if(StringUtils.isNotEmpty(c.getValue()))
					{
						hasAlipayLogin=true;
					}
					break;
				}
			}
		}
	}
	
	/**
	 * 查找盛付通令牌
	 */
	private void findSndaToken(){
		Cookie[] cookies = ServletActionContext.getRequest().getCookies();
		if(!ArrayUtils.isEmpty(cookies)){
			for(Cookie cookie:cookies){
				if(StringUtils.equals(cookie.getName(), SNDA_TOKEN_KEY)){
					if(StringUtils.isNotEmpty(cookie.getValue())){
						isSndaLogin = true;
					}
					break;
				}
			}
		}
	}
	
	 /**
	 * 验证登陆，请求参数等信息
	 * @return
	 */
	private boolean checkRequestData(){
		if(!this.isLogin()){
			return false;
		}
		String paramsignature = checkSignature();
		log.info("payfront orderView request signature: " + signature);
		if(StringUtils.isBlank(signature)||!signature.equalsIgnoreCase(paramsignature)){
			return false;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId.longValue());
		params.put("userId", this.getUser().getId());
		List<OrdUserOrder> ordUserOrders = ordUserOrderService.queryOrdUserOrderListByParams(params);
		if(null==ordUserOrders||ordUserOrders.isEmpty()){
			return false;
		}
		return true;
	}
	
	 /**
	 * 生成签名窜
	 * @return
	 */
	private String checkSignature() {
		
		Map<String,String> params = new HashMap<String,String>();
		if(null!=orderId&&StringUtils.isNotBlank(String.valueOf(orderId))){
			params.put("orderId",orderId.toString());
		}
		if(StringUtils.isNotBlank(orderName)){
			params.put("orderName",orderName);
		}
		if(null!=orderAmountFen&&StringUtils.isNotBlank(String.valueOf(orderAmountFen))){
			params.put("orderAmountFen",orderAmountFen.toString());
		}
		if(null!=payAmountFen&&StringUtils.isNotBlank(String.valueOf(payAmountFen))){
			params.put("payAmountFen",payAmountFen.toString());
		}
		if(StringUtils.isNotBlank(objectType)){
			params.put("objectType",objectType);
		}
		if(StringUtils.isNotBlank(paymentType)){
			params.put("paymentType",paymentType);
		}
		if(StringUtils.isNotBlank(waitPayment)){
			params.put("waitPayment",waitPayment);
		}
		if(StringUtils.isNotBlank(approveTime)){
			params.put("approveTime",approveTime);
		}
		if(StringUtils.isNotBlank(visitTime)){
			params.put("visitTime",visitTime);
		}
		if(StringUtils.isNotBlank(bizType)){
			params.put("bizType",bizType);
		}
		if(StringUtils.isNotBlank(royaltyParameters)){
			params.put("royaltyParameters",royaltyParameters);
		}
		
		if(StringUtils.isNotBlank(String.valueOf(hasNeedPrePay))&&hasNeedPrePay){
			params.put("hasNeedPrePay",String.valueOf(hasNeedPrePay));
		}
		
		if(StringUtils.isNotBlank(String.valueOf(prepayAble))&&!prepayAble){
			params.put("prepayAble",String.valueOf(prepayAble));
		}
		
		if(StringUtils.isNotBlank(String.valueOf(isCanBounusPay))&&isCanBounusPay){
			params.put("isCanBounusPay",String.valueOf(isCanBounusPay));
			if(null!=bounusPayAmountFen&&StringUtils.isNotBlank(String.valueOf(bounusPayAmountFen))){
				params.put("bounusPayAmountFen",bounusPayAmountFen.toString());
			}
		}
		
		if(StringUtils.isNotBlank(String.valueOf(isCanInstalment))&&isCanInstalment){
			params.put("isCanInstalment",String.valueOf(isCanInstalment));
			if(StringUtils.isNotBlank(instalmentGateway)){
				params.put("instalmentGateway",instalmentGateway);
			}
			if(StringUtils.isNotBlank(instalmentInfoMap.toString())){
				params.put("instalmentInfoMap",instalmentInfoMap.toString());
			}
		}
		if(StringUtils.isNotBlank(paymentChannel)){
			params.put("paymentChannel",paymentChannel);
		}
		if(StringUtils.isNotBlank(orderIds)){
			params.put("orderIds",orderIds);
		}
		if(StringUtils.isNotBlank(mergePayData)){
			params.put("mergePayData",mergePayData);
		}

		log.info("payfront params map: " + params.toString());
		
		List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);//对参数排序，已确保拼接的签名顺序正确
        StringBuffer prestr = new StringBuffer();
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = params.get(key);
			if (StringUtils.isNotBlank(value)) {
				prestr.append(value);
			}
		}
		log.info("payfront request params source: " + prestr.toString());
		String paramsSignature = MD5.md5(prestr.append(PaymentConstant.SIG_PRIVATE_KEY).toString()).toUpperCase();
		log.info("payfront request params md5: " + paramsSignature);
		return paramsSignature;
	}
	
	public boolean isHasAlipayLogin() {
		return hasAlipayLogin;
	}

	public void setHasAlipayLogin(boolean hasAlipayLogin) {
		this.hasAlipayLogin = hasAlipayLogin;
	}

	public boolean isSndaLogin() {
		return isSndaLogin;
	}

	public String getOrderUserId() {
		return this.getUserId();
	}

	public void setSndaLogin(boolean isSndaLogin) {
		this.isSndaLogin = isSndaLogin;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public Long getPayAmountFen() {
		return payAmountFen;
	}

	public void setPayAmountFen(Long payAmountFen) {
		this.payAmountFen = payAmountFen;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getRoyaltyParameters() {
		return royaltyParameters;
	}

	public void setRoyaltyParameters(String royaltyParameters) {
		this.royaltyParameters = royaltyParameters;
	}

	public Map<String, Long> getInstalmentInfoMap() {
		return instalmentInfoMap;
	}

	public void setInstalmentInfoMap(Map<String, Long> instalmentInfoMap) {
		this.instalmentInfoMap = instalmentInfoMap;
	}

	public String getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public boolean getIsCanInstalment() {
		return isCanInstalment;
	}

	public void setIsCanInstalment(boolean isCanInstalment) {
		this.isCanInstalment = isCanInstalment;
	}

	public String getWaitPayment() {
		return waitPayment;
	}

	public void setWaitPayment(String waitPayment) {
		this.waitPayment = waitPayment;
	}

	public String getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}

	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	public String getMergePayData() {
		return mergePayData;
	}

	public void setMergePayData(String mergePayData) {
		this.mergePayData = mergePayData;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getInstalmentGateway() {
		return instalmentGateway;
	}

	public void setInstalmentGateway(String instalmentGateway) {
		this.instalmentGateway = instalmentGateway;
	}
	
	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public Long getOrderAmountFen() {
		return orderAmountFen;
	}

	public void setOrderAmountFen(Long orderAmountFen) {
		this.orderAmountFen = orderAmountFen;
	}
	
	public boolean getIsCanBounusPay() {
		return isCanBounusPay;
	}

	public boolean getHasNeedPrePay() {
		return hasNeedPrePay;
	}

	public void setHasNeedPrePay(boolean hasNeedPrePay) {
		this.hasNeedPrePay = hasNeedPrePay;
	}

	public boolean isPrepayAble() {
		return prepayAble;
	}

	public void setPrepayAble(boolean prepayAble) {
		this.prepayAble = prepayAble;
	}

	public void setIsCanBounusPay(boolean isCanBounusPay) {
		this.isCanBounusPay = isCanBounusPay;
	}

	public long getBounusPayAmountFen() {
		return bounusPayAmountFen;
	}

	public void setBounusPayAmountFen(Long bounusPayAmountFen) {
		this.bounusPayAmountFen = bounusPayAmountFen;
	}

	public String getPaymentParams() {
		return paymentParams;
	}

	public void setPaymentParams(String paymentParams) {
		this.paymentParams = paymentParams;
	}

	public String getPaymentParamsCashAccount() {
		return paymentParamsCashAccount;
	}

	public void setPaymentParamsCashAccount(String paymentParamsCashAccount) {
		this.paymentParamsCashAccount = paymentParamsCashAccount;
	}

	public CashAccountVO getMoneyAccount() {
		return moneyAccount;
	}

	public void setMoneyAccount(CashAccountVO moneyAccount) {
		this.moneyAccount = moneyAccount;
	}

	public CashAccountService getCashAccountService() {
		return cashAccountService;
	}

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}

	public IOrdUserOrderService getOrdUserOrderService() {
		return ordUserOrderService;
	}

	public void setOrdUserOrderService(IOrdUserOrderService ordUserOrderService) {
		this.ordUserOrderService = ordUserOrderService;
	}


	/**
	 * 获得最晚支付时间 yyyy-MM-dd HH:mm:ss
	 */
	public String getLastPayTime() {
		try{
			if(null!=approveTime&&StringUtils.isNotBlank(approveTime)&&null!=waitPayment&&StringUtils.isNotBlank(waitPayment)){
				Date lastPayTime = DateUtil.DsDay_Minute(DateUtil.toDate(approveTime, "yyyyMMddHHmmss"),Integer.valueOf(waitPayment.trim()));
				return DateUtil.formatDate(lastPayTime, "yyyy-MM-dd HH:mm:ss");
			}	
		}catch(Exception e){
			return "";
		}
		return "";
	}
	
	/**
	 * 获得支付等待时间 
	 */
	public long getLastPayWaitTime() {
		try{
			String lastPayTime = this.getLastPayTime();
			if(null!=lastPayTime&&StringUtils.isNotBlank(lastPayTime)){
				Date today = new Date();
				Date lastPayDate = DateUtil.toDate(lastPayTime, "yyyy-MM-dd HH:mm:ss");
				if(DateUtil.inAdvance(lastPayDate,today)){
					return 0;
				}else{
					return lastPayDate.getTime() - today.getTime();
				}
			}	
		}catch(Exception e){
			return 0;
		}
		return 0;
	}
	
	/**
	 * 奖金账户支付金额.元
	 */
	public long getBounusPayAmountYuan() {
		if (bounusPayAmountFen==null || bounusPayAmountFen <= 0L) {
			return 0L;
		} else {
			return (long)PriceUtil.convertToYuan(bounusPayAmountFen);
		}
	}
	
	/**
	 * 订单金额.元
	 */
	public String getOrderAmountYuan() {
		if (orderAmountFen==null || orderAmountFen <= 0L) {
			return "0";
		} else {
			return String.valueOf(PriceUtil.convertToYuan(orderAmountFen));
		}
	}

	/**
	 * 支付金额.元
	 */
	public String getOughtPayYuan() {
		if (payAmountFen==null || payAmountFen <= 0L) {
			return "0";
		} else {
			return String.valueOf(PriceUtil.convertToYuan(payAmountFen));
		}
	}

}
