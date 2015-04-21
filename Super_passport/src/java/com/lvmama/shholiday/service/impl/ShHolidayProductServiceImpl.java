package com.lvmama.shholiday.service.impl;

import java.beans.PropertyEditor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.meta.MetaProductRoute;
import com.lvmama.comm.bee.po.meta.MetaTravelCode;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductBranchItem;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.prod.TimeRange;
import com.lvmama.comm.bee.po.prod.TimeRange.TimeRangePropertEditor;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.meta.MetaTravelCodeService;
import com.lvmama.comm.bee.service.op.IOpTravelGroupService;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.service.prod.ProdProductRelationService;
import com.lvmama.comm.bee.service.prod.ProdProductTagService;
import com.lvmama.comm.bee.service.view.ViewPageJourneyService;
import com.lvmama.comm.bee.service.view.ViewPageService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.businessCoupon.BusinessCoupon;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdEContract;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.po.sup.MetaBCertificate;
import com.lvmama.comm.pet.po.sup.MetaPerform;
import com.lvmama.comm.pet.po.sup.MetaSettlement;
import com.lvmama.comm.pet.service.businessCoupon.BusinessCouponService;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.HttpsUtil.HttpResponseWrapper;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.utils.pic.UploadCtrl;
import com.lvmama.comm.vo.Constant;
import com.lvmama.hotel.service.BaseHotelProductService;
import com.lvmama.passport.utils.WebServiceConstant;
import com.lvmama.shholiday.SHholidayConstant;
import com.lvmama.shholiday.ShholidayClient;
import com.lvmama.shholiday.request.ProductDetailRequest;
import com.lvmama.shholiday.request.ProductInfoListReqeust;
import com.lvmama.shholiday.response.ProductDetailResponse;
import com.lvmama.shholiday.response.ProductInfoListResponse;
import com.lvmama.shholiday.service.ShHolidayProductService;
import com.lvmama.shholiday.vo.product.ProductInfo;
import com.lvmama.shholiday.vo.product.ProductPrice;

public class ShHolidayProductServiceImpl extends BaseHotelProductService implements ShHolidayProductService{
	private static final Log log = LogFactory.getLog(ShHolidayProductServiceImpl.class);

//	private static final Long AHEAD_BOOKING_DAYS = 7L;
	
	private static final Long CANCELHOUR = 1440L;//最晚修改或者取消小时分钟数 一天
	
	private static final Long AHEADHOUR = 10080L;//最少提前预定小时分钟数默认 七天
	
	private static final Integer SHOW_DAY = 180;

	private ShholidayClient shholidayClient;
	
	private MetaTravelCodeService metaTravelCodeService;
	private PermUserService permUserService;
	
	private BCertificateTargetService bCertificateTargetService;
	private PerformTargetService performTargetService;
	private SettlementTargetService settlementTargetService;
	private ViewPageService viewPageService;
	private ComPictureService comPictureService;
	private ViewPageJourneyService viewPageJourneyService;
	private ProdProductPlaceService prodProductPlaceService;
	private PlaceService placeService;
	private BusinessCouponService businessCouponService;
	private ProdProductRelationService prodProductRelationService;
	private ProdProductTagService prodProductTagService;
	private TopicMessageProducer productMessageProducer;
	private IOpTravelGroupService opTravelGroupService;
	/**
	 * 根据前台action的查询条件获得结果集
	 * @return
	 */
	public Page<ProductInfo> selectProductInfoByCondition(Long currentPage,Long pageSize,Map<String,Object> paramMap){
		Long supplierId = Long.valueOf(WebServiceConstant.getProperties("shholiday.supplierId"));
		Page<ProductInfo> page = new Page<ProductInfo>();
		String destination = (String)paramMap.get("destination");
		//城市三位码
		String destinationCode = SHholidayConstant.DestinationCode.getDestinationCode(destination);
		String productType = (String)paramMap.get("routeType");
		String productName = (String)paramMap.get("keyword");
		int pageNo = paramMap.get("pageNo") == null ? 1:Integer.parseInt(paramMap.get("pageNo").toString());
		//分页查询
		page.setTotalResultSize(100);
		ProductInfoListReqeust request = new ProductInfoListReqeust(destinationCode,productName ,productType, 10, pageNo);
		ProductInfoListResponse response = shholidayClient.execute(request);
		
		List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierType(supplierId, null, null);
		List<ProductInfo> productInfoList = response.getProductInfos();
		for(ProductInfo info:productInfoList){
			initProductInfo(info, metaProductBranchs);
		}
		page.setCurrentPage(currentPage);
		page.setItems(productInfoList);
		
		return page;
	}
	
	
	/**
	 * action 的采购产品详情页面展示
	 */
	public ProductDetailResponse selectProductInfoById(Long productId){
		ProductDetailRequest request = new ProductDetailRequest(String.valueOf(productId), null, null,"all");
		ProductDetailResponse res = shholidayClient.execute(request);
		return res;
	}
	
	@Override
	public void updateAllProductInfo(Date startDate, Date endDate){
		Long supplierId = Long.valueOf(WebServiceConstant.getProperties("shholiday.supplierId"));
		List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierId(supplierId);
		for(MetaProductBranch metaBranch : metaProductBranchs){
			try {
				updateProductStocked(metaBranch,startDate,endDate);
			} catch (Exception e) {
				log.error("updateProduct Exception:" , e);
			}
		}
	}
	
	/**
	 * 更新一个产品
	 * @param metaBranch
	 * @param startDate
	 * @param endDate
	 */
	private void updateProductStocked(MetaProductBranch metaBranch,Date startDate, Date endDate){
		String userName = WebServiceConstant.getProperties("shholiday.supplier.username");
		PermUser user = permUserService.getPermUserByUserName(userName);
		String productId = metaBranch.getProductIdSupplier();
		ProductDetailRequest request = new ProductDetailRequest(productId, null, null,"all");
		ProductDetailResponse res = shholidayClient.execute(request);
		ProductInfo productInfo = res.getProductInfo();
		productInfo.setValid(true);
		ProdProduct prodProduct;
		try {
			prodProduct = saveProdProductInfo(productInfo,metaBranch.getMetaBranchId() ,user);
		} catch (Exception e) {
			log.error("updateProduc Exception:" ,e);
			return;
		}
		Long prodProductId = prodProduct.getProductId();
		
		try {
			saveViewContent(prodProductId,productInfo);
		} catch (Exception e) {
			log.error("updateViewContent Exception:" ,e);
		}
		
		try {
			saveViewJourney(prodProductId,productInfo);
		} catch (Exception e) {
			log.error("updatePic Exception:",e);
		}
		
		try {
			syncCoupon(metaBranch, startDate, endDate);
		} catch (Exception e) {
			log.error("updateCoupons Exception" , e);
		}
		
		// 发送修改销售产品的消息
		productMessageProducer.sendMsg(MessageFactory.newProductUpdateMessage(prodProductId));
		// 发送place变更消息
		productMessageProducer.sendMsg(MessageFactory.newProductPlaceUpdateMessage(prodProductId));
	}
	
