package com.lvmama.comm.pet.po.user;

import java.io.Serializable;
import java.util.Date;

public class Annwinnerslist implements Serializable{

	private static final long serialVersionUID = 8290356086399689311L;
	
	private Long id;
	private String projectName; // 所属项目
	private Long lpId; // 礼品id
	private String lpName; // 礼品名称
	private String  lpDescription; // 礼品描述 
	private Date huojiangTime; // 获奖时间
	private Long userId; // 用户id
	private String userName; // 用户名称
	private String realName; // 真实姓名
	private Long mobile; // 手机号
	private Long zipcode; // 邮编
	private String address; // 地址
	private Date createDate; // 创建时间
	private Long flag; // (0-获奖 ，1-未获奖)
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * @return the lpId
	 */
	public Long getLpId() {
		return lpId;
	}
	/**
	 * @param lpId the lpId to set
	 */
	public void setLpId(Long lpId) {
		this.lpId = lpId;
	}
	/**
	 * @return the lpName
	 */
	public String getLpName() {
		return lpName;
	}
	/**
	 * @param lpName the lpName to set
	 */
	public void setLpName(String lpName) {
		this.lpName = lpName;
	}
	/**
	 * @return the lpDescription
	 */
	public String getLpDescription() {
		return lpDescription;
	}
	/**
	 * @param lpDescription the lpDescription to set
	 */
	public void setLpDescription(String lpDescription) {
		this.lpDescription = lpDescription;
	}
	/**
	 * @return the huojiangTime
	 */
	public Date getHuojiangTime() {
		return huojiangTime;
	}
	/**
	 * @param huojiangTime the huojiangTime to set
	 */
	public void setHuojiangTime(Date huojiangTime) {
		this.huojiangTime = huojiangTime;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}
	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}
	/**
	 * @return the mobile
	 */
	public Long getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the zipcode
	 */
	public Long getZipcode() {
		return zipcode;
	}
	/**
	 * @param zipcode the zipcode to set
	 */
	public void setZipcode(Long zipcode) {
		this.zipcode = zipcode;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the flag
	 */
	public Long getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(Long flag) {
		this.flag = flag;
	}
 
 
}