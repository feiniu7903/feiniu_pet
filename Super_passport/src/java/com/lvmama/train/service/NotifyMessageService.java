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
public interface NotifyMessageService {

	/**
	 * 消息通知接口，其中包括出票结果通知、退票结果通知、退款通知、产品数据更新通知
	 * @param requestXml
	 * @return
	 */
	Rsp request(ReqVo vo, int iCode);
}
