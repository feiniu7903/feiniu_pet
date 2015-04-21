package com.lvmama.pet.fin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.hessian.HessianService;
import com.lvmama.comm.pet.po.fin.FinDeduction;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.service.fin.FinDeductionService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.BaseService;
import com.lvmama.pet.fin.dao.FinDeductionDAO;
import com.lvmama.pet.fin.dao.FinSupplierMoneyDAO;

/**
 * 
 * @author zhangwenjun
 *
 */
@HessianService("finDeductionService")
@Service("finDeductionService")
public class FinDeductionServiceImpl extends BaseService implements FinDeductionService {

	@Autowired
	private FinDeductionDAO finDeductionDAO;
	@Autowired
	private FinSupplierMoneyDAO finSupplierMoneyDAO;
	
	public FinDeductionDAO getFinDeductionDAO() {
		return finDeductionDAO;
	}

	@Override
	public Page<FinSupplierMoney> search(Map<String, Object> map) {
		return finSupplierMoneyDAO.searchDeduction(map);
	}

	@Override
	public Integer updateDeduction(FinDeduction finDeduction) {
		// 根据供应商id查询是否存在供应商信息
		FinSupplierMoney finSupplierMoney = finSupplierMoneyDAO.searchBySupplierId(finDeduction.getSupplierId());
		// 存在供应商信息时，更新抵扣款金额
		if(null != finSupplierMoney){
			long amount = 0l ;
			if(finDeduction.getType().equals(Constant.FIN_DEDUCTION_TYPE.DEPOSIT.toString())){
				finDeduction.setDirection(Constant.DERECTION_TYPE.DEBIT.toString());// 借
				amount = finDeduction.getAmount();
			}else if(finDeduction.getType().equals(Constant.FIN_DEDUCTION_TYPE.RETURN.toString())){
				finDeduction.setDirection(Constant.DERECTION_TYPE.CREDIT.toString());// 贷
				amount-=finDeduction.getAmount();
			}else{
				throw new RuntimeException("添加预存款的交易类型错误！");
			}
			
			// 从抵扣款管理中点击【修改】超链接进入该方法
			if(null == finDeduction.getCurrency() || finDeduction.getCurrency().equals("")){
				// 修改抵扣款金额
				finSupplierMoneyDAO.addDeduction(amount, finDeduction.getSupplierId());
				// 添加流水记录
				finDeductionDAO.insertFinDeduction(finDeduction);
			}
			// 从核算中调用该方法
			else{
				// 传入参数的币种不能为空
				if(StringUtil.isEmptyString(finSupplierMoney.getDeductionCurrency())){
					finSupplierMoney.setDeductionCurrency(finDeduction.getCurrency());
				}
				if(finDeduction.getCurrency().equals(finSupplierMoney.getDeductionCurrency())){
					// 修改抵扣款金额
					finSupplierMoneyDAO.addDeduction(amount, finDeduction.getSupplierId(),finSupplierMoney.getDeductionCurrency());
					// 添加流水记录
					finDeductionDAO.insertFinDeduction(finDeduction);
				}else{
					return -1;
				}
			}
		}
		// 不存在供应商信息时，新增供应商（供应商ID, 抵扣款余额， 抵扣款币种）
		else{
			finSupplierMoneyDAO.insertDeduction(finDeduction.getAmount(), finDeduction.getSupplierId(), finDeduction.getCurrency());
		}
		return 1;
	}

	@Override
	public List<FinDeduction> searchRecord(Map<String, Object> map) {
		return finDeductionDAO.searchRecord(map);
	}

	@Override
	public Long searchRecordCount(Map<String, Object> map) {
		return finDeductionDAO.searchRecordCount(map);
	}

	@Override
	public void insertFinDeduction(FinDeduction finDeduction) {
		finDeductionDAO.insertFinDeduction(finDeduction);
	}
	
}
