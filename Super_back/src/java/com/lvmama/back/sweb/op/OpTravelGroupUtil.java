/**
 * 
 */
package com.lvmama.back.sweb.op;

import com.lvmama.comm.bee.po.op.OpTravelGroup;

/**
 * 团工具类
 * @author yangbin
 *
 */
@Deprecated
public abstract class OpTravelGroupUtil {

	/**
	 * 计算group的还剩人数
	 * @param group
	 * @return
	 */
	public static long remain(OpTravelGroup group){
		if(group.getInitialGroupNum()<0)
		{
			return -1L;
		}else
		{
			return group.getInitialGroupNum()-group.getPaySuccessNum()-group.getPayPartNum()-group.getPayNotNum();
		}		
	}
}
