package com.lvmama.order.dao;

import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrderMemo;

/**
 * 订单备注DAO接口.
 *
 * <pre>
 * 封装订单备注CRUD
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.ord.po.OrdOrderMemo
 */
public interface OrderMemoDAO {
	List<OrdOrderMemo> selectByOrderId(Long orderId);

	int deleteByPrimaryKey(Long memoId);

	Long insert(OrdOrderMemo record);

	OrdOrderMemo selectByPrimaryKey(Long memoId);

	int updateByPrimaryKey(OrdOrderMemo record);
}
