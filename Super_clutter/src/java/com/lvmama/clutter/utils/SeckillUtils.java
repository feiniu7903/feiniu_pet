package com.lvmama.clutter.utils;

import java.util.Calendar;
import java.util.Date;

import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;

/**    
 * @Title: SeckillUtils.java
 * @Package com.lvmama.clutter.utils
 * @Description: TODO
 * @author jiangzhihu
 * @date 2014-4-28 下午4:28:46
 * @version V1.0.0
 */
public class SeckillUtils {

	/**
	 * 获取秒杀产品的状态
	 * 
	 * @param prodSeckillRule
	 * @return
	 */
	public static String getSeckillStatus(ProdSeckillRule prodSeckillRule) {
		Date startTime = prodSeckillRule.getStartTime();// 秒杀开始时间
		Date endTime = prodSeckillRule.getEndTime();// 秒杀开始时间
		Date currentTime = Calendar.getInstance().getTime();
		if (currentTime.before(startTime)) {
			return EnumSeckillStatus.SECKILL_BEFORE.name();
		}
		if (currentTime.after(startTime) && currentTime.before(endTime)) {
			if (prodSeckillRule.getAmount() == 0) {// 缓存中库存为0表示已经售罄
				return EnumSeckillStatus.SECKILL_FINISHED.name();
			}
			return EnumSeckillStatus.SECKILL_BEING.name();
		}
		if (currentTime.after(endTime)) {
			return EnumSeckillStatus.SECKILL_AFTER.name();
		}
		return EnumSeckillStatus.SECKILL_AFTER.name();
	}
	
}
