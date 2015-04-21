package com.lvmama.pet.fin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.fin.SetSettlement;
import com.lvmama.comm.pet.po.fin.SetSettlementItem;
import com.lvmama.comm.pet.vo.Page;
@Repository
@SuppressWarnings("unchecked")
public class FinanceSettlementDAO extends BaseIbatisDAO {
	public Page<SetSettlementItem> searchItemListPage(Map<String, Object> map) {
		return super.queryForPage("FINANCE_SETTLEMENT.searchItemList", map);
	}
	
	public List<SetSettlementItem> searchItemList(Map<String, Object> map) {
		map.put("start", 0);
		map.put("end", 50000);
		return super.queryForListForReport("FINANCE_SETTLEMENT.searchItemList", map);
	}
	
	public Page<SetSettlement> searchSettleListPage(Map<String,Object> map){
		return super.queryForPage("FINANCE_SETTLEMENT.searchSettleList", map);
	}
	public List<SetSettlement> searchSettleList(Map<String,Object> map){
		map.put("start", 0);
		map.put("end", 50000);
		return super.queryForListForReport("FINANCE_SETTLEMENT.searchSettleList", map);
	}
	
	public List<SetSettlementItem> searchItemsByItemIds(List<Long> settlementIds){
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("settlementItemIds", "settlementItemIds");
		return searchItemList(map);
	}
	public SetSettlement searchSetSettlementById(Long settlementId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("settlementId", settlementId);
		map.put("start", 0);
		map.put("end", 2);
		List<SetSettlement> settles = searchSettleList(map);
		if(null!=settles && !settles.isEmpty()){
			return settles.get(0);
		}
		return new SetSettlement();
	}
	
	public Long searchSumprice(Map<String,Object> map){
		return (Long) super.queryForObject("FINANCE_SETTLEMENT.searchSumprice", map);
	}
	
	public SetSettlementItem searchItemsByItemId(final Long setSettlementItemId){
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("settlementItemId", setSettlementItemId);
		List<SetSettlementItem> result =searchItemList(map);
		return null!=result&&!result.isEmpty()?result.get(0):null;
	}
}
