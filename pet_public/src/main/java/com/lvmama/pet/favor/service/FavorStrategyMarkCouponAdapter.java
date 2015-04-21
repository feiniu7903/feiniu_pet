package com.lvmama.pet.favor.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.businessCoupon.BusinessCoupon;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.mark.MarkCouponProduct;
import com.lvmama.comm.pet.vo.favor.FavorStrategy;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 该类存在的目的是将MarkCoupon类转变成FavorStrategy类，或者将FavorStrategy类转化成MarkCoupon类。非标准的适配器模式，主要不想在po中使用service。
 * @author Brian
 *
 */
public final class FavorStrategyMarkCouponAdapter {
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(FavorStrategyMarkCouponAdapter.class);
	/**
	 * 优惠策略的包路径
	 */
	private static final String ORDER_STRATEGY_CLASSPATH= "com.lvmama.comm.pet.vo.favor.strategy.OrderFavorStrategyFor";
	
	private static final String PRODUCT_STRATEGY_CLASSPATH= "com.lvmama.comm.pet.vo.favor.strategy.ProductFavorStrategyFor";
	
	/**
	 * 将页面传递过来的markCoupon转换成存入数据库的markCoupon
	 * @param markCoupon 页面传递过来的markCoupon
	 * @return 存入数据库的markCoupon
	 */
	public static MarkCoupon markCouponToDB(final MarkCoupon markCoupon) {
		if (null == markCoupon) {
			return null;
		}
		/** 满X元后，一次性优惠Y元**/
		if (Constant.FAVOR_TYPE.AMOUNT_AMOUNT_WHOLE.name().equals(markCoupon.getFavorType())) {
			markCoupon.setArgumentX(PriceUtil.convertToFen(markCoupon.getArgumentX()));
			markCoupon.setArgumentY(PriceUtil.convertToFen(markCoupon.getArgumentY()));
			return markCoupon;
		}
		/** 满X元后，每满Y元，优惠Z元**/
		if (Constant.FAVOR_TYPE.AMOUNT_AMOUNT_INTERVAL.name().equals(markCoupon.getFavorType())) {
			markCoupon.setArgumentX(PriceUtil.convertToFen(markCoupon.getArgumentX()));
			markCoupon.setArgumentY(PriceUtil.convertToFen(markCoupon.getArgumentY()));
			markCoupon.setArgumentZ(PriceUtil.convertToFen(markCoupon.getArgumentZ()));
			return markCoupon;
		}
		/** 满X份后，一次性优惠Y元**/
		if (Constant.FAVOR_TYPE.AMOUNT_QUANTITY_WHOLE.name().equals(markCoupon.getFavorType())) {
			markCoupon.setArgumentY(PriceUtil.convertToFen(markCoupon.getArgumentY()));
			return markCoupon;
		}
		/** 满X份后，每满Y份，优惠Z元**/
		if (Constant.FAVOR_TYPE.AMOUNT_QUANTITY_INTERVAL.name().equals(markCoupon.getFavorType())) {
			markCoupon.setArgumentZ(PriceUtil.convertToFen(markCoupon.getArgumentZ()));
			return markCoupon;
		}
		/** 满X份后，每份优惠Y元**/
		if (Constant.FAVOR_TYPE.AMOUNT_QUANTITY_PRE.name().equals(markCoupon.getFavorType())) {
			markCoupon.setArgumentY(PriceUtil.convertToFen(markCoupon.getArgumentY()));
			return markCoupon;
		}
		/** 满X元后，一次性享受Y折扣**/
		if (Constant.FAVOR_TYPE.DISCOUNT_AMOUNT_WHOLE.name().equals(markCoupon.getFavorType())) {
			markCoupon.setArgumentX(PriceUtil.convertToFen(markCoupon.getArgumentX()));
			return markCoupon;
		}
		/** 满X份，一次性享受Y折扣**/
		if (Constant.FAVOR_TYPE.DISCOUNT_QUANTITY_WHOLE.name().equals(markCoupon.getFavorType())) {
			return markCoupon;
		}
		/** 满X份，每满Y份，超出部分销售价享受Z折扣 **/
		if (Constant.FAVOR_TYPE.AMOUNT_AMOUNT_INTERVAL.name().equals(markCoupon.getFavorType())) {
			return markCoupon;
		}
		throw new java.lang.UnsupportedOperationException("Cann't find mapping strategy. MarkCoupon's favorType is " + markCoupon.getFavorType());
	}
	
