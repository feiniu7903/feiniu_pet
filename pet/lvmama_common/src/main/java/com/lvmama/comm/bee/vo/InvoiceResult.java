/**
 * 
 */
package com.lvmama.comm.bee.vo;

import java.io.Serializable;

/**
 * 因为发票的状态多种，使用该操作来做返回值.
 * 发票状态更改用的返回值.
 * @author yangbin
 *
 */
public class InvoiceResult implements Serializable{

	enum Status{
		CANCEL,SUCCESS,ERROR;
	}
	
	public static class CancelException extends Exception{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1857254006075886035L;
		private Long orderId;
		private boolean updateNeedInvoice=false;

		public CancelException(Long orderId) {
			super();
			this.orderId = orderId;
		}
		
		

		public CancelException(Long orderId, boolean updateNeedInvoice) {
			super();
			this.orderId = orderId;
			this.updateNeedInvoice = updateNeedInvoice;
		}



		public boolean isUpdateNeedInvoice() {
			return updateNeedInvoice;
		}



		/**
		 * @return the orderId
		 */
		public Long getOrderId() {
			return orderId;
		}
		
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7972874257616698421L;
	
	private Status status=Status.SUCCESS;
	private String msg;

	public void rasieCancel(CancelException ex){
		status=Status.CANCEL;
		msg="订单ID:"+ex.getOrderId()+" 可开发票金额少于1自动废票";
	}
	
	public void rasieError(Exception ex){
		status=Status.ERROR;
		msg=ex.getMessage();
	}
	
	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}


	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}


	public boolean isError(){
		return status==Status.ERROR;
	}

	public boolean isSuccess(){
		return status==Status.SUCCESS;
	}
	public boolean isCancel(){
		return status==Status.CANCEL;
	}

}