package com.lvmama.comm.pet.po.prod;

import java.io.Serializable;

import com.lvmama.comm.utils.ReplaceEnter;

public class ProdContent implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3035041322598878653L;

	private Long contentId;

	private String content;

	private Long seq;

	private Long pageId;

	private String contentType;

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public String getContent() {
		return this.content;
	}

	public String getContentRn() {
		return ReplaceEnter.replaceEnterRn(content,this.contentType);
	}
	
	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}