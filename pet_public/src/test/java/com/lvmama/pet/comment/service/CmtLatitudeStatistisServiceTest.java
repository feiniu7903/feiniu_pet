package com.lvmama.pet.comment.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.comment.CmtLatitudeStatistics;
import com.lvmama.comm.pet.service.comment.CmtLatitudeStatistisService;
import com.lvmama.pet.BaseTest;

public class CmtLatitudeStatistisServiceTest extends BaseTest {
	
	@Autowired
	private CmtLatitudeStatistisService cmtLatitudeStatistisService;
	
	@Test
	public void testQuery() throws SQLException, IllegalAccessException, InvocationTargetException{
		 
		//testQuery
		Map<String, Object> parametes = new HashMap<String, Object>();
		parametes.put("placeId", 122L);
		
		//testGetLatitudeStatisticsList
		List<CmtLatitudeStatistics> list = cmtLatitudeStatistisService.getLatitudeStatisticsList(parametes);
		Assert.assertEquals(list.size(), 5);
				
		//testMergeStatisticsPlaceLatitudeAvgScore
		cmtLatitudeStatistisService.mergeStatisticsPlaceLatitudeAvgScore();
		cmtLatitudeStatistisService.mergeStatisticsProductLatitudeAvgScore();
	 
		//testGetLatitudeStatisticsList
		parametes.clear();
		parametes.put("productId", 20584L);
		List<CmtLatitudeStatistics> list_2 = cmtLatitudeStatistisService.getLatitudeStatisticsList(parametes);
		Assert.assertEquals(list_2.size(), 5);
		
		//testGetFourAvgLatitudeScoreList
		List<CmtLatitudeStatistics> list_3 = cmtLatitudeStatistisService.getFourAvgLatitudeScoreList(parametes);
		Assert.assertEquals(list_3.size(), 4);
		
	}

	public CmtLatitudeStatistisService getCmtLatitudeStatistisService() {
		return cmtLatitudeStatistisService;
	}

	public void setCmtLatitudeStatistisService(
			CmtLatitudeStatistisService cmtLatitudeStatistisService) {
		this.cmtLatitudeStatistisService = cmtLatitudeStatistisService;
	}

}
	