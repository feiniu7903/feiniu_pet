package com.lvmama.passport.processor.impl.client.manyouyou;

public class SubmitRequest{
	private String method;//必须	固定值 item_orders
	private String format;//可选	返回文件格式，见format 参数说明
	private String _pid;//合作伙伴id
	private String _sig;//签名。见签名参数
	private String orders_id;//	第三方订单ID，可避免网络不好时重复下单
	private String item_id;//购买的票ID
	private String size;//购买票数,缺省1
	private String name;//购票人名称
	private String mobile;//购票人手机号
	private String start_date;//开始游玩时间
	private String sms_send;//是否发送通知短信，0不发送，1发送，缺省1(求账号要有发送短信权限)
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String get_pid() {
		return _pid;
	}
	public void set_pid(String _pid) {
		this._pid = _pid;
	}
	public String get_sig() {
		return _sig;
	}
	public void set_sig(String _sig) {
		this._sig = _sig;
	}
	public String getOrders_id() {
		return orders_id;
	}
	public void setOrders_id(String orders_id) {
		this.orders_id = orders_id;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
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
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getSms_send() {
		return sms_send;
	}
	public void setSms_send(String sms_send) {
		this.sms_send = sms_send;
	}
}