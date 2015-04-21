package com.lvmama.eplace.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.pass.PassPortLog;

/**
 * 
 * @author luoyinqi
 *
 */
@SuppressWarnings("unchecked")
public class PassPortLogDao extends BaseIbatisDAO {
	public List<PassPortLog> selectByOrderItemMetaId(final Long ordOrderItemMetaId) {
		return super.queryForList(
				"PASS_PORT_LOG.PassPortLog_selectByOrderItemMetaId",ordOrderItemMetaId);
		
	}
	/**
	 * 添加通关日志
	 * @param passPortLog
	 */
	public void addPassPortLog(PassPortLog passPortLog) {
		 super.insert(
				"PASS_PORT_LOG.insert",passPortLog);
		
	}
}
