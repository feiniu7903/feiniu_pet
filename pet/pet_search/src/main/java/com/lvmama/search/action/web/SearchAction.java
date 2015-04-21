package com.lvmama.search.action.web;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.po.seo.SeoIndexPage;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.search.ComSearchTranscodeService;
import com.lvmama.comm.pet.service.seo.SeoIndexPageService;
import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.SearchConstants.FILTER_PARAM_TYPE;
import com.lvmama.comm.search.SearchConstants.SEARCH_TYPE;
import com.lvmama.comm.search.annotation.FilterParam;
import com.lvmama.comm.search.service.TuangouSearchService;
import com.lvmama.comm.search.vo.AutoCompletePlaceObject;
import com.lvmama.comm.search.vo.HotelSearchVO;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.RouteSearchVO;
import com.lvmama.comm.search.vo.SearchVO;
import com.lvmama.comm.search.vo.TicketSearchVO;
import com.lvmama.comm.search.vo.TypeCount;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.search.service.HotelSearchService;
import com.lvmama.search.service.KeywordAutoCompletService;
import com.lvmama.search.service.RouteSearchService;
import com.lvmama.search.service.SearchBusinessService;
import com.lvmama.search.service.TicketSearchService;
import com.lvmama.search.service.VerHotelSearchService;
import com.lvmama.search.service.recommendEngine.SearchRecommendService;
import com.lvmama.search.synonyms.LikeHashMap;
import com.lvmama.search.synonyms.LocalSession;
import com.lvmama.search.util.AccessURLException;
import com.lvmama.search.util.CommonUtil;
import com.lvmama.search.util.FilterParamUtil;
import com.lvmama.search.util.LocalCacheManager;
import com.lvmama.search.util.LoggerParms;
import com.lvmama.search.util.PageConfig;
import com.lvmama.search.util.SORT;
import com.lvmama.search.util.SearchStringUtil;

/**
 * 
 * 搜索基础Action
 * 
 * @author YangGan
 *
 */
@Results({
	@Result(name="urlerror",location="http://www.lvmama.com",type="redirect"),
	@Result(name = "recommend", location = "/WEB-INF/ftl/common/recommend.ftl", type = "freemarker"),
	@Result(name="ticket",location="/WEB-INF/ftl/ticket/ticket_search.ftl",type="freemarker"),
	@Result(name = "route", location = "/WEB-INF/ftl/route/route_search.ftl", type = "freemarker"),
	@Result(name = "train", location = "/WEB-INF/ftl/traffic/train_search.ftl", type = "freemarker"),
	@Result(name="hotel",location="/WEB-INF/ftl/hotel/hotel_search.ftl",type="freemarker"),
	@Result(name="verhotel",location="/WEB-INF/ftl/verhotel/verhotel_search.ftl",type="freemarker")
})
@SuppressWarnings({"rawtypes","unchecked"})
public abstract class SearchAction extends BaseAction{
	
	/**
	 * 序列
	 */
	private static final long serialVersionUID = 6392125605413428060L;
	protected Log search_business_loger = LogFactory.getLog("search_new");
	protected Log loger = LogFactory.getLog(this.getClass());
	
	public SearchAction(String result,Class searchVOClazz,String seoPageCode){
		this.result = result;
		this.searchVOClazz = searchVOClazz;
		this.seoPageCode = seoPageCode;
	}
	protected SearchBusinessService searchBusinessService;
	protected TicketSearchService ticketSearchService;
	protected HotelSearchService hotelSearchService;

	protected SearchRecommendService searchRecommendService;

	
	protected VerHotelSearchService verHotelSearchService ;

	
	protected RouteSearchService routeSearchService;
	protected TuangouSearchService tuangouSearchService;
	protected SeoIndexPageService seoIndexPageService;
	protected ComSearchTranscodeService comSearchTranscodeService;
	@Resource
	private KeywordAutoCompletService keywordAutoCompletService;
	/** 
	 * 搜索的分类
	 * ticket 景点门票
	 * hotel 特色酒店
	 * route 度假（包含所有线路）
	 * group 跟团游
	 * freelong 自由行（机票+酒店）
	 * freetour 自由行（景点+酒店）
	 * around 周边/当地跟团游
	 * */
	protected String type;
	/**
	 * 查询参数
	 * 包含出发地、关键词、筛选条件
	 */
	protected String params;
	protected String landMark;
	protected String hotel_star;
	protected String hotelBrand;
	protected String parentID;
	protected String hotel_longitude;
	protected String hotel_latitude;
	protected String hotel_place;
	protected String src;
	
