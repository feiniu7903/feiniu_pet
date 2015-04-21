package com.lvmama.order.dao;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaAperiodic;

public interface OrderItemMetaAperiodicDAO {
	
	int updateStatusByPrimaryKey(final OrdOrderItemMetaAperiodic ordAperiodic);

	Long insert(final OrdOrderItemMetaAperiodic record);
	
	OrdOrderItemMetaAperiodic selectOrderAperiodicByOrderItemId(Long orderItemId);
	
	boolean isPasswordCertificateExisted(String code);
	
	List<OrdOrderItemMetaAperiodic> selectOrderAperiodicByOrderId(Long orderId);
	
	/**
	 * 取最大结束日期,主要是针对一条凭证的多个凭证子项进行激活
	 * */
	Date getMaxValidEndTime(List<Long> orderItemMetaIds);
}
