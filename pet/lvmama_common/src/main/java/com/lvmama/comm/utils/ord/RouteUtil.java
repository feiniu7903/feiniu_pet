package com.lvmama.comm.utils.ord;

import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * @author yangbin
 * 
 */
public abstract class RouteUtil {
	
	

	/**
	 * 判断一个对象是否是需要创建团的线路产品,
	 * 并且判断该产品是否是主产品.
	 * @param product
	 * @return
	 */
	public static boolean hasTravelGroupProduct(ProdProduct product) {
		boolean flag = false;
		//是否是线路产品并且是主产品
		if (product instanceof ProdRoute && hasTravelGroupProduct(product.getSubProductType())) {
			ProdRoute route=ProdRoute.class.cast(product);
			flag = StringUtils.isNotEmpty(route.getTravelGroupCode());
		}
		return flag;
	}

	/**
	 * 判断是否生成团的信息(长途自由行,长途跟团游,出境自由行,出境跟团游,自助巴士班).
	 * 
	 * @param subProductType
	 * @return
	 */
	public static boolean hasTravelGroupProduct(String subProductType) {
		if(StringUtils.isEmpty(subProductType))
			return false;
		return ArrayUtils.contains(SUB_PRODUCT_TYPE_TRAVEL_GROUP_CODE,
				subProductType);
	}

	/**
	 * 判断是否需要生成定金(长途自由行,长途跟团游,出境自由行,出境跟团游).
	 * payDeposit
	 * @param subProductType
	 * @return
	 */
	public static boolean hasMakePayDeposit(String subProductType) {
		if(StringUtils.isEmpty(subProductType))
			return false;
		return ArrayUtils.contains(SUB_PRODUCT_TYPE_TRAVEL_GROUP,
				subProductType);
	}
	/**
	 * 是否需要签证的子类型
	 * @param subProductType
	 * @return
	 */
	public static boolean hasVisa(String subProductType){
		if(StringUtils.isEmpty(subProductType))
			return false;
		
		return Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equalsIgnoreCase(subProductType)
				||Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equalsIgnoreCase(subProductType);
	}

	/**
	 * 生成团号信息
	 * 
	 * @param prodProduct
	 * @param date
	 * @return
	 */
	public static String makeTravelGroupCode(ProdProduct prodProduct, Date date) {
		StringBuffer sb = new StringBuffer();
		if(StringUtils.isNotEmpty(prodProduct.getTravelGroupCode())){
			sb.append(prodProduct.getTravelGroupCode());
			sb.append("-");
		}
		sb.append(DateUtil.getFormatDate(date, "yyyyMMdd"));
		sb.append("-");
		sb.append(prodProduct.getProductId());
		return sb.toString();
	}

	/**
	 * 需要添加团号的相关线路子类型(长途自由行,长途跟团游,出境自由行,出境跟团游)
	 */
	public final static String SUB_PRODUCT_TYPE_TRAVEL_GROUP[] = {
//			"GROUP_LONG", "GROUP_FOREIGN", "FREENESS_LONG", "FREENESS_FOREIGN"
		Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name(),
		Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name(),
		Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name(),
		Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name(),
		};
	/**
	 * 需要添加团号的相关线路子类型(长途自由行,长途跟团游,出境自由行,出境跟团游,自助巴士班)
	 */
	public final static String SUB_PRODUCT_TYPE_TRAVEL_GROUP_CODE[] = {
			Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name(),
			Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name(),
			Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name(),
			Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name(),
			Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.name(),
			Constant.SUB_PRODUCT_TYPE.GROUP.name()
//			"GROUP_LONG", "GROUP_FOREIGN", "FREENESS_LONG", "FREENESS_FOREIGN",
//			"SELFHELP_BUS" 
			};
}
