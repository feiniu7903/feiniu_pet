package com.client;


import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.lvmama.clutter.service.IClientSearchService;

public class ClientPlaceSearch extends TestBase{
	IClientSearchService search = (IClientSearchService)context.getBean("api_com_search");
	
	
	public void testPlaceSearch() {
		Map<String,Object> params  = new HashMap<String, Object>();
		params.put("keyword", "上海");
		params.put("subject", "主题乐园");
		params.put("page", "1");
		params.put("stage", "2");
		Map<String,Object> map  =  search.placeSearch(params);
		System.out.println(map);
	}

	@Test
	public void testplaceAutoComplete(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("keyword", "上海");
		Map<String,Object> map = 	search.placeAutoComplete(params);
		System.out.println(map);
	}
}
