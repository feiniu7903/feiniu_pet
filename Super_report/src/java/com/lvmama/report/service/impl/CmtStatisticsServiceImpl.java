package com.lvmama.report.service.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.report.dao.CmtStatisticsDAO;
import com.lvmama.report.po.CmtStatisticsModel;
import com.lvmama.report.service.CmtStatisticsService;

public class CmtStatisticsServiceImpl implements CmtStatisticsService {

	private CmtStatisticsDAO cmtStatisticsDAO;
	@Override
	public List<CmtStatisticsModel> query(Map<String, Object> param,
			boolean isForReportExport) {
		return cmtStatisticsDAO.query(param,isForReportExport);
	}

	@Override
	public Long count(Map<String, Object> param) {
		return cmtStatisticsDAO.count(param);
	}

	public CmtStatisticsDAO getCmtStatisticsDAO() {
		return cmtStatisticsDAO;
	}

	public void setCmtStatisticsDAO(CmtStatisticsDAO cmtStatisticsDAO) {
		this.cmtStatisticsDAO = cmtStatisticsDAO;
	}

}
