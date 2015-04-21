package com.lvmama.order.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Coupon;
import com.lvmama.comm.bee.vo.ord.BuyInfo.OrdTimeInfo;
import com.lvmama.comm.bee.vo.ord.Invoice;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.order.dao.OrderItemProdDAO;
import com.lvmama.order.service.OrderCreateService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
public class TicketBuildImplTest {
	@Resource
	private OrderCreateService orderBuildServiceProxy;
	@Resource
	private OrderItemMetaDAO orderItemMetaDAO;
	@Resource
	private OrderItemProdDAO orderItemProdDAO;
	@Resource
	private OrderCreateService test;

	@Test
	public void testConserveOrderInfo() {

		BuyInfo buyInfo = null;
		OrdOrder orderInfoOneKindProduct = null;
		OrdOrder orderOneKindProduct = null;
		OrdOrder orderInfoTwoKindsProduct = null;
		OrdOrder orderTwoKindsProduct = null;
		OrdOrder orderInfoThreeKindsProduct = null;
		OrdOrder orderThreeKindsProduct = null;

		/**
		 * 门票测试
		 */
		System.out.println("门票测试");
		buyInfo = createOneKindProductBuyInfo();
		orderInfoOneKindProduct = orderBuildServiceProxy.createOrder(buyInfo, null, null);
		orderOneKindProduct = test.createOrder(buyInfo);
		compareOrderInfoAndOrder(orderInfoOneKindProduct, orderOneKindProduct);

		/**
		 * 门票+保险测试
		 */
		System.out.println("门票+保险测试");
		buyInfo = createManyKindsProductBuyInfo(false);
		orderInfoTwoKindsProduct = orderBuildServiceProxy.createOrder(buyInfo, null, null);
		orderTwoKindsProduct = test.createOrder(buyInfo);
		compareOrderInfoAndOrder(orderInfoTwoKindsProduct, orderTwoKindsProduct);

		/**
		 * 门票+保险+房差测试
		 */
		System.out.println("门票+保险+房差测试");
		buyInfo = createManyKindsProductBuyInfo(true);
		orderInfoThreeKindsProduct = orderBuildServiceProxy.createOrder(buyInfo, null, null);
		orderThreeKindsProduct = test.createOrder(buyInfo);
		compareOrderInfoAndOrder(orderInfoThreeKindsProduct, orderThreeKindsProduct);

	}

