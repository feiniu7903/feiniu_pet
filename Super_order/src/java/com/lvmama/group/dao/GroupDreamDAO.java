package com.lvmama.group.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.group.GroupDream;

public class GroupDreamDAO extends BaseIbatisDAO {
	public GroupDream selectByPrimaryKey(Long dreamId) {
		GroupDream groupDream = (GroupDream) super.queryForObject("GROUP_DREAM.selectByPrimaryKey", dreamId);
		return groupDream;
	}
	public Integer selectRowCount(Map searchConds){
		Integer count = 0;
		count = (Integer) super.queryForObject("GROUP_DREAM.selectRowCount",searchConds);
		return count;
	}

	public List<GroupDream> getSupGroupDreams(Map param) {
		if (param.get("_startRow")==null) {
			param.put("_startRow", 0);
		}
		if (param.get("_endRow")==null) {
			param.put("_endRow", 20);
		}
		return super.queryForList("GROUP_DREAM.selectGroupDream", param);
	}
	
	
	public Long insert(GroupDream groupDream) {
		Long key = (Long) super.insert("GROUP_DREAM.insert", groupDream);
		return key;
	}
	public int updateByPrimaryKey(GroupDream groupDream) {
		int rows = super.update("GROUP_DREAM.updateByPrimaryKey", groupDream);
		return rows;
	}
	public int updateValidByPrimaryKey(GroupDream groupDream) {
		int rows = super.update("GROUP_DREAM.updateValidByPrimaryKey", groupDream);
		return rows;
	}
	
	public void deleteByPrimaryKey(Map params){
		 super.delete("GROUP_DREAM.deleteByPrimaryKey",params);
	}
//	public Integer countByProductId(Long productId) {
//		 GroupDream groupDream = new GroupDream();
//		 groupDream.setProductId(productId);
//		 return (Integer) super.queryForObject("GROUP_DREAM.countByProductId", groupDream);
//	}
	public List<GroupDream> queryDreamProducts(Map paramMap){
		return super.queryForList("GROUP_DREAM.queryDreamProducts",paramMap);
	}
	

}