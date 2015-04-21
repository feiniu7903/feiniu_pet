package com.lvmama.prd.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.meta.MetaTravelCode;
import com.lvmama.comm.bee.service.meta.MetaTravelCodeService;
import com.lvmama.prd.dao.MetaTravelCodeDAO;

public class MetaTravelCodeServiceImpl implements MetaTravelCodeService {

	private MetaTravelCodeDAO metaTravelCodeDAO;
	
	@Override
	public int deleteByPrimaryKey(Long metaTravelCodeId) {
		return metaTravelCodeDAO.deleteByPrimaryKey(metaTravelCodeId);
	}

	@Override
	public Long insert(MetaTravelCode record) {
		return metaTravelCodeDAO.insert(record);
	}
	
	@Override
	public List<MetaTravelCode> selectByCondition(Map<String, Object> params) {
		return metaTravelCodeDAO.selectByCondition(params);
	}

	@Override
	public MetaTravelCode selectByPrimaryKey(Long metaTravelCodeId) {
		return metaTravelCodeDAO.selectByPrimaryKey(metaTravelCodeId);
	}

	@Override
	public MetaTravelCode selectBySuppAndDate(String supplierProductId,
			Date specDate) {
		return metaTravelCodeDAO.selectBySuppAndDate(supplierProductId, specDate);
	}

	public MetaTravelCode selectBySuppAndDateAndChannelAndBranch(String supplierProductId,Date specDate,String channel,String branch){
		List<MetaTravelCode> mtcs = metaTravelCodeDAO.selectBySuppAndDateAndChannelAndBranch(supplierProductId, specDate,channel,branch);
		if(mtcs!=null && mtcs.size()>0){
			return mtcs.get(0);
		}
		return null;
	}
	@Override
	public int updateByPrimaryKeySelective(MetaTravelCode record) {
		return metaTravelCodeDAO.updateByPrimaryKey(record);
	}

	public void setMetaTravelCodeDAO(MetaTravelCodeDAO metaTravelCodeDAO) {
		this.metaTravelCodeDAO = metaTravelCodeDAO;
	}

	
	
	

}
