package com.lvmama.ord.service.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.NcComplaintType;
import com.lvmama.comm.bee.service.complaint.NcComplaintTypeService;
import com.lvmama.order.dao.NcComplaintTypeDAO;

public class NcComplaintTypeServiceImpl implements NcComplaintTypeService {

	private NcComplaintTypeDAO ncComplaintTypeDAO;

	@Override
	public Long getTypeCount() {
		return ncComplaintTypeDAO.getTypeCount();
	}

	@Override
	public List<NcComplaintType> getAllTypeByPage(Map<String, Object> map) {
		return ncComplaintTypeDAO.queryAllTypeByPage(map);
	}

	@Override
	public NcComplaintType selectTypeById(Map<String, Object> map) {
		return ncComplaintTypeDAO.selectTypeById(map);
	}

	@Override
	public int updateComplaintType(NcComplaintType type) {
		return ncComplaintTypeDAO.updateType(type);
	}

	@Override
	public Long addComplaintType(NcComplaintType type) {
		return ncComplaintTypeDAO.addComplaintType(type);
	}

	public NcComplaintTypeDAO getNcComplaintTypeDAO() {
		return ncComplaintTypeDAO;
	}

	public void setNcComplaintTypeDAO(NcComplaintTypeDAO ncComplaintTypeDAO) {
		this.ncComplaintTypeDAO = ncComplaintTypeDAO;
	}
}
