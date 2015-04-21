package com.lvmama.report.service.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.usertags.UserTagsSearchLogs;
import com.lvmama.comm.pet.service.usertags.UserTagsSearchLogsService;
import com.lvmama.report.dao.UserTagsSearchLogsDAO;

 

public class UserTagsSearchLogsServiceImpl implements UserTagsSearchLogsService{

    private UserTagsSearchLogsDAO userTagsSearchLogsDAO;
    
    @Override
    public List<UserTagsSearchLogs> queryAllUserTagsLogByParam(
            Map<String, Object> param) {
        return userTagsSearchLogsDAO.queryAllUserTagsLogByParam(param);
    }

    @Override
    public void updateUserTagsLog(UserTagsSearchLogs tagsLog) {
        userTagsSearchLogsDAO.updateUserTagsLog(tagsLog);
    }

    @Override
    public Long countUserTagsLogByParam(Map<String, Object> param) {
        return userTagsSearchLogsDAO.countUserTagsLogByParam(param);
    }

    @Override
    public void deleteSearchLogsByDoTagsSave(long searchId) {
        userTagsSearchLogsDAO.deleteSearchLogsByDoTagsSave(searchId);
    }
    
    @Override
    public UserTagsSearchLogs querySearchLogsByLogsId(long searchId) {
        return userTagsSearchLogsDAO.querySearchLogsByLogsId(searchId);
    }

    /***********************getter/setter***********************/
    public UserTagsSearchLogsDAO getUserTagsSearchLogsDAO() {
        return userTagsSearchLogsDAO;
    }

    public void setUserTagsSearchLogsDAO(UserTagsSearchLogsDAO userTagsSearchLogsDAO) {
        this.userTagsSearchLogsDAO = userTagsSearchLogsDAO;
    }
}
