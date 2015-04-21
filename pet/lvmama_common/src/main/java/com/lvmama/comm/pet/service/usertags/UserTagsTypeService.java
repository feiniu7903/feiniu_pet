package com.lvmama.comm.pet.service.usertags;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.usertags.UserTagsType;

public interface UserTagsTypeService {
    
    public List<UserTagsType> queryTagsTypeByParam(Map<String,Object> param);
    
    public void insertUserTagsType(UserTagsType tagType);
    
    public void updateUserTagsType(UserTagsType tagType);
    
    public void deleteUserTagsTypeByParam(Map<String,Object> param);
    
    public Long countUserTagsTypeByParam(Map<String,Object> param);
}
