package com.lvmama.pet.fin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.fin.FinDeposit;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.vo.Page;

/**
 * 押金管理/预警
 * @author zhangwenjun
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class FinDepositDAO extends BaseIbatisDAO {

	/**
	 * 查询流水记录
	 * 
	 * @param type
	 *            类型 押金/担保函
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	public List<FinDeposit> searchDepositRecord(Map<String,Object> map) {
		return super.queryForList("FIN_DEPOSIT.searchDepositRecord", map);
	}
	
	public Long searchDepositRecordCount(Map<String,Object> map) {
		return (Long)super.queryForObject("FIN_DEPOSIT.searchDepositRecordCount", map);
	}

	/**
	 * 更新供应商押金金额
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @param amount
	 *            金额
	 */
	public void updateSupplierDepositAmount(Long supplierId, Long amount) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("amount", amount);
		map.put("supplierId", supplierId);
		super.update("FIN_DEPOSIT.updateSupplierDepositAmount", map);
	}

	/**
	 * 更新供应商担保函额度
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @param amount
	 *            金额
	 */
	public void updateSupplierGuaranteeLimit(Long supplierId, Long amount) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("amount", amount);
		map.put("supplierId", supplierId);
		super.update("FIN_DEPOSIT.updateSupplierGuaranteeLimit", map);
	}

	/**
	 * 新增流水记录
	 * 
	 * @param fincDeposit
	 *            押金信息
	 */
	public void insertFincDeposit(FinDeposit fincDeposit) {
		super.insert("FIN_DEPOSIT.insertFincDeposit", fincDeposit);
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
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", type);
		map.put("supplierId", supplierId);
		return (Double) super.queryForObject("FIN_DEPOSIT.searchAmount",map);
	}
	/**
	 * 查询供应商押金预警
	 * 
	 * @return
	 */
	public Page<FinSupplierMoney> searchDepositWarning(Map<String,Object> map) {
		return super.queryForPage("FIN_DEPOSIT.searchDepositWarning", map);
	}
	
	/**
	 * 查询供应商的预存款余额
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	public FinSupplierMoney searchSupplier(Long supplierId) {
		return (FinSupplierMoney)super.queryForObject("FIN_DEPOSIT.searchSupplierCash",supplierId);
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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currency", currency);
		map.put("supplierId", supplierId);
		super.update("FIN_DEPOSIT.updateSupplierCurrency", map);
	}
	
	/**
	 * 更新供应商押金币种
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @param amount
	 *            金额
	 */
	public void updateSupplierForecurrency(Long supplierId, String advcurrency) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("advcurrency", advcurrency);
		map.put("supplierId", supplierId);
		super.update("FIN_DEPOSIT.updateSupplierCurrency", map);
	}

}
