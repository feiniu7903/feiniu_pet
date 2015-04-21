package com.lvmama.order.service.impl;

import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrderBlack;
import com.lvmama.comm.bee.service.ord.OrdOrderBlackService;
import com.lvmama.order.dao.OrdOrderBlackDao;

public class OrdOrderBlackServiceImpl implements OrdOrderBlackService{
	
	private OrdOrderBlackDao ordOrderBlackDao;
	
	@Override
	public void insertOrdOrderBlack(OrdOrderBlack ordOrderBlack) {
		ordOrderBlackDao.insertOrdOrderBlack(ordOrderBlack);
	}

	@Override
	public int queryOrderBlackByParam(Map<String, Object> param) {
		return ordOrderBlackDao.queryOrderBlackByParam(param);
	}

	public void setOrdOrderBlackDao(OrdOrderBlackDao ordOrderBlackDao) {
		this.ordOrderBlackDao = ordOrderBlackDao;
	}
}
