/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.money.dao;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.money.CashChange;
import com.lvmama.comm.vo.CashAccountChangeLogVO;
import com.lvmama.comm.vo.Constant.MoneyAccountChangeType;
/**
 * CashChangeDAO,持久层类 用于CashChange 表的CRUD.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class CashChangeDAO extends BaseIbatisDAO{	
	/**
	 * 持久化对象
	 * @return
	 */
	public Long insert(CashChange cashChange) {
		return (Long)super.insert("CASH_CHANGE.insert", cashChange);
	}
	
	/**
	 * 记录余额变更.
	 *
	 * @param cashAccountId
	 *            用户ID
	 * @param amount
	 *            变更金额，以分为单位
	 * @param comeFrom
	 *            来源
	 * @param businessId
	 *            业务ID
	 */
	public void balanceChange(final Long cashAccountId, final Long amount,
			final String comeFrom, final String businessId) {
		final Map<Object, Object> map = new Hashtable<Object, Object>();
		map.put("cashAccountId", cashAccountId);
		map.put("amount", amount);
		map.put("comeFrom", comeFrom);
		map.put("businessId", businessId);
		super.insert("CASH_CHANGE.balanceChange", map);
	}
	
	/**
	 * 查询现金账户变更日志计数.
	 * 
	 * @param userId
	 *            用户ID
	 * @param moneyAccountChangeType
	 *            现金账户变更日志类型
	 * @param beginIndex
	 *            起始索引（包括）
	 * @param endIndex
	 *            结束索引（包括）
	 * @return 现金账户变更日志计数
	 */
	public Long queryMoneyAccountChangeLogCount(final Long cashAccountId,final String userNo,
			final MoneyAccountChangeType moneyAccountChangeType,final String payFrom,final String bonusRefundment){
		final Map<String, Object> map = new Hashtable<String, Object>();
		if(cashAccountId!=null){
			map.put("cashAccountId", cashAccountId);
		}
		if(StringUtils.isNotBlank(userNo)){
			map.put("userNo", userNo);
		}
		
		if(StringUtils.isNotBlank(payFrom)){
			map.put("payFrom", payFrom);
		}
		if(StringUtils.isNotBlank(bonusRefundment)){
			map.put("bonusRefundment", bonusRefundment);
		}
		Long count = Long.valueOf(0);
		if (moneyAccountChangeType.getCode().equals(
				MoneyAccountChangeType.ALL.getCode())) {
			count = (Long) super
					.queryForObject(
							"CASH_CHANGE.queryMoneyAccountChangeLogCountAll",
							map);
		} else {
			map.put("moneyAccountChangeType", moneyAccountChangeType.getCode());
			count = (Long) super.queryForObject(
					"CASH_CHANGE.queryMoneyAccountChangeLogCount",
					map);
		}
		return count;
	}
	
	/**
	 * 查询现金账户变更日志.
	 * 
	 * @param userId
	 *            用户ID
	 * @param moneyAccountChangeType
	 *            现金账户变更日志类型
	 * @param beginIndex
	 *            起始索引（包括）
	 * @param endIndex
	 *            结束索引（包括）
	 * @return 现金账户变更日志列表
	 */
	@SuppressWarnings("unchecked")
	public List<CashAccountChangeLogVO> queryMoneyAccountChangeLog(
			final Long cashAccountId,final String userNo,
			final MoneyAccountChangeType moneyAccountChangeType,
			final Integer beginIndex, final Integer endIndex, final String payFrom,final String bonusRefundment) {
		final Map<String, Object> map = new Hashtable<String, Object>();
		if(cashAccountId!=null){
			map.put("cashAccountId", cashAccountId);
		}
		if(StringUtils.isNotBlank(userNo)){
			map.put("userNo", userNo);
		}
		
		if(StringUtils.isNotBlank(payFrom)){
			map.put("payFrom", payFrom);
		}
		if(StringUtils.isNotBlank(bonusRefundment)){
			map.put("bonusRefundment", bonusRefundment);
		}
		
		List<CashAccountChangeLogVO> list = new ArrayList<CashAccountChangeLogVO>();
		if (moneyAccountChangeType.getCode().equals(
				MoneyAccountChangeType.ALL.getCode())) {
			map.put("beginIndex", beginIndex.toString());
			map.put("endIndex", endIndex.toString());
			list = super.queryForList(
					"CASH_CHANGE.queryMoneyAccountChangeLogAll",
					map);
		} else {
			map.put("moneyAccountChangeType", moneyAccountChangeType.getCode());
			map.put("beginIndex", beginIndex.toString());
			map.put("endIndex", endIndex.toString());
			list = super.queryForList(
					"CASH_CHANGE.queryMoneyAccountChangeLog", map);
		}
		return list;
	}
}
