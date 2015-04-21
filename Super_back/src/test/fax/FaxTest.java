package fax;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class FaxTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			ApplicationContext  context = new ClassPathXmlApplicationContext("applicationContext-back-beans.xml");
			//OrdFaxTaskService ordFaxTaskService = (OrdFaxTaskService) context.getBean("ordFaxTaskService");
			//ordFaxTaskService.addFaxTaskDataByOrderId(327163);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