	@Override
	public void productNotify(String productId){
		Long supplierId = Long.valueOf(WebServiceConstant.getProperties("shholiday.supplierId"));
		List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierType(supplierId, null, productId);
		Date startDate=new Date();
		Date endDate=DateUtils.addMonths(startDate, 2);
		for(MetaProductBranch metaBranch : metaProductBranchs){
			try {
				updateProductStocked(metaBranch,startDate,endDate);
			} catch (Exception e) {
				log.error("updateProduct Exception:" , e);
			}
		}
	}
	
	/**
	 * action 入库，针对从未入库过的供应商产品进行入库操作
	 * @throws Exception 
	 */
	public void saveMetaProductForUnStocked(String productId){
		Long supplierId = Long.valueOf(WebServiceConstant.getProperties("shholiday.supplierId"));
		List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierType(supplierId, null, productId);
		String userName = WebServiceConstant.getProperties("shholiday.supplier.username");
		PermUser user = permUserService.getPermUserByUserName(userName);
		ProductDetailRequest request = new ProductDetailRequest(productId, null, null,"all");
		ProductDetailResponse res = shholidayClient.execute(request);
		ProductInfo productInfo = res.getProductInfo();
		productInfo = initProductInfo(productInfo,metaProductBranchs);
		Long metaProductId = null;
		Long metaBranchId = null;
		if(metaProductBranchs != null && !metaProductBranchs.isEmpty()){
			metaProductId = metaProductBranchs.get(0).getMetaProductId();
			metaBranchId = metaProductBranchs.get(0).getMetaBranchId();
		}
		MetaProduct metaProduct = saveMetaProductInfo(productInfo ,metaProductId, user);
		ProdProduct prodProduct;
		try {
			prodProduct = saveProdProductInfo(productInfo,metaBranchId ,user);
		} catch (Exception e) {
			log.error("saveProduct Exception:" ,e);
			return;
		}
		
		Long prodProductId = prodProduct.getProductId();
		if(!productInfo.isValid()){
			addMetaTarget(metaProduct.getMetaProductId());
			List<MetaProductBranch> metaBranch =  saveMetaBranch(metaProduct, productInfo);
			List<ProdProductBranch> prodBranch = saveProdBranch(prodProduct);
			packMeta(prodBranch,metaBranch,productInfo);
			saveMetaProdCoupons(metaProduct.getMetaProductId(),metaBranch,res.getMetaProdCoupons());
			saveProdProdCoupons(prodProductId,prodBranch,res.getProdProdCoupons());
			savePicture(prodProductId,productInfo);
			saveProductPlace(prodProductId,productInfo);
		}
		try {
			saveViewContent(prodProductId,productInfo);
		} catch (Exception e) {
			log.error("updateViewContent Exception:" ,e);
		}
		
		
		try {
			saveViewJourney(prodProductId,productInfo);
		} catch (Exception e) {
			log.error("savePic Exception:",e);
		}
		
		try {
			for(Constant.SH_HOLIDAY_BRANCH_TYPE productType : Constant.SH_HOLIDAY_BRANCH_TYPE.values()){
				updateProductTimePrice(productId, productType.name(), null, null);
			}
		} catch (Exception e) {
			log.error("saveProductTimePrice Exception" , e);
		}
		try {
			List<MetaProductBranch> metaBranchs = metaProductBranchService.selectMetaProductBranchBySupplierType(supplierId, null , productInfo.getSupplierProdId());
			for(MetaProductBranch metaProductBranch : metaBranchs){
				syncCoupon(metaProductBranch, null, null);
			}
		} catch (Exception e) {
			log.error("saveProductCoupons Exception" , e);
		}

		// 发送修改销售产品的消息
		productMessageProducer.sendMsg(MessageFactory.newProductUpdateMessage(prodProductId));
		// 发送place变更消息
		productMessageProducer.sendMsg(MessageFactory.newProductPlaceUpdateMessage(prodProductId));
	}
	
	/**
	 * 上下线指定的产品
	 */
	public void onOfflineProduct(ProductInfo productInfo) throws Exception {
		Long supplierId = Long.valueOf(WebServiceConstant.getProperties("shholiday.supplierId"));
		boolean isHotelOnline = productInfo.isOnline();
		List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierType(supplierId,null , productInfo.getSupplierProdId());
		for (MetaProductBranch metaProductBranch : metaProductBranchs) {
			onOffline(metaProductBranch, isHotelOnline);
		}
	}
	
	/**
	 * 更新所有产品的时间价格库存
	 */
	public void updateAllProductTimePrices(Date startDate, Date endDate){
		Long supplierId = Long.valueOf(WebServiceConstant.getProperties("shholiday.supplierId"));
		List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierId(supplierId);
		for (MetaProductBranch metaProductBranch : metaProductBranchs) {
				try {
					syncProductTimePrice(metaProductBranch, startDate, endDate);
				} catch (Exception e) {
					log.error("updateProductPrice Exception:", e);
				}
		}
	}
	
	/**
	 * 更新指定类别的时间价格库存
	 */
	public void updateProductTimePrice(String productId,String productType, Date startDate, Date endDate) throws Exception {
		Long supplierId = Long.valueOf(WebServiceConstant.getProperties("shholiday.supplierId"));
		List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierType(supplierId,productType,productId);
		if (!metaProductBranchs.isEmpty()) {
			MetaProductBranch metaProductBranch = metaProductBranchs.get(0);
			syncProductTimePrice(metaProductBranch,startDate,endDate);
		}
	}
	
	
	
