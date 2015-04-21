package com.lvmama.report.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.report.po.MoneyUserChangeTV;

public class MoneyUserDAO extends BaseIbatisDAO{
	
	/**
	 * 生成现金账户退款数据
	 */
	public void insertRefundData() {
		super.insert("MONEY_USER_CHANGE_TV.insertRefundData");
	}
	
	/**
	 * 生成奖金账户退款数据
	 */
	public void insertBonusRefundData() {
		super.insert("MONEY_USER_CHANGE_TV.insertBonusRefundData");
	}

	/**
	 * 生成现金账户支付数据
	 */
	public void insertPayData() {
		super.insert("MONEY_USER_CHANGE_TV.insertPayData");
	}
	/**
	 * 生成奖金账户支付数据
	 */
	public void insertBonusPayData() {
		super.insert("MONEY_USER_CHANGE_TV.insertBonusPayData");
	}
	
	/**
	 * 生成奖金账户返现数据
	 */
	public void insertBonusReturnData(){
		super.insert("MONEY_USER_CHANGE_TV.insertBonusReturnData");
	}

	/**
	 * 生成现金账户充值数据
	 */
	public void insertRechargeData() {
		super.insert("MONEY_USER_CHANGE_TV.insertRechargeData");
	}
	

	/**
	 * 生成现金账户提现数据
	 */
	public void insertDrawData() {
		super.insert("MONEY_USER_CHANGE_TV.insertDrawData");
	}
	
	/**
	 * 按条件查询现金账户变动表
	 * @param params
	 * @return
	 */
	public List<MoneyUserChangeTV> queryMoneyUserChangeTV(Map<String, Object> params){
		return super.queryForList("MONEY_USER_CHANGE_TV.queryMoneyUserChangeTV", params);
	}
	
	/**
	 * 按条件统计现金账户变动表记录条数
	 * @param params
	 * @return
	 */
	public Long countMoneyUserChangeTV(Map<String, Object> params){
		return (Long)super.queryForObject("MONEY_USER_CHANGE_TV.countMoneyUserChangeTV", params);
	}
	
	/**
	 * 按条件统计借方发生金额
	 * @param params
	 * @return
	 */
	public Long sumMoneyUserChangeTVDebitAmount(Map<String, Object> params,boolean isForReportExport){
		return (Long)super.queryForObject("MONEY_USER_CHANGE_TV.sumMoneyUserChangeTVDebitAmount", params,isForReportExport);
	}
	/**
	 * 按条件统计贷方发生金额
	 * @param params
	 * @return
	 */
	public Long sumMoneyUserChangeTVCreditAmount(Map<String, Object> params){
		return (Long)super.queryForObject("MONEY_USER_CHANGE_TV.sumMoneyUserChangeTVCreditAmount", params);
	}
	/**
	 * 按条件统计余额
	 * @param params
	 * @return
	 */
	public Long sumMoneyUserChangeTVBalanceAmount(Map<String, Object> params){
		return (Long)super.queryForObject("MONEY_USER_CHANGE_TV.sumMoneyUserChangeTVBalanceAmount", params);
	}
}
