package com.lvmama.op.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.op.OpGroupBudgetProd;

public class OpGroupBudgetProdDAO extends BaseIbatisDAO{	
	
	public Long insert(OpGroupBudgetProd opGroupBudgetProd){
		return (Long)super.insert("OP_GROUP_BUDGET_PROD.insert", opGroupBudgetProd);
	}
	/**
	 * 查询实际产品成本
	 */
	public List<OpGroupBudgetProd> getGroupBudgetProdListByGroupCode(Map<String,Object> parameter){
		return super.queryForList("OP_GROUP_BUDGET_PROD.selectGroupBudgetProdListFromOrderByParam",parameter);
	}
}
