package com.lvmama.clutter.web.route;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.json.JSONException;
import org.json.JSONObject;

import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.model.MobileProductRoute;
import com.lvmama.clutter.service.IClientOfflineCacheService;
import com.lvmama.clutter.service.IClientProductService;
import com.lvmama.clutter.service.IClientRecommendService;
import com.lvmama.clutter.service.IClientSearchService;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.search.vo.ProductBean;

/**
 * 度假.
 * 
 * @author qinzubo
 */
@Results({
		@Result(name = "holiday_index", location = "/WEB-INF/pages/holiday/holiday.html", type = "freemarker"),
		@Result(name = "holiday_index_ajax", location = "/WEB-INF/pages/holiday/holiday_index_ajax.html", type = "freemarker"),
		@Result(name = "holiday_list", location = "/WEB-INF/pages/holiday/holiday_list.html", type = "freemarker"),
		@Result(name = "holiday_search", location = "/WEB-INF/pages/holiday/holiday_search.html", type = "freemarker"),
		@Result(name = "holiday_ajax", location = "/WEB-INF/pages/holiday/holiday_ajax.html", type = "freemarker"),
		@Result(name = "holiday_choose_city", location = "/WEB-INF/pages/holiday/holiday_choose_fromDest.html", type = "freemarker"),
		@Result(name = "holiday_detail", location = "/WEB-INF/pages/holiday/holiday_detail.html", type = "freemarker") })
