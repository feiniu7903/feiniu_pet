package com.lvmama.back.sweb.op;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderRoute;
import com.lvmama.comm.bee.po.ord.OrdOrderSum;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderTimeRange;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.SortTypeEnum;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.vo.Constant;

/**
 * 根据团号查看属于该团的订单.
 * @author liwenzhan.
 * @version 1.0 2011-9-21.
 * 
 * @see com.lvmama.back.sweb.BaseAction;
 * @see com.lvmama.back.utils.WebUtils;
 * @see com.lvmama.common.ord.po.OrdOrder;
 * @see com.lvmama.common.ord.service.OrderService;
 * @see com.lvmama.common.ord.service.po.CompositeQuery;
 * @see com.lvmama.common.ord.service.po.CompositeQuery.OrderContent;
 * @see com.lvmama.common.ord.service.po.CompositeQuery.OrderIdentity;
 * @see com.lvmama.common.ord.service.po.CompositeQuery.OrderStatus;
 * @see com.lvmama.common.ord.service.po.CompositeQuery.OrderTimeRange;
 * @see com.lvmama.common.ord.service.po.CompositeQuery.PageIndex;
 * @see com.lvmama.common.ord.service.po.CompositeQuery.SortTypeEnum;
 * @see com.lvmama.common.vo.Constant;
 */
@ParentPackage("json-default")
@Results({ @Result(name = "travel_order_list", location = "/WEB-INF/pages/back/op/travel_ord_list.jsp") })
public class OpOrderAction extends BaseAction {
	
	/**
	 * 序列化ID.
	 */
	private static final long serialVersionUID = 640891709006320210L;
	/**
	 * 综合查询类.
	 */
	private CompositeQuery compositeQuery;
	/**
	 * 根据id查询相关参数.
	 */
	private OrderIdentity orderIdentity; 
	/**
	 *  根据时间范围查询相关参数
	 */
	private OrderTimeRange orderTimeRange; 
	/**
	 *  根据状态查询相关参数.
	 */
	private OrderStatus orderStatus; 
	/**
	 * 根据订单内容查询相关参数.
	 */
	private OrderContent orderContent; 
	/**
	 * 分页相关参数.
	 */
	private PageIndex pageIndex; 
	/**
	 * 排序的相关参数.
	 */
	private List<SortTypeEnum> typeList;
	/**
	 * 团号.
	 */
	private String travelCode;
	/**
	 * 订单号.
	 */
	private String orderId;
	/**
	 * 要修改的订单号.
	 */
	private Long ordId;
	/**
	 * 游玩开始时间.
	 */
	private Date visitTimeStart;
	/**
	 * 游玩结束时间.
	 */
	private Date visitTimeEnd;
	/**
	 * 下单开始时间.
	 */
	private Date createTimeStart;
	/**
	 * 下单结束时间.
	 */
	private Date createTimeEnd;
	/**
	 * 订单状态.
	 */
	private String order_Status;
	/**
	 * 审核状态.
	 */
	private String approveStatus;
	/**
	 * 支付状态.
	 */
	private String paymentStatus;
	/**
	 * 开票状态.
	 */
	private String trafficTicketStatus;
	
	/**
	 * 出团通知书状态
	 */
	private String groupWordStatus;
	

	/**
	 * 团订单列表集合总数.
	 */
	private Long totalRecords;
	/**
	 * 团订单列表.
	 */
	private List<OrdOrder> ordersList;
	
	/**
	 * 总金额统计结果
	 */
	private OrdOrderSum ordOrderSum;

	/**
	 * 订单服务接口.
	 */
	private OrderService orderServiceProxy;
	
	/**
	 * 产品ID
	 */
	private String productId;
	
	/**
	 * 销售产品经理名称.
	 */
	private String prodProductManagerName;
	
	/**
	 * 供应商名称
	 */
	private String supplierName;
	
	/**
	 * 分公司
	 */
	private String filialeName;
	
 
	
