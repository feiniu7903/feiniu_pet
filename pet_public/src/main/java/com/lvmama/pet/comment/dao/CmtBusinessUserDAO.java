package com.lvmama.pet.comment.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.comment.CmtBusinessUser;
import com.lvmama.comm.pet.po.place.Place;
public class CmtBusinessUserDAO extends BaseIbatisDAO {
	
	public List<CmtBusinessUser> query(Map<String, Object> parameters)
	{
		List<CmtBusinessUser> query = (List<CmtBusinessUser>) super.queryForList("CMT_BUSINESS_USER.query", parameters);
		for(CmtBusinessUser user:query){
			user.setPlaceList((List<Place>) super.queryForList("CMT_BUSINESS_USER.getPlaceListByBusinessUserID",user.getCmtBusinessUserID()));
		}
		return query;
	}
	
	
	public long insert(CmtBusinessUser cmtBusinessUser)
	{
		super.insert("CMT_BUSINESS_USER.insert", cmtBusinessUser);
		return cmtBusinessUser.getCmtBusinessUserID();
	}
	
	public long insertPlaceRelation(CmtBusinessUser cmtBusinessUser)
	{
		super.insert("CMT_BUSINESS_USER.insertPlaceRelation", cmtBusinessUser);
		return cmtBusinessUser.getCmtBusinessUserID();
	}
	
	public int update(final CmtBusinessUser cmtBusinessUser)
	{
		return super.update("CMT_BUSINESS_USER.update", cmtBusinessUser);
	}
	
	public int deletePlaceRelation(final CmtBusinessUser cmtBusinessUser)
	{
		return super.delete("CMT_BUSINESS_USER.deletePlaceRelation", cmtBusinessUser);
	}
	
	public Long getUserCount(Map<String, Object> parameters)
	{
		return (Long) super.queryForObject("CMT_BUSINESS_USER.cmtBusinessUserCount", parameters);
	}

}
