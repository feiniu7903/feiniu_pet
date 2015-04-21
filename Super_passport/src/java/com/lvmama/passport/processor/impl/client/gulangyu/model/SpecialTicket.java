package com.lvmama.passport.processor.impl.client.gulangyu.model;
/**
 * 鼓浪屿特殊门票对象(节假日特价票)
 * 
 * @author lipengcheng
 * 
 */
public class SpecialTicket {
	
	/** ID号 */
	private String uuId;

	/** 景区ID号 */
	private String uuLid;

	/** 门票ID号 */
	private String uuTid;

	/** 指定日期 */
	private String uuPtime;

	/** 特价门票价格 */
	private String uuPprice;

	/** 特价说明 */
	private String uuNotes;

	public String getUuId() {
		return uuId;
	}

	public void setUuId(String uuId) {
		this.uuId = uuId;
	}

	public String getUuLid() {
		return uuLid;
	}

	public void setUuLid(String uuLid) {
		this.uuLid = uuLid;
	}

	public String getUuTid() {
		return uuTid;
	}

	public void setUuTid(String uuTid) {
		this.uuTid = uuTid;
	}

	public String getUuPtime() {
		return uuPtime;
	}

	public void setUuPtime(String uuPtime) {
		this.uuPtime = uuPtime;
	}

	public String getUuPprice() {
		return uuPprice;
	}

	public void setUuPprice(String uuPprice) {
		this.uuPprice = uuPprice;
	}

	public String getUuNotes() {
		return uuNotes;
	}

	public void setUuNotes(String uuNotes) {
		this.uuNotes = uuNotes;
	}
	
}