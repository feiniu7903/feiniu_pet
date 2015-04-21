package com.lvmama.comm.bee.service.ord;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrderParent;


public interface OrderParentService {

	/**
	 * 保存批次信息
	 * @param orderParent
	 * @return
	 */
	Long insert(OrderParent orderParent);
	
	OrderParent queryLastOrderByPhoneOrUserId(Long userId,String phoneNum);
	OrderParent find(Long parentId);
	List<OrderParent> query(Map<String,Object> map);
	
}
