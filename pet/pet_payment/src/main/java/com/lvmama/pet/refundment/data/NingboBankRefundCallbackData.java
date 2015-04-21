package com.lvmama.pet.refundment.data;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.infosec.NetSignServer;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.vo.PaymentErrorData;

/**
 * 宁波银行-支付宝退款通知数据.
 * @author ZHANG Nan
 */
public class NingboBankRefundCallbackData implements RefundCallbackData {
	
	private Logger log = Logger.getLogger(this.getClass());
	private Map<String, String> paraMap;
	//通知时间
	private String notify_time;
	//通知类型
	private String 	notify_type;
	//通知校验ID
	private String notify_id;
	//退款批次号
	private String batch_no;
	//退款成功总数
	private String success_num;
	//处理结果详情
	private String result_details;
	//解冻结果明细
	private String unfreezed_details;
	//商户传入退款订单号
	private String out_order_no;
	
	private String refundSerial;
	private String refundAmount;
	private String refundResult;
	
	
	private String sign;
	
	//证书验签参数
	private String dnHead =  PaymentConstant.getInstance().getProperty("NING_BO_BANK_DN_HEAD");
	private String dnTail = PaymentConstant.getInstance().getProperty("NING_BO_BANK_DN_TAIL");
	
	public NingboBankRefundCallbackData(Map<String,String> params){
		this.paraMap = params;
		log.info("ningboBank refund CallbackData:"+paraMap.toString());
		String notifyMsg=paraMap.get("notifyMsg");
		if(StringUtils.isNotBlank(notifyMsg)){
			String[] param=notifyMsg.split("\\|");
				this.notify_time=param[0];
				this.notify_type =param[1];
				this.notify_id =param[2];
				this.batch_no =param[3];
				this.success_num =param[4];
				this.result_details =param[5];
				this.unfreezed_details =param[6];
				this.out_order_no=param[7];
				this.sign =param[8];
		}
		this.analysisResultDetails();
	}
	/**
	 * 解析处理结果详情.
	 */
	private void analysisResultDetails(){
		String[] details = result_details.split("\\$");
		String payRefundResult = details[0];
		String[] payRefundResultDetail = payRefundResult.split("\\^");
		refundSerial = payRefundResultDetail[0];
		refundAmount = payRefundResultDetail[1];
		refundResult= payRefundResultDetail[2];
	}
	
	
	@Override
	public boolean isSuccess() {
		return refundResult != null && "SUCCESS".equals(refundResult) && checkSignature();
	}

	@Override
	public boolean checkSignature() {
		 String	sourceMsg = 
				notify_time  + "|" + 
				notify_type  + "|" + 
				notify_id  + "|" + 
				batch_no  + "|" + 
				success_num  + "|" + 
				result_details  + "|" + 
				unfreezed_details  + "|"+
				out_order_no  + "|";
		log.info("refund async callback signature SourceMsg="+sourceMsg);
		try {
			NetSignServer nss = new NetSignServer();
			nss.NSDetachedVerify(sign.getBytes("GBK"),sourceMsg.getBytes("GBK"));
			int i = nss.getLastErrnum();
			log.info("refund async callback verifyCode...." +i);
			if(i==0){
				return true;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getSerial() {
		return out_order_no;
	}

	@Override
	public String getCallbackInfo() {
		return PaymentErrorData.getInstance().getErrorMessage(Constant.PAYMENT_GATEWAY.NING_BO_BANK.name(), refundResult);
	}
	
	
	
	
	
	public Map<String, String> getParaMap() {
		return paraMap;
	}
	public void setParaMap(Map<String, String> paraMap) {
		this.paraMap = paraMap;
	}
	public String getNotify_time() {
		return notify_time;
	}
	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}
	public String getNotify_type() {
		return notify_type;
	}
	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}
	public String getNotify_id() {
		return notify_id;
	}
	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}
	public String getBatch_no() {
		return batch_no;
	}
	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	public String getSuccess_num() {
		return success_num;
	}
	public void setSuccess_num(String success_num) {
		this.success_num = success_num;
	}
	public String getResult_details() {
		return result_details;
	}
	public void setResult_details(String result_details) {
		this.result_details = result_details;
	}
	public String getUnfreezed_details() {
		return unfreezed_details;
	}
	public void setUnfreezed_details(String unfreezed_details) {
		this.unfreezed_details = unfreezed_details;
	}
	public String getRefundSerial() {
		return refundSerial;
	}
	public void setRefundSerial(String refundSerial) {
		this.refundSerial = refundSerial;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getRefundResult() {
		return refundResult;
	}
	public void setRefundResult(String refundResult) {
		this.refundResult = refundResult;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getDnHead() {
		return dnHead;
	}
	public void setDnHead(String dnHead) {
		this.dnHead = dnHead;
	}
	public String getDnTail() {
		return dnTail;
	}
	public void setDnTail(String dnTail) {
		this.dnTail = dnTail;
	}
	public String getOut_order_no() {
		return out_order_no;
	}
	public void setOut_order_no(String out_order_no) {
		this.out_order_no = out_order_no;
	}
}
