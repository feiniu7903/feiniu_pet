package com.lvmama.pet.sms.dao;

import org.apache.log4j.Logger;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.sms.SmsConfig;

/**
 * 短信渠道全局配置
 * 
 * @author ready
 * 
 */
public class SmsConfigDAO extends BaseIbatisDAO {
	/**
	 * 日志输出器
	 */
	private static final Logger log = Logger.getLogger(SmsConfigDAO.class);
	private static final String NAMESPACE = "SMS_CONFIG";

	/**
	 * 获取短信渠道全局配置信息
	 * 
	 * @return
	 */
	public SmsConfig querySmsConfig() {
		return (SmsConfig) this.queryForObject(NAMESPACE + ".query");
	}

	/**
	 * 更新短信全局配置信息
	 * 
	 * @param config
	 * @return
	 */
	public int update(SmsConfig config) {
		return this.update(NAMESPACE + ".update", config);
	}
}
