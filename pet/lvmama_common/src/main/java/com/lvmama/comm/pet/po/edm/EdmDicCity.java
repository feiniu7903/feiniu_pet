package com.lvmama.comm.pet.po.edm;

import java.io.Serializable;

/**
 * @author yangbin
 *
 */
public class EdmDicCity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6586605048117862349L;
	private String id;
	private String name;
	private String parent;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	
	
	
}
