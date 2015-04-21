/**
 * 
 */
package com.lvmama.shholiday.response;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.shholiday.vo.order.Contact;
import com.lvmama.shholiday.vo.order.OrderPassenger;

/**
 * @author yangbin
 *
 */
public class CreateOrderResponse extends AbstractResponse{

	private String supplierOrderId;//供应商订单ID
	private Contact contact;
	private List<OrderPassenger> passengers;
	
	
	public CreateOrderResponse() {
		super("TourBookRS");
	}

	@Override
	protected void parseBody(Element body) {
		
		supplierOrderId = body.attributeValue("UniqueID");
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
		passengers = new ArrayList<OrderPassenger>();
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
	}
	private void buildContactInfo(Element orderContactInfo) {
		if(orderContactInfo==null) return ;
		contact = new Contact();
		contact.setUniqueId(orderContactInfo.attributeValue("UniqueID"));
	}
	
	public String getSupplierOrderId() {
		return supplierOrderId;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public List<OrderPassenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<OrderPassenger> passengers) {
		this.passengers = passengers;
	}
	
	
}
