package com.lvmama.back.sweb.ord;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.ORDER_STATUS;
import com.lvmama.comm.vo.Constant.PAYMENT_TARGET;
@ParentPackage("json-default")
@Results( { @Result(name = "order_approve_area", location = "/WEB-INF/pages/back/ord/order_approve_area.jsp") })

public class OrderApproveAreaAction extends BaseAction {
	private UserUserProxy userUserProxy;
	private CompositeQuery compositeQuery;
	/**
	 * 订单服务接口
	 */
	private OrderService orderServiceProxy;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 我的审核任务订单集合列表
	 */
	private List<OrdOrder> ordersList;
	/**
	 * 查询参数
	 */
	private HashMap<String,Object> queryParms = new HashMap<String,Object>();
	/**
	 * 废单原因
	 */
	private List<CodeItem> cancelReasons;
	/**
	 * 废单原因
	 * */
	private String cancelReason;
	private java.text.SimpleDateFormat dateFormat=new java.text.SimpleDateFormat("yyyy-MM-dd");
	@Action("/ord/order_approve_area")
	public String execute() {
		this.cancelReasons = CodeSet.getInstance().getCachedCodeList(
				Constant.CODE_TYPE.ORD_CANCEL_REASON.name());
		return "order_approve_area";
	}
	
	public String doQuery() {
		try {
			ordersList = new ArrayList<OrdOrder>();
			// 使用参数queryParms,查询所有的景区付款且状态正常的订单,存入ordersList
			compositeQuery = new CompositeQuery();
			if (queryParms.get("orderId")!=null&&!queryParms.get("orderId").equals("")){
				compositeQuery.getOrderIdentity().setOrderId(new Long(((String)queryParms.get("orderId")).trim()));
			}
			if (queryParms.get("userId")!=null){
				compositeQuery.getContent().setUserName((String)queryParms.get("userId"));
			}
			if (queryParms.get("visitTimeB")!=null&&!queryParms.get("visitTimeB").equals("")){
				compositeQuery.getOrderTimeRange().setOrdOrderVisitTimeStart(dateFormat.parse((String)queryParms.get("visitTimeB")));
			}
			if (queryParms.get("visitTimeE")!=null&&!queryParms.get("visitTimeE").equals("")){
				compositeQuery.getOrderTimeRange().setOrdOrderVisitTimeEnd(new Date(dateFormat.parse((String)queryParms.get("visitTimeE")).getTime()+1000*60*60*24-1));
			}
			if (queryParms.get("createTimeB")!=null&&!queryParms.get("createTimeB").equals("")){
				compositeQuery.getOrderTimeRange().setCreateTimeStart(dateFormat.parse((String)queryParms.get("createTimeB")));
			}
			if (queryParms.get("createTimeE")!=null&&!queryParms.get("createTimeE").equals("")){
				compositeQuery.getOrderTimeRange().setCreateTimeEnd(new Date(dateFormat.parse((String)queryParms.get("createTimeE")).getTime()+1000*60*60*24-1));
			}
			
			compositeQuery.getContent().setPaymentTarget(PAYMENT_TARGET.TOSUPPLIER.name());
			compositeQuery.getStatus().setOrderStatus(ORDER_STATUS.NORMAL.name());
			//将用户名Email等转为IdList形式查询
			Map<String, Object> param = compositeQuery.getContent().getUserParam(compositeQuery.getContent());
			if(param != null) {
				compositeQuery.getContent().setUserIdList(compositeQuery.getContent().getUserList(userUserProxy.getUsers(param)));
			}
			Long totalRecords = orderServiceProxy
			.compositeQueryOrdOrderCount(compositeQuery);
	pagination = initPagination();
	pagination.setTotalRecords(totalRecords);
	PageIndex pageIndex =new PageIndex();
	pageIndex.setBeginIndex(pagination.getFirstRow());
	pageIndex.setEndIndex(pagination.getLastRow());
	pagination
			.setActionUrl(initPageActionUrl("ord/order_approve_area!doQuery.do?"));
	compositeQuery.setPageIndex(pageIndex);
//	
//	
			ordersList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
			this.cancelReasons = CodeSet.getInstance().getCachedCodeList(
					Constant.CODE_TYPE.ORD_CANCEL_REASON.name());
			return "order_approve_area";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}
	private String nvl(String key){
		Object obj = queryParms.get(key);
		if (obj!=null) return "&"+key+"="+obj.toString();
		else return "";
	}
	public String initPageActionUrl(String actionUrl) {
		
		String url = actionUrl;
		url+=nvl("orderId");
		url+=nvl("userId");
		url+=nvl("visitTimeB");
		url+=nvl("visitTimeE");
		url+=nvl("createTimeB");
		url+=nvl("createTimeE");
		
		return url;
	}
	
	public String cancelOrder(){
		
		if (queryParms.get("cbx")!=null){
			String[] orderIds=((String)queryParms.get("cbx")).split(",");
			for ( int i=0;i<orderIds.length;i++){
				orderServiceProxy.cancelOrder(new Long(orderIds[i]),cancelReason, getOperatorName());
			}
			
		}
		doQuery();
		return "order_approve_area";
	}
	public HashMap<String, Object> getQueryParms() {
		return queryParms;
	}
	public void setQueryParms(HashMap<String, Object> queryParms) {
		this.queryParms = queryParms;
	}

	public List<OrdOrder> getOrdersList() {
		return ordersList;
	}

	public void setCompositeQuery(CompositeQuery compositeQuery) {
		this.compositeQuery = compositeQuery;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public List<CodeItem> getCancelReasons() {
		return cancelReasons;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
}
