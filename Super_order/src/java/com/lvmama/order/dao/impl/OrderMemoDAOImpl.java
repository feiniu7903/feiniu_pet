package com.lvmama.order.dao.impl;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderMemo;
import com.lvmama.order.dao.OrderMemoDAO;

/**
 * 订单备注DAO实现类.
 *
 * <pre>
 * 封装订单备注CRUD
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.BaseIbatisDao
 * @see com.lvmama.ord.po.OrdOrderMemo
 * @see com.lvmama.order.dao.OrderMemoDAO
 */
public final class OrderMemoDAOImpl extends BaseIbatisDAO implements
		OrderMemoDAO {
	public List<OrdOrderMemo> selectByOrderId(final Long orderId) {
		return (List<OrdOrderMemo>) super.queryForList(
				"ORDER_MEMO.select", orderId);
	}

	public int deleteByPrimaryKey(final Long memoId) {
		int rows = super.delete(
				"ORDER_MEMO.deleteByPrimaryKey", memoId);
		return rows;
	}

	public Long insert(final OrdOrderMemo record) {
		Object newKey = super.insert("ORDER_MEMO.insert",
				record);
		return (Long) newKey;
	}

	public OrdOrderMemo selectByPrimaryKey(final Long memoId) {
		OrdOrderMemo key = new OrdOrderMemo();
		key.setMemoId(memoId);
		OrdOrderMemo record = (OrdOrderMemo) super
				.queryForObject("ORDER_MEMO.selectByPrimaryKey", key);
		return record;
	}

	public int updateByPrimaryKey(final OrdOrderMemo record) {
		int rows = super.update(
				"ORDER_MEMO.updateByPrimaryKey", record);
		return rows;
	}
}
