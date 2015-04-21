package com.lvmama.back.web.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.prod.ProceedTours;
import com.lvmama.comm.bee.service.ProceedToursService;

/**
 * 巴士班自助统计
 * @author Brian
 *
 */
public class ListProceedToursAction extends BaseAction {
	private static final long serialVersionUID = 6117972785289616485L;
	
	private List<ProceedTours> toursList = new ArrayList<ProceedTours>();
	@SuppressWarnings("rawtypes")
	private Map searchConds = new HashMap();
	
	private ProceedToursService proceedToursService;
	
	@SuppressWarnings("unchecked")
	public void loadDataList() {
		searchConds = initialPageInfoByMap(proceedToursService.countProceedTours(searchConds), searchConds);
		toursList = proceedToursService.query(searchConds);
	}
	
	@SuppressWarnings("unchecked")
	public void changeProceedStatus(String value) {
		searchConds.remove("PROCEED");
		searchConds.remove("UNPROCEED");
		if (!StringUtils.isEmpty(value)) {
			searchConds.put(value, "true");
		} 
	}

	@SuppressWarnings("rawtypes")
	public Map getSearchConds() {
		return searchConds;
	}

	public void setSearchConds(Map<String, Object> searchConds) {
		this.searchConds = searchConds;
	}

	public List<ProceedTours> getToursList() {
		return toursList;
	}
	
	
}
