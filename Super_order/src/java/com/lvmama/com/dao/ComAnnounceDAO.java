package com.lvmama.com.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComAnnouncement;

@SuppressWarnings("unchecked")
public class ComAnnounceDAO extends BaseIbatisDAO {
	public Long queryselectByGroups(Map param){
		return (Long) super.queryForObject(
				"COM_ANNOUNCEMENT.selectByGroups", param);
	}
	public List<ComAnnouncement> queryComAnnounceByBeginTime(Map map) {
		return super.queryForList(
				"COM_ANNOUNCEMENT.selectByBeginTime",map);
	}
	
	public void addComAnnouncement(ComAnnouncement comAnnouncement) {
		super.insert(
				"COM_ANNOUNCEMENT.insert", comAnnouncement);
	}

	public Long queryComAnnounceByParamCount(Map param) {
		return (Long) super.queryForObject(
				"COM_ANNOUNCEMENT.selectByParamCount", param);
	}
	
	public Boolean selectByUserIdOrgId(Map param) {
		Long count=0l;
		count=(Long) super.queryForObject(
				"COM_ANNOUNCEMENT.selectByUserIdOrgId", param);
		return count>0;
	}
	
	public List<ComAnnouncement> queryComAnnounceByParam(Map param) {
		return super.queryForList(
				"COM_ANNOUNCEMENT.selectByParam", param);
	}
}