package com.lvmama.pet.fin.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.fin.FinAdvanceDeposit;
import com.lvmama.comm.pet.po.fin.FinDeposit;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;

@Repository
@SuppressWarnings("unchecked")
public class FinanceSupplierMoneyDAO extends BaseIbatisDAO {

	/**
	 * 查询抵扣款分页信息
	 * @param map
	 * @return
	 */
	public Page<FinSupplierMoney> searchDeduction(Map<String, Object> map) {
		map.put("flag","deduction");
		return super.queryForPage("FINANCE_SUPPLIER_MONEY.searchSupplierMoney", map);
	}
	/**
	 * 查询押金分页信息
	 * @param map
	 * @return
	 */
	public Page<FinSupplierMoney> searchAdvanceDeposit(Map<String,Object> map) {
		return super.queryForPage("FINANCE_SUPPLIER_MONEY.searchAdvanceDeposit", map);
	}
	/**
	 * 查询预存款分页信息
	 * @param map
	 * @return
	 */
	public Page<FinSupplierMoney> searchDeposit(Map<String, Object> map) {
		map.put("flag","deposit");
		return super.queryForPage("FINANCE_SUPPLIER_MONEY.searchSupplierMoney", map);
	}
	/**
	 * 查询担保函分页信息
	 * @param map
	 * @return
	 */
	public Page<FinSupplierMoney> searchGuarantee(Map<String, Object> map) {
		map.put("flag","guarantee");
		return super.queryForPage("FINANCE_SUPPLIER_MONEY.searchSupplierMoney", map);
	}

	/**
	 * 添加抵扣款
	 * 
	 * @param amount
	 *            抵扣款金额
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	public int addDeduction(Long amount, Long supplierId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", amount);
		map.put("supplierId", supplierId);
		return super.update("FINANCE_SUPPLIER_MONEY.addDeduction", map);
	}
	/**
	 * 添加抵扣款
	 * 
	 * @param amount
	 *            抵扣款金额
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	public int addDeduction(Long amount, Long supplierId,String deductionCurrency) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", amount);
		map.put("supplierId", supplierId);
		map.put("deductionCurrency", deductionCurrency);
		return super.update("FINANCE_SUPPLIER_MONEY.addDeduction", map);
	}
	public void insertSupplierMoney(FinSupplierMoney finSupplierMoney) {
		finSupplierMoney.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
		super.insert("FINANCE_SUPPLIER_MONEY.insertSupplierMoney",finSupplierMoney);
	}
	/**
	 * 新增抵扣款
	 * 
	 * @param amount
	 *            抵扣款金额
	 * @param supplierId
	 *            供应商ID
	 */
	public void insertDeduction(Long amount, Long supplierId) {
		FinSupplierMoney supplierMoney = new FinSupplierMoney();
		supplierMoney.setSupplierId(supplierId);
		supplierMoney.setDeductionAmount(amount);
		supplierMoney.setDeductionCurrency("CNY");
		super.update("FINANCE_SUPPLIER_MONEY.insertSupplierMoney", supplierMoney);
	}

	/**
	 * 根据供应商ID查询
	 * 
	 * @param supplierId
	 * @return
	 */
	public FinSupplierMoney searchBySupplierId(Long supplierId) {
		return (FinSupplierMoney) super.queryForObject("FINANCE_SUPPLIER_MONEY.searchBySupplierId", supplierId);
	}

	/**
	 * 减去供应商金额信息
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @param deductionAmount
	 *            抵扣款金额
	 * @param advanceDepositAmount
	 *            预存款金额
	 * @param depositAmount
	 *            押金金额
	 */
	public void minusSupplierMoney(Long supplierId, Long deductionAmount, Long advanceDepositAmount, Long depositAmount) {
		Map<String,Long> map = new HashMap<String,Long>();
		if (deductionAmount != null) {
			map.put("deductionAmount", deductionAmount);
		}
		if (advanceDepositAmount != null) {
			map.put("advanceDepositAmount", advanceDepositAmount);
		}
		if (depositAmount != null) {
			map.put("depositAmount", depositAmount);
		}
		map.put("supplierId", supplierId);
		super.update("FINANCE_SUPPLIER_MONEY.minusSupplierMoney", map);
	}
	
	public void updateSupplierMoney(FinSupplierMoney finSupplierMoney){
		super.update("FINANCE_SUPPLIER_MONEY.updateSupplierMoney", finSupplierMoney);
	}

	/**
	 * 新增押金
	 * 
	 * @param amount
	 *            抵扣款金额
	 * @param supplierId
	 *            供应商ID
	 */
	public void insertForegift(FinDeposit finDeposit) {
		FinSupplierMoney supplierMoney = new FinSupplierMoney();
		supplierMoney.setDepositCurrency(finDeposit.getCurrency());
		supplierMoney.setSupplierId(finDeposit.getSupplierId());
		// 押金
		if(finDeposit.getKind().equals(Constant.FOREGIFTS_KIND.CASH.toString())){
			supplierMoney.setDepositAmount(finDeposit.getAmount());
		}
		// 担保函
		else{
			supplierMoney.setGuaranteeLimit(finDeposit.getAmount());
		}
		supplierMoney.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
		super.update("FINANCE_SUPPLIER_MONEY.insertSupplierMoney", supplierMoney);
	}

	/**
	 * 新增预存款
	 * 
	 * @param amount
	 *            抵扣款金额
	 * @param supplierId
	 *            供应商ID
	 */
	public void insertAdvanceDeposit(FinAdvanceDeposit finAdvancedDeposit) {
		FinSupplierMoney supplierMoney = new FinSupplierMoney();
		supplierMoney.setSupplierId(finAdvancedDeposit.getSupplierId());
		supplierMoney.setAdvanceDepositAmount(finAdvancedDeposit.getAmount());
		supplierMoney.setAdvanceDepositCurrency(finAdvancedDeposit.getCurrency());
		supplierMoney.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
		super.update("FINANCE_SUPPLIER_MONEY.insertSupplierMoney", supplierMoney);
	}
	
	/**
	 * 新增抵扣款
	 * 
	 * @param amount
	 *            抵扣款金额
	 * @param supplierId
	 *            供应商ID
	 */
	public void insertDeduction(Long amount, Long supplierId, String currency) {
		FinSupplierMoney supplierMoney = new FinSupplierMoney();
		supplierMoney.setSupplierId(supplierId);
		supplierMoney.setDeductionAmount(amount);
		supplierMoney.setDeductionCurrency(currency);
		supplierMoney.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
		super.update("FINANCE_SUPPLIER_MONEY.insertSupplierMoney", supplierMoney);
	}
	
}
