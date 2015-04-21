package com.lvmama.clutter.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class RefundUtils {

	/**
	 * 获得多返金额(单位元)
	 * 
	 * @param maxCashRefund
	 * @param productType
	 * @return
	 */
	public static float getMoreMobileRefundYuan(Long maxCashRefund,
			String productType) {

//		String strDoubleRefundDate = Constant.getInstance().getValue(
//				"mobile.double.refund.date");// 双倍返现
//		if (!StringUtils.isEmpty(strDoubleRefundDate)) {
//			try {
//				Date doubleRefundDate = DateUtils.parseDate(
//						strDoubleRefundDate.trim(), "yyyy-MM-dd");
//				if (Calendar.getInstance().getTime().before(doubleRefundDate)) {// 当前日期在双倍返现之前
//					return PriceUtil.convertToYuan(maxCashRefund);
//				}
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		}
//		if (maxCashRefund != 0) {
//			if (Constant.PRODUCT_TYPE.TICKET.getCode().equals(productType)) {// 门票
//				return PriceUtil.convertToYuan(ClutterConstant
//						.getMobileTiketRefund());
//			} else if (Constant.PRODUCT_TYPE.ROUTE.getCode()
//					.equals(productType)) { // 线路
//				return PriceUtil.convertToYuan(ClutterConstant
//						.getMobileRouteRefund());
//			} else {
//				return 0;
//			}
//		} else {
//			return 0;
//		}
		float totalRefundYuan = getMobileRefundYuan(maxCashRefund,productType);
		return totalRefundYuan-PriceUtil.convertToYuan(maxCashRefund);//总返现金额减去网站返的金额即为多返金额
	}

	/**
	 * 获取门票多返金额(单位元)
	 * 
	 * @param maxCashRefund
	 * @return
	 */
	public static float getTicketMoreMobileRefundYuan(Long maxCashRefund) {
		return getMoreMobileRefundYuan(maxCashRefund,
				Constant.PRODUCT_TYPE.TICKET.getCode());

	}

	/**
	 * 获取线路多返金额(单位元)
	 * 
	 * @param maxCashRefund
	 * @return
	 */
	public static float getRouteMoreMobileRefundYuan(Long maxCashRefund) {
		return getMoreMobileRefundYuan(maxCashRefund,
				Constant.PRODUCT_TYPE.ROUTE.getCode());
	}

	/**
	 * 获得多返金额:单位分
	 * 
	 * @param maxCashRefund
	 * @param productType
	 * @return
	 */
	public static long getMoreMobileRefundFen(Long maxCashRefund,
			String productType) {

//		String strDoubleRefundDate = Constant.getInstance().getValue(
//				"mobile.double.refund.date");// 双倍返现
//		if (!StringUtils.isEmpty(strDoubleRefundDate)) {
//			try {
//				Date doubleRefundDate = DateUtils.parseDate(
//						strDoubleRefundDate.trim(), "yyyy-MM-dd");
//				if (Calendar.getInstance().getTime().before(doubleRefundDate)) {// 当前日期在双倍返现之前
//					return maxCashRefund;
//				}
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		}
//
//		if (maxCashRefund != 0) {
//			if (Constant.PRODUCT_TYPE.TICKET.getCode().equals(productType)) {// 门票
//				return ClutterConstant.getMobileTiketRefund();
//			} else { // 线路
//				return ClutterConstant.getMobileRouteRefund();
//			}
//		} else {
//			return 0;
//		}
		
		long totalRefundFen = getMobileRefundFen(maxCashRefund,productType);
		return totalRefundFen-maxCashRefund;//总返现金额减去网站返的金额即为多返金额

	}

	/**
	 * 获取门票多返金额:单位分
	 * 
	 * @param maxCashRefund
	 * @return
	 */
	public static long getTicketMoreMobileRefundFen(Long maxCashRefund) {
		return getMoreMobileRefundFen(maxCashRefund,
				Constant.PRODUCT_TYPE.TICKET.getCode());

	}

	/**
	 * 获取线路多返金额:单位分
	 * 
	 * @param maxCashRefund
	 * @return
	 */
	public static long getRouteMoreMobileRefundFen(Long maxCashRefund) {
		return getMoreMobileRefundFen(maxCashRefund,
				Constant.PRODUCT_TYPE.ROUTE.getCode());
	}

	/**
	 * 获得返现金额（单位分）
	 * 
	 * @param maxCashRefund
	 * @param productType
	 * @return
	 */
	public static long getMobileRefundFen(long maxCashRefund, String productType) {

		String strDoubleRefundDate = Constant.getInstance().getValue(
				"mobile.double.refund.date");// 双倍返现
		String strPlaceRate = Constant.getInstance().getValue(
				"mobile.double.refund.rate.place");// 景点返现率
		String strPouteRate = Constant.getInstance().getValue(
				"mobile.double.refund.rate.route");// 线路返现率
		if (!StringUtils.isEmpty(strDoubleRefundDate)) {
			try {
				Date doubleRefundDate = DateUtils.parseDate(
						strDoubleRefundDate.trim(), "yyyy-MM-dd");
				if (Calendar.getInstance().getTime().before(doubleRefundDate)) {// 当前日期在双倍返现之前
					if (Constant.PRODUCT_TYPE.TICKET.getCode().equals(productType)) {// 门票
						if(!StringUtils.isEmpty(strPouteRate)){
							double placeRate = Double.parseDouble(strPlaceRate);
							return PriceUtil.convertToFen((float) Math.ceil(PriceUtil.convertToYuan((long)(maxCashRefund * placeRate))));
						}else{
							return maxCashRefund;
						}
					}else if(Constant.PRODUCT_TYPE.ROUTE.getCode().equals(productType)){
						if(!StringUtils.isEmpty(strPouteRate)){
							double routeRate = Double.parseDouble(strPouteRate);
							return PriceUtil.convertToFen((float) Math.ceil(PriceUtil.convertToYuan((long)(maxCashRefund * routeRate))));
						}else{
							return maxCashRefund;
						}
					}else{//其他类型产品返现
						return maxCashRefund;
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (maxCashRefund != 0) {
			if (Constant.PRODUCT_TYPE.TICKET.getCode().equals(productType)) {// 门票
				return maxCashRefund + ClutterConstant.getMobileTiketRefund();
			} else { // 线路
				return maxCashRefund + ClutterConstant.getMobileRouteRefund();
			}
		} else {
			return 0;
		}

	}

	/**
	 * 获得门票返现金额（单位分）
	 * 
	 * @param maxCashRefund
	 * @param productType
	 * @return
	 */
	public static long getTicketMobileRefundFen(Long maxCashRefund) {
		return getMobileRefundFen(maxCashRefund,
				Constant.PRODUCT_TYPE.TICKET.getCode());

	}

	/**
	 * 获得线路返现金额（单位分）
	 * 
	 * @param maxCashRefund
	 * @param productType
	 * @return
	 */
	public static long getRouteMobileRefundFen(Long maxCashRefund) {
		return getMobileRefundFen(maxCashRefund,
				Constant.PRODUCT_TYPE.ROUTE.getCode());
	}

	/**
	 * 获得返现金额（单位元）
	 * 
	 * @param maxCashRefund
	 * @param productType
	 * @return
	 */
	public static float getMobileRefundYuan(long maxCashRefund,
			String productType) {

		String strDoubleRefundDate = Constant.getInstance().getValue(
				"mobile.double.refund.date");// 双倍返现
		String strPlaceRate = Constant.getInstance().getValue(
				"mobile.double.refund.rate.place");// 景点返现率
		String strPouteRate = Constant.getInstance().getValue(
				"mobile.double.refund.rate.route");// 线路返现率
		if (!StringUtils.isEmpty(strDoubleRefundDate)) {
			try {
				Date doubleRefundDate = DateUtils.parseDate(
						strDoubleRefundDate.trim(), "yyyy-MM-dd");
				if (Calendar.getInstance().getTime().before(doubleRefundDate)) {// 当前日期在双倍返现之前
					if (Constant.PRODUCT_TYPE.TICKET.getCode().equals(productType)) {// 门票
						if(!StringUtils.isEmpty(strPouteRate)){
							double placeRate = Double.parseDouble(strPlaceRate);
							return (float) Math.ceil(PriceUtil.convertToYuan((long)(maxCashRefund * placeRate)));
						}else{
							return PriceUtil.convertToYuan(maxCashRefund);
						}
					}else if(Constant.PRODUCT_TYPE.ROUTE.getCode().equals(productType)){
						if(!StringUtils.isEmpty(strPouteRate)){
							double routeRate = Double.parseDouble(strPouteRate);
							return (float) Math.ceil(PriceUtil.convertToYuan((long)(maxCashRefund * routeRate)));
						}else{
							return PriceUtil.convertToYuan(maxCashRefund);
						}
					}else{//其他类型产品返现
						return maxCashRefund;
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (maxCashRefund != 0) {
			if (Constant.PRODUCT_TYPE.TICKET.getCode().equals(productType)) {// 门票
				return PriceUtil.convertToYuan(maxCashRefund
						+ ClutterConstant.getMobileTiketRefund());
			} else { // 线路
				return PriceUtil.convertToYuan(maxCashRefund
						+ ClutterConstant.getMobileRouteRefund());
			}
		} else {
			return 0;
		}
	}

	/**
	 * 获得门票返现金额（单位元）
	 * 
	 * @param maxCashRefund
	 * @param productType
	 * @return
	 */
	public static float getTicketMobileRefundYuan(Long maxCashRefund) {
		return getMobileRefundYuan(maxCashRefund,
				Constant.PRODUCT_TYPE.TICKET.getCode());

	}

	/**
	 * 获得线路返现金额（单位元）
	 * 
	 * @param maxCashRefund
	 * @param productType
	 * @return
	 */
	public static float getRouteMobileRefundYuan(Long maxCashRefund) {
		return getMobileRefundYuan(maxCashRefund,
				Constant.PRODUCT_TYPE.ROUTE.getCode());
	}
	
	/**
	 * 根据总返现金额获取客户端多返金额(单位元)
	 * 
	 * @param totalCashRefund 总返现金额
	 * @param productType
	 * @return
	 */
	public static float getClientMoreCashRefundByTotalCashRefund(long totalCashRefund,
			String productType) {

		String strDoubleRefundDate = Constant.getInstance().getValue(
				"mobile.double.refund.date");// 双倍返现
		String strPlaceRate = Constant.getInstance().getValue(
				"mobile.double.refund.rate.place");// 景点返现率
		String strPouteRate = Constant.getInstance().getValue(
				"mobile.double.refund.rate.route");// 线路返现率
		if (!StringUtils.isEmpty(strDoubleRefundDate)) {
			try {
				Date doubleRefundDate = DateUtils.parseDate(
						strDoubleRefundDate.trim(), "yyyy-MM-dd");
				if (Calendar.getInstance().getTime().before(doubleRefundDate)) {// 当前日期在双倍返现之前
					if (Constant.PRODUCT_TYPE.TICKET.getCode().equals(productType)) {// 门票
						if(!StringUtils.isEmpty(strPouteRate)){
							double placeRate = Double.parseDouble(strPlaceRate);
							long pcCashRefund = PriceUtil.convertToFen((float) Math.floor(PriceUtil.convertToYuan((long)(totalCashRefund/placeRate))));
							return PriceUtil.convertToYuan((long)(totalCashRefund-pcCashRefund));
						}else{
							return 0;
						}
					}else if(Constant.PRODUCT_TYPE.ROUTE.getCode().equals(productType)){
						if(!StringUtils.isEmpty(strPouteRate)){
							double routeRate = Double.parseDouble(strPouteRate);
							long pcCashRefund = PriceUtil.convertToFen((float) Math.floor(PriceUtil.convertToYuan((long)(totalCashRefund/routeRate))));
							return PriceUtil.convertToYuan((long)(totalCashRefund-pcCashRefund));
						}else{
							return 0;
						}
					}else{//其他类型产品返现
						return 0;
					}
//					if(0!=totalCashRefund){
//						return PriceUtil.convertToYuan(totalCashRefund/2);
//					}else{
//						return 0;
//					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (totalCashRefund != 0) {
			if (Constant.PRODUCT_TYPE.TICKET.getCode().equals(productType)) {// 门票
				return PriceUtil.convertToYuan(ClutterConstant
						.getMobileTiketRefund());
			} else if (Constant.PRODUCT_TYPE.ROUTE.getCode()
					.equals(productType)) { // 线路
				return PriceUtil.convertToYuan(ClutterConstant
						.getMobileRouteRefund());
			} else {
				return 0;
			}
		} else {
			return 0;
		}

	}

	/**
	 * 根据门票总返现金额获取客户端多返金额(单位元)
	 * 
	 * @param totalCashRefund 总返现金额
	 * @param productType
	 * @return
	 */
	public static float getTicketClientMoreCashRefundByTotalCashRefund(long totalCashRefund) {
		return getClientMoreCashRefundByTotalCashRefund(totalCashRefund,
				Constant.PRODUCT_TYPE.TICKET.getCode());

	}

	/**
	 * 根据线路总返现金额获取客户端多返金额(单位元)
	 * 
	 * @param totalCashRefund 总返现金额
	 * @param productType
	 * @return
	 */
	public static float getRouteClientMoreCashRefundByTotalCashRefund(long totalCashRefund) {
		return getClientMoreCashRefundByTotalCashRefund(totalCashRefund,
				Constant.PRODUCT_TYPE.ROUTE.getCode());
	}

}
