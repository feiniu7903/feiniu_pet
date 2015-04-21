import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lvmama.comm.bee.service.ebkpush.IEbkPushService;



public class TestServer extends TestBase{

	public static void main(String[] args) {
		//TestServer s = new TestServer();
		ApplicationContext	context = new ClassPathXmlApplicationContext("applicationContext-pet-push-beans.xml");
		IEbkPushService ebk = (IEbkPushService) context.getBean("ebkPushService");
	}
}
