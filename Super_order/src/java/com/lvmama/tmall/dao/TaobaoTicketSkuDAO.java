package com.lvmama.tmall.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.tmall.TaobaoTicketSku;

public class TaobaoTicketSkuDAO extends BaseIbatisDAO {
	public TaobaoTicketSkuDAO() {
		super();
	}
	
	public void insertTaobaoTicketSku(TaobaoTicketSku taobaoTicketSku) {
		if (taobaoTicketSku.getId() == null) {
			taobaoTicketSku.setId(selectSeq());
		}
		super.insert("TAOBAO_TICKET_SKU.insert", taobaoTicketSku);
	}

	public int updateTaobaoTicketSku(TaobaoTicketSku taobaoTicketSku) {
		return super.update("TAOBAO_TICKET_SKU.update", taobaoTicketSku);
	}

	public TaobaoTicketSku selectTaobaoTicketSku(Long id) {
		return (TaobaoTicketSku) super.queryForObject("TAOBAO_TICKET_SKU.byId", id);
	}
	
	public List<TaobaoTicketSku> selectTaobaoTicketSku(Map<String, Object> params) {
		return super.queryForList("TAOBAO_TICKET_SKU.byMap", params);
	}

    public Long selectTaobaoTicketSkuToCount(Map<String, Object> params) {
        return (Long) super.queryForObject("TAOBAO_TICKET_SKU.toCountbyMap", params);
    }

	public List<TaobaoTicketSku> selectTaobaoTicketSkuList(Long tbProdSyncId) {
		return (List<TaobaoTicketSku>) super.queryForList("TAOBAO_TICKET_SKU.byTbProdSyncId", tbProdSyncId);
	}
	
	public int deleteTaobaoTicketSku(Long id) {
		return super.delete("TAOBAO_TICKET_SKU.delete", id);
	}
	
	public Long selectSeq() {
		return (Long) super.queryForObject("TAOBAO_TICKET_SKU.selectSeq");
	}

	public int deleteTaobaoTicketSkuByTbProdSyncId(Long tbProdSyncId) {
		return super.delete("TAOBAO_TICKET_SKU.deleteByTbProdSyncId", tbProdSyncId);
	}
}
