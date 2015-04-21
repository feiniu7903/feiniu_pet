package com.lvmama.comm.pet.po.ord;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.ord.OrdEContract;
import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.vo.Constant;

/**
 * 公用订单头
 * @author yuzhibing
 *
 */
public class OrdOrderHead implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1927487516619032485L;
	private Long orderId;
	private String bizType;
	private String orderType;
	private Long userId;

	private String paymentStatus;
	private String approveStatus;
	private String settlementStatus;
	
	/**
	 * 合同状态.
	 */
	private String contractStatus;
	/**
	 * 合同.
	 */
	private OrdEContract orderEContract = new OrdEContract();
	
	/**
	 * 用户.
	 */
	private UserUser user;
	
	/**
	 * 发票.
	 */
	private List<OrdInvoice> invoiceList;
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
    public OrdEContract getOrderEContract() {
		return orderEContract;
	}
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	public String getSettlementStatus() {
		return settlementStatus;
	}
	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}
	/**
     * 获取订单合同.
     * @param ordEContract
     */
    public void setOrderEContract(OrdEContract orderEContract) {
		this.orderEContract = orderEContract;
	}
	/**
	 * 设置订单合同.
	 * @return
	 */
	public String getContractStatus() {
		if(StringUtils.isNotEmpty(orderEContract.getEcontractStatus())){
			return orderEContract.getEcontractStatus();
		}else{
			return contractStatus;
		}
	}
	
	/**
	 * 加载用户.
	 * 
	 * @return user 用户
	 */
	public UserUser getUser() {
		return user;
	}

	/**
	 * 设置用户.
	 * 
	 * @param user
	 *            用户
	 */
	public void setUser(UserUser user) {
		this.user = user;
	}
	

	public List<OrdInvoice> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(final List<OrdInvoice> invoiceList) {
		this.invoiceList = invoiceList;
	}
	/**
	 * 判断支付状态,是否全额支付.
	 * 
	 * @return
	 */
	public boolean isPaymentSucc() {
		return Constant.PAYMENT_STATUS.PAYED.name().equalsIgnoreCase(paymentStatus);
	}
	/**
	 * 是否能被支付
	 * @return
	 */
	public boolean isCanToPay() {
		if (isPaymentSucc()) {
			return false;
		}else{
			return true;
		}
	}
	
}
