package com.lvmama.pet.mobile.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobilePersistanceLog;

public class MobilePersistanceLogDAO extends BaseIbatisDAO {

    public MobilePersistanceLogDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long mobilePersistanceLogId) {
        MobilePersistanceLog key = new MobilePersistanceLog();
        key.setMobilePersistanceLogId(mobilePersistanceLogId);
        int rows = super.delete("MOBILE_PERSISTANCE_LOG.deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(MobilePersistanceLog record) {
        super.insert("MOBILE_PERSISTANCE_LOG.insert", record);
    }

    public void insertSelective(MobilePersistanceLog record) {
        super.insert("MOBILE_PERSISTANCE_LOG.insertSelective", record);
    }

    public MobilePersistanceLog selectByPrimaryKey(Long mobilePersistanceLogId) {
        MobilePersistanceLog key = new MobilePersistanceLog();
        key.setMobilePersistanceLogId(mobilePersistanceLogId);
        MobilePersistanceLog record = (MobilePersistanceLog) super.queryForObject("MOBILE_PERSISTANCE_LOG.selectByPrimaryKey", key);
        return record;
    }
    
    public List<MobilePersistanceLog> selectByParam(Map<String,Object> params){
    	if(params==null||params.isEmpty()){
    		throw new RuntimeException("params not be empty");
    	}
    	return  super.queryForList("MOBILE_PERSISTANCE_LOG.selectByParam", params);
    }

    public int updateByPrimaryKeySelective(MobilePersistanceLog record) {
        int rows = super.update("MOBILE_PERSISTANCE_LOG.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MobilePersistanceLog record) {
        int rows = super.update("MOBILE_PERSISTANCE_LOG.updateByPrimaryKey", record);
        return rows;
    }
}