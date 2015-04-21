package com.lvmama.pet.userTags.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.usertags.UserTagsType;
import com.lvmama.comm.pet.service.usertags.UserTagsTypeService;
import com.lvmama.pet.userTags.dao.UserTagsTypeDAO;

public class UserTagsTypeServiceImpl implements UserTagsTypeService{

    private UserTagsTypeDAO userTagsTypeDAO;
    
    @Override
    public List<UserTagsType> queryTagsTypeByParam(Map<String, Object> param) {
       return userTagsTypeDAO.queryTagsTypeByParam(param);
    }

    @Override
    public void insertUserTagsType(UserTagsType tagType) {
        userTagsTypeDAO.insertUserTagsType(tagType);
    }

    @Override
    public void updateUserTagsType(UserTagsType tagType) {
        userTagsTypeDAO.updateUserTagsType(tagType);
    }

    @Override
    public void deleteUserTagsTypeByParam(Map<String, Object> param) {
        userTagsTypeDAO.deleteUserTagsTypeByParam(param);
    }

    @Override
    public Long countUserTagsTypeByParam(Map<String, Object> param) {
        return userTagsTypeDAO.countByParam(param);
    }
    
    /***--------------------------getter/setter-------------------------***/
    public UserTagsTypeDAO getUserTagsTypeDAO() {
        return userTagsTypeDAO;
    }

    public void setUserTagsTypeDAO(UserTagsTypeDAO userTagsTypeDAO) {
        this.userTagsTypeDAO = userTagsTypeDAO;
    }

    
}
