package com.lvmama.ord.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdSaleServiceDeal;

public class OrdSaleServiceDealDAO extends BaseIbatisDAO {
	public List<OrdSaleServiceDeal> getOrdSaleServiceAllByParam(Map params) {
		List<OrdSaleServiceDeal> ret = null;
		ret = super.queryForList(
				"ORD_SALE_SERVICE_DEAL.selectSaleDealFull", params);
		return ret;
	}

	public Long addOrdSaleServiceDeal(OrdSaleServiceDeal ordSaleServiceDeal) {
		Object newKey = super.insert(
				"ORD_SALE_SERVICE_DEAL.insert", ordSaleServiceDeal);
		return (Long) newKey;
	}

}