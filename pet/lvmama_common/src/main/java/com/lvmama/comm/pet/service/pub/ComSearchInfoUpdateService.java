package com.lvmama.comm.pet.service.pub;

import java.util.List;

/**
 * 对产品，产品分类，目的地/景区/酒店修改之后需要向COM_SEARCH_INFP_UPDATE表中新增一条记录
 * 用于同步修改内容至PRODUCT_SEARCH_INFO,PROD_BRANCH_SEARCH_INFO,PLACE_SEARCH_INFO表中
 * 
 * @author yanggan
 * 
 */
public interface ComSearchInfoUpdateService {

	/**
	 * PLACE表的信息更新
	 * @param placeId
	 */
	public void placeUpdated(Long placeId,String stage);
	
	/**
	 * 产品的信息更新
	 * @param productId
	 */
	public void productUpdated(Long productId);
	
	/**
	 * 产品分类的信息更新
	 * @param prodBranchId
	 */
	public void productBranchUpdated(Long prodBranchId);
	

	/**
	 *ver酒店的信息更新
	 * @param prodBranchId
	 */
	public void verHotelUpdated(Long verHotelId);
	
	public List<Long> deleteUpdated(String updateType);
	
	public List<String> deleteUpdatedWithExtCol(String updateType);
	
	/**
	 * 同步更新PRODUCT_SEARCH_INFO
	 */
	public void syncProductSearchInfo();
	
	/**
	 * 同步更新PROD_BRANCH_SEARCH_INFO
	 */
	public void syncProdBranchSearchInfo();
	/**
	 * 同步更新PLACE_SEARCH_INFO
	 */
	public void syncPlaceSearchInfo();
}
