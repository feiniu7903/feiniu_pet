package com.lvmama.tnt.back.user;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.lvmama.tnt.user.po.TntChannel;
import com.lvmama.tnt.user.po.TntCompanyType;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.po.TntUserDetail;
import com.lvmama.tnt.user.service.TntChannelService;
import com.lvmama.tnt.user.service.TntCompanyTypeService;
import com.lvmama.tnt.user.service.TntCompanyTypeUserService;
import com.lvmama.tnt.user.service.TntUserService;

@Controller
@RequestMapping("/user/type")
public class TypeController {

	@Autowired
	private TntUserService tntUserService;

	@Autowired
	private TntCompanyTypeService tntCompanyTypeService;

	@Autowired
	private TntCompanyTypeUserService tntCompanyTypeUserService;

	@Autowired
	private TntChannelService tntChannelService;

	/** 分销商类型列表 **/
	@RequestMapping(value = "/list")
	public String search(Model model, HttpServletRequest request,
			TntCompanyType t, Integer page) {
		int pageNo = page != null ? page : 1;
		Page<TntCompanyType> p = Page.page(pageNo, t);
		List<TntCompanyType> tntCompanyTypeList = list(p, request);
		model.addAttribute(tntCompanyTypeList);
		model.addAttribute("channelMap", getChannelMap());
		model.addAttribute(Page.KEY, p);
		model.addAttribute(new TntChannel());
		Long channelId = t.getChannelId();
		if (channelId != null) {
			TntCompanyType type = new TntCompanyType();
			type.setChannelId(channelId);
			Map<String, String> typeMap = tntCompanyTypeService.map(type);
			model.addAttribute("typeMap", typeMap);
		}
		return "user/type/list";
	}

	/** 添加分销商类型 **/
	@RequestMapping(method = RequestMethod.POST)
	public String add(TntCompanyType tntCompanyType) {
		insert(tntCompanyType);
		return "redirect:type/list";
	}

	/** 打开编辑分销商类型弹窗 **/
	@RequestMapping(value = "edit/{companyTypeId}")
	public String showEditBox(@PathVariable Long companyTypeId, Model model) {
		TntCompanyType tntCompanyType = get(companyTypeId);
		model.addAttribute(tntCompanyType);
		model.addAttribute("channelMap", getChannelMap());
		return "user/type/edit";
	}

	/** 编辑分销商类型 **/
	@RequestMapping(method = RequestMethod.PUT)
	public String edit(TntCompanyType t) {
		modify(t);
		return "redirect:type/list";
	}

	/** 打开分销商类型删除弹窗 **/
	@RequestMapping(value = "toDelete/{companyTypeId}")
	public void showDeleteBox(@PathVariable Long companyTypeId,
			HttpServletResponse response) {
		boolean flag = isCanDelete(companyTypeId);
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
		Long companyTypeId = TntUtil.parserLong(request
				.getParameter("companyTypeId"));
		delete(companyTypeId);
		return "redirect:type/list";
	}

	/** 联动效果：改变渠道，联动查询分销商类型下拉 **/
	@RequestMapping(value = "changeChannel")
	public void changeChannel(
			@RequestParam(value = "channelId", required = false) String channelId,
			Model model, HttpServletResponse response) {
		Long cid = TntUtil.parserLong(channelId);
		if (cid != null) {
			PrintWriter writer = null;
			try {
				TntCompanyType t = new TntCompanyType();
				t.setChannelId(cid);
				List<String> list = tntCompanyTypeService
						.getTypeIdANdNameList(t);
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

	/** 查看供应商 **/
	@RequestMapping(value = "users/{companyTypeId}")
	public String showUsers(@PathVariable Long companyTypeId, Integer page,
			Model model, HttpServletRequest request) {
		if (companyTypeId != null) {
			TntUser t = new TntUser();
			t.setDetail(new TntUserDetail());
			t.getDetail().setCompanyTypeId(companyTypeId);
			int pageNo = page != null ? page : 1;
			Page<TntUser> p = Page.page(pageNo, t);
			t.trim();
			List<TntUser> tntUserList = this.listUsers(p, request);
			model.addAttribute(Page.KEY, p);
			model.addAttribute(tntUserList);
		}
		return "user/type/users";
	}

	protected List<TntUser> listUsers(Page<TntUser> page,
			HttpServletRequest request) {
		WebFetchPager<TntUser> pager = new WebFetchPager<TntUser>(page, request) {
			@Override
			protected long getTotalCount(TntUser t) {
				return tntUserService.queryWithDetailCount(t);
			}

			@Override
			protected List<TntUser> fetchDetail(Page<TntUser> page) {
				return tntUserService.queryPageWithDetail(page);
			}

		};
		return pager.fetch();

	}

	protected List<TntCompanyType> list(Page<TntCompanyType> page,
			HttpServletRequest request) {
		WebFetchPager<TntCompanyType> pager = new WebFetchPager<TntCompanyType>(
				page, request) {

			@Override
			protected long getTotalCount(TntCompanyType t) {
				return tntCompanyTypeService.count(t);
			}

			@Override
			protected List<TntCompanyType> fetchDetail(Page<TntCompanyType> page) {
				return tntCompanyTypeUserService.queryWithUserTotal(page);
			}

		};
		return pager.fetch();
	}

	protected TntCompanyType get(Long companyTypeId) {
		return tntCompanyTypeService.get(companyTypeId);
	}

	protected boolean insert(TntCompanyType t) {
		return tntCompanyTypeService.insert(t);
	}

	protected boolean modify(TntCompanyType t) {
		return tntCompanyTypeService.update(t);
	}

	protected boolean isCanDelete(Long companyTypeId) {
		return !tntCompanyTypeUserService.isContainUser(companyTypeId);
	}

	protected boolean delete(Long companyTypeId) {
		return tntCompanyTypeService.delete(companyTypeId);
	}

	protected Map<Long, String> getChannelMap() {
		return tntChannelService.getMap();
	}

}
