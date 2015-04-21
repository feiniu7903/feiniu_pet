package com.lvmama.report.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.prod.ProdChannel;

public interface ProdChannelService {
	
	List<ProdChannel> searchChannel(Map param);
	
	ProdChannel getProdChannelById(Long channelId);
}
