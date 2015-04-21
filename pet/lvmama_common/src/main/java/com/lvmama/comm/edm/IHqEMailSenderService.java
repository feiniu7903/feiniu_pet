package com.lvmama.comm.edm;

import java.io.IOException;

/**
 * 汉启EDM发送接口
 * 
 * @author likun
 * 
 */
public interface IHqEMailSenderService {
	/**
	 * 批量发送邮件
	 * 
	 * @param task
	 *            任务
	 * @param template
	 *            模板
	 * @param userEmaiList
	 *            收件人列表
	 * @param isSend
	 *            是否启动任务发送
	 * @return
	 * @throws IOException
	 */
	public HqEmailResultMessage sendEmail(TaskDTO task, TemplateDTO template,
			String[] userEmaiList, boolean isSend) throws Exception;

	/**
	 * 上载任务和模板
	 * 
	 * @param task
	 *            任务对象
	 * @param template
	 *            模板对象
	 * @return
	 */
	public HqEmailResultMessage addTaskAndUploadTemplate(TaskDTO task,
			TemplateDTO template) throws Exception;

	/**
	 * 上载用户信息
	 * 
	 * @param taskId
	 *            任务id
	 * @param userEmaiList
	 *            接受者邮箱列表
	 * @param partFlag
	 *            分段标识，详情请查看汉启EDM接口描述文档
	 * @return
	 * @throws IOException
	 */
	public HqEmailResultMessage uploadEmailList(int taskId,
			String[] userEmaiList, String partFlag) throws Exception;

	/**
	 * EmailList上传完成后通知服务器端的方法
	 * 
	 * @param taskId
	 *            参数的值必须是已经添加过任务信息和上传过EmailList数据的Id 编号
	 * @return
	 * @throws IOException
	 */
	public HqEmailResultMessage finish(int taskId) throws Exception;

	/**
	 * 发送的方法
	 * 
	 * @param taskId
	 *            参数的值必须是已经添加过任务信息和上传过EmailList数据并且调用了finish方法的Id 编号
	 * @param sendMode
	 *            发送模式。发送模式：0：发送 1：清洗
	 * @return
	 * @throws IOException
	 */
	public HqEmailResultMessage start(int taskId, int sendMode)
			throws Exception;

	/**
	 * 根据taskId获取任务执行是否成功
	 * 
	 * @param taskId
	 *            任务id
	 * @return
	 */
	public boolean taskIsSuccess(int taskId) throws Exception;
}
