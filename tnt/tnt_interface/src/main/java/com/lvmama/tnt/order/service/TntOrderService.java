package com.lvmama.tnt.order.service;

import java.util.List;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.ResultGod;
import com.lvmama.tnt.order.po.TntOrder;
import com.lvmama.tnt.order.vo.TntBuyInfo;

/**
 * 分销平台订单
 * 
 * @author gaoxin
 * 
 */
public interface TntOrderService {

	public void insert(TntOrder entity);

	public List<TntOrder> findPage(Page<TntOrder> page);

	public int count(TntOrder entity);

	public int update(TntOrder tntOrder);

	public int delete(Long tntOrderId);

	public TntOrder getById(Long tntOrderId);

	public TntOrder getByOrderId(Long orderId);
	
	public TntOrder getDetailByOrderId(Long orderId);

	public TntOrder get(TntOrder t);

	public TntOrder getWithItems(TntOrder t);

	public TntOrder buildItems(TntOrder t);

	public List<TntOrder> removeCanotPay(List<TntOrder> list);

	public List<TntOrder> queryWithItems(List<Long> orderIds, Long userId);

	public List<TntOrder> queryCanPay(List<Long> orderIds, Long userId);

	public boolean changePayStatus(Long tntOrderId, String status);

	public Long sumAmount(List<TntOrder> orders);

	public String checkOrder(TntOrder t);

	public ResultGod<TntOrder> createOrder(TntBuyInfo buyInfo);
	
	public void synOrder(Long orderId);

	public void synOrderRefund(Long refundmentId);

	/**
	 * B2B平台取消未付款订单
	 * @param tntOrder
	 * @return
	 */
	public boolean cancelOrder(TntOrder tntOrder);

}
