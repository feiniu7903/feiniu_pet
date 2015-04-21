package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;

public class OrdOrderTrafficRefund implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1880404923261377641L;

	private Long orderTrafficRefundId;
    private Long orderTrafficId;
    /**
     * 退款金额
     */
    private Long amount;
    /**
     * 流水号
     */
    private String billNo;
    /**
     * 生成时间
     */
    private Date createTime;
    /**
     * 退款手续费 
     */
    private Long ticketCharge;
    /**
     * 退款类型
     */
    private Integer refundType;
    /**
     * 需退票或者退款车票张数
     */
    private Integer ticketNum;

	public Long getOrderTrafficRefundId() {
        return orderTrafficRefundId;
    }
    public void setOrderTrafficRefundId(Long orderTrafficRefundId) {
        this.orderTrafficRefundId = orderTrafficRefundId;
    }
    public Long getOrderTrafficId() {
        return orderTrafficId;
    }
    public void setOrderTrafficId(Long orderTrafficId) {
        this.orderTrafficId = orderTrafficId;
    }
    public Long getAmount() {
        return amount;
    }
    public void setAmount(Long amount) {
        this.amount = amount;
    }
    public String getBillNo() {
        return billNo;
    }
    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Long getTicketCharge() {
		return ticketCharge;
	}
	public void setTicketCharge(Long ticketCharge) {
		this.ticketCharge = ticketCharge;
	}
	public Integer getRefundType() {
		return refundType;
	}
	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}
	public Integer getTicketNum() {
		return ticketNum;
	}
	public void setTicketNum(Integer ticketNum) {
		this.ticketNum = ticketNum;
	}
}