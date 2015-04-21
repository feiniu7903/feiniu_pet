package com.lvmama.comm.search.vo;

import java.io.Serializable;

public class ComSearchIntelligentType  implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long fromDestId;
	private String fromDestName;
	private Long toDestId;
	private String toDestName;
	private String searchType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFromDestId() {
		return fromDestId;
	}
	public void setFromDestId(Long fromDestId) {
		this.fromDestId = fromDestId;
	}

	public String getFromDestName() {
		return fromDestName;
	}
	public void setFromDestName(String fromDestName) {
		this.fromDestName = fromDestName;
	}
	public Long getToDestId() {
		return toDestId;
	}
	public void setToDestId(Long toDestId) {
		this.toDestId = toDestId;
	}
	public String getToDestName() {
		return toDestName;
	}
	public void setToDestName(String toDestName) {
		this.toDestName = toDestName;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	
	
}
