package com.lvmama.comm.pet.service.sale;


import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdSaleServiceDeal;

public interface OrdSaleServiceServiceDeal {
	public List<OrdSaleServiceDeal> getOrdSaleServiceAllByParam(Map params) ;
	Long addOrdSaleServiceDeal(OrdSaleServiceDeal ordSaleServiceDeal);
}
