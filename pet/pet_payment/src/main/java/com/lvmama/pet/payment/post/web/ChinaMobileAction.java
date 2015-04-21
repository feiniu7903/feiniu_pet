package com.lvmama.pet.payment.post.web;

import java.io.IOException;
import java.net.URLDecoder;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.payment.post.data.ChinaMobilePostData;
import com.lvmama.pet.payment.post.data.PostData;
import com.lvmama.pet.utils.ChinaMobileUtil;

@Results({
	@Result(name = "success", location = "/WEB-INF/pages/forms/chinaMobile.ftl", type = "freemarker")
})
public class ChinaMobileAction extends PayAction {

	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = 3884523053913159710L;
	private ChinaMobilePostData chinaMobilePostData;
	private String requestUrl=PaymentConstant.getInstance().getProperty("CHINA_MOBILE_REQUEST_URL");
	
	@Action("/pay/chinaMobile")
	public String pay() {
		if(payment()){
			try {
				//-- 请求报文
				String buf = "characterSet=" + chinaMobilePostData.getCharacterSet()+ "&callbackUrl="
						+ chinaMobilePostData.getCallbackUrl() + "&notifyUrl=" + chinaMobilePostData.getNotifyUrl()
						+ "&ipAddress=" + chinaMobilePostData.getIpAddress()+ "&merchantId="
						+ chinaMobilePostData.getMerchantId()+ "&requestId=" + chinaMobilePostData.getRequestId()+ "&signType="
						+ chinaMobilePostData.getSignType()+ "&type=" + chinaMobilePostData.getType()+ "&version=" + chinaMobilePostData.getVersion()
						+ "&amount=" + chinaMobilePostData.getAmount()+ "&bankAbbr=" + chinaMobilePostData.getBankAbbr()
						+ "&currency=" +chinaMobilePostData.getCurrency()+ "&orderDate=" + chinaMobilePostData.getOrderDate()
						+ "&orderId=" + chinaMobilePostData.getChinaMobileOrderId()+ "&merAcDate=" + chinaMobilePostData.getMerAcDate()
						+ "&period=" + chinaMobilePostData.getPeriod()+ "&periodUnit=" + chinaMobilePostData.getPeriodUnit()
						+ "&merchantAbbr=" + chinaMobilePostData.getMerchantAbbr()+ "&productDesc="
						+ chinaMobilePostData.getProductDesc()+ "&productId=" + chinaMobilePostData.getProductId()
						+ "&productName=" + chinaMobilePostData.getProductName()+ "&productNum="
						+ chinaMobilePostData.getProductNum()+ "" + "&reserved1=" + chinaMobilePostData.getReserved1()
						+ "&reserved2=" + chinaMobilePostData.getReserved2()+ "&userToken=" + chinaMobilePostData.getUserToken()
						+ "&showUrl=" + chinaMobilePostData.getShowUrl()+ "&couponsFlag=" + chinaMobilePostData.getCouponsFlag();
				//-- 带上消息摘要
				buf = "hmac=" + chinaMobilePostData.getHmac()+ "&" + buf;
				ChinaMobileUtil util=new ChinaMobileUtil();
				//发起http请求，并获取响应报文
			
				String res = util.sendAndRecv(requestUrl, buf, chinaMobilePostData.getCharacterSet());
				//获得手机支付平台的消息摘要，用于验签,
				String hmac1 = util.getValue(res, "hmac");
				String vfsign = util.getValue(res, "merchantId")
						+ util.getValue(res, "requestId")
						+ util.getValue(res, "signType")
						+ util.getValue(res, "type")
						+ util.getValue(res, "version")
						+ util.getValue(res, "returnCode")
						+ URLDecoder.decode(util.getValue(res, "message"),chinaMobilePostData.getCharacterSet()) + util.getValue(res, "payUrl");

				//响应码
				String code = util.getValue(res, "returnCode");
				//下单交易成功
				if (!code.equals("000000")) {
					LOG.error("chinaMobile paymentError:" + code + URLDecoder.decode(util.getValue(res,"message"),"UTF-8"));
					return ERRORNO;
				}

				// -- 验证签名
				boolean flag = util.MD5Verify(vfsign, hmac1,chinaMobilePostData.getMerchantSignatureKey());
				if (!flag) {
					LOG.error("chinaMobile check Signature Fail! vfsign="+vfsign+",hmac1="+hmac1);
					return ERRORNO;
				}

				String payUrl = util.getValue(res, "payUrl");
				String submit_url = util.getRedirectUrl(payUrl);
				getResponse().sendRedirect(submit_url);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return SUCCESS;
		} else {
			return ERRORNO;
		}
	}
	
	@Override
	PostData getPostData(PayPayment payPayment) {
		chinaMobilePostData=new ChinaMobilePostData(payPayment,bankid);
		return chinaMobilePostData;
	}
	
	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.CHINA_MOBILE_PAY.name();
	}
	@Override
	String getPaymentTradeNo(Long randomId) {
		return SerialUtil.generate24ByteSerialAttaObjectId(randomId);
	}

	
	
	public ChinaMobilePostData getChinaMobilePostData() {
		return chinaMobilePostData;
	}

	public void setChinaMobilePostData(ChinaMobilePostData chinaMobilePostData) {
		this.chinaMobilePostData = chinaMobilePostData;
	}
}
