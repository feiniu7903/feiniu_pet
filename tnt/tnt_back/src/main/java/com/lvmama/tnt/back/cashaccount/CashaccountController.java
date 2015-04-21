package com.lvmama.tnt.back.cashaccount;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lvmama.tnt.cashaccount.po.TntCashAccount;
import com.lvmama.tnt.cashaccount.po.TntCashCommission;
import com.lvmama.tnt.cashaccount.po.TntCashFreezeQueue;
import com.lvmama.tnt.cashaccount.po.TntCashMoneyDraw;
import com.lvmama.tnt.cashaccount.po.TntCashPay;
import com.lvmama.tnt.cashaccount.po.TntCashRecharge;
import com.lvmama.tnt.cashaccount.po.TntCashRefundment;
import com.lvmama.tnt.cashaccount.service.TntCashaccountService;
import com.lvmama.tnt.comm.util.WebFetchPager;
import com.lvmama.tnt.comm.util.web.BaseCommonController;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.comm.vo.TntConstant.COM_LOG_OBJECT_TYPE;
import com.lvmama.tnt.comm.vo.TntConstant.COM_LOG_TYPE;
import com.lvmama.tnt.recognizance.po.TntAccount;
import com.lvmama.tnt.recognizance.service.TntAccountService;

@Controller
@RequestMapping("/cashaccount")
public class CashaccountController extends BaseCommonController {

	@Autowired
	private TntCashaccountService tntCashaccountService;
	
	@Autowired
	private TntAccountService tntAccountService;
	
	@RequestMapping(value="/editAccount.do",method = RequestMethod.GET)
	public String eidtAccount( Model model) {
		TntAccount tntAccount = tntAccountService.getByAccountType(TntConstant.ACCOUNT_TYPE.CASHACCOUNT.name());
		if(tntAccount==null){
			tntAccount = new TntAccount();
		}
		model.addAttribute("tntAccount",tntAccount);
		return "/cashaccount/editAccount";
	}
	
	@RequestMapping(value="/saveAccount.do",method = RequestMethod.POST)
	public void saveAccount(TntAccount tntAccount, Model model,HttpServletResponse response,HttpServletRequest request) {
		tntAccount.setType(TntConstant.ACCOUNT_TYPE.CASHACCOUNT.name());
		tntAccountService.save(tntAccount);
	}
	@RequestMapping(value="/addMoney.do",method = RequestMethod.POST)
	public void addMoney(TntCashRecharge t, HttpServletResponse response,HttpServletRequest request) {
		t.setStatus(TntConstant.CASH_RECHARGE_STATUS.PENDING_AUDIT.name());
		Long rechargeId = tntCashaccountService.addRecharge(t);
		insertComLog(request, response, "充值原因:"+t.getReason(), "充值申请",
				COM_LOG_TYPE.ACCOUNT_RECHARGE.name(), rechargeId,
				COM_LOG_OBJECT_TYPE.TNT_ACCOUNT.getCode());
	}
	
	@RequestMapping(value="/updateRecharge.do",method = RequestMethod.POST)
	public void updateRecharge(TntCashRecharge tntCashRecharge, HttpServletResponse response,HttpServletRequest request) {
		tntCashRecharge.setStatus(TntConstant.CASH_RECHARGE_STATUS.DOUBLE_AUDIT.name());
		tntCashaccountService.updateRecharge(tntCashRecharge);
		insertComLog(request, response, "充值重新申请", "充值重申",
				COM_LOG_TYPE.ACCOUNT_RECHARGE.name(), tntCashRecharge.getCashRechargeId(),
				COM_LOG_OBJECT_TYPE.TNT_ACCOUNT.getCode());
	}
	
	@RequestMapping(value="/passRecharge.do",method = RequestMethod.POST)
	public void passRecharge(TntCashRecharge t, HttpServletResponse response,HttpServletRequest request) {
		t.setStatus(TntConstant.CASH_RECHARGE_STATUS.PENDING_AUDIT.name());
		tntCashaccountService.passRecharge(t);
		insertComLog(request, response, "充值审核通过", "充值审核",
				COM_LOG_TYPE.ACCOUNT_RECHARGE.name(), t.getCashRechargeId(),
				COM_LOG_OBJECT_TYPE.TNT_ACCOUNT.getCode());
	}
	
