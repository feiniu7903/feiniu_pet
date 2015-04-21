package com.lvmama.pet.payment.post.data;

import java.io.FileInputStream;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.infosec.icbc.ReturnValue;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.PaymentConstant;
/**
 * 工行直连支付DATA
 * @author ZHANG Nan
 *
 */
public class ICBCPostData implements PostData {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(ICBCPostData.class);
	
	/**
	 * 接口名称
	 */
	private String interfaceName="ICBC_PERBANK_B2C";
	/**
	 * 接口版本号
	 */
	private String interfaceVersion="1.0.0.11";
	/**
	 * 交易数据
	 */
	private String tranData="";
	/**
	 * 订单签名数据
	 */
	private String merSignMsg="";
	/**
	 * 商城证书公钥
	 */
	private String merCert="";
	
	//tranData参数数据包含字段定义
	/**
	 * 交易日期时间
	 */
	private String orderDate="";
	/**
	 * 订单号
	 */
	private String orderid="";
	/**
	 * 订单金额
	 */
	private Long amount=0L;
	/**
	 * 分期付款期数
	 */
	private String installmentTimes="1";
	/**
	 * 商户账号
	 */
	private String merAcct =PaymentConstant.getInstance().getProperty("ICBC_MERCHANT_ACCOUNT");
	/**
	 * 商品编号
	 */
	private String goodsID="";
	/**
	 * 商品名称
	 */
	private String goodsName="lvmama_goods";
	/**
	 * 商品数量
	 */
	private String goodsNum="";
	/**
	 * 已含运费金额
	 */
	private String carriageAmt="";
	/**
	 * 检验联名标志
	 */
	private String verifyJoinFlag="0";
	/**
	 * 语言版本
	 */
	private String language="ZH_CN";
	/**
	 * 支付币种
	 */
	private String curType="001";
	/**
	 * 商户代码
	 */
	private String merID=PaymentConstant.getInstance().getProperty("ICBC_MERCHANT_ID");
	/**
	 * 支持订单支付的银行卡种类
	 */
	private String creditType="2";
	/**
	 * 通知类型
	 */
	private String notifyType="HS";
	
	/**
	 * 结果发送类型
	 */
	private String resultType="0";
	/**
	 * 商户reference
	 */
	private String merReference="*.lvmama.com";
	
	/**
	 * 客户端IP
	 */
	private String merCustomIp="";
	
	/**
	 * 虚拟商品/实物商品标志位
	 */
	private String goodsType="";
	/**
	 * 买家用户号
	 */
	private String merCustomID="";
	/**
	 * 买家联系电话
	 */
	private String merCustomPhone="";
	/**
	 * 收货地址
	 */
	private String goodsAddress="";
	/**
	 * 订单备注
	 */
	private String merOrderRemark="";
	/**
	 * 商城提示
	 */
	private String merHint="";
	/**
	 * 备注字段1
	 */
	private String remark1="";
	/**
	 * 备注字段2
	 */
	private String remark2="";
	/**
	 * 返回商户URL
	 */
	private String merURL=PaymentConstant.getInstance().getProperty("ICBC_NOTIFY_URL");
	/**
	 * 返回商户变量
	 */
	private String merVAR="";
	
