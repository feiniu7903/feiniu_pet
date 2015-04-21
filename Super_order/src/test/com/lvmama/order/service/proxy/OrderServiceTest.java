package com.lvmama.order.service.proxy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderSum;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.ExcludedContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.FinishSettlementItemRelate;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.InvoiceRelate;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.MetaPerformRelate;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.MoneyAccountChangeLogRelate;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.MoneyDrawRelate;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrdSettlementRelate;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderTimeRange;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PassPortDetailRelate;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PassPortSummaryRelate;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PerformDetailRelate;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PerformDetailSortTypeEnum;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PlayMoneyRelate;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.SaleServiceRelate;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.SettlementItemRelate;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.SettlementQueueRelate;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.SortTypeEnum;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.bee.vo.ord.Tours;
import com.lvmama.comm.vo.Constant.AUDIT_TAKEN_STATUS;
import com.lvmama.comm.vo.Constant.CHANNEL;
import com.lvmama.comm.vo.Constant.FAX_TASK_TYPE;
import com.lvmama.comm.vo.Constant.FINC_CASH_STATUS;
import com.lvmama.comm.vo.Constant.MoneyAccountChangeType;
import com.lvmama.comm.vo.Constant.ORDER_APPROVE_STATUS;
import com.lvmama.comm.vo.Constant.ORDER_PERFORM_STATUS;
import com.lvmama.comm.vo.Constant.ORDER_RESOURCE_STATUS;
import com.lvmama.comm.vo.Constant.ORDER_STATUS;
import com.lvmama.comm.vo.Constant.PAYMENT_STATUS;
import com.lvmama.comm.vo.Constant.PAYMENT_TARGET;
import com.lvmama.comm.vo.Constant.PlayMoneyType;
import com.lvmama.comm.vo.Constant.SETTLEMENT_DIRECTION;
import com.lvmama.comm.vo.Constant.SUB_PRODUCT_TYPE;

