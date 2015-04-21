package com.lvmama.back.remote.impl.template;

import java.util.Map;

public class BuyOutStockWarningEmailTemplate {
	
	static final String TEMPLATE_NORMAL = "邮件提醒：${productName}，产品编号：${productId}实际销售量过低<br/>" +
									"采购产品名称：${productName}<br/>" +
									"采购产品编号：${productId}<br/>" +
									"到${warningTime}为止，但实际销量比应销量要低${levelValue}。系统自动发此邮件提醒。";
	
	static final String TEMPLATE_NOTGOT = "邮件提醒：${productName}，产品编号：${productId}实际销售量过低<br/>" +
									"采购产品名称：${productName}<br/>" +
									"采购产品编号：${productId}<br/>" +
									"到${warningTime}为止,但实际销量没有达到应销量。系统自动发此邮件提醒。";
	
	static final String TEMPLATE_FINISHSALE = "邮件提醒：${productName}，产品编号：${productId}的买断库存已经售完<br/>" +
									"采购产品名称：${productName}<br/>" +
									"采购产品编号：${productId}<br/>" +
									"昨天买断库存已经售完。系统自动发此邮件提醒。";

	public static String getEmailContent(Map<String, String> param) {
		return getResult(param, TEMPLATE_NORMAL);
	}

	public static String getNotGotEmailContent(Map<String, String> param) {
		return getResult(param, TEMPLATE_NOTGOT);
	}
	
	public static String getFinishSaleEmailContent(Map<String, String> param) {
		return getResult(param, TEMPLATE_FINISHSALE);
	}
	
	private static String getResult(Map<String, String> param, String template) {
		String result = template;
		for (Map.Entry<String, String> entry : param.entrySet()) {
			result = result.replace(entry.getKey(), entry.getValue());
		}
		return result;
	}
}
