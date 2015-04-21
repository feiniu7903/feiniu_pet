package com.lvmama.finance.group.ibatis.dao;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.bee.po.op.OpGroupBudget;
import com.lvmama.finance.base.BaseDAO;

/**
 * 
 * 团预算明细DAO
 * 
 * @author yanggan
 * 
 */
@Repository
public class OpGroupBudgetDAO extends BaseDAO {

	/**
	 * 根据团号查询团预算信息
	 * 
	 * @param travelGroupCode
	 *            团号
	 * 
	 * @return 预算信息
	 */
	public OpGroupBudget searchBudget(String travelGroupCode) {
		return (OpGroupBudget) queryForObject("OpGroupBudget.searchBudget", travelGroupCode);
	}

}
