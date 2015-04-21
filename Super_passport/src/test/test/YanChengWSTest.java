package test;

import junit.framework.TestCase;

/**
 * 测试淹城WebService
 * 
 * @author qiuguobin
 */
public class YanChengWSTest extends TestCase {
	public void testSendOrder() {/*
		System.out.println(CalendarUtils.getXMLGregorianCalendar("2011-03-24 00:00:00"));

		Reserve reserve = new Reserve();
		reserve.setDjno("201103282256");
		reserve.setLinkman("李四");
		reserve.setTelphone("12345678901");
		reserve.setContent(null);
		reserve.setCdate(CalendarUtils.getXMLGregorianCalendar(new Date()));
		reserve.setPaymode("01");
		reserve.setPayflag(false);
		reserve.setAgentno("002");
		// reserve.setIpaddress("");
		reserve.setIpaddress("127.0.0.1");
		reserve.setRtypeid("001");
		reserve.setRnum(20);
		reserve.setRprice(new BigDecimal(20.0));
		reserve.setRdate(CalendarUtils.getXMLGregorianCalendar(new Date()));
		reserve.setAgentkey("16A92A3CA4FA2835");

		Service1Client serviceClient = new Service1Client();
		Service1Soap servicePort = serviceClient.getService1Soap();
		try {
			servicePort.addData(reserve);
		} catch(WebServiceException e) {
			e.printStackTrace();
		}
	*/}

	public void testDoAfterPerformance() {/*
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-passport-beans.xml");
		IYanChengService yanChengService = (IYanChengService) context.getBean("testYanChengService");
		String djno = "201104022336".trim();//orderid=331037
		int childQuantity = 0;
		int adultQuantity = 0;
		Assert.assertEquals(1, yanChengService.doAfterPerformance(djno, childQuantity, adultQuantity));
	*/}
	
	public void testDoAfterPerformanceCancelled() {/*
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-passport-beans.xml");
		IYanChengService yanChengService = (IYanChengService) context.getBean("testYanChengService");
		String djno = "111".trim();
		int childQuantity = 0;
		int adultQuantity = 0;
		Assert.assertEquals(2, yanChengService.doAfterPerformance(djno, childQuantity, adultQuantity));
	*/}
}
