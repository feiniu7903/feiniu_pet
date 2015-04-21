/**
 * 
 */
package com.lvmama.front.web.product;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.view.ViewMultiJourneyService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.pet.client.RecommendInfoClient;
import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.po.comment.CmtLatitudeStatistics;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceHistoryCookie;
import com.lvmama.comm.pet.po.prod.ProdContainer;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;
import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.service.businessCoupon.ProdSeckillRuleService;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.comment.CmtLatitudeStatistisService;
import com.lvmama.comm.pet.service.comment.CmtTitleStatistisService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.prod.ProductHeadQueryService;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.pet.vo.ViewPlaceCoordinate;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.homePage.PlaceUtils;
import com.lvmama.comm.utils.homePage.TwoDimensionCode;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ProdCProduct;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
import com.lvmama.front.web.home.BaseHomeAction;
import com.lvmama.front.web.home.ToPlaceOnlyTemplateHomeAction;
import com.lvmama.front.web.seckill.SeckillMemcachedUtil;

/**
 * 
 * 团购明细Actin
 * @author zhongshuangxi
 *
 */
@Results( { 
	@Result(name = "success", location = "/WEB-INF/pages/group/tuangouDetail.ftl", type = "freemarker"),
	@Result(name = "seckillDetail", params={"status", "301","headers.Location","http://www.lvmama.com/tuangou/seckill-${prodBranchId}"}, type="httpheader"),
	@Result(name = "error", params={"status", "404"},type="dispatcher")
})
public class TuangouDetailAction extends ToPlaceOnlyTemplateHomeAction{
    private Long productId;
	private long diff=0;
	private Map<String, Object> productInfo;
	private ProdProduct prodProduct= null;
	private double discount;
	private long orderCount;
	private String tn;
	private String baiduid;
	private String provincePlaceId;
	private boolean isPreview = true;
	private ProdProductBranch prodProductBranch;
	private ProductHeadQueryService productServiceProxy;
	private ComPictureService comPictureService;
	private ProdProductService prodProductService;
	private ProdProductPlaceService prodProductPlaceService;
	private PlaceService placeService;
	
	private List<CalendarModel> cmList;
//	private List<CommonCmtCommentVO> comments;
//	private CmtTitleStatisticsVO commentStatistics;
	private List<ViewPlaceCoordinate> placeCoordinateHotel;
	//用于前台页面显示Tag
    private List<ProdTag> tagList;
    private Map<String,List<ProdTag>> tagGroupMap ;
	
	//产品点评相关信息
//	private List<CommonCmtCommentVO> productComments;
//    private CmtTitleStatisticsVO productCommentStatistics;
//    private List<CmtLatitudeStatistics> productCmtLatitudeStatisticsVOList;
    
    private RecommendInfoClient recommendInfoClient;
    private CmtCommentService cmtCommentService;
    private CmtTitleStatistisService cmtTitleStatistisService;
    private CmtLatitudeStatistisService cmtLatitudeStatistisService;
    private ViewMultiJourneyService viewMultiJourneyService;
    
    private static final String HISTORYKEY = "TUANGOU_DETAIL_PRODUCTID";
    private static final int MAXCOUNTBYRECOMMEND = 3;
	
