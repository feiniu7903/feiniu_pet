package com.lvmama.finance.settlement.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.vo.Currency;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.settlement.ibatis.po.OrdSettlementPayment;

public interface OrdSettlementPaymentService {
	Page<OrdSettlementPayment> getOrdSettlementPayments(Map<String, Object> map);
	/**
	 * 查询所有的币种
	 * @return
	 */
	List<Currency> searchAllCurrency();
}
