package com.lvmama.order.dao;

import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdPerform;
import com.lvmama.comm.bee.vo.ord.PerformDetail;

public interface OrderPerformDAO {

	Long insert(OrdPerform ordPerform);
	
	List<OrdPerform> selectByObjectIdAndObjectType(Long objectId, String objectType);
	
	int update(OrdPerform ordPerform);
	
	boolean isAllPerformed(Long orderId);
	
	List<PerformDetail> getOrderPerformDetail(List<Long> orderItemMetaIds);
}
