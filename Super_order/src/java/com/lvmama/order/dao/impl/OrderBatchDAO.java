package com.lvmama.order.dao.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderBatch;
import com.lvmama.comm.bee.po.ord.OrdOrderBatchOrder;
import com.lvmama.comm.pet.vo.Page;

public final class OrderBatchDAO extends BaseIbatisDAO {
	public Long insert(final OrdOrderBatch record) {
		Object newKey = super.insert("ORDER_BATCH.insert", record);
		return (Long) newKey;
	}

	public Page<OrdOrderBatch> selectByParams(Map<Object,Object> params) {
		return super.queryForPage("ORDER_BATCH.selectByParams",params);
	}

	public void insertBatchOrders(OrdOrderBatchOrder batchOrder){
		super.insert("ORDER_BATCH.insertBatchOrder", batchOrder);
	}
	
	@SuppressWarnings("unchecked")
	public List<OrdOrderBatch> listBatchPassCode(Long batchId){
		return super.queryForList("ORDER_BATCH.listBatchPassCode", batchId);
	}
	
	public Page<OrdOrderBatch> listAbandonOrder(Map<Object,Object> params){
		return super.queryForPage("ORDER_BATCH.listAbandonOrder2", params);
	}

	public List<OrdOrderBatch> queryBatchOrdersForCancel(Map<String, Object> params){
		return super.queryForList("ORDER_BATCH.queryBatchOrdersForCancel", params);
	}
	public Integer getBatchOrderCount(Long batchId) {
		return (Integer) super.queryForObject("ORDER_BATCH.getBatchOrderCount", batchId);
	}

	public Integer getBatchCanceledCount(Long batchId) {
		return (Integer) super.queryForObject("ORDER_BATCH.getBatchCanceledCount", batchId);
	}
	public Integer getBatchUsedCount(Long batchId) {
		return (Integer) super.queryForObject("ORDER_BATCH.getBatchUsedCount", batchId);
	}
	public Integer getBatchPassCodeCount(Long batchId) {
		return (Integer) super.queryForObject("ORDER_BATCH.getBatchPassCodeCount", batchId);
	}
	public Integer getBatchcanCancelCount(Long batchId) {
		return (Integer) super.queryForObject("ORDER_BATCH.getBatchcanCancelCount", batchId);
	}
	public boolean updateBatchStatus(Map<Object,Object> params) {
		int count = super.update("ORDER_BATCH.updateBatchStatus", params);
		return count>0;
	}

	public boolean updateBatchValid(Map<Object,Object> params) {
		int count = super.update("ORDER_BATCH.updateBatchValid", params);
		return count>0;
	}
	public OrdOrderBatch selectByBatchId(Long batchId) {
		return (OrdOrderBatch) super.queryForObject("ORDER_BATCH.selectByBatchId", batchId);
	}

	public List<OrdOrderBatch> selectNeedCreateOrder() {
		return super.queryForList("ORDER_BATCH.selectNeedCreateOrder");
	}
}
