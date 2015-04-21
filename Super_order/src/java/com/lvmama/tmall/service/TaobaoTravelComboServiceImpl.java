package com.lvmama.tmall.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.tmall.TaobaoTravelCombo;
import com.lvmama.comm.bee.service.tmall.TaobaoTravelComboService;
import com.lvmama.tmall.dao.TaobaoTravelComboDAO;

public class TaobaoTravelComboServiceImpl implements TaobaoTravelComboService {
	private TaobaoTravelComboDAO taobaoTravelComboDAO;

	@Override
	public void insertTaobaoTravelCombo(TaobaoTravelCombo taobaoTravelCombo) {
		taobaoTravelComboDAO.insertTaobaoTravelCombo(taobaoTravelCombo);
	}

	@Override
	public int updateTaobaoTravelCombo(TaobaoTravelCombo taobaoTravelCombo) {
		return taobaoTravelComboDAO.updateTaobaoTravelCombo(taobaoTravelCombo);
	}

	@Override
	public TaobaoTravelCombo getTaobaoTravelCombo(Long id) {
		return taobaoTravelComboDAO.selectTaobaoTravelCombo(id);
	}
	
	@Override
	public List<TaobaoTravelCombo> getTaobaoTravelCombo(Map<String, Object> params) {
		return taobaoTravelComboDAO.selectTaobaoTravelCombo(params);
	}

	@Override
	public List<TaobaoTravelCombo> getTaobaoTravelComboList(Long tbProdSyncId) {
		return taobaoTravelComboDAO.selectTaobaoTravelComboList(tbProdSyncId);
	}
	
	public Long getSeq() {
		return taobaoTravelComboDAO.selectSeq();
	}

	public TaobaoTravelComboDAO getTaobaoTravelComboDAO() {
		return taobaoTravelComboDAO;
	}

	public void setTaobaoTravelComboDAO(TaobaoTravelComboDAO taobaoTravelComboDAO) {
		this.taobaoTravelComboDAO = taobaoTravelComboDAO;
	}
	
	@Override
	public int deleteTaobaoTravelCombo(Long id) {
		return taobaoTravelComboDAO.deleteTaobaoTravelCombo(id);
	}
}
