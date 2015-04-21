package com.lvmama.prd.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.prod.ProdProductModelProperty;
import com.lvmama.comm.vo.ProdProductModelPropertyVO;

public class ProdProductModelPropertyDAO extends BaseIbatisDAO {
	public void saveProdProductModelProperty(ProdProductModelProperty prodProductModelProperty){
		super.insert("PROD_PRODUCT_MODEL_PROPERTY.insert",prodProductModelProperty);
	}
	public void deleteProdProductModelPropertyByProductId(Map map){
		super.delete("PROD_PRODUCT_MODEL_PROPERTY.deleteByProductId",map);
	}
	public List<ProdProductModelProperty> getProdProductModelPropertyByProductId(String productId){
		List<ProdProductModelProperty> list=(List<ProdProductModelProperty>)super.queryForList("PROD_PRODUCT_MODEL_PROPERTY.getProdProductModelPropertyByProductId",productId);
		return list;
	}
	public List<ProdProductModelPropertyVO> getProdProductModelPropertyVOByProductId(String productId){
		List<ProdProductModelPropertyVO> list=(List<ProdProductModelPropertyVO>)super.queryForList("PROD_PRODUCT_MODEL_PROPERTY.getProdProductModelPropertyVOByProductId",productId);
		return list;
	}
	public long isCheckExistByProperty(String propertyId){
		Long count=(Long)super.queryForObject("PROD_PRODUCT_MODEL_PROPERTY.isCheckExistByProperty",propertyId);
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProdProductModelProperty> selectByParam(Map<String, Object> map){
		if(map != null && map.size() > 0){
			return (List<ProdProductModelProperty>)super.queryForList("PROD_PRODUCT_MODEL_PROPERTY.selectByParam", map);
		}else{
			return null;
		}
	}
	
}
