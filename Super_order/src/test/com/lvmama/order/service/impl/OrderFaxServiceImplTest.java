package com.lvmama.order.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.service.ebooking.CertificateService;
import com.lvmama.comm.bee.service.ebooking.EbkCertificateService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.spring.SpringBeanProxy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
public class OrderFaxServiceImplTest implements ApplicationContextAware{
	@Resource
	private EbkCertificateService ebkCertificateService;
	@Resource
	private OrderService orderServiceProxy;
	@Resource
	private BCertificateTargetService bCertificateTargetService;
	@Resource
	private SupplierService supplierService;
	
	
	@Test
	public final void testAddFaxTaskDataByOrderId() {
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(1308374L);
		Map<Long, SupBCertificateTarget> sbct = this.getSupBCertificateTarget(ordOrder);
		ebkCertificateService.createSupplierCertificate(ordOrder, sbct,CertificateService.ORDER_PAYMENT,"false", null);
	}
	/**
	 * 
	 * @param ordOrderItemMetas
	 * @return  key targetId
	 * 			value SupBCertificateTarget
	 */
	private Map<Long, SupBCertificateTarget> getSupBCertificateTarget(OrdOrder order){
		Map<Long, SupBCertificateTarget> s = new HashMap<Long, SupBCertificateTarget>();
		SupBCertificateTarget sct = null;
		for (OrdOrderItemMeta ordOrderItemMeta : order.getAllOrdOrderItemMetas()) {
			sct = bCertificateTargetService.getSuperMetaBCertificateByMetaProductId(ordOrderItemMeta.getMetaProductId());
			sct.setSupplier(supplierService.getSupplier(sct.getSupplierId()));
			s.put(sct.getTargetId(), sct);
			ordOrderItemMeta.setBcertificateTarget(sct);
		}
		return s;
	}
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		SpringBeanProxy.setApplicationContext(arg0);
	}
	
}