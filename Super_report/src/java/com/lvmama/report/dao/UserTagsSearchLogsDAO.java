package com.lvmama.report.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.usertags.UserTagsSearchLogs;

public class UserTagsSearchLogsDAO extends BaseIbatisDAO {

    @SuppressWarnings("unchecked")
    public List<UserTagsSearchLogs> queryAllUserTagsLogByParam(Map<String,Object> param){
        return super.queryForList("USERTAGS_SEARCHLOGS.queryAllUserTagsLogByParam",param);
    }
    
    public void updateUserTagsLog(UserTagsSearchLogs tagsLog){
        super.update("USERTAGS_SEARCHLOGS.updateUserTagsLog",tagsLog);
    }
    
    public Long countUserTagsLogByParam(Map<String,Object> param){
        return (Long)super.queryForObject("USERTAGS_SEARCHLOGS.countUserTagsLogByParam",param);
    }
    
    public void deleteSearchLogsByDoTagsSave(long searchId){
        super.delete("USERTAGS_SEARCHLOGS.deleteSearchLogsByDoTagsSave",searchId);
    }
    
    public UserTagsSearchLogs querySearchLogsByLogsId(long searchId){
        return (UserTagsSearchLogs)super.queryForObject("USERTAGS_SEARCHLOGS.querySearchLogsByLogsId",searchId);
    }
}
