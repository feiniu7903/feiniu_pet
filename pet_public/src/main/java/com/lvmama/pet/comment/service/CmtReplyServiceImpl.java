/**
 * 
 */
package com.lvmama.pet.comment.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.service.comment.CmtReplyService;
import com.lvmama.comm.vo.comment.CmtReplyVO;
import com.lvmama.pet.comment.dao.CmtReplyDAO;

/**
 * @author liuyi
 *点评回复的逻辑类
 */
public class CmtReplyServiceImpl implements CmtReplyService {

	/**
	 * 数据库实现层
	 */
	private CmtReplyDAO cmtReplyDAO;
	
	/**
	 * 插入点评回复
	 * @param parameters cmtReply
	 * @return 表识
	 */
	@Override
	public long insert(CmtReplyVO cmtReply) {
		long id = getCmtReplyDAO().insert(cmtReply);
		//现在的逻辑是审核通过，然后再加回复数
		//cmtCommentDAO.addReply(cmtReply.getCommentId());
		return id;
	}

	/**
	 * 根据条件查询点评回复
	 * @param parameters 查询条件
	 * @return 回复列表
	 */
	@Override
	public List<CmtReplyVO> query(Map<String, Object> parameters) {
		List<CmtReplyVO> list = getCmtReplyDAO().query(parameters);
		if(list.size() > 0){
			return list;
		}else{
			return null;
		}
	}

	 /**
	  * 根据条件查询点评回复数
	  * @param parameters 
	  * @return 
	 */
	@Override
	public long count(Map<String, Object> parameters) {
		return getCmtReplyDAO().count(parameters);
	}

	/**
	  * 修改点评回复审核状态 
	  * @param parameters 
	  * @return 
	 */
	@Override
	public int update(CmtReplyVO cmtReply) {
		return getCmtReplyDAO().update(cmtReply);
	}

	/**
	 * 根据条件查询回复
	 * @param id 查询条件
	 * @return 回复
	 */
	@Override
	public CmtReplyVO queryCmtReplyByKey(long id) {
		return getCmtReplyDAO().queryCmtReplyByKey(id);
	}

	public CmtReplyDAO getCmtReplyDAO() {
		return cmtReplyDAO;
	}

	public void setCmtReplyDAO(CmtReplyDAO cmtReplyDAO) {
		this.cmtReplyDAO = cmtReplyDAO;
	}

    @Override
    public long getCountByReviewStatus(Map<String, Object> parameters) {
       return cmtReplyDAO.queryCountOfCommentReview(parameters);
    }

    @Override
    public List<CmtReplyVO> queryCmtReplyByReviewStatus(Map<String, Object> parameters) {
      return cmtReplyDAO.selectParamByComment(parameters);
    }

    @Override
    public void updateCmtReplyByReviewStatus(CmtReplyVO cmtReply) {
        cmtReplyDAO.updateReviewStatusByComment(cmtReply);
    }
}
