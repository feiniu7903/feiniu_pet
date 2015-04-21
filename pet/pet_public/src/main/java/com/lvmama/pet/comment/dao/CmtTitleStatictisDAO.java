package com.lvmama.pet.comment.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;

/**
 * 景点/产品点评统计表访问接口
 * @author yuzhizeng
 *
 */
public class CmtTitleStatictisDAO extends BaseIbatisDAO{

	/**
	 * 获取点评统计表数据 ---------增加了排序条件
	 * @param parameters 查询参数
	 * @return 集合
	 */
	@SuppressWarnings("unchecked")
	public List<CmtTitleStatisticsVO> query(final Map<String, Object> parameters) {
		if(parameters == null || parameters.size() < 1) return null;
		
		return super.queryForList("CMT_TITLE_STATISTICS.query", parameters);
	}
	
	/**
	 * 获取点评统计个数
	 * @param parameters 查询参数
	 * @return 个数
	 */
	public Long getCommentStatisticsCount(final Map<String, Object> parameters) {
		if(parameters == null || parameters.size() < 1) return null;
		
		Long i = (Long)super.queryForObject("CMT_TITLE_STATISTICS.cmtStatisticsCount", parameters);
		return i;
	}

	/**
	 * 统计place score
	 * @return 是否成功
	 */
	public int mergeStatisticsPlaceScore() {
		return  super.update("CMT_TITLE_STATISTICS.mergeStatisticsPlaceScore");
	}
	
	/**
	 * 统计product score
	 * @return 是否成功
	 */
	public int mergeStatisticsProductScore() {
		return  super.update("CMT_TITLE_STATISTICS.mergeStatisticsProductScore");
	}
	
	/**
	 * 取得招募推荐
	 * @param parameters
	 * @return
	 */
	public List<CmtTitleStatisticsVO>  recommendQuery(final Map<String, Object> parameters){
		return (List<CmtTitleStatisticsVO>)super.queryForList("CMT_TITLE_STATISTICS.recommendQuery", parameters);
	}
	
	
	/**
	 * 查询热点目的地/景点统计数据
	 * @param parameters
	 * @return
	 */
	public List<CmtTitleStatisticsVO> queryHotCommentStatisticsOfPlace(final Map<String, Object> parameters)
	{
		return (List<CmtTitleStatisticsVO>)super.queryForList("CMT_TITLE_STATISTICS.queryHotCommentStatisticsOfPlace", parameters);
	}
	
	/**
	 * 修改
	 * @return 是否成功
	 */
	public int update(final CmtTitleStatisticsVO cmtTitleStatisticsVO) {
		return super.update("CMT_TITLE_STATISTICS.update", cmtTitleStatisticsVO);
	}
	
}
