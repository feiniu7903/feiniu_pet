package org.sendinfo.piaowubao;

import junit.framework.TestCase;
/**
 *  订单操作
 * @author xujianmin
 *
 */
public class DistributorOrderTest extends TestCase {/*
	private DistributorOrderClient distributorOrderClient;
	private DistributorOrderPortType port;
	public void setUp(){
		distributorOrderClient = new DistributorOrderClient();
		port = distributorOrderClient.getDistributorOrderHttpPort();
		
	}
	*//**
	 * 下载订单信息
	 *//*
	public void testOrderInfo() {
//		try {
//			port.orderInfo("", "", "", "", "", "");
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
	}
	*//**
	 * 订单查询
	 *//*
	public void testOrderQuery() {
//		try {
//			port.orderQuery("", "", "", "", "");
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
	}
	*//**
	 * 订单取消
	 *//*
	public void testOrderCancel() {
//		try {
//			port.orderCancel("", "", "", "", "", "");
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}

	}
	*//**
	 * 订单预定
	 *//*
	public void testOrderBook() {
		String orderInfo="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><order wayType=\"1\" sellerCode=\"0000000163\" outOrderNo=\"1201669\" mobile=\"13735875217\" linkman=\"庄俊安\" idCard=\"330721198204221052\"><ticketDetail travelDate=\"2011-01-12\" ticketCode=\"0000000314\" price=\"300.0\" amount=\"3\"/></order>";
		String sysdate = SzParkStringUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		String operator = SzParkConstant.getSzParkOperator();
		String pass=SzParkConstant.getSzParkPass();
		String sign = SzParkStringUtils.getCheckImageSign(orderInfo,sysdate,operator,pass);
		String rs=port.orderBook(orderInfo, sysdate, SzParkConstant.getSzParkMerId(), operator, sign, "md5");
		System.out.println("rs============="+rs);
		String szorderno = SzParkStringUtils.getAttributeValue(rs, "orderNo");
		if("".equals(szorderno)){
			System.out.println("msgId============="+ SzParkStringUtils.getAttributeValue(rs, "msgId"));
			System.out.println("msg============="+ SzParkStringUtils.getAttributeValue(rs, "msg"));
		}
		System.out.println("szorderno============="+szorderno);
	}
	*//**
	 * 得到订单的二维码图片
	 *//*
	public void testOrderCheckImage() {
//		String order = SzParkStringUtils.getorderInfoByExtId("0000000389");
//		System.out.println("order=========="+order);
//		String sysdate = SzParkStringUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
//		String operator = SzParkConstant.getSzParkOperator();
//		String sign=SzParkStringUtils.getCheckImageSign(order,sysdate,operator);
//		System.out.println("sign==========="+sign);
//		String rs=port.orderCheckImage(order.toString(),sysdate,SzParkConstant.getSzParkMerId(),operator, sign, "md5");
//		String imageStr = SzParkStringUtils.paserXml(rs, SzParkConstant.CHECK_IMAGE);
//		try {
//			byte[] b = new BASE64Decoder().decodeBuffer(imageStr);
//			createImage(b);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public void createImage(byte[] fileinfo) {
		File file = new File("g:/mail/bbb.jpg");
		try {
			FileOutputStream os = new FileOutputStream(file);
			os.write(fileinfo);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	*//**
	 * 订单修改
	 *//*
	public void testOrderModify() {
//		try {
//			port.orderModify("", "", "", "", "", "");
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
	}

*/}
