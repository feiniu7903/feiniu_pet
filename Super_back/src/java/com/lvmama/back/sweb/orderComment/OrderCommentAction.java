package com.lvmama.back.sweb.orderComment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.axis.transport.http.AbstractQueryStringHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.ord.OrderUpdateService;
import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
//super_back/WEB-INF/pages/orderComment/ WEB-INF/pages/back/ord/orderComment/order_manual.jsp
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;

@ParentPackage("json-default")
@Results({ 
	@Result(name = "order_comment_list", location = "/WEB-INF/pages/back/ord/orderComment/order_comment.jsp"),
	@Result(name = "order_manual_list",  location = "/WEB-INF/pages/back/ord/orderComment/order_manual.jsp")
})
public class OrderCommentAction  extends BaseAction {
	private static final long serialVersionUID = -670220168384752717L;
	private OrderService orderServiceProxy;
	
	//引入hession接口, 填写配置
	private CmtCommentService cmtCommentService;
	private OrderAndComment orderAndComment;
	
	
	private String orderId;
	private OrdOrder order;
	private UserUser user;
	/**
	 * 订购并点评处理器.
	 */
	
	private CashAccountService cashAccountService;
	
	/**
	 * SSO远程调用服务
	 */
	private UserUserProxy userUserProxy;
	
	private List<OrderAndComment>  orderCommentlist = new ArrayList<OrderAndComment>();
	 
	/**
	 * 默认进入列表页面，不读取数据.
	 * @return
	 */
	@Action(value = "/orderComment/goOrderComment")
	public String goBatchList(){
		return "order_comment_list";
	}
	
	/**
	 * 查询该订单是否能返现
	 * @return
	 */
	@Action(value = "/orderComment/orderCommentList")
	public String commentList(){
		
		if (StringUtils.isNotEmpty(orderId.trim())) {
			
			//当前时间获取履行状态能返现的订单,没状态时间限制.
			final List<OrderAndComment> orderList = (List<OrderAndComment>) orderServiceProxy.selectCanRefundOrderByOrderId(Long.parseLong(orderId.trim()));
			
			//获取到同时满足订单和点评条件的记录
			List<OrderAndComment> canRefundOrderAndCommentList = getCanRefundObjectMeetCommentCondition(orderList);
			
			//页面展示对象
			orderCommentlist = new ArrayList<OrderAndComment>();
			if (canRefundOrderAndCommentList != null && canRefundOrderAndCommentList.size() > 0) {
				Set<OrderAndComment> set = new java.util.TreeSet<OrderAndComment>();
				set.addAll(canRefundOrderAndCommentList);
				for (OrderAndComment e : set) {
					orderCommentlist.add(e);
				}
			}
		}
		return "order_comment_list";
	}
	
	 
	/**
	 * 对该订单做返现操作
	 * @return
	 */
	@Action(value = "/orderComment/cashOrder")
	public String cashOrder() {
		
		if (StringUtils.isNotEmpty(orderId.trim())) {
			
			//当前时间获取履行状态能返现的订单,没状态时间限制.
			final List<OrderAndComment> orderList = (List<OrderAndComment>) orderServiceProxy.selectCanRefundOrderByOrderId(Long.parseLong(orderId.trim()));
			
			//获取到同时满足订单和点评条件的记录
			List<OrderAndComment> canRefundOrderAndCommentList = getCanRefundObjectMeetCommentCondition(orderList);
			LOG.debug("target quantity " + canRefundOrderAndCommentList.size());
			
			if (canRefundOrderAndCommentList != null && canRefundOrderAndCommentList.size() > 0) {
				Set<OrderAndComment> set = new java.util.TreeSet<OrderAndComment>();
				set.addAll(canRefundOrderAndCommentList);
				
				for (OrderAndComment orderAndComment : set) {
					//奖金账户返现
					cashAccountService.returnBonusForOrderComment(orderAndComment);
					
					//订单返现
					orderServiceProxy.cashOrder(
							Long.parseLong(orderAndComment.getOrderId()),
							orderAndComment.getCashRefund());
					//点评返现
					cmtCommentService.updateExperienceComment(
							Long.valueOf(orderAndComment.getOrderId()),
							Long.valueOf(orderAndComment.getCommentId()),
							Long.valueOf(orderAndComment.getCashRefund()));
				}
			}
		}
		return "order_comment_list";
	}
	
