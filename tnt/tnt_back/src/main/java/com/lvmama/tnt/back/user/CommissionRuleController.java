package com.lvmama.tnt.back.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.comm.util.WebFetchPager;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.po.TntCommissionRule;
import com.lvmama.tnt.user.service.TntCommissionRuleService;

@Controller
@RequestMapping("/user/commission/rule")
public class CommissionRuleController {

	@Autowired
	private TntCommissionRuleService tntCommissionRuleService;

	/** 分销商类型列表 **/
	@RequestMapping(value = "/list")
	public String search(Model model, HttpServletRequest request,
			TntCommissionRule t, Integer page) {
		int pageNo = page != null ? page : 1;
		Page<TntCommissionRule> p = Page.page(pageNo, t);
		List<TntCommissionRule> tntCompanyTypeList = list(p, request);
		model.addAttribute(tntCompanyTypeList);
		model.addAttribute(Page.KEY, p);
		initMap(model);
		Map<String, String> tdSubProductTypeMap = getSubProductTypeMap();
		model.addAttribute("tdSubProductTypeMap", tdSubProductTypeMap);
		if (XIAN_LU_KEY.equals(t.getProductType())) {
			model.addAttribute("subProductTypeMap", tdSubProductTypeMap);
		}
		return "user/commission/rule/list";
	}

	/** 添加分销商返佣规则 **/
	@RequestMapping(method = RequestMethod.POST)
	public String add(TntCommissionRule tntCommissionRule) {
		insert(tntCommissionRule);
		return "redirect:rule/list";
	}

	/** 打开编辑分销商返佣规则 **/
	@RequestMapping(value = "edit/{commissionRuleId}")
	public String showEditBox(@PathVariable Long commissionRuleId, Model model) {
		TntCommissionRule tntCommissionRule = get(commissionRuleId);
		model.addAttribute(tntCommissionRule);
		initMap(model);
		if (tntCommissionRule != null) {
			model.addAttribute("subProductTypeMap", this
					.getSubProductTypeMap(tntCommissionRule.getProductType()));
		}
		return "user/commission/rule/edit";
	}

	/** 编辑分销商返佣规则 **/
	@RequestMapping(method = RequestMethod.PUT)
	public String edit(TntCommissionRule t) {
		modify(t);
		return "redirect:rule/list";
	}

	/** 打开分销商类型删除弹窗 **/
	@RequestMapping(value = "toDelete/{commissionRuleId}")
	public void showDeleteBox(@PathVariable Long commissionRuleId,
			HttpServletResponse response) {
		boolean flag = isCanDelete(commissionRuleId);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.print(flag);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
	}

	/** 删除分销商类型 **/
	@RequestMapping(method = RequestMethod.DELETE)
	public String delete(HttpServletRequest request) {
		Long commissionRuleId = TntUtil.parserLong(request
				.getParameter("commissionRuleId"));
		delete(commissionRuleId);
		return "redirect:rule/list";
	}

	/** 联动效果：改变渠道，联动查询分销商类型下拉 **/
	@RequestMapping(value = "changeProductType")
	public void changeProductType(
			@RequestParam(value = "productType", required = false) String productType,
			Model model, HttpServletResponse response) {

		if (productType != null) {
			PrintWriter writer = null;
			try {
				Map<String, String> map = getSubProductTypeMap(productType);
				List<String> list = getSubProductTypeIdANdNameList(map);
				if (list != null && !list.isEmpty()) {
					response.setContentType("text/json; charset=utf-8");
					String json = JSONArray.fromObject(list).toString();
					writer = response.getWriter();
					writer.print(json);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					writer.flush();
					writer.close();
				}
			}
		}
	}

	protected List<TntCommissionRule> list(Page<TntCommissionRule> page,
			HttpServletRequest request) {
		WebFetchPager<TntCommissionRule> pager = new WebFetchPager<TntCommissionRule>(
				page, request) {

			@Override
			protected long getTotalCount(TntCommissionRule t) {
				return tntCommissionRuleService.count(t);
			}

			@Override
			protected List<TntCommissionRule> fetchDetail(
					Page<TntCommissionRule> page) {
				return tntCommissionRuleService.pageQuery(page);
			}

		};
		return pager.fetch();
	}

	protected TntCommissionRule get(Long commissionRuleId) {
		return tntCommissionRuleService.getById(commissionRuleId);
	}

	protected boolean insert(TntCommissionRule t) {
		return tntCommissionRuleService.insert(t);
	}

	protected boolean modify(TntCommissionRule t) {
		return tntCommissionRuleService.update(t);
	}

	protected boolean isCanDelete(Long commissionRuleId) {
		return true;
	}

	protected boolean delete(Long commissionRuleId) {
		return tntCommissionRuleService.deleteById(commissionRuleId);
	}

	private void initMap(Model model) {
		model.addAttribute("productTypeMap", getProductTypeMap());
		model.addAttribute("payOnlineMap", getPayOnlineMap());
	}

	private final static String XIAN_LU_KEY = "3";

	protected Map<String, String> getProductTypeMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "门票");
		map.put("2", "酒店");
		map.put(XIAN_LU_KEY, "线路");
		return map;
	}

	protected Map<String, String> getSubProductTypeMap(String productType) {
		if (XIAN_LU_KEY.equals(productType)) {
			return getSubProductTypeMap();
		}
		return null;
	}

	private Map<String, String> getSubProductTypeMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "短途跟团游");
		map.put("2", "长途跟团游");
		map.put("3", "出境跟团游");
		map.put("4", "目的地自由行");
		map.put("5", "长途自由行");
		map.put("6", "出境自由行");
		map.put("7", "自助巴士班");
		return map;
	}

	protected List<String> getSubProductTypeIdANdNameList(
			Map<String, String> map) {
		List<String> list = null;
		if (map != null && !map.isEmpty()) {
			list = new ArrayList<String>();
			for (String key : map.keySet()) {
				list.add(key + ":" + map.get(key));
			}
		}
		return list;
	}

	protected Map<String, String> getPayOnlineMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "景区支付");
		map.put("2", "在线支付");
		return map;
	}
}