	private void syncProductTimePrice(MetaProductBranch metaProductBranch, Date startDate, Date endDate)throws Exception{
		String supplierProductId = metaProductBranch.getProductIdSupplier();
		List<ProdProductBranch> prodProductBranchs = prodProductBranchService.getProductBranchByMetaProdBranchId(metaProductBranch.getMetaBranchId());
		ProductDetailRequest request = new ProductDetailRequest(supplierProductId, startDate, endDate,"PriceInfo");
		ProductDetailResponse response = shholidayClient.execute(request);
		// 从client返回的价格信息
		List<ProductPrice> productPriceList = response.getProductPrices();
		if (productPriceList != null) {
			for (ProductPrice productPrice : productPriceList) {
				productPrice.setSupplierProdId(supplierProductId);
				if (metaProductBranch.getProductTypeSupplier().equals(productPrice.getSupplierBranchId())) {
					TimePrice metaTimePrice = saveMetaProductBranchTimePrice(metaProductBranch, productPrice);
					if(!"true".equals(metaProductBranch.getVirtual())){
						saveMetaTravelCode(metaProductBranch, productPrice);
						for (ProdProductBranch prodProductBranch : prodProductBranchs) {
							saveProductTimePrice(prodProductBranch, productPrice, metaTimePrice);
						}
					}
				}
			}
			for (ProdProductBranch prodProductBranch : prodProductBranchs) {
				prodProductBranchService.updatePriceByBranchId(prodProductBranch.getProdBranchId());
				if(prodProductBranch.hasDefault()){
					Long productId = prodProductBranch.getProductId();
					opTravelGroupService.createTravelGroupByProductId(productId);
				}

			}
			if(startDate == null) startDate=new Date();
			if(endDate == null) endDate=DateUtils.addMonths(startDate, 2);
			TimeRange range=new TimeRange(startDate,endDate);
			PropertyEditor editor = new TimeRangePropertEditor();
			editor.setValue(range);	
			productMessageProducer.sendMsg(MessageFactory.newProductMetaPriceMessage(metaProductBranch.getMetaBranchId(),editor.getAsText()));
		}

	}
	
	
	private void syncCoupon(MetaProductBranch metaProductBranch, Date startDate, Date endDate)throws Exception{
		String supplierProductId = metaProductBranch.getProductIdSupplier();
		List<ProdProductBranch> prodProductBranchs = prodProductBranchService.getProductBranchByMetaProdBranchId(metaProductBranch.getMetaBranchId());
		ProductDetailRequest request = new ProductDetailRequest(supplierProductId, startDate, endDate,"all");
		ProductDetailResponse response = shholidayClient.execute(request);
		try {
			//client返回的优惠券
			List<BusinessCoupon> metaProdCouponList = response.getMetaProdCoupons();
			if(metaProdCouponList != null){
				if(metaProductBranch.getBranchName().equals(Constant.SH_HOLIDAY_BRANCH_TYPE.ADULT.getCnName()) ||
						metaProductBranch.getBranchName().equals(Constant.SH_HOLIDAY_BRANCH_TYPE.CHILDREN.getCnName()) ||
						metaProductBranch.getBranchName().equals(Constant.SH_HOLIDAY_BRANCH_TYPE.INFANT.getCnName()) ){
					saveMetaProdCoupons(metaProductBranch,metaProdCouponList);
				}
			}
			List<BusinessCoupon> prodProdCouponList = response.getProdProdCoupons();
			if(prodProdCouponList != null){
				for(ProdProductBranch prodProductBranch : prodProductBranchs){
					if(prodProductBranch.getBranchName().equals(Constant.SH_HOLIDAY_BRANCH_TYPE.ADULT.getCnName()) ||
							prodProductBranch.getBranchName().equals(Constant.SH_HOLIDAY_BRANCH_TYPE.CHILDREN.getCnName()) ||
							prodProductBranch.getBranchName().equals(Constant.SH_HOLIDAY_BRANCH_TYPE.INFANT.getCnName())){
						saveProProdCoupons(prodProductBranch,prodProdCouponList);
					}
				}
			}
		} catch (Exception e) {
			log.error("saveCoupons Exception: " , e);
		}
	}
	
	private void closeCoupon(List<BusinessCoupon> lvmamaList,List<BusinessCoupon> prodProdCouponList){
		List<BusinessCoupon> closeList = new ArrayList<BusinessCoupon>();
		for(BusinessCoupon lvmamaCoupon : lvmamaList){
			String couponId2 = lvmamaCoupon.getCouponName().split("-")[1];
			boolean isOffline = true;
			for (BusinessCoupon businessCoupon : prodProdCouponList) {
				String couponId1 = businessCoupon.getCouponName().split("-")[1];
				if(couponId1.equals(couponId2)){
					isOffline = false;
					break;
				}
			}
			if(isOffline){
				lvmamaCoupon.setValid(BusinessCoupon.VALID_FALSE);
				closeList.add(lvmamaCoupon);
			}
		}
		for(BusinessCoupon closeCoupon : closeList){
			businessCouponService.updateByPrimaryKey(closeCoupon);
		}
	}
	
	
	private void saveProProdCoupons(ProdProductBranch prodProductBranch,List<BusinessCoupon> prodProdCouponList){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("branchId", prodProductBranch.getProdBranchId());
		List<BusinessCoupon> lvmamaList = businessCouponService.selectByParam(paramMap);
		closeCoupon(lvmamaList, prodProdCouponList);
		for(BusinessCoupon coupon:prodProdCouponList){
			BusinessCoupon coupon2 = null;
			String couponId1 = coupon.getCouponName().split("-")[1];
			for(BusinessCoupon lvmamaCoupon : lvmamaList){
				String couponId2 = lvmamaCoupon.getCouponName().split("-")[1]; 
				if(couponId1.equals(couponId2)){
					coupon2 = lvmamaCoupon;
					break;
				}
			}
			if(coupon.getMemo().equals(prodProductBranch.getBranchName())){
				if(coupon2 != null){
					Long id= coupon2.getBusinessCouponId();
					try {
						PropertyUtils.copyProperties(coupon2, coupon);
					} catch (Exception e) {
						e.printStackTrace();
					}
					coupon2.setBusinessCouponId(id);
					coupon2.setBranchId(prodProductBranch.getProdBranchId());
					coupon2.setProductId(prodProductBranch.getProductId());
					businessCouponService.updateByPrimaryKey(coupon2);
				}else{
					coupon2 = new BusinessCoupon();
					try {
						PropertyUtils.copyProperties(coupon2, coupon);
					} catch (Exception e) {
						e.printStackTrace();
					}
					coupon2.setBranchId(prodProductBranch.getProdBranchId());
					coupon2.setProductId(prodProductBranch.getProductId());
					businessCouponService.insertBusinessCoupon(coupon2);
				}
			}
		}
	}
	
	private void saveMetaProdCoupons(MetaProductBranch metaProductBranch,List<BusinessCoupon> metaProdCouponList){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("branchId", metaProductBranch.getMetaBranchId());
		List<BusinessCoupon> lvmamaList = businessCouponService.selectByParam(paramMap);
		closeCoupon(lvmamaList, metaProdCouponList);
		for(BusinessCoupon coupon:metaProdCouponList){
			String couponId1 = coupon.getCouponName().split("-")[1];
			BusinessCoupon coupon2 = null;
			for(BusinessCoupon lvmamaCoupon : lvmamaList){
				String couponId2 = lvmamaCoupon.getCouponName().split("-")[1]; 
				if(couponId1.equals(couponId2)){
					coupon2 = lvmamaCoupon;
					break;
				}
			}
			if(coupon.getMemo().equals(metaProductBranch.getBranchName())){
				if(coupon2 != null){
					Long id= coupon2.getBusinessCouponId();
					try {
						PropertyUtils.copyProperties(coupon2, coupon);
					} catch (Exception e) {
						e.printStackTrace();
					}
					coupon2.setBusinessCouponId(id);
					coupon2.setBranchId(metaProductBranch.getMetaBranchId());
					coupon2.setProductId(metaProductBranch.getMetaProductId());
					businessCouponService.updateByPrimaryKey(coupon2);
				}else{
					coupon2 = new BusinessCoupon();
					try {
						PropertyUtils.copyProperties(coupon2, coupon);
					} catch (Exception e) {
						e.printStackTrace();
					}
					coupon2.setBranchId(metaProductBranch.getMetaBranchId());
					coupon2.setProductId(metaProductBranch.getMetaProductId());
					businessCouponService.insertBusinessCoupon(coupon2);
				}
			}
		}
	}


