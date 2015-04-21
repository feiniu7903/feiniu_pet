package com.lvmama.front.web.ajax;

import java.io.Serializable;

import net.sf.json.JSONObject;
/**
 * 推荐的产品
 * @author Brian
 *
 */
public class AjaxRtnRecommendProduct implements Serializable {
	/**
	 * 推荐标识
	 */
	private String sid;
	/**
	 * 产品标识
	 */
	private Long productId;
	/**
	 * 产品名字
	 */
	private String productName;
	/**
	 * 图片
	 */
	private String pic;
	/**
	 * 价格
	 */
	private Float price;
	
	public AjaxRtnRecommendProduct() {}
	
	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
	
	public AjaxRtnRecommendProduct(String sid, Long productId, String productName, String pic, Float price) {
		this.sid = sid;
		this.productId = productId;
		this.productName = productName;
		this.pic = pic;
		this.price = price;
	}
	
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
}
