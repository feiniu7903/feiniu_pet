package com.lvmama.comm.abroad.vo.request;

import java.io.Serializable;

public class CancellationPolicyMergeRoom implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -879932250620373609L;
	/**房间id*/
	private String IDroom;
	/**数量*/
	private String Quantity;
	/**提供服务类型*/
	private String IDBoardType;
	public String getIDroom() {
		return IDroom;
	}
	public void setIDroom(String iDroom) {
		IDroom = iDroom;
	}
	public String getQuantity() {
		return Quantity;
	}
	public void setQuantity(String quantity) {
		Quantity = quantity;
	}
	public String getIDBoardType() {
		return IDBoardType;
	}
	public void setIDBoardType(String iDBoardType) {
		IDBoardType = iDBoardType;
	}
}
