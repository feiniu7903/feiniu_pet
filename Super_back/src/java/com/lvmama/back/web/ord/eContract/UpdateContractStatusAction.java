package com.lvmama.back.web.ord.eContract;
/**
 * @description 修改签约状态，记录签约方式
 * @author shangzhengyuan
 * @version 在线预售权
 * @time 20120727
 */
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdEcontractSignLog;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.service.econtract.OrdEContractService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class UpdateContractStatusAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2760294502361678361L;
	
	private static final Logger LOG	= Logger.getLogger(UpdateContractStatusAction.class);
	
	private TopicMessageProducer orderMessageProducer;
	
	private String econtractId;
	/**
	 * 电子合同编码
	 */
	private String econtractNo;
	
	/**
	 * 订单编码
	 */
	private Long orderId;
	
	private String signMode;
	
	/**
	 * 签约信息
	 */
	private OrdEcontractSignLog signLog = new OrdEcontractSignLog();
	List<CodeItem> signModeList = new ArrayList<CodeItem>();
	
	final OrderService orderServiceProxy = (OrderService)SpringBeanProxy.getBean("orderServiceProxy"); 
	private OrdEContractService ordEContractService;
	protected void doBefore() throws Exception {
		setSignModeList();
		signLog.setEcontractNo(econtractNo);
	}
	
	public void signContract()throws Exception{

		String  operatorName=super.getOperatorName();
		signLog.setSignUser(operatorName);
		if(validate()){
			if (econtractId != null) { // 修改合同状态
				boolean isSign = orderServiceProxy.updateOrdEContractStatusToConfirmed(orderId);
				if(!isSign){
					LOG.info("后台操作修改签约方式，订单"+orderId+" 在订单表中没能修改签约状态");
				}
				 isSign = ordEContractService.signContract(orderId, signLog);
				if(isSign){
					//发送邮件及短信
					OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
					if(StringUtil.isEmptyString(signMode) && !order.isCanceled() && order.isEContractConfirmed() && order.isNeedEContract()){
						orderMessageProducer.sendMsg(MessageFactory.newOrderSendEContract(orderId));
					}
					alert("签约成功");
				}else{
					alert("签约失败");
				}
				refreshParent("search");
				closeWindow();
			}
		}
		
	}
	
	public void setSignModeList(){
		Constant.ECONTRACT_SIGN_TYPE[]  signModeArray=Constant.ECONTRACT_SIGN_TYPE.values();
		for(Constant.ECONTRACT_SIGN_TYPE item:signModeArray){
			CodeItem codeItem = new CodeItem(item.getCode(),item.getCnName());
			if(!StringUtil.isEmptyString(signMode) && signMode.equals(item.getCode())){
				codeItem.setChecked(Boolean.TRUE.toString());
			}
			signModeList.add(codeItem);
		}
	}
	public List<CodeItem> getSignModeList(){
		return this.signModeList;
	}
	/**
	 * 修改合同状态
	 * 
	 * @param channel
	 */
	private boolean validate() throws Exception{
		if(null == signLog){
			alert("签约信息为空");
			return Boolean.FALSE;
		}
		if(StringUtil.isEmptyString(econtractNo)){
			alert("订单还没有生成合同，不能签约");
			return Boolean.FALSE;
		}
		if(StringUtil.isEmptyString(signLog.getSignMode())){
			alert("请选择签约方式");
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	public String getEcontractId() {
		return econtractId;
	}

	public void setEcontractId(String econtractId) {
		this.econtractId = econtractId;
	}

	public String getEcontractNo() {
		return econtractNo;
	}

	public void setEcontractNo(String econtractNo) {
		this.econtractNo = econtractNo;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public OrdEcontractSignLog getSignLog() {
		return signLog;
	}

	public void setSignLog(OrdEcontractSignLog signLog) {
		this.signLog = signLog;
	}

	public void setSignModeList(List<CodeItem> signModeList) {
		this.signModeList = signModeList;
	}

	public TopicMessageProducer getOrderMessageProducer() {
		return orderMessageProducer;
	}

	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	public String getSignMode() {
		return signMode;
	}

	public void setSignMode(String signMode) {
		this.signMode = signMode;
	}

	public void setOrdEContractService(OrdEContractService ordEContractService) {
		this.ordEContractService = ordEContractService;
	}
 
}