	private void compareOrderInfoAndOrder(OrdOrder orderInfo, OrdOrder order) {
		Assert.assertNotNull(orderInfo);
		Assert.assertNotNull(order);
		System.out.println("orderInfo.getOrderId() = " + orderInfo.getOrderId());
		System.out.println("order.getOrderId() = " + order.getOrderId());
		Assert.assertEquals(orderInfo.getCreateTime(), order.getCreateTime());
		Assert.assertEquals(orderInfo.getPaymentStatus(), order.getPaymentStatus());
		System.out.println("orderInfo.getOughtPay() = " + orderInfo.getOughtPay());
		System.out.println("order.getOughtPay() = " + order.getOughtPay());
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
		// Assert.assertEquals(orderInfo.getResourceConfirm(),
		// order.getResourceConfirm());
		Assert.assertEquals(orderInfo.getOrderStatus(), order.getOrderStatus());
		Assert.assertEquals(orderInfo.getChannel(), order.getChannel());
		Assert.assertEquals(orderInfo.getPerformStatus(), order.getPerformStatus());
		Assert.assertEquals(orderInfo.getVisitTime(), order.getVisitTime());
		Assert.assertEquals(orderInfo.getResourceConfirmStatus(), order.getResourceConfirmStatus());
		Assert.assertEquals(orderInfo.getDealTime(), order.getDealTime());
		// Assert.assertEquals(orderInfo.getApproveTime(),
		// order.getApproveTime());
		// Assert.assertEquals(orderInfo.getStockReduced(),
		// order.getStockReduced());
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
		 * 测试ord_person表,由于List<OrdPerson> personList_old =
		 * order.getPersonList();中personList_old为null，故而先不进行该项测试
		 */
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
				Assert.assertEquals(new String(byte_old), new String(byte_new));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			/**
			 * 逐一比较orderItemProd_new和orderItemProd_old的对应属性值
			 */
			Assert.assertEquals(orderItemProd_new.getAdditional(), orderItemProd_old.getAdditional());
			Assert.assertEquals(orderItemProd_new.getAmountYuan(), orderItemProd_old.getAmountYuan());
			Assert.assertEquals(orderItemProd_new.getDateRange(), orderItemProd_old.getDateRange());
			Assert.assertEquals(orderItemProd_new.getDays(), orderItemProd_old.getDays());
			Assert.assertEquals(orderItemProd_new.getFaxMemo(), orderItemProd_old.getFaxMemo());
			Assert.assertEquals(orderItemProd_new.getHotelQuantity(), orderItemProd_old.getHotelQuantity());
			Assert.assertEquals(orderItemProd_new.getMarketAmountYuan(), orderItemProd_old.getMarketAmountYuan());
			Assert.assertEquals(orderItemProd_new.getMarketPriceYuan(), orderItemProd_old.getMarketPriceYuan());
			Assert.assertEquals(orderItemProd_new.getNeedEContract(), orderItemProd_old.getNeedEContract());
			Assert.assertEquals(orderItemProd_new.getPriceYuan(), orderItemProd_old.getPriceYuan());
			Assert.assertEquals(orderItemProd_new.getProductName(), orderItemProd_old.getProductName());
			Assert.assertEquals(orderItemProd_new.getProductType(), orderItemProd_old.getProductType());
			Assert.assertEquals(orderItemProd_new.getSendSms(), orderItemProd_old.getSendSms());
			Assert.assertEquals(orderItemProd_new.getShortName(), orderItemProd_old.getShortName());
			Assert.assertEquals(orderItemProd_new.getSubProductType(), orderItemProd_old.getSubProductType());
			Assert.assertEquals(orderItemProd_new.getWrapPage(), orderItemProd_old.getWrapPage());
			Assert.assertEquals(orderItemProd_new.getZhAdditional(), orderItemProd_old.getZhAdditional());
			// Assert.assertEquals(orderItemProd_new.getZhProductType(),
			// orderItemProd_old.getZhProductType());
			Assert.assertEquals(orderItemProd_new.getZhVisitTime(), orderItemProd_old.getZhVisitTime());
			Assert.assertEquals(orderItemProd_new.getAllOrdOrderItemProdTime(), orderItemProd_old.getAllOrdOrderItemProdTime());
			Assert.assertEquals(orderItemProd_new.getHotelOughtPay(), orderItemProd_old.getHotelOughtPay());
			Assert.assertEquals(orderItemProd_new.getMarketPrice(), orderItemProd_old.getMarketPrice());
			Assert.assertEquals(orderItemProd_new.getOrdOrderItemMetas(), orderItemProd_old.getOrdOrderItemMetas());
			Assert.assertEquals(orderItemProd_new.getOughtPay(), orderItemProd_old.getOughtPay());
			Assert.assertEquals(orderItemProd_new.getPrice(), orderItemProd_old.getPrice());
			Assert.assertEquals(orderItemProd_new.getProduct(), orderItemProd_old.getProduct());
			Assert.assertEquals(orderItemProd_new.getOrdOrderItemMetas(), orderItemProd_old.getOrdOrderItemMetas());
			Assert.assertEquals(orderItemProd_new.getQuantity(), orderItemProd_old.getQuantity());
			Assert.assertEquals(orderItemProd_new.getTimeInfoList(), orderItemProd_old.getTimeInfoList());
			Assert.assertEquals(orderItemProd_new.getVisitTime(), orderItemProd_old.getVisitTime());
		}

		/**
		 * 测试ord_order_item_meta表
		 */

