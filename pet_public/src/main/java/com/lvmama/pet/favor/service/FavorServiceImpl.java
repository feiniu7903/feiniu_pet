/**
 * 
 */
package com.lvmama.pet.favor.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Coupon;
import com.lvmama.comm.pet.po.businessCoupon.BusinessCoupon;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.mark.MarkCouponProduct;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.service.businessCoupon.BusinessCouponService;
import com.lvmama.comm.pet.service.favor.FavorService;
import com.lvmama.comm.pet.service.mark.MarkCouponProductService;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.vo.favor.FavorProductResult;
import com.lvmama.comm.pet.vo.favor.FavorResult;
import com.lvmama.comm.pet.vo.favor.OrderFavorStrategy;
import com.lvmama.comm.pet.vo.favor.ProductFavorStrategy;
import com.lvmama.comm.pet.vo.mark.ValidateCodeInfo;
import com.lvmama.comm.utils.ActivityUtil;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.search.dao.ProdBranchSearchInfoDAO;

/**
 * 优惠/优惠券系统服务类
 * @author liuyi
 *
 */
public class FavorServiceImpl implements FavorService {
	
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(FavorServiceImpl.class);
	@Autowired
	private MarkCouponService markCouponService;
	@Autowired
	private MarkCouponProductService markCouponProductService;
	@Autowired
	private BusinessCouponService businessCouponService;
	@Autowired
	private ProdBranchSearchInfoDAO prodBranchSearchInfoDAO;

	/**
	 *  计算优惠系统优惠结果
	 */
	@Override
	public FavorResult calculateFavorResultByBuyInfo(final BuyInfo buyInfo) {
		FavorResult favorResult = new FavorResult();
		if (!ActivityUtil.getInstance().checkActivityIsValid(Constant.ACTIVITY_FIVE_YEAR)) {
			getProductFavorStrategies(buyInfo, favorResult);
		}
		getOrderFavorStrategy(buyInfo, favorResult);
		
		return favorResult;
	}
	
	/**
	 * 根据销售产品的标识，购买人数，游玩时间来计算各个产品的优惠策略
	 * @param buyInfo 原始的购买信息
	 * @param favorResult 优惠策略结果集
	 * 此代码等待产品优惠出现后实现
	 */
	private void getProductFavorStrategies(final BuyInfo buyInfo, final FavorResult favorResult) {
		//implement
		List<FavorProductResult> favorProductResultList = getBusinessCouponByBuyInfo(buyInfo);
		favorResult.setFavorProductList(favorProductResultList);
	}
	
	/**
	 * 计算采购产品优惠系统优惠结果
	 */
	@Override
	public List<FavorProductResult> getFavorMetaProductResultByOrderInfo(OrdOrder order){
		List<FavorProductResult> favorProductResultList = new ArrayList<FavorProductResult>();
		try{
			Map<String, Object> param=new HashMap<String, Object>();
			List<BusinessCoupon> tempBusCouponList=new ArrayList<BusinessCoupon>();
			//查询定义在每个产品类别上的优惠策略
			for(OrdOrderItemProd ordOrderItemProd:order.getOrdOrderItemProds()){
				for(OrdOrderItemMeta ordOrderItemMeta:ordOrderItemProd.getOrdOrderItemMetas()){
					param.put("productId", ordOrderItemMeta.getMetaProductId());
					param.put("branchId",ordOrderItemMeta.getMetaBranchId());
					param.put("currentDate", new Date());
					param.put("metaType", Constant.BUSINESS_COUPON_META_TYPE.META.getCode());
					tempBusCouponList.addAll(businessCouponService.selectWithProdInfo(param));
				}
			}
			
			//判断当前优惠策略集合是否有当前item条件的优惠策略
			// 第一是判断这些集合里是否有满足条件的早买早慧的优惠策略  然后在判断是否有多买多慧的优惠策略
			for(OrdOrderItemProd ordOrderItemProd:order.getOrdOrderItemProds()){
				for(OrdOrderItemMeta ordOrderItemMeta:ordOrderItemProd.getOrdOrderItemMetas()){
					FavorProductResult favorProductResult = new FavorProductResult();
					favorProductResult.setProductId( ordOrderItemMeta.getMetaProductId());
					favorProductResult.setProductBranchId(ordOrderItemMeta.getMetaBranchId());
					//convert businessCoupon to valid product strategy
				    calProductFavorStrategry(favorProductResult, tempBusCouponList, ordOrderItemMeta.getMetaProductId(), 
				    		ordOrderItemMeta.getMetaBranchId(), ordOrderItemMeta.getVisitTime());
				    favorProductResultList.add(favorProductResult);
				}
			 }
		}catch(Exception ex){
			LOG.error(ex, ex);
		}
		return favorProductResultList;
	}
	
