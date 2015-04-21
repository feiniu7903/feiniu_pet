package com.lvmama.clutter.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.clutter.model.MobileRecommend;
import com.lvmama.clutter.service.IClientRecommendService;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.clutter.utils.ClientRecommendProperties;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.utils.DateUtil;
import com.lvmama.clutter.utils.MobileCopyPropertyUtils;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.pet.po.mobile.MobileRecommendBlock;
import com.lvmama.comm.pet.po.mobile.MobileRecommendInfo;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlacePhoto;
import com.lvmama.comm.pet.po.prod.ProdContainerFromPlace;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.service.mobile.MobileRecommendBlockService;
import com.lvmama.comm.pet.service.mobile.MobileRecommendInfoService;
import com.lvmama.comm.pet.service.place.PlacePhotoService;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.pet.service.seo.RecommendInfoService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.enums.PlacePhotoTypeEnum;

/**
 * 驴途移动端 from 3.0. 推荐信息 包括{首页推荐，自由行推荐 ，度假目的地推荐 ，攻略目的地推荐等 }
 * 
 * @author qinzubo
 * 
 */
public class ClientRecommendServiceImpl extends AppServiceImpl implements IClientRecommendService {
	Logger log = Logger.getLogger(ClientRecommendServiceImpl.class);
	protected final static String MOBILE_RECOMMEND_CACHE = "MOBILE_RECOMMEND_CACHE";

	protected static int MOBILE_MEMCACHE_SECOND = 60 * 60 * 2;
	/**
	 * 默认父节点blockType
	 */
	private static String parentBlockIds = "1,2,3,4,5";

	/**
	 * 目的地 tags FREE_TOUR("周边自由行"), GROUP("周边跟团游"), LDISTANCE_TRAVEL("长途游"),
	 * OVERSEA_TRAVEL("出境游"), OTHER("其它");
	 */
	private static String tags = String.format("%s,%s,%s,%s,%s",
			Constant.CLIENT_RECOMMEND_TAG.FREE_TOUR.name(),
			Constant.CLIENT_RECOMMEND_TAG.GROUP.name(),
			Constant.CLIENT_RECOMMEND_TAG.LDISTANCE_TRAVEL.name(),
			Constant.CLIENT_RECOMMEND_TAG.OVERSEA_TRAVEL.name(),
			Constant.CLIENT_RECOMMEND_TAG.OTHER.name());

	/**
	 * 攻略目的地 (国内4，国外5)，默认两个.
	 */
	private static String guideBlockIds = "4,5";
	/**
	 * 推荐块
	 */
	protected MobileRecommendBlockService mobileRecommendBlockService;

	/**
	 * 推荐信息
	 */
	protected MobileRecommendInfoService mobileRecommendInfoService;

	private RecommendInfoService recommendInfoService;
	private ProdProductPlaceService prodProductPlaceService;
	private ComPictureService comPictureService;
	private ProductSearchInfoService productSearchInfoService;
	private PlacePhotoService placePhotoService;
	protected PageService pageService;

	/**
	 * 获取首页推荐信息.
	 */
	@Override
	public Map<String, Object> getFocusRecommend(Map<String, Object> params) {
		Long blockId = Long.valueOf(ClientRecommendProperties.getBlockId("1")); // 获取block的id.
		params.put("parentId", blockId);
		params.put("currentDate", new Date()); // 只有首页推荐有该时间 .当前时间 .
		params.put("isValid", "Y"); // 是否有效
		if ("IPAD".equals(params.get("firstChannel"))) {
			params.put("hdUrl", "isNotNull");//修复查询出来的记录数过少的问题，因为包含手机客户端的记录被过滤
			return getHDCommonRecommendInfo(params);
		} else {
			params.put("url", "isNotNull");//修复查询出来的记录数过少的问题，因为包含HD客户端的记录被过滤
			return getCommonRecommendInfo(params);
		}

	}

	/**
	 * v3.1 客户端推荐
	 */
	@Override
	public Map<String, Object> getClientRecommend(Map<String, Object> params) {
		return null;
	}

	/**
	 * 获取自由行推荐信息.
	 * 
	 */
	@Override
	public Map<String, Object> getCitiesArea(Map<String, Object> params) {
		Long blockId = Long.valueOf(ClientRecommendProperties.getBlockId("2"));
		params.put("parentId", blockId);
		return getCommonRecommendInfo(params, "2");
	}

	/**
	 * 度假 目的地 城市数据 .
	 */
	@Override
	public Map<String, Object> getRouteArroundRecommendCities(
			Map<String, Object> params) {
		Long blockId = Long.valueOf(ClientRecommendProperties.getBlockId("3"));
		params.put("parentId", blockId);
		// 根据拼音排序
		params.put("orderByPin", "true");
		return getDestCommonRecommendInfo(params, null);
	}

	/**
	 * 攻略 目的地城市 .
	 * 
	 * @param blockType
	 *            4:国外 ，5：国内.
	 * 
	 *            攻略 目的地城市数据 (国外4).
	 */
	@Override
	public Map<String, Object> getGuideRecommendCities(
			Map<String, Object> params) {
		Map<String, Object> map = new HashMap<String, Object>();
		String blockType = guideBlockIds; // 默认id
		// 如果有传入目的地参数.
		if (null != params.get("blockType")
				&& !StringUtils
						.isEmpty(String.valueOf(params.get("blockType")))) {
			blockType = String.valueOf(params.get("blockType"));
		}

		List<Map<String, Object>> recommendMapList = new ArrayList<Map<String, Object>>();
		String[] blockIds = blockType.split(",");
		// 遍历父block
		for (int i = 0; i < blockIds.length; i++) {
			Map<String, Object> rMap = new HashMap<String, Object>();
			Long blockId = Long.valueOf(ClientRecommendProperties
					.getBlockId(blockIds[i]));
			params.put("parentId", blockId);
			params.remove("blockType");
			// 查询字块id.
			List<MobileRecommendBlock> blockList = mobileRecommendBlockService
					.queryMobileRecommendBlockByParam(params);
			// 主block下所有info信息
			List<MobileRecommend> mmList = new ArrayList<MobileRecommend>();
			if (null != blockList && blockList.size() > 0) {
				for (int j = 0; j < blockList.size(); j++) {
					MobileRecommendBlock mb = blockList.get(j);
					Map<String, Object> t_map = new HashMap<String, Object>();
					t_map.put("recommendBlockId", mb.getId());
					mmList.addAll(getMobileRecommendList(t_map));
				}
			}
			if ("4".equals(blockIds[i])) { // 国外
				rMap.put("blockName", "攻略国外");
			} else if ("5".equals(blockIds[i])) { // 国内
				rMap.put("blockName", "攻略国内");
			} else { // 其它
				rMap.put("blockName", "其它");
			}
			rMap.put("blockType", blockIds[i]);
			rMap.put("data", mmList);
			// version判断
			recommendMapList.add(rMap);
		}
		map.put("datas", recommendMapList);
		return initVersion(map, params);

	}

	/**
	 * 攻略 目的地城市数据 (国内 5).
	 */
	@Override
	public Map<String, Object> getGuideRecommendCitiesInternal(
			Map<String, Object> params) {
		Long blockId = Long.valueOf(ClientRecommendProperties.getBlockId("5"));
		params.put("parentId", blockId);
		return getDestStrategyCommonRecommendInfo(params, null);
	}

