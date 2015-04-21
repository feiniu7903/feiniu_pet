package com.lvmama.report.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.report.po.VistorDetailBasicMV;

/**
 * 游客信息
 * @author yangchen
 *
 */
public class VistorDetailBasicMVDAO extends BaseIbatisDAO {
	/***
	 * 查询游客信息的列表的集合
	 * @param param 参数
	 * @return 游客信息的列表的集合
	 */
	public List<VistorDetailBasicMV> queryVistorDetaiBasicMV(final Map<String, Object> param,boolean isForReportExport) {
		return  super.queryForList("ORDER_CUSTOMER_BASIC_MV.queryVistorBasicMVByTime", param, isForReportExport);
	}

	/***
	 * 查询总条数
	 * @param param 参数
	 * @return 总条数
	 */
	public Long countVistorDetailBasicMV(final Map<String, Object> param) {
		return (Long) super.queryForObject(
				"ORDER_CUSTOMER_BASIC_MV.countVistorBasicMVByTime", param);
	}
}
