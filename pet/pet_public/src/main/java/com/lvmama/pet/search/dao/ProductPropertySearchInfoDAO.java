package com.lvmama.pet.search.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.search.ProductPropertySearchInfo;

public class ProductPropertySearchInfoDAO extends BaseIbatisDAO{

	public ProductPropertySearchInfo getProductPropertySearchInfoByProductId(Long productId){
		return (ProductPropertySearchInfo)this.queryForObject("PRODUCT_PROPERTY_SEARCH_INFO.getProductPropertySearchInfoByProductId", productId);
	}
}
