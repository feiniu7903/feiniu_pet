package com.lvmama.distribution.model.lv;

import java.util.List;

import com.lvmama.comm.bee.po.distribution.DistributionProduct;

/**
 * 分销--时间价格列表
 * @author lipengcheng
 *
 */
public class PriceList {
	private List<Product> products;
	private List<DistributionProduct> distributionProductList;
	
	public PriceList(){}
	
	public PriceList(List<DistributionProduct> distributionProductList){
		this.distributionProductList = distributionProductList;
	}

	/**
	 * 构造时间价格表报文--产品列表节点
	 * @return
	 */
	public String buildForProductPriceList() {
		if(distributionProductList==null){
			return null;
		}
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<priceList>");
		for (DistributionProduct distributionProduct : distributionProductList) {
			Product product = new Product(distributionProduct);
			xmlStr.append(product.buildForProductPriceList());
		}
		xmlStr.append("</priceList>");
		return xmlStr.toString();
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
