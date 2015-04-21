package com.lvmama.back.web.ord.refundMent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;

import com.lvmama.back.utils.StringUtil;
import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdRefundMentItem;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.bee.po.ord.OrdSaleServiceDeal;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceService;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceServiceDeal;
import com.lvmama.comm.pet.service.sale.OrderRefundService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.PriceUtil;
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
public class OrdOrderRefundUpdateAction extends ordTreeAddAction {
	private OrderService orderServiceProxy = (OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	/**
	 * 增加退款对像服务.
	 */
	private OrderRefundService orderRefundService;
	/**
	 * 退款服务对像.
	 */
	private OrdRefundMentService ordRefundMentService;
	/**
	 * 退款对象.
	 */
	private OrdRefundment ordRefundment = new OrdRefundment();
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
	 * 退款编号.
	 */
	private String refundmentId;
	/**
	 * 订单服务类型 .
	 */
	private String serviceType;
	
	private String cancelResson;

	private Listbox ordItemMetaListbox;
	/**
	 * 新系统订单服务
	 */
	private VstOrdOrderService vstOrdOrderService;
	private UserUserProxy userUserProxy;
	private VstDistributorService vstDistributorService;
	
	public VstDistributorService getVstDistributorService() {
		return vstDistributorService;
	}

	public void setVstDistributorService(VstDistributorService vstDistributorService) {
		this.vstDistributorService = vstDistributorService;
	}

	public Listbox getOrdItemMetaListbox() {
		return ordItemMetaListbox;
	}

	public void setOrdItemMetaListbox(Listbox ordItemMetaListbox) {
		this.ordItemMetaListbox = ordItemMetaListbox;
	}
	/**
	 *  选中的采购产品
	 */
	private List<OrdOrderItemMeta> orderItemMetaList = new ArrayList();
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
	
	public List<OrdOrderItemMeta> getOrderItemMetaList() {
		return orderItemMetaList;
	}

	private ComLogService comLogService;
	
	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}


	public boolean setOrderItemMetaList(List<OrdOrderItemMeta> orderItemMetaList, String refundType) {
		Set set = ordItemMetaListbox.getSelectedItems();
		//如果是补偿单，当不选择明细的时候，直接返回true
		if(set.size() == 0 && !ordRefundment.getRefundType().equals("ORDER_REFUNDED")){
			return true;
		}
		List<OrdOrderItemMeta> orderItemMetasList = historyOrderDetail.getAllOrdOrderItemMetas();

		for ( Object tmp: set ) {
			OrdOrderItemMeta orderItemMeta = new OrdOrderItemMeta();
			Listitem listItem = (Listitem)tmp;
			List listCells = listItem.getChildren();
			Listcell cell0 = (Listcell)listCells.get(0);
			Label label = (Label)cell0.getChildren().get(0);
			
			for(int j=0; j<orderItemMetasList.size(); j++){
				OrdOrderItemMeta ordOrderItemMeta = orderItemMetasList.get(j);
				if(Long.parseLong(label.getId()) == ordOrderItemMeta.getOrderItemMetaId()){
					orderItemMeta = ordOrderItemMeta;
				}
			}
			
			// 采购产品id
			orderItemMeta.setMetaProductId(Long.parseLong(label.getValue()));

			// 金额类型（游客损失/供应商承担金额）
			Listcell cell3 = (Listcell)listCells.get(3);
			Listbox listbox3 = (Listbox)cell3.getChildren().get(0);
			if(null != listbox3.getSelectedItem()){
				orderItemMeta.setAmountType((String) listbox3.getSelectedItem().getValue());
			} else{
				alert("金额类型不能为空");
				return false;
			}
			
			// 金额（游客损失/供应商承担金额）
			Doublebox textbox3 = (Doublebox)cell3.getChildren().get(1);
			Label label2 = (Label)cell3.getChildren().get(2);
			Label label3 = (Label)cell3.getChildren().get(3);
			Label label4 = (Label)cell3.getChildren().get(4);
			Long compareValue = Long.parseLong(label2.getValue()) * Long.parseLong(label3.getValue()) * Long.parseLong(label4.getValue());
			if(null != textbox3.getValue()){
				if(!textbox3.getValue().equals("")){
					if(textbox3.getValue().longValue() < 0){
						alert("游客损失或供应商承担金额不能小于0");
						return false;
					}
					if(PriceUtil.convertToFen(textbox3.getValue().toString()) > compareValue){
						alert("游客损失或供应商承担金额过大，请重新输入");
						return false;
					}
					orderItemMeta.setAmountValue(PriceUtil.convertToFen(textbox3.getValue().toString()));
				}
			} else{
				alert("游客损失或供应商承担金额不能为空");
				return false;
			}

			// 备注
			Listcell cell4 = (Listcell)listCells.get(4);
			Textbox listbox4 = (Textbox)cell4.getChildren().get(0);
			if(null != listbox4.getValue()){
				orderItemMeta.setMemo(listbox4.getValue());
			}
			
			orderItemMetaList.add(orderItemMeta);
		}
		
		// 当退款单类型为退款时，判断是否选中订单明细
		if(orderItemMetaList.size()==0 && refundType.equals("ORDER_REFUNDED")){
			alert("【请选择订单进行退款】!");
			return false;
		}
		
		return true;
	}

