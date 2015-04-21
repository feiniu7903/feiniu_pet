package com.lvmama.pet.fin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.fin.FinAdvanceDeposit;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.po.fin.SetSettlementPayment;
import com.lvmama.comm.pet.vo.Page;

@Repository
@SuppressWarnings("unchecked")
public class FinanceAdvanceDepositDAO extends BaseIbatisDAO {

	/**
	 * 查询达到预警的预存款信息
	 * @return
	 */
	public Page<FinSupplierMoney> searchAdvanceDepositWarning(Map<String,Object> map) {
		return super.queryForPage("FINANCE_ADVANCE_DEPOSIT.searchAdvanceDepositWarning", map);
	}

	/**
	 * 查询结算记录
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	public List<SetSettlementPayment> searchAdvanceDepositPayment(Long supplierId) {
		return super.queryForList("FINANCE_ADVANCE_DEPOSIT.searchAdvanceDepositPayment", supplierId);
	}

	/**
	 * 查询历史记录
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	public List<FinAdvanceDeposit> searchFinAdvanceDeposit(Map<String,Object> map) {
		return super.queryForList("FINANCE_ADVANCE_DEPOSIT.searchFinAdvanceDeposit", map);
	}
	public Long searchFinAdvanceDepositCount(Map<String,Object> map) {
		return (Long)super.queryForObject("FINANCE_ADVANCE_DEPOSIT.searchFinAdvanceDepositCount", map);
	}
	
	/**
	 * 更新供应商预存款金额
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @param amount
	 *            金额
	 */
	public void updateSupplierAdvanceDepositAmount(Long supplierId, Long amount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", amount);
		map.put("supplierId", supplierId);
		super.update("FINANCE_ADVANCE_DEPOSIT.updateSupplierAdvanceDepositAmount", map);
	}

	/**
	 * 新增流水记录
	 * 
	 * @param fincForegifts
	 *            押金信息
	 */
	public void insertFinadvanceDeposit(FinAdvanceDeposit fad) {
		super.insert("FINANCE_ADVANCE_DEPOSIT.insertFinadvanceDeposit", fad);
	}
	
	/**
	 * 查询供应商的预存款余额
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	public Double searchAmount(Long supplierId,String currency) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		map.put("currency", currency);
		return (Double) super.queryForObject("FINANCE_ADVANCE_DEPOSIT.searchAmount",map);
	}

	
	/**
	 * 查询供应商的预存款余额
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	public FinSupplierMoney searchSupplier(Long supplierId) {
		return (FinSupplierMoney)super.queryForObject("FINANCE_ADVANCE_DEPOSIT.searchSupplier",supplierId);
	}

	
	/**
	 * 更新供应商预存款币种
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @param amount
	 *            金额
	 */
	public void updateSupplierCurrency(Long supplierId, String currency) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("advcurrency", currency);
		map.put("supplierId", supplierId);
		super.update("FINANCE_DEPOSIT.updateSupplierCurrency", map);
	}
	
	/**
	 * 更新供应商押金币种
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @param amount
	 *            金额
	 */
	public void updateSupplierForecurrency(Long supplierId, String currency) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currency", currency);
		map.put("supplierId", supplierId);
		super.update("FINANCE_DEPOSIT.updateSupplierCurrency", map);
	}

}