	/**
	 * 为时间价格表填充优惠提示参数信息
	 */
	@Override
	public List<CalendarModel> fillFavorParamsInfoForCalendar(Long productId,Long branchId, List<CalendarModel> calendarModelList){
		//获取当前在线的产品类别
		List<ProdBranchSearchInfo> prodBranchList =new ArrayList<ProdBranchSearchInfo>();
		if(branchId!=null){
			ProdBranchSearchInfo prodBranchSearchInfo =new ProdBranchSearchInfo();
			prodBranchSearchInfo.setProdBranchId(branchId);
			Map<String,Object> param =new HashMap<String,Object>();
			param.put("prodBranchId",branchId );
			prodBranchList=prodBranchSearchInfoDAO.query(param);
		}else if(productId!=null){
			prodBranchList = prodBranchSearchInfoDAO.getProductBranchByProduct(productId, Boolean.FALSE.toString(), "true","true");
		}
		LOG.info("productId:"+productId+"<=========>prodBranchId:"+ branchId);
		
		
		if(prodBranchList != null){
			//获取类别所对应的有效优惠策略
			List<BusinessCoupon> tempBusCouponList=new ArrayList<BusinessCoupon>();
			//五周年活动不适用优惠券
			if(!ActivityUtil.getInstance().checkActivityIsValid(Constant.ACTIVITY_FIVE_YEAR)){
				for(ProdBranchSearchInfo prodBranchSearchInfo : prodBranchList){
					Map<String, Object> param=new HashMap<String, Object>();
					param.put("productId", prodBranchSearchInfo.getProductId());
					param.put("branchId",prodBranchSearchInfo.getProdBranchId());
					param.put("currentDate", new Date());
					param.put("metaType", Constant.BUSINESS_COUPON_META_TYPE.SALES.getCode());
					param.put("createTime321", "true");
					tempBusCouponList.addAll(businessCouponService.selectWithProdInfo(param));
				}
			}
			LOG.info("tempBusCouponList:"+tempBusCouponList.size());
			//遍历时间价格表每一天
			for(CalendarModel calendarModel : calendarModelList){
				TimePrice[][]  timePriceArray = calendarModel.getCalendar();
				for(int i = 0; i < timePriceArray.length; i++){
					for(int j = 0; j < timePriceArray[i].length; j++){
						TimePrice timePrice = timePriceArray[i][j];
						//获取适合当前时间价格表的优惠JSON参数信息，并设置
						if(timePrice.getSpecDate() != null){
							timePrice.setFavorJsonParams(getFitTimePriceFavorJsonParams(prodBranchList, tempBusCouponList, timePrice));
						}
					}
				}
			}
		}
		return calendarModelList;
	}
	