	/**
	 * 默认进入订单页面，不读取数据.
	 * @return
	 */
	@Action("/op/goOrderList")
	public String go(){
		order_Status=Constant.ORDER_STATUS.NORMAL.name();
		return "travel_order_list";
	}
	/**
	 * 团订单列表.
	 * @return  String
	 *       跳转页面地址.
	 */
	@Action("/op/opOrderList")
	public String opOrderList() {
		getCompositeQuery();//查询条件集合
		if(StringUtils.isNotEmpty(travelCode)){//只有在选择团的时候才读取统计信息
			ordOrderSum=orderServiceProxy.compositeQueryOrdOrderSum(compositeQuery);
		}
		if(!this.getSessionUser().isAdministrator()){
			orderIdentity.setOrgId(this.getSessionUser().getDepartmentId());
		}
 		compositeQuery.setPageIndex(new PageIndex());
		compositeQuery.setExcludedContent(null);
		compositeQuery.setOrderIdentity(orderIdentity);
		totalRecords = orderServiceProxy
				.compositeQueryOrdOrderCount(compositeQuery);
		pagination = initPagination();
		pagination.setTotalRecords(totalRecords);
		pageIndex.setBeginIndex(pagination.getFirstRow());
		pageIndex.setEndIndex(pagination.getLastRow());
		pagination.setActionUrl(WebUtils.getUrl(getRequest()));
		compositeQuery.setPageIndex(pageIndex);
		compositeQuery.getQueryFlag().setQuerySupplier(false);
		compositeQuery.getQueryFlag().setQueryUser(false);
		ordersList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
		String workTaskId = getRequest().getParameter("workTaskId");
		getRequest().getSession().setAttribute("workTaskId", workTaskId);
		return "travel_order_list";
	}
	
