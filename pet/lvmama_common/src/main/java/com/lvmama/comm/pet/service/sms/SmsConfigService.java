package com.lvmama.comm.pet.service.sms;

import com.lvmama.comm.pet.po.sms.SmsConfig;

/**
 * 短信渠道全局配置服务
 * 
 * @author ready
 * 
 */
public interface SmsConfigService {
	/**
	 * 获取短信渠道全局配置信息
	 * 
	 * @return
	 */
	public SmsConfig querySmsConfig();

	/**
	 * 更新短信全局配置信息
	 * 
	 * @param config
	 * @return
	 */
	public int update(SmsConfig config);
}