	/**
	 * 验证优惠券
	 * @param couponId
	 * @param couponCode
	 * @param mainProductId
	 * @param subProductType
	 * @param info
	 * @return
	 */
	public OrderFavorStrategy validateCoupon(final Long couponId, final String couponCode, final Long mainProductId, final String subProductType, final ValidateCodeInfo info) {
		MarkCouponCode markCouponCode = null;
		MarkCoupon markCoupon = null;
		
		//优惠券享受优惠
		if (StringUtils.isNotEmpty(couponCode)) {		
			markCouponCode = markCouponService.getMarkCouponCodeByCouponIdAndCode(null, couponCode);
			//优惠券号码不存在
			if (null == markCouponCode) {
				info.setKey(Constant.COUPON_INFO.NOTEXSIT.name());
				info.setValid(false);
				return null;
			}
			
			markCoupon = markCouponService.selectMarkCouponByPk(markCouponCode.getCouponId());
			
			//优惠券已经使用
			if (null != markCoupon 
					&& Constant.COUPON_TYPE.B.name().equals(markCoupon.getCouponType())
					&& "true".equalsIgnoreCase(markCouponCode.getUsed())) {
				info.setKey(Constant.COUPON_INFO.COUPON_USED.name());
				info.setValid(false);
				return null;
			}
		} else {
			//优惠活动
			markCoupon = markCouponService.selectMarkCouponByPk(couponId);
			markCouponCode = markCouponService.getMarkCouponCodeByCouponIdAndCode(couponId, null);
		}
		
		//优惠券号码批次不存在
		if (null == markCoupon) {
			info.setKey(Constant.COUPON_INFO.NOTEXSIT.name());
			info.setValid(false);
			return null;
		}
		
		/**
		 * @deprecated 对于京东优惠券产生独立的逻辑到6月30号
		 */
		if (markCouponCode.getCouponCode().startsWith("B012370")) {
			info.setValue("京东2500代金券不支持网上使用，请致电10106060客服操作。");
			info.setValid(false);
			return null;	
		}
		
		//优惠券过期
		if ("false".equals(markCoupon.getValid()) 
				|| (MarkCoupon.FIXED_VALID.equals(markCoupon.getValidType()) && markCoupon.isOverDue()) 
				|| (MarkCoupon.UNFIXED_VALID.equals(markCoupon.getValidType()) && null != markCouponCode && markCouponCode.isOverDue())) { 
			info.setKey(Constant.COUPON_INFO.OVERDATE.name());
			info.setValid(false);
			return null;
		}
		
		// 判断优惠券是否有绑定过该产品
		MarkCouponProduct mcp = null;
		if (Constant.COUPON_TARGET.PRODUCT.name().equals(markCoupon.getCouponTarget())) {
			if(mainProductId != null && mainProductId != 0){//优先通过产品ID获取
				mcp = markCouponProductService.getSuitableMarkCouponProduct(markCoupon.getCouponId(), mainProductId, null);
			}
			if(mcp == null && !StringUtils.isEmpty(subProductType)){
				mcp = markCouponProductService.getSuitableMarkCouponProduct(markCoupon.getCouponId(), null, subProductType);
			}
			
			if (null == mcp) {
				info.setKey(Constant.COUPON_INFO.CANNOTUSED_TO_PRODUCT_NOT.name());
				info.setValid(false);
				return null;
			} 		
		}
		
		//不满足优惠条件
		OrderFavorStrategy orderFavorStrategy = (OrderFavorStrategy) FavorStrategyMarkCouponAdapter.getStrategy(markCoupon, markCouponCode);
		if (null != mcp && null != mcp.getAmount()) {
			orderFavorStrategy.setSpecialDiscountAmount(mcp.getAmount());
		}
		
		return orderFavorStrategy;
	}
	
	
	/**
	 * 根据产品上设置的优惠券/活动开关过滤优惠券/活动
	 * @param couponList
	 * @param prodProduct
	 * @return
	 */
	public List<Coupon> filterCouponListByProductCouponUseFlag(List<Coupon> couponList, ProdProduct prodProduct){
		List<Coupon> filterCouponList = new ArrayList<Coupon>();
		if(prodProduct != null){
			for(Coupon coupon: couponList){
				MarkCoupon markCoupon = markCouponService.selectMarkCouponByPk(coupon.getCouponId());
				if("true".equals(markCoupon.getWithCode()) && !"false".equals(prodProduct.getCouponAble())){
					filterCouponList.add(coupon);
				}
				
				if("false".equals(markCoupon.getWithCode()) && !"false".equals(prodProduct.getCouponActivity())){
					filterCouponList.add(coupon);
				}
			}
		}else{
			filterCouponList = couponList;
		}
		return filterCouponList;
	}
	
