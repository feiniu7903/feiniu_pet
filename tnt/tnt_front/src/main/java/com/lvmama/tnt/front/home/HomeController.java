package com.lvmama.tnt.front.home;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.search.service.SearchService;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.vst.api.search.vo.SearchResultVo;
import com.lvmama.vst.api.vo.ResultHandleT;

@Controller
public class HomeController extends BaseHomeController {

	protected Log loger = LogFactory.getLog(this.getClass());
	
	@Autowired
	public SearchService searchService;

	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model) {
		return execute(request, response, session, model);
	}

	@RequestMapping(value = "/fx_home")
	public String execute(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model) {

		this.init(request, response, Constant.CHANNEL_ID.CH_INDEX.name());
		ResultHandleT<SearchResultVo> resultHandle = new ResultHandleT<SearchResultVo>();
		try {
			TntUser user = this.getLoginUser(session);
			TntUser t  = null;
			if(user!=null)
				t = TntConstant.USER_INFO_STATUS.isActived(user.getDetail().getInfoStatus())?user:null;
			resultHandle = searchService.searchTicket(this.fromPlaceName
					+ "-" + this.fromPlaceName,t );
			
			if(StringUtil.isNotEmptyString(resultHandle.getMsg())){
				loger.info("HomeController searchThrows Exception msg: "+resultHandle.getMsg());
				return "error";
			}
			SearchResultVo searchResultVo = resultHandle.getReturnContent();
			
			Map<String, Object> map = searchResultVo.getResultMap();
			if (map != null)
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					model.addAttribute(entry.getKey(), entry.getValue());
				}
			
			// 初始化分页
			model.addAttribute("pageConfig", searchResultVo.getPageConfig());
			model.addAttribute("pageinations", new Pagination().paginationVo(
					searchResultVo.getPageConfig(), 2));
			model.addAttribute("pageinationd", new Pagination().paginationVo(
					searchResultVo.getPageConfig(), 3));
			
			this.disposeModel(model, request, response);
			
			model.addAttribute("tntUser", new TntUser());
		} catch (Exception e) {
			loger.error("HomeController Exception:",e);
			return "error";
		}
		
		return "/home";
	}

	@RequestMapping(value = "/search")
	public String search(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model,
			String search) {

		if (StringUtil.isEmptyString(search)) {
			return execute(request, response, session, model);
		}
		try {
			ResultHandleT<SearchResultVo> resultHandle = new ResultHandleT<SearchResultVo>();
			TntUser user = this.getLoginUser(session);
			TntUser t  = null;
			if(user!=null)
				t = TntConstant.USER_INFO_STATUS.isActived(user.getDetail().getInfoStatus())?user:null;
			resultHandle = searchService.searchTicket(search,
						t);
			
			if(StringUtil.isNotEmptyString(resultHandle.getMsg())){
				loger.info("HomeController searchThrows Exception msg: "+resultHandle.getMsg());
				return "error";
			}
			
			SearchResultVo searchResultVo = resultHandle.getReturnContent();
			Map<String, Object> map = searchResultVo.getResultMap();
			
			if(map != null)
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					model.addAttribute(entry.getKey(), entry.getValue());
				}
			
			model.addAttribute("pageConfig", searchResultVo.getPageConfig());
			model.addAttribute("pageinations", new Pagination().paginationVo(
					searchResultVo.getPageConfig(), 2));
			model.addAttribute("pageinationd", new Pagination().paginationVo(
					searchResultVo.getPageConfig(), 3));
			
			this.disposeModel(model, request, response);
			
			model.addAttribute("tntUser", new TntUser());
		} catch (Exception e) {
			loger.error("HomeController search Exception:",e);
			return "error";
		}
		return "/home";
	}

	public void disposeModel(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		model.addAttribute("httpInclude", new HttpInclude(request, response));
	}
}
