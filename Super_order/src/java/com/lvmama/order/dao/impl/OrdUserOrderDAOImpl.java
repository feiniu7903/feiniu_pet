package com.lvmama.order.dao.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdUserOrder;
import com.lvmama.order.dao.OrdUserOrderDAO;

public class OrdUserOrderDAOImpl extends BaseIbatisDAO implements
		OrdUserOrderDAO {

	@Override
	public int deleteByPrimaryKey(Long userOrderId) {
		return super.delete("ORD_USER_ORDER.deleteByPrimaryKey", userOrderId);
	}

	@Override
	public int insert(OrdUserOrder record) {
		Object obj = super.insert("ORD_USER_ORDER.insert", record);
		return ((Long)obj).intValue();
	}

	@Override
	public int insertSelective(OrdUserOrder record) {
		Object obj = super.insert("ORD_USER_ORDER.insertSelective", record);
		return ((Long)obj).intValue();
	}

	@Override
	public OrdUserOrder selectByPrimaryKey(Long userOrderId) {
		return (OrdUserOrder) super.queryForObject("ORD_USER_ORDER.selectByPrimaryKey", userOrderId);
	}

	@Override
	public int updateByPrimaryKeySelective(OrdUserOrder record) {
		return super.update("ORD_USER_ORDER.updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(OrdUserOrder record) {
		return super.update("ORD_USER_ORDER.updateByPrimaryKey", record);
	}
	
	@Override
	public List<OrdUserOrder> selectListByParams(Map<String, Object> params) {
		List<OrdUserOrder> orderList = null;
		if (params != null) {
			orderList = super.queryForList("ORD_USER_ORDER.selectListByParams", params);
		}
		
		return orderList;
	}

	@Override
	public Long queryTotalCount(Map<String, Object> params) {
		Long count = 0L;
		if (params != null) {
			count = (Long) super.queryForObject("ORD_USER_ORDER.queryTotalCount", params);
		}
		return count;
	}

}
