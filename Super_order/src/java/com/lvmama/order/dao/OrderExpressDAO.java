package com.lvmama.order.dao;

import com.lvmama.comm.bee.po.ord.OrdExpress;


public interface OrderExpressDAO {

	public int deleteByPrimaryKey(Long expressId);

    public Long insert(OrdExpress record);

    public OrdExpress selectByPrimaryKey(Long expressId);

    public int updateByPrimaryKey(OrdExpress record);
}
