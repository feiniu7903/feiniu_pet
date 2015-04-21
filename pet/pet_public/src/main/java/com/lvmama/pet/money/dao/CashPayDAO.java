/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.money.dao;

import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.money.CashPay;
/**
 * CashPayDAO,持久层类 用于CashPay 表的CRUD.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class CashPayDAO extends BaseIbatisDAO{	
	/**
	 * 持久化对象
	 * @return
	 */
	public Long insert(CashPay cashPay) {
		return (Long)super.insert("CASH_PAY.insert", cashPay);
	}
	public int updateByParamMap(Map paramMap) {
		return super.update("", paramMap);
	}

	public int updateByPrimaryKey(CashPay cashPay) {
		return super.update("CASH_PAY.updateFincCashPayByPrimaryKey", cashPay);
	}
	
	public CashPay findFincCashPayByPrimaryKey(Long cashPayId)
	{
		Object result = super.queryForObject("CASH_PAY.selectFincCashPayByPrimaryKey", cashPayId);
		
		if(result != null)
			return (CashPay) result;
		else
			return null;
	}
	
	public CashPay findCashPayByOutTradeNo(String outTradeNo)
	{
		Object result = super.queryForObject("CASH_PAY.findCashPayByOutTradeNo", outTradeNo);
		
		if(result != null)
			return (CashPay) result;
		else
			return null;
	}
	
	
	
	/**
	 * 查询现金支付次数
	 * @param paramMap 查询参数
	 * @return 支付记录数
	 */
	public Long getCashPayCount(Map<String,Object> paramMap){
		return (Long) super.queryForObject("CASH_PAY.getCashPayCount",paramMap);
	}
	
	
	
}
