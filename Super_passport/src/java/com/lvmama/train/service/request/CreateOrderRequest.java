/**
 * 
 */
package com.lvmama.train.service.request;

import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.comm.vo.train.order.OrderCreateReqVo;
import com.lvmama.train.service.TrainResponse;
import com.lvmama.train.service.response.CreateOrderResponse;

/**
 * @author yangbin
 *
 */
public class CreateOrderRequest extends AbstractTrainRequest{

	/**
	 * 
	 * @param traffic  
	 * @param itemMeta 需要生成订单的子子项
	 */
	public CreateOrderRequest(ReqVo vo) {
		super(vo);
		if(!(vo instanceof OrderCreateReqVo))
			throw new RuntimeException("未接受到正确的处理数据!");
		OrderCreateReqVo ocvo = (OrderCreateReqVo)vo;
		put("user_ip", ocvo.getUserIp());
		put("depart_station", ocvo.getDepartStation());
		put("arrive_station", ocvo.getArriveStation());
		put("depart_date", ocvo.getDepartDate());
		put("train_id", ocvo.getTrainId());
		if(ocvo.getOrderItemMetaId() != null) {
			put("merchant_order", ocvo.getOrderItemMetaId());
		}
		put("json_param", JsonUtil.getJsonString4List(ocvo.getPassengers()));
		setRequestType(ocvo.getRequestType());
		setObjectId(ocvo.getObjectId());
	}

	@Override
	public Class<? extends TrainResponse> getClazz() {
		return CreateOrderResponse.class;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "http://" +hostaddress+ Constant.TRAIN_INTERFACE.TICKET_ORDER_CREATE.getUrl();
	}

	@Override
	public String getBaseUrl() {
		// TODO Auto-generated method stub
		return Constant.TRAIN_INTERFACE.TICKET_ORDER_CREATE.getUrl();
	}

	
}
