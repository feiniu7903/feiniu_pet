package fax;

import com.lvmama.comm.utils.fax.FaxSender;

public class FaxSendTest {

	public static void main(String[] age){
		sendTicket();
		sendRoute();
		sendHotel();
	}
	private static void sendHotel() {
		String path="D:/ssh_crt/dowloads/temp/80941366369680719.html";
		FaxSender sender = new FaxSender("69108062", "80941366369680719", path,FaxSender.PNG);
		sender.send();
	}
	private static void sendRoute() {
		String path="D:/ssh_crt/dowloads/temp/84791366800003762.html";
		FaxSender sender = new FaxSender("69108062", "84791366800003762", path,FaxSender.PNG);
		sender.send();
	}
	private static void sendTicket() {
		String path="D:/ssh_crt/dowloads/temp/214831366874400071.html";
		FaxSender sender = new FaxSender("69108062", "68271365441001377", path,FaxSender.PNG);
		sender.setWidth(1000L);
		sender.send();
	}
	
	
}
