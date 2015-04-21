package com.lvmama.pet.fin.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.hessian.HessianService;
import com.lvmama.comm.pet.po.fin.FinAdvanceDeposit;
import com.lvmama.comm.pet.po.fin.FinDeposit;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.po.fin.SetSettlementPayment;
import com.lvmama.comm.pet.service.fin.FinDepositService;
import com.lvmama.comm.pet.service.fin.FinanceAdvanceDepositService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.BaseService;
import com.lvmama.pet.fin.dao.FinanceAdvanceDepositDAO;
import com.lvmama.pet.fin.dao.FinanceDepositDAO;
import com.lvmama.pet.fin.dao.FinanceSupplierMoneyDAO;

@HessianService("financeAdvanceDepositService")
@Service("financeAdvanceDepositService")
public class FinanceAdvanceDepositServiceImpl extends BaseService implements FinanceAdvanceDepositService {

	@Autowired
	private FinanceAdvanceDepositDAO financeAdvanceDepositDAO;
	@Autowired
	private FinDepositService financeDepositService;
	@Autowired
	private FinanceSupplierMoneyDAO financeSupplierMoneyDAO;
	@Autowired
	private FinanceDepositDAO financeDepositDAO;
	
	@Override
	public Page<FinSupplierMoney> searchAdvanceDepositWarning(Map<String,Object> map) {
		return financeAdvanceDepositDAO.searchAdvanceDepositWarning(map);
	}

	@Override
	public Page<FinSupplierMoney> searchAdvanceDeposit(Map<String,Object> map) {
		return financeSupplierMoneyDAO.searchAdvanceDeposit(map);
	}

	@Override
	public void addAdvanceDeposit(FinAdvanceDeposit finAdvanceDeposit) {
		String type = finAdvanceDeposit.getType();
		long amount = 0l ;
		//当类型为存入，预存款转入时 借贷方向为：借
		if(Constant.FIN_ADVANCE_DEPOSIT_TYPE.DEPOSIT.toString().equals(type) 
				|| Constant.FIN_ADVANCE_DEPOSIT_TYPE.SHIFTIN.toString().equals(type) ){
			finAdvanceDeposit.setDirection(Constant.DERECTION_TYPE.DEBIT.toString());
			amount = finAdvanceDeposit.getAmount();
		}else if(Constant.FIN_ADVANCE_DEPOSIT_TYPE.SHIFTOUT.toString().equals(type)
				|| Constant.FIN_ADVANCE_DEPOSIT_TYPE.RETURN.toString().equals(type)
				||Constant.FIN_ADVANCE_DEPOSIT_TYPE.REVISION.toString().equals(type)||Constant.FIN_ADVANCE_DEPOSIT_TYPE.PAYMENT.toString().equals(type)){
			finAdvanceDeposit.setDirection(Constant.DERECTION_TYPE.CREDIT.toString());
			amount-=finAdvanceDeposit.getAmount();
		}else{
			throw new RuntimeException("添加预存款的交易类型错误！");
		}
		finAdvanceDeposit.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
		
		financeAdvanceDepositDAO.updateSupplierAdvanceDepositAmount(finAdvanceDeposit.getSupplierId(), amount);
		
		//生成流水记录
		financeAdvanceDepositDAO.insertFinadvanceDeposit(finAdvanceDeposit);
		finAdvanceDeposit.setAmount(amount);
	}

	@Override
	public List<FinAdvanceDeposit> searchFinAdvanceDeposit(Map<String,Object> map) {
		return financeAdvanceDepositDAO.searchFinAdvanceDeposit(map);
	}

	@Override
	public Long searchFinAdvanceDepositCount(Map<String,Object> map) {
		return financeAdvanceDepositDAO.searchFinAdvanceDepositCount(map);
	}

	@Override
	public List<SetSettlementPayment> searchSettlementPayment(Long supplierId) {
		return financeAdvanceDepositDAO.searchAdvanceDepositPayment(supplierId);
	}

	@Override
	public void shiftout2Deposit(FinAdvanceDeposit finAdvancedDeposit) {
		FinDeposit ffo = new FinDeposit();
		ffo.setSupplierId(finAdvancedDeposit.getSupplierId());
		ffo.setAmount(finAdvancedDeposit.getAmount());
		ffo.setOperatetime(new Date());
		ffo.setKind(Constant.FOREGIFTS_KIND.CASH.toString());
		ffo.setType(Constant.FIN_DEPOSIT_TYPE.SHIFTIN.toString());
		ffo.setCreator(finAdvancedDeposit.getCreator());
		finAdvancedDeposit.setType(Constant.FIN_DEPOSIT_TYPE.SHIFTOUT.toString());
		finAdvancedDeposit.setOperatetime(ffo.getOperatetime());
		finAdvancedDeposit.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
		// 预存款转出
		this.addAdvanceDeposit(finAdvancedDeposit);
		// 押金转入
		financeDepositService.addDeposit(ffo);
		// 更新押金的币种为预存款的币种
		financeAdvanceDepositDAO.updateSupplierForecurrency(finAdvancedDeposit.getSupplierId(), finAdvancedDeposit.getCurrency());
	}
	@Override
	public void shiftout2AdvanceDeposit(FinDeposit fincDeposit) {
		FinAdvanceDeposit fad = new FinAdvanceDeposit();
		fad.setSupplierId(fincDeposit.getSupplierId());
		fad.setAmount(fincDeposit.getAmount());
		fad.setOperatetime(new Date());
		fad.setType(Constant.FIN_DEPOSIT_TYPE.SHIFTIN.toString());
		fad.setCurrency(fincDeposit.getCurrency());
		fad.setCreator(fincDeposit.getCreatorName());
		fad.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
		fincDeposit.setKind(Constant.FOREGIFTS_KIND.CASH.toString());
		fincDeposit.setType(Constant.FIN_DEPOSIT_TYPE.SHIFTOUT.toString());
		fincDeposit.setOperatetime(fad.getOperatetime());
		fincDeposit.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
		//押金转出
		financeDepositService.addDeposit(fincDeposit);
		//预存款转入
		addAdvanceDeposit(fad);
		// 更新预存款的币种为押金的币种
		financeDepositDAO.updateSupplierForecurrency(fincDeposit.getSupplierId(), fincDeposit.getCurrency());
	}
	@Override
	public FinSupplierMoney searchSupplier(Long supplierId) {
		return financeAdvanceDepositDAO.searchSupplier(supplierId);
	}

	@Override
	public void updateSupplierCurrency(FinAdvanceDeposit finAdvancedDeposit) {
		// 根据供应商id查询是否存在供应商信息
		FinSupplierMoney finSupplierMoney = financeSupplierMoneyDAO.searchBySupplierId(finAdvancedDeposit.getSupplierId());
		// 存在供应商信息时，更新押金币种
		if(null != finSupplierMoney){
			financeAdvanceDepositDAO.updateSupplierCurrency(finAdvancedDeposit.getSupplierId(), finAdvancedDeposit.getCurrency());
		}
		// 不存在供应商信息时，新增供应商（供应商ID, 押金余额， 押金币种）
		else{
			financeSupplierMoneyDAO.insertAdvanceDeposit(finAdvancedDeposit);
		}
	}
}
