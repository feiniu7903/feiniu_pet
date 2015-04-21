package com.lvmama.comm.utils;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.vo.Constant;

public class PaymentUtil {
	/**
	 * 通过网关获取对应中文含义
	 * 
	 * @return
	 */
	public static String getGatewayNameByGateway(String gateway) {
		if(StringUtils.isBlank(gateway)){
			return gateway;
		}
		String gatewayName = Constant.PAYMENT_GATEWAY.getCnName(gateway);
		if (gateway.equalsIgnoreCase(gatewayName)) {
			gatewayName = Constant.PAYMENT_GATEWAY_OTHER_MANUAL.getCnName(gateway);
		}
		if (gateway.equalsIgnoreCase(gatewayName)) {
			gatewayName = Constant.PAYMENT_GATEWAY_DIST_MANUAL.getCnName(gateway);
		}
		return gatewayName;
	}
}
