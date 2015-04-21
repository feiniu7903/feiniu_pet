/**
 * 
 */
package com.lvmama.comm.bee.service.favor;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.mark.MarkCouponUsage;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.vo.favor.FavorProductResult;

/**
 * 优惠系统服务类
 * @author liuyi
 *
 */
public interface FavorOrderService {
	
	Long selectCountByParam(Map<String, Object> param);
	
	List<MarkCouponUsage> selectByParam(Map<String, Object> param);
	
	List<MarkCouponUsage> getMarkCouponUsageByObjectIdAndType(Long objectId, String type);
	
	
	/**
	 * 记录优惠日志
	 * @param markCouponUsage
	 */
	void saveCouponUsage(MarkCouponUsage markCouponUsage);
	
	/**
	 * 获取用户已使用总优惠金额
	 * @param user
	 * @return
	 */
	Long getSumUsageAmountByUser(UserUser user);
	
	/**
	 * 获取单个优惠券已使用优惠金额
	 * @param markCouponCode
	 * @return
	 */
	Long getSumUsageAmountByCouponCode(MarkCouponCode markCouponCode);
	
	Long getSumUsageAmount(Map<String,Object> param);
	
	/**
	 * 更新优惠后的订单销售产品子项价格
	 * @param orderId
	 * @param favorProductResultList
	 */
	void updateOrderItemMetaPriceByCoupon(Long orderId, List<FavorProductResult> favorProductResultList);
}
