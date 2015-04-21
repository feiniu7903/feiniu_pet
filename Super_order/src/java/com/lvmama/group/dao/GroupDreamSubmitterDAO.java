/**
 * 
 */
package com.lvmama.group.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.group.GroupDreamSubmitter;

/**
 * @author songlianjun
 *	团梦想提交记录DAO
 */
public class GroupDreamSubmitterDAO extends BaseIbatisDAO {
	public void insert(GroupDreamSubmitter dreamSubmitter){
		super.insert("GROUP_DREAM_SUBMITTER.insert",
				dreamSubmitter);
	}
	/**
	 * 统计某团梦想的提交人数
	 * @param dreamId
	 * @return
	 */
	public Map countDreamSubmitNumsByDreamId(Long dreamId){
		return (Map)super.queryForObject("GROUP_DREAM_SUBMITTER.countDreamSubmitNumsByDreamId",
				dreamId);
	}
	/**
	 * 查询团购梦想喜欢的提交者
	 * @param dreamId
	 * @return
	 */
	public List<GroupDreamSubmitter> queryEnjoyDreamSubmitters(Map paramMap){
		return super.queryForList("GROUP_DREAM_SUBMITTER.queryEnjoyDreamSubmitters",paramMap);
	}
	/**
	 *统计团购梦想喜欢的提交者数量
	 * @param dreamId
	 * @return
	 */
	public Long countEnjoyDreamSubmitters(Map paramMap){
		return (Long)super.queryForObject("GROUP_DREAM_SUBMITTER.countEnjoyDreamSubmitters",
				paramMap);
	}
}
