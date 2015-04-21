package com.lvmama.shholiday.response;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.shholiday.vo.order.OrderBaseInfo;

public class CheckOrderResponse extends AbstractResponse {

	private OrderBaseInfo baseInfo;
	
	public CheckOrderResponse() {
		super("TourBookCheckRS");
	}

	@Override
	protected void parseBody(Element body) {
		try {
			Element obaseInfo = body.element("OrderBaseInfo");
			this.baseInfo = initBaseInof(obaseInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private OrderBaseInfo initBaseInof(Element baseInfo2) {
		OrderBaseInfo orderBbaseInfo = new OrderBaseInfo();
		if(baseInfo2!=null){
			String totalAmountStr = baseInfo2.elementText("OrderTotalAmount");
			String orderFavorAmount = baseInfo2.elementText("OrderFavorAmount");
			if (StringUtils.isNotBlank(totalAmountStr)) {
				orderBbaseInfo.setOrderTotalAmount(PriceUtil.convertToFen(totalAmountStr));
			}
			if (StringUtils.isNotBlank(orderFavorAmount)) {
				orderBbaseInfo.setOrderFavorAmount(PriceUtil.convertToFen(orderFavorAmount));
			}
			orderBbaseInfo.setPriceArithmetic(baseInfo2.elementText("PriceArithmetic"));
			orderBbaseInfo.setOrderState(baseInfo2.elementText("PriceFavorArithmetic"));
		}
		return orderBbaseInfo;
	}

	public OrderBaseInfo getBaseInfo() {
		return baseInfo;
	}

}