    private Long commonBlockId = 14516L;
    private String channelPage = "tuangou";
    private String containerCode = "TUANGOU_RECOMMEND";
    private Map<String, List<RecommendInfo>> groupMap;
    private List<RecommendInfo> recommendBanner;
    private String jsonstr;
    private boolean isMultiJourney = false;
    //秒杀
    private ProdSeckillRuleService prodSeckillRuleService;
    private long prodBranchId;
	/**
	 * 查看团购产品详情    
	 * @return
	 * @throws Exception
	 */
	@Action("/homePage/prodProductDetail")
	public String viewTuangouPrdDetail() throws Exception {
		
	    if(null != productId && productId >0){
        boolean isGroupPrd = pageService.isGroupProduct(productId);   
        if(isGroupPrd){
        	//过滤秒杀
			Object obj = SeckillMemcachedUtil.getSeckillMemcachedUtil().getSeckillRuleByProductId(productId);
			if(obj != null){
				prodBranchId = ((ProdSeckillRule) obj).getBranchId();
				LOG.info("该产品是秒杀产品，从团购跳转到秒杀商品页面，BranchId:" + prodBranchId);
				return "seckillDetail";
			}
			
		HttpSession session = getRequest().getSession(true);
		if (getFromPlaceId() != null) {
			session.setAttribute("fromPCode", getFromPlaceCode());
			session.setAttribute("fromPid", getFromPlaceId());
			session.setAttribute("fromPName", getFromPlaceName());
		} else if ((Long) session.getAttribute("fromPid") != null) {
			this.fromPlaceCode = (String) session.getAttribute("fromPCode");
			this.fromPlaceId = (Long) session.getAttribute("fromPid");
			this.fromPlaceName = (String) session.getAttribute("fromPName");
		}
		super.init("CH_TUANGOUDEST");
		
		
		
		//获得各个频道的推荐的值 团购频道
		putRecommentInfoResult(channelPage, commonBlockId, containerCode,
        this.fromPlaceId);
        groupMap = (Map<String, List<RecommendInfo>>) map
                .get("recommendInfoMainList");
        recommendBanner = initRecommendInfo("_bannernew");
        List<RecommendInfo> recommendProducts = initRecommendInfo("_productsnew");
        init(Constant.CHANNEL_ID.CH_TUANGOU.name());
        //包含产品信息门票出发地信息
        productInfo = pageService.getProdCProductInfo(productId,isPreview);
   	
//		//根据模块ID和产品编号查看推荐信息
//        Map<String, Object> param = new HashMap<String, Object>();
//        param.put("parentRecommendBlockId",14525L);
//        param.put("recommObjectId", productId);
//		List<RecommendInfo> recommInfoList = recommendInfoService.queryRecommendInfoByParam(param);
//		if(null != recommInfoList && recommInfoList.size()>0){
//		    productInfo.put("recommendInfo", recommInfoList.get(0));
//		}
		
		//根据对象ID以及对象类型查询其对应的图片
		if(productInfo != null && productInfo.get("viewPage") != null) {
			ViewPage record = (ViewPage)productInfo.get("viewPage");
			record.setPictureList(getComPictureService().getPictureByPageId(record.getPageId()));
			productInfo.put("comPictureList", record.getPictureList());
		}
		
		
		ProdCProduct prodCProduct =(ProdCProduct) productInfo.get("prodCProduct");
		if(prodCProduct==null ){//没有找到跳转404
			this.LOG.info("product is null,pageId:"+productId+" channel:"+Constant.CHANNEL.TUANGOU.name());
			 return ERROR; 
		}else if(!isPreview && !prodCProduct.getProdProduct().isOnLine()){
			this.LOG.info("产品"+productId+"未上线Sellable="+prodCProduct.getProdProduct().getOnLine());
//			return ERROR;
		}
		//多行程TODO
//		if(prodCProduct.getProdProduct().isRoute()){
//			ProdRoute pr = (ProdRoute) prodCProduct.getProdProduct();
//			if(pr.hasMultiJourney()) {
//				Map<String, Object> multiParam = new HashMap<String, Object>();
//				multiParam.put("productId", productId);
//				multiParam.put("valid","Y");
//				List<ViewMultiJourney> multiJourneys = viewMultiJourneyService.queryMultiJourneyByParams(multiParam);
//				if(null != multiJourneys && multiJourneys.size()>0){
//				    productInfo.put("multiJourneysList", multiJourneys);
//				    for (ViewMultiJourney vmj : multiJourneys){
//				        List<ViewJourney> viewJourneyList = pageService.getViewJourneyByMultiJourneyId(vmj.getMultiJourneyId());
//				        for (ViewJourney vj : viewJourneyList) {
//			                vj.setJourneyPictureList(getComPictureService().getPictureByObjectIdAndType(vj.getJourneyId(), "VIEW_JOURNEY"));
//			            }
//				    }    	            		
//				}
//			}
//		}
		if(productInfo.get("viewJourneyList") != null) {
			List<ViewJourney> viewJourneyList = (List<ViewJourney>)productInfo.get("viewJourneyList");
			for (ViewJourney vj : viewJourneyList) {
				vj.setJourneyPictureList(getComPictureService().getPictureByObjectIdAndType(vj.getJourneyId(), "VIEW_JOURNEY"));
			}
		}
		
		
		prodProduct = prodCProduct.getProdProduct();
		productInfo.put("productStatic","ptcp");
		if(Constant.PRODUCT_TYPE.ROUTE.getCode().equals(prodProduct.getProductType()) || Constant.PRODUCT_TYPE.TICKET.getCode().equals(prodProduct.getProductType())){
		    if(null != recommendProducts && recommendProducts.size()>0){
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("parentRecommendBlockId",recommendProducts.get(0).getParentRecommendBlockId());
                param.put("recommObjectId", productId);
                List<RecommendInfo> rec = recommendInfoService.queryRecommendInfoByParam(param);
                if(null != rec && rec.size()==1){
                    RecommendInfo recInfo = rec.get(0);
                    if(null != recInfo.getBakWord4()){
                        productInfo.put("productStatic","wxzq");
                    }
                }
		    }
		} 
		long offlineTime = 0;
        long now = System.currentTimeMillis();
        if(prodProduct.getOfflineTime()!=null){
            offlineTime = prodProduct.getOfflineTime().getTime();
        }else{
            offlineTime=-1;
        }
        diff= offlineTime-now;
		
		if(diff<=0 && prodProduct.getOnLine()!="true"){
            productInfo.put("productStatic","xxcp");
        }
		
		//优惠说明 即折扣
		if(prodProduct.getMarketPriceYuan()>0){
			discount = new BigDecimal(prodProduct.getSellPriceYuan()/prodProduct.getMarketPriceYuan()*10).setScale(1,BigDecimal.ROUND_FLOOR).doubleValue();
		}else{
			discount=0;
		}
		
//		if(null != prodProduct.getProductId()){
//		    //获得产品点评相关信息
//	        productComments = cmtCommentService.getNewestCommentByProductID(productId, 5);
//	        productComments = cmtCommentService.composeUserImagOfComment(productComments);
//		    
//	        //点评统计 vo
//		    productCommentStatistics = cmtTitleStatistisService.getCmtTitleStatisticsByProductId(productId);
//		    
//		    
//	        Map<String,Object> params = new HashMap<String, Object>();
//	        params.put("productId", productId);
//	        productCmtLatitudeStatisticsVOList = cmtLatitudeStatistisService.getFourAvgLatitudeScoreList(params);
//	        
//	        
//	        if (prodCProduct != null && prodCProduct.getTo() != null ) {
//	            if (null != prodCProduct.getProdRoute()) {
//	                
//	            }
//	        } 
//		}
		
//		//默认类别
		prodProductBranch = (ProdProductBranch) productInfo.get("prodBranch");
		if (prodProductBranch==null) {
			this.LOG.info("产品"+productId+"没有默认类别");
		}
		
		
		this.timePrice(prodProduct);
		
		if(prodProductBranch != null && prodProductBranch.getMinimum() != null && prodProductBranch.getMinimum() == 0 ){
			prodProductBranch.setMinimum(new Long(1));
		}
		//获取产团购产品已购买人数
		orderCount = super.groupDreamService.countOrderByProduct(productId);
//		productInfo.put("cmList", productServiceProxy.getProductCalendarByProductId(productId));
		
		
        String cmtKey = "cmtLatitudeStatisticsList_avg_" + productId;
        CmtLatitudeStatistics cmtBean = (CmtLatitudeStatistics) MemcachedUtil.getInstance().get(cmtKey);
        Float avgScore,clsAvg;
        if(null == cmtBean){
            Map<String,Object> parameters = new HashMap<String, Object>();
            parameters.put("productId", productId);
            List<CmtLatitudeStatistics> cmtLatitudeStatisticsList = cmtLatitudeStatistisService.getLatitudeStatisticsList(parameters);
            if(null != cmtLatitudeStatisticsList && cmtLatitudeStatisticsList.size()>0 ){
                for (CmtLatitudeStatistics cls : cmtLatitudeStatisticsList) {
                    if("FFFFFFFFFFFFFFFFFFFFFFFFFFFF".equals(cls.getLatitudeId())){
                    	cmtBean = cls;
                        MemcachedUtil.getInstance().set(cmtKey,MemcachedUtil.TWO_HOUR, cmtBean);
                    }
               }
            }
        }
        if(cmtBean == null || cmtBean.getAvgScore() == null || cmtBean.getAvgScore() == 0){
            avgScore = 100F;
            clsAvg = 5f;
        }else{
        	avgScore = cmtBean.getAvgScore()*20;//讲平均分提升为百分比模式 原5分满分
            clsAvg = cmtBean.getAvgScore();
        }
        productInfo.put("avgScore", avgScore);
        productInfo.put("clsAvgScore", clsAvg);
		
        //取ProductSeachInfo上的tag信息由于页面显示
        ProductSearchInfo productSearchInfo = productSearchInfoService.queryProductSearchInfoByProductId(productId);
        if(productSearchInfo!=null){
            tagList = productSearchInfo.getTagList();
            tagGroupMap =productSearchInfo.getTagGroupMap();
        }
		//存取近期浏览
		setHistoryCookie(productId);
		List<PlaceHistoryCookie> historyProd = getHistoryCookie(HISTORYKEY);
		if(null != historyProd && historyProd.size()>0){
		    productInfo.put(HISTORYKEY,historyProd);
		}
		//调用二维码
		excuteQRDataFile();
		
		if(null != prodCProduct.getTo()){
//		    List<ProdProduct> prodRecommendToPlaceList = prodProductService.selectProdToPlaceProduct(Constant.CHANNEL.TUANGOU.getCode(), prodCProduct.getTo().getPlaceId(), MAXCOUNTBYRECOMMEND);//,productId,recommendPrdIds);
//          productInfo.put("prodRecommendToPlaceList",prodRecommendToPlaceList);
		    
    		String recommendPrdIds = "";
    		if(null != recommendProducts)
    		for (RecommendInfo ri : recommendProducts) {
    		    recommendPrdIds = recommendPrdIds+ ri.getRecommObjectId()+",";
            }
    		if(recommendPrdIds.length()>0 && recommendPrdIds.indexOf(",")>0){
    		  recommendPrdIds = recommendPrdIds.substring(0,recommendPrdIds.length()-1);
    		  List<ProdProduct> prodRecommendToPlaceList = prodProductService.selectProdToPlaceProduct(Constant.CHANNEL.TUANGOU.getCode(), prodCProduct.getTo().getPlaceId(), MAXCOUNTBYRECOMMEND,productId,recommendPrdIds);
    	      productInfo.put("prodRecommendToPlaceList",prodRecommendToPlaceList);
    		}
		}
		if(Constant.PRODUCT_TYPE.ROUTE.getCode().equals(prodProduct.getProductType())){
		    List<ProdProductPlace> prodPlace = prodProductPlaceService.selectByProductId(productId);
		    if(null != prodPlace && prodPlace.size()>0){
    		    String _scenicPlace ="";
    		    String _hotelPlace = "";
    		    for (ProdProductPlace p : prodPlace) {
    		        Place place =placeService.queryPlaceByPlaceId(p.getPlaceId());
    		        if(Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode().equals(place.getStage())){
    		            _scenicPlace = _scenicPlace +  place.getName() +"  ";
    		        }
    		        if(Constant.PLACE_STAGE.PLACE_FOR_HOTEL.getCode().equals(place.getStage())){
    		            _hotelPlace = _hotelPlace + place.getName() +"  ";
    		        }
                }
    		    
    		    if(_scenicPlace.length()>60){
    		        _scenicPlace = StringUtil.subStringStr(_scenicPlace,60);
    		        int lastScenic = _scenicPlace.lastIndexOf("  ");
    		        _scenicPlace = StringUtil.subStringStr(_scenicPlace,lastScenic);
    		    }
    		    if(_hotelPlace.length()>27){
    		        _hotelPlace = StringUtil.subStringStr(_hotelPlace,27);
    		        int lastHotel = _hotelPlace.lastIndexOf("  ");
    		        _hotelPlace = StringUtil.subStringStr(_hotelPlace,lastHotel);
    		        }
    		    productInfo.put("scenicPlace", _scenicPlace);
    		    productInfo.put("hotelPlace", _hotelPlace);
		    }
		}
		super.comSeoIndexPage.setSeoTitle(this.getSeoIndexPageRegular(super.comSeoIndexPage.getSeoTitle()));
		super.comSeoIndexPage.setSeoDescription(this.getSeoIndexPageRegular(super.comSeoIndexPage.getSeoDescription()));
		super.comSeoIndexPage.setSeoKeyword(this.getSeoIndexPageRegular(super.comSeoIndexPage.getSeoKeyword()));
		return SUCCESS;
	    }
	    }
	    return ERROR;
	}
	
