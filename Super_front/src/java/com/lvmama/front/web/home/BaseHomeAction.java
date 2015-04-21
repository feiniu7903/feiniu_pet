package com.lvmama.front.web.home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lvmama.comm.bee.service.GroupDreamService;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.po.seo.SeoIndexPage;
import com.lvmama.comm.pet.service.favor.FavorService;
import com.lvmama.comm.pet.service.prod.ProdContainerProductService;
import com.lvmama.comm.pet.service.search.PlaceSearchInfoService;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.pet.service.seo.RecommendInfoService;
import com.lvmama.comm.pet.service.seo.SeoIndexPageService;
import com.lvmama.comm.utils.IPMap;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.homePage.ManageMemMapUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.front.web.BaseAction;

public abstract class BaseHomeAction extends BaseAction {
	private static final long serialVersionUID = 4766169957721320263L;
	protected SeoIndexPage comSeoIndexPage;
	protected GroupDreamService groupDreamService;
	protected PlaceSearchInfoService placeSearchInfoService;
	protected ProdContainerProductService prodContainerProductService;
	protected ProductSearchInfoService productSearchInfoService;
	private SeoIndexPageService seoIndexPageService;
	protected RecommendInfoService recommendInfoService;
	private String cookieAreaLocation = (String) this.getRequest().getAttribute(Constant.IP_AREA_LOCATION);
	private Long cookieIpFormPlaceId = (Long) (this.getRequest().getAttribute(Constant.IP_FROM_PLACE_ID));
	private Long searchBlockId;
	protected PageService pageService;
	protected FavorService favorService;

	protected Map<String, Object> map = new HashMap<String, Object>();

	public Long getIp() {
		String ip = getRequest().getHeader("X-Real-IP");
		if (ip == null) {
			ip = getRequest().getRemoteAddr();
		}
		return IPMap.stringToLong(ip);
	}

