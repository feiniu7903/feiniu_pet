package com.lvmama.report.web.user;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.report.po.UserRegisterBasicMV;
import com.lvmama.report.service.ReportService;
import com.lvmama.report.web.BaseAction;

public class listUserOverviewAction extends BaseAction{
	
	private List<UserRegisterBasicMV> overviewList = new ArrayList<UserRegisterBasicMV>();
	protected Map<String, Object> searchConds = new HashMap<String, Object>();
	private ReportService reportService;
	private Long UserUpdate=0l;
	private Long PayUserUpdate=0l;
	private Long Pay2UserUpdate=0l;
	private Long allUserUpdate=0l;
	private Long allPayUserUpdate=0l;
	private Long allPay2UserUpdate=0l;
	private String allPayUserUpdatePer;
	private String allPay2UserUpdatePer;
	
	
	public void doBefore() throws Exception{
		DecimalFormat df = new DecimalFormat("0.000"); 
		
		allUserUpdate = reportService.allUserUpdate(searchConds);
		allPayUserUpdate = reportService.allPayUserUpdate(searchConds);
		allPay2UserUpdate = reportService.allPay2UserUpdate(searchConds);
		
		float tmp = allPayUserUpdate*100.00f/allUserUpdate;
		allPayUserUpdatePer = df.format(tmp);
		tmp = allPay2UserUpdate*100.00f/allUserUpdate;
		allPay2UserUpdatePer = df.format(tmp);
	}
	
	public void doQuery() throws Exception{
		initialPageInfoByMap(reportService.countUserOverviewRegisterBasicMV(searchConds), searchConds);
		overviewList = reportService.queryUserOverviewRegisterBasicMV(searchConds,false);
		
		UserUpdate = reportService.sumUserUpdate(searchConds);
		PayUserUpdate = reportService.sumPayUserUpdate(searchConds);
		Pay2UserUpdate = reportService.sumPay2UserUpdate(searchConds);
		
	}
	
	public void doExport() throws Exception{
		Map<String, Object> _searchConds = new HashMap<String,Object>();
		_searchConds.putAll(searchConds);
		_searchConds.remove("_startRow");
		_searchConds.remove("_endRow");
		overviewList = reportService.queryUserOverviewRegisterBasicMV(_searchConds,true);
		Map beans = new HashMap();
		beans.put("excelList", overviewList);
		doExcel(beans, "/WEB-INF/resources/template/userOverview.xls");
		
	}
	
	public Map<String, Object> getSearchConds() {
		return searchConds;
	}
	public void setSearchConds(Map<String, Object> searchConds) {
		this.searchConds = searchConds;
	}
	public List<UserRegisterBasicMV> getOverviewList() {
		return overviewList;
	}
	public void setOverviewList(List<UserRegisterBasicMV> overviewList) {
		this.overviewList = overviewList;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public Long getUserUpdate() {
		return UserUpdate;
	}

	public void setUserUpdate(Long userUpdate) {
		UserUpdate = userUpdate;
	}

	public Long getPayUserUpdate() {
		return PayUserUpdate;
	}

	public void setPayUserUpdate(Long payUserUpdate) {
		PayUserUpdate = payUserUpdate;
	}

	public Long getPay2UserUpdate() {
		return Pay2UserUpdate;
	}

	public void setPay2UserUpdate(Long pay2UserUpdate) {
		Pay2UserUpdate = pay2UserUpdate;
	}

	public Long getAllUserUpdate() {
		return allUserUpdate;
	}

	public void setAllUserUpdate(Long allUserUpdate) {
		this.allUserUpdate = allUserUpdate;
	}

	public Long getAllPayUserUpdate() {
		return allPayUserUpdate;
	}

	public void setAllPayUserUpdate(Long allPayUserUpdate) {
		this.allPayUserUpdate = allPayUserUpdate;
	}

	public Long getAllPay2UserUpdate() {
		return allPay2UserUpdate;
	}

	public void setAllPay2UserUpdate(Long allPay2UserUpdate) {
		this.allPay2UserUpdate = allPay2UserUpdate;
	}

	public String getAllPayUserUpdatePer() {
		return allPayUserUpdatePer;
	}

	public void setAllPayUserUpdatePer(String allPayUserUpdatePer) {
		this.allPayUserUpdatePer = allPayUserUpdatePer;
	}

	public String getAllPay2UserUpdatePer() {
		return allPay2UserUpdatePer;
	}

	public void setAllPay2UserUpdatePer(String allPay2UserUpdatePer) {
		this.allPay2UserUpdatePer = allPay2UserUpdatePer;
	}


}
