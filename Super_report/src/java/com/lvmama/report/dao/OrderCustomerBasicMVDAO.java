package com.lvmama.report.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.report.po.OrderCustomerBasicMV;

public class OrderCustomerBasicMVDAO extends BaseIbatisDAO {
	
	@SuppressWarnings("unchecked")
	public List<OrderCustomerBasicMV> queryOrderCustomerBasicMVByTime(Map param,boolean isForReportExport){
		List<OrderCustomerBasicMV> result = new ArrayList<OrderCustomerBasicMV>();
		result = super.queryForList("ORDER_CUSTOMER_BASIC_MV.queryOrderCustomerBasicMVByTime", param,isForReportExport);
		return result;
	}
	
	public Long countOrderCustomerBasicMVByTime(Map param){
		return (Long)super.queryForObject("ORDER_CUSTOMER_BASIC_MV.countOrderCustomerBasicMVByTime", param);
	}
	
	public Long sumAmountOrderCustomerBasicMV(Map param){
		return (Long)super.queryForObject("ORDER_CUSTOMER_BASIC_MV.sumAmountOrderCustomerBasicMV", param);
	}
	
	public Long sumProfitOrderCustomerBasicMV(Map param){
		return (Long)super.queryForObject("ORDER_CUSTOMER_BASIC_MV.sumProfitOrderCustomerBasicMV", param);
	}
}
