package com.lvmama.comm.pet.service.comment;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.vo.comment.NewsReplyVO;

/**
 * 小驴说事回复的Service
 * @author yuzhizeng
 */
public interface CmtNewsReplyService {

	/**
	 * 插入小驴说事回复
	 * @param newsReply 对象
	 * @return 表识
	 */
	long insert(NewsReplyVO newsReply);

	/**
	 * 根据条件查询小驴说事回复
	 * @param parameters 查询条件
	 * @return 小驴说事回复列表
	 */
	List<NewsReplyVO> query(Map<String, Object> parameters);

	/**
	 * 修改小驴说事回复审核状态
	 * @param newsReply 对象
	 * @return int
	 */
	int update(final NewsReplyVO newsReply);

	/**
	  * 根据条件查询小驴说事回复数
	  * @param parameters 查询条件
	  * @return 数量
	 */
	Long count(Map<String, Object> parameters);

	/**
	 * 根据条件查询回复
	 * @param id 表识
	 * @return 回复
	 */
	NewsReplyVO queryCmtReplyByKey(long id);
	
}
