package com.lvmama.order.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProdTime;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pub.ComAudit;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.OrdTimeInfo;
import com.lvmama.comm.bee.vo.ord.Invoice;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.order.dao.OrdOrderItemProdTimeDAO;
import com.lvmama.order.dao.OrderAuditDAO;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.order.dao.OrderItemProdDAO;
import com.lvmama.order.dao.OrderPersonDAO;
import com.lvmama.order.service.OrderCreateService;
import com.lvmama.order.service.proxy.OrderServiceProxy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
public class OtherBuildImplTest {
	@Resource
	private OrderCreateService orderBuildServiceProxy;
	@Resource
	private OrderPersonDAO orderPersonDAO;
	@Resource
	private OrderItemMetaDAO orderItemMetaDAO;
	@Resource
	private OrderItemProdDAO orderItemProdDAO;
	@Resource
	private OrderAuditDAO orderAuditDAO;
	@Resource
	private OrdOrderItemProdTimeDAO ordOrderItemProdTimeDAO;
	@Resource
	private OrderServiceProxy orderServiceImpl;
	@Test
	public void testConserveOrderInfo() {

		/**
		 * 测试ord_order表
		 */
		OrdOrder orderInfoManyKindsProduct = orderBuildServiceProxy.createOrder(createManyKindsProductBuyInfo(), null, null);
		OrdOrder orderManyKindsProduct = orderServiceImpl.createOrder(createManyKindsProductBuyInfo());
		compareOrderInfoAndOrder(orderInfoManyKindsProduct, orderManyKindsProduct);
		
		OrdOrder orderInfoOneKindProduct = orderBuildServiceProxy.createOrder(createOneKindProductBuyInfo(), null, null);
		OrdOrder orderOneKindProduct = orderServiceImpl.createOrder(createOneKindProductBuyInfo());
		compareOrderInfoAndOrder(orderInfoOneKindProduct, orderOneKindProduct);

	}

