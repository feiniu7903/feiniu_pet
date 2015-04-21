package com.lvmama.pet.comment.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.service.comment.CmtNewsReplyService;
import com.lvmama.comm.vo.comment.NewsReplyVO;
import com.lvmama.pet.comment.dao.CmtNewsReplyDAO;

public class CmtNewsReplyServiceImpl implements CmtNewsReplyService {
	/**
	 * 小驴说事的DAO
	 */
	private CmtNewsReplyDAO cmtNewsReplyDAO;

	@Override
	public long insert(final NewsReplyVO newsReply) {
		return cmtNewsReplyDAO.insert(newsReply);
	}
	
	@Override
	public List<NewsReplyVO> query(final Map<String, Object> parameters) {
		return cmtNewsReplyDAO.query(parameters);
	}

	@Override
	public int update(final NewsReplyVO newsReply) {
		return cmtNewsReplyDAO.update(newsReply);
	}

	@Override
	public Long count(final Map<String, Object> parameters) {
		return cmtNewsReplyDAO.count(parameters);
	}

	@Override
	public NewsReplyVO queryCmtReplyByKey(final long id) {
		return cmtNewsReplyDAO.queryCmtNewsReplyByKey(id);
	}

	public void setCmtNewsReplyDAO(final CmtNewsReplyDAO cmtNewsReplyDAO) {
		this.cmtNewsReplyDAO = cmtNewsReplyDAO;
	}

}
