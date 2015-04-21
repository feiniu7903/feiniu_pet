package com.lvmama.search.action.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.vo.AutoCompletePlaceObject;
import com.lvmama.comm.search.vo.JasonPlaceBean;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.TreeBean;
import com.lvmama.comm.utils.StackOverFlowUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.search.action.web.SearchBaseAction;
import com.lvmama.search.lucene.query.QueryUtilForClient;
import com.lvmama.search.lucene.service.autocomplete.AutoCompleteService;
import com.lvmama.search.util.LoggerParms;
import com.lvmama.search.util.PageConfig;
import com.lvmama.search.util.SearchStringUtil;

/**
 * 手机端景点|酒店搜索接口
 * 
 * @return
 * @throws IOException
 */
@Results({
	@Result(name="ticket",location="/WEB-INF/ftl/ticket/ticket_search.ftl",type="freemarker")
})
@Action
public class ClientSearchAction extends SearchBaseAction {
	private static final long serialVersionUID = -2161508894972378430L;
	private Log loger = LogFactory.getLog(getClass());
	/** 关键字 */
	private String keyword = "";
	/** 分页对象 */
	private PageConfig<Map<String, Object>> pageConfig;
	/** 产品每页显示数量 */
	private int pageSize = 10;
	/** 当前页数 */
	private int page = 1;
	/** 经纬度 */
	private String x = "";
	/** 经纬度 */
	private String y = "";
	/** 邻近范围半径单位米(m) 默认3公里 **/
	private String windage = "3000";
	/** 城市ID */
	private String cityId = "";
	/**邻近搜索是,是否是以一个景点为中心，还是以坐标XY为中心，默认不是**/
	private boolean cityIdFirst=false;
	/** 关键字是否能匹配出结果，默认能匹配出结果 **/
	private boolean keywordValid = true;
	/**
	 * @introduce Client搜索省市树类型参数 ,
	 * @TYPE 景点|酒店|自由行线路|所有三种|
	 * @param hasTicket
	 *            |hasHotel|hasFreeness|ALL
	 **/
	private String productType = "";
	/** 搜索接口的调用方, 默认=浏览器, 点评栏目=dianpin , 手机端=isClient **/
	private String fromPage = "";
	/** 城市范围 */
	private String cityName = "";
	/** 酒店周边景点 **/
	private String spot = "";
	/** 酒店价格范围 */
	private String priceRange = "";
	/** 酒店星级 */
	private String star = "";
	/** 酒店类型 */
	private String hotelType = "";
	/** 排序：距离||seq||伪字典顺序||价格升序||点评数量||点评综合分降序 */
	private String sort = "";
	/** 产品类型 */
	private String stage = "";

	/**
	 * 统一搜索接口(带经纬度算差距) ：省市(则转换为找省市下的景点和酒店)+景点+酒店 . 接受 中文/简拼/全拼/城市编号/酒店星级/酒店价格范围
	 * 
	 * @param keyword
	 *            =&[cityId=||cityName=]&fromPage=isClient&sort=[juli||seq||
	 *            zidian||salse一周销售额
	 *            ]&priceRange=min,max&star=&stage=2||3&productType=[hasTicket||
	 *            noTicket||hasHotel||noHotel]&x=&y=
	 * @author huangzhi
	 * @throws IOException
	 * @return JASON格式
	 */
	@Action("clientSearch")
	public void placeSearch() throws IOException {
		loger.info("SECH!;" + LoggerParms.getNowTime() + ";" + LoggerParms.getSessionId(getSession()) + ";" + LoggerParms.getIpAddr(getRequest()) + ";" + "手机端" + ";" + "统一搜索" + ";" + stage + ";"
				+ keyword + cityName + ";" + cityName + ";" + cityId + ";" + x + ";" + page + ";" + y + ";" + star + ";" + pageSize + ";" + priceRange + ";" + sort + ";" + productType + ";" + "TRUE"
				+ ";" + "SEND!");
		try {
			if (StringUtils.isNotEmpty(keyword) || StringUtils.isNotEmpty(cityId) || StringUtils.isNotEmpty(cityName) || StringUtils.isNotEmpty(subject)) {
				Map<String, String> params = initParamMap();
				loger.debug(QueryUtilForClient.getClientPlaceQuery(params));
				@SuppressWarnings("unchecked")
				List<PlaceBean> matchList =newPlaceSearchService.search(QueryUtilForClient.getClientPlaceQuery(params));
				String json = "";
				if (!matchList.isEmpty()) {
					getDetaXY(matchList);
					placeListSort(matchList);
					for (int i = 0; i < matchList.size(); i++) {// 排序后恢复空价格
						if (matchList.get(i).getSellPrice().equals("888888888") || matchList.get(i).getSellPrice().equals("-888888888")) {
							matchList.get(i).setSellPrice("");
						}
					}
					debugPrint(matchList);
				}
				json = convertSearchResultToJsonString(matchList, matchList.size());
				String callback = this.getRequest().getParameter("callback");
				this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
				if (callback == null) {
					this.getResponse().getWriter().println(json); // 返回CONTENT.
				} else {
					this.getResponse().getWriter().println(callback + "(" + json + ")"); // 返回CONTENT.
				}
			}

		} catch (Exception e) {
			System.out.println("手机端景点酒店统一搜索出错！");
			e.printStackTrace();
		}
	}

