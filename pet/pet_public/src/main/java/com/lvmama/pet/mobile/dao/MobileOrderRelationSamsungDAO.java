package com.lvmama.pet.mobile.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobileOrderRelationSamsung;

public class MobileOrderRelationSamsungDAO extends BaseIbatisDAO {

    public MobileOrderRelationSamsungDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long id) {
        MobileOrderRelationSamsung key = new MobileOrderRelationSamsung();
        key.setId(id);
        int rows = super.delete("MOBILE_ORDER_RELATION_SAMSUNG.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(MobileOrderRelationSamsung record) {
        Object newKey = super.insert("MOBILE_ORDER_RELATION_SAMSUNG.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(MobileOrderRelationSamsung record) {
        Object newKey = super.insert("MOBILE_ORDER_RELATION_SAMSUNG.insertSelective", record);
        return (Long) newKey;
    }

    public MobileOrderRelationSamsung selectByPrimaryKey(Long id) {
        MobileOrderRelationSamsung key = new MobileOrderRelationSamsung();
        key.setId(id);
        MobileOrderRelationSamsung record = (MobileOrderRelationSamsung) super.queryForObject("MOBILE_ORDER_RELATION_SAMSUNG.selectByPrimaryKey", key);
        return record;
    }
    
    public MobileOrderRelationSamsung selectByOrderId(Long orderId) {
        MobileOrderRelationSamsung key = new MobileOrderRelationSamsung();
        key.setOrderid(orderId);
        MobileOrderRelationSamsung record = (MobileOrderRelationSamsung) super.queryForObject("MOBILE_ORDER_RELATION_SAMSUNG.selectByOrderId", key);
        return record;
    }

    public int updateByPrimaryKeySelective(MobileOrderRelationSamsung record) {
        int rows = super.update("MOBILE_ORDER_RELATION_SAMSUNG.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MobileOrderRelationSamsung record) {
        int rows = super.update("MOBILE_ORDER_RELATION_SAMSUNG.updateByPrimaryKey", record);
        return rows;
    }
}