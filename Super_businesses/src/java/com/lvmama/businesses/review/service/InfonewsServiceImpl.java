package com.lvmama.businesses.review.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.businesses.review.dao.PhpReviewKeywordDao;
import com.lvmama.businesses.review.dao.PhpcmsCommentDao;
import com.lvmama.businesses.review.dao.PhpcmsContentDao;
import com.lvmama.comm.businesses.po.review.KeyWord;
import com.lvmama.comm.businesses.po.review.PhpcmsComment;
import com.lvmama.comm.businesses.po.review.PhpcmsContent;
import com.lvmama.comm.businesses.service.review.InfonewsService;

public class InfonewsServiceImpl implements InfonewsService{
	@Autowired
	private PhpcmsCommentDao phpcmsCommentDao;
	//资讯内容
	@Autowired
	private PhpcmsContentDao phpcmsContentDao;
	@Override
	public List<PhpcmsComment> queryPhpcmsCommentByParam(Map param) {
 		return phpcmsCommentDao.queryByParam(param);
	}

	@Override
	public long countForPhpcmsComment(Map param) {
		return phpcmsCommentDao.count(param);
	}

	@Override
	public void updateForPhpcmsComment(Map<String, Object> param) {
		phpcmsCommentDao.update(param);
	}
	
	@Override
	public void updateForPhpcmsContent(Map<String, Object> param) {
		phpcmsContentDao.updateForPhpcmsContent(param);
	}

	@Override
	public List<PhpcmsContent> queryPhpcmsCByParam(Map param) {
		return phpcmsContentDao.queryPhpcmsCByParam(param);
	}

	@Override
	public long countForPhpcmsC(Map param) {
		return phpcmsContentDao.countForPhpcmsC(param);
	}

	@Override
	public void updateForPhpcmsC(Map<String, Object> param) {
		phpcmsContentDao.updateForPhpcmsC(param);
	}

	@Override
	public PhpcmsComment queryForPhpcmsCommentById(Integer commentid) {
 		return  phpcmsCommentDao.queryForPhpcmsCommentById(commentid);
	}

	@Override
	public PhpcmsContent queryForPhpcmsContentById(Integer contentid,String tablename) {
 		return phpcmsContentDao.queryForPhpcmsContentById(contentid,tablename);
	}

}
