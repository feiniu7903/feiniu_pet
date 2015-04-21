package com.lvmama.comm.pet.service.prod;

import java.util.List;

import com.lvmama.comm.pet.po.prod.ProdProductPlace;
import com.lvmama.comm.pet.po.prod.ProductProductPlace;

public interface ProductProductPlaceService {
	/**
	 * 根据productId来整理product_product_place里面的数据
	 * @param productId
	 * @param list
	 */
	void updateProductProductPlaceByProductId(Long productId,List<ProdProductPlace> list);

	/**
	 * 根据placeId获取所有绑定此标的的产品id
	 * @param placeId
	 * @return
	 */
	public List<ProductProductPlace> getProductProductPlaceListByPlaceId(Long placeId,int startIndex,int maxResult);
	
	public List<ProductProductPlace> getProductProductPlaceListByProductId(Long productId);
	
	
	public int countProductProductPlaceListByPlaceId(Long placeId);
	
}
