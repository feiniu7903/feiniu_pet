package com.lvmama.order.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdInvoiceRelation;
import com.lvmama.order.dao.OrderInvoiceDAO;

public class OrderInvoiceDAOImpl extends BaseIbatisDAO implements
		OrderInvoiceDAO {

	@Override
	public int deleteByPrimaryKey(Long invoiceId) {
        OrdInvoice key = new OrdInvoice();
        key.setInvoiceId(invoiceId);
        int rows = super.delete("ORDER_INVOICE.deleteByPrimaryKey", key);
        return rows;
    }

	@Override
	public Long insert(OrdInvoice record) {
        Object newKey = super.insert("ORDER_INVOICE.insert", record);
        return (Long) newKey;
    }
	
	

	@Override
	public OrdInvoice selectByPrimaryKey(Long invoiceId) {
        OrdInvoice key = new OrdInvoice();
        key.setInvoiceId(invoiceId);
        OrdInvoice record = (OrdInvoice) super.queryForObject("ORDER_INVOICE.selectByPrimaryKey", key);
        return record;
    }

	@Override
	public int updateByPrimaryKey(OrdInvoice record) {
        int rows = super.update("ORDER_INVOICE.updateByPrimaryKey", record);
        return rows;
    }
	
	@Override
	public int updateByParamMap(Map params) {
        int rows = super.update("ORDER_INVOICE.updateByParamMap", params);
        return rows;
    }
	
	@Override
	public List<OrdInvoice> queryInvoiceByOrderId(Long orderId)
	{
		return super.queryForList("ORDER_INVOICE.queryInvoiceByOrderId", orderId);
	}
	
	@Override
	public List<OrdInvoice> queryInvoiceByStatus(String status)
	{
		return super.queryForList("ORDER_INVOICE.queryInvoiceByStatus", status);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrdInvoiceRelation> queryRelationList(Long invoiceId) {
		return super.queryForList("ORDER_INVOICE_RELATION.selectByInvoiceId",invoiceId);
	}

	@Override
	public long selectNotCancelInvoiceCountByOrderId(Long orderId,String status) {
		Assert.notNull(orderId);
		Assert.hasText(status);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("status", status);
		try{
			return (Long)super.queryForObject("ORDER_INVOICE.selectNotCancelInvoiceCountByOrderId",map);
		}catch(Exception ex){
			return 0;
		}
	}

	@Override
	public boolean updateRedFlag(OrdInvoice record) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("invoiceId", record.getInvoiceId());
		param.put("redFlag", record.getRedFlag());
		param.put("redReqTime", record.getRedReqTime());
		super.update("ORDER_INVOICE.updateByParamMap",param);
		return true;
	}

	@Override
	public long getInvoiceAmountSum(Map<String,Object> param) {
		try{
			return (Long)super.queryForObject("ORDER_INVOICE.getInvoiceAmountSum",param);
		}catch(Exception ex){			
			return 0L;
		}
	}
}
