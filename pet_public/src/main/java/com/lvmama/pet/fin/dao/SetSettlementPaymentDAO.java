package com.lvmama.pet.fin.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.fin.SetSettlementPayment;
import com.lvmama.comm.pet.vo.Page;

@Repository
@SuppressWarnings({ "unchecked" })
public class SetSettlementPaymentDAO extends BaseIbatisDAO {

	public Page<SetSettlementPayment> search(Map<String,Object> map){
		return super.queryForPage("SET_SETTLEMENT_PAYMENT.search", map);
	}
	/**
	 * 根据供应商名称、支付平台查询打款记录
	 * @param map
	 * @return
	 */
	public Page<SetSettlementPayment> getOrdSettlementPayments(Map<String, Object> map){
		return super.queryForPage("SET_SETTLEMENT_PAYMENT.getSetSettlementPaymentHistory", map);
	}
	/**
	 * 新增打款记录
	 * @param payment
	 */
	public void insertPayment(SetSettlementPayment payment) {
		super.insert("SET_SETTLEMENT_PAYMENT.insertPayment",payment);
	}
}