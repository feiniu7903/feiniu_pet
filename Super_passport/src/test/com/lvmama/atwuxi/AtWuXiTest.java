package com.lvmama.atwuxi;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.passport.processor.impl.client.wuxiloch.model.Order;
import com.lvmama.passport.utils.Md5;
import com.lvmama.passport.utils.WebServiceConstant;

public class AtWuXiTest {
	private static final Log log = LogFactory.getLog(AtWuXiTest.class);
	public static void main(String[] args) throws DocumentException {
		try {
			//new AtWuXiTest().createOrder();
			//new AtWuXiTest().cancelOrder();
			new AtWuXiTest().performOrder(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void createOrder() throws Exception {
		String otachecknum=WebServiceConstant.getProperties("wuxiloch.ota_check_num");
		String key=WebServiceConstant.getProperties("wuxiloch.key");
		Map<String, String> params = new HashMap<String, String>();
		Order ord=new Order();
		Date useDate = DateUtil.toDate("2014-05-31", "yyyy-MM-dd");
		ord.setExp_time(useDate.getTime()); 
		System.out.println(useDate.getTime());
//		ord.setNum(String.valueOf(1));
//		ord.setCost(String.valueOf(1));
		ord.setNum(1);
		ord.setCost(1d);
		ord.setGoods_id(2278);//产品编号
		ord.setSpec_id(4372);//成人票儿童票类型
		ord.setPrice(1d);
		String ordStr = JsonUtil.getJsonString4JavaPOJO(ord);
		params.put("rstType", "2");//请求为xml格式 
		params.put("xmlType", "2");//响应为xml格式
		params.put("order", "["+ordStr+"]");
		System.out.println(ordStr);
		//params.put("order", "[{\"goods_id\":2286,\"spec_id\":4371,\"price\":95,\"cost\":95,\"num\":1,\"exp_time\":1449926956288}]");
		params.put("phone", "15221026734");
		params.put("paymentid", "4");
		String action="createOrder";
		String orderSn="150251";
		System.out.println(key+action+orderSn);
		String keyStr=MD5.encode32(key+action+orderSn);
		System.out.println("keyStr:"+keyStr);
		params.put("key", keyStr);
		params.put("ota_check_num", otachecknum);
		params.put("OTAcode", "lmm");
		//params.put("orderSn", "1005021");
		params.put("orderSn", orderSn);
		params.put("action", action);
		

		//String targetUrl= WebServiceConstant.getProperties("wuxiloch.url")+ "?rstType=2&xmlType=2&order=[" 
				//+ ordStr+"]&phone=15221026734&paymentid=4&key="+keyStr+"&ota_check_num="+otachecknum+"&OTAcode=lmm&orderSn=20140528174501&action=createOrder";
		//log.info(targetUrl);
		String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("wuxiloch.url"), params);
		//String result=HttpsUtil.requestGet(targetUrl.toString());
		log.info(TemplateUtils.getElementValue(result, "//div/atwuxi_terminal_get_response/result_code"));
		log.info(result);
	}
	public static void cancelOrder() throws Exception {
		String otachecknum=WebServiceConstant.getProperties("wuxiloch.ota_check_num");
		String key=WebServiceConstant.getProperties("wuxiloch.key");
		Map<String, String> params = new HashMap<String, String>();
		params.put("rstType", "2");//请求为xml格式
		params.put("xmlType", "2");//响应为xml格式
		params.put("phone", "15221026734");
		String action="cancelOrder";
		String orderSn="154551";
		params.put("ota_check_num", otachecknum);
		params.put("OTAcode", "lmm");
		//params.put("orderSn", "20140528174501");
		params.put("orderSn", "154551");
		//params.put("orderSn", "150251");
		params.put("action", "cancelOrder");
		String keyStr=MD5.encode32(key+action+orderSn);
		params.put("key", keyStr);
		//String targetUrl= WebServiceConstant.getProperties("wuxiloch.url")+ 
				//"?rstType=2&phone=15221026734&paymentid=4&key="+key+"&ota_check_num="+otachecknum+"&OTAcode=lvmama&orderSn=20140528174501&action=createOrder";
		//log.info(targetUrl);
		String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("wuxiloch.url"), params);
		System.out.println(result);
//		String result=HttpsUtil.requestGet(targetUrl.toString());
		log.info(TemplateUtils.getElementValue(result, "//div/atwuxi_terminal_get_response/result_code"));
		log.info(result);
	}
	
	public static void performOrder() throws Exception {
		String otachecknum=WebServiceConstant.getProperties("wuxiloch.ota_check_num");
		String key=WebServiceConstant.getProperties("wuxiloch.key");
		Map<String, String> params = new HashMap<String, String>();
		String action="queryOrder";
		String orderSn="20140530277127";
		params.put("rstType", "2");//请求为xml格式
		params.put("xmlType", "2");//响应为xml格式
		params.put("phone", "15026847838");
		params.put("orderSn", "20140530277127");
		params.put("OTAcode", "lmm");
		params.put("ota_check_num", otachecknum);
		params.put("action", "queryOrder");
		String keyStr=MD5.encode32(key+action+orderSn);
		params.put("key", keyStr);
		String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("wuxiloch.url"), params);
//		String result=HttpsUtil.requestGet(targetUrl.toString());
		log.info(TemplateUtils.getElementValue(result, "//div/atwuxi_terminal_get_response/result_code"));
		log.info(result);
	}
	
	
}
