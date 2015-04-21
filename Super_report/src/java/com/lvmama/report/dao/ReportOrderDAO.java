package com.lvmama.report.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;

public class ReportOrderDAO extends BaseIbatisDAO {

	public int deleteAfterSpecTime(Date theTime) {
		return super.delete("ORDER_DETAIL_TV.deleteAfterSpecTime", theTime);
	}
	
	public List<Long> selectForNew(Date theTime){
		return super.queryForListForReport("ORDER_DETAIL_TV.selectForNew", theTime);
	}

	public List<Long> selectForOld(Date theTime){
		return super.queryForListForReport("ORDER_DETAIL_TV.selectForOld", theTime);
	}

	public void clearSpecOrder(Long orderId) {
		super.delete("ORDER_DETAIL_TV.deleteByOrderId", orderId);
	}

	public void addSpecOrder(Long orderId) {
		super.insert("ORDER_DETAIL_TV.insert", orderId);
	}
	
	public List<Long> getHistoryOrderId(Date startDate, Date endDate){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return super.queryForListForReport("ORDER_DETAIL_TV.getHistoryOrderId",map);
	}
	
	public void updateRakeBack(Long rakeBackRate, Long orderId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("rakeBackRate", rakeBackRate);
		params.put("orderId", orderId);
		super.update("ORDER_DETAIL_TV.updateRakeBack", params);
	}
}
