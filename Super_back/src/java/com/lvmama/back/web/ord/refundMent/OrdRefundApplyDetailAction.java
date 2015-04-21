package com.lvmama.back.web.ord.refundMent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.pet.po.pub.ComMessage;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.COM_LOG_CASH_EVENT;
import com.lvmama.comm.vst.service.VstOrdOrderService;

/**
 * 退款申请明细
 * 
 * @author zhangwenjun
 */
@SuppressWarnings("unused")
public class OrdRefundApplyDetailAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 订单编号.
	 */
	private String orderId;
	
	/**
	 * 退款单ID
	 */
	private Long refundmentId;
	/**
	 * 退款单类型
	 */
	private String refundType;
	/**
	 * 订单子子项ID
	 */
	private Long orderItemMetaId;

	/**
	 * 采购产品明细集合 
	 */
	private List<OrdOrderItemMeta> ordOrderItemMetaList;

	/**
	 * listbox组件
	 */
	private Listbox ordItemMetaListbox;

	private ComMessageService comMessageService;
	
	private ComLogService comLogService;

	/**
	 *  选中的采购产品
	 */
	private List<OrdOrderItemMeta> orderItemMetaList = new ArrayList();
	
	/**
	 * 新系统订单服务
	 */
	private VstOrdOrderService vstOrdOrderService;
	
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

	public boolean setOrderItemMetaList(List<OrdOrderItemMeta> orderItemMetaList) {
		// 获取listbox中的所有数据
		List set = ordItemMetaListbox.getItems();
		
		// 循环listbox，当listbox中的某一行数据的订单子子项id与当前操作行的订单子子项id匹配时，保存这一行数据
		for ( Object tmp: set ) {
			OrdOrderItemMeta orderItemMeta = new OrdOrderItemMeta();
			Listitem listItem = (Listitem)tmp;
			List listCells = listItem.getChildren();
			Listcell cell0 = (Listcell)listCells.get(0);
			// 订单子子项ID
			Label label = (Label)cell0.getChildren().get(1);
			
			// 判断这一行的订单子子项ID 是否与传过来的相等
			if(orderItemMetaId == Long.parseLong(label.getValue())){
				orderItemMeta.setOrderItemMetaId(Long.parseLong(label.getValue()));
				// 退款单ID
				Label refundmentId = (Label)cell0.getChildren().get(2);
				orderItemMeta.setRefundmentId(Long.parseLong(refundmentId.getValue()));

				// 金额类型（游客损失/供应商承担金额）
				Listcell cell5 = (Listcell)listCells.get(5);
				Listbox listbox5 = (Listbox)cell5.getChildren().get(0);
				if(null != listbox5.getSelectedItem()){
					orderItemMeta.setAmountType((String) listbox5.getSelectedItem().getValue());
				} else{
					alert("金额类型不能为空");
					return false;
				}
				
				// 金额（游客损失/供应商承担金额）
				Textbox textbox5 = (Textbox)cell5.getChildren().get(1);
				Label label2 = (Label)cell5.getChildren().get(4);
				Label label3 = (Label)cell5.getChildren().get(5);
				Label label4 = (Label)cell5.getChildren().get(6);
				Long compareValue = Long.parseLong(label2.getValue()) * Long.parseLong(label3.getValue()) * Long.parseLong(label4.getValue());
				if(null != textbox5.getValue()){
					if(!textbox5.getValue().equals("")){
						if(PriceUtil.convertToFen(textbox5.getValue().toString()) < 0){
							alert("游客损失或供应商承担金额不能小于0");
							return false;
						}
						if(PriceUtil.convertToFen(textbox5.getValue().toString()) > compareValue){
							alert("游客损失或供应商承担金额过大，请重新输入");
							return false;
						}
						orderItemMeta.setAmountValueYuan(textbox5.getValue().toString());
					}
				} else{
					alert("游客损失或供应商承担金额不能为空");
					return false;
				}
				
				// 实际损失
				Textbox doublebox = (Textbox)cell5.getChildren().get(3);
				if(null != doublebox.getValue()){
					if(!doublebox.getValue().equals("")){
						if(PriceUtil.convertToFen(doublebox.getValue().toString()) < 0){
							alert("实际损失金额不能小于0");
							return false;
						}
						if(PriceUtil.convertToFen(doublebox.getValue().toString()) > compareValue){
							alert("实际损失金额过大，请重新输入");
							return false;
						}
						orderItemMeta.setActualLossYuan(doublebox.getValue().toString());
					}
				} else{
					if(!listbox5.getSelectedItem().getValue().equals("SUPPLIER_BEAR")){
						alert("实际损失不能为空");
						return false;
					}
				}

				// 备注
				Listcell cell6 = (Listcell)listCells.get(6);
				Textbox listbox6 = (Textbox)cell6.getChildren().get(0);
				if(null != listbox6.getValue()){
					orderItemMeta.setMemo(listbox6.getValue());
				}
				
				orderItemMetaList.add(orderItemMeta);
			}
		}
		return true;
	}
	
	/**
	 * 初始化
	 */
	@SuppressWarnings("unchecked")
	public void doBefore() {
		if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(this.sysCode)) {
			ordOrderItemMetaList = this.getOrdRefundMentService().queryVstOrdOrderItemMetaList(refundmentId);
		}else {
			ordOrderItemMetaList = this.getOrdRefundMentService().queryOrdOrderItemMetaList(refundmentId);	
		}
	}
	
	/**
	 * 增加退款认务.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void doRefundMent(Map param) {
		if (orderId != null) {
			// 获取页面传递的参数
			orderItemMetaId = Long.parseLong(param.get("orderItemMetaId").toString());
			orderItemMetaList = new ArrayList();
			boolean flag = this.setOrderItemMetaList(orderItemMetaList);
			if(!flag){
				return;
			}

			/**
			 *  增加/修改退款单明细
			 */
			if (UtilityTool.isValid(orderItemMetaList) && orderItemMetaList.size() > 0) {
				for (int i=0; i<orderItemMetaList.size();i++) {
					OrdOrderItemMeta ordOrderItemMeta = (OrdOrderItemMeta)orderItemMetaList.get(i);
					List<OrdOrderItemMeta> itemMetaList = null;
					if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(this.sysCode)) {
						itemMetaList = this.getOrdRefundMentService().queryVstOrdOrderItemMetaList(refundmentId);
					}else {
						itemMetaList = this.getOrdRefundMentService().queryOrdOrderItemMetaList(refundmentId);	
					}
					if(itemMetaList.size() > 0){
						for(int k=0; k<itemMetaList.size(); k++){
							OrdOrderItemMeta ordOrderItemMeta1 = (OrdOrderItemMeta)itemMetaList.get(k);
							if(ordOrderItemMeta1.getOrderItemMetaId().equals(orderItemMetaId)){
								Map paramMap = new HashMap();
								paramMap.put("refundmentId", refundmentId);
								paramMap.put("orderItemMetaId", ordOrderItemMeta.getOrderItemMetaId());
								paramMap.put("type", ordOrderItemMeta.getAmountType());
								paramMap.put("amount", ordOrderItemMeta.getAmountValue());
								paramMap.put("memo", ordOrderItemMeta.getMemo());
								paramMap.put("actualLoss", ordOrderItemMeta.getActualLoss());
								// 修改
								this.getOrdRefundMentService().updateOrdRefundmentItem(paramMap);

								String typeStr;
								String actualLossStr = "";
								if(ordOrderItemMeta.getAmountType().equals("SUPPLIER_BEAR")){
									typeStr = "供应商承担金额";
								}else{
									typeStr = "游客损失";
									actualLossStr = "，实际损失为：" + ordOrderItemMeta.getActualLossYuan();
								}
								this.getOrdRefundMentService().insertLog("ORD_REFUNDMENT_ITEM", refundmentId, ordOrderItemMeta1.getRefundmentItemId(),
										this.getSessionUser().getUserName(), COM_LOG_CASH_EVENT.insertOrderRefundment.name(),
										"修改退款明细:" + typeStr + "为："+ordOrderItemMeta.getAmountValueYuan() + actualLossStr, 
										"修改退款明细:" + typeStr + "为："+ordOrderItemMeta.getAmountValueYuan() + actualLossStr);
							}
						}
					}
					
				}
			}
			
			/**
			 * 判断此次确认的明细项是否为最后一项，为最后 一项时，需要改变退款单的状态为“已确认”
			 */
			List<OrdOrderItemMeta> itemMetaList1 = null;
			if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(this.sysCode)) {
				itemMetaList1 = this.getOrdRefundMentService().queryVstOrdOrderItemMetaList(refundmentId);
			}else {
				itemMetaList1 = this.getOrdRefundMentService().queryOrdOrderItemMetaList(refundmentId);	
			}
			if(itemMetaList1.size() > 0){
				for(int j=0; j<itemMetaList1.size(); j++){
					OrdOrderItemMeta ordOrderItemMeta = (OrdOrderItemMeta)itemMetaList1.get(j);
					if(null == ordOrderItemMeta.getAmountValue() || "".equals(ordOrderItemMeta.getAmountValue())){
						return;
					}
				}
				OrdRefundment ref =	this.getOrdRefundMentService().findOrdRefundmentById(refundmentId);
				ComMessage comMessage = new ComMessage();		
				comMessage.setReceiver(ref.getOperatorName());
				comMessage.setSender(Constant.SYSTEM_USER);
				comMessage.setContent("订单["+orderId+"]退款已确认，请及时处理");
				comMessage.setStatus("CREATE");
				comMessage.setCreateTime(new Date());
				comMessageService.insertComMessage(comMessage);
				/**
				 *  修改退款单状态
				 */
				boolean result = this.getOrdRefundMentService().updateRefundStatus(refundmentId, Constant.REFUNDMENT_STATUS.APPLY_CONFIRM.name());
				
				/**
				 *  添加日志
				 */
				this.getOrdRefundMentService().insertLog("ORD_REFUNDMENT", Long.parseLong(orderId), refundmentId,
						this.getSessionUser().getUserName(), COM_LOG_CASH_EVENT.updateOrderRefundment.name(),
						"修改退款单状态为“已确认”", "修改退款单状态为“已确认”");
			}

			/**
			 *  更新父窗口的列表数据
			 */
			this.refreshParent("search");
			/**
			 * 关闭当前窗口
			 */
			this.closeWin();
		}
	}
	
