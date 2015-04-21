package com.lvmama.tnt.back.helpcenter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.comm.util.WebFetchPager;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.po.TntAnnouncement;
import com.lvmama.tnt.user.service.TntAnnouncementService;

@Controller
@RequestMapping("/helpcenter/announcement")
public class AnnouncementController {

	@Autowired
	private TntAnnouncementService tntAnnouncementService;



	/** 平台公告列表 **/
	@RequestMapping(value = "/list")
	public String search(Model model, HttpServletRequest request,
			TntAnnouncement t, Integer page) {
		int pageNo = page != null ? page : 1;
		Page<TntAnnouncement> p = Page.page(pageNo, t);
		p.desc("PUBLISH_TIME");
		t.trim();
		List<TntAnnouncement> tntAnnouncementList = list(p, request);
		model.addAttribute(tntAnnouncementList);
		model.addAttribute(Page.KEY, p);
		return "helpcenter/announcement/announcementlist";
	}

	/** 添加平台公告 **/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(TntAnnouncement tntAnnouncement) {
		insert(tntAnnouncement);
		return "redirect:list";
	}

	/** 打开编辑公告的弹窗 **/
	@RequestMapping(value = "edit/{announcementId}")
	public String showEditBox(@PathVariable Long announcementId, Model model) {
		TntAnnouncement tntAnnouncement = get(announcementId);
		model.addAttribute(tntAnnouncement);
		return "helpcenter/announcement/edit";
	}
	
	/** 编辑平台公告 **/
	@RequestMapping(method = RequestMethod.PUT)
	public String edit(TntAnnouncement t) {
		modify(t);
		return "redirect:announcement/list";
	}

	/** 删除公告 **/
	@RequestMapping(method = RequestMethod.DELETE)
	public String delete(HttpServletRequest request) {
		Long announcementId = TntUtil.parserLong(request
				.getParameter("announcementId"));
		delete(announcementId);
		return "redirect:announcement/list";
	}
	
	/** 查看公告明细**/
	@RequestMapping(value = "showdetail/{announcementId}")
	public String showDetailBox(@PathVariable Long announcementId, Model model) {
		TntAnnouncement tntAnnouncement = get(announcementId);
		model.addAttribute(tntAnnouncement);
		return "helpcenter/announcement/detail";
	}	
	
	/** 打开平台公告删除弹窗 **/
	@RequestMapping(value = "toDelete/{announcementId}")
	public void showDeleteBox(@PathVariable Long announcementId,
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
	
	protected List<TntAnnouncement> list(Page<TntAnnouncement> page,
			HttpServletRequest request) {
		WebFetchPager<TntAnnouncement> pager = new WebFetchPager<TntAnnouncement>(
				page, request) {

			@Override
			protected long getTotalCount(TntAnnouncement t) {
				return tntAnnouncementService.count(t);
			}

			@Override
			protected List<TntAnnouncement> fetchDetail(Page<TntAnnouncement> page) {
				return tntAnnouncementService.fetchPage(page);
			}

		};
		return pager.fetch();
	}

	protected TntAnnouncement get(Long announcementId) {
		return tntAnnouncementService.selectByPrimaryKey(announcementId);
	}

	protected boolean insert(TntAnnouncement t) {
		return tntAnnouncementService.insertTntAnnouncement(t);
	}

	protected boolean delete(Long announcementId) {
		return tntAnnouncementService.delete(announcementId);
	}
	
	protected boolean modify(TntAnnouncement t) {
		return tntAnnouncementService.update(t);
	}

}

