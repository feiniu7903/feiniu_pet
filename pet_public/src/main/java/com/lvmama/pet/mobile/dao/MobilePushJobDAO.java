package com.lvmama.pet.mobile.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobilePushJob;

public class MobilePushJobDAO extends BaseIbatisDAO {

    public MobilePushJobDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long mobilePushJob) {
        MobilePushJob key = new MobilePushJob();
        key.setMobilePushJobId(mobilePushJob);
        int rows = super.delete("MOBILE_PUSH_JOB.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(MobilePushJob record) {
        return (Long) super.insert("MOBILE_PUSH_JOB.insert", record);
    }

    public Long insertSelective(MobilePushJob record) {
    	return (Long) super.insert("MOBILE_PUSH_JOB.insertSelective", record);
    }

    public MobilePushJob selectByPrimaryKey(Long mobilePushJob) {
        MobilePushJob key = new MobilePushJob();
        key.setMobilePushJobId(mobilePushJob);
        MobilePushJob record = (MobilePushJob) super.queryForObject("MOBILE_PUSH_JOB.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(MobilePushJob record) {
        int rows = super.update("MOBILE_PUSH_JOB.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MobilePushJob record) {
        int rows = super.update("MOBILE_PUSH_JOB.updateByPrimaryKey", record);
        return rows;
    }
    
    /**
     * 查询有效的 推送任务
     * @return
     */
    public List<MobilePushJob> queryValidJobs(){
    	return (List<MobilePushJob> )super.queryForList("MOBILE_PUSH_JOB.queryValidJobs");
    }
    
    public List<MobilePushJob> queryJobsByParams(Map<String,Object> param){
    	return (List<MobilePushJob> )super.queryForList("MOBILE_PUSH_JOB.queryPushJobs",param);
    }
    
    public Long countQueryJobsByParams(Map<String,Object> param){
    	return (Long)super.queryForObject("MOBILE_PUSH_JOB.countMobilePushJobList",param);
    }
}