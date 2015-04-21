package com.lvmama.prd.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.meta.MetaTravelCode;

public class MetaTravelCodeDAO extends BaseIbatisDAO {

    public MetaTravelCodeDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long metaTravelCodeId) {
        MetaTravelCode key = new MetaTravelCode();
        key.setMetaTravelCodeId(metaTravelCodeId);
        int rows = super.delete("META_TRAVEL_CODE.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(MetaTravelCode record) {
        Object newKey = super.insert("META_TRAVEL_CODE.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(MetaTravelCode record) {
        Object newKey = super.insert("META_TRAVEL_CODE.insertSelective", record);
        return (Long) newKey;
    }
    
    public List<MetaTravelCode> selectByCondition(Map<String,Object> params){
    	return (List<MetaTravelCode>)super.queryForList("META_TRAVEL_CODE.selectByCondition", params);
    }

    public MetaTravelCode selectByPrimaryKey(Long metaTravelCodeId) {
        MetaTravelCode key = new MetaTravelCode();
        key.setMetaTravelCodeId(metaTravelCodeId);
        MetaTravelCode record = (MetaTravelCode) super.queryForObject("META_TRAVEL_CODE.selectByPrimaryKey", key);
        return record;
    }

    public MetaTravelCode selectBySuppAndDate(String supplierProductId,Date specDate) {
        MetaTravelCode key = new MetaTravelCode();
        key.setSupplierProductId(supplierProductId);
        key.setSpecDate(specDate);
        MetaTravelCode record = (MetaTravelCode) super.queryForObject("META_TRAVEL_CODE.selectBySuppAndDate", key);
        return record;
    }
    public List<MetaTravelCode> selectBySuppAndDateAndChannelAndBranch(String supplierProductId,Date specDate,String channel,String branch){
    	MetaTravelCode key = new MetaTravelCode();
        key.setSupplierProductId(supplierProductId);
        key.setSpecDate(specDate);
        key.setSupplierChannel(channel);
        key.setProductBranch(branch);
    	return super.queryForList("META_TRAVEL_CODE.selectBySuppAndDateAndChannelAndBranch", key);
    	
    }
    public int updateByPrimaryKeySelective(MetaTravelCode record) {
        int rows = super.update("META_TRAVEL_CODE.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MetaTravelCode record) {
        int rows = super.update("META_TRAVEL_CODE.updateByPrimaryKey", record);
        return rows;
    }
}