package com.lvmama.order.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdPerform;
import com.lvmama.comm.bee.vo.ord.PerformDetail;
import com.lvmama.order.dao.OrderPerformDAO;

public class OrderPerformDAOImpl extends BaseIbatisDAO implements OrderPerformDAO {

	@Override
	public Long insert(OrdPerform ordPerform) {
		Object obj = super.insert("ORDER_PERFORM.insert", ordPerform);
		
		if(obj != null)
			return (Long) obj;
		else
			return null;
	}
	
	@Override
	public List<OrdPerform> selectByObjectIdAndObjectType(Long objectId, String objectType)
	{
		Map params = new HashMap();
		params.put("objectId", objectId);
		params.put("objectType", objectType);
		return super.queryForList("ORDER_PERFORM.selectByObjectIdAndObjectType", params);
	}

	@Override
	public int update(OrdPerform ordPerform) {
		return super.update("ORDER_PERFORM.update", ordPerform);
	}

	@Override
	public boolean isAllPerformed(Long orderId) {
		Long count = (Long)super.queryForObject("ORDER_PERFORM.countUnPerformed", orderId);
		if (count==0) {
			return true;
		}
		return false;
	}
	
	public List<PerformDetail> getOrderPerformDetail(List<Long> orderItemMetaIds){
		return super.queryForList("ORDER_PERFORM.getOrderPerformDetail",orderItemMetaIds);
	}
}
