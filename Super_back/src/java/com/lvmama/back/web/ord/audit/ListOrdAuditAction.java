package com.lvmama.back.web.ord.audit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderTimeRange;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;

/**
 * @author luo
 * 
 */
public class ListOrdAuditAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy
			.getBean("orderServiceProxy");
	protected Map<String, Object> searchConds = new HashMap<String, Object>();
	private List<OrdOrder> orderList = new ArrayList<OrdOrder>();
	private List<CodeItem> approveStatusList = new ArrayList<CodeItem>();
	private List<CodeItem> paymentStatusList = new ArrayList<CodeItem>();
	private List<CodeItem> orderStatusList = new ArrayList<CodeItem>();
	private String tab;

	/**
	 * 产品服务接口
	 */
	private ProdProductService prodProductService;
	


	public void doBefore() throws Exception {
		this.approveStatusList.add(new CodeItem(null,"全部"));
		this.approveStatusList.addAll(CodeSet.getInstance().getCodeList(
				Constant.CODE_TYPE.ORDER_APPROVE_STATUS.name()));
		this.paymentStatusList.add(new CodeItem(null,"全部"));
		this.paymentStatusList.addAll(CodeSet.getInstance().getCodeList(
				Constant.CODE_TYPE.PAYMENT_STATUS.name()));
		this.orderStatusList.add(new CodeItem(null,"全部"));
		this.orderStatusList.addAll(CodeSet.getInstance().getCodeList(
				Constant.CODE_TYPE.ORDER_STATUS.name()));
		if("RES".equals(tab)){
			Calendar cal = Calendar.getInstance();
	        cal.set(Calendar.HOUR_OF_DAY, 0);
	        cal.set(Calendar.SECOND, 0);
	        cal.set(Calendar.MINUTE, 0);
	        cal.set(Calendar.MILLISECOND, 0);
			searchConds.put("minCreateTime", new Date(cal.getTimeInMillis()));
		}		
	}

	public final void loadDataList() {
		CompositeQuery compositeQuery = initQueryParam();
		initialPageInfo(orderServiceProxy.compositeQueryOrdOrderCount(compositeQuery), compositeQuery);
		orderList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
	}

	private CompositeQuery initQueryParam() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		CompositeQuery compositeQuery = new CompositeQuery();
		OrderIdentity orderIdentity = new OrderIdentity();
		OrderContent orderContent = new OrderContent();
		OrderTimeRange orderTimeRange = new OrderTimeRange();
		OrderStatus orderStatusForQuery = new OrderStatus();

		if ("WAIT".equals(tab)) {
			orderStatusForQuery.setAuditTakenStatus(Constant.AUDIT_TAKEN_STATUS.UNTAKEN.name());
			orderStatusForQuery.setInfoApproveStatus(Constant.INFO_APPROVE_STATUS.UNVERIFIED.name());
			orderStatusForQuery.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
			orderContent.setChannel(Constant.CHANNEL.FRONTEND.name()+","+Constant.CHANNEL.CLIENT.name());
			// 子类型,只在分单中有效
			//orderContent.setSubProductType(subProductTypeString());
		}
		if("SENT".equals(tab)){
			orderStatusForQuery.setAuditTakenStatus(Constant.AUDIT_TAKEN_STATUS.TAKEN.name());
			// 子类型,只在分单中有效
			//orderContent.setSubProductType(subProductTypeString());
			// 显示系统分单
			Object sSystemAudit = searchConds.get("sSystemAudit");
			if (sSystemAudit != null
					&& (Boolean) sSystemAudit) {
				/*String[] assignOperators = new String[2];
				assignOperators[0] = this.getSessionUserName();
				assignOperators[1] = "系统";
				orderContent.setAssignOperators(assignOperators);*/
				orderContent.setAssignOperator("系统");
			}else{
				orderContent.setAssignOperator(this.getSessionUserName());
			}
		}
		if("RES".equals(tab)){
			orderStatusForQuery.setSpecialTakenStatus(Constant.AUDIT_TAKEN_STATUS.UNTAKEN.name());
			orderContent.setProductType(Constant.PRODUCT_TYPE.ROUTE.name());
			orderContent.setPaymentTarget(Constant.PAYMENT_TARGET.TOLVMAMA.name());
			orderContent.setResourceConfirm("false");
			orderStatusForQuery.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
			orderContent.setChannel(Constant.CHANNEL.FRONTEND.name()+","+Constant.CHANNEL.CLIENT.name());
		}

		//根据子类型查询，2011.12.29之前只有分单有，现在大家都有了！
		String subProducts=subProductTypeString();
		if(StringUtils.isNotEmpty(subProducts)){
			orderContent.setSubProductType(subProducts);
		}
		// 订单号
		Object orderId = searchConds.get("orderId");
		if ( orderId != null && !"".equals(orderId)) {
			Long ordId = NumberUtils.toLong(orderId.toString());
			if(ordId>0){
				orderIdentity.setOrderId(ordId);
			}
		}
		// 手机号
		Object mobile = searchConds.get("mobile");
		if (mobile != null && !"".equals(mobile)) {
			orderContent.setContactMobile(((String) mobile).trim());
		}
		// 下单时间
		Object minCreateTime = searchConds.get("minCreateTime");
		if (minCreateTime != null) {
			orderTimeRange.setCreateTimeStart((Date) minCreateTime);
		}
		Object maxCreateTime = searchConds.get("maxCreateTime");
		if (maxCreateTime != null) {
			try {
				Date time = simpleDateFormat.parse(simpleDateFormat.format((Date) maxCreateTime));
				c.setTime(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			c.add(Calendar.DATE, 1);
			orderTimeRange.setCreateTimeEnd(c.getTime());
		}
		//销售产品名称
		Object productName = searchConds.get("productName");
		if(productName != null && !"".equals("productName")){
			List<Long> productIds=prodProductService.selectProductIdsByLikeProductName(((String)productName).trim());
			orderIdentity.setProductIds(productIds);
		}
		// 游玩时间
		Object minVisitTime = searchConds.get("minVisitTime");
		if (minVisitTime != null) {
			orderTimeRange.setOrdOrderVisitTimeStart((Date) minVisitTime);
		}
		Object maxVisitTime = searchConds.get("maxVisitTime");
		if ( maxVisitTime != null) {
			try {
				Date time = simpleDateFormat.parse(simpleDateFormat.format((Date) maxVisitTime));
				c.setTime(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			c.add(Calendar.DATE, 1);
			orderTimeRange.setOrdOrderVisitTimeEnd(c.getTime());
		}
		// 首处理时间
		Object minDealTime = searchConds.get("minDealTime");
		if (minDealTime != null) {
			orderTimeRange.setDealTimeStart((Date)minDealTime);
		}
		Object maxDealTime = searchConds.get("maxDealTime");
		if ( maxDealTime != null) {
			try {
				Date time = simpleDateFormat.parse(simpleDateFormat.format((Date) maxDealTime));
				c.setTime(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			c.add(Calendar.DATE, 1);
			orderTimeRange.setDealTimeEnd(c.getTime());
		}
		// 领单人
		Object takenOperator = searchConds.get("takenOperator");
		if ( takenOperator!= null && !"".equals(takenOperator)) {
			orderContent.setTakenOperator(((String)takenOperator).trim());
		}
		// 审核状态
		Object approveStatus = searchConds.get("approveStatus");
		if (approveStatus != null && !"".equals(approveStatus)) {
			orderStatusForQuery.setOrderApproveStatus(((String)approveStatus).trim());
		}
		
		//订单状态
		Object orderStatus = searchConds.get("orderStatus");
		if (orderStatus != null && !"".equals(orderStatus)) {
			orderStatusForQuery.setOrderStatus(((String)orderStatus).trim());
		}
		
		//支付状态
		Object paymentStatus = searchConds.get("paymentStatus");
		if (paymentStatus != null && !"".equals(paymentStatus)) {
			orderStatusForQuery.setPaymentStatus(((String)paymentStatus).trim());
		}
		compositeQuery.setOrderIdentity(orderIdentity);
		compositeQuery.setOrderTimeRange(orderTimeRange);
		compositeQuery.setContent(orderContent);
		compositeQuery.setStatus(orderStatusForQuery);
		return compositeQuery;
	}

	public void doDeliver(Window win, Set<Listitem> set) throws Exception {
		String url = "";
		orderList = this.getSelectItemList(set);
		if (orderList.size() == 0) {
			alert("请至少选择一个订单项进行分单！");
			return;
		}
		System.out.println(win.getId());
		if("listordauditres".equals(win.getId())){
			url = "/ord/audit/editconfimoperator.zul";
		}else if ("listordauditwait".equals(win.getId())) {
			url = "/ord/audit/editoperator.zul";
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderList", orderList);

		Window window = (Window) Executions.createComponents(url, win, params);
		window.setWidth("300px");
		window.setMaximizable(true);
		window.setClosable(true);
		window.doModal();
	}
	
	public void doRecycle(Set<Listitem> set) throws Exception {
		int res=0;
		orderList = this.getSelectItemList(set);
		if (orderList.size() == 0) {
			alert("请至少选择一个订单项进行回收！");
			return;
		}
		
		for(int i=0;i<orderList.size();i++){
			Map<String, String> params = new HashMap<String, String>();
			params.put("objectId", orderList.get(i).getOrderId().toString());
			params.put("assignUserId", this.getOperatorName());
			if(orderServiceProxy.canRecycle(params)){
				if(orderServiceProxy.cancelOrdOrderAuditByOrderId(this.getOperatorName(), orderList.get(i).getOrderId())){
					res++;
				}
			}else {
				params.put("assignUserId","系统");
				if(orderServiceProxy.canRecycle(params)){
					if(orderServiceProxy.cancelOrdOrderAuditByOrderId(this.getOperatorName(), orderList.get(i).getOrderId())){
						res++;
					}
				}
			}
		}

		if(res>0){
			alert("选择了"+orderList.size()+"条，成功回收"+res+"条");
			this.refreshComponent("search");
		}else{
			alert("因所选订单均已被审核或非分配的订单所以无法回收");
			this.refreshComponent("search");
		}
	}

	public String subProductTypeString() {
		String typeString = "";
		Object sTicket = searchConds.get("sTicket");
		if (sTicket != null
				&& (Boolean) sTicket) {
			typeString += (Constant.SUB_PRODUCT_TYPE.SUIT + ","
					+ Constant.SUB_PRODUCT_TYPE.SINGLE + ","
					+ Constant.SUB_PRODUCT_TYPE.WHOLE + ","
					+ Constant.SUB_PRODUCT_TYPE.UNION + ",");
		}
		Object sHotel = searchConds.get("sHotel");
		if (sHotel != null && (Boolean) sHotel) {
			typeString += (Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT + ","
					+ Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM + ",");
		}
		Object sGroupLong = searchConds.get("sGroupLong");
		if ( sGroupLong != null && (Boolean) sGroupLong) {
			typeString += (Constant.SUB_PRODUCT_TYPE.GROUP_LONG + ",");
		}
		Object sGroup = searchConds.get("sGroup");
		if (sGroup != null && (Boolean)sGroup) {
			typeString += (Constant.SUB_PRODUCT_TYPE.GROUP + ",");
		}
		Object sGroupForeign = searchConds.get("sGroupForeign");
		if (sGroupForeign != null && (Boolean) sGroupForeign) {
			typeString += (Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN + ",");
		}
		Object sFree = searchConds.get("sFree");
		if (sFree != null && (Boolean) sFree) {
			typeString += (Constant.SUB_PRODUCT_TYPE.FREENESS + ",");
		}
		Object sFreeForeign = searchConds.get("sFreeForeign");
		if (sFreeForeign != null && (Boolean) sFreeForeign) {
			typeString += (Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN + ",");
		}
		Object sFreeLong = searchConds.get("sFreeLong");
		if (sFreeLong != null && (Boolean) sFreeLong) {
			typeString += (Constant.SUB_PRODUCT_TYPE.FREENESS_LONG + ",");
		}
		Object sHelpSelfBus = searchConds.get("sHelpSelfBus");
		if (sHelpSelfBus != null && (Boolean) sHelpSelfBus) {
			typeString += (Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS + ",");
		}

		return typeString;
	}

	private List<OrdOrder> getSelectItemList(Set<Listitem> set) {
		List<OrdOrder> list = new ArrayList<OrdOrder>();
		if (set != null && set.size() > 0) {
			for (Iterator<Listitem> iter = set.iterator(); iter.hasNext();) {
				Listitem listitem = (Listitem) iter.next();
				OrdOrder order = (OrdOrder) listitem.getValue();
				list.add(order);
			}
		}
		return list;
	}
	
	public void searchList() {
		CompositeQuery compositeQuery = initQueryParam();
		OrderContent orderContent = compositeQuery.getContent();
		orderContent.setTakenOperator(this.getOperatorName());
		compositeQuery.setContent(orderContent);
		
		initialPageInfo(orderServiceProxy.compositeQueryOrdOrderCount(compositeQuery), compositeQuery);
		orderList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
	}

	public void changeStatus(String status) {
		this.searchConds.put("approveStatus", status);
	}
	
	public void changePaymentStatus(String status) {
		this.searchConds.put("paymentStatus", status);
	}

	public void changeOrderStatus(String status) {
		this.searchConds.put("orderStatus", status);
	}
	
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public Map<String, Object> getSearchConds() {
		return searchConds;
	}

	public void setSearchConds(Map<String, Object> searchConds) {
		this.searchConds = searchConds;
	}

	public List<OrdOrder> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrdOrder> orderList) {
		this.orderList = orderList;
	}

	public List<CodeItem> getApproveStatusList() {
		return approveStatusList;
	}

	public void setApproveStatusList(List<CodeItem> approveStatusList) {
		this.approveStatusList = approveStatusList;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public List<CodeItem> getPaymentStatusList() {
		return paymentStatusList;
	}

	public void setPaymentStatusList(List<CodeItem> paymentStatusList) {
		this.paymentStatusList = paymentStatusList;
	}

	public List<CodeItem> getOrderStatusList() {
		return orderStatusList;
	}

	public void setOrderStatusList(List<CodeItem> orderStatusList) {
		this.orderStatusList = orderStatusList;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
 
}
