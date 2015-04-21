package com.lvmama.prd.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.com.dao.ComPlaceDAO;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.LimitSaleTime;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductJourneyPack;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.RouteProduct;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewJourneyTips;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.po.prod.ViewTravelTips;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductJourneyService;
import com.lvmama.comm.bee.vo.view.ViewProdProductJourneyDetail;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdProductChannel;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ProdCProduct;
import com.lvmama.comm.vo.ProductResult;
import com.lvmama.prd.dao.MetaProductBranchDAO;
import com.lvmama.prd.dao.MetaProductDAO;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdProductDAO;
import com.lvmama.prd.dao.ProdTimePriceDAO;
import com.lvmama.prd.dao.RouteProductDAO;
import com.lvmama.prd.dao.ViewJourneyDAO;
import com.lvmama.prd.dao.ViewJourneyTipDAO;
import com.lvmama.prd.dao.ViewPageDAO;
import com.lvmama.prd.dao.ViewTravelTipsDAO;
import com.lvmama.prd.logic.ProdJourneyLogic;
import com.lvmama.prd.logic.ProductResourceConfirmLogic;
import com.lvmama.prd.logic.ProductTimePriceLogic;

public class PageServiceImpl implements PageService {
	protected static final Log LOG = LogFactory.getLog(PageServiceImpl.class);

	private ViewPageDAO viewPageDAO;
	private ViewJourneyDAO viewJourneyDAO;
	private RouteProductDAO routeProductDAO;
	private ComPlaceDAO comPlaceDAO;
	private MarkCouponService markCouponService;
	private ProdProductJourneyService prodProductJourneyService;
	private ProdProductDAO prodProductDAO;
	private ProdTimePriceDAO prodTimePriceDAO;
	private ProductTimePriceLogic productTimePriceLogic;
	private MetaProductBranchDAO metaProductBranchDAO;
	private MetaProductDAO metaProductDAO;
	private ProdProductBranchDAO prodProductBranchDAO;
 	private ProductResourceConfirmLogic productResourceConfirmLogic;
	private ViewJourneyTipDAO viewJourneyTipDAO;
	private ProdJourneyLogic prodJourneyLogic;
	private ProdProductBranchService prodProductBranchService;
	private ViewTravelTipsDAO viewTravelTipsDAO;

	private ProdProduct record;

	@Override
	public MetaProduct getMetaProductByMetaProductId(Long metaProductId){
		return metaProductDAO.getMetaProductByPk(metaProductId);
	}

