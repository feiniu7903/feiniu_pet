package com.lvmama.order.dao.impl;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.order.dao.OrderAmountItemDAO;

/**
 * 订单金额DAO实现类.
 *
 * <pre>
 * 封装订单金额CRUD
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.BaseIbatisDao
 * @see com.lvmama.ord.po.OrdOrderAmountItem
 * @see com.lvmama.order.dao.OrderAmountItemDAO
 */
public final class OrderAmountItemDAOImpl extends BaseIbatisDAO implements
		OrderAmountItemDAO {
	public Long insert(final OrdOrderAmountItem record) {
		Object newKey = super.insert(
				"ORDER_AMOUNT_ITEM.insert", record);
		return (Long) newKey;
	}
	
	public void updateByPrimaryKey(final OrdOrderAmountItem record) {
		 super.update(
				"ORDER_AMOUNT_ITEM.updateByPrimaryKey", record);
	}

	public List<OrdOrderAmountItem> querOrderAmountItemByOrderId(final Long orderId) {
		return (List<OrdOrderAmountItem>)super.queryForList("ORDER_AMOUNT_ITEM.querOrderAmountItemByOrderId", orderId);
	}
	
	
}
