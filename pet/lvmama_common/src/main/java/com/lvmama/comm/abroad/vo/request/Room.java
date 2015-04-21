package com.lvmama.comm.abroad.vo.request;

import java.io.Serializable;

public class Room implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String IDroom;//房间ID
	private String quantity;//每晚订的房间数
	public String getIDroom() {
		return IDroom;
	}
	public void setIDroom(String iDroom) {
		IDroom = iDroom;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	
}