	/**
	 * 初始化更新采购时间价格表
	 * @param metaProductBranch
	 * @param productPrice
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private TimePrice saveMetaProductBranchTimePrice(MetaProductBranch metaProductBranch, ProductPrice productPrice) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		TimePrice timePrice = metaProductService.getMetaTimePriceByIdAndDate(metaProductBranch.getMetaBranchId(), productPrice.getPriceDate());
		TimePrice bean = new TimePrice();
		Long stock = productPrice.getDayStock();
		Long settlementPrice = productPrice.getSettlementPrice();
		Long marketPrice = productPrice.getIndividualPrice();
		if (timePrice == null) {
			bean.setProductId(metaProductBranch.getMetaProductId());
			bean.setMetaBranchId(metaProductBranch.getMetaBranchId());
			bean.setSpecDate(productPrice.getPriceDate());
			bean.setSettlementPrice(settlementPrice);
			bean.setMarketPrice(marketPrice);
			bean.setOverSale("false");
			bean.setTotalDayStock(0L);
			bean.setBreakfastCount(0L);
			bean.setAheadHour(AHEADHOUR);
			bean.setCancelHour(CANCELHOUR);
			bean.setResourceConfirm(String.valueOf(true));
			bean.setDayStock(stock);
			if (settlementPrice <= 0 && stock == 0) {
				return null;
			}
			metaProductService.insertTimePrice(bean);
		} else {
			PropertyUtils.copyProperties(bean, timePrice);
			if (stock != 0) {
				bean.setSettlementPrice(settlementPrice);
				bean.setMarketPrice(marketPrice);	
			}
			bean.setResourceConfirm(String.valueOf(true));
			bean.setDayStock(stock);
			metaProductService.updateTimePrice(bean, timePrice);
		}
		return bean;
	}
	
	
	/**
	 * 初始化更新团
	 * @param metaProductBranch
	 * @param productPrice
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private void saveMetaTravelCode(MetaProductBranch metaProductBranch, ProductPrice productPrice) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		if(productPrice.getTeamNo() != null){
			MetaTravelCode metaTravelCode = metaTravelCodeService.selectBySuppAndDate(metaProductBranch.getProductIdSupplier(), productPrice.getPriceDate());
			MetaTravelCode bean = new MetaTravelCode();
			if(metaTravelCode == null){
				bean.setSpecDate(productPrice.getPriceDate());
				bean.setSupplierProductId(metaProductBranch.getProductIdSupplier());
				bean.setTravelCode(productPrice.getTeamName());
				bean.setTravelCodeId(productPrice.getTeamNo());
				bean.setSupplierChannel(Constant.SUPPLIER_CHANNEL.SH_HOLIDAY.name());
				metaTravelCodeService.insert(bean);
			}else{
				PropertyUtils.copyProperties(bean, metaTravelCode);
				bean.setTravelCode(productPrice.getTeamName());
				bean.setTravelCodeId(productPrice.getTeamNo());
				bean.setSupplierChannel(Constant.SUPPLIER_CHANNEL.SH_HOLIDAY.name());
				metaTravelCodeService.updateByPrimaryKeySelective(bean);
			}
		}
	}

	/**
	 * 初始化更新销售时间价格表
	 * @param prodProductBranch
	 * @param productPrice
	 * @param metaTimePrice
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private void saveProductTimePrice(ProdProductBranch prodProductBranch, ProductPrice productPrice, TimePrice metaTimePrice) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Long productId = prodProductBranch.getProductId();
		Long prodBranchId = prodProductBranch.getProdBranchId();
		Long stock = productPrice.getDayStock();
		TimePrice timePrice = prodProductService.getTimePriceByProdId(productId, prodBranchId, productPrice.getPriceDate());
		Long settlementPrice = productPrice.getSettlementPrice();
		TimePrice bean = new TimePrice();
		if (timePrice == null ) {
			bean.setProductId(productId);
			bean.setProdBranchId(prodBranchId);
			bean.setSpecDate(productPrice.getPriceDate());
			bean.setDayStock(stock);
			bean.setCancelHour(CANCELHOUR);
			bean.setAheadHour(AHEADHOUR);
			bean.setPriceType(Constant.PRICE_TYPE.FIXED_PRICE.name());
			bean.setPrice(settlementPrice);
			if (settlementPrice <= 0 && stock == 0) {
				return ;
			}
			prodProductService.insertTimePrice(bean, metaTimePrice);
		} else {
			PropertyUtils.copyProperties(bean, timePrice);
			bean.setCancelHour(CANCELHOUR);
			bean.setAheadHour(AHEADHOUR);
			if (stock != 0) {
				bean.setPrice(settlementPrice);
			}
			bean.setDayStock(stock);
			prodProductService.updateTimePrice(bean, metaTimePrice);
		}
	}
	
	
	/**
	 * 创建采购产品
	 * @param productInfo
	 * @param user
	 * @return
	 */
	private MetaProduct saveMetaProductInfo(ProductInfo productInfo ,Long metaProductId,PermUser user){
		Long supplierId = Long.valueOf(WebServiceConstant.getProperties("shholiday.supplierId"));
		String contractId =WebServiceConstant.getProperties("shholiday.supplier.contractid");
		MetaProduct metaProduct = new MetaProductRoute();
		if(metaProductId != null){
			metaProduct =metaProductService.getMetaProduct(metaProductId);
		}
		metaProduct.setBizCode(productInfo.getSupplierProdId());
		metaProduct.setCurrencyType(Constant.FIN_CURRENCY.CNY.name());
		metaProduct.setIsResourceSendFax("false");
		metaProduct.setPayToLvmama("true");
		metaProduct.setPayToSupplier("false");
		metaProduct.setProductType(Constant.PRODUCT_TYPE.ROUTE.name());
		metaProduct.setSupplierId(supplierId);
		metaProduct.setSubProductType(Constant.ROUTE_SUB_PRODUCT_TYPE.GROUP.name());
		metaProduct.setManagerId(user.getUserId());
		metaProduct.setOrgId(user.getDepartmentId());
		metaProduct.setValidDays(1L);//有效天数
		metaProduct.setContractId(Long.valueOf(contractId));
		metaProduct.setValid("Y");
		metaProduct.setSupplierChannel(Constant.SUPPLIER_CHANNEL.SH_HOLIDAY.name());
		if(!productInfo.isValid()){
			metaProduct.setProductName(productInfo.getSupplierProdName());
			metaProductId = metaProductService.addMetaProduct(metaProduct, "system");
			metaProduct.setMetaProductId(metaProductId);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("metaProductId", metaProductId);
			map.put("valid", "Y");
			metaProductService.changeMetaProductValid(map, "SYSTEM");
		}else{
			metaProductService.updateMetaProduct(metaProduct, "SYSTEM");
		}
		return metaProduct;
	}
	
	
	/**
	 * 产品绑定履行对象，结算对象，凭证对象
	 * @param metaProductId
	 */
	private void addMetaTarget(final Long metaProductId){
		String settlmentTargetId = WebServiceConstant.getProperties("shholiday.supplier.settletargetid");
		String performTargetId = WebServiceConstant.getProperties("shholiday.supplier.performtargetid");
		String bcertificateTargetId = WebServiceConstant.getProperties("shholiday.supplier.bcertificatetargetid");
		
		MetaBCertificate bcertificate = new MetaBCertificate();
		bcertificate.setTargetId(Long.valueOf(bcertificateTargetId));
		bcertificate.setMetaProductId(metaProductId);
		bCertificateTargetService.insertSuperMetaBCertificate(bcertificate, "SYSTEM");
		
		MetaSettlement settlement = new MetaSettlement();
		settlement.setMetaProductId(metaProductId);
		settlement.setTargetId(Long.valueOf(settlmentTargetId));
		settlementTargetService.addMetaRelation(settlement, "SYSTEM");
		
		MetaPerform perform = new MetaPerform();
		perform.setMetaProductId(metaProductId);
		perform.setTargetId(Long.valueOf(performTargetId));
		performTargetService.addMetaRelation(perform, "SYSTEM");		
	}
	
