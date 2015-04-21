package com.lvmama.pet.web.user.parttime;

import java.util.List;

import com.lvmama.comm.pet.po.pub.ComParttimeUser;
import com.lvmama.comm.pet.service.pub.ComParttimeUserService;
import com.lvmama.pet.web.user.imp.UserChannelBaseAction;

public class ListParttimeUserAction  extends UserChannelBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 8352490696575177946L;
	
	private ComParttimeUserService comParttimeUserService;
	
	private List<ComParttimeUser> userChannelList; // 促销员与渠道关系查询结果列表
	
	/**
	 * 根据渠道类型或渠道标识查询促销员与渠道关系
	 */
	public void search() {
		Long rowCount=comParttimeUserService.count(searchConds);
		_totalRowCountLabel.setValue(String.valueOf(rowCount));
		_paging.setTotalSize(rowCount.intValue());
		searchConds.put("skipResults", _paging.getActivePage()*_paging.getPageSize());
		searchConds.put("maxResults", _paging.getActivePage()*_paging.getPageSize()+_paging.getPageSize());
		userChannelList = comParttimeUserService.query(searchConds);
	}

	public List<ComParttimeUser> getUserChannelList() {
		return userChannelList;
	}

	public void setComParttimeUserService(
			ComParttimeUserService comParttimeUserService) {
		this.comParttimeUserService = comParttimeUserService;
	}


}
