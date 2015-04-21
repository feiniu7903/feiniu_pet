package com.lvmama.com.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComCondition;

public class ComConditionDAO extends BaseIbatisDAO {

	public int deleteByPrimaryKey(ComCondition cond) {
		int rows = super.delete("COM_CONDITION.deleteByPrimaryKey", cond);
		return rows;
	}

	public void insert(ComCondition record) {
		super.insert("COM_CONDITION.insert",
				record);
	}
	
	public ComCondition selectByPrimaryKey(Long conditionId)
			 {
		ComCondition key = new ComCondition();
		key.setConditionId(conditionId);
		ComCondition record = (ComCondition) super
				.queryForObject(
						"COM_CONDITION.selectByPrimaryKey", key);
		return record;
	}
	
	public int updateByPrimaryKey(ComCondition record) {
		int rows = super.update(
				"COM_CONDITION.updateByPrimaryKey",
				record);
		return rows;
	}
	
	public List<ComCondition> selectConditionByObjectId(Map params) {
		return super.queryForList("COM_CONDITION.selectCondition", params);
	}

}