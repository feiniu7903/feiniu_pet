package com.lvmama.order.dao.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountApply;
import com.lvmama.order.dao.OrdOrderAmountApplyDAO;

public class OrdOrderAountApplyDAOImpl extends BaseIbatisDAO implements OrdOrderAmountApplyDAO{
  
    public void insertSelective(OrdOrderAmountApply record) {
        super.insert("ORD_ORDER_AMOUNT_APPLY.insertSelective", record);
    }

    public OrdOrderAmountApply selectByPrimaryKey(Long amountApplyId) {
        OrdOrderAmountApply key = new OrdOrderAmountApply();
        key.setAmountApplyId(amountApplyId);
        OrdOrderAmountApply record = (OrdOrderAmountApply) super.queryForObject("ORD_ORDER_AMOUNT_APPLY.selectByPrimaryKey", key);
        return record;
    }
    /**
	  * 查询记录
	  * @param ordOrderAmountApply
	  * @return
	  */
    public List<OrdOrderAmountApply> selectByOrdOrderAmountApply(Map<String, Object> parameter) {
    	return super.queryForList("ORD_ORDER_AMOUNT_APPLY.selectByOrdParam", parameter);
    }
    /**
     * 查询记录数.
     * @param ordOrderAmountApply
     * @return
     */
    public Long selectByOrdOrderAmountApplyCount(Map<String, Object> parameter) {
    	try
		{
			return (Long)super.queryForObject("ORD_ORDER_AMOUNT_APPLY.selectByParamCount",parameter);
		}catch(Exception ex)
		{
			return 0L;
		}
    }
    
    
    public int updateByPrimaryKeySelective(OrdOrderAmountApply record) {
        int rows = super.update("ORD_ORDER_AMOUNT_APPLY.updateByPrimaryKeySelective", record);
        return rows;
    }
}