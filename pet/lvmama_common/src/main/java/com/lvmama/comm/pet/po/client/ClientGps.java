package com.lvmama.comm.pet.po.client;

import java.io.Serializable;

public class ClientGps implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4852397102993840474L;
	private String xPoint;
	private String yPoint;
	
	public String getxPoint() {
		return xPoint;
	}
	public void setxPoint(String xPoint) {
		this.xPoint = xPoint;
	}
	public String getyPoint() {
		return yPoint;
	}
	public void setyPoint(String yPoint) {
		this.yPoint = yPoint;
	}
	
}
