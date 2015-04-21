package com.lvmama.pet.recon.utils;

import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.digester.Digester;

import com.lvmama.comm.utils.XmlToBeanUtil;
import com.lvmama.pet.vo.AlipayAccountLogVO;
import com.lvmama.pet.vo.AlipayResponseBean;



/**
 * 下载Alipay的财务明细数据
 * 
 * @author ranlongfei 2012-6-21
 * @version
 */
public class AlipayParsePayData{

	/**
	 * 将对账明细封装为对账对象
	 * 
	 * @author: ranlongfei 2012-7-2 下午05:39:06
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static AlipayResponseBean parseResponseBean(String data){
		Reader in = new StringReader(data); 
		Object parseResult = null;
		try {
			parseResult = XmlToBeanUtil.parseXml(buildXmlParserNew(), in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (AlipayResponseBean)parseResult;
	}
	
	/**
	 * 支付宝数据解析器
	 * 
	 * @author: ranlongfei 2012-7-2 下午04:54:13
	 * @return
	 */
	private static Digester buildXmlParserNew() {
		Digester digester = new Digester();
		digester.setValidating(false);
		digester.addObjectCreate("alipay", AlipayResponseBean.class);
		
		digester.addCallMethod("alipay/is_success", "setIsSuccess", 0);
		digester.addCallMethod("alipay/error", "setError", 0);
		digester.addCallMethod("alipay/response/account_page_query_result/has_next_page", "setHasNextPage", 0);
		digester.addObjectCreate("*/AccountQueryAccountLogVO", AlipayAccountLogVO.class);
			digester.addBeanPropertySetter("*/AccountQueryAccountLogVO/income", "income");
			digester.addBeanPropertySetter("*/AccountQueryAccountLogVO/outcome", "outcome");
			digester.addCallMethod("*/AccountQueryAccountLogVO/trans_code_msg", "setTransCodeMsg", 0);
			digester.addCallMethod("*/AccountQueryAccountLogVO/trans_date", "setTransDate", 0);
			digester.addCallMethod("*/AccountQueryAccountLogVO/merchant_out_order_no", "setMerchantOutOrderNo", 0);
			digester.addCallMethod("*/AccountQueryAccountLogVO/trade_no", "setTransOutOrderNo", 0);
			digester.addCallMethod("*/AccountQueryAccountLogVO/memo", "setMemo", 0);
		digester.addSetNext("*/AccountQueryAccountLogVO", "addAccountLogList");
		return digester;
	}
}
