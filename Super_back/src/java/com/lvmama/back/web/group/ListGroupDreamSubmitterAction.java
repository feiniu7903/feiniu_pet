package com.lvmama.back.web.group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Label;
import org.zkoss.zul.Paging;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.group.GroupDreamSubmitter;
import com.lvmama.comm.bee.service.GroupDreamService;
import com.lvmama.comm.pet.vo.Page;
/**
 * 喜欢团购梦想提交者信息
 * @author songlianjun
 *
 */
public class ListGroupDreamSubmitterAction extends BaseAction {
	private List<GroupDreamSubmitter> groupDreamSubmitterList;
	private GroupDreamService groupDreamService;
	private Long dreamId;
	public void search(){
		Map<String, Object> map = new HashMap<String, Object>();
		//查询款失败的记录
		map.put("dreamId",dreamId);
		Paging paging=(Paging)this.getComponent().getFellow("_paging");
		Label totalRowCountLabel =(Label)this.getComponent().getFellow("_totalRowCountLabel");
		Page page = Page.page(paging.getPageSize(), paging.getActivePage()+1);
		page = groupDreamService.getGroupDreamEnjoySubmitters(page,map);
		groupDreamSubmitterList = page.getItems();
		totalRowCountLabel.setValue(page.getTotalResultSize()+"");
		paging.setTotalSize((int)page.getTotalResultSize());
	}
	
	public List<GroupDreamSubmitter> getGroupDreamSubmitterList() {
		return groupDreamSubmitterList;
	}

	public void setDreamId(Long dreamId) {
		this.dreamId = dreamId;
	}

	public void setGroupDreamService(GroupDreamService groupDreamService) {
		this.groupDreamService = groupDreamService;
	}
	
}
