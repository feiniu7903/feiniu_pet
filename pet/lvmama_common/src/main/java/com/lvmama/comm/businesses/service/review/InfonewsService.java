package com.lvmama.comm.businesses.service.review;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.businesses.po.review.KeyWord;
import com.lvmama.comm.businesses.po.review.PhpcmsComment;
import com.lvmama.comm.businesses.po.review.PhpcmsContent;
/**
 * 资讯服务
 * @author nixianjun
 *
 */
public interface InfonewsService {
	/**
	 * 资讯点评
	 * @param param
	 * @return
	 * @author nixianjun 2013-9-29
	 */
	public List<PhpcmsComment> queryPhpcmsCommentByParam(Map param);
	/**
	 * 资讯点评
	 * @param param
	 * @return
	 */
	public long countForPhpcmsComment(Map param);
	/**
	 * 资讯点评
	 * @param param
	 */
	public void updateForPhpcmsComment(Map<String, Object> param);
	
	
	/**
	 * 更改资讯内容状态
	 * @param param
	 */
	public void updateForPhpcmsContent(Map<String, Object> param);
	
	/**
	 * 资讯内容
	 * @param param
	 * @return
	 * @author nixianjun 2013-9-29
	 */
	public List<PhpcmsContent> queryPhpcmsCByParam(Map param);
	/**
	 *  资讯内容
	 * @param param
	 * @return
	 */
	public long countForPhpcmsC(Map param);
	/**
	 *  资讯内容
	 * @param param
	 */
	public void updateForPhpcmsC(Map<String, Object> param);
	public PhpcmsComment queryForPhpcmsCommentById(Integer valueOf);
	public PhpcmsContent queryForPhpcmsContentById(Integer contentid, String subTable);
	
}
