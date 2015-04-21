package com.lvmama.businesses.review.dao;

import java.util.Map;

import com.lvmama.comm.businesses.po.review.ReviewSendEmail;

public class BbsReviewKeywordDao extends CommReviewKeywordDao{
 
	private static final long serialVersionUID = 1346453434L;

	public Integer exeScanningForumPost(Map map) {
		 super.update("reviewproduce.exeScanningForumPost",map);
		 return (Integer)map.get("count");
 	}

	public Integer exeScanningForumThread(Map map) {
		 super.update("reviewproduce.exeScanningForumThread",map);
		 return (Integer)map.get("count");
 	}

	
}
