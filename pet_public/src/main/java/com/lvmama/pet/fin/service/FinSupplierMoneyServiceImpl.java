package com.lvmama.pet.fin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.hessian.HessianService;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.service.fin.FinSupplierMoneyService;
import com.lvmama.pet.BaseService;
import com.lvmama.pet.fin.dao.FinSupplierMoneyDAO;

@HessianService("finSupplierMoneyService")
@Service("finSupplierMoneyService")
public class FinSupplierMoneyServiceImpl extends BaseService implements FinSupplierMoneyService {

	@Autowired
	private FinSupplierMoneyDAO finSupplierMoneyDAO;
	
	@Override
	public FinSupplierMoney searchBySupplierId(Long supplierId) {
		return finSupplierMoneyDAO.searchBySupplierId(supplierId);
	}

	@Override
	public void updateMoney(Long advanceDepositAmount, Long deductionAmount, Long supplierId) {
		finSupplierMoneyDAO.minusSupplierMoney(supplierId, deductionAmount, advanceDepositAmount, null);
	}

	/**
	 * 更新供应商金融信息
	 * 
	 */
	@Override
	public void updateSupplierMoney(FinSupplierMoney finSupplierMoney){
		FinSupplierMoney exists = this.searchBySupplierId(finSupplierMoney.getSupplierId());
		if(exists!=null){
			finSupplierMoneyDAO.updateSupplierMoney(finSupplierMoney);
		}else{
			finSupplierMoneyDAO.insertSupplierMoney(finSupplierMoney);
		}
	}
	
}
