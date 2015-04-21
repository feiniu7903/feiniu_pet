package com.lvmama.front.web.shop;

import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.service.seo.RecommendInfoService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.front.web.BaseAction;

@Results({
	@Result(name="brandList",location="/WEB-INF/pages/shop/brandList.ftl",type="freemarker")
})
public class ShopBrandList extends BaseAction {

	/**
	 * 序列
	 */
	private static final long serialVersionUID = 2731788961728799906L;
	/**
	 * 首页推荐商品
	 */
	private Map<String, List<RecommendInfo>> shopBrandListMap;
	private List<RecommendInfo> brandList;
	/**
	 * SUPER后台推送
	 */
	protected RecommendInfoService recommendInfoService;
	// pcadmin后台推荐块：热门景点排行
	private long blockId = 16127; // 主块ID

	private String station = "LP"; // 站点

	private Page<RecommendInfo> page = new Page<RecommendInfo>(0);
	private long currentPage = 1L;
	private long pageSize = 10L;
	
	/**
	 * 积分商城首页
	 * @return
	 * @throws Exception
	 */
	@Action("/shop/brandList")
	public String index()  throws Exception {
		List<RecommendInfo> mapList = null;
		shopBrandListMap = getRecProductByBlockIdAndStationFromCache();
		if(shopBrandListMap != null){
			mapList = shopBrandListMap.get("LP_brandList");
		}
		
		// 分页逻辑
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		if(mapList != null){
			page.setTotalResultSize(mapList.size());
		}else{
			page.setTotalResultSize(0);
		}
		
		brandList = mapList.subList((int)page.getStartRows() - 1, (int)page.getEndRows());
		page.setItems(brandList);
		
		if (page.getItems().size() > 0) {
			this.page.setUrl("/shop/brandList.do?currentPage=");
		}
		return "brandList";
	}
	
	/**
	 * 从cache(1天)获取近期口碑榜,景区标签
	 */
	@SuppressWarnings("unchecked")
	private Map<String, List<RecommendInfo>> getRecProductByBlockIdAndStationFromCache() {
		Object cache = MemcachedUtil.getInstance().get("SHOP_RECOMMEND_INFO");
		if (null != cache) {
			LOG.debug("从MemCache中获取近期口碑榜列表");
		} else {
			LOG.debug("MemCache中获取近期口碑榜列表不存在或已经过期，需要重新获取");
			cache = recommendInfoService.getRecommendInfoByParentBlockIdAndPageChannel(blockId, station);
			if(cache != null)
			{
				MemcachedUtil.getInstance().set("SHOP_RECOMMEND_INFO", 24 * 60 * 60, cache);
			}
		}
		return (Map<String, List<RecommendInfo>>) cache;
	}
	
	public List<RecommendInfo> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<RecommendInfo> brandList) {
		this.brandList = brandList;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public void setRecommendInfoService(RecommendInfoService recommendInfoService) {
		this.recommendInfoService = recommendInfoService;
	}

	public Page<RecommendInfo> getPage() {
		return page;
	}

	public void setPage(Page<RecommendInfo> page) {
		this.page = page;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getBlockId() {
		return blockId;
	}

	public void setBlockId(long blockId) {
		this.blockId = blockId;
	}

}
