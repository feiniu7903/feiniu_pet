package com.lvmama.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderTrack;
import com.lvmama.ord.dao.OrdOrderTrackDAO;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.service.IOrderCancleService;

/**
 * 订单二次跟踪处理,取特定取消的订单.
 * @author liwenzhan
 *
 */
public class OrderCancleServiceImpl extends OrderServiceImpl implements IOrderCancleService {
    
	/**
	 * 订单服务DAO.
	 */
	private OrderDAO orderDAO;
	/**
	 * 二次跟单处理DAO.
	 */
	private OrdOrderTrackDAO ordOrderTrackDAO;
	/**
	 * 订单二次跟踪处理,取特定取消的订单.
	 *  <pre>
	 * 因资源审核未通过而取消的，可立即进行二次跟踪处理.
	 * 超时未支付系统自动取消的，取消半个小时后可进行二次跟踪处理.
	 * 客户线上自己取消的，取消半小时后可进行二次跟踪处理.
	 * </pre>
	 * @return
	 */
	public List<OrdOrder> queryOrderNotTrack(final Long number){
		Map<String, String> params = new HashMap<String, String>();
		params.put("number", number.toString());
		return orderDAO.queryOrderNotTrack(params);
	}
	
	/**
	 * 生成二次跟踪处理记录.
	 * @param number
	 * @param userName
	 */
	 public void saveOrdertrack(final Long number,final String userName){
		 List<OrdOrder> list=this.queryOrderNotTrack(number);
		 if(list!=null&&list.size()>0){
			 for (OrdOrder order : list) {
				 OrdOrderTrack orderTrack=new OrdOrderTrack();
				 orderTrack.setCreateTime(new Date());
				 orderTrack.setOrderId(order.getOrderId());
				 orderTrack.setUserName(userName);
				 ordOrderTrackDAO.saveOrdertrack(orderTrack);
			 }
		 }
	 }
	
	
	/**
	 * 设置订单服务DAO.
	 * @param orderDAO
	 */
	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	/**
	 * 设置二次跟单处理DAO.
	 * @param ordOrderTrackDAO
	 */
	public void setOrdOrderTrackDAO(OrdOrderTrackDAO ordOrderTrackDAO) {
		this.ordOrderTrackDAO = ordOrderTrackDAO;
	}
	
	
}
