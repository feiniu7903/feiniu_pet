/**
 * 
 */
package com.lvmama.shholiday;

import java.util.Map;

/**
 * @author yangbin
 *
 */
public interface Handle {

	/**
	 * 创建请求参数列表
	 * @return
	 */
	public Map<String,Object> createBody();
}
