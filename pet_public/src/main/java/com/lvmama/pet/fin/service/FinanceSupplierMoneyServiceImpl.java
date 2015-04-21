package com.lvmama.pet.fin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.hessian.HessianService;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.service.fin.FinanceSupplierMoneyService;
import com.lvmama.pet.BaseService;
import com.lvmama.pet.fin.dao.FinanceSupplierMoneyDAO;

@HessianService("financeSupplierMoneyService")
@Service("financeSupplierMoneyService")
public class FinanceSupplierMoneyServiceImpl extends BaseService implements FinanceSupplierMoneyService {

	@Autowired
	private FinanceSupplierMoneyDAO financeSupplierMoneyDAO;
	
	@Override
	public FinSupplierMoney searchBySupplierId(Long supplierId) {
		return financeSupplierMoneyDAO.searchBySupplierId(supplierId);
	}

	@Override
	public void updateMoney(Long advanceDepositAmount, Long deductionAmount, Long supplierId) {
		financeSupplierMoneyDAO.minusSupplierMoney(supplierId, deductionAmount, advanceDepositAmount, null);
	}

	/**
	 * 更新供应商金融信息
	 * 
	 */
	@Override
	public void updateSupplierMoney(FinSupplierMoney finSupplierMoney){
		FinSupplierMoney exists = this.searchBySupplierId(finSupplierMoney.getSupplierId());
		if(exists!=null){
			financeSupplierMoneyDAO.updateSupplierMoney(finSupplierMoney);
		}else{
			financeSupplierMoneyDAO.insertSupplierMoney(finSupplierMoney);
		}
	}
	
}
