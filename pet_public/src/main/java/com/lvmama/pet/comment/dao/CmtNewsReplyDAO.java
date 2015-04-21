package com.lvmama.pet.comment.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.vo.comment.NewsReplyVO;

public class CmtNewsReplyDAO extends BaseIbatisDAO {

	public Long insert(final NewsReplyVO newsReply) {
		super.insert("CMT_NEWS_REPLY.insert", newsReply);
		return newsReply.getId();
	}

	@SuppressWarnings("unchecked")
	public List<NewsReplyVO> query(final Map<String, Object> parameters) {
		return (List<NewsReplyVO>) super.queryForList(
				"CMT_NEWS_REPLY.query", parameters);
	}

	public int update(final NewsReplyVO newsReply) {
		return super.update("CMT_NEWS_REPLY.update",
				newsReply);
	}

	public Long count(Map<String, Object> parameters) {
		return (Long) super.queryForObject(
				"CMT_NEWS_REPLY.count", parameters);
	}

	public NewsReplyVO queryCmtNewsReplyByKey(long id) {
		return (NewsReplyVO) super.queryForObject(
				"CMT_NEWS_REPLY.queryByKey", id);
	}

}