package com.lvmama.front.web.home;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.GroupDreamService;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.front.tools.taglib.PageConfig;

/**
 * 
 * 团购首页Action
 * 
 * @author zuozhengpeng
 * 
 */
@Results({
 @Result(name = "groupIndex", location = "/WEB-INF/pages/group/tuangou.ftl", type = "freemarker"),
	@Result(name = "oldtuangou301Forword",params={"status", "301", "headers.Location", "/tuangou/${pinyin}/${type}-all-all-1"}, type="httpheader")
 })
public class TuangouHomeAction extends ToPlaceOnlyTemplateHomeAction {
	private static final long serialVersionUID = -4949783901374992072L;
	private List<Map<String, Object>> groupPrdList = new ArrayList<Map<String, Object>>();
	private ComPictureService comPictureService;
	private ProdProductPlaceService prodProductPlaceService;
	private GroupDreamService groupDreamService;
	private Map<String, List<RecommendInfo>> groupMap;
	private Map<Long, Object> recommendPrdMap;
	/**
	 * 频道 : 门票=ticket 酒店=hotel 自由行=free 跟团游=group 长途游=long 出境游=foreign
	 **/
	private String type = "all";
	/** 地区筛选 */
	private String city = "all";
	/** 排序参数 ,默认=空 ,热门='hot',价格='price' */
	private String sort = "all";
	/** 当前页数 */
	private int page = 1;
	/** 分页对象 */
	private PageConfig<Map<String, Object>> pageConfig;
	/** 产品每页显示数量 */
	private int pageSize = 15;
	/** 目的地集合 */
	private Map<String, String> cities;
	private Long commonBlockId = 14516L;
	private String channelPage = "tuangou";
	private String containerCode = "TUANGOU_RECOMMEND";
	
	private String pinyin;

	/**
	 * @return the pinyin
	 */
	public String getPinyin() {
		return pinyin;
	}

	@Action("/homePage/tuangou301")
	public String  tiaozhuan301(){
		if("all".equals(this.city)){
			pinyin=city;
 			return "oldtuangou301Forword";
		}
		PlaceSearchInfo place= placeSearchInfoService.getPlaceSearchInfoByPlaceId(Long.valueOf(this.city));
		if(null!=place&&StringUtil.isNotEmptyString(place.getPinYin())){
			pinyin=place.getPinYin();
		}else{
			pinyin="all";
		}			
		return "oldtuangou301Forword";
	}

