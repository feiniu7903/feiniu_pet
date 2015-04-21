package com.lvmama.op.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.op.OpOtherIncoming;

public class OpOtherIncomingDAO extends BaseIbatisDAO{	
	
	/**
	 * 查询团预算
	 */
	public List<OpOtherIncoming> getOtherIncomingByGroupCode(Map<String,Object> parameter){
		return super.queryForList("OP_OTHER_INCOMING.selectOtherIncomingListByParam",parameter);
	}
	
	/**
	 * 查询团预算
	 */
	public Long getSumOtherIncomingByGroupCode(Map<String,Object> parameter){
		return (Long) super.queryForObject("OP_OTHER_INCOMING.selectSumOtherIncomingByParam",parameter);
	}
}