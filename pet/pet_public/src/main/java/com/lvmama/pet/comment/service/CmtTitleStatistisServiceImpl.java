package com.lvmama.pet.comment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.service.comment.CmtTitleStatistisService;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.pet.comment.dao.CmtTitleStatictisDAO;

public class CmtTitleStatistisServiceImpl implements CmtTitleStatistisService {
	 
	private CmtTitleStatictisDAO cmtTitleStatictisDAO;
	
	@Override
	public List<CmtTitleStatisticsVO> getCommentStatisticsList(
			final Map<String, Object> parameters) {
		List<CmtTitleStatisticsVO> list = cmtTitleStatictisDAO.query(parameters);
		return list;
	}

	@Override
	public CmtTitleStatisticsVO getCmtTitleStatisticsByPlaceId(final Long placeId) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("placeId", placeId);
		List<CmtTitleStatisticsVO> list = getCommentStatisticsList(params);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public CmtTitleStatisticsVO getCmtTitleStatisticsByProductId(final Long productId) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		List<CmtTitleStatisticsVO> list = getCommentStatisticsList(params);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	
	@Override
	public Long getCommentStatisticsCount(final Map<String, Object> parameters) {
		return cmtTitleStatictisDAO.getCommentStatisticsCount(parameters);
	}
	
	/**
	 * 启动景点点评统计操作
	 * @param parameters
	 * @return
	 */
	@Override
	public int mergeStatisticsPlaceScore() {
		return cmtTitleStatictisDAO.mergeStatisticsPlaceScore();
	}
	/**
	 * 启动产品点评统计操作
	 * @param parameters
	 * @return
	 */
	@Override
	public int mergeStatisticsProductScore() {
		return cmtTitleStatictisDAO.mergeStatisticsProductScore();
	}
	
	/**
	 * 查询热点目的地/景点点评统计数据
	 * @param parameters
	 * @return
	 */
	public List<CmtTitleStatisticsVO> queryHotCommentStatisticsOfPlace(final Map<String, Object> parameters)
	{
		return cmtTitleStatictisDAO.queryHotCommentStatisticsOfPlace(parameters);
	}
	
	@Override
	public List<CmtTitleStatisticsVO> recommendQuery(
			Map<String, Object> parameters) {
		return cmtTitleStatictisDAO.recommendQuery(parameters);
	}
 
	/**
	 * 修改
	 * @return 是否成功
	 */
	public int update(final CmtTitleStatisticsVO cmtTitleStatisticsVO) {
		return cmtTitleStatictisDAO.update(cmtTitleStatisticsVO);
	}
 
	/**
	 *  ----------------------  get and set property -------------------------------
	 */
	public void setCmtTitleStatictisDAO(CmtTitleStatictisDAO cmtTitleStatictisDAO) {
		this.cmtTitleStatictisDAO = cmtTitleStatictisDAO;
	}
}
