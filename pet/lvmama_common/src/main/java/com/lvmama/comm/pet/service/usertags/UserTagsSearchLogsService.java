package com.lvmama.comm.pet.service.usertags;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.usertags.UserTagsSearchLogs;

public interface UserTagsSearchLogsService {
    /**根据查询条件查出日志表信息*/
    public List<UserTagsSearchLogs> queryAllUserTagsLogByParam(Map<String,Object> param);
    /**根据日志对象修改日志信息*/
    public void updateUserTagsLog(UserTagsSearchLogs tagsLog);
    /**根据查询条件查出日志条数*/
    public Long countUserTagsLogByParam(Map<String,Object> param);
    /**根据日志ID删除日志信息*/
    public void deleteSearchLogsByDoTagsSave(long searchId);
    /**根据日志ID查询日志对象*/
    public UserTagsSearchLogs querySearchLogsByLogsId(long searchId);
}
