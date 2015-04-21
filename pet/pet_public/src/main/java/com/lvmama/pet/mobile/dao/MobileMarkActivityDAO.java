package com.lvmama.pet.mobile.dao;

import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobileMarkActivity;

public class MobileMarkActivityDAO extends BaseIbatisDAO {

	public MobileMarkActivityDAO() {
		super();
	}

	public MobileMarkActivity queryUniqueMobileMarkActivity(Map<String, Object> param) {
		return (MobileMarkActivity) super.queryForObject(
				"MOBILE_MARK_ACTIVITY.queryUniqueMobileMarkActivity", param);
	}

	public Long getOperateNumByMobileMarkActivityId(Long mobileMarkActivityId) {
		return (Long) super.queryForObject(
				"MOBILE_MARK_ACTIVITY.getOperateNumByMobileMarkActivityId",
				mobileMarkActivityId);
	}
	
	public Long getTotalByMobileMarkActivityId(Long mobileMarkActivityId) {
		return (Long) super.queryForObject(
				"MOBILE_MARK_ACTIVITY.getTotalByMobileMarkActivityId",
				mobileMarkActivityId);
	}

	public int deleteByPrimaryKey(Long mobileMarkActivityId) {
		MobileMarkActivity key = new MobileMarkActivity();
		key.setMobileMarkActivityId(mobileMarkActivityId);
		int rows = super.delete("MOBILE_MARK_ACTIVITY.deleteByPrimaryKey", key);
		return rows;
	}

	public Long insert(MobileMarkActivity record) {
		return (Long)super.insert("MOBILE_MARK_ACTIVITY.insert", record);

	}

	public void insertSelective(MobileMarkActivity record) {
		super.insert("MOBILE_MARK_ACTIVITY.insertSelective", record);
	}

	public MobileMarkActivity selectByPrimaryKey(Long mobileMarkActivityId) {
		MobileMarkActivity key = new MobileMarkActivity();
		key.setMobileMarkActivityId(mobileMarkActivityId);
		MobileMarkActivity record = (MobileMarkActivity) super.queryForObject(
				"MOBILE_MARK_ACTIVITY.selectByPrimaryKey", key);
		return record;
	}

	public int updateByPrimaryKeySelective(MobileMarkActivity record) {
		int rows = super.update(
				"MOBILE_MARK_ACTIVITY.updateByPrimaryKeySelective", record);
		return rows;
	}

	public int updateByPrimaryKey(MobileMarkActivity record) {
		int rows = super.update("MOBILE_MARK_ACTIVITY.updateByPrimaryKey",
				record);
		return rows;
	}
}