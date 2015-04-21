package com.lvmama.pet.job.quartz.sms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tempuri.MMSLocator;

import com.lvmama.comm.pet.po.sms.SmsContent;
import com.lvmama.pet.job.util.TestResources;

/**
 * 亿美彩信发送通道
 * @author Brian
 *
 */
public class EMayMMSSendJob extends AbstractSendJob {
	/**
	 * 日志输入器
	 */
	private static final Log LOG = LogFactory.getLog(EMayMMSSendJob.class);
   /**
    * 彩信发送账户
    */
	private String account;
	/**
	 * 彩信发送密码
	 */
	private String password;
	/**
	 * 彩信发送主题
	 */
	private String title;

	@Override
	protected int actualSend(final SmsContent smsContent) {
		if (null == smsContent || null == smsContent.getMobile()) {
			return ERROR;
		}
		//短信设置唯一的序列号
		smsContent.setSerialId(System.currentTimeMillis() * 1000 + (int) Math.ceil((Math.random() * 1000)));

		return send(smsContent);
	}

	@Override
	protected int send(final SmsContent smsContent) {
		if (null == smsContent || null == smsContent.getMobile()) {
			LOG.warn("joke?Try to send mssage to null!");
			return IGNORE;
		}
		if (TestResources.getInstance().getSmsJobIsTest()
				&& !TestResources.getInstance().getTestMobiles()
						.contains(smsContent.getMobile())) {
			LOG.info("[亿美彩信]短信测试发送,若要正式发送,请修改test.properties配置文件!");
			return SUCCESS;
		}
		String rtn = "";
		if (isActualSend()) {
			MMSLocator locator = new MMSLocator();
			try {
				rtn = locator.getMMSSoap().sendMMS(account, password, title,
						smsContent.getMobile(), smsContent.getData(), 1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		if (rtn.startsWith("OK")) {
			LOG.info("send success EMayMMS to mobiles:" + smsContent.getMobile() + ", id:" + smsContent.getId() + ", 返回值为:" + rtn);
			return SUCCESS;
		} else {
			LOG.error("send fail EMayMMS to mobiles:" + smsContent.getMobile() + ", id:" + smsContent.getId() + ", 返回值为:" + rtn);
			return ERROR;
		}
	}

	@Override
	protected void onSuccess(final SmsContent smsContent, final boolean autoSplit) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("It's MMS, save directly!");
		}
		try {
			smsRemoteService.moveSmsFromWaittingToSended(smsContent);
		} catch (Exception e) {
			LOG.error(smsContent + "encounter exception!" + e.getMessage());
		}
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(final String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * 主函数入口
	 * @param args 参数列表
	 */
	public static void main(final String[] args) {
		MMSLocator locator = new MMSLocator();
		java.io.FileInputStream fs;
		try {
			fs = new java.io.FileInputStream(
					"D://工作目录//亿美软通SDK4.1.0(JAVA)版_支持状态报告//"
					+ "亿美软通彩信平台SDK1.1.0(WebService版)用户手册//调用示例//Java//EmayMMSClient//EmayMMSClient//test.zip");
			byte[] content = new byte[fs.available()];
			fs.read(content, 0, content.length);
			fs.close();
			LOG.info(locator.getMMSSoap().sendMMS("SH-lvmm", "s123", "test", "13917677725", content, 1));
			LOG.info(locator.getMMSSoap().getStatusReport("SH-lvmm", "s123"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
