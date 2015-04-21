package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;

/**
 * 科捷广告的文字描述
 * @author Administrator
 *
 */
public class ComKeJetWord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3661449024571041904L;
	private Long id;
	private String code;
	private String description;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	
}
