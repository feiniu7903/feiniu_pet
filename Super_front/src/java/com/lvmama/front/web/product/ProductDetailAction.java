package com.lvmama.front.web.product;

import com.lvmama.comm.bee.po.prod.*;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.bee.vo.view.ViewProdProductJourney;
import com.lvmama.comm.bee.vo.view.ViewProdProductJourneyDetail;
import com.lvmama.comm.pet.client.RecommendInfoClient;
import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.po.comment.CmtLatitudeStatistics;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceHotel;
import com.lvmama.comm.pet.po.place.PlacePhoto;
import com.lvmama.comm.pet.po.prod.ProdEContract;
import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.po.visa.VisaApplicationDocument;
import com.lvmama.comm.pet.po.visa.VisaApplicationDocumentDetails;
import com.lvmama.comm.pet.service.businessCoupon.ProdSeckillRuleService;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.comment.CmtLatitudeStatistisService;
import com.lvmama.comm.pet.service.comment.CmtTitleStatistisService;
import com.lvmama.comm.pet.service.place.HotelTrafficInfoService;
import com.lvmama.comm.pet.service.place.PlaceFlightService;
import com.lvmama.comm.pet.service.place.PlacePageService;
import com.lvmama.comm.pet.service.place.PlacePhotoService;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.pet.service.visa.VisaApplicationDocumentService;
import com.lvmama.comm.pet.vo.ProductList;
import com.lvmama.comm.pet.vo.ViewPlaceCoordinate;
import com.lvmama.comm.utils.*;
import com.lvmama.comm.utils.homePage.PlaceUtils;
import com.lvmama.comm.utils.homePage.TwoDimensionCode;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ProdCProduct;
import com.lvmama.comm.vo.ProductResult;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
import com.lvmama.comm.vo.visa.VisaVO;
import com.lvmama.front.dto.CalendarInfo;
import com.lvmama.front.web.seckill.SeckillMemcachedUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import java.io.File;
import java.util.*;


@Results( { @Result(name = "SUCCESS", location = "/WEB-INF/pages/product/newdetail/main.ftl", type = "freemarker") ,
	 @Result(name=com.opensymphony.xwork2.Action.ERROR,params={"status", "404"},type="dispatcher"),
	 @Result(name = "hotelPlace", params={"status", "301","headers.Location","http://www.lvmama.com/dest/${pinYinUrl}"}, type = "httpheader"),
	 @Result(name = "selfPack",location="/WEB-INF/pages/product/newdetail/buttom/selfPack/OJViewDetail.ftl"),
	 @Result(name = "groupPrdDetail",params={"status", "301","headers.Location","http://www.lvmama.com/tuangou/detail-${id}"}, type="httpheader"),
	 @Result(name = "seckillDetail", params={"status", "301","headers.Location","http://www.lvmama.com/tuangou/seckill-${prodBranchId}"}, type="httpheader"),	 
	 @Result(name = "TICKET", params={"status", "301","headers.Location","http://www.lvmama.com/search/ticket-kw-.html"}, type="httpheader"), 
	 @Result(name = "AROUND",params={"status", "301", "headers.Location", "http://www.lvmama.com/search/around-from--to--route.html"}, type = "httpheader"), 
	 @Result(name = "ABROAD",params={"status", "301", "headers.Location", "http://www.lvmama.com/search/abroad-from--to--route.html"}, type = "httpheader"), 
	 @Result(name = "DESTROUTE",params={"status", "301", "headers.Location", "http://www.lvmama.com/search/destroute-from--to--route.html"}, type = "httpheader"), 
	 @Result(name = "FREETOUR",params={"status", "301", "headers.Location", "http://www.lvmama.com/search/freetour-to-.html"}, type = "httpheader"),
	 @Result(name = "TRAIN",params={"status", "301", "headers.Location", "http://www.lvmama.com/train/"}, type = "httpheader"), 
	 @Result(name = "HOTEL", params = { "status", "301", "headers.Location", "http://www.lvmama.com/search/hotel-cityid-0-spot--name--price-0_80000-page-1.html" }, type = "httpheader"),
	 @Result(name = "wap_route", location = "http://m.lvmama.com/clutter/route/${id}", type = "redirect"),
	 @Result(name = "wap_place_dest", location = "http://m.lvmama.com/clutter/place/${id}", type = "redirect")
})
public class ProductDetailAction extends ProductBaseAction {
	private static final long serialVersionUID = -2045684444185641952L;
	protected final Log LOG = LogFactory.getLog(ProductDetailAction.class);
	private ComPictureService comPictureService;
	private ProdProductPlaceService prodProductPlaceService;
	private PlacePageService placePageService;
	private HotelTrafficInfoService hotelTrafficInfoService;
	private PlacePhotoService placePhotoService;
	private PlacePhoto placePhoto = new PlacePhoto();
	private PlaceFlightService placeFlightService;
	private String pinYinUrl;//目的地拼音URL
	
