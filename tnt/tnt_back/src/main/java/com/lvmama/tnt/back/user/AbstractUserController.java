package com.lvmama.tnt.back.user;

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

import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.comm.util.WebFetchPager;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.comm.vo.TntConstant.GENDER;
import com.lvmama.tnt.user.po.TntCompanyType;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.po.TntUserDetail;
import com.lvmama.tnt.user.po.TntUserMaterial;
import com.lvmama.tnt.user.service.TntCompanyTypeService;
import com.lvmama.tnt.user.service.TntUserMaterialService;
import com.lvmama.tnt.user.service.TntUserService;

@Controller
@RequestMapping("/user")
public class AbstractUserController {

	@Autowired
	private TntUserService tntUserService;

	@Autowired
	private TntCompanyTypeService tntCompanyTypeService;

	@Autowired
	private TntUserMaterialService tntUserMaterialService;

	@Autowired
	private PlaceCityService placeCityService;

	/** 列表搜索 **/
	@RequestMapping
	public String search(
			Model model,
			HttpServletRequest request,
			@RequestParam(required = false) Integer page,
			TntUser t,
			@RequestParam(value = "companyTypeId", required = false) String companyTypeId) {
		String status = this.getStatus();
		Long typeId = TntUtil.parserLong(companyTypeId);
		if (t.getDetail() == null)
			t.setDetail(new TntUserDetail());
		t.getDetail().setCompanyTypeId(typeId);
		t.getDetail().setFinalStatus(status);
		model.addAttribute(t);
		int pageNo = page != null ? page : 1;
		Page<TntUser> p = Page.page(pageNo, t);
		t.trim();
		p.desc("b.CREATED_DATE");
		List<TntUser> tntUserList = list(p, request);
		model.addAttribute(tntUserList);
		model.addAttribute("status", status);
		model.addAttribute(Page.KEY, p);
		model.addAttribute("companyTypeMap", getCompanyTypeMap());
		model.addAttribute("payTypeMap", TntConstant.PAY_TYPE.toMap());
		init(model);
		return "user/" + getPage();
	}

	public TntUserService getTntUserService() {
		return tntUserService;
	}

	/** 初始化状态种类 **/
	protected void init(Model model) {
		// do nothing
	}

	/** 分销商详情 **/
	@RequestMapping("info/{userId}")
	public String showUserInfo(@PathVariable Long userId, Model model) {
		TntUser tntUser = get(userId);
		model.addAttribute(tntUser);
		TntUserDetail detail = tntUser.getDetail();
		boolean canApprove = TntConstant.USER_FINAL_STATUS.isWaiting(detail
				.getFinalStatus())
				&& !TntConstant.USER_INFO_STATUS.isAgreed(detail
						.getInfoStatus());
		model.addAttribute("canApprove", canApprove);
		model.addAttribute("genderMap", GENDER.toMap());
		model.addAttribute("companyTypeMap", getCompanyTypeMap());
		String provinceId = detail.getProvinceId();
		if (provinceId != null) {
			String cityId = detail.getCityId();
			ComProvince province = placeCityService
					.selectByPrimaryKey(provinceId);
			model.addAttribute("provinceName",
					province != null ? province.getProvinceName() : "");
			if (cityId != null) {
				ComCity city = this.placeCityService
						.selectCityByPrimaryKey(cityId);
				model.addAttribute("cityName",
						city != null ? city.getCityName() : "");
			}
		}
		return "user/info";
	}

	/** 显示基本资料列表 **/
	@RequestMapping("material/{userId}")
	public String listMaterial(@PathVariable Long userId, Model model) {
		TntUser tntUser = get(userId);
		if (tntUser != null) {
			boolean canApprove = !TntConstant.USER_FINAL_STATUS.isEnd(tntUser
					.getDetail().getFinalStatus())
					&& !TntConstant.USER_MATERIAL_STATUS.isAgreed(tntUser
							.getDetail().getMaterialStatus());
			model.addAttribute("canApprove", canApprove);
			List<TntUserMaterial> tntUserMaterialList = this.tntUserMaterialService
					.queryByUserId(userId);
			TntUserDetail detail = new TntUserDetail();
			detail.setUserId(userId);
			model.addAttribute(detail);
			TntUserMaterial tntUserMaterial = new TntUserMaterial();
			tntUserMaterial.setUserId(userId);
			model.addAttribute(tntUserMaterial);
			model.addAttribute(tntUserMaterialList);
			model.addAttribute("statusMap",
					TntConstant.USER_MATERIAL_STATUS.toMap());
			model.addAttribute("typeMap",
					TntConstant.USER_MATERIAL_TYPE.toMap());
		}
		return "user/material";
	}

	@RequestMapping(value = "material", method = RequestMethod.POST)
	public String agree(TntUserMaterial t, Model model) {
		Long userId = t.getUserId();
		tntUserMaterialService.agree(t.getUserId(), t.getMaterialId());
		return "redirect:material/" + userId;
	}

	@RequestMapping(value = "material", method = RequestMethod.DELETE)
	public String reject(TntUserMaterial t, HttpServletRequest request,
			Model model) {
		Long userId = t.getUserId();
		String materialId = request.getParameter("materialId");
		if (materialId != null && !materialId.isEmpty()) {
			String failReason = request.getParameter("failReason");
			tntUserMaterialService.singlReject(TntUtil.parserLong(materialId),
					failReason);
		} else {
			tntUserMaterialService.reject(userId, t.getFailReason());
		}
		return "redirect:material/" + userId;
	}

	/** 打开终审弹窗 **/
	// 暂时不用
	@RequestMapping(value = "toFinal")
	public void finalApproveBox(@PathVariable Long userId,
			HttpServletResponse response) {
		boolean flag = tntUserService.canFinalApprove(userId);
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

	protected List<TntUser> list(Page<TntUser> page, HttpServletRequest request) {
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

	protected TntUser get(Long userId) {
		return tntUserService.findWithDetailByUserId(userId);
	}

	private Map<String, String> getCompanyTypeMap() {
		return tntCompanyTypeService.map(new TntCompanyType());
	}

	protected String getStatus() {
		return null;
	}

	protected String getPage() {
		return null;
	}
}
