package com.lvmama.pet.job.quartz.sms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.tempuri.WmgwLocator;

import com.lvmama.comm.pet.po.sms.SmsContent;
import com.lvmama.comm.pet.po.sms.SmsContentLog;
import com.lvmama.comm.pet.po.sms.SmsReceive;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.SmsStatusReport;
import com.lvmama.pet.job.sms.ReceiveLogic;
import com.lvmama.pet.job.util.TestResources;

/**
 * 梦网的短信发送机制
 * 
 * @author Brian
 * 
 */
public class MontnetsSendJob extends AbstractSendJob {
	WmgwLocator wmgwLocator = new WmgwLocator();
	/**
	 * 账户名
	 */
	private String account = "J21138";
	/**
	 * 密码
	 */
	private String password = "885962";
	/**
	 * 是否检测开关
	 */
	private boolean actualMonitor = false;
	/**
	 * 是否接收上行短信开关
	 */
	private boolean actualReceive = false;
	/**
	 * 短信上行逻辑列表
	 */
	private List<ReceiveLogic> receiveLogicList = new ArrayList<ReceiveLogic>();	

	@Override
	protected int send(SmsContent smsContent) {
		if (null == smsContent || null == smsContent.getMobile()) {
			LOG.warn("joke?Try to send mms to null mobile!");
			return IGNORE;
		}
		if (TestResources.getInstance().getSmsJobIsTest()
				&& !TestResources.getInstance().getTestMobiles()
						.contains(smsContent.getMobile())) {
			LOG.info("[梦网短信]短信测试发送,若要正式发送,请修改test.properties配置文件!");
			return SUCCESS;
		}
		String rtn = "0";
		if (isActualSend()) {
			try {
				rtn = wmgwLocator.getwmgwSoap().mongateCsSpSendSmsNew(account,
						password, smsContent.getMobile(),
						smsContent.getContent(),
						smsContent.getMobile().split(",").length, "*");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("send mobiles:" + smsContent.getMobile() + ", content:"
					+ smsContent.getContent() + ", return value:" + rtn);
		}
		try {
			if (null != rtn && rtn.length() > 10 && rtn.length() < 25) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("send successfully!content \""
							+ smsContent.getContent() + "\"to"
							+ smsContent.getMobile());
				}
				smsContent.setMemo(rtn);
				LOG.info("send " + smsContent.getId() + ", rtn " + rtn);
				return SUCCESS;
			} else {
				LOG.warn("failure(" + rtn + ")!content \""
						+ smsContent.getContent() + "\"to"
						+ smsContent.getMobile());
				return ERROR;
			}
		} catch (Exception e) {
			LOG.error("failure(" + rtn + ")!content \""
					+ smsContent.getContent() + "\"to" + smsContent.getMobile());
			return ERROR;
		}
	}

	/**
	 * 接受状态报告 短信报告处理
	 */
	public void monitor() {
		if (Constant.getInstance().isJobRunnable() && actualMonitor) {
			String[] strRet = null;
			
			try {
				strRet = wmgwLocator.getwmgwSoap()
						.mongateCsGetStatusReportExEx(account, password);
			} catch (Exception e) {
				return;
			}
			
			if (null == strRet) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("无最新的状态报告");
				}
			} else {
				List<SmsStatusReport> statusReportList = new ArrayList<SmsStatusReport>();
				for (String ret : strRet) {
					String[] resultArray = ret.split(",");
					String serialId = null;
					if (null == resultArray || resultArray.length != 6) {
						continue;
					}
					try {
						serialId = resultArray[2];
					} catch (Exception e) {
						continue;
					}
					if ("0".equals(resultArray[4])) {
						if (LOG.isDebugEnabled()) {
							LOG.debug("收到成功的发送状态报告,短信的流水号为" + serialId);
						}
						statusReportList.add(new SmsStatusReport(serialId,
								SmsContentLog.REPORT_FOR_SUCCESS, new Date(),
								ret));
					} else {
						if (LOG.isDebugEnabled()) {
							LOG.debug("收到成功的发送状态报告,短信的流水号为" + serialId);
						}
						statusReportList
								.add(new SmsStatusReport(serialId,
										SmsContentLog.REPORT_FOR_ERROR,
										new Date(), ret));
					}
				}
				if (!statusReportList.isEmpty()) {
					onReport(statusReportList);
				}
			}
		}
	}
	
	/**
	 * 接收上行短信
	 */
	public void receive() {
		if (!actualReceive) {
			return;
		}
		try {
			String[] receives = wmgwLocator.getwmgwSoap().mongateCsGetSmsExEx(account, password);
			for (String receive : receives) {
				String[] modules = receive.split(",");
				if (null != modules && modules.length == 6) {
					SmsReceive smsReceive = new SmsReceive();
					smsReceive.setChannelNumber(modules[3]);
					smsReceive.setMobileNumber(modules[2]);
					smsReceive.setSendDate(com.lvmama.comm.utils.DateUtil.stringToDate(modules[0] + " " + modules[1], "yyyy-MM-dd hh:mm:ss"));
					smsReceive.setContent(modules[5]);
					smsRemoteService.saveSmsReceive(smsReceive);
					String mobile = modules[2];
					if (mobile.length() == 13 && mobile.startsWith("86")) {
						mobile = mobile.substring(2);
					}
					for (ReceiveLogic logic : receiveLogicList) {
						try {
							if  (ReceiveLogic.SKIP_MESSAGE
									== logic.execute(mobile, modules[5]))  {
								break;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			
		}
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setActualMonitor(boolean actualMonitor) {
		this.actualMonitor = actualMonitor;
	}
	
	public void setReceiveLogicList(List<ReceiveLogic> receiveLogicList) {
		this.receiveLogicList = receiveLogicList;
	}	
		
	public void setActualReceive(boolean actualReceive) {
		this.actualReceive = actualReceive;
	}

	public static void main(String[] args) throws Exception {
		WmgwLocator wmgwLocator = new WmgwLocator();
		wmgwLocator.getwmgwSoap().mongateCsSpSendSmsNew("F12240",
				"209910", "15800926537",
				"驴妈妈测试短信1111你好",
				1, "*");
		/*String[] receives = new String[]{"2008-01-23,15:43:34,15986756631,10657302056780408,*,信息内容1"};
		for (String receive : receives) {
			String[] modules = receive.split(",");
			if (null != modules && modules.length == 6) {
				for (String module : modules) {
					System.out.println(module);
				}
				System.out.println(com.lvmama.comm.utils.DateUtil.stringToDate(modules[0] + " " + modules[1], "yyyy-MM-dd hh:mm:ss"));
			}
		}*/
	}
}
