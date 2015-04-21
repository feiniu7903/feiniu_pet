package com.lvmama.pet.mobile.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobilePushJogTask;
import com.lvmama.comm.vo.Constant;

public class MobilePushJogTaskDAO extends BaseIbatisDAO {

	public MobilePushJogTaskDAO() {
		super();
	}

	
	
	
	public List<MobilePushJogTask> selectAllJobs(){
		return (List<MobilePushJogTask>) super
				.queryForList("MOBILE_PUSH_JOG_TASK.selectAllJobs",Constant.getInstance().getValue("PUSH_SUFFIX"));
	}

	public int deleteByPrimaryKey(Long mobilePushJogTaskId) {
		MobilePushJogTask key = new MobilePushJogTask();
		key.setMobilePushJogTaskId(mobilePushJogTaskId);
		int rows = super.delete("MOBILE_PUSH_JOG_TASK.deleteByPrimaryKey", key);
		return rows;
	}

	public void insert(MobilePushJogTask record) {
		System.out.println("#####"+record.getPushSuffix());
		super.insert("MOBILE_PUSH_JOG_TASK.insert", record);
	}

	public void insertSelective(MobilePushJogTask record) {
		super.insert("MOBILE_PUSH_JOG_TASK.insertSelective", record);
	}

	public MobilePushJogTask selectByPrimaryKey(Long mobilePushJogTaskId) {
		MobilePushJogTask key = new MobilePushJogTask();
		key.setMobilePushJogTaskId(mobilePushJogTaskId);
		MobilePushJogTask record = (MobilePushJogTask) super.queryForObject(
				"MOBILE_PUSH_JOG_TASK.selectByPrimaryKey", key);
		return record;
	}

	public int updateByPrimaryKeySelective(MobilePushJogTask record) {
		int rows = super.update(
				"MOBILE_PUSH_JOG_TASK.updateByPrimaryKeySelective", record);
		return rows;
	}

	public int updateByPrimaryKey(MobilePushJogTask record) {
		int rows = super.update("MOBILE_PUSH_JOG_TASK.updateByPrimaryKey",
				record);
		return rows;
	}
}