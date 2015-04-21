package com.lvmama.front.web.comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.comment.CmtLatitudeStatistics;
import com.lvmama.comm.pet.po.comment.DicCommentLatitude;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.po.seo.SeoIndexPage;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.pet.service.seo.SeoIndexPageService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.pet.vo.ProductList;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StackOverFlowUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtSpecialSubjectVO;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;


@Results({
	@Result(name = "cmtList", location = "/WEB-INF/pages/comment/dest/list.ftl", type = "freemarker"),
	@Result(name = "cmtScenicList", location = "/WEB-INF/pages/comment/dest/scenicList.ftl", type = "freemarker")
})
public class ListCmtsOfDestBasicInfoAction extends CmtBaseAction {
	/**
	 * 序列
	 */
	private static final long serialVersionUID = 812860578845846006L;
	
	final Log log = LogFactory.getLog(getClass());

	private String cmtPage;
	private ProductList productList;
	private String id;
	
	private String type;
	
	private String page;
	
	private Place place;
//	private SeoFooter seoFooter;
	private Long placeId;
	/**
	 * 点评维度列表
	 */
	private List<DicCommentLatitude> commentLatitudeList;

	/**
	 * 获取景点的点评统计信息
	 */
	private CmtTitleStatisticsVO cmtCommentStatisticsVO;

	/**
	 * 每页记录数
	 */
	private long pageSize = 10;
	/**
	 * 获取景点的所有点评(扩展实体)
	 */
	private List<CommonCmtCommentVO> cmtCommentVOList;
	/**
	 * 分页配置信息
	 */
	private Page<CommonCmtCommentVO> pageConfig;

	/**
	 * 是否显示分页
	 */
	private String pageFlag = "Y";
//	private PlaceTimingTimeNote timeNote;
	
	/**
	 * 维度平均分统计
	 */
	private List<CmtLatitudeStatistics> cmtLatitudeStatisticsList;
	/**
	 * 当前显示的tab
	 */
	private String currentTab;
	/**
	 * 专题
	 */
	private List<CmtSpecialSubjectVO> cmtSpecialSubjectList = new ArrayList<CmtSpecialSubjectVO>();

	private SeoIndexPageService seoService;
	//页面导航
	private Map<String,Object> navigationMap;
	
	
	private String pinyin;
	
	private ProductSearchInfoService productSearchInfoService;
	
	private List<Place> recommendPlaceList; 
	
	/**
	 * 所有点评数量不用统计数据
	 */
	private long allCmtRecords;
	/**
	 * 临时存放travel相关内容，以后要移到PHP里
	 * @return
	 */
	@Action("/comment/listCmtsOfDestBasicTravel")
	public String cmtTravelList()
	{
		this.place = placeService.getPlaceByPinYin(pinyin);
		if(this.place != null)
		{
			this.id = String.valueOf(place.getPlaceId());
			return cmtList();
		}
		else
		{
			return ERROR_PAGE;
		}
		 
	}
	
	
	
	@Action("/comment/listCmtsOfDestBasic")
	public String cmtList()
	{
		try {
			if (id == null) {
				return ERROR_PAGE;
				//this.place = placeService.getPlaceById(Long.valueOf(id));
			} else {
				if(StringUtils.isNotEmpty(id)&&id.length()<10){
					this.place = placeService.queryPlaceByPlaceId(Long.parseLong(id));
					PlaceSearchInfo placeSearchInfo = placeSearchInfoService.getPlaceSearchInfoByPlaceId(Long.parseLong(id));
					if(placeSearchInfo != null)
					{
						this.place.setLargeImage(placeSearchInfo.getLargeImage());
					}
				}else {
					return ERROR_PAGE;
				}
			}
 			if(this.place==null){
				return ERROR_PAGE;
			}else{
				this.placeId = place.getPlaceId();
//				this.seoFooter=seoService.getSeoContentByDestId(place.getPlaceId());
//				this.timeNote = placeService.getPlacetimingtimenoteByPlaceId(this.place.getPlaceId());
				if(StringUtils.isEmpty(type)){
					type="1";
				}
				if(StringUtils.isEmpty(page)){
					page="1";
				}
				
				this.getCommentList(place.getPlaceId(), type, page);
				
				//获得某个景点的点评维度统计平均分
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("placeId", placeId);
				this.cmtLatitudeStatisticsList = cmtLatitudeStatistisService.getFourAvgLatitudeScoreList(params);
				
				//获取右侧专题
				//this.cmtSpecialSubjectList = getIndexPageOfCmtSpecialSubjectList();
				
				//获取右侧推荐产品
				this.productList = this.productSearchInfoService.getProductByPlaceIdAnd4Type(placeId, 5, "FRONTEND");
				
				//添加页面导航
				setNavigation(place.getPlaceId());
				
				Map<String, Object> parameter =  new HashMap<String, Object>();
				parameter.put("placeId", placeId);
				parameter.put("isAudit",  Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name());
				parameter.put("cmtType", "COMMON");
				long CommentsTotalRecords = cmtCommentService.getCommentTotalCount(parameter);	
				
				parameter.remove("cmtType");
				parameter.put("cmtType",  Constant.EXPERIENCE_COMMENT_TYPE);
				long experienceCmtsRecords = cmtCommentService.getCommentTotalCount(parameter);	
				allCmtRecords=CommentsTotalRecords+experienceCmtsRecords;
				
				if(place.getStage().equals("1"))
				{
					  recommendPlaceList = placeService
							.getPlaceInfoBySameParentPlaceId(
									place.getPlaceId(),
									Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode(),
									3L,place.getPlaceId().toString());
					return "cmtList"; //目的地页
				}
				else {
					recommendPlaceList = placeService
							.getPlaceInfoBySameParentPlaceId(
									place.getParentPlaceId(),
									Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode(),
									3L,place.getPlaceId().toString());
					return "cmtScenicList";//景点和酒店页
				}
			}
		} catch (Exception e) {
			log.error("error CmtAction.cmtList id="+id+" Referer url:"+this.getRequest().getHeader("Referer")+" url:"+this.getRequest().getRequestURI());
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
		}
		return ERROR_PAGE;
	}
	
	
	private void getCommentList(Long placeId, String type, String page){

		//景区点评纬度列表
		commentLatitudeList = dicCommentLatitudeService.getDicCommentLatitudeListBySubject(place);		
		
		//获取该景点的点评统计信息
		cmtCommentStatisticsVO = cmtTitleStatistisService.getCmtTitleStatisticsByPlaceId(placeId);
		
		if(null != cmtCommentStatisticsVO)
		{
			cmtCommentStatisticsVO = composeCmtTitleStatistics(cmtCommentStatisticsVO);
		}

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("placeId", placeId);
		parameters.put("getPlaceCmts", "Y");  //取景点点评
		parameters.put("isAudit",  Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name());
		Long totalRecords = cmtCommentService.getCommentTotalCount(parameters);	
		pageConfig = Page.page(totalRecords, pageSize, Long.valueOf(page));
		
		parameters.put("_startRow", (pageConfig.getStartRows()) + "");
		parameters.put("_endRow", pageConfig.getEndRows() + "");
		parameters.put("createTime321", "true");
		cmtCommentVOList = cmtCommentService.getCmtCommentList(parameters);
		pageConfig.setItems(cmtCommentVOList);
		if (pageConfig.getItems().size() > 0) {
			this.pageConfig.setUrl("/dest/"+place.getPinYinUrl()+"/comment_"+ place.getStage() +"_");
		}
		//获取点评发布的图片(暂取消该功能)
		//cmtCommentService.fillApprovedPicture(cmtCommentVOList);
		
	}
	
