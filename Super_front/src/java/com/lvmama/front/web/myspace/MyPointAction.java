package com.lvmama.front.web.myspace;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.shop.ShopOrderService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.pet.vo.UserPointLogWithDescription;


/**
 * 我的积分Action层
 * @author Brian
 *
 */
@Results({
	@Result(name = "myPoint", location = "/WEB-INF/pages/myspace/sub/myPoints.ftl", type = "freemarker"),
	//@Result(name = "myPointDescription",location = "/WEB-INF/ftl/myPoints/myPointsDescription.ftl", type = "freemarker"),
	@Result(name = "error", location = "/WEB-INF/pages/product/404.ftl", type = "freemarker")
})
public class MyPointAction extends SpaceBaseAction {
	/**
	 * 序列化值
	 */
	private static final long serialVersionUID = 6049504587570376504L;
	/**
	 * 日志记录器
	 */
	private static final Log LOG = LogFactory.getLog(MyPointAction.class);
	/**
	 * 每页显示行数
	 */
	private static final Long ROW_NUMBER = 10L;
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
	 * 当前页码
	 */
	private Long currentPage = 1L;
	/**
	 * 用户积分记录
	 */
	private List<UserPointLogWithDescription> userPointLogWithDescriptionList;
	/**
	 * 用户操作的逻辑层
	 */
	private UserUserProxy userUserProxy;
	/**
	 * 分页配置信息
	 */
	private Page<UserPointLogWithDescription> pageConfig;
	
	/**
	 * 积分商城的远程调用接口
	 */
	private ShopOrderService shopOrderService;
	
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
	 * 我的积分
	 * @return 跳转页面
	 */
	@SuppressWarnings("unchecked")
	@Action("/myspace/account/points")
	public String myPoint() {
		UserUser users = super.getUser();
		if (null == users) {
			LOG.error("Cann't get user's point log for empty userId");
			return ERROR;
		}

		if(userPointPageIndex != -1)
		{
			currentPage = userPointPageIndex;
		}
		else
		{
			userPointPageIndex = currentPage;
		}
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userId", users.getId());
		
		orderCount = shopOrderService.orderCount(parameters);
		
		//分页逻辑
		//parameters.put("getPoint", true);
		userPointCount = userUserProxy.getCountUserPointLog(parameters);
		pageConfig = new Page<UserPointLogWithDescription>(userPointCount, ROW_NUMBER, currentPage);
		parameters.put("_startRow", (pageConfig.getStartRows()) + "");
		parameters.put("_endRow", pageConfig.getEndRows() + "");
		userPointLogWithDescriptionList = userUserProxy.getPointLog(parameters);
		pageConfig.setItems(userPointLogWithDescriptionList);
		if (pageConfig.getItems().size() > 0) {
			this.pageConfig.setUrl("/myspace/account/points.do?currentPage=");
		}

		//积分日志列表
		currentPoint = userUserProxy.getUserUserByPk(users.getId()).getPoint();
		currentPoint = null == currentPoint ? 0L : currentPoint;
		usedPoint = userUserProxy.getUsedUsersPoint(users.getId());
		usedPoint = null == usedPoint ? 0L : usedPoint;
		aboutToExpiredPoint = userUserProxy.getAboutToExpiredUsersPoint(users.getId());
		aboutToExpiredPoint = null == aboutToExpiredPoint ? 0L : aboutToExpiredPoint;
		
		return "myPoint";
	}

	/**
	 * 积分说明
	 * 目前页面不用
	 * @return 跳转页面
	 */
	@Action("/myspace/point/myPointDescription")
	public String myPointDescription() {
		return "myPointDescription";
	}
	
	public void setShopOrderService(final ShopOrderService shopOrderService) {
		this.shopOrderService = shopOrderService;
	}

	public Long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(final Long currentPage) {
		this.currentPage = currentPage;
	}

	public List<UserPointLogWithDescription> getUserPointLogWithDescriptionList() {
		return userPointLogWithDescriptionList;
	}

	public void setUserUserProxy(final UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public Long getCurrentPoint() {
		return currentPoint;
	}

	public Long getAboutToExpiredPoint() {
		return aboutToExpiredPoint;
	}

	public Page<UserPointLogWithDescription> getPageConfig() {
		return pageConfig;
	}

	public Long getUsedPoint() {
		return usedPoint;
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
