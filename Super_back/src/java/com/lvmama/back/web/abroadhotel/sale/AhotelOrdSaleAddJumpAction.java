package com.lvmama.back.web.abroadhotel.sale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.abroad.po.AhotelOrdRefundment;
import com.lvmama.comm.abroad.po.AhotelOrdSaleService;
import com.lvmama.comm.abroad.service.AbroadhotelOrderService;

/**
 * 订单跳转售后服务类.
 */
public class AhotelOrdSaleAddJumpAction extends AhotelOrdSaleTreeAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 海外酒店订单的service.
	 */
	private AbroadhotelOrderService abroadhotelOrderService;
	/**
	 * 订单编号.
	 */
	private String orderId;
	/**
	 * 用户售后处理集合.
	 */
	private List<AhotelOrdSaleService> ordSaleServiceList;
	/**
	 * 退款查询集合.
	 */
	private List<AhotelOrdRefundment> ordRefundmentList;

	/**
	 * 生成树控件.
	 */
	public void doAfter() {
		if (orderId != null) {
			initSaleTreeList(orderId);// 创建树
		}
	}

	/**
	 * 初始化查询参数.
	 */
	public void doBefore() {
		Map<String,String> map = new HashMap<String,String>();
		map.put("orderId", orderId);
		ordRefundmentList = abroadhotelOrderService.findAhotelOrdRefundByParam(map, 0, 10);
	}

	public void setAbroadhotelOrderService(
			AbroadhotelOrderService abroadhotelOrderService) {
		this.abroadhotelOrderService = abroadhotelOrderService;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<AhotelOrdSaleService> getOrdSaleServiceList() {
		return ordSaleServiceList;
	}

	public void setOrdSaleServiceList(List<AhotelOrdSaleService> ordSaleServiceList) {
		this.ordSaleServiceList = ordSaleServiceList;
	}

	public List<AhotelOrdRefundment> getOrdRefundmentList() {
		return ordRefundmentList;
	}

	public void setOrdRefundmentList(List<AhotelOrdRefundment> ordRefundmentList) {
		this.ordRefundmentList = ordRefundmentList;
	}
}