	/**
	 * 添加页面导航
	 * @param placeId
	 */
	private void setNavigation(Long placeId) {
		String key = "loadNavigation_" + placeId;
		navigationMap = (Map<String, Object>) MemcachedUtil.getInstance().get(key);
		if (navigationMap == null) {
			navigationMap = placeService.loadNavigation(placeId);
			MemcachedUtil.getInstance().set(key,
					MemcachedUtil.getDateAfter(60 * 60 * 24), navigationMap);
		}
	}
	
	
	/**
	 *  ------------------------------------  get and set property -------------------------------------------
	 */
	
	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCmtPage() {
		return cmtPage;
	}

	public void setCmtPage(String cmtPage) {
		this.cmtPage = cmtPage;
	}

//	public PlaceTimingTimeNote getTimeNote() {
//		return timeNote;
//	}
//
//	public void setTimeNote(PlaceTimingTimeNote timeNote) {
//		this.timeNote = timeNote;
//	}
	  
	public ProductList getProductList() {
		return productList;
	}

//	public SeoFooter getSeoFooter() {
//		return seoFooter;
//	}
//
//	public void setSeoFooter(SeoFooter seoFooter) {
//		this.seoFooter = seoFooter;
//	}

	public SeoIndexPageService getSeoService() {
		return seoService;
	}

	public void setSeoService(SeoIndexPageService seoService) {
		this.seoService = seoService;
	}


	public long getPageSize() {
		return pageSize;
	}


	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}


	public List<DicCommentLatitude> getCommentLatitudeList() {
		return commentLatitudeList;
	}


	public CmtTitleStatisticsVO getCmtCommentStatisticsVO() {
		return cmtCommentStatisticsVO;
	}


	public List<CommonCmtCommentVO> getCmtCommentVOList() {
		return cmtCommentVOList;
	}


	public Page<CommonCmtCommentVO> getPageConfig() {
		return pageConfig;
	}


	public String getPageFlag() {
		return pageFlag;
	}


	public Long getPlaceId() {
		return placeId;
	}


	/**
	 * @param cmtLatitudeStatisticsList the cmtLatitudeStatisticsList to set
	 */
	public void setCmtLatitudeStatisticsList(
			List<CmtLatitudeStatistics> cmtLatitudeStatisticsList) {
		this.cmtLatitudeStatisticsList = cmtLatitudeStatisticsList;
	}


	/**
	 * @return the cmtLatitudeStatisticsList
	 */
	public List<CmtLatitudeStatistics> getCmtLatitudeStatisticsList() {
		return cmtLatitudeStatisticsList;
	}

	public String getCurrentTab() {
		return currentTab;
	}

	public void setCurrentTab(String currentTab) {
		this.currentTab = currentTab;
	}
	
	/**
	 * 获取驴友热评信息
	 * @return
	 */
	public List<CmtSpecialSubjectVO> getCmtSpecialSubjectList() {
		return this.cmtSpecialSubjectList;
	}

	public Map<String, Object> getNavigationMap() {
		return navigationMap;
	}



	public String getPinyin() {
		return pinyin;
	}



	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}


	public void setProductSearchInfoService(ProductSearchInfoService productSearchInfoService) {
		this.productSearchInfoService = productSearchInfoService;
	}



	/**
	 * @return the recommendPlaceList
	 */
	public List<Place> getRecommendPlaceList() {
		return recommendPlaceList;
	}



	/**
	 * @param recommendPlaceList the recommendPlaceList to set
	 */
	public void setRecommendPlaceList(List<Place> recommendPlaceList) {
		this.recommendPlaceList = recommendPlaceList;
	}
	
	public long getAllCmtRecords() {
		return allCmtRecords;
	}
	
}
