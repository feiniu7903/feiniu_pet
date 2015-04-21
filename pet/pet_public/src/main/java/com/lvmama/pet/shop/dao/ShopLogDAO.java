package com.lvmama.pet.shop.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.shop.ShopCustomerPresentPoint;
import com.lvmama.comm.pet.po.shop.ShopLog;

/**
 * 积分商城日志的数据库实现类
 * @author Brian
 *
 */
public class ShopLogDAO  extends BaseIbatisDAO {

	/**
	 * 插入日志
	 * @param shopLog 日志
	 * @return 日志ID
	 */
	public Long insert(final ShopLog shopLog) {
		 super.insert("SHOP_LOG.insert", shopLog);
		return shopLog.getLogId();
	}


	/**
	 * 查询日志
	 * @param parametes 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ShopLog> query(final Map<String, Object> parametes) {
		return super.queryForList("SHOP_LOG.query", parametes);
	}
	
	/**
	 * 保存客服发放积分的信息
	 * @param scpp 客服发放积分的信息
	 */
	public void savePutPoint(final ShopCustomerPresentPoint scpp) {
		super.insert("SHOP_LOG.savePutPoint", scpp);
	}

}