	/**
	 * 创建销售产品
	 * @param productInfo
	 * @param user
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private ProdProduct saveProdProductInfo(ProductInfo productInfo,Long metaBranchId,PermUser user) throws Exception{
		ProdRoute product =new ProdRoute();
		String[] channels = new String[]{Constant.CHANNEL.BACKEND.name(),Constant.CHANNEL.FRONTEND.name(),Constant.CHANNEL.CLIENT.name()};
		Long groupMin = productInfo.getGroupMin()!= null ? Long.valueOf(productInfo.getGroupMin()) : 20L;
		if(!productInfo.isValid() || metaBranchId == null){
			Date now = new Date();
			product.setBizcode(UUID.randomUUID().toString().substring(0,7));
			product.setCreateTime(now);
			product.setOnlineTime(DateUtil.toYMDDate(now));
			product.setOfflineTime(DateUtils.addMonths(now, 4));
			String supplierProdName = productInfo.getSupplierProdName();
			product.setProductName(supplierProdName);
			product.setOnLine("false");
			product.setProductType(Constant.PRODUCT_TYPE.ROUTE.name());
			product.setDays(productInfo.getDayCount());
			product.setFilialeName(Constant.FILIALE_NAME.SH_FILIALE.name());
			product.setAdditional("false");
			product.setCanPayByBonus("N");
			product.setCouponAble("false");
			product.setValid("Y");
			product.setCouponActivity("false");
			product.setIsRefundable("N");
			product.setManagerId(user.getUserId());
			product.setOrgId(user.getDepartmentId());
			product.setTravellerInfoOptions("NAME,CARD_NUMBER,MOBILE,F_NAME,F_CARD_NUMBER,F_MOBILE");
			product.setWrapPage("false");
			product.setValid("Y");
			product.setIsForegin("N");
			product.setIsAperiodic("false");
			product.setInitialNum(groupMin);
			product.setShowSaleDays(SHOW_DAY );//显示几天价格
			product.setGroupType(Constant.GROUP_TYPE.AGENCY.name());
			product.setSubProductType(Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name());//产品类型
			product.seteContract(Constant.ECONTRACT_TYPE.NEED_ECONTRACT.name());
			product.setPrePaymentAble("N");
			ProdProduct prodProduct = prodProductService.addProductChannel(product, channels,"SYSTEM");
			Long productId = prodProduct.getProductId();
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("productId", productId);
			param.put("payToLvmama", "true");
			param.put("payToSupplier", "false");
			this.prodProductService.updatePaymentTarget(param);
			
			ProdEContract eContract = new ProdEContract();
			eContract.setProductId(productId);
			eContract.setEContractTemplate(Constant.ECONTRACT_TEMPLATE.GROUP_ECONTRACT.name());
			eContract.setTravelFormalities(Constant.TRAVEL_FORMALITIES.OTHERS.name());
			eContract.setGroupType(Constant.GROUP_TYPE.AGENCY.name());
			eContract.setGuideService(Constant.GUIDE_SERVICE.LOCAL_GUIDE.name());
			prodProductService.saveEContract(eContract);
			return prodProduct;
		}else{
			List<ProdProduct> products = prodProductService.selectProductByMetaBranchId(metaBranchId);
			ProdProduct oldProduct = products.get(0);
			PropertyUtils.copyProperties(product, oldProduct);
			product.seteContract(Constant.ECONTRACT_TYPE.NEED_ECONTRACT.name());
			product.setDays(productInfo.getDayCount());
			product.setInitialNum(groupMin);
			product.setGroupType(Constant.GROUP_TYPE.AGENCY.name());
			product.seteContract(Constant.ECONTRACT_TYPE.NEED_ECONTRACT.name());
			prodProductService.updateProdProduct(product, channels, "SYSTEM", null);
			return product;
		}
	}
	/**
	 * 打包采购
	 * @param prodBranch
	 * @param metaBranch
	 */
	private void packMeta(List<ProdProductBranch> prodBranchs,List<MetaProductBranch> metaBranchs,ProductInfo productInfo){
		ProdProductBranchItem virtualItem = new ProdProductBranchItem();
		for(MetaProductBranch metaBranch : metaBranchs){
			if(Constant.SH_HOLIDAY_BRANCH_TYPE.VIRTUAL_STOCK.name().equals(metaBranch.getProductTypeSupplier())){
				virtualItem.setCreateTime(new Date());
				virtualItem.setMetaProductId(metaBranch.getMetaProductId());
				virtualItem.setMetaBranchId(metaBranch.getMetaBranchId());
				virtualItem.setQuantity(1L);
				break;
			}
		}
		for(ProdProductBranch prodBranch : prodBranchs){
			for(MetaProductBranch metaBranch : metaBranchs){
				if(prodBranch.getBranchName().equals(metaBranch.getBranchName())){
					Long prodBranchId = prodBranch.getProdBranchId();
					ProdProductBranchItem item = new ProdProductBranchItem();
					item.setCreateTime(new Date());
					item.setMetaProductId(metaBranch.getMetaProductId());
					item.setMetaBranchId(metaBranch.getMetaBranchId());
					item.setProdBranchId(prodBranchId);
					item.setQuantity(1L);
					prodProductBranchService.addItem(item,prodBranch,"SYSTEM");
					if(Constant.SH_HOLIDAY_BRANCH_TYPE.CHILDREN.name().equals(metaBranch.getProductTypeSupplier()) && !productInfo.isChildConsumeStock()){
						continue;
					}
					if(Constant.SH_HOLIDAY_BRANCH_TYPE.INFANT.name().equals(metaBranch.getProductTypeSupplier()) && !productInfo.isInfantConsumeStock()){
						continue;
					}
					if(Constant.SH_HOLIDAY_BRANCH_TYPE.ROOMDIFFER.name().equals(metaBranch.getProductTypeSupplier())){
						continue;
					}
					virtualItem.setProdBranchId(prodBranchId);
					prodProductBranchService.addItem(virtualItem,prodBranch,"SYSTEM");
				}
			}
		}
	}
	
