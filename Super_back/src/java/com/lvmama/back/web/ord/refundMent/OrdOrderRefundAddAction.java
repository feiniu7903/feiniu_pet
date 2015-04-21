package com.lvmama.back.web.ord.refundMent;

import java.io.UnsupportedEncodingException;
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
import org.zkoss.zul.Window;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdRefundMentItem;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.ord.SendContractEmailService;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.ComMessage;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceService;
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
public class OrdOrderRefundAddAction extends ordTreeAddAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7408246568365029019L;
	private OrderService orderServiceProxy = (OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	private ComMessageService comMessageService;
	private PermUserService permUserService;
	private MetaProductService metaProductService;
	/**
	 * 窗口.
	 */
	private Window winOrdSaleAdd;
	/**
	 * 退款服务对像.
	 */
	private OrdRefundMentService ordRefundMentService;
	/**
	 * 增加售后服务接口.
	 */
	private OrdSaleServiceService ordSaleServiceService;
	/**
	 * 发送作废合同短信
	 */
	private SendContractEmailService sendContractEmailService;
	/**
	 * 新系统订单服务
	 */
	private VstOrdOrderService vstOrdOrderService;
	private UserUserProxy userUserProxy;
	private VstDistributorService vstDistributorService;
	/**
	 * 退款对象.
	 */
	private OrdRefundment ordRefundment = new OrdRefundment();
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
	private List<OrdOrderItemMeta> allOrdOrderItemMetas = new ArrayList();
	/**
	 * 订单服务集合.
	 */
	private List<OrdSaleService> ordSaleServiceList;
	
	private String ordSaleContext;
	/**
	 * 是否被废弃订单.
	 */
	private String isCancelOrder;
	/**
	 * 废单原因.
	 */
	private String cancelResson;
	/**
	 * 购建售后处理对像.
	 */
	private boolean enabled=true;
	private OrdSaleService ordSalePo=new OrdSaleService();

	private Listbox ordItemMetaListbox;
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
	 * 新系统中选中的采购产品
	 */
	private List<VstOrdOrderItem> vstOrdOrderItemsList = new ArrayList();
	
	public List<OrdOrderItemMeta> getOrderItemMetaList() {
		return orderItemMetaList;
	}

	public boolean setOrderItemMetaList(List<OrdOrderItemMeta> orderItemMetaList, String refundStatus) {
		Set set = ordItemMetaListbox.getSelectedItems();
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
			
			/**
			 * 不为退款申请时，需要校验明细项，退款申请时不需要校验，只要选中明细即可
			 */
			if(!"REFUND_APPLY".equals(refundStatus)){
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
				if(refundStatus.equals("REFUND_APPLY")){
					orderItemMeta.setAmountValue(0l);
				}else{
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
							// 如果金额类型为游客损失，则将实际损失等于游客损失
							if(orderItemMeta.getAmountType().equals("VISITOR_LOSS")){
								orderItemMeta.setActualLoss(PriceUtil.convertToFen(textbox3.getValue().toString()));
							}
						}
					} else{
						alert("游客损失或供应商承担金额不能为空");
						return false;
					}
				}

				// 备注
				Listcell cell4 = (Listcell)listCells.get(4);
				Textbox listbox4 = (Textbox)cell4.getChildren().get(0);
				if(null != listbox4.getValue()){
					orderItemMeta.setMemo(listbox4.getValue());
				}
			}
			
			orderItemMetaList.add(orderItemMeta);
		}
		if(orderItemMetaList.size()==0){
			alert("【请选择订单进行退款】!");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 新系统中采购的产品
	 * @param orderItemMetaList
	 * @param refundStatus
	 * @return
	 */
	public boolean setVstOrdOrderItemsList(List<VstOrdOrderItem> vstOrdOrderItemsList, String refundStatus, VstOrdOrderVo vstOrdOrderVo) {
		Set set = ordItemMetaListbox.getSelectedItems();
		List<VstOrdOrderItem> vstOrdOrderItems = vstOrdOrderVo.getVstOrdOrderItems();
		for ( Object tmp: set ) {
			VstOrdOrderItem vstOrderItem = new VstOrdOrderItem();
			Listitem listItem = (Listitem)tmp;
			List listCells = listItem.getChildren();
			Listcell cell0 = (Listcell)listCells.get(0);
			Label label = (Label)cell0.getChildren().get(0);
			
			for(int j=0; j<vstOrdOrderItems.size(); j++){
				VstOrdOrderItem vstOrdOrderItem = vstOrdOrderItems.get(j);
				if(Long.parseLong(label.getId()) == vstOrdOrderItem.getOrderItemId()){
					vstOrderItem = vstOrdOrderItem;
				}
			}
			
			// 采购产品id
			vstOrderItem.setProductId(Long.parseLong(label.getValue()));
			
			/**
			 * 不为退款申请时，需要校验明细项，退款申请时不需要校验，只要选中明细即可
			 */
			if(!"REFUND_APPLY".equals(refundStatus)){
				// 金额类型（游客损失/供应商承担金额）
				Listcell cell3 = (Listcell)listCells.get(3);
				Listbox listbox3 = (Listbox)cell3.getChildren().get(0);
				if(null != listbox3.getSelectedItem()){
					vstOrderItem.setAmountType((String) listbox3.getSelectedItem().getValue());
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
				if(refundStatus.equals("REFUND_APPLY")){
					vstOrderItem.setAmountValue(0l);
				}else{
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
							vstOrderItem.setAmountValue(PriceUtil.convertToFen(textbox3.getValue().toString()));
							// 如果金额类型为游客损失，则将实际损失等于游客损失
							if(vstOrderItem.getAmountType().equals("VISITOR_LOSS")){
								vstOrderItem.setActualLoss(PriceUtil.convertToFen(textbox3.getValue().toString()));
							}
						}
					} else{
						alert("游客损失或供应商承担金额不能为空");
						return false;
					}
				}

				// 备注
				Listcell cell4 = (Listcell)listCells.get(4);
				Textbox listbox4 = (Textbox)cell4.getChildren().get(0);
				if(null != listbox4.getValue()){
					vstOrderItem.setMemo(listbox4.getValue());
				}
			}
			
			vstOrdOrderItemsList.add(vstOrderItem);
		}
		if(vstOrdOrderItemsList.size()==0){
			alert("【请选择订单进行退款】!");
			return false;
		}
		
		return true;
	}

	
	/**
	 * 计算退款金额
	 */
	public Double computeAmount(){
		// 订单实付金额
		Long actualPay = historyOrderDetail.getActualPay();
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

	private ComLogService comLogService;
	
	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public void doBefore(){
		if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(sysCode)) {
			VstOrdOrderVo vstOrdOrderVo = vstOrdOrderService.getVstOrdOrderVo(new Long(orderId));
			this.setVstOrdOrderVoDetail(vstOrdOrderVo);
			UserUser userUser=userUserProxy.getUserUserByUserNo(vstOrdOrderVo.getUserId());
			long refundedAmount = this.getOrderServiceProxy().getRefundAmountByOrderId(Long.valueOf(orderId), this.sysCode);
			this.historyOrderDetail = new OrdOrder().setProp(vstOrdOrderVo, userUser, refundedAmount);
		}else {
			historyOrderDetail= orderServiceProxy.queryOrdOrderByOrderIdRefund(0L,Long.valueOf(orderId));
			if (historyOrderDetail.getOrderStatus().equals(Constant.ORDER_STATUS.CANCEL.name())){
				enabled = false;
			}
		}
	}
	
	public void doAfter() {
		if (orderId != null) {
//			initTreeList(orderId);// 创建菜单
		}
	}
	
	public void checkUserIsvalid(final String refundStatus){
		if(historyOrderDetail.getUser().getIsValid().equals("N")){
			ZkMessage.showQuestion("此订单用户为无效状态，是否继续?", new ZkMsgCallBack() {
				public void execute() {
					addRefundMent(refundStatus);
				}
			}, new ZkMsgCallBack() {
				public void execute() {
					
				}
			});
		}else{
			addRefundMent(refundStatus);
		}
	}
	
	/**
	 * 增加退款认务.
	 * 
	 * @return
	 */
	public void addRefundMent(String refundStatus) {
			if (orderId != null) {
				// 退款单需要校验，补偿单不校验退款金额不能大于订单应付金额
				if("ORDER_REFUNDED".equals(ordRefundment.getRefundType())){
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
//				/**
//				 * 不为退款申请时，需要判断是否选中采购产品id
//				 */
//				if(!"REFUND_APPLY".equals(refundStatus)){
//				}
				boolean flag1;
				if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(sysCode)) {
					vstOrdOrderItemsList = new ArrayList();
					VstOrdOrderVo vstOrdOrderVo = vstOrdOrderService.getVstOrdOrderVo(new Long(orderId));
					flag1 = this.setVstOrdOrderItemsList(vstOrdOrderItemsList, refundStatus, vstOrdOrderVo);
				}else {
					orderItemMetaList = new ArrayList();
					flag1 = this.setOrderItemMetaList(orderItemMetaList, refundStatus);
				}
				if(!flag1){
					return;
				}
				
				OrdSaleService ordSevice=new OrdSaleService();
				ordSevice.setCreateTime(new Date());
				ordSevice.setOperatorName(this.getSessionUserName());
				ordSevice.setOrderId(Long.valueOf(this.orderId));
				ordSevice.setApplyContent(ordSalePo.getApplyContent());
				ordSevice.setServiceType(ordSalePo.getServiceType());
				ordSevice.setStatus("NORMAL");
				if(StringUtils.isEmpty(sysCode)){
					sysCode=Constant.COMPLAINT_SYS_CODE.SUPER.getCode();
				}
				ordSevice.setSysCode(sysCode);
				Long saleId=ordSaleServiceService.addOrdSaleService(ordSevice);
				if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(sysCode)) {
					//TODO heyuxing 对应于com.lvmama.comm.bee.po.ord.OrdOrder.needSaleService，VST的OrdOrder没有相应字段
				}else {
					this.getOrderServiceProxy().updateNeedSaleService("true", Long.valueOf(orderId), this.getOperatorName());
				}
				if("true".equalsIgnoreCase(isCancelOrder)){
					ordRefundment.setRefundType(Constant.REFUND_TYPE.ORDER_REFUNDED.name());
				}
				
				Constant.APPLY_REFUNDMENT_RESULT applyRes ;
				// 判断是退款单的状态（未审核/退款申请）
				if(Constant.REFUNDMENT_STATUS.UNVERIFIED.toString().equals(refundStatus)){
					// 如果违约金为空，则指定默认值为0
					if(null == ordRefundment.getPenaltyAmount()){
						ordRefundment.setPenaltyAmount(0l);
					}
					if(ordRefundment.getAmount() == null){
						ordRefundment.setAmountYuanStr("0");
					}
					if(ordRefundment.getAmount() < 0){
						alert("退款金额不能小于0");
						return;
					}
					// 以下的3种情况退款单会提交失败(存在未退款的退款单.实付金额0，退款金额过大)
					if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(this.sysCode)) {
						applyRes = ordRefundMentService.applyRefundVst(Long.valueOf(orderId),saleId, vstOrdOrderItemsList, 
								ordRefundment.getAmount(), Constant.REFUNDMENT_TYPE.ORDER_REFUNDED.toString(), refundStatus, 
								ordRefundment.getMemo(), this.getSessionUserName(), 0l);
					}else {
						applyRes = ordRefundMentService.applyRefund(Long.valueOf(orderId),saleId, orderItemMetaList, 
								ordRefundment.getAmount(), Constant.REFUNDMENT_TYPE.ORDER_REFUNDED.toString(), refundStatus, 
								ordRefundment.getMemo(), this.getSessionUserName(), ordRefundment.getPenaltyAmount());	
					}
				}else if(Constant.REFUNDMENT_STATUS.REFUND_APPLY.toString().equals(refundStatus)){//申请退款
					// 以下的3种情况退款单会提交失败(订单取消.实付金额0，已经生成退款单)
					if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(this.sysCode)) {
						applyRes = ordRefundMentService.applyRefundVst(Long.valueOf(orderId),saleId, vstOrdOrderItemsList, 
								0l, Constant.REFUNDMENT_TYPE.ORDER_REFUNDED.toString(), refundStatus, 
								ordRefundment.getMemo(), this.getSessionUserName(), 0l);
					}else {
						applyRes = ordRefundMentService.applyRefund(Long.valueOf(orderId),saleId, orderItemMetaList, 
								0L, Constant.REFUNDMENT_TYPE.ORDER_REFUNDED.toString(), refundStatus, 
								ordRefundment.getMemo(), this.getSessionUserName(), ordRefundment.getPenaltyAmount());	
					}
					if(applyRes.equals(Constant.APPLY_REFUNDMENT_RESULT.APPLY_SUCCESS)){
						Map map = new HashMap();
						for(OrdOrderItemMeta item : orderItemMetaList){
							MetaProduct meta =  metaProductService.getMetaProduct(item.getMetaProductId());
							if(meta != null && meta.getManagerId() != null && map.get(meta.getManagerId())==null){
								PermUser pUser = permUserService.getPermUserByUserId(meta.getManagerId());
								ComMessage comMessage = new ComMessage();		
								comMessage.setReceiver(pUser.getUserName());
								comMessage.setSender(Constant.SYSTEM_USER);
								comMessage.setContent("订单["+orderId+"]申请退款，请及时处理");
								comMessage.setStatus("CREATE");
								comMessage.setCreateTime(new Date());
								comMessageService.insertComMessage(comMessage);
								map.put(meta.getManagerId(), meta.getManagerId());
							}
						}
					}
				} else {
					alert("操作类型错误，提交退款失败");
					return;
				}
				
				if(!applyRes.equals(Constant.APPLY_REFUNDMENT_RESULT.APPLY_SUCCESS)){
					alert(applyRes.getCnName());
					return;
				}
				
				if("true".equalsIgnoreCase(isCancelOrder)){
						if(Constant.REFUND_TYPE.ORDER_REFUNDED.name().equals(ordRefundment.getRefundType())){
							if (isCancelOrder!=null && isCancelOrder.equals("true")){
								// 申请时不废单
								if(!"REFUND_APPLY".equals(refundStatus)){
									boolean flag;
									if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(this.sysCode)) {
										flag = orderServiceProxy.cancelVstOrder(Long.valueOf(orderId), this.cancelResson, getOperatorName());
									}else {
										flag = orderServiceProxy.cancelOrder(Long.valueOf(orderId), this.cancelResson, getOperatorName());	
									}
									
									if(flag){
										OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId));
										sendContractEmailService.sendCancelContractSms(order);
										alert("废单成功");
										super.closeWindow();
									}else{
										alert("废单失败");
									}
								}else{
									alert("申请成功");
									super.closeWindow();
								}
							}
						}else{
							alert("提交成功");
						}
				}else{
						alert("提交成功");
						super.refreshParent("refreshOrdRefundBtn");
						super.refreshParent("refreshOrdSaleBtn");
						super.closeWindow();
				}
			}
	}
	
	
	public OrdSaleServiceService getOrdSaleServiceService() {
		return (OrdSaleServiceService)SpringBeanProxy.getBean("ordSaleServiceService");
	}
	public boolean isenabled() {
		return enabled;
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

	public void setOrdRefundMentService(
			OrdRefundMentService ordRefundMentService) {
		this.ordRefundMentService = ordRefundMentService;
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

	public void setAllOrdOrderItemMetas(
			List<OrdOrderItemMeta> allOrdOrderItemMetas) {
		this.allOrdOrderItemMetas = allOrdOrderItemMetas;
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
	public List<OrdSaleService> getOrdSaleServiceList() {
		return ordSaleServiceList;
	}
	public void setOrdSaleServiceList(List<OrdSaleService> ordSaleServiceList) {
		this.ordSaleServiceList = ordSaleServiceList;
	}
	public String getOrdSaleContext() {
		return ordSaleContext;
	}
	public void setOrdSaleContext(String ordSaleContext) {
		this.ordSaleContext = ordSaleContext;
	}
	
	public Window getWinOrdSaleAdd() {
		return winOrdSaleAdd;
	}
	public void setWinOrdSaleAdd(Window winOrdSaleAdd) {
		this.winOrdSaleAdd = winOrdSaleAdd;
	}
	public OrdSaleService getOrdSalePo() {
		return ordSalePo;
	}
	public void setOrdSalePo(OrdSaleService ordSalePo) {
		this.ordSalePo = ordSalePo;
	}
	public void setOrdSaleServiceService(OrdSaleServiceService ordSaleServiceService) {
		this.ordSaleServiceService = ordSaleServiceService;
	}
	public String getIsCancelOrder() {
		return isCancelOrder;
	}
	public void setIsCancelOrder(String isCancelOrder) {
		this.isCancelOrder = isCancelOrder;
	}
	public String getCancelResson() {
		return cancelResson;
	}
	public void setCancelResson(String cancelResson) {
		try {
			this.cancelResson = new String(cancelResson.getBytes("iso-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public SendContractEmailService getSendContractEmailService() {
		return sendContractEmailService;
	}

	public void setSendContractEmailService(
			SendContractEmailService sendContractEmailService) {
		this.sendContractEmailService = sendContractEmailService;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
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
//		//设置订单来源渠道
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

	public VstDistributorService getVstDistributorService() {
		return vstDistributorService;
	}

	public void setVstDistributorService(VstDistributorService vstDistributorService) {
		this.vstDistributorService = vstDistributorService;
	}

}
