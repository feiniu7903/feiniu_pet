package com.lvmama.search.action.web;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.vo.AutoCompletePlaceHotel;
import com.lvmama.comm.search.vo.AutoCompletePlaceObject;
import com.lvmama.comm.search.vo.AutoCompleteVerHotel;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.search.lucene.service.autocomplete.AutoCompleteOneService;
import com.lvmama.search.service.HotelSearchService;
import com.lvmama.search.service.KeywordAutoCompletService;
import com.lvmama.search.service.KeywordDirectSearchService;
import com.lvmama.search.synonyms.LikeHashMap;
import com.lvmama.search.synonyms.LocalSession;
import com.lvmama.search.util.CommonUtil;
import com.lvmama.search.util.LocalCacheManager;
import com.lvmama.search.util.LoggerParms;
import com.lvmama.search.util.PageConfig;
import com.lvmama.search.util.SearchStringUtil;

@Results({ 
	@Result(name = "TICKET", location = "http://www.lvmama.com/search/ticket/${params}.html", type ="redirect"),
	@Result(name = "HOTEL", location = "http://www.lvmama.com/search/hotel/${params}.html", type ="redirect"),
	@Result(name = "ROUTE", location = "http://www.lvmama.com/search/route/${params}.html", type ="redirect"),
	@Result(name = "FREETOUR", location = "http://www.lvmama.com/search/freetour/${params}.html", type ="redirect"),
	@Result(name = "FREELONG", location = "http://www.lvmama.com/search/freelong/${params}.html", type ="redirect"),
	@Result(name = "GROUP", location = "http://www.lvmama.com/search/group/${params}.html", type ="redirect"),
	@Result(name = "AROUND", location = "http://www.lvmama.com/search/around/${params}.html", type ="redirect")
})
public class OneSearchAction extends BaseAction{
	private static final long serialVersionUID = -2161508894972378430L;
	
	private Log loger = LogFactory.getLog(this.getClass());
	@Resource
	private KeywordAutoCompletService keywordAutoCompletService;
	@Resource
	private KeywordDirectSearchService keywordDirectSearchService;
	@Resource
	protected HotelSearchService hotelSearchService;
	
	@Resource
	protected AutoCompleteOneService autoCompleteOneService;
	/**
	 * 客户所选默认频道,这个选项会影响自动补全列表的排序 
	 * fromChannel=FREETOUR 自由行(门票+酒店)
	 * fromChannel=GROUP 周边跟团 改为新栏目的 ROUTE
	 * fromChannel=GROUP 长途 改为新栏目的* ROUTE
	 * fromChannel=GROUP 出境 改为新栏目的    ROUTE
	 * fromChannel=TICKET 门票 
	 * fromChannel=HOTEL * 酒店 
	 * fromChannel= 首页
	 * 新的栏目为："ROUTE","FREETOUR","FREELONG","GROUP","TICKET","HOTEL"
	 **/
	private String fromChannel = "MAIN";
	/**
	 * 当客户下拉选择选定频道或者智能运算后选择的新频道,
	 * newChannel=TICKET 新门票 
	 * newChannel=HOTEL 新酒店
	 * newChannel=ROUTE 新度假
	 * newChannel=GROUP 新跟团
	 * newChannel=FREETOUR 新(景点+酒店)
	 * newChannel=FREELONG 新(机票+酒店) 
	 * newChannel=AROUND 新周边
	 **/
	private String newChannel = "";		
	/**
	 * 不论何种入口，最后确认要跳转的栏目,默认为空
	 * **/
	private String selectChannel ="";
	
	/**
	 * 当客户选择的栏目newChannel 与 程序判断的栏目不一致的时候,做一个表示开关false,默认为true 
	 * **/
	private boolean selectChannellValid = true ;
	/**
	 * 是否是产品ID号搜索
	 * **/
	private boolean productIdSearch = false;	
	/**
	 * 当搜索各个栏目无结果时候，设置推荐页面标识
	 * **/
	private boolean isRecommendPageValid = false;
	
