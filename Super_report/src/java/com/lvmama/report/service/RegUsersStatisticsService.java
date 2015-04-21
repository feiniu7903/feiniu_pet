package com.lvmama.report.service;

import java.util.List;
import java.util.Map;

import com.lvmama.report.vo.TotalAnalysisVo;
import com.lvmama.report.vo.UserAnalysisVO;

public interface RegUsersStatisticsService {
	/**
	 * @param parameters
	 * 获取用户统计数据记录的集合
	 * @param parameters
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @return list
	 */
	List<UserAnalysisVO> getUserAnalysisVOList(final Map parameters)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, NoSuchFieldException;
	/**
	 * 获取整体统计数据记录的集合
	 * @param parameters
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	List<TotalAnalysisVo> getTotalAnalysisTV(Map parameters)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, NoSuchFieldException;
}
