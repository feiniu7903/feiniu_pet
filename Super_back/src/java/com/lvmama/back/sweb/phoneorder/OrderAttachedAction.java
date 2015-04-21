/**
 * 
 */
package com.lvmama.back.sweb.phoneorder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.ResultHandle;

/**
 * 订单附属action请求处理
 * @author yangbin
 *
 */
public class OrderAttachedAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5663446299213291324L;
	private OrderService orderServiceProxy;
	private Long id;
	private Long mainProdBranchId;
	private int quantity;
	private Date visitTime;
	
	/**
	 * 库存检查
	 */
	@Action("/phoneOrder/orderCheckStock")
	public void checkStock(){
		JSONResult result = new JSONResult();
		BuyInfo buyInfo = new BuyInfo();
		List<BuyInfo.Item> itemList = new ArrayList<BuyInfo.Item>();
		BuyInfo.Item item = new BuyInfo.Item();
		item.setProductBranchId(mainProdBranchId);
		item.setQuantity(quantity);
		item.setVisitTime(visitTime);
		item.setIsDefault("true");
		item.setProductId(id);
		itemList.add(item);
		buyInfo.setItemList(itemList);
		ResultHandle handle = orderServiceProxy.checkOrderStock(buyInfo);
		if(handle.isFail()){
			result.raise(handle.getMsg());
		}
		result.output(getResponse());
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	

	public void setMainProdBranchId(Long mainProdBranchId) {
		this.mainProdBranchId = mainProdBranchId;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
