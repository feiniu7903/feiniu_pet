package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MobileOrderRelationSamsung implements Serializable {
	private static final long serialVersionUID = -2331772978048990483L;

	private Long id;

    private String ticketid;

    private String serial;

    private long orderid;

    private Date createDate;

    private Date cancalDate;

    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketid() {
        return ticketid;
    }

    public void setTicketid(String ticketid) {
        this.ticketid = ticketid == null ? null : ticketid.trim();
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial == null ? null : serial.trim();
    }

    public long getOrderid() {
        return orderid;
    }

    public void setOrderid(long orderid) {
        this.orderid = orderid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCancalDate() {
        return cancalDate;
    }

    public void setCancalDate(Date cancalDate) {
        this.cancalDate = cancalDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}