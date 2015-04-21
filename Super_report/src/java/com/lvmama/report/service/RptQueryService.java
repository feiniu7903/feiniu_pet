package com.lvmama.report.service;

import java.util.List;
import java.util.Map;

public interface RptQueryService {
	
	public List<Map> query(Map map);
	
	public Integer count(Map map);
	
}
