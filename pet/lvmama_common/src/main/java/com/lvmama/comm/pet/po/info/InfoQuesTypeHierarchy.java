/**
 * 
 */
package com.lvmama.comm.pet.po.info;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyi
 *
 */
public class InfoQuesTypeHierarchy extends InfoQuesType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1849150415687595358L;

	private Long parentTypeId = Long.valueOf(-1); // default is -1, no parent

	private String parentTypeName = "";

	private List<InfoQuesTypeHierarchy> childInfoQuesTypeHierarchyList = new ArrayList<InfoQuesTypeHierarchy>();

	public Long getParentTypeId() {
		return parentTypeId;
	}

	public void setParentTypeId(Long parentTypeId) {
		this.parentTypeId = parentTypeId;
	}

	public String getParentTypeName() {
		return parentTypeName;
	}

	public void setParentTypeName(String parentTypeName) {
		this.parentTypeName = parentTypeName;
	}

	public List<InfoQuesTypeHierarchy> getChildInfoQuesTypeHierarchyList() {
		return childInfoQuesTypeHierarchyList;
	}

	public void setChildInfoQuesTypeHierarchyList(
			List<InfoQuesTypeHierarchy> childInfoQuesTypeHierarchyList) {
		this.childInfoQuesTypeHierarchyList = childInfoQuesTypeHierarchyList;
	}

	
}
