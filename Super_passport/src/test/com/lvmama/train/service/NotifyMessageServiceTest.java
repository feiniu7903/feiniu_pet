package com.lvmama.train.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.passport.utils.WebServiceClient;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration( { "classpath:applicationContext-back-beans.xml" })
//@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
//@Transactional
public class NotifyMessageServiceTest {
	
	//@Autowired
	//NotifyMessageService notifyMessageService;
	StringBuffer requestXml=new StringBuffer();
	@Before
	public void doBefore(){
//		File file = new File("E:/火车票/01系统对接/接口XML示例/消息通知服务契约/03出票结果通知请求.xml");
		File file = new File("E:/火车票/接口契约新增和修改/消息通知服务契约---新增/07退款通知请求.xml");
		try {
			System.out.println(file.exists());
			//Scanner sc = new Scanner(file);
			FileReader reader = new FileReader(file);
			char[] buf=new char[1024];
			int len=0;
			while((len=reader.read(buf))>0){System.out.println("vvv");
				requestXml.append(buf,0,len);
				//requestXml.append(sc.nextLine());
				//requestXml.append("\r\n");
			}
			System.out.println(requestXml);
			//requestXml.setLength(requestXml.length()-2);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void testRequest() {
		//notifyMessageService.Request(requestXml.toString());
		try {
			String str = WebServiceClient.call("http://192.168.0.248:8035/passport/services/trainNotifyService?wsdl", new String[]{requestXml.toString()}, "Request");
			System.out.println(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
