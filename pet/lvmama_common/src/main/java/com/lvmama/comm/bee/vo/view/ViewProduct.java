package com.lvmama.comm.bee.vo.view;

import java.io.Serializable;

public class ViewProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7270627128117461062L;

	private ViewProdProduct prodProduct;
	
	/** 产品特色 */
	private String features;
	/** 费用包含 */
	private String costcontain;
	/** 推荐项目 */
	private String recommendproject;
	/** 购物说明 */
	private String shoppingexplain;
	/** 产品特别提示 */
	private String importmentclew;
	/** 公告 */
	private String announcement;
	/** 产品经理推荐 */
	private String managerrecommend;
	/** 预订须知 */
	private String ordertoknown;
	/** 服务保障*/
	private String serviceguarantee;
	public ViewProdProduct getProdProduct() {
		return prodProduct;
	}
	public void setProdProduct(ViewProdProduct prodProduct) {
		this.prodProduct = prodProduct;
	}
	public String getFeatures() {
		return features;
	}
	public void setFeatures(String features) {
		this.features = features;
	}
	public String getCostcontain() {
		return costcontain;
	}
	public void setCostcontain(String costcontain) {
		this.costcontain = costcontain;
	}
	public String getRecommendproject() {
		return recommendproject;
	}
	public void setRecommendproject(String recommendproject) {
		this.recommendproject = recommendproject;
	}
	public String getShoppingexplain() {
		return shoppingexplain;
	}
	public void setShoppingexplain(String shoppingexplain) {
		this.shoppingexplain = shoppingexplain;
	}
	public String getImportmentclew() {
		return importmentclew;
	}
	public void setImportmentclew(String importmentclew) {
		this.importmentclew = importmentclew;
	}
	public String getAnnouncement() {
		return announcement;
	}
	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}
	public String getManagerrecommend() {
		return managerrecommend;
	}
	public void setManagerrecommend(String managerrecommend) {
		this.managerrecommend = managerrecommend;
	}
	public String getOrdertoknown() {
		return ordertoknown;
	}
	public void setOrdertoknown(String ordertoknown) {
		this.ordertoknown = ordertoknown;
	}
	public String getServiceguarantee() {
		return serviceguarantee;
	}
	public void setServiceguarantee(String serviceguarantee) {
		this.serviceguarantee = serviceguarantee;
	}
	
	 
}
