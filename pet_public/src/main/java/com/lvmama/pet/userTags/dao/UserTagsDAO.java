package com.lvmama.pet.userTags.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.usertags.UserTags;

public class UserTagsDAO extends BaseIbatisDAO {
    
    @SuppressWarnings("unchecked")
    public List<UserTags> queryAllUserTagsByParam(Map<String,Object> param){
        return super.queryForList("USER_TAGS.queryAllUserTagsByParam",param);
    }
    
    @SuppressWarnings("unchecked")
	public List<UserTags> queryAllUserTagsByRes(Map<String,Object> param){
    	return super.queryForList("USER_TAGS.queryAllUserTagsByRes",param);
    }
    
    public void saveUserTags(UserTags tags){
        super.insert("USER_TAGS.saveUserTags",tags);
    }
   
    public UserTags queryUserTagsByName(UserTags tags){
        return (UserTags)super.queryForObject("USER_TAGS.queryUserTagsByName",tags);
    }
    
    public Long countByParam(Map<String,Object> param){
        return (Long)super.queryForObject("USER_TAGS.countByParam",param);
    }
    
    public void updateUserTags(UserTags tags){
        super.update("USER_TAGS.updateUserTags",tags);
    }
    
}
