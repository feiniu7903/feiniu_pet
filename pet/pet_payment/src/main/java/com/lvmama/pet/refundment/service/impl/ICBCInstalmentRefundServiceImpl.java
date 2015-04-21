
package com.lvmama.pet.refundment.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import cn.com.infosec.icbc.ReturnValue;

import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.pet.utils.StringDom4jUtil;
/**
 * 工行分期退款
 * @author ZHANG Nan
 *
 */
public class ICBCInstalmentRefundServiceImpl implements BankRefundmentService {
	/**
	 * LOG.
	 */
	private static final Logger LOG = Logger.getLogger(ICBCInstalmentRefundServiceImpl.class);
	
	/**
	 * 交易代码
	 */
	private String transCode="ESTAGEBUSC";
	/**
	 * 集团CIS号
	 */
	private String CIS=PaymentConstant.getInstance().getProperty("ICBC_MERCHANT_CLS");
	/**
	 * 归属银行编号
	 */
	private String bankCode="102";
	/**
	 * 证书ID
	 */
	private String ID=PaymentConstant.getInstance().getProperty("ICBC_MERCHANT_REFUND_ID");
	
	/**
	 * 支付指令类型   0：退货 1：返还  2：转付
	 */
	private String tranType="0";
	/**
	 * 商城类型   1：B2B商城  2：B2C商城
	 */
	private String shopType="2";
	/**
	 * 商城代码
	 */
	private String shopCode=PaymentConstant.getInstance().getProperty("ICBC_MERCHANT_ID");
	/**
	 * 商城账号
	 */
	private String shopAcct=PaymentConstant.getInstance().getProperty("ICBC_MERCHANT_ACCOUNT");
	
	/**
	 * 支付速度方式  0：普通 1：加急（B2C交易可为空）
	 */
	private String payType="";
	
	/**
	 * 转付人名称  B2C转付操作时必输，其他情况为空
	 */
	private String transferName="";
	/**
	 * 转付账号/卡号  B2C转付操作时必输，其他情况为空
	 */
	private String transferAccNo="";
	
	/**
	 * 请求备用字段2  备用，目前无意义
	 */
	private String reqReserved2="";
	
	
	/**
	 * 获取签名请求URL
	 */
	private String refundSignURL=PaymentConstant.getInstance().getProperty("ICBC_REFUND_GETSIGN_URL");
	/**
	 * 退货请求URL
	 */
	private String refundURL=PaymentConstant.getInstance().getProperty("ICBC_REFUND_URL");
	
	/**
	 * 退货请求时版本
	 */
	private String version="0.0.0.1";
	
