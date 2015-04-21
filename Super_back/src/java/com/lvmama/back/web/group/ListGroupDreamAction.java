package com.lvmama.back.web.group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.group.GroupDream;
import com.lvmama.comm.bee.service.GroupDreamService;
import com.lvmama.comm.jms.TopicMessageProducer;

public class ListGroupDreamAction extends BaseAction {

	private GroupDreamService groupDreamService;
	private List<GroupDream> groupDreamList;
	private Map<String, Object> searchConds = new HashMap<String, Object>();
	private Integer totalRowCount;
	private TopicMessageProducer productMessageProducer;

	
   
	public void doDelete(Map params) {
		delQuest(params);
		this.refreshComponent("search");
	}
	/**
	 * 此处为逻辑删除
	 * @param params
	 */
	public void delQuest(final Map params){ 
		final Long dreamId=(Long)params.get("dreamId");
		ZkMessage.showQuestion("您要删除 "+dreamId+", 请确认。", new ZkMsgCallBack(){
			public void  execute(){
				GroupDream gd = new GroupDream();
				gd.setDreamId(dreamId);
				gd.setOperator(ListGroupDreamAction.this.getOperatorName());
				gd.setValid("N");
				groupDreamService.deleteGroupDream(gd);
				refreshComponent("search");
			}
		}, new ZkMsgCallBack(){
			public void  execute(){
				
			}
		});
	}
	public void search(){
		totalRowCount=groupDreamService.selectRowCount(searchConds);
		_totalRowCountLabel.setValue(totalRowCount.toString()); 
		_paging.setTotalSize(totalRowCount.intValue());
		searchConds.put("_startRow", _paging.getActivePage()*_paging.getPageSize()+1);
		searchConds.put("_endRow", _paging.getActivePage()*_paging.getPageSize()+_paging.getPageSize());
		groupDreamList = groupDreamService.getGroupDreams(searchConds);
	}

	public List<GroupDream> getGroupDreamList() {
		return groupDreamList;
	}


	public void setGroupDreamList(List<GroupDream> groupDreamList) {
		this.groupDreamList = groupDreamList;
	}


	public Map<String, Object> getSearchConds() {
		return searchConds;
	}

	public void setGroupDreamService(GroupDreamService groupDreamService) {
		this.groupDreamService = groupDreamService;
	}

	public TopicMessageProducer getProductMessageProducer() {
		return productMessageProducer;
	}

	public void setProductMessageProducer(
			TopicMessageProducer productMessageProducer) {
		this.productMessageProducer = productMessageProducer;
	}


	
}
