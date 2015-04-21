package com.lvmama.report.service;

import java.util.Date;
import java.util.List;

import com.lvmama.report.po.MoneyUserDetailExportRefund;


/**
 * 现金账户支付、退费明细导出EXCEL
 * 
 * @author yanggan
 * 
 */
public interface MoneyUserDetailExportService {

	/**
	 * 现金账户支付明细导出
	 * 
	 * @param visitStartDate 
	 * 						游玩时间开始日期
	 * @param visitEndDate 
	 * 						游玩时间结束日期
	 * @param moneyChangeStartDate	
	 * 						支付时间开始日期
	 * @param moneyChangeEndDate	
	 * 						支付时间结束日期
	 * @return	现在账户支付明细
	 */
	public List payExport(Date visitStartDate, Date visitEndDate, Date moneyChangeStartDate, Date moneyChangeEndDate);

	/**
	 * 现金账户退费明细导出
	 * 
	 * @param visitStartDate 
	 * 						游玩时间开始日期
	 * @param visitEndDate 
	 * 						游玩时间结束日期
	 * @param moneyChangeStartDate	
	 * 						退款时间开始日期
	 * @param moneyChangeEndDate	
	 * 						退款时间结束日期
	 * @return 现在账户退费明细
	 */
	public List<MoneyUserDetailExportRefund> refundExport(Date visitStartDate, Date visitEndDate, Date moneyChangeStartDate, Date moneyChangeEndDate);

}
