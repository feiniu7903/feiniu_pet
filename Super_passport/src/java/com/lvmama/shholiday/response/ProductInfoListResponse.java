package com.lvmama.shholiday.response;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.lvmama.shholiday.vo.product.ProductInfo;


public class ProductInfoListResponse extends AbstractResponse {
	private List<ProductInfo> productInfos ;
	
	public ProductInfoListResponse() {
		super("ProductSearchRS");
		productInfos = new ArrayList<ProductInfo>();
	}

	@Override
	protected void parseBody(Element body) {
		List<Element> productInfoEles = body.element("ProductInfos").elements();
		for(Element productInfo : productInfoEles){
			ProductInfo product = new ProductInfo();
			product.setSupplierProdId(productInfo.attributeValue("UniqueID"));
			product.setSupplierProdName(productInfo.elementText("Name"));
			product.setDestinationCity(productInfo.elementText("DestinationCityName"));
			productInfos.add(product);
		}
	}

	public List<ProductInfo> getProductInfos() {
		return productInfos;
	}

	public void setProductInfos(List<ProductInfo> productInfos) {
		this.productInfos = productInfos;
	}


}