	/**
	 * 获取子block节点 - recommend_block.properties.
	 * 
	 * @param parentId
	 *            必填项
	 */
	@Override
	public Map<String, Object> getDepaturePlace(Map<String, Object> params) {
		// 从缓存中获取信息.
		String memcacheKey = getMemcacheKeyByParams(params);
		Object obj = getMemecachedInfo(memcacheKey);
		Map<String, Object> blockMap = new HashMap<String, Object>();
		if (null == obj) {
			String type = parentBlockIds; // 默认查询所有子节点
			// 如果有传入目的地参数.
			if (null != params.get("blockType")
					&& !StringUtils.isEmpty(String.valueOf(params
							.get("blockType")))) {
				type = String.valueOf(params.get("blockType"));
			}
			String[] types = type.split(",");
			List<Map<String, Object>> blockMapList = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < types.length; i++) {
				Long blockId = Long.valueOf(ClientRecommendProperties
						.getBlockId(types[i]));
				if (null != blockId) {
					Map<String, Object> m = new HashMap<String, Object>();
					params.put("parentId", blockId);
					params.remove("blockType");
					params.put("orderByPin", "true"); // 更加拼音排序.
					List<MobileRecommendBlock> mrb = mobileRecommendBlockService
							.queryMobileRecommendBlockByParam(params);
					m.put("blockType", types[i]);
					m.put("data", getBlockInfo(mrb));
					blockMapList.add(m);
				}
			}
			blockMap.put("datas", blockMapList);
			if (blockMapList != null && !blockMapList.isEmpty()) {
				MemcachedUtil.getInstance().set(
						MOBILE_RECOMMEND_CACHE + memcacheKey,
						MOBILE_MEMCACHE_SECOND, blockMapList);
			}
		} else {
			blockMap.put("datas", obj);
		}

