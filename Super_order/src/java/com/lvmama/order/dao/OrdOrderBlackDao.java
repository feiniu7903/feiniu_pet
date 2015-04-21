package com.lvmama.order.dao;

import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderBlack;

public class OrdOrderBlackDao extends BaseIbatisDAO{
	
	public void insertOrdOrderBlack(OrdOrderBlack ordOrderBlack) {
		super.insert("ORD_ORDER_BLACK.insert", ordOrderBlack);
	}

	public int queryOrderBlackByParam(Map<String, Object> param) {
		Integer count = 0; 
		count = (Integer) super.queryForObject("ORD_ORDER_BLACK.queryOrderBlackByParam", param);
		return count;
	}
	
}
