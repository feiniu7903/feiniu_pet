package com.lvmama.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrderMemo;
import com.lvmama.order.dao.OrderMemoDAO;
import com.lvmama.order.service.OrderMemoService;

/**
 * 订单备注服务实现类.
 *
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.ord.po.OrdOrderMemo
 * @see com.lvmama.order.dao.OrderMemoDAO
 * @see com.lvmama.order.service.OrderMemoService
 */
public class OrderMemoServiceImpl implements OrderMemoService {

	private OrderMemoDAO orderMemoDAO;

	public void setOrderMemoDAO(OrderMemoDAO orderMemoDAO) {
		this.orderMemoDAO = orderMemoDAO;
	}

	public List<OrdOrderMemo> saveMemoList(List<OrdOrderMemo> memoList,
			String operatorId) {
		if (memoList == null || memoList.size() == 0)
			return new ArrayList<OrdOrderMemo>();

		for (OrdOrderMemo memo : memoList) {
			Long memoId = orderMemoDAO.insert(memo);
			memo.setMemoId(memoId);
		}

		return memoList;
	}

	public OrdOrderMemo saveMemo(OrdOrderMemo memo, String operatorId) {
		Long memoId = orderMemoDAO.insert(memo);
		memo.setMemoId(memoId);
		return memo;
	}

	public boolean deleteMemo(Long memoId, String operatorId) {
		int row = orderMemoDAO.deleteByPrimaryKey(memoId);
		return (row == 1 ? true : false);
	}
	
	public OrdOrderMemo selectMemo(Long memoId)
	{
		return orderMemoDAO.selectByPrimaryKey(memoId);
	}

	public List<OrdOrderMemo> queryMemoByOrderId(Long orderId) {
		return orderMemoDAO.selectByOrderId(orderId);
	}

	public boolean updateMemo(OrdOrderMemo memo, String operatorId) {
		int row = orderMemoDAO.updateByPrimaryKey(memo);
		
		if(row == 1)
			return true;
		else
			return false;
	}
}
