package com.lvmama.pet.pub.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComSms;

public class ComSmsDAO extends BaseIbatisDAO {

    public ComSmsDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long smsId) {
        ComSms key = new ComSms();
        key.setSmsId(smsId);
        int rows = super.delete("COM_SMS.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(ComSms record) {
        Object newKey = super.insert("COM_SMS.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(ComSms record) {
        Object newKey = super.insert("COM_SMS.insertSelective", record);
        return (Long) newKey;
    }

    public ComSms selectByPrimaryKey(Long smsId) {
        ComSms key = new ComSms();
        key.setSmsId(smsId);
        ComSms record = (ComSms) super.queryForObject("COM_SMS.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(ComSms record) {
        int rows = super.update("COM_SMS.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ComSms record) {
        int rows = super.update("COM_SMS.updateByPrimaryKey", record);
        return rows;
    }
    
    public List<ComSms> getWaitSendSms(){
    	return super.queryForList("COM_SMS.selectSmsWaitingToSend");
    	
    }
    
    public List<ComSms> getAllByParam(Map param){
    	if (param.get("_startRow")==null) {
			param.put("_startRow", 0);
		}
		if (param.get("_endRow")==null) {
			param.put("_endRow", 20);
		}
    	return super.queryForList("COM_SMS.selelctByPrama",param);
    }
    
	public Integer selectRowCount(Map searchConds){
		Integer count = 0;
		count = (Integer) super.queryForObject("COM_SMS.selectRowCount",searchConds);
		return count;
	}
}