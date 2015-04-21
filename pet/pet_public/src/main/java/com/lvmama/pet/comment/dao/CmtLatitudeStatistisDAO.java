package com.lvmama.pet.comment.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.comment.CmtLatitudeStatistics;

/**
 * 点评维度统计表DAO 
 * (没有对应的CmtLatitudeStatisticsServiceImpl)
 * @author yuzhizeng
 *
 */
public class CmtLatitudeStatistisDAO extends BaseIbatisDAO{
	
	/**
	* 统计place维度平均分
	* @return 是否成功
	*/
	public int mergeStatisticsPlaceLatitudeAvgScore() {
		return  super.update("CMT_LATITUDE_STATISTICS.mergeStatisticsPlaceLatitudeScore");
	}
	
	/**
	* 统计product维度平均分
	* @return 是否成功
	*/
	public int mergeStatisticsProductLatitudeAvgScore() {
		return  super.update("CMT_LATITUDE_STATISTICS.mergeStatisticsProductLatitudeScore");
	}
	
	/**
	* 获取维度统计表数据
	* @param parameters 查询参数
	* @return 集合
	*/
	@SuppressWarnings("unchecked")
	public List<CmtLatitudeStatistics> query(Map<String, Object> parameters) {
		return super.queryForList("CMT_LATITUDE_STATISTICS.query",parameters);
	}
	
	public void updateLatitudeForChangedCmtTitle(Map<String, Object> parameters) {
		super.update("CMT_LATITUDE_STATISTICS.updateLatitudeForChangedCmtTitle", parameters);
	}
	
	/**
	* 依据景点ID或产品ID获取统计维度
	* @param productId 查询参数
	* @return 集合
	*/
	@SuppressWarnings("unchecked")
	public List<CmtLatitudeStatistics> queryLatitudesByParam(final Map<String, Object> parameters) {
		if(parameters.size() > 0){
			return super.queryForList("CMT_LATITUDE_STATISTICS.queryLatitudesByParam",parameters);
		}else{
			return null;
		}
	}
	
	/**
	* 依据景点ID或产品ID删除统计维度
	* @param productId 查询参数
	* @return 集合
	*/
	@SuppressWarnings("unchecked")
	public void deleteLatitudeStatisticsByParam(final Map<String, Object> parameters) {
		if(parameters.size() > 0){
			 super.delete("CMT_LATITUDE_STATISTICS.deleteLatitudeStatisticsByParam",parameters);
		}
	}
	
}
