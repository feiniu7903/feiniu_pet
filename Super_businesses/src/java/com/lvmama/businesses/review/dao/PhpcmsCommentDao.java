package com.lvmama.businesses.review.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.businesses.po.review.GlArticle;
import com.lvmama.comm.businesses.po.review.PhpcmsComment;

public class PhpcmsCommentDao extends BaseIbatisDAO{

	public List<PhpcmsComment> queryByParam(Map param) {
 		return super.queryForList("PHPCMSCOMMENT.queryByParam",param);
	}

	public long count(Map param) {
		return (Long) super.queryForObject("PHPCMSCOMMENT.count",param);
	}

	public void update(Map<String, Object> param) {
		 super.update("PHPCMSCOMMENT.update",param);
	}

	public PhpcmsComment queryForPhpcmsCommentById(Integer commentid) {
 		return  (PhpcmsComment)super.queryForObject("PHPCMSCOMMENT.select",commentid);
	}


}
