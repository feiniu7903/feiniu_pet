package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;

public class OrdOrderChannel implements Serializable, Comparable<OrdOrderChannel>{
	private static final long serialVersionUID = 3241736704217412746L;
	
	protected Long orderId;
	protected String channel;
	protected String arg1;
	protected String arg2;
	protected Date createDate;
	
	protected OrdOrderChannel() {}
	
	public OrdOrderChannel(final Long orderId, final String channel) {
		this.orderId = orderId;
		this.channel = channel;
	}
	
	public OrdOrderChannel(final Long orderId, final String channel, final String arg1, final String arg2) {
		this(orderId, channel);
		this.arg1 = arg1;
		this.arg2 = arg2;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getArg1() {
		return arg1;
	}

	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}

	public String getArg2() {
		return arg2;
	}

	public void setArg2(String arg2) {
		this.arg2 = arg2;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public int compareTo(OrdOrderChannel o) {
		return this.orderId.compareTo(o.getOrderId());
	}



}