	/**
	 * 创建采购类别
	 * @param product
	 * @param productInfo
	 * @return
	 */
	private List<MetaProductBranch> saveMetaBranch(MetaProduct product,ProductInfo productInfo){
		List<MetaProductBranch> metaBranchList = new ArrayList<MetaProductBranch>();
		MetaProductBranch mpb = new MetaProductBranch();
		mpb.setAdditional("false");
		mpb.setCreateTime(new Date());
		mpb.setMetaProductId(product.getMetaProductId());
		mpb.setSendFax("false");
		mpb.setTotalDecrease("false");
		mpb.setValid("Y");
		mpb.setVirtual("false");
		mpb.setCheckStockHandle("SHHOLIDAY");
		
		mpb.setBranchName(Constant.SH_HOLIDAY_BRANCH_TYPE.CHILDREN.getCnName());
		mpb.setBranchType(Constant.ROUTE_BRANCH.CHILD.name());
		mpb.setAdultQuantity(0L);
		mpb.setChildQuantity(1L);
		mpb.setProductIdSupplier(productInfo.getSupplierProdId());
		mpb.setProductTypeSupplier(Constant.SH_HOLIDAY_BRANCH_TYPE.CHILDREN.name());
		MetaProductBranch metaChild = metaProductBranchService.save(mpb, "SYSTEM");
		metaBranchList.add(metaChild);
		
		mpb.setBranchName(Constant.SH_HOLIDAY_BRANCH_TYPE.INFANT.getCnName());
		mpb.setBranchType(Constant.ROUTE_BRANCH.CHILD.name());
		mpb.setAdultQuantity(0L);
		mpb.setChildQuantity(1L);
		mpb.setProductIdSupplier(productInfo.getSupplierProdId());
		mpb.setProductTypeSupplier(Constant.SH_HOLIDAY_BRANCH_TYPE.INFANT.name());
		MetaProductBranch metaInfant = metaProductBranchService.save(mpb, "SYSTEM");
		metaBranchList.add(metaInfant);
		
		
		mpb.setBranchName(Constant.SH_HOLIDAY_BRANCH_TYPE.ACCOMPANY.getCnName());
		mpb.setBranchType(Constant.ROUTE_BRANCH.ADULT.name());
		mpb.setAdultQuantity(1L);
		mpb.setChildQuantity(0L);
		mpb.setProductIdSupplier(productInfo.getSupplierProdId());
		mpb.setProductTypeSupplier(Constant.SH_HOLIDAY_BRANCH_TYPE.ACCOMPANY.name());
		MetaProductBranch metaAccompany = metaProductBranchService.save(mpb, "SYSTEM");
		metaBranchList.add(metaAccompany);
		
		mpb.setBranchName(Constant.SH_HOLIDAY_BRANCH_TYPE.ADULT.getCnName());
		mpb.setBranchType(Constant.ROUTE_BRANCH.ADULT.name());
		mpb.setAdultQuantity(1L);
		mpb.setChildQuantity(0L);
		mpb.setProductIdSupplier(productInfo.getSupplierProdId());
		mpb.setProductTypeSupplier(Constant.SH_HOLIDAY_BRANCH_TYPE.ADULT.name());
		MetaProductBranch metaAdult = metaProductBranchService.save(mpb, "SYSTEM");
		metaBranchList.add(metaAdult);
		
		mpb.setBranchName(Constant.SH_HOLIDAY_BRANCH_TYPE.VIRTUAL_STOCK.getCnName());
		mpb.setBranchType(Constant.ROUTE_BRANCH.CUSTOM.name());
		mpb.setProductIdSupplier(productInfo.getSupplierProdId());
		mpb.setProductTypeSupplier(Constant.SH_HOLIDAY_BRANCH_TYPE.VIRTUAL_STOCK.name());
		mpb.setVirtual("true");
		MetaProductBranch virtualBranch = metaProductBranchService.save(mpb, "SYSTEM");
		metaBranchList.add(virtualBranch);
		
		mpb.setVirtual("false");
		mpb.setBranchName(Constant.SH_HOLIDAY_BRANCH_TYPE.ROOMDIFFER.getCnName());
		mpb.setBranchType(Constant.ROUTE_BRANCH.FANGCHA.name());
		mpb.setAdditional("true");
		mpb.setAdultQuantity(1L);
		mpb.setChildQuantity(0L);
		mpb.setProductIdSupplier(productInfo.getSupplierProdId());
		mpb.setProductTypeSupplier(Constant.SH_HOLIDAY_BRANCH_TYPE.ROOMDIFFER.name());
		MetaProductBranch metaRoomDiffer = metaProductBranchService.save(mpb, "SYSTEM");
		metaBranchList.add(metaRoomDiffer);
		
		return metaBranchList;
	}
	/**
	 * 创建销售类别
	 * @param product
	 * @param productInfo
	 * @return
	 */
	private List<ProdProductBranch> saveProdBranch(ProdProduct product) {
		List<ProdProductBranch> prodBranchList = new ArrayList<ProdProductBranch>();
		
		ProdProductBranch ppb = new ProdProductBranch();
		ppb.setAdditional("false");
		ppb.setCreateTime(new Date());
		ppb.setProductId(product.getProductId());
		ppb.setPriceUnit("人");
		ppb.setValid("Y");
		ppb.setVisible("true");
		ppb.setMaximum(10L);
		
		ppb.setBranchName(Constant.SH_HOLIDAY_BRANCH_TYPE.CHILDREN.getCnName());
		ppb.setBranchType(Constant.ROUTE_BRANCH.CHILD.name());
		ppb.setAdultQuantity(0L);
		ppb.setChildQuantity(1L);
		ppb.setDefaultBranch("false");
		ppb.setMinimum(0L);
		ResultHandleT<ProdProductBranch> childBranch = prodProductBranchService.saveBranch(ppb, "SYSTEM");
		prodBranchList.add(childBranch.getReturnContent());
		
		ppb.setBranchName(Constant.SH_HOLIDAY_BRANCH_TYPE.INFANT.getCnName());
		ppb.setBranchType(Constant.ROUTE_BRANCH.CHILD.name());
		ppb.setAdultQuantity(0L);
		ppb.setChildQuantity(1L);
		ppb.setDefaultBranch("false");
		ppb.setMinimum(0L);
		ResultHandleT<ProdProductBranch> infantBranch = prodProductBranchService.saveBranch(ppb, "SYSTEM");
		prodBranchList.add(infantBranch.getReturnContent());
		
		ppb.setBranchName(Constant.SH_HOLIDAY_BRANCH_TYPE.ACCOMPANY.getCnName());
		ppb.setBranchType(Constant.ROUTE_BRANCH.ADULT.name());
		ppb.setAdultQuantity(1L);
		ppb.setChildQuantity(0L);
		ppb.setDefaultBranch("false");
		ppb.setMinimum(0L);
		ResultHandleT<ProdProductBranch> accompanyBranch = prodProductBranchService.saveBranch(ppb, "SYSTEM");
		prodBranchList.add(accompanyBranch.getReturnContent());
		
		ppb.setBranchName(Constant.SH_HOLIDAY_BRANCH_TYPE.ADULT.getCnName());
		ppb.setBranchType(Constant.ROUTE_BRANCH.ADULT.name());
		ppb.setAdultQuantity(1L);
		ppb.setChildQuantity(0L);
		ppb.setDefaultBranch("true");
		ppb.setMinimum(1L);
		ResultHandleT<ProdProductBranch> adultBranch = prodProductBranchService.saveBranch(ppb, "SYSTEM");
		prodBranchList.add(adultBranch.getReturnContent());
		
		ppb.setBranchName(Constant.SH_HOLIDAY_BRANCH_TYPE.ROOMDIFFER.getCnName());
		ppb.setAdditional("true");
		ppb.setBranchType(Constant.ROUTE_BRANCH.FANGCHA.name());
		ppb.setAdultQuantity(1L);
		ppb.setChildQuantity(0L);
		ppb.setDefaultBranch("false");
		ppb.setMinimum(0L);
		ResultHandleT<ProdProductBranch> roomdifferBranch = prodProductBranchService.saveBranch(ppb, "SYSTEM");
		prodProductRelationService.addRelation(product.getProductId(), roomdifferBranch.getReturnContent(), "SYSTEM");
		prodBranchList.add(roomdifferBranch.getReturnContent());
		
		for(ProdProductBranch productBranch : prodBranchList){
			productBranch.setOnline("true");
			prodProductBranchService.updateByPrimaryKeySelective(productBranch);
		}
		
		return prodBranchList;
	}
	
