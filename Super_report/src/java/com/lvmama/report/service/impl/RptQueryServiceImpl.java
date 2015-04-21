package com.lvmama.report.service.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.report.dao.RptQueryDAO;
import com.lvmama.report.service.RptQueryService;

public class RptQueryServiceImpl implements RptQueryService{
	RptQueryDAO rptQueryDAO;

	public Integer count(Map map) {
		return rptQueryDAO.count(map);
	}

	public List<Map> query(Map map) {
		return rptQueryDAO.query(map);
	}

	public void setRptQueryDAO(RptQueryDAO rptQueryDAO) {
		this.rptQueryDAO = rptQueryDAO;
	}
	
}
