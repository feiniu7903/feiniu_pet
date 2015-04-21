package com.lvmama.comm.pet.po.visa;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.StringUtil;

public class VisaApplicationDocumentDetails implements Serializable {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 8374259452204926889L;
	private Long detailsId;
	private Long documentId;
	private String title;
	private String content;
	private Long seq;
	public Long getDetailsId() {
		return detailsId;
	}
	public void setDetailsId(Long detailsId) {
		this.detailsId = detailsId;
	}
	public Long getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}
	public String getTitle() {
		return title;
	}

	public String getMasterTitle() {
		if (StringUtils.isNotBlank(title)) {
			String var = title.replaceAll("★", "");
			if (var.length() >= 10) {
				return var.substring(0, 10);
			} else {
				return var;
			}
		} else {
			return " ";
		}
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}	
}
