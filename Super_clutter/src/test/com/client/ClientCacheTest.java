package com.client;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.lvmama.clutter.service.IClientOfflineCacheService;

public class ClientCacheTest extends TestBase {
  IClientOfflineCacheService cache = 	(IClientOfflineCacheService) context.getBean("api_com_cache");
	@Test
	public void testGetPlaceCitiesCache() {
		//cache.getPlaceCitiesCache(new HashMap<String,Object>);
		Map<String,Object> param = new HashMap<String,Object>();
		cache.getPlaceCitiesCache(param);
	}

}
