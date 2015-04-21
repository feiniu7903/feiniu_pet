/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.bee.eplace;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.bee.BaseTest;
import com.lvmama.comm.bee.po.eplace.EbkPermission;
import com.lvmama.comm.bee.service.eplace.EbkPermissionService;
/**
 * PermPermission 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class PermPermissionServiceTest extends BaseTest{
	@Autowired
	private EbkPermissionService permPermissionService;
//	@Test
	public void insert(){
		EbkPermission permPermission=new EbkPermission();
		Long id=permPermissionService.insert(permPermission);
		Assert.assertNotNull(id);
	}
//	@Test
	public void select(){
		EbkPermission permPermission=permPermissionService.getEbkPermissionById(21L);
		Assert.assertNotNull(permPermission);
	}
//	@Test
	public void insertUserPermission(){
		List<Long> permissionIds=new ArrayList<Long>();
		permissionIds.add(21L);
		permPermissionService.insertUserPermission(3L, permissionIds);
//		Assert.assertNotNull(permPermission);
	}
	@Test
	public void getPermPermissionByUserId(){
		List<EbkPermission> permPermission=permPermissionService.getEbkPermissionByUserId(3L);
		Assert.assertNotNull(permPermission);
		Assert.assertTrue(permPermission.size()>0);
	}
}
