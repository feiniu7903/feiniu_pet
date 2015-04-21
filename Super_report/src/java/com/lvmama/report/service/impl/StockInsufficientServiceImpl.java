package com.lvmama.report.service.impl;
/**
 * 库存不足查询
 * @author shangzhengyuan
 * @createDate 2012-04-19
 */
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.report.dao.StockInsufficientDAO;
import com.lvmama.report.service.StockInsufficientService;

public class StockInsufficientServiceImpl implements StockInsufficientService {
	private StockInsufficientDAO stockInsufficientDAO;
	private MetaProductService metaProductService;
	/**
	 * 库存不足查询
	 * @author shangzhengyuan
	 * @createDate 2012-04-19
	 * @param parameters
	 * @return
	 */
	@Override
	public List<Map<String, Object>> query(Map<String, Object> parameters) {
		return stockInsufficientDAO.query(parameters);
	}
	/**
	 * 产品售卖日期临近过期时系统邮件提醒功能
	 * @author shangzhengyuan
	 * @createDate 2012-09-18,9.18 记住这个日子，中国加油!
	 * @param parameters
	 * @return
	 */
	@Override
	public List<Map<String,Object>> productSaleRemindList(){
		return stockInsufficientDAO.productSaleRemindList();
	}
	public StockInsufficientDAO getStockInsufficientDAO() {
		return stockInsufficientDAO;
	}
	public MetaProductService getMetaProductService() {
		return metaProductService;
	}
	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}
	public void setStockInsufficientDAO(StockInsufficientDAO stockInsufficientDAO) {
		this.stockInsufficientDAO = stockInsufficientDAO;
	}

}
