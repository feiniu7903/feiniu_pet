package com.lvmama.comm.bee.po.tmall;

import java.io.Serializable;



public class TmallPerson implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6433844482015440150L;

	public TmallPerson(){
		
	}
	public TmallPerson(String name, String mobile, String identity) {
		super();
		this.name = name;
		this.mobile = mobile;
		this.identity = identity;
	}

	public TmallPerson(String name, String mobile) {
		super();
		this.name = name;
		this.mobile = mobile;
	}

	private String name;//姓名
	private String mobile;//手机号
	private String identity;//身份证号

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	

}
