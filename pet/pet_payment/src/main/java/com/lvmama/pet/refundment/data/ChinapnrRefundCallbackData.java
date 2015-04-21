package com.lvmama.pet.refundment.data;

import java.util.Map;

import org.apache.log4j.Logger;

import chinapnr.SecureLink;

import com.lvmama.comm.vo.PaymentConstant;

/**
 * 汇付天下退款通知数据.
 * @author sunruyi
 */
public class ChinapnrRefundCallbackData implements RefundCallbackData {
	
	private Logger LOG = Logger.getLogger(getClass());
	private String respCode;
	private String chkValue;
	private String errMsg;
	private String oldOrdId;
	private String ordId;
	private String cmdId;
	
	public ChinapnrRefundCallbackData(Map<String,String> params){
		LOG.info("chinapnr refund async result ,params="+params);
		this.respCode=params.get("RespCode");
		this.chkValue=params.get("ChkValue");
		if("000000".equals(respCode)){
			this.errMsg="成功";
		}
		else{
			this.errMsg=params.get("ErrMsg");
		}
		this.oldOrdId=params.get("OldOrdId");
		this.ordId=params.get("OrdId");
		this.cmdId=params.get("CmdId");
	}
	@Override
	public boolean isSuccess() {
		//return "000000".equals(respCode) && checkSignature();
		//TODO 等待汇付天下解决乱码问题后再验签
		return "000000".equals(respCode);
	}

	@Override
	public boolean checkSignature() {
		String MerKeyFile = PaymentConstant.getInstance().getProperty("CHINAPNR_PG_PATH");
		String MerData = cmdId + respCode + ordId + oldOrdId + errMsg;
		SecureLink sl = new SecureLink();
		int ret = sl.VeriSignMsg(MerKeyFile , MerData, chkValue);
		LOG.info("chinapnr refund async result verifySign ret="+ret);
		return ret==0;
	}

	@Override
	public String getSerial() {
		return ordId;
	}

	@Override
	public String getCallbackInfo() {
		return errMsg;
	}
	
	
	
	
	
	
	
	
	
	
	
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getChkValue() {
		return chkValue;
	}
	public void setChkValue(String chkValue) {
		this.chkValue = chkValue;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getOldOrdId() {
		return oldOrdId;
	}
	public void setOldOrdId(String oldOrdId) {
		this.oldOrdId = oldOrdId;
	}
	public String getOrdId() {
		return ordId;
	}
	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}
	public String getCmdId() {
		return cmdId;
	}
	public void setCmdId(String cmdId) {
		this.cmdId = cmdId;
	}	
}