	/**
	 * 更正版-根据产品上设置的优惠券/活动开关过滤优惠券/活动
	 * @param couponList
	 * @param prodProduct
	 * @return
	 */
	public List<Coupon> validateCouponListByProductCouponUseFlag(List<Coupon> couponList, ProdProduct prodProduct){
		List<Coupon> filterCouponList = new ArrayList<Coupon>();
		if(prodProduct != null){
			for(Coupon coupon: couponList){
				MarkCoupon markCoupon = markCouponService.selectMarkCouponByPk(coupon.getCouponId());
				if("true".equals(markCoupon.getWithCode()) && !"false".equals(prodProduct.getCouponAble())){
					if(null != coupon.getCode() && !"".equals(coupon.getCode().trim())){
						filterCouponList.add(coupon);
					}
				}
				
				if("false".equals(markCoupon.getWithCode()) && !"false".equals(prodProduct.getCouponActivity())){
					filterCouponList.add(coupon);
				}
			}
		}else{
			filterCouponList = couponList;
		}
		return filterCouponList;
	}

	/**
	 * 返回当前订单所关联产品在系统里所有可用的优惠策略
	 * @param buyInfo
	 * @return
	 */
	private List<FavorProductResult>  getBusinessCouponByBuyInfo(BuyInfo buyInfo) {
		Map<String, Object> param=new HashMap<String, Object>();
		List<BusinessCoupon> tempBusCouponList=new ArrayList<BusinessCoupon>();
		//查询定义在每个产品类别上的优惠策略
		//五周年活动不适用优惠券
		for(BuyInfo.Item item:buyInfo.getItemList()){
			param.put("productId", item.getProductId());
			param.put("branchId",item.getProductBranchId());
			param.put("currentDate", new Date());
			param.put("metaType", Constant.BUSINESS_COUPON_META_TYPE.SALES.getCode());
			param.put("createTime321", "true");
			tempBusCouponList.addAll(businessCouponService.selectWithProdInfo(param));
		}
		
		List<FavorProductResult> favorProductResultList = new ArrayList<FavorProductResult>();
		
		//判断当前优惠策略集合是否有当前item条件的优惠策略
		// 第一是判断这些集合里是否有满足条件的早买早慧的优惠策略  然后在判断是否有多买多慧的优惠策略
		for(BuyInfo.Item item:buyInfo.getItemList()){
			FavorProductResult favorProductResult = new FavorProductResult();
			favorProductResult.setProductId(item.getProductId());
			favorProductResult.setProductBranchId(item.getProductBranchId());
			//convert businessCoupon to valid product strategy
		    calProductFavorStrategry(favorProductResult, tempBusCouponList, item.getProductId(), 
		    		item.getProductBranchId(), item.getVisitTime());
		   favorProductResultList.add(favorProductResult);
		 }
		return favorProductResultList;
	}
	
	/**
	 * 计算产品类别优惠策略
	 * @param favorProductResult
	 * @param tempBusCouponList
	 * @param productId
	 * @param branchId
	 * @param visitTime
	 * @param quantity
	 */
	private void calProductFavorStrategry(FavorProductResult favorProductResult, List<BusinessCoupon> tempBusCouponList, 
			Long productId, Long branchId, Date visitTime){
	    for(BusinessCoupon businessCoupon:tempBusCouponList){//convert businessCoupon to valid product strategy
			if(productId.equals(businessCoupon.getProductId())
					&&branchId.equals(businessCoupon.getBranchId())){
				calProductFavorStrategry(favorProductResult, businessCoupon, new Date(), visitTime);
			}
	     }
	}

