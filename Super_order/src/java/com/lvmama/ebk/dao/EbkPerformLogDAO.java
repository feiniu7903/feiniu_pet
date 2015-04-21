package com.lvmama.ebk.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.eplace.EbkPerformLog;

/**
 * 手机EBK通关数据记录
 * 
 * @author zhangkexing
 */

public class EbkPerformLogDAO extends BaseIbatisDAO {
	/**
	 * 持久化对象
	 * 
	 * @return
	 */
	public Long insert(EbkPerformLog ebkPerformLog) {
		return (Long) super.insert("EBK_PERFORM_LOG.insert", ebkPerformLog);
	}

	public Integer update(EbkPerformLog ebkPerformLog) {
		return super.update("EBK_PERFORM_LOG.update", ebkPerformLog);
	}

	public EbkPerformLog getEbkPerformLogByUUID(String uuid) {
		return (EbkPerformLog) super.queryForObject("EBK_PERFORM_LOG.getEbkPerformLogByUUID", uuid);
	}
}
