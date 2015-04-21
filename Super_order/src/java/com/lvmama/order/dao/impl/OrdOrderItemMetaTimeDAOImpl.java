package com.lvmama.order.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaTime;
import com.lvmama.order.dao.OrdOrderItemMetaTimeDAO;

public class OrdOrderItemMetaTimeDAOImpl extends BaseIbatisDAO implements OrdOrderItemMetaTimeDAO {

    public OrdOrderItemMetaTimeDAOImpl() {
        super();
    }

    public int deleteByPrimaryKey(BigDecimal itemMetaTimeId) {
        OrdOrderItemMetaTime key = new OrdOrderItemMetaTime();
        key.setItemMetaTimeId(itemMetaTimeId);
        int rows = super.delete("ORD_ORDER_ITEM_META_TIME.deleteByPrimaryKey", key);
        return rows;
    }

    public BigDecimal insert(OrdOrderItemMetaTime record) {
        Object newKey = super.insert("ORD_ORDER_ITEM_META_TIME.insert", record);
        return (BigDecimal) newKey;
    }

    public BigDecimal insertSelective(OrdOrderItemMetaTime record) {
        Object newKey = super.insert("ORD_ORDER_ITEM_META_TIME.insertSelective", record);
        return (BigDecimal) newKey;
    }

    public OrdOrderItemMetaTime selectByPrimaryKey(BigDecimal itemMetaTimeId) {
        OrdOrderItemMetaTime key = new OrdOrderItemMetaTime();
        key.setItemMetaTimeId(itemMetaTimeId);
        OrdOrderItemMetaTime record = (OrdOrderItemMetaTime) super.queryForObject("ORD_ORDER_ITEM_META_TIME.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(OrdOrderItemMetaTime record) {
        int rows = super.update("ORD_ORDER_ITEM_META_TIME.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(OrdOrderItemMetaTime record) {
        int rows = super.update("ORD_ORDER_ITEM_META_TIME.updateByPrimaryKey", record);
        return rows;
    }

	@Override
	public long selectCountByOrderMeta(final Long orderItemMetaId) {
		OrdOrderItemMetaTime record=new OrdOrderItemMetaTime();
		record.setOrderItemMetaId(orderItemMetaId);		
		return (Long)super.queryForObject("ORD_ORDER_ITEM_META_TIME.selectCountByOrderMeta",record);
	}

	@Override
	public void deleteAllByOrderMetaId(Long orderItemMetaId) {
		OrdOrderItemMetaTime record=new OrdOrderItemMetaTime();
		record.setOrderItemMetaId(orderItemMetaId);	
		super.delete("ORD_ORDER_ITEM_META_TIME.deleteAllByOrderMetaId",record);
	}

	@Override
	public List<OrdOrderItemMetaTime> selectAllByOrderMetaId(
			Long orderItemMetaId) {
		OrdOrderItemMetaTime record=new OrdOrderItemMetaTime();
		record.setOrderItemMetaId(orderItemMetaId);		
		return super.queryForList("ORD_ORDER_ITEM_META_TIME.selectAllByOrderMetaId",record);
	}
}