package com.lvmama.report.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.report.po.MoneyUserDetailExportRefund;
import com.lvmama.report.po.MoneyUserPayDetail;



/**
 * 现金账户支付、退费明细导出EXCEL
 * 
 * @author yanggan
 * 
 */
public class MoneyUserDetailExportDAO extends BaseIbatisDAO {

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
	 * @return 现在账户支付明细
	 */
	public List<MoneyUserPayDetail> payExport(Date visitStartDate, Date visitEndDate, Date moneyChangeStartDate, Date moneyChangeEndDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("visitStartDate", visitStartDate);
		params.put("visitEndDate", visitEndDate);
		params.put("moneyChangeStartDate", moneyChangeStartDate);
		params.put("moneyChangeEndDate", moneyChangeEndDate);
		return super.queryForList("MONEY_USER_DETAIL_EXPORT.queryMoneyUserPay", params,true);
	}

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
	public List<MoneyUserDetailExportRefund> refundExport(Date visitStartDate, Date visitEndDate, Date moneyChangeStartDate, Date moneyChangeEndDate) {
		Map<String,Date> map = new HashMap<String,Date>();
		map.put("visitStartDate", visitStartDate);
		map.put("visitEndDate", visitEndDate);
		map.put("moneyChangeStartDate", moneyChangeStartDate);
		map.put("moneyChangeEndDate", moneyChangeEndDate);
		return super.queryForList("MONEY_USER_DETAIL_EXPORT.selectRefundDetail",map,true);
	}
}
