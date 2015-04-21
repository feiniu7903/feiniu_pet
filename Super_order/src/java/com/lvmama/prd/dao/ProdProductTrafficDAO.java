package com.lvmama.prd.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProdProductTraffic;

public class ProdProductTrafficDAO extends BaseIbatisDAO {

    public ProdProductTrafficDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long prodTrafficId) {
        ProdProductTraffic key = new ProdProductTraffic();
        key.setProdTrafficId(prodTrafficId);
        int rows = super.delete("PROD_PRODUCT_TRAFFIC.deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(ProdProductTraffic record) {
        super.insert("PROD_PRODUCT_TRAFFIC.insert", record);
    }

    public void insertSelective(ProdProductTraffic record) {
        super.insert("PROD_PRODUCT_TRAFFIC.insertSelective", record);
    }

    public ProdProductTraffic selectByPrimaryKey(Long prodTrafficId) {
        ProdProductTraffic key = new ProdProductTraffic();
        key.setProdTrafficId(prodTrafficId);
        ProdProductTraffic record = (ProdProductTraffic) super.queryForObject("PROD_PRODUCT_TRAFFIC.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(ProdProductTraffic record) {
        int rows = super.update("PROD_PRODUCT_TRAFFIC.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ProdProductTraffic record) {
        int rows = super.update("PROD_PRODUCT_TRAFFIC.updateByPrimaryKey", record);
        return rows;
    }
}