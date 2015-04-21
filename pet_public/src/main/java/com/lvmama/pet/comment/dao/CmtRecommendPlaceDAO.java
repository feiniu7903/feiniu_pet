package com.lvmama.pet.comment.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.comment.CmtRecommendPlace;

public class CmtRecommendPlaceDAO extends BaseIbatisDAO {
	private final static String SPACE = "CMT_RECOMMEND_PLACE.";
	private final static String QUERY_RECOMMEND_PLACE = SPACE + "queryRecommendPlace";
	private final static String UPDATE_RECOMMEND = SPACE + "updateRecommend";
	
	public List<CmtRecommendPlace> queryRecommendPlace(){
		return (List<CmtRecommendPlace>)super.queryForList(QUERY_RECOMMEND_PLACE);
	}
	
	public int updateRecommend(final CmtRecommendPlace cmtRecommendPlace){
		return super.update(UPDATE_RECOMMEND,cmtRecommendPlace);
	}
}
