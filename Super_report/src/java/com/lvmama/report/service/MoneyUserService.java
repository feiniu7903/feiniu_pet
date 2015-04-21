package com.lvmama.report.service;

import java.util.List;
import java.util.Map;

import com.lvmama.report.po.MoneyUserChangeTV;

public interface MoneyUserService {
	/**
	 * 产生现金账户日报表统计数据
	 */
	public void dataMaker();
	
	public List<MoneyUserChangeTV> queryMoneyUserChangeTV(Map<String, Object> params);

	public Long countMoneyUserChangeTV(Map<String, Object> params);

	public Long sumMoneyUserChangeTVDebitAmount(Map<String, Object> params,boolean isForReportExport);

	public Long sumMoneyUserChangeTVCreditAmount(Map<String, Object> params);

	public Long sumMoneyUserChangeTVBalanceAmount(Map<String, Object> params);
}
