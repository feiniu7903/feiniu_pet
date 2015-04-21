package com.lvmama.pet.mobile.dao;

import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobileMarkActivityLog;

public class MobileMarkActivityLogDAO extends BaseIbatisDAO {

    public MobileMarkActivityLogDAO() {
        super();
    }
    
    public MobileMarkActivityLog queryUniqueMobileMarkActivityLog(Map<String,Object> param) {
        return  (MobileMarkActivityLog) super.queryForObject("MOBILE_MARK_ACTIVITY_LOG.queryUniqueMobileMarkActivityLog",param);
    }
    public Long queryTodayTotalMarkCoupon(Map<String,Object> param) {
        return (Long) super.queryForObject("MOBILE_MARK_ACTIVITY_LOG.queryTodayTotalMarkCoupon", param);
    }
    public Long getTodayTotalUsedTimesByMMAId(Map<String,Object> param) {
        return  (Long) super.queryForObject("MOBILE_MARK_ACTIVITY_LOG.getTodayTotalUsedTimesByMMAId",param);
    }
    
    public Long getUsedTimesByObjectId(Map<String,Object> param) {
        return  (Long) super.queryForObject("MOBILE_MARK_ACTIVITY_LOG.getUsedTimesByObjectId",param);
    }
    
    public Long getUsedTimesByUserId(Map<String,Object> param) {
        return  (Long) super.queryForObject("MOBILE_MARK_ACTIVITY_LOG.getUsedTimesByUserId",param);
    }
    
    public int deleteByPrimaryKey(Long mobileMarkActivityLogId) {
        MobileMarkActivityLog key = new MobileMarkActivityLog();
        key.setMobileMarkActivityLogId(mobileMarkActivityLogId);
        int rows = super.delete("MOBILE_MARK_ACTIVITY_LOG.deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(MobileMarkActivityLog record) {
        super.insert("MOBILE_MARK_ACTIVITY_LOG.insert", record);
    }

    public void insertSelective(MobileMarkActivityLog record) {
        super.insert("MOBILE_MARK_ACTIVITY_LOG.insertSelective", record);
    }

    public MobileMarkActivityLog selectByPrimaryKey(Long mobileMarkActivityLogId) {
        MobileMarkActivityLog key = new MobileMarkActivityLog();
        key.setMobileMarkActivityLogId(mobileMarkActivityLogId);
        MobileMarkActivityLog record = (MobileMarkActivityLog) super.queryForObject("MOBILE_MARK_ACTIVITY_LOG.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(MobileMarkActivityLog record) {
        int rows = super.update("MOBILE_MARK_ACTIVITY_LOG.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MobileMarkActivityLog record) {
        int rows = super.update("MOBILE_MARK_ACTIVITY_LOG.updateByPrimaryKey", record);
        return rows;
    }
    
}