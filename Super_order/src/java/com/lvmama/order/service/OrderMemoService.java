package com.lvmama.order.service;

import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrderMemo;

/**
 * 订单备注服务.
 *
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.ord.po.OrdOrderMemo
 */
public interface OrderMemoService {
	List<OrdOrderMemo> saveMemoList(List<OrdOrderMemo> memoList,
			String operatorId);

	OrdOrderMemo saveMemo(OrdOrderMemo memo, String operatorId);

	boolean deleteMemo(Long memoId, String operatorId);
	
	OrdOrderMemo selectMemo(Long memoId);

	List<OrdOrderMemo> queryMemoByOrderId(Long orderId);
	
	boolean updateMemo(OrdOrderMemo memo, String operatorId);
}
