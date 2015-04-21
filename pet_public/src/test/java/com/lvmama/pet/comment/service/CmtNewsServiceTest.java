package com.lvmama.pet.comment.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.service.comment.CmtNewsService;
import com.lvmama.comm.vo.comment.CmtNewsVO;
import com.lvmama.pet.BaseTest;

public class CmtNewsServiceTest extends BaseTest {
	
	@Autowired
	private CmtNewsService cmtNewsService;

	@Test
	public void testSave() throws SQLException, IllegalAccessException, InvocationTargetException{
		//testSave
		CmtNewsVO news = new CmtNewsVO();
		news.setPic("pic.jpg");
		news.setPicUrl("picUrl");
		news.setSummary("summary");
		news.setTitle("title");
		news.setUrl("url");
		Long id = cmtNewsService.save(news);
		Assert.assertNotNull(id);
		
		//testQueryByPk
		CmtNewsVO news2 = cmtNewsService.queryByPk(id);
		Assert.assertEquals(news2.getUrl(), news.getUrl());
		Assert.assertEquals(news2.getPic(), news.getPic());
		
		//testUpdate
		news2.setRelatePlaceId(28L);
		news2.setTitle("titleUpdate");
		cmtNewsService.save(news2);
		
		//testCount
		Map<String, Object> parametes = new HashMap<String, Object>();
		parametes.put("relatePlaceId", 28L);
		parametes.put("id", id);
		Long count = cmtNewsService.count(parametes);
		Assert.assertEquals(1, count.longValue());
		
		//testQuery
		List<CmtNewsVO> list = cmtNewsService.query(parametes);
		Assert.assertEquals(list.size(), 1);
		Assert.assertEquals("pic.jpg", list.get(0).getPic());
		Assert.assertEquals("url", list.get(0).getUrl());
		
		//testSave
		CmtNewsVO news_2 = new CmtNewsVO();
		news_2.setPic("pic_2");
		news_2.setPicUrl("picUrl_2");
		news_2.setRelatePlaceId(29L);
		news_2.setSummary("summary_2");
		news_2.setTitle("title_2");
		news_2.setUrl("url_2");
		Long id_2 = cmtNewsService.save(news_2);
		
		//testqQueryReviewBack
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("latestId", id_2);
		List<CmtNewsVO> list_2 = cmtNewsService.queryReview(para);
		Assert.assertNotNull(list_2.size());
		
	}


	public CmtNewsService getCmtNewsService() {
		return cmtNewsService;
	}
	public void setCmtNewsService(CmtNewsService cmtNewsService) {
		this.cmtNewsService = cmtNewsService;
	}
}
