package com.lvmama.clutter.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;

import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.model.MobileBranch;
import com.lvmama.clutter.model.MobileGroupOn;
import com.lvmama.clutter.model.MobileMyFavorite;
import com.lvmama.clutter.model.MobileOrder;
import com.lvmama.clutter.model.MobileProdTag;
import com.lvmama.clutter.model.MobileProductRoute;
import com.lvmama.clutter.model.MobileProductTitle;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.bee.po.prod.ProdHotel;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.pet.po.client.ClientProduct;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.vo.ProductSearchInfoHotel;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ProdCProduct;
/**
 * 
 * @author dengcheng
 *
 */
public class ClientUtils {
	
	public static void copyProductList(List<ProdBranchSearchInfo> productList,List<ClientProduct> cpList,ProductSearchInfo psi){

		for (ProdBranchSearchInfo viewProductSearchInfo : productList) {
			
			ClientProduct cp = new ClientProduct();
			cp.setSellPrice(viewProductSearchInfo.getSellPrice());
			cp.setMarketPrice(viewProductSearchInfo.getMarketPrice());
			cp.setCouponAble("true");
			cp.setPayToLvmama(psi.getPayToLvmama());
			cp.setPayToSupplier(psi.getPayToSupplier());
			cp.setProductName(viewProductSearchInfo.getProductName());
			cp.setShortName(viewProductSearchInfo.getBranchName());
			cp.setSubProductType(viewProductSearchInfo.getSubProductType());
			cp.setProdBranchId(viewProductSearchInfo.getProdBranchId());
			cp.setProductId(viewProductSearchInfo.getProductId());
			cp.setDescription(viewProductSearchInfo.getDescription());
			cp.setBranchId(viewProductSearchInfo.getProdBranchId()+"");
			cp.setProductType(psi.getProductType());
			cp.setBedType(viewProductSearchInfo.getBedType());
			cp.setBroadband(viewProductSearchInfo.getBroadbandStr());
			if(StringUtil.isEmptyString(cp.getToDest())){
				cp.setToDest("");
			}
			if(StringUtil.isEmptyString(cp.getRecommendReason())){
				cp.setRecommendReason("");
			}
			if(StringUtil.isEmptyString(cp.getSmallImage())){
				cp.setSmallImage("");
			}
			cp.setCouponAble("true");
			cpList.add(cp);
		}
	}
	
	
	public static void copyProductList(List<ProdBranchSearchInfo> productList,List<ClientProduct> cpList,String productType){

		for (ProdBranchSearchInfo viewProductSearchInfo : productList) {
			
			ClientProduct cp = new ClientProduct();
			cp.setSellPrice(viewProductSearchInfo.getSellPrice());
			cp.setMarketPrice(viewProductSearchInfo.getMarketPrice());
			cp.setCouponAble("true");
			cp.setPayToLvmama(viewProductSearchInfo.getPayToLvmama());
			cp.setPayToSupplier(viewProductSearchInfo.getPayToSupplier());
			cp.setProductName(viewProductSearchInfo.getProductName());
			cp.setShortName(viewProductSearchInfo.getBranchName());
			cp.setSubProductType(viewProductSearchInfo.getSubProductType());
			cp.setProdBranchId(viewProductSearchInfo.getProdBranchId());
			cp.setProductId(viewProductSearchInfo.getProductId());
			cp.setDescription(viewProductSearchInfo.getDescription());
			cp.setBranchId(viewProductSearchInfo.getProdBranchId()+"");
			cp.setProductType(productType);
			cp.setBedType(viewProductSearchInfo.getBedType());
			cp.setBroadband(viewProductSearchInfo.getBroadbandStr());
			if(StringUtil.isEmptyString(cp.getToDest())){
				cp.setToDest("");
			}
			if(StringUtil.isEmptyString(cp.getRecommendReason())){
				cp.setRecommendReason("");
			}
			if(StringUtil.isEmptyString(cp.getSmallImage())){
				cp.setSmallImage("");
			}
			cp.setCouponAble("true");
			cpList.add(cp);
		}
	}
	
	public static void copyHotelSuitProductList(ProductSearchInfo viewProductSearchInfo,ProdHotel ph,List<ClientProduct> cpList,String productType){
			ClientProduct cp = new ClientProduct();
			cp.setSellPrice(viewProductSearchInfo.getSellPrice());
			cp.setMarketPrice(viewProductSearchInfo.getMarketPrice());
			cp.setCouponAble("true");
			cp.setPayToLvmama(viewProductSearchInfo.getPayToLvmama());
			cp.setPayToSupplier(viewProductSearchInfo.getPayToSupplier());
			cp.setProductName(viewProductSearchInfo.getProductName());
			cp.setShortName(viewProductSearchInfo.getProductName());
			cp.setSubProductType(viewProductSearchInfo.getSubProductType());
			cp.setProdBranchId(viewProductSearchInfo.getProdBranchId());
			cp.setProductId(viewProductSearchInfo.getProductId());
			cp.setBranchId(viewProductSearchInfo.getProdBranchId()+"");
			cp.setProductType(productType);
			cp.setBedType(viewProductSearchInfo.getBedType());
			cp.setBroadband(viewProductSearchInfo.getBroadbandStr());
			cp.setSellPrice(viewProductSearchInfo.getSellPrice());
			cp.setMarketPrice(viewProductSearchInfo.getMarketPrice());
			cp.setDays(String.valueOf(ph.getDays()));
			cp.setHotelSuit(true);
			if(StringUtil.isEmptyString(cp.getToDest())){
				cp.setToDest("");
			}
			if(StringUtil.isEmptyString(cp.getRecommendReason())){
				cp.setRecommendReason("");
			}
			if(StringUtil.isEmptyString(cp.getSmallImage())){
				cp.setSmallImage("");
			}
			cp.setCouponAble("true");
			cpList.add(cp);
		
	}
	
	

