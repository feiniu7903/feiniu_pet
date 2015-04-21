package com.lvmama.report.service.impl;

import java.util.Date;
import java.util.List;

import com.lvmama.report.dao.MoneyUserDetailExportDAO;
import com.lvmama.report.po.MoneyUserDetailExportRefund;
import com.lvmama.report.service.MoneyUserDetailExportService;

public class MoneyUserDetailExportServiceImpl implements MoneyUserDetailExportService {

	
	private MoneyUserDetailExportDAO moneyUserDetailExportDAO;

	@Override
	public List payExport(Date visitStartDate, Date visitEndDate, Date moneyChangeStartDate, Date moneyChangeEndDate) {
		return moneyUserDetailExportDAO.payExport(visitStartDate, visitEndDate, moneyChangeStartDate, moneyChangeEndDate);
	}

	@Override
	public List<MoneyUserDetailExportRefund> refundExport(Date visitStartDate, Date visitEndDate, Date moneyChangeStartDate, Date moneyChangeEndDate) {
		   List<MoneyUserDetailExportRefund>  list=  moneyUserDetailExportDAO.refundExport(visitStartDate, visitEndDate, moneyChangeStartDate, moneyChangeEndDate);
		return list;
	}

	public void setMoneyUserDetailExportDAO(MoneyUserDetailExportDAO moneyUserDetailExportDAO) {
		this.moneyUserDetailExportDAO = moneyUserDetailExportDAO;
	}

}
