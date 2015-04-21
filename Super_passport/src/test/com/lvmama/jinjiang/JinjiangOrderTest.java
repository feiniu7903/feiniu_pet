package com.lvmama.jinjiang;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.SupplierProductInfo.Item;
import com.lvmama.jinjiang.service.JinjiangOrderService;
import com.lvmama.service.handle.JinjiangCheckHandle;

import freemarker.template.TemplateException;

@ContextConfiguration({ "classpath:applicationContext-passport-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class JinjiangOrderTest {

	@Autowired
	private OrderService orderServiceProxy;
	
	@Autowired
	private JinjiangOrderService jinjiangOrderService;
	
	@Autowired
	private JinjiangCheckHandle jinjiangCheckHandle;
	
	@Test
	public void createOrder() throws TemplateException{
		//Long orderId =1323825l; //1323498l
		Long orderId =1325869l; 
		String message = jinjiangOrderService.submitOrder(orderId);
		System.out.println("message=  " + message);
	}
		
	@Test
	public void cancelOrder() throws TemplateException{
//		Long orderId =1323825l;
		Long orderId =1323498l; 
		String str = jinjiangOrderService.cancelOrder(orderId);
		System.out.println("取消订单结果= " + str);
	}
	
	@Test
	public void payOrder() throws TemplateException{
		Long orderId =1325337L;
//		Long orderId =1323498l; 
		String str = jinjiangOrderService.payOrder(orderId);
		System.out.println("支付订单结果= " + str);
	}
	
	
	@Test
	public void checkOrder() throws TemplateException{
		BuyInfo buyinfo = mockBuyInfo();
		List<Item> list = mockItem();
		jinjiangCheckHandle.check(buyinfo, list);
	}

	private List<Item> mockItem() {
		List<Item> list = new ArrayList<Item>();
		Item item = new Item(736644l,DateUtil.getDateByStr("2013-9-16", "yyyy-MM-dd"));
		item.setQuantity(1l);
		item.setSettlementPrice(800100l);
		list.add(item);
		return list;
	}

	private BuyInfo mockBuyInfo() {
		BuyInfo info = new BuyInfo();
		List<Person> pers= new ArrayList<Person>();
		for(int i=0;i<2;i++){
			Person per = new Person();
			per.setName("name"+i);
			per.setMobile("1300000000"+i);
			per.setBrithday(DateUtil.getDateByStr("1983-10-01", "yyyy-MM-dd"));
			per.setPersonType(Constant.ORD_PERSON_TYPE.TRAVELLER.name());
			if(i==0){
				per.setPersonType(Constant.ORD_PERSON_TYPE.CONTACT.name());
			}
			pers.add(per);
		}
		info.setPersonList(pers);
		return info;
	}
}