	@RequestMapping(value="/unPassRecharge.do",method = RequestMethod.POST)
	public void unPassRecharge(TntCashRecharge t, HttpServletResponse response,HttpServletRequest request) {
		t.setStatus(TntConstant.CASH_RECHARGE_STATUS.PENDING_AUDIT.name());
		tntCashaccountService.unPassRecharge(t);
		insertComLog(request, response, "充值打回原因:" + t.getReason(), "充值审核",
				COM_LOG_TYPE.ACCOUNT_RECHARGE.name(), t.getCashRechargeId(),
				COM_LOG_OBJECT_TYPE.TNT_ACCOUNT.getCode());
	}
	
	@RequestMapping(value="/addCommission.do",method = RequestMethod.POST)
	public void addCommission(TntCashCommission t, HttpServletResponse response,HttpServletRequest request) {
		Long commId = tntCashaccountService.addCommission(t);
		insertComLog(request, response, "添加返佣", "返佣操作",
				COM_LOG_TYPE.ACCOUNT_COMMISSION.name(), commId,
				COM_LOG_OBJECT_TYPE.TNT_ACCOUNT.getCode());
	}

	@RequestMapping(value="/addFreeze.do",method = RequestMethod.POST)
	public void addFreeze(TntCashFreezeQueue t, HttpServletResponse response,HttpServletRequest request) {
		Long freezeId = tntCashaccountService.addCashFreeze(t);
		insertComLog(request, response, "冻结原因："+t.getReason(), "申请冻结",
				COM_LOG_TYPE.ACCOUNT_FREEZE.name(), freezeId,
				COM_LOG_OBJECT_TYPE.TNT_ACCOUNT.getCode());
	}
	
	@RequestMapping(value="/releaseFreeze.do",method = RequestMethod.POST)
	public void releaseFreeze(TntCashFreezeQueue t, HttpServletResponse response,HttpServletRequest request) {
		tntCashaccountService.releaseFreeze(t);
		insertComLog(request, response, "解冻确认：", "解冻",
				COM_LOG_TYPE.ACCOUNT_FREEZE.name(), t.getFreezeQueueId(),
				COM_LOG_OBJECT_TYPE.TNT_ACCOUNT.getCode());
	}
	
	@RequestMapping(value="/updateFreeze.do",method = RequestMethod.POST)
	public void updateFreeze(TntCashFreezeQueue tntCashFreezeQueue, HttpServletResponse response,HttpServletRequest request) {
		tntCashFreezeQueue.setStatus(TntConstant.FREEZE_STATUS.WAIT_RELEASE.name());
		tntCashaccountService.updateFreeze(tntCashFreezeQueue);
		insertComLog(request, response, "解冻原因："+tntCashFreezeQueue.getReason(), "申请解冻",
				COM_LOG_TYPE.ACCOUNT_FREEZE.name(), tntCashFreezeQueue.getFreezeQueueId(),
				COM_LOG_OBJECT_TYPE.TNT_ACCOUNT.getCode());
	}
	
	@RequestMapping(value="/updateDraw.do",method = RequestMethod.POST)
	public void updateDraw(TntCashMoneyDraw t, HttpServletResponse response,HttpServletRequest request) {
		t.setAuditStatus(TntConstant.DRAW_AUDIT_STATUS.PASS_AUDIT.name());
		tntCashaccountService.updateCashMoneyDraw(t);
		insertComLog(request, response, "提现处理", "提现处理",
				COM_LOG_TYPE.ACCOUNT_DRAW.name(), t.getMoneyDrawId(),
				COM_LOG_OBJECT_TYPE.TNT_ACCOUNT.getCode());
	}
	
