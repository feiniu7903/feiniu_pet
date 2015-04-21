package com.lvmama.back.web.abroadhotel.refundMent;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.abroad.service.AbroadhotelOrderService;
import com.lvmama.comm.abroad.vo.response.ReservationsOrder;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.spring.SpringBeanProxy;

/**
 * 订单退款增加对像类.
 */
public class AhotelOrdRefundSaleAddAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 订单子项编号.
	 */
	private String orderId;
	/**
	 * 我的历史订单详细信息
	 */
	private ReservationsOrder historyOrderDetail;
	/**
	 * 售后服务编号.
	 */
	private String saleServiceId;
	/**
	 * 订单服务类型.
	 */
	private String serviceType;
	/**
	 * 只读类型
	 */
	private String editabled = "true";
	/**
	 * 海外酒店订单的service.
	 */
	private AbroadhotelOrderService abroadhotelOrderService;

	/**
	 * 查询页面基本显示信息.
	 * 
	 * @return
	 */
	public void doBefore() {
		if (orderId != null) {
			this.historyOrderDetail =abroadhotelOrderService.queryAbroadHotelOrderByOrderId(orderId);
		}
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public ReservationsOrder getHistoryOrderDetail() {
		return historyOrderDetail;
	}

	public void setHistoryOrderDetail(ReservationsOrder historyOrderDetail) {
		this.historyOrderDetail = historyOrderDetail;
	}

	public OrdRefundMentService getOrdRefundMentService() {
		return (OrdRefundMentService) SpringBeanProxy
				.getBean("ordRefundMentService");
	}

	public String getSaleServiceId() {
		return saleServiceId;
	}

	public void setSaleServiceId(String saleServiceId) {
		this.saleServiceId = saleServiceId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getEditabled() {
		return editabled;
	}

	public void setEditabled(String editabled) {
		this.editabled = editabled;
	}

	public void setAbroadhotelOrderService(
			AbroadhotelOrderService abroadhotelOrderService) {
		this.abroadhotelOrderService = abroadhotelOrderService;
	}


}
