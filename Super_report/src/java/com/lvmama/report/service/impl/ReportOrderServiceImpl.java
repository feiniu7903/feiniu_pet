package com.lvmama.report.service.impl;

import java.util.Date;
import java.util.List;

import com.lvmama.report.dao.ReportOrderDAO;
import com.lvmama.report.service.ReportOrderService;

public class ReportOrderServiceImpl implements ReportOrderService {

	private ReportOrderDAO reportOrderDAO;
	
	@Override
	public int deleteAfterSpecTime(Date theTime) {
		return reportOrderDAO.deleteAfterSpecTime(theTime);
	}

	@Override
	public List<Long> selectForNew(Date theTime) {
		return reportOrderDAO.selectForNew(theTime);
	}

	@Override
	public List<Long> selectForOld(Date theTime) {
		return reportOrderDAO.selectForOld(theTime);
	}

	@Override
	public void updateSpecOrder(Long orderId) {
		reportOrderDAO.clearSpecOrder(orderId);
		reportOrderDAO.addSpecOrder(orderId);
	}

	@Override
	public void addSpecOrder(Long orderId) {
		reportOrderDAO.addSpecOrder(orderId);
	}
	
	@Override
	public List<Long> getHistoryOrderId(Date startDate, Date endDate){
		return reportOrderDAO.getHistoryOrderId(startDate,endDate);
	}

	@Override
	public void updateSpecOrderByOrderId(Long rakeBackRate, Long orderId) {
		reportOrderDAO.updateRakeBack(rakeBackRate, orderId);
	}
	
	public void setReportOrderDAO(ReportOrderDAO reportOrderDAO) {
		this.reportOrderDAO = reportOrderDAO;
	}

}
