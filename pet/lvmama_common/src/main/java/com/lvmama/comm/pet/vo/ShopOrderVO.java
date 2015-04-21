package com.lvmama.comm.pet.vo;

import com.lvmama.comm.pet.po.shop.ShopOrder;

/**
 * 积分订单的VO
 * @author Brian
 *
 */
public class ShopOrderVO extends ShopOrder{	
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 936853026594743241L;
	/**
	 * 用户名
	 */
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}	
    
}