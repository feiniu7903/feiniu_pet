package com.lvmama.pet.comment.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.service.comment.CmtTitleStatistisService;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.pet.BaseTest;

public class CmtTitleStatistisServiceTest extends BaseTest {
	
	@Autowired
	private CmtTitleStatistisService cmtTitleStatistisService;
	
	@Test
	public void testQuery() throws SQLException, IllegalAccessException, InvocationTargetException{
		 
		//testGetCommentStatisticsCount
		Map<String, Object> parametes = new HashMap<String, Object>();
		parametes.put("placeId", 122L);
		Long count = cmtTitleStatistisService.getCommentStatisticsCount(parametes);
		Assert.assertEquals(1, count.longValue());
		
		//testGetCommentStatisticsList
		List<CmtTitleStatisticsVO> list = cmtTitleStatistisService.getCommentStatisticsList(parametes);
		Assert.assertEquals(list.size(), 1);
		
		//testMergeStatisticsPlaceLatitudeAvgScore
		cmtTitleStatistisService.mergeStatisticsPlaceScore();
		cmtTitleStatistisService.mergeStatisticsProductScore();
	 
	}

	public CmtTitleStatistisService getCmtTitleStatistisService() {
		return cmtTitleStatistisService;
	}

	public void setCmtTitleStatistisService(
			CmtTitleStatistisService cmtTitleStatistisService) {
		this.cmtTitleStatistisService = cmtTitleStatistisService;
	}

}
