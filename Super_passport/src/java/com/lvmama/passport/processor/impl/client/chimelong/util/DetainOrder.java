package com.lvmama.passport.processor.impl.client.chimelong.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.lvmama.comm.bee.po.pass.PassCode;

/**
 * 封装长隆改期请求信息字段
 * 
 * @author luoyinqi
 *
 */

public class DetainOrder {

	public Order getOrder(PassCode passCode){
		Order order = new Order();
		String key = ChimelongUtils.getMd5Key();
		String ver_no = ChimelongConfig.getVersion();
		String mer_no = ChimelongConfig.getUserId();
		String orderInfo = mer_no+"|"+passCode.getSerialNo()+"|"+passCode.getCode()+"|"+DateFormatUtils.format(passCode.getValidTime(), "yyyy-MM-dd HH:mm:ss");
		//String sign = StringUtils.getSign(ver_no+mer_no+orderInfo+key);
		
		orderInfo = ChimelongUtils.enCode(key,orderInfo);
		String sign = ChimelongUtils.getSign(ver_no+mer_no+orderInfo+key);
		
		order.setMer_no(mer_no);
		order.setVer_no(ver_no);
		order.setOrderInfo(orderInfo);
		order.setSign(sign);
		
		return order;
	}
}