	/**
	 * 计算到中心位置XY的距离 , 单位为米,M
	 * 
	 * @author huangzhi
	 **/
	private void getDetaXY(List<PlaceBean> matchList) {
		if (StringUtils.isNotEmpty(x) && StringUtils.isNotEmpty(y)) {
			if (x.matches("[\\d]*[\\.]*[\\d]*") && y.matches("[\\d]*[\\.]*[\\d]*")) {// 正则法则判读是否为浮点数
				for (int i = 0; i < matchList.size(); i++) {
					Double detaY = Math.abs(matchList.get(i).getLatitude() - Double.parseDouble(y)) * 100000;
					Double detaX = Math.abs(matchList.get(i).getLongitude() - Double.parseDouble(x)) * 100000;
					int detaXY = (int) Math.sqrt(detaX * detaX + detaY * detaY);
					matchList.get(i).setBoost(detaXY);// Boost字段借用设距离数值。
				}
			}
		}
	}

	/**
	 * 排序:邻近距离由近到远||seq||伪字典顺序
	 * 
	 * @author huangzhi
	 **/
	private void placeListSort(List<PlaceBean> matchList) {
		if (StringUtils.isNotEmpty(sort)) {
			if ("juli".equals(sort.toLowerCase())) {
				if (StringUtils.isNotEmpty(x) && StringUtils.isNotEmpty(y)) {
					Collections.sort(matchList, new PlaceBean.comparatorBoost());
				}
			} else if ("pricedesc".equals(sort.toLowerCase())) {// 价格降序
				// 按定义sellprice为空时设置一极大值88888888,按价格降序时把这个值设为负值做排序-888888888,价格为空排最后。
				for (int i = 0; i < matchList.size(); i++) {
					if (matchList.get(i).getSellPrice().equals("888888888")) {
						matchList.get(i).setSellPrice("-888888888");
					}
				}
				Collections.sort(matchList, new PlaceBean.comparatorSellPriceDesc());
			} else if ("priceasc".equals(sort.toLowerCase())) {// 价格升序
				// 按定义sellprice为空时设置一极大值88888888,按价格降序时这个值不变，价格为空排最后
				Collections.sort(matchList, new PlaceBean.comparatorSellPriceAsc());
			} else if ("zidian".equals(sort.toLowerCase()) && StringUtils.isNotEmpty(keyword.trim())) {// 仅仅当keyword不为空是按字典排序
				if (keyword.matches("[a-z,A-Z]+")) {
					/**
					 * 按拼音SEQ的规则设置排序SEQ:
					 * 拼音匹配位置_拼音长度_SEQ,考虑到SEQ为[-10000,20000]之间, 而且是负值排在前面,所以
					 * 用一个参数100000去加SEQ 的值作为排序数[110000 , 80000
					 * ]，则越大的越排前,为保持字符串比较，则开平方保证值为3位数
					 ***/
					for (int i = 0; i < matchList.size(); i++) {
						matchList.get(i).setSeq(
								matchList.get(i).getPinYin().indexOf(keyword) + "_" + Math.sqrt(matchList.get(i).getPinYin().length()) + "_"
										+ Math.sqrt((100000 + Integer.parseInt(matchList.get(i).getSeq()))));
					}
				} else if (keyword.matches("[^\n]*[\u4e00-\u9fa5]+[^\n]*")) {
					/**
					 * 按中文SEQ的规则设置排序SEQ:
					 * 中文匹配位置_中文长度_SEQ,考虑到SEQ为[-10000,20000]之间, 而且是负值排在前面,所以
					 * 用一个参数100000去加SEQ 的值作为排序数[110000 , 80000
					 * ]，则越大的越排前,为保持字符串比较，则开平方保证值为3位数
					 ***/
					for (int i = 0; i < matchList.size(); i++) {
						matchList.get(i).setSeq(
								matchList.get(i).getName().indexOf(keyword) + "_" + Math.sqrt(matchList.get(i).getName().length()) + "_"
										+ Math.sqrt((100000 + Integer.parseInt(matchList.get(i).getSeq()))));
					}

				}
				Collections.sort(matchList, new PlaceBean.comparatorChinaTree());

			} else if ("seq".equals(sort.toLowerCase())) {
				Collections.sort(matchList, new PlaceBean.comparatorChinaTree());
			}else if ("cmtavg".equals(sort.toLowerCase())) {//点评数降序
				Collections.sort(matchList, new PlaceBean.comparatorCmtAvg());		
			}else if ("cmtnum".equals(sort.toLowerCase())) {//点评分数降序
				Collections.sort(matchList, new PlaceBean.comparatorCmtNum());
			}else if ("salse".equals(sort.toLowerCase())) {//一周销售额排序
				Collections.sort(matchList, new PlaceBean.comparatorSales());
			}
			
		}
	}

	private void debugPrint(List<PlaceBean> matchList) {
		for (int i = 0; i < matchList.size(); i++) {

			loger.debug(i + "搜索结果：" + matchList.get(i).getName() + ":" + matchList.get(i).getPinYin() + ": " + matchList.get(i).getStage() + ": " + matchList.get(i).getShortId() + ": "
					+ matchList.get(i).getPlaceType() + "SEQ=" + matchList.get(i).getSeq() + ": 价格 = " + matchList.get(i).getSellPrice() + "               " + matchList.get(i).getBoost());
		}
	}

