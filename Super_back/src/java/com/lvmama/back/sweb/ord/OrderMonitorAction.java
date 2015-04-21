package com.lvmama.back.sweb.ord;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.ExcludedContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderTimeRange;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.SortTypeEnum;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

@Results( {
		@Result(name = "monitor", location = "/WEB-INF/pages/back/ord/order_monitor_list.jsp"),
		@Result(name = "single", location = "/WEB-INF/pages/back/ord/ord_single_list.jsp"),
		@Result(name = "ord_in_add", location = "/WEB-INF/pages/back/ord/ord_in_add.jsp") })
/**
 * 订单监控类
 * 
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */

public class OrderMonitorAction extends BaseAction {
	private static final long serialVersionUID = 2846989254506848390L;

	private static Logger logger = Logger.getLogger(OrderMonitorAction.class);
	private UserUserProxy userUserProxy;
	/**
	 * 订单服务接口
	 */
	private OrderService orderServiceProxy;

	/**
	 * 产品服务接口
	 */
	private ProdProductService prodProductService;
	/**
	 * 订单集合列表
	 */
	private List<OrdOrder> ordersList;
	/**
	 * 查询订单页面
	 */
	private String pageType="monitor";
	
	// Request Parameter;
	private String orderId="";
	private String productID="";
	private String userName="";
	private String contactName="";
	private String productName="";
	private String visitTimeStart="";
	private String visitTimeEnd="";
	private String createTimeStart="";
	private String createTimeEnd="";
	private String paymentTimeStart="";
	private String paymentTimeEnd="";
	private String paymentStatus="";
	private String approveStatus="";
	private String orderStatus="";
	private String groupWordStatus="";//出团通知书状态
	private String resourceLackReson = ""; //资源审核不通过
	private String userMobile="";
	private String userEmail="";
	private String userMembershipCard="";
	private String contactMobile="";
	private List<String> productTypeList;
	private List<CodeItem> orderApproveList;
	private List<CodeItem> infoApproveList;
	private List<CodeItem> channelList;
	private List<CodeItem> resourceLackResonList;
	private Page<OrdOrder> pageConfig;
	private OrdOrder orderDetail;
	private String dealTimeStart="";	//首处理开始时间
	private String dealTimeEnd="";	//首处理结束时间
	private String takenOperator="";	//处理人
	private String channel="";
	private String filialeName;
	private String infoApproveStatus="";

