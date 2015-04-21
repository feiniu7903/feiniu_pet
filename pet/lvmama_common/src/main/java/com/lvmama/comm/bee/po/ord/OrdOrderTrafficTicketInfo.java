package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class OrdOrderTrafficTicketInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3301085360975552341L;

	private Long trafficTicketInfoId;

    private Long orderTrafficId;

    /**
     * 车票类型，例如301（成人票）
     */
    private String ticketCategory;

    /**
     * 车票坐席，例如204（二等座）
     */
    private String seat;

    /**
     * 车票价格
     */
    private Long price;

    /**
     * 座位号，例如10车厢12A
     */
    private String seatNo;

    private Long ordPersonId;
    private OrdPerson person;
    
    /**
     * 此值只做传值使用
     */
    private String name;
    /**
     * 此值只做传值使用
     */
    private String identity;
    /**
     * 此值只做传值使用
     */
    private String identityNo;
    
    /**
     * 车票编号
     */
    private String ticketId;
    /**
     * 车票状态
     */
    private String ticketStatus;
    /**
     * 退票流水号
     */
    private String billNo;

	public Long getTrafficTicketInfoId() {
        return trafficTicketInfoId;
    }
    public void setTrafficTicketInfoId(Long trafficTicketInfoId) {
        this.trafficTicketInfoId = trafficTicketInfoId;
    }
    public Long getOrderTrafficId() {
        return orderTrafficId;
    }
    public void setOrderTrafficId(Long orderTrafficId) {
        this.orderTrafficId = orderTrafficId;
    }
    public String getTicketCategory() {
        return ticketCategory;
    }
    public void setTicketCategory(String ticketCategory) {
        this.ticketCategory = ticketCategory == null ? null : ticketCategory.trim();
    }
    public String getZhTicketCategory(){
    	return Constant.TRAIN_TICKET_TYPE.getCnName(ticketCategory);
    }
    public String getSeat() {
        return seat;
    }
    
    public String getZhSeat(){
    	return Constant.TRAIN_SEAT_CATALOG.getAttr1(seat);
    }
    
    public void setSeat(String seat) {
        this.seat = seat == null ? null : seat.trim();
    }
    public Long getPrice() {
        return price;
    }
    public void setPrice(Long price) {
        this.price = price;
    }
    public String getSeatNo() {
        return seatNo;
    }
    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo == null ? null : seatNo.trim();
    }
    public Long getOrdPersonId() {
        return ordPersonId;
    }
    public void setOrdPersonId(Long ordPersonId) {
        this.ordPersonId = ordPersonId;
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getIdentityNo() {
		return identityNo;
	}
	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}
	public OrdPerson getPerson() {
		return person;
	}
	public void setPerson(OrdPerson person) {
		this.person = person;
	}
    public float getPriceYuan(){
    	return PriceUtil.convertToYuan(price);
    }
    public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getTicketStatus() {
		return ticketStatus;
	}
	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}
	public String getZhTicketStatus(){
		return ticketStatus+ ":" +Constant.TRAIN_ORDER_STATUS.getCnName(ticketStatus);
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
}