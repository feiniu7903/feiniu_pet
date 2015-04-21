package com.lvmama.tmall.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.tmall.TaobaoTravelComboType;

public class TaobaoTravelComboTypeDAO extends BaseIbatisDAO {
	public TaobaoTravelComboTypeDAO() {
		super();
	}
	
	public void insertTaobaoTravelComboType(TaobaoTravelComboType taobaoTravelComboType) {
		if (taobaoTravelComboType.getId() == null) {
			taobaoTravelComboType.setId(selectSeq());
		}
		super.insert("TAOBAO_TRAVEL_COMBO_TYPE.insert", taobaoTravelComboType);
	}
	
	public void insertTaobaoTravelComboType(Long travelComboId, String comboType) {
		TaobaoTravelComboType taobaoTravelComboType = new TaobaoTravelComboType();
		taobaoTravelComboType.setId(selectSeq());
		taobaoTravelComboType.setTravelComboId(travelComboId);
		taobaoTravelComboType.setComboType(comboType);
		super.insert("TAOBAO_TRAVEL_COMBO_TYPE.insert", taobaoTravelComboType);
	}

	public TaobaoTravelComboType selectTaobaoTravelComboType(Long id) {
		return (TaobaoTravelComboType) super.queryForObject("TAOBAO_TRAVEL_COMBO_TYPE.byId", id);
	}
	
	public List<TaobaoTravelComboType> selectTaobaoTravelComboType(Map<String, Object> params) {
		return super.queryForList("TAOBAO_TRAVEL_COMBO_TYPE.byMap", params);
	}
	
	public List<TaobaoTravelComboType> selectTaobaoTravelComboTypeByTravelComboId(Long travelComboId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("travelComboId", travelComboId);
		return selectTaobaoTravelComboType(params);
	}
	
	public Long selectTaobaoTravelComboType2CountByMap(Map<String, Object> params) {
		return (Long) super.queryForObject("TAOBAO_TRAVEL_COMBO_TYPE.countByMap", params);
	}
	
	public int updateTaobaoTravelComboType(TaobaoTravelComboType taobaoTravelComboType) {
		return super.update("TAOBAO_TRAVEL_COMBO_TYPE.update", taobaoTravelComboType);
	}
	
	public int updateTaobaoTravelComboTypeByMap(Map<String, Object> params) {
		return super.update("TAOBAO_TRAVEL_COMBO_TYPE.updateByMap", params);
	}
	
	public int deleteTaobaoTravelComboType(Long id) {
		return super.delete("TAOBAO_TRAVEL_COMBO_TYPE.delete", id);
	}
	
	public int deleteTaobaoTravelComboTypeByMap(Map<String, Object> params) {
		return super.delete("TAOBAO_TRAVEL_COMBO_TYPE.deleteByMap", params);
	}
	
	public int deleteTaobaoTravelComboTypeByTravelComboId(Long travelComboId) {
		return super.delete("TAOBAO_TRAVEL_COMBO_TYPE.deleteByTravelComboId", travelComboId);
	}
	
	public Long selectSeq() {
		return (Long) super.queryForObject("TAOBAO_TRAVEL_COMBO_TYPE.selectSeq");
	}
}
