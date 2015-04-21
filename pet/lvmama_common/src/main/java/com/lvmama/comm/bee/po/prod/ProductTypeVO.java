package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;
import java.util.List;

public class ProductTypeVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1063800600740053550L;
	private ProductModelType modelType;
	private List<ProductModelProperty> productModelPropertyList;
	public ProductModelType getModelType() {
		return modelType;
	}
	public void setModelType(ProductModelType modelType) {
		this.modelType = modelType;
	}
	public List<ProductModelProperty> getProductModelPropertyList() {
		return productModelPropertyList;
	}
	public void setProductModelPropertyList(
			List<ProductModelProperty> productModelPropertyList) {
		this.productModelPropertyList = productModelPropertyList;
	}
	
}
