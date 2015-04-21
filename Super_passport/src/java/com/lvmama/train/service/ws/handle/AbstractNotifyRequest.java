/**
 * 
 */
package com.lvmama.train.service.ws.handle;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.BaseVo;
import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.comm.vo.train.Rsp;
import com.lvmama.train.service.NotifyRequest;

/**
 * @author yangbin
 *
 */
public abstract class AbstractNotifyRequest implements NotifyRequest{
	protected Rsp check(ReqVo vo){
		if(StringUtils.isEmpty(vo.getMerchantId()))
			return new Rsp(Constant.HTTP_SERVER_ERROR, 
						Constant.HTTP_SERVER_ERROR_MSG, 
						new BaseVo(Constant.REPLY_CODE.MISS_MERCHANT_ID.getRetCode(), 
								Constant.REPLY_CODE.MISS_MERCHANT_ID.getRetMsg()));
		else if(StringUtils.isEmpty(vo.getSign()))
			return new Rsp(Constant.HTTP_SERVER_ERROR, 
					Constant.HTTP_SERVER_ERROR_MSG, 
					new BaseVo(Constant.REPLY_CODE.MISS_SIGN.getRetCode(), 
							Constant.REPLY_CODE.MISS_SIGN.getRetMsg()));
		else return null;
	}
}
