package com.lvmama.report.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.report.po.OrderSaleServiceBasicMV;

public class OrderSaleServiceBasicMVDAO extends BaseIbatisDAO {
	
	public List<OrderSaleServiceBasicMV> queryOrderSaleServiceBasicMV(Map param,boolean isForReportExport){
		List<OrderSaleServiceBasicMV> result = new ArrayList<OrderSaleServiceBasicMV>();
		result = super.queryForList("ORDER_SALE_SERVICE_BASIC_MV.queryOrderSaleServiceBasicMV", param,isForReportExport);
		return result;
	}
	
	public Long countOrderSaleServiceBasicMV(Map param){
		return (Long)super.queryForObject("ORDER_SALE_SERVICE_BASIC_MV.countOrderSaleServiceBasicMV", param);
	}
	
	public Long sumRefundAmountOrderSaleServiceBasicMV(Map param){
		return (Long)super.queryForObject("ORDER_SALE_SERVICE_BASIC_MV.sumRefundAmountOrderSaleServiceBasicMV", param);
	}
	
	public Long sumCompensationAmountOrderSaleServiceBasicMV(Map param){
		return (Long)super.queryForObject("ORDER_SALE_SERVICE_BASIC_MV.sumCompensationAmountOrderSaleServiceBasicMV", param);
	}

}
