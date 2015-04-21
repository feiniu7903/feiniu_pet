package com.lvmama.businesses.review.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.businesses.po.review.GlArticle;

public class GlArticleDao extends BaseIbatisDAO{

	public List<GlArticle> queryGlArticleByParam(Map param) {
 		return super.queryForList("GLARTICLE.queryByParam",param);
	}

	public long countForGlArticle(Map param) {
		return (Long) super.queryForObject("GLARTICLE.count",param);
	}

	public void updateForBbsGlArticle(Map<String, Object> param) {
		 super.update("GLARTICLE.update",param);
	}

	public GlArticle queryForBbsGlArticleById(Integer articleid) {
		return (GlArticle) super.queryForObject("GLARTICLE.queryForBbsGlArticleById",articleid);
	}

}
