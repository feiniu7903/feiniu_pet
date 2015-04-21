package com.lvmama.comm.pet.po.department;

import java.io.Serializable;
import java.util.Date;

public class Department implements Serializable{
	
	private static final long serialVersionUID = 7752429694224459449L;
	private Integer id;
	private String name;
	private Integer superId;
	private Date createDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSuperId() {
		return superId;
	}
	public void setSuperId(Integer superId) {
		this.superId = superId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
