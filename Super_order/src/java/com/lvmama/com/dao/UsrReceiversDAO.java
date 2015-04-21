package com.lvmama.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.vo.UsrReceivers;

public class UsrReceiversDAO extends BaseIbatisDAO {

	private static final Log log = LogFactory.getLog(UsrReceiversDAO.class);
    public UsrReceiversDAO() {
        super();
    }

    public String insert(UsrReceivers record) {
        Object newKey = super.insert("USR_RECEIVERS.insert", record);
        return (String) newKey; 
    }

    public String insertSelective(UsrReceivers record) {
        Object newKey = super.insert("USR_RECEIVERS.insertSelective", record);
        return (String) newKey;
    }
    public UsrReceivers getRecieverByPk(String receiverId){
    	return (UsrReceivers) super.queryForObject("USR_RECEIVERS.selectByPk", receiverId);
    }
    public void update(UsrReceivers record){
    	super.update("USR_RECEIVERS.updateReceivers",record);
    }
    /**
     * 修改地址
     * @param record
     */
    public void updatepostCode(UsrReceivers record){
    	super.update("USR_RECEIVERS.updateReceiversAddress",record);
    }
    
    public List<UsrReceivers> loadRecieverByParams(Map params){
    	if(params.get("userId") == null || "".equals(params.get("userId"))) {
    		log.error("在查询联系人地址（USR_RECEIVERS）时，用户ID为空");
    	}
    	return super.queryForList("USR_RECEIVERS.selectReceiversByParams", params);
    }
    
    public void deleteReciverById(String receiverId) {
    	super.update("USR_RECEIVERS.deleteReceivers",receiverId);
    }
    
    public Long loadReceiversByPageConfigCount(String userId){
    	Map<String,Object> param = new HashMap<String,Object>();
      	param.put("userId", userId);
    	return (Long) super.queryForObject("USR_RECEIVERS.countSelectReceiversByPageConfig", param);
    }
    
    public List<UsrReceivers> loadReceiversByPageConfig(Map<String,Object> params){

	   	return super.queryForList("USR_RECEIVERS.selectReceiversByPageConfig",params);

    }
}