package com.lvmama.tmall.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.tmall.OrdTmallDistributorMap;
import com.lvmama.comm.bee.service.tmall.OrdTmallDistributorMapService;
import com.lvmama.tmall.dao.OrdTmallDistributorMapDAO;

public class OrdTmallDistributorMapServiceImpl implements OrdTmallDistributorMapService {
	private OrdTmallDistributorMapDAO ordTmallDistributorMapDAO;

	@Override
	public boolean getOrdTmallDistributorMapCount(Long fenXiaoId) {
		Map<String,Object> param= new HashMap<String,Object>();
		param.put("fenXiaoId", fenXiaoId);
		return ordTmallDistributorMapDAO.getOrdTmallDistributorMapCount(param);
	}

	@Override
	public Long insert(OrdTmallDistributorMap record) {
		return ordTmallDistributorMapDAO.insert(record);
	}
	@Override
	public Integer updateAllByPrimaryKey(OrdTmallDistributorMap record) {
		return ordTmallDistributorMapDAO.updateAllByPrimaryKey(record);
	}
	@Override
	public List<String>  selectOrdOfCreate() {
		return ordTmallDistributorMapDAO.selectOrdOfCreate();
	}
	@Override
	public boolean selectCertificateType(Long orderId) {
		return ordTmallDistributorMapDAO.selectCertificateType(orderId);
	}
	
	@Override
	public Integer selectCountByParam(Map<String, Object> param) {
		return ordTmallDistributorMapDAO.selectCountByParam(param);
	}

	@Override
	public List<OrdTmallDistributorMap> selectByParam(Map<String, Object> param) {
		return ordTmallDistributorMapDAO.getOrdTmallDistributorMapList(param);
	}
	@Override
	public OrdTmallDistributorMap selectByPK(Long ordTmallDistributorMapId) {
		return ordTmallDistributorMapDAO.selectByPK(ordTmallDistributorMapId);
	}
	public void setOrdTmallDistributorMapDAO(
			OrdTmallDistributorMapDAO ordTmallDistributorMapDAO) {
		this.ordTmallDistributorMapDAO = ordTmallDistributorMapDAO;
	}
}
