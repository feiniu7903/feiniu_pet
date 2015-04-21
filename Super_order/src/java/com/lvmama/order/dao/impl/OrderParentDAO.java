package com.lvmama.order.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrderParent;

public class OrderParentDAO  extends BaseIbatisDAO{

	public Long insert(OrderParent orderParent) {
		Object obj = super.insert("ORDER_PARENT.insert", orderParent);
		if(obj != null){
			return (Long) obj;
		}
		return null;
	}

	public OrderParent queryLastOrderByPhoneOrUserId(Long userId,String phoneNo){
		Map params = new HashMap();
		params.put("userId", userId);
		params.put("phoneNo", phoneNo);
		return (OrderParent)super.queryForObject("ORDER_PARENT.queryLastOrderByPhoneOrUserId", params);
	}

	public List<OrderParent> query(Map<String, Object> map) {
		return super.queryForList("ORDER_PARENT.query", map);
	}

	public OrderParent find(Long parentId) {
		return (OrderParent)super.queryForObject("ORDER_PARENT.find", parentId);
	}
}