	/**
	 * 更改订单开票属性.
	 * @return  String
	 *       跳转页面地址.
	 */
	@Action("/op/opChackOrderTicket")
	public void chackType() {
		JSONResult result=new JSONResult();
		try{
			Assert.isTrue(!(ordId==null||ordId<1),"订单不可以为空");
			  OrdOrderRoute orderRoute = orderServiceProxy.queryOrdOrderRouteByOrderId(Long.valueOf(ordId));
			  if(orderRoute==null){
					throw new Exception("您修改的订单不存在开票状态");
				}
			  orderRoute.setTrafficTicketStatus("true");
			  orderServiceProxy.updateOrderRoute(orderRoute);
		}catch(Exception ex){
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	/**
	 * 团状态.
	 */
	@Action("/op/opChangeWordStatus")
	public void changeWordStatus(){
		JSONResult result=new JSONResult();
		try{
			Assert.isTrue(!(ordId==null||ordId<1),"订单不可以为空");
			OrdOrderRoute orderRoute = orderServiceProxy.queryOrdOrderRouteByOrderId(Long.valueOf(ordId));
			if(orderRoute==null){
				throw new Exception("您修改的订单不存在出团状态");
			}
			orderRoute.setGroupWordStatus(Constant.GROUP_ADVICE_STATE.SENT_NOTICE.name());
			orderServiceProxy.updateOrderRoute(orderRoute);
		}catch(Exception ex){
			result.raise(new JSONResultException(ex));
		}
		
		result.output(getResponse());		
	}
	
	/**
	 * 更改签证状态. 
	 */
	@Action("/op/opChangeVisa")
	public void changeVisaStatus(){
		JSONResult result=new JSONResult();
		try{
			Assert.isTrue(!(ordId==null||ordId<1),"订单不可以为空");
			Assert.hasLength(visaStatus,"状态不可以为空");
			OrdOrderRoute orderRoute = orderServiceProxy.queryOrdOrderRouteByOrderId(Long.valueOf(ordId));
			if(orderRoute==null){
				throw new Exception("您修改的订单不存在签证状态");
			}
				
		    orderRoute.setVisaStatus(visaStatus);
		    orderServiceProxy.updateOrderRoute(orderRoute);
		}catch(Exception ex){
			ex.printStackTrace();
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	/**
	 * 签订状态
	 */
	private String visaStatus;
	
	/**
	 * 包装查询条件.
	 * @return CompositeQuery.
	 */
	public CompositeQuery getCompositeQuery() {
		compositeQuery = new CompositeQuery();
		orderTimeRange=new OrderTimeRange();
		orderStatus = new OrderStatus();  
		orderContent = new OrderContent();
		orderIdentity = new OrderIdentity();
		typeList = new ArrayList<SortTypeEnum>();
		pageIndex = new PageIndex();
		//团号 
		if(StringUtils.isNotEmpty(this.travelCode)){
			orderContent.setTravelCode(travelCode);
		}
		//状态的添加
		if (StringUtils.isNotEmpty(this.order_Status)) {
			orderStatus.setOrderStatus(this.order_Status);
		}
		if (StringUtils.isNotEmpty(this.approveStatus)) {
			orderStatus.setOrderApproveStatus(this.approveStatus);
		}
		if (StringUtils.isNotEmpty(this.paymentStatus)) {
			orderStatus.setPaymentStatus(this.paymentStatus);
		}
		if (StringUtils.isNotEmpty(this.trafficTicketStatus)) {
			orderStatus.setTicketStatus(this.trafficTicketStatus);
		}
		//出团通知书状态
		if (StringUtils.isNotEmpty(this.groupWordStatus)) {
			orderStatus.setGroupWordStatus(this.groupWordStatus);
		}
//         orderStatus.setGroupWordStatus(this.groupWordStatus);
 
		//订单ID 
		if(StringUtils.isNotEmpty(orderId)){
		    orderIdentity.setOrderId(NumberUtils.toLong(this.orderId.trim()));
	    }
		//订单产品
		if(StringUtils.isNotEmpty(productId)){
			orderIdentity.setProductid(NumberUtils.toLong(productId));
		}
		if (createTimeStart != null) {
			orderTimeRange.setCreateTimeStart(this.createTimeStart);
		}
		if (createTimeEnd != null) {
			orderTimeRange.setCreateTimeEnd(DateUtil.getDayEnd(this.createTimeEnd));
		}
		if (visitTimeStart != null) {
			orderTimeRange.setOrdOrderVisitTimeStart(this.visitTimeStart);
		}
		if (visitTimeEnd != null) {
			orderTimeRange.setOrdOrderVisitTimeEnd(DateUtil.getDayEnd(this.visitTimeEnd));
		}
		//供应商
		if(StringUtils.isNotEmpty(supplierName)){
			orderContent.setSupplierName(supplierName);
		}
		if(StringUtils.isNotEmpty(filialeName)){
			orderContent.setFilialeName(filialeName);
		}
		
		//销售产品经理名称.
		if (StringUtils.isNotEmpty(this.prodProductManagerName)) {
			this.orderContent.setProdProductManagerName(this.prodProductManagerName);
		}
		
		orderContent.setPaymentTarget(Constant.PAYMENT_TARGET.TOLVMAMA.name());
		typeList.add(CompositeQuery.SortTypeEnum.ORDER_ID_DESC);//降序
		compositeQuery.setStatus(orderStatus);
		compositeQuery.setContent(orderContent);
		compositeQuery.setTypeList(typeList);
		compositeQuery.setPageIndex(pageIndex);
		compositeQuery.setOrderTimeRange(orderTimeRange);
		compositeQuery.setOrderIdentity(orderIdentity);
		return compositeQuery;
	}

     
	/**
	 * 设置综合查询类.
	 * @param compositeQuery
	 */
	public void setCompositeQuery(CompositeQuery compositeQuery) {
		this.compositeQuery = compositeQuery;
	}

	/**
	 * 获取综合查询类.
	 * @return
	 */
	public OrderIdentity getOrderIdentity() {
		return orderIdentity;
	}

	/**
	 * 设置根据id查询相关参数.
	 * @param orderIdentity
	 */
	public void setOrderIdentity(OrderIdentity orderIdentity) {
		this.orderIdentity = orderIdentity;
	}

	/**
	 * 获取根据id查询相关参数.
	 * @return
	 */
	public OrderTimeRange getOrderTimeRange() {
		return orderTimeRange;
	}

	/**
	 * 
	 * @param orderTimeRange
	 */
	public void setOrderTimeRange(OrderTimeRange orderTimeRange) {
		this.orderTimeRange = orderTimeRange;
	}

	/**
	 * 
	 * @return
	 */
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	/**
	 * 
	 * @param orderStatus
	 */
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * 
	 * @return
	 */
	public OrderContent getOrderContent() {
		return orderContent;
	}

	/**
	 * 
	 * @param orderContent
	 */
	public void setOrderContent(OrderContent orderContent) {
		this.orderContent = orderContent;
	}

	/**
	 * 
	 * @return
	 */
	public PageIndex getPageIndex() {
		return pageIndex;
	}

	/**
	 * 
	 * @param pageIndex
	 */
	public void setPageIndex(PageIndex pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * 
	 * @return
	 */
	public List<SortTypeEnum> getTypeList() {
		return typeList;
	}

	/**
	 * 
	 * @param typeList
	 */
	public void setTypeList(List<SortTypeEnum> typeList) {
		this.typeList = typeList;
	}

	/**
	 * 获取总记录数.
	 * @return
	 */
	public Long getTotalRecords() {
		return totalRecords;
	}

	/**
	 * 设置总记录数.
	 * @param totalRecords
	 */
	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * 获取订单的LIST.
	 * @return
	 */
	public List<OrdOrder> getOrdersList() {
		return ordersList;
	}

	/**
	 * 设置订单的LIST.
	 * @param ordersList
	 */
	public void setOrdersList(List<OrdOrder> ordersList) {
		this.ordersList = ordersList;
	}

	/**
	 * 设置订单服务SERVICE.
	 * @param orderServiceProxy
	 */
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	/**
	 * 获取团号.
	 * @return
	 */
	public String getTravelCode() {
		return travelCode;
	}

	/**
	 * 设置团号.
	 * @param travelCode
	 */
	public void setTravelCode(String travelCode) {
		this.travelCode = travelCode;
	}

	/**
	 * 获取订单ID.
	 * @return
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * 设置订单ID.
	 * @param orderId
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * 获取游玩开始时间.
	 * @return
	 */
	public Date getVisitTimeStart() {
		return visitTimeStart;
	}

	/**
	 * 设置游玩开始时间.
	 * @param visitTimeStart
	 */
	public void setVisitTimeStart(Date visitTimeStart) {
		this.visitTimeStart = visitTimeStart;
	}

	/**
	 * 获取游玩结束时间.
	 * @return
	 */
	public Date getVisitTimeEnd() {
		return visitTimeEnd;
	}

	/**
	 * 设置游玩结束时间.
	 * @param visitTimeEnd
	 */
	public void setVisitTimeEnd(Date visitTimeEnd) {
		this.visitTimeEnd = visitTimeEnd;
	}

	/**
	 * 获取下单开始时间.
	 * @return
	 */
	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	/**
	 * 设置下单开始时间.
	 * @param createTimeStart
	 */
	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	/**
	 * 获取下单结束时间.
	 * @return
	 */
	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	/**
	 * 设置下单结束时间.
	 * @param createTimeEnd
	 */
	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	/**
	 * 获取订单状态.
	 * @return
	 */
	public String getOrder_Status() {
		return order_Status;
	}

	/**
	 * 设置订单状态.
	 * @param order_Status
	 */
	public void setOrder_Status(String order_Status) {
		this.order_Status = order_Status;
	}

	/**
	 * 获取审核状态.
	 * @return
	 */
	public String getApproveStatus() {
		return approveStatus;
	}

	/**
	 * 设置审核状态.
	 * @param approveStatus
	 */
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	/**
	 * 获取支付状态.
	 * @return
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * 设置支付状态.
	 * @param paymentStatus
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * 获取开票状态.
	 * @return
	 */
	public String getTrafficTicketStatus() {
		return trafficTicketStatus;
	}

	/**
	 * 设置开票状态.
	 * @param trafficTicketStatus
	 */
	public void setTrafficTicketStatus(String trafficTicketStatus) {
		this.trafficTicketStatus = trafficTicketStatus;
	}

	/**
	 * 
	 * @return
	 */
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	/**
	 * 获取要修改的订单.
	 * @return
	 */
	public Long getOrdId() {
		return ordId;
	}

	/**
	 * 设置要修改的订单.
	 * @param ordId
	 */
	public void setOrdId(Long ordId) {
		this.ordId = ordId;
	}

	/**
	 * @param visaStatus the visaStatus to set
	 */
	public void setVisaStatus(String visaStatus) {
		this.visaStatus = visaStatus;
	}

	/**
	 * @return the ordOrderSum
	 */
	public OrdOrderSum getOrdOrderSum() {
		return ordOrderSum;
	}
	
	

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		if(StringUtils.isNotEmpty(productId)){
			this.productId=productId.trim();
		}
	}
	/**
	 * 读取数据库当中的签证状态
	 * @return
	 */
	public List<CodeItem> getVisaStatusList(){
		return CodeSet.getInstance().getCachedCodeList(Constant.CODE_TYPE.VISA_STATUS.name());
	}
	
	/**
	 * 下拉框分公司列表
	 * @return
	 */
	public List<CodeItem> getFilialeNameList(){
		List<CodeItem> list=CodeSet.getInstance().getCachedCodeList(Constant.CODE_TYPE.FILIALE_NAME.name());
		list.add(0,new CodeItem("","全部"));
		return list;
	}
	/**
	 * @return the supplierName
	 */
	public String getSupplierName() {
		return supplierName;
	}
	/**
	 * @param supplierName the supplierName to set
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	/**
	 * @return the filialeName
	 */
	public String getFilialeName() {
		return filialeName;
	}
	/**
	 * @param filialeName the filialeName to set
	 */
	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}
	public String getGroupWordStatus() {
		return groupWordStatus;
	}
	public void setGroupWordStatus(String groupWordStatus) {
		this.groupWordStatus = groupWordStatus;
	}
	public String getProdProductManagerName() {
		return prodProductManagerName;
	}
	public void setProdProductManagerName(String prodProductManagerName) {
		this.prodProductManagerName = prodProductManagerName;
	}
	
	
}
