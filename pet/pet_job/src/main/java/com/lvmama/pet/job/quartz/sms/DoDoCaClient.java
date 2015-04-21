package com.lvmama.pet.job.quartz.sms;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.sms.SmsContent;
import com.lvmama.comm.utils.HttpsUtil;

/**
 * 　点点客发送短信类
 * 
 * @author zenglei
 *
 */
public class DoDoCaClient {
	/**
	 * 点点客用户名
	 */
	private String account;
	/**
	 * 点点客密码
	 */
	private String pswd;
	/**
	 * 点点客IP
	 */
	private String ip;
	/**
	 * 是否需要状态报告
	 */
	private String needstatus;
	
	/**
	 * 日志输出器
	 */
	public static final Log LOG = LogFactory.getLog(DoDoCaClient.class);
	
	public DoDoCaClient(final String account, final String pswd,final String ip,final String needstatus) throws Exception {
		this.account = account;
		this.pswd = pswd;
		this.ip = ip;
		this.needstatus = needstatus;
	}
	
	public String sendSMS(SmsContent smsContent){
		if(null == smsContent || null == smsContent.getMobile()){
			LOG.warn("joke?Try to send mms to null mobile!");
			return null;
		}else{
			String url = "http://"+ip+"/msg/HttpBatchSendSM";

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("account", account);
			paramMap.put("pswd", pswd);
			paramMap.put("needstatus", needstatus);
			paramMap.put("mobile", smsContent.getMobile());
			paramMap.put("msg", smsContent.getContent());
			
			String responseCode = HttpsUtil.requestPostForm(url, paramMap);
			return responseCode;
		}
	}
	
	public void setAccount(String account) {
		this.account = account;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setNeedstatus(String needstatus) {
		this.needstatus = needstatus;
	}
}
