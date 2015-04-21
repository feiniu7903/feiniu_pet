package com.lvmama.finance.settlement.service;

import java.util.List;

import com.lvmama.comm.vo.Currency;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.settlement.ibatis.po.FincAdvancedeposits;
import com.lvmama.finance.settlement.ibatis.po.OrdSettlementPayment;
import com.lvmama.finance.settlement.ibatis.vo.SimpleSupplier;

/**
 * 预存管理Service
 * 
 * @author yanggan
 * 
 */
public interface AdvancedepositsService {
	/**
	 * 查询供应商预存款信息
	 * 
	 * @return
	 */
	Page<SimpleSupplier> searchAdvancedeposits();
	
	/**
	 * 查询达到预警的预存款信息
	 * @return
	 */
	public Page<SimpleSupplier> searchAdvancedepositsAlert();
	/**
	 * 查询历史记录
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	List<FincAdvancedeposits> searchFincAdvancedeposits(Long supplierId);

	/**
	 * 查询供应商的预存款结算记录
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	List<OrdSettlementPayment> searchSettlementPayment(Long supplierId);

	/**
	 * 添加预存款信息
	 * 
	 * @param fincAdvancedeposits
	 *            预存款信息
	 */
	void addAdvancedeposits(FincAdvancedeposits fincAdvancedeposits);

	/**
	 * 转为押金
	 * 
	 * @param fincForegifts
	 */
	void shiftout2Foregifts(FincAdvancedeposits fincAdvancedeposits);

	/**
	 * 查询供应商的预存款余额
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	Double searchAmount(Long supplierId,String currency);
	

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
	

	/**
	 * 查询所有的币种
	 * @return
	 */
	List<Currency> searchAllCurrency();
}
