package com.lvmama.pet.refundment.data;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bocnet.common.security.PKCS7Tool;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.vo.PaymentErrorData;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class BOCRefundCallbackData implements RefundCallbackData {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(BOCRefundCallbackData.class);
	private Header header;
	private Body body;
	
	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public BOCRefundCallbackData(){}
	
	public static BOCRefundCallbackData initBOCRefundCallbackXML(String xmlStr){
		XStream stream = new XStream(new DomDriver());
		stream.alias("res", BOCRefundCallbackData.class);
		stream.alias("header",Header.class);
		stream.alias("body",Body.class);
		stream.alias("orderTrans",Body.OrderTrans.class);
		return (BOCRefundCallbackData)stream.fromXML(xmlStr);
	}

	public class Header{
		/**
		 * 商户号.
		 */
		private String merchantNo;
		/**
		 * 返回操作类型.
		 */
		private String returnActFlag;
		/**
		 * 处理状态.
		 */
		private String dealStatus;
		/**
		 * 包体标志:
		 * <pre>
		 * 0：有包体数据 
		 * 1：无包体数据
		 * </pre>
		 */
		private String bodyFlag;
		/**
		 * 错误码.
		 */
		private String exception;
		public String getMerchantNo() {
			return merchantNo;
		}
		public void setMerchantNo(String merchantNo) {
			this.merchantNo = merchantNo;
		}
		public String getReturnActFlag() {
			return returnActFlag;
		}
		public void setReturnActFlag(String returnActFlag) {
			this.returnActFlag = returnActFlag;
		}
		public String getDealStatus() {
			return dealStatus;
		}
		public void setDealStatus(String dealStatus) {
			this.dealStatus = dealStatus;
		}
		public String getBodyFlag() {
			return bodyFlag;
		}
		public void setBodyFlag(String bodyFlag) {
			this.bodyFlag = bodyFlag;
		}
		public String getException() {
			return exception;
		}
		public void setException(String exception) {
			this.exception = exception;
		}
	}
	
	public class Body{
		private OrderTrans orderTrans;
		
		
		public OrderTrans getOrderTrans() {
			return orderTrans;
		}


		public void setOrderTrans(OrderTrans orderTrans) {
			this.orderTrans = orderTrans;
		}


		public class OrderTrans{
			/**
			 * 商户退款交易流水号.
			 */
			private String mRefundSeq;
			/**
			 * 币种.
			 */
			private String curCode;
			/**
			 * 退款金额.
			 */
			private String refundAmount;
			/**
			 * 商户订单号.
			 */
			private String orderNo;
			/**
			 * 银行订单流水号.
			 */
			private String orderSeq;
			/**
			 * 订单金额.
			 */
			private String orderAmount;
			/**
			 * 银行交易流水号.
			 */
			private String bankTranSeq;
			/**
			 * 银行交易时间.
			 */
			private String tranTime;
			/**
			 * 网关签名数据.
			 */
			private String signData;
			
			
			public String getmRefundSeq() {
				return mRefundSeq;
			}
			public void setmRefundSeq(String mRefundSeq) {
				this.mRefundSeq = mRefundSeq;
			}
			public String getCurCode() {
				return curCode;
			}
			public void setCurCode(String curCode) {
				this.curCode = curCode;
			}
			public String getRefundAmount() {
				return refundAmount;
			}
			public void setRefundAmount(String refundAmount) {
				this.refundAmount = refundAmount;
			}
			public String getOrderNo() {
				return orderNo;
			}
			public void setOrderNo(String orderNo) {
				this.orderNo = orderNo;
			}
			public String getOrderSeq() {
				return orderSeq;
			}
			public void setOrderSeq(String orderSeq) {
				this.orderSeq = orderSeq;
			}
			public String getOrderAmount() {
				return orderAmount;
			}
			public void setOrderAmount(String orderAmount) {
				this.orderAmount = orderAmount;
			}
			public String getBankTranSeq() {
				return bankTranSeq;
			}
			public void setBankTranSeq(String bankTranSeq) {
				this.bankTranSeq = bankTranSeq;
			}
			public String getTranTime() {
				return tranTime;
			}
			public void setTranTime(String tranTime) {
				this.tranTime = tranTime;
			}
			public String getSignData() {
				return signData;
			}
			public void setSignData(String signData) {
				this.signData = signData;
			}
		} 
	}
	@Override
	public boolean checkSignature() {
		boolean flag = false;
		if("0".equals(header.getBodyFlag())){
			try{
//		商户号|商户退款交易流水号|退款金额|商户订单号|银行订单流水号|订单金额|银行交易流水号|银行交易时间|退款处理状态
//		merchantNo|mRefundSeq|refundAmount|orderNo|orderSeq|orderAmount|bankTranSeq |tranTime|dealStatus
				Body.OrderTrans ot = body.getOrderTrans();
				StringBuilder signBuilder = new StringBuilder();
				signBuilder.append(header.getMerchantNo()).append("|")
				.append(ot.getmRefundSeq()).append("|")
				.append(ot.getRefundAmount()).append("|")
				.append(ot.getOrderNo()).append("|")
				.append(ot.getOrderSeq()).append("|")
				.append(ot.getOrderAmount()).append("|")
				.append(ot.getBankTranSeq()).append("|")
				.append(ot.getTranTime()).append("|")
				.append(header.getDealStatus());
				
				String signData = ot.getSignData();
				String rootCertificatePath = PaymentConstant.getInstance().getProperty("BOC_ROOT_CERTIFICATE_PATH");
				PKCS7Tool tool= PKCS7Tool.getVerifier(rootCertificatePath);
				byte[] data = signBuilder.toString().getBytes();
				tool.verify(signData, data, null);
				flag = true;
			} catch (GeneralSecurityException e) {
				LOG.error("BOC CALL BACK ERROR GeneralSecurityException: " + e);
			} catch (IOException e) {
				LOG.error("BOC CALL BACK ERROR IOException: " + e);
			}
		} else {
			flag = true;
		}
		return flag;
	}

	@Override
	public String getSerial() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCallbackInfo() {
		return PaymentErrorData.getInstance().getErrorMessage(Constant.PAYMENT_GATEWAY.BOC.name(), header.getException());
	}

	/**
	 * 退款是否成功.
	 * @return
	 */
	public boolean isSuccess(){
		return "0".equals(header.getDealStatus()) && this.checkSignature();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//退款成功报文样例：
//		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><res><header><merchantNo>104110041000000</merchantNo><returnActFlag>3</returnActFlag><dealStatus>0</dealStatus><bodyFlag>0</bodyFlag><exception></exception></header><body><orderTrans><mRefundSeq>r0001</mRefundSeq><curCode>001</curCode><refundAmount>180.00</refundAmount><orderNo>324322</orderNo><orderSeq>1231</orderSeq><orderAmount>200.12</orderAmount><bankTranSeq>2010010111111111123456</bankTranSeq><tranTime>20090605000000</tranTime><signData>MIIEZgYJKoZIhvcNAQcCoIIEVzCCBFMCAQMxCTAHBgUrDgMCGjALBgkqhkiG9w0BBwGgggMdMIIDGTCCAgGgAwIBAgIQIbAY5mFnk0lHZcajtcSdEzANBgkqhkiG9w0BAQUFADBdMQswCQYDVQQGEwJDTjEWMBQGA1UEChMNQkFOSyBPRiBDSElOQTEQMA4GA1UECBMHQkVJSklORzEQMA43pV58B8IM=</signData></orderTrans></body></res>";
		//退款失败报文样例：
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><res><header><merchantNo>104110041000000</merchantNo><returnActFlag>3</returnActFlag><dealStatus>1</dealStatus><bodyFlag>1</bodyFlag><exception>E00000014</exception></header><body/></res>";
		BOCRefundCallbackData br = BOCRefundCallbackData.initBOCRefundCallbackXML(xmlStr);
		System.out.println(br);
		System.out.println(br.isSuccess());
	}
}
