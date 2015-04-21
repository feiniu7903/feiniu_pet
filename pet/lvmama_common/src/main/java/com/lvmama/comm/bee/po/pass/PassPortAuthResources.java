package com.lvmama.comm.bee.po.pass;

import java.io.Serializable;


public class PassPortAuthResources implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2929689370929515531L;
	/**
	 * 编号.
	 */
	private Long resourceId;
	/**
	 * 资源名.
	 */
	private String resourceName;
	/**
	 * 文件名.
	 */
	private String fileName;
	/**
	 * 图标路径.
	 */
	private String image;
	/**
	 * 分类.
	 */
	private String category;
	/**
	 * 描述.
	 */
	private String description;
	/**
	 * 排序.
	 */
	private String seq;
	/**
	 * 父资源ID.
	 */
	private Long parentId;
	/**
	 * 是否被选中.
	 */
	private boolean checked=false;
	
	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}