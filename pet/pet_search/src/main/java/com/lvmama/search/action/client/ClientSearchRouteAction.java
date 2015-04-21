package com.lvmama.search.action.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.Query;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.vo.AutoCompletePlaceObject;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.utils.UniformResourceLocator;
import com.lvmama.search.action.web.SearchBaseAction;
import com.lvmama.search.lucene.document.ProductDocument;
import com.lvmama.search.lucene.query.QueryUtilForClient;
import com.lvmama.search.util.LoggerParms;
import com.lvmama.search.util.PageConfig;
import com.lvmama.search.util.SORT;
import com.lvmama.search.util.SearchStringUtil;

/**
 * 手机端线路产品搜索接口
 * @return
 * @huangzhi 
 * @throws IOException
 */	
@Action
public class ClientSearchRouteAction extends SearchBaseAction {
	private static final long serialVersionUID = -2699789655931574408L;
	private Log loger = LogFactory.getLog(getClass());
	/** 分页对象 */
	private PageConfig<ProductBean> pageConfig;
	/** 产品每页显示数量 */
	private int pageSize = 10;
	/** 当前页数 */
	private int page = 1;
	/** 二次搜索当前页数 */
	private int page2 = 1;
	/** 游玩天数**/
	private String visitDay = "";
	/**按产品价格排序**/
	private String sort="";
	/**按产品经济性筛选**/
	private String priceType="";
	private String fromDest = "";
	private String toDest = "";
	private String routeType="";
	private String tag = "";
	/**页面控制参数表示关键字是否能匹配出结果，默认能匹配出结果**/
	private boolean keywordValid=true;
	/**二次搜索关键字**/
	private String keyword2="";
	/**对线路产品的名称进行搜索,支持多个以空格分词的关键字复合搜索,最大数有限制**/
	private String poductNameSearchKeywords = "";
	/**主题搜索参数**/
	private String keyword="";
	private String cityId="";
	/**
	 * 线路产品统一搜索接口(带经纬度算差距) ：省市(则转换为找省市下的线路产品, 接受关键字/省市/主题/标签
	 * 
	 * @param fromDest =出发点ID&toDest=中文/简拼/全拼/主题/城市/景点&keyword2=&city=cityId||cityName&visitDay=&subject=&tag=&priceType=&sort=[up||dn||空（SEQ）]
	 *        &routeType=[freeness||destroute||around||abroad||all]&fromPage=isClient&page=&pagesize=
	 * @throws IOException
	 * @return JASON格式
	 */
	@SuppressWarnings("unchecked")
	@Action("clientRouteSearch")
	public void routeSearch() throws IOException {
		if (StringUtils.isNotEmpty(SearchStringUtil.treatKeyWord(toDest))||!subject.isEmpty()||!tag.isEmpty()) {
			loger.info("ANYWHERE->" + toDest);
			loger.info("SECH!;" + LoggerParms.getNowTime() + ";" + LoggerParms.getSessionId(getSession()) + ";" + LoggerParms.getIpAddr(getRequest()) + ";" + "手机端" + ";" + "统一搜索" + ";"	+ "" + ";"
					+toDest+keyword2+";;;" + toDest + ";" + page + ";" + city + ";" + subject + ";" + tag + ";" + visitDay + ";" + sort + ";"
					+ priceType + ";" + keywordValid + ";" + "SEND!");
			try {
				/**如果是二次搜索,则把翻页设置为page2**/
				if (StringUtils.isNotEmpty(keyword2.trim())) {
					page=page2;
				}
				Map<String, String> parm = initParam();
				Query q = QueryUtilForClient.getProductSearchAllQuery(parm);
				//手机端排序统一参数priceAsc||priceDesc||空（SEQ）
				SORT[] sorts = null;
				if (StringUtils.isNotEmpty(sort)) {
					sorts = new SORT[1];
					if (sort.equals("priceAsc")) {
						sorts[0] = SORT.priceUp;
					}else if (sort.equals("priceDesc")) {
						sorts[0] = SORT.priceDown;
					}else  {
						sorts[0] = SORT.seq;
					}
				}
				List<ProductBean> matchList = newProductSearchService.search(q, sorts);
				/*
				 * 当对线路的搜索结果为空时，重新对产品的名称进行一次搜索，对关键字具有空格分词的功能
				 */
				if (matchList.size() == 0) {
					if (StringUtils.isNotEmpty(toDest)) {
						poductNameSearchKeywords=toDest;
						parm= initParam();
						q = QueryUtilForClient.getProductSearchAllQuery(parm);
						matchList = newProductSearchService.search(q, sorts);
					}
				}
				String json = "";
				if (matchList!=null && !matchList.isEmpty()) {
					debugPrint(matchList);
					json = convertPlaceListToJsonString(matchList ,matchList.size());					
				}
				String callback = this.getRequest().getParameter("callback");
				this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
				if (callback == null) {
					this.getResponse().getWriter().println(json); // 返回CONTENT.
				} else {
					this.getResponse().getWriter().println(callback + "(" + json + ")"); // 返回CONTENT.
				}
			
			} catch (Exception e) {
				System.out.println("线路搜索手机端接口出错！");
				e.printStackTrace();
			}
		}
	}
	
