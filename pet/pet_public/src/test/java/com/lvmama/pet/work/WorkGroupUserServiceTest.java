/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.work;
import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.pet.BaseTest;
import com.lvmama.comm.pet.po.work.WorkGroupUser;
import com.lvmama.comm.pet.service.work.WorkGroupUserService;
/**
 * WorkGroupUser 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class WorkGroupUserServiceTest extends BaseTest{
	@Autowired
	private WorkGroupUserService workGroupUserService;
	@Test
	public void insert(){
		WorkGroupUser workGroupUser=new WorkGroupUser();
		Long id=workGroupUserService.insert(workGroupUser);
		Assert.assertNotNull(id);
	}
	@Test
	public void select(){
		WorkGroupUser workGroupUser=workGroupUserService.getWorkGroupUserById(1L);
		Assert.assertNotNull(workGroupUser);
	}
}
