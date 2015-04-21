package com.lvmama.finance.settlement.ibatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.vo.Currency;
import com.lvmama.finance.base.FinanceContext;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.PageDao;
import com.lvmama.finance.settlement.ibatis.po.FincForegifts;
import com.lvmama.finance.settlement.ibatis.vo.SimpleSupplier;

/**
 * 供应商押金&担保函
 * 
 * @author yanggan
 * 
 */
@Repository
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FincForegiftsDAO extends PageDao {

	/**
	 * 查询供应商押金
	 * 
	 * @param page
	 * @return
	 */

	public Page<SimpleSupplier> searchForegifts() {
		return queryForPageFin("FINCFOREGIFTS.searchForegifts", FinanceContext.getPageSearchContext().getContext());
	}

	/**
	 * 查询供应商担保函
	 * 
	 * @param page
	 * @return
	 */

	public Page<SimpleSupplier> searchGuaranteeLimit() {
		return queryForPageFin("FINCFOREGIFTS.searchGuaranteeLimit", FinanceContext.getPageSearchContext().getContext());
	}

	/**
	 * 查询流水记录
	 * 
	 * @param type
	 *            类型 押金/担保函
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	public List<FincForegifts> searchFincForegifts(String type, Integer supplierId) {
		Map map = new HashMap();
		map.put("type", type);
		map.put("supplierId", supplierId);
		return queryForList("FINCFOREGIFTS.searchFincForegifts", map);
	}

	/**
	 * 更新供应商押金金额
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @param amount
	 *            金额
	 */
	public void updateSupplierForegiftsBal(Long supplierId, Double amount) {
		Map map = new HashMap();
		map.put("amount", amount);
		map.put("supplierId", supplierId);
		update("FINCFOREGIFTS.updateSupplierForegiftsBal", map);
	}

	/**
	 * 更新供应商担保函额度
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @param amount
	 *            金额
	 */
	public void updateSupplierGuaranteeLimit(Long supplierId, Double amount) {
		Map map = new HashMap();
		map.put("amount", amount);
		map.put("supplierId", supplierId);
		update("FINCFOREGIFTS.updateSupplierGuaranteeLimit", map);
	}

	/**
	 * 新增流水记录
	 * 
	 * @param fincForegifts
	 *            押金信息
	 */
	public void insertFincForegifts(FincForegifts fincForegifts) {
		insert("FINCFOREGIFTS.insertFincForegifts", fincForegifts);
	}

	/**
	 * 查询押金、担保函金额
	 * 
	 * @param type
	 *            类型
	 * @param supplierId
	 *            供应商ID
	 */
	public Double searchAmount(String type, Long supplierId) {
		Map map = new HashMap();
		map.put("type", type);
		map.put("supplierId", supplierId);
		return (Double) this.queryForObject("FINCFOREGIFTS.searchAmount",map);
	}
	/**
	 * 查询供应商押金预警
	 * 
	 * @return
	 */
	public Page<SimpleSupplier> searchForegiftsAlert() {
		return queryForPageFin("FINCFOREGIFTS.searchForegiftsAlert", FinanceContext.getPageSearchContext().getContext());
	}
	
	/**
	 * 查询所有币种
	 * 
	 */
	public List<Currency> searchAllCurrency() {
		return queryForList("FINCADVANCEDEPOSITS.searchAllCurrency");
	}
	
	/**
	 * 查询供应商的预存款余额
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	public SimpleSupplier searchSupplier(Long supplierId) {
		return (SimpleSupplier)queryForObject("FINCFOREGIFTS.searchSupplierCash",supplierId);
	}
	/**
	 * 更新供应商押金币种
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
		update("FINCFOREGIFTS.updateSupplierCurrency", map);
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
		update("FINCFOREGIFTS.updateSupplierForecurrency", map);
	}

}