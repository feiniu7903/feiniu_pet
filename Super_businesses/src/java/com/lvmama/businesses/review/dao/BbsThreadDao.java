package com.lvmama.businesses.review.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.businesses.po.review.BbsPreForumPost;
import com.lvmama.comm.businesses.po.review.BbsPreForumThread;

public class BbsThreadDao extends BaseIbatisDAO{

	public long countForThread(Map param) {
		 
		return (Long) super.queryForObject("BBSPREFORUMTHREAD.count", param);
	}

	public List<BbsPreForumThread> queryForThreadByParam(Map param) {
		return  super.queryForList("BBSPREFORUMTHREAD.queryByParam", param);
	}

	public void update(Map<String, Object> param) {
		 super.update("BBSPREFORUMTHREAD.update",param);
	}

	public BbsPreForumThread queryForThreadByTid(Integer tid) {
		return   (BbsPreForumThread)super.queryForObject("BBSPREFORUMTHREAD.queryForThreadByTid", tid);
	}
      
}