	public ProdCProduct getProdCProduct(long productId){  // select 
		ProdCProduct prodCProduct = new ProdCProduct();
		ProdProduct pp = this.getProdProductById(productId);
		
		if(pp==null){			
			return null;
		}
		
		prodCProduct.setProdProduct(pp); //基本信息
		//后台已经逻辑删除或者产品已经下线
		if(pp.isDisabled()){
			LOG.info("prodProduct isDisabled or isNotOnLine,pageId:"+productId);
			return prodCProduct;
		}
		if (prodCProduct.getProdProduct().getProductType().equals(Constant.PRODUCT_TYPE.TICKET.name())){ //门票 
			prodCProduct.setProdTicket(prodProductDAO.selectProdTicketByPrimaryKey(productId));
			prodCProduct.setTo(comPlaceDAO.getToDestByProductId(productId)); //目的地
		}else if(prodCProduct.getProdProduct().getProductType().equals(Constant.PRODUCT_TYPE.ROUTE.name())){ //线路
			prodCProduct.setProdRoute(prodProductDAO.selectProdRouteByPrimaryKey(productId));
			prodCProduct.setFrom(comPlaceDAO.getFromDestByProductId(productId)); //出发地
			prodCProduct.setTo(comPlaceDAO.getToDestByProductId(productId)); //目的地
		}else if(prodCProduct.getProdProduct().getProductType().equals(Constant.PRODUCT_TYPE.HOTEL.name())) {	//酒店
			prodCProduct.setProdHotel(prodProductDAO.selectProdHotelByPrimaryKey(productId));
			prodCProduct.setTo(comPlaceDAO.getToDestByProductId(productId)); //目的地
		}
		return prodCProduct;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getProdCProductInfo(long productId, boolean isPreview){  
		Map<String, Object> data = new HashMap<String, Object>();
		ProdCProduct prodCProduct = getProdCProduct(productId);   
		if (prodCProduct==null || prodCProduct.getProdProduct()==null) {
			LOG.info("无法找到产品。ProductID=" + productId);
			return data;
		} 
		data.put("prodCProduct", prodCProduct);
		// 产品是否可用
		if (prodCProduct.getProdProduct().isDisabled()) {
			LOG.info("产品不可用。ProdProduct的Valid = N");
			data.put("errorProduct", prodCProduct.getProdProduct());
			return data;
		}	
		
		// 产品所有优惠卷(包括优惠卷和优惠活动)  
		//mod by ljp 20120518
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("productId", productId);
    	map.put("subProductType", prodCProduct.getProdProduct().getSubProductType());
		List<MarkCoupon> productCouponList = this.markCouponService.selectProductCanUseMarkCoupon(map);
		data.put("productCouponList", productCouponList);
		
		// 产品所有优惠活动
		List<MarkCoupon> couponActivityList = new ArrayList<MarkCoupon>();
		for (MarkCoupon markCoupon : productCouponList) {                   //TODO 缺少for,if的右括号.
			if ("false".equalsIgnoreCase(markCoupon.getWithCode())) {
				couponActivityList.add(markCoupon);         
			}
		}
		//获取产品类型 用户查询产品优惠券/活动 add by yanggan  20120301
		String subProductType = prodCProduct.getProdProduct().getSubProductType();
		map = new HashMap<String,Object>();
    	map.put("productId", productId);
    	map.put("subProductType", subProductType);
		List<MarkCoupon> markCouponList = this.markCouponService.selectProductCanUseMarkCoupon(map);
		data.put("productCouponList", markCouponList);
		data.put("viewPage",viewPageDAO.getViewPageByProductId(productId));
		Map<String,Object> placesBlocks;
		Object obj = MemcachedUtil.getInstance().get("VIEW_PRODUCT_PLACES_"+productId);
		if (obj!=null) {
			placesBlocks=(Map<String,Object>)obj;
		}else{
			placesBlocks=navigationPlaces(prodCProduct.getTo());
			if (placesBlocks != null) {
				MemcachedUtil.getInstance().set("VIEW_PRODUCT_PLACES_" + productId,
						MemcachedUtil.getDateAfter(120),placesBlocks);
			}
		}		
		data.put("couponActivityList", couponActivityList);
		
		// 产品页面导航 (先从缓存中读取，否则从数据库中读取)				
		data.put("placesBlocks", placesBlocks);		
		
		List<ProdProductBranch> prodBranchList = this.getProductBranchByProductId(prodCProduct.getProdProduct().getProductId(), "false");
		//不定期产品,已过期的类别不显示,因为不会显示时间价格表和日期选择下拉框,没有机会做当天时间价格库存校验,所以在初始化时做校验,用最晚有效期充当游玩日期    add by shihui
		if(prodCProduct.getProdProduct().IsAperiodic()) {
			List<ProdProductBranch> branchList = new ArrayList<ProdProductBranch>();
			ProdProductBranch defaultBranch = null;
			for (ProdProductBranch prodProductBranch : prodBranchList) {
				Date validEndTime = prodProductBranch.getValidEndTime();
				if(prodProductBranch.getValidBeginTime() != null && validEndTime != null) {
					if(!DateUtil.getDayStart(new Date()).after(DateUtil.getDayStart(validEndTime))) {
						ProdProductBranch branch = this.prodProductBranchService.getProductBranchDetailByBranchId(prodProductBranch.getProdBranchId(), validEndTime,!isPreview);
						if(branch != null) {
							branchList.add(branch);
							//如果默认类别可售,则取该值
							if(StringUtils.isNotEmpty(branch.getDefaultBranch()) && "true".equalsIgnoreCase(branch.getDefaultBranch())) {
								defaultBranch = branch;
							}
						}
					}
				}
			}
			//默认类别可能存在不可售的情况,所以取第一个可售类别为默认类别
			if(defaultBranch == null && !branchList.isEmpty()) {
				defaultBranch = branchList.get(0);
			}
			data.put("prodBranch", defaultBranch);
			// 产品类别			
			data.put("prodProductBranchList", branchList);
		} else {

			// 产品默认类别
			ProdProductBranch prodBranch = prodProductBranchDAO.selectDefaultBranchByProductId(productId);
			data.put("prodBranch", prodBranch);
			// 产品类别			
			data.put("prodProductBranchList", prodBranchList);
		}
 
		//单行程按原来逻辑展示,多行程在日历框加载完成后发起请求展示
		if(prodCProduct.getProdProduct().isRoute()) {
			ProdRoute pr = (ProdRoute) prodCProduct.getProdProduct();
			if(!pr.hasMultiJourney()) {
				// 行程
				List<ViewJourney> viewJourneyList = viewJourneyDAO.getViewJourneysByProductId(productId);
				fillJourneyTipsList(viewJourneyList);
				data.put("viewJourneyList", viewJourneyList);
			}
		}
		
		//套餐信息
		List<ProdProductJourneyPack> prodProdutJourneyPackList = prodProductJourneyService.queryProductJourneyPackByProductId(productId);
		if(prodProdutJourneyPackList!=null&&prodProdutJourneyPackList.size()>0){
			data.put("prodProdutJourneyPackList", prodProdutJourneyPackList);
		}
		
		//线路产品绑定的旅行须知
		if(prodCProduct.getProdProduct().isRoute()) {
			List<ViewTravelTips> viewTravelTipsList = this.viewTravelTipsDAO.selectByProductId(productId); 
			data.put("viewTravelTipsList", viewTravelTipsList);
		}
		
		return data;
	} 
	public boolean isResourceConfirm(Long prodBranchId,Date visitDate){
		//资源需确认
		boolean resourceConfirm = false;
		if (visitDate != null) {
			List<MetaProductBranch> list = metaProductBranchDAO
					.getMetaProductBranchByProdBranchId(prodBranchId);
			for (MetaProductBranch metaBranch : list) {
				resourceConfirm = productResourceConfirmLogic
						.isResourceConfirm(metaBranch, visitDate);
				if(resourceConfirm){//如果已经遇到需要资源确认就跳出
					break;
				}
			}
		}
		return resourceConfirm;
	}
	
	public boolean isVisitDateProduct(Long productId,Long prodBranchId, Date visitDate) {
		TimePrice timePrice = prodTimePriceDAO.getProdTimePrice(productId,prodBranchId, visitDate);
		if(timePrice == null){
			return false;
		}
 		return true;
	}
	
	private Map<String,Object> navigationPlaces(Place leaf){
		if (leaf == null){
			return null;
		}
		List<Place> navigation = new ArrayList<Place>();
		navigation.add(leaf);
		loadPlaces(navigation, leaf);
		//面包屑place
		List<Place> navigationPlace = new ArrayList<Place>();
		
		HashMap<Long,Object> item=new HashMap<Long,Object>();
		//层级变成从大到小。中国》江苏》南京
		for (int i = navigation.size()-1; i >= 0; i--) {
			Place p = navigation.get(i);
			Map<String,Object> par=new HashMap<String,Object>();
			if(p.getParentPlaceId()!=null){
				par.put("destId", p.getParentPlaceId());
				par.put("stage", "0".equals(p.getStage()) ? "1" : p.getStage());
				item.put(p.getPlaceId(), comPlaceDAO.findComPlaceSimple(par));
			}
			navigationPlace.add(p);
		}
		
		Map<String,Object> placeBlocks = new HashMap<String, Object>();
		placeBlocks.put("navigation", navigationPlace);
		placeBlocks.put("brothers", item);
 			
		return placeBlocks;
	} 
	
	private void loadPlaces(List<Place> placesBlocks,Place leaf){
		if (placesBlocks.size() >= 1000) {
			LOG.error("Please check  the parent  of place id = " + leaf.getPlaceId() + ", name = " + leaf.getName());
			return;
		}
		
		if(leaf!=null){
			Long destId=leaf.getParentPlaceId();
 			if (destId!=null){
 				Place comPlace = comPlaceDAO.getComPlaceByDestId(destId);
 				placesBlocks.add(comPlace);
				if(comPlace!=null && comPlace.getParentPlaceId()!=null && !destId.equals(comPlace.getParentPlaceId())){
					loadPlaces(placesBlocks,comPlace);
				}
			}else{
				placesBlocks.add(leaf);
			}
		}
 	}
 
	
	public Place getToDestByProductId(Long productId){
		return comPlaceDAO.getToDestByProductId(productId);
	}
	
	public ViewPage getViewPageByproductId(long productId) {
		return viewPageDAO.getViewPageByProductId(productId);
	}

	public ViewPage getViewPage(Long id) {
		return viewPageDAO.selectByPrimaryKey(id);
	}

	public void setViewPageDAO(ViewPageDAO viewPageDAO) {
		this.viewPageDAO = viewPageDAO;
	}

	public List<ViewJourney> getViewJourneyByProductId(long productId) {
		return viewJourneyDAO.getViewJourneysByProductId(productId);
	}

	public void setViewJourneyDAO(ViewJourneyDAO viewJourneyDAO) {
		this.viewJourneyDAO = viewJourneyDAO;
	}

	public RouteProduct getPageRouteProductByProductId(long productId) {
		return this.routeProductDAO.getProductByProductId(productId);
	}
	public void setComPlaceDAO(ComPlaceDAO comPlaceDAO) {
		this.comPlaceDAO = comPlaceDAO;
	}


	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public void setProductTimePriceLogic(ProductTimePriceLogic productTimePriceLogic) {
		this.productTimePriceLogic = productTimePriceLogic;
	}
 
	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}

