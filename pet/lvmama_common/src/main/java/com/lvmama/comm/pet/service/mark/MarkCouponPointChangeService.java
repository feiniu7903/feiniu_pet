/**
 * 
 */
package com.lvmama.comm.pet.service.mark;

import java.util.List;
import java.util.Map;
import com.lvmama.comm.pet.po.mark.MarkCouponPointChange;

/**
 * 积分兑换优惠券服务
 * @author liuyi
 *
 */
public interface MarkCouponPointChangeService {
	
	/**
	 * 根据产品找到对应的积分兑换优惠规则（其实是根据产品子类型）
	 * @param prodProduct
	 * @return
	 */
	MarkCouponPointChange selectBySubProductType(String subProductType);
	
	List<MarkCouponPointChange> selectByParam(Map<String,Object> param);
	
	Long countByParam(Map<String,Object> param);
	
	MarkCouponPointChange insert(MarkCouponPointChange markCouponPointChange);
	
	int updateByPrimaryKey(MarkCouponPointChange markCouponPointChange);

	int deleteByPrimaryKey(Long markCouponPointChangeId);
}
