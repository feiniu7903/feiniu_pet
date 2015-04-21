package com.lvmama.comm.businesses.service.review;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.businesses.po.review.GlArticle;
import com.lvmama.comm.businesses.po.review.GlComment;
import com.lvmama.comm.businesses.po.review.KeyWord;

 
public interface GuideService {
	public List<GlArticle> queryGlArticleByParam(Map param);
	/**
	 * @param param
	 * @return
	 */
	public long countForGlArticle(Map param);
	/**
	 * @param param
	 */
	public void updateForBbsGlArticle(Map<String, Object> param);
	
	
	public List<GlComment> queryGlCommentByParam(Map param);
	/**
	 * @param param
	 * @return
	 */
	public long countForGlComment(Map param);
	/**
	 * @param param
	 */
	public void updateForGlComment(Map<String, Object> param);
	public GlArticle queryForBbsGlArticleById(Integer valueOf);
	public GlComment queryForGlCommentById(Integer valueOf);
}
