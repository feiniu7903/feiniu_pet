package com.lvmama.tnt.back.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.user.po.TntChannel;
import com.lvmama.tnt.user.service.TntChannelService;
import com.lvmama.tnt.user.service.TntCompanyTypeService;
import com.lvmama.tnt.user.service.TntCompanyTypeUserService;
import com.lvmama.tnt.user.service.TntUserService;

@Controller
@RequestMapping("/user/channel")
public class ChannelController {

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
	public String search(Model model, HttpServletRequest request, TntChannel t,
			Integer page) {
		List<TntChannel> list = tntChannelService.query(t);
		model.addAttribute("tntChannelList", list);
		Map<Long, String> channelMap = tntChannelService.getMap();
		model.addAttribute("channelMap", channelMap);
		model.addAttribute("tntChannel", new TntChannel());
		return "user/channel";
	}

	/** 添加分销商 渠道 **/
	@RequestMapping(method = RequestMethod.POST)
	public void setChannel(Model model, TntChannel tntChannel,
			HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			boolean flag = set(tntChannel);
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

	/** 打开编辑分销商类型弹窗 **/
	@RequestMapping(value = "/{channelId}")
	public void getChannel(@PathVariable Long channelId,
			HttpServletResponse resp) {
		TntChannel t = tntChannelService.getByChannelId(channelId);
		JSONObject json = JSONObject.fromObject(t);
		PrintWriter writer = null;
		try {
			writer = resp.getWriter();
			writer.print(json);
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
		Long channelId = TntUtil.parserLong(request.getParameter("channelId"));
		delete(channelId);
		return "redirect:type/list";
	}

	@RequestMapping(value = "/channelPolicy")
	public String getChannelPolicy(Model model) {
		List<TntChannel> channelPolicyList = tntChannelService
				.getChannelPolicy();
		model.addAttribute("channelPolicyList", channelPolicyList);
		return "/product/policy/channelPolicyList";
	}

	protected boolean set(TntChannel t) {
		boolean flag = false;
		if (t.getChannelId() == null)
			flag = tntChannelService.insert(t);
		else
			flag = tntChannelService.update(t);
		return flag;
	}

	protected boolean delete(Long channelId) {
		return tntChannelService.delete(channelId);
	}

	protected Map<Long, String> getChannelMap() {
		return tntChannelService.getMap();
	}

}
