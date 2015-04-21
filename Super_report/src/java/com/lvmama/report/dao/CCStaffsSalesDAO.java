package com.lvmama.report.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.report.po.CCStaffsSales;

/**
 * 呼叫中心员工销售额DAO.
 * @author sunruyi
 * @see java.util.Map
 * @see java.util.List
 * @see com.lvmama.common.BaseIbatisDao
 * @see com.lvmama.report.po.CCStaffsSales
 */
public class CCStaffsSalesDAO extends BaseIbatisDAO {
	/**
	 * 查询呼叫中心员工销售额总记录数.
	 * @param parameters 查询参数.
	 * @return 总记录数.
	 */
	public Long queryCCStaffsSalesCount(final Map<String, Object> parameters) {
		return (Long) super.queryForObject("CALL_CENTER_BASIC.queryCCStaffsSalesCount", parameters);
	}
	/**
	 * 查询呼叫中心员工销售额记录.
	 * @param parameters 查询参数.
	 * @return 呼叫中心员工销售额记录.
	 */
	@SuppressWarnings("unchecked")
	public List<CCStaffsSales> queryCCStaffsSales(final Map<String, Object> parameters,boolean isForReportExport) {
		return super.queryForList("CALL_CENTER_BASIC.queryCCStaffsSales", parameters, isForReportExport);
	}
}
