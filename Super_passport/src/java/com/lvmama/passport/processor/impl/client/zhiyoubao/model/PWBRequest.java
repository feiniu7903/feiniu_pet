package com.lvmama.passport.processor.impl.client.zhiyoubao.model;
 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PWBRequest {
	private static final Log log = LogFactory.getLog(PWBRequest.class);
	private String transactionName;//交易
	private Header header;
	private IdentityInfo identityInfo;//身份信息
	private OrderRequest orderRequest;//订单请求
	
	public PWBRequest(
			String transactionName,Header header,IdentityInfo identityInfo,OrderRequest orderRequest) {
		this.transactionName = transactionName;
		this.header = header;
		this.identityInfo = identityInfo;
		this.orderRequest = orderRequest;
	}
	public PWBRequest() {}
	/**
	 * 	下订单 xmlMsg
	 * @return
	 */
		public String toSendCodeRequestXml(){
	    	StringBuilder sbi=new StringBuilder();
			sbi.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
			.append("<PWBRequest>")
			.append("<transactionName>SEND_CODE_REQ</transactionName>")
			.append(this.header.toRequestHeaderXml())
			.append(this.identityInfo.toidentityInfoXml())
			.append(this.orderRequest.toorderRequestXml())
			.append("</PWBRequest>");
			 log.info("++++++++++++++++++++++++ Applay Code PWBRequest Xml:"+sbi.toString());
	    	return sbi.toString();
		}
		/**
		 * 取消订单
		 * @return
		 */
		public String toSendCodeCancelRequestXml(){
	    	StringBuilder sbi=new StringBuilder();
	    	sbi.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
			.append("<PWBRequest>")
			.append("<transactionName>SEND_CODE_CANCEL_REQ</transactionName>")
			.append(this.header.toRequestHeaderXml())
			.append(this.identityInfo.toidentityInfoXml())
			.append(this.orderRequest.toOrderRequestUpdateXml())
			.append("</PWBRequest>");
			 log.info("++++++++++++++++++++++++ Destroy Code Request Xml:"+sbi.toString());
	    	return sbi.toString();
		}
		
		/**
		 * 发码图片查询
		 * @return
		 */
		public String toSendCodeImgRequestXml(){
			StringBuilder sbi=new StringBuilder();
	    	sbi.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
			.append("<PWBRequest>")
			.append("<transactionName>SEND_CODE_IMG_REQ</transactionName>")
			.append(this.header.toRequestHeaderXml())
			.append(this.identityInfo.toidentityInfoXml())
			.append(this.orderRequest.toOrderRequestUpdateXml())
			.append("</PWBRequest>");
			 log.info("++++++++++++++++++++++++ SendCodeImgRequest Xml:"+sbi.toString());
	    	return sbi.toString();
		}
		
		/**
		 * 发短信
		 * @return
		 */
		public String toSendSMRequestXml(){
			StringBuilder sbi=new StringBuilder();
	    	sbi.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
			.append("<PWBRequest>")
			.append("<transactionName>SEND_SM_REQ</transactionName>")
			.append(this.header.toRequestHeaderXml())
			.append(this.identityInfo.toidentityInfoXml())
			.append(this.orderRequest.toOrderRequestUpdateXml())
			.append("</PWBRequest>");
			 log.info("++++++++++++++++++++++++ SendSMRequest Xml:"+sbi.toString());
	    	return sbi.toString();
		}
	
		/**
		 * 发彩信
		 * @return
		 */
		public String toSendMMSRequestXml(){
			StringBuilder sbi=new StringBuilder();
	    	sbi.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
			.append("<PWBRequest>")
			.append("<transactionName>SEND_MMS_REQ</transactionName>")
			.append(this.header.toRequestHeaderXml())
			.append(this.identityInfo.toidentityInfoXml())
			.append(this.orderRequest.toOrderRequestUpdateXml())
			.append("</PWBRequest>");
			 log.info("++++++++++++++++++++++++ SendMMSRequest Xml:"+sbi.toString());
	    	return sbi.toString();
		}
		
		/**
		 * 部分退票
		 * @return
		 */
		public String toReturnTicketNumXml(){
			StringBuilder sbi=new StringBuilder();
	    	sbi.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
			.append("<PWBRequest>")
			.append("<transactionName>RETURN_TICKET_NUM_REQ</transactionName>")
			.append(this.header.toRequestHeaderXml())
			.append(this.identityInfo.toidentityInfoXml())
			.append(this.orderRequest.toReturnTicketXml())
			.append("</PWBRequest>");
			 log.info("++++++++++++++++++++++++ ReturnTicketNumRequest Xml:"+sbi.toString());
	    	return sbi.toString();
		}
		/**
		 * 批量退票
		 * @return
		 */
		public String toBatchReturnTicketNumXml(){
			StringBuilder sbi=new StringBuilder();
	    	sbi.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
			.append("<PWBRequest>")
			.append("<transactionName>BATCH_RETURN_TICKET_NUM_REQ</transactionName>")
			.append(this.header.toRequestHeaderXml())
			.append(this.identityInfo.toidentityInfoXml())
			.append(this.orderRequest.toReturnTicketXml())
			.append("</PWBRequest>");
			 log.info("++++++++++++++++++++++++ BatchReturnTicketNum Request Xml:"+sbi.toString());
	    	return sbi.toString();
		}
		
	public String getTransactionName() {
		return transactionName;
	}
	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public IdentityInfo getIdentityInfo() {
		return identityInfo;
	}
	public void setIdentityInfo(IdentityInfo identityInfo) {
		this.identityInfo = identityInfo;
	}
	public OrderRequest getOrderRequest() {
		return orderRequest;
	}
	public void setOrderRequest(OrderRequest orderRequest) {
		this.orderRequest = orderRequest;
	}
	
}