	/**
	 * 计算产品优惠策略
	 * @param currentDate
	 * @param item
	 * @param favorProductResult
	 * @param businessCoupon
	 */
	private void calProductFavorStrategry(FavorProductResult favorProductResult, BusinessCoupon businessCoupon,
			Date currentDate, Date visitTime) {
		Long aheadNum=0L;//优惠系统定义的提前天数
		int  aheadDay = 0;//用户实际下单的提前天数
		aheadNum=businessCoupon.getArgumentX()==null?0L:businessCoupon.getArgumentX();
		if(visitTime != null) {
			aheadDay=DateUtil.getDaysBetween(currentDate,visitTime);
		}
		//判断有无早买早慧的优惠策略
		/**
		 * 条件1:当前系统时间及下单时间要在活动有效期内
		 * 条件2:游玩日期-下单时间>=优惠策略定义的提前天数
		 * 条件3:如果策略游玩日期不为空,还要判断用户游玩日期是否满足条件
		 * **/
		if(businessCoupon.getCouponType().equalsIgnoreCase(Constant.BUSINESS_COUPON_TYPE.EARLY.getCode())&&DateUtil.inAdvance(businessCoupon.getBeginTime(), currentDate)
				&&DateUtil.inAdvance(currentDate, businessCoupon.getEndTime())){
			
			if(aheadNum>0&&businessCoupon.getPlayBeginTime()==null&&businessCoupon.getPlayEndTime()==null&&(aheadDay>=aheadNum)){
				favorProductResult.addProductFavorStrategry((ProductFavorStrategy) FavorStrategyMarkCouponAdapter.getStrategy(businessCoupon));
			}
			
			if(businessCoupon.getPlayBeginTime()!=null&&businessCoupon.getPlayEndTime()!=null&&aheadNum<=0){
				 if((DateUtil.inAdvance(businessCoupon.getPlayBeginTime(), visitTime)||businessCoupon.getPlayBeginTime().equals(visitTime))
						 &&DateUtil.inAdvance(visitTime, businessCoupon.getPlayEndTime())){
					 favorProductResult.addProductFavorStrategry((ProductFavorStrategy) FavorStrategyMarkCouponAdapter.getStrategy(businessCoupon));
				 }
			}
			//同时存在游玩日期和提前天数
			if(businessCoupon.getPlayBeginTime()!=null&&businessCoupon.getPlayEndTime()!=null&&aheadNum>0){
				Date newPlayTime=DateUtil.getDateAfterDays(new Date(), aheadNum.intValue()-1);
				//设置新的游玩日期
				if(newPlayTime.compareTo(businessCoupon.getPlayBeginTime())>=0&& newPlayTime.compareTo(businessCoupon.getPlayEndTime())<=0){
					businessCoupon.setPlayBeginTime(newPlayTime);
				}
				 if((DateUtil.inAdvance(businessCoupon.getPlayBeginTime(), visitTime)||businessCoupon.getPlayBeginTime().equals(visitTime))
						 &&DateUtil.inAdvance(visitTime, businessCoupon.getPlayEndTime()) && newPlayTime.compareTo(businessCoupon.getPlayEndTime())<=0){
					 favorProductResult.addProductFavorStrategry((ProductFavorStrategy) FavorStrategyMarkCouponAdapter.getStrategy(businessCoupon));
				 }
			}
		}
		//判断有无多买多惠的优惠策略
		/**
		 * 条件1:当前系统时间及下单时间要在活动有效期内
		 * 条件2:所买产品的数量达到系统要求的满{X}份
		 * 条件3:如果策略游玩日期不为空,还要判断用户游玩日期是否满足条件
		 * **/
		if(businessCoupon.getCouponType().equalsIgnoreCase(Constant.BUSINESS_COUPON_TYPE.MORE.getCode())&&DateUtil.inAdvance(businessCoupon.getBeginTime(), currentDate)
				&&DateUtil.inAdvance(currentDate, businessCoupon.getEndTime())){
			if(businessCoupon.getPlayBeginTime()!=null&&businessCoupon.getPlayEndTime()!=null){
				 if((DateUtil.inAdvance(businessCoupon.getPlayBeginTime(), visitTime)||businessCoupon.getPlayBeginTime().equals(visitTime))
						 &&DateUtil.inAdvance(visitTime, businessCoupon.getPlayEndTime())){
					 favorProductResult.addProductFavorStrategry((ProductFavorStrategy) FavorStrategyMarkCouponAdapter.getStrategy(businessCoupon));
				 }
			}else{
				favorProductResult.addProductFavorStrategry((ProductFavorStrategy) FavorStrategyMarkCouponAdapter.getStrategy(businessCoupon));
			}
		}
	}

