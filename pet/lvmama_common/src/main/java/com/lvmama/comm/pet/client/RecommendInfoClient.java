package com.lvmama.comm.pet.client;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.pet.service.seo.RecommendInfoService;
import com.lvmama.comm.pet.vo.ProductList;
import com.lvmama.comm.utils.MemcachedUtil;

public class RecommendInfoClient {
	private RecommendInfoService recommendInfoService;
	private ProductSearchInfoService productSearchInfoService;
	public Map<String, List<RecommendInfo>> getRecommendProductByBlockIdAndStation(Long blockId, String station) {
		Map<String, List<RecommendInfo>> result = null;
		String key = "RecommendProductByBlockIdAndStation" + "_" + blockId + "_" + station;
		Object obj = MemcachedUtil.getInstance().get(key);
		if (obj == null) {
			result = recommendInfoService.getRecommendInfoByParentBlockIdAndPageChannel(blockId, station);
			 if(result != null && result.size() > 0){
				 MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR,result);
			 }
		} else {
			result = (Map<String, List<RecommendInfo>>) obj;
		}
		return result;
	}
	
	public ProductList getProductByPlaceIdAnd4Type(long placeId, String stage, long size, String channel) {
		ProductList productList = null;
		String key = "ProductByPlaceIdAnd4Type" + "_" + placeId + "_" + stage + "_" + size;
		Object obj = MemcachedUtil.getInstance().get(key);
		if (obj == null) {
			productList = productSearchInfoService.getProductByPlaceIdAnd4Type(placeId, size, channel);
			if (productList.isNotNull()) {
				MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR, productList);
			}
		} else {
			productList = (ProductList) obj;
		}
		return productList;
	}

	public List<ProductSearchInfo> getProductByPlaceIdAndType(long placeId, String productType, String stage, String channel) {
		List<ProductSearchInfo> result = null;
		String key = "ProductByPlaceIdAndType" + "_" + placeId + "_" + productType + "_" + stage;
		Object obj = MemcachedUtil.getInstance().get(key);
		if (obj == null) {
			result=productSearchInfoService.getProductByPlaceIdAndType(100,1,placeId,productType,stage,channel);
 			if (result != null) {
				MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR, result);
			}
		} else {
			result = (List<ProductSearchInfo>) obj;
		}
		return result;
	}
	public void setRecommendInfoService(RecommendInfoService recommendInfoService) {
		this.recommendInfoService = recommendInfoService;
	}

	public void setProductSearchInfoService(
			ProductSearchInfoService productSearchInfoService) {
		this.productSearchInfoService = productSearchInfoService;
	}
	
}
