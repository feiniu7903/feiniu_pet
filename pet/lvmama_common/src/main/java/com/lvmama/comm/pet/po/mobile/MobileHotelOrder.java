package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;
import java.util.Date;

public class MobileHotelOrder implements Serializable {
	private static final long serialVersionUID = -7004550900467875046L;

	private Long lvHotelOrderId;

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

    private String queryRelated;

    private String isGruarantee;

    private String isChanged;

    private Long parentOrderId;

    private String isRefund;

    private Long refundAmount;

    private String channel;

    public Long getLvHotelOrderId() {
        return lvHotelOrderId;
    }

    public void setLvHotelOrderId(Long lvHotelOrderId) {
        this.lvHotelOrderId = lvHotelOrderId;
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

    public String getQueryRelated() {
        return queryRelated;
    }

    public void setQueryRelated(String queryRelated) {
        this.queryRelated = queryRelated == null ? null : queryRelated.trim();
    }

    public String getIsGruarantee() {
        return isGruarantee;
    }

    public void setIsGruarantee(String isGruarantee) {
        this.isGruarantee = isGruarantee == null ? null : isGruarantee.trim();
    }

    public String getIsChanged() {
        return isChanged;
    }

    public void setIsChanged(String isChanged) {
        this.isChanged = isChanged == null ? null : isChanged.trim();
    }

    public Long getParentOrderId() {
        return parentOrderId;
    }

    public void setParentOrderId(Long parentOrderId) {
        this.parentOrderId = parentOrderId;
    }

    public String getIsRefund() {
        return isRefund;
    }

    public void setIsRefund(String isRefund) {
        this.isRefund = isRefund == null ? null : isRefund.trim();
    }

    public Long getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Long refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }
}