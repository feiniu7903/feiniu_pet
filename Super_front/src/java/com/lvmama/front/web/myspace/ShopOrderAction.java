package com.lvmama.front.web.myspace;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.shop.ShopOrder;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.shop.ShopOrderService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.Page;
/**
 * 积分商城订单Action
 * @author ganyingwen
 *
 */
@Results({
	@Result(name = "orderList", location = "/WEB-INF/pages/myspace/sub/myPoints.ftl", type = "freemarker"),
	@Result(name = "orderDetail", location = "/WEB-INF/pages/shop/shopOrderDetail.ftl", type = "freemarker"),
	@Result(name = "login", location = "http://login.lvmama.com/nsso", type = "redirect"),
	@Result(name = "error", location = "/WEB-INF/pages/product/404.ftl", type = "freemarker")
})
public class ShopOrderAction extends SpaceBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 7841246275506897221L;
	/**
	 * 日志输入器
	 */
	private static final Log LOG = LogFactory.getLog(ShopOrderAction.class);
	/**
	 * 积分商城的远程调用接口
	 */
	private ShopOrderService shopOrderService;
	/**
	 * 用户操作的逻辑层
	 */
	private UserUserProxy userUserProxy;
	/**
	 * 当前积分
	 */
	private Long currentPoint;
	/**
	 * 年末过期的积分
	 */
	private Long aboutToExpiredPoint;
	
	/**
	 *用户使用过的积分
	 */
	private Long usedPoint;
	/**
	 * 订单标识
	 */
	private Long orderId;
	/**
	 * 订单列表
	 */
	private List<ShopOrder> shopOrderList;
	/**
	 * 订单
	 */
	private ShopOrder shopOrder;
	/**
	 * 每页显示行数
	 */
	private static final Long ROW_NUMBER = 10L;
	/**
	 * 当前页码
	 */
	private Long currentPage = 1L;
	/**
	 * 分页配置信息
	 */
	private Page<ShopOrder> pageConfig;
	/**
	 * 产品类型
	 */
	private String productType = "";
	
	private Long userPointCount;
	
	private Long orderCount;
	
	/**
	 * 记住用户获取积分页的页码
	 */
	private Long userPointPageIndex = -1l;
	
	/**
	 * 记住用户兑换积分页的页码
	 */
	private Long orderPageIndex = -1l;

	

	/**
	 * 订单列表
	 * @return 列表标志
	 */
	@SuppressWarnings("unchecked")
	@Action("/myspace/account/points_order")
	public String orderList() {
		UserUser users = validateLogin();
		if (users == null) {
			return "login";
		}
		if(orderPageIndex != -1)
		{
			currentPage = orderPageIndex;
		}
		else
		{
			orderPageIndex = currentPage;
		}
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userId", users.getId());
		if (!(StringUtils.isEmpty(productType) || "".equals(productType))) {
			parameters.put("productType", productType);
			currentPage = 1L;
		}
	
		orderCount = shopOrderService.orderCount(parameters);
		//parameters.put("getPoint", "true");
		userPointCount = userUserProxy.getCountUserPointLog(parameters);
		pageConfig = new Page(orderCount, ROW_NUMBER, currentPage);
		parameters.put("_startRow", (pageConfig.getStartRows()) + "");
		parameters.put("_endRow", pageConfig.getEndRows() + "");
		shopOrderList = shopOrderService.queryShopOrder(parameters);
		pageConfig.setItems(shopOrderList);
		if (pageConfig.getItems().size() > 0) {
			this.pageConfig.setUrl("/myspace/account/points_order.do?productType=" + productType + "&currentPage=");
		}

		//积分信息
		currentPoint = userUserProxy.getUserUserByPk(users.getId()).getPoint();
		currentPoint = null == currentPoint ? 0L : currentPoint;
		usedPoint = userUserProxy.getUsedUsersPoint(users.getId());
		usedPoint = null == usedPoint ? 0L : usedPoint;
		aboutToExpiredPoint = userUserProxy.getAboutToExpiredUsersPoint(users.getId());
		aboutToExpiredPoint = null == aboutToExpiredPoint ? 0L : aboutToExpiredPoint;
		
		return "orderList";
	}

	/**
	 * 订单详情
	 * 目前页面不用
	 * @return 订单详情标志
	 */
	@Action("/myspace/shop/orderdetail")
	public String orderDetail() {
		UserUser users = validateLogin();
		if (users == null) {
			return "login";
		}
		String value = getRequest().getParameter("orderId");
		if (!StringUtils.isEmpty(value)) {
			Long orderId1 = Long.parseLong(value);
			shopOrder = shopOrderService.queryShopOrderByKey(orderId1);
			if(null == shopOrder || (users.getId().compareTo(shopOrder.getUserId())!=0)){
				return ERROR;
			}
			if(null != shopOrder && null != shopOrder.getProductInfo()){
				shopOrder.setProductInfo(shopOrder.getProductInfo().replace("\r\n", "<br/>"));
			}
			return "orderDetail";
		}
		return ERROR;
	}

	/**
	 * 合法登陆
	 * @return 用户
	 */
	private UserUser validateLogin() {
		UserUser users = super.getUser();
		if (null == users) {
			LOG.error("用户标识为空，无法进行操作");
			return null;
		}
		return users;
	}

	public Long getOrderId() {
		return orderId;
	}

	public List<ShopOrder> getShopOrderList() {
		return shopOrderList;
	}

	public Long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(final Long currentPage) {
		this.currentPage = currentPage;
	}

	public Page<ShopOrder> getPageConfig() {
		return pageConfig;
	}

	public void setPageConfig(final Page<ShopOrder> pageConfig) {
		this.pageConfig = pageConfig;
	}

	public static Long getRowNumber() {
		return ROW_NUMBER;
	}

	public ShopOrder getShopOrder() {
		return shopOrder;
	}

	public Long getCurrentPoint() {
		return currentPoint;
	}

	public Long getAboutToExpiredPoint() {
		return aboutToExpiredPoint;
	}

	public Long getUsedPoint() {
		return usedPoint;
	}
	
	public void setProductType(final String productType) {
		this.productType = productType;
	}

	public String getProductType() {
		return productType;
	}

	public void setShopOrderService(final ShopOrderService shopOrderService) {
		this.shopOrderService = shopOrderService;
	}

	public void setUserUserProxy(final UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public Long getUserPointCount() {
		return userPointCount;
	}

	public void setUserPointCount(Long userPointCount) {
		this.userPointCount = userPointCount;
	}

	public Long getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
	}

	public Long getUserPointPageIndex() {
		return userPointPageIndex;
	}

	public void setUserPointPageIndex(Long userPointPageIndex) {
		this.userPointPageIndex = userPointPageIndex;
	}

	public Long getOrderPageIndex() {
		return orderPageIndex;
	}

	public void setOrderPageIndex(Long orderPageIndex) {
		this.orderPageIndex = orderPageIndex;
	}
}
