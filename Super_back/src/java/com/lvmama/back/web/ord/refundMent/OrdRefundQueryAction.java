package com.lvmama.back.web.ord.refundMent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zul.Label;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.pet.service.sale.OrderRefundService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;


/**
 * 订单退款查询类.
 * 
 * @author huangl
 */
@SuppressWarnings("unused")
public class OrdRefundQueryAction extends BaseAction {
	
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(OrdRefundQueryAction.class);
	private static final long serialVersionUID = 1L;
	/**
	 * 退款服务对像.
	 */
	private OrdRefundMentService ordRefundMentService;
	
	private OrderRefundService orderRefundService;
	
	/**
	 * 退款对象.
	 */
	private OrdRefundment ordRefundment;
	/**
	 * 退款查询集合.
	 */
	private List<OrdRefundment> ordRefundmentList;
	/**
	 * 订单子项编号.
	 */
	private String orderId;
	/**
	 * 订单服务接口
	 */
	private OrderService orderServiceProxy = (OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	/**
	 * 我的历史订单详细信息
	 */
	private OrdOrder historyOrderDetail;
	/**
	 * 售后服务编号.
	 */
	private String saleServiceId;
	/**
	 * 账号类型.
	 */
	List<CodeItem> channelList;
	/**
	 * 本页退款合计
	 */
	float countPageAmountYuan = (float)0.0 ;   
	/**
	 * 退款合计
	 */
	float countAmountYuan = 0l;
	private Map<String, Object> searchRefundMentMap = new HashMap<String, Object>();
	/**
	 * 中转售后处理结果.
	 * @return
	 */
	public void doBefore() {
		searchRefundMentMap.put("sysCode",Constant.COMPLAINT_SYS_CODE.SUPER.name());
	}
	/**
	 * 退款审核查询.
	 * @return
	 */
	public void ordRefundMentQuery() {
		//只查询未审核
		searchRefundMentMap.put("status", Constant.REFUNDMENT_STATUS.REFUND_VERIFIED.name());
		doQuery();
	}
	private void doQuery() {
		searchRefundMentMap = formatMap(searchRefundMentMap);
		Map map=initialPageInfoByMap(ordRefundMentService.findOrdRefundByParamCount(searchRefundMentMap),searchRefundMentMap);
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
		ordRefundmentList=ordRefundMentService.findOrdRefundByParam(searchRefundMentMap,skipResults,maxResults);
		countAmountYuan = ordRefundMentService.countOrdRefundSumAmount(ordRefundmentList);
		((Label)this.getComponent().getFellow("_totalRowCountLabel")).setValue(String.valueOf(ordRefundmentList.size()));
		Long countPageAmount = 0l;
		for (OrdRefundment refundment : ordRefundmentList) {
			if(refundment.getAmountYuan() > 0f){
				countPageAmount += refundment.getAmount();
			}
		}
		countPageAmountYuan = PriceUtil.convertToYuan(countPageAmount);
	}
	
	/**
	 * 批量驳回
	 */
	public void auditOrdRefundBatch(){
		// 当查询结果不为空时，执行驳回操作
		if(null != ordRefundmentList && ordRefundmentList.size() > 0){
			final String userName = this.getSessionUserName();
			// 弹出确认框，确认是否进行批量驳回操作
			ZkMessage.showQuestion("确认批量驳回吗?", new ZkMsgCallBack() {
				public void execute() {
					for(int i=0; i<ordRefundmentList.size(); i++){
						OrdRefundment refundment = ordRefundmentList.get(i);
						// 执行驳回--将状态改为REJECTED
						orderRefundService.updateOrderRefundmentApproveStatus(refundment.getRefundmentId(), 
								"REJECTED", refundment.getMemo(), userName);
					}
					
					// 刷新页面
					refreshComponent("search");
				}
			}, new ZkMsgCallBack() {
				public void execute() {
					
				}
			});
		}else{
			alert("没有退款单");
			return;
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
	public void setOrdRefundMentService(OrdRefundMentService ordRefundMentService) {
		this.ordRefundMentService = ordRefundMentService;
	}
	public OrdRefundment getOrdRefundment() {
		return ordRefundment;
	}
	public void setOrdRefundment(OrdRefundment ordRefundment) {
		this.ordRefundment = ordRefundment;
	}
	public List<OrdRefundment> getOrdRefundmentList() {
		return ordRefundmentList;
	}
	public void setOrdRefundmentList(List<OrdRefundment> ordRefundmentList) {
		this.ordRefundmentList = ordRefundmentList;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public OrdOrder getHistoryOrderDetail() {
		return historyOrderDetail;
	}
	public void setHistoryOrderDetail(OrdOrder historyOrderDetail) {
		this.historyOrderDetail = historyOrderDetail;
	}
	public String getSaleServiceId() {
		return saleServiceId;
	}
	public void setSaleServiceId(String saleServiceId) {
		this.saleServiceId = saleServiceId;
	}
	public List<CodeItem> getChannelList() {
		return channelList;
	}
	public void setChannelList(List<CodeItem> channelList) {
		this.channelList = channelList;
	}
	public Map<String, Object> getSearchRefundMentMap() {
		return searchRefundMentMap;
	}
	public void setSearchRefundMentMap(Map<String, Object> searchRefundMentMap) {
		this.searchRefundMentMap = searchRefundMentMap;
	}
	public float getCountPageAmountYuan() {
		return countPageAmountYuan;
	}
	public void setCountPageAmountYuan(float countPageAmountYuan) {
		this.countPageAmountYuan = countPageAmountYuan;
	}
	public OrderRefundService getOrderRefundService() {
		return orderRefundService;
	}
	public void setOrderRefundService(OrderRefundService orderRefundService) {
		this.orderRefundService = orderRefundService;
	}
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public float getCountAmountYuan() {
		return countAmountYuan;
	}
}
