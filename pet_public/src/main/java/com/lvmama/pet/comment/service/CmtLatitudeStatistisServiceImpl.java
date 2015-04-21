package com.lvmama.pet.comment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.comment.CmtLatitudeStatistics;
import com.lvmama.comm.pet.service.comment.CmtLatitudeStatistisService;
import com.lvmama.pet.comment.dao.CmtLatitudeStatistisDAO;

public class CmtLatitudeStatistisServiceImpl implements CmtLatitudeStatistisService {

	private CmtLatitudeStatistisDAO cmtLatitudeStatistisDAO;
	
	/**
	 * 获得点评维度统计
	 * @param parameters
	 * @return
	 */
	public List<CmtLatitudeStatistics> getLatitudeStatisticsList(final Map<String, Object> parameters)
	{
		return this.cmtLatitudeStatistisDAO.query(parameters);
	}
	
	/**
	 * merge点评景点维度平均分统计数据
	 * @return 点评统计集合
	 */
	public int mergeStatisticsPlaceLatitudeAvgScore()
	{
		return this.cmtLatitudeStatistisDAO.mergeStatisticsPlaceLatitudeAvgScore();
	}
	
	/**
	 * merge点评产品维度平均分统计数据
	 * @return 点评统计集合
	 */
	public int mergeStatisticsProductLatitudeAvgScore()
	{
		return this.cmtLatitudeStatistisDAO.mergeStatisticsProductLatitudeAvgScore();
	}
	
	/**
	 * 获得某个对象的4个基本点评维度统计平均分(过滤总体维度)
	 * @param parameters
	 * @return List<CmtLatitudeStatistics>
	 */
	public List<CmtLatitudeStatistics> getFourAvgLatitudeScoreList(final Map<String, Object> parameters)
	{
		List<CmtLatitudeStatistics> returnPlaceAvgLatitudeScoreList = new ArrayList<CmtLatitudeStatistics>();
		List<CmtLatitudeStatistics> placeAvgLatitudeScoreList = this.cmtLatitudeStatistisDAO.query(parameters);
		for(CmtLatitudeStatistics cmtLatitudeStatistics : placeAvgLatitudeScoreList){
			if(!cmtLatitudeStatistics.getLatitudeId().equals("FFFFFFFFFFFFFFFFFFFFFFFFFFFF"))
			{
				returnPlaceAvgLatitudeScoreList.add(cmtLatitudeStatistics);
			}
		}
		return returnPlaceAvgLatitudeScoreList;
	}

	/**
	 *  ----------------------  get and set property -------------------------------
	 */
	public void setCmtLatitudeStatistisDAO(
			CmtLatitudeStatistisDAO cmtLatitudeStatistisDAO) {
		this.cmtLatitudeStatistisDAO = cmtLatitudeStatistisDAO;
	}
 
}
