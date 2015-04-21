package com.lvmama.front.web.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.shop.ShopProduct;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;

/**
 * 积分商城产品集合页的Action
 * @author yuzhizeng
 *
 */
@Results({
	@Result(name="productList",location="/WEB-INF/pages/shop/productList.ftl",type="freemarker")
})
public class ShopProductListAction extends ShopIndexLeftAction {
	/**
	 * 序列
	 */
	private static final long serialVersionUID = 2517818314956703776L;
	
	private Page<ShopProduct> page = new Page<ShopProduct>(0);
	private Long currentPage = 1L;
	private Long pageSize = 12L;
	
	/**
	 * 产品类型
	 */
	private String productType;
	/**
	 * 开始积分
	 */
	private String startPoints;
	private String endPoints;
	/**
	 * 购买力
	 */
	private String purchasingPower;
	
	private List<ShopProduct> productList;
	/**
	 * 兑换方式
	 */
	private String changeType;
	
	/**
	 * 根据产品类型查询产品
	 * @return
	 */
	@Action("/shop/moreProductByType")
	public String moreProductByType() {
		
		initIndexLeft(); 
		
		//构造查询条件
		Map<String,Object> parameters = new HashMap<String, Object>();
		initParameters(parameters);
		
		//分页逻辑
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		page.setTotalResultSize(shopProductService.count(parameters).intValue());
		
		parameters.put("_startRow", (currentPage - 1) * pageSize + 1 + "");
		parameters.put("_endRow", currentPage * pageSize + "");
		
		productList = shopProductService.query(parameters);
		page.setItems(productList);
		
		//获取分页URL
		String paraStr = getPageConfigURL();
		if (page.getItems().size() > 0) {
			this.page.setUrl("/shop/moreProductByType.do?" + paraStr);
		}
		
		return "productList";
	}
	
	/**
	 * 构造分页URL
	 * @return
	 */
	private String getPageConfigURL(){
		StringBuffer para = new StringBuffer();
		if(startPoints == null) {
			para.append("startPoints=");
		}else{
			para.append("startPoints=").append(startPoints);
		}
		if(endPoints == null){
			para.append("&endPoints=");
		}else{
			para.append("&endPoints=" + endPoints);
		}
		if(productType != null) para.append("&productType=").append(productType);
		if(changeType != null) para.append("&changeType=").append(changeType);
		if(purchasingPower != null) para.append("&purchasingPower=").append(purchasingPower);
		para.append("&currentPage=");
		
		return para.toString();
	}
	
	/**
	 * 构造查询条件
	 * @return
	 */
	private void initParameters(Map<String,Object> parameters){
		parameters.put("isValid", "Y");
		if(!StringUtil.isEmptyString(productType)) parameters.put("productType", productType);
		if(!StringUtil.isEmptyString(changeType)) parameters.put("changeType", changeType);
		if(!StringUtil.isEmptyString(startPoints)) parameters.put("startPoints", Long.parseLong(startPoints));
		if(!StringUtil.isEmptyString(endPoints)) parameters.put("endPoints", Long.parseLong(endPoints));
		
		//链接”我的积分能兑换什么”处逻辑(购买力范围)
		if("Y".equalsIgnoreCase(purchasingPower)){
			parameters.put("purchasingPower", purchasingPower);
			parameters.put("endPoints", shopUser.getPoint());
		}
	}
	
	public Long getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Long currentPage) {
		this.currentPage = currentPage;
	}
	public Long getPageSize() {
		return pageSize;
	}
	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getPurchasingPower() {
		return purchasingPower;
	}
	public void setPurchasingPower(String purchasingPower) {
		this.purchasingPower = purchasingPower;
	}
	public List<ShopProduct> getProductList() {
		return productList;
	}
	public void setProductList(List<ShopProduct> productList) {
		this.productList = productList;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public String getStartPoints() {
		return startPoints;
	}
	public void setStartPoints(String startPoints) {
		this.startPoints = startPoints;
	}
	public String getEndPoints() {
		return endPoints;
	}
	public void setEndPoints(String endPoints) {
		this.endPoints = endPoints;
	}

	public Page<ShopProduct> getPage() {
		return page;
	}
	
}
