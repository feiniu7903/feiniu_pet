package com.lvmama.passport.processor.impl.util;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;

public class OrderUtil {
	private PayPaymentService payPaymentService;
	static OrderUtil orderUtil ;
	public static OrderUtil init(){
		if(orderUtil==null){
			orderUtil = new OrderUtil();
			orderUtil.setPayPaymentService((PayPaymentService) SpringBeanProxy.getBean("payPaymentService"));
		}
		return orderUtil;
	}
	//独立申码时获取订单采购产品信息
	public OrdOrderItemMeta getItemMeta(OrdOrder order, PassCode passCode) {
		OrdOrderItemMeta ordItem = null;
		for (OrdOrderItemMeta item : order.getAllOrdOrderItemMetas()) {
			if (item.getOrderItemMetaId().longValue()==(passCode.getObjectId().longValue())) {
				ordItem = item;
				break;
			}
		}
		return ordItem;
	}


	public OrdPerson getContract(OrdOrder order) {
		//添加百付电话号码的逻辑
		String mobile=getPayMobile(Long.valueOf(order.getOrderId()));
		//凭证短信优先发给游玩人，游玩人不存在，才发送给联系人
		OrdPerson contact = null;
		if (!order.getTravellerList().isEmpty() && StringUtils.isNotBlank(order.getTravellerList().get(0).getMobile())) {
			contact = order.getTravellerList().get(0);
		} else {
			contact = order.getContact();
		}
		if(!StringUtils.equals(mobile,"") && mobile!=null){
			contact.setMobile(mobile);
		}
		return contact;
	}
	
	
	//三次自动申码
	public void reapplySet(Passport passport, long times) {
		int reapplyMinute = 10;
		if (times < 3) {
			passport.setReapplyTime(DateUtils.addMinutes(new Date(),reapplyMinute));
			passport.setStatus(PassportConstant.PASSCODE_STATUS.TEMP_FAILED.name());
		} else {
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
		}
	}
	
	//获取百付电话
	private String getPayMobile(Long orderId){
		String paymentGateway = Constant.PAYMENT_GATEWAY.TELBYPAY.name();
		return payPaymentService.selectPayMobileNumByPaymentOrderNoAndPaymentGateway(orderId,paymentGateway);
	}
	
	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}
	
	public static void main(String[] args) {
		String mobile="1";
		if(!StringUtils.equals(mobile,"") && mobile!=null){
					System.out.println("true");
		}else{
			System.out.println("false");
		}
	}
}
