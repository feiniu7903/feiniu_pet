package com.lvmama.pet.userTags.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.usertags.UserTagsRelationship;
import com.lvmama.comm.pet.service.usertags.UserTagsRelationshipService;
import com.lvmama.pet.userTags.dao.UserTagsRelationshipDao;

public class UserTagsRelationshipServiceImpl implements UserTagsRelationshipService{
	private UserTagsRelationshipDao userTagsRelationshipDao;

	@Override
	public List<UserTagsRelationship> queryAllUserTagsRes(Map<String, Object> param) {
		return userTagsRelationshipDao.queryAllUserTagsRes(param);
	}

	@Override
	public Long queryAllUserTagsResByCount(Map<String, Object> param) {
		return userTagsRelationshipDao.queryAllUserTagsResByCount(param);
	}


	@Override
	public void deleteUserTagsRes(Map<String, Object> param) {
		userTagsRelationshipDao.deleteUserTagsRes(param);
	}
	
	@Override
	public void saveUserTagsRes(Map<String, Object> param) {
		userTagsRelationshipDao.saveUserTagsRes(param);
	}
	
	@Override
	public void updateUserTagsRes(Map<String, Object> param) {
		userTagsRelationshipDao.updateUserTagsRes(param);
		
	}
	
	@Override
	public void updateUserTagsResType(Map<String, Object> param){
		userTagsRelationshipDao.updateUserTagsResType(param);
	}

	public UserTagsRelationshipDao getUserTagsRelationshipDao() {
		return userTagsRelationshipDao;
	}

	public void setUserTagsRelationshipDao(
			UserTagsRelationshipDao userTagsRelationshipDao) {
		this.userTagsRelationshipDao = userTagsRelationshipDao;
	}

	@Override
	public List<UserTagsRelationship> queryAllUserTagsPinyin(
			Map<String, Object> param) {
		return this.userTagsRelationshipDao.queryAllUserTagsPinyin(param);
	}

	@Override
	public Long queryAllUserTagsPinyinCount(Map<String, Object> param) {
		return this.userTagsRelationshipDao.queryAllUserTagsPinyinCount(param);
	}

}
