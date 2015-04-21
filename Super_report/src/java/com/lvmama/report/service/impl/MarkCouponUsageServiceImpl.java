package com.lvmama.report.service.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.report.dao.MarkCouponUsageDAO;
import com.lvmama.report.po.CouponUsegeModel;
import com.lvmama.report.service.MarkCouponUsageService;

public class MarkCouponUsageServiceImpl implements MarkCouponUsageService {
	private MarkCouponUsageDAO markCouponUsageDAO;
	public List<CouponUsegeModel> query(Map<String, Object> parameters) {
		return markCouponUsageDAO.query(parameters);
	}
	public MarkCouponUsageDAO getMarkCouponUsageDAO() {
		return markCouponUsageDAO;
	}
	public void setMarkCouponUsageDAO(MarkCouponUsageDAO markCouponUsageDAO) {
		this.markCouponUsageDAO = markCouponUsageDAO;
	}

}
