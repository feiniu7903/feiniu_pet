package com.lvmama.finance.settlement.ibatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.finance.base.FinanceContext;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.PageDao;
import com.lvmama.finance.settlement.ibatis.po.OrdSubSettlementItem;
import com.lvmama.finance.settlement.ibatis.vo.OrderProductDetail;
import com.lvmama.finance.settlement.ibatis.vo.OrderSearchResult;

@Repository
@SuppressWarnings({"unchecked","rawtypes"})
public class OrdSubSettlementItemDAO extends PageDao {

	public Long insertOrdSubSettlementItem(OrdSubSettlementItem po) {
		return (Long) insert("ORDSUBSETTLEMENTITEM.insertOrdSubSettlementItem", po);
	}
	
	public Page<OrdSubSettlementItem> searchOrdSubSettlementItem() {
		Map map = FinanceContext.getPageSearchContext().getContext();
		return queryForPageFin("ORDSUBSETTLEMENTITEM.searchOrdSubSettlementItem", map);
	}

	public Double searchSumPrice(Map map){
		return (Double) queryForObject("ORDSUBSETTLEMENTITEM.searchOrdSubSettlementItemSumprice",map);
	}
	/**
	 * 删除结算子单项
	 * 
	 * @param ordSubSettlementId
	 *            结算子单ID
	 */
	public void deleteOrdSubSettlementItem(Long ordSubSettlementId) {
		this.delete("ORDSUBSETTLEMENTITEM.deleteOrdSubSettlementItem",ordSubSettlementId);
	}
	
	/**
	 * 修改实际结算价
	 * @param change
	 */
	public void modifyPrice(OrdSubSettlementItem item) {
		this.update("ORDSUBSETTLEMENTITEM.modifyPrice",item);
	}
	
	
	public void batchModifyPrice(Long subSettlementId,Long metaProductId,Double amount){
		Map map = new HashMap();
		map.put("subSettlementId", subSettlementId);
		map.put("metaProductId", metaProductId);
		map.put("amount", amount);
		this.update("ORDSUBSETTLEMENTITEM.batchModifyPrice",map);
	}
	
	public void batchDelete(Long[] ids){
		Map map = new HashMap();
		map.put("ids", ids);
		this.delete("ORDSUBSETTLEMENTITEM.batchDelete",map);
	}
	
	public List<OrderSearchResult> searchOrder(Long settlementId,Long orderId){
		Map map = new HashMap();
		map.put("orderId", orderId);
		map.put("settlementId", settlementId);
		return this.queryForList("ORDSUBSETTLEMENTITEM.searchOrder",map);
	}

	public List<OrdSubSettlementItem> searchOrdSubSettlementItemList() {
		return queryForListForReport("ORDSUBSETTLEMENTITEM.searchOrdSubSettlementItemList", FinanceContext.getPageSearchContext().getContext());
	}
	
	public List<OrderProductDetail> searchOrdSubSettlementItemProductList() {
		return queryForList("ORDSUBSETTLEMENTITEM.searchOrdSubSettlementItemProductList", FinanceContext.getPageSearchContext().getContext());
	}
	 
	/**
	 * 修改结算总价
	 * @param subSettlementItemId
	 * @param realItemPriceSum
	 */
	public void modifyTotalPrice(Long subSettlementItemId, Double realItemPriceSum, Long totalQuantity){
		Map map = new HashMap();
		map.put("subSettlementItemId", subSettlementItemId);
		map.put("realItemPriceSum", realItemPriceSum);
		map.put("totalQuantity", totalQuantity);
		this.update("ORDSUBSETTLEMENTITEM.modifyTotalPrice", map);
	}
	
	/**
	 * 查询结算金额
	 * @param settlementId
	 * @return
	 */
	public Double queryPayAmount(Long settlementId){
		return (Double) queryForObject("ORDSUBSETTLEMENTITEM.queryPayAmount", settlementId);
	}
	
	/**
	 * 修改应结金额
	 * @param amount
	 * @param settlementId
	 */
	public void modifyPayAmount(Double amount, Long settlementId){
		Map map = new HashMap();
		map.put("amount", amount);
		map.put("settlementId", settlementId);
		this.update("ORDSUBSETTLEMENTITEM.modifyPayAmount", map);
	}
}


