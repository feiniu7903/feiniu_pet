package com.lvmama.tnt.back.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lvmama.tnt.comm.util.WebFetchPager;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.prod.po.TntProdPolicy;
import com.lvmama.tnt.prod.service.TntProdPolicyService;
import com.lvmama.tnt.user.po.TntAnnouncement;
import com.lvmama.tnt.user.po.TntCompanyType;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.po.TntUserDetail;
import com.lvmama.tnt.user.service.TntAnnouncementService;
import com.lvmama.tnt.user.service.TntCompanyTypeService;
import com.lvmama.tnt.user.service.TntUserService;

@Controller
@RequestMapping("/user/distributor")
public class DistributorController {
	
	@Autowired
	private TntProdPolicyService tntProdPolicyService;
	
	@Autowired
	private TntCompanyTypeService tntCompanyTypeService;
	
	/** 分销商列表 **/
	@RequestMapping(value = "/list")
	public String showDistributors(Model model, HttpServletRequest request,
			TntProdPolicy t, Integer page) {
		if (t.getUser() != null) {
			t.getUser().trim();
		}
		int pageNo = page != null ? page : 1;
		Page<TntProdPolicy> p = Page.page(pageNo, t);
		List<TntProdPolicy> tntDistributorList = list(p, request);
		model.addAttribute(tntDistributorList);
		model.addAttribute(Page.KEY, p);
		
		Map<String, String> typeMap = tntCompanyTypeService
				.map(new TntCompanyType());
		model.addAttribute("companyTypeMap", typeMap);
		model.addAttribute("typeMap", typeMap);
		return "/product/policy/distPolicyList";
	}
	
	protected List<TntProdPolicy> list(Page<TntProdPolicy> page,
			HttpServletRequest request) {
		WebFetchPager<TntProdPolicy> pager = new WebFetchPager<TntProdPolicy>(
				page, request) {

			@Override
			protected long getTotalCount(TntProdPolicy t) {
				return tntProdPolicyService.queryDistPolicyCount(t);
			}

			@Override
			protected List<TntProdPolicy> fetchDetail(Page<TntProdPolicy> page) {
				return tntProdPolicyService.queryDistPolicy(page);
			}

		};
		return pager.fetch();
	}
}
