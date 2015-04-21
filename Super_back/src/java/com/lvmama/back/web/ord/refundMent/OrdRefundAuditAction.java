package com.lvmama.back.web.ord.refundMent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bsh.This;

import com.lvmama.back.web.permission.PermUserMacroChooseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdRefundMentItem;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.bee.po.ord.OrdSaleServiceDeal;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceService;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceServiceDeal;
import com.lvmama.comm.pet.service.sale.OrderRefundService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vst.service.VstDistributorService;
import com.lvmama.comm.vst.service.VstOrdOrderService;
import com.lvmama.comm.vst.vo.VstOrdOrderItem;
import com.lvmama.comm.vst.vo.VstOrdOrderVo;

/**
 * 订单退款增加对像类.
 * 
 * @author huangl
 */
public class OrdRefundAuditAction extends ordTreeAddAction {
	private static final Log loger = LogFactory.getLog(OrdRefundAuditAction.class);
	private static final long serialVersionUID = 1L;
	/**
	 * 退款(审核通过/驳回退款)服务.
	 */
	private OrderRefundService orderRefundService;
	/**
	 * 新系统订单服务
	 */
	private VstOrdOrderService vstOrdOrderService;
	private UserUserProxy userUserProxy;
	private VstDistributorService vstDistributorService;
	/**
	 * 退款服务对像.
	 */
	private OrdRefundMentService ordRefundMentService;
	/**
	 * 退款对象.
	 */
	private OrdRefundment ordRefundment=new OrdRefundment();
	/**
	 * 退款查询集合.
	 */
	private List<OrdRefundment> ordRefundmentList;
	/**
	 * 订单子项编号.
	 */
	private String orderId;
	/**
	 * 我的历史订单详细信息
	 */
	private OrdOrder historyOrderDetail;
	/**
	 * 售后服务编号.
	 */
	private String saleServiceId;
	/**
	 * 综合查询封装.
	 */
	private Map<String, Object> searchRefundMent = new HashMap<String, Object>();
	/**
	 * 查看订单子子项集合.
	 */
	private  List<OrdOrderItemMeta> allOrdOrderItemMetas=new  ArrayList();
	/**
	 * 退款编号.
	 */
	private String refundmentId;
	/**
	 * 服务集合.
	 */
	private List<OrdSaleService> ordSaleServiceList;
	private List<OrdSaleServiceDeal> ordSaleServiceDealList;
    /**
     * 业务系统标示，新旧系统
     * @see com.lvmama.comm.vo.Constant.COMPLAINT_SYS_CODE
     */
    private String sysCode;
    
	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getSysCodeCnName() {
		if (StringUtils.isEmpty(this.sysCode)) {
			return "";
		}
		return Constant.COMPLAINT_SYS_CODE.getCnName(this.sysCode);
	}
	
	public VstDistributorService getVstDistributorService() {
		return vstDistributorService;
	}

	public void setVstDistributorService(VstDistributorService vstDistributorService) {
		this.vstDistributorService = vstDistributorService;
	}

