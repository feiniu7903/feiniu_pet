package com.lvmama.prd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.prod.ProceedTours;
import com.lvmama.comm.bee.service.ProceedToursService;
import com.lvmama.prd.dao.ProceedToursDAO;

public class ProceedToursServiceImpl implements ProceedToursService {
	private final static Log LOG = LogFactory.getLog(ProceedToursServiceImpl.class);
	
	private ProceedToursDAO proceedToursDAO;
	
	@Override
	public void addition(ProceedTours pt) {
		if (hasProceedTours(pt)) {
			LOG.debug("productId:" + pt.getProductId() + " visitDate:" + pt.getVisitDate() + "已经存在，直接更新!");
			proceedToursDAO.additionVisitorsAndUpdate(pt);
		} else {
			LOG.debug("productId:" + pt.getProductId() + " visitDate:" + pt.getVisitDate() + "不存在，需要插入!");
			proceedToursDAO.insert(pt);
		}
	}

	@Override
	public void subtraction(ProceedTours pt) {
		proceedToursDAO.subtractionVisitorsAndUpdate(pt);

	}
	
	@Override
	public void updateStatus(ProceedTours pt) {
		proceedToursDAO.updateStatus(pt);
	}
	
	@Override
	public Long countProceedTours(Map<String, Object> parameters) {
		return proceedToursDAO.count(parameters);
		
	}

	@Override
	public List<ProceedTours> query(Map<String, Object> parameters) {
		return proceedToursDAO.query(parameters);
	}
	
	private boolean hasProceedTours(ProceedTours pt) {
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("productId", pt.getProductId());
		parameters.put("visitDate", pt.getVisitDate());
		List<ProceedTours> pts = proceedToursDAO.query(parameters);
		return !pts.isEmpty();
	}

	//setter and getter
	public ProceedToursDAO getProceedToursDAO() {
		return proceedToursDAO;
	}

	public void setProceedToursDAO(ProceedToursDAO proceedToursDAO) {
		this.proceedToursDAO = proceedToursDAO;
	}
	
}
