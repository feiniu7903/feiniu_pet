package com.lvmama.passport.disney.model;

public class MailBean {
	private String guestName;//游客名称
	private String productName;//产品名称
	private String num;//门票数量
	private String voucherNo;
	private byte[] qrCode;//二维码
	private String barCode;//条形码
	private String reservationNo;//预留号码
	private String productRemark;//产品备注
	private String qrCodeUrl;//映射的图片服务地址
	private String barCodeUrl;//映射的图片服务地址
	private String date;
	private String toAddress;//游客邮箱
	private String branchInfo;
	
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	public byte[] getQrCode() {
		return qrCode;
	}
	public void setQrCode(byte[] qrCode) {
		this.qrCode = qrCode;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
	public String getReservationNo() {
		return reservationNo;
	}
	public void setReservationNo(String reservationNo) {
		this.reservationNo = reservationNo;
	}
	public String getProductRemark() {
		return productRemark;
	}
	public void setProductRemark(String productRemark) {
		this.productRemark = productRemark;
	}
	public String getQrCodeUrl() {
		return qrCodeUrl;
	}
	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}
	public String getBarCodeUrl() {
		return barCodeUrl;
	}
	public void setBarCodeUrl(String barCodeUrl) {
		this.barCodeUrl = barCodeUrl;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getToAddress() {
		return toAddress;
	}
	public String getBranchInfo() {
		return branchInfo;
	}
	public void setBranchInfo(String branchInfo) {
		this.branchInfo = branchInfo;
	}
}
