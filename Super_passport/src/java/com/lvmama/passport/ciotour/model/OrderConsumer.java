package com.lvmama.passport.ciotour.model;

import java.util.List;

public class OrderConsumer {
	private String consumerName; // 消费者（游客）姓名
	private int consumerIDCardType; // 消费者（游客）证件类型
	private String consumerIDCard;// 消费者（游客）证件号码
	private List<OrderConsumerItem> orderConsumerItemList;
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	public int getConsumerIDCardType() {
		return consumerIDCardType;
	}
	public void setConsumerIDCardType(int consumerIDCardType) {
		this.consumerIDCardType = consumerIDCardType;
	}
	public String getConsumerIDCard() {
		return consumerIDCard;
	}
	public void setConsumerIDCard(String consumerIDCard) {
		this.consumerIDCard = consumerIDCard;
	}
	public List<OrderConsumerItem> getOrderConsumerItemList() {
		return orderConsumerItemList;
	}
	public void setOrderConsumerItemList(
			List<OrderConsumerItem> orderConsumerItemList) {
		this.orderConsumerItemList = orderConsumerItemList;
	}

	
}
