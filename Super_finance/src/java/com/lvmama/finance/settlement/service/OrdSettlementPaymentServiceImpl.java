package com.lvmama.finance.settlement.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.vo.Currency;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.settlement.ibatis.dao.OrdSettlementPaymentDAO;
import com.lvmama.finance.settlement.ibatis.po.OrdSettlementPayment;

@Service
public class OrdSettlementPaymentServiceImpl implements OrdSettlementPaymentService{
	@Autowired
	private OrdSettlementPaymentDAO ordSettlementPaymentDAO;
	
	public Page<OrdSettlementPayment> getOrdSettlementPayments(Map<String, Object> map){
		return ordSettlementPaymentDAO.getOrdSettlementPayments(map);
	}

	@Override
	public List<Currency> searchAllCurrency() {
		return ordSettlementPaymentDAO.searchAllCurrency();
	}
}
