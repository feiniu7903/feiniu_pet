package com.lvmama.comm.pet.service.fin;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.hessian.RemoteService;
import com.lvmama.comm.pet.po.fin.FinAdvanceDeposit;
import com.lvmama.comm.pet.po.fin.FinDeposit;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.po.fin.SetSettlementPayment;
import com.lvmama.comm.pet.vo.Page;


/**
 * 预存管理Service
 * 
 * @author yanggan
 * 
 */
@RemoteService("finAdvanceDepositService")
public interface FinAdvanceDepositService {
	
	/**
	 * 查询达到预警的预存款信息
	 * @return
	 */
	public Page<FinSupplierMoney> searchAdvanceDepositWarning(Map<String,Object> map);
	
	/**
	 * 查询供应商预存款信息
	 * 
	 * @return
	 */
	Page<FinSupplierMoney> searchAdvanceDeposit(Map<String,Object> map);

	/**
	 * 添加预存款信息
	 * 
	 * @param fincAdvancedeposits
	 *            预存款信息
	 */
	void addAdvanceDeposit(FinAdvanceDeposit finAdvancedDeposit);
	
	/**
	 * 查询流水记录
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	List<FinAdvanceDeposit> searchFinAdvanceDeposit(Map<String,Object> map);
	Long searchFinAdvanceDepositCount(Map<String,Object> map);

	/**
	 * 查询供应商的预存款结算记录
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	List<SetSettlementPayment> searchSettlementPayment(Long supplierId);

	/**
	 * 转为押金
	 * 
	 * @param fincForegifts
	 */
	void shiftout2Deposit(FinAdvanceDeposit finAdvancedDeposit);
//
//	/**
//	 * 查询供应商的预存款余额
//	 * 
//	 * @param supplierId
//	 *            供应商ID
//	 * @return
//	 */
//	Double searchAmount(Long supplierId,String currency);
	

	/**
	 * 查询供应商的信息
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	FinSupplierMoney searchSupplier(Long supplierId);
	
	/**
	 * 更新供应商币种
	 * @param supplierId
	 * @param currency
	 */
	void updateSupplierCurrency(FinAdvanceDeposit finAdvancedDeposit);

	public void shiftout2AdvanceDeposit(FinDeposit fincDeposit) ;
}
