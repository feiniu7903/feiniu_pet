package com.lvmama.finance.settlement.ibatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.vo.Currency;
import com.lvmama.finance.base.FinanceContext;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.PageDao;
import com.lvmama.finance.settlement.ibatis.po.FincAdvancedeposits;
import com.lvmama.finance.settlement.ibatis.vo.SimpleSupplier;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Repository
public class FincAdvancedepositsDAO extends PageDao {

	/**
	 * 
	 * @return
	 */

	public Page<SimpleSupplier> searchAdvancedeposits() {
		return queryForPageFin("FINCADVANCEDEPOSITS.searchAdvancedeposits", FinanceContext.getPageSearchContext().getContext());
	}

	/**
	 * 查询历史记录
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	public List<FincAdvancedeposits> searchFincAdvancedeposits(Long supplierId) {
		return queryForList("FINCADVANCEDEPOSITS.searchFincAdvancedeposits", supplierId);
	}

	/**
	 * 更新供应商担保函额度
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @param amount
	 *            金额
	 */
	public void updateSupplierAdvancedepositsBal(Long supplierId, Double amount) {
		Map map = new HashMap();
		map.put("amount", amount);
		map.put("supplierId", supplierId);
		update("FINCADVANCEDEPOSITS.updateSupplierAdvancedepositsBal", map);
	}

	/**
	 * 新增流水记录
	 * 
	 * @param fincForegifts
	 *            押金信息
	 */
	public void insertFincAdvancedeposits(FincAdvancedeposits fincAdvancedeposits) {
		insert("FINCADVANCEDEPOSITS.insertFincAdvancedeposits", fincAdvancedeposits);
	}
	
	/**
	 * 查询供应商的预存款余额
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	public Double searchAmount(Long supplierId,String currency) {
		Map map = new HashMap();
		map.put("supplierId", supplierId);
		map.put("currency", currency);
		return (Double) queryForObject("FINCADVANCEDEPOSITS.searchAmount",map);
	}

	/**
	 * 查询达到预警的预存款信息
	 * @return
	 */
	public Page<SimpleSupplier> searchAdvancedepositsAlert() {
		return queryForPageFin("FINCADVANCEDEPOSITS.searchAdvancedepositsAlert", FinanceContext.getPageSearchContext().getContext());
	}

	
	/**
	 * 查询供应商的预存款余额
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	public SimpleSupplier searchSupplier(Long supplierId) {
		return (SimpleSupplier)queryForObject("FINCADVANCEDEPOSITS.searchSupplier",supplierId);
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
		Map map = new HashMap();
		map.put("currency", currency);
		map.put("supplierId", supplierId);
		update("FINCADVANCEDEPOSITS.updateSupplierCurrency", map);
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
		Map map = new HashMap();
		map.put("currency", currency);
		map.put("supplierId", supplierId);
		update("FINCADVANCEDEPOSITS.updateSupplierForecurrency", map);
	}

	/**
	 * 查询所有币种
	 * 
	 */
	public List<Currency> searchAllCurrency() {
		return queryForList("FINCADVANCEDEPOSITS.searchAllCurrency");
	}
}