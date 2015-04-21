package com.lvmama.search.util.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthenticator extends Authenticator{
	 String userName=null;  
	    String password=null;  
	       
	    public MyAuthenticator(){  
	    }  
	    public MyAuthenticator(String username, String password) {   
	        this.userName = username;   
	        this.password = password;   
	    }   
	    protected PasswordAuthentication getPasswordAuthentication(){  
	        return new PasswordAuthentication(userName, password);  
	    }  
	    
	    
	    public static void main(String[] args){  
	    	MyAuthenticator aa=new MyAuthenticator();
	    	aa.sendmessage("test");
	    }  
	    
	    public void sendmessage(String message){
	    	
	    	MailSenderInfo mailInfo = new MailSenderInfo();   
		      mailInfo.setMailServerHost("mail.lvmama.com");   
		      mailInfo.setMailServerPort("25");   
		      mailInfo.setValidate(true);   
		      mailInfo.setUserName("lvmamasearch@lvmama.com");   
		      mailInfo.setPassword("Joyu.com");//您的邮箱密码   
		      mailInfo.setFromAddress("lvmamasearch@lvmama.com");   
		      mailInfo.setToAddress("fuweiren@lvmama.com,chenjie@lvmama.com,renyudi@lvmama.com,shengshenghua@lvmama.com,tianyidan@lvmama.com,zhangshen@lvmama.com,zouhaijun@lvmama.com,wenzhengtao@lvmama.com,chenkeke@lvmama.com");
		      mailInfo.setSubject("index_condition");   
		      mailInfo.setContent(message);   
		         //这个类主要来发送邮件  
		      SimpleMailSender sms = new SimpleMailSender();  
		          sms.sendTextMail(mailInfo);//发送文体格式   
	    }
}