	/**
	 * 获取同时满足订单和点评条件的记录
	 * @param orderList(能返现订单)
	 * @return
	 */
	private List<OrderAndComment> getCanRefundObjectMeetCommentCondition(List<OrderAndComment> orderList) {
		List<OrderAndComment>  list = new ArrayList<OrderAndComment>();
		for (OrderAndComment e : orderList) {
			
			UserUser userUser = userUserProxy.getUserUserByUserNo(e.getUserNo());
			
			//获取同时满足点评条件的记录(获取返现的点评,没时间限制(游玩后写的体验点评)
			OrderAndComment cmt = cmtCommentService.selectCanRefundComment(
					Long.valueOf(e.getOrderId()),
					userUser.getId(), 
					e.getOrderVisitTime());
			if (cmt == null)  break;

			e.setCommentId(cmt.getCommentId());
			e.setCreateDate(cmt.getCreateDate());
			e.setUserName(cmt.getUserName());
			e.setUserId(userUser.getId());
			list.add(e);
		}
		return list;
	}
	
	
	/**
	 * 默认进入手动返现页面
	 * @return
	 */
	@Action(value = "/orderComment/goCatchOrder")
	public String goCatchOrder(){
		return "order_manual_list";
	}

	/**
	 * 点击查询 搜索有效订单
	 * @return
	 */
	@Action(value = "/orderComment/toSeatchOrder")
	public String toSeatchOrder(){
		if(StringUtil.isEmptyString(orderId)){
			order=null;
		}else{
		orderId = StringUtils.trimToEmpty(orderId);
		if (orderId.indexOf(" ")<0){
			order = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId));
			if(order!=null){
				user = userUserProxy.getUserUserByUserNo(order.getUserId());
				Date ordTime = order.getCreateTime();
					if("CANCEL".equals(order.getOrderStatus())){
						order=null;
					}else{
						//订单有效时间在一年内
						if(DateUtil.isCompareTime(ordTime,DateUtil.mergeDateTimeAddYear(new Date(),-1))){
							order=null;
						}
					}
				}
			}
		}
		return "order_manual_list";
	}

	/**
	 * 手动处理返现
	 * @return
	 */
	@Action(value = "/orderComment/toOrderManualAdjust")
	public String toSaveOrderCashAccount(){
		orderAndComment.setOrderId(StringUtils.trimToEmpty(orderAndComment.getOrderId()));
		if(StringUtils.isNotBlank(orderAndComment.getOrderId()) && null!=orderAndComment.getCashRefund() && StringUtils.isNotBlank(orderAndComment.getUserName())){
		OrdOrder o = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderAndComment.getOrderId()));
			if(null!=o){
				user = userUserProxy.getUserUserByUserNo(o.getUserId());
				if(null!=orderAndComment.getUserName() && orderAndComment.getUserName().equals(user.getUserName())){
					if(orderAndComment.getCashRefund()<=100 && orderAndComment.getCashRefund()>0){
						orderAndComment.setUserId(user.getId());
							if(o.getCashRefund()>orderAndComment.getCashRefund()*100){
								//存入数据库的 处理为分
								o.setCashRefund(o.getCashRefund()-orderAndComment.getCashRefund()*100);
							}else{
								o.setCashRefund(Long.valueOf(0));
							}
						if("false".equals(o.getIsCashRefund())){
							o.setIsCashRefund("true");
						}
						if(orderServiceProxy.updateIsCashRefundByOrderId(o)){
							orderAndComment.setCashRefund(orderAndComment.getCashRefund()*100);
						    cashAccountService.returnOrderManualAdjust(orderAndComment);
						}
					}					
				}
			}
		}
		return "order_manual_list";
	}
	
	
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<OrderAndComment> getOrderCommentlist() {
		return orderCommentlist;
	}

	public void setOrderCommentlist(List<OrderAndComment> orderCommentlist) {
		this.orderCommentlist = orderCommentlist;
	}

	public void setCmtCommentService(CmtCommentService cmtCommentService) {
		this.cmtCommentService = cmtCommentService;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public CashAccountService getCashAccountService() {
		return cashAccountService;
	}

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}

	public OrdOrder getOrder() {
		return order;
	}

	public void setOrder(OrdOrder order) {
		this.order = order;
	}

	public UserUser getUser() {
		return user;
	}

	public void setUser(UserUser user) {
		this.user = user;
	}

	public OrderAndComment getOrderAndComment() {
		return orderAndComment;
	}

	public void setOrderAndComment(OrderAndComment orderAndComment) {
		this.orderAndComment = orderAndComment;
	}
	
}
