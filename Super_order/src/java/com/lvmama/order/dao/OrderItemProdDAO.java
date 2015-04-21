package com.lvmama.order.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;

/**
 * 销售产品订单子项DAO接口.
 *
 * <pre>
 * 封装销售产品订单子项CRUD
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.ord.po.OrdOrderItemProd
 */
public interface OrderItemProdDAO {
	Long calcOrderAmount(Long orderId);

	Long insert(OrdOrderItemProd record);
	
	void updateByPrimaryKey(final OrdOrderItemProd record);
		
	List<OrdOrderItemProd> selectByOrderId(Long orderId);
	
	OrdOrderItemProd selectByPrimaryKey(Long orderItemProdId);
	
	boolean isSuperFreeMainProd(Long orderItemProductId);
	
	void updateOrderItemProdPaidAmount(final long orderId);
	/**不定期订单,下单完成后清除游玩日期*/
	void clearVisitTime(Long orderId);
	/**根据批量productId查询订单总数*/ 
	Long getOrderCountByProductIds(String[] productIds,Date startTime,Date endTime);
}
