/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.money.dao;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.money.CashRecharge;
/**
 * CashRechargeDAO,持久层类 用于CashRecharge 表的CRUD.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class CashRechargeDAO extends BaseIbatisDAO{	
	/**
	 * 持久化对象
	 * @return
	 */
	public Long insert(CashRecharge cashRecharge) {
		return (Long)super.insert("CASH_RECHARGE.insert", cashRecharge);
	}
	public CashRecharge findCashRechargeByPrimaryKey(Long cashRechargeId) {
		Object result = super.queryForObject("CASH_RECHARGE.findFincRechargeByPrimaryKey", cashRechargeId);
		
		if(result != null)
			return (CashRecharge) result;
		else
			return null;
	}
	
	public CashRecharge findCashRechargeBySerial(String serial) {
		Object result = super.queryForObject("CASH_RECHARGE.findCashRechargeBySerial", serial);
		
		if(result != null)
			return (CashRecharge) result;
		else
			return null;
	}

	public int updateStatusBySerial(String status, String serial) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("status", status);
		paramMap.put("serial", serial);
		return super.update("CASH_RECHARGE.updateStatusBySerial", paramMap);
	}

	public int updateByPrimaryKey(CashRecharge CashRecharge) {
		return super.update("CASH_RECHARGE.updateCashRechargeByPrimaryKey", CashRecharge);
	}
}
