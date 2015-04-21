package com.lvmama.finance.settlement.ibatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.finance.base.FinanceContext;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.PageDao;
import com.lvmama.finance.settlement.ibatis.po.OrdSettlement;
import com.lvmama.finance.settlement.ibatis.vo.SimpleOrdSettlement;

/**
 * 结算单管理
 * 
 * @author yanggan
 * 
 */
@Repository
@SuppressWarnings({ "unchecked" })
public class OrdSettlementDAO extends PageDao {
	/**
	 * 查询结算单
	 * 
	 * @return 结算单
	 */
	public Page<OrdSettlement> searchOrdSettlement() {
		return queryForPageFin("ORDSETTLEMENT.searchOrdSettlement", FinanceContext.getPageSearchContext().getContext());
	}
	/**
	 * 根据id查询结算单
	 * @param id
	 * @return
	 */
	public OrdSettlement getOrdSettlementById(Long id){
		return (OrdSettlement)queryForObject("ORDSETTLEMENT.getOrdSettlementById",id);
	}

	/**
	 * 结算单支付
	 * 
	 * @param ors
	 *            支付信息
	 */
	public void settlementPay(OrdSettlement ors) {
		this.update("ORDSETTLEMENT.updateOrdSettlementPayedAmount", ors);
	}

	/**
	 * 查询结算单信息（包含结算对象，联系人信息）
	 * 
	 * @param id
	 * @return
	 */
	public SimpleOrdSettlement searchSingleOrdSettlementWithTarget(Long id) {
		return (SimpleOrdSettlement) this.queryForObject("ORDSETTLEMENT.searchSingleOrdSettlementWithTarget", id);
	}

	/**
	 * 查询原始结算单信息
	 * 
	 * @param id
	 * @return
	 */
	public SimpleOrdSettlement searchInitalOrdSettlementWithTarget(Long id) {
		return (SimpleOrdSettlement) this.queryForObject("ORDSETTLEMENT.searchInitalOrdSettlementWithTarget", id);
	}
	
	/**
	 * 修改固化结算单信息
	 * 
	 * @param id
	 * @return
	 */
	public void updateInitalInfo(Long id, OrdSettlement ors) {
		this.update("ORDSETTLEMENT.updateInitalInfo",  ors);
	}

	/**
	 * 查询结算单的结算金额与支付金额
	 * 
	 * @param id
	 * @return
	 */
	public OrdSettlement searchSingleOrdSettlementAmount(Long id) {
		return (OrdSettlement) this.queryForObject("ORDSETTLEMENT.searchSingleOrdSettlementAmount", id);
	}

	/**
	 * 
	 * 结算单确认
	 * 
	 * @param ors
	 *            结算单信息
	 */
	public void update(OrdSettlement ors) {
		this.update("ORDSETTLEMENT.updateSettlement", ors);
	}

	/**
	 * 修改订单子子项的结算状态
	 * 
	 * @param settlementId
	 *            结算单号
	 */
	public void updateOrderItemMetaSettlementStatus(Long settlementId) {
		this.update("ORDSETTLEMENT.updateOrderItemMetaSettlementStatus", settlementId);
	}

	/**
	 * 修改订单的结算状态
	 * 
	 * @param settlementId
	 *            结算单号
	 */
	public void updateOrderSettlementStatus(Long settlementId) {
		this.update("ORDSETTLEMENT.updateOrderSettlementStatus", settlementId);
	}

	/**
	 * 重新刷新结算单的应结金额
	 * 
	 * @param settlementId
	 */
	public void updateSettlementPayAmount(Long settlementId){
		this.update("ORDSETTLEMENT.refreshSettlementPayAmount", settlementId);
	}
	public OrdSettlement getNoConfirmedSettlementByTargetId(Long targetId, String settlementType) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("targetId", targetId);
			map.put("settlementType", settlementType);
			return (OrdSettlement) queryForObject("ORDSETTLEMENT.getNoConfirmedSettlementByTargetId", map);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询结算对象：" + String.valueOf(targetId) + "的未确认结算单时异常");
		}
	}

	public void updateOrdSettlement(OrdSettlement ordSettlement) {
		update("ORDSETTLEMENT.updateOrdSettlement", ordSettlement);
	}

	public Long insertOrdSettlement(OrdSettlement ordSettlement) {
		return (Long) insert("ORDSETTLEMENT.insertOrdSettlement", ordSettlement);
	}

	public List<Long> searchOrdSettlementIdList(){
		return queryForList("ORDSETTLEMENT.searchOrdSettlementIdList", FinanceContext.getPageSearchContext().getContext());
	}
}
