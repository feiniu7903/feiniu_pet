package com.lvmama.pet.refundment.data;

import java.util.Map;

import com.lvmama.pet.utils.AlipayUtil;

/**
 * 支付宝退款通知数据.
 * @author sunruyi
 */
public class AlipayRefundCallbackData implements RefundCallbackData {
	/**
	 * 通知时间.
	 */
	private String notifyTime;
	/**
	 * 通知类型.
	 */
	private String notifyType;
	/**
	 * 通知校验ID.
	 */
	private String notifyId;
	/**
	 * 签名方式.
	 */
	private String signType;
	/**
	 * 签名.
	 */
	private String sign;
	/**
	 * 退款批次.
	 */
	private String batchNo;
	/**
	 * 退款成功总数.
	 */
	private String successNum;
	/**
	 * 处理结果详情.
	 */
	private String resultDetails;
	/**
	 * 解冻结果明细.
	 */
	private String unfreezedDetails;
	
	private Map<String,String> responseMap;
	private String refundSerial;
	private String refundAmount;
	private String code;

	public AlipayRefundCallbackData(){}
	
	public AlipayRefundCallbackData(Map<String,String> params){
		this.setNotifyTime(params.get("notify_time"));
		this.setNotifyType(params.get("notify_type"));
		this.setNotifyId(params.get("notify_id"));
		this.setSignType(params.get("sign_type"));
		this.setSign(params.get("sign"));
		this.setBatchNo(params.get("batch_no"));
		this.setSuccessNum(params.get("success_num"));
		this.setResultDetails(params.get("result_details"));
		this.setUnfreezedDetails(params.get("unfreezed_details"));
		this.setResponseMap(params);
		this.analysisResultDetails();
	}
	
	@Override
	public boolean isSuccess() {
		return code != null && "SUCCESS".equals(code) && checkSignature();
	}

	@Override
	public boolean checkSignature() {
		return AlipayUtil.verify(responseMap);
	}

	@Override
	public String getSerial() {
		return batchNo;
	}

	@Override
	public String getCallbackInfo() {
		return code;
	}

	/**
	 * 解析处理结果详情.
	 */
	private void analysisResultDetails(){
		String[] details = resultDetails.split("\\$");
		String payRefundResult = details[0];
		String[] payRefundResultDetail = payRefundResult.split("\\^");
		refundSerial = payRefundResultDetail[0];
		refundAmount = payRefundResultDetail[1];
		code = payRefundResultDetail[2];
	}
	public String getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(String notifyTime) {
		this.notifyTime = notifyTime;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(String successNum) {
		this.successNum = successNum;
	}

	public String getResultDetails() {
		return resultDetails;
	}

	public void setResultDetails(String resultDetails) {
		this.resultDetails = resultDetails;
	}

	public String getUnfreezedDetails() {
		return unfreezedDetails;
	}

	public void setUnfreezedDetails(String unfreezedDetails) {
		this.unfreezedDetails = unfreezedDetails;
	}

	public Map<String, String> getResponseMap() {
		return responseMap;
	}

	public void setResponseMap(Map<String, String> responseMap) {
		this.responseMap = responseMap;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
