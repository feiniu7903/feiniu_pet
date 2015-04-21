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
import com.lvmama.finance.settlement.ibatis.dao.FincForegiftsDAO;
import com.lvmama.finance.settlement.ibatis.po.FincAdvancedeposits;
import com.lvmama.finance.settlement.ibatis.po.FincForegifts;
import com.lvmama.finance.settlement.ibatis.vo.SimpleSupplier;

@Service
public class ForegiftsServiceImpl implements ForegiftsService {

	@Autowired
	private FincForegiftsDAO fincForegiftsDAO;

	@Autowired
	private AdvancedepositsService advancedepositsService;
	
	@Override
	public Page<SimpleSupplier> searchForegifts() {
		return fincForegiftsDAO.searchForegifts();
	}

	@Override
	public Page<SimpleSupplier> searchForegiftsAlert() {
		return fincForegiftsDAO.searchForegiftsAlert();
	}
	@Override
	public Page<SimpleSupplier> searchGuaranteeLimit() {
		return fincForegiftsDAO.searchGuaranteeLimit();
	}

	@Override
	public List<FincForegifts> searchFincForegifts(String type, Integer supplierId) {
		return fincForegiftsDAO.searchFincForegifts(type, supplierId);
	}
	
	/**
	 * 添加押金
	 * @param fincForegifts
	 */
	public void addForegifts(FincForegifts fincForegifts){
		String type = fincForegifts.getType();
		double amount = 0d ;
		//当类型为存入，预存款转入时 借贷方向为：借
		if(Constant.DEPOSIT.equals(type) || Constant.SHIFTIN.equals(type) ){
			fincForegifts.setDirection(Constant.DEBIT);
			amount = fincForegifts.getAmount();
		}else if(Constant.SHIFTOUT.equals(type)|| Constant.RETURN.equals(type)||Constant.REVISION.equals(type)){
			fincForegifts.setDirection(Constant.CREDIT);
			amount-=fincForegifts.getAmount();
		}else{
			throw new RuntimeException("添加押金的交易类型错误！");
		}
		
		//修改供应商信息
		if(Constant.FOREGIFTS_KIND_CASH.equals(fincForegifts.getKind())){
			//修改押金
			fincForegiftsDAO.updateSupplierForegiftsBal(fincForegifts.getSupplierId(), amount);
		}else if(Constant.FOREGIFTS_KIND_GUARANTEE.equals(fincForegifts.getKind())){
			//修改担保函
			fincForegiftsDAO.updateSupplierGuaranteeLimit(fincForegifts.getSupplierId(), amount);
		}else{
			throw new RuntimeException("添加押金的类型错误！");
		}
		HttpSession session = FinanceContext.getSession();
		PermUser user = (PermUser) session.getAttribute(com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
		fincForegifts.setCreator(user.getUserId());
		//生产流水记录
		fincForegiftsDAO.insertFincForegifts(fincForegifts);
		fincForegifts.setAmount(amount);
	}

	@Override
	public void shiftout2Advancedeposits(FincForegifts fincForegifts) {
		FincAdvancedeposits fad = new FincAdvancedeposits();
		fad.setSupplierId(fincForegifts.getSupplierId());
		fad.setAmount(fincForegifts.getAmount());
		fad.setOperatetime(new Date());
		fad.setType(Constant.SHIFTIN);
		fad.setAdvCurrency(fincForegifts.getDepositCurrency());
		fincForegifts.setKind(Constant.FOREGIFTS_KIND_CASH);
		fincForegifts.setType(Constant.SHIFTOUT);
		fincForegifts.setOperatetime(fad.getOperatetime());
		//押金转出
		this.addForegifts(fincForegifts);
		//预存款转入
		advancedepositsService.addAdvancedeposits(fad);
		// 更新预存款的币种为押金的币种
		fincForegiftsDAO.updateSupplierForecurrency(fincForegifts.getSupplierId(), fincForegifts.getDepositCurrency());
	}

	@Override
	public Double searchAmount(String type, Long supplierId) {
		return  fincForegiftsDAO.searchAmount(type, supplierId);
	}

	@Override
	public List<Currency> searchAllCurrency() {
		return fincForegiftsDAO.searchAllCurrency();
	}

	@Override
	public SimpleSupplier searchSupplier(Long supplierId) {
		return fincForegiftsDAO.searchSupplier(supplierId);
	}

	@Override
	public void updateSupplierCurrency(Long supplierId, String currency) {
		fincForegiftsDAO.updateSupplierCurrency(supplierId, currency);
	}
}