	/**
	 * 交易数据XML原文
	 */
	private String tranDataXML="";
	
	
	public ICBCPostData(PayPayment payment) {
		LOG.info("ICBCPostData,payment="+StringUtil.printParam(payment));
		this.orderDate=DateUtil.formatDate(new Date(), "yyyyMMddHHmmss");
		this.orderid=payment.getPaymentTradeNo();
		this.amount=payment.getAmount();
		this.merCert=getCert();
		this.tranDataXML=generateTranData();
		this.merSignMsg=signature();
		this.tranData=new String(ReturnValue.base64enc(this.tranDataXML.getBytes()));
	}
	/**
	 * 组装支付请求数据
	 * @author ZHANG Nan
	 * @return
	 */
	private String generateTranData(){
		StringBuffer tranDataXML=new StringBuffer("");
		tranDataXML.append("<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?>");
		tranDataXML.append("<B2CReq>");
			tranDataXML.append("<interfaceName>"+interfaceName+"</interfaceName>");
			tranDataXML.append("<interfaceVersion>"+interfaceVersion+"</interfaceVersion>");
			tranDataXML.append("<orderInfo>");
				tranDataXML.append("<orderDate>"+orderDate+"</orderDate>");
				tranDataXML.append("<curType>"+curType+"</curType>");
				tranDataXML.append("<merID>"+merID+"</merID>");
				tranDataXML.append("<subOrderInfoList>");
					tranDataXML.append("<subOrderInfo>");
						tranDataXML.append("<orderid>"+orderid+"</orderid>");
						tranDataXML.append("<amount>"+amount+"</amount>");
						tranDataXML.append("<installmentTimes>"+installmentTimes+"</installmentTimes>");
						tranDataXML.append("<merAcct>"+merAcct+"</merAcct>");
						tranDataXML.append("<goodsID>"+goodsID+"</goodsID>");
						tranDataXML.append("<goodsName>"+goodsName+"</goodsName>");
						tranDataXML.append("<goodsNum>"+goodsNum+"</goodsNum>");
						tranDataXML.append("<carriageAmt>"+carriageAmt+"</carriageAmt>");
					tranDataXML.append("</subOrderInfo>");
				tranDataXML.append("</subOrderInfoList>");
			tranDataXML.append("</orderInfo>");
			tranDataXML.append("<custom>");
				tranDataXML.append("<verifyJoinFlag>"+verifyJoinFlag+"</verifyJoinFlag>");
				tranDataXML.append("<Language>"+language+"</Language>");
			tranDataXML.append("</custom>");
			tranDataXML.append("<message>");
				tranDataXML.append("<creditType>"+creditType+"</creditType>");
				tranDataXML.append("<notifyType>"+notifyType+"</notifyType>");
				tranDataXML.append("<resultType>"+resultType+"</resultType>");
				tranDataXML.append("<merReference>"+merReference+"</merReference>");
				tranDataXML.append("<merCustomIp>"+merCustomIp+"</merCustomIp>");
				tranDataXML.append("<goodsType>"+goodsType+"</goodsType>");
				tranDataXML.append("<merCustomID>"+merCustomID+"</merCustomID>");
				tranDataXML.append("<merCustomPhone>"+merCustomPhone+"</merCustomPhone>");
				tranDataXML.append("<goodsAddress>"+goodsAddress+"</goodsAddress>");
				tranDataXML.append("<merOrderRemark>"+merOrderRemark+"</merOrderRemark>");
				tranDataXML.append("<merHint>"+merHint+"</merHint>");
				tranDataXML.append("<remark1>"+remark1+"</remark1>");
				tranDataXML.append("<remark2>"+remark2+"</remark2>");
				tranDataXML.append("<merURL>"+merURL+"</merURL>");
				tranDataXML.append("<merVAR>"+merVAR+"</merVAR>");
			tranDataXML.append("</message>");
		tranDataXML.append("</B2CReq>");
		LOG.info("ICBC tranDataXML:"+tranDataXML);
		return tranDataXML.toString();
	}
	
