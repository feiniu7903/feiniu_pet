package com.lvmama.distribution.model.ckdevice;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author gaoxin
 *
 */
@XmlRootElement
public class Body {
	
	
	private List<OrderInfo> orderList;
	
    @XmlElementWrapper(name="orderList")
    @XmlElement(name="orderInfo")
	public List<OrderInfo> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderInfo> orderList) {
		this.orderList = orderList;
	}

}
