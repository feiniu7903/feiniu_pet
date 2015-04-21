package com.lvmama.finance.settlement.ibatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.finance.base.FinanceContext;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.PageDao;
import com.lvmama.finance.settlement.ibatis.po.OrdSubSettlement;

@Repository
@SuppressWarnings({ "unchecked" })
public class OrdSubSettlementDAO extends PageDao {
	/**
	 * 查询结算子单
	 * 
	 * @return 结算子单
	 */
	public Page<OrdSubSettlement> searchOrdSubSettlement() {
		return queryForPageFin("ORDSUBSETTLEMENT.searchOrdSubSettlement", FinanceContext.getPageSearchContext().getContext());
	}

	public List<OrdSubSettlement> getOrdSubSettlementBySettlementId(Long settlementId) {
		return queryForList("ORDSUBSETTLEMENT.getOrdSubSettlementBySettlementId", settlementId);
	}

	public void updateOrdSubSettlement(OrdSubSettlement ordSubSettlement) {
		update("ORDSUBSETTLEMENT.updateOrdSubSettlement", ordSubSettlement);
	}

	public Long insertOrdSubSettlement(OrdSubSettlement ordSubSettlement) {
		return (Long) insert("ORDSUBSETTLEMENT.insertOrdSubSettlement", ordSubSettlement);
	}

	public void insertOrdSubSettlementWithId(OrdSubSettlement ordSubSettlement) {
		insert("ORDSUBSETTLEMENT.insertOrdSubSettlementWithId", ordSubSettlement);
	}

	public void deleteOrdSubSettlement(Long ordSubSettlementId) {
		this.delete("ORDSUBSETTLEMENT.deleteOrdSubSettlement", ordSubSettlementId);
	}

	public void updatePayAmount(Long subSettlementId) {
		this.update("ORDSUBSETTLEMENT.updatePayAmount", subSettlementId);
	}

	/**
	 * 根据id获取结算子单
	 * 
	 * @param id
	 * @return
	 */
	public OrdSubSettlement getOrdSubSettlementById(Long id) {
		return (OrdSubSettlement) queryForObject("ORDSUBSETTLEMENT.getOrdSubSettlementById", id);
	}

	/**
	 * 根据结算单ID，采购产品id查询结算子单
	 * 
	 * @param settlementId
	 *            结算单ID
	 * @param metaProductId
	 *            采购产品ID
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public OrdSubSettlement getOrdSubSettlementBySettlementIdMetaProductId(Long settlementId, Long metaProductId,Long metaBranchId) {
		Map map = new HashMap();
		map.put("settlementId", settlementId);
		map.put("metaProductId", metaProductId);
		map.put("metaBranchId", metaBranchId);
		return (OrdSubSettlement) queryForObject("ORDSUBSETTLEMENT.getOrdSubSettlementBySettlementIdMetaProductId", map);
	}
}
