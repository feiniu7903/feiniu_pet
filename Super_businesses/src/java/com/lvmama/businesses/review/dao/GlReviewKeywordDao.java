package com.lvmama.businesses.review.dao;

import java.util.Map;


public class GlReviewKeywordDao extends CommReviewKeywordDao{
 
	private static final long serialVersionUID = 13464534L;
	public Integer exeScanningGlArticle(Map map) {
		 super.update("reviewproduce.exeScanningGlArticle",map);
		 return (Integer)map.get("count");
	}

	public Integer exeScanningGlComment(Map map) {
		 super.update("reviewproduce.exeScanningGlComment",map);
		 return (Integer)map.get("count");
	}
}
