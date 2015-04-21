package com.lvmama.tnt.back.product;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lvmama.tnt.comm.util.WebFetchPager;
import com.lvmama.tnt.comm.util.web.BaseCommonController;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.ResultGod;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.comm.vo.TntConstant.COM_LOG_OBJECT_TYPE;
import com.lvmama.tnt.comm.vo.TntConstant.COM_LOG_TYPE;
import com.lvmama.tnt.comm.vo.TntConstant.PROD_POLICY_TYPE;
import com.lvmama.tnt.prod.po.TntProdPolicy;
import com.lvmama.tnt.prod.po.TntProduct;
import com.lvmama.tnt.prod.service.TntProdPolicyService;
import com.lvmama.tnt.prod.service.TntProdProductService;
import com.lvmama.tnt.prod.service.TntProductService;
import com.lvmama.tnt.prod.vo.TntProdProduct;
import com.lvmama.tnt.user.po.TntCompanyType;
import com.lvmama.tnt.user.service.TntChannelService;
import com.lvmama.tnt.user.service.TntCompanyTypeService;

@Controller
@RequestMapping("/product/distset")
public class ProductDistSetController extends BaseCommonController {

	@Autowired
	private TntProdPolicyService tntProdPolicyService;

	@Autowired
	private TntProdProductService tntProdProductService;

	@Autowired
	private TntChannelService tntChannelService;

	@Autowired
	private TntProductService tntProductService;

	@Autowired
	private TntCompanyTypeService tntCompanyTypeService;

	/** 产品分销设置主页 **/
	@RequestMapping(value = "/{branchId}")
	public String index(@PathVariable Long branchId, Model model) {
		TntProdProduct prod = getByBranchId(branchId);
		model.addAttribute("tntProdProduct", prod);
		return "/product/distset/index";
	}

	/** 产品分销设置-根据渠道类型设置 **/
	@RequestMapping(value = "/channel/{branchId}")
	public String byChannel(@PathVariable Long branchId, Model model) {
		TntProdProduct prod = getByBranchId(branchId);
		if (prod != null) {
			model.addAttribute("tntProdProduct", prod);
			Map<Long, String> channelMap = tntChannelService.getMap();

			List<TntProdPolicy> list = tntProdPolicyService
					.listPolicyByBranchId(branchId, channelMap,
							prod.getProductId());
			buildBlack(list, prod);
			model.addAttribute("tntProdPolicyList", list);
			model.addAttribute("channelMap", channelMap);
		}
		return "/product/distset/byChannel";
	}

	@RequestMapping(value = "/channel/policy/{branchId}")
	public String toSetPolicy(@PathVariable Long branchId,
			@RequestParam Long channelId, Model model) {
		TntProdProduct prod = getByBranchId(branchId);
		if (prod != null) {
			model.addAttribute("tntProdProduct", prod);
			TntProdPolicy tntProdPolicy = tntProdPolicyService
					.getPolicyByBranchId(branchId, channelId,
							prod.getProductId());
			buildBlack(tntProdPolicy, prod);
			model.addAttribute("tntProdPolicy", tntProdPolicy);

			Map<Long, String> channelMap = tntChannelService.getMap();
			model.addAttribute("channelMap", channelMap);
		}
		return "/product/distset/set";
	}

