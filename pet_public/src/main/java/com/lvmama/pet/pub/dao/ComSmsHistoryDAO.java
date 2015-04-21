package com.lvmama.pet.pub.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComSmsHistory;

public class ComSmsHistoryDAO extends BaseIbatisDAO{
	 public ComSmsHistoryDAO() {
	        super();
	    }

	    public int deleteByPrimaryKey(Long smsId) {
	    	ComSmsHistory key = new ComSmsHistory();
	        key.setSmsId(smsId);
	        int rows = super.delete("COM_SMS_HISTORY.deleteByPrimaryKey", key);
	        return rows;
	    }

	    public Long insert(ComSmsHistory record) {
	        Object newKey = super.insert("COM_SMS_HISTORY.insert", record);
	        return (Long) newKey;
	    }

	    public Long insertSelective(ComSmsHistory record) {
	        Object newKey = super.insert("COM_SMS_HISTORY.insertSelective", record);
	        return (Long) newKey;
	    }

	    public ComSmsHistory selectByPrimaryKey(Long smsId) {
	    	ComSmsHistory key = new ComSmsHistory();
	        key.setSmsId(smsId);
	        ComSmsHistory record = (ComSmsHistory) super.queryForObject("COM_SMS_HISTORY.selectByPrimaryKey", key);
	        return record;
	    }

	    public int updateByPrimaryKeySelective(ComSmsHistory record) {
	        int rows = super.update("COM_SMS_HISTORY.updateByPrimaryKeySelective", record);
	        return rows;
	    }

	    public int updateByPrimaryKey(ComSmsHistory record) {
	        int rows = super.update("COM_SMS_HISTORY.updateByPrimaryKey", record);
	        return rows;
	    }
	    public List<ComSmsHistory> getAllSmsHistoryByParam(Map param){
	    	if (param.get("_startRow")==null) {
				param.put("_startRow", 0);
			}
			if (param.get("_endRow")==null) {
				param.put("_endRow", 20);
			}
	    	return super.queryForList("COM_SMS_HISTORY.selelctSmsHistoryByPrama",param);
	    	
	    }
	    
		public Integer selectRowCount(Map searchConds){
			Integer count = 0;
			count = (Integer) super.queryForObject("COM_SMS_HISTORY.selectRowCount",searchConds);
			return count;
		}
		
		public List<ComSmsHistory> selectPassSmsByParam(Map<String,Object> searchConds){
			return super.queryForList("COM_SMS_HISTORY.selectPassSmsByParam",searchConds);
		}
}
