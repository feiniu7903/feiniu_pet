package com.lvmama.comm.abroad.vo.request;

import java.io.Serializable;


public class RecalcRoomPriceReqRoom implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4537847403785433905L;
	private String Quantity;
	private String IDBoardTypeAdults;
	private String IDroom;
	public String getQuantity() {
		return Quantity;
	}
	public void setQuantity(String quantity) {
		Quantity = quantity;
	}
	public String getIDBoardTypeAdults() {
		return IDBoardTypeAdults;
	}
	public void setIDBoardTypeAdults(String iDBoardTypeAdults) {
		IDBoardTypeAdults = iDBoardTypeAdults;
	}
	public String getIDroom() {
		return IDroom;
	}
	public void setIDroom(String iDroom) {
		IDroom = iDroom;
	}
}