	private String character="GBK";
	/**
	 * 工行分期退款逻辑
	 */
	@Override
	public BankReturnInfo refund(RefundmentToBankInfo info) {
		BankReturnInfo returnInfo =new BankReturnInfo();
	    try {
	    	//初始化退货参数
	    	Date now=new Date(); 
	    	String tranDate=DateUtil.formatDate(now, "yyyyMMdd");
	    	String tranTime=DateUtil.formatDate(now, "HHmmssSSS");
	    	String fSeqno=SerialUtil.generate15ByteSerial();
	    	String reqReserved1=fSeqno;
	    	String orderNum=info.getPaymentTradeNo();
	    	String payDate=DateUtil.formatDate(info.getCallbackTime(), "yyyyMMdd");
	    	String payAmt=info.getRefundAmount()+"";
	    	String signTime=DateUtil.formatDate(now, "yyyyMMddHHmmssSSS");
	    	//封装XML格式退货数据
	    	String sendXML=generateRefundData(tranDate,tranTime,fSeqno,orderNum,payDate,payAmt,signTime,reqReserved1);
	    	LOG.info("icbc_instalment_refund refundSignURL="+refundSignURL+",sendXML:"+sendXML);
	    	//获取签名
	    	String signResult=HttpsUtil.requestPostData(refundSignURL, sendXML, "INFOSEC_SIGN/1.0",character , HttpsUtil.SO_TIMEOUT_60S, HttpsUtil.SO_TIMEOUT_60S).getResponseString(character);
	    	LOG.info("icbc_instalment_refund signResult:"+signResult);
	    	
	    	String start="<sign>";
	    	String end="</sign>";
	    	String reqData=signResult.substring(signResult.indexOf(start)+start.length(), signResult.indexOf(end));
	    	String url=refundURL+"?userID="+ID+"&PackageID="+fSeqno+"&SendTime="+signTime;
	    	String postData="Version="+version+"&TransCode="+this.transCode+"&BankCode="+this.bankCode+"&GroupCIS="+this.CIS+"&ID="+this.ID+"&PackageID="+fSeqno+"&Cert=&reqData="+reqData;
	    	LOG.info("icbc_instalment_refund refundURL="+url+",postData:"+postData);
	    	//发起退货请求
	    	String result=HttpsUtil.requestPostData(url, postData,"application/x-www-form-urlencoded", character, HttpsUtil.SO_TIMEOUT_60S, HttpsUtil.SO_TIMEOUT_60S).getResponseString(character);
	    	LOG.info("icbc_instalment_refund result:"+result);
	    	
	    	//解析退货请求返回结果
	    	byte[] resultByte=ReturnValue.base64dec(result.getBytes(character));
	    	String resultXML=new String(resultByte,character);
	    	resultXML="<"+resultXML.substring(3,resultXML.length());
	    	LOG.info("icbc_instalment_refund resultXML="+resultXML);
	    	returnInfo.setSerial(fSeqno);
	    	if(StringUtils.isNotBlank(result) && result.indexOf("errorCode")==0){
	    		returnInfo.setCodeInfo(result.split("=")[1]);
		    	returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
	    	}
	    	else{
	    		Map<String, String> map = StringDom4jUtil.parseICBCBankRefundResult(resultXML);
	    		String retCode=map.get("RetCode");
	    		if("0".equals(retCode)){
		    		returnInfo.setCodeInfo("SUCCESS");
			    	returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name());	
	    		}
	    		else{
	    			returnInfo.setCodeInfo("code="+retCode+",msg="+map.get("RetMsg"));
			    	returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());	
	    		}
	    	}
		} catch (Exception e) {
			e.printStackTrace();
			returnInfo.setCodeInfo("ICBC instalment refund error,msg="+e.getMessage());
			returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());	
		}
       return returnInfo;    
	}
	
	/**
	 * 封装退款数据
	 * @author ZHANG Nan
	 * @return 退款请求数据
	 */
	private String generateRefundData(String tranDate,String tranTime,String fSeqno,String orderNum,String payDate,String payAmt,String signTime,String reqReserved1){
		StringBuffer xml=new StringBuffer("");
		xml.append("<?xml version=\"1.0\" encoding = \"GBK\"?>");
		xml.append("<CMS>");
			xml.append("<eb>");
				xml.append("<pub>");
					xml.append("<TransCode>"+transCode+"</TransCode>");
					xml.append("<CIS>"+CIS+"</CIS>");
					xml.append("<BankCode>"+bankCode+"</BankCode>");
					xml.append("<ID>"+ID+"</ID>	");
					xml.append("<TranDate>"+tranDate+"</TranDate>");
					xml.append("<TranTime>"+tranTime+"</TranTime>");
					xml.append("<fSeqno>"+fSeqno+"</fSeqno>");
				xml.append("</pub>");
				xml.append("<in>");
					xml.append("<TranType>"+tranType+"</TranType>");
					xml.append("<ShopType>"+shopType+"</ShopType>");
					xml.append("<ShopCode>"+shopCode+"</ShopCode>");
					xml.append("<ShopAcct>"+shopAcct+"</ShopAcct>");
					xml.append("<OrderNum>"+orderNum+"</OrderNum>");
					xml.append("<PayType>"+payType+"</PayType>");
					xml.append("<PayDate>"+payDate+"</PayDate>");
					xml.append("<TransferName>"+transferName+"</TransferName>");
					xml.append("<TransferAccNo>"+transferAccNo+"</TransferAccNo>");
					xml.append("<PayAmt>"+payAmt+"</PayAmt>");
					xml.append("<SignTime>"+signTime+"</SignTime>");
					xml.append("<ReqReserved1>"+reqReserved1+"</ReqReserved1>");
					xml.append("<ReqReserved2>"+reqReserved2+"</ReqReserved2>");
				xml.append("</in>");
			xml.append("</eb>");
		xml.append("</CMS>");
		return xml.toString();
	}
	
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getCIS() {
		return CIS;
	}
	public void setCIS(String cIS) {
		CIS = cIS;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	public String getShopType() {
		return shopType;
	}
	public void setShopType(String shopType) {
		this.shopType = shopType;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getShopAcct() {
		return shopAcct;
	}
	public void setShopAcct(String shopAcct) {
		this.shopAcct = shopAcct;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getTransferName() {
		return transferName;
	}
	public void setTransferName(String transferName) {
		this.transferName = transferName;
	}
	public String getTransferAccNo() {
		return transferAccNo;
	}
	public void setTransferAccNo(String transferAccNo) {
		this.transferAccNo = transferAccNo;
	}
	public String getReqReserved2() {
		return reqReserved2;
	}
	public void setReqReserved2(String reqReserved2) {
		this.reqReserved2 = reqReserved2;
	}
	public String getRefundURL() {
		return refundURL;
	}
	public void setRefundURL(String refundURL) {
		this.refundURL = refundURL;
	}
}
