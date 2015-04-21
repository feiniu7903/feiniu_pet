package com.lvmama.tnt.front.helpcenter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lvmama.tnt.comm.util.WebFetchPager;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.po.TntAnnouncement;
import com.lvmama.tnt.user.po.TntFAQ;
import com.lvmama.tnt.user.service.TntAnnouncementService;
import com.lvmama.tnt.user.service.TntFAQService;
import com.lvmama.tnt.user.service.TntUserService;

@Controller
@RequestMapping(value = "/help")
public class HelpCenterController {

	@Autowired
	private TntUserService tntUserService;

	@Autowired
	private TntAnnouncementService tntAnnouncementService;

	@Autowired
	private TntFAQService tntFAQService;

	protected Long page = 1L;

	@RequestMapping("/index")
	public String helpCenterIndex() {
		return "/helpcenter/baseInfo";
	}

	@RequestMapping("/join")
	public String join() {
		return "/helpcenter/helpIndex";
	}

	@RequestMapping("/announcement")
	public String helpCenterAnnouncement(Model model, Integer page,
			TntAnnouncement t, HttpServletRequest request) {
		int pageNo = page != null ? page : 1;
		Page<TntAnnouncement> p = Page.page(pageNo, t);
		p.desc("PUBLISH_TIME");
		List<TntAnnouncement> tntAnnouncementList = list(p, request);
		model.addAttribute(tntAnnouncementList);
		model.addAttribute(Page.KEY, p);
		return "/helpcenter/announcement";
	}

	@RequestMapping("/queryAnnouncementList")
	public String queryAnnouncementList(Model model, Integer page,
			TntAnnouncement t, HttpServletRequest request) {
		int pageNo = page != null ? page : 1;
		Page<TntAnnouncement> p = Page.page(pageNo, t);
		p.desc("PUBLISH_TIME");
		List<TntAnnouncement> tntAnnouncementList = list(p, request);
		model.addAttribute(tntAnnouncementList);
		model.addAttribute(Page.KEY, p);
		return "/helpcenter/announcementList";
	}

	@RequestMapping("/contractUs")
	public String contractUs() {
		return "/helpcenter/contractUs";
	}

	@RequestMapping("/protocol")
	public String arguments() {
		return "/helpcenter/protocol";
	}

	@RequestMapping("/faq")
	public String helpCenterFAQ(Model model, Integer page, TntFAQ t,
			HttpServletRequest request) {
		int pageNo = page != null ? page : 1;
		Page<TntFAQ> p = Page.page(pageNo, t);
		p.desc("PUBLISH_TIME");
		List<TntFAQ> tntFAQList = faqlist(p, request);
		model.addAttribute(tntFAQList);
		model.addAttribute(Page.KEY, p);
		return "/helpcenter/faq";
	}

	@RequestMapping("/queryFAQList")
	public String queryFAQList(Model model, Integer page, TntFAQ t,
			HttpServletRequest request) {
		int pageNo = page != null ? page : 1;
		Page<TntFAQ> p = Page.page(pageNo, t);
		p.desc("PUBLISH_TIME");
		List<TntFAQ> tntFAQList = faqlist(p, request);
		model.addAttribute(tntFAQList);
		model.addAttribute(Page.KEY, p);
		return "/helpcenter/faqList";
	}

	@RequestMapping("/show_announcement_detail")
	public String showAnnouncementDetail(Model model, Long id) {
		TntAnnouncement tntAnnouncement = tntAnnouncementService
				.selectByPrimaryKey(id);
		if (null == tntAnnouncement) {
			return "error_404";
		}
		model.addAttribute("tntAnnouncement", tntAnnouncement);
		return "/helpcenter/announcementDetail";
	}

	protected List<TntAnnouncement> list(Page<TntAnnouncement> page,
			HttpServletRequest request) {
		WebFetchPager<TntAnnouncement> pager = new WebFetchPager<TntAnnouncement>(
				page, request) {

			@Override
			protected long getTotalCount(TntAnnouncement t) {
				return tntAnnouncementService.count(t);
			}

			@Override
			protected List<TntAnnouncement> fetchDetail(
					Page<TntAnnouncement> page) {
				page.setModel(Page.MODEL_FRONT);
				return tntAnnouncementService.fetchPage(page);
			}
		};
		return pager.fetch();
	}

	protected List<TntFAQ> faqlist(Page<TntFAQ> page, HttpServletRequest request) {
		WebFetchPager<TntFAQ> pager = new WebFetchPager<TntFAQ>(page, request) {

			@Override
			protected long getTotalCount(TntFAQ t) {
				return tntFAQService.count(t);
			}

			@Override
			protected List<TntFAQ> fetchDetail(Page<TntFAQ> page) {
				page.setModel(Page.MODEL_FRONT);
				return tntFAQService.fetchPage(page);
			}
		};
		return pager.fetch();
	}
}