	private String getSeoIndexPageRegular(String seoStr){
		String result=seoStr==null?"":seoStr;
		if(prodProduct != null){
			result = result.replace("{title}", prodProduct.getProductName());
			ProdCProduct bean = (ProdCProduct) productInfo.get("prodCProduct");
			if(bean != null && bean.getTo()!=null){
				result = result.replace("{placeTo}", bean.getTo().getName());
			}else{
				result = result.replace("{placeTo}", "");
			}
			if(prodProduct.getProductType()!= null && prodProduct.getProductType().equalsIgnoreCase("TICKET")){
				result = result.replace("{productType}", "门票");
			}else if(prodProduct.getProductType()!= null && prodProduct.getProductType().equalsIgnoreCase("ROUTE")){
				String subProductType = prodProduct.getSubProductType();
				if(subProductType.equalsIgnoreCase("GROUP_LONG") || subProductType.equalsIgnoreCase("FREENESS_LONG")){
					result = result.replace("{productType}", "国内游");
				}else if(subProductType.equalsIgnoreCase("FREENESS") || subProductType.equalsIgnoreCase("SELFHELP_BUS") || subProductType.equalsIgnoreCase("GROUP")){
					result = result.replace("{productType}", "周边游");
				}else if(subProductType.equalsIgnoreCase("GROUP_FOREIGN") || subProductType.equalsIgnoreCase("FREENESS_FOREIGN")){
					result = result.replace("{productType}", "出境游");
				}
				
			}else if(prodProduct.getProductType()!= null && prodProduct.getProductType().equalsIgnoreCase("HOTEL")){
				result = result.replace("{productType}", "酒店");
			}else{
				result = result.replace("{productType}", "");
			}
			ViewPage viewPageBean = (ViewPage) productInfo.get("viewPage");
			if(viewPageBean!=null){
				ViewContent vcbean = (ViewContent)viewPageBean.getContents().get("FEATURES");
				if(vcbean.getContent() != null){
					String _str = StringUtil.subStringStr(vcbean.getContent().replaceAll("－|(<[^>]*>)|(\\\\s)|(&[^;]*;)", ""), 80);
					result = result.replace("{description}", _str);
				}else{
					result = result.replace("{description}", "");
				}
			}else{
				result = result.replace("{description}", "");
			}
		}
		return result;
	}

