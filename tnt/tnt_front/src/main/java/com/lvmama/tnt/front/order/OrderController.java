package com.lvmama.tnt.front.order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lvmama.comm.utils.StackOverFlowUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.tnt.cashaccount.po.TntCashAccount;
import com.lvmama.tnt.cashaccount.service.TntCashaccountService;
import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.comm.vo.ResultGod;
import com.lvmama.tnt.comm.vo.ResultMessage;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.front.BaseController;
import com.lvmama.tnt.order.po.TntOrder;
import com.lvmama.tnt.order.service.BuildTntBuyInfoService;
import com.lvmama.tnt.order.service.OrderCreateService;
import com.lvmama.tnt.order.service.OrderPayService;
import com.lvmama.tnt.order.service.TntOrderService;
import com.lvmama.tnt.order.vo.Person;
import com.lvmama.tnt.order.vo.TntBuyInfo;

@Controller
@RequestMapping(value = "/order")
public class OrderController extends BaseController {

	@Autowired
	private BuildTntBuyInfoService buildTntBuyInfoService;

	@Autowired
	private OrderCreateService orderCreateService;

	@Autowired
	private TntOrderService tntOrderService;

	@Autowired
	private TntCashaccountService tntCashaccountService;

	@Autowired
	private OrderPayService orderPayService;

	private static final String ERROR = "/order/error";

	private static final String ERROR_ATTR = "error";

	@RequestMapping(value = "/fill.do", method = RequestMethod.POST)
	public String fill(TntBuyInfo buyInfo, Model model, HttpSession session) {
		Long userId = getLoginUserId(session);
		String page = ERROR;
		if (userId != null) {
			buyInfo.setDistributor(userId.toString());
			ResultGod<TntBuyInfo> result = buildTntBuyInfoService
					.build(buyInfo);
			buyInfo = result.getResult();
			if (result.isSuccess()) {
				Person contact = new Person();
				buyInfo.setContact(contact);
				List<Person> travellers = new ArrayList<Person>();
				travellers.add(new Person());
				buyInfo.setTravellers(travellers);
				buyInfo.setUsrReceivers(new Person());
				model.addAttribute("buyInfo", buyInfo);
				page = "/order/fill";
			} else {
				model.addAttribute(ERROR_ATTR, result.getErrorText());
			}
		}
		return page;
	}

	@RequestMapping(value = "/ajaxCheckStock.do")
	public void checkStock(TntBuyInfo buyInfo, HttpServletRequest request,
			HttpServletResponse response) {
		JSONResult result = new JSONResult(response);
		try {
			String error = orderCreateService.checkOrderStock(buyInfo);
			if (error != null)
				result.raise(error);
		} catch (Exception ex) {
			result.raise(ex);
			StackOverFlowUtil.printErrorStack(request, response, ex);
			ex.printStackTrace();
		}
		result.output();
	}

	@RequestMapping(value = "/buy.do", method = RequestMethod.POST)
	public String buy(TntBuyInfo tntBuyInfo, Model model, HttpSession session) {
		Long userId = getLoginUserId(session);
		String page = ERROR;
		if (userId != null) {
			tntBuyInfo.setDistributor(userId.toString());
			ResultGod<TntOrder> result = tntOrderService
					.createOrder(tntBuyInfo);
			if (result != null) {
				if (result.isSuccess()) {
					Long orderId = result.getResult().getOrderId();
					if (orderId != null) {
						return "redirect:view.do/" + orderId;
					}
				} else {
					model.addAttribute(ERROR_ATTR, result.getErrorText());
				}
			} else {
				model.addAttribute(ERROR_ATTR, "下单失败！");
			}
		}
		return page;
	}

	@RequestMapping(value = "/mergeSuccess.do")
	public String mergeView(String orderIds, Model model, HttpSession session) {
		Long userId = getLoginUserId(session);
		String page = ERROR;
		if (userId != null && orderIds != null) {
			List<Long> ordIds = TntUtil.getLongList(orderIds);
			List<TntOrder> orders = tntOrderService.queryWithItems(ordIds,
					userId);
			if (orders != null && !orders.isEmpty()) {
				addOrderAttribute(orders, model);
				addCashAccountAttribute(model, userId);
				page = getPaySuccessPage(orders, model);
			}
		}
		return page;
	}

	@RequestMapping(value = "/view.do/{orderId}")
	public String view(@PathVariable Long orderId, Model model,
			HttpSession session) {
		Long userId = getLoginUserId(session);
		String page = ERROR;
		if (userId != null) {
			TntOrder order = new TntOrder();
			order.setOrderId(orderId);
			order.setDistributorId(userId);
			order = tntOrderService.getWithItems(order);
			if (order != null) {
				List<TntOrder> orders = new ArrayList<TntOrder>();
				orders.add(order);
				boolean isShowCashAccount = false;
				if (order.isPayed()) {
					page = getPaySuccessPage(orders, model);
					isShowCashAccount = true;
				} else if (TntConstant.ORDER_APPROVE_STATUS.isVerified(order
						.getApproveStatus())) {
					isShowCashAccount = true;
					String paymentTarget = order.getPaymentTarget();
					if (TntConstant.PRODUCT_PAY_TYPE
							.isPayToLvmama(paymentTarget)) {
						model.addAttribute("orderId", orderId);
						page = "/order/pay";
					} else if (TntConstant.PRODUCT_PAY_TYPE
							.isPayToSupplier(paymentTarget)) {
						page = getPaySuccessPage(orders, model);
					}
				} else {
					page = "/order/waitApprove";
				}
				if (isShowCashAccount) {
					addCashAccountAttribute(model, userId);
				}
				addOrderAttribute(orders, model);
			}
		}
		return page;
	}

