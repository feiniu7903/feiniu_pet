package com.lvmama.report.dao;
/**
 * 库存不足查询
 * @author shangzhengyuan
 * @createDate 2012-04-19
 */
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;

public class StockInsufficientDAO  extends BaseIbatisDAO {
	/**
	 * 库存不足查询
	 * @author shangzhengyuan
	 * @createDate 2012-04-19
	 * @param parameters
	 * @return
	 */
	public List<Map<String,Object>> query(Map<String,Object> parameters){
		List<Map<String,Object>> result = super.queryForListForReport("STOCK_INSUFFICIENT.query", parameters);
		return result;
	}
	
	/**
	 * 标记已发送过邮件
	 * @author haofeifei
	 * @createDate 2014-01-17
	 * @param parameters
	 * @return
	 */
	public void signSendEmail(Map<String,Object> parameters){
		  super.update("STOCK_INSUFFICIENT.signIsSendEmail", parameters);
	}
	/**
	 * 产品售卖日期临近过期时系统邮件提醒功能
	 * @param parameters
	 * @return
	 */
	public List<Map<String,Object>> productSaleRemindList(){
		List<Map<String,Object>> result = super.queryForListForReport("STOCK_INSUFFICIENT.productSaleRemindList");
		return result;
	}
}
