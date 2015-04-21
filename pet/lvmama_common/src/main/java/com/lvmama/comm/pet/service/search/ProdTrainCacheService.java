/**
 * 
 */
package com.lvmama.comm.pet.service.search;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.pet.po.search.ProdTrainCache;
import com.lvmama.comm.search.vo.TrainSearchVO;

/**
 * @author yangbin
 *
 */
public interface ProdTrainCacheService {

	/**
	 * 添加一个车票的缓存
	 * @param cacheList
	 */
	void updateCache(List<ProdTrainCache> cacheList);
	/**
	 * 标识一个不可售
	 * @param timePriceId
	 */
	void markSoldout(final List<Long> prodBranchIds,Date date);
	
	/**
	 * 查询
	 * @param trainSearchVO
	 * @return
	 */
	List<ProdTrainCache> selectCacheList(TrainSearchVO trainSearchVO);
	
	void removeNotValidTrains(Date date);
	
	/**
	 * 查询一个城市key是否已经存在
	 * @param stationKey
	 * @param visitTime
	 * @return
	 */
	boolean existKey(String stationKey,Date visitTime);
	
	/**
	 * 取指定日期的一个缓存数据
	 * @param prodBranchId
	 * @param visitTime
	 * @return
	 */
	ProdTrainCache get(final Long prodBranchId,Date visitTime);
	
	/**
	 * 按指定日期复制一天的数据到新的一天
	 * @param date
	 */
	void copyDataToNewDay(final Date date);
	/**
	 * 根据站站Id、branchId、时间到数据库中获取该cache信息
	 * @param cache
	 * @return
	 */
	ProdTrainCache getTrainCache(ProdTrainCache cache);
	
	/**
	 * 查询一个日期存在时间价格表数量
	 * @param date
	 * @return
	 */
	long queryCount(final Date date);
	
	/**
	 * 查询最后一条导入的数据
	 * @param date
	 * @return
	 */
	ProdTrainCache queryLastCacheByDate(Date date);
}
