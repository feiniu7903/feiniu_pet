package com.lvmama.train.service;

import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderTraffic;
import com.lvmama.comm.vo.train.product.TicketBookInfo;

public interface TrainOrderService {
	/**
	 * 创建火车票订单
	 * @param orderId
	 */
	public void createTrainOrder(Long orderId);
	/**
	 * 取消火车票订单
	 * @param orderId
	 */
	public void cancelTrainOrder(Long orderId);
	/**
	 * 退票请求
	 * @param orderId
	 */
	public void drawbackTrainTicket(Long ticketId);
	/**
	 * 退款成功请求
	 * @param orderId
	 */
	public void refundTrainTicketSuccess(Long refundId);
	/**
	 * 订单状态查询
	 * @param orderId
	 * @return 订单状态码
	 */
	public int queryTrainTicketStatus(Long orderId) throws Exception;
	/**
	 * 订票信息查询
	 * @param departStation 始发车站
	 * @param arriveStation 到达车站
	 * @param departDate 出发日期
	 * @param trainId 车次Id
	 * @return
	 * @throws Exception
	 */
	public List<TicketBookInfo> queryTicketBookInfo(String departStation, String arriveStation, 
			String departDate, String trainId) throws Exception;
	
	/**
	 * 重新向供应商推消息
	 * */
	public boolean sendTrafficOrderToSupplier(OrdOrderTraffic traffic, OrdOrder order, OrdOrderItemMeta adult, 
			OrdOrderItemMeta child, String visitTime) throws Exception;
	
	/**
	 * 发送订单支付成功请求到供应商
	 * */
	public void sendTrafficOrderPaySuccessToSupplier(OrdOrderTraffic traffic, OrdOrder order) throws Exception;
}
