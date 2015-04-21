package com.lvmama.report.service;

import java.util.Date;
import java.util.List;

public interface ReportOrderService {

	int deleteAfterSpecTime(Date theDate);
	
	List<Long> selectForNew(Date theTime);

	List<Long> selectForOld(Date theTime);

	void updateSpecOrder(Long orderId);

	void addSpecOrder(Long orderId);
	
	List<Long> getHistoryOrderId(Date startDate, Date endDate);
	
	void updateSpecOrderByOrderId(Long rakeBackRate, Long orderId);

}