	/**
	 * 根据经纬度度邻近搜索 :景点+酒店 ,分如下三种情况: A:
	 * cityId=城市ID,x=经度,y=纬度,windage=半径[米],productType=ALL|hasHotel|hasTicket B:
	 * cityId=景点ID|酒店ID,x=null,y=null,windage=半径[米],productType=ALL|hasHotel|
	 * hasTicket C:
	 * cityId=null,x=经度,y=纬度,windage=半径[米],productType=ALL|hasHotel|hasTicket
	 * http
	 * ://www.lvmama.com/search/clientSearch!nearSearch.do?cityId=城市ID&x=经度&y
	 * =纬度&windage=半径【米】
	 * &productType=ALL|hasHotel|hasTicket&fromPage=isClient&stage
	 * =2||3||空&sort=[juli||seq||zidian||priceAsc||priceDesc||空（SEQ）]
	 * &priceRange=min,max&star=&hotelType=[D度假酒店||P精品酒店||G高档酒店||K客栈||J经济酒店]
	 * &productType=[hasTicket||ALLTicket||hasHotel||ALLHotel]
	 * 
	 * @author huangzhi
	 * @throws IOException
	 * @return JASON格式
	 */
	@Action("clientSearch")
	public void nearSearch() throws IOException {
		loger.info("SECH!;" + LoggerParms.getNowTime() + ";" + LoggerParms.getSessionId(getSession()) + ";" + LoggerParms.getIpAddr(getRequest()) + ";手机端;;;" + x + ";" + y + ";" + cityId + ";;"
				+ page + ";;;" + windage + ";;;;经纬度邻近搜索;SEND!");
		try {
			// 对于cityId=景点ID|酒店ID,x=null,y=null的情况B,必须先把景点/酒店ID转换为坐标XY
			//并清空cityId字段
			if (StringUtils.isNotEmpty(cityId) & StringUtils.isEmpty(x) & StringUtils.isEmpty(y)) {
				List<PlaceBean> placeList = newPlaceSearchService.search(10, QueryUtilForClient.getCityIdQuery(cityId));
				x = String.valueOf(placeList.get(0).getLongitude());
				y = String.valueOf(placeList.get(0).getLatitude());
				if (StringUtils.isNotEmpty(x) & StringUtils.isNotEmpty(y)) {
					cityId = "";// cityId置空,否则影响query
					cityIdFirst=true;
				}
			}
			Map<String, String> params = initParamMap();
			List<PlaceBean> matchList = newPlaceSearchService.search(QueryUtilForClient.getClientNearSearchQuery(params));
			if (!matchList.isEmpty()) {
				getDetaXY(matchList);
				placeListSort(matchList);
				for (int i = 0; i < matchList.size(); i++) {// 排序后恢复空价格
					if (matchList.get(i).getSellPrice().equals("888888888") || matchList.get(i).getSellPrice().equals("-888888888")) {
						matchList.get(i).setSellPrice("");
					}
				}
				debugPrint(matchList);
			}
			String json = "";
			json = convertNearSearchResultToJsonString(matchList, matchList.size());
			String callback = this.getRequest().getParameter("callback");
			this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
			if (callback == null) {
				this.getResponse().getWriter().println(json); // 返回CONTENT.
			} else {
				this.getResponse().getWriter().println(callback + "(" + json + ")"); // 返回CONTENT.
			}
		} catch (Exception e) {
			System.out.println("手机端邻近搜索出错！");
			e.printStackTrace();
		}
	}

