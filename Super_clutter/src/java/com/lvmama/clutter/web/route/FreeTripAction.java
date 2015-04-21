package com.lvmama.clutter.web.route;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.clutter.model.MobileProductRoute;
import com.lvmama.clutter.service.IClientCommentService;
import com.lvmama.clutter.service.IClientOfflineCacheService;
import com.lvmama.clutter.service.IClientProductService;
import com.lvmama.clutter.service.IClientRecommendService;
import com.lvmama.clutter.service.IClientSearchService;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComPictureService;

/**
 * 自由行. 
 * @author qinzubo
 */
@Results({ 
	@Result(name="freetrip_index",location="/WEB-INF/pages/freetrip/freetrip.html",type="freemarker"),
	@Result(name="freetrip_list",location="/WEB-INF/pages/freetrip/freetrip_list.html",type="freemarker"),
	@Result(name="comment_comm",location="/WEB-INF/pages/common/comment_comm.html",type="freemarker"),
	@Result(name="prod_comm",location="/WEB-INF/pages/common/prod_comm.html",type="freemarker"),
	@Result(name="spotticket_comment_ajax",location="/WEB-INF/pages/spotticket/spotticket_comment_ajax.html",type="freemarker"),
	@Result(name="freetrip_ajax",location="/WEB-INF/pages/freetrip/freetrip_ajax.html",type="freemarker"),
	@Result(name="freetrip_detail",location="/WEB-INF/pages/freetrip/freetrip_detail.html",type="freemarker")
})
@Namespace("/mobile/freetrip")
public class FreeTripAction extends BaseAction {
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
	
	/**
	 * 点评服务
	 */
	IClientCommentService mobileCommentService;


	/**
	 * 目的地服务
	 */
	protected PlaceService placeService;
	private PageService pageService;
	protected ComPictureService comPictureService;
	private ProdProductService prodProductService;

	private String productId; // 产品id 
    private String toDest;// 目的地
    private String subProductType;// 
    private String subject;// 主题类型
	private String sort = "seq";// 排序 
    
	private int page = 1; // 第几页 ; pageSize每页显示多少行
	private boolean ajax;// 是否ajax 查询
	
	private String commType; // 

