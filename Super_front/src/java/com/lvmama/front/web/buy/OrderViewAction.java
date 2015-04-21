package com.lvmama.front.web.buy;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.front.web.BaseAction;

public class OrderViewAction  extends BaseAction{
	private static final long serialVersionUID = 5292464101224568056L;
	protected transient OrderService orderServiceProxy;
	protected Long orderId;
	//主产品
	protected List<OrdOrderItemProd> mainOrderList = new ArrayList<OrdOrderItemProd>();
	//相关产品
	protected List<OrdOrderItemProd> relativeOrderList = new ArrayList<OrdOrderItemProd>();
	//附加产品
	protected List<OrdOrderItemProd> additionalOrderList = new ArrayList<OrdOrderItemProd>();
	protected OrdOrder order;
	protected ViewPage viewPage;
	protected PageService pageService;
	Long id;
	protected CashAccountVO moneyAccount;
	transient CashAccountService cashAccountService;
	private Integer days;
	private String leaveTime;

	public String orderView() throws Exception {
		    order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		    if (null != order && order.isPaymentSucc()) return "toUserOrder";
		    if ( order==null || null == this.getBookerUserId() || !this.getBookerUserId().equals(order.getUserId()) ) {
		    	LOG.info("订单"+orderId+"被userId="+this.getUserId()+"非法访问");
		    	return "error";
		    }
		    moneyAccount = cashAccountService.queryMoneyAccountByUserNo(this.getUserId());
		    List<OrdOrderItemProd> mainOrdProd = order.getOrdOrderItemProds();
		    OrdOrderItemProd orderMainProduct=order.getMainProduct();
		    for (int i = 0; i < mainOrdProd.size(); i++) {
				OrdOrderItemProd itemProd = mainOrdProd.get(i);
				if(itemProd.hasDefault()||orderMainProduct.getOrderItemProdId().equals(itemProd.getOrderItemProdId())){
					//主产品
					mainOrderList.add(itemProd);
					this.id = itemProd.getProductId();
				}else if(!itemProd.isAdditionalProduct()){
					//相关产品
					relativeOrderList.add(itemProd);
					if(id!=null){
						this.id = itemProd.getProductId();
					}
				}else {
					//附加产品
					additionalOrderList.add(itemProd);
				}
			}
		    if(id!=null){
		    	viewPage = pageService.getViewPageByproductId(id);
		    }
		    return "orderView";
	}

	public OrdOrder getOrder() {
		return order;
	}

	public void setOrder(OrdOrder order) {
		this.order = order;
	}
 
	public ViewPage getViewPage() {
		return viewPage;
	}

	public void setViewPage(ViewPage viewPage) {
		this.viewPage = viewPage;
	}

	public PageService getPageService() {
		return pageService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}
	public List<OrdOrderItemProd> getMainOrderList() {
		return mainOrderList;
	}

	public void setMainOrderList(List<OrdOrderItemProd> mainOrderList) {
		this.mainOrderList = mainOrderList;
	}

	public List<OrdOrderItemProd> getAdditionalOrderList() {
		return additionalOrderList;
	}

	public void setAdditionalOrderList(List<OrdOrderItemProd> additionalOrderList) {
		this.additionalOrderList = additionalOrderList;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}


	public List<OrdOrderItemProd> getRelativeOrderList() {
		return relativeOrderList;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	public CashAccountVO getMoneyAccount() {
		return moneyAccount;
	}

	public void setMoneyAccount(CashAccountVO moneyAccount) {
		this.moneyAccount = moneyAccount;
	}

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}	
}