	/** 关键字 */
	private String keyword = "";
	
	private String params ;
	/**
	 * 六个栏目统计数MAP,如果栏目没有值,赋值0 TICKET("门票"), HOTEL("特色酒店"), ROUTE("度假线路"),
	 * GROUP("跟团游"), FREELONG("自由行(机票+酒店)"), FREETOUR("自由行(景点+酒店)"),
	 * AROUD("周边/当地跟团游");
	 * **/
	
	/** 分页对象 */
	private PageConfig<Map<String, Object>> pageConfig;
	/** 产品每页显示数量 */
	private int pageSize = 10;
	/** 当前页数 */
	private int page = 1;
	private String fromDest = "";
	private String fromDestId = "";
	private boolean keywordValid = true;
	/**搜索入口标记:直接搜索,下拉补全搜索,沉底搜索**/
	private String entry="";

	@SuppressWarnings("rawtypes")
	@Action("oneSearch")
	public String oneSearch() throws IOException {
		String orikeyword = "";
		if (StringUtils.isNotEmpty(keyword)) {

			if(keyword.length()>80){
				keyword=keyword.substring(0, 80);
			}
			
			orikeyword = keyword;//保留原来的keyword用来还原搜索栏内容

			//先对keyword进行拆分，查找是否其中有同义词存在，分别对拆分后的keyword进行同义词追加
			List ikKeywords = ikSegmenter(keyword); 
			log.info("keyword拆分结果1:"+ikKeywords);
			
			String tempKeyword = ""; 
			int j = 0;
			String[] arrSynonyms = new String[ikKeywords.size()];
			for (int i = 0; i < ikKeywords.size(); i++) {
				//取得keyword同义词进行search
				LikeHashMap synonymsMap = (LikeHashMap) LocalCacheManager.get("COM_SEARCH_KEYWORD_SYNONYMS");
				//keyword满足同义词的分词数量最大为3组
				if(synonymsMap.get(String.valueOf(ikKeywords.get(i)), true).size()>0 && j<3){
					arrSynonyms[i]="";
					for (Iterator iter = ((List)synonymsMap.get(String.valueOf(ikKeywords.get(i)), true).get(0)).iterator(); iter.hasNext();) {
						arrSynonyms[i] = arrSynonyms[i] + (String)iter.next() + ",";
					}
					tempKeyword = tempKeyword + arrSynonyms[i];
					arrSynonyms[i] = arrSynonyms[i].substring(0, arrSynonyms[i].length()-1).trim();
					++j;
				}else{
					if(StringUtils.isNotBlank(arrSynonyms[i])){
						arrSynonyms[i] = arrSynonyms[i] + String.valueOf(ikKeywords.get(i));
					}else{
						arrSynonyms[i] = String.valueOf(ikKeywords.get(i));
					}
					tempKeyword = tempKeyword + String.valueOf(ikKeywords.get(i))  + ",";
				}
			}
			
			//把搜索内容的拆分的各个数组进行配对
			List synonymsList = new ArrayList<String[]>();
			for (int i = 0; i < arrSynonyms.length; i++) {
				List tempList = new ArrayList<String>();
				String[] arr = arrSynonyms[i].split(",");
				if (synonymsList.size()>0){
					for (Object object : synonymsList) {
						String synonyms1 = new String();
						synonyms1 = String.valueOf(object);
						for (int l = 0; l < arr.length; l++) {
							String synonyms = new String();
							synonyms = synonyms1 + arr[l]+",";
							tempList.add(synonyms);
						}
					}
					synonymsList = (List) ((ArrayList) tempList).clone();
				}else{
					for (int l = 0; l < arr.length; l++) {
						String synonyms = new String();
						synonyms = synonyms + arr[l]+",";
						tempList.add(synonyms);
					}
					synonymsList = (List) ((ArrayList) tempList).clone();
				}
				
			}
			for (int i = 0; i < synonymsList.size(); i++) {
				synonymsList.set(i, String.valueOf(synonymsList.get(i)).substring(0, String.valueOf(synonymsList.get(i)).length()-1).trim());
			}
			HttpServletRequest req = getRequest();
			keyword = tempKeyword.substring(0, tempKeyword.length()-1).trim();
			
			//把分组同义词配对结果放入缓存
			LocalSession.set(keyword, synonymsList);
			//LocalCacheManager.put(keyword, synonymsList);
			log.info("keyword拆分结果2:"+keyword);
			UserUser user = (UserUser)getSession(Constant.SESSION_FRONT_USER);
			StringBuffer log_content = new StringBuffer();
			log_content.append(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS")).append("\t");//开始时间
			log_content.append(LoggerParms.getIpAddr(req)).append("\t");//ip地址
			log_content.append(req.getHeader("referer")).append("\t");//referer
			log_content.append(entry.toUpperCase()).append("\t");//搜索的动作
			log_content.append(ServletUtil.getLvSessionId(req, getResponse())).append("\t");//lvsessionId
			log_content.append(user == null ?"guest":user.getUserNo()).append("\t");//userNo
			/** 先清SESSION **/
			if (ServletUtil.getSession(req, getResponse(), "SEARCH_TYPE_COUNT") != null) {
				ServletUtil.removeSession(req, getResponse(), "SEARCH_TYPE_COUNT");
			}
			ServletUtil.putSession(req, getResponse(), "SEARCH_BUSINESS_LOG_CONTENT", log_content);
			/**
			 *  没有明确选择栏目，两种情况：
			 *  	1 直接搜索入口，没有选择栏目  		 newChannel = "" 
			 *  	2  选择没有明确栏目的下拉补全入口  	 newChannel = "" 
			 *  明确栏目入口，两种情况 
			 * 		1  选择了排最前面的下拉补全，带明确栏目   newChannel = TICKET/HOTEL/FREETOUR/ROUTE
			 * 		2  选择了加在下拉补全最后面的搜索提示：带明确栏目，不一定有结果  newChannel = TICKET/HOTEL/FREETOUR/ROUTE 
			 *  先查栏目统计数,算好后把统计数放在session中给后面的栏目action用 根据统计数判断跳转的栏目 ,做chain跳转
			 * **/
			
			/**
			 * 下拉补全第一项和沉底搜索选择了明确栏目,直接跳转相应栏目
			 * **/
			if (!newChannel.equals("")) {
				//转回原来的keyword转给searchction
				keyword = orikeyword;
				/**中文参数转码才能传参**/
				if("HOTEL".equals(newChannel)){
					params = java.net.URLEncoder.encode(keyword, "UTF-8").replaceAll("\\+", "%20");
				}else{
					params = java.net.URLEncoder.encode(fromDest+"-"+keyword, "UTF-8").replaceAll("\\+", "%20");
				}
				return newChannel;			
			}
			
			/**
			 *直接搜索和下拉补全第二项开始没有栏目，走自己搜索逻辑
			 ***/
			selectChannel = keywordDirectSearchService.decideChannel(getRequest(), getResponse(), fromChannel, newChannel, fromDest, keyword, orikeyword);
			
			/**
			 * 选择了加在下拉补全最后面的搜索提示：在门票，酒店，自由行，度假中分别搜索，带明确栏目,但没有搜索结果时页面需要标识
			 * **/
			if (!"".equals(newChannel)&&!newChannel.equals(selectChannel)) {
				selectChannellValid = false ;		
			}
			//清keywords缓存
//			LocalCacheManager.clear(keyword);
			//转回原来的keyword转给searchction
			keyword = orikeyword;
		}
		
		/** productId 号查找产品 **/
		if ("".equals(selectChannel)) {
			if (keyword.matches("[0-9]+")) {
				params = java.net.URLEncoder.encode(fromDest+"-"+keyword, "UTF-8").replaceAll("\\+", "%20");
				return "ROUTE";
			}
		}
		
		/**
		 * 如果selectChannel = ""说明搜索没有结果
		 * 决定栏目跳转
		 * 设置推荐页面标识
		 * **/
		if ("".equals(selectChannel)) {			
			isRecommendPageValid = true ;	
			if (!StringUtil.isEmptyString(newChannel)) {
				selectChannel = newChannel;
			}else if (!StringUtil.isEmptyString(fromChannel) && "MAIN".equals(selectChannel)) {
				selectChannel = fromChannel ;
			}else {
				selectChannel = "ROUTE" ;
			}			
		}
		/**中文参数转码才能传参**/
		if("HOTEL".equals(selectChannel)){
			//encode会把空格转换为 + 需要reaplce回来
			params = java.net.URLEncoder.encode(keyword, "UTF-8").replaceAll("\\+", "%20");
		}else{
			//encode会把空格转换为 + 需要reaplce回来
			params = java.net.URLEncoder.encode(fromDest+"-"+keyword, "UTF-8").replaceAll("\\+", "%20");
		}
		loger.info("返回的分类："+selectChannel);
		return selectChannel ;
		
	}

	@Action("hotelAutocomplete")
	public void hotelAutoComplete() throws IOException{
		keyword = SearchStringUtil.treatKeyWord(keyword);
		List<AutoCompletePlaceObject> autoCompleteList = new ArrayList<AutoCompletePlaceObject>() ;
		if (StringUtils.isNotEmpty(keyword)) {
			autoCompleteList = keywordAutoCompletService.hotelAutoComplete(keyword);
		}
		String json = "";
		if (!autoCompleteList.isEmpty()  ) {
			json = convertAutoCompleteHotelToJsonString(autoCompleteList);
		}
		String callback = this.getRequest().getParameter("callback");
		this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
		this.getResponse().getWriter().println(callback + "(" + json + ")"); // 返回CONTENT.
//		
		
//		keyword = SearchStringUtil.treatKeyWord(keyword);
//		List<AutoCompleteVerHotel> autoCompleteList = new ArrayList<AutoCompleteVerHotel>() ;
//		if (StringUtils.isNotEmpty(keyword)) {
//			autoCompleteList = keywordAutoCompletService.verHotelAutoComplete(keyword);
//		}
//		String json = "";
//		if (!autoCompleteList.isEmpty()  ) {
//			json = convertAutoCompleteVerHotelToJsonString(autoCompleteList);
//		}
//		String callback = this.getRequest().getParameter("callback");
//		this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
//		this.getResponse().getWriter().println(callback + "(" + json + ")"); // 返回CONTENT.

	}
	
	@Action("verhotelAutocomplete")
	public void verhotelAutoComplete() throws IOException{
		keyword = SearchStringUtil.treatKeyWord(keyword);
		List<AutoCompleteVerHotel> autoCompleteList = new ArrayList<AutoCompleteVerHotel>() ;
		if (StringUtils.isNotEmpty(keyword)) {
			autoCompleteList = keywordAutoCompletService.verHotelAutoComplete(keyword);
		}
		String json = "";
		if (!autoCompleteList.isEmpty()  ) {
			json = convertAutoCompleteVerHotelToJsonString(autoCompleteList);
		}
		String callback = this.getRequest().getParameter("callback");
		this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
		this.getResponse().getWriter().println(callback + "(" + json + ")"); // 返回CONTENT.

	}
	
	@Action("autoCompletePlace")
	public void getAutoCompletePlace() throws IOException {
		//Date d = new Date();
		loger.info("SECH!;" + LoggerParms.getNowTime() + ";" + LoggerParms.getSessionId(getSession()) + ";" + LoggerParms.getIpAddr(getRequest()) + ";" + "通用搜索" + ";" + "" + ";" + "" + ";" + keyword
				+ ";" + "" + ";" + "" + ";" + "" + ";" + page + ";;;" + "" + ";" + "" + ";" + "" + ";" + "" + ";" + "自动补全" + ";" + this.getCookieValue("unUserName") + ";" + "SEND!");
		keyword = SearchStringUtil.treatKeyWord(keyword);
		try {
			List<AutoCompletePlaceObject> autoCompleteList = new ArrayList<AutoCompletePlaceObject>() ;
			if (StringUtils.isNotEmpty(keyword)) {
				autoCompleteList = keywordAutoCompletService.getKeywordAutoComplet(fromChannel, fromDestId, keyword);
			}
			putrelatedKeywords2Session(autoCompleteList);
			String json = "";
			if (!autoCompleteList.isEmpty()  ) {
				json = convertAutoCompletePlaceToJsonString(autoCompleteList);
			}
			
			String callback = this.getRequest().getParameter("callback");
			this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
			this.getResponse().getWriter().println(callback + "(" + json + ")"); // 返回CONTENT.

		} catch (Exception e) {
			loger.info("*******通用搜索自动补全出错！**************");
			e.printStackTrace();
		}
		//Date d2 = new Date();
		//System.out.println("合计消耗(MS):"+(d2.getTime()-d.getTime()));
	}
	/**
	 * 把自动补全出的前11个词放入Session中，用于在搜索结果页显示
	 * @param autoCompleteList
	 */
	private void putrelatedKeywords2Session(List<AutoCompletePlaceObject> autoCompleteList){
		if(autoCompleteList == null || autoCompleteList.size() == 0	){
			ServletUtil.removeSession(this.getRequest(), this.getResponse(), "SEARCH_AUTOCOMPLETE_LIST");
		}else{
			Map<String,String> map = new HashMap<String, String>();
			for(AutoCompletePlaceObject item: autoCompleteList){
				if(map.get(item.getWords()) ==  null ){
					StringBuffer url = new StringBuffer("http://www.lvmama.com/search/");
					String codeKeyword = CommonUtil.codeParams(item.getWords());
					String stage = item.getSavedStage().toLowerCase();
					if(StringUtil.isEmptyString(stage)){
						url.append("{fromDest}").append("/").append(codeKeyword).append(".html");
					}else{
						url.append(stage).append("/").append("{fromDest}").append("-").append(codeKeyword).append(".html");
					}
					map.put(item.getWords(), url.toString());
				}
				if(map.size() >= 11 ){
					break;
				}
			}
			if(map.size()>0){
				ServletUtil.putSession(this.getRequest(), this.getResponse(), "SEARCH_AUTOCOMPLETE_LIST", map);
			}
		}
	}
	/**
	 * 目的地搜索自动补全.
	 * a.汉字：目的地name，高频搜索词 b.拼音/简拼：目的地name，高频搜索词 [数据条件]： a.类别，stage=0、1、2（目的地）
	 * b.状态，有效 查找规则 a. 不可空搜索 b. 关键词获取，1.回车；2.点击补全list c. 点击搜索后，直接跳转到dest页面 补全细节
	 * a.排序：拼音排序 b.选中状态：默认选中符合规则的第一栏
	 * c.补全提示：键入的内容如若有内容，则提示；不键入OR键入无内容，则停留在前一次有内容的提示。
	 * d.搜索不到或关键字为空时取默认10个SEQ排前的值.
	 * **/
	@Action("destSearch")
	public void getDestAutoCompletePlace() throws IOException {
		if (StringUtils.isNotEmpty(keyword)) {
			 keyword = SearchStringUtil.treatKeyWord(keyword);
		}		
		loger.info("SECH!;" + LoggerParms.getNowTime() + ";" + LoggerParms.getSessionId(getSession()) + ";" + LoggerParms.getIpAddr(getRequest()) + ";" + "目的地搜索" + ";" + "" + ";" + ""
				+ ";"+""+";;;;" + page + ";" + "" + ";" + "" + ";" + "" + ";" + "" + ";" + "" + ";" + "" + ";" + "自动补全" + ";" +this.getCookieValue("unUserName")+";"+ "SEND!");
		try {
		List<AutoCompletePlaceObject> matchList  = keywordAutoCompletService.destAutoComplete(keyword);
		String json = convertAutoCompleteDestToJsonString(matchList);
		String callback = this.getRequest().getParameter("callback");
		this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
		this.getResponse().getWriter().println(callback + "(" + json + ")"); // 返回CONTENT.
		} catch (Exception e) {
			loger.info("*******点评目的地搜索自动补全出错！**************");
			e.printStackTrace();
		}
	}


	/**
	 * 酒店城市,酒店名称,周边景点 三合一自动补全
	 * 
	 * @return jasonList
	 * @throws IOException
	 */
	@Action("hotelSearch")
	public void getAutoCompleteHotelName() throws IOException {
		keyword = SearchStringUtil.treatKeyWord(keyword);
		// loger.info(keyword);
		try {
			List<AutoCompletePlaceObject> matchList;
			if (StringUtils.isNotEmpty(keyword)) {
				matchList = keywordAutoCompletService.hotelAutoComplete(keyword);
			} else {// 关键字为空的补全
				matchList = autoCompleteOneService.getAutoCompletePlacePlaceObjectListDefault(SearchConstants.HOTEL_MERGE_COMPLETE, null, 10);
			}
			String json = "";
			if (!matchList.isEmpty()) {
				json = convertAutoCompletePlaceToJsonString(matchList);
			} else {
				json = "{}";
			}
			String callback = this.getRequest().getParameter("callback");
			this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
			this.getResponse().getWriter().println(callback + "(" + json + ")"); // 返回CONTENT.
		} catch (Exception e) {
			System.out.println("*******酒店自动补全出错！**************");
			e.printStackTrace();
		}

	}
	
	/**点评酒店搜索**/
	@Action("hotelNameSearch")
	public void getHotelNameComplete() throws IOException {
		loger.info("SECH!;" + LoggerParms.getNowTime() + ";" + LoggerParms.getSessionId(getSession()) + ";" + LoggerParms.getIpAddr(getRequest()) + ";" + "点评酒店搜索" + ";" + "searchHotelName" + ";"
				+ ";" + keyword + ";" + ""  + ";" + ""  + ";" + "" + ";" + page + ";" + "" + ";" + "" + ";" + "" + ";" + "" + ";" + "" + ";"
				+ "" + ";" + "自动补全" + ";" + "SEND!");
		try {
		StringBuffer json = new StringBuffer("{");
		String placeListJson = "";
		PageConfig<PlaceBean> pageConfig = hotelSearchService.searchHotelName(keyword);
		if (pageConfig != null) {
			json.append("\"page\":" + page + ",");
			json.append("\"totalResultSize\":" + pageConfig.getTotalResultSize());
			List<PlaceBean> placeList = pageConfig.getItems();
			if (!placeList.isEmpty()) {
				placeListJson = convertPlaceListWhitIdToJsonString(placeList);
				json.append(",\"placeListJson\":" + placeListJson);
			}
		}
		json.append("}");
		String callback = this.getRequest().getParameter("callback");
		this.getResponse().setContentType("application/json; charset=utf-8");
		this.getResponse().getWriter().println(callback + "(" + json.toString() + ")");
		} catch (Exception e) {
			loger.info("*******点评酒店搜索自动补全出错！**************");
			e.printStackTrace();
		}
	}

	
	/**
	 * 自动提示LIST封装成JSON格式
	 * 
	 * @param matchList
	 * @return
	 **/
	private String convertAutoCompletePlaceToJsonString(List<AutoCompletePlaceObject> matchList) {
		StringBuffer json = new StringBuffer("{");
		json.append("\"page\":" + page + ",");
		json.append("\"totalResultSize\":" + matchList.size());
		json.append(",\"placeListJson\":");
		json.append("[");
		for (AutoCompletePlaceObject autoCompletePlaceObject : matchList) {
			json.append("{");
			json.append("\"id\":\"" + autoCompletePlaceObject.getShortId()+ "\",");
			json.append("\"pinYin\":\"" + autoCompletePlaceObject.getMatchword() + "\",");
			json.append("\"name\":\"" + autoCompletePlaceObject.getWords() + "\",");
			json.append("\"channel\":\"" + autoCompletePlaceObject.getStage() + "\"");
			json.append("},");
		}
		json.deleteCharAt(json.length() - 1).append("]");
		json.append("}");
		return json.toString();
	}
	
	private String convertAutoCompleteHotelToJsonString(List<AutoCompletePlaceObject> matchList) {
		StringBuffer json = new StringBuffer("{");
		json.append("\"totalResultSize\":" + matchList.size());
		json.append(",\"placeListJson\":");
		json.append("[");
		for (AutoCompletePlaceObject item : matchList) {
			AutoCompletePlaceHotel hotel = (AutoCompletePlaceHotel) item;
			String lon =  hotel.getLongitude() == null ? ""  :  hotel.getLongitude().toString();
			String lat = hotel.getLatitude() == null ? "" : hotel.getLatitude().toString();
			String destParentStr = hotel.getDestParentStr();
			destParentStr = destParentStr == null ? "" : "，" +destParentStr;
			json.append("{");
			json.append("\"id\":\"" + hotel.getShortId()+ "\",");
			json.append("\"pinYin\":\"" + hotel.getMatchword() + "\",");
			json.append("\"name\":\"" + hotel.getWords() + destParentStr +"\",");
			json.append("\"type\":\"" + hotel.getStage() + "\",");
			json.append("\"longitude\":\"" +lon + "\",");
			json.append("\"latitude\":\"" + lat + "\"");
			json.append("},");
		}
		json.deleteCharAt(json.length() - 1).append("]");
		json.append("}");
		return json.toString();
	}
	
	
	private String convertAutoCompleteVerHotelToJsonString(List<AutoCompleteVerHotel> matchList) {
		StringBuffer json = new StringBuffer("{");
		json.append("\"totalResultSize\":" + matchList.size());
		json.append(",\"placeListJson\":");
		json.append("[");
		for (AutoCompleteVerHotel hotel : matchList) {
			String lon =  hotel.getLongitude() == null ? ""  :  hotel.getLongitude().toString();
			String lat = hotel.getLatitude() == null ? "" : hotel.getLatitude().toString();
			String autocompleteName = hotel.getAutocompleteName()+","+hotel.getParentName();
			json.append("{");
			json.append("\"id\":\"" + hotel.getId()+ "\",");
			json.append("\"pinYin\":\"" + hotel.getMatchword() + "\",");
			json.append("\"name\":\""  + autocompleteName +"\",");
			json.append("\"type\":\"" + hotel.getAutocompleteMark() + "\",");
			json.append("\"longitude\":\"" +lon + "\",");
			json.append("\"latitude\":\"" + lat + "\",");
			json.append("\"parentId\":\"" + hotel.getParentId() + "\"");
			json.append("},");
		}
		json.deleteCharAt(json.length() - 1).append("]");
		json.append("}");
		return json.toString();
	}
	/**
	 * 封装成JSON格式
	 * @param matchList
	 * @return
	 */
	private String convertAutoCompleteDestToJsonString(List<AutoCompletePlaceObject> matchList) {
		StringBuffer json = new StringBuffer("{");
		json.append("\"page\":" + page + ",");
		json.append("\"totalResultSize\":" + matchList.size());
		json.append(",\"placeListJson\":");
		json.append("[");
		for (AutoCompletePlaceObject autoCompletePlaceObject : matchList) {
			json.append("{");
			json.append("\"pinYin\":\"" + "http://www.lvmama.com/dest/"+ autoCompletePlaceObject.getPinyin() + "\",");//拼音字段被复用为URL
			json.append("\"id\":\"" + autoCompletePlaceObject.getShortId() + "\",");
			json.append("\"name\":\"" + autoCompletePlaceObject.getWords() + "\"");
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
	
	private String convertPlaceListWhitIdToJsonString(List<PlaceBean> placeList) {
		StringBuffer sb = new StringBuffer("[");
		for (PlaceBean placeBean : placeList) {
			sb.append("{");
			sb.append("\"pinYin\":\"" + placeBean.getPinYin().toLowerCase() + "\",");
			sb.append("\"id\":\"" + placeBean.getId() + "\",");
			//sb.append("\"name\":\"" + placeBean.getName() + "\"");
			/*PLACE_SEARCH_INFO索引的NAME字段包括了拼音和简拼，显示的时候需要去掉拼音部分 */ 
			if (placeBean.getName().indexOf("~")>=0) { 
				sb.append("\"name\":\"" + placeBean.getName().substring(0, placeBean.getName().indexOf("~", 0)) + "\""); 
			}else { 
				sb.append("\"name\":\"" + placeBean.getName() + "\""); 
			}

			sb.append("},");
		}
		sb.deleteCharAt(sb.length() - 1).append("]");
		return sb.toString();
	}
	
	public String getFromChannel() {
		return fromChannel;
	}

	public void setFromChannel(String fromChannel) {
		this.fromChannel = fromChannel;
	}

	public String getNewChannel() {
		return newChannel;
	}

	public void setNewChannel(String newChannel) {
		this.newChannel = newChannel;
	}

	public String getKeyword() {
		return this.keyword;
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

	public String getSelectChannel() {
		return selectChannel;
	}

	public void setSelectChannel(String selectChannel) {
		this.selectChannel = selectChannel;
	}

	public boolean isSelectChannellValid() {
		return selectChannellValid;
	}

	public void setSelectChannellValid(boolean selectChannellValid) {
		this.selectChannellValid = selectChannellValid;
	}

	public boolean isRecommendPageValid() {
		return isRecommendPageValid;
	}

	public void setRecommendPageValid(boolean isRecommendPageValid) {
		this.isRecommendPageValid = isRecommendPageValid;
	}

	public String getFromDest() {
		return this.fromDest;
	}

	public void setFromDest(String fromDest) {
		this.fromDest = fromDest;
	}

	public String getFromDestId() {
		return fromDestId;
	}

	public String getParams() {
		return params;
	}

	public boolean isProductIdSearch() {
		return productIdSearch;
	}

	public void setProductIdSearch(boolean productIdSearch) {
		this.productIdSearch = productIdSearch;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public void setFromDestId(String fromDestId) {
		this.fromDestId = fromDestId;
	}

	public boolean isKeywordValid() {
		return keywordValid;
	}

	public void setKeywordValid(boolean keywordValid) {
		this.keywordValid = keywordValid;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public static String getSessionId(HttpSession session) {
		return session.getId();
	}

	protected HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	public String getCookieValue(String cookieName) {
		Cookie[] cookies = this.getRequest().getCookies();
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookieName.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return "";
	}

	// 分词器
	public static List ikSegmenter(String str) {
		List iklist = new ArrayList<String>();
		StringReader reader = new StringReader(str);
		IKSegmenter ik = new IKSegmenter(reader, true);// 后一个变量决定是否消歧
		String result = "";
		Lexeme lexeme = null;
		try {
			while ((lexeme = ik.next()) != null) {
				iklist.add(lexeme.getLexemeText());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(iklist.size()<=0){
			iklist.add(str);
		}
		return iklist;
	}
	
}
