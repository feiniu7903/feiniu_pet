package com.lvmama.finance.settlement.ibatis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.vo.Currency;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.PageDao;
import com.lvmama.finance.settlement.ibatis.po.OrdSettlementPayment;

@Repository
@SuppressWarnings({ "unchecked" })
public class OrdSettlementPaymentDAO extends PageDao {

	/**
	 * 查询供应商的预存款结算记录
	 * @param supplierId
	 * @return
	 */
	public List<OrdSettlementPayment> searchAdvancedepositsPayment(Long supplierId) {
		return queryForList("ORDSETTLEMENTPAYMENT.searchAdvancedepositsPayment", supplierId);
	}
	
	/**
	 * 新增供应商的打款记录
	 * @param orp
	 */
	public void insertPayment(OrdSettlementPayment orp){
		this.insert("ORDSETTLEMENTPAYMENT.insertPayment",orp);
	}
	
	/**
	 * 根据供应商名称、支付平台查询打款记录
	 * @param map
	 * @return
	 */
	public Page<OrdSettlementPayment> getOrdSettlementPayments(Map<String, Object> map){
		return queryForPageFin("ORDSETTLEMENTPAYMENT.getOrdSettlementPaymentHistory",map);
	}

	/**
	 * 查询所有币种
	 * 
	 */
	public List<Currency> searchAllCurrency() {
		return queryForList("ORDSETTLEMENTPAYMENT.searchAllCurrency");
	}
	
	/**
	 * 根据供应商ID查询最后一条打款记录的币种
	 * @param supplierId
	 * @return
	 */
	public String searchLastCurrency(Long supplierId){
		return (String) queryForObject("ORDSETTLEMENTPAYMENT.searchLastCurrency",supplierId);
	}
}