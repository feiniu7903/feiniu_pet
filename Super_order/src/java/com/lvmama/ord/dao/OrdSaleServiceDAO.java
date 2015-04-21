package com.lvmama.ord.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdSaleService;

@SuppressWarnings("unchecked")
public class OrdSaleServiceDAO extends BaseIbatisDAO {
	public void deleteOrdSaleService(String ordSaleId){
		Map map=new HashMap();
		map.put("saleServiceId",ordSaleId);
		super.delete("ORD_SALE_SERVICE.delete",map);	
	}
	public boolean findOrderItemIsRefund(Long orderItemMetaId){
		boolean isRefund=false;
		List<OrdSaleService> ret =new ArrayList();
		Map map=new HashMap();
		map.put("orderItemMetaId", orderItemMetaId);
		ret=super.queryForList("ORD_REFUNDMENT_ITEM.select",map);
		if(ret.size()>0){
			isRefund=true;
		}
		return isRefund;
	}
	public List<OrdSaleService> getOrdSaleServiceAllByParam(Map params) {
		List<OrdSaleService> ret = null;
		ret = super.queryForList(
				"ORD_SALE_SERVICE.selectSaleFull", params);
		return ret;
	}

	public Long addOrdSaleService(OrdSaleService ordSaleService) {
		Object newKey = super.insert(
				"ORD_SALE_SERVICE.insert", ordSaleService);
		return (Long) newKey;
	}

	public OrdSaleService getOrdSaleServiceByPk(Long ordSaleId) {
		OrdSaleService ordsaleservice = new OrdSaleService();
		ordsaleservice.setSaleServiceId(ordSaleId);
		OrdSaleService record = (OrdSaleService) super
				.queryForObject("ORD_SALE_SERVICE.selectByPrimaryKey",
						ordsaleservice);
		return record;
	}

	public boolean updateOrdSaleService(OrdSaleService ordSaleService) {
		int row = super.update("ORD_SALE_SERVICE.updateByPrimaryKey", ordSaleService);
		if(row == 1){
			return true;
		}else{
			return false;
		}
	}

	public int takeOrdSaleServiceByIds(Map<String, Object> params) {
		return super.update("ORD_SALE_SERVICE.takeOrdSaleServiceByIds", params);
	}
}