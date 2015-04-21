package com.lvmama.pet.payment.post.web;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayPrePayment;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.vo.PayMergePayment;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.payment.post.data.PostData;

@Results({
	@Result(name = "errorno", location = "/WEB-INF/pages/forms/errorno.ftl", type = "freemarker")
})
abstract class PayAction extends BaseAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 支付金额.
     */
	private long amount;
	/**
	 * 业务订单ID.
	 */
	private long objectId;

	/**
	 * 合并支付订单号 ,逗号隔开
	 */
	private String objectIds;
	/**
	 * 哪个业务类型(自由行/代售).
	 */
	private String bizType;
	/**
	 * 对象类型(订单).
	 */
	private String objectType;
	/**
	 * 支付类型(正常支付/预授权).
	 */
	private String paymentType;
    /**
     * 	
     */
	private String signature;
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
	 * 分期期数
	 */
	private String instalmentNum;
	/**
	 * 
	 */
	private String beforeURL;
	/**
	 * 操作者(百付在用).
	 */
	private String csno;
	/**
	 * 支付的手机号(百付在用).
	 */
	private String mobilenumber;
	
	/**
	 * 
	 */
	protected PostData payInfo;
	/**
	 * 
	 */
	private PayPaymentService payPaymentService;
	/**
	 * 银行标识.
	 */
	protected String bankid;
		
	protected final static String ERRORNO = "errorno";
	
	/**
	 * 合并支付封装数据
	 */
	private String mergePayData;
	
	/**
	 * 分润账号集
	 */
	private String royaltyParameters;
	
	/**
	 * 业务订单名称.
	 */
	private String objectName;
	/**
	 * 业务订单备注.
	 */
	private String objectDesc;
	/**
	 * 业务订单详情Url.
	 */
	private String objectPageUrl;
	
	
	/**
	 * 
	 * @return
	 */
	protected boolean payment() {
		try{
			log.info("to payment, Action:" + getClass().getName());
			if (checkSignature()) {
				return initPayment();
			}else{
				return false;
			}
		}catch(Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 验证支付数据是否正确.
	 * @return
	 */
	protected boolean checkSignature(){
		//如果签名为空则直接返回false
		if(StringUtils.isBlank(signature)){
			return false;
		}
		String dataStr="";
		if(Constant.PAYMENT_BIZ_TYPE.MERGE_PAY.name().equals(bizType)){
			dataStr=mergePayData+PaymentConstant.SIG_PRIVATE_KEY;
		}else{
			String royaltyParameters=this.getRoyaltyParameters();
			if(StringUtils.isBlank(royaltyParameters)){
				royaltyParameters="";
			}
			dataStr = String.valueOf(objectId)+objectType+String.valueOf(amount)+paymentType+bizType+royaltyParameters+PaymentConstant.SIG_PRIVATE_KEY;
		}
		log.info("source: " + dataStr);
		String md5 = MD5.md5(dataStr);
		log.info("md5: " + md5);
		log.info("signature: "+signature);
		System.out.println("md5: " + md5);
		System.out.println("signature: "+signature);
		return signature.equalsIgnoreCase(md5);
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean initPayment() {
		log.info("init to pay :"+StringUtil.printParam(this));
		if(Constant.PAYMENT_BIZ_TYPE.MERGE_PAY.name().equals(bizType)){
			return initMergePayment();
		}
		else{
			PayPayment payment = new PayPayment();
			payment.setBizType(bizType);
			payment.setObjectId(objectId);
			payment.setObjectType(objectType);
			payment.setPaymentType(paymentType);
			payment.setPaymentGateway(getGateway());
			payment.setAmount(amount);
			payment.setStatus(Constant.PAYMENT_SERIAL_STATUS.CREATE.name());
			payment.setSerial(payment.geneSerialNo());
			payment.setCreateTime(new Date());
			payment.setPaymentTradeNo(getPaymentTradeNo(objectId));
			payInfo = getPostData(payment);
			if(Constant.PAYMENT_OPERATE_TYPE.PRE_PAY.name().equalsIgnoreCase(paymentType)){
				PayPrePayment prePayment = new PayPrePayment();
				prePayment.setStatus(Constant.PAYMENT_PRE_STATUS.CREATE.name());
				payPaymentService.savePaymentAndPrePayment(payment, prePayment);
			}else if(Constant.PAYMENT_OPERATE_TYPE.PAY.name().equalsIgnoreCase(paymentType)){
				 payPaymentService.savePayment(payment);
			}
			log.info("create payment:"+StringUtil.printParam(payment));
			return true;
		}
	}
	/**
	 * 合并支付-初始化支付记录并组装支付数据
	 * @author ZHANG Nan
	 * @return
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	private boolean initMergePayment(){
		log.info("The merger payments start creating payment data ,mergePayData="+mergePayData);
		JSONArray jSONArray=JSONArray.fromObject(mergePayData);
		List<PayMergePayment> payMergePaymentList=jSONArray.toList(jSONArray,new PayMergePayment(),new JsonConfig());
		String paymentTradeNo=getPaymentTradeNo(Long.parseLong(payMergePaymentList.size()+""));
		Long amountSum=0L;
		for (PayMergePayment payMergePayment : payMergePaymentList) {
			PayPayment payment = new PayPayment();
			payment.setBizType(payMergePayment.getBizType());
			payment.setObjectId(payMergePayment.getObjectId());
			payment.setObjectType(payMergePayment.getObjectType());
			payment.setPaymentType(payMergePayment.getPaymentType());
			payment.setPaymentGateway(getGateway());
			payment.setAmount(payMergePayment.getAmount());
			payment.setStatus(Constant.PAYMENT_SERIAL_STATUS.CREATE.name());
			payment.setSerial(payment.geneSerialNo());
			payment.setCreateTime(new Date());
			payment.setPaymentTradeNo(paymentTradeNo);
			payPaymentService.savePayment(payment);
			amountSum+=payment.getAmount();
			log.info("merge pay create payment:"+StringUtil.printParam(payment));
		}
		PayPayment payInfoPayment = new PayPayment();
		payInfoPayment.setAmount(amountSum);
		payInfoPayment.setBizType(bizType);
		payInfoPayment.setCreateTime(new Date());
		payInfoPayment.setPaymentTradeNo(paymentTradeNo);
		payInfo = getPostData(payInfoPayment);
		log.info("The merger payments start creating payment data ,payInfoPayment="+StringUtil.printParam(payInfoPayment));
		return true;
	}
	
	/**
	 * 取10位流水号.
	 * @return
	 */
	public String getserialNoPayTimes(){
		if(Constant.PAYMENT_BIZ_TYPE.MERGE_PAY.name().equals(bizType)){
			return SerialUtil.generate10ByteSerial();
		}
		else{
			return SerialUtil.generate10Byte(objectId, getPayTimes());	
		}
	}

	/**
	 * 订单的支付次数
	 * @return
	 */
	public Long getPayTimes(){
		return payPaymentService.selectPaymentCount(objectId)+1L;
	}
	
	public void setWaitPayment(String waitPayment) {
		this.waitPayment = waitPayment;
	}

	/**
	 * 获取支付等待时间.
	 * @return
	 */
	public String getWaitPayment(){
		return waitPayment;
	}
	/**
	 * 获取支付网关.
	 * @return
	 */
	abstract String getGateway();
	
	/**
	 * 获取支付交易流水号
	 * @author ZHANG Nan
	 * @return
	 */
	abstract String getPaymentTradeNo(Long randomId);
	
	abstract PostData getPostData(PayPayment payPayment);
	

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getObjectId() {
		return objectId;
	}

	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	public String getBankid() {
		return bankid;
	}

	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}
	
	public PayPaymentService getPayPaymentService() {
		return payPaymentService;
	}

	public PostData getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(PostData payInfo) {
		this.payInfo = payInfo;
	}

	public String getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getInstalmentNum() {
		return instalmentNum;
	}

	public void setInstalmentNum(String instalmentNum) {
		this.instalmentNum = instalmentNum;
	}

	public String getBeforeURL() {
		return beforeURL;
	}

	public void setBeforeURL(String beforeURL) {
		this.beforeURL = beforeURL;
	}

	public String getCsno() {
		return csno;
	}

	public void setCsno(String csno) {
		this.csno = csno;
	}

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public String getMergePayData() {
		return mergePayData;
	}

	public void setMergePayData(String mergePayData) {
		this.mergePayData = mergePayData;
	}

	public String getObjectIds() {
		return objectIds;
	}

	public void setObjectIds(String objectIds) {
		this.objectIds = objectIds;
	}
	
	public String getRoyaltyParameters() {
		return royaltyParameters;
	}
	public void setRoyaltyParameters(String royaltyParameters) {
		this.royaltyParameters = royaltyParameters;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectDesc() {
		return objectDesc;
	}

	public void setObjectDesc(String objectDesc) {
		this.objectDesc = objectDesc;
	}

	public String getObjectPageUrl() {
		return objectPageUrl;
	}

	public void setObjectPageUrl(String objectPageUrl) {
		this.objectPageUrl = objectPageUrl;
	}

}
