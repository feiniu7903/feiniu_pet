package com.lvmama.report.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.report.po.CmtStatisticsModel;

public class CmtStatisticsDAO extends BaseIbatisDAO {
	/**
	 * 查询记录
	 * @param param 查询条件
	 * @return列表
	 * */
	public List<CmtStatisticsModel> query(Map<String, Object> param,boolean isForReportExport){
		return (List<CmtStatisticsModel>)super.queryForList("CMT_STATISTICS.query", param,isForReportExport);
	}
	/**
	 * 查询记录总数
	 * @param param   查询条件
	 * @return 记录总数
	 * */
	public Long count(Map<String, Object> param){
		return (Long)super.queryForObject("CMT_STATISTICS.count", param);
	}
}
