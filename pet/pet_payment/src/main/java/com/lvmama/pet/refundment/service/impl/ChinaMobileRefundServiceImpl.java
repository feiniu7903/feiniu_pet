
package com.lvmama.pet.refundment.service.impl;

import java.net.URLDecoder;

import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.pet.utils.ChinaMobileUtil;
/**
 * 中国移动的退款.
 * @author liwenzhan
 *
 */
public class ChinaMobileRefundServiceImpl implements BankRefundmentService {
	
	private static final Logger LOG = Logger.getLogger(ChinaMobileRefundServiceImpl.class);
	
	//商户ID
	private String merchantId=PaymentConstant.getInstance().getProperty("CHINA_MOBILE_MERCHANT_ID");
	//签名方式 只能是MD5或RSA
	private String signType="MD5";
	//接口类型
	private String type="OrderRefund";
	//版本号
	private String version="2.0.0";
	//商户密钥
	private String merchantSignatureKey=PaymentConstant.getInstance().getProperty("CHINA_MOBILE_SIGN_KEY");
	//签名数据
	private String hmac="";
	
	private String requestUrl=PaymentConstant.getInstance().getProperty("CHINA_MOBILE_REQUEST_URL");
	@Override
	public BankReturnInfo refund(RefundmentToBankInfo info) {
		BankReturnInfo returnInfo =new BankReturnInfo();
	    try {
			String requestId=SerialUtil.generate24ByteSerialAttaObjectId(info.getOrderId());
			returnInfo.setSerial(requestId);
			String chinaMobileOrderId=info.getPaymentTradeNo();
			String refundAmout=info.getRefundAmount()+"";
			
			ChinaMobileUtil util = new ChinaMobileUtil();
			//-- 签名
			String signData = merchantId + requestId + signType + type + version + chinaMobileOrderId + refundAmout;
			hmac = util.MD5Sign(signData, merchantSignatureKey);
			LOG.info("chinaMobile Refund md5, signData="+signData+",hmac="+hmac);
			
			//-- 请求报文
			String buf = "merchantId=" + merchantId + "&requestId=" + requestId
			           + "&signType=" + signType + "&type=" + type
			           + "&version=" + version + "&orderId=" + chinaMobileOrderId
			           + "&amount=" + refundAmout;
			buf = "hmac=" + hmac + "&" + buf;
			
			//发起http请求，并获取响应报文
			String res = util.sendAndRecv(requestUrl, buf, "UTF-8");
			LOG.info("chinaMobile Refund info, res="+res);
			 //中国移动支付返回报文的消息摘要，用于商户验签
			String hmac1 = util.getValue(res, "hmac");
			String vfsign = util.getValue(res, "merchantId")
			      + util.getValue(res, "payNo")
			      + util.getValue(res, "returnCode")
			      + URLDecoder.decode(util.getValue(res, "message"), "UTF-8")
			      + util.getValue(res, "signType")
			      + util.getValue(res, "type")
			      + util.getValue(res, "version")
			      + util.getValue(res, "amount")
			      + util.getValue(res, "orderId")
			      + util.getValue(res, "status");

			//获取返回码
			String code = util.getValue(res, "returnCode");
			if (!"000000".equals(code)) {
				returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
				returnInfo.setCodeInfo("退款失败："+code+" "+URLDecoder.decode(util.getValue(res,"message"),"UTF-8"));
				return returnInfo;
			}
			//验证签名
			boolean flag = false;
			flag = util.MD5Verify(vfsign, hmac1, merchantSignatureKey);
			if (!flag) {
				returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
				returnInfo.setCodeInfo("退款失败:验签不成功");
				return returnInfo;
			}
			
			String status = util.getValue(res, "status");
			if ("000000".equals(code) && "SUCCESS".equals(status)) {
				returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name());
				returnInfo.setCodeInfo("退款成功");
				return returnInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
			returnInfo.setCodeInfo("退款失败:系统异常");
		}
		return returnInfo;    
	}
}
