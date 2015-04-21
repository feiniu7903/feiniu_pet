package com.lvmama.report.service;

import java.util.List;
import java.util.Map;

import com.lvmama.report.po.CmtStatisticsModel;
/**
 * 景点后台统计
 * @author yangchen
 *
 */
public interface CmtStatisticsService {

	/**
	 * 查询记录
	 * @param param 查询条件
	 * @return列表
	 * */
	List<CmtStatisticsModel> query(Map<String, Object> param,boolean isForReportExport);
	/**
	 * 查询记录总数
	 * @param param   查询条件
	 * @return 记录总数
	 * */
	Long count(Map<String, Object> param);
}
