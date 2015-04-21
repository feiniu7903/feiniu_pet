package com.lvmama.finance.settlement.ibatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.finance.base.BaseDAO;
import com.lvmama.finance.settlement.ibatis.po.OrdSettlementChange;
import com.lvmama.finance.settlement.ibatis.vo.SettlementDeduction;

/**
 * 结算单修改记录
 * 
 * @author yanggan
 * 
 */
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OrdSettlementChangeDAO extends BaseDAO {

	/**
	 * 新增修改记录
	 * 
	 * @param change
	 *            修改记录
	 */
	public Long insert(OrdSettlementChange change) {
		return (Long) this.insert("SETTLEMENTCHANGE.insert", change);
	}

	/**
	 * 批量新增修改记录
	 * 
	 * @param subSettlementId
	 *            结算子单ID
	 * @param metaProductId
	 *            采购产品ID
	 * @param amount
	 *            金额
	 * @param userid
	 *            用户ID
	 * @param remark
	 *            修改原因
	 */
	public void insertBatch(Long subSettlementId, Long metaProductId, Double amount, Long userid, String remark) {
		Map map = new HashMap();
		map.put("subSettlementId", subSettlementId);
		map.put("metaProductId", metaProductId);
		map.put("amount", amount);
		map.put("creator", userid);
		map.put("remark", remark);
		this.insert("SETTLEMENTCHANGE.insertBatch", map);
	}

	/**
	 * 查询结算子单项的修改记录(结算单内)
	 * 
	 * @return
	 */
	public List<OrdSettlementChange> searchBySettlement(Long settlementId) {
		return queryForList("SETTLEMENTCHANGE.searchBySettlement", settlementId);
	}

	/**
	 * 查询结算子单项的修改记录(结算子单内)
	 * 
	 * @return
	 */
	public List<OrdSettlementChange> searchBySubSettlement(Long subSettlementId) {
		return queryForList("SETTLEMENTCHANGE.searchBySubSettlement", subSettlementId);
	}

	/**
	 * 
	 * 删除结算子单时记录
	 * 
	 * @param ids
	 */
	public void insertBatchDel(Long userid, Long subSettlementId) {
		Map map = new HashMap();
		map.put("creator", userid);
		map.put("subSettlementId", subSettlementId);
		this.insert("SETTLEMENTCHANGE.insertBatchDelBySubSettlementId", map);
	}
	/**
	 * 
	 * 删除结算子单项时记录
	 * 
	 * @param ids
	 */
	public void insertBatchDel(Long userid, Long[] ids) {
		Map map = new HashMap();
		map.put("creator", userid);
		map.put("ids", ids);
		this.insert("SETTLEMENTCHANGE.insertBatchDel", map);
	}
	/**
	 * 查询抵扣款信息
	 * 
	 * @param settlementId
	 *            结算单ID
	 */
	public Integer searchSettlementDeduction(Long settlementId) {
		return (Integer) queryForObject("SETTLEMENTCHANGE.searchSettlementDeduction", settlementId);
	}

	/**
	 * 查询抵扣款名字
	 * 
	 * @param settlementId
	 *            结算单ID
	 * @return
	 */
	public List<SettlementDeduction> searchSettlementDeductionList(Long settlementId) {
		return queryForList("SETTLEMENTCHANGE.searchSettlementDeductionList", settlementId);
	}
	
	/**
	 * 插入结算单修改记录 
	 * 
	 * @param change
	 *            修改记录
	 */
	public Long insertOrdSettlementChange(OrdSettlementChange change) {
		return (Long)this.insert("SETTLEMENTCHANGE.insertOrdSettlementChange", change);
	}
	
	public OrdSettlementChange searchModifyOrDelChange(Long settlementId){
		return (OrdSettlementChange)this.queryForObject("SETTLEMENTCHANGE.searchModifyOrDelChange",settlementId);
	}
}