package com.lvmama.pet.fin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.fin.SetSettlementChange;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;

@Repository
public class SetSettlementChangeDAO extends BaseIbatisDAO {

	public void insert(SetSettlementChange change) {
		super.insert("SET_SETTLEMENT_CHANGE.insert", change);
	}

	public void insertBatchDel(List<Long> settlementItemIds, String operatorName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("creator", operatorName);
		map.put("settlementItemIds", settlementItemIds);
		map.put("changetype", Constant.SET_SETTLEMENT_CHANGE_TYPE.DEL.name());
		super.insert("SET_SETTLEMENT_CHANGE.insertBatchDel", map);
	}
	
	@SuppressWarnings("unchecked")
	public Page<SetSettlementChange> searchBySettlementId(Map<String,Object> searchParams) {
		return super.queryForPage("SET_SETTLEMENT_CHANGE.searchBySettlementId", searchParams);
	}
	
	public void insertBatchModify(Long metaProductId, Long settlementId, Long settlementPrice, String remark, String operatorName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("metaProductId", metaProductId);
		map.put("settlementId", settlementId);
		map.put("amount", settlementPrice);
		map.put("creator", operatorName);
		map.put("remark", remark);
		map.put("changetype", Constant.SET_SETTLEMENT_CHANGE_TYPE.MODIFY.name());
		super.insert("SET_SETTLEMENT_CHANGE.insertBatchModify", map);
	}
	
}
