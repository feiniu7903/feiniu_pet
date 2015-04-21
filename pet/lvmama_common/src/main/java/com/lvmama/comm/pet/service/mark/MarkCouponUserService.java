/**
 * 
 */
package com.lvmama.comm.pet.service.mark;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.mark.MarkCouponUsage;
import com.lvmama.comm.vo.UserCouponDTO;

/**
 * 用户优惠券关联服务
 * @author liuyi
 *
 */
public interface MarkCouponUserService {
	
	/**
	 * 获取用户相关优惠券数
	 * @param param
	 * @return
	 */
	Long selectCountByRelateUser(Map<String, Object> param);
	
	/**
	 * 获取我的驴妈妈用户相关优惠券
	 * @param params
	 * @return
	 */
	List<UserCouponDTO> getMySpaceUserCouponData(Map<String, Object> params);
	
	List<UserCouponDTO> getMySpaceUserCouponData(List<MarkCouponUsage> markCouponUsageList);

}
