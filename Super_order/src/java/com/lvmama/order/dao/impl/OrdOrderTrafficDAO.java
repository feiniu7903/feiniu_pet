package com.lvmama.order.dao.impl;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderTraffic;

public class OrdOrderTrafficDAO extends BaseIbatisDAO {

    public OrdOrderTrafficDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long orderTrafficId) {
        OrdOrderTraffic key = new OrdOrderTraffic();
        key.setOrderTrafficId(orderTrafficId);
        int rows = super.delete("ORD_ORDER_TRAFFIC.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(OrdOrderTraffic record) {
        Object newKey = super.insert("ORD_ORDER_TRAFFIC.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(OrdOrderTraffic record) {
        Object newKey = super.insert("ORD_ORDER_TRAFFIC.insertSelective", record);
        return (Long) newKey;
    }

    public OrdOrderTraffic selectByPrimaryKey(Long orderTrafficId) {
        OrdOrderTraffic key = new OrdOrderTraffic();
        key.setOrderTrafficId(orderTrafficId);
        OrdOrderTraffic record = (OrdOrderTraffic) super.queryForObject("ORD_ORDER_TRAFFIC.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(OrdOrderTraffic record) {
        int rows = super.update("ORD_ORDER_TRAFFIC.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(OrdOrderTraffic record) {
        int rows = super.update("ORD_ORDER_TRAFFIC.updateByPrimaryKey", record);
        return rows;
    }
    
    public OrdOrderTraffic selectByPrimaryOrderItemMetaId(final Long orderItemMetaId){
    	OrdOrderTraffic record = new OrdOrderTraffic();
    	record.setOrderItemMetaId(orderItemMetaId);
    	List<OrdOrderTraffic> list = super.queryForList("ORD_ORDER_TRAFFIC.selectByPrimaryOrderItemMetaId",record);
    	if(!list.isEmpty()){
    		return list.get(0);
    	}
    	return null;
    }
    
    public OrdOrderTraffic selectByPrimarySupplierOrderId(final String supplierOrderId){
    	OrdOrderTraffic record = new OrdOrderTraffic();
    	record.setSupplierOrderId(supplierOrderId);
    	List<OrdOrderTraffic> list = super.queryForList("ORD_ORDER_TRAFFIC.selectByPrimarySupplierOrderId",record);
    	if(!list.isEmpty()){
    		return list.get(0);
    	}
    	return null;
    }
}