	private void debugPrint(List<ProductBean> matchList) {
		for (int i = 0; i < matchList.size(); i++) {
			loger.debug(i + "搜索结果：" + matchList.get(i).getProductName() + ":" + matchList.get(i).getFromDest() + ": " + matchList.get(i).getToDest() + ": " + matchList.get(i).getProductId() + ": "
					+ matchList.get(i).getSellPrice() + "SEQ=" + matchList.get(i).getSeq() + ": 价格 = " + matchList.get(i).getSellPrice() + "               " + matchList.get(i).getVisitDay());
		}
	}
	
	/**
	 * 搜索自动补全景点/城市/主题/标签/叙词/出境区域
	 */
	@Action("clientRouteSearch")
	public void getAutoComplete() throws IOException {
		toDest = SearchStringUtil.treatKeyWord(toDest);
		if (StringUtils.isNotEmpty(toDest)) {
			loger.info("toDest: " + toDest);
			loger.info("SECH!;" + LoggerParms.getNowTime() + ";" + LoggerParms.getSessionId(getSession()) + ";" + LoggerParms.getIpAddr(getRequest()) + ";" + "自游自在" + ";" + routeType + ";" + ""+ ";"
					+ toDest+keyword2+";;;" + toDest + ";" + page + ";" + city + ";" + subject + ";" + tag + ";" + visitDay + ";" + sort + ";" + priceType + ";" + "自动补全" + ";" + "SEND!");
			List<AutoCompletePlaceObject> matchList = autoCompleteService.getAutoCompletePlacePlaceObjectListMatched(SearchConstants.FREETOUR_CHANNEL_TYPE, null, toDest, 50);
			String json = "";
			if (!matchList.isEmpty()) {
				json = convertAutoCompletePlaceToJsonString(matchList);
			}
			String callback = this.getRequest().getParameter("callback");
			this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
			if (callback == null) {
				this.getResponse().getWriter().println(json); // 返回CONTENT.
			} else {
				this.getResponse().getWriter().println(callback + "(" + json + ")"); // 返回CONTENT.
			}
		}
	}