		return initVersion(blockMap, params);
	}

	/**
	 * json中需要的一些信息 .
	 * 
	 * @param mrb
	 * @return
	 */
	public List<Map<String, Object>> getBlockInfo(
			List<MobileRecommendBlock> mrbList) {
		List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
		if (null != mrbList && mrbList.size() > 0) {
			for (MobileRecommendBlock mrb : mrbList) {
				Map<String, Object> t_m = new HashMap<String, Object>();
				t_m.put("id", mrb.getId());
				t_m.put("name", mrb.getBlockName());
				t_m.put("type", mrb.getBlockType());
				t_m.put("pinyin", str2UpperCase(mrb.getReserve1()));
				mList.add(t_m);
			}
		}
		return mList;
	}

	/**
	 * HD首页推荐获取block下所有信息 - 带分页.
	 * 
	 * @param params
	 * @param type
	 * @return
	 */
	public Map<String, Object> getHDCommonRecommendInfo(
			Map<String, Object> params) {
		// 从缓存中获取信息.
		String memcacheKey = getMemcacheKeyByParams(params)
				+ (null == params.get("page") ? "" : params.get("page")
						.toString()) + "IPAD";
		Object obj = getMemecachedInfo(memcacheKey);
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == obj) {
			// 初始化ids
			String[] ids = getIds(params, "1");
			List<Map<String, Object>> recommendMapList = new ArrayList<Map<String, Object>>(); // 存放每个字块的列表
																								// .
			params.put("count", 100);
			Page p = initPage(params, 100, 1);
			params.put("isPaging", "true"); // 是否使用分页
			params.put("startRows", p.getStartRows());
			params.put("endRows", p.getEndRows());
			if (null != ids) {
				for (int i = 0; i < ids.length; i++) {
					// 查询这句就是为了获得block的name .
					MobileRecommendBlock mb = mobileRecommendBlockService
							.getMobileRecommendBlockById(Long.valueOf(ids[i]));
					Map<String, Object> t_map = new HashMap<String, Object>();
					params.put("recommendBlockId", ids[i]);
					List<MobileRecommend> list = getMobileRecommendList(params,
							p);
					// 过滤hdUrl为空的记录
					List<MobileRecommend> hdMobileRecommendList = new ArrayList<MobileRecommend>();
					for (MobileRecommend mobileRecommend : list) {
						if (!StringUtils.isEmpty(mobileRecommend.getHdUrl())) {
							hdMobileRecommendList.add(mobileRecommend);
						}
					}

					t_map.put("data", hdMobileRecommendList); // 列表数据
					t_map.put("isLastPage", !p.hasNext()); // 列表数据
					t_map.put("name", null == mb ? "" : mb.getBlockName());
					t_map.put("id", ids[i]);
					recommendMapList.add(t_map);
				}
			}
			map.put("datas", recommendMapList);
			if (recommendMapList != null && !recommendMapList.isEmpty()) {
				MemcachedUtil.getInstance().set(
						MOBILE_RECOMMEND_CACHE + memcacheKey,
						MOBILE_MEMCACHE_SECOND, recommendMapList);
			}
		} else {
			map.put("datas", obj);
		}
		return initVersion(map, params);
	}

	/**
	 * 首页推荐获取block下所有信息 - 带分页.
	 * 
	 * @param params
	 * @param type
	 * @return
	 */
	public Map<String, Object> getCommonRecommendInfo(Map<String, Object> params) {
		// 从缓存中获取信息.
		String memcacheKey = getMemcacheKeyByParams(params)
				+ (null == params.get("page") ? "" : params.get("page")
						.toString()
						+ (null == params.get("isWap") ? "" : params.get(
								"isWap").toString()));
		memcacheKey += params.get("lvversion");
		
		// android4.0.0 专门的缓存 
		try {
			if( params.get("method").toString().equals("api.com.recommend.getIndexRecommend") 
					&& null != params.get("lvversion")
					&& "ANDROID".equals(params.get("firstChannel").toString())){
				String lvVersion = (String) params.get("lvversion");
				if ("4.0.0".equals(lvVersion) ){
					memcacheKey += "ANDROID";
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		Object obj = getMemecachedInfo(memcacheKey);
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == obj) {
			// 初始化ids
			String[] ids = getIds(params, "1");
			List<Map<String, Object>> recommendMapList = new ArrayList<Map<String, Object>>(); // 存放每个字块的列表
																								// .
			Page p = initPage(params, 6, 1);
			params.put("isPaging", "true"); // 是否使用分页
			params.put("startRows", p.getStartRows());
			params.put("endRows", p.getEndRows());
			if (null != ids) {
				for (int i = 0; i < ids.length; i++) {
					// 查询这句就是为了获得block的name .
					MobileRecommendBlock mb = mobileRecommendBlockService
							.getMobileRecommendBlockById(Long.valueOf(ids[i]));
					Map<String, Object> t_map = new HashMap<String, Object>();
					params.put("recommendBlockId", ids[i]);
					List<MobileRecommend> list = getMobileRecommendList(params,
							p);
					/*if (null != params.get("appVersion")) {
						Long appVersion = Long.valueOf(params.get("appVersion")
								.toString());
						if (appVersion < 310) {
							for (MobileRecommend mobileRecommend : list) {
								if (null != mobileRecommend.getUrl()
										&& !mobileRecommend.getUrl().contains(
												"clutter")) {
									String url = mobileRecommend.getUrl();
									if (url.endsWith(".html")) {
										String name = url.substring(
												url.lastIndexOf("/") + 1,
												url.lastIndexOf("."));
										String proxyUrl = "http://api3g.lvmama.com/clutter/zt/"
												+ name;
										mobileRecommend.setUrl(proxyUrl);
									}
								}
							}
						}
					}*/
					// 过滤url为空的记录
					List<MobileRecommend> mobileRecommendList = new ArrayList<MobileRecommend>();
					for (MobileRecommend mobileRecommend : list) {
						if (!StringUtils.isEmpty(mobileRecommend.getUrl())) {
							mobileRecommendList.add(mobileRecommend);
						}
					}
					
					// 过滤android4.0.0条数
					try {
						if( params.get("method").toString().equals("api.com.recommend.getIndexRecommend")
								&& null != params.get("lvversion")
								&& "ANDROID".equals(params.get("firstChannel").toString())){
							String lvVersion = (String) params.get("lvversion");
							if ("4.0.0".equals(lvVersion) && null != mobileRecommendList && mobileRecommendList.size() > 4 ){
								mobileRecommendList = mobileRecommendList.subList(0, 4);
							}
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
					
					t_map.put("data", mobileRecommendList); // 列表数据
					t_map.put("isLastPage", !p.hasNext()); // 列表数据
					t_map.put("name", null == mb ? "" : mb.getBlockName());
					t_map.put("id", ids[i]);
					recommendMapList.add(t_map);
				}
			}
			
			
			map.put("datas", recommendMapList);
			if (recommendMapList != null && !recommendMapList.isEmpty()) {
				MemcachedUtil.getInstance().set(
						MOBILE_RECOMMEND_CACHE + memcacheKey,
						MOBILE_MEMCACHE_SECOND, recommendMapList);
			}
		} else {
			map.put("datas", obj);
		}
		return initVersion(map, params);
	}

	/**
	 * 获取block下所有信息.
	 * 
	 * @param params
	 * @param type
	 * @return
	 */
	public Map<String, Object> getCommonRecommendInfo(
			Map<String, Object> params, String type) {
		// 从缓存中获取信息.
		String memcacheKey = getMemcacheKeyByParams(params);
		
		Object obj = getMemecachedInfo(memcacheKey);
		
		// 首页地图自由行接口 5.0.0 (包括 )以后不加缓存 
		if(this.hasLatAndLon(params)) {
			obj = null ;
		}
				
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == obj) {
			// 初始化ids
			String[] ids = getIds(params, type);
			List<Map<String, Object>> recommendMapList = new ArrayList<Map<String, Object>>(); // 存放每个字块的列表
																								// .
			if (null != ids) {
				for (int i = 0; i < ids.length; i++) {
					// 查询这句就是为了获得block的name .
					MobileRecommendBlock mb = mobileRecommendBlockService
							.getMobileRecommendBlockById(Long.valueOf(ids[i]));
					Map<String, Object> t_map = new HashMap<String, Object>();
					params.put("recommendBlockId", ids[i]);
					t_map.put("data", getMobileRecommendList(params)); // 列表数据
					t_map.put("name", null == mb ? "" : mb.getBlockName());
					t_map.put("id", ids[i]);
					recommendMapList.add(t_map);
				}
			}
			map.put("datas", recommendMapList);
			if (!this.hasLatAndLon(params) && recommendMapList != null && !recommendMapList.isEmpty()) {
				MemcachedUtil.getInstance().set(
						MOBILE_RECOMMEND_CACHE + memcacheKey,
						MOBILE_MEMCACHE_SECOND, recommendMapList);
			}
		} else {
			map.put("datas", obj);
		}
		return initVersion(map, params);
	}

	public Map<String, Object> getRouteToDest(Map<String, Object> params) {
		ArgCheckUtils.validataRequiredArgs("blockId", params);
		String memcacheKey = getMemcacheKeyByParams(params);
		Object obj = getMemecachedInfo(memcacheKey + params.get("blockId"));
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == obj) {
			List<Map<String, Object>> tagList = new ArrayList<Map<String, Object>>(); // 存放每个子块的tags列表
																						// .
			String[] tags = initTags(params);// 初始化ids
			// 此查询为了获取block的name
			MobileRecommendBlock mb = mobileRecommendBlockService
					.getMobileRecommendBlockById(Long.valueOf(params.get(
							"blockId").toString()));
			if (null != mb) {
				params.put("recommendBlockId", mb.getId());
				for (int x = 0; x < tags.length; x++) {
					Map<String, Object> tagMap = new HashMap<String, Object>();
					params.put("tag", tags[x]); // 参数
					tagMap.put("type", tags[x]);
					tagMap.put("name",
							Constant.CLIENT_RECOMMEND_TAG.getCnName(tags[x]));
					tagMap.put("info", getMobileRecommendList(params)); // 列表数据
					tagList.add(tagMap);
				}
			}
			map.put("datas", tagList);
			map.put("blockId", params.get("blockId"));

			if (tagList != null && !tagList.isEmpty()) {
				MemcachedUtil.getInstance().set(
						MOBILE_RECOMMEND_CACHE + memcacheKey
								+ params.get("blockId"),
						MOBILE_MEMCACHE_SECOND, tagList);
			}
		} else {
			map.put("blockId", params.get("blockId"));
			map.put("datas", obj);
		}
		return initVersion(map, params);
	}

	public Map<String, Object> getRouteFromDest(Map<String, Object> params) {
		Long blockId = Long.valueOf(ClientRecommendProperties.getBlockId("3"));
		params.put("parentId", blockId);
		// 根据拼音排序
		params.put("orderByPin", "true");
		// 从缓存中获取信息.
		String memcacheKey = getMemcacheKeyByParams(params);
		Object obj = getMemecachedInfo(memcacheKey);
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == obj) {
			String[] ids = getIds(params, null); // 初始化ids ， 判断是否传来参数，
													// 如果没传则查询所有数据
			List<Map<String, Object>> recommendMapList = new ArrayList<Map<String, Object>>(); // 存放每个字块的列表
																								// .
			// 字块数据 .
			if (null != ids) {
				for (int i = 0; i < ids.length; i++) {
					MobileRecommendBlock mb = mobileRecommendBlockService
							.getMobileRecommendBlockById(Long.valueOf(ids[i]));
					Map<String, Object> t_map = new HashMap<String, Object>();
					t_map.put("name", mb.getBlockName());
					t_map.put("subName",
							null == mb.getReserve2() ? "" : mb.getReserve2());
					t_map.put("pinyin", str2UpperCase(mb.getReserve1()));
					t_map.put("isHot", HOT_CITY.contains(mb.getBlockName()));
					t_map.put("id", ids[i]);
					recommendMapList.add(t_map);
				}
			}
			map.put("datas", recommendMapList);
			if (recommendMapList != null && !recommendMapList.isEmpty()) {
				MemcachedUtil.getInstance().set(
						MOBILE_RECOMMEND_CACHE + memcacheKey,
						MOBILE_MEMCACHE_SECOND, recommendMapList);
			}
		} else {
			map.put("datas", obj);
		}

		return initVersion(map, params);
	}

	/**
	 * 获取某个非攻略目的地下所有信息 - 非攻略目的地.
	 * 
	 * @param params
	 * @param type
	 * @return
	 */
	public Map<String, Object> getDestCommonRecommendInfo(
			Map<String, Object> params, String type) {
		log.info("...start..."+System.currentTimeMillis()/1000);
		// 从缓存中获取信息.
		String memcacheKey = getMemcacheKeyByParams(params);
		Object obj = getMemecachedInfo(memcacheKey);
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == obj) {
			log.info("..1..memchached is null..."+System.currentTimeMillis()/1000);
			String[] ids = getIds(params, type); // 初始化ids ， 判断是否传来参数，
													// 如果没传则查询所有数据
			String[] tags = initTags(params);// 初始化ids
			List<Map<String, Object>> recommendMapList = new ArrayList<Map<String, Object>>(); // 存放每个字块的列表
																								// .
			// 字块数据 .
			if (null != ids) {
				for (int i = 0; i < ids.length; i++) {
					List<Map<String, Object>> tagList = new ArrayList<Map<String, Object>>(); // 存放每个子块的tags列表
																								// .
					// 此查询为了获取block的name
					MobileRecommendBlock mb = mobileRecommendBlockService
							.getMobileRecommendBlockById(Long.valueOf(ids[i]));
					Map<String, Object> t_map = new HashMap<String, Object>();
					params.put("recommendBlockId", mb.getId());
					for (int x = 0; x < tags.length; x++) {
						Map<String, Object> tagMap = new HashMap<String, Object>();
						params.put("tag", tags[x]); // 参数
						tagMap.put("type", tags[x]);
						tagMap.put("name", Constant.CLIENT_RECOMMEND_TAG
								.getCnName(tags[x]));
						tagMap.put("info", getMobileRecommendList(params)); // 列表数据
						tagList.add(tagMap);
					}
					t_map.put("data", tagList); // 列表数据
					t_map.put("name", mb.getBlockName());
					t_map.put("subName",
							null == mb.getReserve2() ? "" : mb.getReserve2());
					t_map.put("pinyin", str2UpperCase(mb.getReserve1()));
					t_map.put("isHot", HOT_CITY.contains(mb.getBlockName()));
					t_map.put("id", ids[i]);
					recommendMapList.add(t_map);
				}
			}
			map.put("datas", recommendMapList);
			if (recommendMapList != null && !recommendMapList.isEmpty()) {
				log.info(recommendMapList.size()+"..2...set memchache..."+System.currentTimeMillis()/1000);
				MemcachedUtil.getInstance().set(
						MOBILE_RECOMMEND_CACHE + memcacheKey,
						MOBILE_MEMCACHE_SECOND, recommendMapList);
			}
		} else {
			map.put("datas", obj);
		}
		log.info("...end..."+System.currentTimeMillis()/1000);
		return initVersion(map, params);
	}

	/**
	 * 获取某个攻略目的地下所有信息 - 攻略目的地.
	 * 
	 * @param params
	 * @param type
	 * @return
	 */
	public Map<String, Object> getDestStrategyCommonRecommendInfo(
			Map<String, Object> params, String type) {

		// 从缓存中获取信息.
		String memcacheKey = getMemcacheKeyByParams(params);
		Object obj = getMemecachedInfo(memcacheKey);
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == obj) {
			String[] ids = getIds(params, type); // 初始化ids ， 判断是否传来参数，
													// 如果没传则查询所有数据
			List<Map<String, Object>> recommendMapList = new ArrayList<Map<String, Object>>(); // 存放每个字块的列表
																								// .
			// 字块数据 .
			if (null == ids) {
				for (int i = 0; i < ids.length; i++) {
					// 此查询为了获取block的name
					MobileRecommendBlock mb = mobileRecommendBlockService
							.getMobileRecommendBlockById(Long.valueOf(ids[i]));
					Map<String, Object> t_map = new HashMap<String, Object>();
					params.put("recommendBlockId", mb.getId());
					t_map.put("data", getMobileRecommendList(params)); // 列表数据
					t_map.put("name", mb.getBlockName());
					t_map.put("id", ids[i]);
					recommendMapList.add(t_map);
				}
			}
			map.put("datas", recommendMapList);
			if (recommendMapList != null && !recommendMapList.isEmpty()) {
				MemcachedUtil.getInstance().set(
						MOBILE_RECOMMEND_CACHE + memcacheKey,
						MOBILE_MEMCACHE_SECOND, recommendMapList);
			}
		} else {
			map.put("datas", obj);
		}

		return initVersion(map, params);
	}

	/**
	 * 获取推荐信息 - 分页 ---- 暂未用到 .
	 * 
	 * @param params
	 *            查询参数
	 * @param defCount
	 *            默认每个推荐块一次查询的数量.
	 * @param defPage
	 *            默认每个推荐块当前页数
	 * @param type
	 *            用来标示哪一类推荐（1：首页，2:自由行，3：度假目的地，4：攻略目的地）
	 * @return map
	 */
	public Map<String, Object> getCommonRecommendCities(
			Map<String, Object> params, long defCount, long defPage, String type) {
		Map<String, Object> recommendMap = new HashMap<String, Object>();
		// 初始化分页信息 .
		Page p = initPage(params, defCount, defPage);
		params.put("isPaging", "true"); // 是否使用分页
		params.put("startRows", p.getStartRows());
		params.put("endRows", p.getEndRows());
		// 首页推荐
		if ("1".equals(type)) {
			ArgCheckUtils.validataRequiredArgs("recommendBlockId", params);
			params.put("currentDate", new Date()); // 只有首页推荐有该时间 .当前时间 .
			params.put("isValid", "Y"); // 是否有效
			recommendMap.put("datas", getMobileRecommendList(params, p)); // 列表数据
			recommendMap.put("isLastPage", !p.hasNext());
		} else { // 自由行(2) 和 目的地（3：度假目的地，4：攻略目的地）
			String args = "recommendBlockIds"; // 传递的参数名称
			String arg_key = "recommendBlockId"; // 对应实体的key
			if (!"2".equals(type)) {
				args = "tags";
				arg_key = "tag";
			}
			ArgCheckUtils.validataRequiredArgs(args, params); // 校验必填项
			String[] blockids = String.valueOf(params.get(args)).split(",");
			List<Map> recommendMapList = new ArrayList<Map>();
			for (int i = 0; i < blockids.length; i++) {
				Map<String, Object> t_map = new HashMap<String, Object>();
				// 初始化分页信息
				params.put(arg_key, blockids[i]);
				t_map.put("data", getMobileRecommendList(params, p)); // 列表数据
				t_map.put("isLastPage", !p.hasNext()); // 是否最后一页
				recommendMapList.add(t_map);
			}
			recommendMap.put("datas", recommendMapList);
		}
		return recommendMap;
	}

	/**
	 * 数据封装 - 带分页 .
	 * 
	 * @param params
	 * @param p
	 * @return
	 */
	public List<MobileRecommend> getMobileRecommendList(
			Map<String, Object> params, Page p) {
		List<MobileRecommend> mlist = new ArrayList<MobileRecommend>();
		p.setTotalResultSize(mobileRecommendInfoService
				.countMobileRecommendInfoList(params));
		if (p.getTotalResultSize() > 0) {
			List<MobileRecommendInfo> mRlist = mobileRecommendInfoService
					.queryMobileRecommendInfoList(params);
			MobileCopyPropertyUtils.copyMobileRecommendInfo2MobileRecommend(
					mRlist, mlist);
		}
		return mlist;
	}

	/**
	 * 数据封装 - 带分页 .
	 * 
	 * @param params
	 * @param p
	 * @return
	 */
	public List<MobileRecommend> getClentRecommendList(
			Map<String, Object> params, Page p) {
		List<MobileRecommend> mlist = new ArrayList<MobileRecommend>();
		p.setTotalResultSize(mobileRecommendInfoService
				.countMobileRecommendInfoList(params));
		if (p.getTotalResultSize() > 0) {
			List<MobileRecommendInfo> mRlist = mobileRecommendInfoService
					.queryMobileRecommendInfoList(params);
			MobileCopyPropertyUtils.copyMobileRecommendInfo2MobileRecommend(
					mRlist, mlist);
		}
		return mlist;
	}

	/**
	 * 数据封装 - 不带分页 .
	 * 
	 * @param params
	 * @param p
	 * @return
	 */
	public List<MobileRecommend> getMobileRecommendList(
			Map<String, Object> params) {
		List<MobileRecommend> mlist = new ArrayList<MobileRecommend>();
		List<MobileRecommendInfo> mRlist = mobileRecommendInfoService
				.queryMobileRecommendInfoList(params);
		MobileCopyPropertyUtils.copyMobileRecommendInfo2MobileRecommend(mRlist,
				mlist);
		
		// 如果经纬度存在 ，则计算距离 和 更加经纬度排序
		this.sortByLatAndLon(params,mlist);
		
		return mlist;
	}

	/**
	 * 参数中是否包含经纬度信息
	 * @param params
	 * @return
	 */
	private boolean hasLatAndLon(Map<String, Object> params) {
		if(null != params && null != params.get("latitude") && StringUtils.isNotEmpty(params.get("latitude").toString())
				&& null != params.get("longitude") && StringUtils.isNotEmpty(params.get("longitude").toString())
				&& "api.com.recommend.getFreetripRecommend4Map".equalsIgnoreCase(params.get("method").toString())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据加经纬度排序  V50
	 * @param params longitude 经度  ；latitude 维度 
	 * @param mlist
	 */
	private void sortByLatAndLon(Map<String, Object> params, List<MobileRecommend> mlist) {
		if(this.hasLatAndLon(params)) {
			for(MobileRecommend mr:mlist) {
				if(null != mr.getLat() && null != mr.getLon()) {
					try {
						mr.setJuli(ClientUtils.getDistatce(Double.valueOf(params.get("latitude").toString()),mr.getLat(),
								Double.valueOf(params.get("longitude").toString()),mr.getLon()));
					}catch(Exception e) {
						e.printStackTrace();
						log.error("...自由行地图更加经纬度排序错误..mr.getLat()="+mr.getLat()+"..mr.getLon()="+mr.getLon()
								+"...latitude="+params.get("latitude")+"..longitude="+params.get("longitude"));
					}
				}
			}
		}
		
		// 根据距离排序
		Collections.sort(mlist, new MobileRecommend.comparatorBoost());
	}

	
	/**
	 * 初始化分页信息 --暂未用到.
	 * 
	 * @param params
	 * @param count
	 *            默认显示条数
	 * @param page
	 *            默认页数
	 * @return
	 */
	public Page initPage(Map params, long count, long page) {
		try {
			Object oCount = params.get("count");
			Object oPage = params.get("page");
			if (null != oCount) {
				count = Long.valueOf(oCount + "");
			}
			if (null != oPage) {
				page = Long.valueOf(oPage + "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 初始化分页信息
		return new Page(count < 1 ? 10 : count, page < 1 ? 1 : page);
	}

	/**
	 * 初始化ids
	 * 
	 * @param params
	 * @return
	 */
	public String[] getIds(Map<String, Object> params, String type) {
		Object obj = params.get("ids");
		String[] ids = null;
		if (null != obj && !StringUtils.isEmpty(String.valueOf(obj))) {
			ids = String.valueOf(obj).split(",");
			return ids;
		}
		// 查询子块
		List<MobileRecommendBlock> blockList = mobileRecommendBlockService
				.queryMobileRecommendBlockByParam(params);
		if (null != blockList && blockList.size() > 0) {
			ids = new String[blockList.size()];
			for (int i = 0; i < blockList.size(); i++) {
				MobileRecommendBlock m = blockList.get(i);
				ids[i] = m.getId() + "";
			}
		}
		return ids;
	}

	/**
	 * 初始化标签（目的地） ，周边自由行1， 周边跟团 2 长途游 3 出境游 4 全部
	 * 
	 * @param params
	 * @return
	 */
	private String[] initTags(Map<String, Object> params) {
		Object obj = params.get("tags");
		String[] atags = null;
		if (null != obj && !StringUtils.isEmpty(String.valueOf(obj))) {
			atags = String.valueOf(obj).split(",");
			return atags;
		} else {
			return tags.split(",");
		}
	}

	
	private Long getPlaceIdByCity(String city) {
		Long placeId = null;
		Map<String, Long> hotCityParam = new LinkedHashMap<String, Long>();
		hotCityParam.put("北京", 1L);
		hotCityParam.put("上海", 79L);
		hotCityParam.put("杭州", 100L);
		hotCityParam.put("南京", 82L);
		hotCityParam.put("成都", 279L);
		hotCityParam.put("广州", 229L);
		hotCityParam.put("深圳", 231L);
		hotCityParam.put("三亚", 272L);

		if (!StringUtils.isEmpty(city)) {
			try {
				city = new String(city.getBytes("iso-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (hotCityParam.containsKey(city)) {
				placeId = hotCityParam.get(city);
			} else {
				placeId = 79L;
			}
		} else {
			placeId = 79L;
		}
		return placeId;
	}

	@Override
	public Map<String, Object> getSellData(Map<String, Object> params) {
		Long placeId = this.getPlaceIdByCity((String) params.get("city"));
		String SELL_CACHE = Constant.SELL_CACHE + placeId;

		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("placeId", placeId);
		queryParams.put("SELL_CACHE", SELL_CACHE);
		List<RecommendInfo> recommendInfos = this.getSellList(queryParams);

		if (recommendInfos.isEmpty()) {
			placeId = 79L;
			SELL_CACHE = Constant.SELL_CACHE + placeId;

			queryParams.put("placeId", placeId);
			queryParams.put("SELL_CACHE", SELL_CACHE);
			recommendInfos = this.getSellList(queryParams);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// resultMap.put("recommendInfos", recommendInfos);
		resultMap.put("recommendInfos", getRecommendInfos(recommendInfos));
		return resultMap;
	}

	@Override
	public Map<String, Object> getHotData(Map<String, Object> params) {
		Long placeId = this.getPlaceIdByCity((String) params.get("city"));
		String HOT_CACHE = Constant.HOT_CACHE + placeId;

		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("placeId", placeId);
		queryParams.put("HOT_CACHE", HOT_CACHE);
		List<RecommendInfo> recommendInfos = this.getHotList(queryParams);

		if (recommendInfos.isEmpty()) {
			placeId = 79L;
			HOT_CACHE = Constant.HOT_CACHE + placeId;

			queryParams.put("placeId", placeId);
			queryParams.put("HOT_CACHE", HOT_CACHE);
			recommendInfos = this.getHotList(queryParams);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("recommendInfos", getRecommendInfos(recommendInfos));
		return resultMap;
	}

	@Override
	public Map<String, Object> getSellDataGroupByType(Map<String, Object> params) {
		Long placeId = this.getPlaceIdByCity((String) params.get("city"));
		String SELL_CACHE = Constant.SELL_CACHE + placeId;

		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("placeId", placeId);
		queryParams.put("SELL_CACHE", SELL_CACHE);
		List<RecommendInfo> recommendInfos = this.getSellList(queryParams);

		if (recommendInfos.isEmpty()) {
			placeId = 79L;
			SELL_CACHE = Constant.SELL_CACHE + placeId;

			queryParams.put("placeId", placeId);
			queryParams.put("SELL_CACHE", SELL_CACHE);
			recommendInfos = this.getSellList(queryParams);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<RecommendInfo> freenessRecommendInfos = new ArrayList<RecommendInfo>();// 周边自由行
		List<RecommendInfo> groupRecommendInfos = new ArrayList<RecommendInfo>();// 周边跟团游
		List<RecommendInfo> journeyRecommendInfos = new ArrayList<RecommendInfo>();// 长途游
		List<RecommendInfo> outboundRecommendInfos = new ArrayList<RecommendInfo>();// 出境游
		for (RecommendInfo recommendInfo : recommendInfos) {
			if (Constant.SUB_PRODUCT_TYPE.FREENESS.name().equals(
					recommendInfo.getBakWord9())) {
				freenessRecommendInfos.add(recommendInfo);
			} else if (Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.name().equals(
					recommendInfo.getBakWord9())) {
				groupRecommendInfos.add(recommendInfo);
			} else if (Constant.SUB_PRODUCT_TYPE.GROUP.name().equals(
					recommendInfo.getBakWord9())) {
				groupRecommendInfos.add(recommendInfo);
			} else if (Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name().equals(
					recommendInfo.getBakWord9())) {
				journeyRecommendInfos.add(recommendInfo);
			} else if (Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name().equals(
					recommendInfo.getBakWord9())) {
				journeyRecommendInfos.add(recommendInfo);
			} else if (Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name()
					.equals(recommendInfo.getBakWord9())) {
				outboundRecommendInfos.add(recommendInfo);
			} else if (Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equals(
					recommendInfo.getBakWord9())) {
				outboundRecommendInfos.add(recommendInfo);
			} else {

			}
		}
		resultMap.put("freenessRecommendInfos",
				getRecommendInfos(freenessRecommendInfos));// 周边自由行
		resultMap.put("groupRecommendInfos",
				getRecommendInfos(groupRecommendInfos));// 周边跟团游
		resultMap.put("journeyRecommendInfos",
				getRecommendInfos(journeyRecommendInfos));// 长途游
		resultMap.put("outboundRecommendInfos",
				getRecommendInfos(outboundRecommendInfos));// 出境游
		return this.initVersion(resultMap, params);
		//return resultMap;
	}

	private List<Map<String, Object>> getRecommendInfos(
			List<RecommendInfo> recommendInfos) {
		List<Map<String, Object>> recommendInfoList = new ArrayList<Map<String, Object>>();
		for (RecommendInfo recommendInfo : recommendInfos) {
			String objectType = recommendInfo.getBakWord8();
			if (Constant.PRODUCT_TYPE.ROUTE.name().equals(objectType)) {// 线路
				Map<String, Object> recommendInfoMap = new HashMap<String, Object>();
				recommendInfoMap.put("title", recommendInfo.getTitle());
				recommendInfoMap.put("recommObjectId",
						recommendInfo.getRecommObjectId());
				recommendInfoMap.put("imgUrl", recommendInfo.getImgUrl());
				recommendInfoMap.put("marketPrice",
						recommendInfo.getMarketPrice());
				// recommendInfoMap.put("cmtAvgScore",
				// recommendInfo.getCmtAvgScore());
				recommendInfoMap.put("allowance", recommendInfo.getBakWord3());
				recommendInfoMap.put("sellPrice", recommendInfo.getBakWord7());
				recommendInfoMap.put("objectType", objectType);
				recommendInfoList.add(recommendInfoMap);
			} else if (Constant.PRODUCT_TYPE.TICKET.name().equals(objectType)) {// 景点
				Map<String, Object> recommendInfoMap = new HashMap<String, Object>();
				recommendInfoMap.put("title", recommendInfo.getTitle());
				recommendInfoMap.put("recommObjectId",
						recommendInfo.getPlaceId());
				recommendInfoMap.put("imgUrl", recommendInfo.getImgUrl());
				recommendInfoMap.put("marketPrice",
						recommendInfo.getMarketPrice());
				// recommendInfoMap.put("cmtAvgScore",
				// recommendInfo.getCmtAvgScore());
				recommendInfoMap.put("allowance", recommendInfo.getBakWord3());
				recommendInfoMap.put("sellPrice", recommendInfo.getBakWord7());
				recommendInfoMap.put("objectType", "PLACE");
				recommendInfoList.add(recommendInfoMap);
			} else {
				// 过滤掉不是线路或者景点的数据
			}
		}
		return recommendInfoList;
	}

	@Override
	public Map<String, Object> getHotDataGroupByType(Map<String, Object> params) {
		Long placeId = this.getPlaceIdByCity((String) params.get("city"));
		String HOT_CACHE = Constant.HOT_CACHE + placeId;

		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("placeId", placeId);
		queryParams.put("HOT_CACHE", HOT_CACHE);
		List<RecommendInfo> recommendInfos = this.getHotList(queryParams);

		if (recommendInfos.isEmpty()) {
			placeId = 79L;
			HOT_CACHE = Constant.HOT_CACHE + placeId;

			queryParams.put("placeId", placeId);
			queryParams.put("HOT_CACHE", HOT_CACHE);
			recommendInfos = this.getHotList(queryParams);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<RecommendInfo> freenessRecommendInfos = new ArrayList<RecommendInfo>();// 周边自由行
		List<RecommendInfo> groupRecommendInfos = new ArrayList<RecommendInfo>();// 周边跟团游
		List<RecommendInfo> journeyRecommendInfos = new ArrayList<RecommendInfo>();// 长途游
		List<RecommendInfo> outboundRecommendInfos = new ArrayList<RecommendInfo>();// 出境游
		for (RecommendInfo recommendInfo : recommendInfos) {
			if (Constant.SUB_PRODUCT_TYPE.FREENESS.name().equals(
					recommendInfo.getBakWord9())) {
				freenessRecommendInfos.add(recommendInfo);
			} else if (Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.name().equals(
					recommendInfo.getBakWord9())) {
				groupRecommendInfos.add(recommendInfo);
			} else if (Constant.SUB_PRODUCT_TYPE.GROUP.name().equals(
					recommendInfo.getBakWord9())) {
				groupRecommendInfos.add(recommendInfo);
			} else if (Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name().equals(
					recommendInfo.getBakWord9())) {
				journeyRecommendInfos.add(recommendInfo);
			} else if (Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name().equals(
					recommendInfo.getBakWord9())) {
				journeyRecommendInfos.add(recommendInfo);
			} else if (Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name()
					.equals(recommendInfo.getBakWord9())) {
				outboundRecommendInfos.add(recommendInfo);
			} else if (Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equals(
					recommendInfo.getBakWord9())) {
				outboundRecommendInfos.add(recommendInfo);
			} else {

			}
		}
		resultMap.put("freenessRecommendInfos",
				getRecommendInfos(freenessRecommendInfos));// 周边自由行
		resultMap.put("groupRecommendInfos",
				getRecommendInfos(groupRecommendInfos));// 周边跟团游
		resultMap.put("journeyRecommendInfos",
				getRecommendInfos(journeyRecommendInfos));// 长途游
		resultMap.put("outboundRecommendInfos",
				getRecommendInfos(outboundRecommendInfos));// 出境游
		return this.initVersion(resultMap, params);
	}

	@SuppressWarnings("unchecked")
	public List<RecommendInfo> getHotList(Map<String, Object> queryParams) {
		Long placeId = (Long) queryParams.get("placeId");
		final String HOT_CACHE = (String) queryParams.get("HOT_CACHE");
		List<RecommendInfo> returnlist = new ArrayList<RecommendInfo>();
		List<RecommendInfo> list = new ArrayList<RecommendInfo>();
		Object o = MemcachedUtil.getInstance().get(HOT_CACHE);
		if (o == null
				|| (o instanceof List && ((List<RecommendInfo>) o).size() == 0)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("dataCode", "ReXiaoPaiHang");
			param.put("status", "true");

			List<ProdContainerFromPlace> listContainer = recommendInfoService
					.getBlocksByPlaceIdWitheContainerCode("HOME_RECOMMEND",
							placeId);
			for (ProdContainerFromPlace prodContainerFromPlace : listContainer) {
				param.put("parentRecommendBlockId",
						prodContainerFromPlace.getBlockId());
				List<RecommendInfo> listTemp = recommendInfoService
						.queryRecommendInfoByParam(param,
								prodContainerFromPlace.getBlockId());
				if (listTemp != null) {
					list.addAll(listTemp);
				}
			}

			for (RecommendInfo recommendInfo : list) {
				Long productId = Long
						.valueOf(recommendInfo.getRecommObjectId());
				ProductSearchInfo psi = productSearchInfoService
						.queryProductSearchInfoByProductId(productId);
				if (psi != null
						&& psi.getChannel() != null
						&& !psi.getChannel().contains(
								Constant.CHANNEL.CLIENT.name())) {
					continue;
				}
				if (psi != null) {
					if (null != psi.getSellPrice()) {
						recommendInfo.setMarketPrice(psi.getSellPrice()
								.toString());
					}
					if (null != psi.getSellPrice()) {
						Long sellPrice = Long.valueOf(psi.getSellPrice());
						recommendInfo.setBakWord7(String
								.valueOf(sellPrice / 100));
					}
					recommendInfo.setBakWord8(psi.getProductType());
					recommendInfo.setBakWord9(psi.getSubProductType());// 子产品类型
					recommendInfo.setBakWord10(String.valueOf(psi
							.getOrderQuantitySum() == null ? 0 : psi
							.getOrderQuantitySum()));
					if ("true".equals(psi.getSelfPack())
							|| Constant.PRODUCT_TYPE.HOTEL.name().equals(
									psi.getProductType())
							|| "true".equals(psi.getIsAperiodic())) {
						continue;
					}
				} else {
					continue;
				}
				recommendInfo.setTitle(StringUtil
						.filterOutHTMLTags(recommendInfo.getTitle()));
				Map<String, Object> data = pageService.getProdCProductInfo(
						productId, false);
				ViewPage viewPage = (ViewPage) data.get("viewPage");
				if (viewPage != null) {
					List<ComPicture> pictureList = comPictureService
							.getPictureByPageId(viewPage.getPageId());
					if (pictureList != null && !pictureList.isEmpty()) {
						recommendInfo.setImgUrl(pictureList.get(0)
								.getAbsolute580x290Url());
					}
				}

				if (psi != null
						&& Constant.PRODUCT_TYPE.TICKET.name().equals(
								psi.getProductType())) {
					Place p = prodProductPlaceService
							.getToDestByProductId(productId);
					recommendInfo.setPlaceId(p.getPlaceId());
					PlacePhoto pp = new PlacePhoto();
					pp.setType(PlacePhotoTypeEnum.LARGE.name());
					pp.setPlaceId(p.getPlaceId());
					List<PlacePhoto> ppList = this.placePhotoService
							.queryByPlacePhoto(pp);
					if (StringUtil.isEmptyString(recommendInfo.getImgUrl())) {
						if (ppList != null && !ppList.isEmpty()) {
							recommendInfo.setImgUrl(ppList.get(0)
									.getAbsoluteImageUrl());
						}
					}
					// comPictureService.getPictureByPK(pkId)
				}
				returnlist.add(recommendInfo);
			}
			MemcachedUtil.getInstance().set(HOT_CACHE, MemcachedUtil.TWO_HOUR,
					returnlist);
		} else {
			returnlist = (List<RecommendInfo>) o;
		}
		return returnlist;
	}

	@SuppressWarnings("unchecked")
	public List<RecommendInfo> getSellList(Map<String, Object> queryParams) {

		Long placeId = (Long) queryParams.get("placeId");
		final String SELL_CACHE = (String) queryParams.get("SELL_CACHE");

		List<RecommendInfo> returnlist = new ArrayList<RecommendInfo>();
		List<RecommendInfo> list = new ArrayList<RecommendInfo>();
		Object o = MemcachedUtil.getInstance().get(SELL_CACHE);
		if (o == null
				|| (o instanceof List && ((List<RecommendInfo>) o).size() == 0)) {
			Map<String, Object> param = new HashMap<String, Object>();

			param.put("dataCode", "XianShiTeMai");
			param.put("status", "true");

			List<ProdContainerFromPlace> listContainer = recommendInfoService
					.getBlocksByPlaceIdWitheContainerCode("HOME_RECOMMEND",
							placeId);
			for (ProdContainerFromPlace prodContainerFromPlace : listContainer) {
				param.put("parentRecommendBlockId",
						prodContainerFromPlace.getBlockId());
				List<RecommendInfo> listTemp = recommendInfoService
						.queryRecommendInfoByParam(param,
								prodContainerFromPlace.getBlockId());
				if (listTemp != null) {
					list.addAll(listTemp);
				}
			}

			for (RecommendInfo recommendInfo : list) {
				if (recommendInfo.getRecommObjectId() == null) {
					if (!StringUtil.isEmptyString(recommendInfo.getUrl())) {
						String lastKeyCode = recommendInfo.getUrl().substring(
								recommendInfo.getUrl().lastIndexOf("/") + 1,
								recommendInfo.getUrl().length());
						if (!StringUtil.isNumber(lastKeyCode)) {
							continue;
						}
						recommendInfo.setRecommObjectId(lastKeyCode);
					}
				}
				Date beginDate = DateUtil.parseDate(
						recommendInfo.getBakWord4(), "yyyy-MM-dd HH:mm:ss");
				Date endDate = DateUtil.parseDate(recommendInfo.getBakWord5(),
						"yyyy-MM-dd HH:mm:ss");
				Date currentDate = new Date();
				if (beginDate != null && endDate != null
						&& beginDate.before(currentDate)
						&& currentDate.before(endDate)) {

					Long productId = Long.valueOf(recommendInfo
							.getRecommObjectId());
					ProductSearchInfo psi = productSearchInfoService
							.queryProductSearchInfoByProductId(productId);
					if (psi != null
							&& psi.getChannel() != null
							&& !psi.getChannel().contains(
									Constant.CHANNEL.CLIENT.name())) {
						continue;
					}
					if (psi != null) {
						if (null != psi.getSellPrice()) {
							recommendInfo.setMarketPrice(psi.getSellPrice()
									.toString());
						}
						if (null != psi.getSellPrice()) {
							Long sellPrice = Long.valueOf(psi.getSellPrice());
							recommendInfo.setBakWord7(String
									.valueOf(sellPrice / 100));
						}
						recommendInfo.setBakWord8(psi.getProductType());
						recommendInfo.setBakWord9(psi.getSubProductType());// 子产品类型
						recommendInfo.setBakWord1(String.valueOf(psi
								.getOrderQuantitySum()));
						if ("true".equals(psi.getSelfPack())
								|| Constant.PRODUCT_TYPE.HOTEL.name().equals(
										psi.getProductType())
								|| "true".equals(psi.getIsAperiodic())) {
							continue;
						}
					} else {
						continue;
					}
					recommendInfo.setTitle(StringUtil
							.filterOutHTMLTags(recommendInfo.getTitle()));
					Map<String, Object> data = pageService.getProdCProductInfo(
							productId, false);
					ViewPage viewPage = (ViewPage) data.get("viewPage");
					if (viewPage != null) {
						List<ComPicture> pictureList = comPictureService
								.getPictureByPageId(viewPage.getPageId());
						if (pictureList != null && !pictureList.isEmpty()) {
							recommendInfo.setImgUrl(pictureList.get(0)
									.getAbsolute580x290Url());
						}
					}

					if (psi != null
							&& Constant.PRODUCT_TYPE.TICKET.name().equals(
									psi.getProductType())) {
						Place p = prodProductPlaceService
								.getToDestByProductId(productId);
						recommendInfo.setPlaceId(p.getPlaceId());
						PlacePhoto pp = new PlacePhoto();
						pp.setType(PlacePhotoTypeEnum.LARGE.name());
						pp.setPlaceId(p.getPlaceId());
						List<PlacePhoto> ppList = this.placePhotoService
								.queryByPlacePhoto(pp);
						if (StringUtil.isEmptyString(recommendInfo.getImgUrl())) {
							if (ppList != null && !ppList.isEmpty()) {
								recommendInfo.setImgUrl(ppList.get(0)
										.getAbsoluteImageUrl());
							}
						}
					}

					returnlist.add(recommendInfo);
				}

			}
			MemcachedUtil.getInstance().set(SELL_CACHE, MemcachedUtil.TWO_HOUR,
					returnlist);
		} else {
			returnlist = (List<RecommendInfo>) o;
		}
		return returnlist;
	}
	

	@Override
	public Map<String, Object> getHolidayData(Map<String, Object> params) {
		Long placeId = this.getPlaceIdByCity((String) params.get("city"));
		String HOLIDAY_CACHE = Constant.HOLIDAY_CACHE + placeId;

		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("placeId", placeId);
		queryParams.put("HOLIDAY_CACHE", HOLIDAY_CACHE);
		List<RecommendInfo> recommendInfos = this.getHolidayList(queryParams);

		if (recommendInfos.isEmpty()) {
			placeId = 79L;
			HOLIDAY_CACHE = Constant.HOLIDAY_CACHE + placeId;

			queryParams.put("placeId", placeId);
			queryParams.put("HOLIDAY_CACHE", HOLIDAY_CACHE);
			recommendInfos = this.getHolidayList(queryParams);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("recommendInfos", getRecommendInfos(recommendInfos));
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	public List<RecommendInfo> getHolidayList(Map<String, Object> queryParams) {
		Long placeId = (Long) queryParams.get("placeId");
		final String HOLIDAY_CACHE = (String) queryParams.get("HOLIDAY_CACHE");

		List<RecommendInfo> returnlist = new ArrayList<RecommendInfo>();
		List<RecommendInfo> list = new ArrayList<RecommendInfo>();
		Object o = MemcachedUtil.getInstance().get(HOLIDAY_CACHE);
		if (o == null
				|| (o instanceof List && ((List<RecommendInfo>) o).size() == 0)) {
			Map<String, Object> param = new HashMap<String, Object>();
			 //'ranked_destroute','ranked_around','ranked_abroad'
			 //包含国内游，周边游，出境游
//			 param.put("dataCode",
//			 "'ranked_destroute','ranked_around','ranked_abroad'");
			param.put("dataCode", queryParams.get("dataCode"));
			param.put("status", "true");

			List<ProdContainerFromPlace> listContainer = recommendInfoService
					.getBlocksByPlaceIdWitheContainerCode("HOME_RECOMMEND",
							placeId);
			for (ProdContainerFromPlace prodContainerFromPlace : listContainer) {
				param.put("parentRecommendBlockId",
						prodContainerFromPlace.getBlockId());
				List<RecommendInfo> listTemp = recommendInfoService
						.queryRecommendInfoByParam(param,
								prodContainerFromPlace.getBlockId());
//				List<RecommendInfo> listTemp = recommendInfoService
//						.queryRecommendInfoByParamAndDataCodes(param,
//								prodContainerFromPlace.getBlockId());
				if (listTemp != null) {
					list.addAll(listTemp);
				}
			}

			for (RecommendInfo recommendInfo : list) {
				if (recommendInfo.getRecommObjectId() == null) {
					if (!StringUtil.isEmptyString(recommendInfo.getUrl())) {
						String lastKeyCode = recommendInfo.getUrl().substring(
								recommendInfo.getUrl().lastIndexOf("/") + 1,
								recommendInfo.getUrl().length());
						if (!StringUtil.isNumber(lastKeyCode)) {
							continue;
						}
						recommendInfo.setRecommObjectId(lastKeyCode);
					}
				}
//				Date beginDate = DateUtil.parseDate(
//						recommendInfo.getBakWord4(), "yyyy-MM-dd HH:mm:ss");
//				Date endDate = DateUtil.parseDate(recommendInfo.getBakWord5(),
//						"yyyy-MM-dd HH:mm:ss");
//				Date currentDate = new Date();
//				if (beginDate != null && endDate != null
//						&& beginDate.before(currentDate)
//						&& currentDate.before(endDate)) {

					Long productId = Long.valueOf(recommendInfo
							.getRecommObjectId());
					ProductSearchInfo psi = productSearchInfoService
							.queryProductSearchInfoByProductId(productId);
					if (psi != null
							&& psi.getChannel() != null
							&& !psi.getChannel().contains(
									Constant.CHANNEL.CLIENT.name())) {
						continue;
					}
					if (psi != null) {
						if (null != psi.getSellPrice()) {
							recommendInfo.setMarketPrice(psi.getSellPrice()
									.toString());
						}
						if (null != psi.getSellPrice()) {
							Long sellPrice = Long.valueOf(psi.getSellPrice());
							recommendInfo.setBakWord7(String
									.valueOf(sellPrice / 100));
						}
						recommendInfo.setBakWord8(psi.getProductType());
						recommendInfo.setBakWord9(psi.getSubProductType());// 子产品类型
						recommendInfo.setBakWord1(String.valueOf(psi
								.getOrderQuantitySum()));
						if ("true".equals(psi.getSelfPack())
								|| Constant.PRODUCT_TYPE.HOTEL.name().equals(
										psi.getProductType())
								|| "true".equals(psi.getIsAperiodic())) {
							continue;
						}
					} else {
						continue;
					}
					recommendInfo.setTitle(StringUtil
							.filterOutHTMLTags(recommendInfo.getTitle()));
					Map<String, Object> data = pageService.getProdCProductInfo(
							productId, false);
					ViewPage viewPage = (ViewPage) data.get("viewPage");
					if (viewPage != null) {
						List<ComPicture> pictureList = comPictureService
								.getPictureByPageId(viewPage.getPageId());
						if (pictureList != null && !pictureList.isEmpty()) {
							recommendInfo.setImgUrl(pictureList.get(0)
									.getAbsolute580x290Url());
						}
					}

					if (psi != null
							&& Constant.PRODUCT_TYPE.TICKET.name().equals(
									psi.getProductType())) {
						Place p = prodProductPlaceService
								.getToDestByProductId(productId);
						recommendInfo.setPlaceId(p.getPlaceId());
						PlacePhoto pp = new PlacePhoto();
						pp.setType(PlacePhotoTypeEnum.LARGE.name());
						pp.setPlaceId(p.getPlaceId());
						List<PlacePhoto> ppList = this.placePhotoService
								.queryByPlacePhoto(pp);
						if (StringUtil.isEmptyString(recommendInfo.getImgUrl())) {
							if (ppList != null && !ppList.isEmpty()) {
								recommendInfo.setImgUrl(ppList.get(0)
										.getAbsoluteImageUrl());
							}
						}
					}

					returnlist.add(recommendInfo);
				//}

			}
			MemcachedUtil.getInstance().set(HOLIDAY_CACHE,
					MemcachedUtil.TWO_HOUR, returnlist);
		} else {
			returnlist = (List<RecommendInfo>) o;
		}
		return returnlist;
	}


	@Override
	public String getProvinceByCityName(String city) {
		ComCity comCity = placeCityService.selectByProvinceAndName(null, city);
		if(null!=comCity)
			return comCity.getProvinceId();
		return "310000";//上海
	}

	/**
	 * 根据参数获取相应的key .
	 * 
	 * @param params
	 * @return key
	 */
	public String getMemcacheKeyByParams(Map<String, Object> params) {
		String memcacheKey = "";
		// 先从缓存中区
		try {
			memcacheKey = params.get("method").toString();
			// MD5.encode(params.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memcacheKey;
	}

	/**
	 * 先从缓存中回去信息.
	 * 
	 * @param params
	 * @return obj
	 */
	public Object getMemecachedInfo(String memcacheKey) {
		Object obj = null;
		// 先从缓存中区
		if (!StringUtils.isEmpty(memcacheKey)) {
			obj = MemcachedUtil.getInstance().get(
					MOBILE_RECOMMEND_CACHE + memcacheKey);
		}

		return obj;
	}

	/**
	 * 转换大写.
	 * 
	 * @param str
	 * @return str
	 */
	public String str2UpperCase(String str) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		return str.toUpperCase();
	}

	public void setMobileRecommendBlockService(
			MobileRecommendBlockService mobileRecommendBlockService) {
		this.mobileRecommendBlockService = mobileRecommendBlockService;
	}

	public void setMobileRecommendInfoService(
			MobileRecommendInfoService mobileRecommendInfoService) {
		this.mobileRecommendInfoService = mobileRecommendInfoService;
	}

	public void setRecommendInfoService(
			RecommendInfoService recommendInfoService) {
		this.recommendInfoService = recommendInfoService;
	}

	public void setProdProductPlaceService(
			ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}

	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}

	public void setProductSearchInfoService(
			ProductSearchInfoService productSearchInfoService) {
		this.productSearchInfoService = productSearchInfoService;
	}

	public void setPlacePhotoService(PlacePhotoService placePhotoService) {
		this.placePhotoService = placePhotoService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	

	@Override
	public Map<String, Object> getInternalDestRecommendCities( Map<String, Object> param) {
		return null;
	}

	@Override
	public Map<String, Object> getOverseasDestRecommendCities( Map<String, Object> param) {
		return null;
	}

	@Override
	public Map<String,Object> getIndexRecommend(Map<String, Object> params) {
		Long blockId = Long.valueOf(ClientRecommendProperties.getBlockId("6")); // 获取block的id.
		params.put("parentId", blockId);
		params.put("currentDate", new Date()); // 只有首页推荐有该时间 .当前时间 .
		params.put("isValid", "Y"); // 是否有效
		params.put("count", 100);
		params.put("page", 1);
		if ("IPAD".equals(params.get("firstChannel"))) {
			return getHDCommonRecommendInfo(params);
		} else {
			return getCommonRecommendInfo(params);
		}
	}

	@Override
	public Map<String,Object> getFreetripRecommend4Map(Map<String, Object> params) {
		Long blockId = Long.valueOf(ClientRecommendProperties.getBlockId("7"));
		params.put("parentId", blockId);
		return getCommonRecommendInfo(params, "2");
	}

}
