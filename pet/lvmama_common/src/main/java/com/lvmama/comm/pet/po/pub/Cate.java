package com.lvmama.comm.pet.po.pub;

import java.util.List;

public class Cate {
	private String name;
	private String code;
	private List<Cate> child;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Cate> getChild() {
		return child;
	}

	public void setChild(List<Cate> child) {
		this.child = child;
	}
}
