package com.lvmama.tmall.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.tmall.TaobaoProductSync;
import com.lvmama.comm.bee.po.tmall.TaobaoProductSyncPojo;

public class TaobaoProductSyncDAO extends BaseIbatisDAO {
	public TaobaoProductSyncDAO() {
		super();
	}
	
	public void insertTaobaoProductSync(TaobaoProductSync taobaoProductSync) {
		if (taobaoProductSync.getId() == null) {
			taobaoProductSync.setId(selectSeq());
		}
		super.insert("TAOBAO_PRODUCT_SYNC.insert", taobaoProductSync);
	}
	
	public int updateTaobaoProductSync(TaobaoProductSync taobaoProductSync) {
		return super.update("TAOBAO_PRODUCT_SYNC.update", taobaoProductSync);
	}
	
	public int updateAuctionStatus(TaobaoProductSync taobaoProductSync) {
		return super.update("TAOBAO_PRODUCT_SYNC.updateAuctionStatusById", taobaoProductSync);
	}
	
	public int deleteTaobaoProductSync(Long id) {
		return super.update("TAOBAO_PRODUCT_SYNC.delete", id);
	}
	
	public Long selectCountByItemId(Long itemId) {
		return (Long) super.queryForObject("TAOBAO_PRODUCT_SYNC.countByItemId", itemId);
	}
	
	public List<TaobaoProductSync> selectTaobaoProductSyncByItemId(Long itemId) {
		return super.queryForList("TAOBAO_PRODUCT_SYNC.byItemId", itemId);
	}
	
	public TaobaoProductSync selectTaobaoProductSync(Long id) {
		return (TaobaoProductSync) super.queryForObject("TAOBAO_PRODUCT_SYNC.byId", id);
	}
	
	public List<TaobaoProductSync> selectTaobaoProductSync(Map<String, Object> params) {
		return super.queryForList("TAOBAO_PRODUCT_SYNC.byMap", params);
	}
	
	public Integer selectTaobaoTicketSyncCount(Map<String, Object> pageMap) {
		return (Integer) super.queryForObject("TAOBAO_PRODUCT_SYNC.queryTicketSyncCount", pageMap);
	}
	
	public List<TaobaoProductSyncPojo> selectTaobaoTicketSyncList(
			Map<String, Object> pageMap) {
		return super.queryForList("TAOBAO_PRODUCT_SYNC.queryTicketSyncList", pageMap);
	}
	
	public Integer selectTaobaoTravelSyncCount(Map<String, Object> pageMap) {
		return (Integer) super.queryForObject("TAOBAO_PRODUCT_SYNC.queryTravelSyncCount", pageMap);
	}
	
	public List<TaobaoProductSyncPojo> selectTaobaoTravelSyncList(Map<String, Object> pageMap) {
		return super.queryForList("TAOBAO_PRODUCT_SYNC.queryTravelSyncList", pageMap);
	}
	
	public Long selectSeq() {
		return (Long) super.queryForObject("TAOBAO_PRODUCT_SYNC.selectSeq");
	}

    public List<Long> queryTicketSkuId(Map<String, Object> params) {
        return super.queryForList("TAOBAO_PRODUCT_SYNC.queryTicketSkuId", params);
    }

	public List<TaobaoProductSyncPojo> selectTravelList(Map<String, Object> params) {
		return super.queryForList("TAOBAO_PRODUCT_SYNC.queryTravel", params);
	}
	
	public List<Long> selectTravelToTravelComboId(Map<String, Object> params) {
		return super.queryForList("TAOBAO_PRODUCT_SYNC.queryTravelToTravelComboId", params);
	}
	
	
	public Long selectTaobaoProductSyncCountByMap(Map<String, Object> params) {
		return (Long) super.queryForObject("TAOBAO_PRODUCT_SYNC.countByMap", params);
	}

	public List<Long> selectTaobaoProductItemIdList(Map<String, Object> params) {
		return super.queryForList("TAOBAO_PRODUCT_SYNC.queryProductItemIdList", params);
	}
}