	@Override
	public boolean isGroupProduct(Long productId) {
		 List<ProdProductChannel> prdChannelList = this.prodProductDAO.getProductChannelByProductId(productId);
		 for(ProdProductChannel prdChannel:prdChannelList){
			 if(Constant.CHANNEL.TUANGOU.name().equals(prdChannel.getProductChannel())){
				 return true;
			 }
		 }
		 
		return false;
	}

	@Override
	public boolean checkDateCanSale(Long id, Date choseDate) {
		LimitSaleTime limitSaleTime =	productTimePriceLogic.getLimitSaleTime(id, choseDate);
		if(limitSaleTime != null){
			//hourRange用来标识时间点的限售类型
			if (StringUtils.equals(limitSaleTime.getLimitType(),Constant.LIMIT_SALE_TYPE.HOURRANGE.getCode())) {
				String now = DateUtil.formatDate(new Date(), "HH:mm");
				if (!(DateUtil.toDate(now, "HH:mm").after(DateUtil.toDate(limitSaleTime.getLimitHourStart(), "HH:mm"))
						&& DateUtil.toDate(now, "HH:mm").before(DateUtil.toDate(limitSaleTime.getLimitHourEnd(), "HH:mm")))) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public LimitSaleTime getDateCanSale(Long id, Date choseDate) {
		LimitSaleTime limitSaleTime=productTimePriceLogic.getLimitSaleTime(id, choseDate);
		if(limitSaleTime!=null){
			return limitSaleTime;
		}
		return null;
	}

	@Override
	public ProdProduct getProdProductByProductId(Long productId) {
		return this.prodProductDAO.selectByPrimaryKey(productId);
	}
	
	public ProdProductBranch getProdBranchByProdBranchId(Long prodBranchId){
		ProdProductBranch prodProductBranch = prodProductBranchDAO.selectByPrimaryKey(prodBranchId);
		if(prodProductBranch != null){
			ProdProduct prodProduct = this.prodProductDAO.selectProductDetailByPrimaryKey(prodProductBranch.getProductId());
			prodProductBranch.setProdProduct(prodProduct);
		}
		return prodProductBranch;
	}
	
	public ProdProductBranch selectDefaultBranchByProductId(Long productId) {
		ProdProductBranch branch=this.prodProductBranchDAO.selectDefaultBranchByProductId(productId);
		if(branch!=null){
			branch.setProdProduct(getProdProductById(branch.getProductId()));
		}
		return branch;
	}
	/**
	 * @param prodProductBranchDAO the prodProductBranchDAO to set
	 */
	public void setProdProductBranchDAO(ProdProductBranchDAO prodProductBranchDAO) {
		this.prodProductBranchDAO = prodProductBranchDAO;
	}
 	
	public void setProductResourceConfirmLogic(
			ProductResourceConfirmLogic productResourceConfirmLogic) {
		this.productResourceConfirmLogic = productResourceConfirmLogic;
	}
	/**
	 * @param metaProductBranchDAO the metaProductBranchDAO to set
	 */
	public void setMetaProductBranchDAO(MetaProductBranchDAO metaProductBranchDAO) {
		this.metaProductBranchDAO = metaProductBranchDAO;
	}


	@Override
	public List<ProdProductBranch> getProdBranchListByProductId(Long productId) {
		return prodProductBranchDAO.getProductBranchByProductId(productId, "false");
	}


	public void setProdTimePriceDAO(ProdTimePriceDAO prodTimePriceDAO) {
		this.prodTimePriceDAO = prodTimePriceDAO;
	}
	
	public List<ProdProductBranch> getProductBranchByProductId(Long productId, String additional) {
		return prodProductBranchDAO.getProductBranchByProductId(productId, "false");
	}
	
	/**
	 * 取行程目的地相关信息
	 */
	@Override
	public ViewProdProductJourneyDetail getProductJourneyFromProductId(Long productId, Date visitTime, Long adult, Long child) {
		return prodJourneyLogic.getProductJourneyFromProductId(productId, visitTime, adult, child,true,null);		
	}
	
	/**
	 * 根据ID号查询产品
	 * @param productId
	 * @return ProdProduct
	 */
	public ProdProduct getProdProductById(Long id) {
		ProdProduct product = prodProductDAO.selectProductDetailByPrimaryKey(id);	
		return product;
	}
	
	public ProductResult findProduct(Long id){
		ProductResult pr=new ProductResult();
		ProdProduct product = prodProductDAO.selectProductDetailByPrimaryKey(id);
		if(product!=null){
			pr.setStatus(ProductResult.Status.Find);
		}else if(product == null&& id<80000L){//产品ID如果是在			
			//LOG.info("产品表中找不到 productId=" + id+" 的产品，用该ID继续查产品类别表");
			ProdProductBranch ppb = prodProductBranchDAO.selectByPrimaryKey(id);
			if (ppb!=null) {				
				pr.setProductId(ppb.getProductId());		
				pr.setStatus(ProductResult.Status.Branch);
			} else {
				//LOG.info("类别表中找不到产品类别，branchId=" + id);
			}
		}
		return pr;
	}

	/**
	 * 填充行程小贴士
	 */
	public List<ViewJourney> fillJourneyTipsList(List<ViewJourney> viewJourneyList) {
		for (ViewJourney vj : viewJourneyList) {
			List<ViewJourneyTips> journeyTipsList = viewJourneyTipDAO.getViewJourneyTipsByJourneyId(vj.getJourneyId());
		    if (journeyTipsList != null && journeyTipsList.size() != 0) {
		    	vj.setJourneyTipsList(journeyTipsList);
		    }
		}
		return viewJourneyList;
	}
 
	public void setViewJourneyTipDAO(ViewJourneyTipDAO viewJourneyTipDAO) {
		this.viewJourneyTipDAO = viewJourneyTipDAO;
	}

	/**
	 * @param prodJourneyLogic the prodJourneyLogic to set
	 */
	public void setProdJourneyLogic(ProdJourneyLogic prodJourneyLogic) {
		this.prodJourneyLogic = prodJourneyLogic;
	}
	
	public void setMetaProductDAO(MetaProductDAO metaProductDAO) {
		this.metaProductDAO = metaProductDAO;
	}

	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public ProdProductJourneyService getProdProductJourneyService() {
		return prodProductJourneyService;
	}

	public void setProdProductJourneyService(
			ProdProductJourneyService prodProductJourneyService) {
		this.prodProductJourneyService = prodProductJourneyService;
	}
	
	public List<ViewJourney> getViewJourneyByMultiJourneyId(long multiJourneyId) {
		return viewJourneyDAO.getViewJourneyByMultiJourneyId(multiJourneyId);
	}

	public void setViewTravelTipsDAO(ViewTravelTipsDAO viewTravelTipsDAO) {
		this.viewTravelTipsDAO = viewTravelTipsDAO;
	}

	@Override
	public boolean isInteriorExist(Long productId) {
		List<ViewContent> contentList = viewPageDAO.queryForList("VIEW_CONTENT.getViewContentBypageId", productId);
		if(!contentList.isEmpty()) {
			for (int i = 0; i < contentList.size(); i++) {
				ViewContent vc = contentList.get(i);
				if(Constant.VIEW_CONTENT_TYPE.INTERIOR.name().equalsIgnoreCase(vc.getContentType())) {
					if(StringUtils.isNotEmpty(vc.getContentRn())) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
