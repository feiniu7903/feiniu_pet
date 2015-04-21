package com.lvmama.back.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.api.Bandbox;
import org.zkoss.zul.api.Textbox;

import com.lvmama.comm.bee.service.prod.ProdChannelService;
import com.lvmama.comm.pet.po.prod.ProdChannel;
import com.lvmama.comm.spring.SpringBeanProxy;

public class MacroChannelSelect extends GenericForwardComposer {

	private ProdChannelService channelService = (ProdChannelService)SpringBeanProxy.getBean("prodChannelService");
	Textbox channelId;
	Bandbox mc_bandChannel;
	List<ProdChannel> channelList;

	public void changeChannel(InputEvent event) {
		String name = event.getValue();
		Map<String, String> param = new HashMap<String, String>();
		if (name != null && !"".equals(name)) {
			param.put("channelSearch", name);
		}
		param.put("_startRow", "0");
		param.put("_endRow", "10");
		channelList = channelService.searchChannel(param);
	}

	public void doAfterCompose(Component win) throws Exception {
		Components.wireVariables(win, this);
		Events.addEventListeners(win, this);
		Components.addForwards(win, this);

		if (channelId != null && channelId.getValue() != null && !channelId.getValue().equals("")) {
			Long productIdValue = Long.parseLong(channelId.getValue());
			ProdChannel prodChannel = channelService.getProdChannelById(productIdValue);
			mc_bandChannel.setValue(prodChannel.getChannelName());
		}
	}

	public List<ProdChannel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<ProdChannel> channelList) {
		this.channelList = channelList;
	}

}
