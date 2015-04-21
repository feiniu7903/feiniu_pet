package com.lvmama.back.web.ord.refundMent;

import com.lvmama.back.utils.StringUtil;
import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.ord.*;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.ComMessage;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceServiceDeal;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vst.service.VstDistributorService;
import com.lvmama.comm.vst.service.VstOrdOrderService;
import com.lvmama.comm.vst.vo.VstOrdOrderItem;
import com.lvmama.comm.vst.vo.VstOrdOrderVo;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.*;

import java.math.BigDecimal;
import java.util.*;


/**
 * 订单退款增加对像类.
 * 
 * @author huangl
 */
public class OrdRefundAddAction extends ordTreeAddAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 退款服务对像.
	 */
	private OrdRefundMentService ordRefundMentService;
	private ComMessageService comMessageService;
	private PermUserService permUserService;
	private MetaProductService metaProductService;
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
	 * 订单服务类型.
	 */
	private String serviceType;
    /**
     * 窗口类型，jsp或zk
     */
	private String windowType;

	private String cancelResson;

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

	public VstDistributorService getVstDistributorService() {
		return vstDistributorService;
	}

	public void setVstDistributorService(VstDistributorService vstDistributorService) {
		this.vstDistributorService = vstDistributorService;
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
		//如果是补偿单，当不选择明细的时候，直接返回true
		if(set.size() == 0 && !ordRefundment.getRefundType().equals("ORDER_REFUNDED") && !"REFUND_APPLY".equals(refundStatus)){
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

			/**
			 *  不为退款申请时，需要校验明细项，退款申请时不需要校验，只要选中明细即可
			 */
			if(!"REFUND_APPLY".equals(refundStatus)){
				// 金额类型（游客损失/供应商承担金额）
				Listcell cell3 = (Listcell)listCells.get(3);
				Listbox listbox3 = (Listbox)cell3.getChildren().get(0);
				if(null != listbox3.getSelectedItem()){
					
					// 退款申请时不需要填写退款金额和违约金
					if(!ordRefundment.getRefundType().equals("ORDER_REFUNDED") && listbox3.getSelectedItem().getValue().toString().equals("VISITOR_LOSS")){
						alert("补偿单的金额类型不能为游客损失");
						return false;
					}else{
						orderItemMeta.setAmountType((String) listbox3.getSelectedItem().getValue());
					}
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
						// 如果金额类型为游客损失，则将实际损失等于游客损失
						if(orderItemMeta.getAmountType().equals("VISITOR_LOSS")){
							orderItemMeta.setActualLoss(PriceUtil.convertToFen(textbox3.getValue().toString()));
						}
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
			}
			
			
			orderItemMetaList.add(orderItemMeta);
		}
		if(orderItemMetaList.size()==0 && ordRefundment.getRefundType().equals("ORDER_REFUNDED")){
			alert("【请选择订单进行退款】!");
			return false;
		}
		if(orderItemMetaList.size()==0 && !ordRefundment.getRefundType().equals("ORDER_REFUNDED") && "REFUND_APPLY".equals(refundStatus)){
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
		//如果是补偿单，当不选择明细的时候，直接返回true
		if(set.size() == 0 && !ordRefundment.getRefundType().equals("ORDER_REFUNDED") && !"REFUND_APPLY".equals(refundStatus)){
			return true;
		}
		
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
			 *  不为退款申请时，需要校验明细项，退款申请时不需要校验，只要选中明细即可
			 */
			if(!"REFUND_APPLY".equals(refundStatus)){
				// 金额类型（游客损失/供应商承担金额）
				Listcell cell3 = (Listcell)listCells.get(3);
				Listbox listbox3 = (Listbox)cell3.getChildren().get(0);
				if(null != listbox3.getSelectedItem()){
					// 退款申请时不需要填写退款金额和违约金
					if(!ordRefundment.getRefundType().equals("ORDER_REFUNDED") && listbox3.getSelectedItem().getValue().toString().equals("VISITOR_LOSS")){
						alert("补偿单的金额类型不能为游客损失");
						return false;
					}else{
						vstOrderItem.setAmountType((String) listbox3.getSelectedItem().getValue());
					}
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

				// 备注
				Listcell cell4 = (Listcell)listCells.get(4);
				Textbox listbox4 = (Textbox)cell4.getChildren().get(0);
				if(null != listbox4.getValue()){
					vstOrderItem.setMemo(listbox4.getValue());
				}
			}
			
			
			vstOrdOrderItemsList.add(vstOrderItem);
		}
		if(vstOrdOrderItemsList.size()==0 && ordRefundment.getRefundType().equals("ORDER_REFUNDED")){
			alert("【请选择订单进行退款】!");
			return false;
		}
		if(vstOrdOrderItemsList.size()==0 && !ordRefundment.getRefundType().equals("ORDER_REFUNDED") && "REFUND_APPLY".equals(refundStatus)){
			alert("【请选择订单进行退款】!");
			return false;                                                            
		}

		return true;
	}


	private ComLogService comLogService;
	
	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
	
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
	
	/**
	 * 查询页面基本显示信息.
	 * 
	 * @return
	 */
	public void doBefore() {
		if (orderId != null) {
			if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(this.sysCode)) {
				VstOrdOrderVo vstOrdOrderVo = vstOrdOrderService.getVstOrdOrderVo(new Long(orderId));
				this.setVstOrdOrderVoDetail(vstOrdOrderVo);
				UserUser userUser=userUserProxy.getUserUserByUserNo(vstOrdOrderVo.getUserId());
				long refundedAmount = this.getOrderServiceProxy().getRefundAmountByOrderId(Long.valueOf(orderId), this.sysCode);
				this.historyOrderDetail = new OrdOrder().setProp(vstOrdOrderVo, userUser, refundedAmount);
			}else {
				this.historyOrderDetail = this.getOrderServiceProxy().queryOrdOrderByOrderIdRefund(0L,new Long(orderId));	
			}

			// 判断是否显示【转投诉】按钮
			if(serviceType.equals("NORMAL")){// 常规售后
				Map map = new HashMap();
				map.put("orderId", orderId);
				// 查询该订单下的所有退款单
				List<OrdRefundment> refundmentList = ordRefundMentService.queryRefundmentByOrderId(map);
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
	}

	/**
	 * 退款综合查询.
	 * 
	 * @return
	 */
	public void ordRefundMentQuery() {
		ordRefundmentList = ordRefundMentService.findOrdRefundByParam(
				searchRefundMent, 0, 1);
	}
	
	/**
	 * 计算退款金额
	 */
	public Double computeAmount(String refundType){
		// 订单实付金额
		Long actualPay = historyOrderDetail.getActualPay();
		// 如果是补偿单，退款金额为订单实付金额
		if(refundType.equals("COMPENSATION")){
			ordRefundment.setAmount(actualPay);
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
	

	boolean flagStatus=false;

	/**
	 * 增加退款认务.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void addRefundMent(final String refundStatus) {
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
				if(ordRefundment.getRefundType() == null || ordRefundment.getRefundType().equals("")){
					alert("请填写退款单中的退款类型!");
					return;
				}
				if (ordRefundment.getMemo()==null) {
					alert("退款备注不能为空!");
					return;
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
				
				Constant.APPLY_REFUNDMENT_RESULT applyRes ;
				//提交退款
				if(Constant.REFUNDMENT_STATUS.REFUND_VERIFIED.toString().equals(refundStatus)){
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
						applyRes = ordRefundMentService.applyRefundVst(Long.valueOf(orderId), Long.valueOf(saleServiceId), vstOrdOrderItemsList, 
								ordRefundment.getAmount(), ordRefundment.getRefundType(), Constant.REFUNDMENT_STATUS.REFUND_VERIFIED.name(), 
								ordRefundment.getMemo(), this.getSessionUserName(), 0l);
					}else {
						applyRes = ordRefundMentService.applyRefund(Long.valueOf(orderId), Long.valueOf(saleServiceId), orderItemMetaList, 
								ordRefundment.getAmount(), ordRefundment.getRefundType(), Constant.REFUNDMENT_STATUS.REFUND_VERIFIED.name(), 
								ordRefundment.getMemo(), this.getSessionUserName(), ordRefundment.getPenaltyAmount());
					}
				}else if(Constant.REFUNDMENT_STATUS.REFUND_APPLY.toString().equals(refundStatus)){//申请退款
					// 以下的3种情况退款单会提交失败(订单取消.实付金额0，已经生成退款单)
					if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(this.sysCode)) {
						applyRes = ordRefundMentService.applyRefundVst(Long.valueOf(orderId), Long.valueOf(saleServiceId), vstOrdOrderItemsList, 
								0L, ordRefundment.getRefundType(), Constant.REFUNDMENT_STATUS.REFUND_APPLY.name(), 
								ordRefundment.getMemo(), this.getSessionUserName(), 0l);
					}else {
						applyRes = ordRefundMentService.applyRefund(Long.valueOf(orderId), Long.valueOf(saleServiceId), orderItemMetaList, 
								0L, ordRefundment.getRefundType(), Constant.REFUNDMENT_STATUS.REFUND_APPLY.name(), 
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
				
				//当退款单为第一笔退款单时，询问是否需要废单
				Map<String,Object> p = new HashMap<String,Object>();
				p.put("orderId", orderId);
				p.put("sysCode", sysCode);
				if (applyRes.equals(Constant.APPLY_REFUNDMENT_RESULT.APPLY_SUCCESS) //1.退款单提交成
						&&	Constant.REFUNDMENT_TYPE.ORDER_REFUNDED.name().equalsIgnoreCase(ordRefundment.getRefundType())//2.退款类型为退款单
						&&  getOrdRefundMentService().findOrdRefundByParamCount(p).longValue() == 1L  ) {//3.退款单为第一笔退款
					flagStatus=true;
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
				if(!"NcComplaint".equals(windowType)){
                    // 更新父窗口的列表数据
                    this.refreshParent("search");
                }else{
                	if(!flagStatus){
	                    //新投诉关闭窗口的方法
	                    org.zkoss.zul.api.Button b = (org.zkoss.zul.api.Button)getComponent().getFellow("closeDialog");
	                    Events.sendEvent(new Event("onClick", b));
                	}
                }
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
			this.refreshAndCloseParent("search");
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

	public OrdRefundMentService getOrdRefundMentService() {
		return (OrdRefundMentService) SpringBeanProxy
				.getBean("ordRefundMentService");
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

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

	/**
	 * @param cancelResson the cancelResson to set
	 */
	public void setCancelResson(String cancelResson) {
		this.cancelResson = cancelResson;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

    public String getWindowType() {
        return windowType;
    }

    public void setWindowType(String windowType) {
        this.windowType = windowType;
    }

	public boolean isFlagStatus() {
		return flagStatus;
	}

	public void setFlagStatus(boolean flagStatus) {
		this.flagStatus = flagStatus;
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
