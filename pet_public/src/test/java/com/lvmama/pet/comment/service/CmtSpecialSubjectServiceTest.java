package com.lvmama.pet.comment.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.service.comment.CmtSpecialSubjectService;
import com.lvmama.comm.vo.comment.CmtSpecialSubjectVO;
import com.lvmama.pet.BaseTest;

public class CmtSpecialSubjectServiceTest extends BaseTest {
	
	
	@Autowired
	private CmtSpecialSubjectService cmtSpecialSubjectService;
	
	@Test
	public void testInsert() throws SQLException, IllegalAccessException, InvocationTargetException{
		 
		CmtSpecialSubjectVO vo = new CmtSpecialSubjectVO();
		vo.setPic("pic");
		vo.setPicUrl("picUrl");
		vo.setSummary("summary");
		vo.setTitle("title");
		vo.setUrl("url");
		vo.setVersionNum("1");
		
		Long id = cmtSpecialSubjectService.save(vo);
		CmtSpecialSubjectVO vo_1 = cmtSpecialSubjectService.queryByPk(id);
		
		Assert.assertEquals("summary", vo.getSummary());
		Assert.assertEquals("picUrl", vo.getPicUrl());
		Assert.assertEquals("title", vo.getTitle());
		Assert.assertEquals("1", vo.getVersionNum());
		//Assert.assertNotNull(vo.getCreatedTime());
		
		Map<String, Object> parametes = new HashMap<String, Object>();
		parametes.put("id", id);
		Long count = cmtSpecialSubjectService.count(parametes);
		Assert.assertEquals(1, count.longValue());
		
		List<CmtSpecialSubjectVO> list = cmtSpecialSubjectService.query(parametes);
		
		Assert.assertEquals("summary", list.get(0).getSummary());
		Assert.assertEquals("picUrl", list.get(0).getPicUrl());
		Assert.assertEquals("title", list.get(0).getTitle());
		Assert.assertEquals("1", list.get(0).getVersionNum());
		//Assert.assertNotNull(list.get(0).getCreatedTime());
		
	}

}