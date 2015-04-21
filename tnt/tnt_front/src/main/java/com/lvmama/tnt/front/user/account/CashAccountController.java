package com.lvmama.tnt.front.user.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.tnt.cashaccount.po.TntCashAccount;
import com.lvmama.tnt.cashaccount.po.TntCashCommission;
import com.lvmama.tnt.cashaccount.po.TntCashMoneyDraw;
import com.lvmama.tnt.cashaccount.po.TntCashPay;
import com.lvmama.tnt.cashaccount.po.TntCashRecharge;
import com.lvmama.tnt.cashaccount.po.TntCashRefundment;
import com.lvmama.tnt.cashaccount.service.TntCashaccountService;
import com.lvmama.tnt.comm.service.TntLogService;
import com.lvmama.tnt.comm.util.WebFetchPager;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.ResultMessage;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.comm.vo.TntConstant.COM_LOG_OBJECT_TYPE;
import com.lvmama.tnt.comm.vo.TntConstant.COM_LOG_TYPE;
import com.lvmama.tnt.front.BaseController;
import com.lvmama.tnt.recognizance.po.TntAccount;
import com.lvmama.tnt.recognizance.service.TntAccountService;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.service.TntUserService;

/**
 * 现金账户
 * 
 * @author gaoxin
 * 
 */
@Controller
@RequestMapping(value = "/userspace/cashAccount")
public class CashAccountController extends BaseController {

	@Autowired
	private TntUserService tntUserService;

	@Autowired
	private TntLogService tntLogService;

	@Autowired
	private TntCashaccountService tntCashaccountService;

	@Autowired
	private TntAccountService tntAccountService;

	@RequestMapping(value = "/index.do")
	public String index(HttpSession session, Model model,
			HttpServletRequest request) {
		TntCashAccount cashCount = tntCashaccountService
				.getAccountByUserId(getLoginUserId(session));
		model.addAttribute("tntCashAccount", cashCount);
		if (cashCount != null) {
			int pageNo = 1;
			TntCashPay t = new TntCashPay();
			t.setCashAccountId(cashCount.getCashAccountId());
			Page<TntCashPay> p = Page.page(pageNo, t);
			p.setModel(Page.MODEL_FRONT);
			List<TntCashPay> tntCashPayList = queryCashPay(p, request);
			model.addAttribute(tntCashPayList);
			model.addAttribute(Page.KEY, p);
		}

		return "/userspace/account/cash_account";
	}

