package com.lvmama.back.sweb.ord;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.StringUtil;
import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderMemo;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderTimeRange;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.SortTypeEnum;
import com.lvmama.comm.bee.vo.ord.OrderPersonCount;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;


@ParentPackage("json-default")
@Results({
		@Result(name = "ord_detail", location = "/WEB-INF/pages/back/ord/ord_history.jsp"),
		@Result(name = "ord_team_monitor", location = "/WEB-INF/pages/back/ord/ord_team_monitor.jsp") })
/**
 * 成团监控
 * 
 * @author luoyinqi
 * 
 */
public class OrderTeamMonitorAction extends BaseAction {

	private OrderService orderServiceProxy;
	private CompositeQuery compositeQuery;
	private OrderIdentity orderIdentity;
	private OrderTimeRange orderTimeRange;
	private PageIndex pageIndex;
	private OrderStatus status;

	private OrdOrder orderDetail;

	private Long orderId;
	private List<CodeItem> cancelReasons;
	private List<OrdOrder> ordersList;
	private List<OrdInvoice> invoiceList;
	private List<ComLog> comLogs;
	private List<SortTypeEnum> typeList;
	private ComLogService comLogService;

	private String productId = "";
	private String visitTime = "";
	private String queryString;
	
	private OrderPersonCount opc;

	public List<OrdInvoice> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<OrdInvoice> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public List<ComLog> getComLogs() {
		return comLogs;
	}

	public void setComLogs(List<ComLog> comLogs) {
		this.comLogs = comLogs;
	}

	/**
	 * 初始化动作
	 */
	@Action("ord_team_monitor")
	public String execute() {
		opc = new OrderPersonCount();
		opc.setAdultCount(0l);
		opc.setChildCount(0l);

		return "ord_team_monitor";
	}

	public String doQuery() {
		compositeQuery = new CompositeQuery();
		orderIdentity = new OrderIdentity();
		orderTimeRange = new OrderTimeRange();
		pageIndex = new PageIndex();
		status = new OrderStatus();
		typeList = new ArrayList<SortTypeEnum>();

		String tempString = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();

		queryString = this.getRequest().getParameter("button");
		productId = this.getRequest().getParameter("productId");
		visitTime = this.getRequest().getParameter("visitTime");

		if (queryString != null) {
			if (queryString.equals("库耗订单")) {
				status.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
			} else if (queryString.equals("取消订单")) {
				status.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
			} else if (queryString.equals("出游订单")) {
				status.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED
						.name());
			}
		}

