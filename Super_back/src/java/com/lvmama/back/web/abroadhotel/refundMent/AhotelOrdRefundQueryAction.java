package com.lvmama.back.web.abroadhotel.refundMent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.abroad.po.AhotelOrdRefundment;
import com.lvmama.comm.abroad.service.AbroadhotelOrderService;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;


/**
 * 订单退款查询类.
 */
@SuppressWarnings("unused")
public class AhotelOrdRefundQueryAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5463497796479822999L;
	/**
	 * 退款对象.
	 */
	private AhotelOrdRefundment ordRefundment;
	/**
	 * 退款查询集合.
	 */
	private List<AhotelOrdRefundment> ordRefundmentList;
	/**
	 * 订单子项编号.
	 */
	private String orderId;
	/**
	 * 海外酒店订单的service.
	 */
	private AbroadhotelOrderService abroadhotelOrderService;
	/**
	 * 售后服务编号.
	 */
	private String saleServiceId;
	/**
	 * 本页退款合计
	 */
	Double countPageAmountYuan = 0D;   
	/**
	 * 退款合计
	 */
	java.math.BigDecimal countAmountYuan = java.math.BigDecimal.ZERO;
	private Map<String, Object> searchRefundMentMap = new HashMap<String, Object>();
	/**
	 * 中转售后处理结果.
	 * @return
	 */
	public void doBefore() {
	}
	/**
	 * 退款审核查询.
	 * @return
	 */
	public void ordRefundMentQuery() {
		//只查询未审核
		searchRefundMentMap.put("status", Constant.REFUNDMENT_STATUS.REFUND_VERIFIED.toString());
		doQuery();
	}
	private void doQuery() {
		Map map=initialPageInfoByMap(abroadhotelOrderService.findAhotelOrdRefundByParamCount(searchRefundMentMap),searchRefundMentMap);
		countAmountYuan = abroadhotelOrderService.findAhotelOrdfundByParamSumAmount(searchRefundMentMap);
		int skipResults=0;
		int maxResults=10;
		if(map.get("skipResults")!=null){
			skipResults=Integer.parseInt(map.get("skipResults").toString());
		}
		if(map.get("maxResults")!=null){
			maxResults=Integer.parseInt(map.get("maxResults").toString());
		}
		searchRefundMentMap.put("skipResults",skipResults);
		searchRefundMentMap.put("maxResults",maxResults);
		ordRefundmentList=abroadhotelOrderService.findAhotelOrdRefundByParam(searchRefundMentMap,skipResults,maxResults);
		countPageAmountYuan = 0D;
		for (AhotelOrdRefundment refundment : ordRefundmentList) {
			countPageAmountYuan += refundment.getAmountYuan();
		}
	}
	/**
	 * 退款任务历史查询
	 */
	public void ordRefundMentHisQuery() {
		doQuery();
	}
	
	public void selectStatus(String value) {
		if (null == value || "".equalsIgnoreCase(value)) {
			searchRefundMentMap.remove("status");
		} else {
			searchRefundMentMap.put("status", value);
		}
	}
	
	public OrdRefundMentService getOrdRefundMentService() {
		return (OrdRefundMentService)SpringBeanProxy.getBean("ordRefundMentService");
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSaleServiceId() {
		return saleServiceId;
	}
	public void setSaleServiceId(String saleServiceId) {
		this.saleServiceId = saleServiceId;
	}
	public Map<String, Object> getSearchRefundMentMap() {
		return searchRefundMentMap;
	}
	public void setSearchRefundMentMap(Map<String, Object> searchRefundMentMap) {
		this.searchRefundMentMap = searchRefundMentMap;
	}
	public void setCountPageAmountYuan(Double countPageAmountYuan) {
		this.countPageAmountYuan = countPageAmountYuan;
	}
	public void setCountAmountYuan(java.math.BigDecimal countAmountYuan) {
		this.countAmountYuan = countAmountYuan;
	}
	public Double getCountPageAmountYuan() {
		return countPageAmountYuan;
	}
	public java.math.BigDecimal getCountAmountYuan() {
		return countAmountYuan;
	}
	public AhotelOrdRefundment getOrdRefundment() {
		return ordRefundment;
	}
	public void setOrdRefundment(AhotelOrdRefundment ordRefundment) {
		this.ordRefundment = ordRefundment;
	}
	public List<AhotelOrdRefundment> getOrdRefundmentList() {
		return ordRefundmentList;
	}
	public void setOrdRefundmentList(List<AhotelOrdRefundment> ordRefundmentList) {
		this.ordRefundmentList = ordRefundmentList;
	}
	public AbroadhotelOrderService getAbroadhotelOrderService() {
		return abroadhotelOrderService;
	}
	public void setAbroadhotelOrderService(
			AbroadhotelOrderService abroadhotelOrderService) {
		this.abroadhotelOrderService = abroadhotelOrderService;
	}
	
}
