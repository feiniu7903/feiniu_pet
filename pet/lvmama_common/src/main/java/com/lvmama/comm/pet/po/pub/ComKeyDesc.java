package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;

/**
 * @deprecated 临时使用，记录memcache的键值对
 * @author Brian
 *
 */
public class ComKeyDesc implements Serializable {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 2182646178834834325L;
	
	private String key;
	private String desc;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
