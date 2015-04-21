package com.lvmama.report.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.service.usertags.UserTagsSearchLogsService;
import com.lvmama.report.dao.UserTagsSearchLogsDAO;
import com.lvmama.report.dao.WeekAnalysisTVDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-report-beans.xml" })
public class WeekAnalysisTVDAOTest {

	@Resource
	private WeekAnalysisTVDAO weekAnalysisTVDAO;
	@Autowired
	private UserTagsSearchLogsService userTagsSearchLogsService;
	@Autowired 
	private UserTagsSearchLogsDAO userTagsSearchLogsDAO;
	@Test
	public void testsdfas() {
		Map map=new HashedMap();
		System.out.println(userTagsSearchLogsDAO.countUserTagsLogByParam(map));
		
	}
}