	private ProductInfo initProductInfo(ProductInfo info,List<MetaProductBranch> metaProductBranchs){
		for(MetaProductBranch metaBranch : metaProductBranchs){
			String supProdId = metaBranch.getProductIdSupplier();
            if(info.getSupplierProdId().equals(supProdId)){
            	info.setValid(true);
            	ProdProductBranch item =  prodProductBranchService.getProductBranchByMetaProdBranchId(metaBranch.getMetaBranchId()).get(0);
    			info.setLvmamaProdId(item.getProductId());
            	break;
            } 
        }
		
		return info;
	}

	/** 保存销售产品描述信息*/
	private void saveViewContent(Long productId,ProductInfo productInfo){
		String userName = WebServiceConstant.getProperties("shholiday.supplier.username");
		List<ViewContent> contentList=productInfo.getContents();
		
		for(ViewContent content : contentList){
			content.setPageId(productId);
		}
		ViewPage viewPage=new ViewPage();
		viewPage.setProductId(productId);
		if(!contentList.isEmpty()){
			if(!productInfo.isValid()){
					viewPageService.addViewPage(viewPage);
					viewPage.setContentList(contentList);
					viewPageService.saveViewContent(viewPage,userName);
			} else {
				List<ViewContent> contentRemove = new ArrayList<ViewContent>();
				for (ViewContent content : contentList) {
					if (Constant.VIEW_CONTENT_TYPE.FEATURES.name().equals(content.getContentType())
							|| Constant.VIEW_CONTENT_TYPE.INTERIOR.name().equals(content.getContentType())
							|| Constant.VIEW_CONTENT_TYPE.SHOPPINGEXPLAIN.name().equals(content.getContentType())
							|| Constant.VIEW_CONTENT_TYPE.SERVICEGUARANTEE.name().equals(content.getContentType())
							|| Constant.VIEW_CONTENT_TYPE.TRAFFICINFO.name().equals(content.getContentType())
							|| Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name().equals(content.getContentType())
							|| Constant.VIEW_CONTENT_TYPE.RECOMMENDPROJECT.name().equals(content.getContentType())
							|| Constant.VIEW_CONTENT_TYPE.ORDERTOKNOWN.name().equals(content.getContentType())
							|| Constant.VIEW_CONTENT_TYPE.PLAYPOINTOUT.name().equals(content.getContentType())
							|| Constant.VIEW_CONTENT_TYPE.ACITONTOKNOW.name().equals(content.getContentType())
							) {
						contentRemove.add(content);
					}
				}
				contentList.removeAll(contentRemove);
				viewPage.setContentList(contentList);
				viewPageService.saveViewContent(viewPage, userName);
			}
		}
	}
	
	/** 保存产品的展示图片*/
	private void savePicture(Long productId,ProductInfo productInfo){
		int size = productInfo.getPictureList().size() >4 ? 4 : productInfo.getPictureList().size();
		for(int i=0 ; i<size ; i++){
			String filename = productInfo.getPictureList().get(i);
			try {
				String localFilename = uploadPicture(filename);
				ComPicture picture = new ComPicture();
				picture.setPictureName(i+"");
				picture.setPictureObjectId(productId);
				picture.setPictureObjectType("VIEW_PAGE");
				picture.setPictureUrl(localFilename);
				picture.setIsNew(true);// 标识图片是新建产生的
				Long pictureId = comPictureService.savePicture(picture);
				log.info(pictureId);
			} catch (Exception e) {
				log.error("PictureException", e);
			}	
		}
	}

