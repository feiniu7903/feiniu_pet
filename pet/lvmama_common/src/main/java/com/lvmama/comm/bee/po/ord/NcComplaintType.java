package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;

/**
 * 投诉类型信息表
 * 
 * @author zhushuying
 * 
 */
public class NcComplaintType implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long typeId;
	private String typeName; // 类型名称
	private String typeDescription; // 类型描述
	private int sort; // 排序

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeDescription() {
		return typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
