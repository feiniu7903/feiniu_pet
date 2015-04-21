package com.lvmama.passport.dao;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
@ContextConfiguration("classpath:applicationContext-passport-database.xml")
public class PassCodeDAOTest extends AbstractJUnit4SpringContextTests {/*
	@Resource(name="passCodeDAO")
	private PassCodeDAO passCodeDAO;
	@Resource(name="orderServiceProxy")
	private OrderService orderServiceProxy;
	*//**
	 * 测试回调保存数据
	 *//*
	//@org.junit.Test
	public void testUpdatePassCode(){
		PassCode passcode = new PassCode();
		passcode.setSerialNo("111111");
		passcode.setExtId("111111");
		byte im[] = process();
		passcode.setCodeImage(im);
		passCodeDAO.updatePassCodeBySerialNo(passcode);
	}
	*//**
	 * 生成二维码二进制流
	 * @return
	 *//*
	public byte[] process(){
		DistributorOrderClient distributorOrderClient = new DistributorOrderClient();
		String order = SzParkStringUtils.getorderInfoByExtId("0000000389");
		System.out.println("order=========="+order);
		String sysdate = SzParkStringUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		String operator = SzParkConstant.getSzParkOperator();
		String pass=SzParkConstant.getSzParkPass();
		String sign=SzParkStringUtils.getCheckImageSign(order,sysdate,operator,pass);
		System.out.println("sign==========="+sign);
		String rs=distributorOrderClient.getDistributorOrderHttpPort().orderCheckImage(order.toString(),sysdate,SzParkConstant.getSzParkMerId(),operator, sign, "md5");
		String imageStr = SzParkStringUtils.paserXml(rs, SzParkConstant.CHECK_IMAGE);
		System.out.println("image========"+imageStr);
		try {
			byte[] b = new BASE64Decoder().decodeBuffer(imageStr);
			System.out.println("base64=========="+new BASE64Encoder().encode(b).length());
			return b;
		} catch (IOException e) {
//			e.printStackTrace();
		}
		return null;
	}
	//@org.junit.Test
	public void testImage(){
		File file = new File("g:/mail/bbb.jpg");
		try {
			FileOutputStream os = new FileOutputStream(file);
			PassCode passcode = passCodeDAO.getCodeBySerialNo("111111");
			os.write(passcode.getCodeImage());
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@org.junit.Test
	public void testRemoteOrder(){
		Assert.assertNotNull(orderServiceProxy.queryOrdOrderByOrderId(new Long(320276)));
	}
	public PassCodeDAO getPassCodeDAO() {
		return passCodeDAO;
	}
	public void setPassCodeDAO(PassCodeDAO passCodeDAO) {
		this.passCodeDAO = passCodeDAO;
	}
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
*/}
