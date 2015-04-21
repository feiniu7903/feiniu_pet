package com.lvmama.finance.settlement.service;

import java.util.List;

import com.lvmama.comm.vo.Currency;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.settlement.ibatis.po.FincForegifts;
import com.lvmama.finance.settlement.ibatis.vo.SimpleSupplier;

/**
 * 供应商押金
 * 
 * @author yanggan
 * 
 */
public interface ForegiftsService {

	/**
	 * 查询供应商押金信息
	 * 
	 * @return
	 */
	Page<SimpleSupplier> searchForegifts();

	/**
	 * 查询供应商押金预警
	 * 
	 * @return
	 */
	Page<SimpleSupplier> searchForegiftsAlert();
	
	/**
	 * 查询供应商担保函信息
	 * 
	 * @return
	 */
	Page<SimpleSupplier> searchGuaranteeLimit();

	/**
	 * 查询流水记录
	 * 
	 * @param type
	 *            类型 押金/担保函
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	List<FincForegifts> searchFincForegifts(String type, Integer supplierId);

	/**
	 * 增加押金
	 * 
	 * @param fincForegifts
	 */
	void addForegifts(FincForegifts fincForegifts);

	/**
	 * 转为预存款
	 * 
	 * @param fincForegifts
	 */
	void shiftout2Advancedeposits(FincForegifts fincForegifts);

	/**
	 * 查询押金金额
	 * 
	 * @param type
	 *            类型 押金/担保函
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	Double searchAmount(String type, Long supplierId);
	
	List<Currency> searchAllCurrency();
	
	/**
	 * 查询供应商的信息
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	SimpleSupplier searchSupplier(Long supplierId);
	
	/**
	 * 更新供应商币种
	 * @param supplierId
	 * @param currency
	 */
	void updateSupplierCurrency(Long supplierId, String currency);
	
}
