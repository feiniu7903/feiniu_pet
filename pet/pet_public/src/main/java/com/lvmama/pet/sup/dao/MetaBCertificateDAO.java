package com.lvmama.pet.sup.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.sup.MetaBCertificate;
import com.lvmama.comm.vo.Constant;

public class MetaBCertificateDAO extends BaseIbatisDAO {

	public int deleteByMetaProductIdAndTargetId(
			MetaBCertificate metaBCertificate) {
		int rows = super.delete(
				"META_B_CERTIFICATE.deleteByMetaProductIdAndTargetId", metaBCertificate);
		return rows;
	}

	public void insert(MetaBCertificate record) {
		super.insert(
				"META_B_CERTIFICATE.insert", record);
	}
	 
	public Integer selectSuperByMetaProductId(Long metaProductId){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("metaProductId", metaProductId);
		param.put("bizType", Constant.PRODUCT_BIZ_TYPE.BEE.name());
		return (Integer) super.queryForObject("META_B_CERTIFICATE.selectByMetaProductId",param);
 	}
 
	@SuppressWarnings("unchecked")
	public List<MetaBCertificate> findByMetaParams(MetaBCertificate metaBCertificate){
		return  super.queryForList("META_B_CERTIFICATE.findByMetaParams", metaBCertificate);
	}
	
	
}