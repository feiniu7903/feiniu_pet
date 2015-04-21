/**
 * 
 */
package com.lvmama.comm.pet.service.comment;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.vo.comment.CmtReplyVO;

/**
 * @author liuyi
 *点评回复的逻辑接口类
 */
public interface CmtReplyService {
	
	/**
	 * 插入点评回复
	 * @param parameters cmtReply
	 * @return 表识
	 */
	 long insert(CmtReplyVO cmtReply);
	 
	/**
	 * 根据条件查询点评回复
	 * @param parameters 查询条件
	 * @return 回复列表
	 */
	 List<CmtReplyVO> query(Map<String, Object> parameters);
	 
	 /**
	  * 根据条件查询点评回复数
	  * @param parameters 
	  * @return 
	 */
	long count(Map<String, Object> parameters);
	 
	/**
	  * 修改点评回复审核状态 
	  * @param parameters 
	  * @return 
	 */
	public int update(final CmtReplyVO cmtReply);
	
	/**
	 * 根据条件查询回复
	 * @param id 查询条件
	 * @return 回复
	 */
	public CmtReplyVO queryCmtReplyByKey(long id);

	/**
	 * 获取条数来自条件 审核状态及查询时间
	 * @param parameters
	 * @return
	 * 针对内容审核
	 */
	long getCountByReviewStatus(Map<String, Object> parameters);
	
	/**
	 * 获取点评回复内容来自条件 审核状态及查询时间
	 * @param parameters
	 * @return
     * 针对内容审核
	 */
	List<CmtReplyVO> queryCmtReplyByReviewStatus(Map<String, Object> parameters);
	
	/**
	 * 修改点评回复状态 根据点评回复对象
	 * @param cmtReply
     * 针对内容审核
	 */
	void updateCmtReplyByReviewStatus(CmtReplyVO cmtReply);
	
	
}
