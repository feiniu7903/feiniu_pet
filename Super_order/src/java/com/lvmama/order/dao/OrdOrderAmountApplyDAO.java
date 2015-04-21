package com.lvmama.order.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrderAmountApply;



public interface OrdOrderAmountApplyDAO {
	void insertSelective(OrdOrderAmountApply record);
	OrdOrderAmountApply selectByPrimaryKey(Long amountApplyId);
	int updateByPrimaryKeySelective(OrdOrderAmountApply record);
	 /**
	  * 查询记录
	  * @param ordOrderAmountApply
	  * @return
	  */
     List<OrdOrderAmountApply> selectByOrdOrderAmountApply(Map<String, Object> parameter);
     /**
      * 查询记录数.
      * @param ordOrderAmountApply
      * @return
      */
    Long selectByOrdOrderAmountApplyCount(Map<String, Object> parameter);
}