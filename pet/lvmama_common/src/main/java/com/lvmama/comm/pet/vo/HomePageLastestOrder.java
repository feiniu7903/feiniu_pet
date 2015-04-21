package com.lvmama.comm.pet.vo;

import java.util.Date;
import java.util.Random;

import com.lvmama.comm.utils.DateUtil;

public class HomePageLastestOrder implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5335299276800717061L;
	private String userName;
	private Date createDate;
	private String productName;
	private Long productId;
	
	public HomePageLastestOrder() {
		
	}
	
	public HomePageLastestOrder(String userName, Date createDate, String productName, Long productId) {
		this.userName = userName;
		this.createDate = createDate;
		this.productName = productName;
		this.productId = productId;
	}
	/**
	 * 显示当前时间和创建时间只差
	 * 如果大于30，就用30以内的任意个随机数取代
	 */
	public long getMinuteToCurrent() {
		if(createDate != null){
			return DateUtil.getMinBetween(createDate, new Date()) <= 30 ? DateUtil
					.getMinBetween(createDate, new Date()) : new Random().nextInt(30);
		}else{
			return 1l;
		}
	}
	
	public Boolean getAllNotEmpty() {
		if (this.createDate != null && this.productId != null
				&& this.productName != null && this.userName != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("用户<span>" + getUserName() + "</span>");
		sb.append((getMinuteToCurrent() > 30 ? 30 : (getMinuteToCurrent() < 1 ? 1 : getMinuteToCurrent())) +  "分钟前预订了");
		sb.append("<a rel='nofollow' target='_blank' href='http://www.lvmama.com/product/"+ getProductId() + "'>" + getProductName() + "</a>");
		return sb.toString();
	}
	
	public String getUserName() {
		if(this.userName.length()>4){
			userName=userName.substring(0,4)+"***";
		}else{
			userName=userName+"***";
		}
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCreateDate() {
		return createDate;
	}	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getProductName() {
		if(this.productName.length()>16){
			productName=productName.substring(0, 16)+"***";
		}
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
}