	/** 
	 * 搜索的筛选条件
	 * 包含所有的筛选条件与排序、分页信息A12B23C23...S12P1
	 * 每个筛选类型均有固定的字母、S代表排序、P代表分页页码
	 * 
	 */
	protected String filterStr ="";
	
	protected SearchVO searchvo;
	
	protected SORT[] sorts;
	
	/** 出发地  （通过解析params之后获取值）*/
	protected String fromDest;
	
	protected String request_fromDest;
	/** 搜索关键词 （通过解析params之后获取值）*/
	protected String keyword;
	
	protected String request_keyword;
	/**
	 * freemarker的result
	 */
	protected String result;
	/**
	 * SEO_INDEX_PAGE表中的PAGE_CODE
	 */
	protected String seoPageCode;
	
	
	protected Class<?> searchVOClazz;
	
	protected PlaceBean place;

	/**
	 * 分类的统计数
	 */
	protected TypeCount tc;
	
	/** 分页对象 */
	protected PageConfig pageConfig;
	
	/** 团购列表*/
	protected List<ProductBean> tuanGouList = new ArrayList<ProductBean>();
	/** 推荐列表*/
	protected List<ProductBean> recommendList ;
	
	/**
	 * 用于生成筛选条件的结果List
	 */
	protected List pageConfigItems; 
	/**
	 * 出发地、关键词、筛选条件MAP
	 * 用户套用TDK模版
	 */
	protected Map<String,Object> filedValueMap = new HashMap<String, Object>();
	protected String pageTitle;
	protected String pageDescription;
	protected String pageKeyword;
	protected Map<String,String> relatedKeywords;
	protected boolean useRecommendLogic;
	
	/**
	 * 解析查询参数
	 */
	protected void parseParams(){
		String[] tmp_strs = this.params.split("-");
		if(tmp_strs.length == 2){//查询参数仅有出发地、关键词 格式如：/search/ticket/上海-欢乐谷.html
			if("hotel".equals(type)){//酒店搜索 没有出发地 2个参数是 /search/hotel/关键词-筛选条件.htlm
				Pattern p2 = Pattern.compile("(([A-Z])("+SearchConstants.FILTER_PARAM_REGEX+"))");
				Matcher m2 =p2.matcher(tmp_strs[1]);
				if(m2.find()){
					this.keyword = tmp_strs[0];
					this.filterStr = tmp_strs[1];
				}else{
					this.fromDest = tmp_strs[0];
					this.keyword = tmp_strs[1];
				}
			}else{
				this.fromDest = tmp_strs[0];
				this.keyword = tmp_strs[1];
			}
		}else if(tmp_strs.length ==3){//查询参数包含出发地、关键词、筛选条件 格式如：/search/ticket/上海-欢乐谷-A1B2C3S1P1.html
			this.fromDest = tmp_strs[0];
			this.keyword = tmp_strs[1].trim();
			this.filterStr = tmp_strs[2];
		}else if(tmp_strs.length == 1){//查询参数不包含出发地、筛选条件 仅包含关键词 格式如：/search/ticket/欢乐谷.html
			this.keyword = tmp_strs[0].trim();
		}else{
			loger.warn("Search Params is error!!!!! Access URL: "+getRequest().getRequestURL());
			throw new AccessURLException("查询参数异常.");
		}
		if(StringUtil.isEmptyString(this.keyword)){
			loger.warn("Search keyword is empty!!!!! Access URL: "+getRequest().getRequestURL());
			throw new AccessURLException("查询参数异常.");
		}
		
		if(StringUtil.isEmptyString(this.fromDest)){//出发地为空，取缓存中的出发地
			this.fromDest = (String) getRequest().getAttribute(Constant.IP_FROM_PLACE_NAME);
			if(StringUtil.isEmptyString(this.fromDest)){
				this.fromDest = "上海";
			}
		}
		if(this.keyword!=null){
			if(keyword.length()>80){
				this.keyword=this.keyword.substring(0, 80);
			}
		}
		this.request_fromDest = new String(this.fromDest);
		this.request_keyword= new String(this.keyword);
		this.fromDest = CommonUtil.codeParams(this.fromDest);
		
		if(!CommonUtil.isNumeric(this.keyword)){//非数字的关键词
			if(this.keyword.startsWith("W")){
				this.keyword = CommonUtil.codeParams(this.keyword.substring(1));
			}else{
				this.keyword = CommonUtil.codeParams(this.keyword);
			}
		}
	}
	/**
	 * 解析筛选条件
	 * @param params 字符串格式的筛选条件
	 * @return 解析完成的筛选条件
	 */
	protected abstract void parseFilterStr();
	