	/**
	 * 获得路线的主题/标红主题
	 * 
	 * @author huangzhi
	 * @throws IOException
	 * @return JASON格式
	 */
	@Action("clientRouteSearch")
	public void getRouteSubject() throws IOException {
		loger.info("SECH!;" + LoggerParms.getNowTime() + ";" + LoggerParms.getSessionId(getSession()) + ";" + LoggerParms.getIpAddr(getRequest()) + ";" + "手机端" + ";" + "" + ";" + "" + ";"
				+ keyword + ";" + "" + ";" + "" + ";" + "" + ";" + page + ";" + "" + ";" + "" + ";" + "" + ";" + "" + ";" + sort + ";" + "" + ";" + "自动补全" + ";" + "SEND!");
		try {
			List<AutoCompletePlaceObject> matchList ;
			if (StringUtils.isNotEmpty(keyword)) {
				if (StringUtils.isNotEmpty(cityId)) {
					matchList = autoCompleteService.getAutoCompletePlacePlaceObjectListMatched(SearchConstants.CLIENT_ROUTER_SUBJECT, Long.parseLong(cityId), keyword, 1000);
				} else {
					matchList = autoCompleteService.getAutoCompletePlacePlaceObjectListMatched(SearchConstants.CLIENT_ROUTER_SUBJECT, 9999L, keyword, 1000);
				}
			} else {
				if (StringUtils.isNotEmpty(cityId)) {
					matchList = autoCompleteService.getAutoCompletePlacePlaceObjectListDefault(SearchConstants.CLIENT_ROUTER_SUBJECT, Long.parseLong(cityId), 1000);
				} else {
					matchList = autoCompleteService.getAutoCompletePlacePlaceObjectListDefault(SearchConstants.CLIENT_ROUTER_SUBJECT, 9999L, 1000);
				}
			}
				String json = "";
				json = convertAutoCompletePlaceToJsonString(matchList);
				String callback = this.getRequest().getParameter("callback");
				this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
				if (callback == null) {
					this.getResponse().getWriter().println(json); // 返回CONTENT.
				} else {
					this.getResponse().getWriter().println(callback + "(" + json + ")"); // 返回CONTENT.
				}
		} catch (Exception e) {
			System.out.println("手机端景点主题出错！");
			e.printStackTrace();
		}
	}

	/**
	 * 封装成JSON格式
	 * @param matchList
	 * @return
	 */
	private String convertAutoCompletePlaceToJsonString(List<AutoCompletePlaceObject> matchList) {
		StringBuffer json = new StringBuffer("{");
		json.append("\"page\":" + page + ",");
		json.append("\"totalResultSize\":" + matchList.size());
		json.append(",\"placeListJson\":");
		json.append("[");
		for (AutoCompletePlaceObject autoCompletePlaceObject : matchList) {
			if (!autoCompletePlaceObject.getWords().equals("0")) {
			json.append("{");
			json.append("\"pinyin\":\"" + autoCompletePlaceObject.getPinyin() + "\",");//拼音字段被复用为URL
			json.append("\"id\":\"" + autoCompletePlaceObject.getShortId() + "\",");
			json.append("\"name\":\"" + autoCompletePlaceObject.getWords() + "\"");
			json.append("},");
			}
		}
		if (json.toString().endsWith(",")) {// 如果多一个逗号结尾,去逗号
			json.deleteCharAt(json.length() - 1).append("]");
		} else {// 为空或翻页越界时候
			json.append("]");
		}
		json.append("}");
		return json.toString();
	}
	
