package com.lvmama.tnt;

import java.net.MalformedURLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.caucho.hessian.client.HessianProxyFactory;
import com.lvmama.tnt.search.service.SearchService;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.vst.api.search.service.VstSearchService;
import com.lvmama.vst.api.search.vo.SearchResultVo;
import com.lvmama.vst.api.vo.ResultHandleT;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class SearchServiceTest {
	
	String url = "http://10.2.1.25:8080/vst_pet/remoting/vstSearchServiceRemote"; 
	@Autowired
	VstSearchService searchService;
	@Autowired
	SearchService tntSearchService;
	@Test
	public void searchRemoteTest() {
		HessianProxyFactory factory = new HessianProxyFactory();
		try {
			searchService = (VstSearchService) factory.create(VstSearchService.class, url);
			ResultHandleT<SearchResultVo> resultHandleT = searchService.searchTicket("上海-上海");
			System.out.println("resultHandleT: " + resultHandleT.getReturnContent().getPageConfig().getItems().get(0));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testSearch() throws Exception{
		System.out.println(tntSearchService.searchTicket("上海-上海", new TntUser(0L)));
	}
}
