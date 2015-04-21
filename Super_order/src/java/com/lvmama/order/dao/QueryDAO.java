package com.lvmama.order.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderSum;
import com.lvmama.comm.bee.po.ord.OrdOrderTraffic;
import com.lvmama.comm.bee.po.ord.OrdOrderTrafficTicketInfo;
import com.lvmama.comm.bee.vo.ord.PerformDetail;

/**
 * 查询DAO接口.
 * 
 * <pre></pre>
 * 
 * 
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.ord.po.OrdOrder
 */
public interface QueryDAO extends QueryExtendDAO {
	/**
	 * 根据订单ID查询{@link OrdOrder}.
	 * 
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * 指定订单ID的{@link OrdOrder}，如果指定ID的{@link OrdOrder}不存在则返回<code>null</code>
	 * </pre>
	 */
	OrdOrder queryOrdOrder(Long orderId);

	/**
	 * 根据订单号查询{@link OrdOrder}.
	 * 
	 * @param orderNo
	 *            订单号
	 * @return <pre>
	 * 指定订单号的{@link OrdOrder}，如果指定订单号的{@link OrdOrder}不存在则返回<code>null</code>
	 * </pre>
	 */
	OrdOrder queryOrdOrderByOrderNo(String orderNo);	

	/**
	 * 组合查询，查询订单三种金额的总额
	 * @param completeSQL
	 * @return
	 */
	OrdOrderSum queryOrdOrderPaySum(String completeSQL);
 
	/**
	 * @param orderItemMetaId
	 * @return
	 */
	OrdOrderItemMeta queryOrdOrderItemMetaById(final Long orderItemMetaId);

	List<OrdOrderItemMeta> queryOrderItemMetaListRefund(Long refundmentId, Long orderId);
	
	List<OrdOrderItemMeta> queryOrdOrderItemMetaList(String sql);
	
	Long queryOrderByParams1(Map<String, Object> params);
	
	List<Map<String, Object>> queryOrderByParams2(Map<String, Object> params);

	/**
	 * 查询此订单的毛利润
	 * 
	 * @param orderId
	 * @return
	 */
	Long queryOrderProfitByOrderId(Long orderId);

	List<PerformDetail> queryPerformDetailForEplaceList(Map<String, Object> params);
	Long queryOrderLimitByUserId(Map<String, Object> params);
	Long queryOrderLimitByMobile(Map<String, Object> params);

	Long queryOrderLimitByCertNo(Map<String, Object> params);
	List<Long> queryOrderLimitByParam(Map<String,Object> params);
	
	
	List<OrdOrderTraffic> queryOrderTrafficList(final Long orderId);
	List<OrdOrderTrafficTicketInfo> queryOrderTrafficTicketInfo(final Long orderId);
	/**
	 * 订单统计
	 * @param params
	 * @return
	 */
	Long queryOrdOrderCount(Map<String, Object> params);
}