	/**
	 * 封装成JSON格式
	 * 
	 * @param matchList
	 * @return
	 * @throws IOException 
	 */
	private String convertPlaceListToJsonString(List<ProductBean> matchList , int totalResultSize) throws IOException {
		StringBuffer json = new StringBuffer("{");
		//如果keyword不为空而且是省市则返回省市id
		String cityIdbyKeyword="-1";
		if (StringUtils.isNotEmpty(cityId)) {
			cityIdbyKeyword = cityId;
		} else if (StringUtils.isNotEmpty(toDest)) {
			cityIdbyKeyword = findCityidByKeyword(toDest);
		}
		json.append("\"cityId\":" + cityIdbyKeyword + ",");
		json.append("\"page\":" + page + ",");
		json.append("\"totalResultSize\":" +totalResultSize);
		json.append(",\"routeListJson\":");
		json.append("[");
		for (int i = (page - 1) * pageSize; (i < matchList.size()) && (i < page * pageSize); i++) {// 翻页参数控制翻页
																									// pageSize:page
			ProductBean productBean = matchList.get(i);
			json.append("{");
			json.append("\"id\":\"" + productBean.getProductId() + "\",");
			json.append("\"name\":\"" + productBean.getProductName().replace("\"", "“") + "\","); //半角双引号导致json报错，改为全角双引号
			json.append("\"productType\":\"" + productBean.getProductType() + "\",");
			json.append("\"subProductType\":\"" + productBean.getSubProductType() + "\",");
			json.append("\"fromDest\":\"" + productBean.getFromDest() + "\",");
			json.append("\"toDest\":\"" + productBean.getToDest() + "\",");
			json.append("\"middleImage\":\"" + productBean.getSmallImage() + "\",");
			json.append("\"subject\":\"" + (productBean.getTopic().indexOf(",")>0 ? productBean.getTopic().substring(0, productBean.getTopic().indexOf(",")) :productBean.getTopic()) + "\",");
			json.append("\"marketPrice\":\"" + productBean.getMarketPrice() + "\",");//分为单位
			json.append("\"sellPrice\":\"" +  productBean.getSellPrice() + "\",");//分为单位
			json.append("\"placeTitel\":\"" + productBean.getSubProductType() + "\"");
			json.append("},");
		}
		if (json.toString().endsWith(",")) {// 如果多一个逗号结尾,去逗号
			json.deleteCharAt(json.length() - 1).append("]");
		} else {// 为空或翻页越界时候
			json.append("]");
		}
		json.append("}");
		return json.toString();
	}
	
	//如果输入的是省市关键字，则返回省市ID号
	private String findCityidByKeyword(String keyword) throws IOException {
		String cityId="-1";
		if (StringUtils.isNotEmpty(SearchStringUtil.treatKeyWord(keyword))) {
			PageConfig<PlaceBean> cityPageConfig = newPlaceSearchService.search(10, page, QueryUtilForClient.getCityQuery(keyword));
			if (cityPageConfig.getItems().size() > 0) {
				cityId = cityPageConfig.getItems().get(0).getShortId();	
			}
		}
		return cityId;
	}
	
	/**
	 * 初始化搜索条件的参数
	 * @return
	 */
	private Map<String, String> initParam() {
		Map<String, String> map = new HashMap<String, String>(20);
		if (StringUtils.isNotEmpty(toDest.trim())) {
			map.put(ProductDocument.TO_DEST, SearchStringUtil.treatKeyWord(toDest.trim()));
			map.put(ProductDocument.PRODUCT_ALLTO_PLACE_PINYIN, toDest);
		}
		//二次搜索关键字
		if (StringUtils.isNotEmpty(keyword2.trim())) {
			map.put("keyword2", SearchStringUtil.treatKeyWord(keyword2.trim()));
		}
		//对线路产品的名称进行搜索,支持多个以空格分词的关键字复合搜索,用数组存储,如果数据第一个值不为空，则至少有一个值
		if (StringUtils.isNotEmpty(poductNameSearchKeywords)) {
			map.put("poductNameSearchKeywords", poductNameSearchKeywords);
		}
		map.put(ProductDocument.FROM_DEST, fromDest);
		map.put("city", city);
		map.put("subject", subject);
		map.put("tagName", tag);
//		//手机端排序统一参数priceAsc||priceDesc||空（SEQ）
//		if (StringUtils.isNotEmpty(sort)) {
//			if (sort.equals("priceAsc")) {
//				sort="up";
//			}else if (sort.equals("priceDesc")) {
//				sort="dn";
//			}
//		}
//		map.put("sortPrice", sort);
		map.put("productRouteTypeName", priceType);
		map.put("visitDay", visitDay);
		map.put(ProductDocument.PRODUCT_CHANNEL, super.FRONTEND);
		if ("freeness".equalsIgnoreCase(routeType)) {//自由自在
			map.put(ProductDocument.SUB_PRODUCT_TYPE, "FREENESS");
		} else if ("destroute".equalsIgnoreCase(routeType)) {//国内长途
			map.put(ProductDocument.SUB_PRODUCT_TYPE, "GROUP_LONG,FREENESS_LONG,FREENESS");
		} else if ("around".equalsIgnoreCase(routeType)) {//周边跟团
			map.put(ProductDocument.SUB_PRODUCT_TYPE, "GROUP,SELFHELP_BUS,FREENESS");
		} else if ("abroad".equalsIgnoreCase(routeType)) {//国外游
			map.put(ProductDocument.SUB_PRODUCT_TYPE, "GROUP_FOREIGN,FREENESS_FOREIGN,FREENESS");
		}
//
//		//兼容老版本设置空参数,搜索排序需要
//		map.put("agioSeq", "");
//		map.put("sellSeq", "");
//		map.put("isTicket", "");
		return map;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getKeyword2() {
		return keyword2;
	}

	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}

