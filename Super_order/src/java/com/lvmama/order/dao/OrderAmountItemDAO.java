package com.lvmama.order.dao;

import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;

/**
 * 订单金额DAO接口.
 *
 * <pre>
 * 封装订单金额CRUD
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.ord.po.OrdOrderAmountItem
 */
public interface OrderAmountItemDAO {
	Long insert(OrdOrderAmountItem record);
	void updateByPrimaryKey(final OrdOrderAmountItem record);
	List<OrdOrderAmountItem> querOrderAmountItemByOrderId(final Long orderId);
}
