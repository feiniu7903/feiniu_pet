package com.lvmama.pet.job.quartz.sms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.emay.sdk.client.api.Client;
import cn.emay.sdk.client.api.MO;
import cn.emay.sdk.client.api.StatusReport;
import cn.emay.sdk.client.listener.ReceiveMessageListener;
import cn.emay.sdk.communication.socket.ResponseMsg;

import com.lvmama.comm.pet.po.sms.SmsContent;
import com.lvmama.comm.pet.po.sms.SmsContentLog;
import com.lvmama.comm.pet.po.sms.SmsReceive;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.SmsStatusReport;
import com.lvmama.comm.vo.Constant.SEND_MSG_CHANNEL;
import com.lvmama.pet.job.sms.ReceiveLogic;
import com.lvmama.pet.job.util.TestResources;

/**
 * 亿美的短信发送通道
 * @author Brian
 */
public class EMaySendJob extends AbstractSendJob {
	
	public static void main(String[] args) throws Exception {
		Client client = new Client("9SDK-EMY-0999-JBYRR", "061614",null, "sdk999.eucp.b2m.cn");
		System.out.println(client.getBalance());
		System.out.println(client.getEachFee());
//		SDKClient client = new SDKClient("9SDK-EMY-0999-JBYRR", "061614", "sdk999.eucp.b2m.cn");
		String [] mobiles = {"15800926537"};
		String content = "订单号3680966，感谢预定此产品，请凭手机短信在景区入园处刷二维码入园（08:00－16:00），仅限使用一次，转发无效，景区地址：成都外北熊猫大道1375号，祝您明日出行愉快，如需帮助请致电021-60810158 并报订单号。【你好】";
		Long serialId = 1000000000000000001L;
		System.out.println("返回值:"+client.sendSMSEx(mobiles, content,
				ADDSERIAL, "GBK", DEFAULT_PRIORITY, serialId));
		/*while(true){
			System.out.println(JSONSerializer.toJSON(client.getReport()));
			Thread.sleep(1000);
		}*/
	}
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(EMaySendJob.class);
	/**
	 * 扩展位，无具体含义，只为了配合亿美的函数参数使用
	 */
	private static final String ADDSERIAL = "";
	/**
	 * 亿美短信日期格式化器
	 */
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 亿美客户端
	 */
	private SDKClient client;
	/**
	 * 余额阀值
	 */
	private double minThreshold = 10D;
	/**
	 * 是否检测开关
	 */
	private boolean actualMonitor = false;
	/**
	 * 是否接收上行短信开关
	 */
	private boolean actualReceive = false;
	/**
	 * 失败是否重新发送
	 */
	private boolean retrySend = false;

	/**
	 * 短信上行逻辑列表
	 */
	private List<ReceiveLogic> receiveLogicList = new ArrayList<ReceiveLogic>();

	@Override
	protected int send(final SmsContent smsContent) {
		if (null == smsContent || null == smsContent.getMobile()) {
			LOG.warn("joke?Try to send mms to null mobile!");
			return IGNORE;
		}
		if (TestResources.getInstance().getSmsJobIsTest()
				&& !TestResources.getInstance().getTestMobiles()
						.contains(smsContent.getMobile())) {
			LOG.info("[亿美短信]短信测试发送,若要正式发送,请修改test.properties配置文件!");
			return SUCCESS;
		}
		String originalContent = smsContent.getContent();
		preDisposeSmsContent(smsContent);
		String[] mobiles = null;
		if (smsContent.getMobile().indexOf(SEPARATOR) != -1) {
			mobiles = new String[]{smsContent.getMobile()};
		} else {
			mobiles = smsContent.getMobile().split(SEPARATOR);
		}
		int rtn = 0;
		if (isActualSend()) {
			rtn = client.sendSMSEx(mobiles, smsContent.getContent(),
					ADDSERIAL, "GBK", DEFAULT_PRIORITY, smsContent.getSerialId());
			smsContent.setContent(originalContent);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("send mobiles:" + smsContent.getMobile() + ", content:"
					+ smsContent.getContent() + ", return value:" + rtn);
		}
		switch (rtn) {
			case 0 :
				if (LOG.isDebugEnabled()) {
					LOG.debug("send successfully!content \"" + smsContent.getContent() + "\"to" + smsContent.getMobile());
				}
				return SUCCESS;
			case 17:
				LOG.warn("failure(" + rtn + ")!content \"" + smsContent.getContent() + "\"to" + smsContent.getMobile());
				return ERROR;
			case 101:
				LOG.error("failure(101)!content \"" + smsContent.getContent() + "\"to" + smsContent.getMobile());
				return ERROR;
			case 305:
				LOG.error("failure(305)!content \"" + smsContent.getContent() + "\"to" + smsContent.getMobile());
				return ERROR;
			case 307:
				LOG.error("failure(307)!content \""
						+ smsContent.getContent() + "\"to" + smsContent.getMobile());
				return ERROR;
			case 997:
				LOG.error("failure(997)!content \""
						+ smsContent.getContent() + "\"to" + smsContent.getMobile());
				return ERROR;
			case 998:
				LOG.error("failure(998)!content \""
						+ smsContent.getContent() + "\"to" + smsContent.getMobile());
				return ERROR;
			default:
				LOG.error("failure(" + rtn + ")!content \"" + smsContent.getContent() + "\"to" + smsContent.getMobile());
				return ERROR;
		}
	}
	
