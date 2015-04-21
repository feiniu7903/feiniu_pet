package com.lvmama.back.web.ord.invoice;

import org.zkoss.zk.ui.Executions;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.spring.SpringBeanProxy;

public class ViewInvoiceAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private OrderService orderServiceProxy = (OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	private OrdInvoice ordInvoice;
	
	protected void doBefore() throws Exception {
		ordInvoice = (OrdInvoice)Executions.getCurrent().getArg().get("invoice");
	}
	
	public OrdInvoice getOrdInvoice() {
		return ordInvoice;
	}

}