	public static MobileBranch copyTicketBranch(ProdBranchSearchInfo pbsi,ProductSearchInfo psi){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		MobileBranch mb = new MobileBranch();
		mb.setMarketPriceYuan(PriceUtil.convertToYuan(pbsi.getMarketPrice()));
		mb.setSellPriceYuan(Float.valueOf(pbsi.getSellPriceYuan().toString()));
		mb.setShortName(pbsi.getBranchName());
		mb.setCanOrderToday(pbsi.todayOrderAble());
		mb.setCanOrderTodayCurrentTime(pbsi.canOrderTodayCurrentTime());
		mb.setDescription( filterOutHTMLTags(pbsi.getDescription()));
		mb.setBranchId(pbsi.getProdBranchId());
		mb.setProductId(pbsi.getProductId());
		mb.setTodayOrderLastOrderTime(pbsi.getTodayOrderAbleTime());
		mb.setIcon(pbsi.getIcon());
		mb.setLargeImage(psi.getLargeImage());
		mb.setNewTodayOrderLastTime(pbsi.getTodayOrderLastTime());
		if(pbsi.getTodayOrderLastTime() != null){
			if(sdf.format(pbsi.getTodayOrderLastTime()).equals(sdf.format(new Date()))
					&& new Date().before(pbsi.getTodayOrderLastTime())){
				mb.setOrderTodayAble(true);
			}else{
				mb.setOrderTodayAble(false);
			}
		}else{
			mb.setOrderTodayAble(false);
		}
		mb.setPayToLvmama(pbsi.getPayToLvmama());
		if("true".equals(pbsi.getPayToLvmama())) {
			mb.setPayTarget(Constant.PAYMENT_TARGET.TOLVMAMA.name());
		} else {
			mb.setPayTarget(Constant.PAYMENT_TARGET.TOSUPPLIER.name());
		}
		
		// 优惠信息标签 
		Map<String,Object> map = getPreferentialTags(psi);
		mb.setPreferentialInfo(null==map.get("info")?"":map.get("info").toString()); // 优惠信息内容  
		mb.setPreferentialTags(null==map.get("tags")?"":map.get("tags").toString());  // 标签
		mb.setHasBusinessCoupon(Boolean.parseBoolean(map.get("hasBusinessCoupon").toString())); // 是否支持多定多惠，早定早惠
		// 返现金额
		mb.setMaxCashRefund(PriceUtil.convertToFen(pbsi.getCashRefund()));
		mb.setProductType(psi.getProductType());
		
		//V5.0
		mb.setCanDeduction(Boolean.parseBoolean(map.get("canDeduction").toString()));// 抵扣
		// 是否客户端独享 
		mb.setMobileAlone(ClientUtils.mobileAlone(pbsi));
		// 活动标签 
		mb.setTagList(ClientUtils.getMobileProdTags(psi,mb.isMobileAlone()));
		// 抵扣
		mb.setDeductionDesc(null==map.get("deductionDesc")?"":map.get("deductionDesc").toString());
		// 优惠
		mb.setFavourableDesc(null==map.get("favourableDesc")?"":map.get("favourableDesc").toString());
		mb.setShareWeixin("Y".equals(pbsi.getShareWeiXin()));
		return mb;
	}

	
	/**
	 *  获取优惠信息tags 
	 * @param psi
	 * @return
	 */
	private static Map<String,Object> getPreferentialTags(ProductSearchInfo psi) {
		if(null == psi) {
			Map<String,Object> refMap = new HashMap<String,Object>();
			refMap.put("hasBusinessCoupon", false);
			refMap.put("canDeduction", false);
			refMap.put("info", "");
			refMap.put("deductionDesc", "");
			refMap.put("favourableDesc", "");
			refMap.put("tags", "");
			return refMap;
		}
		return getPreferentialTags(psi.getTagList());
	}
	
	/**
	 * 获取优惠信息tags . 
	 * @param ProductSearchInfo psi
	 * @return map
	 */
	private static Map<String,Object> getPreferentialTags(List<ProdTag> plist) {
		Map <String,Object> refMap = new HashMap<String,Object>();
		//用于前台页面显示Tag
		String preferentialInfo="";// 优惠信息,抵扣信息
		String preferentialTags="";
		boolean hasBusinessCoupon = false; // 是否支持多订多惠 ，早定早惠
		boolean canDeduction = false; // 是否支持奖金抵扣 ，积分抵用
		String favourableDesc = ""; // 优惠信息 
		String deductionDesc = ""; // 抵扣信息
		//取ProductSeachInfo上的tag信息由于页面显示
		if(null != plist && plist.size() > 0) {
			for(int i = 0 ;i < plist.size() ;i++) {
				ProdTag pt = plist.get(i);
				/*EARLY("早订早惠"),
				MORE("多订多惠"),
				BONUS_PAY("奖金抵扣"),
				POINT_PAY("积分抵用"),
				BANK_ACTIVITY("银行活动"),
				SALES_PROMOTION("促销"),
				APERIODIC("期票");*/
				
				// EARLY("早订早惠")
				if(Constant.PROD_TAG_NAME.EARLY.getCnName().equals(pt.getTagName())) {
					hasBusinessCoupon = true;
					preferentialInfo = getTagNames(preferentialInfo,pt.getTagName());
					favourableDesc = getTagNames(favourableDesc,pt.getTagName());
					preferentialTags = preferentialTags + "2," ;
				// MORE("多订多惠")
				} else if(Constant.PROD_TAG_NAME.MORE.getCnName().equals(pt.getTagName())) {
					hasBusinessCoupon = true;
					preferentialInfo = getTagNames(preferentialInfo,pt.getTagName());
					favourableDesc =  getTagNames(favourableDesc,pt.getTagName());
					preferentialTags = preferentialTags + "3," ;
				// BONUS_PAY("奖金抵扣")
				} else if(Constant.PROD_TAG_NAME.BONUS_PAY.getCnName().equals(pt.getTagName())) {
					canDeduction = true;
					preferentialInfo = getTagNames(preferentialInfo,pt.getTagName());
					deductionDesc = getTagNames(deductionDesc,pt.getTagName());
					preferentialTags = preferentialTags + "1," ;
				// POINT_PAY("积分抵用")					
			    } else if(Constant.PROD_TAG_NAME.POINT_PAY.getCnName().equals(pt.getTagName())) {
			    	canDeduction = true;
			    	preferentialInfo = getTagNames(preferentialInfo,pt.getTagName());
			    	deductionDesc = getTagNames(deductionDesc,pt.getTagName());
					preferentialTags = preferentialTags + "4," ;
				}
			}
		}
		refMap.put("info", preferentialInfo);
		refMap.put("deductionDesc", deductionDesc);
		refMap.put("favourableDesc", favourableDesc);
		refMap.put("tags", preferentialTags);
		refMap.put("hasBusinessCoupon", hasBusinessCoupon);
		refMap.put("canDeduction", canDeduction);
		
		return refMap;
	}
	
