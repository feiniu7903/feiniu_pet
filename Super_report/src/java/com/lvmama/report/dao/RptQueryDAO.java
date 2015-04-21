package com.lvmama.report.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;

public class RptQueryDAO extends BaseIbatisDAO{
	
	public List<Map> query(Map map) {
		return super.queryForList("ORD_REPORT.weekReport", map);
	}
	
	public Integer count(Map map) {
		return (Integer)super.queryForObject("ORD_REPORT.weekReportCount", map);
	}
}
