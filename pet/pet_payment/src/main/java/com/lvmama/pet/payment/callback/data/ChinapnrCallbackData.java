package com.lvmama.pet.payment.callback.data;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import chinapnr.SecureLink;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.vo.PaymentErrorData;

public class ChinapnrCallbackData implements CallbackData {
	
	protected transient final Log log = LogFactory.getLog(getClass());
	private Map<String, String> paraMap;
	private String cmdId;
	private String merId;
	private String respCode;
	private String trxId;
	private String ordAmt;
	private String curCode;
	private String pid;
	private String ordId;
	private String merPriv;
	private String retType;
	private String divDetails;
	private String gateId;
	private String chkValue;
	
	public ChinapnrCallbackData (Map<String, String> paraMap) {
		this.paraMap = paraMap;
		log.info("chinapnr payCallback async: paraMap="+paraMap.toString());
		cmdId = this.paraMap.get("CmdId");
		
		merId = this.paraMap.get("MerId");
		respCode = this.paraMap.get("RespCode");
		trxId = this.paraMap.get("TrxId");
		ordAmt = this.paraMap.get("OrdAmt");
		curCode = this.paraMap.get("CurCode");
		pid = this.paraMap.get("Pid");
		ordId = this.paraMap.get("OrdId");
		merPriv = this.paraMap.get("MerPriv");
		retType = this.paraMap.get("RetType");
		divDetails = this.paraMap.get("DivDetails");
		gateId = this.paraMap.get("GateId");
		chkValue = this.paraMap.get("ChkValue");
		
	}
	
	@Override
	public boolean checkSignature() {
		String 	MerKeyFile	= PaymentConstant.getInstance().getProperty("CHINAPNR_PG_PATH");
		String MerData = cmdId + merId + respCode + trxId + ordAmt + curCode + pid + ordId + merPriv + retType + divDetails + gateId;
		SecureLink sl = new SecureLink();
		int ret = sl.VeriSignMsg(MerKeyFile , MerData, chkValue);
		log.info("chinapnr pay verifySign ret="+ret);
		return ret==0;
	}
	
	@Override
	public String getCallbackInfo() {
		return PaymentErrorData.getInstance().getErrorMessage(Constant.PAYMENT_GATEWAY.CHINAPNR.name(), this.respCode);
	}

	@Override
	public String getGatewayTradeNo() {
		return this.trxId;
	}

	@Override
	public String getMessage() {
		return null;
	}

	@Override
	public long getPaymentAmount() {
		return PriceUtil.convertToFen(Float.parseFloat(ordAmt));
	}

	
	@Override
	public String getPaymentGateway() {
		return Constant.PAYMENT_GATEWAY.CHINAPNR.name();
	}


	@Override
	public boolean isSuccess() {
		return ("00".equals(respCode) || "000000".equals(respCode)) && checkSignature();
	}

	@Override
	public String getPaymentTradeNo() {
		return ordId;
	}

	@Override
	public String getRefundSerial() {
		return null;
	}

	@Override
	public Date getCallBackTime() {
		return new Date();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public Map<String, String> getParaMap() {
		return paraMap;
	}

	public void setParaMap(Map<String, String> paraMap) {
		this.paraMap = paraMap;
	}

	public String getCmdId() {
		return cmdId;
	}

	public void setCmdId(String cmdId) {
		this.cmdId = cmdId;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getTrxId() {
		return trxId;
	}

	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}

	public String getOrdAmt() {
		return ordAmt;
	}

	public void setOrdAmt(String ordAmt) {
		this.ordAmt = ordAmt;
	}

	public String getCurCode() {
		return curCode;
	}

	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getOrdId() {
		return ordId;
	}

	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}

	public String getMerPriv() {
		return merPriv;
	}

	public void setMerPriv(String merPriv) {
		this.merPriv = merPriv;
	}

	public String getRetType() {
		return retType;
	}

	public void setRetType(String retType) {
		this.retType = retType;
	}

	public String getDivDetails() {
		return divDetails;
	}

	public void setDivDetails(String divDetails) {
		this.divDetails = divDetails;
	}

	public String getGateId() {
		return gateId;
	}

	public void setGateId(String gateId) {
		this.gateId = gateId;
	}

	public String getChkValue() {
		return chkValue;
	}

	public void setChkValue(String chkValue) {
		this.chkValue = chkValue;
	}
}
