package com.lvmama.passport.processor.impl.client.time100;

public class SanYaMinJianBean {
	private String tid; // 产品ID关
	private String indate; // 游玩日期
	private String num; // 客人数量
	private String name; // 客人名字
	private String mb; // 手机号码
	private String payWay; // ０代收代付　１挂帐　2现付
	private String note = ""; // 如果一日游时写接送地址

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getIndate() {
		return indate;
	}

	public void setIndate(String indate) {
		this.indate = indate;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMb() {
		return mb;
	}

	public void setMb(String mb) {
		this.mb = mb;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
