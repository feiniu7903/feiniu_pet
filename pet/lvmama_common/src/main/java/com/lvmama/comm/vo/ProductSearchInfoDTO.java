package com.lvmama.comm.vo;

import java.io.Serializable;
import java.util.List;

import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;

public class ProductSearchInfoDTO implements Serializable {
	private static final long serialVersionUID = 1496243250483086143L;

	private ProductSearchInfo productSearchInfo;
	private List<ProdTag> prodTagList;
	private boolean twoDimCodeProduct;

	public ProductSearchInfo getProductSearchInfo() {
		return productSearchInfo;
	}

	public void setProductSearchInfo(ProductSearchInfo productSearchInfo) {
		this.productSearchInfo = productSearchInfo;
	}

	public List<ProdTag> getProdTagList() {
		return prodTagList;
	}

	public void setProdTagList(List<ProdTag> prodTagList) {
		this.prodTagList = prodTagList;
	}

	public boolean isTwoDimCodeProduct() {
		return twoDimCodeProduct;
	}

	public void setTwoDimCodeProduct(boolean twoDimCodeProduct) {
		this.twoDimCodeProduct = twoDimCodeProduct;
	}
}
