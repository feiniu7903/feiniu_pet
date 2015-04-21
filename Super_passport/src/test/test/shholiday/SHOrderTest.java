package test.shholiday;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.service.favor.FavorOrderService;
import com.lvmama.comm.bee.service.meta.MetaTravelCodeService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.service.businessCoupon.BusinessCouponService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.SupplierProductInfo.Item;
import com.lvmama.service.handle.SHHolidayCheckHandle;
import com.lvmama.shholiday.ShholidayClient;
import com.lvmama.shholiday.service.SHHolidayOrderService;
import com.lvmama.shholiday.vo.order.OrderDetailInfo;

import freemarker.template.TemplateException;

@ContextConfiguration({ "classpath:applicationContext-passport-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SHOrderTest {

	@Autowired
	private OrderService orderServiceProxy;
	
	@Autowired
	private FavorOrderService favorOrderService;
	
	@Autowired
	private BusinessCouponService businessCouponService;
	
	@Autowired
	private MetaTravelCodeService metaTravelCodeService;
	
	@Autowired
	ShholidayClient shholidayClient;
	
	@Autowired
	SHHolidayOrderService sHHolidayOrderService;
	
	@Autowired
	SHHolidayCheckHandle sHHolidayCheckHandle;
	
	@Autowired
	private TopicMessageProducer productMessageProducer;
	
	@Test
	public void createOrder() throws TemplateException{
		Long orderId =1323825l; //1323055l
		String message = sHHolidayOrderService.submitOrder(orderId);
		System.out.println("message=  " + message);
	}
		
	@Test
	public void cancelOrder() throws TemplateException{
		Long orderId =1323566l;
		
		String str = sHHolidayOrderService.cancelOrder(orderId);
		System.out.println("取消订单结果= " + str);
	}
	
	@Test
	public void updateOrder() throws TemplateException{
		Long orderId =1323050l;
		
		String str = sHHolidayOrderService.updateOrder(orderId);
		System.out.println("取消订单结果= " + str);
	}
	
	@Test
	public void payOrder() throws TemplateException{
		Long orderId =1323667l;
		
		String str = sHHolidayOrderService.payOrder(orderId);
		System.out.println("取消订单结果= " + str);
	}
	
	@Test
	public void modifyOrder() throws TemplateException{
		Long orderId =1309170l;
		
		String str = sHHolidayOrderService.cancelOrder(orderId);
		System.out.println("取消订单结果= " + str);
	}
	
	@Test
	public void findOrder() throws TemplateException{
		Long orderId =1309170l;
		
		OrderDetailInfo findOrder = sHHolidayOrderService.findOrder(orderId);
		if(findOrder!=null){
			System.out.println(findOrder.getExternalOrderNo() + " orderStatus= " +findOrder.getOrderStatus());
		}
	}
	@Test
	public void checkOrder() throws TemplateException{
		BuyInfo buyinfo = mockBuyInfo();
		List<Item> list = mockItem();
		sHHolidayCheckHandle.check(buyinfo, list);
	}

	@Test
	public void sendMsg() throws TemplateException{
		productMessageProducer.sendMsg(MessageFactory.newProductUpdateMessage(2266L));
	}
	
	private List<Item> mockItem() {
		List<Item> list = new ArrayList<Item>();
		Item item = new Item(69506l,DateUtil.getDateByStr("2013-9-16", "yyyy-MM-dd"));
		item.setQuantity(1l);
		item.setSettlementPrice(459600l);
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
