/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.money.dao;

import java.util.Hashtable;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.money.CashFreezeQueue;
/**
 * CashFreezeQueueDAO,持久层类 用于CashFreezeQueue 表的CRUD.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class CashFreezeQueueDAO extends BaseIbatisDAO{	
	/**
	 * 持久化对象
	 * @return
	 */
	public Long insert(CashFreezeQueue cashFreezeQueue) {
		return (Long)super.insert("CASH_FREEZE_QUEUE.insert", cashFreezeQueue);
	}
	
	/**
	 * 冻结提现金额.
	 *
	 * @param userId
	 *            用户ID
	 * @param cashDrawId
	 *            提现记录ID
	 * @param drawAmount
	 *            提现金额，以分为单位
	 * @return <code>true</code>代表冻结提现金额成功，<code>false</code>代表冻结提现金额失败
	 */
	public boolean freezeDrawAmount(final Long cashAccountId, final Long cashDrawId,
			final Long drawAmount) {
		final Map<Object, Object> map = new Hashtable<Object, Object>();
		map.put("cashAccountId", cashAccountId);
		map.put("cashDrawId", cashDrawId);
		map.put("drawAmount", drawAmount);
		super.insert(
				"CASH_FREEZE_QUEUE.freezeDrawAmount", map);
		return true;
	}

	/**
	 * 解冻提现金额.
	 *
	 * @param cashDrawId
	 *            提现记录ID
	 * @return <code>true</code>代表解冻提现金额成功，<code>false</code>代表解冻提现金额失败
	 */
	public boolean unfreezeDrawAmount(final Long cashDrawId) {
		super.insert(
				"CASH_FREEZE_QUEUE.unfreezeDrawAmount", cashDrawId);
		return true;
	}
}
