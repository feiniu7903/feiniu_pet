package com.lvmama.pet.userTags.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.usertags.UserTagsType;

public class UserTagsTypeDAO extends BaseIbatisDAO {

    @SuppressWarnings("unchecked")
    public List<UserTagsType> queryTagsTypeByParam(Map<String,Object> param){
        return (List<UserTagsType>)super.queryForList("USERTAGS_TYPE.queryTagsTypeByParam",param);
    }
    
    public void insertUserTagsType(UserTagsType tagType){
        super.insert("USERTAGS_TYPE.insertUserTagsType",tagType);
    }
    
    public void updateUserTagsType(UserTagsType tagType){
        super.update("USERTAGS_TYPE.updateUserTagsType",tagType);
    }
    
    public void deleteUserTagsTypeByParam(Map<String,Object> param){
        super.delete("USERTAGS_TYPE.deleteUserTagsTypeByParam",param);
    }
    
    public Long countByParam(Map<String,Object> param){
        return (Long)super.queryForObject("USERTAGS_TYPE.countByParam",param);
    }
}
