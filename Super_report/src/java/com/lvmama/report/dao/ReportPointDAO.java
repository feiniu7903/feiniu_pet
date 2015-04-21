package com.lvmama.report.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.report.po.PointRules;
import com.lvmama.report.vo.ShopPointVo;

/**
 * 实现类
 * @author yangchen
 */
public class ReportPointDAO extends BaseIbatisDAO {
	/**
	 * 查询
	 * @param param
	 *            参数
	 * @return 集合
	 */
	@SuppressWarnings("unchecked")
	public List<ShopPointVo> queryAll(final Map<String, Object> param) {
		return super.queryForList("REPORT_POINT.query",
				param,true);
	}

	public Long queryPointStatistics(final Map<String, Object> param) {
		return (Long) super.queryForObject(
				"REPORT_POINT.sumPointProportion", param);
	}

	public Long queryMemStatistics(final Map<String, Object> param) {
		return (Long) super.queryForObject(
				"REPORT_POINT.sumMemProportion", param);
	}

	@SuppressWarnings("unchecked")
	public List<PointRules> queryTypeList() {
		return (List<PointRules>) super.queryForList(
				"REPORT_POINT.listType");
	}

	/**
	 * 可用积分
	 * @return 返回可用积分
	 */
	public Long sumPoint() {
		return (Long) super.queryForObject(
				"REPORT_POINT.sumPoint");
	}

	/**
	 * 过期积分
	 * @return 过期积分
	 */
	public Long expiredPoint() {
		return (Long) super.queryForObject(
				"REPORT_POINT.queryExpiredPoint");
	}

	/**
	 * 保存客服发放积分的信息
	 * @param info
	 *            信息
	 */
	public void savePutPoint(final Map<String, Object> info) {
		super.insert("REPORT_POINT.savePutPoint", info);
	}

}
