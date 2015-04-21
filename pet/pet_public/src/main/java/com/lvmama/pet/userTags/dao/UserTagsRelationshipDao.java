package com.lvmama.pet.userTags.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.usertags.UserTagsRelationship;

public class UserTagsRelationshipDao extends BaseIbatisDAO{
	public void saveUserTagsRes(Map<String, Object> param){
        super.insert("USERTAGS_RELATIONSHIP.saveUserTagsRes",param);
    }
	
	public void updateUserTagsRes(Map<String, Object> param) {
		super.update("USER_TAGS.updateUserTagsDealStatus",param);
	}
	
	public void updateUserTagsResType(Map<String,Object> param){
		super.update("USERTAGS_RELATIONSHIP.updateUserTagsRes",param);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserTagsRelationship> queryAllUserTagsRes(Map<String, Object> param) {
		return super.queryForList("USERTAGS_RELATIONSHIP.queryAllUserTagsRes", param);
	}
	
	public Long queryAllUserTagsResByCount(Map<String, Object> param) {
		return (Long) super.queryForObject("USERTAGS_RELATIONSHIP.queryAllUserTagsResByCount",param);
	}
	
	public void deleteUserTagsRes(Map<String, Object> param) {
		super.delete("USERTAGS_RELATIONSHIP.deleteUserTagsResByParam",param);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserTagsRelationship> queryAllUserTagsPinyin(Map<String,Object> param){
		return super.queryForList("USERTAGS_RELATIONSHIP.queryAllUserTagsPinyin",param);
	}
	
	public Long queryAllUserTagsPinyinCount(Map<String,Object> param){
		return (Long)super.queryForObject("USERTAGS_RELATIONSHIP.queryAllUserTagsPinyinCount",param);
	}
}
