package com.lvmama.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.po.ord.OrdOrderBatch;
import com.lvmama.comm.bee.po.ord.OrdOrderBatchOrder;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderBatchService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.passport.dao.PassCodeDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
public class OrderBatchServiceTest implements ApplicationContextAware{
	@Resource
	private OrderBatchService orderBatchService;
	
	@Autowired
	private PassCodeDAO passCodeDAO;
	@Test
	public void testAddBatch(){
		OrdOrderBatch batch = new OrdOrderBatch();
		batch.setBatchCount(1000);
		batch.setContacts("张伟");
		batch.setContactsPhone("123456789");
		batch.setCreator(2);
		batch.setProductBranchId(1);
		batch.setProductId(2);
		batch.setStatus(Constant.ORDER_BATCH_STATUS.BATCHINIG.name());
		batch.setReson("测试用的");
		batch.setCreatetime(new Date());
//		1302983 ORDER_ID
		Long batchId = orderBatchService.insert(batch);
		
		Long orderId=1302983L;
		OrdOrderBatchOrder ob = new OrdOrderBatchOrder();
		ob.setBatchId(batchId);
		ob.setOrderId(orderId);
		
		orderBatchService.inserBatchOrder(ob);
		
	}
	
	@Test
	public void testListpasscode(){
		List<OrdOrderBatch> obs =orderBatchService.listBatchPassCode(10017L);
		for(OrdOrderBatch ob : obs){
			System.out.println(ob.getProductName());
			System.out.println(ob.getAddCode());
		}
	}
	
	@Test
	public void testUpdatePassCode(){
		PassCode pc=new PassCode();
		System.out.println("========testUpdatePassCode==begin=====");
		pc.setStatusExplanation(null);
		pc.setMobile("13671629937");
		pc.setCodeId(276924L);
		passCodeDAO.updatePassCode(pc);
		System.out.println("========testUpdatePassCode===end===");
	}
	
	@Test
	public void testList(){
		Map batch = new HashMap();
		batch.put("contacts", "张伟");
		
		Page<OrdOrderBatch> obs = orderBatchService.selectByParams(batch);
		for (OrdOrderBatch ordOrderBatch : obs.getItems()) {
			System.out.println(ordOrderBatch.getBatchCount());
		}
	} 
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		SpringBeanProxy.setApplicationContext(arg0);
	}
	
}