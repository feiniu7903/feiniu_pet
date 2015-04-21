/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.work;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.pet.BaseTest;
import com.lvmama.comm.pet.po.work.WorkTask;
import com.lvmama.comm.pet.service.work.WorkTaskService;
/**
 * WorkTask 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class WorkTaskServiceTest extends BaseTest{
	@Autowired
	private WorkTaskService workTaskService;
//	@Test
	public void insert(){
		WorkTask workTask=new WorkTask();
		Long id=workTaskService.insert(workTask);
		Assert.assertNotNull(id);
	}
//	@Test
	public void select(){
		WorkTask workTask=workTaskService.getWorkTaskById(1L);
		Assert.assertNotNull(workTask);
	}
	@Test
	public void getReciverOffLineTaskByGroup(){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("workGroupId", 1);
		//获取发送人和接收人都不在线的任务
		params.put("count", 1000);
		params.put("createrWorkStatus","OFFLINE");
		List<WorkTask> taskListCof=workTaskService.getReciverOffLineTaskByGroup(params);
		System.out.println("------------------------"+taskListCof.size());
	}
}
