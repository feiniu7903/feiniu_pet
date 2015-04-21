package com.lvmama.comm.abroad.vo.response;

import java.io.Serializable;

public class AvailAccomAdvancedRoomInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7503682502865449373L;
	private String ID;
	private String IDRoomType;
	private String Description;
	private Long Price;
	private Long FinalPrice;
	private String NumAdults;
	private String NumChildren;
	private String Quantity;
	private String AvailStatus;
	private Long Discount;
	private Long BoardPrice;
	private AvailAccomAdvancedRoomBoardType RoomBoardType;
	/**
	 * 房间id
	 * @return
	 */
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	/**
	 * 房间类型
	 * @return
	 */
	public String getIDRoomType() {
		return IDRoomType;
	}
	public void setIDRoomType(String iDRoomType) {
		IDRoomType = iDRoomType;
	}
	/**
	 * 描述
	 * @return
	 */
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	/**
	 * 净价
	 * <p>Room price without supplements, discounts and optional boardtypes
	 * @return
	 */
	public Long getPrice() {
		return Price;
	}
	public void setPrice(Long price) {
		Price = price;
	}
	/**
	 * 成人数
	 * <p>Number of adults allowed per room
	 * @return
	 */
	public String getNumAdults() {
		return NumAdults;
	}
	public void setNumAdults(String numAdults) {
		NumAdults = numAdults;
	}
	/**
	 * 数量
	 * <p>inform you if the room is preselected by Transhotel as best option. (Always the
<p>cheapest option) to recognize form all the other rooms the value is > 0. Further if we request
<p>more than one room, summarize of the quantity values should be the same than the number of
<p>rooms selected
	 * @return
	 */
	public String getQuantity() {
		return Quantity;
	}
	public void setQuantity(String quantity) {
		Quantity = quantity;
	}
	/**
	 * 房间状态
	 * <p>inform you about the reservation status. ‘A’ Available ‘R’ on request.
	 * @return
	 */
	public String getAvailStatus() {
		return AvailStatus;
	}
	public void setAvailStatus(String availStatus) {
		AvailStatus = availStatus;
	}
	/**
	 * 折扣<p>
	 * Discount applied to the room
	 * @return
	 */
	public Long getDiscount() {
		return Discount;
	}
	public void setDiscount(Long discount) {
		Discount = discount;
	}
	/**
	 * 
	 * @return
	 */
	public Long getBoardPrice() {
		return BoardPrice;
	}
	public void setBoardPrice(Long boardPrice) {
		BoardPrice = boardPrice;
	}
	/**
	 * 儿童数
	 * @return
	 */
	public String getNumChildren() {
		return NumChildren;
	}
	public void setNumChildren(String numChildren) {
		NumChildren = numChildren;
	}
	/**
	 * 房间最终价格
	 * @return
	 */
	public Long getFinalPrice() {
		return FinalPrice;
	}
	public void setFinalPrice(Long finalPrice) {
		FinalPrice = finalPrice;
	}
	public AvailAccomAdvancedRoomBoardType getRoomBoardType() {
		return RoomBoardType;
	}
	public void setRoomBoardType(AvailAccomAdvancedRoomBoardType roomBoardType) {
		RoomBoardType = roomBoardType;
	}
}
