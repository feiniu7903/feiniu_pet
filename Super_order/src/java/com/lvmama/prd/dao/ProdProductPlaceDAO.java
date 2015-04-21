package com.lvmama.prd.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;

public class ProdProductPlaceDAO extends BaseIbatisDAO{
	
	/**
	 * 返回列表
	 * @param productId 销售产品id
	 * @return
	 */
	public List<ProdProductPlace> selectByProductId(Long productId){
		return super.queryForList("PROD_PRODUCT_PLACE.PRODUCT_PLACE_SELECT", productId);
	}
	
	/**
	 * 返回列表 placeId
	 * @param productId 销售产品id
	 * @return
	 */
	public List<ProdProductPlace> selectPlaceIdByProductId(Long productId){
		return super.queryForList("PROD_PRODUCT_PLACE.PRODUCT_PLACE_SELECT_BY_PRODUCTID", productId);
	}
	
	/**
	 * 新增一条记录
	 * @param prodProductPlace
	 */
	public Long insert(ProdProductPlace prodProductPlace){
		return (Long)super.insert("PROD_PRODUCT_PLACE.PRODUCT_PLACE_INSERT", prodProductPlace);
	}
	
	public ProdProductPlace selectByPrimaryKey(Long productPlaceId){
		return (ProdProductPlace)super.queryForObject("PROD_PRODUCT_PLACE.PRODUCT_PLACE_LOAD", productPlaceId);
	}
	
	/**
	 * 删除一条记录
	 * @param productPlaceId 主键
	 */
	public void delete(Long productPlaceId){
		super.delete("PROD_PRODUCT_PLACE.PRODUCT_PLACE_DELETE", productPlaceId);
	}
	
	public void deleteByProductId(Long productId){
		super.delete("PROD_PRODUCT_PLACE.PRODUCT_PLACE_DELETE_BY_PRODUCTID", productId);
	}
	
	public List<ProdProductPlace> findProdProductPlace(Long prodProductId) {
		return (List<ProdProductPlace>) super.queryForList("PROD_PRODUCT_PLACE.selectByProdProductId", prodProductId);
	}
	
	/**
	 * 根据产品ID查询标的ID
	 * @param productId
	 * @return
	 */
	public Long selectDestByProductId(Long productId) {
		return (Long) super.queryForObject("PROD_PRODUCT_PLACE.selectDestByProductId",productId);
	}
	
	
	/**
	 * 清空一个产品的出发或目的地
	 * @param param
	 */
	public void clearProductPlaceFT(Map<String,Object> param){
		super.update("PROD_PRODUCT_PLACE.clearProductPlaceFT",param);
	}
	
	public void updateByPrimaryKey(ProdProductPlace place){		
		super.update("PROD_PRODUCT_PLACE.updateByPrimaryKey", place);
	}
	public List<ProdProductPlace> getProdProductPlaceListByProductId(Long productId){
		return (List<ProdProductPlace>)super.queryForList("PROD_PRODUCT_PLACE.getProdProductPlaceListByProductId",productId);
	}
	
}
