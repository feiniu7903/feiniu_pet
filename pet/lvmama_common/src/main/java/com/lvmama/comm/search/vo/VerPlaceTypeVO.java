package com.lvmama.comm.search.vo;

import java.io.Serializable;

import com.lvmama.comm.search.SearchConstants.SEARCH_TYPE;
import com.lvmama.comm.search.annotation.FilterParam;

public class VerPlaceTypeVO extends SearchVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String dictId;

	private Long dictDefId;

	private String dictName;

	private String cancelFlag;

	// 是否可补充
	private String addFlag;

	private String isHasTree;

	public String getIsHasTree() {
		return isHasTree;
	}

	public void setIsHasTree(String isHasTree) {
		this.isHasTree = isHasTree;
	}

	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public Long getDictDefId() {
		return dictDefId;
	}

	public void setDictDefId(Long dictDefId) {
		this.dictDefId = dictDefId;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getCancelFlag() {
		return cancelFlag;
	}

	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public String getAddFlag() {
		return addFlag;
	}

	public void setAddFlag(String addFlag) {
		this.addFlag = addFlag;
	}

}
