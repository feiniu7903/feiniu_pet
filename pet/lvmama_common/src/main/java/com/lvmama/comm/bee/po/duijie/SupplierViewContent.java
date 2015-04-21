package com.lvmama.comm.bee.po.duijie;

import java.io.Serializable;

/**
 * 供应商产品介绍 
 * @author yanzhirong
 */
public class SupplierViewContent implements Serializable{

	private static final long serialVersionUID = 7234718523721480225L;
	
	/** 主键id*/
	private Long contentId;
	
	/** 供应商产品外键id*/
	private Long productId;
	
	/** 内容类型*/
	private String contentType;
	
	/** 内容*/
	private String content;

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
