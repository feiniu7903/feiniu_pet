package com.lvmama.comm.abroad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.lvmama.comm.abroad.service.IReservationsOrder;
import com.lvmama.comm.abroad.vo.request.ReservationsOrderReq;
import com.lvmama.comm.abroad.vo.response.ReservationsOrderHotelDetailRes;
import com.lvmama.comm.abroad.vo.response.ReservationsOrderRes;

public class ReservationsOrderTest extends BaseTest{
	@Autowired
	@Qualifier("abroadhotelReservationsOrderService")
	private IReservationsOrder reservationsOrder;
//	@Test
//	public void getReservationsOrderList(){
//		ReservationsOrderReq reservationsOrderReq=new ReservationsOrderReq();
//		reservationsOrderReq.setUserId("12345613646");
////		reservationsOrderReq.setResMadeFrom(DateUtils.addDays(new Date(), -10));
//		ReservationsOrderRes reservationsOrderRes=reservationsOrder.queryForOrderList(reservationsOrderReq,null);
//		System.out.println(reservationsOrderRes.getReservationsOrders().size());
//		XStream xstream = new XStream(new DomDriver());
//		xstream.alias("ReservationsOrderRes", ReservationsOrderRes.class);
//	    String xml = xstream.toXML(reservationsOrderRes);
//	    System.out.println(xml); 
//		
//	}
////	@Test
//	public void getPersonTest(){
//		List<ReservationsOrderPersonDetailRes> lst=reservationsOrder.queryForPersonDetailByAbroadhotelId(1538722);
//		System.out.println(lst.size());
//	}
//	@Test
	public void xmlToObjectTest(){
		ReservationsOrderHotelDetailRes res=reservationsOrder.queryForHotelDetailByKey(2421L);
		
		
		
		
		
		
		
		
		
		
		
//		XStream xstream = new XStream(new DomDriver());
//		xstream.alias("root", HashMap.class);
//		xstream.alias("map1", Map.Entry.class);
//		xstream.alias("GetReservationDetails", HashMap.class);
//		xstream.alias("Reservation", HashMap.class);
//		xstream.alias("Hotel", HashMap.class);
//		xstream.alias("CancellationPolicy", HashMap.class);
//		xstream.alias("isSuccess", HashMap.class);
//		xstream.alias("Days", ArrayList.class);
//		xstream.alias("Day", HashMap.class);
//		xstream.alias("Rooms", ArrayList.class);
//		xstream.alias("Room", HashMap.class);
//		xstream.alias("InclBoardType", HashMap.class);
//		xstream.alias("Adults", HashMap.class);
//		xstream.alias("Children", HashMap.class);
//		xstream.registerConverter(new PojoMapConverter());
//	    Map<String, Object> map = (Map<String, Object>)xstream.fromXML(res.getVoucher());
//		Object map= xstream.fromXML(str);
//		System.out.println(res.getVoucher());
	}
	@Test
	public void getOrderTest(){
		ReservationsOrderReq reservationsOrderReq=new ReservationsOrderReq();
		reservationsOrderReq.setOrderNo("2421");
		ReservationsOrderRes res=reservationsOrder.queryForOrderList(reservationsOrderReq, "");
		
		System.out.println(res.getReservationsOrders().get(0).getActualPayFloat());
		System.out.println(res.getReservationsOrders().get(0).getOughtPayFloat());
	}
//	@Test
	public void getVoucher(){
//		String back=reservationsOrder.getVoucherXml("1420");
//		System.out.println(back);
//		write2File(back);
		
//		String back=reservationsOrder.getVoucherXml("1420");
//		back=back.replace("SPAIN", "西班牙");
//		System.out.println(back);
//		write2File(back);
//		PdfUtil.createPdfFile(back, "d:/112211.pdf");
		
//		write2File(reservationsOrder.getVoucherXml("61"));
		byte[] b=reservationsOrder.getVoucherPdf("81");
		write2File(b);
		
	}
	
	private void write2File(String str){
		OutputStream output=null;
		try {
			output=new FileOutputStream(new File("d:/lvmama"+System.currentTimeMillis()+".xml"));
			output.write(str.getBytes());
			output.flush();
			output.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void write2File(byte[] bs){
		OutputStream output=null;
		try {
			output=new FileOutputStream(new File("d:/lvmama"+System.currentTimeMillis()+".pdf"));
			output.write(bs);
			output.flush();
			output.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
