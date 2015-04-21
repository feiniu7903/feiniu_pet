package com.lvmama.prd.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.LimitSaleTime;

public class LimitSaleTimeDAO extends BaseIbatisDAO {

    public LimitSaleTimeDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long limitSaleTimeId) {
        LimitSaleTime key = new LimitSaleTime();
        key.setLimitSaleTimeId(limitSaleTimeId);
        int rows = super.delete("LIMIT_SALE_TIME.deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(LimitSaleTime record) {
        super.insert("LIMIT_SALE_TIME.insert", record);
    }

    public void insertSelective(LimitSaleTime record) {
        super.insert("LIMIT_SALE_TIME.insertSelective", record);
    }

    public LimitSaleTime selectByPrimaryKey(Long limitSaleTimeId) {
        LimitSaleTime key = new LimitSaleTime();
        key.setLimitSaleTimeId(limitSaleTimeId);
        LimitSaleTime record = (LimitSaleTime) super.queryForObject("LIMIT_SALE_TIME.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(LimitSaleTime record) {
        int rows = super.update("LIMIT_SALE_TIME.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(LimitSaleTime record) {
        int rows = super.update("LIMIT_SALE_TIME.updateByPrimaryKey", record);
        return rows;
    }
    
    public List<LimitSaleTime> queryLimitSaleTimeByproductId(Long productId){
    	LimitSaleTime record = new LimitSaleTime();
    	record.setProductId(productId);
    	List<LimitSaleTime> list = super.queryForList("LIMIT_SALE_TIME.queryLimitSaleTimeByproductId", record);
    	return list;
    }
    
    public List<LimitSaleTime> queryByProductIdAndLimitTime(LimitSaleTime record){
    	List<LimitSaleTime> list = super.queryForList("LIMIT_SALE_TIME.queryByproductIdAndLimitTime", record);
    	return list;
    }
}