	@RequestMapping(value="/releaseDraw.do",method = RequestMethod.POST)
	public void releaseDraw(Long drawId, HttpServletResponse response,HttpServletRequest request) {
		tntCashaccountService.releaseCashMoneyDraw(drawId);
		insertComLog(request, response, "提现处理", "提现处理",
				COM_LOG_TYPE.ACCOUNT_DRAW.name(), drawId,
				COM_LOG_OBJECT_TYPE.TNT_ACCOUNT.getCode());
	}
	
	/** 分销商预存款账户列表 **/
	@RequestMapping(value = "/search.do")
	public String distSearch(Model model, HttpServletRequest request,
			TntCashAccount t, Integer page) {
		int pageNo = page != null ? page : 1;
		Page<TntCashAccount> p = Page.page(pageNo, t);
		List<TntCashAccount> tntCashAccountList = list(p, request);
		model.addAttribute(tntCashAccountList);
		model.addAttribute(Page.KEY, p);
		if(t==null){
			t = new TntCashAccount();
		}
		model.addAttribute(t);
		model.addAttribute(new TntCashRecharge());
		model.addAttribute(new TntCashCommission());
		model.addAttribute(new TntCashFreezeQueue());
		model.addAttribute("productTypes",TntConstant.PRODUCT_TYPE.values());
		return "/dist_cashaccount/list";
	}
	
	/** 分销商预存款账户列表 **/
	@RequestMapping(value = "/list.do")
	public String search(Model model, HttpServletRequest request,
			TntCashAccount t, Integer page) {
		int pageNo = page != null ? page : 1;
		Page<TntCashAccount> p = Page.page(pageNo, t);
		List<TntCashAccount> tntCashAccountList = list(p, request);
		model.addAttribute(tntCashAccountList);
		model.addAttribute(Page.KEY, p);
		if(t==null){
			t = new TntCashAccount();
		}
		TntCashMoneyDraw tmd = new TntCashMoneyDraw();
		tmd.setAuditStatus(TntConstant.DRAW_AUDIT_STATUS.PENDING_AUDIT.name());
		long totalCount = tntCashaccountService.countCashMoneyDraw(tmd);
		model.addAttribute(t);
		model.addAttribute(new TntCashRecharge());
		model.addAttribute(new TntCashCommission());
		model.addAttribute(new TntCashFreezeQueue());
		model.addAttribute("totalCount",totalCount);
		model.addAttribute("productTypes",TntConstant.PRODUCT_TYPE.values());
		
		TntAccount tntAccount = tntAccountService.getByAccountType(TntConstant.ACCOUNT_TYPE.CASHACCOUNT.name());
		if(tntAccount==null){
			tntAccount = new TntAccount();
		}
		model.addAttribute("tntAccount", tntAccount);
		
		return "/cashaccount/list";
	}
	
	protected List<TntCashAccount> list(Page<TntCashAccount> page,
			HttpServletRequest request) {
		WebFetchPager<TntCashAccount> pager = new WebFetchPager<TntCashAccount>(
				page, request) {

			@Override
			protected long getTotalCount(TntCashAccount t) {
				return tntCashaccountService.count(t);
			}

			@Override
			protected List<TntCashAccount> fetchDetail(
					Page<TntCashAccount> page) {
					page = page.desc("CASH_ACCOUNT_ID");
				return tntCashaccountService.pageQuery(page);
			}

		};
		return pager.fetch();
	}
	