	/**
	 * freetrip_index
	 */
	@Action("freetrip")
	public String index() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("method", "api.com.recommend.getCitiesArea");
		Map<String,Object> map = mobileRecommendService.getCitiesArea(params);
		if(null != map && null != map.get("datas")) {
			List<Map<String,Object>> recommendMapList = (List<Map<String,Object>>)map.get("datas");
			if(null != recommendMapList && recommendMapList.size() > 0) {
				for(Map<String,Object> m:recommendMapList) {
					if(null != m && null != m.get("data")) {
						getRequest().setAttribute("name"+m.get("id"), m.get("name"));
						getRequest().setAttribute("list"+m.get("id"), m.get("data"));
					}
				}
			}
		}
		return "freetrip_index";
	}
	
	//自由行列表 .
	@Action("freetrip_list")
	public String freetripList() {
		// 设置图片前缀 
		this.setImagePrefix();
		Map<String,Object> param = new HashMap<String,Object>();
		try {
			initParams(param);
			Map<String,Object> resultMap = mobileSearchService.routeSearch(param);
			if(null != resultMap ){ // List<MobileProductTitle> mpList
				getRequest().setAttribute("mobileProductList", resultMap.get("datas"));
				getRequest().setAttribute("subjects", resultMap.get("subjects"));
				getRequest().setAttribute("isLastPage", resultMap.get("isLastPage"));
			}
			
			if(ajax) {
				return "freetrip_ajax";
			}
			
			Map<String,Object> cacheMap = mobileOfflineCacheService.getRouteFilterCache(new HashMap<String,Object>());
			if(null != cacheMap) {
				//getRequest().setAttribute("subjects", cacheMap.get("subjects"));
				getRequest().setAttribute("sortTypes", cacheMap.get("sortTypes"));
				getRequest().setAttribute("routeTypes", cacheMap.get("routeTypes"));
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "freetrip_list";
	}
	
	//自由行详情 .
	@Action("freetrip_detail")
	public String freetripDetail() {
		Map<String,Object> param = new HashMap<String,Object>();
		try {
			param.put("productId", Long.valueOf(productId));
			UserUser u = getUser();
			if(null != u) {
				param.put("userNo", u.getUserNo());
				param.put("userId", u.getId());
			}
			MobileProductRoute mpr = mobileProductService.getRouteDetail(param);
			String productNameTitle="";
			if(null != mpr && !StringUtils.isEmpty(mpr.getProductName())) {
				mpr.setProductName(ClientUtils.filterQuotationMarks(mpr.getProductName()));
				if(mpr.getProductName().length()>30){
					productNameTitle=getProductTitle(mpr.getProductName());
				}else{
					productNameTitle=mpr.getProductName();
				}
			}
			Map<String,Object> data = pageService.getProdCProductInfo(Long.valueOf(productId),false);
			if(null != data ) {
				// 行程说明
				if(data.get("viewJourneyList") != null) {
					 List<ViewJourney> viewJourneyList = (List<ViewJourney>)data.get("viewJourneyList");
					for (ViewJourney vj : viewJourneyList) {
						vj.setJourneyPictureList(comPictureService.getPictureByObjectIdAndType(vj.getJourneyId(), "VIEW_JOURNEY"));
					}
					getRequest().setAttribute("viewJourneyList", viewJourneyList);
				}
			}
			getRequest().setAttribute("mpr", mpr);
			getRequest().setAttribute("productNameTitle", productNameTitle);
			// 设置图片前缀 
			this.setImagePrefix();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "freetrip_detail";
	}
	/**
	 * 产品名在页面TITLE限制30字
	 * @param productName
	 * @return
	 */
	public String getProductTitle(String productName){
		String ss=productName.substring(0, 30);
		return ss;
	}
	/**
	 * 线路评论详情页
	 * @return 
	 */
	@Action("comment_comm")
	public String commentComm() {
		try {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("productId", productId);
			//MobileProduct mobileProduct = api_com_product.getProduct(param);
			MobileProductRoute mpr = mobileProductService.getRouteDetail(param);
			getRequest().setAttribute("mobileProduct",  mpr);
			
			param.put("page", page);
			Map<String, Object>  map = mobileCommentService.getProductComment(param);
			if(null != map && null != map.get("datas")) {
				getRequest().setAttribute("commentList",  map.get("datas"));
				getRequest().setAttribute("isLastPage",  map.get("isLastPage"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(ajax) {
			return "spotticket_comment_ajax";
		}
		
		return "comment_comm";
	}
	
	
	/**
	 * 线路详情页
	 * @return 
	 */
	@Action("prod_comm")
	public String prodComm() {
		try{
			// Constant.VIEW_CONTENT_TYPE
			if(!StringUtils.isEmpty(productId)) { // 产品 
				ProdProduct product = prodProductService.getProdProductById(Long.valueOf(productId));
				Map<String,Object> data = pageService.getProdCProductInfo(Long.valueOf(productId),false);
				if(null != data ) {
					if( data.get("viewPage") != null) {
						ViewPage viewPage = (ViewPage)data.get("viewPage");
						getRequest().setAttribute("viewPage", viewPage);
					}
					// 行程说明
					if(data.get("viewJourneyList") != null) {
						 List<ViewJourney> viewJourneyList = (List<ViewJourney>)data.get("viewJourneyList");
						for (ViewJourney vj : viewJourneyList) {
							vj.setJourneyPictureList(comPictureService.getPictureByObjectIdAndType(vj.getJourneyId(), "VIEW_JOURNEY"));
						}
						getRequest().setAttribute("viewJourneyList", viewJourneyList);
					}
				}
				getRequest().setAttribute("product", product);
			}
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
		return "prod_comm";
	}
	
	/**
	 * 初始化参数.
	 * 
	 * @param param
	 * @throws UnsupportedEncodingException
	 */
	public void initParams(Map<String, Object> param)
			throws UnsupportedEncodingException {
		param.put("fromDest", " ");

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
		param.put("subProductType", "FREENESS");

	}
	
	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}
	
	
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
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
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
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
	
	public boolean isAjax() {
		return ajax;
	}

	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}
	
	public String getCommType() {
		return commType;
	}

	public void setCommType(String commType) {
		this.commType = commType;
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

	public void setMobileCommentService(IClientCommentService mobileCommentService) {
		this.mobileCommentService = mobileCommentService;
	}

}