	/**
	 * 
	 * @param desc
	 * @param tagName
	 * @return
	 */
	public static String getTagNames(String desc,String tagName) {
		return ("".equals(desc)?tagName:(desc+"•"+tagName));
	}
	
	/**
	 * 是否支持多订多惠，早定早恵 
	 * @param psi ProductSearchInfo 
	 * @return boolean 
	 */
	public static boolean hasBusinessCoupon(ProductSearchInfo psi) {
		if(null == psi) {
			return false;
		}
		Map<String,Object> m = getPreferentialTags(psi.getTagList());
		if(null != m && null !=m.get("hasBusinessCoupon") ) {
			return Boolean.parseBoolean(m.get("hasBusinessCoupon").toString());
		}
		return false;
		
	}
	
	/**
	 * 是否支持多订多惠，早定早恵 ; 积分抵用，奖金抵扣 
	 * 客户端5.0 
	 * @param psi ProductSearchInfo 
	 * @return boolean 
	 */
	public static void initTagsInfo4V50(ProductSearchInfo psi,MobileProductTitle mpt) {
		if(null == psi) {
			return;
		}
		Map<String,Object> m = getPreferentialTags(psi.getTagList());
		if(null != m && null !=m.get("canDeduction") ) {
			mpt.setCanDeduction(Boolean.parseBoolean(m.get("canDeduction").toString()));
		}
		if(null != m && null !=m.get("hasBusinessCoupon") ) {
			mpt.setHasBusinessCoupon(Boolean.parseBoolean(m.get("hasBusinessCoupon").toString()));
		}
	}
	
	/**
	 * 我的收藏是否支持多订多惠，早定早恵 ; 积分抵用，奖金抵扣 
	 * 客户端5.0 
	 * @param psi ProductSearchInfo 
	 * @return boolean 
	 */
	public static void initTagsInfo4V50(ProductSearchInfo psi,MobileMyFavorite mpt) {
		if(null == psi) {
			return;
		}
		Map<String,Object> m = getPreferentialTags(psi.getTagList());
		if(null != m && null !=m.get("canDeduction") ) {
			mpt.setCanDeduction(Boolean.parseBoolean(m.get("canDeduction").toString()));
		}
		if(null != m && null !=m.get("hasBusinessCoupon") ) {
			mpt.setHasBusinessCoupon(Boolean.parseBoolean(m.get("hasBusinessCoupon").toString()));
		}
	}
	
	
	/**
	 * 是否支持多订多惠，早定早恵 
	 * @param psi ProductSearchInfo 
	 * @return boolean 
	 */
	public static boolean hasBusinessCoupon(List<ProdTag> plist) {
		if(null == plist) {
			return false;
		}
		Map<String,Object> m = getPreferentialTags(plist);
		if(null != m && null !=m.get("hasBusinessCoupon") ) {
			return Boolean.parseBoolean(m.get("hasBusinessCoupon").toString());
		}
		return false;
		
	}
	
	
	/**
	 * 是否支持多订多惠，早定早恵 
	 * @param psi ProductSearchInfo 
	 * @return boolean 
	 */
	public static boolean hasBusinessCoupon(PlaceSearchInfo psi) {
		if(null == psi) {
			return false;
		}
		Map<String,Object> m = getPreferentialTags(psi.getTagList());
		if(null != m && null !=m.get("hasBusinessCoupon") ) {
			return Boolean.parseBoolean(m.get("hasBusinessCoupon").toString());
		}
		return false;
		
	}
	
	
	
	public static void copyProductBranchList(List<ProdProductBranch> productList,List<ClientProduct> cpList,String productType,String subProductType){

		for (ProdProductBranch viewProductSearchInfo : productList) {
			ClientProduct cp = new ClientProduct();
			cp.setSellPrice(viewProductSearchInfo.getSellPrice()==null?0L:viewProductSearchInfo.getSellPrice());
			cp.setMarketPrice(viewProductSearchInfo.getMarketPrice()==null?0L:viewProductSearchInfo.getMarketPrice());
			cp.setCouponAble("true");

			cp.setShortName(viewProductSearchInfo.getBranchName());
			cp.setSubProductType(subProductType);
			cp.setProdBranchId(viewProductSearchInfo.getProdBranchId());
			cp.setProductId(viewProductSearchInfo.getProductId());
			cp.setDescription(viewProductSearchInfo.getDescription());
			cp.setBroadband(viewProductSearchInfo.getBroadband());
			cp.setBedType(viewProductSearchInfo.getBedType());
			cp.setBranchId(viewProductSearchInfo.getProdBranchId()+"");
			cp.setProductType(productType);
			cp.setCanOrderToday(Boolean.valueOf(viewProductSearchInfo.getTodayOrderAble()));
			cp.setDescription(viewProductSearchInfo.getDescription());
			cp.setCanOrderToday(Boolean.valueOf(viewProductSearchInfo.getTodayOrderAble()));
			if(StringUtil.isEmptyString(cp.getToDest())){
				cp.setToDest("");
			}
			if(StringUtil.isEmptyString(cp.getRecommendReason())){
				cp.setRecommendReason("");
			}
			if(StringUtil.isEmptyString(cp.getSmallImage())){
				cp.setSmallImage("");
			}
			
			cp.setCouponAble("true");
			cpList.add(cp);
		}
	
	}
	
	
	
	
	
