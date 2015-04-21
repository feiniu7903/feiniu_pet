package com.lvmama.shholiday.notify;

import org.apache.commons.lang3.math.NumberUtils;
import org.dom4j.Element;

import com.lvmama.comm.bee.po.ord.OrdOrderSHHoliday;
import com.lvmama.comm.bee.service.ord.OrdOrderSHHolidayService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;


public abstract class AbstractShholidayOrderNotify extends AbstractShholidayNotify {

	
	protected OrderService orderServiceProxy;
	private OrdOrderSHHolidayService ordOrderSHHolidayService;
	
	public AbstractShholidayOrderNotify(String bodyElementTag, String rs) {
		super(bodyElementTag, rs);
		orderServiceProxy = SpringBeanProxy.getBean(OrderService.class, "orderServiceProxy");
		ordOrderSHHolidayService = SpringBeanProxy.getBean(OrdOrderSHHolidayService.class,"ordOrderSHHolidayService");
	}

	@Override
	protected void handle(Element body) {
		orderPackageNo = body.attributeValue("OrderPackageNo");
		externalOrderNo = body.attributeValue("ExternalOrderNo");
		handleOther(body);
	}
	
	protected abstract void handleOther(Element body);

	protected String orderPackageNo;
	protected String externalOrderNo;
	
	public String getOrderPackageNo() {
		return orderPackageNo;
	}

	public String getExternalOrderNo() {
		return externalOrderNo;
	}
	
	public OrdOrderSHHolidayService getOrdOrderSHHolidayService(){
		return ordOrderSHHolidayService;
	}
	
	public OrdOrderSHHoliday getEntity(){
		Long orderID = NumberUtils.toLong(getExternalOrderNo());
		if(orderID==0){
			orderID=null;
		}
		OrdOrderSHHoliday sh = new OrdOrderSHHoliday(orderID,
				Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER.getCode(),
				getOrderPackageNo()); 
		sh= ordOrderSHHolidayService.selectByObjectIdAndObjectType(sh);
		return sh;
	}
	
	public void saveSHHolidayInfo(OrdOrderSHHoliday sh){ 
		ordOrderSHHolidayService.insert(sh);
	}
}
