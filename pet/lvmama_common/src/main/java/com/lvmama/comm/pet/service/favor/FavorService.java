/**
 * 
 */
package com.lvmama.comm.pet.service.favor;

import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Coupon;
import com.lvmama.comm.pet.vo.favor.FavorProductResult;
import com.lvmama.comm.pet.vo.favor.FavorResult;
import com.lvmama.comm.pet.vo.favor.OrderFavorStrategy;
import com.lvmama.comm.pet.vo.mark.ValidateCodeInfo;

/**
 * 优惠/优惠券系统接口
 * @author liuyi
 *
 */
public interface FavorService {
	
	/**
	 * 计算优惠系统优惠结果
	 * @param buyInfo
	 * @return
	 */
	FavorResult calculateFavorResultByBuyInfo(BuyInfo buyInfo);
	
	OrderFavorStrategy validateCoupon(Long couponId, String couponCode, Long mainProductId, String subProductType, ValidateCodeInfo info);
	
	/**
	 * 计算采购产品优惠系统优惠结果
	 * @param buyInfo
	 * @return
	 */
	List<FavorProductResult> getFavorMetaProductResultByOrderInfo(OrdOrder order);
	
	/**
	 * 为时间价格表填充优惠提示参数信息
	 * @param productId
	 * @param calendarModelList
	 * @return
	 */
	List<CalendarModel> fillFavorParamsInfoForCalendar(Long productId,Long branchId, List<CalendarModel> calendarModelList);
	/**
	 * 根据时间价格表取出早订早慧最优价格
	 * @param calendarModelList
	 * @param product
	 * @param branchId
	 * @return
	 */
	Float getEarlyCouponPrice(List<CalendarModel> calendarModelList,Long branchId);
	
	/**
	 * 根据产品上设置的优惠券/活动开关过滤优惠券/活动
	 * @param couponList
	 * @param prodProduct
	 * @return
	 */
	
	List<Coupon> filterCouponListByProductCouponUseFlag(List<Coupon> couponList, ProdProduct prodProduct);
	/**
	 * 验证产品上设置的优惠券/活动开关过滤优惠券/活动
	 * @param couponList
	 * @param prodProduct
	 * @return
	 */
	List<Coupon> validateCouponListByProductCouponUseFlag(List<Coupon> couponList, ProdProduct prodProduct);
}
