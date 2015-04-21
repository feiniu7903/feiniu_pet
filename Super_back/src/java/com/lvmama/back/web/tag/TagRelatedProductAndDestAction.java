package com.lvmama.back.web.tag;

import com.lvmama.back.web.BaseAction;

/**
 * 标签管理 主要作用是拿到上一级传来的标签ID,向一级进行传递
 * 
 * @author lipengcheng
 * 
 */
@SuppressWarnings("serial")
public class TagRelatedProductAndDestAction extends BaseAction {
	
	private Long attrTagId;
	/**
	 * 页面初始化时加载
	 */
	
	
	public Long getAttrTagId() {
		return attrTagId;
	}

	public void setAttrTagId(Long attrTagId) {
		this.attrTagId = attrTagId;
	}
	

}
