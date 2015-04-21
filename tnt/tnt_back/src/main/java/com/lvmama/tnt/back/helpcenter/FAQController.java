package com.lvmama.tnt.back.helpcenter;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.comm.util.WebFetchPager;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.po.TntFAQ;
import com.lvmama.tnt.user.service.TntFAQService;

@Controller
@RequestMapping("/helpcenter/faq")
public class FAQController {

	@Autowired
	private TntFAQService tntFAQService;



	/** 常见问题列表 **/
	@RequestMapping(value = "/list")
	public String search(Model model, HttpServletRequest request,
			TntFAQ t, Integer page) {
		int pageNo = page != null ? page : 1;
		Page<TntFAQ> p = Page.page(pageNo, t);
		p.desc("PUBLISH_TIME");
		t.trim();
		List<TntFAQ> tntFAQList = list(p, request);
		model.addAttribute(tntFAQList);
		model.addAttribute(Page.KEY, p);
		return "helpcenter/faq/faqlist";
	}

	/** 添加平台公告 **/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(TntFAQ tntFAQ) {
		insert(tntFAQ);
		return "redirect:list";
	}

	/** 打开编辑公告的弹窗 **/
	@RequestMapping(value = "edit/{tntFAQId}")
	public String showEditBox(@PathVariable Long tntFAQId, Model model) {
		TntFAQ tntFAQ = get(tntFAQId);
		model.addAttribute(tntFAQ);
		return "helpcenter/faq/edit";
	}
	
	/** 编辑平台公告 **/
	@RequestMapping(method = RequestMethod.PUT)
	public String edit(TntFAQ t) {
		modify(t);
		return "redirect:faq/list";
	}

	/** 删除公告 **/
	@RequestMapping(method = RequestMethod.DELETE)
	public String delete(HttpServletRequest request) {
		Long tntFAQId = TntUtil.parserLong(request
				.getParameter("tntFAQId"));
		delete(tntFAQId);
		return "redirect:faq/list";
	}
	
	/** 打开平台公告删除弹窗 **/
	@RequestMapping(value = "toDelete/{tntFAQId}")
	public void showDeleteBox(@PathVariable Long tntFAQId,
			HttpServletResponse response) {
		boolean flag = true;
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
	
	protected List<TntFAQ> list(Page<TntFAQ> page,
			HttpServletRequest request) {
		WebFetchPager<TntFAQ> pager = new WebFetchPager<TntFAQ>(
				page, request) {

			@Override
			protected long getTotalCount(TntFAQ t) {
				return tntFAQService.count(t);
			}

			@Override
			protected List<TntFAQ> fetchDetail(Page<TntFAQ> page) {
				return tntFAQService.fetchPage(page);
			}

		};
		return pager.fetch();
	}

	protected TntFAQ get(Long tntFAQId) {
		return tntFAQService.selectByPrimaryKey(tntFAQId);
	}

	protected boolean insert(TntFAQ t) {
		return tntFAQService.insertTntFAQ(t);
	}

	protected boolean delete(Long tntFAQId) {
		return tntFAQService.delete(tntFAQId);
	}
	
	protected boolean modify(TntFAQ t) {
		return tntFAQService.update(t);
	}

}
