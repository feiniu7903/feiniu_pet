package com.lvmama.pet.fin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.hessian.HessianService;
import com.lvmama.comm.pet.po.fin.FinDeposit;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.service.fin.FinDepositService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.BaseService;
import com.lvmama.pet.fin.dao.FinDepositDAO;
import com.lvmama.pet.fin.dao.FinSupplierMoneyDAO;

/**
 * 
 * @author zhangwenjun
 *
 */
@HessianService("finDepositService")
@Service("finDepositService")
public class FinDepositServiceImpl extends BaseService implements FinDepositService{

	@Autowired
	private FinDepositDAO finDepositDAO;
	@Autowired
	private FinSupplierMoneyDAO finSupplierMoneyDAO;


	@Override
	public Page<FinSupplierMoney> searchDeposit(Map<String,Object> map) {
		return finSupplierMoneyDAO.searchDeposit(map);
	}
	
	@Override
	public Page<FinSupplierMoney> searchGuaranteeLimit(Map<String,Object> map) {
		return finSupplierMoneyDAO.searchGuarantee(map);
	}

	@Override
	public Page<FinSupplierMoney> searchForegiftWarning(Map<String,Object> map) {
		return finDepositDAO.searchDepositWarning(map);
	}

	@Override
	public List<FinDeposit> searchForegiftRecord(Map<String,Object> map) {
		return finDepositDAO.searchDepositRecord(map);
	}

	@Override
	public Long searchForegiftRecordCount(Map<String,Object> map) {
		return finDepositDAO.searchDepositRecordCount(map);
	}
	
	/**
	 * 添加押金
	 * @param fincForegifts
	 */
	public void addDeposit(FinDeposit fincDeposit){
		String type = fincDeposit.getType();
		Long amount = 0l;
		// 当类型为存入，预存款转入时，借贷方向为：借
		if(Constant.FIN_DEPOSIT_TYPE.DEPOSIT.toString().equals(type) 
				|| Constant.FIN_DEPOSIT_TYPE.SHIFTIN.toString().equals(type) ){
			fincDeposit.setDirection(Constant.DERECTION_TYPE.DEBIT.toString());
			amount = fincDeposit.getAmount();
		}
		// 当类型为转出、退回、冲正时，借贷方向为：贷
		else if(Constant.FIN_DEPOSIT_TYPE.SHIFTOUT.toString().equals(type)
				|| Constant.FIN_DEPOSIT_TYPE.RETURN.toString().equals(type)
				||Constant.FIN_DEPOSIT_TYPE.REVISION.toString().equals(type)){
			fincDeposit.setDirection(Constant.DERECTION_TYPE.CREDIT.toString());
			amount-=fincDeposit.getAmount();
		}else{
			throw new RuntimeException("添加押金的交易类型错误！");
		}
		
		// 修改供应商信息
		if(Constant.FOREGIFTS_KIND.CASH.toString().equals(fincDeposit.getKind())){
			// 修改押金
			finDepositDAO.updateSupplierDepositAmount(fincDeposit.getSupplierId(), amount);
		}else if(Constant.FOREGIFTS_KIND.GUARANTEE.toString().equals(fincDeposit.getKind())){
			// 修改担保函
			finDepositDAO.updateSupplierGuaranteeLimit(fincDeposit.getSupplierId(), amount);
		}else{
			throw new RuntimeException("添加押金的类型错误！");
		}

		// 产生流水记录
		finDepositDAO.insertFincDeposit(fincDeposit);
		fincDeposit.setAmount(amount);
	}

	@Override
	public void updateSupplierCurrency(FinDeposit fincDeposit) {
		// 根据供应商id查询是否存在供应商信息
		FinSupplierMoney finSupplierMoney = finSupplierMoneyDAO.searchBySupplierId(fincDeposit.getSupplierId());
		// 存在供应商信息时，更新押金币种
		if(null != finSupplierMoney){
			finDepositDAO.updateSupplierCurrency(fincDeposit.getSupplierId(), fincDeposit.getCurrency());
		}
		// 不存在供应商信息时，新增供应商（供应商ID, 押金余额， 押金币种）
		else{
			finSupplierMoneyDAO.insertForegift(fincDeposit);
		}
	}

	@Override
	public FinSupplierMoney searchSupplier(Long supplierId) {
		return finDepositDAO.searchSupplier(supplierId);
	}
//	@Override
//	public Double searchAmount(String type, Long supplierId) {
//		return  finForegiftDAO.searchAmount(type, supplierId);
//	}
	
}