	public void doAfter() {
		if (orderId != null) {
//			initTreeList(orderId);// 创建树
		}
	}
	/**
	 * 中转售后处理结果.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void doBefore() {
		if (orderId != null&&refundmentId!=null) {
			if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(this.sysCode)) {
				VstOrdOrderVo vstOrdOrderVo = vstOrdOrderService.getVstOrdOrderVo(new Long(orderId));
				System.out.println("vstOrdOrderVo is null?"+vstOrdOrderVo==null?true:false);
				System.out.println("userUserProxy is null?"+userUserProxy==null?true:false);
				System.out.println(StringUtil.printParam(vstOrdOrderVo));
				loger.info("vstOrdOrderVo is null?"+vstOrdOrderVo==null?true:false);
				loger.info("userUserProxy is null?"+userUserProxy==null?true:false);
				loger.info(StringUtil.printParam(vstOrdOrderVo));
				if(vstOrdOrderVo!=null) {
					loger.info("hyx log ");
					System.out.println("hyx println");
				}
				loger.info("vstOrdOrderVo="+vstOrdOrderVo);
				this.setVstOrdOrderVoDetail(vstOrdOrderVo);
				System.out.println("userUserProxy.getUserUserByUserNo start");
				loger.info("vstOrdOrderVo.getUserId()="+vstOrdOrderVo!=null?vstOrdOrderVo.getUserId():" vstOrdOrderVo is null");
				System.out.println("vstOrdOrderVo.getUserId() is null?"+vstOrdOrderVo.getUserId()==null?true:false);
				UserUser userUser=userUserProxy.getUserUserByUserNo(vstOrdOrderVo.getUserId());
				System.out.println("userUserProxy.getUserUserByUserNo end");
				long refundedAmount = this.getOrderServiceProxy().getRefundAmountByOrderId(Long.valueOf(orderId), this.sysCode);
				this.historyOrderDetail = new OrdOrder().setProp(vstOrdOrderVo, userUser, refundedAmount);
			}else {
				this.historyOrderDetail = this.getOrderServiceProxy().queryOrdOrderByOrderIdRefund(new Long(refundmentId),new Long(orderId));	
			}	
			Map refundMap=new HashMap();
			refundMap.put("refundmentId",refundmentId);
			if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(this.sysCode)){
				refundMap.put("sysCode",this.sysCode);
			}
			List list=this.getOrdRefundMentService().findOrdRefundByParam(refundMap,0,1);
			ordRefundment=(OrdRefundment)list.get(0);
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
		OrdRefundment ordrefundment = ordRefundMentService.findOrdRefundmentById(ordRefundment.getRefundmentId());
		if(Constant.REFUNDMENT_STATUS.REFUND_VERIFIED.name().equals(ordrefundment.getStatus())) {
			orderRefundService.updateOrderRefundmentApproveStatus(ordRefundment.getRefundmentId(), auidtStatus, ordRefundment.getMemo(), this.getSessionUserName());	
		}else {
			alert("退款单已被处理，请勿重复处理！");
		}
		this.refreshParent("search");
	}
	
	public void changeRefundType(String refundType) {
		ordRefundment.setRefundType(refundType);
	}
	public void changeAccountType(String accountType) {
		ordRefundment.setAccountType(accountType);
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
	public OrderService getOrderServiceProxy() {
		return (OrderService)SpringBeanProxy.getBean("orderServiceProxy");
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
	public OrdRefundMentService getOrdRefundMentService() {
		return (OrdRefundMentService)SpringBeanProxy.getBean("ordRefundMentService");
	}
	public Map<String, Object> getSearchRefundMent() {
		return searchRefundMent;
	}
	public void setSearchRefundMent(Map<String, Object> searchRefundMent) {
		this.searchRefundMent = searchRefundMent;
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
	public OrderRefundService getOrderRefundService() {
		return orderRefundService;
	}
	public void setOrderRefundService(OrderRefundService orderRefundService) {
		this.orderRefundService = orderRefundService;
	}
	public void setVstOrdOrderService(VstOrdOrderService vstOrdOrderService) {
		this.vstOrdOrderService = vstOrdOrderService;
	}

	public UserUserProxy getUserUserProxy() {
		return userUserProxy;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	public void setVstOrdOrderVoDetail(VstOrdOrderVo vstOrdOrderVo) {
		if(vstOrdOrderVo==null) {
			return;
		}
		//设置订单来源渠道
//		System.out.println("vstOrdOrderVo.getDistributorId()="+vstOrdOrderVo.getDistributorId());
//		if(vstOrdOrderVo.getDistributorId()!=null) {
//			vstOrdOrderVo.setDistributorName(vstDistributorService.getDistributorName(vstOrdOrderVo.getDistributorId()));	
//			System.out.println("vstOrdOrderVo.getDistributorName()="+vstOrdOrderVo.getDistributorName());
//		}
		//设置退款信息
		List<VstOrdOrderItem> vstOrdOrderItemList = vstOrdOrderVo.getVstOrdOrderItems();
		if(vstOrdOrderItemList!=null) {
			for(VstOrdOrderItem vstOrdOrderItem : vstOrdOrderItemList) {
				Long orderItemMetaId = vstOrdOrderItem.getOrderItemId();
				if(orderItemMetaId!=null) {
					List<OrdRefundMentItem> ordRefundMentItemList = ordRefundMentService.queryRefundMentItem(orderItemMetaId);
					for(OrdRefundMentItem ordRefundMentItem : ordRefundMentItemList) {
						vstOrdOrderItem.setAmountType(ordRefundMentItem.getType());
						vstOrdOrderItem.setAmountValue(ordRefundMentItem.getAmount());
						vstOrdOrderItem.setActualLoss(ordRefundMentItem.getActualLoss());
					}
				}
				//TODO vstOrdOrderItem.getSettlementStatus();	//结算状态
			}
		}
	}

	public void setOrdRefundMentService(OrdRefundMentService ordRefundMentService) {
		this.ordRefundMentService = ordRefundMentService;
	}
	
}
