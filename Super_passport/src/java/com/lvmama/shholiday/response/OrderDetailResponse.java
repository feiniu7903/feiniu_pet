package com.lvmama.shholiday.response;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.shholiday.vo.order.Contact;
import com.lvmama.shholiday.vo.order.OrderDetailInfo;
import com.lvmama.shholiday.vo.order.OrderPassenger;

public class OrderDetailResponse extends AbstractResponse {

	private OrderDetailInfo orderDetailInfo;
	
	@Override
	protected void parseBody(Element body) {
		if(body== null) return ;
		
		Element orderDetail = body.element("OrderBaseInfo");
		orderDetailInfo = new OrderDetailInfo();
		orderDetailInfo.setUniqueID(body.attributeValue("UniqueID"));
		if(orderDetail!=null){
			orderDetailInfo.setBookDate(orderDetail.elementText("BookDate"));
			orderDetailInfo.setExternalOrderNo(orderDetail.elementText("ExternalOrderNo"));
			orderDetailInfo.setHadOutTicket(orderDetail.elementText("HadOutTicket"));
			orderDetailInfo.setIsExpress(orderDetail.elementText("IsExpress"));
			orderDetailInfo.setOrderModifyAfterAmount(orderDetail.elementText("OrderModifyAfterAmount"));
			orderDetailInfo.setOrderStatus(orderDetail.elementText("OrderStatus"));
			orderDetailInfo.setPriceArithmetic("PriceArithmetic");
		}
		Element orderContactInfo = body.element("OrderContactInfo");
		Element orderPassengers = body.element("OrderPassengers");
		try {
			buildContactInfo(orderContactInfo);
			buildPassengers(orderPassengers);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void buildPassengers(Element orderPassengers) {
		if(orderPassengers==null) return ;
		@SuppressWarnings("unchecked")
		List<Element> orderPassengerList = orderPassengers.elements();
		List<OrderPassenger> passengers = new ArrayList<OrderPassenger>();
		for(Element orderPassenger: orderPassengerList){
			OrderPassenger pa = new OrderPassenger();
			pa.setUniqueId(orderPassenger.attributeValue("UniqueID"));
			if(orderPassenger.elementText("Birth")!=null){
				pa.setBrithday(DateUtil.getDateByStr(orderPassenger.elementText("Birth"), "yyyyMMdd"));
			}
			pa.setCertNo(orderPassenger.elementText("IndentityCardNumber"));
			pa.setName(orderPassenger.elementText("PassengerName"));
			passengers.add(pa);
		}
		orderDetailInfo.setPassengers(passengers);
	}
	private void buildContactInfo(Element orderContactInfo) {
		if(orderContactInfo==null) return ;
		
		Contact contact = new Contact();
		contact.setUniqueId(orderContactInfo.attributeValue("UniqueID"));
		orderDetailInfo.setContact(contact);
	}
	public OrderDetailResponse(){
		super("OrderDetailRS");
	}

	public OrderDetailInfo getOrderDetailInfo() {
		return orderDetailInfo;
	}
	
	

}
