package com.lvmama.report.web.moneyUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.time.DateFormatUtils;
import com.lvmama.report.service.CashBonusReportService;
import com.lvmama.report.vo.CashBonusReportVO;
import com.lvmama.report.web.BaseAction;

/**
 * 奖金账户报表
 * 
 * @author taiqichao
 * 
 */
public class CashBonusAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private List<CashBonusReportVO> cashBonusReportVOList;
	private CashBonusReportService cashBonusReportService;
	
	/**
	 * 查询参数
	 */
	private Map<String, Object> paramMap = new HashMap<String, Object>();
	

	public void doQuery() {
		Date startDate = (Date) paramMap.get("startDate");
		Date endDate = (Date) paramMap.get("endDate");
		if(startDate!=null&&endDate!=null){
			cashBonusReportVOList=new ArrayList<CashBonusReportVO>();
			cashBonusReportVOList.add(cashBonusReportService.getCashBonusDayReport(startDate, endDate));
		}
	}


	/**
	 * 导出查询结果
	 * 
	 * @throws Exception
	 */
	public void doExport() throws Exception {
		Date startDate = (Date) paramMap.get("startDate");
		Date endDate = (Date) paramMap.get("endDate");
		if (startDate != null && endDate != null ) {
			cashBonusReportVOList=new ArrayList<CashBonusReportVO>();
			cashBonusReportVOList.add(cashBonusReportService.getCashBonusDayReport(startDate, endDate));
			String templatePath = "/WEB-INF/resources/template/cash_bonus_day_report.xls";
			String outputFile="cash_bonus_day_report_"+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") +".xls";
			Map<String,Object> beans = new HashMap<String,Object>();
			beans.put("cashBonusReportVO", cashBonusReportVOList.get(0));
			doExcel(beans, templatePath, outputFile);
		}
	}

	

	public List<CashBonusReportVO> getCashBonusReportVOList() {
		return cashBonusReportVOList;
	}


	public void setCashBonusReportVOList(
			List<CashBonusReportVO> cashBonusReportVOList) {
		this.cashBonusReportVOList = cashBonusReportVOList;
	}


	public CashBonusReportService getCashBonusReportService() {
		return cashBonusReportService;
	}


	public void setCashBonusReportService(
			CashBonusReportService cashBonusReportService) {
		this.cashBonusReportService = cashBonusReportService;
	}


	public Map<String, Object> getParamMap() {
		return paramMap;
	}


	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	
	
	

}
