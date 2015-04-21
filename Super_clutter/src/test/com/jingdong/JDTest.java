package com.jingdong;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.distribution.service.DistributionForJingdongService;
import com.lvmama.distribution.util.JdUtil;

public class JDTest {
	private DistributionForJingdongService distributionForJingdongService;

	public static void main(String[] args) throws Exception {
		/*ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-clutter-beans.xml");
		DistributionForJingdongService distributionForJingdongService = (DistributionForJingdongService)context.getBean("distributionForJingdongService");
		Request request = null;
		String xml = new JDTest().getXml("JingdongTest.xml");
		try {
			request = JdUtil.getRequestOrder(xml);
		} catch (Exception e) {
			
		}
		distributionForJingdongService.getSumbitOrderResXml(request);*/
		Long a = 11l;
		Long b = 11l;
		System.out.println(a != b );
	}
	
	@Test
	public void addProduct(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-clutter-beans.xml");
		DistributionForJingdongService distributionForJingdongService = (DistributionForJingdongService)context.getBean("distributionForJingdongService");
		distributionForJingdongService.addProducts();
	}
	
	private String getXml(String fileName) throws Exception{
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("com/jingdong/"+fileName);
		SAXReader reader = new SAXReader();
		org.dom4j.Document document = reader.read(inputStream);
		return document.asXML();
	}

	/**
	 * 解析京东查询订单
	 * @throws Exception 
	 */
	@Test
	public void getQueryOrder() throws Exception {
		String xml = getXml("queryOrder.xml");
		Map<String,String> map=new HashMap<String, String>();
		map.put("msg", xml);
		map.put("cmd", "20003");
		System.out.println(HttpsUtil.requestPostForm("http://192.168.0.249/clutter/distribution/distributionForJingdong.do",map));
	}

	@Test
	public void createOrder() throws Exception{
		String xml = getXml("JingdongTest.xml");
		Map<String,String> map=new HashMap<String, String>();
		map.put("msg", xml);
		map.put("cmd", "20002");
		System.out.println(HttpsUtil.requestPostForm("http://192.168.0.243/clutter/distribution/distributionForJingdong.do",map));
	}
	
	/**
	 * 解析京东查询每日价格
	 */
	@Test
	public void getQueryDailyPrice() throws Exception{
		String xml =getXml("QueryDailyPrice.xml");
		Map<String,String> map=new HashMap<String, String>();
		map.put("msg", xml);
		map.put("cmd", "20006");
		System.out.println("reqXml = "+xml);
		System.out.println(HttpsUtil.requestPostForm("http://192.168.0.243/clutter/distribution/distributionForJingdong.do",map));
	}

	@Test
	public void resendSMS() throws Exception {
		String xml =getXml("resendSMS.xml");
		Map<String,String> map=new HashMap<String, String>();
		map.put("msg", xml);
		map.put("cmd", "20004");
		System.out.println(HttpsUtil.requestPostForm("http://192.168.0.249:8035/clutter/distribution/distributionForJingdong.do",map));
	}
	@Test
	public void signTest(){
		System.out.println(JdUtil.getSigned("ea880e03b2cdaa1641bebc3eabe86ed1JINGDONG36097"));
		
	}
	

	public DistributionForJingdongService getDistributionForJingdongService() {
		return distributionForJingdongService;
	}

	public void setDistributionForJingdongService(DistributionForJingdongService distributionForJingdongService) {
		this.distributionForJingdongService = distributionForJingdongService;
	}
}