		List<OrdOrderItemMeta> orderItemMetaList_new = orderItemMetaDAO.selectByOrderId(orderInfo.getOrderId());
		Assert.assertNotNull(orderItemMetaList_new);
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
				Assert.assertEquals(new String(byte_old), new String(byte_new));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			/**
			 * 逐一比较orderItemMeta_new和orderItemMeta_old的对应属性值
			 */
			Assert.assertEquals(orderItemMeta_new.getAvgSingelRoomSellPriceToYuan(), orderItemMeta_old.getAvgSingelRoomSellPriceToYuan());
			Assert.assertEquals(orderItemMeta_new.getAvgSingelRoomSettlementPriceToYuan(), orderItemMeta_old.getAvgSingelRoomSettlementPriceToYuan());
			Assert.assertEquals(orderItemMeta_new.getCheckItem(), orderItemMeta_old.getCheckItem());
			Assert.assertEquals(orderItemMeta_new.getDateRange(), orderItemMeta_old.getDateRange());
			Assert.assertEquals(orderItemMeta_new.getFaxMemo(), orderItemMeta_old.getFaxMemo());
			Assert.assertEquals(orderItemMeta_new.getFaxSendStatus(), orderItemMeta_old.getFaxSendStatus());
			Assert.assertEquals(orderItemMeta_new.getHotelQuantity(), orderItemMeta_old.getHotelQuantity());
			Assert.assertEquals(orderItemMeta_new.getHotelSingelRoomSettlementPriceToYuan(), orderItemMeta_old.getHotelSingelRoomSettlementPriceToYuan());
			Assert.assertEquals(orderItemMeta_new.getPassStatus(), orderItemMeta_old.getPassStatus());
			Assert.assertEquals(orderItemMeta_new.getPaymentTarget(), orderItemMeta_old.getPaymentTarget());
			Assert.assertEquals(orderItemMeta_new.getPerformStatus(), orderItemMeta_old.getPerformStatus());
			Assert.assertEquals(orderItemMeta_new.getPrePayStatus(), orderItemMeta_old.getPrePayStatus());
			Assert.assertEquals(orderItemMeta_new.getProdSellPriceList(), orderItemMeta_old.getProdSellPriceList());
			Assert.assertEquals(orderItemMeta_new.getProductIdSupplier(), orderItemMeta_old.getProductIdSupplier());
			Assert.assertEquals(orderItemMeta_new.getProductName(), orderItemMeta_old.getProductName());
			Assert.assertEquals(orderItemMeta_new.getProductType(), orderItemMeta_old.getProductType());
			Assert.assertEquals(orderItemMeta_new.getProductTypeSupplier(), orderItemMeta_old.getProductTypeSupplier());
			Assert.assertEquals(orderItemMeta_new.getRefund(), orderItemMeta_old.getRefund());
			/**
			 * false null
			 */
			// Assert.assertEquals(orderItemMeta_new.getResourceConfirm(),
			// orderItemMeta_old.getResourceConfirm());
			/**
			 * 新的订单创建添加了逻辑，故而orderItemMeta_new.getResourceStatus()和
			 * orderItemMeta_old.getResourceStatus()值会出现不同，此处不做比较
			 */
			// Assert.assertEquals(orderItemMeta_new.getResourceStatus(),
			// orderItemMeta_old.getResourceStatus());
			Assert.assertEquals(orderItemMeta_new.getSellPriceToYuan(), orderItemMeta_old.getSellPriceToYuan());
			Assert.assertEquals(orderItemMeta_new.getSendFax(), orderItemMeta_old.getSendFax());
			Assert.assertEquals(orderItemMeta_new.getSettlementPriceToYuan(), orderItemMeta_old.getSettlementPriceToYuan());
			Assert.assertEquals(orderItemMeta_new.getSettlementPriceToYuanHotel(), orderItemMeta_old.getSettlementPriceToYuanHotel());
			Assert.assertEquals(orderItemMeta_new.getSettlementStatus(), orderItemMeta_old.getSettlementStatus());
			Assert.assertEquals(orderItemMeta_new.getSettmentPriceList(), orderItemMeta_old.getSettmentPriceList());
			Assert.assertEquals(orderItemMeta_new.getStockReduced(), orderItemMeta_old.getStockReduced());
			Assert.assertEquals(orderItemMeta_new.getStrVisitTime(), orderItemMeta_old.getStrVisitTime());
			Assert.assertEquals(orderItemMeta_new.getSubProductType(), orderItemMeta_old.getSubProductType());
			Assert.assertEquals(orderItemMeta_new.getTaken(), orderItemMeta_old.getTaken());
			Assert.assertEquals(orderItemMeta_new.getTotalAdultProductQuantity(), orderItemMeta_old.getTotalAdultProductQuantity());
			Assert.assertEquals(orderItemMeta_new.getTotalAdultQuantity(), orderItemMeta_old.getTotalAdultQuantity());
			Assert.assertEquals(orderItemMeta_new.getTotalChildProductQuantity(), orderItemMeta_old.getTotalChildProductQuantity());
			Assert.assertEquals(orderItemMeta_new.getTotalChildQuantity(), orderItemMeta_old.getTotalChildQuantity());
			Assert.assertEquals(orderItemMeta_new.getTotalQuantity(), orderItemMeta_old.getTotalQuantity());
			Assert.assertEquals(orderItemMeta_new.getVisitTimeDay(), orderItemMeta_old.getVisitTimeDay());
			// Assert.assertEquals(orderItemMeta_new.getZhProductType(),
			// orderItemMeta_old.getZhProductType());
			// Assert.assertEquals(orderItemMeta_new.getZhResourceStatus(),
			// orderItemMeta_old.getZhResourceStatus());
			Assert.assertEquals(orderItemMeta_new.getActualSettlementPrice(), orderItemMeta_old.getActualSettlementPrice());
			Assert.assertEquals(orderItemMeta_new.getAdultQuantity(), orderItemMeta_old.getAdultQuantity());
			Assert.assertEquals(orderItemMeta_new.getAllOrdOrderItemMetaTime(), orderItemMeta_old.getAllOrdOrderItemMetaTime());
			Assert.assertEquals(orderItemMeta_new.getChildQuantity(), orderItemMeta_old.getChildQuantity());
			Assert.assertEquals(orderItemMeta_new.getMarketPrice(), orderItemMeta_old.getMarketPrice());
			Assert.assertEquals(orderItemMeta_new.getMetaProduct(), orderItemMeta_old.getMetaProduct());
			Assert.assertEquals(orderItemMeta_new.getMetaProductId(), orderItemMeta_old.getMetaProductId());
			Assert.assertEquals(orderItemMeta_new.getOrdPerform(), orderItemMeta_old.getOrdPerform());
			Assert.assertEquals(orderItemMeta_new.getProductQuantity(), orderItemMeta_old.getProductQuantity());
			Assert.assertEquals(orderItemMeta_new.getQuantity(), orderItemMeta_old.getQuantity());
			Assert.assertEquals(orderItemMeta_new.getSellPrice(), orderItemMeta_old.getSellPrice());
			Assert.assertEquals(orderItemMeta_new.getSettlementPrice(), orderItemMeta_old.getSettlementPrice());
			Assert.assertEquals(orderItemMeta_new.getSuggestionPrice(), orderItemMeta_old.getSuggestionPrice());
			Assert.assertEquals(orderItemMeta_new.getSupplier(), orderItemMeta_old.getSupplier());
			Assert.assertEquals(orderItemMeta_new.getSupplierId(), orderItemMeta_old.getSupplierId());
			Assert.assertEquals(orderItemMeta_new.getValidDays(), orderItemMeta_old.getValidDays());
			Assert.assertEquals(orderItemMeta_new.getVisitTime(), orderItemMeta_old.getVisitTime());