	private void preDisposeSmsContent(SmsContent content){
		if(content!=null && StringUtil.isNotEmptyString(content.getContent())&&!content.getContent().contains(QIANMING)){
			content.setContent(content.getContent()+QIANMING);
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
			List<MO> receives = client.getMO();
			if (null != receives && !receives.isEmpty()) {
				LOG.info("共收到了" + receives.size() + "条短信!");
				for (MO receive : receives) {
					if (StringUtils.isEmpty(receive.getSmsContent())) {
						continue;
					}
					SmsReceive smsReceive = new SmsReceive();
					smsReceive.setChannelNumber(receive.getChannelnumber());
					smsReceive.setMobileNumber(receive.getMobileNumber());
					smsReceive.setSendDate(receive.getSentTime());
					smsReceive.setContent(receive.getSmsContent());
					smsRemoteService.saveSmsReceive(smsReceive);
					String mobile = receive.getMobileNumber();
					if (mobile.length() == 13 && mobile.startsWith("86")) {
						mobile = mobile.substring(2);
					}
					for (ReceiveLogic logic : receiveLogicList) {
						try {
							if  (ReceiveLogic.SKIP_MESSAGE
									== logic.execute(mobile, receive.getSmsContent()))  {
								break;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.warn("接收上行短信出错!" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 余额监控
	 * 短信报告处理
	 */
	public void monitor() {
		if (Constant.getInstance().isJobRunnable() && actualMonitor) {
			try {
				
				List<StatusReport> reports = null;
				reports = client.getReport();
				LOG.info("MONITOR is running, reports: " + reports);
				
				if (null == reports) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("无最新的状态报告");
					}
				} else {
					StringBuilder memo = new StringBuilder();
					Date receiveDate = null;
					List<SmsStatusReport> statusReportList = new ArrayList<SmsStatusReport>();
					for (StatusReport report : reports) {
						memo.setLength(0);
						receiveDate = null;

						memo.append(report.getSeqID() + "|");
						memo.append(report.getReportStatus() + "|");
						memo.append(report.getMobile() + "|");
						memo.append(report.getErrorCode() + "|");
						memo.append(report.getReceiveDate() + "|");
						memo.append(report.getServiceCodeAdd() + "|");
						memo.append(report.getSubmitDate() + "|");
						memo.append(report.getMemo());

						if (null != report.getReceiveDate()) {
							try {
								receiveDate = SDF.parse(report.getReceiveDate());
							} catch (Exception e) {
								receiveDate = new Date();
								LOG.error("格式化接受时间出错!接受时间为" + report.getReceiveDate());
							}
						} else {
							receiveDate = new Date();
						}
						switch (report.getReportStatus()) {
							case 0:
								if (LOG.isDebugEnabled()) {
									LOG.debug("收到成功的发送状态报告,短信的流水号为" + report.getSeqID());
								}
								statusReportList.add(new SmsStatusReport(report.getSeqID(), SmsContentLog.REPORT_FOR_SUCCESS,
										receiveDate, memo.toString()));
								break;
							default:
								if (LOG.isDebugEnabled()) {
									LOG.debug("收到失败的发送状态报告,短信的流水号为" + report.getSeqID());
								}
								statusReportList.add(new SmsStatusReport(report.getSeqID(), SmsContentLog.REPORT_FOR_ERROR,
										receiveDate, memo.toString()));
								break;
						}
					}
					if (!statusReportList.isEmpty()) {
						onReport(statusReportList);
					}
				}

			} catch (Exception e) {
				LOG.warn("获取短信状态报告发生异常,可能导致丢失短信报告.");
			}
		}
	}

	/**
	 * 无状态报告下的重发
	 */
//	@SuppressWarnings("rawtypes")
//	public void retrySend() {
//		if (isRunnable() && actualMonitor) {
//			Map<String, Object> parameters = new HashMap<String, Object>();
//			if (null != getCondition() && getCondition().trim().length() > 0) {
//				parameters.put("condition", getCondition()
//						+ " AND SERIALID IS NOT NULL AND PRIORITY != 9 AND REPORTSTATUS IS NULL AND"
//						+ " actual_send_date> sysdate-1/24*12 AND actual_send_date<sysdate-1/24/60*15");
//			} else {
//				parameters.put("condition", " SERIALID IS NOT NULL AND PRIORITY != 9 AND REPORTSTATUS"
//						+ " IS NULL AND actual_send_date> sysdate-1/24*12 AND actual_send_date<sysdate-1/24/60*15");
//			}
//
//
//			List list = smsRemoteService。querySendedSms(parameters);
//			if (LOG.isDebugEnabled()) {
//				LOG.debug("共需要重新发送" + list.size() + "条消息");
//			}
//			for (Object o : list) {
//				onAutoSend(((SmsContentLog) o).getSerialId());
//			}
//		}
//	}

	//setter and getter
	public SDKClient getClient() {
		return client;
	}

	public void setClient(final SDKClient client) {
		this.client = client;
	}

	public double getMinThreshold() {
		return minThreshold;
	}

	public void setMinThreshold(final double minThreshold) {
		this.minThreshold = minThreshold;
	}

	public void setActualMonitor(final boolean actualMonitor) {
		this.actualMonitor = actualMonitor;
	}

	public void setActualReceive(final boolean actualReceive) {
		this.actualReceive = actualReceive;
	}

	public void setRetrySend(final boolean retrySend) {
		this.retrySend = retrySend;
	}

	public void setReceiveLogicList(List<ReceiveLogic> receiveLogicList) {
		this.receiveLogicList = receiveLogicList;
	}
	
	@Override
	protected String getChannel() {
		return SEND_MSG_CHANNEL.EMAY.toString();
	}

}
