package com.lvmama.front.web.usr;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.front.web.BaseAction;

@Results( {
	@Result(name = "success", location = "/WEB-INF/pages/usr/usrordertopay.ftl", type = "freemarker")
})
public class ToPayUsrOrderAction extends BaseAction {

	private OrderService orderServiceProxy;
	private Long orderId;
	private OrdOrder order;
	//主产品
	private List<OrdOrderItemProd> mainOrderList = new ArrayList<OrdOrderItemProd>();
	//相关产品
	private List<OrdOrderItemProd> relativeOrderList = new ArrayList<OrdOrderItemProd>();
	//附加产品
	private List<OrdOrderItemProd> additionalOrderList = new ArrayList<OrdOrderItemProd>();
	private CashAccountService cashAccountService;
	private CashAccountVO moneyAccount;

	/**
	 * 拉卡拉URL.
	 */
	private String lakalaURL;


	@Action("/usr/ordertopay")
	public String execute() {
		if(this.getUserId()==null){
			return LOGIN;
		}
		order = this.orderServiceProxy.queryOrdOrderByOrderId(orderId);
	    if ( order==null || !super.getBookerUserId().equalsIgnoreCase(order.getUserId()) ) {
	    	return "error";
	    }
	    initMainOrdProduct(this.order);
		return SUCCESS;
	}

	private void initMainOrdProduct(OrdOrder order) {
		List<OrdOrderItemProd> mainOrdProd = order.getOrdOrderItemProds();
	    moneyAccount = cashAccountService.queryMoneyAccountByUserNo(this.getUserId());
	    OrdOrderItemProd orderMainProduct=order.getMainProduct();
	    for (int i = 0; i < mainOrdProd.size(); i++) {
			OrdOrderItemProd itemProd = mainOrdProd.get(i);
			if(itemProd.hasDefault()||orderMainProduct.getOrderItemProdId().equals(itemProd.getOrderItemProdId())){
				//主产品
				mainOrderList.add(itemProd);
			}else if(!itemProd.isAdditionalProduct()){
				//相关产品
				relativeOrderList.add(itemProd);
			}else {
				//附加产品
				additionalOrderList.add(itemProd);
			}
		}
	}
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public OrdOrder getOrder() {
		return order;
	}

	public List<OrdOrderItemProd> getMainOrderList() {
		return mainOrderList;
	}

	public List<OrdOrderItemProd> getRelativeOrderList() {
		return relativeOrderList;
	}

	public List<OrdOrderItemProd> getAdditionalOrderList() {
		return additionalOrderList;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	/**
	 * getLakalaURL.
	 * 
	 * @return 拉卡拉URL
	 */
	public String getLakalaURL() {
		return lakalaURL;
	}

	/**
	 * setLakalaURL.
	 * 
	 * @param lakalaURL
	 *            拉卡拉URL
	 */
	public void setLakalaURL(final String lakalaURL) {
		this.lakalaURL = lakalaURL;
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
