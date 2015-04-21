package com.lvmama.client.service;

import java.util.List;

import com.lvmama.client.dao.ClientOrderReportDAO;
import com.lvmama.client.dao.ComClientLogDAO;
import com.lvmama.comm.bee.po.client.ComClientLog;
import com.lvmama.comm.pet.po.client.ClientOrderReport;
import com.lvmama.comm.pet.service.client.ComClientService;

public class ComClientServiceImpl implements ComClientService {

	private ClientOrderReportDAO clientOrderReportDAO;

	private ComClientLogDAO comClientLogDAO;

	@Override
	public Long countUdidOrder(String udid) {
		return clientOrderReportDAO.countUdidOrder(udid);
	}
	
	public List<ClientOrderReport> getTodayOrderByUdid(String udid) {
		return clientOrderReportDAO.getTodayOrderByUdid(udid);
	}

	@Override
	public Long insert(ComClientLog record) {
		return comClientLogDAO.insert(record);
	}

	public void setClientOrderReportDAO(ClientOrderReportDAO clientOrderReportDAO) {
		this.clientOrderReportDAO = clientOrderReportDAO;
	}

	public void setComClientLogDAO(ComClientLogDAO comClientLogDAO) {
		this.comClientLogDAO = comClientLogDAO;
	}
	
	
}
