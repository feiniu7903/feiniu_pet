package com.lvmama.operate.mail;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.lvmama.comm.edm.HqEmailResultMessage;
import com.lvmama.comm.edm.IHqEMailSenderService;
import com.lvmama.comm.edm.TaskDTO;
import com.lvmama.comm.edm.TemplateDTO;
import com.lvmama.operate.mail.model.ResultMessage;
import com.lvmama.operate.mail.util.HanQiUtil;

/**
 * 汉启EDM发送邮件服务实现
 * 
 * @author likun
 * 
 */
public class HqEMailSenderServiceImpl implements IHqEMailSenderService {

	@Override
	public HqEmailResultMessage sendEmail(TaskDTO task, TemplateDTO template,
			String[] userEmaiList, boolean isSend) throws Exception {
		return convertRsToHqEmailResultMessage(HqEMailSenderService
				.getInstance().sendEmail(
						HanQiUtil.createTask(task.getTaskName(),
								task.getEmailColumnName(),
								task.getTaskGroupId()),
						HanQiUtil.createTemplate(template.getFromName(),
								template.getSenderEmail(),
								template.getSubject(), template.getTempName(),
								template.getTemplateContent()), userEmaiList,
						null, isSend));
	}

	@Override
	public HqEmailResultMessage addTaskAndUploadTemplate(TaskDTO task,
			TemplateDTO template) throws Exception {
		return convertRsToHqEmailResultMessage(HqEMailSenderService
				.getInstance().addTaskAndUploadTemplate(
						HanQiUtil.createTask(task.getTaskName(),
								task.getEmailColumnName(),
								task.getTaskGroupId()),
						HanQiUtil.createTemplate(template.getFromName(),
								template.getSenderEmail(),
								template.getSubject(), template.getTempName(),
								template.getTemplateContent())));
	}

	@Override
	public HqEmailResultMessage uploadEmailList(int taskId,
			String[] userEmaiList, String partFlag) throws Exception {
		return convertRsToHqEmailResultMessage(HqEMailSenderService
				.getInstance().uploadEmailList(taskId, userEmaiList, partFlag));
	}

	@Override
	public HqEmailResultMessage finish(int taskId) throws Exception {
		return convertRsToHqEmailResultMessage(HqEMailSenderService
				.getInstance().finish(taskId));
	}

	@Override
	public HqEmailResultMessage start(int taskId, int sendMode)
			throws Exception {
		return convertRsToHqEmailResultMessage(HqEMailSenderService
				.getInstance().start(taskId, sendMode));
	}

	@Override
	public boolean taskIsSuccess(int taskId) throws Exception {
		return HqEMailSenderService.getInstance().taskIsSuccess(taskId);
	}

	private static HqEmailResultMessage convertRsToHqEmailResultMessage(
			ResultMessage rs) throws IllegalAccessException,
			InvocationTargetException {
		if (rs != null) {
			HqEmailResultMessage result = new HqEmailResultMessage();
			BeanUtils.copyProperties(result, rs);
			return result;
		} else {
			return null;
		}
	}

}