	/**
	 * @param buyInfo
	 * @param favorResult
	 */
	private void getOrderFavorStrategy(final BuyInfo buyInfo, final FavorResult favorResult) {
		if (null == buyInfo || null == favorResult) {
			debug("it's a joke? Either buyInfo or favorResuls is null.");
			return;
		}
		/**
		 * @deprecated 让人很不舒服的一段代码，为什么要用内部类呢
		 */
		List<BuyInfo.Coupon> buyInfo_coupons = buyInfo.getCouponList();
		if (buyInfo_coupons!=null&&!buyInfo_coupons.isEmpty()) {
			for (BuyInfo.Coupon buyInfo_coupon : buyInfo_coupons) {
				if (null != buyInfo_coupon
						&& "true".equalsIgnoreCase(buyInfo_coupon.getChecked())) {
					OrderFavorStrategy orderFavorStrategy = validateCoupon(buyInfo_coupon.getCouponId(), buyInfo_coupon.getCode(),  getMainProductId(buyInfo), buyInfo.getMainSubProductType(), favorResult.getValidateCodeInfo());
					
					if (null != orderFavorStrategy) {
						favorResult.addOrderFavorStrategy(orderFavorStrategy);
						break;  //只能存在一个订单优惠策略
					}
				}
			}
		}
		
	}
	
	/**
	 * 获取主产品的产品标识
	 * @param buyInfo 原始的购买信息
	 * @return 主产品的标识
	 * <p>根据原始的购买信息，获得订单中主产品的产品标识</p>
	 */
	private Long getMainProductId(final BuyInfo buyInfo) {
		if (null == buyInfo || buyInfo.getItemList().isEmpty()) {
			debug("Cann't find item product, how to discount?");
			return null;
		}
		List<BuyInfo.Item> items = buyInfo.getItemList();
		for (BuyInfo.Item item : items) {
			if ("true".equalsIgnoreCase(item.getIsDefault())) {
				return item.getProductId();
			}
		}
		return items.get(0).getProductId();
	}
	
	/**
	 * 获取适合当前时间价格表的优惠JSON参数信息
	 * @param prodBranchList
	 * @param tempBusCouponList
	 * @param timePrice
	 * @return
	 */
	private String getFitTimePriceFavorJsonParams(List<ProdBranchSearchInfo> prodBranchList, List<BusinessCoupon> tempBusCouponList, TimePrice timePrice){
		String displayJsonInfo = "[";
		String earlyStrategyTitle = "";
		String moreStrategyTitle = "";
		String earlyStrategyJsonInfo = "";
		String moreStrategyJsonInfo = "";
		for(int i = 0; i < prodBranchList.size(); i++){//遍历每一个在线类别
			ProdBranchSearchInfo prodBranchSearchInfo = prodBranchList.get(i);
			FavorProductResult favorProductResult = new FavorProductResult();
			favorProductResult.setProductId(prodBranchSearchInfo.getProductId());
			favorProductResult.setProductBranchId(prodBranchSearchInfo.getProdBranchId());
			//计算产品类别优惠策略，得到最后剩下的优惠策略结果
		    calProductFavorStrategry(favorProductResult, tempBusCouponList, prodBranchSearchInfo.getProductId(), 
		    		prodBranchSearchInfo.getProdBranchId(), timePrice.getSpecDate());
		    String tempInfo = "";
		    //获取早定早惠/多定多惠展示的JSON参数信息
		    tempInfo = favorProductResult.getTimePriceJsonParams(prodBranchSearchInfo, timePrice.getSpecDate()
		    		, Constant.BUSINESS_COUPON_TYPE.EARLY.getCode());
		    if(StringUtils.isNotEmpty(tempInfo) && StringUtils.isNotEmpty(earlyStrategyJsonInfo)){
		    	earlyStrategyJsonInfo += ",";
		    }
		    earlyStrategyJsonInfo += tempInfo;
		    tempInfo = favorProductResult.getTimePriceJsonParams(prodBranchSearchInfo, timePrice.getSpecDate()
		    		, Constant.BUSINESS_COUPON_TYPE.MORE.getCode());
		    if(StringUtils.isNotEmpty(tempInfo) && StringUtils.isNotEmpty(moreStrategyJsonInfo)){
		    	moreStrategyJsonInfo += ",";
		    }
		    moreStrategyJsonInfo += tempInfo;
		}
		
		if(earlyStrategyJsonInfo.length() > 0){
			String dateString = new SimpleDateFormat("MM.dd").format(timePrice.getSpecDate());
			earlyStrategyTitle = "{\"index\":\""+Constant.FAVOR_TIME_PRICE_TEMPLATE_INDEX.EARLY_TITLE.getIndex()+"\",\"param\":\""+dateString+"\"}";
			displayJsonInfo += earlyStrategyTitle +"," + earlyStrategyJsonInfo;
		}
		if(moreStrategyJsonInfo.length() > 0){
			moreStrategyTitle = "{\"index\":\""+Constant.FAVOR_TIME_PRICE_TEMPLATE_INDEX.MORE_TITLE.getIndex()+"\"}";
			if(earlyStrategyJsonInfo.length() > 0){
				displayJsonInfo += ",";
			}
			displayJsonInfo += moreStrategyTitle +"," + moreStrategyJsonInfo;
		}
		displayJsonInfo += "]";
		if(displayJsonInfo.length() == 2){
			displayJsonInfo = "";//清空无值
		}
		if(LOG.isDebugEnabled()){
			LOG.debug(timePrice.getSpecDate() + displayJsonInfo);
		}
		//LOG.info(timePrice.getSpecDate() + displayJsonInfo);
		return displayJsonInfo;
	}


