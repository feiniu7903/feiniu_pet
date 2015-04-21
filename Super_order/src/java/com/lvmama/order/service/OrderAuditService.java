package com.lvmama.order.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.pub.ComAudit;

/**
 * 领单服务.
 *
 *
 * @author wuwei
 * @author tom

 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.ord.po.OrdOrder
 * @see com.lvmama.ord.po.OrdOrderItemMeta
 */
public interface OrderAuditService {

	OrdOrder getOrderByOrderItemMetaId(Long orderItemMetaId);
	
	/**
	 * 根据订单类型OrdOrder领单.
	 *
	 * @param operatorId
	 *            领单人ID
	 * @param orderType
	 *            订单类型
	 * @return Audit
	 */
	OrdOrder makeOrdOrderAudit(String operatorId, String orderType);
	
	/**
	 * 根据订单id领单
	 * @param operatorId
	 * @param orderId
	 * @return
	 * @author zhushuying
	 */
	OrdOrder makeOrdOrderAuditById(String operatorId, Long orderId);
	
	/**
	 * 根据订单编号OrdOrder领单（分单）.
	 *
	 * @author luoyinqi
	 *
	 * @param operatorId
	 *            领单人ID
	 * @param orderId
	 * 			  订单编号           
	 * @return row
	 */
	boolean makeOrdOrderAuditByOrderId(String operator, Long orderId,String assignUser );
	
	/**
	 * 根据订单编号分配无需审核的订单
	 * @param operator
	 * @param orderId
	 * @param assignUser
	 * @return
	 */
	boolean makeOrdOrderConfirmAuditByOrderId(String operator, Long orderId,String assignUser );

	/**
	 * 根据销售产品类型OrdOrderItemMeta领单.
	 *
	 * @param operatorId
	 *            领单人ID
	 * @param productType
	 *            销售产品类型
	 * @return Audit
	 */
	OrdOrderItemMeta makeOrdOrderItemMetaAudit(String operatorId,
			String productType);

	/**
	 * 指定销售产品OrdOrderItemMeta领单批量.
	 *
	 * @param operatorId
	 *            领单人ID
	 * @param itemMetaIdList
	 *            销售产品类型
	 * @return <pre>
	 * <code>true</code>代表领单成功，<code>false</code>代表领单失败
	 * </pre>
	 */
	List<OrdOrderItemMeta> makeOrdOrderItemMetaListToAudit(String operatorId,
			List<Long> orderItemMetaIdList);
	/**
	 * 指定销售产品OrdOrderItemMeta领单批量.
	 *
	 * @param operatorId
	 *            领单人ID
	 * @param itemMetaIdList
	 *            销售产品类型
	 * @return <pre>
	 * <code>true</code>代表领单成功，<code>false</code>代表领单失败
	 * </pre>
	 */
	boolean makeOrdOrderItemMetaToAuditByAssignUser(String operatorId,String assignUserId,OrdOrderItemMeta item); 
	/**
	 * 取消OrdOrder领单.
	 *
	 * @param operatorId
	 *            操作人ID
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * <code>true</code>代表取消领单成功，<code>false</code>代表取消领单失败
	 * </pre>
	 */
	boolean cancelOrdOrderAudit(String operatorId, Long orderId);

	boolean cancelOrdOrderAuditByOrderId(String operator, Long orderId);
	/**
	 * 取消OrdOrderItemMeta领单.
	 *
	 * @param operatorId
	 *            操作人ID
	 * @param ordOrderItemMetaId
	 *            OrdOrderItemMeta的ID
	 * @return <pre>
	 * <code>true</code>代表取消领单成功，<code>false</code>代表取消领单失败
	 * </pre>
	 */
	boolean cancelOrdOrderItemMetaAudit(String operatorId,
			Long ordOrderItemMetaId);
	
	boolean canGoingBack(Map<String, String> params);
	
	boolean canRecycle(Map<String, String> params);
	
	
	/**
	 * 取审核列表,按条件
	 * @param params
	 * @return
	 */
	List<ComAudit> selectComAuditByParam(Map<String,String> params);
	/**
	 * 取得分单数据的个数
	 * @param params
	 * @return
	 */
	public Long selectComAuditCountByParams(Map<String, Object> params);
}
