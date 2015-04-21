package com.lvmama.comm.abroad.vo.request;

import java.io.Serializable;

public class AvailAccomAdvancedRoom implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1040372397946260730L;
	/**房间数*/
	private int roomQuantity;
	/**成人数*/
	private int numAdults;
	/**儿童数*/
	private int numChildren;
	/**儿童年龄1*/
	private String firstChildAge;
	/**儿童年龄2*/
	private String secondChildAge;
	/**儿童年龄3*/
	private String thirdChildAge;
	public int getRoomQuantity() {
		return roomQuantity;
	}
	/**
	 * 房间数.必填
	 * @param roomQuantity
	 */
	public void setRoomQuantity(int roomQuantity) {
		this.roomQuantity = roomQuantity;
	}
	public int getNumAdults() {
		return numAdults;
	}
	/**
	 * 成人数
	 * @param numAdults
	 */
	public void setNumAdults(int numAdults) {
		this.numAdults = numAdults;
	}
	public int getNumChildren() {
		return numChildren;
	}
	/**
	 * 儿童数
	 * @param numChildren
	 */
	public void setNumChildren(int numChildren) {
		this.numChildren = numChildren;
	}
	public String getFirstChildAge() {
		return firstChildAge;
	}
	public void setFirstChildAge(String firstChildAge) {
		this.firstChildAge = firstChildAge;
	}
	public String getSecondChildAge() {
		return secondChildAge;
	}
	public void setSecondChildAge(String secondChildAge) {
		this.secondChildAge = secondChildAge;
	}
	public String getThirdChildAge() {
		return thirdChildAge;
	}
	public void setThirdChildAge(String thirdChildAge) {
		this.thirdChildAge = thirdChildAge;
	}
}

