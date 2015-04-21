package com.lvmama.pet.comment.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.service.comment.CmtNewsReplyService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.NewsReplyVO;
import com.lvmama.pet.BaseTest;

public class CmtNewsReplyServiceTest extends BaseTest {
	@Autowired
	private CmtNewsReplyService CmtNewsReplyService;
	
	@Test
	public void testSave() throws SQLException, IllegalAccessException, InvocationTargetException{
		//testInsert
		NewsReplyVO newsReply = new NewsReplyVO();
		newsReply.setCmtNewsId(2L);
		newsReply.setContent("content");
		newsReply.setUserId(123L);
		newsReply.setUserName("summary");
		Long id = CmtNewsReplyService.insert(newsReply);
		Assert.assertNotNull(id);
		
		//testQueryCmtNewsReplyByKey
		NewsReplyVO newsReply_2 = CmtNewsReplyService.queryCmtReplyByKey(id);
		Assert.assertEquals(newsReply_2.getContent(), newsReply.getContent());
		Assert.assertEquals(newsReply_2.getCmtNewsId(), newsReply.getCmtNewsId());
		Assert.assertEquals(newsReply_2.getUserId(), newsReply.getUserId());
		
		//testUpdate
		newsReply_2.setIsAudit(Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name());
		CmtNewsReplyService.update(newsReply_2);
		
		//testCount
		Map<String, Object> parametes = new HashMap<String, Object>();
		parametes.put("cmtNewsId", 2L);
		parametes.put("id", id);
		Long count = CmtNewsReplyService.count(parametes);
		Assert.assertEquals(1, count.longValue());
		
		//testQuery
		List<NewsReplyVO> list = CmtNewsReplyService.query(parametes);
		Assert.assertEquals(list.size(), 1);
		Assert.assertEquals("content", list.get(0).getContent());
		Assert.assertEquals(123, list.get(0).getUserId().longValue());
		Assert.assertEquals(Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name(), list.get(0).getIsAudit());
		
	}

	public CmtNewsReplyService getCmtNewsReplyService() {
		return CmtNewsReplyService;
	}
	public void setCmtNewsReplyService(CmtNewsReplyService CmtNewsReplyService) {
		this.CmtNewsReplyService = CmtNewsReplyService;
	}

}
