package com.lvmama.report.service.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.report.dao.MoneyUserDAO;
import com.lvmama.report.po.MoneyUserChangeTV;
import com.lvmama.report.service.MoneyUserService;

public class MoneyUserServiceImpl implements MoneyUserService {

	private MoneyUserDAO moneyUserDAO;

	@Override
	public void dataMaker() {
		//现金提现
		moneyUserDAO.insertDrawData();
		//现金支付
		moneyUserDAO.insertPayData();
		//现金退款
		moneyUserDAO.insertRefundData();
		//现金充值
		moneyUserDAO.insertRechargeData();
		
		//奖金支付
		moneyUserDAO.insertBonusPayData();
		//奖金退款
		moneyUserDAO.insertBonusRefundData();
		//奖金返现
		moneyUserDAO.insertBonusReturnData();
	}

	public List<MoneyUserChangeTV> queryMoneyUserChangeTV(Map<String, Object> params) {
		return moneyUserDAO.queryMoneyUserChangeTV(params);
	}

	public Long sumMoneyUserChangeTVDebitAmount(Map<String, Object> params,boolean isForReportExport) {
		return moneyUserDAO.sumMoneyUserChangeTVDebitAmount(params,isForReportExport);
	}

	public Long sumMoneyUserChangeTVCreditAmount(Map<String, Object> params) {
		return moneyUserDAO.sumMoneyUserChangeTVCreditAmount(params);
	}

	public Long sumMoneyUserChangeTVBalanceAmount(Map<String, Object> params) {
		return moneyUserDAO.sumMoneyUserChangeTVBalanceAmount(params);
	}

	public Long countMoneyUserChangeTV(Map<String, Object> params) {
		return moneyUserDAO.countMoneyUserChangeTV(params);
	}

	public MoneyUserDAO getMoneyUserDAO() {
		return moneyUserDAO;
	}

	public void setMoneyUserDAO(MoneyUserDAO moneyUserDAO) {
		this.moneyUserDAO = moneyUserDAO;
	}

}
