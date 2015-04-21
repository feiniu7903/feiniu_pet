package com.lvmama.report.web.moneyUser;

import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Label;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.report.po.MoneyUserChangeTV;
import com.lvmama.report.service.MoneyUserService;
import com.lvmama.report.web.BaseAction;

/**
 * 现金账户报表
 * 
 * @author zhaojindong
 * 
 */
public class MoneyUserAction extends BaseAction {
	/**
	 * 借方发生总额
	 */
	private Label debitAmount;
	/**
	 * 贷方发生总额
	 */
	private Label creditAmount;
	/**
	 * 余额
	 */
	private Label balanceAmount;

	/**
	 * 现金账户变动记录列表
	 */
	private List<MoneyUserChangeTV> moneyUserChangeList;

	/**
	 * 查询参数 startDate： 起始日期 endDate：结束日期 type： 业务类型
	 */
	private Map<String, Object> paramMap = new HashMap<String, Object>();;

	private MoneyUserService moneyUserService;

	private void doQuery() {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		
		// 查询借方发生总额
		Long tmp1 = moneyUserService.sumMoneyUserChangeTVDebitAmount(paramMap,false);
		if(tmp1 == null){
			tmp1 = new Long(0);
		}
		debitAmount.setValue(nf.format(tmp1 / 100.00) + "元");

		// 查询贷方发生总额
		Long tmp2 = moneyUserService.sumMoneyUserChangeTVCreditAmount(paramMap);
		if(tmp2 == null){
			tmp2 = new Long(0);
		}
		creditAmount.setValue(nf.format((0 - tmp2) / 100.00) + "元");

		// 发生总额
		Long tmp3 = tmp1 + tmp2;
		balanceAmount.setValue(nf.format(tmp3 / 100.00) + "元");

		// 查询列表
		paramMap = initialPageInfoByMap(moneyUserService.countMoneyUserChangeTV(paramMap), paramMap);
		moneyUserChangeList = moneyUserService.queryMoneyUserChangeTV(paramMap);
	}

	/**
	 * 查询现金账户变动日报表
	 * 
	 * @return
	 */
	public void queryMoneyUserChangesTV() {
		// 条件判断
		Date startDate = (Date) paramMap.get("startDate");
		Date endDate = (Date) paramMap.get("endDate");
		if (startDate != null && endDate != null && startDate.after(endDate)) {
			return;
		}

		doQuery();
	}

	/**
	 * 导出查询结果
	 * 
	 * @throws Exception
	 */
	public void doExport() throws Exception {
		// 条件判断
		Date startDate = (Date) paramMap.get("startDate");
		Date endDate = (Date) paramMap.get("endDate");
		paramMap.remove("_startRow");
		paramMap.remove("_endRow");
		if (startDate != null && endDate != null && startDate.after(endDate)) {
			return;
		}

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		
		// 查询借方发生总额
		Long tmp1 = moneyUserService.sumMoneyUserChangeTVDebitAmount(paramMap,true);
		if(tmp1 == null){
			tmp1 = new Long(0);
		}
		debitAmount.setValue(nf.format(tmp1 / 100.00) + "元");

		// 查询贷方发生总额
		Long tmp2 = moneyUserService.sumMoneyUserChangeTVCreditAmount(paramMap);
		if(tmp2 == null){
			tmp2 = new Long(0);
		}
		creditAmount.setValue(nf.format((0 - tmp2) / 100.00) + "元");

		// 发生总额
		Long tmp3 = tmp1 + tmp2;
		balanceAmount.setValue(nf.format(tmp3 / 100.00) + "元");

		// 查询列表
		moneyUserChangeList = moneyUserService.queryMoneyUserChangeTV(paramMap);

		Map<String, Object> beans = new HashMap();
		beans.put("debitAmount", debitAmount.getValue());
		beans.put("creditAmount", creditAmount.getValue());
		beans.put("balanceAmount", balanceAmount.getValue());
		beans.put("excelList", moneyUserChangeList);
		doExcel(beans, "/WEB-INF/resources/template/moneyUserChangeByDayTemplate.xls", "report_money_user_" + DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss"));

	}

	public List<MoneyUserChangeTV> getMoneyUserChangeList() {
		return moneyUserChangeList;
	}

	public void setMoneyUserChangeList(List<MoneyUserChangeTV> moneyUserChangeList) {
		this.moneyUserChangeList = moneyUserChangeList;
	}

	public MoneyUserService getMoneyUserService() {
		return moneyUserService;
	}

	public void setMoneyUserService(MoneyUserService moneyUserService) {
		this.moneyUserService = moneyUserService;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

}
