package com.lvmama.back.web.ord.sale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.ord.NcComplaint;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceService;
import com.lvmama.comm.spring.SpringBeanProxy;

/**
 * 订单跳转售后服务类.
 * 
 * @author huangl
 */
@SuppressWarnings("unused")
public class OrdSaleAddJumpAction extends OrdSaleTreeAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 订单编号.
	 */
	private String orderId;
	
	private String sysCode;
	/**
	 * 用户售后处理集合.
	 */
	private List<OrdSaleService> ordSaleServiceList;
	
	/**
	 * 新投诉信息
	 */
	private List<NcComplaint> complaintList;
	
	/**
	 * 退款查询集合.
	 */
	private List<OrdRefundment> ordRefundmentList;
	
	
	/**
	 * 生成树控件.
	 */
	public void doAfter() {
		if (orderId != null) {
			initSaleTreeList(orderId,sysCode);// 创建树
//			initTreeList(orderId);// 创建树
			initComplaintTreeList(orderId,sysCode);
		}
	}

	/**
	 * 初始化查询参数.
	 */
	@SuppressWarnings("unchecked")
	public void doBefore() {
		Map map = new HashMap();
		map.put("orderId", orderId);
		if(StringUtils.isNotEmpty(sysCode)){
			map.put("sysCode", sysCode);
		}
		ordRefundmentList = this.getOrdRefundMentService().findOrdRefundByParam(map, 0, 10);
	}

	public OrdRefundMentService getOrdRefundMentService() {
		return (OrdRefundMentService) SpringBeanProxy
				.getBean("ordRefundMentService");
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public OrdSaleServiceService getOrdSaleServiceService() {
		return (OrdSaleServiceService) SpringBeanProxy
				.getBean("ordSaleServiceService");
	}

	public List<OrdSaleService> getOrdSaleServiceList() {
		return ordSaleServiceList;
	}

	public void setOrdSaleServiceList(List<OrdSaleService> ordSaleServiceList) {
		this.ordSaleServiceList = ordSaleServiceList;
	}

	public List<OrdRefundment> getOrdRefundmentList() {
		return ordRefundmentList;
	}

	public void setOrdRefundmentList(List<OrdRefundment> ordRefundmentList) {
		this.ordRefundmentList = ordRefundmentList;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	
	
}
