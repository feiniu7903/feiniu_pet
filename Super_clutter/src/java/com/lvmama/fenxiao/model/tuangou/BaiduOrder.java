package com.lvmama.fenxiao.model.tuangou;

/**
 * 应用级输入参数 {@link http://developer.baidu.com/wiki/index.php?title=Open_API%E6%96%87%E6%A1%A3/hao123/saveOrder}
 * 
 * @author qiuguobin
 * 
 */
public class BaiduOrder {
	private String order_id;
	private String order_time;
	private String order_city;
	private String title;
	private String logo;
	private String url;
	private String price;
	private String goods_num;
	private String sum_price;
	private String summary;
	private String expire;
	private String addr;
	private String uid;
	private String mobile;
	private String tn;
	private String baiduid;
	private String bonus;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_time() {
		return order_time;
	}

	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}

	public String getOrder_city() {
		return order_city;
	}

	public void setOrder_city(String order_city) {
		this.order_city = order_city;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(String goods_num) {
		this.goods_num = goods_num;
	}

	public String getSum_price() {
		return sum_price;
	}

	public void setSum_price(String sum_price) {
		this.sum_price = sum_price;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getExpire() {
		return expire;
	}

	public void setExpire(String expire) {
		this.expire = expire;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTn() {
		return tn;
	}

	public void setTn(String tn) {
		this.tn = tn;
	}

	public String getBaiduid() {
		return baiduid;
	}

	public void setBaiduid(String baiduid) {
		this.baiduid = baiduid;
	}

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	@Override
	public String toString() {
		return "BaiduOrder [order_id=" + order_id + ", order_time=" + order_time + ", order_city=" + order_city + ", title=" + title + ", logo=" + logo + ", url=" + url + ", price=" + price + ", goods_num=" + goods_num + ", sum_price="
				+ sum_price + ", summary=" + summary + ", expire=" + expire + ", addr=" + addr + ", uid=" + uid + ", mobile=" + mobile + ", tn=" + tn + ", baiduid=" + baiduid + ", bonus=" + bonus + "]";
	}
}
