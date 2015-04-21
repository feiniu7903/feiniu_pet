package com.lvmama.report.service;

import java.util.Date;

import com.lvmama.report.vo.CashBonusReportVO;

/**
 * 奖金账户报表服务
 * 
 * @author taiqichao
 * 
 */
public interface CashBonusReportService {
	
	
	/**
	 * 执行统计存储过程
	 */
	public void execSPFinancialReport();

	/**
	 * 奖金账户报表
	 * 
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 */
	public CashBonusReportVO getCashBonusDayReport(Date startDate, Date endDate);

}