//	/**
//	 * 取消退款单
//	 * @param refundmentId
//	 */
//	@SuppressWarnings("unchecked")
//	public void cancelRefundment(String refundmentId){
//		Long id = Long.parseLong(refundmentId);
//		/**
//		 *  修改退款单状态
//		 */
//		boolean result = this.getOrdRefundMentService().updateRefundStatus(id, Constant.REFUNDMENT_STATUS.CANCEL.name());
//		/**
//		 *  更新父窗口的列表数据
//		 */
//		this.refreshParent("search");
//		/**
//		 * 关闭当前窗口
//		 */
//		this.closeWin();
//	}

	public OrdRefundMentService getOrdRefundMentService() {
		return (OrdRefundMentService) SpringBeanProxy.getBean("ordRefundMentService");
	}
	
	public void updateOrdRefundSuccess(Long refundmentId){
		Map map = new HashMap();
		map.put("refundmentId", refundmentId);
		List ordRefundmentList = this.getOrdRefundMentService().findOrdRefundByParam(map, 0, 1);
		if(ordRefundmentList.size()>0){
			OrdRefundment ordrefundment=(OrdRefundment)ordRefundmentList.get(0);
			if(Constant.REFUNDMENT_STATUS.REFUND_VERIFIED.toString().equals(ordrefundment.getStatus())){
				alert("该退款单已经审核通过了!");
				return;
			}else if(Constant.REFUNDMENT_STATUS.UNVERIFIED.toString().equals(ordrefundment.getStatus())){
				ordrefundment.setApproveTime(new Date());
				ordrefundment.setStatus(Constant.REFUNDMENT_STATUS.REFUND_VERIFIED.toString());
				this.getOrdRefundMentService().updateOrdRefundmentByPK(ordrefundment);
				comLogService.insert("ORD_REFUNDMENT", ordrefundment.getOrderId(), refundmentId, 
						super.getOperatorName(), Constant.COM_LOG_CASH_EVENT.updateOrderRefundment.name(), 
						"退款单审核通过", "退款单审核通过", "ORD_ORDER");
				alert("该退款单已经成功审核通过!");
				return;
			}else{
				alert("不能被修改的状态!");
				return;
			}
		}
	}


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public Long getRefundmentId() {
		return refundmentId;
	}

	public void setRefundmentId(Long refundmentId) {
		this.refundmentId = refundmentId;
	}

	public OrdSaleServiceService getOrdSaleServiceService() {
		return (OrdSaleServiceService) SpringBeanProxy
				.getBean("ordSaleServiceService");
	}
	public List<OrdOrderItemMeta> getOrdOrderItemMetaList() {
		return ordOrderItemMetaList;
	}

	public void setOrdOrderItemMetaList(List<OrdOrderItemMeta> ordOrderItemMetaList) {
		this.ordOrderItemMetaList = ordOrderItemMetaList;
	}

	public Long getOrderItemMetaId() {
		return orderItemMetaId;
	}

	public void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}

	public Listbox getOrdItemMetaListbox() {
		return ordItemMetaListbox;
	}

	public void setOrdItemMetaListbox(Listbox ordItemMetaListbox) {
		this.ordItemMetaListbox = ordItemMetaListbox;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public void setVstOrdOrderService(VstOrdOrderService vstOrdOrderService) {
		this.vstOrdOrderService = vstOrdOrderService;
	}
	
}
