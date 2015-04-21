package com.lvmama.pet.sup.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.sup.MetaPerform;
import com.lvmama.comm.vo.Constant;

public class MetaPerformDAO extends BaseIbatisDAO{
	
	public void deleteByMetaProductIdAndTargetId(MetaPerform metaPerform) {
		int rows = super.delete(
				"META_PERFORM.deleteByMetaProductIdAndTargetId", metaPerform);
	}
	
	public void insertSuperMetaPerform(MetaPerform record) {
		record.setBizType(Constant.PRODUCT_BIZ_TYPE.BEE.name());
		super.insert("META_PERFORM.insert",
				record);
	}
	
	public Integer selectSuperByMetaProductId(Long metaProductId){
		HashMap par=new HashMap();
		par.put("metaProductId", metaProductId);
		par.put("bizType", Constant.PRODUCT_BIZ_TYPE.BEE.name());
 		Integer count= (Integer) super.queryForObject("META_PERFORM.selectByMetaProductIdAndTargetId",par);
 		return count;
 	}
	 
	public List<Long> selectMetaRelationByParam(Long metaProductId){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("metaProductId", metaProductId);
		params.put("bizType", Constant.PRODUCT_BIZ_TYPE.BEE.name());
		return super.queryForList("META_PERFORM.selectMetaRelationByParam",params);
	}
	
	public List<Long> selectMetaRelationBySupplierId(Long supplierId,Constant.PRODUCT_BIZ_TYPE type){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("supplierId", supplierId);
		params.put("bizType", type.name());
		return super.queryForList("META_PERFORM.selectMetaRelationBySupplierId",params);
	}
	public List<MetaPerform> getMetaPerformByMetaProductId(Long metaProductId){
		HashMap par=new HashMap();
		par.put("metaProductId", metaProductId);
		par.put("bizType", Constant.PRODUCT_BIZ_TYPE.BEE.name());
 		return (List<MetaPerform>) super.queryForList("META_PERFORM.getMetaPerformByMetaProductId",par);
 	}
}