/**
 * 
 */
package com.lvmama.order.dao.impl;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdInvoiceRelation;
import com.lvmama.order.dao.OrderInvoiceRelationDAO;

/**
 * @author yangbin
 *
 */
public class OrderInvoiceRelationDAOImpl extends BaseIbatisDAO implements OrderInvoiceRelationDAO{

	/* (non-Javadoc)
	 * @see com.lvmama.order.dao.impl.OrdInvoiceRelationDAO#insert(com.lvmama.ord.po.OrdInvoiceRelation)
	 */
	public Long insert(OrdInvoiceRelation relation){
		 Object newKey = super.insert("ORDER_INVOICE_RELATION.insert", relation);
	        return (Long) newKey;
	}

	@Override
	public long selectInvoiceCountByOrderId(Long orderId) {
		OrdInvoiceRelation record=new OrdInvoiceRelation();
		record.setOrderId(orderId);
		try{
		   return (Long)super.queryForObject("ORDER_INVOICE_RELATION.selectInvoiceCountByOrderId",record);
		} catch(Exception ex){			
			return 0L;
		}
	}
}
