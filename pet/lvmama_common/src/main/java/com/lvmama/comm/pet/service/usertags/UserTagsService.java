package com.lvmama.comm.pet.service.usertags;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.usertags.UserTags;

public interface UserTagsService {

    public List<UserTags> queryAllUserTagsByParam(Map<String,Object> param);
    
    public void saveUserTags(UserTags tags);
    
    public UserTags queryUserTagsByName(UserTags tags);
    
    public List<UserTags> queryAllUserTagsByRes(Map<String,Object> param);
    
    public Long countByParam(Map<String,Object> param);
 
    public void updateUserTags(UserTags tags);
}
