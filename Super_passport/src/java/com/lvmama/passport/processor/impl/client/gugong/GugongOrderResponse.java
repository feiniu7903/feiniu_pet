package com.lvmama.passport.processor.impl.client.gugong;

public class GugongOrderResponse {
	private String orderid;//	String	联盟方订单流水ID	联盟方要保证唯一性	是
	private String ylorderid;//	String	永乐/平台该订单唯一ID	永乐方要保证唯一性	否
	private int resultcode;//	Int	返回码	详见（5.1响应结果码定义）	是
	private int status;	//订单状态 0:未支付 1:未出票 2:已出票 3:无效订单
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getYlorderid() {
		return ylorderid;
	}
	public void setYlorderid(String ylorderid) {
		this.ylorderid = ylorderid;
	}
	public int getResultcode() {
		return resultcode;
	}
	public void setResultcode(int resultcode) {
		this.resultcode = resultcode;
	}
	public boolean isSuccess(){
		return this.resultcode == GugongConstant.successful;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
