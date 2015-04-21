package com.lvmama.tnt.partner.hotel.service;

import com.lvmama.tnt.partner.comm.ResponseVO;
import com.lvmama.vst.api.hotel.push.vo.ProductPushVo;

/**
 * 产品推送接口
 * @author gaoyafeng
 *
 */
public interface ProductPushService {
	
	   /**
	    * 推送产品状态
	    * @param prodId 产品ID
	    * @param productPushVo 产品推送对象
	    * @return 0：推送成功 1：推送失败
	    */
	   public ResponseVO<Integer> pushProductStatus(Long prodId,ProductPushVo productPushVo);

}
