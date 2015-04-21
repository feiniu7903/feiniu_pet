package com.lvmama.order.dao;

import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrderItemProdTime;

public interface OrdOrderItemProdTimeDAO {
	 public Long insert(OrdOrderItemProdTime record);
	 public List<OrdOrderItemProdTime> selectProdTimeByProdItemId(Long itemId);
}
