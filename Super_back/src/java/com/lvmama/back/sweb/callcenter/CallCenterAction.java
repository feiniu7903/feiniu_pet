package com.lvmama.back.sweb.callcenter; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.SortTypeEnum;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 来电弹屏.	<br/>
 * 提供给驴妈妈华为呼叫中心来电弹屏页面,同样对应super后台下单页面的"来电注册"页面.<br/>
 * 主要功能:<br/>
 * 1.根据用户手机号等信息加载用户的相关信息,<br/>
 * 2.在此过程中会核实用户的信息,<br/>
 * 3.核实通过后查询出所关联的订单信息,<br/>
 * 4.提供注册功能,<br/>
 */
@ParentPackage("json-default")
public class CallCenterAction extends BaseAction {
	private static final long serialVersionUID = 1992859771997204710L;
	private String userId;
	/**
	 * 订单服务接口
	 */
	private OrderService orderServiceProxy;
  
	/**
	 * 订单服务接口.
	 */
	private List<OrdOrder> orderList;
	
	/**
	 * 已完成订单数
	 * */
	private Long finishedOrdersCount;
	
	/**
	 * 已完成订单总金额
	 * */
	private float finishedOrdersAmount;
	
	private Map<String,Object> orderInfoMap;
	/**
	 * 初始化订单信息
	 * 
	 * @author shihui
	 * */
	@Action(value = "/call/queryOrder", results = @Result(type = "json", name = "json", params = {
			"includeProperties", "orderInfoMap.*" }))
	public String initOrderInfo() {
			if(StringUtils.isNotEmpty(userId)) {
				CompositeQuery compositeQuery = new CompositeQuery();
				OrderIdentity orderIdentity = new OrderIdentity();
				orderIdentity.setUserId(userId);
				
				PageIndex pageIndex = new PageIndex();
				pageIndex.setEndIndex(5);
				
				List<SortTypeEnum> typeList = new ArrayList<SortTypeEnum>();
				typeList.add(CompositeQuery.SortTypeEnum.CREATE_TIME_DESC);
				
				compositeQuery.setOrderIdentity(orderIdentity);
				compositeQuery.setTypeList(typeList);
				compositeQuery.setPageIndex(pageIndex);
				compositeQuery.getQueryFlag().setQuerySupplier(false);
				compositeQuery.getQueryFlag().setQueryUser(false);
				orderList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
				
				OrderStatus orderStatus = new OrderStatus();
				orderStatus.setOrderStatus(Constant.ORDER_STATUS.FINISHED.toString());
				compositeQuery.setStatus(orderStatus);
				compositeQuery.setPageIndex(null);
				finishedOrdersCount = orderServiceProxy.compositeQueryOrdOrderCount(compositeQuery);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("userId",  userId);
				params.put("status", Constant.ORDER_STATUS.FINISHED.name());
				finishedOrdersAmount = orderServiceProxy.queryOrdersAmountByParams(params);
				
				this.orderInfoMap = new HashMap<String, Object>();
				this.orderInfoMap.put("finishedOrdersCount", finishedOrdersCount);
				this.orderInfoMap.put("finishedOrdersAmount", PriceUtil.convertToYuan((long)finishedOrdersAmount));
				this.orderInfoMap.put("orderList", converOrder(orderList));
			}
		return "json";
	}
	
	private JSONArray converOrder(List<OrdOrder> orderList){
		JSONArray array=new JSONArray();
		for(OrdOrder order:orderList){
			JSONObject obj=new JSONObject();
			obj.put("orderId", order.getOrderId());
			obj.put("zhCreateTime", order.getZhCreateTime());
			obj.put("actualPayYuan", order.getActualPayYuan());
			if(null!=order.getContact()){
				obj.put("contactMobileNo", order.getContact().getMobile());
			}
			OrdOrderItemProd mainProduct=order.getMainProduct();
			JSONObject mp=new JSONObject();
			mp.put("quantity", mainProduct.getQuantity());
			mp.put("productName", mainProduct.getProductName());
			obj.put("mainProduct", mp);
			array.add(obj);
		}
		return array;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @param orderServiceProxy the orderServiceProxy to set
	 */
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	/**
	 * @return the orderInfoMap
	 */
	public Map<String, Object> getOrderInfoMap() {
		return orderInfoMap;
	}
 
	
}
