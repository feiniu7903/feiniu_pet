package com.lvmama.comm.pet.po.user;

import java.io.Serializable;
import java.util.Date;

public class Annliping implements Serializable{

	private static final long serialVersionUID = 8290356086399689311L;
	private Long lpId;
	private String projectName;
	private String lpName; // 礼品名
	private String lpType; // 类型 0-优惠券 1-实物 2-电子券
	private Long lpCount; // 礼品数量
	private Long lpSpare; // 剩余数量
	private Long lpLimit; 
	private Long lpChance; // 中奖概率
	private Date beginTime; 
	private Date endTime;
	private Date createTime ;
	private Date UPDATE_TIME;
	private Long lpDengjiang; // 几等奖
	private String ztname; // 所属专题
	/**
	 * @return the lpDengjiang
	 */
	public Long getLpDengjiang() {
		return lpDengjiang;
	}
	/**
	 * @param lpDengjiang the lpDengjiang to set
	 */
	public void setLpDengjiang(Long lpDengjiang) {
		this.lpDengjiang = lpDengjiang;
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
	 * @return the lpType
	 */
	public String getLpType() {
		return lpType;
	}
	/**
	 * @param lpType the lpType to set
	 */
	public void setLpType(String lpType) {
		this.lpType = lpType;
	}
	/**
	 * @return the lpCount
	 */
	public Long getLpCount() {
		return lpCount;
	}
	/**
	 * @param lpCount the lpCount to set
	 */
	public void setLpCount(Long lpCount) {
		this.lpCount = lpCount;
	}
	/**
	 * @return the lpSpare
	 */
	public Long getLpSpare() {
		return lpSpare;
	}
	/**
	 * @param lpSpare the lpSpare to set
	 */
	public void setLpSpare(Long lpSpare) {
		this.lpSpare = lpSpare;
	}
	/**
	 * @return the lpLimit
	 */
	public Long getLpLimit() {
		return lpLimit;
	}
	/**
	 * @param lpLimit the lpLimit to set
	 */
	public void setLpLimit(Long lpLimit) {
		this.lpLimit = lpLimit;
	}
	/**
	 * @return the lpChance
	 */
	public Long getLpChance() {
		return lpChance;
	}
	/**
	 * @param lpChance the lpChance to set
	 */
	public void setLpChance(Long lpChance) {
		this.lpChance = lpChance;
	}
	/**
	 * @return the beginTime
	 */
	public Date getBeginTime() {
		return beginTime;
	}
	/**
	 * @param beginTime the beginTime to set
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the uPDATE_TIME
	 */
	public Date getUPDATE_TIME() {
		return UPDATE_TIME;
	}
	/**
	 * @param uPDATE_TIME the uPDATE_TIME to set
	 */
	public void setUPDATE_TIME(Date uPDATE_TIME) {
		UPDATE_TIME = uPDATE_TIME;
	}
	public String getZtname() {
		return ztname;
	}
	public void setZtname(String ztname) {
		this.ztname = ztname;
	}
 }