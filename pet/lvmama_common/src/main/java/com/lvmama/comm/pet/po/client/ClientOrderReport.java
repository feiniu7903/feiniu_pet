package com.lvmama.comm.pet.po.client;

import java.io.Serializable;
import java.util.Date;


public class ClientOrderReport implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7623722531018457026L;
	private Long id;

    private String udid;

    private Long orderId;

    private Date createdTime;

    private String channel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid == null ? null : udid.trim();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }
}