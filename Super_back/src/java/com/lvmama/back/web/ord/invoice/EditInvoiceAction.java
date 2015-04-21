package com.lvmama.back.web.ord.invoice;

import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Paging;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.spring.SpringBeanProxy;


@Deprecated
public class EditInvoiceAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private OrderService orderServiceProxy = (OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	private OrdInvoice ordInvoice;
	
	protected void doBefore() throws Exception {
		ordInvoice = (OrdInvoice)Executions.getCurrent().getArg().get("invoice");
	}
	
	@Deprecated
	public void save(){
		if (ordInvoice.getInvoiceNo()==null||ordInvoice.getInvoiceNo().equals("")){
			alert("发票单号不能为空");
			return;
		}
		ordInvoice.setBillDate(new Date());
		orderServiceProxy.updateWithDate(ordInvoice.getInvoiceNo(),ordInvoice.getBillDate(), ordInvoice.getInvoiceId(), this.getSessionUserName());
		
		//刷新父窗口
		Component c = this.getComponent().getParent();
		Paging b = (Paging) c.getFellow("_paging");
		Events.sendEvent(new Event("onPaging",b));
		
		this.closeWindow();
	}

	public OrdInvoice getOrdInvoice() {
		return ordInvoice;
	}

}
