package com.lvmama.report.service;
/**
 * 库存不足查询
 * @author shangzhengyuan
 * @createDate 2012-04-19
 */
import java.util.List;
import java.util.Map;


public interface StockInsufficientService {
	/**
	 * 库存不足查询
	 * @author shangzhengyuan
	 * @createDate 2012-04-19
	 * @param parameters
	 * @return
	 */
	public List<Map<String,Object>> query(Map<String,Object> parameters);
	/**
	 * 产品售卖日期临近过期时系统邮件提醒功能
	 * @param parameters
	 * @return
	 */
	public List<Map<String,Object>> productSaleRemindList();
	
}
