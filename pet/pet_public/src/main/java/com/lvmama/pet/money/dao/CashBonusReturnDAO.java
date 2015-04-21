
package com.lvmama.pet.money.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.money.CashBonusReturn;
import com.lvmama.comm.pet.vo.Page;
/**
 * CashBonusReturnDAO,持久层类 用于CashBonusReturn表的CRUD.
 * @author taiqichao
 * @version 1.0
 */

public class CashBonusReturnDAO extends BaseIbatisDAO{	
	/**
	 * 持久化对象
	 * @return
	 */
	public Long insert(CashBonusReturn cashBonusReturn) {
		return (Long)super.insert("CASH_BONUS_RETURN.insert", cashBonusReturn);
	}
	/**
	 * 分页查询返现信息
	 * @param userId
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CashBonusReturn> queryBonusReturn(final Long userId,final Integer beginIndex, final Integer endIndex){
		final Map<String, Object> map=new HashMap<String, Object>();
		if(userId!=null){
			map.put("userId", userId);
		}
		map.put("beginIndex", beginIndex.toString());
		map.put("endIndex", endIndex.toString());
		return super.queryForList("CASH_BONUS_RETURN.queryBonusReturn",map);
	}
	/**
	 * 查询返现信息的条数
	 * @param userId
	 * @return
	 */
	public Long getBonusReturnCount(final Long userId){
		final Map<String, Object> map=new HashMap<String, Object>();
		if(userId!=null){
			map.put("userId", userId);
		}
		return (Long) super.queryForObject("CASH_BONUS_RETURN.getBonusReturnCount",map);
	}
}
