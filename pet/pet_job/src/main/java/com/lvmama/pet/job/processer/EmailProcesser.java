package com.lvmama.pet.job.processer;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.service.email.EmailService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.job.task.EmailSendTask;

/**
 * 处理新建EMAIL消息
 * @author Alex Wang
 *
 */

public class EmailProcesser implements MessageProcesser{

	private EmailService emailService;
	
	@Override
	public void process(Message message) {
		if (message.isEmailTaskCreateMessage()) {
			EmailContent content = emailService.getEmailDirectSend(message.getObjectId());
			if (!Constant.emailStatus.SUCCESS.name().equals(content.getStatus())) {
				EmailSendTask task = (EmailSendTask)SpringBeanProxy.getBean("emailSendTask");
				task.initEmailContent(content);
				task.run();
			}
		}
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

}
