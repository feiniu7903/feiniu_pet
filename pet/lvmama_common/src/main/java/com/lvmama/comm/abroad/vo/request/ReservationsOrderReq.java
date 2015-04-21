package com.lvmama.comm.abroad.vo.request;

import java.io.Serializable;
import java.util.Date;
/**
 * 订单查询参数对象
 * @author ruanxiequan 2012-5-14
 *
 */
public class ReservationsOrderReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1288197564191274170L;
	/**订单号*/
	private String OrderNo;
	/**下单时间*/
	private Date ResMadeFrom;
	/**下单时间*/
	private Date ResMadeTo;
	/**用户id*/
	private String UserId;
	/**酒店名称*/
	private String HotelNameLike;
	/**入住时间*/
	private Date CheckInFrom;
	/**入住时间*/
	private Date CheckInTo;
	/**离店时间*/
	private Date CheckOutFrom;
	/**离店时间*/
	private Date CheckOutTo;
	/**支付状态*/
	private String PaymentStatus;
	/**审核状态*/
	private String ApproveStatus;
	/**订单状态*/
	private String OrderStatus;
	/**取消时间*/
	private Date CancelledFrom;
	/**取消时间*/
	private Date CancelledTo;
	/***/
	private String IncludeExtraInfo;
	/***/
	private String IncludeInvoiceInfo;
	/**应付金额*/
	private Long OughtPayMin;
	/**应付金额*/
	private Long OughtPayMax;
	/**实付金额*/
	private Long ActualPayMin;
	/**实付金额*/
	private Long ActualPayMax;
	/**废单人*/
	private String CancelOperator;
	/**支付时间*/
	private Date PaymentTimeFrom;
	/**支付时间*/
	private Date PaymentTimeTo;
	/**联系人*/
	private String ContactPersonName;
	/**联系电话*/
	private String ContactMobile;
	/**联系email*/
	private String ContactEmail;
	public String getOrderNo() {
		return OrderNo;
	}
	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}
	public Date getResMadeFrom() {
		return ResMadeFrom;
	}
	public void setResMadeFrom(Date resMadeFrom) {
		ResMadeFrom = resMadeFrom;
	}
	public Date getResMadeTo() {
		return ResMadeTo;
	}
	public void setResMadeTo(Date resMadeTo) {
		ResMadeTo = resMadeTo;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getHotelNameLike() {
		return HotelNameLike;
	}
	public void setHotelNameLike(String hotelNameLike) {
		HotelNameLike = hotelNameLike;
	}
	public Date getCheckInFrom() {
		return CheckInFrom;
	}
	public void setCheckInFrom(Date checkInFrom) {
		CheckInFrom = checkInFrom;
	}
	public Date getCheckInTo() {
		return CheckInTo;
	}
	public void setCheckInTo(Date checkInTo) {
		CheckInTo = checkInTo;
	}
	public Date getCheckOutFrom() {
		return CheckOutFrom;
	}
	public void setCheckOutFrom(Date checkOutFrom) {
		CheckOutFrom = checkOutFrom;
	}
	public Date getCheckOutTo() {
		return CheckOutTo;
	}
	public void setCheckOutTo(Date checkOutTo) {
		CheckOutTo = checkOutTo;
	}
	public String getPaymentStatus() {
		return PaymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		PaymentStatus = paymentStatus;
	}
	public String getApproveStatus() {
		return ApproveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		ApproveStatus = approveStatus;
	}
	public String getOrderStatus() {
		return OrderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		OrderStatus = orderStatus;
	}
	public Date getCancelledFrom() {
		return CancelledFrom;
	}
	public void setCancelledFrom(Date cancelledFrom) {
		CancelledFrom = cancelledFrom;
	}
	public Date getCancelledTo() {
		return CancelledTo;
	}
	public void setCancelledTo(Date cancelledTo) {
		CancelledTo = cancelledTo;
	}
	public String getIncludeExtraInfo() {
		return IncludeExtraInfo;
	}
	public void setIncludeExtraInfo(String includeExtraInfo) {
		IncludeExtraInfo = includeExtraInfo;
	}
	public String getIncludeInvoiceInfo() {
		return IncludeInvoiceInfo;
	}
	public void setIncludeInvoiceInfo(String includeInvoiceInfo) {
		IncludeInvoiceInfo = includeInvoiceInfo;
	}
	public Long getOughtPayMin() {
		return OughtPayMin;
	}
	public void setOughtPayMin(Long oughtPayMin) {
		OughtPayMin = oughtPayMin;
	}
	public Long getOughtPayMax() {
		return OughtPayMax;
	}
	public void setOughtPayMax(Long oughtPayMax) {
		OughtPayMax = oughtPayMax;
	}
	public Long getActualPayMin() {
		return ActualPayMin;
	}
	public void setActualPayMin(Long actualPayMin) {
		ActualPayMin = actualPayMin;
	}
	public Long getActualPayMax() {
		return ActualPayMax;
	}
	public void setActualPayMax(Long actualPayMax) {
		ActualPayMax = actualPayMax;
	}
	public String getCancelOperator() {
		return CancelOperator;
	}
	public void setCancelOperator(String cancelOperator) {
		CancelOperator = cancelOperator;
	}
	public Date getPaymentTimeFrom() {
		return PaymentTimeFrom;
	}
	public void setPaymentTimeFrom(Date paymentTimeFrom) {
		PaymentTimeFrom = paymentTimeFrom;
	}
	public Date getPaymentTimeTo() {
		return PaymentTimeTo;
	}
	public void setPaymentTimeTo(Date paymentTimeTo) {
		PaymentTimeTo = paymentTimeTo;
	}
	public String getContactPersonName() {
		return ContactPersonName;
	}
	public void setContactPersonName(String contactPersonName) {
		ContactPersonName = contactPersonName;
	}
	public String getContactMobile() {
		return ContactMobile;
	}
	public void setContactMobile(String contactMobile) {
		ContactMobile = contactMobile;
	}
	public String getContactEmail() {
		return ContactEmail;
	}
	public void setContactEmail(String contactEmail) {
		ContactEmail = contactEmail;
	}
}