	/**
	 * 打印调试信息
	 * @param message 调试信息
	 */
	private void debug(final String message) {
		if (LOG.isDebugEnabled() && StringUtils.isNotBlank(message)) {
			LOG.debug(message);
		}
	}

	
	/**
	 * 根据时间价格表取出早订早慧最优价格
	 * @param calendarModelList
	 * @param product
	 * @param branchId
	 * @return
	 */
	@Override
	public Float getEarlyCouponPrice(List<CalendarModel> calendarModelList,Long branchId) {
		
		//获取类别所对应的有效优惠策略
		List<BusinessCoupon> tempBusCouponList=new ArrayList<BusinessCoupon>();
		//五周年活动不适用优惠券
		if(!ActivityUtil.getInstance().checkActivityIsValid(Constant.ACTIVITY_FIVE_YEAR)){
			Map<String, Object> param=new HashMap<String, Object>();
			//param.put("productId", productId);
			param.put("branchId",branchId);
			param.put("currentDate", new Date());
			param.put("metaType", Constant.BUSINESS_COUPON_META_TYPE.SALES.getCode());
			param.put("couponType", Constant.BUSINESS_COUPON_TYPE.EARLY.getCode());
			param.put("createTime321", "true");
			tempBusCouponList.addAll(businessCouponService.selectWithProdInfo(param));
		}
		Float earlyCouponPice=0f;
		if(tempBusCouponList!=null && tempBusCouponList.size()>0){
			//取出产品Id
			Long productId = tempBusCouponList.get(0).getProductId();
			//遍历时间价格表每一天
			for(CalendarModel calendarModel : calendarModelList){
				TimePrice[][]  timePriceArray = calendarModel.getCalendar();
				for(int i = 0; i < timePriceArray.length; i++){
					for(int j = 0; j < timePriceArray[i].length; j++){
						TimePrice timePrice = timePriceArray[i][j];
						//获取适合当前时间价格表的优惠,并设置
						if(timePrice.getSpecDate() != null){
							Float tempPice=getFitTimePriceFavorParams(productId,branchId,tempBusCouponList,timePrice);
							if(earlyCouponPice.compareTo(tempPice)<0){
								earlyCouponPice=tempPice;
							}
						}
					}
				}
			}
		}
		return earlyCouponPice;
	}
	
	/**
	 * 获取适合当前时间价格表的早订早慧优惠信息
	 * @param prodBranchList
	 * @param tempBusCouponList
	 * @param timePrice
	 * @return
	 */
	private Float getFitTimePriceFavorParams(Long productId, Long branchId, List<BusinessCoupon> tempBusCouponList, TimePrice timePrice){
		FavorProductResult favorProductResult = new FavorProductResult();
		favorProductResult.setProductId(productId);
		favorProductResult.setProductBranchId(branchId);
		//计算产品类别优惠策略，得到最后剩下的优惠策略结果
	    calProductFavorStrategry(favorProductResult, tempBusCouponList, productId,branchId, timePrice.getSpecDate());
	    return favorProductResult.getEarlyTimePrice();
	}

}
