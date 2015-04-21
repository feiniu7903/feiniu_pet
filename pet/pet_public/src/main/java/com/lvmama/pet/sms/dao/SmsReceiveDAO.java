package com.lvmama.pet.sms.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.sms.SmsReceive;

/**
 * 短信上行数据库操作类
 * @author Brian
 *
 */
public class SmsReceiveDAO extends BaseIbatisDAO {
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(SmsReceiveDAO.class);

	public SmsReceive save(final SmsReceive smsReceive) {
		super.insert("SMS_RECEIVE.insert", smsReceive);
		if (LOG.isDebugEnabled()) {
			LOG.debug("save " + smsReceive + " successfully!");
		}
		return smsReceive;
	}

}
