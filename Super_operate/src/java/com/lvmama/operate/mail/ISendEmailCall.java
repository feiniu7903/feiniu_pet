package com.lvmama.operate.mail;

import com.lvmama.operate.mail.model.ResultMessage;

/**
 * 发送邮件过程中回调的接口
 * 
 * @author likun
 * 
 */
public interface ISendEmailCall {

	/**
	 * 发送邮件之前调用的方法
	 * 
	 * @param paramMap
	 */
	public void sendEmailBefore(ResultMessage result);

	/**
	 * 上载邮件结束以后回调的方法
	 * 
	 * @param paramMap
	 */
	public void uploadEmailSuccess(ResultMessage resul);

	/**
	 * 发送以后回调的方法
	 * 
	 * @param paramMap
	 */
	public void sendEmailSuccess(ResultMessage result);
}