	/** 产品的行程展示
	 * @throws Exception 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClientProtocolException */
	private void saveViewJourney(Long productId,ProductInfo productInfo) throws ClientProtocolException, FileNotFoundException, IOException, Exception{
		String userName = WebServiceConstant.getProperties("shholiday.supplier.username");
		if(!productInfo.isValid()){
			for(ViewJourney journey:productInfo.getJourneys()){
				journey.setPageId(productId);
				journey.setProductId(productId);
				viewPageJourneyService.insertViewJourney(journey,userName);
			}
			List<ViewJourney> journeyList = viewPageJourneyService.getViewJourneysByProductId(productId);
			for(ViewJourney journey:productInfo.getJourneys()){
				for(ViewJourney lvmamaJourney : journeyList){
					if(journey.getSeq().longValue() == lvmamaJourney.getSeq().longValue()){
						Long journeyId = lvmamaJourney.getJourneyId();
						List<ComPicture> picList =  journey.getJourneyPictureList();
						for(ComPicture pic : picList){
							String picUrl = pic.getPictureUrl();
							String filename = uploadPicture(picUrl);
							pic.setPictureObjectId(journeyId);
							pic.setPictureUrl(filename);
							pic.setIsNew(true);
							comPictureService.savePicture(pic);
						}
					}
				}
			}
		}else{
			List<ViewJourney> journeyList=viewPageJourneyService.getViewJourneysByProductId(productId);
			Map<Long, Long> map = new HashMap<Long, Long>();			
			for(ViewJourney journey:journeyList){
				map.put(journey.getSeq(), journey.getJourneyId());				
			}
			for(ViewJourney journey:productInfo.getJourneys()){
				journey.setPageId(productId);
				journey.setProductId(productId);
				journey.setJourneyId(map.get(journey.getSeq()));
				viewPageJourneyService.updateViewJourney(journey,userName);
			}
		}
	}
	private void saveProductPlace(Long productId , ProductInfo productInfo){
		
		String from = productInfo.getDepartCity();
		Place placeFrom = placeService.getPlaceByName(from, "Y");
		Long fromPlaceId = placeFrom.getPlaceId();

		String to = productInfo.getDestinationCity();
		Place placeTo = placeService.getPlaceByName(to, "Y");
		Long toPlaceId = placeTo.getPlaceId();
		if (placeFrom != null || placeTo != null) {
			prodProductPlaceService.insertOrUpdateTrafficPlace(productId, fromPlaceId, toPlaceId);
		}
	}
	
	/**  保存采购产品的优惠券*/
	private void saveMetaProdCoupons(Long metaProductId,List<MetaProductBranch> metaBranchList,List<BusinessCoupon> metaProdCouponList){
		for(BusinessCoupon coupon:metaProdCouponList){
			Long branchId = findBranchIdInMetaProductBranchList(coupon.getMemo(), metaBranchList);
			if( branchId!= null){
				coupon.setProductId(metaProductId);
				coupon.setBranchId(branchId);
				businessCouponService.insertBusinessCoupon(coupon);
			}
		}
	}
	
	/**  保存销售产品的优惠券*/
	private void saveProdProdCoupons(Long proProductId,List<ProdProductBranch> prodProductBranchList,List<BusinessCoupon> proProdCouponList){
		
		for(BusinessCoupon coupon:proProdCouponList){
			Long branchId = findBranchIdInProProductBranchList(coupon.getMemo(), prodProductBranchList);
			if(branchId != null){
				coupon.setProductId(proProductId);
				coupon.setBranchId(branchId);
				businessCouponService.insertBusinessCoupon(coupon);
			}
		}
		saveProdProductTag(proProductId);
	}
	
	/** 添加早Tag*/
	private void saveProdProductTag(Long proProductId){
		List<Long> productIds=new ArrayList<Long>();
		productIds.add(proProductId);
		List<ProdProductTag> prodProductTags=null;
		ProdProductTag prodProductTag=businessCouponService.checkProductTag(Constant.BUSINESS_COUPON_TYPE.EARLY.getCode(), proProductId);
		if(prodProductTag!=null){
			prodProductTags=new ArrayList<ProdProductTag>();
			prodProductTags.add(prodProductTag);
		}
		prodProductTagService.addSystemProgProductTags(prodProductTags, productIds, Constant.PROD_TAG_NAME.EARLY.getCnName());
	}
	
	private Long findBranchIdInMetaProductBranchList(String type,List<MetaProductBranch> list){
		Long branchId = null;
		for(MetaProductBranch metaProductBranch:list){
			if(metaProductBranch.getBranchName().equals(type)){
				branchId = metaProductBranch.getMetaBranchId();
				break;
			}
		}
		return branchId;
	}
	
	private Long findBranchIdInProProductBranchList(String type,List<ProdProductBranch> prodProductBranchList){
		Long branchId = null;
		for(ProdProductBranch prodProductBranch:prodProductBranchList){
			if(prodProductBranch.getBranchName().equals(type)){
				branchId = prodProductBranch.getProdBranchId();
				break;
			}
		}
		return branchId;
	}
	
	private String uploadPicture(String picUrl) throws ClientProtocolException, IOException, FileNotFoundException, Exception {
		HttpResponseWrapper response = HttpsUtil.requestGetResponse(picUrl);
		InputStream in = response.getResponseStream();
		File file = File.createTempFile("shholiday", ".jpg");
		FileOutputStream fos = new FileOutputStream(file);
		IOUtils.copy(in, fos);
		response.close();
		String localFilename = UploadCtrl.postToRemote(file);
		return localFilename;
	}
	
	public void setShholidayClient(ShholidayClient shholidayClient) {
		this.shholidayClient = shholidayClient;
	}

	public void setMetaTravelCodeService(MetaTravelCodeService metaTravelCodeService) {
		this.metaTravelCodeService = metaTravelCodeService;
	}

	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}

	public void setbCertificateTargetService(BCertificateTargetService bCertificateTargetService) {
		this.bCertificateTargetService = bCertificateTargetService;
	}

	public void setSettlementTargetService(SettlementTargetService settlementTargetService) {
		this.settlementTargetService = settlementTargetService;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

	public void setViewPageService(ViewPageService viewPageService) {
		this.viewPageService = viewPageService;
	}


	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}
	
	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}
	
	public void setViewPageJourneyService(
			ViewPageJourneyService viewPageJourneyService) {
		this.viewPageJourneyService = viewPageJourneyService;
	}

	public void setProdProductPlaceService(ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}

	public void setBusinessCouponService(BusinessCouponService businessCouponService) {
		this.businessCouponService = businessCouponService;
	}


	public void setProdProductRelationService(ProdProductRelationService prodProductRelationService) {
		this.prodProductRelationService = prodProductRelationService;
	}
	public void setProdProductTagService(ProdProductTagService prodProductTagService) {
		this.prodProductTagService = prodProductTagService;
	}


	public void setProductMessageProducer(TopicMessageProducer productMessageProducer) {
		this.productMessageProducer = productMessageProducer;
	}


	public void setOpTravelGroupService(IOpTravelGroupService opTravelGroupService) {
		this.opTravelGroupService = opTravelGroupService;
	}
}
