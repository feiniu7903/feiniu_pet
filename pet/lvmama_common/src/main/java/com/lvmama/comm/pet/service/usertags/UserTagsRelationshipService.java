package com.lvmama.comm.pet.service.usertags;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.usertags.UserTagsRelationship;




public interface UserTagsRelationshipService {
	public List<UserTagsRelationship> queryAllUserTagsRes(Map<String, Object> param);
	public Long queryAllUserTagsResByCount(Map<String, Object> param);
	public void saveUserTagsRes(Map<String,Object> param);
	public void updateUserTagsRes(Map<String, Object> param);
	public void deleteUserTagsRes(Map<String,Object> param);
	public void updateUserTagsResType(Map<String,Object> param);
	public List<UserTagsRelationship> queryAllUserTagsPinyin(Map<String,Object> param);
	public Long queryAllUserTagsPinyinCount(Map<String,Object> param);
}
