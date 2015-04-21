package com.lvmama.pet.sms.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.sms.SmsContentLog;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.pet.vo.SmsLogStat;

/**
 * 短信发送记录的数据库操作实现类
 * @author Brian
 *
 */
public class SmsContentLogDAO extends BaseIbatisDAO {
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(SmsContentLogDAO.class);

	public SmsContentLog save(final SmsContentLog log) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Want to save " + log);
		}
		super.insert("SMS_CONTENT_LOG.insert", log);
		if (LOG.isDebugEnabled()) {
			LOG.debug("save " + log + " successfully!");
		}
		return log;
	}

	public void saveBatch(final Map<String, Object> parameters) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Want to save,condition: " + parameters);
		}
		super.insert("SMS_CONTENT_LOG.batchCopy", parameters);
	}

	public Object querySmsContentLogByPk(final Long id) {
		return super.queryForObject(
				"SMS_CONTENT_LOG.querySmsContentLogByPk", id);
	}

	@SuppressWarnings({"rawtypes" })
	public List query(final Map<String, Object> parameters) {
		return super.queryForList("SMS_CONTENT_LOG.query", parameters);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page pagedQuery(final Map parameterObject, final int currentPage, final int limit) {
		Assert.isTrue(currentPage >= 0, "pageNo should start from 0");

		// 计算总数
		SmsLogStat sls = (SmsLogStat) queryForObject("SMS_CONTENT_LOG.count", parameterObject);
		Long totalCount = sls.getTotal();
		// 如果没有数据则返回Empty Page
		Assert.notNull(totalCount, "totalCount Error");
		if (totalCount.longValue() == 0) {
			return new Page(0);
		}

		List list;
		long totalPageCount = 0;
		long startIndex = 0;

		// 如果pageSize小于0，则返回所有数据，等同于getAll
		if (limit > 0) {
			// 计算页数
			totalPageCount = totalCount / limit;
			totalPageCount += ((totalCount % limit) > 0) ? 1 : 0;

			// 计算skip数量
			if (currentPage > totalPageCount) {
				startIndex = (totalPageCount - 1) * limit;
			} else {
				startIndex = (currentPage - 1) * limit;
			}

			Map newParameterObject = new HashMap();
			if (null != parameterObject) {
				newParameterObject.putAll(parameterObject);
			}

			newParameterObject.put("startIndex", startIndex);
			newParameterObject.put("limit", limit);

			list = super.queryForList(
					"SMS_CONTENT_LOG.query", newParameterObject);
		} else {
			list = super.queryForList(
					"SMS_CONTENT_LOG.query", parameterObject);
		}
		Page page = new Page(totalCount, limit, 12);
		page.setItems(list);
		return page;
	}

	public void increaseNumbers(final Serializable id) {
		super.update("SMS_CONTENT_LOG.updateNumber", id);
	}

	public void updateReport(final Long serialId, final Integer reportStatus, final Date date, final String memo) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("serialId", serialId);
		parameters.put("reportStatus", reportStatus);
		parameters.put("receiveDate", date);
		parameters.put("memo", memo);
		super.update("SMS_CONTENT_LOG.updateReport", parameters);
	}
	
	public void updateReport(final String beforeMemo, final Integer reportStatus, final Date date, final String memo) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("beforeMemo", beforeMemo);
		parameters.put("reportStatus", reportStatus);
		parameters.put("receiveDate", date);
		parameters.put("memo", memo);
		super.update("SMS_CONTENT_LOG.updateReport", parameters);
	}

	@SuppressWarnings("unchecked")
	public List<SmsLogStat> queryStat(final Map<String, Object> parameters) {
		return (List<SmsLogStat>) super.queryForList("SMS_CONTENT_LOG.queryStat", parameters);
	}

	public long queryMMSStat(final Map<String, Object> parameters) {
		return (Long) super.queryForObject("SMS_CONTENT_LOG.queryMMSStat", parameters);
	}
	
	/**
	 * 实现  Ticket-5259, 移动指定时间偏移量之前的所有数据到SMS_CONTENT_LOG_HIS表<br/>
	 * @param parameters 时间偏移量，配合oracle函数add_months(sysdate,-?)使用，默认值：-3<br/>\
	 * 					若需配置偏移量，请参阅spring bean配置“moveSmsContentLogToHisBean”
	 * @return 
	 */
	public void moveSmsContentLogToHis(final Map<String, Object> parameters) {
		super.insert("SMS_CONTENT_LOG.moveToHis", parameters);
		super.delete("SMS_CONTENT_LOG.deleteFromLogTable", parameters);
	}
	
	public long count(final Map<String, Object> parameters){
		SmsLogStat sls =  (SmsLogStat) super.queryForObject("SMS_CONTENT_LOG.count", parameters);
		return sls.getTotal();
	}
	
	/**
	 * 查询已发送短信列表
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SmsContentLog> getListByParams(Map<String, Object> params){
		return this.queryForList("SMS_CONTENT_LOG.getListByParams", params);
	}
	
	
	/**
	 * 查询已发送短信列表数量
	 * @param params
	 * @return
	 */
	public Long getListCountByParams(Map<String, Object> params){
		return Long.valueOf(this.queryForObject("SMS_CONTENT_LOG.getListCountByParams", params).toString());
	}
	
	/**
	 * 更新短信日志信息
	 * @param smsContentLog
	 * @return
	 */
	public int updateSmsLog(SmsContentLog smsContentLog){
		return this.update("SMS_CONTENT_LOG.updateSmsLog", smsContentLog);
	}
	
	/**
	 * 查询短信日志信息
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SmsContentLog> getSmsLogListByParams(Map<String, Object> paramMap){
		return this.queryForList("SMS_CONTENT_LOG.getSmsLogListByParams", paramMap);
	}
	
	/**
	 * 获取推送到第三方失败以及发送失败的短信列表，这些短信需要自动重发
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SmsContentLog> getResendSmsLogList(){
		return this.queryForList("SMS_CONTENT_LOG.getResendSmsLogList");
	}
	
}