	/**
	 * 查询下拉提示补全 ：城市+景点+酒店 , 接受 中文/简拼/全拼
	 * 
	 * @author huangzhi
	 * @throws IOException
	 * @return JASON格式
	 */
	@Action("clientSearch")
	public void getAutoCompletePlace() throws IOException {
		loger.info("SECH!;" + LoggerParms.getNowTime() + ";" + LoggerParms.getSessionId(getSession()) + ";" + LoggerParms.getIpAddr(getRequest()) + ";" + "手机端" + ";" + fromPage + ";" + "" + ";"
				+ keyword + ";" + "" + ";" + "" + ";" + "" + ";" + page + ";" + "" + ";" + "" + ";" + "" + ";" + "" + ";" + sort + ";" + "" + ";" + "自动补全" + ";" + "SEND!");
		try {
			List<AutoCompletePlaceObject> matchList ;
			if (StringUtils.isNotEmpty(keyword)) {
				if (StringUtils.isNotEmpty(stage)) {
					matchList = autoCompleteService.getAutoCompletePlacePlaceObjectListMatched(SearchConstants.CLIENT_CHANNEL_TYPE, Long.parseLong(stage), keyword, 100);
					
				} else {
					matchList = autoCompleteService.getAutoCompletePlacePlaceObjectListMatched(SearchConstants.CLIENT_CHANNEL_TYPE, null, keyword, 100);
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

			}
		} catch (Exception e) {
			System.out.println("手机端提示补全查询出错！");
			e.printStackTrace();
		}
	}

	/**
	 * 获得制定城市的景点主题/标红主题
	 * 
	 * @author huangzhi
	 * @throws IOException
	 * @return JASON格式
	 */
	@Action("clientSearch")
	public void getClientSubjectByCity() throws IOException {
		loger.info("SECH!;" + LoggerParms.getNowTime() + ";" + LoggerParms.getSessionId(getSession()) + ";" + LoggerParms.getIpAddr(getRequest()) + ";" + "手机端" + ";" + fromPage + ";" + "" + ";"
				+ keyword + ";" + "" + ";" + "" + ";" + "" + ";" + page + ";" + "" + ";" + "" + ";" + "" + ";" + "" + ";" + sort + ";" + "" + ";" + "自动补全" + ";" + "SEND!");
		try {
			List<AutoCompletePlaceObject> matchList;
			if (StringUtils.isNotEmpty(keyword)) {
				if (StringUtils.isNotEmpty(cityId)) {
					matchList = autoCompleteService.getAutoCompletePlacePlaceObjectListMatched(SearchConstants.CLIENT_CHANNEL_SUBJECT, Long.parseLong(cityId), keyword, 1000);
				} else {
					matchList = autoCompleteService.getAutoCompletePlacePlaceObjectListMatched(SearchConstants.CLIENT_CHANNEL_SUBJECT, 9999L, keyword, 1000);
				}
			} else {
				if (StringUtils.isNotEmpty(cityId)) {
					matchList = autoCompleteService.getAutoCompletePlacePlaceObjectListDefault(SearchConstants.CLIENT_CHANNEL_SUBJECT, Long.parseLong(cityId), 1000);
				} else {
					matchList = autoCompleteService.getAutoCompletePlacePlaceObjectListDefault(SearchConstants.CLIENT_CHANNEL_SUBJECT, 9999L, 1000);
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
	 * 酒店所在城市下拉提示补全
	 * 
	 * @return jasonList
	 * @throws IOException
	 */
	@Action("clientSearch")
	public void getAutoCompleteHotelCity() throws IOException {
		keyword = SearchStringUtil.treatKeyWord(keyword);
		if (StringUtils.isNotEmpty(keyword)) {
			// loger.info(keyword);
			loger.info("SECH!;" + LoggerParms.getNowTime() + ";" + LoggerParms.getSessionId(getSession()) + ";" + LoggerParms.getIpAddr(getRequest()) + ";" + "手机端" + ";" + fromPage + ";" + "" + ";"
					+ keyword + ";" + "" + ";" + "" + ";" + "" + ";" + page + ";" + "" + ";" + "" + ";" + "" + ";" + "" + ";" + sort + ";" + "" + ";" + "自动补全" + ";" + "SEND!");
			try {
				List<AutoCompletePlaceObject> matchList = autoCompleteService.getAutoCompletePlacePlaceObjectListMatched(SearchConstants.HOTEL_MERGE_COMPLETE, null, keyword, 50);
				String json = "";
				if (!matchList.isEmpty()) {
					json = convertAutoCompletePlaceToJsonString(matchList);
				} else {
					json = "{}";
				}
				String callback = this.getRequest().getParameter("callback");
				this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
				if (callback == null) {
					this.getResponse().getWriter().println(json); // 返回CONTENT.
				} else {
					this.getResponse().getWriter().println(callback + "(" + json + ")"); // 返回CONTENT.
				}
			} catch (Exception e) {
				System.out.println("*******酒店城市自动补全出错！**************");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 酒店周边景点下拉提示补全
	 * 
	 * @return jasonList
	 * @throws IOException
	 */
	@Action("clientSearch")
	public void getAutoCompleteHotelSpot() throws IOException {
		keyword = SearchStringUtil.treatKeyWord(keyword);
		// loger.info(keyword);
		loger.info("SECH!;" + LoggerParms.getNowTime() + ";" + LoggerParms.getSessionId(getSession()) + ";" + LoggerParms.getIpAddr(getRequest()) + ";" + "手机端" + ";" + fromPage + ";" + "" + ";"
				+ keyword + ";" + "" + ";" + "" + ";" + "" + ";" + page + ";" + "" + ";" + "" + ";" + "" + ";" + "" + ";" + sort + ";" + "" + ";" + "自动补全" + ";" + "SEND!");
		try {
			List<AutoCompletePlaceObject> matchList;
			if (StringUtils.isNotEmpty(keyword)) {
				matchList = autoCompleteService.getAutoCompletePlacePlaceObjectListMatched(SearchConstants.HOTEL_MERGE_COMPLETE, Long.parseLong(cityId.trim()), keyword, pageSize);
			} else {// 关键字为空的补全
				matchList = autoCompleteService.getAutoCompletePlacePlaceObjectListDefault(SearchConstants.HOTEL_MERGE_COMPLETE, Long.parseLong(cityId.trim()), pageSize);
			}
			String json = "";
			if (!matchList.isEmpty()) {
				//sortHoteSpot(matchList);
				json = convertAutoCompletePlaceToJsonString(matchList);
			} else {
				json = "{}";
			}
			String callback = this.getRequest().getParameter("callback");
			this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
			if (callback == null) {
				this.getResponse().getWriter().println(json); // 返回CONTENT.
			} else {
				this.getResponse().getWriter().println(callback + "(" + json + ")"); // 返回CONTENT.
			}
		} catch (Exception e) {
			System.out.println("*******酒店周边景点自动补全出错！**************cityId:"+cityId);
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
		}

	}

	/** 酒店的周边景点要按照拼音排序 **/
	private void sortHoteSpot(List<AutoCompletePlaceObject> matchList) {
		for (int i = 0; i < matchList.size(); i++) {
			/** 为什么在words后加"~"匹配,是因为解决 '徐家汇天主教堂 '和 '徐家汇'类型的冲突问题,前缀仍然要考虑 **/
			int start = matchList.get(i).getPinyin().indexOf(matchList.get(i).getWords() + "~") + matchList.get(i).getWords().length() + 1;
			// System.out.println(start+"----"+matchList.get(i).getWords());
			// System.out.println("++++"+ matchList.get(i).getPinyin());
			String pinyinAll = matchList.get(i).getPinyin().substring(start);
			// System.out.println(pinyinAll);
			int end = pinyinAll.indexOf("~");
			pinyinAll = pinyinAll.substring(0, end);
			// System.out.println("="+pinyinAll);
			matchList.get(i).setPinyin(pinyinAll);
		}
		Collections.sort(matchList, new AutoCompletePlaceObject.comparatorPinyin());
	}

	/**
	 * 构造jasonTree中国树 ,得到地标下有 景点|酒店|自由行线路|所有三种| 的城市列表
	 * 
	 * @author huangzhi
	 * @throws IOException
	 * @return JASON格式
	 */
	@Action("clientSearch")
	public void getCityListByHasProduct() throws IOException {
		loger.info("SECH!;" + LoggerParms.getNowTime() + ";" + LoggerParms.getSessionId(getSession()) + ";" + LoggerParms.getIpAddr(getRequest()) + ";" + "手机端" + ";" + fromPage + ";" + "" + ";"
				+ keyword + ";" + "" + ";" + "" + ";" + "" + ";" + page + ";" + "" + ";" + "" + ";" + "" + ";" + "" + ";" + sort + ";" + "" + ";" + "中国树列表" + ";" + "SEND!");
		try {
			if (StringUtils.isNotEmpty(productType)) {
				Map<String, String> params = initParamMap();
				PageConfig<PlaceBean> placePageConfig = newPlaceSearchService.search(1000, page, QueryUtilForClient.getChinaTreeByHasProductQuery(params));
				List<PlaceBean> matchList = placePageConfig.getItems();
				List<PlaceBean> cityList = new ArrayList<PlaceBean>();
				String json = "";
				if (!matchList.isEmpty()) {					
					for (int i = 0; i < matchList.size(); i++) {
						String cityType = matchList.get(i).getPlaceType();
						if ("ZXS".equals(cityType) || "TBXZQ".equals(cityType) || "CITY".equals(cityType) || "FOREIGN".equals(cityType) || "OTHER".equals(cityType)) {
							if (StringUtils.isEmpty(matchList.get(i).getPinYin())) {
								matchList.get(i).setPinYin("Z");
							}
							cityList.add(matchList.get(i));
						}
					}
					
				}
				if (!cityList.isEmpty()) {
					Collections.sort(cityList,new PlaceBean.comparatorPinyin());
					json=convertSearchResultToJsonString(cityList , cityList.size());
				}
				
				String callback = this.getRequest().getParameter("callback");
				this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
				if (callback == null) {
					this.getResponse().getWriter().println(json); // 返回CONTENT.
				} else {
					this.getResponse().getWriter().println(callback + "(" + json + ")"); // 返回CONTENT.
				}
			}
		} catch (Exception e) {
			System.out.println("手机端提示补全查询出错！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 构造jasonTree中国树 ,得到地标下有 景点|酒店|自由行线路|所有三种| 的省市树
	 * 
	 * @author huangzhi
	 * @throws IOException
	 * @return JASON格式
	 */
	@Action("clientSearch")
	public void getChinaTreeByHasProduct() throws IOException {
		loger.info("SECH!;" + LoggerParms.getNowTime() + ";" + LoggerParms.getSessionId(getSession()) + ";" + LoggerParms.getIpAddr(getRequest()) + ";" + "手机端" + ";" + fromPage + ";" + "" + ";"
				+ keyword + ";" + "" + ";" + "" + ";" + "" + ";" + page + ";" + "" + ";" + "" + ";" + "" + ";" + "" + ";" + sort + ";" + "" + ";" + "中国树列表" + ";" + "SEND!");
		try {
			if (StringUtils.isNotEmpty(productType)) {
				Map<String, String> params = initParamMap();
				PageConfig<PlaceBean> placePageConfig = newPlaceSearchService.search(1000, page, QueryUtilForClient.getChinaTreeByHasProductQuery(params));
				List<PlaceBean> matchList = placePageConfig.getItems();
				// 构造中国树
				TreeBean<PlaceBean> chinaTree = new TreeBean<PlaceBean>();
				getChinaTree(matchList, chinaTree);
				debugPrintChinaTree(matchList, chinaTree);
				String json = "";
				if (!matchList.isEmpty()) {
					json = convertChinaTreeToJsonString(chinaTree);
				}
				String callback = this.getRequest().getParameter("callback");
				this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
				if (callback == null) {
					this.getResponse().getWriter().println(json); // 返回CONTENT.
				} else {
					this.getResponse().getWriter().println(callback + "(" + json + ")"); // 返回CONTENT.
				}
			}
		} catch (Exception e) {
			System.out.println("手机端提示补全查询出错！");
			e.printStackTrace();
		}
	}

	/**
	 * 构造中国树
	 **/
	private void getChinaTree(List<PlaceBean> matchList, TreeBean<PlaceBean> chinaTree) {
		PlaceBean rootBean = new PlaceBean();
		rootBean.setName("中国");
		rootBean.setStage("0");
		chinaTree.setNode(rootBean);
		List<TreeBean> provinceList = new ArrayList<TreeBean>();
		// 遍历列表得到省|直辖市|特别行政区的节点
		for (int i = 0; i < matchList.size(); i++) {
			if (matchList.get(i).getPlaceType().equals("PROVINCE") || matchList.get(i).getPlaceType().equals("ZXS") || matchList.get(i).getPlaceType().equals("TBXZQ")) {
				TreeBean<PlaceBean> provinceTree = new TreeBean<PlaceBean>();
				provinceTree.setNode(matchList.get(i));
				PlaceBean placebean = matchList.get(i);
				if (!StringUtil.isEmptyString(matchList.get(i).getPinYin())) {
					provinceTree.setSort(matchList.get(i).getPinYin());
				} else {// 没有拼音则修正为Z排到最后
					provinceTree.setSort("z");
				}
				if (matchList.get(i).getPlaceType().equals("ZXS") || matchList.get(i).getPlaceType().equals("TBXZQ")) {
					TreeBean<PlaceBean> cityTree = new TreeBean<PlaceBean>();
					cityTree.setNode(matchList.get(i));
					if (!StringUtil.isEmptyString(matchList.get(i).getSeq())) {
						cityTree.setSort(matchList.get(i).getSeq());
					} else {
						cityTree.setSort("0");
					}
					List<TreeBean> cityList = new ArrayList<TreeBean>();
					cityList.add(cityTree);
					provinceTree.setSubNode(cityList);
				}
				provinceList.add(provinceTree);
			}
		}
		Collections.sort(provinceList, new TreeBean.comparatorChinaTree());
		// 遍历列表得到市的节点
		for (int i = 0; i < matchList.size(); i++) {
			if (matchList.get(i).getPlaceType().equals("CITY")) {
				PlaceBean placeBean = matchList.get(i);
				boolean addCity = addCityTree(provinceList, placeBean);
				if (!addCity) {// 对于象山这样的地标，升级为CITY，但是上级目的地也为CITY宁波，所以需要重新找宁波的上级目的地
					String destId = placeBean.getDestId();
					for (int j = 0; j < matchList.size(); j++) {
						if (matchList.get(j).getShortId().equals(destId)) {
							placeBean.setDestId(matchList.get(j).getDestId());
							break;
						}
					}
					addCityTree(provinceList, placeBean);
				}
			}
		}
		for (int i = 0; i < provinceList.size(); i++) {
			if (provinceList.get(i).getSubNode() != null) {
				Collections.sort(provinceList.get(i).getSubNode(), new TreeBean.comparatorChinaTree());
			}
		}
		chinaTree.setSubNode(provinceList);
	}

	/**
	 * 构造中国树时加入城市节点
	 **/
	private boolean addCityTree(List<TreeBean> provinceList, PlaceBean placeBean) {
		boolean addCity = false;
		for (int j = 0; j < provinceList.size(); j++) {
			PlaceBean provincebean = (PlaceBean) provinceList.get(j).getNode();
			if (provincebean.getShortId().equals(placeBean.getDestId())) {
				TreeBean<PlaceBean> cityTree = new TreeBean<PlaceBean>();
				cityTree.setNode(placeBean);
				if (!StringUtil.isEmptyString(placeBean.getSeq())) {
					cityTree.setSort(placeBean.getSeq());
				} else {
					cityTree.setSort("0");
				}
				if (provinceList.get(j).getSubNode() == null) {
					List<TreeBean> cityList = new ArrayList<TreeBean>();
					cityList.add(cityTree);
					provinceList.get(j).setSubNode(cityList);
					addCity = true;
				} else {
					provinceList.get(j).getSubNode().add(cityTree);
					addCity = true;
				}
			}
		}
		return addCity;
	}

	/**
	 * 打印中国树,调试用
	 **/
	private void debugPrintChinaTree(List<PlaceBean> matchList, TreeBean<PlaceBean> chinaTree) {
		Collections.sort(matchList, new PlaceBean.comparatorChinaTree());
		loger.debug(" ++中国 +++++++++++++++++++++++++++");
		int sum = 1;
		for (int i = 0; i < chinaTree.getSubNode().size(); i++) {
			PlaceBean provinceBean = (PlaceBean) chinaTree.getSubNode().get(i).getNode();
			loger.debug((sum++) + "    " + provinceBean.getName() + "  " + provinceBean.getDestId() + "  " + provinceBean.getPlaceType() + "  " + provinceBean.getShortId());
			List<TreeBean> cityList = chinaTree.getSubNode().get(i).getSubNode();
			if (cityList != null) {
				for (int j = 0; j < cityList.size(); j++) {
					PlaceBean cityBean = (PlaceBean) cityList.get(j).getNode();
					loger.debug((sum++) + "        " + cityBean.getName() + "  " + cityBean.getDestId() + "  " + cityBean.getPlaceType() + "  " + cityBean.getShortId());
				}
			}
		}
		loger.debug("=====省市总数：" + matchList.size());
		for (int i = 0; i < matchList.size(); i++) {
			loger.debug("中国树：" + matchList.get(i).getPlaceType() + ";" + matchList.get(i).getDestId() + ";" + matchList.get(i).getName() + ": " + matchList.get(i).getShortId() + ": ");
		}
	}

	/**
	 * ChinaTree封装成JSON格式
	 * 
	 * @param matchList
	 * @return
	 */
	private String convertChinaTreeToJsonString(TreeBean<PlaceBean> chinaTree) {
		List<TreeBean> provinceList = chinaTree.getSubNode();
		List<TreeBean> provinceListJson = new ArrayList<TreeBean>();
		for (int i = 0; i < provinceList.size(); i++) {
			PlaceBean provinceBean = (PlaceBean) provinceList.get(i).getNode();
			JasonPlaceBean provinceBeanJson = new JasonPlaceBean();
			provinceBeanJson.setName(provinceBean.getName());
			provinceBeanJson.setId(provinceBean.getShortId());
			provinceBeanJson.setPinyin(provinceBean.getPinYin());
			TreeBean<JasonPlaceBean> provinceJson = new TreeBean<JasonPlaceBean>();
			List<TreeBean> cityList = provinceList.get(i).getSubNode();
			List<TreeBean> cityListJson = new ArrayList<TreeBean>();
			if (cityList != null && cityList.size() > 0) {
				for (TreeBean cityTreeBean : cityList) {
					PlaceBean cityBean = (PlaceBean) cityTreeBean.getNode();
					JasonPlaceBean cityBeanJson = new JasonPlaceBean();
					cityBeanJson.setName(cityBean.getName());
					cityBeanJson.setId(cityBean.getShortId());
					cityBeanJson.setPinyin(cityBean.getPinYin());

					TreeBean<JasonPlaceBean> cityJson = new TreeBean<JasonPlaceBean>();
					cityJson.setNode(cityBeanJson);
					cityListJson.add(cityJson);
				}
			}
			provinceJson.setNode(provinceBeanJson);
			provinceJson.setSubNode(cityListJson);
			provinceListJson.add(provinceJson);
		}
		JSONArray jarray = JSONArray.fromObject(provinceListJson);
		return jarray.toString();
	}

	/**
	 * 封装成JSON格式
	 * 
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
				if("100441".equals(autoCompletePlaceObject.getShortId())){
					continue;
				}
				json.append("{");
				json.append("\"pinyin\":\"" + autoCompletePlaceObject.getPinyin() + "\",");
				json.append("\"name\":\"" + autoCompletePlaceObject.getWords().trim() + "\",");
				json.append("\"stage\":\"" + autoCompletePlaceObject.getStage() + "\",");
				json.append("\"id\":\"" + autoCompletePlaceObject.getShortId() + "\"");
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
	private String convertSearchResultToJsonString(List<PlaceBean> matchList, int totalResultSize) throws IOException {
		StringBuffer json = new StringBuffer("{");
		// 如果keyword不为空而且是省市则返回省市id
		String cityIdbyKeyword = "-1";
		if (StringUtils.isNotEmpty(cityId)) {
			cityIdbyKeyword = cityId;
		} else if (StringUtils.isNotEmpty(keyword)) {
			cityIdbyKeyword = findCityidByKeyword(keyword);
		}
		json.append("\"cityId\":" + cityIdbyKeyword + ",");
		json.append("\"page\":" + page + ",");
		json.append("\"totalResultSize\":" + totalResultSize);
		json.append(",\"placeListJson\":");
		json.append("[");
		for (int i = (page - 1) * pageSize; (i < matchList.size()) && (i < page * pageSize); i++) {// 翻页参数控制翻页
																									// pageSize:page
			PlaceBean placeBean = matchList.get(i);
			if(placeBean.getId().equals("100441")){
				matchList.remove(i);
				continue;
			}
			json.append("{");
			json.append("\"id\":\"" + placeBean.getShortId() + "\",");
			json.append("\"pinyin\":\"" + placeBean.getPinYin() + "\",");
			json.append("\"longitude\":\"" + placeBean.getLongitude() + "\",");
			json.append("\"latitude\":\"" + placeBean.getLatitude() + "\",");
			json.append("\"name\":\"" + placeBean.getName() + "\",");
			json.append("\"stage\":\"" + placeBean.getStage() + "\",");
			if (StringUtils.isNotEmpty(x) && StringUtils.isNotEmpty(y)) {
				// 如果按中心位置XY的距离排序,则把距离传到客户端，否则传空,Boost字段借用设距离数值。
				json.append("\"juli\":\"" + placeBean.getBoost() + "\",");
			} else {
				json.append("\"juli\":\"" + "" + "\",");
			}
			json.append("\"avgScore\":\"" + placeBean.getAvgScore() + "\",");
			json.append("\"cmtNum\":\"" + placeBean.getCmtNum() + "\",");
			json.append("\"middleImage\":\"" + placeBean.getMiddleImage() + "\",");
			json.append("\"marketPrice\":\"" + placeBean.getMarketPrice() * 100 + "\",");// 分为单位
			if (StringUtils.isNotEmpty(placeBean.getSellPrice())) {
				json.append("\"sellPrice\":\"" + Long.parseLong(placeBean.getSellPrice()) + "\",");// 分为单位
			} else {// 价格为空的情况
				json.append("\"sellPrice\":\"" + "" + "\",");// 分为单位
			}
			json.append("\"placeMainTitel\":\"" + placeBean.getPlaceMainTitel() + "\",");
//			json.append("\"hotelType\":\"" + convertHotelType(placeBean.getHotelSearchContent()) + "\",");// 酒店类型
			json.append("\"todayOrderAble\":\"" + placeBean.canOrderTodayCurrentTimeForPlace() + "\",");
			json.append("\"placeTitel\":\"" + placeBean.getPlaceTitel() + "\"");
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

	/**
	 * 封装成JSON格式,专门为邻近搜索用，去掉搜索原点的值 .
	 * 
	 * @param matchList
	 * @return
	 * @throws IOException
	 */
	private String convertNearSearchResultToJsonString(List<PlaceBean> matchList, int totalResultSize) throws IOException {
		StringBuffer json = new StringBuffer("{");
		// 如果keyword不为空而且是省市则返回省市id
		String cityIdbyKeyword = "-1";
		if (StringUtils.isNotEmpty(cityId)) {
			cityIdbyKeyword = cityId;
		} else if (StringUtils.isNotEmpty(cityName)) {
			cityIdbyKeyword = findCityidByKeyword(cityName);
		}
		json.append("\"cityId\":" + cityIdbyKeyword + ",");
		json.append("\"page\":" + page + ",");
		if (cityIdFirst&&totalResultSize>1) {//如果是以一个景点为中心做邻近搜索,本景点不出现在列表中,统计数减去1
			json.append("\"totalResultSize\":" + (totalResultSize-1));
		}else {
			json.append("\"totalResultSize\":" + totalResultSize);
		}
		json.append(",\"placeListJson\":");
		json.append("[");
		for (int i = (page - 1) * pageSize; (i < matchList.size()) && (i < page * pageSize); i++) {// 翻页参数控制翻页
																									// :pageSize:page
			PlaceBean placeBean = matchList.get(i);
			if (placeBean.getBoost() != 0) {
				json.append("{");
				json.append("\"id\":\"" + placeBean.getShortId() + "\",");
				json.append("\"pinyin\":\"" + placeBean.getPinYin() + "\",");
				json.append("\"name\":\"" + placeBean.getName() + "\",");
				json.append("\"stage\":\"" + placeBean.getStage() + "\",");
				if (StringUtils.isNotEmpty(x) && StringUtils.isNotEmpty(y)) {
					// 如果按中心位置XY的距离排序,则把距离传到客户端，否则传空,Boost字段借用设距离数值。
					json.append("\"juli\":\"" + placeBean.getBoost() + "\",");
				} else {
					json.append("\"juli\":\"" + "" + "\",");
				}
				json.append("\"longitude\":\"" + placeBean.getLongitude() + "\",");
				json.append("\"latitude\":\"" + placeBean.getLatitude() + "\",");
				json.append("\"avgScore\":\"" + placeBean.getAvgScore() + "\",");
				json.append("\"cmtNum\":\"" + placeBean.getCmtNum() + "\",");
				json.append("\"middleImage\":\"" + placeBean.getMiddleImage() + "\",");
				json.append("\"marketPrice\":\"" + placeBean.getMarketPrice() * 100 + "\",");// 分为单位
				if (StringUtils.isNotEmpty(placeBean.getSellPrice())) {
					json.append("\"sellPrice\":\"" + Long.parseLong(placeBean.getSellPrice()) + "\",");// 分为单位
				} else {// 价格为空的情况
					json.append("\"sellPrice\":\"" + "" + "\",");// 分为单位
				}
				json.append("\"placeMainTitel\":\"" + placeBean.getPlaceMainTitel() + "\",");
				json.append("\"todayOrderAble\":\"" + placeBean.canOrderTodayCurrentTimeForPlace() + "\",");

//				json.append("\"hotelType\":\"" + convertHotelType(placeBean.getHotelSearchContent()) + "\",");// 酒店类型
				json.append("\"placeTitel\":\"" + placeBean.getPlaceTitel() + "\"");
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

	// 如果输入的是省市关键字，则返回省市ID号
	private String findCityidByKeyword(String keyword) throws IOException {
		String cityId = "-1";
		if (StringUtils.isNotEmpty(SearchStringUtil.treatKeyWord(keyword))) {
			List<PlaceBean> cityList = newPlaceSearchService.search(QueryUtilForClient.getCityQuery(keyword));
			if (cityList.size() > 0) {
				cityId = cityList.get(0).getShortId();
			}
		}
		return cityId;
	}

	// 根据酒店搜索内容字段得到酒店类型
	private String convertHotelType(String hotelSearchContent) {
		String hotelType = "";
		if (hotelSearchContent.indexOf("度假酒店") >= 0) {
			hotelType = "度假酒店";
		} else if (hotelSearchContent.indexOf("精品酒店") >= 0) {
			hotelType = "精品酒店";
		} else if (hotelSearchContent.indexOf("高档酒店") >= 0) {
			hotelType = "高档酒店";
		} else if (hotelSearchContent.indexOf("经济酒店") >= 0) {
			hotelType = "经济酒店";
		} else if (hotelSearchContent.indexOf("客栈") >= 0) {
			hotelType = "客栈.农家乐";
		}
		return hotelType;
	}

	/**
	 * 初始化搜索参数
	 * 
	 * @param keyword
	 *            =&[cityId=||cityName=]&fromPage=isClient&sort=[juli||seq||
	 *            zidian
	 *            ]&priceRange=min,max&star=&stage=2||3&productType=[hasTicket||
	 *            noTicket||hasHotel||noHotel]&x=&y=
	 * @return
	 */
	private Map<String, String> initParamMap() {
		Map<String, String> params = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(keyword)) {
			params.put("name", keyword.toLowerCase());
		}
		if (StringUtils.isNotEmpty(y)) {
			params.put("latitude", y);
		}
		if (StringUtils.isNotEmpty(x)) {
			params.put("longitude", x);
		}
		if (StringUtils.isNotEmpty(cityId)) {
			params.put("cityId", cityId);
		}
		if (StringUtils.isNotEmpty(cityName)) {
			params.put("cityName", cityName);
		}
		if (StringUtils.isNotEmpty(subject)) {
			params.put("subject", subject);
		}
		if (StringUtils.isNotEmpty(spot)) {
			params.put("spot", spot);
		}
		if (StringUtils.isNotEmpty(priceRange)) {
			params.put("priceRange", priceRange);
		}
		if (StringUtils.isNotEmpty(star)) {
			params.put("star", star);
		}
		if (StringUtils.isNotEmpty(stage)) {
			params.put("stage", stage);
		}
		if (StringUtils.isNotEmpty(productType)) {
			params.put("productType", productType.toLowerCase());
		}
		if (StringUtils.isNotEmpty(windage)) {
			params.put("windage", windage);
		}
		if (StringUtils.isNotEmpty(hotelType)) {
			hotelType = hotelType.toUpperCase();
			// 转换为相应中文字段
			Map<String, String> typeMap = new HashMap<String, String>();
			typeMap.put("D", "度假酒店");
			typeMap.put("P", "精品酒店");
			typeMap.put("G", "高档酒店");
			typeMap.put("K", "客栈");// “客栈/农家乐”省略斜杠后,前缀匹配
			typeMap.put("J", "经济酒店");
			StringBuffer typeHotel = new StringBuffer();
			for (int i = 0; i < hotelType.length(); i++) {
				typeHotel.append(typeMap.get(String.valueOf(hotelType.charAt(i)))).append(",");
			}
			params.put("hotelType", typeHotel.toString());

		}

		return params;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getFromPage() {
		return fromPage;
	}

	public String getWindage() {
		return windage;
	}

	public void setWindage(String windage) {
		this.windage = windage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	public Log getLoger() {
		return loger;
	}

	public void setLoger(Log loger) {
		this.loger = loger;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public PageConfig<Map<String, Object>> getPageConfig() {
		return pageConfig;
	}

	public void setPageConfig(PageConfig<Map<String, Object>> pageConfig) {
		this.pageConfig = pageConfig;
	}

	

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public boolean isKeywordValid() {
		return keywordValid;
	}

	public void setKeywordValid(boolean keywordValid) {
		this.keywordValid = keywordValid;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getLongitude() {
		return x;
	}

	public void setLongitude(String longitude) {
		this.x = longitude;
	}

	public String getLatitude() {
		return y;
	}

	public void setLatitude(String latitude) {
		this.y = latitude;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getCityName() {
		return cityName;
	}

	public String getSpot() {
		return spot;
	}

	public void setSpot(String spot) {
		this.spot = spot;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public AutoCompleteService getAutoCompleteService() {
		return autoCompleteService;
	}
}