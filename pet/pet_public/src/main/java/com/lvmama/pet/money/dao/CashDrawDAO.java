/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.money.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.money.CashDraw;
/**
 * CashDrawDAO,持久层类 用于CashDraw 表的CRUD.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class CashDrawDAO extends BaseIbatisDAO{	
	/**
	 * 持久化对象
	 * @return
	 */
	public Long insert(CashDraw cashDraw) {
		return (Long)super.insert("CASH_DRAW.insert", cashDraw);
	}
	public int updateByPrimaryKey(CashDraw cashDraw) {
		return super.update("CASH_DRAW.updateFincCashDrawByPrimaryKey", cashDraw);
	}
	
	public CashDraw findCashDrawBySerial(String serial)
	{
		Object result = super.queryForObject("CASH_DRAW.findFincCashDrawBySerial", serial);
		
		if(result != null)
			return (CashDraw) result;
		else
			return null;
	}
	
	public CashDraw findCashDrawByAlipay2bankFile(String alipay2bankFile)
	{
		Object result = super.queryForObject("CASH_DRAW.findFincCashDrawByAlipay2bankFile", alipay2bankFile);
		
		if(result != null)
			return (CashDraw) result;
		else
			return null;
	}

	public CashDraw selectByPrimaryKey(Long cashDrawId) 
	{
		Object result = super.queryForObject("CASH_DRAW.selectByPrimaryKey", cashDrawId);
		
		if(result != null)
			return (CashDraw) result;
		else
			return null;
	}

	/**
	 * 根据提现单ID查询{@link fincCashDraw}.
	 * 
	 * @param moneyDrawId
	 *            提现单ID
	 * 
	 * @return {@link fincCashDraw}
	 */
	public CashDraw findCashDrawByMoneyDrawId(final Long moneyDrawId) {
		return (CashDraw) super.queryForObject(
				"CASH_DRAW.findFincCashDrawByMoneyDrawId", moneyDrawId);
	}
}
