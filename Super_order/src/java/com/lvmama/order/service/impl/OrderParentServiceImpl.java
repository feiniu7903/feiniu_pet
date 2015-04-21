package com.lvmama.order.service.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrderParent;
import com.lvmama.comm.bee.service.ord.OrderParentService;
import com.lvmama.order.dao.impl.OrderParentDAO;

public class OrderParentServiceImpl implements OrderParentService {

	private OrderParentDAO orderParentDAO; 
	
	
	public void setOrderParentDAO(OrderParentDAO orderParentDAO) {
		this.orderParentDAO = orderParentDAO;
	}

	@Override
	public Long insert(OrderParent orderParent) {
		// TODO Auto-generated method stub
		return orderParentDAO.insert(orderParent);
	}

	@Override
	public OrderParent queryLastOrderByPhoneOrUserId(Long userId,
			String phoneNum) {
		return orderParentDAO.queryLastOrderByPhoneOrUserId(userId, phoneNum);
	}

	
	@Override
	public List<OrderParent> query(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderParent find(Long parentId) {
		// TODO Auto-generated method stub
		return orderParentDAO.find(parentId);
	}
	
	

}
