/**
 * 
 */
package com.lvmama.comm.pet.service.pub;

import com.lvmama.comm.pet.po.pub.ComMobileArea;

/**
 * 手机归属接口
 * @author yangbin
 *
 */
public interface ComMobileAreaService {

	/**
	 * 查找手机归属地
	 * 
	 * @param mobileAreaQuery
	 * @return
	 */
	public ComMobileArea findMobileArea(ComMobileArea comMobileArea);
}
