package com.lvmama.jinjiang.model;

import java.util.List;

/**
 * 线路特色
 * @author chenkeke
 *
 */
public class LineFeature {
	private String description;
	private List<String> item;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getItem() {
		return item;
	}
	public void setItem(List<String> item) {
		this.item = item;
	}
}
