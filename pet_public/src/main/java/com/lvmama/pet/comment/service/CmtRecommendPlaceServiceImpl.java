package com.lvmama.pet.comment.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.comment.CmtRecommendPlace;
import com.lvmama.comm.pet.service.comment.CmtRecommendPlaceService;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.pet.comment.dao.CmtRecommendPlaceDAO;

public class CmtRecommendPlaceServiceImpl implements CmtRecommendPlaceService {

	private CmtRecommendPlaceDAO cmtRecommendPlaceDAO;
	@Override
	public List<CmtTitleStatisticsVO> getCommentStatisticsList(
			Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateRecommend(final CmtRecommendPlace cmtRecommendPlace) {
		return cmtRecommendPlaceDAO.updateRecommend(cmtRecommendPlace);
	}

	@Override
	public List<CmtTitleStatisticsVO> getRecommendPlaceList(int _startRow,
			int _endRow, int stage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CmtRecommendPlace> queryRecommendPlace() {
		// TODO Auto-generated method stub
		return cmtRecommendPlaceDAO.queryRecommendPlace();
	}

	@Override
	public Long queryByPlaceId(Long placeId) {
		// TODO Auto-generated method stub
		return null;
	}

	public CmtRecommendPlaceDAO getCmtRecommendPlaceDAO() {
		return cmtRecommendPlaceDAO;
	}

	public void setCmtRecommendPlaceDAO(CmtRecommendPlaceDAO cmtRecommendPlaceDAO) {
		this.cmtRecommendPlaceDAO = cmtRecommendPlaceDAO;
	}

}