@Namespace("/mobile/holiday")
public class HolidayAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 产品服务
	 */
	IClientProductService mobileProductService;

	/**
	 * 推荐服务.
	 */
	IClientRecommendService mobileRecommendService;

	/**
	 * 搜索服务.
	 */
	IClientSearchService mobileSearchService;

	/**
	 * 
	 */
	IClientOfflineCacheService mobileOfflineCacheService;

	private String productId; // 产品id
	private String toDest;// 目的地
	private String keyword;
	private String subProductType;//
	private String subject;// 主题类型
	private String sort = "seq";// 排序

	private int page = 1; // 第几页 ; pageSize每页显示多少行
	private String pageSize;// 一页显示多少条

	private String fromDest;// 出发地
	private boolean ajax;//
	
	private String holidayClickStatus;//用于判断是从度假首页点击还是度假列表页点击城市--holiday_list是从度假列表页点击

	/**
	 * holiday_index
	 */
	@Action("holiday")
	public String index() {
		// 图片地址前缀
		try {
			if (ajax) {
				// 设置图片前缀
				this.setImagePrefix();
				Map<String, Object> params = new HashMap<String, Object>();
				if (StringUtils.isEmpty(fromDest)) {
					fromDest = "上海";
				} else {
					fromDest = URLDecoder.decode(fromDest, "UTF-8");
				}
				params.put("method","api.com.recommend.getRouteArroundRecommendCities"+getString(fromDest)) ;
				params.put("blockNameLike", fromDest);
				Map<String, Object> map = mobileRecommendService.getRouteArroundRecommendCities(params);
				if (null != map && null != map.get("datas")) {
					List<Map<String, Object>> recommendMapList = (List<Map<String, Object>>) map
							.get("datas");
					if (null != recommendMapList && recommendMapList.size() > 0) {
						for (Map<String, Object> m : recommendMapList) {
							if (null != m
									&& null != m.get("name")
									&& fromDest
											.equals(m.get("name").toString())) {
								getRequest().setAttribute("destList",
										m.get("data"));
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ajax) {
			return "holiday_index_ajax";
		} else {
			return "holiday_index";
		}
	}

	public String getString(String chinese) {
		if(StringUtils.isEmpty(chinese)) {
			return "";
		}
		byte[] bs;
		String s = "";
		try {
			bs = chinese.getBytes("GB2312");
			for (int i = 0; i < bs.length; i++) {
				int a = Integer.parseInt(bytes2HexString(bs[i]), 16);
				s += (a - 0x80 - 0x20) + "";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public String bytes2HexString(byte b) { 
		return bytes2HexString(new byte[] { b }); 
	} 
	
	public String bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}
	
	// 度假列表 .
	@Action("holiday_list")
	public String holidayList() {
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			initParams(param);
			Map<String, Object> resultMap = mobileSearchService.routeSearch(param);
			if (null != resultMap) { // List<MobileProductTitle> mpList
				getRequest().setAttribute("mobileProductList", resultMap.get("datas"));
				getRequest() .setAttribute("subjects", resultMap.get("subjects"));
				getRequest().setAttribute("isLasgPage",
						resultMap.get("isLastPage"));
			}

			if (ajax) {
				return "holiday_ajax";
			}
			Map<String, Object> cacheMap = mobileOfflineCacheService
					.getRouteFilterCache(new HashMap<String, Object>());
			if (null != cacheMap) {
				// getRequest().setAttribute("subjects",
				// cacheMap.get("subjects"));
				getRequest().setAttribute("sortTypes",
						cacheMap.get("sortTypes"));
				getRequest().setAttribute("routeTypes",
						cacheMap.get("routeTypes"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ajax) {
			return "holiday_ajax";
		}
		return "holiday_list";
	}

	// 度假列表 .
	@Action("holiday_search")
	public String holidaySearchList() {
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			initParams(param);
			Map<String, Object> resultMap = mobileSearchService.routeSearch(param);
			if (null != resultMap) { // List<MobileProductTitle> mpList
				getRequest().setAttribute("mobileProductList",
						resultMap.get("datas"));
				getRequest()
						.setAttribute("subjects", resultMap.get("subjects"));
				getRequest().setAttribute("isLasgPage",
						resultMap.get("isLastPage"));
			}

			if (ajax) {
				return "holiday_ajax";
			}
			Map<String, Object> cacheMap = mobileOfflineCacheService
					.getRouteFilterCache(new HashMap<String, Object>());
			if (null != cacheMap) {
				// getRequest().setAttribute("subjects",
				// cacheMap.get("subjects"));
				getRequest().setAttribute("sortTypes",
						cacheMap.get("sortTypes"));
				getRequest().setAttribute("routeTypes",
						cacheMap.get("routeTypes"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "holiday_search";
	}

	@Action("listRoute")
	public String listRoute() throws Exception {
		HttpServletResponse response = this.getResponse();
		response.setCharacterEncoding("gb2312");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("toDest", " ");
		param.put("fromDest", " ");
		param.put("page", page);
		param.put("pageSize", pageSize);
		// initParams(param);
		Page<ProductBean> page = mobileSearchService.listRoute(param);
		List<ProductBean> productBeanList = page.getItems();
		if (null != productBeanList && productBeanList.size() > 0) {
			List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
			for (ProductBean productBean : productBeanList) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("Name", productBean.getProductName());
				dataMap.put(
						"Pic",
						"http://pic.lvmama.com/pics/"
								+ productBean.getSmallImage());
				dataMap.put("Price", productBean.getMarketPrice());
				dataMap.put("Url", "http://m.lvmama.com/clutter/route/"
						+ productBean.getProductId());
				dataMap.put("source", productBean.getFromDest());
				dataMap.put("destination", productBean.getToDest());
				datas.add(dataMap);
			}
			PrintWriter out = response.getWriter();
			try {
				JSONObject json = new JSONObject();
				json.put("Items", datas);
				json.put("Version", "1.0");
				json.put("SourceSiteName", "驴妈妈旅游网");
				json.put("TotalCount", page.getTotalResultSize());
				json.put("TotalPageNum", page.getTotalPageNum());
				json.put("Page", page.getCurrentPage());
				out.println(json);
			} catch (JSONException e) {
				throw new LogicException("JSON转化异常！", e);
			} finally {
				out.flush();
				out.close();
			}
		}
		return null;
	}

	/**
	 * 选择出发地
	 */
	@Action("holiday_choose_city")
	public String chooseFromDest() {
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			// 出发地城市 getDepaturePlace
			param.put("method", "api.com.recommend.getRouteFromDest");
			Map<String, Object> resultMap = mobileRecommendService
					.getRouteFromDest(param);
			getRequest().setAttribute("cityListJson",
					new ArrayList<Map<String, Object>>());
			if (null != resultMap && null != resultMap.get("datas")) {
				List<Map<String, Object>> blockMapList = (List<Map<String, Object>>) resultMap
						.get("datas");
				if (null != blockMapList && blockMapList.size() > 0) {
					getRequest().setAttribute("cityList", blockMapList);
					// 热门城市搜索用到 .
					getRequest().setAttribute("cityListJson",
							JSONArray.fromObject(blockMapList));


					Set<String> slist = getChars((List<Map<String, Object>>) blockMapList);
					getRequest().setAttribute("charList", slist);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "holiday_choose_city";
	}


	/**
	 * 获取pinyin首字母
	 * 
	 * @param mList
	 * @return
	 */
	public Set<String> getChars(List<Map<String, Object>> mList) {
		Map<String, Object> t_m = new HashMap<String, Object>();
		for (Map<String, Object> m : mList) {
			if (null != m && null != m.get("pinyin")) {
				String str = m.get("pinyin").toString().substring(0, 1);
				t_m.put(str, str);
			}
		}
		return new TreeSet(t_m.keySet());
	}

	/**
	 * 初始化参数.
	 * 
	 * @param param
	 * @throws UnsupportedEncodingException
	 */
	public void initParams(Map<String, Object> param)
			throws UnsupportedEncodingException {
		if (StringUtils.isEmpty(fromDest)) {
			fromDest = " ";
		} else {
			fromDest = URLDecoder.decode(fromDest, "UTF-8");
		}
		param.put("fromDest", fromDest);

		if (StringUtils.isEmpty(toDest)) {
			toDest = "上海";
		} else {
			toDest = URLDecoder.decode(toDest, "UTF-8");
		}
		param.put("toDest", toDest);
		param.put("sort", sort);
		param.put("page", page);
		if (!StringUtils.isEmpty(subject)) {
			subject = URLDecoder.decode(subject, "UTF-8");
			param.put("subject", subject); // 主题
		}
		param.put("subProductType", subProductType);

	}

	// 自由行详情 .
	@Action("holiday_detail")
	public String holidayDetail() {
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			UserUser u = getUser();
			if (null != u) {
				param.put("userNo", u.getUserNo());
				param.put("userId", u.getId());
			}
			param.put("productId", Long.valueOf(productId));
			MobileProductRoute mpr = mobileProductService.getRouteDetail(param);
			if(null != mpr && !StringUtils.isEmpty(mpr.getProductName())) {
				mpr.setProductName(ClientUtils.filterQuotationMarks(mpr.getProductName()));
			}
			getRequest().setAttribute("mpr", mpr);
			getRequest().setAttribute("ios", isIos());
			getRequest().setAttribute("android", isAndroid());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "holiday_detail";
	}



	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getToDest() {
		return toDest;
	}

	public void setToDest(String toDest) {
		this.toDest = toDest;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFromDest() {
		return fromDest;
	}

	public void setFromDest(String fromDest) {
		this.fromDest = fromDest;
	}

	public boolean isAjax() {
		return ajax;
	}

	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public void setMobileProductService(IClientProductService mobileProductService) {
		this.mobileProductService = mobileProductService;
	}

	public void setMobileRecommendService(
			IClientRecommendService mobileRecommendService) {
		this.mobileRecommendService = mobileRecommendService;
	}

	public void setMobileSearchService(IClientSearchService mobileSearchService) {
		this.mobileSearchService = mobileSearchService;
	}

	public void setMobileOfflineCacheService(
			IClientOfflineCacheService mobileOfflineCacheService) {
		this.mobileOfflineCacheService = mobileOfflineCacheService;
	}

	public String getHolidayClickStatus() {
		return holidayClickStatus;
	}

	public void setHolidayClickStatus(String holidayClickStatus) {
		this.holidayClickStatus = holidayClickStatus;
	}
	
}
