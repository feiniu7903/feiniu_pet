package com.lvmama.front.dto.abroadHotel;

import java.io.Serializable;

import com.lvmama.comm.abroad.vo.request.Adult;

public class AbroadHotelPerson extends Adult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4957538307502087688L;
	
	/**
	 * 年龄
	 */
	private String age;
	
	/**
	 * 是否小孩
	 */
	private boolean isChild;

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public boolean getIsChild() {
		return isChild;
	}

	public void setIsChild(boolean isChild) {
		this.isChild = isChild;
	}

}
