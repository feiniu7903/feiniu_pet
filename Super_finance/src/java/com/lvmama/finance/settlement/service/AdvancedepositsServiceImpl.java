package com.lvmama.finance.settlement.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.vo.Currency;
import com.lvmama.finance.base.Constant;
import com.lvmama.finance.base.FinanceContext;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.settlement.ibatis.dao.FincAdvancedepositsDAO;
import com.lvmama.finance.settlement.ibatis.dao.OrdSettlementPaymentDAO;
import com.lvmama.finance.settlement.ibatis.po.FincAdvancedeposits;
import com.lvmama.finance.settlement.ibatis.po.FincForegifts;
import com.lvmama.finance.settlement.ibatis.po.OrdSettlementPayment;
import com.lvmama.finance.settlement.ibatis.vo.SimpleSupplier;

@Service
public class AdvancedepositsServiceImpl implements AdvancedepositsService {

	@Autowired
	private FincAdvancedepositsDAO advancedepositsDAO;
	
	@Autowired
	private ForegiftsService foregiftsService;
	
	@Autowired
	private OrdSettlementPaymentDAO ordSettlementPaymentDAO;

	@Override
	public Page<SimpleSupplier> searchAdvancedeposits() {
		return advancedepositsDAO.searchAdvancedeposits();
	}

	@Override
	public Page<SimpleSupplier> searchAdvancedepositsAlert() {
		return advancedepositsDAO.searchAdvancedepositsAlert();
	}

	
	@Override
	public List<FincAdvancedeposits> searchFincAdvancedeposits(Long supplierId) {
		return advancedepositsDAO.searchFincAdvancedeposits(supplierId);
	}

	@Override
	public List<OrdSettlementPayment> searchSettlementPayment(Long supplierId) {
		return ordSettlementPaymentDAO.searchAdvancedepositsPayment(supplierId);
	}

	@Override
	public void addAdvancedeposits(FincAdvancedeposits fincAdvancedeposits) {
		String type = fincAdvancedeposits.getType();
		double amount = 0d ;
		//当类型为存入，预存款转入时 借贷方向为：借
		if(Constant.DEPOSIT.equals(type) || Constant.SHIFTIN.equals(type) ){
			fincAdvancedeposits.setDirection(Constant.DEBIT);
			amount = fincAdvancedeposits.getAmount();
		}else if(Constant.SHIFTOUT.equals(type)|| Constant.RETURN.equals(type)||Constant.REVISION.equals(type)||Constant.PAYMENT.equals(type)){
			fincAdvancedeposits.setDirection(Constant.CREDIT);
			amount-=fincAdvancedeposits.getAmount();
		}else{
			throw new RuntimeException("添加预存款的交易类型错误！");
		}
		

		advancedepositsDAO.updateSupplierAdvancedepositsBal(fincAdvancedeposits.getSupplierId(), amount);
		
		HttpSession session = FinanceContext.getSession();
		PermUser user = (PermUser) session.getAttribute(com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
		fincAdvancedeposits.setCreator(user.getUserId());
		//生成流水记录
		advancedepositsDAO.insertFincAdvancedeposits(fincAdvancedeposits);
		fincAdvancedeposits.setAmount(amount);
	}

	@Override
	public void shiftout2Foregifts(FincAdvancedeposits fincAdvancedeposits) {
		FincForegifts ffo = new FincForegifts();
		ffo.setSupplierId(fincAdvancedeposits.getSupplierId());
		ffo.setAmount(fincAdvancedeposits.getAmount());
		ffo.setOperatetime(new Date());
		ffo.setKind(Constant.FOREGIFTS_KIND_CASH);
		ffo.setType(Constant.SHIFTIN);
		fincAdvancedeposits.setType(Constant.SHIFTOUT);
		fincAdvancedeposits.setOperatetime(ffo.getOperatetime());
		// 预存款转出
		this.addAdvancedeposits(fincAdvancedeposits);
		// 押金转入
		foregiftsService.addForegifts(ffo);
		// 更新押金的币种为预存款的币种
		advancedepositsDAO.updateSupplierForecurrency(fincAdvancedeposits.getSupplierId(), fincAdvancedeposits.getAdvCurrency());
	}

	@Override
	public Double searchAmount(Long supplierId,String currency) {
		return advancedepositsDAO.searchAmount(supplierId,currency);
	}


	@Override
	public SimpleSupplier searchSupplier(Long supplierId) {
		return advancedepositsDAO.searchSupplier(supplierId);
	}

	@Override
	public void updateSupplierCurrency(Long supplierId, String currency) {
		advancedepositsDAO.updateSupplierCurrency(supplierId, currency);
	}

	@Override
	public List<Currency> searchAllCurrency() {
		return advancedepositsDAO.searchAllCurrency();
	}
}
