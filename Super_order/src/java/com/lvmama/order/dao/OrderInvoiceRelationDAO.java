package com.lvmama.order.dao;

import com.lvmama.comm.bee.po.ord.OrdInvoiceRelation;


public interface OrderInvoiceRelationDAO {

	public abstract Long insert(OrdInvoiceRelation relation);
	
	public long selectInvoiceCountByOrderId(final Long orderId);

}