	/**
	 * 查看今日团购产品
	 */
	@SuppressWarnings("unchecked")
	@Action("/homePage/tuangou")
	public String execute() throws Exception {
		HttpSession session = getRequest().getSession(true);
		if (getFromPlaceId() != null) {
			session.setAttribute("fromPCode", getFromPlaceCode());
			session.setAttribute("fromPid", getFromPlaceId());
			session.setAttribute("fromPName", getFromPlaceName());
		} else if ((Long) session.getAttribute("fromPid") != null) {
			this.fromPlaceCode = (String) session.getAttribute("fromPCode");
			this.fromPlaceId = (Long) session.getAttribute("fromPid");
			this.fromPlaceName = (String) session.getAttribute("fromPName");
		}
		init(Constant.CHANNEL_ID.CH_TUANGOU.name());
		putRecommentInfoResult(channelPage, commonBlockId, containerCode, this.fromPlaceId);

		// 获取团购推荐的产品
		groupMap = (Map<String, List<RecommendInfo>>) map.get("recommendInfoMainList");
		groupPrdList = new ArrayList<Map<String, Object>>();
		recommendPrdMap = new HashMap<Long, Object>();
		List<RecommendInfo> topList = groupMap.get(channelPage + "_products");

		// 加载团购产品的产品价格上下线等数据
		Map<Long, Map<String, Object>> returnProductMap = getProductList(topList);
		
		// 处理团购推荐产品
		pageConfig = new PageConfig<Map<String, Object>>(0);
		if (topList != null && topList.size() > 0) {
			cities = new HashMap<String, String>();
			for (RecommendInfo rec : topList) {
				// 查询产品信息 和推荐产品所关联的标的(类型：直辖市、特别行政区、城市、出境目的地)
				Map<String, Object> returnMap = returnProductMap.get(Long.valueOf(rec.getRecommObjectId()));// 每个产品ID
				if (returnMap != null) {
					// 产品类型和目的地筛选开关
					boolean cityIsvalid = false;
					if (city.equals("all")) {
						cityIsvalid = true;
					}
					boolean typeIsvalid = false;

					// 单独加载图片
					if (returnMap.get("pageId") != null) {
						String key = "getPictureByPageId_" + returnMap.get("pageId");
						List<ComPicture> picture = (List<ComPicture>) MemcachedUtil.getInstance().get(key);
						if (picture == null) {
							picture = comPictureService.getPictureByPageId((Long) returnMap.get("pageId"));
							 MemcachedUtil.getInstance().set(key,MemcachedUtil.ONE_HOUR,picture);
						}
						returnMap.put("comPictureList", picture);
					}
					returnMap.put("viewRecommendInfo", rec);
					returnMap.put("isRecommendPrd", true);
					returnMap.put("seq", rec.getSeq());
					recommendPrdMap.put(Long.valueOf(rec.getRecommObjectId()), rec.getRecommObjectId());
					
					// 产品类型筛选
					ProdProduct pp = new ProdProduct();
					pp = (ProdProduct) returnMap.get("prodProduct");
					String proudctType = pp.getProductType();
					String subProductType = pp.getSubProductType();
					/**
					 * 门票 酒店取 productType 判断, 自由行 跟团游 长途游 出境游取subProductType判断
					 **/

					if (type.toLowerCase().equals("all")) {// 全部
						typeIsvalid = true;
					} else if (type.toLowerCase().equals("ticket")
							&& "TICKET".equals(proudctType)) {// 门票
						typeIsvalid = true;
					} else if (type.toLowerCase().equals("hotel")
							&& "HOTEL".equals(proudctType)) {// 酒店
						typeIsvalid = true;
					} else if (type.toLowerCase().equals("freetour")
							&& ("FREENESS".equals(subProductType) || "FREENESS_LONG"
									.equals(subProductType))) {// 自由行
						typeIsvalid = true;
					} else if (type.toLowerCase().equals("around")
							&& ("GROUP".equals(subProductType) || "SELFHELP_BUS"
									.equals(subProductType))) {// 跟团
						typeIsvalid = true;
					} else if (type.toLowerCase().equals("destroute")
							&& "GROUP_LONG".equals(subProductType)) {// 长途
						typeIsvalid = true;
					} else if (type.toLowerCase().equals("abroad")
							&& ("GROUP_FOREIGN".equals(subProductType) || "FREENESS_FOREIGN"
									.equals(subProductType))) {// 出境
						typeIsvalid = true;
					}
					
					//目的地筛选和收集
					List<Place> recommendPrdPlace = (List<Place>) returnMap.get("recommendPrdPlace");																											
					for (int i = 0; i < recommendPrdPlace.size(); i++) {
						if (typeIsvalid) {
							cities.put(String.valueOf(recommendPrdPlace.get(i).getPlaceId()),recommendPrdPlace.get(i).getName());
						}						
						if (StringUtils.isNotEmpty(city)&& city.equals(String.valueOf(recommendPrdPlace.get(i).getPlaceId()))) {
							cityIsvalid = true;
						}
					}
					
					// 产品类型和目的地筛选
					if (typeIsvalid && cityIsvalid) {
						groupPrdList.add(returnMap);
					}
				}

			}

			// 对匹配的结果排序
			if (sort.toLowerCase().equals("all")) {
				Collections.sort(groupPrdList, new seqCompare());
			} else if (sort.toLowerCase().equals("hot")) {
				Collections.sort(groupPrdList, new sellHotCompare());
			} else if (sort.toLowerCase().equals("price")) {
				Collections.sort(groupPrdList, new sellPriceCompare());
			} else {
				Collections.sort(groupPrdList, new seqCompare());
			}
			pageConfig = new PageConfig<Map<String, Object>>(
					groupPrdList.size(), pageSize, page);
			List<Map<String, Object>> groupPrdListParse = new ArrayList<Map<String, Object>>();
			if (groupPrdList.size() > 0) {

				for (int i = pageConfig.getStartResult(); i < pageConfig.getCurrentRowNum(); i++) {
					groupPrdListParse.add(groupPrdList.get(i));
				}
			}
			pageConfig.setItems(groupPrdListParse);
			initTicketPageUrl();
		}
		return "groupIndex";
	}

