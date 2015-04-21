package com.lvmama.tmall.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.tmall.TaobaoTravelCombo;

public class TaobaoTravelComboDAO extends BaseIbatisDAO {
	public TaobaoTravelComboDAO() {
		super();
	}
	
	public void insertTaobaoTravelCombo(TaobaoTravelCombo taobaoTravelCombo) {
		if (taobaoTravelCombo.getId() == null) {
			taobaoTravelCombo.setId(selectSeq());
		}
		super.insert("TAOBAO_TRAVEL_COMBO.insert", taobaoTravelCombo);
	}

	public int updateTaobaoTravelCombo(TaobaoTravelCombo taobaoTravelCombo) {
		return super.update("TAOBAO_TRAVEL_COMBO.update", taobaoTravelCombo);
	}
	
	public TaobaoTravelCombo selectTaobaoTravelCombo(Long id) {
		return (TaobaoTravelCombo) super.queryForObject("TAOBAO_TRAVEL_COMBO.byId", id);
	}
	
	public List<TaobaoTravelCombo> selectTaobaoTravelCombo(Map<String, Object> params) {
		return super.queryForList("TAOBAO_TRAVEL_COMBO.byMap", params);
	}

	public List<TaobaoTravelCombo> selectTaobaoTravelComboList(Long tbProdSyncId) {
		return (List<TaobaoTravelCombo>) super.queryForList("TAOBAO_TRAVEL_COMBO.byTbProdSyncId", tbProdSyncId);
	}
	
	public int deleteTaobaoTravelCombo(Long id) {
		return super.delete("TAOBAO_TRAVEL_COMBO.delete", id);
	}
	
	public Long selectSeq() {
		return (Long) super.queryForObject("TAOBAO_TRAVEL_COMBO.selectSeq");
	}
	
	public int deleteTaobaoTravelComboByTbProdSyncId(Long tbProdSyncId) {
		return super.delete("TAOBAO_TRAVEL_COMBO.deleteByTbProdSyncId", tbProdSyncId);
	}
}
