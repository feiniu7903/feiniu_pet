package com.lvmama.finance.group.ibatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.finance.base.BaseDAO;
import com.lvmama.finance.group.ibatis.vo.GroupBudgetProd;

/**
 * 
 * 团产品明细DAO
 * 
 * @author yanggan
 * 
 */
@Repository
@SuppressWarnings({ "unchecked", "rawtypes" })
public class OpGroupBudgetProdDAO extends BaseDAO {

	/**
	 * 查询团产品明细
	 * 
	 * @param travelGroupCode
	 *            团号
	 * @param type
	 *            类型
	 * @return
	 */
	public List<GroupBudgetProd> searchBudgetProd(String travelGroupCode, String type) {
		Map map = new HashMap();
		map.put("code", travelGroupCode);
		map.put("type", type);
		return queryForList("OpGroupBudgetProd.searchBudgetProd", map);
	}

	/**
	 * 打款后修改产品明细表的打款状态
	 * 
	 * @param groupSettlementIds
	 *            团单项结算ID集合
	 * @param exchangeRate
	 *            汇率
	 * @param amount
	 *            打款金额
	 * @param remark
	 *            备注
	 * @param status
	 *            状态
	 */
	public void pay(Long itemId, Double exchangeRate, Double amount, String remark, String status) {
		Map map = new HashMap();
		map.put("itemId", itemId);
		map.put("exchangeRate", exchangeRate);
		if(amount != null){
			map.put("amount", amount);
		}
		map.put("remark", remark);
		map.put("status", status);
		update("OpGroupBudgetProd.pay", map);
	}
	
	/**
	 * 根据成本项ID查询产品成本
	 * 
	 * @param itemId
	 *            成本项ID
	 * @return
	 */
	public GroupBudgetProd searchBudgetProdById(Long itemId) {
		return (GroupBudgetProd) queryForObject("OpGroupBudgetProd.searchBudgetProdById", itemId);
	}

	/**
	 * 修改产品成本的人民币总金额
	 * 
	 * @param prod
	 *            产品成本项信息
	 */
	public void updateSubTotalCosts(GroupBudgetProd prod) {
		this.update("OpGroupBudgetProd.updateSubTotalCosts", prod);
	}
}
