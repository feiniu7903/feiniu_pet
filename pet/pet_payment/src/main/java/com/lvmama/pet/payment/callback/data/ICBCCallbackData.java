package com.lvmama.pet.payment.callback.data;

import java.io.FileInputStream;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import cn.com.infosec.icbc.ReturnValue;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.StringDom4jUtil;

/**
 * 工行支付回调信息
 * @author ZHANG Nan
 *
 */
public class ICBCCallbackData implements CallbackData {
	
	private Logger LOG = Logger.getLogger(this.getClass());
	
	private String merVAR;
	private String signMsg;
	private String notifyData;
	
	//notifyData参数 数据
	/**
	 * 接口名称
	 */
	private String interfaceName;
	/**
	 * 接口版本号
	 */
	private String interfaceVersion;
	
	/**
	 * 交易日期时间
	 */
	private String orderDate;
	/**
	 * 订单号
	 */
	private String orderid;
	/**
	 * 订单金额
	 */
	private Long amount;
	/**
	 * 分期付款期数
	 */
	private String installmentTimes;
	/**
	 * 商户账号
	 */
	private String merAcct;
	/**
	 * 银行指令序号
	 */
	private String TranSerialNo;

	/**
	 * 支付币种
	 */
	private String curType;
	/**
	 * 商户代码
	 */
	private String merID;
	
	/**
	 * 检验联名标志
	 */
	private String verifyJoinFlag;
	/**
	 * 客户联名标志
	 */
	private String JoinFlag;
	/**
	 * 联名会员号
	 */
	private String UserNum;
	/**
	 * 批次号
	 */
	private String TranBatchNo;
	/**
	 * 返回通知日期时间
	 */
	private String notifyDate;
	/**
	 * 订单处理状态
	 */
	private String tranStat;
	/**
	 * 错误描述
	 */
	private String comment;
	
	
	
	private byte[] notifyDataB;
	private byte[] signature;
	
	
	public ICBCCallbackData(Map<String, String> map) {
		try {
			LOG.info("ICBCCallbackData Map:"+map.toString());
			String notifyDataBase64=map.get("notifyData");
			this.notifyDataB = ReturnValue.base64dec(notifyDataBase64.getBytes("GBK"));
			this.notifyData = new String(notifyDataB,"GBK");
			LOG.info("notifyData="+notifyData);
			this.signMsg = map.get("signMsg");
			this.signature = ReturnValue.base64dec(signMsg.getBytes());
			Map<String, String> paramMap=StringDom4jUtil.parseICBCBankResult(notifyData);
			LOG.info("ICBCCallbackData paramMap:"+paramMap.toString());
			this.interfaceName=paramMap.get("interfaceName");
			this.interfaceVersion=paramMap.get("interfaceVersion");
			this.orderDate=paramMap.get("orderDate");
			this.orderid=paramMap.get("orderid");
			this.amount=Long.parseLong(paramMap.get("amount"));
			this.installmentTimes=paramMap.get("installmentTimes");
			this.merAcct=paramMap.get("merAcct");
			this.TranSerialNo=paramMap.get("tranSerialNo");
			this.curType=paramMap.get("curType");
			this.merID=paramMap.get("merID");
			this.verifyJoinFlag=paramMap.get("verifyJoinFlag");
			this.JoinFlag=paramMap.get("JoinFlag");
			this.UserNum=paramMap.get("UserNum");
			this.TranBatchNo=paramMap.get("TranBatchNo");
			this.notifyDate=paramMap.get("notifyDate");
			this.tranStat=paramMap.get("tranStat");
			this.comment=paramMap.get("comment");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public long getPaymentAmount() {
		return this.amount*100;
	}

	@Override
	public boolean checkSignature() {
		try {
			FileInputStream f = new FileInputStream(PaymentConstant.getInstance().getProperty("ICBC_PUBLIC_CERT_PATH"));
			byte[] bs = new byte[f.available()];
			f.read(bs);
			f.close();
			int result = ReturnValue.verifySign(notifyDataB, notifyDataB.length,bs, signature);
			LOG.info("com.lvmama.pet.payment.callback.data.ICBCCallbackData.checkSignature() result="+result);
			if(result == 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return false;
	}

	
	
	@Override
	public String getCallbackInfo() {
		return this.comment;
	}

	@Override
	public String getPaymentTradeNo() {
		return this.orderid;
	}
	@Override
	public String getGatewayTradeNo() {
		return this.TranSerialNo;
	}
	@Override
	public String getRefundSerial() {
		return this.orderid;
	}

	@Override
	public String getMessage() {
		return this.comment;
	}



	@Override
	public boolean isSuccess() {
		if (tranStat!=null && tranStat.equalsIgnoreCase("1") && checkSignature()) {
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String getPaymentGateway() {
		return Constant.PAYMENT_GATEWAY.ICBC.name();
	}

	@Override
	public Date getCallBackTime() {
		if(StringUtils.isNotBlank(notifyDate)){
			return DateUtil.toDate(notifyDate, "yyyyMMddHHmmss");
		}
		else{
			return new Date();	
		}
	}

	public String getMerVAR() {
		return merVAR;
	}

	public void setMerVAR(String merVAR) {
		this.merVAR = merVAR;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public String getNotifyData() {
		return notifyData;
	}

	public void setNotifyData(String notifyData) {
		this.notifyData = notifyData;
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

	public String getTranSerialNo() {
		return TranSerialNo;
	}

	public void setTranSerialNo(String tranSerialNo) {
		TranSerialNo = tranSerialNo;
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

	public String getVerifyJoinFlag() {
		return verifyJoinFlag;
	}

	public void setVerifyJoinFlag(String verifyJoinFlag) {
		this.verifyJoinFlag = verifyJoinFlag;
	}

	public String getJoinFlag() {
		return JoinFlag;
	}

	public void setJoinFlag(String joinFlag) {
		JoinFlag = joinFlag;
	}

	public String getUserNum() {
		return UserNum;
	}

	public void setUserNum(String userNum) {
		UserNum = userNum;
	}

	public String getTranBatchNo() {
		return TranBatchNo;
	}

	public void setTranBatchNo(String tranBatchNo) {
		TranBatchNo = tranBatchNo;
	}

	public String getNotifyDate() {
		return notifyDate;
	}

	public void setNotifyDate(String notifyDate) {
		this.notifyDate = notifyDate;
	}

	public String getTranStat() {
		return tranStat;
	}

	public void setTranStat(String tranStat) {
		this.tranStat = tranStat;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
