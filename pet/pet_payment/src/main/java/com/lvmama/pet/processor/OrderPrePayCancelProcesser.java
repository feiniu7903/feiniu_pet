package com.lvmama.pet.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.UnionUtil;
import com.lvmama.pet.vo.PaymentErrorData;

/**
 * 
 * @author zhangjie
 *
 */
public class OrderPrePayCancelProcesser implements MessageProcesser {
	protected final Log LOG =LogFactory.getLog(this.getClass());

	private PayPaymentService payPaymentService;

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
	 * 版本号.
	 */
	private  String version = PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_VERSION");

	/**
	 * 商户密钥.
	 */
	private  String key = PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_KEY");

	/**
	 * 后台交易.
	 */
	private String backStagegateWay = PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_BACK_URL");
	
	/**
	 * 银联预授权撤销支付消息处理（补偿冻结机制，处理此消息前，请核实详细订单信息）
	 * @param message
	 */
	public void process(Message message) {
		if (message.isOrderPrepayCancelMsg()) {

			Long paymentId = message.getObjectId();
			String gatewayTradeNo = message.getAddition();

			LOG.info("Order OrderPrepayCancel init:"+paymentId.toString()+"----"+gatewayTradeNo);
			
			PayPayment payment = payPaymentService.selectByPaymentId(paymentId);
			if (null!=payment) {
				String serial = SerialUtil.generate24ByteSerialAttaObjectId(payment.getPaymentId());
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
						payment.getAmount().toString(),//orderAmount   m
						orderCurrency,//orderCurrency   m
						serial,//orderNumber  m
						DateUtil.getFormatDate(payment.getCreateTime(), "yyyyMMddHHmmss"),//orderTime   m
						gatewayTradeNo,//origQid   c
//						"",
//						"",
						"",//transTimeout  o
						PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_TRANSTYPE_PAY_CANCEL"),//transType   m
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
							LOG.info("Order OrderPrepayCancel returninfo:"+"success,"+returninfo.getCodeInfo());
						}
						else{
							LOG.info("Order OrderPrepayCancel returninfo:"+"fail,"+returninfo.getCodeInfo());
						}
					}
				}
			}
		}
	}
	
	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}
}
