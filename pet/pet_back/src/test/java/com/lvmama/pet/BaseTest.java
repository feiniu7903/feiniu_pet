package com.lvmama.pet;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath*:/applicationContext-pet-back-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseTest {

	@Autowired
	private JavaMailSender javaMailSender;
	@Test
	public void testInsert(){
		MimeMessagePreparator preparator = new MimeMessagePreparator() {  
            public void prepare(MimeMessage mimeMessage) throws Exception {  
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("874504134@qq.com"));//toXXX邮箱  
                mimeMessage.setFrom(new InternetAddress("nixianjun@joyu.com")); //from XXX邮箱  
                mimeMessage.setSubject("weolcom");      //设置主题  
                mimeMessage.setText("hello");          //设置内容  
            }  
        }; 
		javaMailSender.send(preparator);
		
	}
}
