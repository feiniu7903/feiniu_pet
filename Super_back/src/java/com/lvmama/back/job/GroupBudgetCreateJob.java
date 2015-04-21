package com.lvmama.back.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.service.IGroupBudgetService;
import com.lvmama.comm.vo.Constant;

/**
 * 线路类型代售产品需要系统自动做实际成本表，游玩时间过了之后生成实际成本表。
 * @author zhaojindong
 *
 */
public class GroupBudgetCreateJob implements Runnable {
	private static Log log = LogFactory.getLog(GroupBudgetCreateJob.class);
	
	private IGroupBudgetService groupBudgetService;
	
	@Override
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("执行线路类型代售产品自动生成团实际成本表任务");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("settlementStatus", "UNBUDGET");
			List<String> groupCodeList = groupBudgetService.getGroupCode(map);
			if(groupCodeList != null && groupCodeList.size() > 0){
				for(String code : groupCodeList){
					try{
						groupBudgetService.autoCreateFinalBudget(code);
					}catch (Exception e) {
						log.error("自动生成团成本表异常。团号：" + code);
						log.error(GroupBudgetCreateJob.class, e);
					}
				}
			}
			log.info("线路类型代售产品自动生成团实际成本表任务完成");
		}
	}

	public IGroupBudgetService getGroupBudgetService() {
		return groupBudgetService;
	}

	public void setGroupBudgetService(IGroupBudgetService groupBudgetService) {
		this.groupBudgetService = groupBudgetService;
	}
}
