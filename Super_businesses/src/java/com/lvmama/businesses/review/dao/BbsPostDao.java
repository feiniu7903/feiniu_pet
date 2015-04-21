package com.lvmama.businesses.review.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.businesses.po.review.BbsPreForumPost;

public class BbsPostDao extends BaseIbatisDAO{
 
	private static final long serialVersionUID = 13464534L;


	public BbsPreForumPost queryForBbsPreForumPostById(int pid) {
		return (BbsPreForumPost) super.queryForObject("BBSPREFORUMPOST.select",pid);
	}

	public  List<BbsPreForumPost> queryBbsPreForumPostByParam(Map param) {
		 
		return super.queryForList("BBSPREFORUMPOST.queryBbsPreForumPostByParam", param);
	}


	public long count(Map param) {
 		return (Long) super.queryForObject("BBSPREFORUMPOST.count",param);
	}


	public void update(Map param) {
		super.update("BBSPREFORUMPOST.update",param);
	}



	 
}
