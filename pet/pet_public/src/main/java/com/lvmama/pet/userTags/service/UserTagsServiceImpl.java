package com.lvmama.pet.userTags.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.usertags.UserTags;
import com.lvmama.comm.pet.service.usertags.UserTagsService;
import com.lvmama.pet.userTags.dao.UserTagsDAO;

public class UserTagsServiceImpl implements UserTagsService {

    private UserTagsDAO userTagsDAO;
    
    @Override
    public List<UserTags> queryAllUserTagsByParam(Map<String, Object> param) {
        return userTagsDAO.queryAllUserTagsByParam(param);
    }

    @Override
    public void saveUserTags(UserTags tags) {
        userTagsDAO.saveUserTags(tags);
    }

    @Override
    public UserTags queryUserTagsByName(UserTags tags) {
        return userTagsDAO.queryUserTagsByName(tags);
    }
    
    @Override
    public Long countByParam(Map<String, Object> param) {
       return userTagsDAO.countByParam(param);
    }
    
    @Override
    public void updateUserTags(UserTags tags) {
       userTagsDAO.updateUserTags(tags);
    }
    
    /*********************************getter/setter***********************************/
    public UserTagsDAO getUserTagsDAO() {
        return userTagsDAO;
    }

    public void setUserTagsDAO(UserTagsDAO userTagsDAO) {
        this.userTagsDAO = userTagsDAO;
    }

	@Override
	public List<UserTags> queryAllUserTagsByRes(Map<String, Object> param) {
		return this.userTagsDAO.queryAllUserTagsByRes(param);
	}

}
