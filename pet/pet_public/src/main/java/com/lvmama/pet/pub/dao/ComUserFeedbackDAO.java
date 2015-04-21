package com.lvmama.pet.pub.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComUserFeedback;


public class ComUserFeedbackDAO extends BaseIbatisDAO {

    public ComUserFeedbackDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long userFeedbackId) {
        ComUserFeedback key = new ComUserFeedback();
        key.setUserFeedbackId(userFeedbackId);
        int rows = super.delete("COM_USER_FEEDBACK.deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(ComUserFeedback record) {
        super.insert("COM_USER_FEEDBACK.insert", record);
    }

    public void insertSelective(ComUserFeedback record) {
        super.insert("COM_USER_FEEDBACK.insertSelective", record);
    }

    public ComUserFeedback selectByPrimaryKey(Long userFeedbackId) {
        ComUserFeedback key = new ComUserFeedback();
        key.setUserFeedbackId(userFeedbackId);
        ComUserFeedback record = (ComUserFeedback) super.queryForObject("COM_USER_FEEDBACK.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(ComUserFeedback record) {
        int rows = super.update("COM_USER_FEEDBACK.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ComUserFeedback record) {
        int rows = super.update("COM_USER_FEEDBACK.updateByPrimaryKey", record);
        return rows;
    }
    
    public List<ComUserFeedback> queryFeedBackByParams(ComUserFeedback feedBack, int beignIndex, int endIndex) {
    	Map params = new HashMap<String, String>();
    	params.put("type", feedBack.getType());
    	params.put("content", feedBack.getContent());
    	params.put("stateId", feedBack.getStateId());
    	params.put("beignIndex", beignIndex);
    	params.put("endIndex", endIndex);
    	return super.queryForList("COM_USER_FEEDBACK.queryFeedBackByParams", params);
    }
    
    public Long queryFeedBackCountByParams(ComUserFeedback feedBack) {
    	return (Long) super.queryForObject("COM_USER_FEEDBACK.queryFeedBackCountByParams", feedBack);
    }
    
    public List<String> getFeedBackTypes() {
    	return super.queryForList("COM_USER_FEEDBACK.getFeedBackTypes");
    }
    
    public List<String> getFeedBackStateIds() {
    	return super.queryForList("COM_USER_FEEDBACK.getFeedBackStateIds");
    }
}