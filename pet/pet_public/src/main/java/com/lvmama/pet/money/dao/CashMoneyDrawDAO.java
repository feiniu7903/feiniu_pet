/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.money.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.money.CashMoneyDraw;
/**
 * CashMoneyDrawDAO,持久层类 用于CashMoneyDraw 表的CRUD.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class CashMoneyDrawDAO extends BaseIbatisDAO{	
	/**
	 * 持久化对象
	 * @return
	 */
	public Long insert(CashMoneyDraw cashMoneyDraw) {
		return (Long)super.insert("CASH_MONEY_DRAW.insert", cashMoneyDraw);
	}
	public int updateByParamMap(Map<String,Object> paramMap) {
		return super.update("CASH_MONEY_DRAW.updateByParamMap", paramMap);
	}

	public int updateAuditStatusByMoneyDrawId(Long moneyDrawId,
			String auditStatus) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("moneyDrawId", moneyDrawId);
		paramMap.put("auditStatus", auditStatus);
		return super.update("CASH_MONEY_DRAW.updateAuditStatusByMoneyDrawId", paramMap);
	}

	public int updateByPrimaryKey(CashMoneyDraw cashMoneyDraw) {
		return super.update("CASH_MONEY_DRAW.updateByPrimaryKey", cashMoneyDraw);
	}

	public CashMoneyDraw selectByPrimaryKey(Long moneyDrawId) {
		Object result = super.queryForObject("CASH_MONEY_DRAW.selectByPrimaryKey", moneyDrawId);
		
		if(result != null)
			return (CashMoneyDraw) result;
		else
			return null;
	}

	@SuppressWarnings("rawtypes")
	public List<CashMoneyDraw> queryMoneyDrawTasks(Map<String,Object> paramMap) {
		return super.queryForList("CASH_MONEY_DRAW.queryFincMoneyDrawTasks", paramMap);
	}

	public Long queryMoneyDrawTasksCount(Map<String,Object> paramMap) {
		return (Long)super.queryForObject("CASH_MONEY_DRAW.queryFincMoneyDrawTasksCount",paramMap);

	}
	@SuppressWarnings("rawtypes")
	public List queryMoneyDrawHistory(Map<String,Object> paramMap) {
		return super.queryForList("CASH_MONEY_DRAW.queryMoneyDrawHistory", paramMap);
	}

	public Long queryMoneyDrawHistoryCount(Map<String,Object> paramMap) {
		return (Long)super.queryForObject("CASH_MONEY_DRAW.queryMoneyDrawHistoryCount", paramMap);
	}
	
	/**
	 * 查询提现记录.
	 * @param cashAccountId
	 * @param userNo
	 * @param userMobile
	 * @param bankAccountName
	 * @param beginIndex
	 * @param endIndex
	 * @param status
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @return 提现记录列表
	 */
	@SuppressWarnings("unchecked")
	public List<CashMoneyDraw> queryMoneyDraw(final Long cashAccountId,final String userNo,
			final String userMobile,final String bankAccountName,
			final Integer beginIndex, final Integer endIndex,
			final String status, final Date createTimeStart,
			final Date createTimeEnd) {
		final Map<String, Object> map = new Hashtable<String, Object>();
		if (cashAccountId!=null) {
			map.put("cashAccountId", cashAccountId);
		}
		if(StringUtils.isNotBlank(userNo)){
			map.put("userNo", userNo);
		}
		map.put("beginIndex", beginIndex.toString());
		map.put("endIndex", endIndex.toString());
		if (StringUtils.isNotBlank(status)) {
			map.put("status", status);
		}
		if (createTimeStart!=null) {
			map.put("createTimeStart", createTimeStart);
		}
		if (createTimeEnd!=null) {
			map.put("createTimeEnd", createTimeEnd);
		}
		if(StringUtils.isNotBlank(userMobile)){
			map.put("userMobile", userMobile);
		}
		if(StringUtils.isNotBlank(bankAccountName)){
			map.put("bankAccountName", bankAccountName);
		}
		return super.queryForList(
				"CASH_MONEY_DRAW.queryMoneyDraw", map);
	}
	/**
	 * 查询提现记录计数.
	 * @param cashAccountId
	 * @param userNo
	 * @param userMobile
	 * @param bankAccountName
	 * @param status
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @return 查询提现记录计数
	 */
	public Long queryMoneyDrawCount(final Long cashAccountId, final String userNo,
			final String userMobile,final String bankAccountName,
			final String status,
			final Date createTimeStart, final Date createTimeEnd) {
		final Map<String, Object> map = new Hashtable<String, Object>();
		if (cashAccountId!=null) {
			map.put("cashAccountId", cashAccountId);
		}
		if(StringUtils.isNotBlank(userNo)){
			map.put("userNo", userNo);
		}
		if (StringUtils.isNotBlank(status)) {
			map.put("status", status);
		}
		if (createTimeStart!=null) {
			map.put("createTimeStart", createTimeStart);
		}
		if (createTimeEnd!=null) {
			map.put("createTimeEnd", createTimeEnd);
		}
		if(StringUtils.isNotBlank(userMobile)){
			map.put("userMobile", userMobile);
		}
		if(StringUtils.isNotBlank(bankAccountName)){
			map.put("bankAccountName", bankAccountName);
		}
		return (Long) super.queryForObject(
				"CASH_MONEY_DRAW.queryMoneyDrawCount", map);
	}
	
	/**
	 * 提现银行卡账户名申请提现次数
	 * @param bankAccountName 提现银行卡账户名
	 * @return 申请提现次数
	 */
	public Long queryMoneyDrowCountByBankAccount(String bankAccountName){
		if(StringUtils.isBlank(bankAccountName)){
			return 0L;
		}
		final Map<String, Object> map = new Hashtable<String, Object>();
		map.put("bankAccountName", bankAccountName);
		return (Long) super.queryForObject("CASH_MONEY_DRAW.queryMoneyDrowCountByBankAccount", map);
	}
	
	
}
