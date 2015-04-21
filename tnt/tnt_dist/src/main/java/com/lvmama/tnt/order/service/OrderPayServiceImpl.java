package com.lvmama.tnt.order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.tnt.cashaccount.mapper.TntCashPayMapper;
import com.lvmama.tnt.cashaccount.po.TntCashAccount;
import com.lvmama.tnt.cashaccount.po.TntCashPay;
import com.lvmama.tnt.cashaccount.service.TntCashaccountService;
import com.lvmama.tnt.comm.util.PriceUtil;
import com.lvmama.tnt.comm.util.TntCertUtil;
import com.lvmama.tnt.comm.util.UserClient;
import com.lvmama.tnt.comm.vo.ResultGod;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.comm.vo.TntConstant.CASHPAY_STATUS;
import com.lvmama.tnt.comm.vo.TntConstant.PAYMENT_GATEWAY;
import com.lvmama.tnt.comm.vo.TntConstant.PAYMENT_STATUS;
import com.lvmama.tnt.order.po.TntOrder;
import com.lvmama.tnt.order.vo.TntPayPayment;
import com.lvmama.tnt.user.mapper.TntUserDetailMapper;
import com.lvmama.tnt.user.po.TntUserDetail;

@Repository("orderPayService")
public class OrderPayServiceImpl implements OrderPayService {

	@Autowired
	private TntCashaccountService tntCashaccountService;

	@Autowired
	private TntOrderService tntOrderService;

	@Autowired
	private OrderCreateService orderCreateService;

	@Autowired
	private TntUserDetailMapper tntUserDetailMapper;

	@Autowired
	private TntCashPayMapper tntCashPayMapper;

	@Autowired
	private UserClient tntUserClient;

	public String validateBeforePay(Long userId, String paymentPassword,
			List<Long> orderIds) {
		String error = null;
		if (userId == null || paymentPassword == null || orderIds == null
				|| orderIds.isEmpty()) {
			error = "支付失败，您不符合支付条件";
		} else {
			TntCashAccount account = new TntCashAccount();
			account.setUserId(userId);
			account.setPaymentPassword(paymentPassword);
			error = tntCashaccountService.validatePayPassword(account);
			if (error == null) {
				List<TntOrder> orders = tntOrderService.queryCanPay(orderIds,
						userId);
				if (orders == null || orders.isEmpty()) {
					error = "支付失败，没有可支付的订单";
				} else {
					boolean canIgnore = canIgnoreBalance(userId);
					if (!canIgnore) {
						Long amount = tntOrderService.sumAmount(orders);
						ResultGod<TntCashAccount> result = tntCashaccountService
								.validateCashBalance(account, amount);
						error = result.getErrorText();
					}
				}
			}
		}
		return error;
	}

	@Override
	public boolean canIgnoreBalance(Long userId) {
		TntUserDetail detail = tntUserDetailMapper.getByUserId(userId);
		return detail != null
				&& !TntConstant.PAY_TYPE.isSinglePay(detail.getPayType());
	}

	@Override
	public ResultGod<List<TntOrder>> orderPayment(Long userId,
			String paymentPassword, List<Long> orderIds) {
		StringBuffer sb = new StringBuffer();
		String error = validateBeforePay(userId, paymentPassword, orderIds);
		List<TntOrder> orders = null;
		if (error == null) {
			boolean canIgnore = canIgnoreBalance(userId);
			orders = new ArrayList<TntOrder>();
			for (Long orderId : orderIds) {
				TntOrder order = tntOrderService.getByOrderId(orderId);
				// 检查订单状态，订单状态，审核状态，支付状态
				error = tntOrderService.checkOrder(order);
				if (error == null) {
					Long amount = order.getOrderAmount();
					// 检查账户余额是否足够
					ResultGod<TntCashAccount> cashResult = tntCashaccountService
							.validateCashBalance(userId, amount);
					TntCashAccount t = cashResult.getResult();
					Long cashAccountId = t.getCashAccountId();
					String mobile = t.getMobileNumber();
					if (!canIgnore && !cashResult.isSuccess()) {
						error = cashResult.getErrorText();
					}
					if (error != null) {
						sb.append("您的账户余额不足，部分订单未能成功支付");
					} else {
						TntCashPay pay = new TntCashPay();
						pay.setAmount(amount);
						pay.setCashAccountId(cashAccountId);
						pay.setProductName(order.getProductName());
						pay.setStatus(CASHPAY_STATUS.CREATE.name());
						pay.setTntOrderId(order.getTntOrderId());
						pay.setSerial(TntCertUtil.serialGenerator());
						boolean success = false;
						String payStatus = PAYMENT_STATUS.PAYFAIL.name();
						try {
							success = tntCashPayMapper.insert(pay) > 0;
							if (success) {
								TntPayPayment payment = new TntPayPayment();
								payment.setOperator("operator");
								payment.setOrderId(orderId);
								payment.setPaymentGateway(PAYMENT_GATEWAY.DISTRIBUTOR_B2B
										.name());
								payment.setSerial(orderId.toString());
								payment.setPaymentTradeNo(pay.getSerial());
								success = orderCreateService
										.orderPayment(payment);
								if (success) {
									pay.setStatus(CASHPAY_STATUS.SUCCESS.name());
									success = tntCashaccountService.pay(pay);
									if (success) {
										tntUserClient
												.sendCashPaySms(
														mobile,
														PriceUtil
																.trans2YuanStr(amount));
										tntOrderService.buildItems(order);
										orders.add(order);
										// 更新订单支付状态
										payStatus = PAYMENT_STATUS.PAYED.name();
									}
								}
							}
							if (!success) {
								throw new Exception("支付失败");
							}
						} catch (Exception e) {
							pay.setStatus(CASHPAY_STATUS.FAIL.name());
							tntCashaccountService.pay(pay);
							sb.append("订单" + orderId + "支付失败<br/>");
							e.printStackTrace();
						}
						if (PAYMENT_STATUS.isPayed(payStatus))
							tntOrderService.changePayStatus(
									order.getTntOrderId(), payStatus);
					}
				} else {
					sb.append(error + "<br/>");
				}
			}
		} else {
			sb.append(error);
		}
		ResultGod<List<TntOrder>> god = new ResultGod<List<TntOrder>>();
		god.setSuccess(orders != null && !orders.isEmpty());
		god.setResult(orders);
		error = sb.toString();
		god.setErrorText(error.isEmpty() ? null : error);
		return god;
	}
}
