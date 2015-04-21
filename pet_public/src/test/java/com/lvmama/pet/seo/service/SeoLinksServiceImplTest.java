package com.lvmama.pet.seo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.seo.SeoLinks;
import com.lvmama.comm.pet.service.seo.SeoLinksService;
import com.lvmama.pet.BaseTest;
public class SeoLinksServiceImplTest extends BaseTest {
	@Autowired
	private SeoLinksService seoLinksService;
	@Test
	public void testInsert() {
		
		SeoLinks seoLinks=new SeoLinks();
		seoLinks.setLinkName("钟双喜");
		seoLinks.setLinkUrl("http://www.lvmama.com/zhongshuangxi");
		seoLinks.setPlaceId((long) 14L);
		seoLinksService.insert(seoLinks);
	}
	@Test
	public void testBatchInsert() {
		List<SeoLinks> list=new ArrayList<SeoLinks>();
		
		long max=1000L;
		for(long i=1;i<=max;i++){
			SeoLinks seoLinks=new SeoLinks();
			seoLinks.setLinkName("钟双喜");
			seoLinks.setLinkUrl("http://www.lvmama.com/zhongshuangxi");
			seoLinks.setPlaceId(i);
			list.add(seoLinks);
		}
		seoLinksService.batchInsert(list);
	}
	@Test
	public void testBatchDeleteByParam(){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("placeName", "宣城");
		List<SeoLinks> list=seoLinksService.batchQuerySeoLinksByParam(map);
		for(SeoLinks s:list){
			System.out.println(s.getPlaceName());
		}
		if(null!=list)seoLinksService.batchDeleteByParam(map);
	}
	/**
	 * @return the seoLinksService
	 */
	public SeoLinksService getSeoLinksService() {
		return seoLinksService;
	}
	/**
	 * @param seoLinksService the seoLinksService to set
	 */
	public void setSeoLinksService(SeoLinksService seoLinksService) {
		this.seoLinksService = seoLinksService;
	} 
	
}
