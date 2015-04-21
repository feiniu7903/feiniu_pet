package com.lvmama.ebk.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.service.ebooking.EbkProductReportService;
import com.lvmama.comm.bee.vo.report.EbkProductVisitorReportVo;
import com.lvmama.ebk.dao.EbkProductReportDAO;

public class EbkProductReportServiceImpl implements EbkProductReportService {

	private EbkProductReportDAO ebkProductReportDAO;
	@Override
	public List<EbkProductVisitorReportVo> findOnSaleProductList(Map<String, Object> param) {
		return ebkProductReportDAO.findOnSaleProductList(param);
	}
	@Override
	public int findOnSaleProductCount(Map<String, Object> param) {
		return ebkProductReportDAO.findOnSaleProductCount(param);
	}

	@Override
	public List<EbkProductVisitorReportVo> findProductSalesList(Map<String, Object> param) {
		return ebkProductReportDAO.findProductSalesList(param);
	}
	@Override
	public int findProductSalesCount(Map<String, Object> param) {
		return ebkProductReportDAO.findProductSalesCount(param);
	}

	@Override
	public List<EbkProductVisitorReportVo> findProductVisitorList(Map<String, Object> param) {
		return ebkProductReportDAO.findProductVisitorList(param);
	}
	@Override
	public int findProductVisitorCount(Map<String, Object> param) {
		return ebkProductReportDAO.findProductVisitorCount(param);
	}

	public EbkProductReportDAO getEbkProductReportDAO() {
		return ebkProductReportDAO;
	}

	public void setEbkProductReportDAO(EbkProductReportDAO ebkProductReportDAO) {
		this.ebkProductReportDAO = ebkProductReportDAO;
	}

}