		if (!StringUtil.isEmptyString(productId)) {
			orderIdentity.setProductid(Long.parseLong(productId.trim()));
			tempString += ("productId=" + productId.trim() + "&");
		}
		if (!StringUtil.isEmptyString(visitTime)) {
			tempString += ("visitTime=" + visitTime.trim() + "&");
			try {
				orderTimeRange.setOrdOrderVisitTimeStart(DateUtil.getDayStart(simpleDateFormat.parse(visitTime.trim()))); 
				orderTimeRange.setOrdOrderVisitTimeEnd(DateUtil.getDayEnd(simpleDateFormat.parse(visitTime.trim())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		typeList.add(CompositeQuery.SortTypeEnum.ORDER_ID_DESC);

		pageIndex.setEndIndex(10);
		compositeQuery.setOrderIdentity(orderIdentity);
		compositeQuery.setOrderTimeRange(orderTimeRange);
		compositeQuery.setStatus(status);
		compositeQuery.setTypeList(typeList);

		Long totalRecords = orderServiceProxy
				.compositeQueryOrdOrderCount(compositeQuery);
		pagination = initPagination();
		pagination.setTotalRecords(totalRecords);
		pageIndex.setBeginIndex(pagination.getFirstRow());
		pageIndex.setEndIndex(pagination.getLastRow());
		pagination.setActionUrl("ord/ord_team_monitor!doQuery.do?button="
				+ queryString + "&" + tempString);

		compositeQuery.setPageIndex(pageIndex);
		ordersList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
		
		String paymentStatus = null;
		String orderStatus = null;
		if (status.getPaymentStatus() != null) {
			paymentStatus = status.getPaymentStatus().name();
		}
		if (status.getOrderStatus() != null) {
			orderStatus = status.getOrderStatus().name();
		}

		opc = orderServiceProxy.queryOrderPersonCount(
				Long.parseLong(productId.trim()),
				orderTimeRange.getOrdOrderVisitTimeStart(),
				orderTimeRange.getOrdOrderVisitTimeEnd(), paymentStatus,
				orderStatus);
		return "ord_team_monitor";
	}

	public void exportQueryResult() {
		compositeQuery = new CompositeQuery();
		orderIdentity = new OrderIdentity();
		orderTimeRange = new OrderTimeRange();
		pageIndex = new PageIndex();
		status = new OrderStatus();
		typeList = new ArrayList<SortTypeEnum>();

		String tempString = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();

		queryString = this.getRequest().getParameter("hiddenStatus");
		productId = this.getRequest().getParameter("productId");
		visitTime = this.getRequest().getParameter("visitTime");

		if (queryString != null) {
			if (queryString.equals("库耗订单")) {
				status.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
			} else if (queryString.equals("取消订单")) {
				status.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
			} else if (queryString.equals("出游订单")) {
				status.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED
						.name());
			}
		}

		if (!StringUtil.isEmptyString(productId)) {
			orderIdentity.setProductid(Long.parseLong(productId.trim()));
			tempString += ("productId=" + productId.trim() + "&");
		}
		if (!StringUtil.isEmptyString(visitTime)) {
			tempString += ("visitTime=" + visitTime.trim() + "&");
			try {
				orderTimeRange.setOrdOrderVisitTimeStart(simpleDateFormat
						.parse(visitTime.trim()));

				c.setTime(simpleDateFormat.parse(visitTime.trim()));
				c.add(Calendar.DATE, 1);
				orderTimeRange.setOrdOrderVisitTimeEnd(c.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		typeList.add(CompositeQuery.SortTypeEnum.ORDER_ID_DESC);

		pageIndex.setEndIndex(10);
		compositeQuery.setOrderIdentity(orderIdentity);
		compositeQuery.setOrderTimeRange(orderTimeRange);
		compositeQuery.setStatus(status);
		compositeQuery.setTypeList(typeList);

		Long totalRecords = orderServiceProxy
				.compositeQueryOrdOrderCount(compositeQuery);
		pagination = initPagination();
		
		pagination.setTotalRecords(totalRecords);
		pageIndex.setBeginIndex(0);
		pageIndex.setEndIndex(Integer.valueOf(String.valueOf(totalRecords.longValue())));
		compositeQuery.setPageIndex(pageIndex);
		ordersList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
		
		for (OrdOrder oo : ordersList) {
			Long orderId = oo.getOrderId();
			
			StringBuffer sb = new StringBuffer("");
			
			if(!StringUtil.isEmptyString(oo.getUserMemo())){
				sb.append(oo.getUserMemo()).append(";");
			}
			/**
			 * 订单备注
			 */
			List<OrdOrderMemo> orderMemos = orderServiceProxy.queryMemoByOrderId(orderId);
			for(OrdOrderMemo oom : orderMemos){
				if(oom != null && !StringUtil.isEmptyString(oom.getContent())){
					sb.append(oom.getContent()).append(";");
				}
			}
			oo.setUserMemo(sb.toString());
		}
		output(ordersList, "/WEB-INF/resources/template/stocksMonitorTemplate.xls");
		
		
	}
	
	@SuppressWarnings("unchecked")
	private void output(List list,String template){
		FileInputStream fin=null;
		OutputStream os=null;
		try
		{
			File templateResource = ResourceUtil.getResourceFile(template);
			Map beans = new HashMap();
			beans.put("orderList", list);
			XLSTransformer transformer = new XLSTransformer();
			File destFileName = new File(Constant.getTempDir() + "/excel"+new Date().getTime()+".xls");
			transformer.transformXLS(templateResource.getAbsolutePath(), beans, destFileName.getAbsolutePath());
					
			getResponse().setContentType("application/vnd.ms-excel");
			getResponse().setHeader("Content-Disposition", "attachment; filename=" + destFileName.getName());
			os=getResponse().getOutputStream();
			fin=new FileInputStream(destFileName);
			IOUtils.copy(fin, os);
			
			os.flush();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			IOUtils.closeQuietly(fin);
			IOUtils.closeQuietly(os);
		}
	}
	
	@Action("/ord/showOrderDetail")
	public String showOrderDetail() {
		System.out.println();

		if (orderId != null) {
			this.orderDetail = orderServiceProxy
					.queryOrdOrderByOrderId(new Long(orderId));

			// 如果需要发票
			if (orderDetail.getNeedInvoice() != null) {
				if (orderDetail.getNeedInvoice().equals("true")) {
					this.invoiceList = orderServiceProxy
							.queryInvoiceByOrderId(orderId);
				}
			}
			this.comLogs = comLogService.queryByParentId(
					Constant.COM_LOG_OBJECT_TYPE.ORD_ORDER.name(), orderId);
			this.cancelReasons = CodeSet.getInstance().getCachedCodeList(
					Constant.CODE_TYPE.ORD_CANCEL_REASON.name());
		}
		return "ord_detail";
	}
	

	public List<OrdOrder> getOrdersList() {
		return ordersList;
	}

	public void setOrdersList(List<OrdOrder> ordersList) {
		this.ordersList = ordersList;
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public OrdOrder getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrdOrder orderDetail) {
		this.orderDetail = orderDetail;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public List<CodeItem> getCancelReasons() {
		return cancelReasons;
	}

	public void setCancelReasons(List<CodeItem> cancelReasons) {
		this.cancelReasons = cancelReasons;
	}

	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public OrderPersonCount getOpc() {
		return opc;
	}

	public void setOpc(OrderPersonCount opc) {
		this.opc = opc;
	}
	
}
