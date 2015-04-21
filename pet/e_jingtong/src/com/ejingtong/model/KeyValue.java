package com.ejingtong.model;

public class KeyValue {

	private String key;
	private String value;
	private String tag;
//	private int id = -1;
	private OrderMeta meta;

	public KeyValue(String key, String value){
		this.key = key;
		this.value = value;
	}
//	
	public KeyValue(String key, String value,OrderMeta meta, String tag){
		this.key = key;
		this.value = value;
		this.meta = meta;
		this.tag = tag;
	}
//	
//	public KeyValue(String key, String value, String tag, int id){
//		this.key = key;
//		this.value = value;
//		this.tag = tag;
//		this.id = id;
//	}
	
	
	public KeyValue(String key, String value, OrderMeta meta){
		this.key = key;
		this.value = value;
		this.meta = meta;
	}
	
	
	public OrderMeta getMeta() {
		return meta;
	}

	public void setMeta(OrderMeta meta) {
		this.meta = meta;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
	
	
}
