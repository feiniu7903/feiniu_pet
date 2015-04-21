/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.money.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.money.CashRefundment;
/**
 * CashRefundmentDAO,持久层类 用于CashRefundment 表的CRUD.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class CashRefundmentDAO extends BaseIbatisDAO{	
	/**
	 * 持久化对象
	 * @return
	 */
	public Long insert(CashRefundment cashRefundment) {
		return (Long)super.insert("CASH_REFUNDMENT.insert", cashRefundment);
	}
}
