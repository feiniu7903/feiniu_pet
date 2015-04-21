package com.lvmama.pet.sup.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;


public class SupBCertificateTargetDAO extends BaseIbatisDAO {

	public List<SupBCertificateTarget> findBCertificateTarget(Map param) {
		if (param.get("_startRow")==null) {
			param.put("_startRow", 0);
		}
		if (param.get("_endRow")==null) {
			param.put("_endRow", 20);
		}
		return super.queryForList("SUP_B_CERTIFICATE_TARGET.findBCertificateTarget", param);
	}
 
	public int deleteByPrimaryKey(Long targetId) {
		SupBCertificateTarget key = new SupBCertificateTarget();
		key.setTargetId(targetId);
		int rows = super.delete("SUP_B_CERTIFICATE_TARGET.deleteByPrimaryKey", key);
		return rows;
	}

	public Long insert(SupBCertificateTarget record) {
		try {
			return (Long)super.insert("SUP_B_CERTIFICATE_TARGET.insert", record);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public SupBCertificateTarget selectByPrimaryKey(Long targetId) {
		SupBCertificateTarget key = new SupBCertificateTarget();
		key.setTargetId(targetId);
		SupBCertificateTarget record = (SupBCertificateTarget) super.queryForObject(
				"SUP_B_CERTIFICATE_TARGET.selectByPrimaryKey", key);
		return record;
	}

	public int updateByPrimaryKey(SupBCertificateTarget record) {
		int rows = super.update("SUP_B_CERTIFICATE_TARGET.updateByPrimaryKey", record);
		return rows;
	}

	public void markIsValid(Map params) {
		super.update("SUP_B_CERTIFICATE_TARGET.markIsValid", params);
	}
	
	public Integer selectRowCount(Map searchConds){
		Integer count = 0;
		count = (Integer) super.queryForObject("SUP_B_CERTIFICATE_TARGET.selectRowCount",searchConds);
		return count;
	}
 
	public List<SupBCertificateTarget> selectMetaBCertificateByMetaProductId(Long metaProductId,String bizType) {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("metaProductId", metaProductId);
		p.put("bizType", bizType);
		return super.queryForList("SUP_B_CERTIFICATE_TARGET.selectMetaBCertificateByMetaProductId", p);
	}
	
	public SupBCertificateTarget getMetaBCertificateByMetaProductId(Long metaProductId,String bizType) {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("metaProductId", metaProductId);
		p.put("bizType", bizType);
		List<SupBCertificateTarget> supBCertificateTargetList = super.queryForList("SUP_B_CERTIFICATE_TARGET.selectMetaBCertificateByMetaProductId", p);
		if(supBCertificateTargetList != null && supBCertificateTargetList.size() > 0 ){
			return supBCertificateTargetList.get(0);
		}
		return null;
	}
}