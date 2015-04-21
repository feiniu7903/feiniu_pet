package com.lvmama.tnt.back.recognizance;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lvmama.tnt.comm.util.WebFetchPager;
import com.lvmama.tnt.comm.util.web.BaseCommonController;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.ResultGod;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.comm.vo.TntConstant.COM_LOG_OBJECT_TYPE;
import com.lvmama.tnt.comm.vo.TntConstant.COM_LOG_TYPE;
import com.lvmama.tnt.recognizance.po.TntAccount;
import com.lvmama.tnt.recognizance.po.TntRecognizance;
import com.lvmama.tnt.recognizance.po.TntRecognizanceChange;
import com.lvmama.tnt.recognizance.service.TntAccountService;
import com.lvmama.tnt.recognizance.service.TntRecognizanceService;

@Controller
@RequestMapping("/recognizance")
public class RecognizanceController extends BaseCommonController {

	@Autowired
	private TntRecognizanceService tntRecognizanceService;

	@Autowired
	private TntAccountService tntAccountService;

	@RequestMapping(method = RequestMethod.POST)
	public String set(TntRecognizanceChange t, HttpServletResponse response) {
		tntRecognizanceService.set(t);
		return "redirect:recognizance/list";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public void recharge(TntRecognizanceChange t, HttpServletRequest request,
			HttpServletResponse response) {
		ResultGod<TntRecognizanceChange> result = tntRecognizanceService
				.recharge(t);
		String content = "申请为分销商" + t.getUserId() + "充值金额" + t.getAmountY();
		if (!result.isSuccess()) {
			content += "，充值失败，失败原因：" + result.getErrorText();
		}
		t = result.getResult();
		printRtn(request, response, result);
		insertComLog(request, response, content, "保证金充值",
				COM_LOG_TYPE.CHANGE_RECHARGE.name(), t.getChangeId(),
				COM_LOG_OBJECT_TYPE.TNT_RECOGNIZANCE_CHANGE.getCode());

	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void debit(TntRecognizanceChange t, HttpServletRequest request,
			HttpServletResponse response) {
		ResultGod<TntRecognizanceChange> result = tntRecognizanceService
				.debit(t);
		String content = "申请为分销商" + t.getUserId() + "扣除金额" + t.getAmountY()
				+ "，" + "扣除原因：" + t.getReason();
		if (!result.isSuccess()) {
			content += "，扣款失败，失败原因：" + result.getErrorText();
		}
		t = result.getResult();
		printRtn(request, response, result);
		insertComLog(request, response, content, "保证金扣款",
				COM_LOG_TYPE.CHANGE_RECHARGE.name(), t.getChangeId(),
				COM_LOG_OBJECT_TYPE.TNT_RECOGNIZANCE_CHANGE.getCode());
	}

	@RequestMapping(value = "/{userId}")
	public String showSetBox(@PathVariable Long userId, Model model) {
		TntRecognizanceChange t = new TntRecognizanceChange();
		t.setUserId(userId);
		TntRecognizance exist = tntRecognizanceService.getByUserId(userId);
		if (exist != null) {
			t.setAmount(exist.getLimits());
		}
		model.addAttribute(t);
		return "recognizance/set";
	}

	/** 分销商类型列表 **/
	@RequestMapping(value = "/account", method = RequestMethod.POST)
	public void account(TntAccount tntAccount, HttpServletResponse response) {
		tntAccount.setType(TntConstant.ACCOUNT_TYPE.RECOGNIZANCE.name());
		boolean flag = tntAccountService.setAccount(tntAccount);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(Boolean.toString(flag));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
	}

	/** 分销商保证金列表-运营视角 **/
	@RequestMapping(value = "/list")
	public String search(Model model, HttpServletRequest request,
			TntRecognizance t, Integer page) {
		int pageNo = page != null ? page : 1;
		Page<TntRecognizance> p = Page.page(pageNo, t);
		t.trim();
		List<TntRecognizance> tntRecognizanceList = list(p, request);
		model.addAttribute(tntRecognizanceList);
		model.addAttribute(Page.KEY, p);

		model.addAttribute(new TntRecognizanceChange());

		TntAccount tntAccount = tntAccountService.getLvmamaAccount();
		model.addAttribute("tntAccount", tntAccount);
		return "recognizance/list";
	}

	/** 分销商类型列表 **/
	@RequestMapping(value = "/details/{recognizanceId}")
	public String details(Model model, HttpServletRequest request,
			@PathVariable Long recognizanceId, Integer page) {
		int pageNo = page != null ? page : 1;
		TntRecognizanceChange t = new TntRecognizanceChange();
		t.setRecognizanceId(recognizanceId);
		Page<TntRecognizanceChange> p = Page.page(pageNo, t);
		p.desc("CREATE_TIME");
		List<TntRecognizanceChange> tntRecognizanceChangeList = listDetails(p,
				request);
		model.addAttribute(tntRecognizanceChangeList);
		model.addAttribute(Page.KEY, p);
		model.addAttribute("typeMap", TntRecognizance.TYPE.toMap());
		String userName = this.tntRecognizanceService
				.getUserNameByRecognizanceId(recognizanceId);
		model.addAttribute("userName", userName);
		return "recognizance/details";
	}

	private void changeList(TntRecognizanceChange t, String type, Integer page,
			HttpServletRequest request, Model model) {
		int pageNo = page != null ? page : 1;
		if (t == null) {
			t = new TntRecognizanceChange();
		}
		t.trim();
		t.setType(type);
		Page<TntRecognizanceChange> p = Page.page(pageNo, t);
		p.desc("CREATE_TIME");
		List<TntRecognizanceChange> tntRecognizanceChangeList = withUserList(p,
				request);
		model.addAttribute("tntRecognizanceChangeList",
				tntRecognizanceChangeList);
		model.addAttribute(Page.KEY, p);
		model.addAttribute("approveStatusMap",
				TntConstant.RECOGNIZANCE_CHANGE_STATUS.toMap());
		model.addAttribute("tntRecognizanceChange", t);
	}

	/** 充值单列表-运营视角 **/
	@RequestMapping(value = "/recharge/list")
	public String rechargeList(TntRecognizanceChange t, Integer page,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		changeList(t, TntRecognizance.TYPE.RECHARGE.getValue(), page, request,
				model);
		return "recognizance/recharge/list";
	}

	/** 扣款单列表-运营视角 **/
	@RequestMapping(value = "/debit/list")
	public String debitList(TntRecognizanceChange t, Integer page,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		changeList(t, TntRecognizance.TYPE.DEBIT.getValue(), page, request,
				model);
		return "recognizance/debit/list";
	}

	/** 修改-运营视角 **/
	@RequestMapping(value = "/edit/{changeId}")
	public String toEdit(@PathVariable Long changeId, Model model) {
		TntRecognizanceChange tntRecognizanceChange = tntRecognizanceService
				.getWithUser(new TntRecognizanceChange(changeId));
		model.addAttribute("tntRecognizanceChange", tntRecognizanceChange);
		if (tntRecognizanceChange.isDebit()) {
			return "recognizance/debit/edit";
		} else {
			return "recognizance/recharge/edit";
		}

	}

	/** 修改-运营视角 **/
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public void edit(TntRecognizanceChange t, HttpServletRequest request,
			HttpServletResponse response) {
		ResultGod<String> result = tntRecognizanceService.edit(t);
		String content = result.isSuccess() ? result.getResult() : result
				.getErrorText();
		insertComLog(request, response, content, "修改保证金详情记录",
				COM_LOG_TYPE.CHANGE_EDIT.name(), t.getChangeId(),
				COM_LOG_OBJECT_TYPE.TNT_RECOGNIZANCE_CHANGE.getCode());
		printRtn(request, response, result);
	}

	/** 提交确认-运营视角 **/
	@RequestMapping(value = "/confirm/{changeId}")
	public void confirm(@PathVariable Long changeId,
			HttpServletRequest request, HttpServletResponse response) {
		ResultGod<String> result = tntRecognizanceService.confirm(changeId);
		String content = result.isSuccess() ? result.getResult() : result
				.getErrorText();
		insertComLog(request, response, content, "提交确认保证金详情记录",
				COM_LOG_TYPE.CHANGE_CONFIRM.name(), changeId,
				COM_LOG_OBJECT_TYPE.TNT_RECOGNIZANCE_CHANGE.getCode());
		printRtn(request, response, result);
	}

	/** 作废-运营视角 **/
	@RequestMapping(value = "/cancel/{changeId}")
	public void cancel(@PathVariable Long changeId, HttpServletRequest request,
			HttpServletResponse response) {
		ResultGod<String> result = tntRecognizanceService
				.cancel(changeId, null);
		String content = result.isSuccess() ? result.getResult() : result
				.getErrorText();
		insertComLog(request, response, content, "废除保证金详情记录",
				COM_LOG_TYPE.CHANGE_CANCEL.name(), changeId,
				COM_LOG_OBJECT_TYPE.TNT_RECOGNIZANCE_CHANGE.getCode());
		printRtn(request, response, result);
	}

	protected List<TntRecognizance> list(Page<TntRecognizance> page,
			HttpServletRequest request) {
		WebFetchPager<TntRecognizance> pager = new WebFetchPager<TntRecognizance>(
				page, request) {

			@Override
			protected long getTotalCount(TntRecognizance t) {
				return tntRecognizanceService.count(t);
			}

			@Override
			protected List<TntRecognizance> fetchDetail(
					Page<TntRecognizance> page) {
				return tntRecognizanceService.pageQuery(page);
			}

		};
		return pager.fetch();
	}

	protected List<TntRecognizanceChange> listDetails(
			Page<TntRecognizanceChange> page, HttpServletRequest request) {
		WebFetchPager<TntRecognizanceChange> pager = new WebFetchPager<TntRecognizanceChange>(
				page, request) {

			@Override
			protected long getTotalCount(TntRecognizanceChange t) {
				return tntRecognizanceService.count(t);
			}

			@Override
			protected List<TntRecognizanceChange> fetchDetail(
					Page<TntRecognizanceChange> page) {
				page.desc("CHANGE_ID");
				return tntRecognizanceService.pageQueryDetail(page);
			}

		};
		return pager.fetch();
	}

	protected List<TntRecognizanceChange> withUserList(
			Page<TntRecognizanceChange> page, HttpServletRequest request) {
		WebFetchPager<TntRecognizanceChange> pager = new WebFetchPager<TntRecognizanceChange>(
				page, request) {

			@Override
			protected long getTotalCount(TntRecognizanceChange t) {
				return tntRecognizanceService.withUserCount(t);
			}

			@Override
			protected List<TntRecognizanceChange> fetchDetail(
					Page<TntRecognizanceChange> page) {
				page.desc("CHANGE_ID");
				return tntRecognizanceService.findWithUserPage(page);
			}

		};
		return pager.fetch();
	}

	/** 分销商保证金列表-财务视角 **/
	@RequestMapping(value = "/finance/list")
	public String financeSearch(Model model, HttpServletRequest request,
			TntRecognizance t, Integer page) {
		search(model, request, t, page);
		return "recognizance/financeList";
	}

	/** 充值单审核列表-财务视角 **/
	@RequestMapping(value = "/finance/recharge/list")
	public String financeRechargeList(TntRecognizanceChange t, Integer page,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if (t.getDealed() == null)
			t.setDealed(false);
		changeList(t, TntRecognizance.TYPE.RECHARGE.getValue(), page, request,
				model);
		return "recognizance/recharge/financeList";
	}

	/** 充值单审核列表-财务视角 **/
	@RequestMapping(value = "/finance/debit/list")
	public String financeDebitList(TntRecognizanceChange t, Integer page,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if (t.getDealed() == null)
			t.setDealed(false);
		changeList(t, TntRecognizance.TYPE.DEBIT.getValue(), page, request,
				model);
		return "recognizance/debit/financeList";
	}

	/** 审核 -财务视角 **/
	@RequestMapping(value = "/finance/approve", method = RequestMethod.PUT)
	public void approve(TntRecognizanceChange t, HttpServletRequest request,
			HttpServletResponse response) {
		t.setApprover(getUserName(request, response));
		ResultGod<String> result = tntRecognizanceService.approve(t);
		String content = result.isSuccess() ? result.getResult() : result
				.getErrorText();
		insertComLog(request, response, content, "审核保证金详情记录",
				COM_LOG_TYPE.CHANGE_APPROVE.name(), t.getChangeId(),
				COM_LOG_OBJECT_TYPE.TNT_RECOGNIZANCE_CHANGE.getCode());
		printRtn(request, response, result);
	}

}
