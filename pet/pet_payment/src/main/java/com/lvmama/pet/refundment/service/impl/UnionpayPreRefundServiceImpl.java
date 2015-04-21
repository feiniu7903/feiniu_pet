package com.lvmama.pet.refundment.service.impl;

import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.pet.payment.service.impl.UnionpayPreServiceImpl;
import com.lvmama.pet.utils.UnionUtil;
import com.lvmama.pet.vo.PaymentErrorData;


/**
 * 此Service做银联预授权撤销和预授权退款动作
 * @author Alex Wang
 *
 */
public class UnionpayPreRefundServiceImpl implements BankRefundmentService{
	
	/**
	 * LOG.
	 */
	private static final Logger LOG = Logger.getLogger(UnionpayPreServiceImpl.class);
	
	/**
	 * 版本号.
	 */
	private  String version = PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_VERSION");
	/**
	 * 字符集.
	 */
	private static String charset = "UTF-8";
	/**
	 * 商户号.
	 */
	private  String merId = PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_MERID");
	/**
	 * 交易币种.
	 */
	private  String orderCurrency = PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_CURRENCY");
	/**
	 * 商户密钥.
	 */
	private  String key = PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_KEY");

	/**
	 * 后台交易.
	 */
	private String backStagegateWay = PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_BACK_URL");
	
	/**
	 * 退款的处理.
	 * @param info
	 * @return
	 */
	@Override
	public BankReturnInfo refund(RefundmentToBankInfo info) {
		String serial = SerialUtil.generate24ByteSerialAttaObjectId(info.getPaymentId());
		String[] valueVo = new String[]{
				"",//acqCode   c
				PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_REFUND_CALLBACK_URL"),//backEndUrl   m
				charset,//charset  m
				"",//commodityDiscount  o
				"",//commodityName    o
				"",//commodityQuantity   o
				"",//commodityUnitPrice   o
				"",//commodityUrl       o
				"127.0.0.1",//customerIp   m
				"",//customerName    o
				"",//defaultBankNumber    o
				"",//defaultPayType   o
				PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_REFUND_CALLBACK_URL"),//frontEndUrl    m
				PaymentConstant.MERABBR,//merAbbr   m
				"",//merCode    c
				merId,//merId     m
				"",//merReserved   o
				info.getRefundAmount().toString(),//orderAmount   m
				orderCurrency,//orderCurrency   m
				serial,//orderNumber  m
				DateUtil.getFormatDate(info.getCreateTime(), "yyyyMMddHHmmss"),//orderTime   m
				info.getGatewayTradeNo(),//origQid   c
//				"",
//				"",
				"",//transTimeout  o
				info.getPreRefundType(),//transType   m
				"",//transferFee   0
				version//version  m
		};
		
		BankReturnInfo returninfo =new BankReturnInfo();
		returninfo.setSerial(serial);
		String res = UnionUtil.getUnionPaySyncRes(key, backStagegateWay, valueVo);
		LOG.info("response:" + res );
		if (res != null && !"".equals(res)) {
			String[] arr = UnionUtil.getResArr(res);
			if(UnionUtil.checkSecurity(key, arr)){//验证签名
				String result=UnionUtil.getRespCode(arr);//商户业务逻辑
				returninfo.setCode(result);
				returninfo.setCodeInfo(PaymentErrorData.getInstance().getErrorMessage(Constant.PAYMENT_GATEWAY.CHINAPAY_PRE.name(), result));
				if("00".equals(result)){
					returninfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.PROCESSING.name());
				}
				else{
					returninfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
				}
			}
		}
		return returninfo;
	}
}
