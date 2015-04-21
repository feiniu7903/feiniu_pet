package com.lvmama.businesses;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.python.antlr.ast.keyword;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.businesses.review.dao.BbsPostDao;
import com.lvmama.comm.businesses.po.review.BbsPreForumPost;
import com.lvmama.comm.businesses.po.review.KeyWord;
import com.lvmama.comm.businesses.po.review.ReviewSendEmail;
import com.lvmama.comm.businesses.service.review.BbsService;
import com.lvmama.comm.businesses.service.review.GuideService;
import com.lvmama.comm.businesses.service.review.InfonewsService;
import com.lvmama.comm.pet.service.review.ReviewSendEmailService;
import com.lvmama.comm.utils.DateUtil;

public class BbsServiceImplTest  extends BaseTest {
	@Autowired
	private BbsService bbsService;
	@Autowired
	private GuideService guideService;
	@Autowired
	private InfonewsService infonewsService;
	@Autowired
	private ReviewSendEmailService reviewSendEmailService;

	@Test
	public void test() throws IOException{
		 Map map=new HashMap();
		 map.put("keyword_b", DateUtil.toDate("2012-1-1", "yyyy-MM-dd"));
		 map.put("keyword_e", DateUtil.toDate("2013-11-1", "yyyy-MM-dd"));
		 map.put("param_b",   DateUtil.toDate("2012-1-1", "yyyy-MM-dd"));
		 map.put("param_e",  DateUtil.toDate("2012-5-1", "yyyy-MM-dd"));
		 map.put("param_r1", 3);
		 map.put("param_r2", 5);
	 Integer i=	reviewSendEmailService.exeScanningCmtReply(map);
	 System.out.print("**************"+i+"*******");
	}
		 
}
