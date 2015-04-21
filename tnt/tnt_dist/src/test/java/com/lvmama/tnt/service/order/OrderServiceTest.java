package com.lvmama.tnt.service.order;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.order.po.TntOrder;
import com.lvmama.tnt.order.service.BuildTntBuyInfoService;
import com.lvmama.tnt.order.service.TntOrderService;
import com.lvmama.tnt.order.vo.TntBuyInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class OrderServiceTest {

	@Autowired
	public TntOrderService tntOrderService;
	
	@Autowired
	private OrderService orderServiceProxy;


	@Autowired
	private BuildTntBuyInfoService buildTntBuyInfoService;

	@Test
	public void order_build_test() {
		TntBuyInfo info = new TntBuyInfo();
		info.setProductId(73045l);
		info.setBranchId(793880l);
		buildTntBuyInfoService.build(info);
	}
	
	@Test
	public void order_query_test() {
		TntOrder t = new TntOrder();
		//t.setCreateTimeBegin("2014-01-12");
		//t.setCreateTimeEnd("2014-01-15");
		Page<TntOrder> p = Page.page(1, t);
		t.setContactName("胡培培");
		t.setProductName("重固福泉");
		t.setOrderStatus(Constant.ORDER_STATUS.NORMAL.getCode());
		t.setPaymentStatus(Constant.PAYMENT_STATUS.UNPAY.getCode());
		t.setPaymentTarget(Constant.PAYMENT_TARGET.TOLVMAMA.getCode());
		t.setRefundStatus(TntConstant.REFUND_STATUS.WAITING.name());
		p = p.desc("TNT_ORDER_ID");
		int count = tntOrderService.count(t);
		List<TntOrder> list = tntOrderService.findPage(p);
		System.out.println("count = " + count);
		System.out.println("list = " + list.size());
	}
	

	@Test
	public void testUpdate(){
		synOrder(1342841L);
	}
	
	
	private void synOrder(Long orderId) {
		TntOrder tntOrder = tntOrderService.getByOrderId(orderId);
		if(tntOrder==null){
			return ;
		}
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if(ordOrder==null){
			return ;
		}
		
		tntOrder.setApproveStatus(ordOrder.getApproveStatus());
		if(ordOrder.getContact()!=null){
			tntOrder.setContactMoblie(ordOrder.getContact().getMobile());
			tntOrder.setContactName(ordOrder.getContact().getName());
		}
		tntOrder.setOrderStatus(ordOrder.getOrderStatus());
		tntOrder.setPaymentStatus(ordOrder.getPaymentStatus());
		tntOrder.setPaymentTime(ordOrder.getPaymentTime());
		tntOrder.setPerformStatus(ordOrder.getPerformStatus());
		tntOrder.setResourceLackReason(ordOrder.getResourceLackReason());
		tntOrder.setVisitTime(ordOrder.getVisitTime());
		String quantity="";
		if(ordOrder.getOrdOrderItemProds()!=null){
			for(OrdOrderItemProd ip:ordOrder.getOrdOrderItemProds()){
				if("HOTEL".equalsIgnoreCase(ip.getProductType())){
					quantity = quantity + ip.getHotelQuantity() +",";
				}else{
					quantity = quantity + ip.getQuantity() +",";
				}
			}
			if(quantity.length()>1){
				quantity = quantity.substring(0, quantity.length()-1);
			}
		}
		tntOrder.setQuantity(quantity);
		tntOrderService.update(tntOrder);
	}
}
