package com.lvmama.pet.shop.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.shop.ShopCustomerPresentPoint;
import com.lvmama.comm.pet.po.shop.ShopLog;
import com.lvmama.comm.pet.service.shop.ShopLogService;
import com.lvmama.pet.shop.dao.ShopLogDAO;


/**
 * 积分商城日志逻辑实现层
 * @author Brian
 *
 */
class ShopLogServiceImpl implements ShopLogService {
	private static final int MAX_LOG_CONTENT = 2000;
	/**
	 * 商城日志的数据库接口
	 */
	private ShopLogDAO shopLogDAO;

	@Override
	public void insert(final String content, final Long objectId, final String objectType,
			final String logType, final String operatorId) {
		ShopLog log = new ShopLog();
		if (null != content && content.length() >= MAX_LOG_CONTENT ) {
			log.setContent(content.substring(0, MAX_LOG_CONTENT - "......".length()) + "....");
		} else {
			log.setContent(content);
		}
		log.setObjectId(objectId);
		log.setObjectType(objectType);
		log.setLogType(logType);
		log.setOperatorId(operatorId);
		shopLogDAO.insert(log);
	}

	@Override
	public List<ShopLog> query(final Map<String, Object> parametes) {
		return shopLogDAO.query(parametes);
	}
	
	@Override
	public void savePutPoint(final Map<String, Object> info)
	{
		if (null == info || null == info.get("userId") || null == info.get("point")) {
			return;
		}
		ShopCustomerPresentPoint scpp = new ShopCustomerPresentPoint();
		scpp.setCreateDate(new Date(System.currentTimeMillis()));
		scpp.setCsName((String) info.get("csName"));
		scpp.setMemo((String) info.get("memo"));
		scpp.setOrderId((Long) info.get("orderId"));
		scpp.setPutPoint(Long.parseLong((String) info.get("point")));
		scpp.setPutThing((String) info.get("putThings"));
		scpp.setUserId((Long) info.get("userId"));
		scpp.setUserName((String) info.get("userName"));
		shopLogDAO.savePutPoint(scpp);
	}
	
	public void setShopLogDAO(final ShopLogDAO shopLogDAO) {
		this.shopLogDAO = shopLogDAO;
	}	

}
