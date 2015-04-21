package com.lvmama.pet.sms.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.sms.SmsContent;
import com.lvmama.comm.pet.vo.SmsLogStat;
import com.lvmama.comm.utils.DateUtil;

/**
 * 待发送短信的数据库操作实现类
 * @author Brian
 *
 */
public class SmsContentDAO extends BaseIbatisDAO {
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(SmsContentDAO.class);
	/**
	 * 最大可重复发送次数
	 */
	private static final int MAX_DUPLICATION = 3;

	/**
	 * 发送规则
	 * 默认规则 0 -  
	 * 规则 1 - 同一个手机号10分钟内无论任何内容只允许发送3跳
	 */
	private static int rule = 0;
	
	/**
	 * 应用发送规则
	 * @param sms
	 */
	private void applyRule(SmsContent sms){
		Map<String, Object> parameters = new HashMap<String, Object>();
		if(rule == 1){
			/**
			 * 规则 1 同一个手机号10分钟内无论任何内容只允许发送3跳
			 */
			parameters.put("mobile", sms.getMobile());
			parameters.put("condition", "send_date>= sysdate-10/24/60 ");
			
		}else{
			/**
			 * 默认规则 同一个手机号10分钟内相同内容只允许发送3条
			 */
			parameters.put("mobile", sms.getMobile());
			parameters.put("condition", "send_date>= sysdate-10/24/60 and content = '"+sms.getContent()+"' ");
		}
		SmsLogStat sls =  (SmsLogStat)  super.queryForObject("SMS_CONTENT.count", parameters);
		SmsLogStat sls_log =  (SmsLogStat)  super.queryForObject("SMS_CONTENT_LOG.count", parameters);
		Long count = sls.getTotal()+sls_log.getTotal();
		Date send_date = sls.getSendDate();
		Date send_date_log = sls_log.getSendDate();
		if (count.intValue() >= MAX_DUPLICATION) {
			send_date = send_date == null ? sms.getSendDate() : send_date;
			send_date_log = send_date_log == null ? sms.getSendDate() : send_date_log;
			int multiple = count.intValue() / MAX_DUPLICATION;
			if(send_date.getTime() < send_date_log.getTime()){
				send_date = send_date_log;
			} 
			System.out.println("-----count"+count.intValue());
			if(count.intValue()%MAX_DUPLICATION == 0){
				System.out.println("-----count++");
				sms.setSendDate(new Date(send_date.getTime() + 10 * 60 * 1000));
			}else{
				System.out.println("-----count--"+send_date);
				sms.setSendDate(new Date(send_date.getTime()));
			}
			
		} 
	}
	public SmsContent save(final SmsContent sms) {
		if (null == sms
				|| StringUtils.isEmpty(sms.getMobile())
				|| StringUtils.isEmpty(sms.getContent())) {
			return sms;
		}
		
		this.applyRule(sms);
		
		super.insert("SMS_CONTENT.insert", sms);
		if (LOG.isDebugEnabled()) {
			LOG.debug("save " + sms + " successfully!");
		}
		return sms;
	}

	@SuppressWarnings("unchecked")
	public List<SmsContent> query(final Map<String, Object> parameters) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Want to query,condition: " + parameters);
		}
		return (List<SmsContent>) super.queryForList("SMS_CONTENT.query", parameters);
	}

	public void deleteByPrimaryKey(final Serializable id) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Want to delete id = " + id);
		}
		super.delete("SMS_CONTENT.deleteByPrimaryKey", id);
	}

	public void delete(final Map<String, Object> parameters) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Want to delete,condition: " + parameters);
		}
		super.delete("SMS_CONTENT.delete", parameters);
	}

	public void updateFailureCount(final Serializable id) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Want to update failure count where id = " + id);
		}
		super.update("SMS_CONTENT.updateFailureCount", id);
	}

	public SmsContent getByPrimaryKey(final Serializable id) {
		Object o = super.queryForObject("SMS_CONTENT.selectByPrimaryKey", id);
		if (null == o) {
			return null;
		} else {
			return (SmsContent) o;
		}
	}

	public void retrySend(final Serializable serialId) {
		super.insert("SMS_CONTENT.retrySend", serialId);
	}

	public static int getRule() {
		return rule;
	}

	public static void setRule(int rule) {
		SmsContentDAO.rule = rule;
	}
	
	@SuppressWarnings("unchecked")
	public List<SmsContent> queryByParam(Map<String, Object> param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Want to query,condition: " + param);
		}
		return super.queryForList("SMS_CONTENT.selectByParam", param);
	}
	
	/**
	 * 重发短信,指定需要重发的短信的id，短信通道，原短信所在表
	 */
	public void reSendSmsByMap(Map<Object, Object> extendParam){
		this.insert("SMS_CONTENT.reSendSmsByMap", extendParam);
	}
	
	/**
	 * 修改短信内容信息
	 * @param smsContent
	 * @return
	 */
	public int update(SmsContent smsContent){
		return this.update("SMS_CONTENT.update", smsContent);
	}
	/**
	 * 插入短信内容信息
	 * @param smsContent
	 * @return
	 */
	public void insert(SmsContent smsContent){
		this.insert("SMS_CONTENT.insert", smsContent);
	}
}