	public static void copyTicketBranch(List<ProdProductBranch> productList,List<MobileBranch> list){
		for (ProdProductBranch viewProductSearchInfo : productList) {
			MobileBranch mb = new MobileBranch();
			mb.setMarketPriceYuan(viewProductSearchInfo.getMarketPriceYuan());
			mb.setSellPriceYuan(viewProductSearchInfo.getSellPriceYuan());
			mb.setShortName(viewProductSearchInfo.getBranchName());
			mb.setCanOrderToday(Boolean.valueOf(viewProductSearchInfo.getTodayOrderAble()));
			mb.setDescription(viewProductSearchInfo.getDescription());
			mb.setBranchId(viewProductSearchInfo.getProdBranchId());
			mb.setProductId(viewProductSearchInfo.getProductId());
			list.add(mb);
		}
	}
	
	public static MobileBranch copyTicketBranch(ProdBranchSearchInfo pbsi){
		MobileBranch mb = new MobileBranch();
		mb.setMarketPriceYuan(PriceUtil.convertToYuan(pbsi.getMarketPrice()));
		mb.setSellPriceYuan(Float.valueOf(pbsi.getSellPriceYuan().toString()));
		mb.setShortName(pbsi.getBranchName());
		mb.setCanOrderToday(pbsi.todayOrderAble());
		mb.setCanOrderTodayCurrentTime(pbsi.canOrderTodayCurrentTime());
		mb.setDescription(pbsi.getDescription());
		mb.setBranchId(pbsi.getProdBranchId());
		mb.setProductId(pbsi.getProductId());
		mb.setTodayOrderLastOrderTime(pbsi.getTodayOrderAbleTime());
		return mb;
	}

	
	/**
	 * 字符串转换long 类型。 
	 * @param value
	 * @return
	 */
	public static Long string2Long(String value) {
		if(StringUtils.isEmpty(value)) {
			return 0l;
		}
		try{
			return Long.valueOf(value);
		}catch(Exception e){
			e.printStackTrace();
			return 0l;
		}
	}
	
	
	public static ClientProduct copyProductBranches(ProductSearchInfoHotel productSearchInfo){
	
			ClientProduct cp = new ClientProduct();
			cp.setSellPrice(productSearchInfo.getSellPrice());
			cp.setMarketPrice(productSearchInfo.getMarketPrice());
			cp.setPayToLvmama(productSearchInfo.getPayToLvmama());
			cp.setPayToSupplier(productSearchInfo.getPayToSupplier());
			cp.setCouponAble("true");
			cp.setShortName(productSearchInfo.getProductName());
			cp.setSubProductType(productSearchInfo.getSubProductType());
			cp.setProdBranchId(productSearchInfo.getProdBranchId());
			cp.setProductId(productSearchInfo.getProductId());
			//cp.setDescription(productSearchInfo.getd);
			cp.setBranchId(productSearchInfo.getProdBranchId()+"");
			if(!StringUtil.isEmptyString(productSearchInfo.getSmallImage())){
				cp.setIcon(productSearchInfo.getSmallImage());
			} else {
				cp.setIcon(productSearchInfo.getLargeImage());

			}
			cp.setBedType(productSearchInfo.getBedType());
			cp.setBroadband(productSearchInfo.getBroadband());
			return cp;
		
		
	}
	
	public static List<ClientProduct> copyHotelProductList(List<ProductSearchInfo> productList){
		List<ClientProduct> cpList = new ArrayList<ClientProduct>();
		
		for (ProductSearchInfo viewProductSearchInfo : productList) {
			if(Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT.name().equals(viewProductSearchInfo.getSubProductType())){
				ClientProduct cp = new ClientProduct();
				BeanUtils.copyProperties(viewProductSearchInfo, cp);
				cp.setShortName(viewProductSearchInfo.getProductName());
				cp.setMarketPrice(viewProductSearchInfo.getMarketPrice());
				cp.setSellPrice(viewProductSearchInfo.getSellPrice());
				
				if (!StringUtil.isEmptyString(viewProductSearchInfo.getSmallImage())) {
					cp.setIcon(viewProductSearchInfo.getSmallImage());
				} else {
					cp.setIcon(viewProductSearchInfo.getLargeImage());
				}
				cp.setSmallImage(viewProductSearchInfo.getSmallImage());
				cp.setCouponAble("true");
				cpList.add(cp);
			} else {
				for (ProdBranchSearchInfo viewProdProductBranch : viewProductSearchInfo.getProdBranchSearchInfoList()) {
					ClientProduct cp = new ClientProduct();
					BeanUtils.copyProperties(viewProductSearchInfo, cp);
					cp.setCouponAble("true");
					cp.setShortName(viewProdProductBranch.getBranchName());
					cp.setSubProductType(viewProductSearchInfo.getSubProductType());
					cp.setProdBranchId(viewProdProductBranch.getProdBranchId());
					cp.setProductId(viewProdProductBranch.getProductId());
					cp.setDescription(viewProdProductBranch.getDescription());
					
					if(!StringUtil.isEmptyString(viewProdProductBranch.getIcon())){
						cp.setIcon(viewProdProductBranch.getIcon());
					} else {
						if (!StringUtil.isEmptyString(viewProductSearchInfo.getSmallImage())) {
							cp.setIcon(viewProductSearchInfo.getSmallImage());
						} else {
							cp.setIcon(viewProductSearchInfo.getLargeImage());
						}
						
					}
					cp.setBedType(viewProdProductBranch.getBedType());
					cp.setBroadband(viewProdProductBranch.getBroadband());
					cpList.add(cp);
				}
			}
		}
		return cpList;
	}
	
	
	public static void copyProductSearchInfo(ProductSearchInfo viewProductSearchInfo,List<ClientProduct> cpList){
		ClientProduct cp = new ClientProduct();
		BeanUtils.copyProperties(viewProductSearchInfo, cp);
		cp.setShortName(viewProductSearchInfo.getProductName());
		cp.setMarketPrice(viewProductSearchInfo.getMarketPrice());
		cp.setSellPrice(viewProductSearchInfo.getSellPrice());
		if (!StringUtil.isEmptyString(viewProductSearchInfo.getSmallImage())) {
			cp.setIcon(viewProductSearchInfo.getSmallImage());
		} else {
			cp.setIcon(viewProductSearchInfo.getLargeImage());
		}
		cp.setSmallImage(viewProductSearchInfo.getSmallImage());
		cp.setCouponAble("true");
		cpList.add(cp);
	}
	
	
	
	
	public static boolean canUserClientPromotion(ProdProduct mainProduct ,String promotionAble){
		if("true".equals(mainProduct.getCouponAble())&&"true".equals(promotionAble)){
			return true;
		}
		return false;
	}
	
