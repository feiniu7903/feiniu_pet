package com.lvmama.passport.disney.model;

public class OrderRespose {
	private String responseCode;//状态码
	private String status;//订单状态//NOT_FOUND COMPLETED CANCEL_PENDING CANCELLED CANCEL_DECLINED
	private String reservationNo;//对方订单号
	private String voucherNo;//条形码原数据
	private String confirmationLetter;//二维码图片
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReservationNo() {
		return reservationNo;
	}
	public void setReservationNo(String reservationNo) {
		this.reservationNo = reservationNo;
	}
	public String getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	public String getConfirmationLetter() {
		return confirmationLetter;
	}
	public void setConfirmationLetter(String confirmationLetter) {
		this.confirmationLetter = confirmationLetter;
	}
}
