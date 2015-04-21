package com.lvmama.tnt.partner.hotel.service;

import com.lvmama.tnt.partner.comm.ResponseVO;
import com.lvmama.vst.api.hotel.push.vo.OrderPushVo;

/**
 * 订单推送接口
 * @author gaoyafeng
 *
 */
public interface OrderPushService {
	
	   /**
	    * 推送产品状态
	    * @param orderId 订单ID
	    * @param orderPushVo 订单推送对象
	    * @return 0：推送成功 1：推送失败
	    */
	   public ResponseVO<Integer> pushOrderStatus(Long orderId,OrderPushVo orderPushVo);

}
