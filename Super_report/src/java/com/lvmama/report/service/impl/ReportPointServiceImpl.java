package com.lvmama.report.service.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.report.dao.ReportPointDAO;
import com.lvmama.report.po.PointRules;
import com.lvmama.report.service.ReportPointService;
import com.lvmama.report.vo.ShopPointVo;

/**
 * 实现类
 * @author yangchen
 */
public class ReportPointServiceImpl implements ReportPointService {
	/** DAO **/
	private ReportPointDAO reportPointDAO;

	/**
	 * 查询
	 * @param param
	 *            参数
	 * @return 集合
	 */
	@Override
	public List<ShopPointVo> queryAll(final Map<String, Object> param) {
		return reportPointDAO.queryAll(param);
	}

	/***
	 * 当前可用积分的总和
	 * @return 总和
	 */
	@Override
	public Long sumPoint() {
		return reportPointDAO.sumPoint();
	}

	@Override
	public Long expiredPoint() {
		return reportPointDAO.expiredPoint();
	}

	@Override
	public List<PointRules> queryTypeList() {
		return reportPointDAO.queryTypeList();
	}

	/**
	 * 客服发放的信息保存
	 * @param info
	 *            属性
	 */
	@Override
	public void savePutPoint(final Map<String, Object> info) {
		reportPointDAO.savePutPoint(info);
	}

	/**
	 * 查询统计的数据
	 * @param param
	 *            参数
	 * @return 统计的对象
	 */
	@Override
	public Long queryPointStatistics(final Map<String, Object> param) {
		return reportPointDAO.queryPointStatistics(param);
	}

	@Override
	public Long queryMemStatistics(final Map<String, Object> param) {
		return reportPointDAO.queryMemStatistics(param);
	}

	public ReportPointDAO getReportPointDAO() {
		return reportPointDAO;
	}

	public void setReportPointDAO(ReportPointDAO reportPointDAO) {
		this.reportPointDAO = reportPointDAO;
	}

}
