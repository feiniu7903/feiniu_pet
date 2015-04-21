/**
 * 
 */
package com.lvmama.train.service.ws.handle;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.dom4j.Element;

import com.lvmama.comm.bee.service.ord.OrderTrafficService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.XmlUtils;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.comm.vo.train.Rsp;

/**
 * @author yangbin
 *
 */
public class LockOrderNotifyRequest extends AbstractNotifyRequest {

	@Override
	public Rsp handle(ReqVo vo) throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

//	private OrderTrafficService orderTrafficService;
//	
//	public LockOrderNotifyRequest() {
//		super("LockOrderRequest", "LockOrderResponse");
//		orderTrafficService = SpringBeanProxy.getBean(OrderTrafficService.class, "orderTrafficService");
//	}
//
//	@Override
//	protected void parseBody(Element body) {
//		String txt = XmlUtils.getChildElementContent(body, "OrderID");
//		//long orderItemMetaId = NumberUtils.toLong(txt);
//		if(StringUtils.isEmpty(txt)){
//			setFail("订单号不存在");
//		}else{
//			ResultHandleT<Boolean> result = orderTrafficService.lockOrder(txt);
//			if(result.isFail()){
//				setFail(result.getMsg());
//			}else if(result.isSuccess()){
//				setSuccess(result.getReturnContent());
//			}
//		}
//	}
}