	public static boolean canUserCoupon(ProdProduct mainProduct){
		if (mainProduct.isPaymentToLvmama()&&"true".equals(mainProduct.getCouponAble())){
			return true;
		}
		return false;
	}
	
	public static boolean isCanCommitOrder(Map<String,Object> errorInfo){
		if(errorInfo.isEmpty()){
			return true;
		}
		return false;
	}
	
	public static String getErrorInfo(Map<String,Object> errorInfo){
		return errorInfo.get("errorInfo").toString();
	}
	
	
	public static void promotionSet(String promotionEnabled,ProdProduct mainProduct){
		if ("true".equals(promotionEnabled)) {
			mainProduct.setCouponAble("true");
			
		} else {
			if(mainProduct.getCouponAble()==null||"".equals(mainProduct.getCouponAble())){
				mainProduct.setCouponAble("true");
			} 
		}
	}
	
	
	/**
	 * 获得最晚预订时间
	 */
	public  static String getLeatestOrder(Date leatestVisit,Long lastTicketTime,Long lastPassTime,long PayWaitTime){
		if(lastTicketTime==null){
			lastTicketTime=0L;
		}
		
		if(lastPassTime==null){
			lastPassTime=0L;
		}
		if(leatestVisit==null){
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.set(Calendar.HOUR, 23);
			leatestVisit = c.getTime();
		}
		Float lastTicketTimeSecond = ((lastTicketTime/60f)*60*60);
		Float lastPassTimeSecond =   lastPassTime/60f*60*60;
		Long d = Math.abs(leatestVisit.getTime()/1000)-lastTicketTimeSecond.longValue()-lastPassTimeSecond.longValue()-PayWaitTime;
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
		String fd = sf.format(new Date(d*1000L));
		return fd;
	}
	
	public static List<String> convetToList(String strs) {
		String[] array = strs.split(",");
		List<String> list = new ArrayList<String>();
		for (String string : array) {
			if(!StringUtil.isEmptyString(string)){
				list.add(string);
			}
		}
		return list;
	}
	

	/**
	 * 截取附加产品（保险）的链接. 
	 * @param str
	 * @return
	 */
	public static  String spliteDescByStr(String str) {
		if(StringUtils.isEmpty(str)) {
			return "";
		}
		int i = str.indexOf("详细信息请见");
		if(i != -1) {
			return str.substring(0,i);
		}
		str = str.replaceAll("（", "(");
		str = str.replaceAll("）", ")");
		return str;
	}
	