	private void compareOrderInfoAndOrder(OrdOrder orderInfo, OrdOrder order) {
		Assert.assertNotNull(orderInfo);
		Assert.assertNotNull(order);
		System.out.println("orderInfo.getOrderId() = " + orderInfo.getOrderId());
		System.out.println("order.getOrderId() = " + order.getOrderId());
		Assert.assertEquals(orderInfo.getCreateTime(), order.getCreateTime());
		Assert.assertEquals(orderInfo.getPaymentStatus(), order.getPaymentStatus());
		Assert.assertEquals(orderInfo.getOughtPay(), order.getOughtPay());
		Assert.assertEquals(orderInfo.getActualPay(), order.getActualPay());
		Assert.assertEquals(orderInfo.getApproveStatus(), order.getApproveStatus());
		Assert.assertEquals(orderInfo.getPaymentTarget(), order.getPaymentTarget());
		Assert.assertEquals(orderInfo.getCancelReason(), order.getCancelReason());
		Assert.assertEquals(orderInfo.getCancelOperator(), order.getCancelOperator());
		Assert.assertEquals(orderInfo.getCancelTime(), order.getCancelTime());
		Assert.assertEquals(orderInfo.getUserMemo(), order.getUserMemo());
		Assert.assertEquals(orderInfo.getUserReply(), order.getUserReply());
		Assert.assertEquals(orderInfo.getWaitPayment(), order.getWaitPayment());
		Assert.assertEquals(orderInfo.getUserId(), order.getUserId());
		Assert.assertEquals(orderInfo.getResourceConfirm(), order.getResourceConfirm());
		Assert.assertEquals(orderInfo.getOrderStatus(), order.getOrderStatus());
		Assert.assertEquals(orderInfo.getChannel(), order.getChannel());
		Assert.assertEquals(orderInfo.getPerformStatus(), order.getPerformStatus());
		Assert.assertEquals(orderInfo.getVisitTime(), order.getVisitTime());
		Assert.assertEquals(orderInfo.getResourceConfirmStatus(), order.getResourceConfirmStatus());
		Assert.assertEquals(orderInfo.getDealTime(), order.getDealTime());
		// Assert.assertEquals(orderInfo.getApproveTime(),
		// order.getApproveTime());
//		Assert.assertEquals(orderInfo.getStockReduced(), order.getStockReduced());
		Assert.assertEquals(orderInfo.getRedail(), order.getRedail());
		Assert.assertEquals(orderInfo.getOriginalOrderId(), order.getOriginalOrderId());
		Assert.assertEquals(orderInfo.getPassport(), order.getPassport());
		// Assert.assertEquals(orderInfo.getTaken(), order.getTaken());
		Assert.assertEquals(orderInfo.getOrderType(), order.getOrderType());
		Assert.assertEquals(orderInfo.getCashRefund(), order.getCashRefund());
		Assert.assertEquals(orderInfo.getIsCashRefund(), order.getIsCashRefund());
		Assert.assertEquals(orderInfo.getPhysical(), order.getPhysical());
		Assert.assertEquals(orderInfo.getNeedInvoice(), order.getNeedInvoice());
		Assert.assertEquals(orderInfo.getNeedSaleService(), order.getNeedSaleService());
		Assert.assertEquals(orderInfo.getTakenOperator(), order.getTakenOperator());
		Assert.assertEquals(orderInfo.getPaymentChannel(), order.getPaymentChannel());

		/**
		 * 测试ord_person表
		 */
		order = orderServiceImpl.queryOrdOrderByOrderId(order.getOrderId());
		Assert.assertNotNull(order);
		List<OrdPerson> personList_new = orderInfo.getPersonList();
		Assert.assertNotNull(personList_new);
		List<OrdPerson> personList_old = order.getPersonList();
		Assert.assertNotNull(personList_old);
		Assert.assertEquals(personList_new.size(), personList_old.size());
		for (int i = 0; i < personList_new.size(); i++) {
			OrdPerson person_new = orderPersonDAO.selectByPrimaryKey(personList_new.get(i).getPersonId());
			Assert.assertNotNull(person_new);
			OrdPerson person_old = orderPersonDAO.selectByPrimaryKey(personList_old.get(i).getPersonId());
			Assert.assertNotNull(person_old);
			Assert.assertEquals(person_new.getAddress(), person_old.getAddress());
			Assert.assertEquals(person_new.getCertNo(), person_old.getCertNo());
			Assert.assertEquals(person_new.getCertType(), person_old.getCertType());
			Assert.assertEquals(person_new.getEmail(), person_old.getEmail());
			Assert.assertEquals(person_new.getFax(), person_old.getFax());
			Assert.assertEquals(person_new.getFaxTo(), person_old.getFaxTo());
			Assert.assertEquals(person_new.getMemo(), person_old.getMemo());
			Assert.assertEquals(person_new.getMobile(), person_old.getMobile());
			Assert.assertEquals(person_new.getName(), person_old.getName());
			Assert.assertEquals(person_new.getObjectType(), person_old.getObjectType());
			Assert.assertEquals(person_new.getPersonType(), person_old.getPersonType());
			Assert.assertEquals(person_new.getPostcode(), person_old.getPostcode());
			Assert.assertEquals(person_new.getQq(), person_old.getQq());
			Assert.assertEquals(person_new.getReceiverId(), person_old.getReceiverId());
			Assert.assertEquals(person_new.getTel(), person_old.getTel());
			Assert.assertEquals(person_new.getZhCertType(), person_old.getZhCertType());
			/**
			 * 序列化比较两个对象值是否相同 首先，对两个对象中某些值不相同的属性设置相同的值 然后通过new
			 * String()进行比较，由于会出现乱码
			 * ，所以控制台看不到输入信息，想看到的话，可以对new出来的String进行MD5加密，然后输出
			 */
			byte[] byte_old;
			byte[] byte_new;
			try {
				ObjectOutputStream stream_old = new ObjectOutputStream(new FileOutputStream("D:\\T.txt"));
				byte_old = new byte[1024];
				person_old.setObjectId(1L);
				person_old.setPersonId(1L);
				stream_old.writeObject(person_old);
				stream_old.write(byte_old);
				stream_old.close();

				ObjectOutputStream stream_new = new ObjectOutputStream(new FileOutputStream("D:\\T.txt"));
				byte_new = new byte[1024];
				person_new.setObjectId(1L);
				person_new.setPersonId(1L);
				stream_new.writeObject(person_new);
				stream_new.write(byte_new);
				stream_new.close();
				System.out.println(new String(byte_old));
				System.out.println(new String(byte_new));
				Assert.assertEquals(new String(byte_old), new String(byte_new));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * 测试ord_invoice表,由于List<OrdInvoice> invoiceList_old =
		 * order.getInvoiceList();中invoiceList_old为null，故而先不进行该项测试
		 */
		/**
		 * 测试ord_order_item_prod表
		 */
		List<OrdOrderItemProd> orderItemProdList_new = orderItemProdDAO.selectByOrderId(orderInfo.getOrderId());
		Assert.assertNotNull(orderItemProdList_new);
		List<OrdOrderItemProd> orderItemProdList_old = orderItemProdDAO.selectByOrderId(order.getOrderId());
		Assert.assertNotNull(orderItemProdList_old);
		Assert.assertEquals(orderItemProdList_new.size(), orderItemProdList_old.size());
		for (int i = 0; i < orderItemProdList_new.size(); i++) {
			OrdOrderItemProd orderItemProd_new = orderItemProdList_new.get(i);
			Assert.assertNotNull(orderItemProd_new);
			OrdOrderItemProd orderItemProd_old = orderItemProdList_old.get(i);
			Assert.assertNotNull(orderItemProd_old);

			byte[] byte_old;
			byte[] byte_new;
			try {
				ObjectOutputStream stream_old = new ObjectOutputStream(new FileOutputStream("D:\\T.txt"));
				byte_old = new byte[1024];
				orderItemProd_old.setOrderId(1L);
				stream_old.writeObject(orderItemProd_old);
				stream_old.write(byte_old);
				stream_old.close();

				ObjectOutputStream stream_new = new ObjectOutputStream(new FileOutputStream("D:\\T.txt"));
				byte_new = new byte[1024];
				orderItemProd_new.setOrderId(1L);
				stream_new.writeObject(orderItemProd_new);
				stream_new.write(byte_new);
				stream_new.close();
				System.out.println(new String(byte_old));
				System.out.println(new String(byte_new));
				Assert.assertEquals(new String(byte_old), new String(byte_new));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			/**
			 * 测试ord_order_item_prod_time表
			 */
			List<OrdOrderItemProdTime> orderItemProdTimeList_new = ordOrderItemProdTimeDAO.selectProdTimeByProdItemId(orderItemProd_new.getOrderItemProdId());
			Assert.assertNotNull(orderItemProdTimeList_new);
			List<OrdOrderItemProdTime> orderItemProdTimeList_old = ordOrderItemProdTimeDAO.selectProdTimeByProdItemId(orderItemProd_old.getOrderItemProdId());
			Assert.assertNotNull(orderItemProdTimeList_old);
			Assert.assertEquals(orderItemProdTimeList_new.size(), orderItemProdTimeList_old.size());
			for (int j = 0; j < orderItemProdTimeList_new.size(); j++) {
				OrdOrderItemProdTime orderItemProdTime_new = orderItemProdTimeList_new.get(j);
				Assert.assertNotNull(orderItemProdTime_new);
				OrdOrderItemProdTime orderItemProdTime_old = orderItemProdTimeList_old.get(j);
				Assert.assertNotNull(orderItemProdTime_old);
				try {
					ObjectOutputStream stream_old = new ObjectOutputStream(new FileOutputStream("D:\\T.txt"));
					byte_old = new byte[1024];
					stream_old.writeObject(orderItemProdTime_old);
					stream_old.write(byte_old);
					stream_old.close();

					ObjectOutputStream stream_new = new ObjectOutputStream(new FileOutputStream("D:\\T.txt"));
					byte_new = new byte[1024];
					stream_new.writeObject(orderItemProdTime_new);
					stream_new.write(byte_new);
					stream_new.close();
					System.out.println(new String(byte_old));
					System.out.println(new String(byte_new));
					Assert.assertEquals(new String(byte_old), new String(byte_new));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		/**
		 * 测试ord_order_item_meta表
		 */

		List<OrdOrderItemMeta> orderItemMetaList_new = orderItemMetaDAO.selectByOrderId(orderInfo.getOrderId());
		System.out.println("orderInfo.getOrderId() = " + orderInfo.getOrderId());
		System.out.println("order.getOrderId() = " + order.getOrderId());
		Assert.assertNotNull(orderItemMetaList_new);
		System.out.println("orderItemMetaList_new = " + orderItemMetaList_new);
		List<OrdOrderItemMeta> orderItemMetaList_old = orderItemMetaDAO.selectByOrderId(order.getOrderId());
		Assert.assertNotNull(orderItemMetaList_old);
		Assert.assertEquals(orderItemMetaList_new.size(), orderItemMetaList_old.size());
		for (int i = 0; i < orderItemMetaList_new.size(); i++) {
			OrdOrderItemMeta orderItemMeta_new = orderItemMetaList_new.get(i);
			Assert.assertNotNull(orderItemMeta_new);
			OrdOrderItemMeta orderItemMeta_old = orderItemMetaList_old.get(i);
			Assert.assertNotNull(orderItemMeta_old);
			byte[] byte_old;
			byte[] byte_new;
			try {
				ObjectOutputStream stream_old = new ObjectOutputStream(new FileOutputStream("D:\\T.txt"));
				byte_old = new byte[1024];
				orderItemMeta_old.setOrderId(1L);
				orderItemMeta_old.setOrderItemId(1L);
				orderItemMeta_old.setOrderItemMetaId(1L);
				stream_old.writeObject(orderItemMeta_old);
				stream_old.write(byte_old);
				stream_old.close();

				ObjectOutputStream stream_new = new ObjectOutputStream(new FileOutputStream("D:\\T.txt"));
				byte_new = new byte[1024];
				orderItemMeta_new.setOrderId(1L);
				orderItemMeta_new.setOrderItemId(1L);
				orderItemMeta_new.setOrderItemMetaId(1L);
				stream_new.writeObject(orderItemMeta_new);
				stream_new.write(byte_new);
				stream_new.close();
				System.out.println(new String(byte_old));
				System.out.println(new String(byte_new));
				Assert.assertEquals(new String(byte_old), new String(byte_new));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/**
		 * 测试ord_order_item_meta_time表
		 */

		/**
		 * 测试com_audit表
		 */
		Map<String, String> paramMap_new = new HashMap<String, String>();
		paramMap_new.put("objectId", orderInfo.getOrderId().toString());
		List<ComAudit> comAuditList_new = orderAuditDAO.selectComAuditByParamMap(paramMap_new);
		Assert.assertNotNull(comAuditList_new);

		Map<String, String> paramMap_old = new HashMap<String, String>();
		paramMap_old.put("objectId", order.getOrderId().toString());
		List<ComAudit> comAuditList_old = orderAuditDAO.selectComAuditByParamMap(paramMap_old);
		Assert.assertNotNull(comAuditList_old);
		Assert.assertEquals(comAuditList_new.size(), comAuditList_old.size());
		for (int i = 0; i < comAuditList_new.size(); i++) {
			ComAudit comAudit_new = comAuditList_new.get(i);
			Assert.assertNotNull(comAudit_new);
			ComAudit comAudit_old = comAuditList_old.get(i);
			Assert.assertNotNull(comAudit_old);
			Assert.assertEquals(comAudit_new, comAudit_old);
		}
	}

	private BuyInfo createManyKindsProductBuyInfo() {
		BuyInfo buyInfo = new BuyInfo();
		buyInfo.setChannel("FRONTEND");
		buyInfo.setUserId("UnitTest");
		buyInfo.setPaymentTarget("TOLVMAMA");
		buyInfo.setUserMemo("UnitTest");
		buyInfo.setResourceConfirmStatus("true");

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2011);
		cal.set(Calendar.MONTH, 7);
		cal.set(Calendar.DATE, 10);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);


		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.YEAR, 2011);
		cale.set(Calendar.MONTH, 6);
		cale.set(Calendar.DATE, 31);
		cale.set(Calendar.HOUR_OF_DAY, 0);
		cale.set(Calendar.MINUTE, 0);
		cale.set(Calendar.SECOND, 0);
		cale.set(Calendar.MILLISECOND, 0);

		// 其他
		BuyInfo.Item item1 = new BuyInfo.Item();
		item1.setProductId(29573);
		item1.setQuantity(1);
		item1.setVisitTime(cal.getTime());
		item1.setFaxMemo("Test4");
		
		// 其他
		BuyInfo.Item item2 = new BuyInfo.Item();
		item2.setProductId(29375);
		item2.setQuantity(1);
		item2.setVisitTime(cale.getTime());
		item2.setFaxMemo("Test3");

		OrdTimeInfo timeInfo1 = new OrdTimeInfo();
		timeInfo1.setProductId(item1.getProductId());
		timeInfo1.setQuantity(2L);
		timeInfo1.setVisitTime(cal.getTime());
		
		OrdTimeInfo timeInfo2 = new OrdTimeInfo();
		timeInfo2.setProductId(item2.getProductId());
		timeInfo2.setQuantity(2L);
		timeInfo2.setVisitTime(cal.getTime());


		List<OrdTimeInfo> timeInfoList1 = new ArrayList<OrdTimeInfo>();
		timeInfoList1.add(timeInfo1);
		
		List<OrdTimeInfo> timeInfoList2 = new ArrayList<OrdTimeInfo>();
		timeInfoList2.add(timeInfo2);
		
		item1.setTimeInfoList(timeInfoList1);
		item2.setTimeInfoList(timeInfoList2);
		
		List<BuyInfo.Item> itemList = new ArrayList<BuyInfo.Item>();
		itemList.add(item1);
		itemList.add(item2);
		buyInfo.setItemList(itemList);

		Person person1 = new Person();
		person1.setPersonType("traveller");
		person1.setName("www");
		person1.setMobile("13918066110");
		person1.setEmail("sericwu@hotmail.com");
		Person person2 = new Person();
		person2.setPersonType("traveller");
		person2.setName("www");
		person2.setFax("13918066110");
		person2.setMemo("sericwu@hotmail.com");
		List<Person> personList = new ArrayList<Person>();
		personList.add(person1);
		personList.add(person2);
		buyInfo.setPersonList(personList);

		Invoice invoice1 = new Invoice();
		invoice1.setTitle("UnitTest1");
		invoice1.setDetail("UnitTest1Detail");
		invoice1.setAmount(0L);
		invoice1.setMemo("" + new Date());
		Invoice invoice2 = new Invoice();
		invoice2.setTitle("UnitTest2");
		invoice2.setDetail("UnitTest2Detail");
		invoice2.setAmount(0L);
		invoice2.setMemo("" + new Date());
		List<Invoice> invoiceList = new ArrayList<Invoice>();
		invoiceList.add(invoice1);
		invoiceList.add(invoice2);
		buyInfo.setInvoiceList(invoiceList);
		return buyInfo;
	}

	private BuyInfo createOneKindProductBuyInfo() {
		BuyInfo buyInfo = new BuyInfo();
		buyInfo.setChannel("FRONTEND");
		buyInfo.setUserId("UnitTest");
		buyInfo.setPaymentTarget("TOLVMAMA");
		buyInfo.setUserMemo("UnitTest");
		buyInfo.setResourceConfirmStatus("true");

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2011);
		cal.set(Calendar.MONTH, 7);
		cal.set(Calendar.DATE, 10);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		// 其他
		BuyInfo.Item item1 = new BuyInfo.Item();
		item1.setProductId(29573);
		item1.setQuantity(1);
		item1.setVisitTime(cal.getTime());
		item1.setFaxMemo("Test1");

		OrdTimeInfo timeInfo = new OrdTimeInfo();
		timeInfo.setProductId(item1.getProductId());
		timeInfo.setQuantity(2L);
		timeInfo.setVisitTime(cal.getTime());

		List<OrdTimeInfo> timeInfoList = new ArrayList<OrdTimeInfo>();
		timeInfoList.add(timeInfo);
		item1.setTimeInfoList(timeInfoList);

		List<BuyInfo.Item> itemList = new ArrayList<BuyInfo.Item>();
		itemList.add(item1);
		buyInfo.setItemList(itemList);

		Person person1 = new Person();
		person1.setPersonType("traveller");
		person1.setName("www");
		person1.setMobile("13918066110");
		person1.setEmail("sericwu@hotmail.com");
		Person person2 = new Person();
		person2.setPersonType("traveller");
		person2.setName("www");
		person2.setFax("13918066110");
		person2.setMemo("sericwu@hotmail.com");
		List<Person> personList = new ArrayList<Person>();
		personList.add(person1);
		personList.add(person2);
		buyInfo.setPersonList(personList);

		Invoice invoice1 = new Invoice();
		invoice1.setTitle("UnitTest1");
		invoice1.setDetail("UnitTest1Detail");
		invoice1.setAmount(0L);
		invoice1.setMemo("" + new Date());
		Invoice invoice2 = new Invoice();
		invoice2.setTitle("UnitTest2");
		invoice2.setDetail("UnitTest2Detail");
		invoice2.setAmount(0L);
		invoice2.setMemo("" + new Date());
		List<Invoice> invoiceList = new ArrayList<Invoice>();
		invoiceList.add(invoice1);
		invoiceList.add(invoice2);
		buyInfo.setInvoiceList(invoiceList);
		return buyInfo;
	}

}
