package com.lvmama.pet.refundment.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.merPlus.OrdersBean;
import com.merPlus.PlusTools;

/**
 * 百付-手机支付退款.
 * 百付的退款异步回调和同步回调的返回信息都是一样的
 * @author ZHANG Nan
 */
public class UpompRefundServiceImpl implements BankRefundmentService {
	/**
	 * LOG.
	 */
	private static final Logger LOG = Logger.getLogger(UpompRefundServiceImpl.class);
	
	/**
	 * 商户公钥证书
	 */
	private String privateCertPath = PaymentConstant.getInstance().getProperty("UPOMP_PFX_PATH");
	/**
	 * 商户公钥证书
	 */
	private String publicCertPath = PaymentConstant.getInstance().getProperty("UPOMP_PUBLIC_CERT_PATH");
	/**
	 * 商户证书密码
	 */
	private String keyPassWord = PaymentConstant.getInstance().getProperty("UPOMP_KEY_PASSWORD");
	/**
	 * 商户代码
	 */
	private String merchantId = PaymentConstant.getInstance().getProperty("UPOMP_MERCHANT_ID");
	/**
	 * 百付-手机支付密钥
	 */
	private Map<String, String> keyMap = new HashMap<String,String>();
	/**
	 * 退款回调action地址
	 */
	private String upompRefundBackendUrl = PaymentConstant.getInstance().getProperty("UPOMP_REFUND_BACKEND_URL");
	
	/**
	 * 百付-手机支付 退款
	 */
	@Override
	public BankReturnInfo refund(RefundmentToBankInfo info) {
		//商户订单号(每次交易生成随机流水号)
		String merchantOrderId = SerialUtil.generate24ByteSerialAttaObjectId(info.getPaymentId());
		
		keyMap=PlusTools.getCertKey(privateCertPath, keyPassWord,publicCertPath);
		//退款
		OrdersBean ordersBean=returnOrder(info, keyMap,merchantOrderId);
		LOG.info("returnOrder RespCode:"+ordersBean.getRespCode()+" RespDesc:"+ordersBean.getRespDesc());
		
		BankReturnInfo returnInfo =new BankReturnInfo();
		String respCode = ordersBean.getRespCode();
		
		returnInfo.setSerial(merchantOrderId);
		returnInfo.setCodeInfo(ordersBean.getRespDesc());
		returnInfo.setCode(respCode);
		//判断是否退款成功
		boolean isSuccess=isSuccess(respCode);
		if(isSuccess){
			returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name());
		}
		else{
			returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
		}
		return returnInfo;
	}
	/**
	 * 退款处理
	 * @author ZHANG Nan
	 * @param info 退款对象
	 * @param keyMap 密钥map
	 * @return 百付退款结果
	 */
	private OrdersBean returnOrder(RefundmentToBankInfo info,Map<String, String> keyMap,String merchantOrderId){
		String merchantOrderTime=DateUtil.formatDate(info.getCreateTime(),"yyyyMMddHHmmss");
		try {
			return PlusTools.returnsXML(merchantId, merchantOrderId, merchantOrderTime, 
					String.valueOf(info.getRefundAmount()), info.getGatewayTradeNo(), 
					upompRefundBackendUrl, keyMap.get("publicCert"), keyMap.get("privateKey"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 判断是否退款成功
	 * @author ZHANG Nan
	 * @param respCode
	 * @return
	 */
	private boolean isSuccess(String respCode){
		boolean isSuccess = PaymentConstant.BYPAY_SUCCESS_KEY.equalsIgnoreCase(respCode) 
		|| "00".equalsIgnoreCase(respCode);
		return isSuccess;
	}
}