	/**
     * 获取团购子项
     * @param recommendInfoName
     * @return
     */
    private List<RecommendInfo> initRecommendInfo(String recommendInfoName){
        return groupMap.get(channelPage + recommendInfoName);
    }
	/**
     * 二维码生成
     * @author nixianjun
     */
    private void excuteQRDataFile(){
        /**
         *增加二维码 
         */
        ProdCProduct prodCProduct = (ProdCProduct) productInfo.get("prodCProduct");
        if ((prodCProduct.getTo() != null)
                && (prodCProduct.getTo().getPlaceId() != null)&&Constant.PRODUCT_TYPE.TICKET.name().equals(
                prodCProduct.getProdProduct().getProductType())) {
            String dirPath = ResourceUtil
                    .getResourceFileName("/qrTicket");
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String imgPath = ResourceUtil
                    .getResourceFileName("/qrTicket/"
                            + prodCProduct.getTo().getPlaceId() + "."
                            + PlaceUtils.QR_IMGTYPE);
            File imgFile = new File(imgPath);
            if (!imgFile.exists()) {
                TwoDimensionCode.encoderQRCode(
                        "http://m.lvmama.com/clutter/place/"
                                + prodCProduct.getTo().getPlaceId()
                                + "/?channel=QR", imgPath,
                        PlaceUtils.QR_IMGTYPE, PlaceUtils.QR_SIZE);
            }
        }
        if (Constant.PRODUCT_TYPE.ROUTE.name().equals(
                prodCProduct.getProdProduct().getProductType())) {
            String dirPath = ResourceUtil
                    .getResourceFileName("/qrRoute");
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String imgPath = ResourceUtil
                    .getResourceFileName("/qrRoute/"
                            + prodCProduct.getProdProduct()
                                    .getProductId() + "."
                            + PlaceUtils.QR_IMGTYPE);
            File imgFile = new File(imgPath);
            if (!imgFile.exists()) {
                TwoDimensionCode
                        .encoderQRCode(
                                "http://m.lvmama.com/clutter/route/"
                                        + prodCProduct.getProdProduct()
                                                .getProductId()
                                        + "/?channel=QR", imgPath,
                                PlaceUtils.QR_IMGTYPE,
                                PlaceUtils.QR_SIZE);
            }
        }
    }
	