	/**
	 * 初始化产品 tag标签 ，如积奖金折扣 早订早惠 
	 * @param productId 产品id
	 * @param mpr       线路详情模型
	 * @param canPayByBonus 是否支持奖金折扣  Y 
	 */
	public static void initProductSearchInfos(List<ProductSearchInfo> productSearchInfoList,MobileProductRoute mpr) {
		if(null != productSearchInfoList && productSearchInfoList.size() > 0) {
			// 优惠信息标签 
			ProductSearchInfo psi = productSearchInfoList.get(0);
			Map<String,Object> map = getPreferentialTags(psi); // 5.0版本
			mpr.setPreferentialInfo(null==map.get("info")?"":map.get("info").toString()); // 优惠信息内容  
			mpr.setPreferentialTags(null==map.get("tags")?"":map.get("tags").toString());  // 标签
			// 是否支持多定多惠，早定早惠
			mpr.setHasBusinessCoupon(Boolean.parseBoolean(map.get("hasBusinessCoupon").toString())); 
			// 返现金额
			mpr.setMaxCashRefund(PriceUtil.convertToFen(psi.getCashRefund()));
			
			/*********V5.0********/
			// 是否客户端独享 
			mpr.setMobileAlone(ClientUtils.mobileAlone(psi));
			// 活动标签 
			mpr.setTagList(ClientUtils.getMobileProdTags(psi,mpr.isMobileAlone()));
			//  抵扣
			mpr.setCanDeduction(Boolean.parseBoolean(map.get("canDeduction").toString()));
			// 抵扣信息 
			mpr.setDeductionDesc(null==map.get("deductionDesc")?"":map.get("deductionDesc").toString());
			// 优惠信息 
			mpr.setFavourableDesc((null==map.get("favourableDesc")?"":map.get("favourableDesc").toString())); 
		}
	}
	
	
	/**
	 * 获取团购优惠标签.
	 * @param cg   团购对象 
	 * @param psi  搜索的对象 
	 */
	public static void getGouponBuyPreferInfo(MobileGroupOn cg,ProductSearchInfo psi){
		if(null != psi) {
			if("true".equals(psi.getPayToLvmama())) {
				cg.setPayTarget(Constant.PAYMENT_TARGET.TOLVMAMA.getCode());
			} else {
				cg.setPayTarget(Constant.PAYMENT_TARGET.TOSUPPLIER.getCode());
			}
		}
		
		// 优惠信息标签 
		Map<String,Object> map = getPreferentialTags(psi);
		cg.setPreferentialInfo(null==map.get("info")?"":map.get("info").toString()); // 优惠信息内容  
		cg.setPreferentialTags(null==map.get("tags")?"":map.get("tags").toString());  // 标签
		cg.setHasBusinessCoupon(Boolean.parseBoolean(map.get("hasBusinessCoupon").toString())); // 是否支持多定多惠，早定早惠
		// 返现金额
		cg.setMaxCashRefund(PriceUtil.convertToFen(psi.getCashRefund()));
		
		/*********V5.0********/
		// 是否客户端独享 
		cg.setMobileAlone(ClientUtils.mobileAlone(psi));
		// 活动标签 
		cg.setTagList(ClientUtils.getMobileProdTags(psi,cg.isMobileAlone()));
		//  抵扣
		cg.setCanDeduction(Boolean.parseBoolean(map.get("canDeduction").toString()));
		// 优惠 
		cg.setFavourableDesc(map.get("favourableDesc").toString());
		// 抵扣desc
		cg.setDeductionDesc(map.get("deductionDesc").toString()); 
	}
	
	
	/**
	 * 获取团购优惠标签.
	 * @param cg   团购对象 
	 * @param psi  搜索的对象 
	 */
	public static boolean needVisa(ProdCProduct prodCProduct){
		// 判断是否需要签证 
		if(null != prodCProduct && null!=prodCProduct.getProdRoute()&&StringUtils.isNotBlank(prodCProduct.getProdRoute().getCountry())
			    &&StringUtils.isNotBlank(prodCProduct.getProdRoute().getCity())
				&&StringUtils.isNotBlank(prodCProduct.getProdRoute().getVisaType())){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 获得该优惠券优惠金额或折扣
	 * @return
	 */
	public static String getFavorTypeAmount(MarkCoupon mc){
		if(null ==  mc) {
			return "0";
		}
		if(Constant.FAVOR_TYPE.AMOUNT_AMOUNT_WHOLE.name().equals(mc.getFavorType())){
			return String.valueOf(PriceUtil.convertToYuan(mc.getFavorTypeAmount()));
		}else if(Constant.FAVOR_TYPE.AMOUNT_AMOUNT_INTERVAL.name().equals(mc.getFavorType())){
			return String.valueOf(PriceUtil.convertToYuan(mc.getFavorTypeAmount()));
		}else if(Constant.FAVOR_TYPE.AMOUNT_QUANTITY_WHOLE.name().equals(mc.getFavorType())){
			return String.valueOf(PriceUtil.convertToYuan(mc.getFavorTypeAmount()));
		}else if(Constant.FAVOR_TYPE.AMOUNT_QUANTITY_INTERVAL.name().equals(mc.getFavorType())){
			return String.valueOf(PriceUtil.convertToYuan(mc.getFavorTypeAmount()));
		}else if(Constant.FAVOR_TYPE.AMOUNT_QUANTITY_PRE.name().equals(mc.getFavorType())){
			return String.valueOf(PriceUtil.convertToYuan(mc.getFavorTypeAmount()));
		}else if(Constant.FAVOR_TYPE.DISCOUNT_AMOUNT_WHOLE.name().equals(mc.getFavorType())){
			return String.valueOf(mc.getFavorTypeAmount().floatValue()/10);
		}else if(Constant.FAVOR_TYPE.DISCOUNT_QUANTITY_WHOLE.name().equals(mc.getFavorType())){
			return String.valueOf(mc.getFavorTypeAmount().floatValue()/10);
		}else if(Constant.FAVOR_TYPE.DISCOUNT_QUANTITY_PRE.name().equals(mc.getFavorType())){
			return String.valueOf(mc.getFavorTypeAmount().floatValue()/10);
		}
		return "0";
	}
	
	
	/**
	 * 获取订单优惠券金额列表
	 */
	public float getYouHuiAmountList(List<OrdOrderAmountItem> listAmountItem) {
		Long itemAmount = 0l;
		if (null != listAmountItem && !listAmountItem.isEmpty()) {
			for (OrdOrderAmountItem item : listAmountItem) {
				// ORDER_COUPON_AMOUNT("订单优惠金额"), 
				if (item.isCouponItem()) {
					itemAmount += item.getItemAmount();
				}
			}
		}
		return PriceUtil.convertToYuan(itemAmount);
	}
	
	/** 
     * 使用java正则表达式去掉多余的.与0 
     * @param s 
     * @return  
     */  
    public static String subZeroAndDot(String s){  
        if(s.indexOf(".") > 0){  
            s = s.replaceAll("0+?$", "");//去掉多余的0  
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }  
        return s;  
    }  
    
    /**
     * 过滤单引号和双引号 
     * @param inputString
     * @return
     */
    public static String filterQuotationMarks(String inputString) {
    	if(StringUtils.isEmpty(inputString)) {
			return "";
		}
    	return inputString.replaceAll("\"", "").replaceAll("'", "");
    }
    /**
	 * 过滤html文档
	 * @param inputString
	 * @return
	 */
	public static String filterOutHTMLTags(String inputString) {
		if(StringUtils.isEmpty(inputString)) {
			return "";
		}
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>																						// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			String regEx_html = "<[^>]+>|&nbsp;|&amp;|nbsp;|nbsp"; // 定义HTML标签的正则表达式

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			textStr = htmlStr;
		} catch (Exception e) {
			System.err.println("Super_clutter ClientUtils Html2Text: " + e.getMessage());
		}
		return replaceAllN(textStr);// 返回文本字符串
	}
	
	/**
	 * 
	 * @param url
	 */
	public static String contentTag(String str){
		if(StringUtils.isEmpty(str)) {
			return "①";
		}
		try {
			if(str.indexOf("④") != -1) {
				return "⑤";
			} else if(str.indexOf("③") != -1) {
				return "④";
			}if(str.indexOf("②") != -1) {
				return "③";
			}if(str.indexOf("①") != -1) {
				return "②";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "①";
	}
	
	/**
	 * 去掉字符串中连续所有重复的字符串.
	 * @param str
	 * @return
	 */
	public static String replaceAllTheSameChar(String str) {
		if(StringUtils.isEmpty(str)) {
			return "";
		}
		return str.replaceAll("(.)\\1+", "$1");
	}
	
	/**
	 * 去掉字符串中重复的/n,把多个/n合并成一个.
	 * @param str
	 * @return
	 */
	public static String replaceAllN(String str) {
		if(StringUtils.isEmpty(str)) {
			return "";
		}
		str = str.replaceAll(" +", "").replaceAll("\\n+", "\n");
		// 如果第一个字符是/n ,则去掉 /n
		if(str.indexOf("\n") == 0) {
			str = str.replaceFirst("\n", "");
		}
		return  str;
	}
	
	/**
	 * 需要资源审核的门票是否可支付 
	 * @param order
	 * @return
	 */
	public static boolean isNeedResourceTicketCanToPay(MobileOrder order) {
		if(null == order) {
			return false;
		}
		
	    // 门票 ，需要资源审核 ，审核状态不是待支付 和 部分支付  ,目的地自由行可以支付 
		if((Constant.PRODUCT_TYPE.TICKET.getCode().equals(order.getMainProductType()) 
				|| (Constant.PRODUCT_TYPE.ROUTE.getCode().equals(order.getMainProductType())
						&& Constant.SUB_PRODUCT_TYPE.FREENESS.getCode().equals(order.getOrderType())  ) )
				&& order.isNeedResourceConfirm() 
				&& !Constant.ORDER_VIEW_STATUS.UNPAY.getCode().equals(order.getOrderViewStatus()) 
				&& !Constant.ORDER_VIEW_STATUS.PARTPAY.getCode().equals(order.getOrderViewStatus()) ) {
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * 从配置所有优惠券ID中随机生成一个优惠券ID
	 * 
	 * @param strCouponIds
	 * @return
	 */
	public static long getFirstCouponId(String strCouponIds) {
		if (StringUtils.isEmpty(strCouponIds)) {
			throw new LogicException("优惠券ID不能为空!");
		}
		String[] strcouponIdArr = strCouponIds.split(",");
		return Long.parseLong(strcouponIdArr[0]);
	}
	
	/**
	 * 判断当前日期是否在指定的日期内
	 * @param startDate 开始日期，null 为不判断开始日期 
	 * @param endDate  结束日期 ，null 为不判断结束日期 
	 * @param formatStr  日期格式 如 yyyy-MM-dd HH:mm:ss
	 * @return boolean 
	 */
	public static boolean isValidateDate(String startDate,String endDate,String formatStr){
		Date currentDate = new Date();
		// 如果当前日期大于结束日期 
		if(!StringUtil.isEmptyString(endDate)) {
			Date endD = com.lvmama.comm.utils.DateUtil.stringToDate(endDate, formatStr);
			if(currentDate.getTime() > endD.getTime()) {
				return false;
			}
		}
		
		// 如果当前日期小于开始日期
		if(!StringUtil.isEmptyString(startDate)) {
			Date strD = com.lvmama.comm.utils.DateUtil.stringToDate(startDate, formatStr);
			if(strD.getTime() > currentDate.getTime()) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 截取产品名称，最多50个中文 
	 * @param productName
	 * @return
	 */
	public static String subProductName(String productName) {
		if(StringUtils.isEmpty(productName)) {
			return "";
		}
		try {
			if(productName.length() > 47) {
				productName = productName.substring(0,47) + "...";
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return productName;
	} 
	
	/**
	 * 获取参数
	 * 
	 * @param param
	 * @param key
	 *            参数关键字
	 * @param clazz
	 *            转换对象类型
	 * @param isNotNull
	 *            可否为空
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getT(Map<String, Object> param, String key, Class<T> clazz) {
		Object value = param.get(key);
		T t = null;
		try {
			if (null != value) {
				if (Date.class.equals(clazz)) {
						return (T) DateUtils.parseDate((String) value, "yyyy-MM-dd");
				} else if (Integer.class.equals(clazz)) {
					return (T) Integer.valueOf((String) value);
				} else if (Long.class.equals(clazz)) {
					return (T) Long.valueOf((String) value);
				} else if (Double.class.equals(clazz)) {
					return (T) Double.valueOf((String) value);
				} else if (BigDecimal.class.equals(clazz)) {
					return (T) BigDecimal.valueOf(Double.valueOf((String) value));
				} else if (Boolean.class.equals(clazz)) {
					return (T) Boolean.valueOf((String) value);
				}
				t = (T) value;
			} 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}
	
	/**
	 * 是否手机独享 -  product_search_info表中channel只为CLIENT时表示手机独享 ；
	 * @return
	 */
	public static boolean mobileAlone(ProductSearchInfo psi) {
		if(null != psi) {
			return isMoileAlone(psi.getChannel());
		} else {
			return false;
		}
	}
	
	/**
	 * 是否手机独享 -  product_search_info表中channel只为CLIENT时表示手机独享 ；
	 * @return
	 */
	public static boolean mobileAlone(ProdBranchSearchInfo psi) {
		if(null != psi) {
			return isMoileAlone(psi.getChannel());
		} else {
			return false;
		}
	}
	
	public static boolean isMoileAlone(String channel) {
		boolean b = false;
		if(!StringUtils.isEmpty(channel)) {
			if(Constant.CHANNEL.CLIENT.name().equalsIgnoreCase(channel) 
					|| "BACKEND,CLIENT".equals(channel)
					|| "CLIENT,BACKEND".equals(channel) ) {
				return true;
			}
		}
		return b;
		
	}
	
	/**
	 * 标签活动 ；
	 * @return
	 */
	public static List<MobileProdTag> getMobileProdTags(ProductSearchInfo psi,boolean mobileAlone) {
		List<MobileProdTag> listTags = new ArrayList<MobileProdTag>();
		/** 主站逻辑 pageConfig  around_list.ftl  20 行
		 *  <#list product.tagList as t>
            	<#if t.tagGroupName!='优惠' && t.tagGroupName!='抵扣'>
            		<span <#if t.description!="" >tip-content="${t.description}"</#if> class="${t.cssId}">${t.tagName}</span>
            	</#if>
            </#list>
		 */
		
		if(null != psi) {
			List<ProdTag> pList = psi.getTagList();
			if(null != pList && pList.size() > 0) {
				for(ProdTag pd :pList) {
					MobileProdTag mpt = new MobileProdTag();
					mpt.setTagId(pd.getTagId());
					mpt.setTagName(pd.getTagName());
					//mpt.setDescription(pd.getDescription());
					if(!"优惠".equals(pd.getTagGroupName()) && !"抵扣".equals(pd.getTagGroupName()) ) {
						listTags.add(mpt);
						if(mobileAlone) {
							break;
						}
						if(listTags.size() == 2) {
							break;
						}
					}
				}
			}
		}
		
		return listTags;
	}
	
	/**
	 * 线路搜索信息 
	 * @param psi
	 * @param mp
	 */
	public static void initMobileProductTitle4V50(ProductSearchInfo psi,MobileProductTitle mp ) {
	   if(null != mp) {
			// 是否客户端独享 
			mp.setMobileAlone(ClientUtils.mobileAlone(psi));
			// 活动标签 
			mp.setTagList(ClientUtils.getMobileProdTags(psi,mp.isMobileAlone()));
			// 优惠  和 抵扣
		    initTagsInfo4V50(psi,mp);
	   }
	}
	
	/**
	 * 我的收藏搜索信息 
	 * @param psi
	 * @param mp
	 */
	public static void initMobileProductTitle4V50(ProductSearchInfo psi,MobileMyFavorite mp ) {
	   if(null != mp) {
			// 是否客户端独享 
			mp.setMobileAlone(ClientUtils.mobileAlone(psi));
			// 活动标签 
			mp.setTagList(ClientUtils.getMobileProdTags(psi,mp.isMobileAlone()));
			// 优惠  和 抵扣
		    initTagsInfo4V50(psi,mp);
	   }
	}
	
	/**
	 * 过滤特殊字符. 
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String StringFilter(String str) throws PatternSyntaxException {
		if(StringUtils.isEmpty(str)) {
			return "";
		}
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/** 
     * 功能：验证字符串长度是否符合要求，一个汉字等于两个字符
     * @param strParameter 要验证的字符串
     * @param limitLength 验证的长度
     * @return 符合长度ture 超出范围false
     */
	public static boolean validateStrByLength(String strParameter, int limitLength) {
		int temp_int = strLength(strParameter); // 字符串长度 

		if (temp_int > limitLength) {
			return false;
		} else {
			return true;
		}
	}
	
	/** 
     * 功能：验证字符串长度是否符合要求，一个汉字等于两个字符
     * @param strParameter 要验证的字符串
     * @param minLength 验证的最小长度
     * @param maxLength 验证的最大长度
     * @return 符合长度ture 超出范围false
     */
	public static boolean validateStrByLength(String strParameter,int minLength, int maxLength) {
		int temp_int = strLength(strParameter); // 字符串长度 
		if (temp_int > maxLength || temp_int < minLength) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 计算字符串长度 ，一个中文2个字符 
	 * @param value
	 * @return
	 */
	public static int strLength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }
	
	/**
	 * 计算距离,单位米 
	 * @param lat1
	 * @param lat2
	 * @param lon1
	 * @param lon2
	 * @return
	 */
	public static double getDistatce(double lat1, double lat2, double lon1, double lon2) { 
        double R = 6371; 
        Double distance = 0.0; 
        double dLat = (lat2 - lat1) * Math.PI / 180; 
        double dLon = (lon2 - lon1) * Math.PI / 180; 
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) 
                + Math.cos(lat1 * Math.PI / 180) 
                * Math.cos(lat2 * Math.PI / 180) * Math.sin(dLon / 2) 
                * Math.sin(dLon / 2); 
        distance = (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))) * R *1000; 
        
        return distance.intValue(); 
    }
	
	/**
	 * 是否首发包
	 * @param channel
	 * @return
	 */
	public static boolean isExtChannel(String channel) {
		if (("IPHONE_IOS_91").equalsIgnoreCase(channel) 
				|| ("ANDROID_HIAPK").equalsIgnoreCase(channel) 
				|| ("ANDROID_ANDROID_91").equalsIgnoreCase(channel)) { 
			return true;
		}
		
		return false;
	}

	public static void main(String[] args){
		//String str = "\n \n \n① 订单提交成功后，驴妈妈将会发订单确认短信到你的手机，凭短信可顺利入园。 \n \n \n关于优惠 \n \n成人票、儿童票（1.2米以下免票）、学生票等价格相同，无其他票价优惠政策；www.lvmama.com \n\n\n \n\n \n \n \n \n\n\n \n\n \n关于取票 \n \n请携带身份证前往万达影城海棠秀服务台，取票观看演出； \n\n\n \n\n \n \n \n \n\n\n \n\n \n关于穿梭巴士 \n \n请关注交通信息中的免费穿梭巴士时刻表； \n\n\n \n\n\n \n \n \n \n\n\n \n\n \n关于演出时间 \n \n海棠秀演出时间：每周周一、周二、周三、周五、周六19:00-20:30（90分钟）； \n\n\n \n\n \n \n \n \n\n\n \n\n \n关于儿童 \n \n未成年儿童需在家长陪同下凭票入场； \n\n\n \n\n \n \n \n \n\n\n \n\n \n关于拍照 \n \n演出涉及高科技效果应用，演出过程中请勿使用手机、照相机、摄像机等电子设备。 \n\n\n \n\n \n \n \n \n\n\n \n\n \n";
		/*String str = "\"开心驴行\"—日照海滨、连云港花果山、云龙涧纯玩3日游(上海出发)";*/
		System.out.println("" + StringFilter("asasdfasd[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%"));
		System.out.println("" + isValidateDate("2013-10-03","2013-11-25","yyyy-MM-dd HH:mm:ss"));
		System.out.println("" + isValidateDate("2013-10-23","2013-10-11","yyyy-MM-dd HH:mm:ss"));
		System.out.println("" + isValidateDate("2012-10-23","2013-11-27","yyyy-MM-dd HH:mm:ss"));
	}
	
	public static boolean isQingHost(HttpServletRequest request) {
		return "qing.lvmama.com".equals(request.getHeader("Host"));
	}
}	