	public String getFromDest() {
		return fromDest;
	}

	public void setFromDest(String fromDest) {
		this.fromDest = fromDest;
	}

	public String getToDest() {
		return toDest;
	}
	public void setToDest(String toDest) {
		this.toDest = toDest;
	}

	public String getCityEcode() {
		if (StringUtils.isNotEmpty(city)) {
			return UniformResourceLocator.encode(city);
		} else {
			return "";
		}
	}

	public String getSubjectEcode() {
		if (StringUtils.isNotEmpty(subject)) {
			return UniformResourceLocator.encode(subject);
		} else {
			return "";
		}
	}
	
	public String getTagEncode() {
		if (StringUtils.isNotEmpty(this.tag)) {
			return UniformResourceLocator.encode(this.tag);
		} else {
			return "";
		}
	}	
	
	public String getToDestEncode() {
		if (StringUtils.isNotEmpty(this.toDest)) {
			return UniformResourceLocator.encode(this.toDest);
		} else {
			return "";
		}
	}

	public PageConfig<ProductBean> getPageConfig() {
		return pageConfig;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPage2() {
		return page2;
	}

	public void setPage2(int page2) {
		this.page2 = page2;
	}

	public int getPagesize() {
		return pageSize;
	}

	public void setPagesize(int pagesize) {
		this.pageSize = pagesize;
	}

	public void setPageConfig(PageConfig<ProductBean> pageConfig) {
		this.pageConfig = pageConfig;
	}
	
	public String getProductRouteTypeName() {
		return priceType;
	}

	public void setProductRouteTypeName(String productRouteTypeName) {
		this.priceType = productRouteTypeName;
	}

	public String getProductRouteTypeNameEncode() {
		if (StringUtils.isNotEmpty(this.priceType)) {
			return UniformResourceLocator.encode(this.priceType);
		} else {
			return "";
		}
	}

	public String getVisitDay() {
		return visitDay;
	}

	public void setVisitDay(String visitDay) {
		/**urlrewriter 重写导致visitDay 格式只能为(day1,day2)， 或为空:(,) ,为空时候要去掉逗号，保持一致**/
		if(StringUtils.isNotEmpty(visitDay)&&!visitDay.equals(",")){
			this.visitDay=visitDay;
		}else if (visitDay.equals(",")) {
			this.visitDay="";
		}
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	public String getRouteType() {
		return routeType;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isKeywordValid() {
		return keywordValid;
	}

	public void setKeywordValid(boolean keywordValid) {
		this.keywordValid = keywordValid;
	}

	public void setPoductNameSearchKeywords(String poductNameSearchKeywords) {
		this.poductNameSearchKeywords = poductNameSearchKeywords;
	}

	public String getPoductNameSearchKeywords() {
		return poductNameSearchKeywords;
	}

}
