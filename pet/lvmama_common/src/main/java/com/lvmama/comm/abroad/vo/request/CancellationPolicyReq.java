package com.lvmama.comm.abroad.vo.request;

import java.io.Serializable;

public class CancellationPolicyReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3359025858602919419L;
	private String IDroom;
	public String getIDroom() {
		return IDroom;
	}
	/**
	 * 设置IDRoom，必填
	 * @param iDroom
	 */
	public void setIDroom(String iDroom) {
		IDroom = iDroom;
	}
}
