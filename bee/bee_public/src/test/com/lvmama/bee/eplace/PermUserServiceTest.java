/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.bee.eplace;
import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.bee.BaseTest;
import com.lvmama.comm.bee.po.eplace.EbkUser;
import com.lvmama.comm.bee.service.eplace.EbkUserService;
/**
 * PermUser 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class PermUserServiceTest extends BaseTest{
	@Autowired
	private EbkUserService permUserService;
//	@Test
	public void insert(){
		EbkUser permUser=new EbkUser();
		Long id=permUserService.insert(permUser);
		Assert.assertNotNull(id);
	}
//	@Test
	public void select(){
		EbkUser permUser=permUserService.getEbkUserById(21L);
		Assert.assertNotNull(permUser);
	}
	@Test
	public void getPermUserById(){
		EbkUserService permUserService2=getBean("permUserService", EbkUserService.class);
		EbkUser permUser=permUserService2.getEbkUserById(21L);
		Assert.assertNotNull(permUser);
	}
}
