package com.lvmama.apply.code;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lvmama.comm.bee.po.pass.PassportMessage;
import com.lvmama.comm.bee.service.pass.PassportMessageService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;

public class ApplyCodeThreadTest {
	static PassportMessageService passportMessageService;
	
	public static void main(String[] args) throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
				"applicationContext-passport-beans.xml"
		});
		try{
			passportMessageService = context.getBean("passportMessageService",PassportMessageService.class);
//			while(true){
//				Scanner scanner = new Scanner(System.in);
//				while(scanner.hasNextLine()){
//					String line = scanner.nextLine();
			String line="50";
					if(line.equals("break")){
						throw new IllegalArgumentException();
					}
					int num = NumberUtils.toInt(line);
					buildMessage(num);
//				}
//			}
		}catch(IllegalArgumentException ex){
			
		}
		System.out.println("done.");
	}
	
	private static void buildMessage(int num){
		for(int i =0;i<num;i++){
			Message msg = MessageFactory.newPasscodeApplyMessage((long)i);
			PassportMessage p = PassportMessage.newPassportMessage(msg, "ip1");
			p.setProcessor(processorList[RandomUtils.nextInt()%3]);
			passportMessageService.add(p);
		}
	}

	private static final String[] processorList={"baidu.com","csdn.net","ifeng.com"};
}
