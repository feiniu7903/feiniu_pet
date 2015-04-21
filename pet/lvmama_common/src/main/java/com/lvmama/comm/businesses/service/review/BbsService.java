/**
 * 
 */
package com.lvmama.comm.businesses.service.review;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.businesses.po.review.BbsPreForumPost;
import com.lvmama.comm.businesses.po.review.BbsPreForumThread;
import com.lvmama.comm.businesses.po.review.KeyWord;
import com.lvmama.comm.businesses.po.review.ReviewSendEmail;
 /**
 * @author nixianjun
 *
 */
public interface BbsService {
	public BbsPreForumPost queryForBbsPreForumPostById(int pid);
	public List<BbsPreForumPost> queryBbsPreForumPostByParam(Map param);
	/**
	 * @param param
	 * @return
	 */
	public long countForBbsPreForumPost(Map param);
	/**
	 * @param param
	 */
	public void updateForBbsPreForumPost(Map<String, Object> param);
	public BbsPreForumThread queryForThreadByTid(Integer valueOf);
	/**
	 * @param param
	 * @return
	 */
	public long countForThread(Map param);
	/**
	 * @param param
	 * @return
	 */
	public List<BbsPreForumThread> queryForThreadByParam(Map param);
	/**
	 * @param param
	 */
	public void updateForThread(Map<String, Object> param);
	public void reviewKeywordSynInset(List<KeyWord> keyWordsList);
	public void synKeyWordUpdate(String oldContent, String kContent);
	public void synDeleteBykeyWord(String content);
	public void synBatchDeleteKeyWord(List<KeyWord> keyWordsList);
	public Integer exeScanningForumPost(Map reviewSendEmail);
	public Integer exeScanningForumThread(Map map);
	public Integer exeScanningGlArticle(Map map);
	public Integer exeScanningGlComment(Map map);
	public Integer exeScanningPhpcmsComment(Map map);
	public Integer exeScanningPhpcmsContentAll(Map map);
}
