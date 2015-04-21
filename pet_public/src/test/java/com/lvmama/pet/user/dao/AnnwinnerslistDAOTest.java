/**
 * 
 */
package com.lvmama.pet.user.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import oracle.net.aso.k;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import C.V;

import com.lvmama.comm.pet.po.user.Annhongbao;
import com.lvmama.comm.pet.po.user.Annwinnerslist;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.pet.conn.BaseDAOTest;

/**
 * @author liuyi
 *
 */
@ContextConfiguration(locations = { "classpath:applicationContext-pet-public-beans.xml" })
@Transactional 
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class AnnwinnerslistDAOTest extends BaseDAOTest {
	
	 
	@Autowired
	private AnnwinnerslistDAO annwinnerslistDAO;
	
	@Test
	public void testSave() throws Exception {
		Annwinnerslist annwinnerlist=new Annwinnerslist();
		annwinnerlist.setFlag(1L);
		annwinnerlist.setAddress("ok");
 		 annwinnerlist.setHuojiangTime(DateUtil.getTodayDate());
 		 annwinnerlist.setCreateDate(null);
		annwinnerslistDAO.save(annwinnerlist);
	}
	
	@Test
	public void testQuery() throws Exception {
 	    Map map=new HashMap();
	    map.put("senddate", DateUtil.getTodayDate());
 		Assert.assertNotNull(annwinnerslistDAO.queryAnnhongbaoByParam(map));
 		
 		Annhongbao ann=new Annhongbao();
 		ann.setSenddate(DateUtil.stringToDate("2014-5-9", "yyyy-MM-dd"));
  		ann.setTwoShengyu(1111L);
 		annwinnerslistDAO.updateAnnhongbao(ann);
	}

}
