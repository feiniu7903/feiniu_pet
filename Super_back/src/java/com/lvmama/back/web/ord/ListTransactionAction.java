package com.lvmama.back.web.ord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.pay.PayTransaction;
import com.lvmama.comm.pet.service.pay.PayPaymentService;

public class ListTransactionAction extends BaseAction {

	private PayPaymentService payPaymentService; 
	private Map<String, Object> searchParams = new HashMap();
	private List<PayTransaction> transList = new ArrayList<PayTransaction>();

	@Override
	protected void doAfter() throws Exception {
	}

	public void loadDataList() {
		Map map = initialPageInfoByMap(200l, searchParams);
		searchParams.put("skipResults", map.get("skipResults"));
		searchParams.put("maxResults", map.get("maxResults"));
		searchParams.put("orderBy", "TRANSACTION_ID desc");
		this.transList = payPaymentService.selectByParams(searchParams);
	}

	public Map getSearchParams() {
		return searchParams;
	}

	public List<PayTransaction> getTransList() {
		return transList;
	}

	public void setTransList(List<PayTransaction> transList) {
		this.transList = transList;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

}