	protected void init(String channel) {
	    provincePlaceId = (String) getRequest().getAttribute(Constant.DEFAULT_PROVINCE_PLACE_ID);
        cityPlaceId = (String) getRequest().getAttribute(Constant.DEFAULT_CITY_PLACE_ID);
        if (provincePlaceId == null) {
    		String ipProvincePlaceId = (String) getRequest().getAttribute(Constant.IP_PROVINCE_PLACE_ID);
    		String ipCityPlaceId = (String) getRequest().getAttribute(Constant.IP_CITY_PLACE_ID);
    		if (!"".equals(ipProvincePlaceId) && ipProvincePlaceId != null) {
    		    provincePlaceId = this.getDefaultPlaceId(containerCode, ipProvincePlaceId, "3548");
            }
            if (provincePlaceId == null || "".equals(provincePlaceId)) {
                provincePlaceId = "79";
                cityPlaceId = "79";
            } else if (!"".equals(ipCityPlaceId)) {
                cityPlaceId = this.getDefaultPlaceId(containerCode, ipCityPlaceId, null);
            }
            ServletUtil.addCookie(super.getResponse(), Constant.DEFAULT_PROVINCE_PLACE_ID, provincePlaceId, 30);
            ServletUtil.addCookie(super.getResponse(), Constant.DEFAULT_CITY_PLACE_ID, cityPlaceId, 30);
        }
		if (fromPlaceCode == null) {
			fromPlaceCode = (String) getRequest().getAttribute(Constant.IP_AREA_LOCATION);
		}
		if (fromPlaceId == null) {
            fromPlaceId = (Long) getRequest().getAttribute(Constant.IP_FROM_PLACE_ID);
        }
		if (fromPlaceName == null) {
		    fromPlaceName = (String) getRequest().getAttribute(Constant.IP_FROM_PLACE_NAME);
		}
	}
	private String getDefaultPlaceId(String containerCode, String ipLocationId, String destId) {
		String key = "ToPlaceOnlyTemplateHomeAction_getDefaultPlaceId" + "_" + containerCode + "_" + ipLocationId + "_" + destId;
		ProdContainer prodContainer = (ProdContainer) MemcachedUtil.getInstance().get(key);
		if (prodContainer == null) {
			prodContainer = prodContainerProductService.getToPlace(containerCode, ipLocationId, destId);
			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR, prodContainer);
		}
		if (prodContainer!=null) {
			return prodContainer.getToPlaceId();
		}else{
			return "";
		}
	}
