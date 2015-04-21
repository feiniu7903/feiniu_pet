package com.lvmama.report.service;

import java.util.List;
import java.util.Map;

import com.lvmama.report.po.CCStaffsSales;

/**
 * 呼叫中心员工销售额统计服务接口.
 * @author sunruyi
 * @see java.util.List
 * @see java.util.Map
 * @see com.lvmama.report.po.CCStaffsSales
 */
public interface ICCStaffsSalesStatisticsService {
	/**
	 * 查询呼叫中心员工销售额总记录数.
	 * @param parameters 查询参数.
	 * @return 总记录数.
	 */
	Long ccStaffsSalesCount(final Map<String, Object> parameters);
	/**
	 * 查询呼叫中心员工销售额记录.
	 * @param parameters 查询参数.
	 * @return 呼叫中心员工销售额记录.
	 */
	List<CCStaffsSales> queryCCStaffsSales(final Map<String, Object> parameters,boolean isForReportExport);
}
