package com.lvmama.back.web.abroadhotel.refundMent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.abroad.po.AhotelOrdRefundment;
import com.lvmama.comm.abroad.service.AbroadhotelOrderService;
import com.lvmama.comm.abroad.vo.response.ReservationsOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.bee.po.ord.OrdSaleServiceDeal;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceService;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceServiceDeal;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;

/**
 * 订单退款增加对像类.
 */
public class AhotelOrdRefundAuditAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private UserUserProxy userUserProxy;
	/**
	 * 海外酒店订单的service.
	 */
	private AbroadhotelOrderService abroadhotelOrderService;
	/**
	 * 退款对象.
	 */
	private AhotelOrdRefundment ordRefundment = new AhotelOrdRefundment();
	/**
	 * 退款查询集合.
	 */
	private List<AhotelOrdRefundment> ordRefundmentList;
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
	 * 账号类型.
	 */
	List<CodeItem> channelList;
	/**
	 * 综合查询封装.
	 */
	private Map<String, Object> searchRefundMent = new HashMap<String, Object>();
	/**
	 * 查看订单子子项集合.
	 */
	private  List<OrdOrderItemMeta> allOrdOrderItemMetas = new  ArrayList();
	/**
	 * 退款编号.
	 */
	private String refundmentId;
	/**
	 * 服务集合.
	 */
	private List<OrdSaleService> ordSaleServiceList;
	private List<OrdSaleServiceDeal> ordSaleServiceDealList;
	public void doAfter() {
	}
	/**
	 * 中转售后处理结果.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void doBefore() {
		if (orderId != null&&refundmentId!=null) {
			this.historyOrderDetail = abroadhotelOrderService.queryAbroadHotelOrderByOrderId(orderId);
			UserUser user = userUserProxy.getUserUserByUserNo(historyOrderDetail.getUserId());
			historyOrderDetail.setUserId(user.getUserName());
			
			channelList = CodeSet.getInstance().getCachedCodeList(Constant.CODE_TYPE.ACCOUNT_TYPE.name());
			Map refundMap=new HashMap();
			refundMap.put("refundmentId",refundmentId);
			List<AhotelOrdRefundment> list = abroadhotelOrderService.findAhotelOrdRefundByParam(refundMap,0,1);
			ordRefundment=list.get(0);
			if(ordRefundment.getSaleServiceId()!=null){
				saleServiceId=ordRefundment.getSaleServiceId().toString();
			}else{
				saleServiceId=null;
			}
			
		}
	}
	
	/**
	 * 审核用户提交的退款单.退款审核动作：已退款,审核不通过（拒绝、驳回）,未审核,审核通过(等待退款) 
	 */
	public void auditOrdRefund(String auidtStatus){
		abroadhotelOrderService.updateOrderRefundmentApproveStatus(ordRefundment.getRefundmentId(), auidtStatus, ordRefundment.getMemo(), this.getSessionUserName());
		this.refreshParent("search");
	}
	
	public void changeRefundType(String refundType) {
		ordRefundment.setRefundType(refundType);
	}
	public void changeAccountType(String accountType) {
		ordRefundment.setAccountType(accountType);
	}
	public void changeRefundChannel(String refundChannel) {
		ordRefundment.setRefundChannel(refundChannel);
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

	public String getSaleServiceId() {
		return saleServiceId;
	}

	public void setSaleServiceId(String saleServiceId) {
		this.saleServiceId = saleServiceId;
	}
	public OrdRefundMentService getOrdRefundMentService() {
		return (OrdRefundMentService)SpringBeanProxy.getBean("ordRefundMentService");
	}
	public Map<String, Object> getSearchRefundMent() {
		return searchRefundMent;
	}
	public void setSearchRefundMent(Map<String, Object> searchRefundMent) {
		this.searchRefundMent = searchRefundMent;
	}
	public List<CodeItem> getChannelList() {
		return channelList;
	}
	public void setChannelList(List<CodeItem> channelList) {
		this.channelList = channelList;
	}

	public List<OrdOrderItemMeta> getAllOrdOrderItemMetas() {
		return allOrdOrderItemMetas;
	}

	public void setAllOrdOrderItemMetas(List<OrdOrderItemMeta> allOrdOrderItemMetas) {
		this.allOrdOrderItemMetas = allOrdOrderItemMetas;
	}

	public OrdSaleServiceService getOrdSaleServiceService() {
		return (OrdSaleServiceService)SpringBeanProxy.getBean("ordSaleServiceService");
	}
	public OrdSaleServiceServiceDeal getOrdSaleServiceServiceDeal() {
		return (OrdSaleServiceServiceDeal)SpringBeanProxy.getBean("ordSaleServiceServiceDeal");
	}

	public List<OrdSaleService> getOrdSaleServiceList() {
		return ordSaleServiceList;
	}

	public void setOrdSaleServiceList(List<OrdSaleService> ordSaleServiceList) {
		this.ordSaleServiceList = ordSaleServiceList;
	}

	public List<OrdSaleServiceDeal> getOrdSaleServiceDealList() {
		return ordSaleServiceDealList;
	}

	public void setOrdSaleServiceDealList(
			List<OrdSaleServiceDeal> ordSaleServiceDealList) {
		this.ordSaleServiceDealList = ordSaleServiceDealList;
	}

	public String getRefundmentId() {
		return refundmentId;
	}

	public void setRefundmentId(String refundmentId) {
		this.refundmentId = refundmentId;
	}
	public AbroadhotelOrderService getAbroadhotelOrderService() {
		return abroadhotelOrderService;
	}
	public void setAbroadhotelOrderService(
			AbroadhotelOrderService abroadhotelOrderService) {
		this.abroadhotelOrderService = abroadhotelOrderService;
	}
	public UserUserProxy getUserUserProxy() {
		return userUserProxy;
	}
	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	
}
