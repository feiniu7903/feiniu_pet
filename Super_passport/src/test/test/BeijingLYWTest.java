package test;

import java.util.HashMap;
import java.util.Map;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.lvmama.comm.utils.TemplateUtils;

public class BeijingLYWTest {
	public static void main(String[] args) throws Exception {
		String reqXml = null;
		String resXml = null;
		//reqXml = "<request><head><version>1</version><messageId>20121214163612203</messageId><partnerCode>59000</partnerCode><proxyId>ticket_test</proxyId><timeStamp>20121214163612203</timeStamp><signed>nXuDJXLMAE9YQg+REvIL+w==</signed></head><body><order><orderId>20121214260190</orderId><agentOrderId></agentOrderId><count>3</count><settlementPrice>100</settlementPrice><isSendSms>1</isSendSms><productId>109</productId><payType>1</payType><validTimeBegin>201212150000</validTimeBegin><validTimeEnd>201212291200</validTimeEnd><inDate>201212150000</inDate><user><name>测试</name><mobile>13100000001</mobile><sex></sex><idCard></idCard></user><feature></feature></order></body></request>";
		//System.out.println(TemplateUtils.formatXml(reqXml));
		//resXml = sendReqToSohu(WebServiceConstant.getProperties("beijingLYW.url"), "submitOrder", reqXml);
		//System.out.println(TemplateUtils.formatXml(resXml));
		
		//reqXml = "<request><head><version>1</version><messageId>20121218113856176</messageId><partnerCode>59000</partnerCode><proxyId>ticket_test</proxyId><timeStamp>20121218113856176</timeStamp><signed>t0i5BUaadCZEa5jERZNYAQ==</signed></head><body><order><orderId>20121218260230</orderId></order></body></request>";
		//System.out.println(TemplateUtils.formatXml(reqXml));
		//resXml = sendReqToSohu(WebServiceConstant.getProperties("beijingLYW.url"), "reSendSMS", reqXml);
		//System.out.println(TemplateUtils.formatXml(resXml));
		
		String s="hi,${user}";
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("user", "guobin");
		System.out.println(TemplateUtils.fillStringTemplate(s, root));
	}
	
	private static String sendReqToSohu(String url, String methodName, String reqXml) throws Exception {
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(url));
		call.setOperationName(methodName);	//接口名称
		call.setTimeout(10000);//10s
		return (String) call.invoke(new String[] {reqXml});
	}
}
