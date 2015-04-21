package com.lvmama.duijie.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.duijie.SupplierViewJourney;
import com.lvmama.comm.bee.service.duijie.SupplierViewJourneyService;
import com.lvmama.duijie.dao.SupplierViewJourneyDAO;

public class SupplierViewJourneyServiceImpl implements SupplierViewJourneyService{
	private SupplierViewJourneyDAO supplierViewJourneyDAO;
	
	@Override
	public void insert(SupplierViewJourney journey) {
		supplierViewJourneyDAO.insert(journey);
	}
	
	@Override
	public void update(SupplierViewJourney journey) {
		 supplierViewJourneyDAO.update(journey);
	}

	@Override
	public List<SupplierViewJourney> selectSupplierViewJourneyByCondition(Map<String,Object> params) {
		return supplierViewJourneyDAO.selectSupplierViewJourneyByCondition(params);
	}

	public void setSupplierViewJourneyDAO(
			SupplierViewJourneyDAO supplierViewJourneyDAO) {
		this.supplierViewJourneyDAO = supplierViewJourneyDAO;
	}

	
	
}
