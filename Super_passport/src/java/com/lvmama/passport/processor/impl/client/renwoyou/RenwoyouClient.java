package com.lvmama.passport.processor.impl.client.renwoyou;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import com.lvmama.passport.processor.impl.client.renwoyou.model.OrderItem;
import com.lvmama.passport.processor.impl.client.renwoyou.model.OrderRequest;
import com.lvmama.passport.processor.impl.client.renwoyou.model.OrderResponse;
public class RenwoyouClient {
	private static final Log log = LogFactory.getLog(RenwoyouClient.class);
	/**
	 * 组装JSON数据
	 * @param order
	 * @throws Exception
	 */
	public static String toJSONOrderInfo(OrderRequest order) throws Exception{
		JSONObject info = new JSONObject();
		JSONArray array = new JSONArray();
		info.put("action",order.getAction());
		info.put("outOrderNo",order.getOutOrderNoString());
		info.put("buyer",order.getBuyer());
		info.put("mobile",order.getMobile());
		info.put("idCardNo",order.getIdCardNo());
		info.put("salePrice",order.getSalePrice());
		for (OrderItem item :order.getOrderItemList()) {
			JSONObject obj = new JSONObject();
			obj.put("id",item.getId());
			obj.put("qty",item.getQty());
			obj.put("ticketType",item.getTicketType());
			obj.put("startDate",item.getStartDate());
			array.put(obj);
		}
		info.put("orderItemList",array);
		String body=info.toString();
		log.info("body:"+body);
		return body;
	}
	
	//解析下单和查询订单的返回信息
	public static  OrderResponse parseOrderResponse(String response) throws Exception {
		JSONObject orderResponse = new JSONObject(response);
		if (orderResponse.has("status")) {
			OrderResponse res=new OrderResponse();
			String status=orderResponse.getString("status");
			String orderInfo =orderResponse.getString("body");
			res.setStatus(status);
			if(status.equalsIgnoreCase("ok")){
				JSONObject info = new JSONObject(orderInfo);
				String no=info.getString("no");
				String state=info.getString("state");
				res.setNo(no);
				res.setState(state);
			}else{
				String errorMsg=orderResponse.getString("body");
				res.setErrorMsg(errorMsg);
			}
			return res;
		}
		return null;
	}
	

	
	//解析调用库存校验接口返回信息
	public static OrderResponse parseTicketInvtrResponse(String response) throws Exception {
		JSONObject result = new JSONObject(response);
		String status=result.getString("status");
		OrderResponse orderRes=null;
		if(StringUtils.equals(status, "ok")){
				if (result.has("body")) {
					JSONArray jsonArray = new JSONArray(result.getString("body"));
					for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject order =jsonArray.getJSONObject(i);
					orderRes=new OrderResponse();
					orderRes.setStatus(status);
					if (order.has("qty")) {
						orderRes.setQty(order.getInt("qty"));
					}
				}
			}
		}
		return orderRes;
	}
	

	//解析调用重发短信接口的返回信息
	public static OrderResponse parseSendSmsResponse(String response) throws Exception {
		JSONObject orderResponse = new JSONObject(response);
		OrderResponse res = new OrderResponse();
		if (orderResponse.has("status")) {
			String status = orderResponse.getString("status");
			res.setStatus(status);
			if (!StringUtils.equalsIgnoreCase(status, "ok")) {
				String errorMsg = orderResponse.getString("body");
				res.setErrorMsg(errorMsg);
			}
			return res;
		}
		return null;
	}
	
	//解析退款接口返回信息
	public static OrderResponse parseRefundOrderResponse(String response) throws Exception{
		JSONObject orderResponse = new JSONObject(response);
		OrderResponse res=new OrderResponse();
		if (orderResponse.has("status")) {
			String status=orderResponse.getString("status");
			String body=orderResponse.getString("body");
			res.setStatus(status);
			res.setErrorMsg(body);
			return res;
		}
		return null;
	}
}
