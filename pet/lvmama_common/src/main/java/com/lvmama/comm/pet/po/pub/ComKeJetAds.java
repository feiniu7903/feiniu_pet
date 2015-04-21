package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;

/**
 * 科捷广告的链接地址
 * @author Administrator
 *
 */
public class ComKeJetAds implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 182862031163773800L;
	private Long id;
	private String ap;
	private String ct;
	private String extKey;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAp() {
		return ap;
	}
	public void setAp(String ap) {
		this.ap = ap;
	}
	public String getCt() {
		return ct;
	}
	public void setCt(String ct) {
		this.ct = ct;
	}
	public String getExtKey() {
		return extKey;
	}
	public void setExtKey(String extKey) {
		this.extKey = extKey;
	}
}
