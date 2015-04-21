package com.lvmama.back;

import static org.junit.Assert.*;


import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.python.core.exceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ibm.db2.jcc.uw.classloader.e;
import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ebooking.EbkOrderDataRev;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.service.ebooking.EbkCertificateService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.cert.builder.EbkCertBuilder;
import com.lvmama.comm.vo.cert.builder.EbkCertBuilderFactory;
import com.lvmama.comm.vo.cert.builder.EbkCertDataBuilder;
import com.lvmama.comm.vo.cert.builder.content.EbkCertTicketBuilder;
import com.lvmama.comm.vo.cert.builder.data.EbkCertDataTicketBuilder;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:applicationContext-back-beans.xml" })
//@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class EbkCertDataBuilderTest {
	
	static Map<String,Object> params = new HashMap<String, Object>();
	@Autowired	
	private static OrderService orderServiceProxy;
	@Autowired
	private static EbkCertificateService ebkCertificateService;
	
	
	private static EbkCertificate ebkCertificate;
	
	private static Long orderId;
	private static Long ebkCertificateId;

	//@Before
	public static void doBefore(){
		orderId = 1311605L;
		ebkCertificateId = 6521L;
		init();
		
	}
	private static void init(){
		OrdOrder order =orderServiceProxy.queryOrdOrderByOrderId(orderId);
		params.put(EbkCertDataBuilder.ORD_ORDER, order);
		ebkCertificate = ebkCertificateService.selectEbkCertificateDetailByPrimaryKey(ebkCertificateId);
		List<OrdOrderItemMeta> list = order.getAllOrdOrderItemMetas();
		Map<Long,OrdOrderItemMeta> map = new HashMap<Long,OrdOrderItemMeta>();
		for(OrdOrderItemMeta meta:list){
			map.put(meta.getOrderItemMetaId(), meta);
		}
		for(EbkCertificateItem item:ebkCertificate.getEbkCertificateItemList()){
			item.setOrderItemMeta(map.get(item.getOrderItemMetaId()));
		}
		params.put(EbkCertDataBuilder.EBK_CERTIFICATE, ebkCertificate);
	}
	@Before
	public static void doBeforeHotel(){
		orderId = 1311500L;
		ebkCertificateId = 6494L;
		init();
	}
	
	public static void doBeforeHotelSuit(){
		orderId = 1311719L;
		ebkCertificateId = 6605L;
		init();
	}
	
	public static void doRoute(){
		orderId = 1307866L;
		ebkCertificateId = 6645L;
		init();
	}
	
	@Test
	public static void testMakeData() {
		EbkCertDataBuilder builder = EbkCertBuilderFactory.create(params);
		builder.makeData();
		List<EbkOrderDataRev> list =builder.getDataRevList();
		System.out.println(list.size());
//			System.out.println(rev.getDataType()+"\t\t"+rev.getValue());
		//ebkCertificateService.createData(list);
		System.out.println("done.");
	}
	
	public static void testMakeFax()throws Exception{
		EbkCertBuilder builder =EbkCertBuilderFactory.create(ebkCertificate);
		builder.init();
		FileWriter fw =new FileWriter(new File("d:/"+ebkCertificate.getProductType()+".html"));
		builder.makeCertContent(ebkCertificate,fw);
//		System.out.println(txt);
	}

	public static void main(String[] args) throws Exception{
		try{
			ApplicationContext context =new ClassPathXmlApplicationContext("classpath:applicationContext-back-beans.xml");
			SpringBeanProxy.setApplicationContext(context);
			orderServiceProxy =(OrderService)context.getBean("orderServiceProxy");
			ebkCertificateService = (EbkCertificateService)context.getBean("ebkCertificateService");
	//		doBefore();
//			doBeforeHotel();
//			doRoute();
			doBeforeHotelSuit();
//			testMakeData();
			testMakeFax();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		System.exit(0);
	}
}
