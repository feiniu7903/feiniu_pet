package com.lvmama.front.web.myspace;

/**
 * 攻略行程调用
 * @author shangzhengyuan
 *
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.ExcludedContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.SortTypeEnum;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

@Results({
	@Result(name = "myTrip", location = "/WEB-INF/pages/myspace/sub/trip.ftl", type = "freemarker")
})
public class MyTripAction extends SpaceBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3843051892555764781L;
	
	
	private OrderService orderServiceProxy;
	private ProdProductPlaceService prodProductPlaceService;
	private List<OrdOrder> orderList;
	
	@Action(value="/myspace/share/trip")
	public String execute(){
		if(!isLogin()){
			return LOGIN;
		}
		getUserOrder(getUserId());
		return "myTrip";
	}
	@Action(value="/mmyspace/share/trip/allorder")
	public void allorder() throws IOException {
		if(null == this.getRequest().getParameter("userId")){
			return;
		}
		getUserOrder((String) this.getRequest().getParameter("userId"));
//		getUserOrder("5ad32f1a1ccdf220011ccfc756ab0012");
		StringBuffer sb = new StringBuffer();
		if(null!=orderList && !orderList.isEmpty()){
			sb.append("{");
			sb.append("\"count\":\""+orderList.size());
			sb.append("\",\"data\":[");
			for(OrdOrder order:orderList){
				sb.append("{\"orderId\":").append("\""+order.getOrderId()+"\"");
				sb.append(",\"productId\":").append("\""+order.getMainProduct().getProductId()+"\"");
				sb.append(",\"title\":").append("\""+order.getMainProduct().getProductName()+"\"");
				sb.append(",\"visitTime\":").append("\""+DateUtil.formatDate(order.getVisitTime(), "yyyy-MM-dd")+"\"");
				sb.append(",\"days\":").append("\""+order.getMainProduct().getDays()+"\"");
				sb.append(",\"cost\":").append("\""+order.getActualPay()+"\"");
				sb.append(",\"mark\":").append("["+order.getMainProduct().getWrapPage()+"]},");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("]");
			sb.append("}");
		}else{
			sb.append("{\"count\":\"0\",data:[]}");
		}
		printRtn(getRequest(), getResponse(), sb.toString());
	}
	
	@Action(value="/mmyspace/share/trip/getUserAllorder")
	public void allorder2() throws IOException {
		if(null == this.getRequest().getParameter("userId")){
			return;
		}
		getUserOrderByUserId((String) this.getRequest().getParameter("userId"));
		StringBuffer sb = new StringBuffer();
		if(null!=orderList && !orderList.isEmpty()){
			sb.append("{");
			sb.append("\"count\":\""+orderList.size());
			sb.append("\",\"data\":[");
			for(OrdOrder order:orderList){
				sb.append("{\"orderId\":").append("\""+order.getOrderId()+"\"");
				sb.append(",\"productId\":").append("\""+order.getMainProduct().getProductId()+"\"");
				sb.append(",\"title\":").append("\""+order.getMainProduct().getProductName()+"\"");
				sb.append(",\"visitTime\":").append("\""+DateUtil.formatDate(order.getVisitTime(), "yyyy-MM-dd")+"\"");
				sb.append(",\"days\":").append("\""+order.getMainProduct().getDays()+"\"");
				sb.append(",\"cost\":").append("\""+order.getActualPay()+"\"");
				sb.append(",\"orderStatus\":").append("\""+order.getOrderStatus()+"\"");
				sb.append(",\"paymentStatus\":").append("\""+order.getPaymentStatus()+"\"");
				sb.append(",\"createTime\":").append("\""+DateUtil.formatDate(order.getCreateTime(),DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss)+"\"");
				sb.append(",\"mark\":").append("["+order.getMainProduct().getWrapPage()+"]},");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("]");
			sb.append("}");
		}else{
			sb.append("{\"count\":\"0\",data:[]}");
		}
		printRtn(getRequest(), getResponse(), sb.toString());
	}
	
	private void printRtn(final HttpServletRequest request,
			final HttpServletResponse response, final String bean) throws IOException {
		response.setContentType("text/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		if (request.getParameter("jsoncallback") == null) {
			response.getWriter().print(JSONObject.fromObject(bean));
		} else {
			getResponse().getWriter().print(getRequest().getParameter(
					"jsoncallback") + "(" + JSONObject.fromObject(bean) + ")");
		}
	}	
	
	private void getUserOrder(final String userId){
		OrderContent orderContent = new OrderContent();
		orderContent.setUserId(userId);
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.setContent(orderContent);
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.getCode());
		
		compositeQuery.setStatus(orderStatus);
		List<SortTypeEnum> sort = new ArrayList<SortTypeEnum>();
		sort.add(SortTypeEnum.CREATE_TIME_DESC);
		compositeQuery.setTypeList(sort);
		PageIndex pageIndex = new PageIndex();
		pageIndex.setBeginIndex(0);
		pageIndex.setEndIndex(1000);
		compositeQuery.setTypeList(sort);
		compositeQuery.setPageIndex(pageIndex);
		ExcludedContent excludedContent = new ExcludedContent();
		excludedContent.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
		compositeQuery.setExcludedContent(excludedContent);
		orderList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
		if(null!=orderList && !orderList.isEmpty()){
			Map<Long,OrdOrder> map = new HashMap<Long,OrdOrder>();
			for(OrdOrder order:orderList){
				OrdOrderItemProd product = order.getMainProduct();
				Long productId = product.getProductId();
				Date visitDate = order.getVisitTime();
				Date currentDate = DateUtil.accurateToDay(new Date());
				if((null==visitDate || (null!=visitDate && currentDate.after(visitDate))) && !map.containsKey(productId)){
					map.put(productId, order);
					List<ProdProductPlace> placeList = prodProductPlaceService.selectByProductId(productId);
//					String productName = product.getProductName();
					StringBuffer sb = new StringBuffer();
					if(null!=placeList && !placeList.isEmpty()){
						for(ProdProductPlace place:placeList){
							sb.append(",{\"id\":\"").append(place.getPlaceId()).append("\",\"name\":\"").append(place.getPlaceName()).append("\"}");
//							if("true".equals(place.getTo())){
//								if(Constant.PRODUCT_TYPE.TICKET.name().equals(product.getProductType())){
//									productName = place.getPlaceName()+"1日游";
//								}else if(Constant.PRODUCT_TYPE.HOTEL.name().equals(product.getProductType())){
//									productName = place.getPlaceName()+product.getDays()+"日游";
//								}else if(Constant.PRODUCT_TYPE.ROUTE.name().equals(product.getProductType())){
//									productName = place.getPlaceName()+product.getDays()+"日游";
//								}
//							}
						}
					}
					product.setProductName(product.getProductName().replaceAll("\"", "").replaceAll("\'", ""));
					product.setWrapPage(sb.toString().replaceFirst(",", ""));
				}
			}
			orderList.clear();
			orderList.addAll(map.values());
		}
	}
	
	private void getUserOrderByUserId(final String userId){
		OrderContent orderContent = new OrderContent();
		orderContent.setUserId(userId);
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.setContent(orderContent);
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.getCode());
		
		compositeQuery.setStatus(orderStatus);
		List<SortTypeEnum> sort = new ArrayList<SortTypeEnum>();
		sort.add(SortTypeEnum.CREATE_TIME_DESC);
		compositeQuery.setTypeList(sort);
		PageIndex pageIndex = new PageIndex();
		pageIndex.setBeginIndex(0);
		pageIndex.setEndIndex(1000);
		compositeQuery.setTypeList(sort);
		compositeQuery.setPageIndex(pageIndex);
		ExcludedContent excludedContent = new ExcludedContent();
		excludedContent.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
		compositeQuery.setExcludedContent(excludedContent);
		orderList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
		if(null!=orderList && !orderList.isEmpty()){
			Map<Long,OrdOrder> map = new HashMap<Long,OrdOrder>();
			for(OrdOrder order:orderList){
				OrdOrderItemProd product = order.getMainProduct();
				Long productId = product.getProductId();
				if(!map.containsKey(productId)){
					map.put(productId, order);
					List<ProdProductPlace> placeList = prodProductPlaceService.selectByProductId(productId);
					String productName = product.getProductName();
					StringBuffer sb = new StringBuffer();
					if(null!=placeList && !placeList.isEmpty()){
						for(ProdProductPlace place:placeList){
							sb.append(",{\"id\":\"").append(place.getPlaceId()).append("\",\"name\":\"").append(place.getPlaceName()).append("\"}");
							if("true".equals(place.getTo())){
								if(Constant.PRODUCT_TYPE.TICKET.name().equals(product.getProductType())){
									productName = place.getPlaceName()+"1日游";
								}else if(Constant.PRODUCT_TYPE.HOTEL.name().equals(product.getProductType())){
									productName = place.getPlaceName()+product.getDays()+"日游";
								}else if(Constant.PRODUCT_TYPE.ROUTE.name().equals(product.getProductType())){
									productName = place.getPlaceName()+product.getDays()+"日游";
								}
							}
						}
					}
					product.setProductName(productName);
					product.setWrapPage(sb.toString().replaceFirst(",", ""));
				}
			}
			orderList.clear();
			orderList.addAll(map.values());
		}
	}
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public List<OrdOrder> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<OrdOrder> orderList) {
		this.orderList = orderList;
	}
	public ProdProductPlaceService getProdProductPlaceService() {
		return prodProductPlaceService;
	}
	public void setProdProductPlaceService(
			ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}

}
