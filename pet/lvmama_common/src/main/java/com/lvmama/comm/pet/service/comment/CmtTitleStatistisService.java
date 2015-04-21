package com.lvmama.comm.pet.service.comment;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;

public interface CmtTitleStatistisService {

	static int EXPIRY_OF_STATISTICS = 120;
	/**
	 * 获取点评统计
	 * @param parameters  查询参数
	 * @return 点评统计集合
	 */
	List<CmtTitleStatisticsVO> getCommentStatisticsList(final Map<String, Object> parameters);

	/**
	 * 获取点评统计个数
	 * @param parameters
	 *            查询参数
	 * @return 个数
	 */
	Long getCommentStatisticsCount(final Map<String, Object> parameters);
	
	/**
	 * merge点评景点统计数据
	 * @return 点评统计集合
	 */
	int mergeStatisticsPlaceScore();
	
	/**
	 * merge点评产品统计数据
	 * @return 点评统计集合
	 */
	int mergeStatisticsProductScore();
	
	/**
	 * 取得招聘推荐
	 * @param parameters
	 * @return
	 */
	List<CmtTitleStatisticsVO>  recommendQuery(final Map<String, Object> parameters);
	
	/**
	 * 查询景区点评统计
	 * @param placeId 查询参数
	 * @return 景区点评统计
	 */
	CmtTitleStatisticsVO getCmtTitleStatisticsByPlaceId(Long placeId);
	
	/**
	 * 查询产品点评统计
	 * @param placeId 查询参数
	 * @return 产品点评统计
	 */
	CmtTitleStatisticsVO getCmtTitleStatisticsByProductId(Long productId);
	
	/**
	 * 修改
	 * @return 是否成功
	 */
	int update(final CmtTitleStatisticsVO cmtTitleStatisticsVO);
	
	/**
	 * 查询热点目的地/景点点评统计数据
	 * @param parameters
	 * @return
	 */
	List<CmtTitleStatisticsVO> queryHotCommentStatisticsOfPlace(final Map<String, Object> parameters);
	
	
	/**------------------------------------迁移到CmtLatitudeStatisticsService--------------------------------------
	 * 获得点评维度统计列表
	 * @param parameters
	 * @return
	List<CmtLatitudeStatistics> getLatitudeStatisticsList(final Map<String, Object> parameters);
	
	/**
	 * 获得点评维度平均分统计列表
	 * @param parameters
	 * @return
	List<CmtLatitudeStatistics> getAvgLatitudeScoreStatisticsList(final Map<String, Object> parameters);
	
	/**
	 * 获得某个对象的4个基本点评维度统计平均分(过滤总体维度)
	  * @param parameters
	 * @return List<CmtLatitudeStatistics>
	List<CmtLatitudeStatistics> getFourAvgLatitudeScoreList(final Map<String, Object> parameters);
	
	/**
	 * merge点评景点维度平均分统计数据
	 * @return 点评统计集合
	int mergeStatisticsPlaceLatitudeAvgScore();
	
	/**
	 * merge点评产品维度平均分统计数据
	 * @return 点评统计集合
	int mergeStatisticsProductLatitudeAvgScore();
	*/
	
	
	/**------------------------------------移除--------------------------------------
	 * 
	 * 用户订单中待点评的景区数
	 * @param parameters 查询条件
	 * @return 数量
	Long getPlacesCountByUsersOrder(final Map<String, Object> parameters);
	 
	 /**
	 * 用户订单中待点评的产品数
	 * @param parameters
	 * @return 数量
	Long getProductsCountByUsersOrder(final Map<String, Object> parameters);
	
	/**
	 * 查询景区点评统计------方法名需要修改
	 * @param parameters 查询参数
	 * @return 景区点评列表
	List<CmtPlaceTitleStatisticsVO> getPlaceCommentList(final Map<String, Object> parameters);
	
	/**
	 * 用户订单中待点评的景区列表-----方法名需要修改
	 * @param parameters 查询条件
	 * @return 景区列表
	List<CmtPlaceTitleStatisticsVO> getPlacesByUsersOrder(final Map<String, Object> parameters);	
	
	/**
	 * 用户订单中待点评的产品列表-----方法名需要修改
	 * @param parameters
	 * @return 产品列表
	List<CmtProdTitleStatisticsVO> getProductsByUsersOrder(final Map<String, Object> parameters);
	
	*/
}