/**
 * OrderServiceProxyTest.
 * 
 * 
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class OrderServiceTest {
	@Resource
	private OrderServiceProxy orderServiceImpl;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("所有测试方法执行前");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("所有测试方法执行后");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("测试方法执行前");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("测试方法执行后");
	}

	public void testCreateOrderBuyInfo() {
	}

	public void testCreateOrderBuyInfoString() {
	}

	public void testCreateOrderBuyInfoLongString() {
	}

	public void testUpdateRedail() {
	}

	public void testUpdateOrderIdItemFaxMemo() {
	}

	public void testUpdateWaitPayment() {
	}

 
	@Test
	public void testCompositeQueryOrderSum(){
		CompositeQuery compositeQuery = new CompositeQuery();
		OrderIdentity identity=new OrderIdentity();
		identity.setOperatorId("admin");
		compositeQuery.setOrderIdentity(identity);
		compositeQuery.setStatus(new OrderStatus());
		OrdOrderSum sum=orderServiceImpl.compositeQueryOrdOrderSum(compositeQuery);
		Assert.assertNotNull(sum);
		Assert.assertTrue(sum.getActualPay()>0L);
//		System.out.println(sum.);
	}

	private void testTours() {
		final Date now = new Date();
		CompositeQuery compositeQuery = new CompositeQuery();
		List<Tours> toursRelate = compositeQuery.getToursRelate();
		toursRelate.add(new Tours(1l, now));
		orderServiceImpl.compositeQueryOrdOrder(compositeQuery);
	}

	private void testPlayMoneyRelate() {
		final Date now = new Date();
		CompositeQuery compositeQuery = new CompositeQuery();
		PlayMoneyRelate playMoneyRelate = compositeQuery.getPlayMoneyRelate();
		playMoneyRelate.setCreateTimeEnd(now);
		playMoneyRelate.setCreateTimeStart(now);
		playMoneyRelate.setFincCashStatus(FINC_CASH_STATUS.ApplyPayCash);
		playMoneyRelate.setPlayMoneyType(PlayMoneyType.DRAW);
		playMoneyRelate.setUserId("1");
		orderServiceImpl.compositeQueryOrdOrder(compositeQuery);
		playMoneyRelate = new PlayMoneyRelate();
		playMoneyRelate.setUserId("1");
		compositeQuery.setPlayMoneyRelate(playMoneyRelate);
		orderServiceImpl.compositeQueryOrdOrder(compositeQuery);
	}

	private void testMoneyDrawRelate() {
		final Date now = new Date();
		CompositeQuery compositeQuery = new CompositeQuery();
		MoneyDrawRelate moneyDrawRelate = compositeQuery.getMoneyDrawRelate();
		moneyDrawRelate.setUserId("1");
		moneyDrawRelate.setCreateTimeEnd(now);
		moneyDrawRelate.setCreateTimeStart(now);
		orderServiceImpl.compositeQueryOrdOrder(compositeQuery);
		moneyDrawRelate = new MoneyDrawRelate();
		moneyDrawRelate.setCreateTimeStart(now);
		compositeQuery.setMoneyDrawRelate(moneyDrawRelate);
		orderServiceImpl.compositeQueryOrdOrder(compositeQuery);
	}

	private void testMoneyAccountChangeLogRelate() {
		CompositeQuery compositeQuery = new CompositeQuery();
		MoneyAccountChangeLogRelate moneyAccountChangeLogRelate = compositeQuery
				.getMoneyAccountChangeLogRelate();
		moneyAccountChangeLogRelate
				.setMoneyAccountChangeType(MoneyAccountChangeType.DRAW);
		moneyAccountChangeLogRelate.setUserId("1");
		orderServiceImpl.compositeQueryOrdOrder(compositeQuery);
	}

	private void testPassPortDetailRelate() {
		final Date now = new Date();
		CompositeQuery compositeQuery = new CompositeQuery();
		PassPortDetailRelate passPortDetailRelate = compositeQuery
				.getPassPortDetailRelate();
		passPortDetailRelate.setMetaProductid(1L);
		passPortDetailRelate.setOrderStatus(ORDER_STATUS.NORMAL.name());
		passPortDetailRelate.setTargetId(1L);
		passPortDetailRelate.setPassPortUserId(1L);
		passPortDetailRelate.setVisitTimeEnd(now);
		passPortDetailRelate.setVisitTimeStart(now);
		orderServiceImpl.queryPassPortDetail(compositeQuery);
		passPortDetailRelate = new PassPortDetailRelate();
		passPortDetailRelate.setVisitTimeStart(now);
		compositeQuery.setPassPortDetailRelate(passPortDetailRelate);
		orderServiceImpl.queryPassPortDetail(compositeQuery);
	}

	private void testPassPortSummaryRelate() {
		final Date now = new Date();
		CompositeQuery compositeQuery = new CompositeQuery();
		PassPortSummaryRelate passPortSummaryRelate = compositeQuery
				.getPassPortSummaryRelate();
		passPortSummaryRelate.setMetaProductid(1L);
		passPortSummaryRelate.setOrderStatus(ORDER_STATUS.FINISHED.name());
		passPortSummaryRelate.setPassPortUserId(1L);
		passPortSummaryRelate.setVisitTimeEnd(now);
		passPortSummaryRelate.setVisitTimeStart(now);
		orderServiceImpl.queryPassPortSummary(compositeQuery);
		passPortSummaryRelate = new PassPortSummaryRelate();
		passPortSummaryRelate.setVisitTimeStart(now);
		compositeQuery.setPassPortSummaryRelate(passPortSummaryRelate);
		orderServiceImpl.queryPassPortSummary(compositeQuery);
	}

	private void testPerformDetailSortTypeEnum() {
		CompositeQuery compositeQuery = new CompositeQuery();
		List<PerformDetailSortTypeEnum> performTypeList = compositeQuery
				.getPerformTypeList();
		PerformDetailRelate performDetailRelate = compositeQuery
				.getPerformDetailRelate();
		performDetailRelate.setContactMobile("1");
		performTypeList.add(PerformDetailSortTypeEnum.CONTACT_NAME_ASC);
		orderServiceImpl.queryPerformDetail(compositeQuery);
	}

	private void testPerformDetailRelate() {
		final Date now = new Date();
		CompositeQuery compositeQuery = new CompositeQuery();
		PerformDetailRelate performDetailRelate = compositeQuery
				.getPerformDetailRelate();
		performDetailRelate.setContactMobile("1");
		performDetailRelate.setContactName("1");
		performDetailRelate.setMetaProductName("1");
		performDetailRelate.setOrderId(1L);
		performDetailRelate.setOrderStatus(ORDER_STATUS.FINISHED.name());
		performDetailRelate.setPassPortUserId(1L);
		performDetailRelate.setPerformStatus(ORDER_PERFORM_STATUS.UNPERFORMED);
		performDetailRelate.setSupplierName("1");
		performDetailRelate.setTargetId(1L);
		performDetailRelate.setVisitTimeEnd(now);
		performDetailRelate.setVisitTimeStart(now);
		orderServiceImpl.queryPerformDetail(compositeQuery);
		performDetailRelate = new PerformDetailRelate();
		performDetailRelate.setVisitTimeEnd(now);
		compositeQuery.setPerformDetailRelate(performDetailRelate);
		orderServiceImpl.queryPerformDetail(compositeQuery);
	}

	private void testMetaPerformRelate() {
		CompositeQuery compositeQuery = new CompositeQuery();
		MetaPerformRelate metaPerformRelate = compositeQuery
				.getMetaPerformRelate();
		metaPerformRelate.setOrderId(1L);
		metaPerformRelate.setSupplierId(1L);
		metaPerformRelate.setTargetId("1");
		orderServiceImpl
				.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
		metaPerformRelate = new MetaPerformRelate();
		metaPerformRelate.setOrderId(1L);
		compositeQuery.setMetaPerformRelate(metaPerformRelate);
		orderServiceImpl
				.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
	}

	private void testFinishSettlementItemRelate() {
		final Date now = new Date();
		CompositeQuery compositeQuery = new CompositeQuery();
		FinishSettlementItemRelate finishSettlementItemRelate = compositeQuery
				.getFinishSettlementItemRelate();
		finishSettlementItemRelate.setOrderId(1L);
		finishSettlementItemRelate.setSettlementId(1L);
		finishSettlementItemRelate.setSubSettlementId(1L);
		finishSettlementItemRelate.setVisitTimeEnd(now);
		finishSettlementItemRelate.setVisitTimeStart(now);
		finishSettlementItemRelate = new FinishSettlementItemRelate();
		finishSettlementItemRelate.setVisitTimeStart(now);
		compositeQuery
				.setFinishSettlementItemRelate(finishSettlementItemRelate);
	}

	private void testOrdSettlementRelate() {
		final Date now = new Date();
		CompositeQuery compositeQuery = new CompositeQuery();
		OrdSettlementRelate ordSettlementRelate = compositeQuery
				.getOrdSettlementRelate();
		ordSettlementRelate.setCreateTimeEnd(now);
		ordSettlementRelate.setCreateTimeStart(now);
		ordSettlementRelate.setMetaProductId(1L);
		ordSettlementRelate.setSettlementId(1L);
		ordSettlementRelate.setTargetId(1L);
		ordSettlementRelate = new OrdSettlementRelate();
		ordSettlementRelate.setTargetId(1L);
		compositeQuery.setOrdSettlementRelate(ordSettlementRelate);
	}

	private void testSettlementItemRelate() {
		final Date now = new Date();
		CompositeQuery compositeQuery = new CompositeQuery();
		SettlementItemRelate settlementItemRelate = compositeQuery
				.getSettlementItemRelate();
		settlementItemRelate.setIncludeRefundmentOrder(false);
		settlementItemRelate.setMetaProductId(1L);
		settlementItemRelate.setOrderId(1L);
		settlementItemRelate.setTargetId(1L);
		settlementItemRelate.setVisitTimeEnd(now);
		settlementItemRelate.setVisitTimeStart(now);
		settlementItemRelate = new SettlementItemRelate();
		settlementItemRelate.setVisitTimeStart(now);
		settlementItemRelate.setOrderId(1L);
		settlementItemRelate.setMetaProductId(1L);
		compositeQuery.setSettlementItemRelate(settlementItemRelate);
	}

	private void testSettlementQueueRelate() {
		CompositeQuery compositeQuery = new CompositeQuery();
		SettlementQueueRelate settlementQueueRelate = compositeQuery
				.getSettlementQueueRelate();
		// settlementQueueRelate.setMetaProductId(1L);
		// settlementQueueRelate.setPay2Lvmama("1");
		// settlementQueueRelate.setPay2Supplier("1");
		// settlementQueueRelate.setSettlementPeriod("1");
		// settlementQueueRelate.setTargetId(1L);
		// orderServiceImpl.queryOrdSettlementQueue(compositeQuery);
		// settlementQueueRelate = new SettlementQueueRelate();
		// settlementQueueRelate.setTargetId(1L);
		// settlementQueueRelate.setSettlementPeriod("1");
		settlementQueueRelate.setMetaProductId(1L);
		settlementQueueRelate.setPay2Lvmama("1");
		settlementQueueRelate.setPay2Supplier("1");
		settlementQueueRelate.setSettlementPeriod("1");
		settlementQueueRelate.setTargetId(1L);
		settlementQueueRelate = new SettlementQueueRelate();
		settlementQueueRelate.setTargetId(1L);
		settlementQueueRelate.setSettlementPeriod("1");
		settlementQueueRelate.setVisitTimeStart(new Date());
		settlementQueueRelate.setVisitTimeEnd(new Date());
		compositeQuery.setSettlementQueueRelate(settlementQueueRelate);
	}

	private void testSaleServiceRelate() {
		final Date now = new Date();
		CompositeQuery compositeQuery = new CompositeQuery();
		SaleServiceRelate saleServiceRelate = compositeQuery
				.getSaleServiceRelate();
		saleServiceRelate.setSaleServiceApplyName("1");
		saleServiceRelate.setSaleServiceCreateTimeEnd(now);
		saleServiceRelate.setSaleServiceCreateTimeStart(now);
		saleServiceRelate.setSaleServiceMobile("1");
		saleServiceRelate.setSaleServiceOrderId(1L);
		saleServiceRelate.setSaleServiceOrderType("1");
		saleServiceRelate.setSaleServiceType("1");
		saleServiceRelate.setSaleServiceUserName("1");
		saleServiceRelate.setSaleStatus("1");
		orderServiceImpl.compositeQueryOrdOrder(compositeQuery);
		saleServiceRelate = new SaleServiceRelate();
		saleServiceRelate.setSaleStatus("1");
		saleServiceRelate.setSaleServiceApplyName("1");
		saleServiceRelate.setSaleServiceOrderId(1L);
		compositeQuery.setSaleServiceRelate(saleServiceRelate);
		orderServiceImpl.compositeQueryOrdOrder(compositeQuery);
	}
 
	private void testInvoiceRelate() {
		CompositeQuery compositeQuery = new CompositeQuery();
		InvoiceRelate invoiceRelate = compositeQuery.getInvoiceRelate();
		invoiceRelate.setCompanyId("");
		invoiceRelate.setInvoiceNo("");
		invoiceRelate.setOrderId(1L);
		invoiceRelate.setUserId("1");
		orderServiceImpl.compositeQueryOrdOrder(compositeQuery);
		invoiceRelate = new InvoiceRelate();
		invoiceRelate.setUserId("1");
		compositeQuery.setInvoiceRelate(invoiceRelate);
		orderServiceImpl.compositeQueryOrdOrder(compositeQuery);
	}

	private void testExcludedContent() {
		CompositeQuery compositeQuery = new CompositeQuery();
		ExcludedContent excludedContent = compositeQuery.getExcludedContent();
		excludedContent.setInSettlementQueue(false);
		excludedContent.setOrderApproveStatus(ORDER_APPROVE_STATUS.INFOPASS
				.name());
		excludedContent.setOrderStatus(ORDER_STATUS.UNCONFIRM.name());
		excludedContent.setPaymentTarget(PAYMENT_TARGET.TOSUPPLIER);
		orderServiceImpl.compositeQueryOrdOrder(compositeQuery);
		excludedContent = new ExcludedContent();
		excludedContent.setPaymentTarget(PAYMENT_TARGET.TOSUPPLIER);
		compositeQuery.setExcludedContent(excludedContent);
		orderServiceImpl.compositeQueryOrdOrder(compositeQuery);
	}

	private void testSortTypeEnum() {
		CompositeQuery compositeQuery = new CompositeQuery();
		List<SortTypeEnum> typeList = compositeQuery.getTypeList();
		typeList.add(SortTypeEnum.CREATE_TIME_ASC);
		compositeQuery.getPageIndex().setEndIndex(2);
		orderServiceImpl.compositeQueryOrdOrder(compositeQuery);
	}

	private void testOrderIdentity() {
		CompositeQuery compositeQuery = new CompositeQuery();
		OrderIdentity orderIdentity = compositeQuery.getOrderIdentity();
		orderIdentity.setItemOperatorId("1");
		orderIdentity.setMetaProductid(1L);
		orderIdentity.setOperatorId("1");
		orderIdentity.setOrderId(1L);
		orderIdentity.setProductid(1L);
		orderIdentity.setSupplierId(1L);
		orderIdentity.setUserId("1");
		orderServiceImpl.compositeQueryOrdOrder(compositeQuery);
		orderIdentity = new OrderIdentity();
		orderIdentity.setOrderId(477732L);
		compositeQuery.setOrderIdentity(orderIdentity);
		orderServiceImpl.compositeQueryOrdOrder(compositeQuery);
	}

	private void testOrderTimeRange() {
		CompositeQuery compositeQuery = new CompositeQuery();
		OrderTimeRange orderTimeRange = compositeQuery.getOrderTimeRange();
		final Date now = new Date();
		orderTimeRange.setCreateTimeEnd(now);
		orderTimeRange.setCreateTimeStart(now);
		orderTimeRange.setDealTimeEnd(now);
		orderTimeRange.setDealTimeStart(now);
		orderTimeRange.setOrdOrderItemProdVisitTimeEnd(now);
		orderTimeRange.setOrdOrderItemProdVisitTimeStart(now);
		orderTimeRange.setOrdOrderVisitTimeEnd(now);
		orderTimeRange.setOrdOrderVisitTimeStart(now);
		orderTimeRange.setPayTimeEnd(now);
		orderTimeRange.setPayTimeStart(now);
		orderServiceImpl.compositeQueryOrdOrder(compositeQuery);
		orderTimeRange = new OrderTimeRange();
		orderTimeRange.setPayTimeEnd(now);
		compositeQuery.setOrderTimeRange(orderTimeRange);
		orderServiceImpl.compositeQueryOrdOrder(compositeQuery);
	}

	private void testOrderContent() {
		CompositeQuery compositeQuery = new CompositeQuery();
		OrderContent content = compositeQuery.getContent();
		content.setChannel(CHANNEL.BACKEND.name());
		content.setContactMobile("1");
		content.setContactName("1");
		content.setEmail("1");
		content.setIsCashRefund("1");
		content.setIsCashRefund("1");
		content.setMembershipCard("1");
		content.setMetaProductName("1");
		content.setMetaProductType("1");
		content.setMobile("1");
		content.setOrderType("1");
		content.setPaymentTarget(PAYMENT_TARGET.TOLVMAMA.name());
		content.setPlaceName("1");
		content.setProductName("1");
		content.setProductType("1");
		content.setRedail("1");
		content.setSubProductType(SUB_PRODUCT_TYPE.FREENESS.name());
		content.setSupplierName("1");
		content.setTakenOperator("1");
		content.setUserId("1");
		content.setUserName("1");
		content.setVisitMobile("1");
		content.setVisitName("1");
		orderServiceImpl.compositeQueryOrdOrder(compositeQuery);
		content = new OrderContent();
		content.setChannel(CHANNEL.BACKEND.name());
		compositeQuery.setContent(content);
		orderServiceImpl.compositeQueryOrdOrder(compositeQuery);
	}
 
	@Test
	public void testQueryOrdOrderByOrderId() {
		final OrdOrder order = orderServiceImpl.queryOrdOrderByOrderId(328139L);
		System.out.println(order.isCanToPay());
		Assert.assertTrue(order.getOrdOrderItemProds().size() > 0);
		Assert.assertTrue(order.getAllOrdOrderItemMetas().size() > 0);
		Assert.assertTrue(order.getOrdOrderItemProds().get(0)
				.getOrdOrderItemMetas().size() > 0);
		Assert.assertNotNull(order.getOrdOrderItemProds().get(0)
				.getOrdOrderItemMetas().get(0).getSupplier());
		Assert.assertTrue(order.getPersonList().size() > 0);
		Assert.assertNotNull(order.getContact());
		Assert.assertNotNull(order.getUser());
	}

	@Test
	public void testQueryOrdOrderBySerialNo() {
		orderServiceImpl.queryOrdOrderBySerialNo("201008130016");
	}

	@Test
	public void testUpdateFaxMemo() {
		Assert.assertTrue(orderServiceImpl.updateFaxMemo(305L, "单元测试传真备注", "1"));
	}

	@Test
	public void testCompositeQueryOrdOrderCount() {
		Assert.assertTrue(orderServiceImpl
				.compositeQueryOrdOrderCount(new CompositeQuery()) > 0);
	}

 
	@Test
	public void testCashOrder() {
		orderServiceImpl.cashOrder(320276L, 100L);
	}

	@Test
	public void testQueryOrdOrderByOrdOrderItemMetaId() {
		final OrdOrder order = orderServiceImpl
				.queryOrdOrderByOrdOrderItemMetaId(543L);
		Assert.assertNotNull(order);
		Assert.assertTrue(order.getOrdOrderItemProds().size() > 0);
		Assert.assertTrue(order.getAllOrdOrderItemMetas().size() > 0);
		Assert.assertTrue(order.getOrdOrderItemProds().get(0)
				.getOrdOrderItemMetas().size() > 0);
		Assert.assertNotNull(order.getOrdOrderItemProds().get(0)
				.getOrdOrderItemMetas().get(0).getSupplier());
		Assert.assertTrue(order.getPersonList().size() > 0);
		Assert.assertNotNull(order.getContact());
		Assert.assertNotNull(order.getUser());
	}
 

 
	@Test
	public void testQueryOrdSaleService() {
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getSaleServiceRelate().setSaleServiceOrderType("TICKET");
		orderServiceImpl.queryOrdSaleService(compositeQuery);
	}

	@Test
	public void testQueryOrdSaleServiceCount() {
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getSaleServiceRelate().setSaleServiceOrderType("TICKET");
		orderServiceImpl.queryOrdSaleServiceCount(compositeQuery);
	}

 

	@Test
	public void testQueryOrdInvoice() {
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getPageIndex().setEndIndex(10);
		List<OrdInvoice> list = orderServiceImpl
				.queryOrdInvoice(compositeQuery);
		Assert.assertTrue(list.size() > 0);
		for (OrdInvoice item : list) {
			if (item.getOrderId().equals(327004L)) {
				Assert.assertNotNull(item.getAddress());
			}
		}
	}

	@Test
	public void testQueryOrdInvoiceCount() {
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getInvoiceRelate().setCompanyId("COMPANY_2");
		orderServiceImpl.queryOrdInvoiceCount(compositeQuery);
	}
 
 

	@Test
	public void testQueryOrdOrderItemProdById() {
		Assert.assertNotNull(orderServiceImpl.queryOrdOrderItemProdById(308L));
	}

	@Test
	public void testQueryOrdOrderByOrderNo() {
		final OrdOrder order = orderServiceImpl
				.queryOrdOrderByOrderNo("201008130016");
		Assert.assertNotNull(order);
		Assert.assertTrue(order.getOrdOrderItemProds().size() > 0);
		Assert.assertTrue(order.getAllOrdOrderItemMetas().size() > 0);
		Assert.assertTrue(order.getOrdOrderItemProds().get(0)
				.getOrdOrderItemMetas().size() > 0);
		Assert.assertNotNull(order.getOrdOrderItemProds().get(0)
				.getOrdOrderItemMetas().get(0).getSupplier());
		Assert.assertTrue(order.getPersonList().size() > 0);
		Assert.assertNotNull(order.getContact());
		Assert.assertNotNull(order.getUser());
	}

	@Test
	public void testCompositeQueryOrdOrderItemMeta() {
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getExcludedContent().setInSettlementQueue(true);
		compositeQuery.getOrderIdentity().setMetaProductid(636L);
		Assert.assertTrue(orderServiceImpl.compositeQueryOrdOrderItemMeta(
				compositeQuery).size() > 0);
		Assert.assertTrue(orderServiceImpl
				.compositeQueryOrdOrderItemMeta(compositeQuery).get(0)
				.getOrdOrderItemProds().size() > 0);
		Assert.assertTrue(orderServiceImpl
				.compositeQueryOrdOrderItemMeta(compositeQuery).get(0)
				.getOrdOrderItemProds().get(0).getOrdOrderItemMetas().size() > 0);
	}

	@Test
	public void testCompositeQueryOrdOrderItemMetaCount() {
		Assert.assertTrue(orderServiceImpl
				.compositeQueryOrdOrderItemMetaCount(new CompositeQuery()) > 0);
	}
 
 
	@Test
	public final void testCompositeQueryOrdOrderItemMetaByMetaPerformRelate() {
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMetaPerformRelate().setSupplierId(1L);
		Assert.assertTrue(orderServiceImpl
				.compositeQueryOrdOrderItemMetaByMetaPerformRelate(
						compositeQuery).size() > 0);
	}

	@Test
	public final void testQueryPerformDetail() {
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getPerformDetailRelate().setPassPortUserId(185L);
		// compositeQuery.getPerformTypeList().add(
		// PerformDetailSortTypeEnum.CONTACT_NAME_ASC);
		// compositeQuery.getPerformTypeList().add(
		// PerformDetailSortTypeEnum.CONTACT_NAME_DESC);
		// compositeQuery.getPerformTypeList().add(
		// PerformDetailSortTypeEnum.ORDER_ID_ASC);
		// compositeQuery.getPerformTypeList().add(
		// PerformDetailSortTypeEnum.ORDER_ID_DESC);
		// compositeQuery.getPerformTypeList().add(
		// PerformDetailSortTypeEnum.META_PRODUCT_NAME_ASC);
		// compositeQuery.getPerformTypeList().add(
		// PerformDetailSortTypeEnum.META_PRODUCT_NAME_DESC);
		// compositeQuery.getPerformTypeList().add(
		// PerformDetailSortTypeEnum.STATUS);
		// compositeQuery.getPerformTypeList().add(
		// PerformDetailSortTypeEnum.USED_TIME_DESC);
		Assert.assertNotNull(orderServiceImpl
				.queryPerformDetail(compositeQuery));
	}

	@Test
	public final void testQueryPerformDetailCount() {
		CompositeQuery compositeQuery = new CompositeQuery();
		// compositeQuery.getPerformDetailRelate().setOrderStatus("CANCEL");
		compositeQuery.getPerformDetailRelate().setPassPortUserId(92L);
		// compositeQuery.getPerformTypeList().add(
		// PerformDetailSortTypeEnum.CONTACT_NAME_ASC);
		// compositeQuery.getPerformTypeList().add(
		// PerformDetailSortTypeEnum.CONTACT_NAME_DESC);
		// compositeQuery.getPerformTypeList().add(
		// PerformDetailSortTypeEnum.ORDER_ID_ASC);
		// compositeQuery.getPerformTypeList().add(
		// PerformDetailSortTypeEnum.ORDER_ID_DESC);
		// compositeQuery.getPerformTypeList().add(
		// PerformDetailSortTypeEnum.META_PRODUCT_NAME_ASC);
		compositeQuery.getPerformTypeList().add(
				PerformDetailSortTypeEnum.STATUS);
		compositeQuery.getPerformTypeList().add(
				PerformDetailSortTypeEnum.USED_TIME_ASC);
		Assert.assertNotNull(orderServiceImpl
				.queryPerformDetailCount(compositeQuery));
	}

	@Test
	public final void testQueryPassPortDetail() {
		CompositeQuery compositeQuery = new CompositeQuery();
		// compositeQuery.getPassPortDetailRelate().setOrderStatus("CANCEL");
		compositeQuery.getPassPortDetailRelate().setPassPortUserId(183L);
		compositeQuery.getPerformTypeList().add(
				PerformDetailSortTypeEnum.STATUS);
		Assert.assertNotNull(orderServiceImpl
				.queryPassPortDetail(compositeQuery));
	}

	@Test
	public final void testQueryPassPortDetailCount() {
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getPerformTypeList().add(
				PerformDetailSortTypeEnum.USED_TIME_ASC);
		compositeQuery.getPassPortDetailRelate().setPassPortUserId(92L);
		Assert.assertNotNull(orderServiceImpl
				.queryPassPortDetailCount(compositeQuery));
	}

	@Test
	public final void testQueryPassPortSummary() {
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getPassPortSummaryRelate().setOrderStatus("CANCEL");
		compositeQuery.getPassPortSummaryRelate().setPassPortUserId(92L);
		Assert.assertNotNull(orderServiceImpl
				.queryPassPortSummary(compositeQuery));
	}

	@Test
	public final void testQueryPassPortSummaryCount() {
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getPassPortSummaryRelate().setPassPortUserId(92L);
		Assert.assertNotNull(orderServiceImpl
				.queryPassPortSummaryCount(compositeQuery));
	}

	@Test
	public final void testRestoreOrder() {
		System.out.println(orderServiceImpl.restoreOrder(333826L, "")
				.getResponse());
	}

	//================
	@Rollback(true)
	public final void testCreateOrderWithOperatorId() {
			orderServiceImpl.createOrderWithOperatorId(createBuyInfo(),
					"unit test");
	}
	 
	private BuyInfo createBuyInfo() {
		BuyInfo buyInfo = new BuyInfo();
		List<Person> personList = new ArrayList<Person>();
		Person person = new Person();
		personList.add(person);
		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		item.setProductId(21786L);
		item.setQuantity(1);
		try {
			item.setVisitTime(new SimpleDateFormat("yyyy-MM-dd")
					.parse("2011-4-25"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		itemList.add(item);
		buyInfo.setUserId("123");
		buyInfo.setItemList(itemList);
		buyInfo.setPersonList(personList);
		return buyInfo;
	}
 
	private FinishSettlementItemRelate getFinishSettlementItemRelate() {
		FinishSettlementItemRelate finishSettlementItemRelate = new FinishSettlementItemRelate();
		finishSettlementItemRelate.setOrderId(1L);
		finishSettlementItemRelate.setSettlementId(1L);
		finishSettlementItemRelate.setVisitTimeEnd(new Date());
		finishSettlementItemRelate.setVisitTimeStart(new Date());
		finishSettlementItemRelate.setSubSettlementId(1L);
		return finishSettlementItemRelate;
	}

	private OrdSettlementRelate getOrdSettlementRelate() {
		OrdSettlementRelate ordSettlementRelate = new OrdSettlementRelate();
		ordSettlementRelate.setSettlementId(1L);
		ordSettlementRelate.setMetaProductId(1L);
		ordSettlementRelate.setCreateTimeStart(new Date());
		ordSettlementRelate.setCreateTimeEnd(new Date());
		return ordSettlementRelate;
	}

	private SettlementItemRelate getSettlementItemRelate() {
		SettlementItemRelate settlementItemRelate = new SettlementItemRelate();
		settlementItemRelate.setIncludeRefundmentOrder(true);
		settlementItemRelate.setOrderId(1L);
		settlementItemRelate.setVisitTimeEnd(new Date());
		settlementItemRelate.setVisitTimeStart(new Date());
		return settlementItemRelate;
	}

	private SettlementQueueRelate getSettlementQueueRelate() {
		SettlementQueueRelate settlementQueueRelate = new SettlementQueueRelate();
		settlementQueueRelate.setPay2Lvmama("true");
		settlementQueueRelate.setPay2Supplier("true");
		settlementQueueRelate.setSettlementPeriod("P");
		return settlementQueueRelate;
	}

	private SaleServiceRelate getSaleServiceRelate() {
		SaleServiceRelate saleServiceRelate = new SaleServiceRelate();
		saleServiceRelate.setSaleServiceApplyName("ApplyName");
		saleServiceRelate.setSaleServiceCreateTimeEnd(new Date());
		saleServiceRelate.setSaleServiceCreateTimeStart(new Date());
		saleServiceRelate.setSaleServiceMobile("137");
		saleServiceRelate.setSaleServiceOrderId(1L);
		saleServiceRelate.setSaleServiceOrderType("TICKET");
		saleServiceRelate.setSaleServiceType("2");
		saleServiceRelate.setSaleServiceUserName("1");
		return saleServiceRelate;
	}
 

	private InvoiceRelate getInvoiceRelate() {
		InvoiceRelate invoiceRelate = new InvoiceRelate();
		invoiceRelate.setInvoiceNo("xccv");
		invoiceRelate.setOrderId(321069L);
		invoiceRelate.setCompanyId("COMPANY_2");
		invoiceRelate.setUserId("402880ca1d0ff4bc011d0ff4f60f0360");
		return invoiceRelate;
	}

	private ExcludedContent getExcludedContent() {
		ExcludedContent excludedContent = new ExcludedContent();
		excludedContent.setOrderStatus(ORDER_STATUS.CANCEL.name());
		excludedContent.setOrderApproveStatus("VERIFIED");
		excludedContent.setInSettlementQueue(true);
		return excludedContent;
	}
 

	private OrderContent getOrderContent() {
		OrderContent content = new OrderContent();
		content.setProductType("ROUTE,OTHER");
		content.setUserName("zhcn2002");
		content.setVisitName("测试");
		content.setPaymentTarget("TOLVMAMA");
		content.setPlaceName("楚雄");
		content.setOrderType("TICKET,GROUP");
		content.setSubProductType("SINGLE");
		content.setEmail("tongtong1987@163.com");
		content.setMobile("13818961274");
		content.setMetaProductType("ROUTE");
		content.setIsCashRefund("false");
		content.setChannel("FRONTEND");
		return content;
	}

	private OrderTimeRange getOrderTimeRange() {
		OrderTimeRange orderTimeRange = new OrderTimeRange();
		orderTimeRange.setCreateTimeEnd(new Date());
		orderTimeRange.setCreateTimeStart(new Date());
		orderTimeRange.setOrdOrderItemProdVisitTimeEnd(new Date());
		orderTimeRange.setOrdOrderItemProdVisitTimeStart(new Date());
		orderTimeRange.setOrdOrderVisitTimeStart(new Date());
		orderTimeRange.setOrdOrderVisitTimeEnd(new Date());
		orderTimeRange.setPayTimeStart(new Date());
		orderTimeRange.setPayTimeEnd(new Date());
		return orderTimeRange;
	}

	private OrderIdentity getOrderIdentity() {
		OrderIdentity identity = new OrderIdentity();
		identity.setOrderId(321015L);
		identity.setOperatorId("100");
		identity.setSupplierId(1L);
		return identity;
	}

	private List<SortTypeEnum> getTypeList() {
		List<SortTypeEnum> typeList = new ArrayList<SortTypeEnum>();
		typeList.add(SortTypeEnum.CREATE_TIME_ASC);
		typeList.add(SortTypeEnum.CREATE_TIME_DESC);
		typeList.add(SortTypeEnum.RECEIVE_ORDER_TIME_ASC);
		typeList.add(SortTypeEnum.RECEIVE_ORDER_TIME_DESC);
		typeList.add(SortTypeEnum.ORDER_ID_ASC);
		typeList.add(SortTypeEnum.ORDER_ID_DESC);
		typeList.add(SortTypeEnum.SALE_SERVICE_CREATE_TIME_ASC);
		typeList.add(SortTypeEnum.SALE_SERVICE_CREATE_TIME_DESC);
		return typeList;
	}

	private PageIndex getPageIndex() {
		PageIndex pageIndex = new PageIndex();
		pageIndex.setBeginIndex(1);
		pageIndex.setEndIndex(1);
		return pageIndex;
	}
}
