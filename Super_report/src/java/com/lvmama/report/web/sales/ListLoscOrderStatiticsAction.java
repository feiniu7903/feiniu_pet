package com.lvmama.report.web.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.report.po.LoscOrderStatisticsMV;
import com.lvmama.report.service.ReportService;
import com.lvmama.report.web.BaseAction;

/**
 * 
 * @author ganyingwen
 *
 */
@SuppressWarnings("serial")
public class ListLoscOrderStatiticsAction extends BaseAction{
	private ReportService reportService;
	private Map<String, Object> parameters = new HashMap<String, Object>();
	private List<LoscOrderStatisticsMV> statisticsList = new ArrayList<LoscOrderStatisticsMV>();
	private boolean isDisabled = true;
	
	/**
	 * 查询
	 */
	public void search() { 
		statisticsList.clear();
		Long totalRowCount = reportService.loscOrderStatisticsCount(parameters);
		initialPageInfoByMap(totalRowCount, parameters);
		statisticsList = reportService.queryLoscOrderStatisticsList(parameters,false);
		if (statisticsList.size() > 0) {
			isDisabled = false;
		} else {
			isDisabled = true;
		}
	}
	
	/**
	 * 导出Excel
	 * @throws Exception Exception
	 */
	public void doExport() throws Exception {
		Map<String, Object> parameTemp = parameters;
		parameTemp.remove("_startRow");
		parameTemp.remove("_endRow");
		List<LoscOrderStatisticsMV> list = reportService.queryLoscOrderStatisticsList(parameters,true);
		parameTemp.put("excelList", list);
		super.doExcel(parameTemp, "/WEB-INF/resources/template/LoscOrderStatisticsTemplate.xls");
	}

	public List<LoscOrderStatisticsMV> getStatisticsList() {
		return statisticsList;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public boolean isDisabled() {
		return isDisabled;
	}

	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}	
}