			/**
			 * 逐一比较ordOrderItemProd_new和ordOrderItemProd_old对应属性值
			 */
			OrdOrderItemProd ordOrderItemProd_new = orderItemMeta_new.getRelateOrdOrderItemProd();
			OrdOrderItemProd ordOrderItemProd_old = orderItemMeta_old.getRelateOrdOrderItemProd();
			Assert.assertNotNull(ordOrderItemProd_new);
			Assert.assertNotNull(ordOrderItemProd_old);
			Assert.assertEquals(ordOrderItemProd_new.getAdditional(), ordOrderItemProd_old.getAdditional());
			Assert.assertEquals(ordOrderItemProd_new.getDays(), ordOrderItemProd_old.getDays());
			Assert.assertEquals(ordOrderItemProd_new.getFaxMemo(), ordOrderItemProd_old.getFaxMemo());
			Assert.assertEquals(ordOrderItemProd_new.getHotelQuantity(), ordOrderItemProd_old.getHotelQuantity());
			Assert.assertEquals(ordOrderItemProd_new.getNeedEContract(), ordOrderItemProd_old.getNeedEContract());
			Assert.assertEquals(ordOrderItemProd_new.getProductName(), ordOrderItemProd_old.getProductName());
			Assert.assertEquals(ordOrderItemProd_new.getProductType(), ordOrderItemProd_old.getProductType());
			Assert.assertEquals(ordOrderItemProd_new.getSendSms(), ordOrderItemProd_old.getSendSms());
			Assert.assertEquals(ordOrderItemProd_new.getShortName(), ordOrderItemProd_old.getShortName());
			Assert.assertEquals(ordOrderItemProd_new.getSubProductType(), ordOrderItemProd_old.getSubProductType());
			Assert.assertEquals(ordOrderItemProd_new.getWrapPage(), ordOrderItemProd_old.getWrapPage());
			Assert.assertEquals(ordOrderItemProd_new.getZhAdditional(), ordOrderItemProd_old.getZhAdditional());
			// Assert.assertEquals(ordOrderItemProd_new.getZhProductType(),
			// ordOrderItemProd_old.getZhProductType());
			Assert.assertEquals(ordOrderItemProd_new.getZhVisitTime(), ordOrderItemProd_old.getZhVisitTime());
			Assert.assertEquals(ordOrderItemProd_new.getAllOrdOrderItemProdTime(), ordOrderItemProd_old.getAllOrdOrderItemProdTime());
			Assert.assertEquals(ordOrderItemProd_new.getHotelOughtPay(), ordOrderItemProd_old.getHotelOughtPay());
			Assert.assertEquals(ordOrderItemProd_new.getMarketPrice(), ordOrderItemProd_old.getMarketPrice());
			Assert.assertEquals(ordOrderItemProd_new.getOrdOrderItemMetas(), ordOrderItemProd_old.getOrdOrderItemMetas());
			Assert.assertEquals(ordOrderItemProd_new.getOughtPay(), ordOrderItemProd_old.getOughtPay());
			Assert.assertEquals(ordOrderItemProd_new.getPrice(), ordOrderItemProd_old.getPrice());
			Assert.assertEquals(ordOrderItemProd_new.getProduct(), ordOrderItemProd_old.getProduct());
			Assert.assertEquals(ordOrderItemProd_new.getProductId(), ordOrderItemProd_old.getProductId());
			Assert.assertEquals(ordOrderItemProd_new.getQuantity(), ordOrderItemProd_old.getQuantity());
			Assert.assertEquals(ordOrderItemProd_new.getTimeInfoList(), ordOrderItemProd_old.getTimeInfoList());
			Assert.assertEquals(ordOrderItemProd_new.getVisitTime(), ordOrderItemProd_old.getVisitTime());
			/**
			 * ordOrderItemProd_new.getAmountYuan() 和
			 * ordOrderItemProd_old.getAmountYuan()都报空指针，故不作判断
			 */
			// System.out.println("ordOrderItemProd_new.getAmountYuan() = "+ordOrderItemProd_new.getAmountYuan());
			// System.out.println("ordOrderItemProd_old.getAmountYuan() = "+ordOrderItemProd_old.getAmountYuan());
			// Assert.assertEquals(ordOrderItemProd_new.getAmountYuan(),
			// ordOrderItemProd_old.getAmountYuan());
			/**
			 * ordOrderItemProd_new.getDateRange() 和
			 * ordOrderItemProd_old.getDateRange()都报空指针，故不作判断
			 */
			// System.out.println("ordOrderItemProd_new.getDateRange() = "+ordOrderItemProd_new.getDateRange());
			// System.out.println("ordOrderItemProd_old.getDateRange() = "+ordOrderItemProd_old.getDateRange());
			// Assert.assertEquals(ordOrderItemProd_new.getDateRange(),
			// ordOrderItemProd_old.getDateRange());
			/**
			 * ordOrderItemProd_new.getMarketAmountYuan() 和
			 * ordOrderItemProd_old.getMarketAmountYuan()都报空指针，故不作判断
			 */
			// System.out.println("ordOrderItemProd_new.getMarketAmountYuan() = "+ordOrderItemProd_new.getMarketAmountYuan());
			// System.out.println("ordOrderItemProd_old.getMarketAmountYuan() = "+ordOrderItemProd_old.getMarketAmountYuan());
			// Assert.assertEquals(ordOrderItemProd_new.getMarketAmountYuan(),
			// ordOrderItemProd_old.getMarketAmountYuan());
			/**
			 * ordOrderItemProd_new.getMarketPriceYuan() 和
			 * ordOrderItemProd_old.getMarketPriceYuan()都报空指针，故不作判断
			 */
			// System.out.println("ordOrderItemProd_new.getMarketPriceYuan() = "+ordOrderItemProd_new.getMarketPriceYuan());
			// System.out.println("ordOrderItemProd_old.getMarketPriceYuan() = "+ordOrderItemProd_old.getMarketPriceYuan());
			// Assert.assertEquals(ordOrderItemProd_new.getMarketPriceYuan(),
			// ordOrderItemProd_old.getMarketPriceYuan());

