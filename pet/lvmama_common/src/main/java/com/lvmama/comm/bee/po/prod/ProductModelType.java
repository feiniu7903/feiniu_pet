package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;
import java.util.Date;

/**
 * 模块类型表
 * @author ganyingwen
 *
 */
public class ProductModelType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2251533916793789596L;
	private Long id;
	/**
	 * 模块名称
	 */
	private String modelName;
	/**
	 * 父模块类型ID
	 */
	private Long parentId;
	/**
	 * 是否可维护
	 */
	private String isMaintain;
	/**
	 * 是否可多选
	 */
	private String isMultiChoice;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 顺序
	 */
	private int seq;
	private Date updateDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getIsMaintain() {
		return isMaintain;
	}
	public void setIsMaintain(String isMaintain) {
		this.isMaintain = isMaintain;
	}
	public String getIsMultiChoice() {
		return isMultiChoice;
	}
	public void setIsMultiChoice(String isMultiChoice) {
		this.isMultiChoice = isMultiChoice;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
}
