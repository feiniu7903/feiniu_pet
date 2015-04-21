/**
 * 
 */
package com.lvmama.comm.pet.vo.favor;

import java.io.Serializable;

/**
 * 优惠策略对象通用接口
 * @author liuyi
 */
public interface FavorStrategy extends Serializable {

	/**
	 * 获取当前优惠策略类型
	 * @return
	 */
	String getFavorType();

}