	private long prodBranchId;
	/**
	 * 是否可售<br>
	 * 1)在时间价格表中存在<br>
	 * 2)销售产品可售属性为true
	 */
	private String isSell = "true";
	private String choseDate;
	private Map<String, Object> data;
	private ProdCProduct prodCProduct;
	private ProdProduct errorProduct;
	private String errorProductType;
	private String errorSubProductType;
	private boolean isPreview = false;
	private List<CommonCmtCommentVO> comments;
	private long adult;
	private long child;
	private ViewProdProductJourneyDetail prodProductJourneyDetail;//行程信息.
	private CmtTitleStatisticsVO commentStatistics;
	private Map<String,Long> ordNum=new HashMap<String, Long>();
	private ViewPage tmpViewPage;//供从data当中取出使用
	
	//产品点评相关对象
	private List<CommonCmtCommentVO> productComments;
	private CmtTitleStatisticsVO productCommentStatistics;
	private List<CmtLatitudeStatistics> productCmtLatitudeStatisticsVOList;
	
	private RecommendInfoClient recommendInfoClient;
	private CmtCommentService cmtCommentService;
	private CmtTitleStatistisService cmtTitleStatistisService;
	private CmtLatitudeStatistisService cmtLatitudeStatistisService;
	//取Product_Seach_Info信息的Service
	private ProductSearchInfoService productSearchInfoService;
	//用于前台页面显示Tag
	private List<ProdTag> tagList;
	private 	Map<String,List<ProdTag>> tagGroupMap ;
	private boolean isMultiJourney = false;
	/**
	 * 签证材料远程服务
	 */
	private VisaApplicationDocumentService visaApplicationDocumentService;
	private List<VisaVO> visaVOList=new ArrayList<VisaVO>();
	/**
	 * 电子合同信息
	 */
	private ProdEContract prodEContract;
	private ProdProductService prodProductService;
	/**
	 * 手机返现金额
	 */
	private long mobileMoney;
	private ProdSeckillRuleService prodSeckillRuleService;
	private String qrFlag="false";

    /**
     * 优惠是否有效
     */
    private String couponEnabled = "Y";


    @Action("/product/detail")
	public String execute() {
		
		if(getSession("PRODUCT_PREVIEW_KEY")!=null){
			removeSession("PRODUCT_PREVIEW_KEY");
		}
		
		return detail();
	}
	
