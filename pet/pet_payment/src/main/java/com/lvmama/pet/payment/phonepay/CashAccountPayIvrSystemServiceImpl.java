package com.lvmama.pet.payment.phonepay;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.PAYMENT_SERIAL_STATUS;

public class CashAccountPayIvrSystemServiceImpl implements CashAccountPayIvrSystemService {
	/**
	 * 记日志.
	 */
	private Logger LOG = Logger.getLogger(getClass());
	/**
	 * 存款账户支付服务.
	 */
	private CashAccountService cashAccountService;

	private PayPaymentService payPaymentService;

	/**
	 * 订单消息.
	 */
	private TopicMessageProducer resourceMessageProducer;

	/**
	 * 接收IVR传来的存款账户支付数据.
	 * 
	 * @param xmlRequest
	 *            IVR传来的存款账户支付数据.
	 */
	@Override
	public void receiveDataFromIVRSystem(String xmlRequest) {
		/** ******收集讯鸟info********* */
		LOG.info(xmlRequest);
		CashAccountPayObject moneyAccountPayObject = CashAccountPayObject
				.createInstance(xmlRequest);
		CashAccountPayInfo cashAccountPayInfo = moneyAccountPayObject.getMoneyaccountpayinfo();
		LOG.info("moneyAccountMobile:" + cashAccountPayInfo.getMoneyAccountMobile());
		LOG.info("orderId:" + cashAccountPayInfo.getOrderId());
		LOG.info("userId:" + cashAccountPayInfo.getUserId());
		if (moneyAccountPayObject.verifySignature()) {
			// 在电话支付过程中 金额发生变化时,在解析XML文件后做支付金额 的判断.
			// TODO:后续需要判断金额是否发生变化，由于目前不能调用order service，所以暂时不做判断

			boolean codeIsRight = false;
			if ("Y".equals(cashAccountPayInfo.getHasDynamicCode())) {
				String mobileNumber = cashAccountPayInfo.getMoneyAccountMobile();
				String code = cashAccountPayInfo.getPaymentPassword();
				Object obj = MemcachedUtil.getInstance().get(
						"moneyAccountPay_" + mobileNumber + "_" + code);
				if (obj != null && code.equalsIgnoreCase(obj.toString())) {
					codeIsRight = true;
				}
			}
			
			Long orderId = new Long(cashAccountPayInfo.getOrderId());
			CashAccountVO cashAccount = cashAccountService.queryMoneyAccountByUserNo(cashAccountPayInfo.getUserId());
			if (cashAccount.getMaxPayMoney() > 0) {// 客户设置了支付密码时
				if (cashAccount.getPaymentPassword() != null && !codeIsRight) {
					String md5Password = MD5.md5(cashAccountPayInfo.getPaymentPassword());
					if (cashAccount.getPaymentPassword().equals(md5Password)) {
						List<Long> paymentIds=cashAccountService.payFromCashAccount(cashAccount.getUserId(), orderId,cashAccountPayInfo.getBizType(),cashAccountPayInfo.getPaytotalFen(),0L);
						for(Long paymentId:paymentIds) {
							resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(paymentId));
						}
					} else {
						this.insertFailedMoneyAccountPayment(cashAccountPayInfo, "支付密码错误");
					}
				} else if (codeIsRight) {
					// 用户未设置支付密码，使用动态支付密码并且密码正确时
					List<Long> paymentIds=cashAccountService.payFromCashAccount(cashAccount.getUserId(), orderId,cashAccountPayInfo.getBizType(),cashAccountPayInfo.getPaytotalFen(),0L);
					for(Long paymentId:paymentIds) {
						resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(paymentId));
					}
				} else {
					this.insertFailedMoneyAccountPayment(cashAccountPayInfo, "动态支付密码错误");
				}
			} else {
				this.insertFailedMoneyAccountPayment(cashAccountPayInfo, "存款账户余额不足");
			}
		}
	}

	/**
	 * 添加存款账户支付错误记录.
	 * 
	 * @param orderId
	 *            订单号
	 * @param actualPay
	 *            实际支付
	 * @param callbackInfo
	 *            错误信息
	 */
	private void insertFailedMoneyAccountPayment(CashAccountPayInfo cashAccountPayInfo,
			String callbackInfo) {
		final Date date = new Date();
		PayPayment payment = new PayPayment();
		payment.setObjectId(Long.valueOf(cashAccountPayInfo.getOrderId()));
		payment.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
		payment.setPaymentGateway(Constant.PAYMENT_GATEWAY.CASH_ACCOUNT.name());
		payment.setAmount(cashAccountPayInfo.getPaytotalFen());
		payment.setStatus(PAYMENT_SERIAL_STATUS.FAIL.name());
		payment.setSerial(payment.geneSerialNo());
		payment.setCallbackInfo(callbackInfo);
		payment.setCreateTime(date);
		payment.setCallbackTime(date);
		payPaymentService.savePayment(payment);
	}

	public CashAccountService getCashAccountService() {
		return cashAccountService;
	}

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}

	public PayPaymentService getPayPaymentService() {
		return payPaymentService;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public void setResourceMessageProducer(TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}

	public static void main(String[] args) {
		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><Head><Version>1</Version><SequenceId>20120307170154</SequenceId><Signed>W1ldRlpkWWJdYllaZiYUaBsYIBkZQ1hsH3Fgc3tvaCE/JCM=</Signed></Head><Body>VlUWIQ9LIhUmJx0kDx4qXy1nWl4dQyYgaBkzKW5kRYWINy05Ki40mDFAR4OGPgMDGTgXEQ0kXFVQaiodXxNiZG5xjVdxM1Y4HyZTJHJjbC8rmH2CfUdbMTA1HwYaJgokKQwSC05VDmtoE1FAIGhpjFx1JGplYRw0NHZ8k3ktKXlnpDF2Ly03AwZSXwgaKgtaWhQiIGVfJGIbZm1WhVlxMzIwKzcnJHJiJjF8en59cU85MTQ/BCAjHwkUDBVaZVNMaSoVZxNwcHFkhDQgciUoQygjO2ZgcKB+f4OEfYB4NjJBLhMEFQ4UDy8LEilTLx0fGGImQCUWNjQmb0MyKCZ4bGFvY4B0e2iIeT0pPTRADlZfRFhfSyIVHSEeD2V8D1AmLxcyOWNxWTMsNkIeaiU+Ki0vfDR8KHxvfDJ+XlReRVlZZVpmEUxZaSEdYWcWaFctSCwrlSZjcFxFO3trY5R6fn1thDpML0pbBA4FUF9OLgsQHU4=</Body></Request>";
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext-payment-beans.xml");
		CashAccountPayIvrSystemService service = (CashAccountPayIvrSystemService) context
				.getBean("moneyAccountPayIvrSystem");
		service.receiveDataFromIVRSystem(str);
	}

}
