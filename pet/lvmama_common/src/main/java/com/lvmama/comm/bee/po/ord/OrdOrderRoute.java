package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;

/**
 * @author liwenzhan
 * @version  1.0  2011-9-20
 *  
 */
public class OrdOrderRoute  implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 4312939125993174330L;
	
	/**
	 * 
	 */
	private Long orderRouteId;
	/**
	 * 订单ID
	 */
	private Long orderId;
	/**
	 * 开票状态.
	 */
	private String trafficTicketStatus;
	/**
	 * 出团通知书的状态.
	 */
	private String groupWordStatus;
	/**
	 * 签证状态
	 */
	private String visaStatus;
	
	
	
	
	/**
	 *  获取ID.
	 * @return
	 */
	public Long getOrderRouteId() {
		return orderRouteId;
	}

	/**
	 * 设置ID.
	 * @param orderRouteId
	 */
	public void setOrderRouteId(Long orderRouteId) {
		this.orderRouteId = orderRouteId;
	}

	/**
	 * 获取订单ID.
	 * @return
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * 设置订单ID.
	 * @param OrderId
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * 获取开票状态.
	 * 
	 * @return
	 */
	public String getTrafficTicketStatus() {
		return trafficTicketStatus;
	}

	/**
	 * 设置开票状态.
	 * 
	 * @param trafficTicketStatus
	 */
	public void setTrafficTicketStatus(String trafficTicketStatus) {
		this.trafficTicketStatus = trafficTicketStatus;
	}

	/**
	 * 获取出团通知书的状态.
	 * 
	 * @return
	 */
	public String getGroupWordStatus() {
		return groupWordStatus;
	}

	/**
	 * 设置出团通知书的状态.
	 * 
	 * @param isPartPay
	 */
	public void setGroupWordStatus(String groupWordStatus) {
		this.groupWordStatus = groupWordStatus;
	}

	/**
	 * 设置签证状态.
	 * 
	 * @param visaStatus
	 */
	public String getVisaStatus() {
		return visaStatus;
	}

	/**
	 * 设置签证状态.
	 * 
	 * @param visaStatus
	 */
	public void setVisaStatus(String visaStatus) {
		this.visaStatus = visaStatus;
	}
	
}
