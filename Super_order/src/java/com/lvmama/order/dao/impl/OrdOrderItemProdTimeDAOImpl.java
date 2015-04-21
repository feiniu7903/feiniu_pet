package com.lvmama.order.dao.impl;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProdTime;
import com.lvmama.order.dao.OrdOrderItemProdTimeDAO;

public class OrdOrderItemProdTimeDAOImpl extends BaseIbatisDAO implements OrdOrderItemProdTimeDAO{

    public OrdOrderItemProdTimeDAOImpl() {
        super();
    }

    public int deleteByPrimaryKey(Long itemProdTimeId) {
        OrdOrderItemProdTime key = new OrdOrderItemProdTime();
        key.setItemProdTimeId(itemProdTimeId);
        int rows = super.delete("ORD_ORDER_ITEM_PROD_TIME.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(OrdOrderItemProdTime record) {
        Object newKey = super.insert("ORD_ORDER_ITEM_PROD_TIME.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(OrdOrderItemProdTime record) {
        Object newKey = super.insert("ORD_ORDER_ITEM_PROD_TIME.insertSelective", record);
        return (Long) newKey;
    }

    public OrdOrderItemProdTime selectByPrimaryKey(Long itemProdTimeId) {
        OrdOrderItemProdTime key = new OrdOrderItemProdTime();
        key.setItemProdTimeId(itemProdTimeId);
        OrdOrderItemProdTime record = (OrdOrderItemProdTime) super.queryForObject("ORD_ORDER_ITEM_PROD_TIME.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(OrdOrderItemProdTime record) {
        int rows = super.update("ORD_ORDER_ITEM_PROD_TIME.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(OrdOrderItemProdTime record) {
        int rows = super.update("ORD_ORDER_ITEM_PROD_TIME.updateByPrimaryKey", record);
        return rows;
    }
    
	 public List<OrdOrderItemProdTime> selectProdTimeByProdItemId(Long itemId){
		 return super.queryForList("ORD_ORDER_ITEM_PROD_TIME.selectProdTimeByProdItemId",itemId);
	 }
}