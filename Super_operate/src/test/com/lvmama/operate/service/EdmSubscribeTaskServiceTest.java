package com.lvmama.operate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.po.edm.EdmSubscribeTask;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-back-beans.xml" })
public class EdmSubscribeTaskServiceTest {

     @Resource
     private EdmSubscribeTaskService edmSubscribeTaskService;
     @Test
     public void testSelect() {
          Map<String,Object> parameters = new HashMap<String,Object>();
          
          List<EdmSubscribeTask> list = edmSubscribeTaskService.search(parameters);
          Assert.assertEquals(true, list.size()>0);
     }

     @Test
     public void testCount() {
          Map<String,Object> parameters = new HashMap<String,Object>();
          Long count = edmSubscribeTaskService.count(parameters);
          Assert.assertEquals(true, count>0);
     }

     @Test
     public void testInsert() {
          EdmSubscribeTask object = new EdmSubscribeTask();
          object.setTaskId(1L);
          object.setTaskName("EDM邮件发送单元测试任务名称");
          object.setTaskDesc("这是我的一个单元测试任务名称");
          object.setTaskStatus("Y");
          object.setTaskType("EDM_TASK_MARKETING");//EDM_TASK_MARKETING,EDM_TASK_TRIGGER
          object.setChannelType("0");//0,3
          object.setTempId(1L);
          object.setUserGroupId(1L);
          object.setEmailTitle("测试标题");
          object.setSendTime("1 0");
          object.setSendCycle("1");
          object.setSendUser("驴妈妈旅游网");
          object.setSendEmail("webmaster@lvmama.com");
          object.setCreateUser("shangzhengyaun");
          Long key = edmSubscribeTaskService.insert(object);
          Assert.assertEquals(true, key!=null);
     }

     @Test
     public void testUpdate() {
          Map<String,Object> parameters = new HashMap<String,Object>();
          parameters.put("taskId", 1L);
          parameters.put("_startRow", 1);
          parameters.put("_endRow", 1);
          EdmSubscribeTask object = edmSubscribeTaskService.search(parameters).get(0);
          object.setTaskName("EDM邮件发送单元测试任务名称");
          object.setTaskDesc("这是我的一个单元测试任务名称");
          object.setTaskStatus("Y");
          object.setTaskType("EDM_TASK_MARKETING");//EDM_TASK_MARKETING,EDM_TASK_TRIGGER
          object.setChannelType("0");//0,3
          object.setTempId(1L);
          object.setUserGroupId(1L);
          object.setEmailTitle("测试标题修改");
          object.setSendTime("1 0");
          object.setSendCycle("1");
          object.setSendUser("驴妈妈旅游网");
          object.setSendEmail("webmaster@lvmama.com");
          object.setUpdateUser("shangzhengyaun");
          int result = edmSubscribeTaskService.update(object);
          Assert.assertEquals(true,result==1);
          EdmSubscribeTask object2 = edmSubscribeTaskService.search(parameters).get(0);
          Assert.assertEquals(true,"测试标题修改".equals(object2.getEmailTitle()));
     }

}
