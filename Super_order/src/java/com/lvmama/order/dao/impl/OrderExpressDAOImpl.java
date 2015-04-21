package com.lvmama.order.dao.impl;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdExpress;
import com.lvmama.order.dao.OrderExpressDAO;

public class OrderExpressDAOImpl extends BaseIbatisDAO implements
		OrderExpressDAO {

	@Override
	public int deleteByPrimaryKey(Long expressId) {
		OrdExpress key = new OrdExpress();
        key.setExpressId(expressId);
        int rows = super.delete("ORDER_EXPRESS.deleteByPrimaryKey", key);
        return rows;
	}

	@Override
	public Long insert(OrdExpress record) {
		Object newKey = super.insert("ORDER_EXPRESS.insert", record);
        return (Long) newKey;
	}

	@Override
	public OrdExpress selectByPrimaryKey(Long expressId) {
		OrdExpress key = new OrdExpress();
        key.setExpressId(expressId);
        OrdExpress record = (OrdExpress) super.queryForObject("ORDER_EXPRESS.selectByPrimaryKey", key);
        return record;
	}

	@Override
	public int updateByPrimaryKey(OrdExpress record) {
		int rows = super.update("ORDER_EXPRESS.updateByPrimaryKey", record);
        return rows;
	}

}