	@RequestMapping(value = "/mergePay.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String mergePay(String orderIds, Model model, HttpSession session) {
		Long userId = getLoginUserId(session);
		String page = ERROR;
		if (userId != null && orderIds != null) {
			List<Long> ordIds = TntUtil.getLongList(orderIds);
			List<TntOrder> orders = tntOrderService.queryWithItems(ordIds,
					userId);
			orders = tntOrderService.removeCanotPay(orders);
			if (orders != null && !orders.isEmpty()) {
				addOrderAttribute(orders, model);
				addCashAccountAttribute(model, userId);
				model.addAttribute("orderId", orderIds);
				page = "/order/pay";
			} else {
				model.addAttribute(ERROR_ATTR, "没有可支付的订单");
			}
		}
		return page;
	}

	@RequestMapping(value = "/payValidate.do")
	public void payValidate(String paymentPassword, String orderIds,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		Long userId = getLoginUserId(session);
		ResultMessage result = new ResultMessage(true, "支付失败");
		List<Long> ordIds = TntUtil.getLongList(orderIds);
		String message = orderPayService.validateBeforePay(userId,
				paymentPassword, ordIds);
		if (message != null) {
			result.setSuccess(false);
			result.setErrorText(message);
		}
		try {
			printRtn(request, response, result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/pay.do", method = RequestMethod.POST)
	public String pay(TntCashAccount account, Float money, Model model,
			HttpServletRequest request, HttpSession session) {
		Long userId = getLoginUserId(session);
		String page = ERROR;
		String orderIds = request.getParameter("orderId");
		List<Long> ordIds = TntUtil.getLongList(orderIds);
		ResultGod<List<TntOrder>> result = orderPayService.orderPayment(userId,
				account.getPaymentPassword(), ordIds);
		if (result.isSuccess()) {
			List<TntOrder> orders = result.getResult();
			if (orders != null) {
				int size = orders.size();
				orderIds = getOrderIds(orders);
				if (size == 1) {
					page = "redirect:view.do/" + orderIds;
				} else if (size > 1) {
					page = "redirect:mergeSuccess.do?orderIds=" + orderIds;
				}
			}
		}
		model.addAttribute(ERROR_ATTR, result.getErrorText());
		return page;
	}

	private static String getOrderIds(List<TntOrder> orders) {
		StringBuffer sb = new StringBuffer();
		int size = orders.size();
		for (int i = 0; i < size; i++) {
			TntOrder t = orders.get(i);
			sb.append(t.getOrderId());
			if (i < size - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	private String getPaySuccessPage(List<TntOrder> orders, Model model) {
		return "/order/paySuccess";
	}

	private void addOrderAttribute(List<TntOrder> orders, Model model) {
		String lastWaitPaymentTime = getLastWaitPaymentTime(orders);
		model.addAttribute("lastWaitPaymentTime", lastWaitPaymentTime);
		Long orderAmount = 0l;
		String tickerMobile = null;
		if (orders != null) {
			for (TntOrder o : orders) {
				orderAmount += o.getOrderAmount();
				if (tickerMobile == null && o.getTickerMobile() != null)
					tickerMobile = o.getTickerMobile();
			}
		}
		model.addAttribute("orders", orders);
		model.addAttribute("orderAmount", orderAmount);
		model.addAttribute("tickerMobile", tickerMobile);
	}

	private static String getLastWaitPaymentTime(List<TntOrder> orders) {
		String lastWaitPaymentTime = null;
		if (orders != null) {
			Collections.sort(orders, new Comparator<TntOrder>() {
				@Override
				public int compare(TntOrder o1, TntOrder o2) {
					String st1 = o1.getLastWaitPaymentTime();
					String st2 = o2.getLastWaitPaymentTime();
					if (st1 != null && st2 != null && !st1.isEmpty()
							&& !st2.isEmpty()) {
						Long t1 = TntUtil.stringToDate(st1,
								"yyyy年MM月dd日 HH时mm分ss秒").getTime();
						Long t2 = TntUtil.stringToDate(st2,
								"yyyy年MM月dd日 HH时mm分ss秒").getTime();
						return t1 > t2 ? 1 : -1;
					}
					return 0;
				}
			});
			lastWaitPaymentTime = orders.get(0).getLastWaitPaymentTime();
		}
		return lastWaitPaymentTime;
	}

	private void addCashAccountAttribute(Model model, Long userId) {
		TntCashAccount tntCashAccount = tntCashaccountService
				.getAccountByUserId(userId);
		tntCashAccount.setPaymentPassword(null);
		model.addAttribute("tntCashAccount", tntCashAccount);
	}
}