	/**
	 * 无结果时的推荐逻辑
	 * 1.查询线路是否有结果 
	 * 2.查询出境是否有结果
	 * 3.查询门票是否有结果
	 * 4.查询酒店是否有结果
	 */
	protected void searchRecommend(){
		String fromType = type;
		getRequest().setAttribute("fromType", fromType);
		if("hotel".equals(fromType)){
			result = "hotel";
			return;
		}
		//查询线路是否有结果 
		if(!"route".equals(fromType)){
			pageConfig = routeSearchService.search(new RouteSearchVO(searchvo.getFromDest(), searchvo.getKeyword()), sorts);
		}
		if(pageConfig != null && pageConfig.getTotalResultSize() > 0 ){//线路查询有结果 直接返回
			type = "route";
			result = "route";
			return;
		}
		//当查询线路无结果时 查询出境是否有结果 
		pageConfig = searchBusinessService.foreignHits(searchvo.getFromDest(), searchvo.getKeyword());
		if(pageConfig != null && pageConfig.getTotalResultSize() > 0 ){//出境查询有结果 直接返回
			type = "route";
			result = "recommend";
			getRequest().setAttribute("SEARCH_FOREIGN", true);
			return;
		}
		
		//当出境无结果时 查询门票产品
		if(!"ticket".equals(fromType)){
			pageConfig = ticketSearchService.search(new TicketSearchVO(searchvo.getFromDest(), searchvo.getKeyword()), sorts);
			pageConfigItems = pageConfig.getAllItems();
		}
		if(pageConfig != null && pageConfig.getTotalResultSize() > 0 ){//门票查询有结果 直接返回
			pageConfig = ticketSearchService.getTicketProducts(pageConfig,new TicketSearchVO(searchvo.getFromDest(), searchvo.getKeyword()));
			type = "ticket";
			result = "ticket";
			return;
		}
//		//当门票无结果时 查询酒店产品
//		if(!"hotel".equals(fromType)){
//			pageConfig = hotelSearchService.search(new HotelSearchVO(searchvo.getFromDest(), searchvo.getKeyword()), sorts);
//			type = "hotel";
//			result = "hotel";
//			return;
//		}
//		if(pageConfig != null && pageConfig.getTotalResultSize() > 0 ){//酒店查询有结果 直接返回
//			pageConfig = hotelSearchService.gethotelProductsAndBranch(pageConfig, new HotelSearchVO(searchvo.getFromDest(), searchvo.getKeyword()));
//			type = "hotel";
//			return;
//		}
		result = "recommend";
		type = "route";
	}
	/**
	 * 按照传入的筛选条件类型
	 * @param paramTypes 包含的筛选条件类型
	 * @return SearchVO 构建好查询对象
	 * 备注：filterStr(E461F465J457)
	 * 
	 */
	protected SearchVO fillSearchvo(){
		try {
			searchvo  = (SearchVO) searchVOClazz.newInstance();
			if(filterStr != null && !"".equals(filterStr)){
				StringBuffer sbuff = new StringBuffer();
				//不进行转码的参数
				List<String> unTranscodeParams = new ArrayList<String>();
				Map<String,Method> map = new HashMap<String, Method>();
				Map<String,String> fieldNameMap = new HashMap<String, String>();
				
				for(Class<?> clazz = searchVOClazz ; clazz != Object.class ; clazz = clazz.getSuperclass()) {
					Field[] fields =  clazz.getDeclaredFields();
					for( Field f : fields ){
						FilterParam fp_a = f.getAnnotation(FilterParam.class);
						if(fp_a!=null){
							Method method = clazz.getDeclaredMethod(CommonUtil.parSetName(f.getName()), f.getType());
							sbuff.append(fp_a.value());
							if(f.getType().equals(List.class)){
								map.put(fp_a.value()+"_GET", clazz.getDeclaredMethod(CommonUtil.parGetName(f.getName())));
							}
							map.put(fp_a.value(), method);
							fieldNameMap.put(fp_a.value(), f.getName());
							if(! fp_a.transcode() ){
								unTranscodeParams.add(fp_a.value());
							}
						}
					}
				}
				StringBuffer regex = new StringBuffer();
				//sbuff(KOJEFNP---J456K200O499)
				regex.append("([").append(sbuff).append(FILTER_PARAM_TYPE.S.toString()).append("]("+SearchConstants.FILTER_PARAM_REGEX+"))");
				Pattern p = Pattern.compile(regex.toString());
				Matcher m = p.matcher(filterStr); //filterStr(E461F465J457)
				List<String> p_list = new ArrayList<String>();
				while(m.find()){
					String a = m.group(1);	//E461
					Pattern p2 = Pattern.compile("(([A-Z])("+SearchConstants.FILTER_PARAM_REGEX+"))");
					Matcher m2 =p2.matcher(a);
					if(m2.find()){
						String key_val = m2.group(1);	//key_val(J456)
						String key = m2.group(2); 		//E
						String val = m2.group(3).replace("/", "");	//461
						
						//FILTER_PARAM_TYPE.Q 二次搜索
						if(FILTER_PARAM_TYPE.Q.toString().equals(key) && !CommonUtil.isNumeric(val)){
							String code_val;
							String val_copy = val;
							if(val.startsWith("W")){
								val_copy = val_copy.substring(1);
							}
							code_val = CommonUtil.codeParams(val_copy);
							if(code_val.equals(val_copy)){//没有转码成功
								p_list.add(key_val);
							}else{
								//对于Filterparams中的Q（二次筛选）进行编码处理
								p_list.add("Q"+code_val);
							}
						}else{
							//用于重新计算Filterparams的排序
							p_list.add(key_val);	//key_val(E461)
						}
						
						if(FILTER_PARAM_TYPE.S.toString().equals(key)){
							sorts = new SORT[]{SORT.getSort(Integer.parseInt(val))};
						}else{
							//Filterparams中的设置transcode为false的 不进行转码处理 例如:K(开始价格)，O(结束价格)，P(分页),I(游玩天数),U(是否有景区活动),V(促销活动)
							if(! unTranscodeParams.contains(key) ){
								if(FILTER_PARAM_TYPE.Q.toString().equals(key) && val.startsWith("W")){
									val = val.substring(1);
								}else{
									val = CommonUtil.transcodeParams(val);	//解码参数 把编码转换为汉字(高档酒店)
								}
							}
							Method method = map.get(key);
							
							//searchvo对象中Integer,List参数设置
							if(method.getParameterTypes()[0].equals(Integer.class)){
								try{
									method.invoke(searchvo, Integer.parseInt(val));
									filedValueMap.put(fieldNameMap.get(key),val);
								}catch(NumberFormatException e){
									loger.warn("parseInt fail,FILTER_PARAM_TYPE:"+key+"FILTER_PARAM_VAL:"+val);
								}
							}else if(method.getParameterTypes()[0].equals(List.class)){
								Method getMethod = map.get(key+"_GET");
								List<String> val_list = (List<String>) getMethod.invoke(searchvo);
								if(val_list == null){
									val_list = new ArrayList<String>();
								}
								val_list.add(val);
								method.invoke(searchvo, val_list);
								filedValueMap.put(fieldNameMap.get(key),val_list);
							}else{
								method.invoke(searchvo, val);
								filedValueMap.put(fieldNameMap.get(key),val);
							}
						}
					}
				}
				Collections.sort(p_list);//p_list[J456, K200, O499]
				StringBuffer sb = new StringBuffer();
				for(String s:p_list){
					sb.append(s);
				}
				filterStr = sb.toString();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return searchvo;
	}
	/**
	 * 查询数据
	 * @return 查询的结果数
	 */
	protected abstract void searchData();
	/**
	 * 初始化团购数据
	 */
	protected void initGroupData(){		
		tuanGouList = tuangouSearchService.search(searchvo, type);
	}
	/**
	 * 利用TDK模版生成页面TDK信息
	 * @param str TDK模版
	 * @return
	 */
	public String replacePageInfo(String str){
		if(!StringUtil.isEmptyString(str)){
			String regex = "\\{([a-zA-Z]+)\\}";
			Pattern p = Pattern.compile(regex);
			Matcher m=p.matcher(str);
			while(m.find()){
				String fieldName = m.group(1);
				Object val = filedValueMap.get(fieldName);
				if(val != null){
					if("visitDay".equals(fieldName)){
						val = val.toString()+"日";
					}
					if(val.getClass().equals(ArrayList.class)){
						String val_str = StringUtils.join((List<String>)val, " ");
						System.out.println(val_str);
						str = str.replaceAll("\\{"+fieldName+"\\}",val_str);
					}else{
						str = str.replaceAll("\\{"+fieldName+"\\}",val.toString());
					}
				}else{
					str = str.replaceAll("\\{"+fieldName+"\\}","");
				}
			}
		}
		return str;
	}
	/**
	 * 初始化结果页的Title keyword description
	 */
	protected void initTitle(){
		SeoIndexPage sip =  (SeoIndexPage) LocalCacheManager.get("SIP_"+this.seoPageCode);
		if(sip == null){
			sip = seoIndexPageService.getSeoIndexPageByPageCode(this.seoPageCode);
			if(sip == null){
				throw new RuntimeException("Can not find SEO_INDEX_PAGE. The PAGE_CODE is:"+this.seoPageCode);
			}
			LocalCacheManager.put("SIP_"+this.seoPageCode, sip);
		}
		pageTitle = replacePageInfo(sip.getSeoTitle());
		pageKeyword = replacePageInfo(sip.getSeoKeyword());
		pageDescription = replacePageInfo(sip.getSeoDescription());
	}
	/**
	 * 初始化分页的URL链接
	 */
	protected void initPageURL(){
		pageConfig.setUrl("http://www.lvmama.com/search/"+type+"/" + this.fromDest+"-"+this.keyword +FilterParamUtil.initPageURL(filterStr));
	}
	
	
	/**
	 * 搜索方法
	 * @return
	 */
	@Action("search")
	public String search(){
		
		HttpServletRequest req = getRequest();
		HttpServletResponse resp = getResponse();
		
		//设置查询日志
		StringBuffer log_content = (StringBuffer) ServletUtil.getSession(req, resp, "SEARCH_BUSINESS_LOG_CONTENT");
		if(log_content == null){
			log_content = new StringBuffer();
			UserUser user = (UserUser)getSession(Constant.SESSION_FRONT_USER);
			log_content.append(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS")).append("\t");//开始时间
			log_content.append(LoggerParms.getIpAddr(req)).append("\t");
			log_content.append(req.getHeader("referer")).append("\t");
			log_content.append("DIRECT").append("\t");//搜索的动作
			log_content.append(ServletUtil.getLvSessionId(req, resp)).append("\t");
			log_content.append(user == null ?"guest":user.getUserNo()).append("\t");
		}else{
			ServletUtil.removeSession(req, resp, "SEARCH_BUSINESS_LOG_CONTENT");
		}
		
		try{
			StringBuffer log_c = new StringBuffer("search type:");
			log_c.append(type);
			log_c.append(" params:").append(params);
			loger.info(log_c.toString());
			
			this.parseParams(); 	//解析查询参数,分解出fromDest,keyword,filterStr
			this.parseFilterStr();  //详细解析各类型的filterStr筛选条件
			
			//-----------------------转码-------------------------
			String trans_code_from_dest = CommonUtil.transcodeParams(this.request_fromDest);
			if(SearchConstants.FROMDESTS.getCode(trans_code_from_dest) == null){//错误的出发地,默认使用上海
				loger.warn("FromDest is error!!!!! Access URL: "+getRequest().getRequestURL());
				trans_code_from_dest = "上海";
				this.fromDest = "79";
			}
			
			this.searchvo.setFromDest(trans_code_from_dest);//出发地
			this.searchvo.setCookieid(ServletUtil.getLvSessionId(req, resp));
		
			String transCode_keyword = null;
			if(request_keyword.startsWith("W")){
				transCode_keyword = request_keyword.substring(1);
			}else{
				transCode_keyword = CommonUtil.transcodeParams(this.request_keyword);
			}
			//取得keyword同义词进行search
			String orikeyword = transCode_keyword;//保留原来的keyword用来还原搜索栏内容
			this.searchvo.setOrikeyword(orikeyword);//出发地
			//keyword规范词
			
			//先对keyword进行拆分，查找是否其中有同义词存在，分别对拆分后的keyword进行同义词追加
			List ikKeywords = OneSearchAction.ikSegmenter(transCode_keyword);
			int j = 0;
			String[] arrSynonyms = new String[ikKeywords.size()];
			transCode_keyword = "";
			for (int i = 0; i < ikKeywords.size(); i++) {
				//取得keyword同义词进行search
				LikeHashMap synonymsMap = (LikeHashMap) LocalCacheManager.get("COM_SEARCH_KEYWORD_SYNONYMS");
				//keyword满足同义词的分词数量最大为3组
				if(synonymsMap.get(String.valueOf(ikKeywords.get(i)), true).size()>0 && j<3){
					arrSynonyms[i]="";
					for (Iterator iter = ((List)synonymsMap.get(String.valueOf(ikKeywords.get(i)), true).get(0)).iterator(); iter.hasNext();) {
						arrSynonyms[i] = arrSynonyms[i] + (String)iter.next() + ",";
					}
					transCode_keyword = transCode_keyword + arrSynonyms[i];
					arrSynonyms[i] = arrSynonyms[i].substring(0, arrSynonyms[i].length()-1).trim();
					++j;
				}else{
					if(StringUtils.isNotBlank(arrSynonyms[i])){
						arrSynonyms[i] = arrSynonyms[i] + String.valueOf(ikKeywords.get(i));
					}else{
						arrSynonyms[i] = String.valueOf(ikKeywords.get(i));
					}
					transCode_keyword = transCode_keyword + String.valueOf(ikKeywords.get(i))  + ",";
				}
			}
//			recommendList=searchRecommendService.getproductBeanById(LoggerParms.getIpAddr(req));
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
			
			transCode_keyword = transCode_keyword.substring(0, transCode_keyword.length()-1);
			//把分组同义词配对结果放入缓存
//			LocalCacheManager.put(transCode_keyword, synonymsList);
			LocalSession.set(transCode_keyword, synonymsList);
			log.info("transCode_keyword拆分结果3:"+transCode_keyword);
			this.searchvo.setKeyword(transCode_keyword);
			this.filedValueMap.put("keyword", transCode_keyword);
			this.filedValueMap.put("fromDest", trans_code_from_dest);
			
			//查询所有栏目的统计数
			if(!"verhotel".equals(result)){
			tc = searchBusinessService.getTypeCount(getRequest(), resp, trans_code_from_dest, transCode_keyword, orikeyword);
			}

			//依据查询HotelSearchVO和sort[]排序对象，查询到N多酒店Place和销售产品
			this.searchData();
			
			//关键词是否为城市
			place = searchBusinessService.KeywordIsCity(trans_code_from_dest,transCode_keyword);
			useRecommendLogic = false;//是否使用了推荐逻辑
			String old_type = new String(type);
			
			//当查询结果为空且没有筛选条件时进行推荐逻辑
			if((pageConfig ==null || pageConfig.getTotalResultSize() == 0) && StringUtil.isEmptyString(filterStr)){
				if(!"verhotel".equals(result)){
					searchRecommend();
				}
				
				useRecommendLogic = true;
			}
			if(pageConfig==null){
				pageConfig= new PageConfig(0);
			}else{
				if(pageConfigItems==null){
					pageConfigItems = pageConfig.getAllItems();  //---为空，但是Items在前面有10个分页值, 关键是线路等也公用该逻辑
				}
				if(!"HOTEL".equals(type)){//非酒店搜索 筛选条件收集排序,酒店搜索单独处理筛选条件
					CommonUtil.initFilterParam2Request(searchvo.getKeyword(),type,req,pageConfigItems);
				}
			}
			//对PageConfig items中的结果进行分页处理
			pageConfig.initItems();
			this.initGroupData();	//初始化团购数据
			this.initPageURL();
			this.initTitle();	//初始化结果页的Title等
			//查询客户端关键字的历史记录
			relatedKeywords = (Map<String, String>) ServletUtil.getSession(req, resp, "SEARCH_AUTOCOMPLETE_LIST");

			//暂时注掉，等数据库同步
//			if(relatedKeywords==null || relatedKeywords.size()<1 ){
//				String keywordcomplete = SearchStringUtil.treatKeyWord(orikeyword);
//				List<AutoCompletePlaceObject> autoCompleteList = new ArrayList<AutoCompletePlaceObject>() ;
//				if (StringUtils.isNotEmpty(keywordcomplete)) {
//					autoCompleteList = keywordAutoCompletService.getKeywordAutoComplet("", this.fromDest, keywordcomplete);
//				}
//				putrelatedKeywords2Session(autoCompleteList);
//				relatedKeywords = (Map<String, String>) ServletUtil.getSession(req, resp, "SEARCH_AUTOCOMPLETE_LIST");
//			}

			if(relatedKeywords != null){
//				relatedKeywords.remove(transCode_keyword);
				//keyword同义词循环除去
				String[] arr = transCode_keyword.split(",");
				for (String string : arr) {
					relatedKeywords.remove(string);
				}
			}
			//设置大产品类型的数量
			int searchCount = pageConfig.getTotalResultSize();
			if(SEARCH_TYPE.TICKET.getCode().equalsIgnoreCase(type)){
				tc.setTicket(searchCount);
			}else if(SEARCH_TYPE.HOTEL.getCode().equalsIgnoreCase(type)){
				tc.setHotel(searchCount);
			}else if(SEARCH_TYPE.ROUTE.getCode().equalsIgnoreCase(type)){
				if(tc.getGroup()>0 || tc.getFreelong() >0){//如果度假查询进行聚合处理了
					tc.setRoute(searchCount+tc.getAround()+tc.getFreetour());

				}else if(tc.getFreetour() > 0 && tc.getAround() > 0 && !tc.isKeywordIsFromDest()){//如果没有则查询FREETOUR返回结果,AROUND聚合沉底
					tc.setRoute(searchCount+tc.getAround());

				}else{
					tc.setRoute(searchCount);
				}
			}else if(SEARCH_TYPE.FREELONG.getCode().equalsIgnoreCase(type)){
				tc.setFreelong(searchCount);
				tc.setRoute(tc.getGroup()+tc.getFreetour()+tc.getAround()+tc.getFreelong());
			}else if(SEARCH_TYPE.FREETOUR.getCode().equalsIgnoreCase(type)){
				tc.setFreetour(searchCount);
				tc.setRoute(tc.getGroup()+tc.getFreetour()+tc.getAround()+tc.getFreelong());
			}else if(SEARCH_TYPE.AROUND.getCode().equalsIgnoreCase(type)){
				tc.setAround(searchCount);
				tc.setRoute(tc.getGroup()+tc.getFreetour()+tc.getAround()+tc.getFreelong());
			}else if(SEARCH_TYPE.GROUP.getCode().equalsIgnoreCase(type)){
				tc.setGroup(searchCount);
				tc.setRoute(tc.getGroup()+tc.getFreetour()+tc.getAround()+tc.getFreelong());
			}
			//保存查询日志
			log_content.append(this.fromDest).append("\t");//出发地
			log_content.append(searchvo.getKeyword().replaceAll("\t", " ")).append("\t");//关键词
			List<String> fv_list = new ArrayList<String>();
			for(Entry<String, Object> item : filedValueMap.entrySet()){
				if(!item.getKey().equals("fromDest") && !item.getKey().equals("keyword")){
					fv_list.add(item.getKey()+":"+item.getValue());
				}
			}
			log_content.append(fv_list.size() == 0 ? "null" : StringUtils.join(fv_list,";")).append("\t");//筛选条件
			log_content.append(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS")).append("\t");//查询结束时间
			log_content.append(old_type.toUpperCase()).append("\t");//查询类型
			if(useRecommendLogic){//使用查询推荐逻辑
				log_content.append(0).append("\t");//查询结果数
				log_content.append(type.toUpperCase()).append("\t");//推荐类型
				log_content.append(pageConfig.getTotalResultSize()).append("\t");//推荐查询结果数
			}else{//没有使用推荐逻辑
				log_content.append(pageConfig.getTotalResultSize()).append("\t");//查询结果数
				log_content.append("null").append("\t");//推荐类型
				log_content.append(0).append("\t");//推荐查询结果数
			}
			log_content.append("http://www.lvmama.com/search/"+old_type+"/"+params+".html").append("\t");//当前URL
			log_content.append(req.getHeader("User-Agent"));
			search_business_loger.info(log_content);
			//恢复原来的keyword值
			//清keywords缓存
//			LocalCacheManager.clear(this.searchvo.getKeyword());
			LocalSession.remove();
			this.searchvo.setKeyword(orikeyword);
		} catch (AccessURLException e){
			searchvo = new SearchVO();
			return "urlerror";
		}
		return result;
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
	* 根据来源不同显示不同的热线电话 
	* @return 
	*/ 
	public String getShowDifferntHotLine() { 
		String tele = this.getRequest().getParameter("tele"); 
		if (null != tele) { 
			Cookie cookie = new Cookie("tele", tele); 
			cookie.setDomain(".lvmama.com"); 
			cookie.setMaxAge(-1); 
			cookie.setPath("/"); 
			getResponse().addCookie(cookie); 
		} else { 
			tele =getCookieValue("tele"); 
		} 
		return tele; 
	} 
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public PageConfig getPageConfig() {
		return pageConfig;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getFromDest() {
		return this.fromDest;
	}
	public String getKeyword() {
		return this.keyword;
	}
	public String getEncodeKeyword() throws UnsupportedEncodingException {
		return URLEncoder.encode(this.keyword, "UTF-8");
	}
	public SearchVO getSearchvo() {
		return searchvo;
	}
	public String getFilterStr() {
		return filterStr;
	}
	public PlaceBean getPlace() {
		return place;
	}
	
	public TypeCount getTc() {
		return tc;
	}

	public List<ProductBean> getTuanGouList() {
		return tuanGouList;
	}
	public void setTuanGouList(List<ProductBean> tuanGouList) {
		this.tuanGouList = tuanGouList;
	}
	public void setSearchBusinessService(SearchBusinessService searchBusinessService) {
		this.searchBusinessService = searchBusinessService;
	}
	
	public void setTicketSearchService(TicketSearchService ticketSearchService) {
		this.ticketSearchService = ticketSearchService;
	}

	public void setHotelSearchService(HotelSearchService hotelSearchService) {
		this.hotelSearchService = hotelSearchService;
	}
	public void setRouteSearchService(RouteSearchService routeSearchService) {
		this.routeSearchService = routeSearchService;
	}
	public void setTuangouSearchService(TuangouSearchService tuangouSearchService) {
		this.tuangouSearchService = tuangouSearchService;
	}
	public void setSeoIndexPageService(SeoIndexPageService seoIndexPageService) {
		this.seoIndexPageService = seoIndexPageService;
	}
	public String getPageTitle() {
		return pageTitle;
	}
	public String getPageDescription() {
		return pageDescription;
	}
	public String getPageKeyword() {
		return pageKeyword;
	}
	public Map<String, String> getRelatedKeywords() {
		return relatedKeywords;
	}
	public void setComSearchTranscodeService(ComSearchTranscodeService comSearchTranscodeService) {
		this.comSearchTranscodeService = comSearchTranscodeService;
	}
	public boolean isUseRecommendLogic() {
		return useRecommendLogic;
	}
	public void setUseRecommendLogic(boolean useRecommendLogic) {
		this.useRecommendLogic = useRecommendLogic;
	}
	public void setKeywordAutoCompletService(
			KeywordAutoCompletService keywordAutoCompletService) {
		this.keywordAutoCompletService = keywordAutoCompletService;
	}
	public void setVerHotelSearchService(VerHotelSearchService verHotelSearchService) {
		this.verHotelSearchService = verHotelSearchService;
	}

	public void setSearchRecommendService(
			SearchRecommendService searchRecommendService) {
		this.searchRecommendService = searchRecommendService;
	}
	public List<ProductBean> getRecommendList() {
		return recommendList;
	}
	public void setRecommendList(List<ProductBean> recommendList) {
		this.recommendList = recommendList;
	}
	
	public String getParentID() {
		return parentID;
	}
	public void setParentID(String parentID) {
		this.parentID = parentID;
	}
	public String getHotel_longitude() {
		return hotel_longitude;
	}
	public void setHotel_longitude(String hotel_longitude) {
		this.hotel_longitude = hotel_longitude;
	}
	public String getHotel_latitude() {
		return hotel_latitude;
	}
	public void setHotel_latitude(String hotel_latitude) {
		this.hotel_latitude = hotel_latitude;
	}
	public String getHotelBrand() {
		return hotelBrand;
	}
	public void setHotelBrand(String hotelBrand) {
		this.hotelBrand = hotelBrand;
	}
	public String getHotel_place() {
		return hotel_place;
	}
	public void setHotel_place(String hotel_place) {
		this.hotel_place = hotel_place;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getLandMark() {
		return landMark;
	}
	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}
	public String getHotel_star() {
		return hotel_star;
	}
	public void setHotel_star(String hotel_star) {
		this.hotel_star = hotel_star;
	}


}
