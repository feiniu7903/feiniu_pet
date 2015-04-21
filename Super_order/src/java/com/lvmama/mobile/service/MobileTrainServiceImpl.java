package com.lvmama.mobile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.prod.LineStation;
import com.lvmama.comm.pet.service.mobile.MobileTrainService;
import com.lvmama.prd.dao.LineStationDAO;

public class MobileTrainServiceImpl implements MobileTrainService {
	@Autowired
	LineStationDAO lineStationDAO;
	@Override
	public List<LineStation> getAllLineStations() {
		return lineStationDAO.selectAll();
	}

}
