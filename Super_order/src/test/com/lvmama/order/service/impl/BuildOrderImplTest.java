package com.lvmama.order.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.order.service.IBuildOrder;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
//@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
//@Transactional
public class BuildOrderImplTest {

	@Resource
	private IBuildOrder buildOrderService;
//	@Resource
//	private OrderCreateService test;
	
 
	private List<BuyInfo> createBuyInfo() {
		List<BuyInfo> list=new ArrayList<BuyInfo>();
//		BuyInfo buyInfo1=MarkBuyInfo.markBuyInfo(MarkItem.markTicket());//门票   
		BuyInfo buyInfo2=MarkBuyInfo.markBuyInfo(MarkItem.markTicketHotel());//门票+房差  
//		BuyInfo buyInfo3=MarkBuyInfo.markBuyInfo(MarkItem.markTicketHotelOther());//门票+房差+保险
//		
//		BuyInfo buyInfo4=MarkBuyInfo.markBuyInfo(MarkItem.marksingleHotel()); //单房型 多天
//		BuyInfo buyInfo5=MarkBuyInfo.markBuyInfo(MarkItem.marksingleHotelHotel());//单房型+房差
//		BuyInfo buyInfo6=MarkBuyInfo.markBuyInfo(MarkItem.marksingleHotelHotelOther());//单房型+房差+保险
//		
//		BuyInfo buyInfo7=MarkBuyInfo.markBuyInfo(MarkItem.markHotel());//套房   
//		BuyInfo buyInfo8=MarkBuyInfo.markBuyInfo(MarkItem.markHotelHotel());//套房+房差
//		BuyInfo buyInfo9=MarkBuyInfo.markBuyInfo(MarkItem.markHotelHotelOther());//套房+房差+其他
//		
//		BuyInfo buyInfo10=MarkBuyInfo.markBuyInfo(MarkItem.markMoreTicket()); //多个门票
//		BuyInfo buyInfo11=MarkBuyInfo.markBuyInfo(MarkItem.markMoreTicketHotel());//多个门票+房差
//		BuyInfo buyInfo12=MarkBuyInfo.markBuyInfo(MarkItem.markMoreTicketHotelOther());//多个门票+房差+其他
//		
//		BuyInfo buyInfo13=MarkBuyInfo.markBuyInfo(MarkItem.markTicketSingleHotel()); //门票+单房型
//		BuyInfo buyInfo14=MarkBuyInfo.markBuyInfo(MarkItem.markTicketSingleHotelHotel());	//门票+单房型+房差
//		BuyInfo buyInfo15=MarkBuyInfo.markBuyInfo(MarkItem.markTicketSingleHotelHotelOther());//门票+单房型+房差+其他
//		
//		BuyInfo buyInfo16=MarkBuyInfo.markBuyInfo(MarkItem.markTicketMoreHotel()); //门票+套房
//		BuyInfo buyInfo17=MarkBuyInfo.markBuyInfo(MarkItem.markTicketHotelHotel());//门票+套房+房差
//		BuyInfo buyInfo18=MarkBuyInfo.markBuyInfo(MarkItem.markTicketHotelHotelOther());//门票+套房+房差+保险
//		
//		BuyInfo buyInfo19=MarkBuyInfo.markBuyInfo(MarkItem.markTicket());//优惠
//		buyInfo19.setCouponList(CouponList.markCouponList());
		
//		list.add(buyInfo1);
		list.add(buyInfo2);
//		list.add(buyInfo3);
//		list.add(buyInfo4);
//		list.add(buyInfo5);
//		list.add(buyInfo6);
//		list.add(buyInfo7);
//		list.add(buyInfo8);
//		list.add(buyInfo9);
//		list.add(buyInfo10);
//		list.add(buyInfo11);
//		list.add(buyInfo12);
//		list.add(buyInfo13);
//		list.add(buyInfo14);
//		list.add(buyInfo15);
//		list.add(buyInfo16);
//		list.add(buyInfo17);
//		list.add(buyInfo18);
//		list.add(buyInfo19);
		
		return list;
	}
	
	
	
	
	
}