	/**
	 * 产品信息读取
	 * @return
	 */
	public String detail(){
		if (id > 0) {
			//检查产品ID是否存在
			ProductResult pr=pageService.findProduct(id);
			if(!pr.isExists()){
				return ERROR;
			}else if(pr.isBranch()){//如果是类别上取对应的类别上的产品ID
				id=pr.getProductId();
			}
			boolean isGroupPrd = pageService.isGroupProduct(id);	
			prodEContract=prodProductService.getProdEContractByProductId(id);//添加电子合同对象
			if(isGroupPrd){
				//过滤秒杀
				Object obj = SeckillMemcachedUtil.getSeckillMemcachedUtil().getSeckillRuleByProductId(id);
				if(obj != null){
					prodBranchId = ((ProdSeckillRule) obj).getBranchId();
					LOG.info("该产品是秒杀产品，跳转到秒杀商品页面，BranchId:" + prodBranchId);
					return "seckillDetail";
				}
				LOG.info("该产品是团购产品，跳转到团购产品页面，productId:" + id);
				return "groupPrdDetail";
			}
	         
					
			if(!productServiceProxy.isSellProductByChannel(id, Constant.CHANNEL.FRONTEND.name())){
				LOG.info("产品销售渠道不是驴妈妈前台 " + Constant.CHANNEL.FRONTEND.name() + "。productId：" + productId);
				return ERROR;				
			}

			String key = "PROD_C_PRODUCT_INFO_"+id;
				Object obj = MemcachedUtil.getInstance().get(key);
				if (obj!=null && obj instanceof Map) {
					//预览不读缓存
					if(isPreview){
						initProductInfo();
					}else{
						data=(Map<String,Object>)obj;
						prodCProduct = (ProdCProduct)data.get("prodCProduct");
					}
				}else {
					initProductInfo();
					MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR, data);
				}

			if (data.size() == 0) {
				return ERROR;
			}
			List<ProdProductBranch> branchList = (List<ProdProductBranch>) data.get("prodProductBranchList");
			if(branchList.isEmpty()) {
				return ERROR;
			}
			
			errorProduct = (ProdProduct) data.get("errorProduct");			
			if (errorProduct != null) {
				LOG.info("取产品详情数据出错，跳到相应产品类型搜索页。productId：" + productId);
				return getErrorProductPage(errorProduct);
			}
			
			Long productId = prodCProduct.getProdProduct().getProductId();			
			
			if(this.getCookieValue("wap_to_lvmama")==null && HttpRequestDeviceUtils.isMobileDevice(getRequest())){
				if(Constant.PRODUCT_TYPE.TICKET.name().equals(prodCProduct.getProdProduct().getProductType())){
					Place p   =prodCProduct.getTo();
					if(p != null){
						this.id = p.getPlaceId();
						return "wap_place_dest";
					}
				}else if(Constant.PRODUCT_TYPE.ROUTE.name().equals(prodCProduct.getProdProduct().getProductType())){
					return "wap_route";
				}
			} 
			
			if (Constant.PRODUCT_TYPE.HOTEL.name().equals(prodCProduct.getProdProduct().getProductType())
					&& Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(prodCProduct.getProdProduct().getSubProductType())) {
				if (prodCProduct.getTo() == null ) {
					LOG.info("该产品为酒店单房型，没有目的地，所以跳转到酒店搜索页。productId：" + productId);
					return "HOTEL";
				} else {
					LOG.info("该产品为酒店单房型，跳转到酒店DEST页。productId：" + productId);
					pinYinUrl = prodCProduct.getTo().getPinYinUrl();
					return "hotelPlace";
				}
			}else if(prodCProduct.getProdProduct().isTrain()){
				LOG.info("该产品为火车票产品,跳转至。productId：" + productId);
				return "TRAIN";
			} else if(Constant.PRODUCT_TYPE.ROUTE.name().equals(prodCProduct.getProdProduct().getProductType())) {
				ProdRoute prodRoute = prodCProduct.getProdRoute();
				isMultiJourney = prodRoute.hasMultiJourney();
			}
			
			if(!isPreview && !prodCProduct.getProdProduct().isOnLine()){				
				LOG.info("产品" + productId + "未上线，Sellable="+prodCProduct.getProdProduct().getOnLine());
				return getErrorProductPage(prodCProduct.getProdProduct());				
			} 
			
			if (!prodCProduct.getProdProduct().isSellable()) {		
				isSell="false";			
			} 						
			
			//签证内容
			if(null!=prodCProduct.getProdRoute()&&StringUtils.isNotBlank(prodCProduct.getProdRoute().getCountry())
			    &&StringUtils.isNotBlank(prodCProduct.getProdRoute().getCity())
				&&StringUtils.isNotBlank(prodCProduct.getProdRoute().getVisaType())){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("country", prodCProduct.getProdRoute().getCountry());
				param.put("visaType", prodCProduct.getProdRoute().getVisaType());
				param.put("city", prodCProduct.getProdRoute().getCity());
				//文档
			    List<VisaApplicationDocument> visaApplicationDocumentList=visaApplicationDocumentService.query(param);
			    for(VisaApplicationDocument v:visaApplicationDocumentList){
			    	List<VisaApplicationDocumentDetails> vList=new ArrayList<VisaApplicationDocumentDetails>();
			    	VisaVO visaVo=new VisaVO();
			    	//文档详情
			    	 vList = visaApplicationDocumentService.queryDetailsByDocumentId(v.getDocumentId());
			    	 visaVo.setDocumentId(v.getDocumentId());
			    	 visaVo.setOccupation(v.getOccupation());
			    	 visaVo.setVisaApplicationDocumentDetailsList(vList);
			    	 this.visaVOList.add(visaVo);
			    }
				
			}

			try {
				//获取产品详情页更多点评有关的信息
				fillMoreCommentInfo(productId);
			} catch (Exception e) {
				LOG.error(e);
				StackOverFlowUtil.printErrorStack(this.getRequest(), this.getResponse(), e);

			}
			//取ProductSeachInfo上的tag信息由于页面显示
		
			ProductSearchInfo productSearchInfo = productSearchInfoService.queryProductSearchInfoByProductId(id);
			if(productSearchInfo!=null){
				tagList = productSearchInfo.getTagList();
				tagGroupMap =productSearchInfo.getTagGroupMap();
			}
			
			/**
			 * 增加二维码
			 * @author nixianjun
			 */
			if (productServiceProxy.isSellProductByChannel(id,
							Constant.CHANNEL.CLIENT.name()) || productServiceProxy
							.isSellProductByChannel(id,
									Constant.CHANNEL.TOUCH.name())) {
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
										+ "?channel=QR", imgPath,
								PlaceUtils.QR_IMGTYPE, PlaceUtils.QR_SIZE);
					}
					qrFlag = "ticketTrue";
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
												+ "?channel=QR", imgPath,
										PlaceUtils.QR_IMGTYPE,
										PlaceUtils.QR_SIZE);
					}
					qrFlag = "routeTrue";
				}
			}

			this.setMobileMultiple();

            couponEnabled = Constant.initCouponEnabled(prodCProduct.getProdProduct().getProductId())?"Y":"N";

            return "SUCCESS";
		}	
		
		return ERROR;
	}
	/**
	 * 初始化
	 * 
	 * @author: ranlongfei 2012-8-2 下午7:36:51
	 */
	@SuppressWarnings("unchecked")
	private void initProductInfo() {
		data = pageService.getProdCProductInfo(id, isPreview);
		if(data != null && data.get("viewJourneyList") != null) {
			List<ViewJourney> viewJourneyList = (List<ViewJourney>)data.get("viewJourneyList");
			for (ViewJourney vj : viewJourneyList) {
				vj.setJourneyPictureList(comPictureService.getPictureByObjectIdAndType(vj.getJourneyId(), "VIEW_JOURNEY"));
			}
		}
		/**
		 * 加载产品图片
		 */
		if(data != null && data.get("viewPage") != null) {
			ViewPage record = (ViewPage)data.get("viewPage");
			record.setPictureList(comPictureService.getPictureByPageId(record.getPageId()));
			data.put("comPictureList", record.getPictureList());
		}
		data = initDestInfo(data,productId);
	}

	private Map<String,Object> initDestInfo(Map<String,Object> data,Long productId) {
		try {
			prodCProduct = (ProdCProduct)data.get("prodCProduct");
			if (prodCProduct != null && prodCProduct.getTo() != null ) {					
				// 相关产品推荐
				if(!"1".equals(prodCProduct.getTo().getStage()) && prodCProduct.getTo().getParentPlaceId() != null) { 	
					ProductList productList = recommendInfoClient.getProductByPlaceIdAnd4Type(prodCProduct.getTo().getParentPlaceId(),"1", 6, Constant.CHANNEL.FRONTEND.name());
					data.put("guestProductList", productList);
				} 
				
				// 目的地信息
//				data.put("viewPlaceInfo", placeRemoteService.getPlaceInfoByPlaceId(prodCProduct.getTo().getPlaceId()));
//				
//				// 目的地周边酒店
//				data.put("placeCoordinateHotel", placeRemoteService.getCoordinateByPlace(prodCProduct.getTo().getPlaceId() + "", "3"));
				
			} else {
				LOG.info("该产品没有目的地，所以无法查询相关产品推荐、目的地周边酒店、景区点评等信息。productId：" + productId);
			}
		} catch (Exception e) {
			StackOverFlowUtil.printErrorStack(this.getRequest(), this.getResponse(), e);
		}
		return data;
	}

	/**
	 * 当删除下线的产品返回不同的搜索结果页
	 * @param errorProduct
	 * @return
	 */
	private String getErrorProductPage(ProdProduct errorProduct) {
		errorSubProductType = errorProduct.getSubProductType();
		errorProductType = errorProduct.getProductType();
		if (Constant.PRODUCT_TYPE.TICKET.name().equalsIgnoreCase(errorProductType)) { // 门票
			return "TICKET";
		} else if (Constant.PRODUCT_TYPE.HOTEL.name().equalsIgnoreCase(errorProductType)) { // 酒店
			return "HOTEL";
		} else if (Constant.PRODUCT_TYPE.ROUTE.name().equalsIgnoreCase(errorProductType)) { // 线路
			if (Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equalsIgnoreCase(errorSubProductType)
					|| Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equalsIgnoreCase(errorSubProductType)) {
				return "ABROAD";
			}
			if (Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.name().equalsIgnoreCase(errorSubProductType) 
					|| Constant.SUB_PRODUCT_TYPE.GROUP.name().equalsIgnoreCase(errorSubProductType)) {
				return "AROUND";
			}
			if (Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name().equalsIgnoreCase(errorSubProductType) 
					|| Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name().equalsIgnoreCase(errorSubProductType)) {
				return "DESTROUTE";
			}
			if (Constant.SUB_PRODUCT_TYPE.FREENESS.name().equalsIgnoreCase(errorSubProductType)) {
				return "FREETOUR";
			}
		}
		return ERROR;
	}
	
	@Action("/product/preview")
	public String productPreview(){
		this.isPreview = true;
		putSession("PRODUCT_PREVIEW_KEY", "true");
		return this.detail();
	}
	
	/**
	 * 读取一个产品的行情信息.
	 * 传入的参数是产品的编号，日期
	 * @return
	 */
	@Action("/product/journey")
	public String productJourney(){
		if (productId != null && productId > 0 && org.apache.commons.lang3.StringUtils.isNotEmpty(choseDate)) {	
			Date date=DateUtil.toDate(choseDate, "yyyy-MM-dd");
			boolean checkOnline=getSession("PRODUCT_PREVIEW_KEY")==null;
			List<ProdProductBranch> list=this.productServiceProxy.getProdBranchListAndOnline(productId, null, date,checkOnline);
			if(data==null || data.get("viewJourneyList")==null){
				if(data==null){
					data = new HashMap<String, Object>();
				}				
				data.put("viewJourneyList", pageService.getViewJourneyByProductId(productId));
			}
			for(ProdProductBranch ppb:list){
				if(ProductUtil.isAdultRoute(ppb)){
					if(ordNum.containsKey("param"+ppb.getProdBranchId())){
						adult=ordNum.get("param"+ppb.getProdBranchId());
					}
				}else if(ProductUtil.isChildTicet(ppb)){
					if(ordNum.containsKey("param"+ppb.getProdBranchId())){
						child=ordNum.get("param"+ppb.getProdBranchId());
					}
				}
				
				if(ppb.hasDefault()){
					prodBranchId=ppb.getProdBranchId();
				}
			}			
			ProdProductBranch branch=this.productServiceProxy.getProdBranchDetailByProdBranchId(prodBranchId, date,checkOnline);
			if(branch!=null){
				getProductJourneyFromProductId(branch.getProductId(),date,adult,child);
			}
		}		
		return "selfPack";
	}
	
	private void getProductJourneyFromProductId(Long productId,Date date,long adult,long child){
		prodProductJourneyDetail = pageService.getProductJourneyFromProductId(productId, date, adult, child);
		if (prodProductJourneyDetail.isSuccess()) {
			List<ViewProdProductJourney> viewProdProductJourneys = prodProductJourneyDetail.getProductJourneyList();
			Map<String, List<ProdProduct>> prodProductMap = null;
			//为prodProduct内的赋值
			Map<Long,Place> cachePlace = new HashMap<Long, Place>();
			for (ViewProdProductJourney viewProdProductJourney : viewProdProductJourneys) {
				prodProductMap = viewProdProductJourney.getProductMap();
				for (String key : prodProductMap.keySet()) {
					List<ProdProduct> prodProducts = prodProductMap.get(key);
					for (ProdProduct prodProduct : prodProducts) {
						Place comPlace = null;
						if(cachePlace.containsKey(prodProduct.getProductId())){
							comPlace =cachePlace.get(prodProduct.getProductId());
						}else{
							comPlace=prodProductPlaceService.getToDestByProductId(prodProduct.getProductId());
							if(comPlace==null){
								comPlace =new Place();
							}else{
								placePhoto.setPlaceId(comPlace.getPlaceId());
								comPlace.setPlacePhoto(placePhotoService.queryByPlacePhoto(placePhoto));	
							}
							//酒店信息
							PlaceHotel placeHotel =  placePageService.searchPlaceHotel(comPlace.getPlaceId());
							if(placeHotel==null){
								placeHotel = new PlaceHotel();
							}
							//酒店交通信息
							placeHotel.setHotelTrafficInfos(hotelTrafficInfoService.queryByPlaceId(comPlace.getPlaceId()));
							comPlace.setPlaceHotel(placeHotel);
							
						}							
						prodProduct.setToPlace(comPlace);
						if(StringUtils.equals(Constant.PRODUCT_TYPE.TRAFFIC.name(), key)){
							if(prodProduct.isTraffic()){
								ProdTraffic traffic = (ProdTraffic)prodProduct;
								if(traffic.getGoFlightId()!=null){
									traffic.setGoFlight(placeFlightService.queryPlaceFlightDetail(traffic.getGoFlightId()));
								}
								if(traffic.hasRound()&&traffic.getBackFlightId()!=null){
									traffic.setBackFlight(placeFlightService.queryPlaceFlightDetail(traffic.getBackFlightId()));
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 获取景区信息(地图请求参数)
	 * @return
	 */
	public String getPlaceInfoForMap() {
		StringBuffer params = new StringBuffer("");
		ViewProdProductJourneyDetail vppjd = getProdProductJourneyDetail();
		if (vppjd != null && vppjd.isSuccess()) {		
			for (ViewProdProductJourney vppj : vppjd.getProductJourneyList()) {
				Set<String> typeSet = vppj.getProductMap().keySet();
				Iterator it = typeSet.iterator();
				while(it.hasNext()) {
					String type = (String)it.next();					
					if (Constant.PRODUCT_TYPE.HOTEL.name().equals(type) || Constant.PRODUCT_TYPE.TICKET.name().equals(type)){
						List<ProdProduct> prodList = vppj.getProductMap().get(type);	
						if (prodList!=null && prodList.size()>0) {				
							for (ProdProduct prod : vppj.getProductMap().get(type)) {
								Place place = prod.getToPlace();
								if (place != null && place.getPlaceId() != null) {
									String placeIdStr = place.getPlaceId() + "," + type + ",";
									for (ProdJourneyProduct pjp : prod.getProdJourneyProductList()) {
										params.append(placeIdStr+ "," + pjp.hasRequire() + "," + pjp.hasDefaultProduct() + "," + pjp.getProdBranchId() +";");
									}
								}
							}
						}
					}
				}				
			}
		}
		return params.toString();
	}

	public ViewPage getViewPage() {
		if(tmpViewPage==null){//避免多次取值，为空时给默认值
			if(data.containsKey("viewPage")){
				tmpViewPage=(ViewPage)data.get("viewPage");
			}			
			if (tmpViewPage == null) {
				tmpViewPage = new ViewPage();
			}
		}
		return tmpViewPage;
	}
	
	public String getProductQuality() {
		ViewPage viewPage = getViewPage();
		
		ViewContent viewContent =
			viewPage.getContents()!=null ? (ViewContent)viewPage.getContents().get("PRODUCTQUALITY") : null;
		if (viewContent != null) {			
			return viewContent.getContent();
		}
		
		return null;
	}
	
	/**
	 * 获取产品详情页更多点评有关的信息
	 * @param productId
	 * @return
	 */
	private void fillMoreCommentInfo(Long productId) throws Exception {
		
		//获得产品点评相关信息
		productComments = cmtCommentService.getNewestCommentByProductID(productId, 5);
		productComments = cmtCommentService.composeUserImagOfComment(productComments);
		
		productCommentStatistics = cmtTitleStatistisService.getCmtTitleStatisticsByProductId(productId);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		productCmtLatitudeStatisticsVOList = cmtLatitudeStatistisService.getFourAvgLatitudeScoreList(params);
		
		 //获取产品目的地景区点评
		if (prodCProduct != null && prodCProduct.getTo() != null ) {
			if (null != this.getComPlace()) {
				comments = cmtCommentService.getNewestCommentByPlaceId(getComPlace().getPlaceId(), 6);
				commentStatistics = cmtTitleStatistisService.getCmtTitleStatisticsByPlaceId(getComPlace().getPlaceId());
			}
		}
	}
		
	/**
	 * 获取手机返现金额-小数向上取整
	 */
	private void setMobileMultiple(){
		long _money = prodCProduct.getProdProduct().getCashRefundY();
		double _beishuMoney = 0;
		if(_money > 0){
			if(Constant.PRODUCT_TYPE.HOTEL.name().equals(prodCProduct.getProdProduct().getProductType())){
				_beishuMoney = (double)_money * Constant.getInstance().getHotelMultiple();
			}else if(Constant.PRODUCT_TYPE.TICKET.name().equals(prodCProduct.getProdProduct().getProductType())){
				_beishuMoney = (double) _money * Constant.getInstance().getTicketMultiple();
			}else if(Constant.PRODUCT_TYPE.ROUTE.name().equals(prodCProduct.getProdProduct().getProductType())){
				_beishuMoney = (double) _money * Constant.getInstance().getRouteMultiple();
			}
		}
		this.mobileMoney = (long) Math.ceil(_beishuMoney);
	}
	
	/**
	 *  -----------------------------  get and set property -------------------------------------
	 */
	/**
	 * @return the ordNum
	 */
	public Map<String, Long> getOrdNum() {
		return ordNum;
	}

	/**
	 * @param ordNum the ordNum to set
	 */
	public void setOrdNum(Map<String, Long> ordNum) {
		this.ordNum = ordNum;
	}
	

	@SuppressWarnings("unchecked")
	public List<ViewJourney> getViewJourneyList() {
		return (List<ViewJourney>)data.get("viewJourneyList");
	}
	@SuppressWarnings("unchecked")
	public List<ComPicture> getComPictureList() {
		return (List<ComPicture>)data.get("comPictureList");
	}
	
	@SuppressWarnings("unchecked")
	public List<ViewTravelTips> getViewTravelTipsList(){
		return (List<ViewTravelTips>)data.get("viewTravelTipsList");
	}

	public Place getComPlace() {
		return prodCProduct.getTo();
	}

	@SuppressWarnings("unchecked")
	public List<ProdProduct> getChildProdProductList() {
		return (List<ProdProduct>)data.get("childProdProductList");
	}
	
	@SuppressWarnings("unchecked")
	public List<ProdProductBranch> getProdProductBranchList() {
		return (List<ProdProductBranch>)data.get("prodProductBranchList");
	}
	
	@SuppressWarnings("unchecked")
	public List<MarkCoupon> getCouponActivityList() {
		return (List<MarkCoupon>)data.get("couponActivityList");
	}
	public String getHavePack() {
		List<ProdProductJourneyPack> packs = (List<ProdProductJourneyPack>)data.get("prodProdutJourneyPackList");
		if(packs!=null&&packs.size()>0){
			for(ProdProductJourneyPack pack:packs){
				if("true".equalsIgnoreCase(pack.getOnLine())){
					return "true";
				}
			}
		}
		return "false";
	}
	@SuppressWarnings("unchecked")
	public ProdProductBranch getProdBranch() {
		return (ProdProductBranch)data.get("prodBranch");
	}
	
	@SuppressWarnings("unchecked")
	public List<ProdProductJourneyPack> getProdProdutJourneyPackList() {
		return (List<ProdProductJourneyPack>)data.get("prodProdutJourneyPackList");
	}
	
	public String getIsSell() {
		return isSell;
	}

	public void setIsSell(String isSell) {
		this.isSell = isSell;
	}

	public String[] getServiceGuarantee() {
		return (String[])data.get("serviceGuarantee");
	}

	public String[] getOrderToKnown() {
		return (String[])data.get("orderToKnown");
	}
	
	public ProdCProduct getProdCProduct() {
		return prodCProduct;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getPlacesBlocks() {
		return (Map<String, Object>)data.get("placesBlocks");
	}
 
	public ProductList getGuestProductList() {
		return (ProductList)data.get("guestProductList");
	}
	
//	public ViewPlaceInfo getViewPlaceInfo() {
//		return (ViewPlaceInfo)data.get("viewPlaceInfo");
//	}

	@SuppressWarnings("unchecked")
	public List<CalendarInfo> getChildCalendarInfo() {
		return (List<CalendarInfo>)data.get("childCalendarInfo");
	}

	@SuppressWarnings("unchecked")
	public List<CalendarModel> getCmList() {
		return (List<CalendarModel>)data.get("cmList");
	}
	
	public String getChoseDate() {
		return choseDate;
	}
	public void setChoseDate(String choseDate) {
		this.choseDate = choseDate;
	}
	@SuppressWarnings("unchecked")
	public List<MarkCoupon> getProductCouponList() {
		return (List<MarkCoupon>)data.get("productCouponList");
	}
	@SuppressWarnings("unchecked")
	public List<SupSupplier> getSuppliers() {
		return (List<SupSupplier>)data.get("suppliers");
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HashMap<Object, List> getRoomList() {
		return (HashMap<Object, List>)data.get("roomList");
	}
	@SuppressWarnings("unchecked")
	public List<ProdProduct> getAddtionalServices() {
		return (List<ProdProduct>)data.get("addtionalServices");
	}
	
	@SuppressWarnings("unchecked")
	public List<ViewPlaceCoordinate> getPlaceCoordinateHotel() {
		return (List<ViewPlaceCoordinate>)data.get("placeCoordinateHotel");
	}
 
	public List<CommonCmtCommentVO> getComments() {
		return comments;
	}
	/**
	 * @return the errorProduct
	 */
	public ProdProduct getErrorProduct() {
		return errorProduct;
	}

	/**
	 * @param errorProduct the errorProduct to set
	 */
	public void setErrorProduct(ProdProduct errorProduct) {
		this.errorProduct = errorProduct;
	}

	/**
	 * @return the errorProductType
	 */
	public String getErrorProductType() {
		return errorProductType;
	}

	/**
	 * @param errorProductType the errorProductType to set
	 */
	public void setErrorProductType(String errorProductType) {
		this.errorProductType = errorProductType;
	}

	/**
	 * @return the errorSubProductType
	 */
	public String getErrorSubProductType() {
		return errorSubProductType;
	}

	/**
	 * @param errorSubProductType the errorSubProductType to set
	 */
	public void setErrorSubProductType(String errorSubProductType) {
		this.errorSubProductType = errorSubProductType;
	}

	public String getPinYinUrl() {
		return pinYinUrl;
	}
 
	/**
	 * @return the adult
	 */
	public long getAdult() {
		return adult;
	}


	/**
	 * @return the child
	 */
	public long getChild() {
		return child;
	}

	

	/**
	 * @return the prodProductJourneyDetail
	 */
	public ViewProdProductJourneyDetail getProdProductJourneyDetail() {
		return prodProductJourneyDetail;
	}

	public CmtTitleStatisticsVO getCommentStatistics() {
		return commentStatistics;
	}

	public List<CommonCmtCommentVO> getProductComments() {
		return productComments;
	}

	public CmtTitleStatisticsVO getProductCommentStatistics() {
		return productCommentStatistics;
	}

	/**
	 * @param productCmtLatitudeStatisticsVOList the productCmtLatitudeStatisticsVOList to set
	 */
	public void setProductCmtLatitudeStatisticsVOList(
			List<CmtLatitudeStatistics> productCmtLatitudeStatisticsVOList) {
		this.productCmtLatitudeStatisticsVOList = productCmtLatitudeStatisticsVOList;
	}

	/**
	 * @return the productCmtLatitudeStatisticsVOList
	 */
	public List<CmtLatitudeStatistics> getProductCmtLatitudeStatisticsVOList() {
		return productCmtLatitudeStatisticsVOList;
	}

	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}

	public void setCmtTitleStatistisService(
			CmtTitleStatistisService cmtTitleStatistisService) {
		this.cmtTitleStatistisService = cmtTitleStatistisService;
	}

	public void setCmtLatitudeStatistisService(
			CmtLatitudeStatistisService cmtLatitudeStatistisService) {
		this.cmtLatitudeStatistisService = cmtLatitudeStatistisService;
	}

	public void setCmtCommentService(CmtCommentService cmtCommentService) {
		this.cmtCommentService = cmtCommentService;
	}

	public void setRecommendInfoClient(RecommendInfoClient recommendInfoClient) {
		this.recommendInfoClient = recommendInfoClient;
	}

	public long getProdBranchId() {
		return prodBranchId;
	}

	public void setPlaceFlightService(PlaceFlightService placeFlightService) {
		this.placeFlightService = placeFlightService;
	}

	public void setProdProductPlaceService(
			ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}

	public void setVisaApplicationDocumentService(
			VisaApplicationDocumentService visaApplicationDocumentService) {
		this.visaApplicationDocumentService = visaApplicationDocumentService;
	}

	public List<VisaVO> getVisaVOList() {
		return visaVOList;
	}

	public void setVisaVOList(List<VisaVO> visaVOList) {
		this.visaVOList = visaVOList;
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

	public void setProductSearchInfoService(
			ProductSearchInfoService productSearchInfoService) {
		this.productSearchInfoService = productSearchInfoService;
	}

	public void setPlacePhotoService(PlacePhotoService placePhotoService) {
		this.placePhotoService = placePhotoService;
	}
	
	public boolean hasMultiJourney() {
		return isMultiJourney;
	}

	public ProdEContract getProdEContract() {
		return prodEContract;
	}

	public void setProdEContract(ProdEContract prodEContract) {
		this.prodEContract = prodEContract;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setPlacePageService(PlacePageService placePageService) {
		this.placePageService = placePageService;
	}

	public void setProdSeckillRuleService(
			ProdSeckillRuleService prodSeckillRuleService) {
		this.prodSeckillRuleService = prodSeckillRuleService;
	}

	public void setHotelTrafficInfoService(
			HotelTrafficInfoService hotelTrafficInfoService) {
		this.hotelTrafficInfoService = hotelTrafficInfoService;
	}

	public String getQrFlag() {
		return qrFlag;
	}

	public void setQrFlag(String qrFlag) {
		this.qrFlag = qrFlag;
	}

	public long getMobileMoney() {
		return mobileMoney;
	}

	public void setMobileMoney(long mobileMoney) {
		this.mobileMoney = mobileMoney;
	}

    public String getCouponEnabled() {
        return couponEnabled;
    }

    public void setCouponEnabled(String couponEnabled) {
        this.couponEnabled = couponEnabled;
    }
}