	/**
	 * 查询产品的价格等实时数据
	 * 
	 * @param topList
	 * @return
	 */
	private Map<Long, Map<String, Object>> getProductList(List<RecommendInfo> topList) {
		List<Long> productIdList = new ArrayList<Long>();
		if(topList!=null&&topList.size()>0){
			for (RecommendInfo rec : topList) {
				if (rec != null && rec.getRecommObjectId() != null) {
					productIdList.add(Long.valueOf(rec.getRecommObjectId()));
				}
			}
		}
		List<ProdProduct> prodProductList = this.queryOnlineProductInProductIds(productIdList, channelPage, commonBlockId, containerCode, fromPlaceId);

		Map<Long, Map<String, Object>> returnMap = new HashMap<Long, Map<String, Object>>();
		for(ProdProduct prod:prodProductList){
			Map<String, Object> inReturnMap = new HashMap<String, Object>();
			inReturnMap.put("prodProduct", prod);
			inReturnMap.put("orderCount", prod.getOrderCount());
			inReturnMap.put("pageId", prod.getPageId());
			inReturnMap.put("managerRecommend", prod.getManagerRecommend());
			inReturnMap.put("recommendPrdPlace", prod.getPlaceList());
			if (prod.getMarketPriceYuan() > 0) {
				inReturnMap.put("discount", new BigDecimal(prod.getSellPriceYuan() / prod.getMarketPriceYuan() * 10).setScale(1, BigDecimal.ROUND_FLOOR).doubleValue());
			}
			long offlineTime = 0;
			if (prod.getOfflineTime() != null) {
				offlineTime = prod.getOfflineTime().getTime();
			} else {
				offlineTime = -1;
			}
			inReturnMap.put("diff", offlineTime - System.currentTimeMillis());

			returnMap.put(prod.getProductId(), inReturnMap);
		}
		return returnMap;
	}

 
	public List<ProdProduct> queryOnlineProductInProductIds(List<Long> productIdList, String channelPage, Long commonBlockId, String containerCode, Long fromPlaceId) {
		String key = "queryOnlineProductInProductIds_" + channelPage + "_" + commonBlockId + "_" + containerCode + "_" + fromPlaceId;
		List<ProdProduct> prodProductList = (List<ProdProduct>) MemcachedUtil.getInstance().get(key);
		if (prodProductList == null) {
			prodProductList = new ArrayList<ProdProduct>();
			if (productIdList.size() > 0) {
				prodProductList = groupDreamService.queryOnlineProductInProductIds(productIdList);
				MemcachedUtil.getInstance().set(key, MemcachedUtil.TWO_HOUR, prodProductList);
			}
		}
		for (ProdProduct prod : prodProductList) {
			// 加载推荐产品的标的
			String placeKey = "recommendPrdPlace_" + prod.getProductId();
			List<Place> recommendPrdPlace = (List<Place>) MemcachedUtil.getInstance().get(placeKey);
			if (recommendPrdPlace == null) {
				recommendPrdPlace = prodProductPlaceService.getComPlaceByProductId(prod.getProductId());
				MemcachedUtil.getInstance().set(placeKey, MemcachedUtil.TWO_HOUR, recommendPrdPlace);
			}
			prod.setPlaceList(recommendPrdPlace);
		}
		return prodProductList;
	}
	
	/**
	 * 格式化URL链接,传回页面
	 */
	private void initTicketPageUrl() {
		pageConfig.setUrl("http://www.lvmama.com/tuangou/" + this.getType() + "-" + this.getCity() + "-" + this.getSort() + "-");
	}

	public List<Map<String, Object>> getGroupPrdList() {
		return groupPrdList;
	}

	public void setGroupPrdList(List<Map<String, Object>> tuanPrdList) {
		this.groupPrdList = tuanPrdList;
	}
 
	public void setGroupDreamService(GroupDreamService groupDreamService) {
		this.groupDreamService = groupDreamService;
	}

	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}

	public boolean isRecommendPrd(Long productId) {
		return recommendPrdMap.containsKey(productId);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public PageConfig<Map<String, Object>> getPageConfig() {
		return pageConfig;
	}

	public void setPageConfig(PageConfig<Map<String, Object>> pageConfig) {
		this.pageConfig = pageConfig;
	}

	public Map<String, String> getCities() {
		return cities;
	}

	public void setCities(Map<String, String> cities) {
		this.cities = cities;
	}

	public String getChannelPage() {
		return channelPage;
	}

	private class seqCompare implements Comparator<Map<String, Object>> {
		public int compare(Map<String, Object> o1, Map<String, Object> o2) {
			long value1 = (Long)o1.get("seq");
			long value2 = (Long)o2.get("seq");
			return (int) (value2 - value1);
		}
	}

	
	private class sellPriceCompare implements Comparator<Map<String, Object>> {
		public int compare(Map<String, Object> o1, Map<String, Object> o2) {
			long value1 = ((ProdProduct) o1.get("prodProduct")).getSellPrice(); 
			long value2 = ((ProdProduct) o2.get("prodProduct")).getSellPrice();
			return (int) (value1 - value2);
		}
	}

	private class offTimeCompare implements Comparator<Map<String, Object>> {
		public int compare(Map<String, Object> o1, Map<String, Object> o2) {
			long value1 = (((ProdProduct) o1.get("prodProduct")).getOfflineTime().getTime())/100000;//long数据溢出int,统一转换
			long value2 = (((ProdProduct) o2.get("prodProduct")).getOfflineTime().getTime())/100000;
			return (int) (value1 - value2);
		}
	}

	private class sellHotCompare implements Comparator<Map<String, Object>> {
		public int compare(Map<String, Object> o1, Map<String, Object> o2) {
			long value1 = (Long) o1.get("orderCount");
			long value2 = (Long) o2.get("orderCount");
			return (int) (value2 - value1);
		}
	}

	public ProdProductPlaceService getProdProductPlaceService() {
		return prodProductPlaceService;
	}

	public void setProdProductPlaceService(
			ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}
}
