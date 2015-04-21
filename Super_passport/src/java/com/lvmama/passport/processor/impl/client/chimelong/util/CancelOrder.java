package com.lvmama.passport.processor.impl.client.chimelong.util;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.pass.PassCode;
/**
 * 封装长隆取消请求信息字段
 * 
 * @author luoyinqi
 *
 */

public class CancelOrder {
	private static Log log = LogFactory.getLog(CancelOrder.class);
	public Order getOrder(PassCode passCode){
		Order order = new Order();
		String key = ChimelongUtils.getMd5Key();
		String ver_no = ChimelongConfig.getVersion();
		String mer_no = ChimelongConfig.getUserId();
		String code=passCode.getCode();
		if(passCode.getExtId()!=null&&!"".equalsIgnoreCase(passCode.getExtId())){
			ver_no="12";
			code=passCode.getExtId();
		}
		String orderInfo = mer_no+"|"+passCode.getSerialNo()+"|"+code+"|"+DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		//String sign = StringUtils.getSign(ver_no+mer_no+orderInfo+key);
		log.info("Chimelong DestoyCode Request data:"+orderInfo);
		orderInfo = ChimelongUtils.enCode(key,orderInfo);
		String sign = ChimelongUtils.getSign(ver_no+mer_no+orderInfo+key);
		
		order.setMer_no(mer_no);
		order.setVer_no(ver_no);
		order.setOrderInfo(orderInfo);
		order.setSign(sign);
		
		return order;
	}
}