	/** 分销商预存款账户列表 **/
	@RequestMapping(value = "/editMoneyDraw.do")
	public String editMoneyDraw(Model model, HttpServletRequest request, Long drawId) {
		TntCashMoneyDraw tmd = tntCashaccountService.findCashMoneyDrawById(drawId);
		model.addAttribute("tntCashMoneyDraw",tmd);
		return "/cashaccount/list/editMoneyDraw";
	}
	protected List<TntCashPay> queryCashPay(Page<TntCashPay> page,
			HttpServletRequest request) {
		WebFetchPager<TntCashPay> pager = new WebFetchPager<TntCashPay>(
				page, request) {

			@Override
			protected long getTotalCount(TntCashPay t) {
				return tntCashaccountService.countCashPay(t);
			}

			@Override
			protected List<TntCashPay> fetchDetail(
					Page<TntCashPay> page) {
				page = page.desc("CASH_PAY_ID");
				return tntCashaccountService.pageQueryCashPay(page);
			}

		};
		return pager.fetch();
	}
	
	
	protected List<TntCashRecharge> queryCashRecharge(Page<TntCashRecharge> page,
			HttpServletRequest request) {
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
				return tntCashaccountService.pageQueryCashRecharge(page);
			}

		};
		return pager.fetch();
	}
	
	
	
	protected List<TntCashRefundment> queryCachRefundment(Page<TntCashRefundment> page,
			HttpServletRequest request) {
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
				return tntCashaccountService.pageQueryCashRefundment(page);
			}

		};
		return pager.fetch();
	}

	

	/** 分销商预存款账户列表 **/
	@RequestMapping(value = "/showCashMoneyDraw.do")
	public String showCashMoneyDraw(Model model, HttpServletRequest request,
			TntCashMoneyDraw t, Integer page) {
		int pageNo = page != null ? page : 1;
		Page<TntCashMoneyDraw> p = Page.page(pageNo, t);
		t.setAuditStatus(TntConstant.DRAW_AUDIT_STATUS.PENDING_AUDIT.name());
		List<TntCashMoneyDraw> tntMoneyDrawList = queryCashMoneyDraw(p, request);
		model.addAttribute("tntMoneyDrawList",tntMoneyDrawList);
		model.addAttribute(Page.KEY, p);
		model.addAttribute(t);
		model.addAttribute(new TntCashMoneyDraw());
		return "/cashaccount/list/moneyDraw";
	}
	
	
	protected List<TntCashMoneyDraw> queryCashMoneyDraw(Page<TntCashMoneyDraw> page,
			HttpServletRequest request) {
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
				return tntCashaccountService.pageQueryCashMoneyDraw(page);
			}

		};
		return pager.fetch();
	}
	
	
	protected List<TntCashCommission> queryCashCommission(Page<TntCashCommission> page,
			HttpServletRequest request) {
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
				return tntCashaccountService.pageQueryCashCommission(page);
			}

		};
		return pager.fetch();
	}
	
	
	
	protected List<TntCashFreezeQueue> queryFreeze(Page<TntCashFreezeQueue> page,
			HttpServletRequest request) {
		WebFetchPager<TntCashFreezeQueue> pager = new WebFetchPager<TntCashFreezeQueue>(
				page, request) {

			@Override
			protected long getTotalCount(TntCashFreezeQueue t) {
				return tntCashaccountService.countCashFreeze(t);
			}

			@Override
			protected List<TntCashFreezeQueue> fetchDetail(
					Page<TntCashFreezeQueue> page) {
				page = page.desc("FREEZE_QUEUE_ID");
				return tntCashaccountService.pageQueryCashFreeze(page);
			}

		};
		return pager.fetch();
	}
	
	@RequestMapping(value="/{path}/showDetail.do")
	public String showDetail(@PathVariable String path,Model model,Long  cashAccountId) {
		model.addAttribute("cashAccountId", cashAccountId);
		if("dist".equals(path)){
			return "/dist_cashaccount/list/detailDiv";
		}
		return "/cashaccount/list/detailDiv";
	}
	/** 分销商预存款账户列表 **/
	@RequestMapping(value = "/{path}/queryCashRefundment.do")
	public String queryCachfundment(@PathVariable String path,Model model, HttpServletRequest request,
			TntCashRefundment t, Integer page) {
		int pageNo = page != null ? page : 1;
		Page<TntCashRefundment> p = Page.page(pageNo, t);
		List<TntCashRefundment> tntCashRefundmentList = queryCachRefundment(p, request);
		model.addAttribute(tntCashRefundmentList);
		model.addAttribute(Page.KEY, p);
		if("dist".equals(path)){
			return "/dist_cashaccount/list/cashRefundment";
		}
		return "/cashaccount/list/cashRefundment";
	}
	
	/** 分销商预存款账户列表 **/
	@RequestMapping(value = "/{path}/queryCashPay.do")
	public String queryCashPay(@PathVariable String path, Model model, HttpServletRequest request,
			TntCashPay t, Integer page) {
		int pageNo = page != null ? page : 1;
		Page<TntCashPay> p = Page.page(pageNo, t);
		List<TntCashPay> tntCashPayList = queryCashPay(p, request);
		model.addAttribute(tntCashPayList);
		model.addAttribute(Page.KEY, p);
		if("dist".equals(path)){
			return "/dist_cashaccount/list/cashPay";
		}
		return "/cashaccount/list/cashPay";
	}
	
	/** 分销商预存款充值列表 **/
	@RequestMapping(value = "/{path}/queryCashRecharge")
	public String queryCashRecharge(@PathVariable String path,Model model, HttpServletRequest request,
			TntCashRecharge t, Integer page) {
		int pageNo = page != null ? page : 1;
		Page<TntCashRecharge> p = Page.page(pageNo, t);
		List<TntCashRecharge> tntCashRechargeList = queryCashRecharge(p, request);
		model.addAttribute(tntCashRechargeList);
		model.addAttribute(Page.KEY, p);
		TntCashAccount tcp = new TntCashAccount();
		tcp.setCashAccountId(t.getCashAccountId());
		Page<TntCashAccount> cp = Page.page(pageNo, tcp);
		List<TntCashAccount> cashRechargeList = tntCashaccountService.pageQuery(cp);
		if(cashRechargeList!=null && cashRechargeList.size()>0){
			model.addAttribute("realName",cashRechargeList.get(0).getRealName());
		}
		if("dist".equals(path)){
			return "/dist_cashaccount/list/cashRecharge";
		}
		return "/cashaccount/list/cashRecharge";
	}

	/** 分销商预存款账户列表 **/
	@RequestMapping(value = "/{path}/queryCashMoneyDraw.do")
	public String queryCashMoneyDraw(@PathVariable String path,Model model, HttpServletRequest request,
			TntCashMoneyDraw t, Integer page) {
		int pageNo = page != null ? page : 1;
		Page<TntCashMoneyDraw> p = Page.page(pageNo, t);
		List<TntCashMoneyDraw> tntCashMoneyDrawList = queryCashMoneyDraw(p, request);
		model.addAttribute(tntCashMoneyDrawList);
		model.addAttribute(Page.KEY, p);
		if("dist".equals(path)){
			return "/dist_cashaccount/list/cashMoneyDraw";
		}
		return "/cashaccount/list/cashMoneyDraw";
	}
	/** 分销商返佣列表 **/
	@RequestMapping(value = "/{path}/queryCashCommission.do")
	public String queryCashCommission(@PathVariable String path,Model model, HttpServletRequest request,
			TntCashCommission t, Integer page) {
		int pageNo = page != null ? page : 1;
		Page<TntCashCommission> p = Page.page(pageNo, t);
		List<TntCashCommission> tntCashCommissionList = queryCashCommission(p, request);
		model.addAttribute(tntCashCommissionList);
		model.addAttribute(Page.KEY, p);
		if("dist".equals(path)){
			return "/dist_cashaccount/list/cashCommission";
		}
		return "/cashaccount/list/cashCommission";
	}
	/** 分销商返佣列表 **/
	@RequestMapping(value = "/{path}/queryFreeze.do")
	public String queryFreeze(@PathVariable String path,Model model, HttpServletRequest request,
			TntCashFreezeQueue t, Integer page) {
		int pageNo = page != null ? page : 1;
		Page<TntCashFreezeQueue> p = Page.page(pageNo, t);
		List<TntCashFreezeQueue> tntCashFreezeQueueList = queryFreeze(p, request);
		model.addAttribute(tntCashFreezeQueueList);
		model.addAttribute(Page.KEY, p);
		if("dist".equals(path)){
			return "/dist_cashaccount/list/cashFreeze";
		}
		return "/cashaccount/list/cashFreeze";
	}
	
	/** 分销商预存款充值列表 **/
	@RequestMapping(value = "/dist/queryCashRechargeList.do",method = {RequestMethod.POST,RequestMethod.GET})
	public String queryDistCashRechargeList(Model model, HttpServletRequest request,
			TntCashAccount tntCashAccount, Integer page) {
		if(tntCashAccount==null){
			tntCashAccount = new TntCashAccount();
		}
		model.addAttribute(tntCashAccount);
		TntCashRecharge c = new TntCashRecharge();
		c.getTntUser().setUserName(tntCashAccount.getUserName());
		c.getTntUser().setRealName(tntCashAccount.getRealName());
		c.setStatus(tntCashAccount.getValid());
		int pageNo = page != null ? page : 1;
		Page<TntCashRecharge> p = Page.page(pageNo, c);
		List<TntCashRecharge> tntCashRechargeList = queryCashRecharge(p, request);
		model.addAttribute(tntCashRechargeList);
		model.addAttribute(Page.KEY, p);
		model.addAttribute("rechargeStats",TntConstant.CASH_RECHARGE_STATUS.values());
		model.addAttribute("tntCashRecharge",new TntCashRecharge());
		return "/dist_cashaccount/cashRechargeList";
	}
	
	/** 分销商返佣列表 **/
	@RequestMapping(value = "/dist/queryFreezeList.do",method = {RequestMethod.POST,RequestMethod.GET})
	public String queryDistFreezeList(Model model, HttpServletRequest request,
			TntCashAccount tntCashAccount, Integer page) {
		if(tntCashAccount==null){
			tntCashAccount = new TntCashAccount();
		}
		model.addAttribute(tntCashAccount);
		TntCashFreezeQueue c = new TntCashFreezeQueue();
		c.getTntUser().setUserName(tntCashAccount.getUserName());
		c.getTntUser().setRealName(tntCashAccount.getRealName());
		c.setStatus(tntCashAccount.getValid());
		int pageNo = page != null ? page : 1;
		Page<TntCashFreezeQueue> p = Page.page(pageNo, c);
		List<TntCashFreezeQueue> tntCashFreezeQueueList = queryFreeze(p, request);
		model.addAttribute(tntCashFreezeQueueList);
		model.addAttribute(Page.KEY, p);
		model.addAttribute(new TntCashFreezeQueue());
		model.addAttribute("freezeStats",TntConstant.FREEZE_STATUS.values());
		return "/dist_cashaccount/cashFreezeList";
	}
	
	/** 分销商预存款充值列表 **/
	@RequestMapping(value = "/fin/{path}/queryCashRechargeList.do")
	public String queryCashRechargeList(@PathVariable String path,Model model, HttpServletRequest request,
			TntCashRecharge t, Integer page) {
		t.setIsWaiting("true");
		
		if("end".equalsIgnoreCase(path)){
			t.setIsWaiting("false");
			model.addAttribute("end","class=\"active\"");
		}else{
			model.addAttribute("beg","class=\"active\"");
		}
		int pageNo = page != null ? page : 1;
		Page<TntCashRecharge> p = Page.page(pageNo, t);
		List<TntCashRecharge> tntCashRechargeList = queryCashRecharge(p, request);
		model.addAttribute(tntCashRechargeList);
		model.addAttribute(Page.KEY, p);
		model.addAttribute("tntCashRecharge", new TntCashRecharge());
		return "/cashaccount/cashRechargeList";
	}
	
	/** 分销商返佣列表 **/
	@RequestMapping(value = "/fin/{path}/queryFreezeList.do")
	public String queryFreezeList(@PathVariable String path,Model model, HttpServletRequest request,
			TntCashFreezeQueue t, Integer page) {
		t.setIsWaiting("true");
		if("end".equalsIgnoreCase(path)){
			t.setIsWaiting("false");
			model.addAttribute("end","class=\"active\"");
		}else{
			model.addAttribute("beg","class=\"active\"");
		}
		int pageNo = page != null ? page : 1;
		Page<TntCashFreezeQueue> p = Page.page(pageNo, t);
		List<TntCashFreezeQueue> tntCashFreezeQueueList = queryFreeze(p, request);
		model.addAttribute(tntCashFreezeQueueList);
		model.addAttribute(Page.KEY, p);
		return "/cashaccount/cashFreezeList";
	}
}