package com.lvmama.tnt.front.user.space;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lvmama.tnt.comm.util.WebFetchPager;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.front.BaseController;
import com.lvmama.tnt.user.po.TntContract;
import com.lvmama.tnt.user.service.TntContractService;

@Controller
@RequestMapping(value = "/user")
public class PactController extends BaseController {

	@Autowired
	private TntContractService tntContractService;

	@RequestMapping("/pact.do")
	public String list(Model model, Integer page, HttpServletRequest request,
			HttpSession session) {
		Long userId = getLoginUserId(session);
		if (userId != null) {
			TntContract t = new TntContract();
			t.setUserId(userId);
			t.setStatus(TntContract.CONTACT_STATUS.ACTIVATE.getValue());
			int pageNo = page != null ? page : 1;
			Page<TntContract> p = Page.page(pageNo, t);
			List<TntContract> tntContractList = list(p, request);
			model.addAttribute(Page.KEY, p);
			model.addAttribute(tntContractList);
		}
		return "userspace/userinfo/pact";
	}

	@RequestMapping("/queryPactList.do")
	public String queryPactList(Model model, Integer page,
			HttpServletRequest request, HttpSession session) {
		list(model, page, request, session);
		return "userspace/userinfo/queryPactList";
	}

	protected List<TntContract> list(Page<TntContract> page,
			HttpServletRequest request) {
		WebFetchPager<TntContract> pager = new WebFetchPager<TntContract>(page,
				request) {

			@Override
			protected long getTotalCount(TntContract t) {
				return tntContractService.count(t);
			}

			@Override
			protected List<TntContract> fetchDetail(Page<TntContract> page) {
				page.setModel(Page.MODEL_FRONT);
				page.desc("contract.CONTRACT_ID");
				return tntContractService.pageQuery(page);
			}
		};
		return pager.fetch();
	}
}
