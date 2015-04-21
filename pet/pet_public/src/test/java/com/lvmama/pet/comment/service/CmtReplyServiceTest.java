/**
 * 
 */
package com.lvmama.pet.comment.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.service.comment.CmtReplyService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtReplyVO;
import com.lvmama.pet.BaseTest;

/**
 * @author liuyi
 *
 */
public class CmtReplyServiceTest extends BaseTest {
	
	
	@Autowired
	private CmtReplyService cmtReplyService;
	
	@Test
	public void testInsert() throws SQLException, IllegalAccessException, InvocationTargetException{
		
//		CmtReplyVO cmtReplyVO = new CmtReplyVO();
//		cmtReplyVO.setCommentId(763264l);
//		cmtReplyVO.setContent("回复测试");
//		cmtReplyVO.setUserId(1718l);
//		cmtReplyVO.setUserName("jackjoesh2");
//		cmtReplyVO.setReplyType(Constant.CMT_REPLY_TYPE.CUSTOMER.name());
//		//cmtReplyVO.s
//		
//		Long replyId = cmtReplyService.insert(cmtReplyVO);
//		cmtReplyVO = cmtReplyService.queryCmtReplyByKey(replyId);
//		
//		Assert.assertEquals(replyId, cmtReplyVO.getReplyId());
//		Assert.assertEquals("回复测试!", cmtReplyVO.getContent());
	    Map<String, Object> param = new HashMap<String, Object>();
//	    param.put("startRows",1);
//        param.put("endRows",10);
        param.put("reviewStatus",3);
        param.put("startDate","Tue Sep 18 00:00:00 CST 2012");
        param.put("endDate", "Wed Sep 18 23:59:59 CST 2013");
        //startDate=Tue Sep 18 00:00:00 CST 2012, reviewStatus=3, endDate=Wed Sep 18 23:59:59 CST 2013
	    
//	    List<CmtReplyVO> CmtReplyList=cmtReplyService.queryCmtReplyByReviewStatus(param);
//		for (int i = 0; i < CmtReplyList.size(); i++) {
////            System.out.println(CmtReplyList.get(i).getCommentId());
//        }
        long count = cmtReplyService.getCountByReviewStatus(param);
        
		System.out.println(count);
	}

    public CmtReplyService getCmtReplyService() {
        return cmtReplyService;
    }

    public void setCmtReplyService(CmtReplyService cmtReplyService) {
        this.cmtReplyService = cmtReplyService;
    }

}
