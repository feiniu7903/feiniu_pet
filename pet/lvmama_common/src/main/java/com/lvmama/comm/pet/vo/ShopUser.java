package com.lvmama.comm.pet.vo;

import java.io.Serializable;

/**
 * 积分商城的用户
 * @author Brian
 *
 */
public class ShopUser implements Serializable {
	/**
	 * 序列化值
	 */
	private static final long serialVersionUID = -6441843469805334280L;
	/**
	 * 用户标识
	 */
	private String userId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 积分
	 */
	private Long point;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getPoint() {
		return point;
	}
	public void setPoint(Long point) {
		this.point = point;
	}
		
}

