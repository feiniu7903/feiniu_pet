package com.lvmama.comm.pet.service.fin;

import com.lvmama.comm.hessian.RemoteService;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;

/**
 * 供应商资金信息接口
 * 
 * @author yanggan
 * 
 */
@RemoteService("finSupplierMoneyService")
public interface FinSupplierMoneyService {

	/**
	 * 根据供应商ID查询
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	public FinSupplierMoney searchBySupplierId(Long supplierId);


	/**
	 * 更新供应商金融信息
	 * 
	 */
	public void updateSupplierMoney(FinSupplierMoney finSupplierMoney);
	
	/**
	 * 根据供应商ID 修改预存款余额和抵扣款余额
	 * @param advanceDepositAmount
	 * @param deductionAmount
	 * @param supplierId
	 */
	public void updateMoney(Long advanceDepositAmount, Long deductionAmount, Long supplierId);

}
