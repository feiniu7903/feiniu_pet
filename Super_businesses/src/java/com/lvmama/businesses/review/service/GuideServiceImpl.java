package com.lvmama.businesses.review.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.businesses.review.dao.GlArticleDao;
import com.lvmama.businesses.review.dao.GlCommentDao;
import com.lvmama.businesses.review.dao.GlReviewKeywordDao;
import com.lvmama.comm.businesses.po.review.GlArticle;
import com.lvmama.comm.businesses.po.review.GlComment;
import com.lvmama.comm.businesses.po.review.KeyWord;
import com.lvmama.comm.businesses.service.review.GuideService;


public class GuideServiceImpl implements GuideService {

	@Autowired
	private GlArticleDao glArticleDao;
	@Autowired
	private GlCommentDao glCommentDao;
	@Override
	public List<GlArticle> queryGlArticleByParam(Map param) {
 		return glArticleDao.queryGlArticleByParam(param);
	}

	@Override
	public long countForGlArticle(Map param) {
		return glArticleDao.countForGlArticle(param);
	}

	@Override
	public void updateForBbsGlArticle(Map<String, Object> param) {
		 glArticleDao.updateForBbsGlArticle(param);
	}

	@Override
	public List<GlComment> queryGlCommentByParam(Map param) {
		 
		return glCommentDao.queryGlCommentByParam(param);
	}

	@Override
	public long countForGlComment(Map param) {
		return glCommentDao.countForGlComment(param);
	}

	@Override
	public void updateForGlComment(Map<String, Object> param) {
		glCommentDao.updateForGlComment(param);
	}

	@Override
	public GlArticle queryForBbsGlArticleById(Integer articleid) {
		return glArticleDao.queryForBbsGlArticleById(articleid);
	}

	@Override
	public GlComment queryForGlCommentById(Integer commentid) {
 		return glCommentDao.queryForGlCommentById(commentid);
	}
}
