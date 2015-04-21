package com.lvmama.back.web.bank;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.spring.SpringBeanProxy;

public class EditBankAciton extends BaseAction{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9077472360726458254L;
	private Map searchParams = new HashMap();
	private Long bankId;
	private OrderService orderServiceProxy = (OrderService)SpringBeanProxy.getBean("orderServiceProxy");


	protected void doBefore() throws Exception {
		if(bankId>0){
			
		}

	}
	
	protected void submit() throws Exception{
		
	}

	public Map getSearchParams() {
		return searchParams;
	}

	public void setSearchParams(Map searchParams) {
		this.searchParams = searchParams;
	}

	

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

}