	public OrdOrder getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrdOrder orderDetail) {
		this.orderDetail = orderDetail;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public List<OrdOrder> getOrdersList() {
		return ordersList;
	}

	public void setOrdersList(List<OrdOrder> ordersList) {
		this.ordersList = ordersList;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getVisitTimeStart() {
		return visitTimeStart;
	}

	public void setVisitTimeStart(String visitTimeStart) {
		this.visitTimeStart = visitTimeStart;
	}

	public String getVisitTimeEnd() {
		return visitTimeEnd;
	}

	public void setVisitTimeEnd(String visitTimeEnd) {
		this.visitTimeEnd = visitTimeEnd;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getPaymentTimeStart() {
		return paymentTimeStart;
	}

	public void setPaymentTimeStart(String paymentTimeStart) {
		this.paymentTimeStart = paymentTimeStart;
	}

	public String getPaymentTimeEnd() {
		return paymentTimeEnd;
	}

	public void setPaymentTimeEnd(String paymentTimeEnd) {
		this.paymentTimeEnd = paymentTimeEnd;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getResourceLackReson() {
		return resourceLackReson;
	}

	public void setResourceLackReson(String resourceLackReson) {
		this.resourceLackReson = resourceLackReson;
	}

	public String getUserMembershipCard() {
		return userMembershipCard;
	}

	public void setUserMembershipCard(String userMembershipCard) {
		this.userMembershipCard = userMembershipCard;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public List<String> getProductTypeList() {
		return productTypeList;
	}

	public void setProductTypeList(List<String> productTypeList) {
		this.productTypeList = productTypeList;
	}

	public Page<OrdOrder> getPageConfig() {
		return pageConfig;
	}

	public void setPageConfig(Page<OrdOrder> pageConfig) {
		this.pageConfig = pageConfig;
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
 
	/**
	 * 进入页面初始化时执行
	 */
	@Action(value = ("/ord/order_monitor_list"), results ={
		@Result(name = "monitor", location = "/WEB-INF/pages/back/ord/ord_monitor_list.jsp"),
		@Result(name = "single", location = "/WEB-INF/pages/back/ord/ord_single_list.jsp")})
	public String execute() {
		orderApproveList = CodeSet.getInstance().getCachedCodeList(Constant.CODE_TYPE.ORDER_APPROVE_STATUS.name());
		infoApproveList = CodeSet.getInstance().getCachedCodeList(Constant.CODE_TYPE.INFO_APPROVE_STATUS.name());
		channelList = CodeSet.getInstance().getCachedCodeList(
				Constant.CODE_TYPE.CHANNEL.name());
		/*resourceLackResonList = CodeSet.getInstance().getCachedCodeList(
				Constant.CODE_TYPE.ORDER_RESOURCE_LACK_REASON.name());*/
		return pageType;
	}

	/**
	 * 订单List查询
	 */
	@Action(value = ("/ord/order_monitor_list"), results ={
		@Result(name = "monitor", location = "/WEB-INF/pages/back/ord/ord_monitor_list.jsp"),
		@Result(name = "single", location = "/WEB-INF/pages/back/ord/ord_single_list.jsp")})
	public String doOrderQuery() {
		
		orderApproveList = CodeSet.getInstance().getCachedCodeList(
				Constant.CODE_TYPE.ORDER_APPROVE_STATUS.name());
		infoApproveList = CodeSet.getInstance().getCachedCodeList(
				Constant.CODE_TYPE.INFO_APPROVE_STATUS.name());
		channelList = CodeSet.getInstance().getCachedCodeList(
				Constant.CODE_TYPE.CHANNEL.name());
		CompositeQuery compositeQuery = new CompositeQuery();
		OrderStatus orderStatusForQuery = new OrderStatus();
		OrderTimeRange orderTimeRange = new OrderTimeRange();
		OrderContent orderContent = new OrderContent();
		OrderIdentity orderIdentity = new OrderIdentity();
		ExcludedContent orderExcludedContent = new ExcludedContent();
		PageIndex pageIndex = new PageIndex();
		List<SortTypeEnum> sortTypeList = new ArrayList<SortTypeEnum>();
		sortTypeList.add(SortTypeEnum.ORDER_ID_DESC);
		Boolean hasParam = Boolean.FALSE;
		
		if (orderId != null && !"".equals(orderId.trim())) {
			try {
				orderIdentity.setOrderId(Long.parseLong(orderId.trim()));
			} catch (Exception e) {
			}
			hasParam = Boolean.TRUE;
		}
		if (productID != null && !"".equals(productID.trim())) {
			try {
				orderIdentity.setProductid(Long.parseLong(productID.trim()));
			} catch (Exception e) {
			}
			hasParam = Boolean.TRUE;
		}
		//TODO
		if (userName != null && !"".equals(userName.trim())) {
			if (getRequest().getMethod().equalsIgnoreCase("GET")) { 
				try {
					userName = URLDecoder.decode(userName, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			} 
			orderContent.setUserName(userName.trim());
			hasParam = Boolean.TRUE;
		}
		if (userEmail != null && !"".equals(userEmail.trim())) {
			orderContent.setEmail(userEmail.trim());
			hasParam = Boolean.TRUE;
		}
		if (userMobile != null && !"".equals(userMobile.trim())) {
			orderContent.setMobile(userMobile.trim());
			hasParam = Boolean.TRUE;
		}
		if (userMembershipCard != null && !"".equals(userMembershipCard.trim())) {
			orderContent.setMembershipCard(userMembershipCard);
			hasParam = Boolean.TRUE;
		}
		if (contactName != null && !"".equals(contactName.trim())) {
			orderContent.setContactName(contactName.trim());
			hasParam = Boolean.TRUE;
		}
		if (contactMobile != null && !"".equals(contactMobile.trim())) {
			orderContent.setContactMobile(contactMobile.trim());
			hasParam = Boolean.TRUE;
		}																
		
		if (productName != null && !"".equals(productName.trim())) {
			List<Long> productIds=prodProductService.selectProductIdsByLikeProductName(productName.trim());
			orderIdentity.setProductIds(productIds);	
			
			orderContent.setProductName(productName.trim());
			hasParam = Boolean.TRUE;
		}
		if (null != takenOperator && !"".equals(takenOperator.trim())){
			orderContent.setTakenOperator(takenOperator);
			hasParam = Boolean.TRUE;
		}
		if (null != channel && !"".equals(channel.trim())){
			orderContent.setChannel(channel);
		}
		if(StringUtils.isNotEmpty(filialeName)){
			orderContent.setFilialeName(filialeName);
		}
		StringBuffer sbUrl = new StringBuffer();
		if (productTypeList != null && productTypeList.size() > 0) {
			StringBuffer sbParam = new StringBuffer();
			for (int i = 0; i < productTypeList.size(); i++) {
				String productType = productTypeList.get(i);
				if (productType != null && !"".equals(productType.trim())) {
					sbParam.append(productType).append(",");
					sbUrl.append("&productTypeList[").append(i).append("]=")
							.append(productType);
				} else {
					sbUrl.append("&productTypeList[").append(i).append("]=");
				}
			}
			orderContent.setOrderType(sbParam.toString());
		}


		if (!StringUtil.isEmptyString(orderStatus)&&!"null".equals(orderStatus))
			orderStatusForQuery.setOrderStatus(orderStatus);
		if (!StringUtil.isEmptyString(groupWordStatus)&&!"null".equals(groupWordStatus))//出团通知书状态设定
			orderStatusForQuery.setGroupWordStatus(groupWordStatus);
		if (!StringUtil.isEmptyString(approveStatus)&&!"null".equals(approveStatus))
			orderStatusForQuery.setOrderApproveStatus(approveStatus);
		if (!StringUtil.isEmptyString(infoApproveStatus)&&!"null".equals(infoApproveStatus))
			orderStatusForQuery.setInfoApproveStatus(infoApproveStatus);
		if (!StringUtil.isEmptyString(paymentStatus)&&!"null".equals(paymentStatus))
			orderStatusForQuery.setPaymentStatus(paymentStatus);
		if (!StringUtil.isEmptyString(resourceLackReson)&&!"null".equals(resourceLackReson)&&!"ALL".equals(resourceLackReson))
			orderStatusForQuery.setOrderResourceLackReason(resourceLackReson);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();

		if (visitTimeStart != null && !("".equals(visitTimeStart))
				&& visitTimeEnd != null && !("".equals(visitTimeEnd))) {
			try {
				orderTimeRange
						.setOrdOrderItemProdVisitTimeStart(simpleDateFormat
								.parse(visitTimeStart));
				c.setTime(simpleDateFormat.parse(visitTimeEnd));
				c.add(Calendar.DATE, 1); // 因取到的结束日期为当天的0:00:00，故查询时需往后延一天
				orderTimeRange.setOrdOrderItemProdVisitTimeEnd(c.getTime());
			} catch (ParseException e) {
				logger.info(e.getMessage());
			}
			hasParam = Boolean.TRUE;
		}
		if (createTimeStart != null && !("".equals(createTimeStart))
				&& createTimeEnd != null && !("".equals(createTimeEnd))) {
			try {
				orderTimeRange.setCreateTimeStart(simpleDateFormat
						.parse(createTimeStart));
				c.setTime(simpleDateFormat.parse(createTimeEnd));
				c.add(Calendar.DATE, 1); // 因取到的结束日期为当天的0:00:00，故查询时需往后延一天
				orderTimeRange.setCreateTimeEnd(c.getTime());
			} catch (ParseException e) {
				logger.info(e.getMessage());
			}
			hasParam = Boolean.TRUE;
		}
		if (paymentTimeStart != null && !("".equals(paymentTimeStart))
				&& paymentTimeEnd != null && !("".equals(paymentTimeEnd))) {
			try {
				orderTimeRange.setPayTimeStart(simpleDateFormat
						.parse(paymentTimeStart));
				c.setTime(simpleDateFormat.parse(paymentTimeEnd));
				c.add(Calendar.DATE, 1); // 因取到的结束日期为当天的0:00:00，故查询时需往后延一天
				orderTimeRange.setPayTimeEnd(c.getTime());
			} catch (ParseException e) {
				logger.info(e.getMessage());
			}
			hasParam = Boolean.TRUE;
		}
		if (dealTimeStart != null && !("".equals(dealTimeStart))
				&& dealTimeEnd != null && !("".equals(dealTimeEnd))) {
			try {
				orderTimeRange.setDealTimeStart(simpleDateFormat
						.parse(dealTimeStart));
				c.setTime(simpleDateFormat.parse(dealTimeEnd));
				c.add(Calendar.DATE, 1); // 因取到的结束日期为当天的0:00:00，故查询时需往后延一天
				orderTimeRange.setDealTimeEnd(c.getTime());
			} catch (ParseException e) {
				logger.info(e.getMessage());
			}
			hasParam = Boolean.TRUE;
		}

		if (!hasParam) {
			this.getRequest().setAttribute("hasParamMessage", "请填写相关的必填查询条件！");
			return pageType;
		}
		
		try {
			compositeQuery.setOrderIdentity(orderIdentity);
			compositeQuery.setOrderTimeRange(orderTimeRange);
			//将用户名Email等转为IdList形式查询
			Map<String, Object> param = orderContent.getUserParam(orderContent);
			if(param != null) {
				orderContent.setUserIdList(orderContent.getUserList(userUserProxy.getUsers(param)));
			}
			compositeQuery.setContent(orderContent);
			compositeQuery.setStatus(orderStatusForQuery);
			compositeQuery.setExcludedContent(orderExcludedContent);
			compositeQuery.setTypeList(sortTypeList);
			compositeQuery.getQueryFlag().setQuerySupplier(false);
			compositeQuery.getQueryFlag().setQueryUser(false);
			Long totalRecords = orderServiceProxy
					.compositeQueryOrdOrderCount(compositeQuery);
			pagination = initPagination();
			pagination.setTotalRecords(totalRecords);
			pageIndex.setBeginIndex(pagination.getFirstRow());
			pageIndex.setEndIndex(pagination.getLastRow());
			pagination.setActionUrl("ord/order_monitor_list!doOrderQuery.do?orderId=" + StringUtil.replaceNullStr(orderId) 
					+ "&userName=" + StringUtil.replaceNullStr(userName) 
					+ "&contactName=" + StringUtil.replaceNullStr(contactName) 
					+ "&productName=" + StringUtil.replaceNullStr(productName)
					+ "&userMobile=" + StringUtil.replaceNullStr(userMobile)
					+ "&userMembershipCard=" + StringUtil.replaceNullStr(userMembershipCard)
					+ "&contactMobile=" + StringUtil.replaceNullStr(contactMobile)
					+ "&visitTimeStart=" + StringUtil.replaceNullStr(visitTimeStart)
					+ "&visitTimeEnd=" + StringUtil.replaceNullStr(visitTimeEnd) 
					+ "&createTimeStart=" + StringUtil.replaceNullStr(createTimeStart) 
					+ "&createTimeEnd=" + StringUtil.replaceNullStr(createTimeEnd)
					+ "&paymentTimeStart=" + StringUtil.replaceNullStr(paymentTimeStart)
					+ "&paymentTimeEnd=" + StringUtil.replaceNullStr(paymentTimeEnd)
					+ "&paymentStatus=" + StringUtil.replaceNullStr(paymentStatus)
					+ "&dealTimeStart=" + StringUtil.replaceNullStr(dealTimeStart)
					+ "&dealTimeEnd=" + StringUtil.replaceNullStr(dealTimeEnd) 
					+ "&approveStatus=" + StringUtil.replaceNullStr(approveStatus)
					+ "&infoApproveStatus=" + StringUtil.replaceNullStr(infoApproveStatus)
					+ "&orderStatus=" + StringUtil.replaceNullStr(orderStatus)
					+ "&groupWordStatus=" + StringUtil.replaceNullStr(groupWordStatus)
					+ "&takenOperator="+ StringUtil.replaceNullStr(takenOperator)
					+ "&productID="+ StringUtil.replaceNullStr(productID) + "&pageType=" + this.pageType 
					+ "&channel="+ StringUtil.replaceNullStr(channel)
					+ "&filialeName="+StringUtil.replaceNullStr(filialeName)
					+ sbUrl.toString());
			compositeQuery.setPageIndex(pageIndex);

			ordersList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		String workTaskId = getRequest().getParameter("workTaskId");
		getRequest().getSession().setAttribute("workTaskId", workTaskId);
		return pageType;
	}

	@Action("/ord/showInvoiceAndAddress")
	public String showInvoiceAndAddress() {
		if (orderId != null)
			orderId = orderId.trim();
		this.orderDetail = orderServiceProxy
				.queryOrdOrderByOrderId(new Long(orderId));
		return "ord_in_add";
	}

	public List<CodeItem> getOrderApproveList() {
		return orderApproveList;
	}

	public void setOrderApproveList(List<CodeItem> orderApproveList) {
		this.orderApproveList = orderApproveList;
	}

	public String getDealTimeStart() {
		return dealTimeStart;
	}

	public void setDealTimeStart(String dealTimeStart) {
		this.dealTimeStart = dealTimeStart;
	}

	public String getDealTimeEnd() {
		return dealTimeEnd;
	}

	public void setDealTimeEnd(String dealTimeEnd) {
		this.dealTimeEnd = dealTimeEnd;
	}

	public String getTakenOperator() {
		return takenOperator;
	}

	public void setTakenOperator(String takenOperator) {
		this.takenOperator = takenOperator;
	}

	public List<CodeItem> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<CodeItem> channelList) {
		this.channelList = channelList;
	}

	public List<CodeItem> getResourceLackResonList() {
		return resourceLackResonList;
	}

	public void setResourceLackResonList(List<CodeItem> resourceLackResonList) {
		this.resourceLackResonList = resourceLackResonList;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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

	/**
	 * @return the groupWordStatus
	 */
	public String getGroupWordStatus() {
		return groupWordStatus;
	}

	/**
	 * @param groupWordStatus the groupWordStatus to set
	 */
	public void setGroupWordStatus(String groupWordStatus) {
		this.groupWordStatus = groupWordStatus;
	}

	/**
	 * 下拉框分公司列表
	 * @return
	 */
	public List<CodeItem> getFilialeNameList(){
		List<CodeItem> list=CodeSet.getInstance().getCachedCodeList("FILIALE_NAME");
		list.add(0,new CodeItem("","全部"));
		return list;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public String getInfoApproveStatus() {
		return infoApproveStatus;
	}

	public void setInfoApproveStatus(String infoApproveStatus) {
		this.infoApproveStatus = infoApproveStatus;
	}

	public List<CodeItem> getInfoApproveList() {
		return infoApproveList;
	}

	public void setInfoApproveList(List<CodeItem> infoApproveList) {
		this.infoApproveList = infoApproveList;
	}
	
}