	/**
	 * 计算退款金额
	 */
	public Double computeAmount(String refundType){
		// 订单实付金额
		Long actualPay = historyOrderDetail.getActualPay();
		// 如果是补偿单，退款金额为订单实付金额
		if(refundType.equals("COMPENSATION")){
			ordRefundment.setAmount(PriceUtil.convertToFen(actualPay.toString()));
			return Double.valueOf(String.valueOf(historyOrderDetail.getActualPayYuan()));
		}
		// 违约金
		Long penaltyAmount = ordRefundment.getPenaltyAmount();
		// 游客损失
		Long visitorLoss = 0l;
		Set set = ordItemMetaListbox.getSelectedItems();
		for ( Object tmp: set ) {
			Listitem listItem = (Listitem)tmp;
			List listCells = listItem.getChildren();
			Listcell cell3 = (Listcell)listCells.get(3);
			Listbox Listbox = (Listbox)cell3.getChildren().get(0);
			if(null != Listbox.getSelectedItem() && Listbox.getSelectedItem().getValue().equals("VISITOR_LOSS")){
				Doublebox doublebox = (Doublebox)cell3.getChildren().get(1);
				if(null != doublebox.getValue()){
					Double v = doublebox.getValue();
					visitorLoss += PriceUtil.convertToFen(v.toString());
				}
			}
		}
		
		Long amountVlaue;
		Long refundAmount = historyOrderDetail.getRefundedAmount();
		refundAmount = refundAmount == null ? 0 : refundAmount;
		if(null != penaltyAmount){
			amountVlaue = actualPay - penaltyAmount - visitorLoss - refundAmount;
		}else{
			amountVlaue = actualPay - visitorLoss - refundAmount;
		}
		ordRefundment.setAmount(amountVlaue);
		amountVlaue = amountVlaue < 0 ? 0 : amountVlaue;
		return Double.valueOf(String.valueOf(PriceUtil.convertToYuan(amountVlaue)));
	}
	
	private OrdSaleService ordSalePo=new OrdSaleService();
	
	/**
	 * 是否显示【转投诉】按钮
	 */
	private boolean showComplaint;

	public boolean isShowComplaint() {
		return showComplaint;
	}

	public void setShowComplaint(boolean showComplaint) {
		this.showComplaint = showComplaint;
	}
	
	// 用来判断“违约金”是否可用
	private boolean penaltyAmountFlag;
	
	public boolean isPenaltyAmountFlag() {
		return penaltyAmountFlag;
	}

