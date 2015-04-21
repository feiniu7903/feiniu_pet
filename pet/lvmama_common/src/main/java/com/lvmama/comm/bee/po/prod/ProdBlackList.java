package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;
import java.util.Date;

/**
 * 产品黑名单
 * 
 * @author zenglei
 * 
 * 2014-4-29 14:49:34
 */
public class ProdBlackList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Long blackId;
	/**
	 * 是否指定手机号码屏蔽
	 * 0:单手机号码     1：单产品      2:单用户产品集     3：单IMEI产品集 4：单手机产品集
	 */
	private String blackIsPhone;
	/**
	 * 指定的手机号码、或着产品ID集合
	 */
	private String blackPhoneNum; 
	/**
	 * 0:天	 1：自然周 	 	2：自然月  	 3：自然年
	 */
	private String blackCirculation;
	/**
	 * 开始时间  
	 */
	private Date blackStartTime;
	/**
	 * 结束时间
	 */
	private Date blackEndTime;
	/**
	 * 限制笔数
	 */
	private Long blackLimit;
	/**
	 * 限制原因
	 */
	private String blackReason;
	/**
	 * 对应产品ID
	 */
	private Long productId;
	/**
	 * 创建时间
	 */
	private Date blackCreateTime;
	public Long getBlackId() {
		return blackId;
	}
	public void setBlackId(Long blackId) {
		this.blackId = blackId;
	}
	public String getBlackIsPhone() {
		return blackIsPhone;
	}
	public void setBlackIsPhone(String blackIsPhone) {
		this.blackIsPhone = blackIsPhone;
	}
	public String getBlackPhoneNum() {
		return blackPhoneNum;
	}
	public void setBlackPhoneNum(String blackPhoneNum) {
		this.blackPhoneNum = blackPhoneNum;
	}
	public String getBlackCirculation() {
		return blackCirculation;
	}
	public void setBlackCirculation(String blackCirculation) {
		this.blackCirculation = blackCirculation;
	}
	public Date getBlackStartTime() {
		return blackStartTime;
	}
	public void setBlackStartTime(Date blackStartTime) {
		this.blackStartTime = blackStartTime;
	}
	public Date getBlackEndTime() {
		return blackEndTime;
	}
	public void setBlackEndTime(Date blackEndTime) {
		this.blackEndTime = blackEndTime;
	}
	public Long getBlackLimit() {
		return blackLimit;
	}
	public void setBlackLimit(Long blackLimit) {
		this.blackLimit = blackLimit;
	}
	public String getBlackReason() {
		return blackReason;
	}
	public void setBlackReason(String blackReason) {
		this.blackReason = blackReason;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Date getBlackCreateTime() {
		return blackCreateTime;
	}
	public void setBlackCreateTime(Date blackCreateTime) {
		this.blackCreateTime = blackCreateTime;
	}
}
