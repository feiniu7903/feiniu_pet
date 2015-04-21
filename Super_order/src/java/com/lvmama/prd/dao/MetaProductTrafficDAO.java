package com.lvmama.prd.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.meta.MetaProductTraffic;

public class MetaProductTrafficDAO extends BaseIbatisDAO {

    public MetaProductTrafficDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long metaTrafficId) {
        MetaProductTraffic key = new MetaProductTraffic();
        key.setMetaTrafficId(metaTrafficId);
        int rows = super.delete("META_PRODUCT_TRAFFIC.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(MetaProductTraffic record) {
        Object newKey = super.insert("META_PRODUCT_TRAFFIC.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(MetaProductTraffic record) {
        Object newKey = super.insert("META_PRODUCT_TRAFFIC.insertSelective", record);
        return (Long) newKey;
    }

    public MetaProductTraffic selectByPrimaryKey(Long metaTrafficId) {
        MetaProductTraffic key = new MetaProductTraffic();
        key.setMetaTrafficId(metaTrafficId);
        MetaProductTraffic record = (MetaProductTraffic) super.queryForObject("META_PRODUCT_TRAFFIC.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(MetaProductTraffic record) {
        int rows = super.update("META_PRODUCT_TRAFFIC.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MetaProductTraffic record) {
        int rows = super.update("META_PRODUCT_TRAFFIC.updateByPrimaryKey", record);
        return rows;
    }
}