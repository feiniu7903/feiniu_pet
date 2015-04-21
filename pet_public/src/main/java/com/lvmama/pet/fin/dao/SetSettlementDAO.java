package com.lvmama.pet.fin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.fin.SetSettlement;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant.SETTLEMENT_TYPE;
import com.lvmama.comm.vo.Constant.SET_SETTLEMENT_STATUS;

/**
 * 结算单DAO
 * 
 * @author yanggan
 * 
 */
@Repository
public class SetSettlementDAO extends BaseIbatisDAO {

	/**
	 * 查询结算单
	 * 
	 * @param targetId
	 *            结算对象ID
	 * @param settlementType
	 *            结算单类型
	 * @param payed
	 *            结算单状态
	 */
	public SetSettlement searchSettlementByTargetIdFilialeName(Long targetId,String filialeName, SETTLEMENT_TYPE settlementType, SET_SETTLEMENT_STATUS status,String businessName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("targetId", targetId);
		map.put("settlementType", settlementType.name());
		map.put("status", status.name());
		map.put("businessName", businessName);
		if(filialeName!= null){
			map.put("filialeName",filialeName);
		}
		return (SetSettlement) super.queryForObject("SET_SETTLEMENT.searchSettlementByTargetIdFilialeName", map);
	}

	/**
	 * 查询结算单
	 * 
	 * @param targetId
	 *            结算对象ID
	 * @param settlementType
	 *            结算单类型
	 * @param payed
	 *            结算单状态
	 */
	public SetSettlement searchSettlementByTargetIdFilialeName(Long targetId,String filialeName, SETTLEMENT_TYPE settlementType, SET_SETTLEMENT_STATUS status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("targetId", targetId);
		map.put("settlementType", settlementType.name());
		map.put("status", status.name());
		if(filialeName!= null){
			map.put("filialeName",filialeName);
		}
		return (SetSettlement) super.queryForObject("SET_SETTLEMENT.searchSettlementByTargetIdFilialeName", map);
	}
	

	/**
	 * 根据结算单号查询结算单
	 * 
	 * @param settlementId
	 *            结算单号
	 * @return
	 */
	public SetSettlement searchSettlementBySettlementId(Long settlementId) {
		return (SetSettlement) super.queryForObject("SET_SETTLEMENT.searchSettlementBySettlementId", settlementId);
	}

	/**
	 * 新增结算单
	 * 
	 * @param settlement
	 * @return
	 */
	public void insertSettlement(SetSettlement settlement) {
		super.insert("SET_SETTLEMENT.insert", settlement);
	}


	/**
	 * 重新刷新结算单的应结金额
	 * 
	 * @param settlementId
	 *            结算单ID
	 */
	public void updateSettlementSettlementAmount(Long settlementId) {
		super.update("SET_SETTLEMENT.updateSettlementSettlementAmount", settlementId);
	}

	/**
	 * 查询分页的结算单信息
	 * 
	 * @param searchParams
	 *            查询参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<SetSettlement> searchList(Map<String, Object> searchParams) {
		return super.queryForPage("SET_SETTLEMENT.searchList", searchParams);
	}
	
	/**
	 * 根据查询条件查询结算单ID
	 * 
	 * @param searchParameter
	 *            查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> searchSettlementIds(Map<String, Object> searchParams){
		return super.queryForList("SET_SETTLEMENT.searchSettlementIds", searchParams);
	}

	/**
	 * 根据结算单号查询结算单信息（包含原始结算对象信息）
	 * 
	 * @param id
	 *            结算单号
	 * @return
	 */
	public SetSettlement searchInitalSettlementBySettlementId(Long id) {
		return (SetSettlement) super.queryForObject("SET_SETTLEMENT.searchInitalSettlementBySettlementId", id);
	}

	/**
	 * 修改固话结算对象信息
	 * 
	 * @param settlement
	 *            结算单信息
	 */
	public void updateInitalInfo(SetSettlement settlement) {
		super.update("SET_SETTLEMENT.updateInitalInfo", settlement);
	}

	/**
	 * 更新结算单状态
	 * @param settlement 结算单信息
	 */
	public void updateSettlement(Map<String,Object> updateMap) {
		super.update("SET_SETTLEMENT.updateSettlement", updateMap);
		
	}
	
}
