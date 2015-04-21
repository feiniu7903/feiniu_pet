package com.lvmama.operate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.po.edm.EdmSubscribeUserGroup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-back-beans.xml" })
public class EdmSubscribeUserGroupServiceTest {

     @Resource
     private EdmSubscribeUserGroupService edmSubscribeUserGroupService;
     @Test
     public void testSelect() {
          Map<String,Object> parameters = new HashMap<String,Object>();
          parameters.put("_startRow", 1);
          parameters.put("_endRow", 10);
          List<EdmSubscribeUserGroup> list = edmSubscribeUserGroupService.search(parameters);
          Assert.assertEquals(true, list.size()>0);
     }

     @Test
     public void testCount() {
          Map<String,Object> parameters = new HashMap<String,Object>();
          parameters.put("_startRow", 1);
          parameters.put("_endRow", 10);
          Long count = edmSubscribeUserGroupService.count(parameters);
          Assert.assertEquals(true, count>0);
     }

     @Test
     public void testInsert() {
          EdmSubscribeUserGroup object = new EdmSubscribeUserGroup();
          object.setUserGroupName("EDM测试用户组");
          object.setFilterType("EDM_MARKETING");
          object.setUserGroupStatus("Y");
          object.setUserSubscribeType("MARKETING_EMAIL");
          object.setEmailIsValid("Y");
          /*object.setRegisterDateFrom("2011-01-02");
          object.setRegisterDateTo("2011-12-30");
          object.setLoginDateFrom("2011-01-02");
          object.setLoginDateTo("2011-12-30");*/
          object.setUserAddress("110000;620000;120000");
          object.setCreateUser("shangzhengyuan");
          Long key =edmSubscribeUserGroupService.insert(object);
          Assert.assertNull(key);
     }

     @Test
     public void testUpdate() {
          Map<String,Object> parameters = new HashMap<String,Object>();
          parameters.put("taskId", 1L);
          parameters.put("_startRow", 1);
          parameters.put("_endRow", 1);
          EdmSubscribeUserGroup object = edmSubscribeUserGroupService.search(parameters).get(0);
          object.setUserGroupName("EDM测试用户组");
          object.setFilterType("EDM_MARKETING");
          object.setUserGroupStatus("Y");
          object.setUserSubscribeType("MARKETING_EMAIL");
          object.setEmailIsValid("Y");
          /*object.setRegisterDateFrom("2011-01-02");
          object.setRegisterDateTo("2011-12-30");
          object.setLoginDateFrom("2011-01-02");
          object.setLoginDateTo("2011-12-30");*/
          object.setUserAddress("110000;620000;120000");
          object.setCreateUser("shangzhengyuan");
          int result = edmSubscribeUserGroupService.update(object);
          Assert.assertEquals(result, 1);
          object = edmSubscribeUserGroupService.search(parameters).get(0);
          Assert.assertNotNull(object);
     }

}