//	public String groupProductPreview() throws Exception{
//		this.isPreview = false;
//		return this.viewTuangouPrdDetail();
//	}
	
	public List<PlaceHistoryCookie> getHistoryCookie(String historyKey){
        Cookie cookie = ServletUtil.getCookie(getRequest(),historyKey);//获取团购详情页近期浏览
        List<PlaceHistoryCookie> historyCookieList = new ArrayList<PlaceHistoryCookie>();
        try {
            //查询cookie中的己有浏览记录
            if(cookie != null&&StringUtil.isNotEmptyString(cookie.getValue())){
                JSONArray cookieList = JSONArray.fromObject(URLDecoder.decode(cookie.getValue(),"UTF-8"));
                for (int i = 0; i < cookieList.size(); i++) {
                    historyCookieList.add((PlaceHistoryCookie)JSONObject.toBean(cookieList.getJSONObject(i),PlaceHistoryCookie.class));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOG.error("GET "+historyKey+ e);
        }
        return historyCookieList;
    }
	
	public void setHistoryCookie(long productId){
        try {
            ProdProduct pp = pageService.getProdProductByProductId(productId);
            if(pp != null){
                PlaceHistoryCookie historyCookie = new PlaceHistoryCookie();
                //存的是产品的ID
                historyCookie.setPlaceId(String.valueOf(productId));
                historyCookie.setName(StringUtil.cutString(10,pp.getProductName()));
                historyCookie.setProductsPrice(String.valueOf(((Float)pp.getSellPriceYuan()).intValue()));
                //获取第一张大图作为缩略图
                historyCookie.setImageUrl(pp.getSmallImage());
                
                Cookie cookie = ServletUtil.getCookie(getRequest(),HISTORYKEY);
                JSONArray cookieList = new JSONArray();
                //新纪录标识
                boolean flag = true;
                if(cookie != null&&StringUtil.isNotEmptyString(cookie.getValue())){
                    cookieList = JSONArray.fromObject(URLDecoder.decode(cookie.getValue(),"UTF-8"));
                    //如果新记录己存在COOKIE中，刚将新记录移至第一位
                    for (int i = 0; i < cookieList.size(); i++) {
                        PlaceHistoryCookie historyTemp = (PlaceHistoryCookie)JSONObject.toBean(cookieList.getJSONObject(i),PlaceHistoryCookie.class);
                        if(historyTemp.getPlaceId().equals(String.valueOf(productId))){
                            cookieList.remove(i);
                            cookieList.add(0, historyTemp);
                            flag = false;
                            break;
                        }
                    }
                    //cookie中达到最大值时，清除超过最大值的记录
                    if(cookieList.size() >= 10){
                        for (int i = 10-1; i < cookieList.size(); i++) {
                            cookieList.remove(i);
                        }
                    }
                }
                //将新记录、排在第一位
                if(flag)cookieList.add(0,historyCookie);
                //加入cookie
                ServletUtil.addCookie(getResponse(),HISTORYKEY,URLEncoder.encode(cookieList.toString(),"UTF-8"),30,false);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOG.error("SAVE "+HISTORYKEY+" ERROR :"+e);
        }
    }
	
	//*************************************** getter/setter ****************************************//
	
	public double getDiscount() {
		return discount;
	}
	public Map<String, Object> getProductInfo() {
		return productInfo;
	}
	public long getDiff() {
		return diff;
	}
	public ProdProduct getProdProduct() {
		return prodProduct;
	}
	public long getOrderCount() {
		return orderCount;
	}

	/**
	 * 当前系统时间
	 * @return
	 */
	public Long getSysDate() {
		
		Date  sysdate = Calendar.getInstance().getTime();
		sysdate.setHours(0);
		sysdate.setMinutes(0);
		sysdate.setSeconds(0);
		return sysdate.getTime();
	}
	
	public List<CalendarModel> getCmList() {
		return cmList;
	}
	
	public ProdProductBranch getProdProductBranch() {
		return prodProductBranch;
	}
	/**时间价格表
	 * @throws IOException */
	public void timePrice(ProdProduct prodProduct) throws IOException {
		String[] month ={"1","2","3","4","5","6","7","8","9","10","11","12"};
		getTimePrice(prodProduct);
		JSONObject jsonObj = new JSONObject();
		if(CollectionUtils.isNotEmpty(cmList)){
			try {
				// 循环判断月份
				for (int m = 0; m < month.length; m++) {
					JSONArray array = new JSONArray();
					
					for (int k = 0; k < cmList.size(); k++) {
						CalendarModel cm = cmList.get(k);
						TimePrice[][] calendar = cm.getCalendar();
						for (int i = 0; i < calendar.length; i++) {
							TimePrice[] objH = calendar[i];
							for (int j = 0; j < objH.length; j++) {
								JSONObject obj = new JSONObject();
								TimePrice tp = objH[j];
								if ((DateUtil.getDateTime("M", tp.getSpecDate())).equals(month[m])) {
										obj.put("date", DateUtil.getDateTime("yyyy-MM-dd", tp.getSpecDate()));
										long dayStock = tp.getDayStock();
										if (dayStock == -1 || dayStock == 0|| dayStock > 0|| tp.isOnlyForLeave() == true|| tp.getOverSale().equals("true")) {
											 JSONObject j1=excecuteGetJsonTimeData(tp,dayStock);
											 obj.putAll(j1);
										} else {
											obj.put("number", "");
											obj.put("price", "");
											obj.put("active", "");
										 }
									   if(!array.contains(obj)){
										   array.add(obj);
									   }
								 }
							}
						}
					}
					if (array != null && !array.equals("")) {
						jsonObj.put(month[m], array);
					}
 				}
				
			} catch (Exception e) {
			    e.printStackTrace();
			    log.info("新版景区返回时间价格表出错");
 			}
		}
		jsonstr = jsonObj.toString();
		this.getRequest().setAttribute("jsonStr", jsonstr);
	}

	public JSONObject excecuteGetJsonTimeData(TimePrice tp,long dayStock){
		JSONObject obj = new JSONObject();
		if ((!tp.isNeedResourceConfirm() && dayStock == -1) || dayStock > 9) {
					if (isMultiJourney && tp.getMultiJourneyId() == null) { } else {
						obj.put("number", "充足");
						obj.put("price",
								tp.getPriceInt());
						if ((tp.getFavorJsonParams() != null && !tp
								.getFavorJsonParams()
								.equals(""))
								|| (tp.getCuCouponFlag() > 0)) {
							obj.put("active", "促");
						} else {
							obj.put("active", "");
						}
					 }
			} else if (dayStock > -1&& dayStock != 0) {
						if (isMultiJourney
								&& tp.getMultiJourneyId() == null) {
						} else {
							obj.put("number", dayStock);
							obj.put("price",
									tp.getPriceInt());
							if ((tp.getFavorJsonParams() != null && !tp
									.getFavorJsonParams()
									.equals(""))
									|| (tp.getCuCouponFlag() > 0)) {
								obj.put("active", "促");
							} else {
								obj.put("active", "");
							}
						}
			} else if (((tp.isOverSaleAble() || tp.isNeedResourceConfirm()) && dayStock == -1)|| (tp.isOverSaleAble() && dayStock == 0)) {
							if (isMultiJourney
									&& tp.getMultiJourneyId() == null) {

								} else {
									obj.put("number", "");
									obj.put("price",
											tp.getPriceInt());
									if ((tp.getFavorJsonParams() != null && !tp
											.getFavorJsonParams()
											.equals(""))
											|| (tp.getCuCouponFlag() > 0)) {
										obj.put("active", "促");
									} else {
										obj.put("active", "");
									}
								}
				} else if (!tp.isSellable(1)) {
				obj.put("number", "售完");
				obj.put("price", tp.getPriceInt());
				obj.put("active", "");
			   }
	   return obj;
	}
	private void getTimePrice(ProdProduct prodProduct) {
		ProdProduct prod = null;
		if (prodProduct.getProductId() != null && prodProduct.getProductId() > 0) {
			prodProductBranch = super.pageService
					.selectDefaultBranchByProductId(prodProduct.getProductId());
			cmList = productServiceProxy
					.getProductCalendarByProductId(prodProduct.getProductId());
			cmList = favorService.fillFavorParamsInfoForCalendar(prodProduct.getProductId(),
					null, cmList);// 填充优惠信息
			prod = prodProductService.getProdProduct(prodProduct.getProductId());
		}
		if (prod != null) {
			if (prod.isRoute()) {
				ProdRoute pr = (ProdRoute) prod;
				isMultiJourney = pr.hasMultiJourney();
			}
		}
	}
	
	/**
	 * @return the placeCoordinateHotel
	 */
	public List<ViewPlaceCoordinate> getPlaceCoordinateHotel() {
		return placeCoordinateHotel;
	}

	public void setProductServiceProxy(ProductHeadQueryService productServiceProxy) {
		this.productServiceProxy = productServiceProxy;
	}

	public String getContainerCode() {
		return containerCode;
	}

	public void setContainerCode(String containerCode) {
		this.containerCode = containerCode;
	}

	public String getProvincePlaceId() {
		return provincePlaceId;
	}

	public void setProvincePlaceId(String provincePlaceId) {
		this.provincePlaceId = provincePlaceId;
	}

	public String getCityPlaceId() {
		return cityPlaceId;
	}

	public void setCityPlaceId(String cityPlaceId) {
		this.cityPlaceId = cityPlaceId;
	}

	public String getFromPlaceCode() {
		return fromPlaceCode;
	}

	public void setFromPlaceCode(String fromPlaceCode) {
		this.fromPlaceCode = fromPlaceCode;
	}

	public Long getFromPlaceId() {
		return fromPlaceId;
	}

	public void setFromPlaceId(Long fromPlaceId) {
		this.fromPlaceId = fromPlaceId;
	}

	public String getFromPlaceName() {
		return fromPlaceName;
	}

	public void setFromPlaceName(String fromPlaceName) {
		this.fromPlaceName = fromPlaceName;
	}

	public String getTn() {
		return tn;
	}

	public void setTn(String tn) {
		this.tn = tn;
	}

	public String getBaiduid() {
		return baiduid;
	}

	public void setBaiduid(String baiduid) {
		this.baiduid = baiduid;
	}

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public ComPictureService getComPictureService() {
        return comPictureService;
    }

    public void setComPictureService(ComPictureService comPictureService) {
        this.comPictureService = comPictureService;
    }

    public RecommendInfoClient getRecommendInfoClient() {
        return recommendInfoClient;
    }

    public void setRecommendInfoClient(RecommendInfoClient recommendInfoClient) {
        this.recommendInfoClient = recommendInfoClient;
    }

    public ProductHeadQueryService getProductServiceProxy() {
        return productServiceProxy;
    }

    public CmtCommentService getCmtCommentService() {
        return cmtCommentService;
    }

    public void setCmtCommentService(CmtCommentService cmtCommentService) {
        this.cmtCommentService = cmtCommentService;
    }

    public CmtTitleStatistisService getCmtTitleStatistisService() {
        return cmtTitleStatistisService;
    }

    public void setCmtTitleStatistisService(
            CmtTitleStatistisService cmtTitleStatistisService) {
        this.cmtTitleStatistisService = cmtTitleStatistisService;
    }

    public CmtLatitudeStatistisService getCmtLatitudeStatistisService() {
        return cmtLatitudeStatistisService;
    }

    public void setCmtLatitudeStatistisService(
            CmtLatitudeStatistisService cmtLatitudeStatistisService) {
        this.cmtLatitudeStatistisService = cmtLatitudeStatistisService;
    }

    public Long getCommonBlockId() {
        return commonBlockId;
    }

    public void setCommonBlockId(Long commonBlockId) {
        this.commonBlockId = commonBlockId;
    }

    public String getChannelPage() {
        return channelPage;
    }

    public void setChannelPage(String channelPage) {
        this.channelPage = channelPage;
    }

    public Map<String, List<RecommendInfo>> getGroupMap() {
        return groupMap;
    }

    public void setGroupMap(Map<String, List<RecommendInfo>> groupMap) {
        this.groupMap = groupMap;
    }
    
    public List<RecommendInfo> getRecommendBanner() {
		return recommendBanner;
	}

	public void setRecommendBanner(List<RecommendInfo> recommendBanner) {
		this.recommendBanner = recommendBanner;
	}

	public ProdProductService getProdProductService() {
        return prodProductService;
    }

    public void setProdProductService(ProdProductService prodProductService) {
        this.prodProductService = prodProductService;
    }

	public String getJsonstr() {
		return jsonstr;
	}

	public void setJsonstr(String jsonstr) {
		this.jsonstr = jsonstr;
	}

    public ProdProductPlaceService getProdProductPlaceService() {
        return prodProductPlaceService;
    }

    public void setProdProductPlaceService(
            ProdProductPlaceService prodProductPlaceService) {
        this.prodProductPlaceService = prodProductPlaceService;
    }

    public PlaceService getPlaceService() {
        return placeService;
    }

    public void setPlaceService(PlaceService placeService) {
        this.placeService = placeService;
    }

	public ViewMultiJourneyService getViewMultiJourneyService() {
		return viewMultiJourneyService;
	}

	public void setViewMultiJourneyService(
			ViewMultiJourneyService viewMultiJourneyService) {
		this.viewMultiJourneyService = viewMultiJourneyService;
	}
	public void setProdSeckillRuleService(
			ProdSeckillRuleService prodSeckillRuleService) {
		this.prodSeckillRuleService = prodSeckillRuleService;
	}

	public long getProdBranchId() {
		return prodBranchId;
	}

	public void setProdBranchId(long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}

    public List<ProdTag> getTagList() {
        return tagList;
    }

    public void setTagList(List<ProdTag> tagList) {
        this.tagList = tagList;
    }

    public Map<String, List<ProdTag>> getTagGroupMap() {
        return tagGroupMap;
    }

    public void setTagGroupMap(Map<String, List<ProdTag>> tagGroupMap) {
        this.tagGroupMap = tagGroupMap;
    }
	
}
