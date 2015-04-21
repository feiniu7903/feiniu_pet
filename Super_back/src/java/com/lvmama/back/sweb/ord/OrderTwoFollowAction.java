package com.lvmama.back.sweb.ord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.StringUtil;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderTrack;
import com.lvmama.comm.bee.service.IOrdertrackService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderTimeRange;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderTrackRelate;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.SortTypeEnum;
import com.lvmama.comm.bee.vo.ord.TrackLog;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

@Results( {
		@Result(name = "two_follow", location = "/WEB-INF/pages/back/ord/resources/ord_two_follow.jsp"),
		@Result(name = "save_tarck", location = "/WEB-INF/pages/back/ord/resources/save_order_track.jsp"),
		@Result(name = "tarck_log", location = "/WEB-INF/pages/back/ord/resources/list_order_track.jsp"),
		@Result(name = "index", location = "/ord/listTwoFollow.do?tab=1", type = "redirect")
		})
/**
 * 资源审核类
 * 
 * @author huangl
 */
public class OrderTwoFollowAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private UserUserProxy userUserProxy;
	/**
	 * 订单服务接口
	 */
	private OrderService orderServiceProxy;
	private IOrdertrackService ordertrackService;
	private ComLogService comLogService;
	/**
	 * 综合查询类.
	 */
	private CompositeQuery compositeQuery;  
	/**
	 * 根据id查询相关参数.
	 */
	private OrderIdentity orderIdentity;  
	/**
	 * 根据时间范围查询相关参数.
	 */
	private OrderTimeRange orderTimeRange;  
	/**
	 * 根据状态查询相关参数.
	 */
	private OrderStatus orderStatus;  
	/**
	 * 根据订单内容查询相关参数.
	 */
	private OrderContent orderContent;
	/**
	 * 二次跟单相关查询参数.
	 */
	private OrderTrackRelate orderTrack;
	/**
	 * 分页相关参数.
	 */
	private PageIndex pageIndex;
	/**
	 * 排序.
	 */
	private List<SortTypeEnum> typeList;
	/**
	 * 我的审核任务订单集合列表
	 */
	private List<OrdOrder> ordersList;
	/**
	 * 我的历史订单集合列表
	 */
	private List<OrdOrder> historyOrdersList;
	/**
	 * 处理结果分类.
	 */
	private List<CodeItem> tarckItemList;
	/**
	 * 集合.
	 */
	private List<TrackLog> trackLogList;
	/**
	 * tab下标
	 */
	private int tab=1;
	String trackType = "";
	/**
	 * 保存二次跟踪处理日志.
	 */
	@Action("/ord/saveOrdTarck")
	public String saveOrdTarck() {
		String memo=this.getRequest().getParameter("memo");
		String trackId=this.getRequest().getParameter("trackId");
		String orderId=this.getRequest().getParameter("orderId");
		String trackType=this.getRequest().getParameter("trackType");
		if(!StringUtil.isEmptyString(trackId)){
			TrackLog o=new TrackLog();
			o.setCreateTime(new Date());
			o.setMemo(memo);
			o.setTrackStatus(trackType);
			o.setTrackId(Long.valueOf(trackId));
			OrdOrderTrack ordTrack=new OrdOrderTrack();
			ordTrack.setOrdTrackId(Long.valueOf(trackId));
			ordTrack.setTrackStatus("true");
			if(!"USER_FOLLOW".equals(trackType)){
				ordTrack.setFinishTime(new Date());
				ordTrack.setTrackStatus("false");
			}
			this.ordertrackService.updateOrdertrack(ordTrack);
			ordertrackService.saveTrackLog(o);
			//添加到日志表
			ComLog log = new ComLog();
			log.setObjectType("ORD_ORDER");
			log.setParentId(Long.valueOf(trackId));
			log.setObjectId(Long.valueOf(orderId));
			log.setOperatorName(this.getOperatorName());
			log.setLogType(Constant.COM_LOG_ORDER_EVENT.systemApprovePass.name());
			log.setLogName("订单二次跟踪");
			log.setContent("已经二次跟踪处理");
			log.setCreateTime(new Date());
			comLogService.addComLog(log);
		}
		return "index";
	}
	
	/**
	 * 跳转进入"二次跟踪处理日志"
	 */
	@Action("/ord/jumpOrdTarckLog")
	public String jumpOrdTarckLog() {
		String trackId=this.getRequest().getParameter("trackId");
		if(!StringUtil.isEmptyString(trackId)){
			this.trackLogList=this.ordertrackService.getTrackLogByTrackId(Long.valueOf(trackId));
			this.getRequest().setAttribute("orderId", trackId);
		}
		return "tarck_log";
	}
	
	/**
	 * 跳转进入"我的跟综订单"
	 */
	@Action("/ord/jumpOrdTarck")
	public String jumpOrdTarck() {
		String trackId=this.getRequest().getParameter("trackId");
		String orderId=this.getRequest().getParameter("orderId");
		this.tarckItemList = CodeSet.getInstance().getCachedCodeList(Constant.CODE_TYPE.ORDER_TRACK_TYPE.name());
		if(!StringUtil.isEmptyString(trackId)){
			this.trackLogList=this.ordertrackService.getTrackLogByTrackId(Long.valueOf(trackId));
			this.getRequest().setAttribute("trackId", trackId);
			this.getRequest().setAttribute("orderId", orderId);
		}
		return "save_tarck";
	}
	

	/**
	 * 验证领单.除去订单还在继续处理,才能继续领单.
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	@Action("/ord/doCheckOrderTrack")
	public void doCheckOrderTrack() {
		try {
			String trackQuantity=this.getRequest().getParameter("trackQuantity");
			if(!StringUtil.isEmptyString(trackQuantity)){
				Map map=new HashMap();
				map.put("beginDate", DateUtil.getYestoday18Hour());
				map.put("userName", this.getOperatorName());
				Long v=this.ordertrackService.checkTrackIsMakeOrder(map);
				if(v>0){
					this.sendAjaxMsg("fail");	
				}else{
					this.sendAjaxMsg("success");	
				}
			}else{
				this.sendAjaxMsg("fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 领单，根据前面传过来的数量,进行领单.
	 * 
	 */
	@Action("/ord/doGetOrderTrack")
	public String doGetOrderTrack() {
		String trackQuantity=this.getRequest().getParameter("trackQuantity");
		if(!StringUtil.isEmptyString(trackQuantity)){
			this.orderServiceProxy.saveOrdertrack(Long.valueOf(trackQuantity), this.getOperatorName());
		}
		return listTwoFollow();
	}
	
	/**
	 * 进入页面初始化时执行 默认为"我的跟踪订单"
	 */
	@Action("/ord/listTwoFollow")
	public String listTwoFollow(){
		compositeQuery = new CompositeQuery();
		orderStatus = new OrderStatus();
		orderContent = new OrderContent();
		orderTrack = new OrderTrackRelate();
		typeList = new ArrayList<SortTypeEnum>();
		pageIndex = new PageIndex();
		//二次跟单查询条件
		orderTrack.setSearch(true);
		orderTrack.setTrackStatus("true");
		//订单取消.
		orderStatus.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
		orderTrack.setTrackOperator(this.getOperatorName());	
		
		compositeQuery.setOrderTrackRelate(orderTrack);
		compositeQuery.setStatus(orderStatus);
		compositeQuery.setTypeList(typeList);
		compositeQuery.setPageIndex(pageIndex);
		
		Long totalRecords = orderServiceProxy.compositeQueryOrdOrderCount(compositeQuery);
		pagination = initPagination();
		pagination.setPerPageRecord(20);
		pagination.setTotalRecords(totalRecords);
		pageIndex.setBeginIndex(pagination.getFirstRow());
		pageIndex.setEndIndex(pagination.getLastRow());
		pagination.setActionUrl("ord/listTwoFollow.do?tab=1");
		
		compositeQuery.setPageIndex(pageIndex);

		ordersList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
		this.tarckItemList = CodeSet.getInstance().getCachedCodeList(Constant.CODE_TYPE.ORDER_TRACK_TYPE.name());
		return "two_follow";
	}
	
	/**
	 * 我的历史订单查询
	 */
	@Action("/ord/doHistoryQuery")
	public String doHistoryQuery() {

		String ordId = "";
		String trackCreateTimeStart = "";
		String trackCreateTimeEnd = "";
		String tempString = "&";
		String person="";
		
		ordId = this.getRequest().getParameter("ordId");
		trackCreateTimeStart = this.getRequest().getParameter("trackCreateTimeStart");
		trackCreateTimeEnd = this.getRequest().getParameter("trackCreateTimeEnd");
		person = this.getRequest().getParameter("person");
		
		compositeQuery = new CompositeQuery();
		orderStatus = new OrderStatus();
		orderContent = new OrderContent();
		orderTrack = new OrderTrackRelate();
		typeList = new ArrayList<SortTypeEnum>();
		OrderContent orderContent=new OrderContent();
		orderIdentity=new OrderIdentity();
		pageIndex = new PageIndex();
		//二次跟单查询条件
		orderTrack.setSearch(true);
		
		if (!StringUtil.isEmptyString(ordId)) {
			orderIdentity.setOrderId(Long.parseLong(ordId.trim()));
			tempString += ("ordId=" + ordId + "&");
			this.getRequest().setAttribute("ordId", ordId);
		}
		
		if (!StringUtil.isEmptyString(person)) {
			orderContent.setUserName(person);
			tempString += ("person=" + person + "&");
			this.getRequest().setAttribute("person", person);
		}
		
		if (!StringUtil.isEmptyString(trackType)) {
			orderTrack.setTrackStatus("false");
			 if("USER_FOLLOW".equals(trackType)){
				orderTrack.setTrackStatus("true");			 
			 }
			orderTrack.setTrackLogStatus(trackType);
			tempString += ("trackType=" + trackType + "&");
			this.getRequest().setAttribute("trackType", trackType);
		}else{
			orderTrack.setTrackStatus("trueOrfalse");
		}
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		if (!StringUtil.isEmptyString(trackCreateTimeStart)&& !StringUtil.isEmptyString(trackCreateTimeEnd)) {
			tempString += ("trackCreateTimeStart=" + trackCreateTimeStart + "&");
			tempString += ("trackCreateTimeEnd=" + trackCreateTimeEnd + "&");
			this.getRequest().setAttribute("trackCreateTimeStart", trackCreateTimeStart);
			this.getRequest().setAttribute("trackCreateTimeEnd", trackCreateTimeEnd);
			try {
				c.setTime(simpleDateFormat.parse(trackCreateTimeEnd)); // 因取到的结束日期为当天的0:00:00，故查询时需往后延一天，下同
				c.add(Calendar.DATE, 1);
				orderTrack.setTrackCreateTimeStart(DateUtil.toDate(trackCreateTimeStart, "yyyy-MM-dd"));
				orderTrack.setTrackCreateTimeEnd(c.getTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		orderTrack.setTrackOperator(this.getOperatorName());			
		
		//订单取消.
		orderStatus.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
		compositeQuery.setOrderIdentity(orderIdentity);
		compositeQuery.setOrderTrackRelate(orderTrack);
		compositeQuery.setStatus(orderStatus);
		compositeQuery.setTypeList(typeList);
		compositeQuery.setPageIndex(pageIndex);
		//将用户名Email等转为IdList形式查询
		Map<String, Object> param = orderContent.getUserParam(orderContent);
		if(param != null) {
			orderContent.setUserIdList(orderContent.getUserList(userUserProxy.getUsers(param)));
		}
		compositeQuery.setContent(orderContent);
		
		Long totalRecords = orderServiceProxy.compositeQueryOrdOrderCount(compositeQuery);
		pagination = initPagination();
		pagination.setTotalRecords(totalRecords);
		pageIndex.setBeginIndex(pagination.getFirstRow());
		pageIndex.setEndIndex(pagination.getLastRow());
		pagination.setActionUrl("ord/doHistoryQuery.do?tab=2"+tempString);
		
		compositeQuery.setPageIndex(pageIndex);

		historyOrdersList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
		this.tarckItemList = CodeSet.getInstance().getCachedCodeList(Constant.CODE_TYPE.ORDER_TRACK_TYPE.name());
		return "two_follow";
	}


	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}


	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}


	public CompositeQuery getCompositeQuery() {
		return compositeQuery;
	}


	public void setCompositeQuery(CompositeQuery compositeQuery) {
		this.compositeQuery = compositeQuery;
	}


	public OrderIdentity getOrderIdentity() {
		return orderIdentity;
	}


	public void setOrderIdentity(OrderIdentity orderIdentity) {
		this.orderIdentity = orderIdentity;
	}


	public OrderTimeRange getOrderTimeRange() {
		return orderTimeRange;
	}


	public void setOrderTimeRange(OrderTimeRange orderTimeRange) {
		this.orderTimeRange = orderTimeRange;
	}


	public OrderStatus getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}


	public OrderContent getOrderContent() {
		return orderContent;
	}


	public void setOrderContent(OrderContent orderContent) {
		this.orderContent = orderContent;
	}


	public PageIndex getPageIndex() {
		return pageIndex;
	}


	public void setPageIndex(PageIndex pageIndex) {
		this.pageIndex = pageIndex;
	}


	public List<SortTypeEnum> getTypeList() {
		return typeList;
	}


	public void setTypeList(List<SortTypeEnum> typeList) {
		this.typeList = typeList;
	}


	public List<OrdOrder> getOrdersList() {
		return ordersList;
	}


	public void setOrdersList(List<OrdOrder> ordersList) {
		this.ordersList = ordersList;
	}


	public List<OrdOrder> getHistoryOrdersList() {
		return historyOrdersList;
	}


	public void setHistoryOrdersList(List<OrdOrder> historyOrdersList) {
		this.historyOrdersList = historyOrdersList;
	}


	public int getTab() {
		return tab;
	}


	public void setTab(int tab) {
		this.tab = tab;
	}
	public IOrdertrackService getOrdertrackService() {
		return ordertrackService;
	}
	public void setOrdertrackService(IOrdertrackService ordertrackService) {
		this.ordertrackService = ordertrackService;
	}
	public List<CodeItem> getTarckItemList() {
		return tarckItemList;
	}
	public void setTarckItemList(List<CodeItem> tarckItemList) {
		this.tarckItemList = tarckItemList;
	}
	public String getTrackType() {
		return trackType;
	}

	public void setTrackType(String trackType) {
		this.trackType = trackType;
	}

	public List<TrackLog> getTrackLogList() {
		return trackLogList;
	}

	public void setTrackLogList(List<TrackLog> trackLogList) {
		this.trackLogList = trackLogList;
	}

	public void setOrderTrack(OrderTrackRelate orderTrack) {
		this.orderTrack = orderTrack;
	}

	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	

 }
