package com.lvmama.back.web.channel;

import java.util.List;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.service.prod.ProdChannelService;
import com.lvmama.comm.pet.po.prod.ProdChannel;

public class ListChannelAction extends BaseAction {
	
	private ProdChannelService prodChannelService;
	
	private List<ProdChannel> prodChannelList;
	
	@Override
	protected void doBefore() throws Exception {
		super.doBefore();
	}

	public void search() {
		prodChannelList = prodChannelService.selectProdChannelByAll();
	}
	
	public List<ProdChannel> getProdChannelList() {
		return prodChannelList;
	}

}
