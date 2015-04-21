package com.lvmama.prd.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProdTraffic;

public class ProdTrafficDAO extends BaseIbatisDAO {

    public ProdTrafficDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long prodTrafficId) {
        ProdTraffic key = new ProdTraffic();
        key.setProdTrafficId(prodTrafficId);
        int rows = super.delete("PROD_TRAFFIC.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(ProdTraffic record) {
        Object newKey = super.insert("PROD_TRAFFIC.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(ProdTraffic record) {
        Object newKey = super.insert("PROD_TRAFFIC.insertSelective", record);
        return (Long) newKey;
    }

    public ProdTraffic selectByPrimaryKey(Long prodTrafficId) {
        ProdTraffic key = new ProdTraffic();
        key.setProdTrafficId(prodTrafficId);
        ProdTraffic record = (ProdTraffic) super.queryForObject("PROD_TRAFFIC.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(ProdTraffic record) {
        int rows = super.update("PROD_TRAFFIC.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ProdTraffic record) {
        int rows = super.update("PROD_TRAFFIC.updateByPrimaryKey", record);
        return rows;
    }
}