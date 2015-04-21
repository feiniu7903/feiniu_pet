package com.client;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.lvmama.clutter.service.IClientProductService;
import com.lvmama.clutter.service.IClientSearchService;

public class TestClientSearch extends TestBase {
	IClientSearchService os = (IClientSearchService) context.getBean("api_com_search_4_0_0");
	public void testRouteSearch() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("fromDest", "上海");
		params.put("toDest", "上海");
		params.put("page", "1");
		params.put("subProductType", "GROUP_LONG,GROUP_FOREIGN,GROUP,SELFHELP_BUS");
		Map<String,Object> object = os.routeSearch(params);
		System.out.println(JSONObject.fromObject(object).toString());
	}
	
	

	public void testPlaceSearch() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("keyword", "wuh");
		params.put("stage", "2");
		params.put("page", "1");
		Map<String,Object> object = os.placeSearch(params);
		
	}
	
	@Test
	public void testrouteAutoComplete() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("fromDest", "上海");
		params.put("keyword", "上海");
		params.put("subProductType", "");
		Map<String,Object> object = os.routeAutoComplete(params);
		System.out.println(object);
		
	}

}
