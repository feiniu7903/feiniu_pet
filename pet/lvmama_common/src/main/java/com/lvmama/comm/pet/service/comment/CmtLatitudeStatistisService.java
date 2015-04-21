package com.lvmama.comm.pet.service.comment;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.comment.CmtLatitudeStatistics;

public interface CmtLatitudeStatistisService{

	/**
	 * 获得点评维度统计列表
	 * @param parameters
	 * @return
	 * 	合并该方法---获得点评维度平均分统计列表
		List<CmtLatitudeStatistics> getAvgLatitudeScoreStatisticsList(final Map<String, Object> parameters);
	 */
	List<CmtLatitudeStatistics> getLatitudeStatisticsList(final Map<String, Object> parameters);
	
	/**
	 * 获得某个对象的4个基本点评维度统计平均分(过滤总体维度)
	  * @param parameters
	 * @return List<CmtLatitudeStatistics>
	 * */
	List<CmtLatitudeStatistics> getFourAvgLatitudeScoreList(final Map<String, Object> parameters);
	
	/**
	 * merge点评景点维度平均分统计数据
	 * @return 点评统计集合
	 * */
	int mergeStatisticsPlaceLatitudeAvgScore();
	
	/**
	 * merge点评产品维度平均分统计数据
	 * @return 点评统计集合
	 * */
	int mergeStatisticsProductLatitudeAvgScore();
	
}
