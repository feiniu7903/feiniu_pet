package com.lvmama.tnt.partner.hotel.service;

import com.lvmama.tnt.partner.comm.RequestVO;
import com.lvmama.tnt.partner.comm.ResponseVO;
import com.lvmama.vst.api.hotel.order.vo.OrderCancelVo;
import com.lvmama.vst.api.hotel.order.vo.OrderInfoVo;
import com.lvmama.vst.api.hotel.order.vo.OrderVo;

/**
 * 订单操作
 * 
 * @author gaoyafeng
 * 
 */
public interface OrderManageService {

	/**
	 * 创建订单
	 * 
	 * @param vstOrderVo
	 *            订单对象
	 * @return 订单对象 （成功则返回订单对象，失败则为null）
	 */
	ResponseVO<OrderVo> createOrder(RequestVO<OrderInfoVo> orderInfo);

	/**
	 * 订单取消
	 * 
	 * @param DistributorId
	 *            分销商ID
	 * @param orderId
	 *            订单ID
	 * @param cancelReson
	 *            取消原因
	 * @param cancelCode
	 *            取消代码
	 * @return 订单取消结果
	 */
	ResponseVO<OrderCancelVo> cancelOrder(
			RequestVO<Long> orderIdInfo, Long distributorId,
			String cancelReson, String cancelCode);


}
