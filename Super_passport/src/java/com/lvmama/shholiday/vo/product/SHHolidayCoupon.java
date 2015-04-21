package com.lvmama.shholiday.vo.product;

import java.util.Date;

import com.lvmama.comm.pet.po.businessCoupon.BusinessCoupon;

/**
 * 上航coupon合并
 */
public class SHHolidayCoupon extends BusinessCoupon{

	private Date dateStart;
	private Date dateEnd;
	private String key;
	private String fitInfant;
	private String fitChildren;
	private String fitAdult;

	public SHHolidayCoupon() {}
	public SHHolidayCoupon(Date buyDateStart,Date buyDateEnd,Date playDateStart,Date playDateEnd,String key,Long argumentY,Long argumentZ) {
		this.setBeginTime(buyDateStart);
		this.setEndTime( buyDateEnd);
		this.setPlayEndTime( playDateEnd);
		this.setPlayBeginTime(playDateStart);
		this.key = key;
		this.setArgumentY(argumentY);
		this.setArgumentZ(argumentZ);
	}
	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	public String getFitInfant() {
		return fitInfant;
	}
	public void setFitInfant(String fitInfant) {
		this.fitInfant = fitInfant;
	}
	public String getFitChildren() {
		return fitChildren;
	}
	public void setFitChildren(String fitChildren) {
		this.fitChildren = fitChildren;
	}
	public String getFitAdult() {
		return fitAdult;
	}
	public void setFitAdult(String fitAdult) {
		this.fitAdult = fitAdult;
	}

}
