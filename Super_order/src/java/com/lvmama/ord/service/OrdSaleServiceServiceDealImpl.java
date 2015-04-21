package com.lvmama.ord.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdSaleServiceDeal;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceServiceDeal;
import com.lvmama.ord.dao.OrdSaleServiceDealDAO;

public class OrdSaleServiceServiceDealImpl implements OrdSaleServiceServiceDeal {

	private OrdSaleServiceDealDAO ordSaleServiceDealDao;

	public Long addOrdSaleServiceDeal(OrdSaleServiceDeal ordSaleServiceDeal) {
		return ordSaleServiceDealDao.addOrdSaleServiceDeal(ordSaleServiceDeal);
	}

	public List<OrdSaleServiceDeal> getOrdSaleServiceAllByParam(Map params) {
		return ordSaleServiceDealDao.getOrdSaleServiceAllByParam(params);
	}

	public OrdSaleServiceDealDAO getOrdSaleServiceDealDao() {
		return ordSaleServiceDealDao;
	}

	public void setOrdSaleServiceDealDao(
			OrdSaleServiceDealDAO ordSaleServiceDealDao) {
		this.ordSaleServiceDealDao = ordSaleServiceDealDao;
	}

}
