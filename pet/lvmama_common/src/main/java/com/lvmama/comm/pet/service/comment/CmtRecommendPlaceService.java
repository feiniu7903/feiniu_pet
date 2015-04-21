package com.lvmama.comm.pet.service.comment;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.comment.CmtRecommendPlace;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
/**
 * 点评招募
 * @author yangchen
 *
 */
public interface CmtRecommendPlaceService {

	/**
	 * 获取点评统计
	 * @param parameters
	 *            查询参数
	 * @return 点评统计集合
	 */
	List<CmtTitleStatisticsVO> getCommentStatisticsList(
			final Map<String, Object> parameters);

	/**
	 * 修改点评招募的数据
	 * @param param
	 *            参数
	 */
	int updateRecommend(final CmtRecommendPlace cmtRecommendPlace);

	/**
	 * 点评招募
	 * @param _startRow
	 *            ,_endRow stage参数的
	 * @return 点评招募的对象
	 */
	List<CmtTitleStatisticsVO> getRecommendPlaceList(final int _startRow,
			final int _endRow, final int stage);

	/**
	 * 查询点评招募的ID
	 * @return List
	 */
	List<CmtRecommendPlace> queryRecommendPlace();

	/**
	 * 查询景点ID
	 * @param placeId
	 *            参数
	 * @return 长度
	 */
	Long queryByPlaceId(Long placeId);
}