	/**
	 * 将页面传递过来的markCoupon转换成存入数据库的markCoupon
	 * @param markCoupon 页面传递过来的markCoupon
	 * @return 存入数据库的markCoupon
	 */
	public static MarkCouponProduct markCouponProductToDB(final MarkCoupon markCoupon, final MarkCouponProduct markCouponProduct) {
		if (null == markCoupon || null == markCouponProduct) {
			return null;
		}
		/** 满X元后，一次性优惠Y元**/
		if (Constant.FAVOR_TYPE.AMOUNT_AMOUNT_WHOLE.name().equals(markCoupon.getFavorType())) {
			markCouponProduct.setAmount(PriceUtil.convertToFen(markCouponProduct.getAmount()));
			return markCouponProduct;
		}
		/** 满X元后，每满Y元，优惠Z元**/
		if (Constant.FAVOR_TYPE.AMOUNT_AMOUNT_INTERVAL.name().equals(markCoupon.getFavorType())) {
			markCouponProduct.setAmount(PriceUtil.convertToFen(markCouponProduct.getAmount()));
			return markCouponProduct;
		}
		/** 满X份后，一次性优惠Y元**/
		if (Constant.FAVOR_TYPE.AMOUNT_QUANTITY_WHOLE.name().equals(markCoupon.getFavorType())) {
			markCouponProduct.setAmount(PriceUtil.convertToFen(markCouponProduct.getAmount()));
			return markCouponProduct;
		}
		/** 满X份后，每满Y份，优惠Z元**/
		if (Constant.FAVOR_TYPE.AMOUNT_QUANTITY_INTERVAL.name().equals(markCoupon.getFavorType())) {
			markCouponProduct.setAmount(PriceUtil.convertToFen(markCouponProduct.getAmount()));
			return markCouponProduct;
		}
		/** 满X份后，每份优惠Y元**/
		if (Constant.FAVOR_TYPE.AMOUNT_QUANTITY_PRE.name().equals(markCoupon.getFavorType())) {
			markCouponProduct.setAmount(PriceUtil.convertToFen(markCouponProduct.getAmount()));
			return markCouponProduct;
		}
		/** 满X元后，一次性享受Y折扣**/
		if (Constant.FAVOR_TYPE.DISCOUNT_AMOUNT_WHOLE.name().equals(markCoupon.getFavorType())) {
			return markCouponProduct;
		}
		/** 满X份，一次性享受Y折扣**/
		if (Constant.FAVOR_TYPE.DISCOUNT_QUANTITY_WHOLE.name().equals(markCoupon.getFavorType())) {
			return markCouponProduct;
		}
		/** 满X份，每满Y份，超出部分销售价享受Z折扣 **/
		if (Constant.FAVOR_TYPE.AMOUNT_AMOUNT_INTERVAL.name().equals(markCoupon.getFavorType())) {
			return markCouponProduct;
		}
		throw new java.lang.UnsupportedOperationException("Cann't find mapping strategy. MarkCoupon's favorType is " + markCoupon.getFavorType());
	}
	
	/**
	 * 将数据库的markCoupon转换成页面显示的markCoupon
	 * @param markCoupon 数据库的markCoupon
	 * @return 页面显示的markCoupon
	 */
	public static MarkCoupon DBToMarkCoupon(final MarkCoupon markCoupon) {
		if (null == markCoupon) {
			return null;
		}

		throw new java.lang.UnsupportedOperationException("Cann't find mapping strategy. MarkCoupon's favorType is " + markCoupon.getFavorType());
	}	
	
