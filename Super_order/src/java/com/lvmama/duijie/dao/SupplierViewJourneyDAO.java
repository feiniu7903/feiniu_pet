package com.lvmama.duijie.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.duijie.SupplierViewJourney;

public class SupplierViewJourneyDAO  extends BaseIbatisDAO{
	public void insert(SupplierViewJourney journey){
		super.insert("SUPPLIER_VIEW_JOURNEY.insert",journey);
	}
	
	public void update(SupplierViewJourney journey){
		super.update("SUPPLIER_VIEW_JOURNEY.update",journey);
	}
	
	public List<SupplierViewJourney> selectSupplierViewJourneyByCondition(Map<String,Object> params){
		return (List<SupplierViewJourney>)super.queryForList("SUPPLIER_VIEW_JOURNEY.selectSupplierViewJourneyByCondition",params);
	}
}
