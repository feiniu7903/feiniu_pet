package com.lvmama.pet.mobile.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobilePushSendTask;
import com.lvmama.comm.vo.Constant;

public class MobilePushSendTaskDAO extends BaseIbatisDAO {

    public MobilePushSendTaskDAO() {
        super();
    }

    public long selectTotalMobilePushSendTask(){
    	return (Long) super.queryForObject(
				"MOBILE_PUSH_SEND_TASK.selectTotalMobilePushSendTask");
    }
    
    public int deleteByPrimaryKey(Long mobilePushSendTaskId) {
        MobilePushSendTask key = new MobilePushSendTask();
        key.setMobilePushSendTaskId(mobilePushSendTaskId);
        int rows = super.delete("MOBILE_PUSH_SEND_TASK.deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(MobilePushSendTask record) {
        super.insert("MOBILE_PUSH_SEND_TASK.insert", record);
    }

    public void insertSelective(MobilePushSendTask record) {
        super.insert("MOBILE_PUSH_SEND_TASK.insertSelective", record);
    }

    public MobilePushSendTask selectByPrimaryKey(Long mobilePushSendTaskId) {
        MobilePushSendTask key = new MobilePushSendTask();
        key.setMobilePushSendTaskId(mobilePushSendTaskId);
        MobilePushSendTask record = (MobilePushSendTask) super.queryForObject("MOBILE_PUSH_SEND_TASK.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(MobilePushSendTask record) {
        int rows = super.update("MOBILE_PUSH_SEND_TASK.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MobilePushSendTask record) {
        int rows = super.update("MOBILE_PUSH_SEND_TASK.updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
	public List<MobilePushSendTask> pagedQueryMobilePushSendTask(
			Map<String, Object> param) {
    	param.put("pushSuffix", Constant.getInstance().getValue("PUSH_SUFFIX"));
		List<MobilePushSendTask> mobilePushSendTaskList = (List<MobilePushSendTask>) super
				.queryForList("MOBILE_PUSH_SEND_TASK.pagedQueryMobilePushSendTask",
						param);
		return mobilePushSendTaskList;
	}
    
    public void syncJobToSendTaskByTaskId(Long jobTaskId) {
    	Map<String,Object> param = new HashMap<String,Object>();
    	param.put("pushSuffix", Constant.getInstance().getValue("PUSH_SUFFIX"));
    	param.put("taskId", jobTaskId);
        super.update("MOBILE_PUSH_SEND_TASK.syncJobToSendTaskByTaskId",param);
    }

}