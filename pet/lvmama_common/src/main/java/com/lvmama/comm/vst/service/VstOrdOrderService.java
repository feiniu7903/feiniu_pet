package com.lvmama.comm.vst.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.vst.vo.VstOrdOrderItem;
import com.lvmama.comm.vst.vo.VstOrdOrderVo;

/**
 * VST订单相关服务
 * @author taiqichao
 *
 */
public interface VstOrdOrderService {
	
	/**
	 * 根据订单ID列表查询订单对象，并以Map形式返回。
	 * 
	 * @param orderIdList
	 * @return
	 */
	public List<Map<String, Object>> queryVstOrdorderByOrderIdList(List<Long> orderIdList);
	
	/**
	 * 根据订单ID查询订单对象，并以Map形式返回。
	 * 
	 * @param orderId
	 * @return
	 */
	public Map<String, Object> queryVstOrdorderByOrderId(Long orderId);
	
	/**
	 * 取消订单
	 * 
	 * @param orderId
	 * @param cancelCode
	 * @param reason
	 * @param operatorId
	 * @param memo
	 * @return
	 */
	public Boolean cancelOrder(Long orderId, String cancelCode, String reason, String operatorId, String memo);
	
	/**
	 * 获取订单详情
	 * @param orderId 订单id
	 * @return 订单信息
	 */
	VstOrdOrderVo getVstOrdOrderVo(Long orderId);
	
	/**
	 * 获取一个订单子项
	 * @param orderItemId
	 * @return
	 */
	VstOrdOrderItem getVstOrdOrderItem(final Long orderItemId);
	
	/**
	 * 更新结算状态
	 * @param orderItemId
	 * @param status
	 */
	void updateOrderItemSettlement(final Long orderItemId,String status);
}
