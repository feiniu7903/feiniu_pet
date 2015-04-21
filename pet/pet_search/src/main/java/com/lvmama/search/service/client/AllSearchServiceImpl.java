package com.lvmama.search.service.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.Query;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.lvmama.comm.hessian.HessianService;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.search.ComSearchTranscodeService;
import com.lvmama.comm.pet.service.seo.SeoIndexPageService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.SearchConstants.SEARCH_TYPE;
import com.lvmama.comm.search.service.AllSearchService;
import com.lvmama.comm.search.service.ClientProductService;
import com.lvmama.comm.search.service.TuangouSearchService;
import com.lvmama.comm.search.vo.AutoCompletePlaceObject;
import com.lvmama.comm.search.vo.ClientRouteSearchVO;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.RouteSearchVO;
import com.lvmama.comm.search.vo.SearchVO;
import com.lvmama.comm.search.vo.TypeCount;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.search.action.web.OneSearchAction;
import com.lvmama.search.action.web.SearchAction;
import com.lvmama.search.lucene.document.ProductDocument;
import com.lvmama.search.lucene.query.NewRouteQueryUtil;
import com.lvmama.search.lucene.query.QueryUtilForClient;
import com.lvmama.search.lucene.service.autocomplete.AutoCompleteService;
import com.lvmama.search.lucene.service.search.NewBaseSearchService;
import com.lvmama.search.service.HotelSearchService;
import com.lvmama.search.service.RouteSearchService;
import com.lvmama.search.service.SearchBusinessService;
import com.lvmama.search.service.TicketSearchService;
import com.lvmama.search.synonyms.LikeHashMap;
import com.lvmama.search.synonyms.LocalSession;
import com.lvmama.search.util.AccessURLException;
import com.lvmama.search.util.CommonUtil;
import com.lvmama.search.util.LocalCacheManager;
import com.lvmama.search.util.LoggerParms;
import com.lvmama.search.util.PageConfig;
import com.lvmama.search.util.SORT;
import com.lvmama.search.util.SearchStringUtil;

@HessianService("allSearchService")
@Service("allSearchService")
public class AllSearchServiceImpl  implements AllSearchService  {
	
	
	@Resource
	private TicketSearchService ticketSearchService;
	@Resource
	private HotelSearchService hotelSearchService;
	@Resource
	private RouteSearchService freelongSearchService;
	@Resource
	private RouteSearchService groupRouteSearchService;
	@Resource
	private RouteSearchService freetourSearchService;
	@Resource
	private RouteSearchService aroundRouteSearchService;
	
	@Resource
	protected SearchBusinessService searchBusinessService;
	protected RouteSearchService routeSearchService;
	@Resource
	protected NewBaseSearchService newProductSearchService;
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

	protected HttpSession getSession() {
		if(ServletActionContext.getRequest()!=null){
			return ServletActionContext.getRequest().getSession();
		}
		return null;
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	@Override
	public Map getAllSearch(Map<String,String> map) {
		
		HttpServletRequest req = getRequest();
		HttpServletResponse resp = getResponse();
		req.setAttribute("isverify", 1);
		String dest=map.get("dest");
		String keyword=map.get("keyword");
		params=dest+"-"+keyword;
		Map returnMap=null;
		try{
			StringBuffer log_c = new StringBuffer("search type:");
			log_c.append(type);
			log_c.append(" params:").append(params);
			
			this.parseParams(); 	//解析查询参数,分解出fromDest,keyword,filterStr
			
			//-----------------------转码-------------------------
			String trans_code_from_dest = CommonUtil.transcodeParams(this.request_fromDest);
			if(SearchConstants.FROMDESTS.getCode(trans_code_from_dest) == null){//错误的出发地,默认使用上海
				trans_code_from_dest = "上海";
				this.fromDest = "79";
			}
			if(this.searchvo==null){
				this.searchvo=new SearchVO();
			}
			this.searchvo.setFromDest(trans_code_from_dest);//出发地
		
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
			this.searchvo.setKeyword(transCode_keyword);
			this.filedValueMap.put("keyword", transCode_keyword);
			this.filedValueMap.put("fromDest", trans_code_from_dest);
			
			//查询所有栏目的统计数
			tc = searchBusinessService.getTypeCount(req, resp, trans_code_from_dest, transCode_keyword, orikeyword);
			List routelist=null;
			RouteSearchVO routeSearchVO = new RouteSearchVO();
			routeSearchVO.setKeyword(searchvo.getKeyword());
			routeSearchVO.setFromDest(searchvo.getFromDest());
			routeSearchVO.setOrikeyword(searchvo.getOrikeyword());
			Map<String, String> params = new HashMap<String, String>();
			if (tc.getGroup() > 0 || tc.getFreelong() > 0 ) {
				/**
				 * 先查各个栏目统计数，判断GROUP+FREELONG 有结 果,则查询GROUP+FREELONG栏目，聚合沉底
				 * **/
				
				params.put(ProductDocument.TO_DEST, routeSearchVO.getKeyword().trim());
				params.put(ProductDocument.SUB_PRODUCT_TYPE, "FREENESS_FOREIGN,FREENESS_LONG,GROUP_LONG,GROUP_FOREIGN,GROUP,SELFHELP_BUS");
				
				if(!StringUtil.isEmptyString(routeSearchVO.getProductName())){
					// 对线路产品的名称进行搜索,支持多个以空格分词的关键字复合搜索,用数组存储,如果数据第一个值不为空，则至少有一个值
					params.put("poductNameSearchKeywords", routeSearchVO.getProductName().trim());
				}
				Query query = NewRouteQueryUtil.getGroupFreelongQuery(params);
				routelist= newProductSearchService.search(query,routeSearchVO, sorts);

			}

			/**
			 * 如果没有则查询FREETOUR+AROUND 返回结果,不聚合沉底
			 * **/
			else if (tc.getFreetour() > 0 && tc.getAround() > 0) {
				params.put("keyword",routeSearchVO.getKeyword().trim());
				params.put(ProductDocument.FROM_DEST, routeSearchVO.getFromDest());
				params.put(ProductDocument.SUB_PRODUCT_TYPE, "FREETOUR,AROUND");
				if(!StringUtil.isEmptyString(routeSearchVO.getProductName())){
					// 对线路产品的名称进行搜索,支持多个以空格分词的关键字复合搜索,用数组存储,如果数据第一个值不为空，则至少有一个值
					params.put("poductNameSearchKeywords", routeSearchVO.getProductName().trim());
				}
				params.put("isKeywordIsFromDest", tc.isKeywordIsFromDest()+"");
				Query query = NewRouteQueryUtil.getFreetourAroundWithFromDestQuery(params);
				routelist = newProductSearchService.search(query,routeSearchVO, sorts);

			}else if(tc.getFreetour() > 0){
				PageConfig cfg= freetourSearchService.search(routeSearchVO, sorts);
				routelist=cfg.getAllItems();
			}else if(tc.getAround() > 0){
				PageConfig cfg= aroundRouteSearchService.search(routeSearchVO, sorts);
				routelist=cfg.getAllItems();
			}
			 returnMap=tc.getMap();
			returnMap.put("routelist", routelist);
		} catch (AccessURLException e){
			searchvo = new SearchVO();
		}
		return returnMap;
	}

	
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
			throw new AccessURLException("查询参数异常.");
		}
		if(StringUtil.isEmptyString(this.keyword)){
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


}
