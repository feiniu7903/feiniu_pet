/**
 * 
 */
package com.lvmama.train.service;

import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.comm.vo.train.Rsp;

/**
 * @author yangbin
 *
 */
public interface NotifyRequest {
	/**
	 * 处理接收到的VO，返回结果
	 * @param json
	 * @return
	 */
	Rsp handle(ReqVo vo) throws RuntimeException;
}
