package com.lvmama.front.web.myspace;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdUserOrder;
import com.lvmama.comm.bee.service.ord.IOrdUserOrderService;
import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.comment.CmtTitleStatistisService;
import com.lvmama.comm.pet.service.mark.MarkCouponUserService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.CommentUtil;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtProdTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;

@Results({
	@Result(name = "index", location = "/WEB-INF/pages/myspace/sub/index.ftl", type = "freemarker")
})
public class MySpaceIndexAction extends MySpaceBaseOrderAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -7150162224298567366L;
	/**
	 * 日志控制台
	 */
	private static final Log LOG = LogFactory.getLog(MySpaceIndexAction.class);
	
	
	private Page<OrdUserOrder> pageConfig; // 订单列表
	private long waittingCommentNumber = 0;  //等待点评的订单
	private long usefulCoupon = 0; //可以使用的优惠券
	private long unPaymentOrderNumber = 0;//待支付的订单
	
	private CmtCommentService cmtCommentService;
	private CmtTitleStatistisService cmtTitleStatistisService;
	private MarkCouponUserService markCouponUserService;
	
	
	@Action(value="/myspace/index")
	public String execute() {
		if (null != getUser()) {
			this.pageConfig = Page.page(3, 1);
			
			List<OrdUserOrder> beeOrderList = new ArrayList<OrdUserOrder>();
			List<OrdUserOrder> vstOrderList = new ArrayList<OrdUserOrder>();
			//查询当前页面用户订单
			List<OrdUserOrder> userOrderList = queryUserOrderList(pageConfig);
			//将用户订单按bee和vst系统分离
			separateBeaAndVstOrder2List(userOrderList, beeOrderList, vstOrderList);
			//查找、填充bee系统订单
			fillBeeOrderByOrdUserOrder(beeOrderList);
			//查找、填充vst系统订单
			fillVstOrderByOrdUserOrder(vstOrderList);
			
			List<OrdOrder> orderList = getBeeOrdOrderListFromUserOrderList(beeOrderList);
			
			// 添加该订单是否可以点评需求
			Map<String, Object> parametersOrder = new HashMap<String, Object>();
			if (orderList != null) {
				for (OrdOrder order : orderList) {
					parametersOrder.put("orderId", order.getOrderId());
					parametersOrder.put("isHide", "displayall");
					List<CommonCmtCommentVO> cmtCommentList = cmtCommentService.getCmtCommentList(parametersOrder);
					if (cmtCommentList == null || cmtCommentList.size() == 0) {
						if (order.getMainProduct().getProductType().equals("ROUTE")|| order.getMainProduct().getProductType().equals("HOTEL")|| order.getMainProduct().getProductType().equals("TICKET")) {
							if (!order.getOrderStatus().equals(Constant.ORDER_STATUS.CANCEL.getCode())&& order.getPaymentStatus().equals(Constant.PAYMENT_STATUS.PAYED.getCode())) {
								if (order.getVisitTime() != null && DateUtil.inAdvance(order.getVisitTime(),new Date())) {
									order.setIscanComment(true);
								}
							}
						}
						if(order.getMainProduct().getProductType().equals("TICKET") && "TOSUPPLIER".equals(order.getPaymentTarget())&&(!order.getOrderStatus().equals(Constant.ORDER_STATUS.CANCEL.getCode()))){
	                        if (order.getVisitTime() != null
	                                && DateUtil.inAdvance(order.getVisitTime(),
	                                        new Date())) {
	                            order.setIscanComment(true);
	                        }
	                    }
					}
				}
			}
			this.pageConfig.setItems(userOrderList);
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("userId", getUser().getUserId());
			//waittingCommentNumber = commentRemoteService.getProductsCountByUsersOrder(parameters).intValue();
			setWaittingCommentNumber();
			
			//可用优惠券数
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("userId", getUser().getId());
			params.put("used", "false");
			params.put("applyField", "MySpaceIndex");
			usefulCoupon = markCouponUserService.selectCountByRelateUser(params);
			
			calcUnPayedOrderNumber();
			return "index";
		} else {
			debug("用户尚未登录，无法进行有效的操作", LOG);
			return ERROR;
		}
	}
	
	/**
	 * 设置待点评产品信息
	 * @return
	 */
	private void setWaittingCommentNumber()
	{
		List<CmtProdTitleStatisticsVO> needProductCommentInfoList = new ArrayList<CmtProdTitleStatisticsVO>();
		//获取可点评的订单信息
		List<OrderAndComment> canCommentOrderProductList = orderServiceProxy.selectCanCommentOrderProductByUserNo(getUser().getUserId());
		
		for(int i = 0; i < canCommentOrderProductList.size(); i++)
		{
			OrderAndComment canCommentOrderProduct = canCommentOrderProductList.get(i);
			Map<String,Object> parameters = new HashMap<String, Object>();
			parameters.put("orderId", canCommentOrderProduct.getOrderId());
			parameters.put("productId", canCommentOrderProduct.getProductId());
			parameters.put("isHide", "displayall");
			List<CommonCmtCommentVO> cmtCommentList = cmtCommentService.getCmtCommentList(parameters);
			if(cmtCommentList == null || cmtCommentList.size() == 0)//该订单产品未被点评过，可以点评
			{
				CmtProdTitleStatisticsVO cmtProdTitleStatisticsVO = CommentUtil.composeProdTitleStatistics(canCommentOrderProduct);
				needProductCommentInfoList.add(cmtProdTitleStatisticsVO);
			}
		}
		waittingCommentNumber = needProductCommentInfoList.size();
	}
	
	
	//查询
	private void calcUnPayedOrderNumber(){
		CompositeQuery compositeQuery = new CompositeQuery();
		OrderIdentity orderIdentity = compositeQuery.getOrderIdentity();
		orderIdentity.setUserId(getUserId());
		
		OrderStatus orderStatus=compositeQuery.getStatus();
		orderStatus.setPaymentStatus(Constant.PAYMENT_STATUS.UNPAY.name());
		orderStatus.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
		unPaymentOrderNumber = orderServiceProxy.compositeQueryOrdOrderCount(compositeQuery);		
	}
	
	public Page<OrdUserOrder> getPageConfig() {
		return pageConfig;
	}

	public long getWaittingCommentNumber() {
		return waittingCommentNumber;
	}

	public long getUsefulCoupon() {
		return usefulCoupon;
	}

	public long getUnPaymentOrderNumber() {
		return unPaymentOrderNumber;
	}

	public CmtCommentService getCmtCommentService() {
		return cmtCommentService;
	}

	public void setCmtCommentService(CmtCommentService cmtCommentService) {
		this.cmtCommentService = cmtCommentService;
	}

	public CmtTitleStatistisService getCmtTitleStatistisService() {
		return cmtTitleStatistisService;
	}

	public void setCmtTitleStatistisService(CmtTitleStatistisService cmtTitleStatistisService) {
		this.cmtTitleStatistisService = cmtTitleStatistisService;
	}

	public void setMarkCouponUserService(MarkCouponUserService markCouponUserService) {
		this.markCouponUserService = markCouponUserService;
	}

	public void setOrdUserOrderService(IOrdUserOrderService ordUserOrderService) {
		this.ordUserOrderService = ordUserOrderService;
	}
	
}
