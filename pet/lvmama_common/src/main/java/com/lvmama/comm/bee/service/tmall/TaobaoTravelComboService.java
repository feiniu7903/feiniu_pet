package com.lvmama.comm.bee.service.tmall;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.tmall.TaobaoTravelCombo;

public interface TaobaoTravelComboService {
	public void insertTaobaoTravelCombo(TaobaoTravelCombo taobaoTravelCombo);
	public int updateTaobaoTravelCombo(TaobaoTravelCombo taobaoTravelCombo);
	public int deleteTaobaoTravelCombo(Long id);
	public List<TaobaoTravelCombo> getTaobaoTravelCombo(Map<String, Object> params);
	public TaobaoTravelCombo getTaobaoTravelCombo(Long id);
	public List<TaobaoTravelCombo> getTaobaoTravelComboList(Long tbProdSyncId);
	public Long getSeq();
}
