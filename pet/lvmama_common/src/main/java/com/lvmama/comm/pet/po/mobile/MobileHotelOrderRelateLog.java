package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;
import java.util.Date;

public class MobileHotelOrderRelateLog implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long mobileHotelOrderRelateId;

    private Long orderId;

    private String userId;

    private Date cancelTime;

    private Long guaranteeAmount;

    private String currencyCode;

    private String isValid;

    private Date createTime;

    private String message;

    private String status;

    private Date arrivaldate;

    private Date departuredate;

    private Long totalPrice;

    private Long quantity;

    private String roomId;

    private String hotelId;

    private String isGruarantee;

    private Long lvHotelOrderId;

    public Long getMobileHotelOrderRelateId() {
        return mobileHotelOrderRelateId;
    }

    public void setMobileHotelOrderRelateId(Long mobileHotelOrderRelateLog) {
        this.mobileHotelOrderRelateId = mobileHotelOrderRelateLog;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Long getGuaranteeAmount() {
        return guaranteeAmount;
    }

    public void setGuaranteeAmount(Long guaranteeAmount) {
        this.guaranteeAmount = guaranteeAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid == null ? null : isValid.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getArrivaldate() {
        return arrivaldate;
    }

    public void setArrivaldate(Date arrivaldate) {
        this.arrivaldate = arrivaldate;
    }

    public Date getDeparturedate() {
        return departuredate;
    }

    public void setDeparturedate(Date departuredate) {
        this.departuredate = departuredate;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId == null ? null : roomId.trim();
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId == null ? null : hotelId.trim();
    }

    public String getIsGruarantee() {
        return isGruarantee;
    }

    public void setIsGruarantee(String isGruarantee) {
        this.isGruarantee = isGruarantee == null ? null : isGruarantee.trim();
    }

    public Long getLvHotelOrderId() {
        return lvHotelOrderId;
    }

    public void setLvHotelOrderId(Long lvHotelOrderId) {
        this.lvHotelOrderId = lvHotelOrderId;
    }
}