	private String getCert(){
		try {
			FileInputStream fs = new FileInputStream(PaymentConstant.getInstance().getProperty("ICBC_CERT_PATH"));
			byte[] bsc = new byte[fs.available()];
			fs.read(bsc);
			fs.close();
			byte[] cert = ReturnValue.base64enc(bsc);
			return  new String(cert);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@Override
	public String signature() {
		try {
			FileInputStream f = new FileInputStream(PaymentConstant.getInstance().getProperty("ICBC_PRIVATE_CERT_PATH"));
			byte[] bs = new byte[f.available()];
			f.read(bs);
			f.close();
			byte[] signature = ReturnValue.base64enc(ReturnValue.sign(this.tranDataXML.getBytes(), this.tranDataXML.getBytes().length, bs,PaymentConstant.getInstance().getProperty("ICBC_PRIVATE_CERT_PASSWORD").toCharArray()));
			return new String(signature,"iso-8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public String getPaymentTradeNo() {
		return getOrderid();
	}

	
	
	
	
	
	
	
	
	
	

	public String getInterfaceName() {
		return interfaceName;
	}


	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}


	public String getInterfaceVersion() {
		return interfaceVersion;
	}


	public void setInterfaceVersion(String interfaceVersion) {
		this.interfaceVersion = interfaceVersion;
	}


	public String getTranData() {
		return tranData;
	}


	public void setTranData(String tranData) {
		this.tranData = tranData;
	}


	public String getMerSignMsg() {
		return merSignMsg;
	}


	public void setMerSignMsg(String merSignMsg) {
		this.merSignMsg = merSignMsg;
	}


	public String getMerCert() {
		return merCert;
	}


	public void setMerCert(String merCert) {
		this.merCert = merCert;
	}


	public String getOrderDate() {
		return orderDate;
	}


	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}


	public String getOrderid() {
		return orderid;
	}


	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Long getAmount() {
		return amount;
	}


	public void setAmount(Long amount) {
		this.amount = amount;
	}


	public String getInstallmentTimes() {
		return installmentTimes;
	}


	public void setInstallmentTimes(String installmentTimes) {
		this.installmentTimes = installmentTimes;
	}


	public String getMerAcct() {
		return merAcct;
	}


	public void setMerAcct(String merAcct) {
		this.merAcct = merAcct;
	}


	public String getGoodsID() {
		return goodsID;
	}


	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}


	public String getGoodsName() {
		return goodsName;
	}


	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}


	public String getGoodsNum() {
		return goodsNum;
	}


	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}


	public String getCarriageAmt() {
		return carriageAmt;
	}


	public void setCarriageAmt(String carriageAmt) {
		this.carriageAmt = carriageAmt;
	}


	public String getVerifyJoinFlag() {
		return verifyJoinFlag;
	}


	public void setVerifyJoinFlag(String verifyJoinFlag) {
		this.verifyJoinFlag = verifyJoinFlag;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getCurType() {
		return curType;
	}


	public void setCurType(String curType) {
		this.curType = curType;
	}


	public String getMerID() {
		return merID;
	}


	public void setMerID(String merID) {
		this.merID = merID;
	}


	public String getCreditType() {
		return creditType;
	}


	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}


	public String getNotifyType() {
		return notifyType;
	}


	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}


	public String getResultType() {
		return resultType;
	}


	public void setResultType(String resultType) {
		this.resultType = resultType;
	}


	public String getMerReference() {
		return merReference;
	}


	public void setMerReference(String merReference) {
		this.merReference = merReference;
	}


	public String getMerCustomIp() {
		return merCustomIp;
	}


	public void setMerCustomIp(String merCustomIp) {
		this.merCustomIp = merCustomIp;
	}


	public String getGoodsType() {
		return goodsType;
	}


	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}


	public String getMerCustomID() {
		return merCustomID;
	}


	public void setMerCustomID(String merCustomID) {
		this.merCustomID = merCustomID;
	}


	public String getMerCustomPhone() {
		return merCustomPhone;
	}


	public void setMerCustomPhone(String merCustomPhone) {
		this.merCustomPhone = merCustomPhone;
	}


	public String getGoodsAddress() {
		return goodsAddress;
	}


	public void setGoodsAddress(String goodsAddress) {
		this.goodsAddress = goodsAddress;
	}


	public String getMerOrderRemark() {
		return merOrderRemark;
	}


	public void setMerOrderRemark(String merOrderRemark) {
		this.merOrderRemark = merOrderRemark;
	}


	public String getMerHint() {
		return merHint;
	}


	public void setMerHint(String merHint) {
		this.merHint = merHint;
	}


	public String getRemark1() {
		return remark1;
	}


	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}


	public String getRemark2() {
		return remark2;
	}


	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}


	public String getMerURL() {
		return merURL;
	}


	public void setMerURL(String merURL) {
		this.merURL = merURL;
	}


	public String getMerVAR() {
		return merVAR;
	}


	public void setMerVAR(String merVAR) {
		this.merVAR = merVAR;
	}

	public String getTranDataXML() {
		return tranDataXML;
	}

	public void setTranDataXML(String tranDataXML) {
		this.tranDataXML = tranDataXML;
	}
}
