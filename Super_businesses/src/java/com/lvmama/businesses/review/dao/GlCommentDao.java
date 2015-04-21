package com.lvmama.businesses.review.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.businesses.po.review.GlArticle;
import com.lvmama.comm.businesses.po.review.GlComment;

public class GlCommentDao extends BaseIbatisDAO{

	public List<GlComment> queryGlCommentByParam(Map param) {
		return super.queryForList("GLCOMMENT.queryByParam",param);
	}

	public long countForGlComment(Map param) {
		return (Long) super.queryForObject("GLCOMMENT.count",param);
	}

	public void updateForGlComment(Map<String, Object> param) {
		super.update("GLCOMMENT.update",param);
	}

	public GlComment queryForGlCommentById(Integer commentid) {
		return  (GlComment) super.queryForObject("GLCOMMENT.queryForGlCommentById",commentid);
	}

}
