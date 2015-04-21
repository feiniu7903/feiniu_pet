/**
 * 
 */
package com.lvmama.order.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

/**
 * @author yangbin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
public class EbkCertBuilderTest implements ApplicationContextAware {

	static Map<String,Object> params = new HashMap<String, Object>();
	@Resource	
	private OrderService orderServiceProxy;
	@Resource
	private EbkCertificateService ebkCertificateService;
	
	
	private static EbkCertificate ebkCertificate;
	
	private Long orderId;
	private Long ebkCertificateId;

	@Before
	public void doBefore(){
		orderId = 1313186L;
		ebkCertificateId = 7932L;
		init();
		
	}
	private void init(){
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
	//@Before
//	public static void doBeforeHotel(){
//		orderId = 1311500L;
//		ebkCertificateId = 6494L;
//		init();
//	}
//	
//	public static void doBeforeHotelSuit(){
//		orderId = 1311719L;
//		ebkCertificateId = 6605L;
//		init();
//	}
//	
//	public static void doRoute(){
//		orderId = 1307866L;
//		ebkCertificateId = 6645L;
//		init();
//	}
	
	@Test
	public void testMakeData() {
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
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		SpringBeanProxy.setApplicationContext(arg0);
	}

//	public static void main(String[] args) throws Exception{
//		try{
//			ApplicationContext context =new ClassPathXmlApplicationContext("classpath:applicationContext-back-beans.xml");
//			SpringBeanProxy.setApplicationContext(context);
//			orderServiceProxy =(OrderService)context.getBean("orderServiceProxy");
//			ebkCertificateService = (EbkCertificateService)context.getBean("ebkCertificateService");
//	//		doBefore();
////			doBeforeHotel();
////			doRoute();
//			doBeforeHotelSuit();
////			testMakeData();
//			testMakeFax();
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//		System.exit(0);
//	}
}
