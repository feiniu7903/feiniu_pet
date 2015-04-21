package com.lvmama.pet.sweb.mark.channel;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import net.sf.json.JSONArray;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.mark.MarkChannel;
import com.lvmama.comm.pet.service.mark.MarkChannelService;

/**
 * 渠道三级联动控件所访问的Ajax请求
 * @author Brian
 *
 */
public class AjaxMarkChannelAction extends BackBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 4624594361520810636L;
	/**
	 * 渠道服务
	 */
	private MarkChannelService markChannelService;
	
	//level的级别
	private int layer;
	//查询的关键字
	private String keywords;
	//上一级渠道的code
	private Long channelId;
	
	@Action(value="/mark/channel/ajaxMarkChannelQuery")
	public void search() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("valid", "Y");
		if (0 != layer) {
			params.put("layer", layer);
		}
		if (StringUtils.isNotBlank(keywords)) {
			params.put("channelName", keywords);
		}
		if (null != channelId) {
			params.put("fatherId", channelId);
		}
		
		List<MarkChannel> channels =  markChannelService.search(params);
		//this.getResponse().setCharacterEncoding("gb2312");
		try {
			if (getRequest().getParameter("jsoncallback") == null) {
				getResponse().getWriter().print(JSONArray.fromObject(channels)); 
			} else {
				getResponse().getWriter().print(getRequest().getParameter("jsoncallback")+"("+ JSONArray.fromObject(channels) + ")");
			}
		} catch (IOException e) {
			
		}
		
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(final String keywords) {
		this.keywords = keywords;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public void setMarkChannelService(final MarkChannelService markChannelService) {
		this.markChannelService = markChannelService;
	}
}
