package com.lvmama.comm.bee.vo.ord;

import com.lvmama.comm.bee.po.ord.OrdEContract;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class OrderEcontractModel{
	private Long id;
	private OrdOrder ordOrder;// 订单信息
	private OrdEContract ordEContract;
	private OrdPerson contact;// 联系人信息
	private String signMode;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public OrdOrder getOrdOrder() {
		return ordOrder;
	}
	public void setOrdOrder(OrdOrder ordOrder) {
		this.ordOrder = ordOrder;
	}
	public OrdEContract getOrdEContract() {
		return ordEContract;
	}
	public void setOrdEContract(OrdEContract ordEContract) {
		this.ordEContract = ordEContract;
	}
	public OrdPerson getContact() {
		return contact;
	}
	public void setContact(OrdPerson contact) {
		this.contact = contact;
	}
	public boolean isSend(){
		if(null!=contact && null!=contact.getEmail() && ordOrder.isEContractConfirmed() && !ordOrder.isCanceled()){
			return false;
		}
		return true;
	}
	public String getSendStatus(){
		String send = "";
		if(null!=contact && null!=contact.getEmail() && ordOrder.isEContractConfirmed() && ordOrder.isNeedEContract()){
			send +="已发送";
		}else{
			send +="未发送";
		}
		if(null!=ordOrder && null!=ordOrder.getContact() && !StringUtil.isEmptyString(ordOrder.getContact().getMobile()) && ordOrder.isEContractConfirmed() && ordOrder.isNeedEContract()){
			send +="/已发送";
		}else{
			send +="/未发送";
		}
		return send;
	}
	public String getConfirmed(){
		if(null!=ordOrder){
			if(!ordOrder.isEContractConfirmed()){
				return "未签约";
			}else if(ordOrder.isCanceled()){
				return "已作废";
			}else if(!ordOrder.isPaymentSucc() || !ordOrder.isApprovePass()){
				return "已签约未生效";
			}else if(ordOrder.isPaymentSucc()){
				return "已生效";
			}
		}
		return "未签约";
	}
	public boolean getUnConfirm(){
		if(null!=ordOrder && ordOrder.isEContractConfirmed()){
			return true;
		}
		/**
		 * 未生成合同的订单不能签约
		 */
		if(null==ordEContract || null!=ordEContract && null==ordEContract.getEcontractId()){
			return true;
		}
		return false;
	}
	public boolean getUnUpdated(){
		if(null== ordOrder){
			return Boolean.TRUE;
		}else if(null==ordEContract || null!=ordEContract && null==ordEContract.getEcontractId()){
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;
		}
	}
	public boolean isWriteComment(){
		if(null!=ordEContract && null!=ordEContract.getEcontractId()){
			return false;
		}
		return true;
	}
	public boolean getUpdateContent(){
		if(null!=ordEContract && null!=ordEContract.getContentFileId()){
			return false;
		}
		return true;
	}
	/**
	 * 签约方式
	 */
	public String getSignMode() {
		return signMode;
	}
	/**
	 * 签约方式
	 */
	public String getZhSignMode() {
		if(StringUtil.isEmptyString(signMode)){
			return "";
		}
		return Constant.ECONTRACT_SIGN_TYPE.getCnName(signMode);
	}
	public void setSignMode(String signMode) {
		this.signMode = signMode;
	}
	 
}
