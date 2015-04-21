package com.lvmama.pet.comment.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.vo.comment.CmtReplyVO;
/**
 * 点评回复逻辑接口
 * @author yuzhizeng
 * */
public class CmtReplyDAO extends BaseIbatisDAO{

	/**
	 * 根据条件查询回复
	 * @param parameters 查询条件
	 * @return 回复列表
	 */
	@SuppressWarnings("unchecked")
	public List<CmtReplyVO> query(final Map<String, Object> parameters) {
		return (List<CmtReplyVO>) super.queryForList("CMT_REPLY.query", parameters);
	}

	/**
	 * 插入点评对应的回复
	 * @param cmtReply 回复
	 * @return 回复表识
	 */
	public long insert(final CmtReplyVO cmtReply) {
		super.insert("CMT_REPLY.insert", cmtReply);
		return cmtReply.getReplyId();
	}

	/**
	 * 修改点评回复审核状态
	  * @param cmtReply  回复
	  * @return 表识
	 */
	public int update(final CmtReplyVO cmtReply) {
		return super.update("CMT_REPLY.update", cmtReply);
	}

	/**
	  * 根据条件查询点评回复数
	  * @param parameters 查询条件
	  * @return 数量
	 */
	public Long count(final Map<String, Object> parameters) {
	    parameters.put("isHide","N");
		return (Long) super.queryForObject("CMT_REPLY.count", parameters);

	}

	/**
	 * 根据条件查询回复
	 * @param id 表识
	 * @return 回复
	 */
	public CmtReplyVO queryCmtReplyByKey(final long id) {
		return (CmtReplyVO) super.queryForObject("CMT_REPLY.queryByKey", id);
	}

	/**
	 * 查询排除某状态的回复
	 * @param parameters 查询条件
	 * @return 回复列表
	 */
	@SuppressWarnings("unchecked")
	public List<CmtReplyVO> queryReplyExceptStatus(
			final Map<String, Object> parameters) {
		return (List<CmtReplyVO>) super.queryForList(
				"CMT_REPLY.queryReplyExceptStatus", parameters);
	}

	/**
	 * 根据点评审核条件返回点评回复条数
	 * @param parameters
	 * @return
	 */
    public Long queryCountOfCommentReview(final Map<String, Object> parameters){
        return (Long) super.queryForObject("CMT_REPLY.selectCountByCmtReplyReviewParam",parameters);
    }
    
    /**
     * 修改点评回复内容审核状态
     * @param CmtReplyVO
     */
    public void updateReviewStatusByComment(CmtReplyVO cmtReply){
        super.update("CMT_REPLY.updateCmtReplyReviewStatus",cmtReply);
    }
    
    /**
     * 根据内容审核条件返回点评回复信息
     * @param parameters
     * @return List<CmtReplyVO>
     */
    @SuppressWarnings("unchecked")
    public List<CmtReplyVO> selectParamByComment(final Map<String, Object> parameters){
        return super.queryForList("CMT_REPLY.selectCmtReplyReviewContent",parameters);
    }
	
}