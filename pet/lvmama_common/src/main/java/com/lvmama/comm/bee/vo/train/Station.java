package com.lvmama.comm.bee.vo.train;

import java.io.Serializable;

public class Station implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2610509442626257141L;
	private String name;
	private String pinyin;
	private String pinyinHead;//简拼
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getPinyinHead() {
		return pinyinHead;
	}
	public void setPinyinHead(String pinyinHead) {
		this.pinyinHead = pinyinHead;
	}
	
	
}
