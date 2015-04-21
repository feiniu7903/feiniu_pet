package com.lvmama.report.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.report.dao.CashBonusDayReportDAO;
import com.lvmama.report.po.CashBonusDayReport;
import com.lvmama.report.service.CashBonusReportService;
import com.lvmama.report.vo.CashBonusReportVO;

public class CashBonusReportServiceImpl implements CashBonusReportService {
	
	private CashBonusDayReportDAO cashBonusDayReportDAO;

	public CashBonusReportVO getCashBonusDayReport(Date startDate, Date endDate) {
		CashBonusReportVO cashBonusReportVO=new CashBonusReportVO();
		cashBonusReportVO.setBeginAmount(cashBonusDayReportDAO.getTotalSumCash(DateUtil.dsDay_Date(startDate, -1)));
		cashBonusReportVO.setEndAmount(cashBonusDayReportDAO.getTotalSumCash(endDate));
		String startDateStr=DateUtil.formatDate(startDate, "yyyy-MM-dd");
		String endDateStr=DateUtil.formatDate(endDate, "yyyy-MM-dd");
		Map<String, BigDecimal> addedAmountMap=cashBonusDayReportDAO.getTotalAddedBonus(startDateStr, endDateStr);
		Map<String, Long> addedAmountMapRe=new HashMap<String, Long>();
		Long totalAddedAmount=0L;
		for (Map.Entry<String, BigDecimal> entry : addedAmountMap.entrySet()) {
			addedAmountMapRe.put(entry.getKey(), entry.getValue().longValue());
			totalAddedAmount+=entry.getValue().longValue();
		}
		cashBonusReportVO.setAddedAmountMap(addedAmountMapRe);
		cashBonusReportVO.setTotalAddedAmount(totalAddedAmount);
		cashBonusReportVO.setConsumeAmount(cashBonusDayReportDAO.getTotalPayBonus(startDateStr, endDateStr));
		return cashBonusReportVO;
	}

	

	@Override
	public void execSPFinancialReport() {
		//判断是否已经统计过
		Date currentDate=new Date();
		Long counts=cashBonusDayReportDAO.getCounts(currentDate);
		if(counts>0){
			//删除先前已有的当天统计
			cashBonusDayReportDAO.delete(currentDate);
		}
		//插入奖金账户财务报表
		Long totalBonusBalance=cashBonusDayReportDAO.sumTotalBonusBalance();
		CashBonusDayReport cashBonusDayReport=new CashBonusDayReport();
		cashBonusDayReport.setCreateDate(DateUtil.formatDate(currentDate, "yyyy-MM-dd"));
		cashBonusDayReport.setTotalSumCash(totalBonusBalance);
		cashBonusDayReportDAO.insertData(cashBonusDayReport);
	}

	public void setCashBonusDayReportDAO(CashBonusDayReportDAO cashBonusDayReportDAO) {
		this.cashBonusDayReportDAO = cashBonusDayReportDAO;
	}
	

}
