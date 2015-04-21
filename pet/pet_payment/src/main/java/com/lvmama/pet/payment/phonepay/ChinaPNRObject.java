package com.lvmama.pet.payment.phonepay;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import chinapnr.SecureLink;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.vo.PaymentErrorData;

public class ChinaPNRObject {
	
	protected transient final Log log = LogFactory.getLog(getClass());
	/**
	 * 同步返回码
	 */
	private String respCode;
	
	private String trxId;
	/**
	 * 消息类型
	 * 电话支付填“TelPay”
	        电话支付退款填“TelRefund”
	 */
	private String cmdId;
	/**
	 * 商户号
	 */
	private String merId;
	/**
	 * 订单号
	 */
	private String ordId;
	/**
	 * 交易金额
	 */
	private String ordAmt;
	
	private String curCode;
	/**
	 * 网关号
	 */
	private String gateId;
	/**
	 * 商户私有域
	 */
	private String merPriv;
	/**
	 * 分账指令串
	 */
	private String divDetails;
	private String pid;
	private String retType;
	/**
	 * 签名
	 */
	private String chkValue;
	private String errMsg;
	
	/**
	 * 解析请求参数--汇付天下返回参数
	 * @param xmlRequest
	 * @return
	 */
	public ChinaPNRObject createInstance(String responseXml) {
		log.info("chinapnr sync pay,responseXml="+responseXml);
		ChinaPNRObject chinaPNRObject=new ChinaPNRObject();
		Map<String,String> paramMap=new HashMap<String,String>();
		if(StringUtils.isNotBlank(responseXml)){
			String data[]=responseXml.split("\r\n");
			for(int i=0,j=data.length;i<j;i++){
				paramMap.put(data[i].split("=")[0], data[i].split("=")[1]);
			}
			chinaPNRObject.setCmdId(paramMap.get("CmdId"));
			chinaPNRObject.setRespCode(paramMap.get("RespCode"));
			chinaPNRObject.setOrdAmt(paramMap.get("OrdAmt"));
			chinaPNRObject.setOrdId(paramMap.get("OrdId"));
			chinaPNRObject.setMerId(paramMap.get("MerId"));
			chinaPNRObject.setGateId(paramMap.get("GateId"));
			chinaPNRObject.setChkValue(paramMap.get("ChkValue"));
			chinaPNRObject.setErrMsg(paramMap.get("ErrMsg"));
		}
		return chinaPNRObject;
	}
	
	public boolean isSuccess() {
		return ("00".equals(respCode)|| "000000".equals(respCode)) && verifySign();
	}
	
	public long getPayedAmount() {
		if(StringUtils.isNotBlank(ordAmt)){
			return PriceUtil.convertToFen(Float.parseFloat(ordAmt));
		}
		return 0L;
	}
	
	public String getErrorMsg() {
		if(StringUtils.isBlank(errMsg)){
			return PaymentErrorData.getInstance().getErrorMessage(Constant.PAYMENT_GATEWAY.CHINAPNR.name(),respCode);	
		}
		return errMsg;
	}
	
	public Boolean verifySign(){
		String 	MerKeyFile	= PaymentConstant.getInstance().getProperty("CHINAPNR_PG_PATH");
		String MerData = cmdId + respCode + merId + ordId + ordAmt + gateId;
		SecureLink sl = new SecureLink();
		int ret = sl.VeriSignMsg(MerKeyFile , MerData, chkValue);
		log.info("chinapnr pay verifySign ret="+ret+",MerData="+MerData+",chkValue="+chkValue);
		return ret==0;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
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

	public String getOrdId() {
		return ordId;
	}

	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}

	public String getOrdAmt() {
		return ordAmt;
	}

	public void setOrdAmt(String ordAmt) {
		this.ordAmt = ordAmt;
	}

	public String getGateId() {
		return gateId;
	}

	public void setGateId(String gateId) {
		this.gateId = gateId;
	}

	public String getMerPriv() {
		return merPriv;
	}

	public void setMerPriv(String merPriv) {
		this.merPriv = merPriv;
	}

	public String getDivDetails() {
		return divDetails;
	}

	public void setDivDetails(String divDetails) {
		this.divDetails = divDetails;
	}

	public String getChkValue() {
		return chkValue;
	}

	public void setChkValue(String chkValue) {
		this.chkValue = chkValue;
	}

	public String getTrxId() {
		return trxId;
	}

	public void setTrxId(String trxId) {
		this.trxId = trxId;
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

	public String getRetType() {
		return retType;
	}

	public void setRetType(String retType) {
		this.retType = retType;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}	
}
