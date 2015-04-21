package com.lvmama.order.dao;

import java.math.BigDecimal;
import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaTime;

public interface OrdOrderItemMetaTimeDAO {
	public BigDecimal insert(OrdOrderItemMetaTime record);
	public long selectCountByOrderMeta(final Long orderItemMetaId);
	
	public void deleteAllByOrderMetaId(final Long orderItemMetaId);
	
	public List<OrdOrderItemMetaTime> selectAllByOrderMetaId(final Long orderItemMetaId);
}
