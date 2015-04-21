package com.lvmama.distribution.model.lv;

import java.util.List;

import com.lvmama.comm.bee.po.distribution.DistributionProduct;

/**
 * 分销产品列表
 * @author lipengcheng
 *
 */
public class ProductList {
	
	/**单个产品信息*/
	private List<Product> productList;
	private List<DistributionProduct> distributionProductList; 
	private boolean isQunar;
	public ProductList(){
	}
	public ProductList(List<DistributionProduct> distributionProductList) {
		this.distributionProductList = distributionProductList;
	}
	
	/**
	 * 构造查询产品列表报文--产品列表节点
	 * @return
	 */
	public String buildForProductInfoList() {
		if (distributionProductList != null) {
			StringBuilder xmlStr = new StringBuilder();
			xmlStr.append("<productList>");
			for (DistributionProduct distributionProduct : distributionProductList) {
				Product product = new Product(distributionProduct);
				if(isQunar){
					product.setBranchIsQuar(isQunar);
					product.setCashZero(distributionProduct.getProdProduct().isPaymentToSupplier());
				}
				xmlStr.append(product.buildForProductInfoList());
			}
			xmlStr.append("</productList>");
			return xmlStr.toString();
		} else {
			return null;
		}
	}
	
	/**
	 * 生成产品上下线信息列表报文
	 * 
	 * @return
	 */
	public String buildForGetProductOnLine() {
		if (distributionProductList != null) {
			StringBuilder xmlStr = new StringBuilder();
			xmlStr.append("<productList>");
			for (DistributionProduct distributionProduct : distributionProductList) {
				Product product = new Product(distributionProduct,Boolean.TRUE);
				xmlStr.append(product.buildForGetProductOnLine());
			}
			xmlStr.append("</productList>");
			return xmlStr.toString();
		} else {
			return null;
		}
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	public void setQunar(boolean isQunar) {
		this.isQunar = isQunar;
	}
	
	
}
