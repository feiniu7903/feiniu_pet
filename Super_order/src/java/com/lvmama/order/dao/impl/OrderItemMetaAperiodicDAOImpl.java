package com.lvmama.order.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaAperiodic;
import com.lvmama.order.dao.OrderItemMetaAperiodicDAO;

public class OrderItemMetaAperiodicDAOImpl extends BaseIbatisDAO  implements OrderItemMetaAperiodicDAO{
	
	@Override
	public OrdOrderItemMetaAperiodic selectOrderAperiodicByOrderItemId(Long orderItemId) {
		OrdOrderItemMetaAperiodic ordAperiodic = (OrdOrderItemMetaAperiodic) super.queryForObject(
				"ORD_ORDER_ITEM_META_APERIODIC.selectOrderAperiodicByOrderItemId", orderItemId);
		return ordAperiodic;
	}

	@Override
	public int updateStatusByPrimaryKey(OrdOrderItemMetaAperiodic ordAperiodic) {
		 int rows = super.update("ORD_ORDER_ITEM_META_APERIODIC.updateStatusByPrimaryKey", ordAperiodic);
	        return rows;
	}

	@Override
	public Long insert(final OrdOrderItemMetaAperiodic record) {
		Object newKey = super.insert(
				"ORD_ORDER_ITEM_META_APERIODIC.insert", record);
		return (Long) newKey;
	}

	public boolean isPasswordCertificateExisted(String code) {
		Long size = (Long) super.queryForObject("ORD_ORDER_ITEM_META_APERIODIC.isPasswordCertificateExisted", code);
		if(size > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<OrdOrderItemMetaAperiodic> selectOrderAperiodicByOrderId(Long orderId) {
		return super.queryForList(
				"ORD_ORDER_ITEM_META_APERIODIC.selectOrderAperiodicByOrderId", orderId);
	}

	@Override
	public Date getMaxValidEndTime(List<Long> orderItemMetaIds) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("orderItemMetaIds", orderItemMetaIds);
		Object d = super.queryForObject("ORD_ORDER_ITEM_META_APERIODIC.getMaxValidEndTime", map);
		if(d == null) {
			return null;
		}
		return (Date) d;
	}
}