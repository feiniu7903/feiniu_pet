package com.lvmama.businesses.review.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.businesses.review.dao.BbsPostDao;
import com.lvmama.businesses.review.dao.BbsReviewKeywordDao;
import com.lvmama.businesses.review.dao.BbsThreadDao;
import com.lvmama.businesses.review.dao.GlReviewKeywordDao;
import com.lvmama.businesses.review.dao.PhpReviewKeywordDao;
import com.lvmama.comm.businesses.po.review.BbsPreForumPost;
import com.lvmama.comm.businesses.po.review.BbsPreForumThread;
import com.lvmama.comm.businesses.po.review.KeyWord;
import com.lvmama.comm.businesses.po.review.ReviewSendEmail;
import com.lvmama.comm.businesses.service.review.BbsService;

public class BbsServiceImpl implements BbsService{
	@Autowired
	private BbsPostDao bbsPostDao;
	@Autowired
	private BbsThreadDao bbsThreadDao;
	@Autowired
	private BbsReviewKeywordDao bbsReviewKeywordDao;
	@Autowired
	private GlReviewKeywordDao glBbsReviewKeywordDao;
	@Autowired
	private PhpReviewKeywordDao phpReviewKeywordDao;

	@Override
	public BbsPreForumPost queryForBbsPreForumPostById(int pid) {
		return bbsPostDao.queryForBbsPreForumPostById(pid);
	}
	@Override
	public List<BbsPreForumPost> queryBbsPreForumPostByParam(Map param) {
		 
		return bbsPostDao.queryBbsPreForumPostByParam(param);
	}

	@Override
	public long countForBbsPreForumPost(Map param) {
		return bbsPostDao.count(param);
	}

	@Override
	public void updateForBbsPreForumPost(Map<String, Object> param) {
		  bbsPostDao.update(param);
	}
	@Override
	public BbsPreForumThread queryForThreadByTid(Integer tid) {
 		return bbsThreadDao.queryForThreadByTid(tid);
	}
	@Override
	public long countForThread(Map param) {
		return bbsThreadDao.countForThread(param);
	}

	@Override
	public List<BbsPreForumThread> queryForThreadByParam(Map param) {
		return bbsThreadDao.queryForThreadByParam(param);
	}

	@Override
	public void updateForThread(Map<String, Object> param) {
		bbsThreadDao.update(param);
	}

	@Override
	public void reviewKeywordSynInset(List<KeyWord> keyWordsList) {
		 bbsReviewKeywordDao.reviewKeywordSynInset(keyWordsList);
		 glBbsReviewKeywordDao.reviewKeywordSynInset(keyWordsList);
		 phpReviewKeywordDao.reviewKeywordSynInset(keyWordsList);
	}

	@Override
	public void synKeyWordUpdate(String oldContent, String newContent) {
		bbsReviewKeywordDao.synKeyWordUpdate(oldContent,newContent);
		glBbsReviewKeywordDao.synKeyWordUpdate(oldContent,newContent);
		phpReviewKeywordDao.synKeyWordUpdate(oldContent,newContent);
	}

	@Override
	public void synDeleteBykeyWord(String content) {
		bbsReviewKeywordDao.synDeleteBykeyWord(content);
		glBbsReviewKeywordDao.synDeleteBykeyWord(content);
		phpReviewKeywordDao.synDeleteBykeyWord(content);
	}

	@Override
	public void synBatchDeleteKeyWord(List<KeyWord> keyWordsList) {
		bbsReviewKeywordDao.synBatchDeleteKeyWord(keyWordsList);
		glBbsReviewKeywordDao.synBatchDeleteKeyWord(keyWordsList);
		phpReviewKeywordDao.synBatchDeleteKeyWord(keyWordsList);
	}

	@Override
	public Integer exeScanningForumPost(Map map) {
		return bbsReviewKeywordDao.exeScanningForumPost(map);
	}
	@Override
	public Integer exeScanningForumThread(Map map) {
		return bbsReviewKeywordDao.exeScanningForumThread(map);
	}
	@Override
	public Integer exeScanningGlArticle(Map map) {
 		return glBbsReviewKeywordDao.exeScanningGlArticle(map);
	}
	@Override
	public Integer exeScanningGlComment(Map map) {
 		return glBbsReviewKeywordDao.exeScanningGlComment(map);
	}
	@Override
	public Integer exeScanningPhpcmsComment(Map map) {
 		return phpReviewKeywordDao.exeScanningPhpcmsComment(map);
	}
	@Override
	public Integer exeScanningPhpcmsContentAll(Map map) {
 		return phpReviewKeywordDao.exeScanningPhpcmsContentAll(map);
	}


}
