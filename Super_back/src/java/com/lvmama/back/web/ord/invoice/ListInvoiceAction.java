package com.lvmama.back.web.ord.invoice;

import java.util.HashMap;
import java.util.List;

import org.zkoss.zul.A;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;

//该类已经不使用 
@Deprecated
public class ListInvoiceAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderService orderServiceProxy = (OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	private List<OrdInvoice> ordInvoiceList;
	private HashMap<Object,Object> queryParm = new HashMap<Object,Object>();
	
	
	
	public void loadDataList(){
		CompositeQuery compositeQuery =new CompositeQuery();
		compositeQuery.getInvoiceRelate().setOrderId(queryParm.get("orderId")==null?null:(Long)queryParm.get("orderId"));
		compositeQuery.getInvoiceRelate().setInvoiceNo(queryParm.get("invoiceNo")==null?null:(String)queryParm.get("invoiceNo"));
		compositeQuery.getInvoiceRelate().setUserId(queryParm.get("userId")==null?null:(String)queryParm.get("userId"));
		compositeQuery.getInvoiceRelate().setCompanyId(queryParm.get("settlementCommpany")==null?null:(String)queryParm.get("settlementCommpany"));
		//compositeQuery.getInvoiceRelate().setUserId(queryParm.get("settlementCommpany")==null?null:(String)queryParm.get("settlementCommpany"));
		
		
		initialPageInfo(orderServiceProxy.queryOrdInvoiceCount(compositeQuery),compositeQuery);
		ordInvoiceList = orderServiceProxy.queryOrdInvoice(compositeQuery);
	}
	@Deprecated
	public void cancelInvoice(A a){
		OrdInvoice ordInvoice = (OrdInvoice)a.getAttribute("invoice");
		orderServiceProxy.update(Constant.INVOICE_STATUS.CANCEL.name(), ordInvoice.getInvoiceId(), this.getSessionUserName());
		this.refreshComponent("search");
	}
	

	public List<OrdInvoice> getOrdInvoiceList() {
		return ordInvoiceList;
	}

	public HashMap<Object, Object> getQueryParm() {
		return queryParm;
	}


}