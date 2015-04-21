package com.lvmama.order.service;

import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.ord.dao.OrdOrderTrackDAO;
import com.lvmama.order.dao.OrderDAO;

/**
 * 订单二次跟踪处理,取特定取消的订单.
 * @author liwenzhan
 *
 */
public interface IOrderCancleService {

	/**
	 * 订单二次跟踪处理,取特定取消的订单.
	 *  <pre>
	 * 因资源审核未通过而取消的，可立即进行二次跟踪处理.
	 * 超时未支付系统自动取消的，取消半个小时后可进行二次跟踪处理.
	 * 客户线上自己取消的，取消半小时后可进行二次跟踪处理.
	 * </pre>
	 * @return
	 */
	List<OrdOrder> queryOrderNotTrack(Long RowNum);
	
	/**
	 * 生成二次跟踪处理记录.
	 * @param number
	 * @param userName
	 */
	 void saveOrdertrack(final Long number,final String userName);

	/**
	 * 设置订单服务DAO.
	 * @param orderDAO
	 */
	void setOrderDAO(OrderDAO orderDAO);
	

	/**
	 * 设置二次跟单处理DAO.
	 * @param ordOrderTrackDAO
	 */
	 void setOrdOrderTrackDAO(OrdOrderTrackDAO ordOrderTrackDAO);

}