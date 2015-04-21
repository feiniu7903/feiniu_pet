package com.lvmama.comm.search.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 搜索转码表
 * 
 * @author yanggan
 * 
 */
public class ComSearchTranscode implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codeId;
	private String keyword;
	private String keywordSource;
	private Date createTime;

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeywordSource() {
		return keywordSource;
	}

	public void setKeywordSource(String keywordSource) {
		this.keywordSource = keywordSource;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
