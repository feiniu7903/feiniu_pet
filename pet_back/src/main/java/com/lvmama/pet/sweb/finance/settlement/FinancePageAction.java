package com.lvmama.pet.sweb.finance.settlement;

import java.util.Map;

import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.sweb.fin.common.FinPageAction;

public class FinancePageAction extends FinPageAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1977484870087522770L;
	@Override
	public Map<String, Object> initRequestParameter() {
		return super.initSearchParameter();
	}
	public String getBusinessName() {
		return Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name();
	}
}
