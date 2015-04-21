package com.lvmama.report.service.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.report.dao.CCStaffsSalesDAO;
import com.lvmama.report.po.CCStaffsSales;
import com.lvmama.report.service.ICCStaffsSalesStatisticsService;

/**
 * 呼叫中心员工销售额统计服务接口实现类.
 * @author sunruyi
 * @see java.util.List
 * @see java.util.Map
 * @see com.lvmama.report.dao.CCStaffsSalesDAO
 * @see com.lvmama.report.po.CCStaffsSales
 * @see com.lvmama.report.service.ICCStaffsSalesStatisticsService
 */
public class CCStaffsSalesStatisticsServiceImpl implements ICCStaffsSalesStatisticsService {
	/**
	 * 呼叫中心员工销售额DAO.
	 */
	private CCStaffsSalesDAO ccStaffsSalesDAO;
	/**
	 * 查询呼叫中心员工销售额总记录数.
	 * @param parameters 查询参数.
	 * @return 总记录数.
	 */
	@Override
	public Long ccStaffsSalesCount(final Map<String, Object> parameters) {
		return ccStaffsSalesDAO.queryCCStaffsSalesCount(parameters);
	}
	/**
	 * 查询呼叫中心员工销售额记录.
	 * @param parameters 查询参数.
	 * @return 呼叫中心员工销售额记录.
	 */
	@Override
	public List<CCStaffsSales> queryCCStaffsSales(final Map<String, Object> parameters,boolean isForReportExport) {
		return ccStaffsSalesDAO.queryCCStaffsSales(parameters,isForReportExport);
	}
	/**
	 * 注入呼叫中心员工销售额DAO.
	 * @param ccStaffsSalesDAO 呼叫中心员工销售额DAO
	 */
	public void setCcStaffsSalesDAO(final CCStaffsSalesDAO ccStaffsSalesDAO) {
		this.ccStaffsSalesDAO = ccStaffsSalesDAO;
	}
}
