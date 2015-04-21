package com.lvmama.pet.job.sms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 默认上行短信处理器
 * @author Brian
 */
public class DefaultReceiveLogic implements ReceiveLogic {
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(DefaultReceiveLogic.class);

	@Override
	public int execute(final String mobile, final String content) {
		return CONTINUE_MSSSAGE;
	}

	/**
	 * 打印调试信息
	 * @param message 调试信息
	 */
    protected void debug(final String message) {
    	if (LOG.isDebugEnabled()) {
    		LOG.debug(message);
    	}
    }

}
