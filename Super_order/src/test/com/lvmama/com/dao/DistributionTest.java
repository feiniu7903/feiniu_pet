package com.lvmama.com.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.bee.po.distribution.DistributionTuanCoupon;
import com.lvmama.comm.bee.service.distribution.DistributionTuanCouponService;
import com.lvmama.comm.bee.service.ord.DistributionOrderService;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.distribution.dao.DistributionTuanCouponDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class DistributionTest {

	@Autowired
	DistributionTuanCouponDAO distributionTuanCouponDAO;
	
	@Autowired
	DistributionTuanCouponService distributionTuanCouponService;
	
	@Autowired
	DistributionOrderService distributionOrderServiceProxy;
	private final String channel=Constant.CHANNEL.DIST_YUYUE.name();
	
	@Test
	public void test() {
		DistributionTuanCoupon t = distributionTuanCouponDAO.queryByCode("15433019");
		DistributionTuanCoupon c = distributionTuanCouponService.queryByCode("15433019");
		Assert.assertNotNull(t);
		Assert.assertNotNull(c);
	}
	
	@Test
	public void testCreateOrder() {
		BuyInfo buyInfo = createBuyInfo();
		List<String> list = new ArrayList<String>();
		list.add("07313075");
		distributionOrderServiceProxy.createOrderByCouponCode(buyInfo, list);
	}
	
	private BuyInfo createBuyInfo(){
		BuyInfo buyInfo = new BuyInfo();
		buyInfo.setChannel(channel);
		buyInfo.setIsAperiodic("false");
		BuyInfo.Item item = new BuyInfo.Item();
		item.setProductId(72709L);
		item.setProductBranchId(781220L);
		item.setIsDefault("true");
		item.setQuantity(1);
		item.setVisitTime(DateUtil.dsDay_Date(DateUtil.getTodayDate(), 5));
		List<BuyInfo.Item> list = new ArrayList<BuyInfo.Item>();
		list.add(item);
		buyInfo.setItemList(list);
//		buyInfo.setMainProductType(prodBranch.getProdProduct().getProductType());
//		buyInfo.setMainSubProductType(prodBranch.getProdProduct().getSubProductType());
		buyInfo.setPaymentTarget(Constant.PAYMENT_TARGET.TOLVMAMA.name());
		buyInfo.setTodayOrder(false);
		buyInfo.setSelfPack("false");
		List<Person> personList = new ArrayList<Person>();
		Person person = new Person();
		person.setName("admin");
		person.setMobile("13800000000");
		person.setPersonType(Constant.ORD_PERSON_TYPE.CONTACT.name());
		personList.add(person);
		buyInfo.setPersonList(personList);
		buyInfo.setUserId("5ad32f1a1ecff4ba011ecffad7490072");
		return buyInfo;
	}
}
