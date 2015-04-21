package com.lvmama.passport.processor.impl.client.zhiyoubao.model;
/**
 * 订单请求
 * @author gaoxin
 */
public class OrderRequest {
	private Order order;
	private ReturnTicket returnTicket;
	
	
	public OrderRequest() {}
	
	public OrderRequest(Order order,ReturnTicket returnTicket) {
		this.order = order;
		this.returnTicket = returnTicket;
	}
	
	
	public OrderRequest(Order order) {
		this.order = order;
	}

	/**
	 * 下订单
	 * @return
	 */
	public String toorderRequestXml(){
		StringBuilder sbi=new StringBuilder();
		sbi.append("<orderRequest>");
		sbi.append(this.order.toCreateOrderXml());
		sbi.append("</orderRequest>");
		return sbi.toString();
	}
	/**
	 *操作订单
	 * @return
	 */
	public String toOrderRequestUpdateXml(){
		StringBuilder sbi=new StringBuilder();
		sbi.append("<orderRequest>");
		sbi.append(this.order.toHandleOrderXml());
		sbi.append("</orderRequest>");
		return sbi.toString();
	}
	/**
	 * 退票
	 * 
	 * @return
	 */
	public String toReturnTicketXml(){
		StringBuilder sbi=new StringBuilder();
		sbi.append("<orderRequest>");
		if(this.returnTicket!=null){
		sbi.append(this.returnTicket.toReturnTicketReq());
		}
		sbi.append("</orderRequest>");
		return sbi.toString();
	}
	
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	public ReturnTicket getReturnTicket() {
		return returnTicket;
	}
	public void setReturnTicket(ReturnTicket returnTicket) {
		this.returnTicket = returnTicket;
	}
	
}
