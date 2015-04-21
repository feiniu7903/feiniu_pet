package com.lvmama.tnt.partner.hotel.service;

import com.lvmama.tnt.partner.comm.RequestVO;
import com.lvmama.tnt.partner.comm.ResponseVO;
import com.lvmama.vst.api.hotel.order.vo.OrderRequestVo;
import com.lvmama.vst.api.hotel.order.vo.OrderVo;
import com.lvmama.vst.api.vo.PageVo;

/**
 * 订单查询接口
 * 
 * @author gaoyafeng
 * 
 */
public interface OrderQueryService {

	/**
	 * 查询订单详情
	 * 
	 * @param DistributorId
	 *            分销商ID
	 * @param orderId
	 *            订单ID
	 * @return 订单对象
	 */
	ResponseVO<OrderVo> findOrderDetail(
			RequestVO<Long> orderIdInfo, Long distributorId);

	/**
	 * 查询订单分页列表
	 * 
	 * @param vstPage
	 *            订单列表分页对象
	 * @param orderRequestVo
	 *            订单查询对象 （可为空）
	 * @return 订单分页对象
	 */
	ResponseVO<PageVo<OrderVo>> findOrderList(
			RequestVO<OrderRequestVo> orderRequestVo, PageVo<OrderVo> page);

}
