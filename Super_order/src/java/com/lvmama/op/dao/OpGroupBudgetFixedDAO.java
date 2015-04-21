package com.lvmama.op.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.op.OpGroupBudgetFixed;

public class OpGroupBudgetFixedDAO extends BaseIbatisDAO{	
	
	/**
	 * 查询团预算
	 */
	public List<OpGroupBudgetFixed> getGroupBudgetFixedListByGroupCode(Map<String,Object> parameter){
		return super.queryForList("OP_GROUP_BUDGET_FIXED.selectGroupBudgetFixedListByParam",parameter);
	}
	
	/**
	 * 查询团预算
	 */
	public Long getSumOtherIncomingByGroupCode(Map<String,Object> parameter){
		return (Long) super.queryForObject("OP_GROUP_BUDGET_FIXED.selectSumGroupBudgetFixedByParam",parameter);
	}
}
