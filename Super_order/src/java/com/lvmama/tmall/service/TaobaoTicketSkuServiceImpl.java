package com.lvmama.tmall.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.tmall.TaobaoTicketSku;
import com.lvmama.comm.bee.service.tmall.TaobaoTicketSkuService;
import com.lvmama.tmall.dao.TaobaoTicketSkuDAO;

public class TaobaoTicketSkuServiceImpl implements TaobaoTicketSkuService {
	private TaobaoTicketSkuDAO taobaoTicketSkuDAO;

	@Override
	public void insertTaobaoTicketSku(TaobaoTicketSku taobaoTicketSku) {
		taobaoTicketSkuDAO.insertTaobaoTicketSku(taobaoTicketSku);
	}

	@Override
	public int updateTaobaoTicketSku(TaobaoTicketSku taobaoTicketSku) {
		return taobaoTicketSkuDAO.updateTaobaoTicketSku(taobaoTicketSku);
	}

	@Override
	public TaobaoTicketSku getTaobaoTicketSku(Long id) {
		return taobaoTicketSkuDAO.selectTaobaoTicketSku(id);
	}
	
	@Override
	public List<TaobaoTicketSku> getTaobaoTicketSku(Map<String, Object> prams) {
		return taobaoTicketSkuDAO.selectTaobaoTicketSku(prams);
	}

	@Override
	public List<TaobaoTicketSku> getTaobaoTicketSkuList(Long tbProdSyncId) {
		return taobaoTicketSkuDAO.selectTaobaoTicketSkuList(tbProdSyncId);
	}
	
	@Override
	public Long getSeq() {
		return taobaoTicketSkuDAO.selectSeq();
	}
	
	public TaobaoTicketSkuDAO getTaobaoTicketSkuDAO() {
		return taobaoTicketSkuDAO;
	}

	public void setTaobaoTicketSkuDAO(TaobaoTicketSkuDAO taobaoTicketSkuDAO) {
		this.taobaoTicketSkuDAO = taobaoTicketSkuDAO;
	}

	@Override
	public int deleteTaobaoTicketSku(Long id) {
		return taobaoTicketSkuDAO.deleteTaobaoTicketSku(id);
	}
}
