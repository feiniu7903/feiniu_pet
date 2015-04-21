package com.lvmama.comm.pet.vo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 积分订单用户填写的信息VO
 * @author Brian
 *
 */
public class ShopUserInfo implements Serializable{
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -7963277109303611903L;
	private String name;
	private String address;
	private String mobile;
	private String zip;
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("name",name)
				.append("address",address).append("mobile",mobile).append("zip",zip).toString();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		System.out.println("setName");
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}	
}