	/**
	 * 获取相应的优惠策略
	 * @param markCoupon 优惠券批次
	 * @param markCouponCode 优惠券号码
	 * @return 优惠策略
	 * 根据优惠券批次的<code>favorType</code>来获取对于的优惠策略，并记录对应的markCouponCode值
	 */
	public static FavorStrategy getStrategy(final MarkCoupon markCoupon, final MarkCouponCode markCouponCode) {
		FavorStrategy strategy = null;
		String strategyName = getStrategyName(markCoupon.getFavorType());
		if (null == strategyName) {
			debug("Cann't find the strategy's name!");
			return null;
		}
		try {
			Class<?> clazz = Class.forName(ORDER_STRATEGY_CLASSPATH + strategyName);
			Constructor<?> constructor= clazz.getConstructor(MarkCoupon.class, MarkCouponCode.class);
			strategy = (FavorStrategy) constructor.newInstance(markCoupon, markCouponCode);
		} catch (NoSuchMethodException nsme) {
			LOG.error(nsme.getMessage());
		} catch (ClassNotFoundException cnfe) {
			debug("Cann't find the class STRATEGY_CLASSPATH" + strategyName);
			LOG.error(cnfe.getMessage());
		} catch (InstantiationException ie) {
			LOG.error(ie.getMessage());
		} catch (IllegalAccessException iae) {
			LOG.error(iae.getMessage());
		} catch (IllegalArgumentException iae) {
			LOG.error(iae.getMessage());
		} catch (InvocationTargetException ite) {
			LOG.error(ite.getMessage());
		}
		return strategy;
	}
	
	
	public static FavorStrategy getStrategy(final BusinessCoupon businessCoupon) {
		FavorStrategy strategy = null;
		String strategyName = getStrategyName(businessCoupon.getFavorType());
		if (null == strategyName) {
			debug("Cann't find the strategy's name!");
			return null;
		}
		try {
			Class<?> clazz = Class.forName(PRODUCT_STRATEGY_CLASSPATH + strategyName);
			Constructor<?> constructor= clazz.getConstructor(BusinessCoupon.class);
			strategy = (FavorStrategy) constructor.newInstance(businessCoupon);
		} catch (NoSuchMethodException nsme) {
			LOG.error(nsme.getMessage());
		} catch (ClassNotFoundException cnfe) {
			debug("Cann't find the class STRATEGY_CLASSPATH" + strategyName);
			LOG.error(cnfe.getMessage());
		} catch (InstantiationException ie) {
			LOG.error(ie.getMessage());
		} catch (IllegalAccessException iae) {
			LOG.error(iae.getMessage());
		} catch (IllegalArgumentException iae) {
			LOG.error(iae.getMessage());
		} catch (InvocationTargetException ite) {
			LOG.error(ite.getMessage());
		}
		return strategy;
	}
	
	/**
	 * 返回优惠策略的类名
	 * @param favorType 优惠策略 
	 * @return 优惠策略的类名
	 * <p>优惠策略的类名和在MarkCoupon记录中的FavorType字段是Restful风格的，将FavorType字段值去掉下划线，并将各单词第一个字母大写，其他字符小写来命名策略名字</p>
	 */
	private static String getStrategyName(final String favorType) {
		if (StringUtils.isBlank(favorType)) {
			debug("FavorType is null, cann't get strategy's name.");
			return null;
		}
		String[] names = favorType.split("_");
		StringBuilder strategyName = new StringBuilder();
		for (String name : names) {
			strategyName.append(name.substring(0, 1).toUpperCase()).append(name.toLowerCase().substring(1));
		}
		return strategyName.toString();
	}
	
	/**
	 * 打印调试信息
	 * @param message 调试信息
	 */
	private static void debug(final String message) {
		if (LOG.isDebugEnabled() && StringUtils.isNotBlank(message)) {
			LOG.debug(message);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(FavorStrategyMarkCouponAdapter.getStrategyName("AMOUNT_AMOUNT_WHOLE"));
		System.out.println(FavorStrategyMarkCouponAdapter.getStrategyName("AMOUNT_AMOUNT_INTERVAL"));
		System.out.println(FavorStrategyMarkCouponAdapter.getStrategyName("AMOUNT_QUANTITY_WHOLE"));
		System.out.println(FavorStrategyMarkCouponAdapter.getStrategyName("AMOUNT_QUANTITY_INTERVAL"));
	}
}