	@RequestMapping(method = RequestMethod.POST)
	public void setPolicy(TntProdPolicy tntProdPolicy,
			HttpServletResponse response, HttpServletRequest request) {
		boolean flag = savePolicy(tntProdPolicy, request, response);
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

	/** 产品分销设置-根据渠道分销商设置 **/
	@RequestMapping(value = "/user")
	public String byUser(Model model,
			@RequestParam(required = false) Long branchId,
			HttpServletRequest request, TntProdPolicy t, Integer page) {
		if (t == null) {
			t = new TntProdPolicy();
			t.setBranchId(branchId);
		}
		TntProdProduct prod = getByBranchId(branchId);
		if (prod != null) {
			int pageNo = page != null ? page : 1;
			Page<TntProdPolicy> p = Page.page(pageNo, t);
			if (t.getUser() != null) {
				t.getUser().trim();
				// Long channelId = t.getChannelId();
				// TntCompanyType type = new TntCompanyType();
				// type.setChannelId(channelId);
				// Map<String, String> typeMap =
				// tntCompanyTypeService.map(type);
				// model.addAttribute("typeMap", typeMap);
			}
			List<TntProdPolicy> list = list(p, request);
			buildBlack(list, prod);
			model.addAttribute("tntProdPolicyList", list);
			model.addAttribute(Page.KEY, p);

			model.addAttribute("tntProdProduct", prod);
			Map<String, String> typeMap = tntCompanyTypeService
					.map(new TntCompanyType());
			model.addAttribute("companyTypeMap", typeMap);
			model.addAttribute("typeMap", typeMap);

			Map<Long, String> channelMap = tntChannelService.getMap();
			model.addAttribute("channelMap", channelMap);
		}
		return "/product/distset/byUser";
	}

	@RequestMapping(value = "/user/policy/{branchId}")
	public String toSetPolicy(@PathVariable Long branchId,
			@RequestParam Long userId, @RequestParam Long channelId,
			Model model, HttpServletRequest request) {
		TntProdProduct prod = getByBranchId(branchId);
		if (prod != null) {
			model.addAttribute("tntProdProduct", prod);
			TntProdPolicy tntProdPolicy = tntProdPolicyService
					.getPolicyByUserBranchId(branchId, userId, channelId,
							prod.getProductId());
			buildBlack(tntProdPolicy, prod);
			model.addAttribute("tntProdPolicy", tntProdPolicy);

			Map<Long, String> channelMap = tntChannelService.getMap();
			model.addAttribute("channelMap", channelMap);

			Map<String, String> typeMap = tntCompanyTypeService
					.map(new TntCompanyType());
			model.addAttribute("companyTypeMap", typeMap);
		}
		return "/product/distset/set";
	}

	protected List<TntProdPolicy> list(Page<TntProdPolicy> page,
			HttpServletRequest request) {
		WebFetchPager<TntProdPolicy> pager = new WebFetchPager<TntProdPolicy>(
				page, request) {

			@Override
			protected long getTotalCount(TntProdPolicy t) {
				return tntProdPolicyService.queryPolicyCount(t);
			}

			@Override
			protected List<TntProdPolicy> fetchDetail(Page<TntProdPolicy> page) {
				return tntProdPolicyService.queryPolicy(page);
			}

		};
		return pager.fetch();
	}

	private void buildBlack(List<TntProdPolicy> list, TntProdProduct prod) {
		if (list != null && !list.isEmpty()) {
			Map<Long, TntProduct> blackMap = tntProductService
					.getBlackListMapByBranchId(prod.getBranchId());
			for (TntProdPolicy t : list) {
				if (blackMap != null && blackMap.containsKey(t.getTargetId())) {
					t.setCanDist(false);
				}
				if (TntConstant.PRODUCT_PAY_TYPE.isPayToLvmama(prod
						.getPayType())) {
					t.setRebate(true);
				}
			}
		}
	}

	private void buildBlack(TntProdPolicy t, TntProdProduct prod) {
		if (t != null && t.getBranchId() != null) {
			boolean isBlack = tntProductService.isInBlack(t.getBranchId(),
					t.getChannelId());
			t.setCanDist(!isBlack);
			if (TntConstant.PRODUCT_PAY_TYPE.isPayToLvmama(prod.getPayType())) {
				t.setRebate(true);
			}
		}
	}

	private boolean savePolicy(TntProdPolicy tntProdPolicy,
			HttpServletRequest request, HttpServletResponse response) {
		tntProdPolicy.setProductType(TntConstant.PRODUCT_TYPE.TICKET.name());
		ResultGod<TntProdPolicy> result = tntProdPolicyService
				.saveOrUpdate(tntProdPolicy);
		boolean success = result.isSuccess();
		if (success) {
			try {
				tntProdPolicy = result.getResult();
				TntProduct t = new TntProduct();
				t.setChannelId(tntProdPolicy.getChannelId());
				t.setBranchId(tntProdPolicy.getBranchId());
				if (!tntProdPolicy.isCanDist()) {
					t.setProductId(tntProdPolicy.getProductId());
					tntProductService.pullToBlack(t);
				} else {
					tntProductService.pullFromBlack(t);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			boolean isProdChannel = TntConstant.PROD_TARGET_TYPE
					.isProdChannel(tntProdPolicy.getTargetType());
			String logType = null;
			String logName = null;
			if (isProdChannel) {
				logType = COM_LOG_TYPE.changeProdChannelPolicy.name();
				logName = "分销设置-产品渠道类型设置";
			} else {
				logType = COM_LOG_TYPE.changeProdDistributor.name();
				logName = "分销设置-产品渠道分销商设置";
			}
			String content = buildContent(tntProdPolicy);
			insertComLog(request, response, content, logName, logType,
					tntProdPolicy.getTntPolicyId(),
					COM_LOG_OBJECT_TYPE.TNT_POLICY.getCode());
		}
		return success;
	}

	private String buildContent(TntProdPolicy t) {
		StringBuffer sb = new StringBuffer();
		sb.append("分销价格设置：");
		PROD_POLICY_TYPE policyType = TntConstant.PROD_POLICY_TYPE.getByCode(t
				.getPolicyType());
		sb.append(policyType != null ? policyType.getCnName() : "" + "： ");
		sb.append("分销价=" + t.getPriceRule());
		sb.append("<br/>");
		sb.append("是否分销：");
		sb.append(t.isCanDist() ? "是" : "否");
		return sb.toString();
	}

	private TntProdProduct getByBranchId(Long branchId) {
		return tntProdProductService.getByBranchIdSure(branchId);
	}
}