	public void setPenaltyAmountFlag(boolean penaltyAmountFlag) {
		this.penaltyAmountFlag = penaltyAmountFlag;
	}
	
	// 用来判断是从售后进入还是从退款单中进入（false表示从退款单中进入）
	private String updateStatus;

	public String getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}

	@SuppressWarnings("unchecked")
	public void doBefore(){
		if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(this.sysCode)) {
			VstOrdOrderVo vstOrdOrderVo = vstOrdOrderService.getVstOrdOrderVo(new Long(orderId));
			this.setVstOrdOrderVoDetail(vstOrdOrderVo);
			UserUser userUser=userUserProxy.getUserUserByUserNo(vstOrdOrderVo.getUserId());
			long refundedAmount = this.getOrderServiceProxy().getRefundAmountByOrderId(Long.valueOf(orderId), this.sysCode);
			this.historyOrderDetail = new OrdOrder().setProp(vstOrdOrderVo, userUser, refundedAmount);
		}else {
			historyOrderDetail= orderServiceProxy.queryOrdOrderByOrderIdRefund(Long.valueOf(refundmentId),Long.valueOf(orderId));
		}
		Map map=new HashMap();
		map.put("refundmentId",refundmentId);
		map.put("sysCode", this.sysCode);
		ordRefundment=(OrdRefundment)ordRefundMentService.findOrdRefundByParam(map, 0, 1).get(0);
		// 判断初始化页面时，违约金输入框是否可用
		if(!ordRefundment.getRefundType().equals("ORDER_REFUNDED")){
			penaltyAmountFlag = true;
		}else{
			penaltyAmountFlag = false;
		}
		
		if(ordRefundment.getRefundType().equals("ORDER_REFUNDED")){
			// 判断是否显示【转投诉】按钮
			if(null != serviceType){
				if(serviceType.equals("NORMAL")){// 常规售后
					Map param = new HashMap();
					param.put("orderId", orderId);
					// 查询该订单下的所有退款单
					List<OrdRefundment> refundmentList = ordRefundMentService.queryRefundmentByOrderId(param);
					if(null != refundmentList && refundmentList.size() > 0){
						for(int i=0; i<refundmentList.size(); i++){
							OrdRefundment ordRefundment = refundmentList.get(i);
							if(!ordRefundment.getStatus().equals("REFUND_APPLY") 
									&& !ordRefundment.getStatus().equals("APPLY_CONFIRM") 
									&& !ordRefundment.getStatus().equals("UNVERIFIED") 
									&& !ordRefundment.getStatus().equals("REFUND_VERIFIED")){
								showComplaint = false;
								return;
							}
						}
						showComplaint = true;
					}else{
						showComplaint = true;
					}
				}else{
					showComplaint = false;
				}
			}
		}else{
			showComplaint = false;
		}
	}
	
	/**
	 * 将用户的售后服务类型转换为投拆类型.
	 */
	public void updateOrdSaleServiceType(){
		String status = "COMPLAINT";
		if(!StringUtil.isEmptyString(saleServiceId)){
			//记录用户点击转换投拆时日志.
			OrdSaleServiceDeal ordSaleDeal=new OrdSaleServiceDeal();
			ordSaleDeal.setCreateTime(new Date());
			ordSaleDeal.setOperatorName(this.getSessionUserName());
			ordSaleDeal.setDealContent("客服:"+this.getSessionUserName()+",将其售后状态变更!");
			ordSaleDeal.setSaleServiceId(Long.valueOf(saleServiceId));
			this.getOrdSaleServiceServiceDeal().addOrdSaleServiceDeal(ordSaleDeal);
			//进行转换投拆或常规售后.
			OrdSaleService ordsaleservice=this.getOrdSaleServiceService().getOrdSaleServiceByPk(Long.valueOf(saleServiceId));
			ordsaleservice.setServiceType(status);
			boolean flag = this.getOrdSaleServiceService().updateOrdSaleService(ordsaleservice);
			if(flag){
				alert("转投诉成功");
			}else{
				alert("转投诉失败");
			}
			// 更新父窗口的列表数据
			this.refreshParent("search");
		}
	}

	public OrdSaleServiceServiceDeal getOrdSaleServiceServiceDeal() {
		return (OrdSaleServiceServiceDeal)SpringBeanProxy.getBean("ordSaleServiceServiceDeal");
	}
	
	public void doAfter() {
		if (orderId != null) {
//			initTreeList(orderId);// 创建菜单
		}
	}
	/**
	 * 增加退款认务.
	 * 
	 * @return
	 */
	public void updateRefundMent(String refundType) {
			if (orderId != null) {
				if(refundType.equals("订单退款")){
					refundType = "ORDER_REFUNDED";
				}else{
					refundType = "COMPENSATION";
				}
				
				// 退款单需要校验，补偿单不校验退款金额不能大于订单应付金额
				if("ORDER_REFUNDED".equals(refundType)){
					float amount= ordRefundment.getAmountYuan();
					float paytotal=historyOrderDetail.getActualPayYuan();
					float refundedAmount = historyOrderDetail.getRefundedAmountYuan();
					BigDecimal bd1 = new BigDecimal(Float.toString(paytotal));
					float oughtRefundAmout = bd1.subtract(new BigDecimal(Float.toString(refundedAmount))).floatValue();
					if (amount > oughtRefundAmout) {
							alert("实付金额:"+paytotal+"元,已退款金额："+refundedAmount+",退款金额不能大于【订单总金额】!");
							return;
					}
				}
				/**
				 *  需要判断是否选中采购产品id
				 */
				orderItemMetaList = new ArrayList();
				boolean flag = this.setOrderItemMetaList(orderItemMetaList, refundType);
				if(!flag){
					return;
				}
				if(ordRefundment.getAmount() == null){
					ordRefundment.setAmountYuanStr("0");
				}
				if(ordRefundment.getAmount() < 0){
					alert("退款金额不能小于0");
					return;
				}
				
				/**
				 *  删除之前的退款单明细
				 */
				boolean isRefund = orderRefundService.deteleRefund(Long.parseLong(orderId), Long.parseLong(refundmentId), refundType);
				
				/**
				 * 判断删除退款单和退款单明细是否成功
				 * 		成功：继续生成新的退款单和退款单明细
				 * 		失败：给出提示，程序终止
				 */
				if(isRefund){
					// 如果违约金为空，则指定默认值为0
					if(null == ordRefundment.getPenaltyAmount()){
						ordRefundment.setPenaltyAmount(0l);
					}
					
					// 状态改为:客服审核通过（从售后服务进入）
					String refundTypeString = Constant.REFUNDMENT_STATUS.REFUND_VERIFIED.name();
					// 调用接口生成退款单和退款单明细
					if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(sysCode)) {
						isRefund = orderRefundService.updateVstRefundment(Long.valueOf(orderId), Long.valueOf(saleServiceId), 
								ordRefundment.getAmount(), refundType,
								refundTypeString, ordRefundment.getMemo(), 
								this.getSessionUserName(), orderItemMetaList, Long.parseLong(refundmentId), ordRefundment.getPenaltyAmount());
					}else {
						isRefund = orderRefundService.updateRefundment(Long.valueOf(orderId), Long.valueOf(saleServiceId), 
								ordRefundment.getAmount(), refundType,
								refundTypeString, ordRefundment.getMemo(), 
								this.getSessionUserName(), orderItemMetaList, Long.parseLong(refundmentId), ordRefundment.getPenaltyAmount());	
					}
					
					//当退款单为第一笔退款单时，询问是否需要废单
					Map<String,Object> p = new HashMap<String,Object>();
					p.put("orderId", orderId);
					p.put("sysCode", sysCode);
					if (isRefund && getOrdRefundMentService().findOrdRefundByParamCount(p).longValue() == 1L ) {
						OrdRefundment refundment = (OrdRefundment) getOrdRefundMentService().findOrdRefundByParam(p, 0, 2).get(0);
						if (Constant.REFUNDMENT_TYPE.ORDER_REFUNDED.name().equalsIgnoreCase(refundment.getRefundType())
								&& !historyOrderDetail.getOrderStatus().equals(Constant.ORDER_STATUS.CANCEL.toString())) {
							
							ZkMessage.showQuestion("您需要将此订单作废吗?", new ZkMsgCallBack() {
								public void execute() {
									// 判断订单用户是否为有效状态
									if(historyOrderDetail.getUser().getIsValid().equals("N")){
										ZkMessage.showQuestion("此订单用户为无效状态，是否继续?", new ZkMsgCallBack() {
											public void execute() {
												if(StringUtils.isEmpty(cancelResson)){
													cancelResson="旅客申请取消";
												}
												if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(sysCode)) {
													getOrderServiceProxy().cancelVstOrder(Long.parseLong(orderId), cancelResson, getOperatorName());
												}else {
													getOrderServiceProxy().cancelOrder(Long.parseLong(orderId), cancelResson, getOperatorName());
												}
											}
										}, new ZkMsgCallBack() {
											public void execute() {
												
											}
										});
									}else{
										if(StringUtils.isEmpty(cancelResson)){
											cancelResson="旅客申请取消";
										}
										if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(sysCode)) {
											getOrderServiceProxy().cancelVstOrder(Long.parseLong(orderId), cancelResson, getOperatorName());
										}else {
											getOrderServiceProxy().cancelOrder(Long.parseLong(orderId), cancelResson, getOperatorName());
										}
									}
								}
							}, new ZkMsgCallBack() {
								public void execute() {
									
								}
							});
						}
					}
					
					if(isRefund){
						alert("修改成功");
					}else{
						alert("修改失败");
					}
					
					// 更新父窗口的列表数据
					this.refreshParent("search");
				}
			}
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getRefundmentId() {
		return refundmentId;
	}

	public void setRefundmentId(String refundmentId) {
		this.refundmentId = refundmentId;
	}

	public OrdSaleServiceService getOrdSaleServiceService() {
		return (OrdSaleServiceService)SpringBeanProxy.getBean("ordSaleServiceService");
	}
	public OrdRefundment getOrdRefundment() {
		return ordRefundment;
	}

	public void setOrdRefundment(OrdRefundment ordRefundment) {
		this.ordRefundment = ordRefundment;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public OrderService getOrderServiceProxy() {
		return (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
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
		return (OrdRefundMentService) SpringBeanProxy
				.getBean("ordRefundMentService");
	}

	public void setOrdRefundMentService(
			OrdRefundMentService ordRefundMentService) {
		this.ordRefundMentService = ordRefundMentService;
	}

	public Tree getOrdTree() {
		return ordTree;
	}

	public void setOrdTree(Tree ordTree) {
		this.ordTree = ordTree;
	}

	public List getCheckItemId() {
		return checkItemId;
	}

	public void setCheckItemId(List checkItemId) {
		this.checkItemId = checkItemId;
	}
	
	public OrderRefundService getOrderRefundService() {
		return orderRefundService;
	}

	public void setOrderRefundService(OrderRefundService orderRefundService) {
		this.orderRefundService = orderRefundService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public OrdSaleService getOrdSalePo() {
		return ordSalePo;
	}
	public void setOrdSalePo(OrdSaleService ordSalePo) {
		this.ordSalePo = ordSalePo;
	}

	public String getCancelResson() {
		return cancelResson;
	}

	public void setCancelResson(String cancelResson) {
		this.cancelResson = cancelResson;
	}

	public VstOrdOrderService getVstOrdOrderService() {
		return vstOrdOrderService;
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
//		if(vstOrdOrderVo.getDistributorId()!=null) {
//			vstOrdOrderVo.setDistributorName(vstDistributorService.getDistributorName(vstOrdOrderVo.getDistributorId()));	
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
}
