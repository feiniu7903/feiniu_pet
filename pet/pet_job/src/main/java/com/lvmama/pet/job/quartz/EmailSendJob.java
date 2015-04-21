package com.lvmama.pet.job.quartz;

import org.springframework.core.task.TaskExecutor;

import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.service.email.EmailService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.job.task.EmailSendTask;

public class EmailSendJob implements Runnable {

	private EmailService emailService;
	private TaskExecutor emailSendTaskExecutor;
	private int reSendTime;
	private int reSendCount;
	@Override
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			boolean loop = true;
			while(loop) {
				EmailContent content = emailService.getQueuedEmail(reSendTime,reSendCount);
				if (content!=null) {
					EmailSendTask task = (EmailSendTask)SpringBeanProxy.getBean("emailSendTask");
					task.initEmailContent(content);
					emailSendTaskExecutor.execute(task);
				}else{
					loop = false;
				}
			}
		}
	}
	
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public void setEmailSendTaskExecutor(TaskExecutor emailSendTaskExecutor) {
		this.emailSendTaskExecutor = emailSendTaskExecutor;
	}

	public void setReSendTime(int reSendTime) {
		this.reSendTime = reSendTime;
	}

	public void setReSendCount(int reSendCount) {
		this.reSendCount = reSendCount;
	}

}
