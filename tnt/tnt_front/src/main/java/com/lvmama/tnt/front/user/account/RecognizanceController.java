package com.lvmama.tnt.front.user.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lvmama.tnt.comm.util.WebFetchPager;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.front.BaseController;
import com.lvmama.tnt.recognizance.po.TntAccount;
import com.lvmama.tnt.recognizance.po.TntRecognizance;
import com.lvmama.tnt.recognizance.po.TntRecognizanceChange;
import com.lvmama.tnt.recognizance.service.TntAccountService;
import com.lvmama.tnt.recognizance.service.TntRecognizanceService;

@Controller
@RequestMapping(value = "/user")
public class RecognizanceController extends BaseController {

	@Autowired
	private TntRecognizanceService tntRecognizanceService;

	@Autowired
	private TntAccountService tntAccountService;

	@RequestMapping("/recognizance.do")
	public String list(Model model, Integer page, HttpServletRequest request,
			HttpSession session) {
		Long userId = getLoginUserId(session);
		if (userId != null) {
			TntRecognizance t = tntRecognizanceService.getByUserId(userId);
			if (t != null) {
				model.addAttribute(t);
				int pageNo = page != null ? page : 1;
				TntRecognizanceChange tc = new TntRecognizanceChange();
				tc.setRecognizanceId(t.getRecognizanceId());
				tc.setNotType(TntRecognizance.TYPE.SETLIMIT.getValue());
				Page<TntRecognizanceChange> p = Page.page(pageNo, tc);
				List<TntRecognizanceChange> tntRecognizanceChangeList = list(p,
						request);
				model.addAttribute(Page.KEY, p);
				model.addAttribute(tntRecognizanceChangeList);
				model.addAttribute("approveStatusMap",
						TntConstant.RECOGNIZANCE_CHANGE_STATUS.toMap());
			}
		}
		return "userspace/account/recognizance";
	}

	@RequestMapping("/queryRecognizanceList.do")
	public String queryPactList(Model model, Integer page,
			HttpServletRequest request, HttpSession session) {
		list(model, page, request, session);
		return "userspace/account/queryRecognizanceList";
	}

	@RequestMapping("/showAccount.do")
	public void showAccount(Model model, HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		TntAccount tntAccount = tntAccountService.getLvmamaAccount();
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

	protected List<TntRecognizanceChange> list(
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
				page.setModel(Page.MODEL_FRONT);
				page.desc("CHANGE_ID");
				return tntRecognizanceService.pageQueryDetail(page);
			}
		};
		return pager.fetch();
	}
}