	protected void initSeoIndexPage(String channel) {
		String key = "getSeoIndexPageBychannelId_" + channel;
		comSeoIndexPage = (SeoIndexPage) MemcachedUtil.getInstance().get(key);
		if (comSeoIndexPage == null) {
			comSeoIndexPage=seoIndexPageService.getSeoIndexPageByPageCode(channel);
			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR,comSeoIndexPage);
		}
	}
	
	/**
	 * 获得各个频道的推荐的值
	 */
	protected void putRecommentInfoResult(String channelPage, Long commonBlockId, String containerCode, Long fromPlaceId) {
		String key = "base_putRecommentInfoResult_"+channelPage+"_"+commonBlockId+"_"+containerCode+"_"+fromPlaceId;
		 Map<String, List<RecommendInfo>> recommendInfoMap = (Map<String, List<RecommendInfo>>)MemcachedUtil.getInstance().get(key);
 		if (recommendInfoMap ==null) {
 			LOG.info("MemCache beginning:" + key);
			recommendInfoMap = recommendInfoService.getRecommendInfoMap(containerCode, commonBlockId, fromPlaceId, channelPage);
			MemcachedUtil.getInstance().set(key, MemcachedUtil.TWO_HOUR,recommendInfoMap); 
			if (null == MemcachedUtil.getInstance().get(key)) {
				LOG.info("SAVE MemCache Failure:" + key);
			}
		}
		map.put("recommendInfoMainList", recommendInfoMap);
	}
	
	/**
	 * 获取各个频道页所推荐的内容
	 * @param channelPage 频道名，对于recommend_block表的PAGE_CHANNEL字段
	 * @param defaultCommonBlockId 默认的块号，代表着全国统一推荐位的块号
	 * @param containerCode 频道页在容器中的标识代码
	 * @param fromPlaceId 当前用户所选择的出发地
	 * @param defaultFromPlaceId 默认选择出发地。 当fromPlaceId所在区域无相关数据时，需要使用默认出发地的数据填充
	 */
	protected void putRecommentInfoResult(final String channelPage, final Long defaultCommonBlockId, final String containerCode, final Long fromPlaceId, final Long defaultFromPlaceId) {
		String key = "base_putRecommentInfoResult_" + channelPage + "_" + defaultCommonBlockId + "_" + containerCode + "_" + fromPlaceId;
		Map<String, List<RecommendInfo>> recommendInfoMap = (Map<String, List<RecommendInfo>>) MemcachedUtil.getInstance().get(key);
		if (recommendInfoMap == null) {
			LOG.info("MemCache beginning:" + key);
			recommendInfoMap = recommendInfoService.getRecommendInfoMap(containerCode, defaultCommonBlockId, fromPlaceId, channelPage);
			if (null != defaultFromPlaceId) {
				Map<String, List<RecommendInfo>> defaultRecommendInfoMap = recommendInfoService.getRecommendInfoMap(containerCode, null, defaultFromPlaceId, channelPage);
				//将默认出发地存在的数据，但当前出发地不存在的数据进行复制。以便分公司不维护数据时，使用总部的数据
				if (null != defaultRecommendInfoMap && !defaultRecommendInfoMap.isEmpty()) {  
					Set<String> keys = defaultRecommendInfoMap.keySet();
					for (String _key : keys) {
						List<RecommendInfo> _currentValue = recommendInfoMap.get(_key); 
						List<RecommendInfo> _defaultValue = defaultRecommendInfoMap.get(_key);
						if ((null == _currentValue || _currentValue.isEmpty()) && null != _defaultValue && !_defaultValue.isEmpty()) {
							recommendInfoMap.put(_key, _defaultValue);
						}
					}
				}
			}
			try {
				resortData(recommendInfoMap);
			} catch (Exception e) {

			}
			
			MemcachedUtil.getInstance().set(key, MemcachedUtil.TWO_HOUR,recommendInfoMap);
			if (null == MemcachedUtil.getInstance().get(key)) {
				LOG.info("SAVE MemCache Failure:" + key);
			}
		}
		map.put("recommendInfoMainList", recommendInfoMap);		
	}
	
	@Deprecated
	protected void putRecommentInfoResult2(final String channelPage, final Long defaultCommonBlockId, final String containerCode, final Long fromPlaceId, final Long defaultFromPlaceId) {
		String key = "base_putRecommentInfoResult_" + channelPage + "_" + defaultCommonBlockId + "_" + containerCode + "_" + fromPlaceId;
		
		Map<String, List<RecommendInfo>> recommendInfoMap = (Map<String, List<RecommendInfo>>) ManageMemMapUtils.getInstance().get(key);
		
		if (recommendInfoMap == null) {
			recommendInfoMap = recommendInfoService.getRecommendInfoMap(containerCode, defaultCommonBlockId, fromPlaceId, channelPage);
			
			if (null != defaultFromPlaceId) {
				Map<String, List<RecommendInfo>> defaultRecommendInfoMap = recommendInfoService.getRecommendInfoMap(containerCode, null, defaultFromPlaceId, channelPage);
				//将默认出发地存在的数据，但当前出发地不存在的数据进行复制。以便分公司不维护数据时，使用总部的数据
				if (null != defaultRecommendInfoMap && !defaultRecommendInfoMap.isEmpty()) {  
					Set<String> keys = defaultRecommendInfoMap.keySet();
					for (String _key : keys) {
						List<RecommendInfo> _currentValue = recommendInfoMap.get(_key); 
						List<RecommendInfo> _defaultValue = defaultRecommendInfoMap.get(_key);
						if ((null == _currentValue || _currentValue.isEmpty()) && null != _defaultValue && !_defaultValue.isEmpty()) {
							recommendInfoMap.put(_key, _defaultValue);
						}
					}
				}
			}
			try {
				resortData(recommendInfoMap);
			} catch (Exception e) {

			}
			
			ManageMemMapUtils.getInstance().set(key, MemcachedUtil.TWO_HOUR,recommendInfoMap);
			if (null == ManageMemMapUtils.getInstance().get(key)) {
				LOG.info("SAVE MemCache Failure:" + key);
			}
		}
		map.put("recommendInfoMainList", recommendInfoMap);		
	}
	
	/**
	 * 对从数据库获取的推荐的数据进行后期的加工
	 * @param recommendInfoMap 推荐的数据
	 * 对于某些频道，需要对某些块的数据进行类似排序等后期加工操作，则应该在各频道的Action中重载此方法实现。
	 */
	protected void resortData(final Map<String, List<RecommendInfo>> recommendInfoMap) {
		
	}
 
	public RecommendInfoService getRecommendInfoService() {
		return recommendInfoService;
	}

	public void setRecommendInfoService(RecommendInfoService recommendInfoService) {
		this.recommendInfoService = recommendInfoService;
	}

	public String getCookieAreaLocation() {
		return cookieAreaLocation;
	}

	public SeoIndexPage getComSeoIndexPage() {
		return comSeoIndexPage;
	}

	public void setComSeoIndexPage(SeoIndexPage comSeoIndexPage) {
		this.comSeoIndexPage = comSeoIndexPage;
	}

	public Long getSearchBlockId() {
		return searchBlockId;
	}

	/**
	 * @return the cookieIpFormPlaceId
	 */
	public Long getCookieIpFormPlaceId() {
		return cookieIpFormPlaceId;
	}

	public Map<String, Object> getMap() {
		return map;
	}
	

	public SeoIndexPageService getSeoIndexPageService() {
		return seoIndexPageService;
	}

	public void setSeoIndexPageService(SeoIndexPageService seoIndexPageService) {
		this.seoIndexPageService = seoIndexPageService;
	}

	public ProdContainerProductService getProdContainerProductService() {
		return prodContainerProductService;
	}

	public void setProdContainerProductService(
			ProdContainerProductService prodContainerProductService) {
		this.prodContainerProductService = prodContainerProductService;
	}

	public void setPlaceSearchInfoService(
			PlaceSearchInfoService placeSearchInfoService) {
		this.placeSearchInfoService = placeSearchInfoService;
	}

	public ProductSearchInfoService getProductSearchInfoService() {
		return productSearchInfoService;
	}

	public void setProductSearchInfoService(
			ProductSearchInfoService productSearchInfoService) {
		this.productSearchInfoService = productSearchInfoService;
	}

	public void setGroupDreamService(GroupDreamService groupDreamService) {
		this.groupDreamService = groupDreamService;
	}
    /**
	 * AB测试方法
	 * @return true 老的版本 false 新的版本
	 * @author:nixianjun 2013-6-17
	 */
	public String getFlagForAB() {
		String id=getRequest().getSession().getId();
		byte[] byId=id.getBytes();
		int t = 0;
		for(byte b:byId){
			t= (t+(int)b);
		}
 		if(t%2==0){
 			return "true";
		}else {
			return "false";
		}
 	}

	public FavorService getFavorService() {
		return favorService;
	}

	public void setFavorService(FavorService favorService) {
		this.favorService = favorService;
	}

	public GroupDreamService getGroupDreamService() {
		return groupDreamService;
	}

	public PageService getPageService() {
		return pageService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}
	
	
}