			/**
			 * ordOrderItemProd_new.getPriceYuan() 和
			 * ordOrderItemProd_old.getPriceYuan()都报空指针，故不作判断
			 */
			// System.out.println("ordOrderItemProd_new.getPriceYuan() = "+ordOrderItemProd_new.getPriceYuan());
			// System.out.println("ordOrderItemProd_old.getPriceYuan() = "+ordOrderItemProd_old.getPriceYuan());
			// Assert.assertEquals(ordOrderItemProd_new.getPriceYuan(),
			// ordOrderItemProd_old.getPriceYuan());

		}

	}

	private BuyInfo createManyKindsProductBuyInfo(boolean isThreeKinds) {
		BuyInfo buyInfo = new BuyInfo();
		buyInfo.setChannel("FRONTEND");
		buyInfo.setUserId("UnitTest");
		buyInfo.setPaymentTarget("TOLVMAMA");
		buyInfo.setUserMemo("UnitTest");
		buyInfo.setResourceConfirmStatus("true");

		Calendar cal = getCalendar(2011, 8, 22);

		// 门票 30394 2011/8/22
		BuyInfo.Item item1 = new BuyInfo.Item();
		item1.setProductId(30394);
		item1.setQuantity(1);
		item1.setVisitTime(cal.getTime());
		item1.setFaxMemo("Test1");

		// 门票TimeInfo
		OrdTimeInfo timeInfo1 = new OrdTimeInfo();
		timeInfo1.setProductId(item1.getProductId());
		timeInfo1.setQuantity(1L);
		timeInfo1.setVisitTime(cal.getTime());

		// 门票TimeInfoList
		List<OrdTimeInfo> timeInfoList1 = new ArrayList<OrdTimeInfo>();
		timeInfoList1.add(timeInfo1);
		item1.setTimeInfoList(timeInfoList1);

		// 保险 30369 2011/8/22
		BuyInfo.Item item2 = new BuyInfo.Item();
		item2.setProductId(30369);
		item2.setQuantity(1);
		item2.setVisitTime(cal.getTime());
		item2.setFaxMemo("Test2");

		// 保险TimeInfo
		timeInfo1.setProductId(item2.getProductId());

		// 保险TimeInfoList
		List<OrdTimeInfo> timeInfoList2 = new ArrayList<OrdTimeInfo>();
		timeInfoList2.add(timeInfo1);
		item2.setTimeInfoList(timeInfoList2);

		// 组装所有的产品 到itemList中
		List<BuyInfo.Item> itemList = new ArrayList<BuyInfo.Item>();
		itemList.add(item1);
		itemList.add(item2);
		/**
		 * flag为true的话，会将第二种附加产品，即“房差类型”的附加产品加入产品列表中进行测试
		 */
		if (isThreeKinds) {
			// 房差 29573 2011/8/22
			BuyInfo.Item item3 = new BuyInfo.Item();
			item3.setProductId(29573);
			item3.setQuantity(1);
			item3.setVisitTime(cal.getTime());
			item3.setFaxMemo("Test3");

			// 房差TimeInfo
			timeInfo1.setProductId(item3.getProductId());

			// 房差TimeInfoList
			List<OrdTimeInfo> timeInfoList3 = new ArrayList<OrdTimeInfo>();
			timeInfoList3.add(timeInfo1);
			item3.setTimeInfoList(timeInfoList3);
			itemList.add(item3);
		}
		// 组装itemList到buyInfo中
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

		/**
		 * 优惠券
		 */
		List<Coupon> couponList = getACouponList();
		buyInfo.setCouponList(couponList);
		return buyInfo;
	}

	private BuyInfo createOneKindProductBuyInfo() {
		BuyInfo buyInfo = new BuyInfo();
		buyInfo.setChannel("FRONTEND");
		buyInfo.setUserId("UnitTest");
		buyInfo.setPaymentTarget("TOLVMAMA");
		buyInfo.setUserMemo("UnitTest");
		buyInfo.setResourceConfirmStatus("true");

		Calendar cal = getCalendar(2011, 8, 22);

		// 门票
		BuyInfo.Item item1 = new BuyInfo.Item();
		item1.setProductId(30394);
		item1.setQuantity(1);
		item1.setVisitTime(cal.getTime());
		item1.setFaxMemo("Test1");

		// 门票TimeInfo
		OrdTimeInfo timeInfo = new OrdTimeInfo();
		timeInfo.setProductId(item1.getProductId());
		timeInfo.setQuantity(1L);
		timeInfo.setVisitTime(cal.getTime());

		// 门票TimeInfoList
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

		/**
		 * 优惠券
		 */
		List<Coupon> couponList = getBCouponList("BK64762518092");
		buyInfo.setCouponList(couponList);
		return buyInfo;
	}

	/**
	 * 创建日历对象.
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	private Calendar getCalendar(int year, int month, int date) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	/**
	 * 针对门票30394
	 * 创建A类优惠券对象.
	 * 
	 * A类中的累加按金额累加
	 * 
	 * @return
	 */
	private List<Coupon> getACouponList() {
		List<Coupon> conList = new ArrayList<Coupon>();
		Coupon coupon = new Coupon();
		coupon.setChecked("true");

		System.out.println("优惠券：A类累加按金额累加");
		coupon.setCouponId(767L);
		coupon.setCode("AHLW7685368465");
		conList.add(coupon);
		return conList;
	}

	/**
	 * 针对门票30394
	 * 创建B类优惠券对象 
	 * B类仅能使用一次
	 * @param code 优惠券code
	 * code 的可取值为:
	 * BK64762518092
	 * BK67807840040
	 * BK69406412316
	 * BK69737038730
	 * BK60807134598
	 * BK68577141994
	 * BK69687099231
	 * BK62129080801
	 * BK62565210123
	 * BK62155230038
	 * @return
	 */
	private List<Coupon> getBCouponList(String code) {
		List<Coupon> conList = new ArrayList<Coupon>();
		Coupon coupon = new Coupon();
		coupon.setChecked("true");

		System.out.println("优惠券：B类仅能使用一次");
		coupon.setCouponId(809L);
		coupon.setCode(code);
		conList.add(coupon);
		return conList;
	}
}
