package com.lvmama.finance.group.ibatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.bee.po.op.OpOtherIncoming;
import com.lvmama.finance.base.BaseDAO;
import com.lvmama.finance.group.ibatis.vo.GroupBudgetFixed;

/**
 * 团固定成本明细 DAO
 * 
 * @author yanggan
 * 
 */
@Repository
@SuppressWarnings({ "unchecked", "rawtypes" })
public class OpGroupBudgetFixedDAO extends BaseDAO {

	/**
	 * 查询团固定成本明细
	 * 
	 * @param travelGroupCode
	 *            团号
	 * @param type
	 *            类型
	 * @return
	 */
	public List<GroupBudgetFixed> searchBudgetFixed(String travelGroupCode, String type) {
		Map map = new HashMap();
		map.put("code", travelGroupCode);
		map.put("type", type);
		return queryForList("OpGroupBudgetFixed.searchBudgetFixed", map);
	}

	/**
	 * 支付成功之后修改固定成本项
	 * 
	 * @param groupSettlementIds
	 *            团单项结算ID集合
	 * @param exchangeRate
	 *            汇率
	 * @param amount
	 *            支付成功的金额
	 * @param remark
	 *            备注
	 * @param status
	 *            状态
	 */
	public void pay(Long itemId, Double exchangeRate, Double amount, String remark, String status) {
		Map map = new HashMap();
		map.put("itemId", itemId);
		map.put("exchangeRate", exchangeRate);
		if(amount !=null ){
			map.put("amount", amount);
		}
		map.put("remark", remark);
		map.put("status", status);
		update("OpGroupBudgetFixed.pay", map);
	}

	/**
	 * 根据成本项ID查询固定成本项
	 * 
	 * @param itemId
	 *            成本项ID
	 * @return
	 */
	public GroupBudgetFixed searchBudgetFixedById(Long itemId) {
		return (GroupBudgetFixed) queryForObject("OpGroupBudgetFixed.searchBudgetFixedById", itemId);
	}

	/**
	 * 修改固定成本项的人民币总金额
	 * 
	 * @param fixed
	 *            固定成本项
	 */
	public void updateSubTotalCosts(GroupBudgetFixed fixed) {
		update("OpGroupBudgetFixed.updateSubTotalCosts", fixed);
	}
	
	/**
	 * 查询附加收入
	 * @param groupCode
	 * @return
	 */
	public List<OpOtherIncoming> searchOtherIncoming(String groupCode) {
		return this.queryForList("OpGroupBudgetFixed.searchOtherIncoming", groupCode);
	}
}
