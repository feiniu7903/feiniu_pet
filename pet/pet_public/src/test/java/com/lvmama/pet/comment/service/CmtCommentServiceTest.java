/**
 * 
 */
package com.lvmama.pet.comment.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.seo.SeoLinksService;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
import com.lvmama.pet.BaseTest;

/**
 * @author liuyi
 *
 */
public class CmtCommentServiceTest extends BaseTest {
	
	@Autowired
	private CmtCommentService cmtCommentService;
	@Autowired
	private SeoLinksService seoLinksService;

	
	@Test
	public void testInsert() throws SQLException, IllegalAccessException, InvocationTargetException{
	    CommonCmtCommentVO cmt = cmtCommentService.getCmtCommentByKey(601098L);
//	    cmt.setReviewStatus(9);
//	    cmt.setCommentId(601098L);
//	    cmtCommentService.updateCmtCommentToReviewStatus(cmt);
//	    Map<String,Object> param = new HashMap<String, Object>();
//	    param.put("reviewStatus", "3");
//	    param.put("startRows", 1);
//	    param.put("endRows", 1);
//	    
//	   long count = cmtCommentService.getCountOfCmtParam(param);
//	    List<CommonCmtCommentVO> cmtList = cmtCommentService.getCmtCommentByParam(null);
//	   System.out.println(cmtList.size());
//	   System.out.println(count);
	  cmt.setReviewStatus(3);
	  if(3==cmt.getReviewStatus()){
	      cmt.setIsHide("Y");
	  }
	  cmtCommentService.updateCmtCommentToReviewStatus(cmt);
	    
	}
	
	
	
	public CmtCommentService getCmtCommentService() {
		return cmtCommentService;
	}

	public void setCmtCommentService(CmtCommentService cmtCommentService) {
		this.cmtCommentService = cmtCommentService;
	}
	
	
	
	
	
	

}
