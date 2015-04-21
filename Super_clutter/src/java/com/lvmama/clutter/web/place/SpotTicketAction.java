package com.lvmama.clutter.web.place;

import java.io.IOException;
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

import org.apache.axis.utils.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.json.JSONException;
import org.json.JSONObject;

import com.lvmama.clutter.model.MobileBranch;
import com.lvmama.clutter.model.MobileDest;
import com.lvmama.clutter.model.MobilePlace;
import com.lvmama.clutter.model.MobileProductTitle;
import com.lvmama.clutter.service.IBaiduActivityService;
import com.lvmama.clutter.service.IClientCommentService;
import com.lvmama.clutter.service.IClientOfflineCacheService;
import com.lvmama.clutter.service.IClientPlaceService;
import com.lvmama.clutter.service.IClientProductService;
import com.lvmama.clutter.service.IClientRecommendService;
import com.lvmama.clutter.service.IClientSearchService;
import com.lvmama.clutter.utils.BaiduActivityUtils;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.utils.JSONUtil;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.pet.po.mobile.City;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.StringUtil;

/**
 * 景点门票.
 * @author qinzubo
 *
 */
@Results({
	@Result(name="spotticket_index",location="/WEB-INF/pages/spotticket/spotticket_index.html",type="freemarker"),
	@Result(name="spotticket",location="/WEB-INF/pages/spotticket/spotticket.html",type="freemarker"),
	@Result(name="spotticket_search",location="/WEB-INF/pages/spotticket/spotticket_search.html",type="freemarker"),
	@Result(name="stnearby",location="/WEB-INF/pages/spotticket/stnearby.html",type="freemarker"),
	@Result(name="spotticket_ajax",location="/WEB-INF/pages/spotticket/spotticket_ajax.html",type="freemarker"),
	@Result(name="spotticket_comment_ajax",location="/WEB-INF/pages/spotticket/spotticket_comment_ajax.html",type="freemarker"),
	@Result(name="spotticket_detail",location="/WEB-INF/pages/spotticket/spotticket_detail.html",type="freemarker"),
	@Result(name="spotticket_iosdetail",location="/WEB-INF/pages/spotticket/spotticket_iosdetail.html",type="freemarker"),
	@Result(name="place_choose_city",location="/WEB-INF/pages/spotticket/spotticket_choose_fromDest.html",type="freemarker"),
	@Result(name="spotticket_no_found",location="/WEB-INF/pages/spotticket/spotticket_no_found.html",type="freemarker")
})
@Namespace("/mobile/spotticket")
public class SpotTicketAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	IClientProductService mobileProductService;
	
	/**
	 * 景点服务 
	 */
	IClientPlaceService mobilePlaceService;


	/**
	 * 
	 */
	IClientOfflineCacheService mobileOfflineCacheService;
	
	/**
	 * 点评服务
	 */
	IClientCommentService mobileCommentService;
	
	/**
	 * 搜索服务 
	 */
	IClientSearchService mobileSearchService;
	
	/**
	 * 推荐服务. 
	 */
	IClientRecommendService mobileRecommendService;
	
	/**
	 *百度活动服务 
	 */
	private IBaiduActivityService baiduActivityService;
	

	private int page = 1; // 第几页 
	private String sort;  // 排序
	private String toDest="上海"; // 目的地
	private String stage;  // 1:目的地（度假）；2：景区；3：酒店；
	private String subjects=" "; // 主题乐园','田园度假', '山水景观', '都市观光'.........
	private String keyword="" ; // 查询关键字 
	private String pageSize;//一页显示多少条
	private String productId; // 产品id 
	private String placeId;// 景点id 
	private boolean ajax;// 是否ajax 查询
	private int windage;
	
	private MobilePlace mobilePlace; // 景点 
	
	private String channelType;//判断是哪里来,CMS周边景点用到
	
	private String firstPost;//判断是否是第一次请求
	
	/**
	 *  景点门票首页. 
	 * @return 
	 */
	@Action("spotticket_index")
	public String spotticketIndex(){
		return "spotticket_index";
	} 
	
	/**
	 *  景点门票首页. 
	 * @return 
	 */
	@Action("spotticket")
	public String spotticket(){
		try {
			// 如果是ajax查询 
			if(ajax) {
				initDataList();
				return "spotticket_ajax";
			} else {
				// 查询列表信息
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("method", "api.com.cache.getPlaceCitiesCache");
				Map<String,Object> mlist = mobileOfflineCacheService.getPlaceCitiesCache(params);
				if(null != mlist) {
					getRequest().setAttribute("sortTypes", mlist.get("sortTypes")); // 排序 
					getRequest().setAttribute("province", mlist.get("province")); // 地区 
					getRequest().setAttribute("sublist", getSubjects(mlist)); // 主题类型
					getRequest().setAttribute("allSubject", mlist.get("allSubject")); // 所有主题类型
				}
				if(!StringUtils.isEmpty(keyword)) {
					keyword = URLDecoder.decode(keyword,"UTF-8");
				} 
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(StringUtils.isEmpty(keyword)) {
			return "spotticket";
		} else {
			return "spotticket_search";
		}
	} 
	
	
	/**
	 *  景点门票首页. 
	 * @return 
	 */
	@Action("listPlace")
	public String listPlace() throws Exception {
		HttpServletResponse response = this.getResponse();
		response.setCharacterEncoding("gb2312");
		Map<String,Object> param = new HashMap<String,Object>();
		if(StringUtil.isEmptyString(stage)){
			stage = "2";
		}
		param.put("stage",stage); // 默认stage=2； 
		if(!StringUtils.isEmpty(keyword)) {
			keyword = URLDecoder.decode(keyword,"UTF-8");
			param.put("keyword", keyword);
		} else {
			param.put("keyword", " ");
		}
		param.put("page", page);
		param.put("pageSize", pageSize);
		//Map<String,Object> map = new HashMap<String,Object>();
		// 景区 

		Page<PlaceBean> page = mobileSearchService.listPlace(param);
		List<PlaceBean> placeBeanList = page.getItems();
		if(null!=placeBeanList && placeBeanList.size()>0){

			List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
			for(PlaceBean placeBean:placeBeanList){
				Map<String,Object> dataMap = new HashMap<String, Object>();
				dataMap.put("City", placeBean.getCity());
				dataMap.put("Name", placeBean.getName());
				dataMap.put("Pic", "http://pic.lvmama.com/pics"+placeBean.getMiddleImage());
				dataMap.put("Price", placeBean.getMarketPrice());
				dataMap.put("Province", placeBean.getProvince());
				dataMap.put("Url", "http://m.lvmama.com/clutter/place/"+placeBean.getId());
				datas.add(dataMap);
			}
			PrintWriter out = response.getWriter();
			try {
				JSONObject json = new JSONObject();
				json.put("Items", datas);
				json.put("Version",  "1.0");
				json.put("SourceSiteName",  "驴妈妈旅游网");
				json.put("TotalCount", page.getTotalResultSize());
				json.put("TotalPageNum", page.getTotalPageNum());
				json.put("Page", page.getCurrentPage());
				out.println(json);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				out.flush();
				out.close();
			}
		}
		return null;
	}
	
	/**
	 * 周边景点 
	 * @return 
	 */
	@Action("stnearby")
	public String stnearby(){
		if(channelType!=null && channelType!="CMSSTNEAR"){//CMS周边景点
			return "stnearby";
		}
		// 默认景区. 
		if(StringUtil.isEmptyString(stage)){
			stage = "2";
		}
		try {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("stage", stage);
			toDest = URLDecoder.decode(toDest,"UTF-8");
			subjects = URLDecoder.decode(subjects,"UTF-8");
			if(!StringUtils.isEmpty(keyword)) {
				keyword = URLDecoder.decode(keyword,"UTF-8");
				param.put("keyword", keyword);
			} else {
				param.put("keyword", "".equals(toDest)?" ":toDest);
			}
			param.put("page", page);
			param.put("sort", sort);
			param.put("subject", subjects);
			param.put("toDest",toDest);
			param.put("longitude", mobilePlace.getBaiduLongitude());
			param.put("latitude", mobilePlace.getBaiduLatitude());
			param.put("windage", windage);
			Map<String,Object> map = new HashMap<String,Object>();
			// 景区 
			if("2".equals(stage)) {
				map = mobileSearchService.placeSearch(param);
			} else if("1".equals(stage)) {// 度假（自由行）
				map = mobileSearchService.routeSearch(param);
			}
			if(null != map && null != map.get("datas")) {
				getRequest().setAttribute("placelist", map.get("datas"));
				getRequest().setAttribute("isLastPage", map.get("isLastPage"));
				
			}
			
			// 设置图片前缀 
			this.setImagePrefix();
			firstPost="alreadyPost";//判断是否是第一次请求
			// 如果是ajax查询
			if(ajax) {
				return "spotticket_ajax";
			}
			
			// 查询列表信息
//			Map<String,Object> params = new HashMap<String,Object>();
//			params.put("method", "api.com.cache.getPlaceCitiesCache");
//			
//			Map<String,Object> mlist = mobileOfflineCacheService.getPlaceCitiesCache(params);
//			getRequest().setAttribute("mlist", mlist);
//			// 主题类型. toDest
//			List<String> subjects = getSubjects(mlist);
//			getRequest().setAttribute("sublist", subjects);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "stnearby";
	} 
	
	/**
	 * 获取 主题类型. 
	 * @param map
	 * @return
	 */
	public List<String> getSubjects(Map<String,Object> map){
		List<String> subjest = new ArrayList<String>();
			if(null != map && null != map.get("province")) {
				List<MobileDest> mdList = (List<MobileDest>) map.get("province");
				if(null != mdList && mdList.size() > 0){
					for(int i = 0; i < mdList.size() ;i++) {
						MobileDest md = mdList.get(i);
						List<MobileDest>subList = md.getCities();
						for(int j = 0; j < subList.size() ;j++) {
							MobileDest smd = subList.get(j);
							if(smd.getName().equals(toDest)) {
								return smd.getSubjects();
							}
						}
					}
				}
			}
		return subjest;
	}
	
	/**
	 * 景点详情
	 * @return
	 */
	@Action("spotticket_detail")
	public String spotticketDetail() {
		try {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("placeId", placeId);
			UserUser u = getUser();
			if(null != u) {
				param.put("userNo", u.getUserNo());
				param.put("userId", u.getId());
			}
			// 获取景点信息
			mobilePlace = mobilePlaceService.getPlace(param);
			
			if(null != mobilePlace && !StringUtils.isEmpty(mobilePlace.getName())) {
				mobilePlace.setName(ClientUtils.filterQuotationMarks(mobilePlace.getName()));
			}
			
			//百度活动产品--立减/半价
			List<ProdBranchSearchInfo> baiduGoodsList=baiduActivityService.getActivityBranches(Long.valueOf(placeId));
			
			if(baiduGoodsList!=null && baiduGoodsList.size()>0){ 
				List<MobileBranch> tempList = new ArrayList<MobileBranch>();
				
				for (ProdBranchSearchInfo o : baiduGoodsList) {
					MobileBranch mb = ClientUtils.copyTicketBranch(o,new ProductSearchInfo());//对象转换
					
					mb.setFullName(o.getProductName());
					mb.setIcon(BaiduActivityUtils.ticketType(String.valueOf(mb.getProductId())));
					mb.setMarketPriceYuan(mb.getSellPriceYuan());
					mb.setSellPriceYuan(BaiduActivityUtils.getProductPrice(String.valueOf(mb.getProductId()),mb.getSellPriceYuan()));
					tempList.add(mb);
				}
				
				List<MobileBranch> b=getProdBranchList(tempList);
				getRequest().setAttribute("baiduGoodsList", b); // 百度
			}
			
			
			// 获取景点对应的产品信息
			initProductInfo(param);
			// 获取自由行信息 
			initProductTitle(param);
			// 初始化点评信息 
			initPlaceComment(param);
			// 设置图片前缀 
			this.setImagePrefix();
			
			return "spotticket_detail";
		}catch(Exception e){
			e.printStackTrace();
			return "spotticket_no_found";
		}
	}
	/**
	 * 景点详情--百度IOS 专用
	 * @return
	 */
	@Action("spotticket_iosdetail")
	public String spotticketIosDetail() {
		try {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("placeId", placeId);
			UserUser u = getUser();
			if(null != u) {
				param.put("userNo", u.getUserNo());
				param.put("userId", u.getId());
			}
			// 获取景点信息
			mobilePlace = mobilePlaceService.getPlace(param);
			
			if(null != mobilePlace && !StringUtils.isEmpty(mobilePlace.getName())) {
				mobilePlace.setName(ClientUtils.filterQuotationMarks(mobilePlace.getName()));
			}
			
			//百度活动产品--立减/半价
			List<ProdBranchSearchInfo> baiduGoodsList=baiduActivityService.getActivityBranches(Long.valueOf(placeId));
			
			if(baiduGoodsList!=null && baiduGoodsList.size()>0){ 
				List<MobileBranch> tempList = new ArrayList<MobileBranch>();
				
				for (ProdBranchSearchInfo o : baiduGoodsList) {
					MobileBranch mb = ClientUtils.copyTicketBranch(o,new ProductSearchInfo());//对象转换
					
					mb.setFullName(o.getProductName());
					mb.setIcon(BaiduActivityUtils.ticketType(String.valueOf(mb.getProductId())));
					mb.setMarketPriceYuan(mb.getSellPriceYuan());
					mb.setSellPriceYuan(BaiduActivityUtils.getProductPrice(String.valueOf(mb.getProductId()),mb.getSellPriceYuan()));
					tempList.add(mb);
				}
				
				List<MobileBranch> b =new ArrayList<MobileBranch>();
				for(MobileBranch o : tempList){
					if("2".equals(o.getIcon())){
						b.add(o);
					}
				}
				getRequest().setAttribute("baiduGoodsList", b); // 百度
			}
			
			
			// 获取景点对应的产品信息
			initProductInfo(param);
			// 获取自由行信息 
			initProductTitle(param);
			// 初始化点评信息 
			initPlaceComment(param);
			// 设置图片前缀 
			this.setImagePrefix();
			
			return "spotticket_iosdetail";
		}catch(Exception e){
			e.printStackTrace();
			return "spotticket_no_found";
		}
	}
	/**
	 * 处理半价/立减排序
	 * @param baiduGoodsList
	 * @return
	 */
	public List<MobileBranch> getProdBranchList(List<MobileBranch> baiduGoodsList){
		List<MobileBranch> b =new ArrayList<MobileBranch>();
		for(MobileBranch o : baiduGoodsList){
			if("1".equals(o.getIcon())){
				b.add(o);
			}
		}
		for(MobileBranch o : baiduGoodsList){
			if("2".equals(o.getIcon())){
				b.add(o);
			}
		}
		return b;
	}
	/**
	 * 初始化产品信息 ，
	 * @param param placeId必填 
	 */
	public void initProductInfo(Map<String,Object> param) {
		// 获取景点对应的产品信息
		Map<String, Object> productMap = mobileProductService.getBranches(param);
		if(null != productMap) {
			getRequest().setAttribute("singleList", productMap.get("single")); // 门票
			getRequest().setAttribute("unionList", productMap.get("union")); // 联票
			getRequest().setAttribute("suitList", productMap.get("suit")); // 套票
		}
	}
	
	/**
	 * 初始化自由行信息 ，
	 * @param param placeId必填 
	 */
	public void initProductTitle(Map<String,Object> param) {
		// 获取景点对应的产品信息
		List<MobileProductTitle> titlList = mobileProductService.getPlaceRoutes(param);
		if(null != titlList) {
			getRequest().setAttribute("titlList", titlList);
		}
	}
	
	/**
	 * 评论列表数据.
	 * @return
	 */
	@Action("spotticket_comment_ajax")
	public String spotticketCommentAjax() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("page", page);
		param.put("placeId", placeId);
		Map<String, Object>  map = mobileCommentService.getPlaceComment(param);
		if(null != map && null != map.get("datas")) {
			getRequest().setAttribute("commentList",  map.get("datas"));
			getRequest().setAttribute("isLastPage",  map.get("isLastPage"));
		}
		return "spotticket_comment_ajax";
	}
	
	/**
	 * 选择出发地
	 */
	@Action("place_choose_city")
	public String chooseFromDest() {
		Map<String,Object> param = new HashMap<String,Object>();
		try {
			// 出发地城市 getDepaturePlace
			param.put("method", "api.com.recommend.getDepaturePlace");
			//param.put("blockType", "3"); // 度假
			Map<String,Object> resultMap = mobileRecommendService.getDepaturePlace(param);
			if(null != resultMap && null != resultMap.get("datas")){
				List<Map<String,Object>> blockMapList =  (List<Map<String, Object>>) resultMap.get("datas");
				if(null != blockMapList && blockMapList.size() > 0) {
					for(Map<String,Object> m:blockMapList) {
						if(null != m && null != m.get("blockType") && "3".equals(m.get("blockType").toString())) {
							getRequest().setAttribute("cityList",m.get("data"));
							getRequest().setAttribute("cityListJson",JSONArray.fromObject(m.get("data")));
							if(null != m.get("data")) {
								Set<String> slist = getChars((List<Map<String,Object>>) m.get("data"));
								getRequest().setAttribute("charList",slist);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "place_choose_city";
	}
	
	/**
	 * 获取pinyin首字母 
	 * @param mList
	 * @return
	 */
	public Set<String> getChars(List<Map<String,Object>>  mList) {
		Map<String,Object> t_m =  new HashMap<String,Object>();
		for(Map<String,Object> m:mList) {
			if(null != m && null != m.get("pinyin")) {
				String str = m.get("pinyin").toString().substring(0,1);
				t_m.put(str, str);
			}
		}
		return new TreeSet(t_m.keySet());
	}
	
	/**
	 * 景点点评列表 . 
	 * @param param  placeId ,page  
	 */
	public void initPlaceComment(Map<String,Object> param){
		param.put("page", page);
		Map<String, Object>  map = mobileCommentService.getPlaceComment(param);
		if(null != map && null != map.get("datas")) {
			getRequest().setAttribute("commentList",  map.get("datas"));
			getRequest().setAttribute("isLastPage",  map.get("isLastPage"));
		}
	}
	
	/**
	 * 初始化查询数据 
	 * @throws UnsupportedEncodingException
	 */
	public void initDataList() throws UnsupportedEncodingException {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("stage", StringUtil.isEmptyString(stage)?"2":stage); // 默认stage=2； 
			toDest = URLDecoder.decode(toDest,"UTF-8");
			subjects = URLDecoder.decode(subjects,"UTF-8");
			if(!StringUtils.isEmpty(keyword)) {
				keyword = URLDecoder.decode(keyword,"UTF-8");
				param.put("keyword", keyword);
			} else {
				param.put("keyword", "".equals(toDest)?" ":toDest);
			}
			param.put("page", page);
			param.put("sort", sort);
			param.put("subject", subjects);
			param.put("toDest",toDest);
			Map<String,Object> map = new HashMap<String,Object>();
			// 景区 
			if("2".equals(stage)) {
				map = mobileSearchService.placeSearch(param);
			} else if("1".equals(stage)) {// 度假（自由行）
				map = mobileSearchService.routeSearch(param);
			}
			if(null != map && null != map.get("datas")) {
				getRequest().setAttribute("placelist", map.get("datas"));
				getRequest().setAttribute("isLastPage", map.get("isLastPage"));
			}
			// 设置图片前缀 
			this.setImagePrefix();
	}
	
	
	public void initSelectList() {
		
	}
	
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getToDest() {
		return toDest;
	}

	public void setToDest(String toDest) {
		this.toDest = toDest;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public MobilePlace getMobilePlace() {
		return mobilePlace;
	}

	public void setMobilePlace(MobilePlace mobilePlace) {
		this.mobilePlace = mobilePlace;
	}

    public String getSubjects() {
		return subjects;
	}

	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}
	
	public boolean isAjax() {
		return ajax;
	}

	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}

	public int getWindage() {
		return windage;
	}

	public void setWindage(int windage) {
		this.windage = windage;
	}
	
	public void setMobileProductService(IClientProductService mobileProductService) {
		this.mobileProductService = mobileProductService;
	}

	public void setMobilePlaceService(IClientPlaceService mobilePlaceService) {
		this.mobilePlaceService = mobilePlaceService;
	}

	public void setMobileOfflineCacheService(
			IClientOfflineCacheService mobileOfflineCacheService) {
		this.mobileOfflineCacheService = mobileOfflineCacheService;
	}

	public void setMobileCommentService(IClientCommentService mobileCommentService) {
		this.mobileCommentService = mobileCommentService;
	}

	public void setMobileSearchService(IClientSearchService mobileSearchService) {
		this.mobileSearchService = mobileSearchService;
	}

	public void setMobileRecommendService(
			IClientRecommendService mobileRecommendService) {
		this.mobileRecommendService = mobileRecommendService;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getFirstPost() {
		return firstPost;
	}

	public void setFirstPost(String firstPost) {
		this.firstPost = firstPost;
	}

	public IBaiduActivityService getBaiduActivityService() {
		return baiduActivityService;
	}

	public void setBaiduActivityService(IBaiduActivityService baiduActivityService) {
		this.baiduActivityService = baiduActivityService;
	}
	
}
