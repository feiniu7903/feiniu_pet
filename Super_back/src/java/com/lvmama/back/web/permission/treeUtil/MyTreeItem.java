package com.lvmama.back.web.permission.treeUtil;

/**
 * @author shihui
 * 
 *         节点对象
 * */
public class MyTreeItem {

	private Long id;

	private String name;

	private Long parentId;

	public MyTreeItem(Long id, String name, Long parentId) {
		this.id = id;
		this.name = name;
		this.parentId = parentId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