	/**
	 * 支付手机绑定页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/mobileBond.do")
	public String mobileBond(Model model, HttpServletRequest request,
			HttpServletResponse response, String mobile) {
		model.addAttribute("mobile", mobile);
		return "/userspace/account/mobile_bond";
	}

	/**
	 * 支付手机绑定
	 * 
	 * @return
	 */
	@RequestMapping(value = "/savePayMobile.do")
	public void savePayMobile(Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, String mobile,
			String password, String isFirst) throws IOException {
		Long userId = getLoginUserId(session);
		ResultMessage message = new ResultMessage(false, "绑定失败！");
		if (userId != null) {
			TntCashAccount account = new TntCashAccount();
			account.setMobileNumber(mobile);
			account.setUserId(userId);
			account.setValid("Y");
			account.setPaymentPassword(password);
			TntCashAccount exist = tntCashaccountService
					.getAccountByUserId(userId);
			try {
				if (exist == null) {
					tntCashaccountService.insert(account);
				} else {
					tntCashaccountService.updatePayMobile(account);
				}
				message.setSuccess(true);
				message.setErrorText("绑定成功！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.printRtn(request, response, message);
	}

	@RequestMapping(value = "/validOldPwd.do")
	public void validOldPwd(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model,
			String oldPayPass) throws Exception {
		TntCashAccount cashAccount = tntCashaccountService
				.getAccountByUserId(getLoginUserId(session));
		if (cashAccount == null
				|| StringUtil.isEmptyString(oldPayPass)
				|| !cashAccount.getPaymentPassword().equals(
						MD5.code(oldPayPass, MD5.KEY_TNT_USER_PASSWORD))) {
			printRtn(request, response, new ResultMessage(false, "支付密码不正确"));
			return;
		}
		printRtn(request, response, new ResultMessage(true, ""));
		return;
	}

	@RequestMapping(value = "/payPwdBond.do")
	public String payPwdBond(HttpSession session, Model model) {
		return "/userspace/account/account_pwd";
	}

	@RequestMapping(value = "/savePassword.do")
	public void savePassword(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model,
			String password) throws IOException {
		if (StringUtils.isNotEmpty(password)) {
			password = MD5.code(password, MD5.KEY_TNT_USER_PASSWORD);
			TntCashAccount cashAccount = new TntCashAccount();
			cashAccount.setUserId(getLoginUserId(session));
			cashAccount.setPaymentPassword(password);
			tntCashaccountService.updatePayPassword(cashAccount);
			ResultMessage message = new ResultMessage(true, "支付密码修改成功！");
			printRtn(request, response, message);
		}
	}

	@RequestMapping(value = "/addMoneyDraw.do", method = RequestMethod.POST)
	public void addMoney(HttpServletRequest request, TntCashMoneyDraw t,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		t.setAuditStatus(TntConstant.DRAW_AUDIT_STATUS.PENDING_AUDIT.name());
		TntCashAccount cashAccount = tntCashaccountService
				.getAccountByUserId(getLoginUserId(session));
		if (cashAccount == null
				|| !cashAccount.getPaymentPassword().equalsIgnoreCase(
						MD5.code(t.getMemo(), MD5.KEY_TNT_USER_PASSWORD))) {
			printRtn(request, response, new ResultMessage(false, "提现失败，支付密码错误"));
			return;
		}
		if (t.getDrawAmount() > cashAccount.getBalance()) {
			TntCashAccount tntUser = tntCashaccountService
					.getAccountByUserId(getLoginUserId(session));
			session.setAttribute(TntConstant.SESSION_TNT_USER, tntUser);
			printRtn(request, response, new ResultMessage(false,
					"提现失败，提现金额大于可以金额"));
			return;
		}
		t.setCashAccountId(cashAccount.getCashAccountId());
		t.setMemo("");
		Long moneyDrawId = tntCashaccountService.addCashMoneyDraw(t);
		TntUser tntUser = tntUserService
				.findWithDetailByUserId(getLoginUserId(session));
		session.setAttribute(TntConstant.SESSION_TNT_USER, tntUser);
		tntLogService.save(COM_LOG_OBJECT_TYPE.TNT_ACCOUNT.getCode(), null,
				moneyDrawId, tntUser.getUserName(),
				COM_LOG_TYPE.ACCOUNT_DRAW.name(), "提现申请", "提现申请", "");
		printRtn(request, response, new ResultMessage(true, ""));
	}

	/** 分销商预存款账户列表 **/
	@RequestMapping(value = "/queryCashPay.do")
	public String queryCashPay(Model model, HttpServletRequest request,
			HttpSession session, TntCashPay t, Integer page) {
		Long accountID = getTntCashAccountId(session);
		if (accountID == null) {
			return "/userspace/account/account/cashPay";
		}
		t.setCashAccountId(accountID);
		int pageNo = page != null ? page : 1;
		Page<TntCashPay> p = Page.page(pageNo, t);
		p.setModel(Page.MODEL_FRONT);
		List<TntCashPay> tntCashPayList = queryCashPay(p, request);
		model.addAttribute(tntCashPayList);
		model.addAttribute(Page.KEY, p);
		return "/userspace/account/account/cashPay";
	}

	protected List<TntCashPay> queryCashPay(Page<TntCashPay> page,
			HttpServletRequest request) {
		WebFetchPager<TntCashPay> pager = new WebFetchPager<TntCashPay>(page,
				request) {

			@Override
			protected long getTotalCount(TntCashPay t) {
				return tntCashaccountService.countCashPay(t);
			}

			@Override
			protected List<TntCashPay> fetchDetail(Page<TntCashPay> page) {
				page = page.desc("CASH_PAY_ID");
				page.setModel(Page.MODEL_FRONT);
				return tntCashaccountService.pageQueryCashPay(page);
			}

		};
		return pager.fetch();
	}

	/** 分销商预存款账户列表 **/
	@RequestMapping(value = "/queryCashRecharge")
	public String queryCashRecharge(Model model, HttpServletRequest request,
			HttpSession session, TntCashRecharge t, Integer page) {
		Long accountID = getTntCashAccountId(session);
		if (accountID == null) {
			return "/userspace/account/account/cashRecharge";
		}
		t.setCashAccountId(accountID);
		int pageNo = page != null ? page : 1;
		Page<TntCashRecharge> p = Page.page(pageNo, t);
		List<TntCashRecharge> tntCashRechargeList = queryCashRecharge(p,
				request);
		model.addAttribute(tntCashRechargeList);
		model.addAttribute(Page.KEY, p);
		model.addAttribute(t);
		return "/userspace/account/account/cashRecharge";
	}

	protected List<TntCashRecharge> queryCashRecharge(
			Page<TntCashRecharge> page, HttpServletRequest request) {
		WebFetchPager<TntCashRecharge> pager = new WebFetchPager<TntCashRecharge>(
				page, request) {

			@Override
			protected long getTotalCount(TntCashRecharge t) {
				return tntCashaccountService.countCashRecharge(t);
			}

			@Override
			protected List<TntCashRecharge> fetchDetail(
					Page<TntCashRecharge> page) {
				page = page.desc("CASH_RECHARGE_ID");
				page.setModel(Page.MODEL_FRONT);
				return tntCashaccountService.pageQueryCashRecharge(page);
			}

		};
		return pager.fetch();
	}

	/** 分销商预存款账户列表 **/
	@RequestMapping(value = "/queryCashRefundment.do")
	public String queryCachfundment(Model model, HttpServletRequest request,
			HttpSession session, TntCashRefundment t, Integer page) {
		Long accountID = getTntCashAccountId(session);
		if (accountID == null) {
			return "/userspace/account/account/cashRefundment";
		}
		t.setCashAccountId(accountID);
		int pageNo = page != null ? page : 1;
		Page<TntCashRefundment> p = Page.page(pageNo, t);
		List<TntCashRefundment> tntCashRefundmentList = queryCachRefundment(p,
				request);
		model.addAttribute(tntCashRefundmentList);
		model.addAttribute(Page.KEY, p);
		model.addAttribute(t);
		return "/userspace/account/account/cashRefundment";
	}

	protected List<TntCashRefundment> queryCachRefundment(
			Page<TntCashRefundment> page, HttpServletRequest request) {
		WebFetchPager<TntCashRefundment> pager = new WebFetchPager<TntCashRefundment>(
				page, request) {

			@Override
			protected long getTotalCount(TntCashRefundment t) {
				return tntCashaccountService.countCashRefundment(t);
			}

			@Override
			protected List<TntCashRefundment> fetchDetail(
					Page<TntCashRefundment> page) {
				page = page.desc("FINC_REFUNDMENT_ID");
				page.setModel(Page.MODEL_FRONT);
				return tntCashaccountService.pageQueryCashRefundment(page);
			}

		};
		return pager.fetch();
	}

	/** 分销商预存款账户列表 **/
	@RequestMapping(value = "/queryCashMoneyDraw.do")
	public String queryCashMoneyDraw(Model model, HttpServletRequest request,
			HttpSession session, TntCashMoneyDraw t, Integer page) {
		Long accountID = getTntCashAccountId(session);
		if (accountID == null) {
			return "/userspace/account/account/cashMoneyDraw";
		}
		t.setCashAccountId(accountID);
		int pageNo = page != null ? page : 1;
		Page<TntCashMoneyDraw> p = Page.page(pageNo, t);
		List<TntCashMoneyDraw> tntMoneyDrawList = queryCashMoneyDraw(p, request);
		model.addAttribute("tntMoneyDrawList", tntMoneyDrawList);
		model.addAttribute(Page.KEY, p);
		model.addAttribute(t);
		return "/userspace/account/account/cashMoneyDraw";
	}

	protected List<TntCashMoneyDraw> queryCashMoneyDraw(
			Page<TntCashMoneyDraw> page, HttpServletRequest request) {
		WebFetchPager<TntCashMoneyDraw> pager = new WebFetchPager<TntCashMoneyDraw>(
				page, request) {

			@Override
			protected long getTotalCount(TntCashMoneyDraw t) {
				return tntCashaccountService.countCashMoneyDraw(t);
			}

			@Override
			protected List<TntCashMoneyDraw> fetchDetail(
					Page<TntCashMoneyDraw> page) {
				page = page.desc("MONEY_DRAW_ID");
				page.setModel(Page.MODEL_FRONT);
				return tntCashaccountService.pageQueryCashMoneyDraw(page);
			}

		};
		return pager.fetch();
	}

	/** 分销商返佣列表 **/
	@RequestMapping(value = "/queryCashCommission.do")
	public String queryCashCommission(Model model, HttpServletRequest request,
			HttpSession session, TntCashCommission t, Integer page) {
		Long accountID = getTntCashAccountId(session);
		if (accountID == null) {
			return "/userspace/account/account/cashCommission";
		}
		t.setCashAccountId(accountID);
		int pageNo = page != null ? page : 1;
		Page<TntCashCommission> p = Page.page(pageNo, t);
		List<TntCashCommission> tntCashCommissionList = queryCashCommission(p,
				request);
		model.addAttribute(tntCashCommissionList);
		model.addAttribute(Page.KEY, p);
		model.addAttribute(t);
		return "/userspace/account/account/cashCommission";
	}

	protected List<TntCashCommission> queryCashCommission(
			Page<TntCashCommission> page, HttpServletRequest request) {
		WebFetchPager<TntCashCommission> pager = new WebFetchPager<TntCashCommission>(
				page, request) {

			@Override
			protected long getTotalCount(TntCashCommission t) {
				return tntCashaccountService.countCashCommission(t);
			}

			@Override
			protected List<TntCashCommission> fetchDetail(
					Page<TntCashCommission> page) {
				page = page.desc("CASH_COMMISSION_ID");
				page.setModel(Page.MODEL_FRONT);
				return tntCashaccountService.pageQueryCashCommission(page);
			}

		};
		return pager.fetch();
	}

	private Long getTntCashAccountId(HttpSession session) {
		TntCashAccount accountUser = tntCashaccountService
				.getAccountByUserId(getLoginUserId(session));
		if (accountUser == null) {
			return null;
		}
		return accountUser.getCashAccountId();
	}

	@RequestMapping(value = "/drawAjaxCheck.do")
	public void drawAjaxCheck(HttpServletRequest request,
			HttpServletResponse response, String drawAmountY, String payword)
			throws Exception {
		TntCashAccount accountUser = tntCashaccountService
				.getAccountByUserId(getLoginUserId(request.getSession()));
		if (StringUtil.isNotEmptyString(payword)) {
			if (!MD5.code(payword, MD5.KEY_TNT_USER_PASSWORD).equals(
					accountUser.getPaymentPassword())) {
				printUnique(request, response, new ResultMessage(false, ""));
				return;
			}
		}
		if (StringUtil.isNotEmptyString(drawAmountY)) {
			Long amount = PriceUtil.convertToFen(drawAmountY);
			if (amount > accountUser.getBalance()) {
				printUnique(request, response, new ResultMessage(false, ""));
				return;
			}
		}
		printUnique(request, response, new ResultMessage(true, ""));
	}

	@RequestMapping("/showAccount.do")
	public void showAccount(Model model, HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		TntAccount tntAccount = tntAccountService
				.getByAccountType(TntConstant.ACCOUNT_TYPE.CASHACCOUNT.name());
		JSONObject json = JSONObject.fromObject(tntAccount);
		PrintWriter writer = null;
		try {
			writer = resp.getWriter();
			writer.print(json);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
	}

	@RequestMapping("/check")
	public void check(Model model, HttpServletRequest req,
			HttpServletResponse resp, HttpSession session) throws IOException {
		ResultMessage result = new ResultMessage(false,"对不起，您暂时还不能下单！");
		TntUser user = getLoginUser(session);
		String error = isCanOrder(user);
		if(error!=null){
			result.setErrorText(error);
		}else{
			result.setSuccess(true);
		}
		super.printRtn(req, resp, result);
	}
	
	private String isCanOrder(TntUser user){
		String error = null;
		if(user==null){
			error = "对不起，您还未登录";
		}else{
			if(!TntConstant.USER_FINAL_STATUS.isDoing(user.getDetail().getFinalStatus())){
				error="尚未通过最终审核，不能下单，请联系运营";
			}else{
				Long userId =user.getUserId();
				TntCashAccount tntCashAccount = tntCashaccountService
						.getAccountByUserId(userId);
				if(tntCashAccount==null){
					error = "尚未绑定支付手机，不能下单";
				}			
			}
		}
		return error;
	}

	private void printUnique(final HttpServletRequest request,
			final HttpServletResponse response, final ResultMessage bean)
			throws IOException {
		response.setContentType("text/json; charset=gb2312");
		if (bean.isSuccess()) {
			response.getWriter().print("true");
		} else {
			response.getWriter().print("false");
		}
	}
}
