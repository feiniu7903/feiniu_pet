package com.lvmama.operate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.lvmama.comm.pet.po.edm.EdmSubscribeTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-back-beans.xml" })
public class EdmSubscribeTemplateServiceTest {

     @Resource
     private EdmSubscribeTemplateService edmSubscribeTemplateService;
     
     @Test
     public void testCount() {
          Map<String,Object> parameters = new HashMap<String,Object>();
          parameters.put("_startRow", 1);
          parameters.put("_endRow", 10);
          Long result = edmSubscribeTemplateService.count(parameters);
          Assert.isTrue(result>0);
     }

     @Test
     public void testInsert() {
          EdmSubscribeTemplate object = new EdmSubscribeTemplate();
          object.setTempName("EDM测试模板");
          object.setTempStatus("Y");
          object.setTempUrl("http://www.omsys.com.cn/scbank/index.php?aid=c2NiYW5rXzIwMjBfMF95ZXM=");
          object.setCreateUser("shangzhengyuan");
          Long result = edmSubscribeTemplateService.insert(object);
          Assert.isTrue(result!=null);
     }

     @Test
     public void testSelect() {
          Map<String,Object> parameters = new HashMap<String,Object>();
          parameters.put("_startRow", 1);
          parameters.put("_endRow", 10);
          List<EdmSubscribeTemplate> list = edmSubscribeTemplateService.search(parameters);
          Assert.isTrue(list!=null && list.size()>0);
     }

     @Test
     public void testUpdate() {
          Map<String,Object> parameters = new HashMap<String,Object>();
          parameters.put("taskId", 1L);
          parameters.put("_startRow", 1);
          parameters.put("_endRow", 1);
          EdmSubscribeTemplate object = edmSubscribeTemplateService.search(parameters).get(0);
          object.setTempName("EDM测试模板");
          object.setTempStatus("N");
          object.setTempUrl("http://www.omsys.com.cn/scbank/index.php?aid=c2NiYW5rXzIwMjBfMF95ZXM=");
          object.setUpdateUser("shangzhengyuan");
          int i=edmSubscribeTemplateService.update(object);
          Assert.isTrue(i==1);
          object = edmSubscribeTemplateService.search(parameters).get(0);
          Assert.isNull(object!=null && "N".equals(object.getTempStatus()));
     }

}
