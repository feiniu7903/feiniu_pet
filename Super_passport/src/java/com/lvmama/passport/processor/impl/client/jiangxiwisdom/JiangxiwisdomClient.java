package com.lvmama.passport.processor.impl.client.jiangxiwisdom;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.passport.processor.impl.client.jiangxiwisdom.model.Order;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.Md5;
import com.lvmama.passport.utils.WebServiceConstant;
public class JiangxiwisdomClient{
	private static  Log log = LogFactory.getLog(JiangxiwisdomClient.class);
	private static final int CONNECTION_TIMEOUT = 10000;
	private static final int SO_TIMEOUT = 180000;
	private static final String CHARACTER_ENCODING = "gb2312";
	
	public static String applyCodeRequest(Order order) throws Exception {
		Map<String, String> param = new LinkedHashMap<String, String>();
		String unixTimestamp =String.valueOf(System.currentTimeMillis()/1000);
		param.put("action", order.getAction());
		param.put("agentNo", order.getAgentNo());
		param.put("OrderNo", order.getOrderNo());
		param.put("custID", order.getCustId());
		param.put("custMobile", order.getCustMobile());
		param.put("ticketNO", order.getTicketNo());
		param.put("buyNum", order.getBuyNum());
		param.put("isDestine", order.getIsDestine());
		param.put("startDate","");
		param.put("endDate","");
		param.put("arriveDate", order.getArriveDate());
		param.put("time",unixTimestamp);
		String sign = makeSign(param);
		System.out.println(order.getCustName());
		String targetUrl =WebServiceConstant.getProperties("jiangxiwisdom.url")
		+ "?action=" + order.getAction()
		+ "&agentNo=" + order.getAgentNo() 
		+ "&OrderNo=" + order.getOrderNo()
		+ "&custName=" +URLEncoder.encode(order.getCustName(),"UTF-8")
		+ "&custID=" + order.getCustId() 
		+ "&custMobile="+ order.getCustMobile() 
		+ "&ticketNO=" + order.getTicketNo() 
		+ "&buyNum=" + order.getBuyNum()
		+ "&isDestine=" + order.getIsDestine()
		+ "&startDate=&endDate="
		+ "&arriveDate=" +order.getArriveDate()
		+ "&time=" + unixTimestamp
		+ "&sign=" + sign;
		log.info("jiangxi wisdom url:"+targetUrl);
		String result = HttpsUtil.requestGet(targetUrl,CHARACTER_ENCODING,CONNECTION_TIMEOUT,SO_TIMEOUT);
		return result;
	}
	
	
	
	public static String orderRequest(String action,String orderNo) throws Exception {
		String unixTimestamp =String.valueOf(System.currentTimeMillis()/1000);
		String agentNo=WebServiceConstant.getProperties("jiangxiwisdom.agentNo");
		Map<String, String> data = new LinkedHashMap<String, String>();
		data.put("action",action);//1订单提交 2订单退回 3订单关闭  5订单查询
		data.put("agentNo",agentNo);
		data.put("orderNo",orderNo);
		data.put("time",unixTimestamp);
		String sign=JiangxiwisdomClient.makeSign(data);
		String targetUrl = WebServiceConstant.getProperties("jiangxiwisdom.url")
				+ "?action="+ action
				+ "&agentNo="+ agentNo 
				+ "&OrderNo="+ orderNo 
				+ "&time="+ unixTimestamp 
				+ "&sign=" + sign;
		String result = HttpsUtil.requestGet(targetUrl,CHARACTER_ENCODING,CONNECTION_TIMEOUT,SO_TIMEOUT);
		return result;
	}
	
	/**
	 * 生成签名信息
	 * @return
	 */
	public static String makeSign(Map<String, String> paramMap) {
		Iterator<String> keys = paramMap.keySet().iterator();
		StringBuffer buffer = new StringBuffer();
		while(keys.hasNext()) {
			String key = (String) keys.next();
			String value = paramMap.get(key);
			buffer.append(value);
		}
		String agentKey=WebServiceConstant.getProperties("jiangxiwisdom.agentKey");
		buffer.append(agentKey);
		log.info("sign before md5 value:"+buffer.toString());
		String sign = Md5.encode(buffer.toString());
		return sign;
	}
}