package com.lvmama.pet.job.quartz.sms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.sf.json.JSONSerializer;

import com.lvmama.comm.pet.po.sms.SmsContent;
import com.lvmama.comm.pet.po.sms.SmsContentLog;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.FTPUtil;
import com.lvmama.comm.utils.FTPUtil.FtpConf;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant.SEND_MSG_CHANNEL;
import com.lvmama.comm.vo.SmsStatusReport;
import com.lvmama.pet.job.util.TestResources;

/**
 * 点点客短信JOB
 * 
 * @author zenglei
 * 
 */
public class DoDoCaSendJob extends AbstractSendJob {
	/**
	 * 点点客　短信发送实现类
	 */
	private DoDoCaClient client;
	/**
	 * 点点客短信日期格式化器
	 */
	private static final SimpleDateFormat SDF = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	/**
	 * 点点客　状态报告成功值
	 */
	private static final String REPORT_DELIVRD = "DELIVRD";
	/**
	 * 点点客　状态报告　转码类型
	 */
	private static final String ENCODE_TYPE = "GBK";

	/**
	 * 点点客人状态报告所在FTP
	 */
	private FtpConf statusReportFtpConf;
	/**
	 * 点点客人状态报告所在FTP目录
	 */
	private String statusReportDir;

	private String statusReportFilePre;
	/**
	 * 点点客　状态报告长度
	 */
	private static final int REPORT_LENGTH = 5;
	
	@Override
	protected int actualSend(SmsContent smsContent) {
		int temp = send(smsContent);
		if (null != this.serialId) {
			smsContent.setSerialId(Long.valueOf(this.serialId));
		} else {
			return ERROR;
		}
		return temp;
	}
	@Override
	protected int send(SmsContent smsContent) {
		if (null == smsContent || null == smsContent.getMobile()) {
			LOG.warn("joke?Try to send mms to null mobile!");
			return IGNORE;
		}
		if (TestResources.getInstance().getSmsJobIsTest()
				&& !TestResources.getInstance().getTestMobiles()
						.contains(smsContent.getMobile())) {
			LOG.info("[点点客]短信测试发送,若要正式发送,请修改test.properties配置文件!");
			this.serialId = "10000";
			return SUCCESS;
		}
		String originalContent = smsContent.getContent();
		preDisposeSmsContent(smsContent);
		String rtn = "";
		if (isActualSend()) {
			rtn = client.sendSMS(smsContent);
			smsContent.setContent(originalContent);
		}
		if (null == rtn) {
			LOG.error("failure( rtn is null )!content \""
					+ smsContent.getContent() + "\"to" + smsContent.getMobile());
			return ERROR;
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("send mobiles:" + smsContent.getMobile() + ", content:"
					+ smsContent.getContent() + ", return value:" + rtn);
		}
		/**
		 * 点点客返回数据格式 成功:20110725160412,0 1234567890100
		 * 
		 * 失败:20110725160412,101
		 */
		String[] temps = rtn.split("\n");

		String[] status = temps[0].split(SEPARATOR);

		int rtStatus = status.length > 1 ? Integer.valueOf(status[1]) : null;

		switch (rtStatus) {
		case 0:
			if (temps.length <= 1) {
				LOG.error("failure(" + rtn + ")!content \""
						+ smsContent.getContent() + "\"to"
						+ smsContent.getMobile());
				return ERROR;
			}
			this.serialId = temps[1];
			if (LOG.isDebugEnabled()) {
				LOG.debug("send successfully(" + rtn + ")!content \""
						+ smsContent.getContent() + "\"to"
						+ smsContent.getMobile());
			}
			LOG.info(" dodoca send: " + rtn);
			return SUCCESS;
		default:
			LOG.error("failure(" + rtn + ")!content \""
					+ smsContent.getContent() + "\"to" + smsContent.getMobile());
			return ERROR;
		}
	}
	
	private void preDisposeSmsContent(SmsContent content){
		if(content!=null && StringUtil.isNotEmptyString(content.getContent())&&content.getContent().contains(QIANMING)){
			content.setContent(content.getContent().substring(0,content.getContent().lastIndexOf(QIANMING)));
		}
	}

	/**
	 * 转换dodoca　状态报告
	 * 
	 * @param reportString
	 *            msgid|reporttime|mobile|status|content
	 *            001|20130131132400|13800210021
	 *            |DELIVRD|%B6%CC%D0%C5%C4%DA%C8%DD1
	 * @return
	 */
	private SmsStatusReport returnReport(String reportString) throws Exception {
		if (null == reportString) {
			LOG.debug(" Dodoca reportString is null!");
			return null;
		}
		reportString = URLDecoder.decode(reportString, ENCODE_TYPE);

		String[] reportContents = reportString.split("\\|");

		if (reportContents.length != REPORT_LENGTH) {
			LOG.debug(" Dodoca reportString error ! reString : " + reportString);
			return null;
		} else {

			// 状态报告
			if (REPORT_DELIVRD.equals(reportContents[3])) {
				return new SmsStatusReport(Long.valueOf(reportContents[0]),
						SmsContentLog.REPORT_FOR_SUCCESS,
						SDF.parse(reportContents[1]), reportContents[4]);
			} else {

				if (LOG.isDebugEnabled()) {
					LOG.debug("收到失败的发送状态报告,短信的流水号为" + reportContents[0]
							+ "  reportString : " + reportString);
				}
				return new SmsStatusReport(Long.valueOf(reportContents[0]),
						SmsContentLog.REPORT_FOR_ERROR,
						SDF.parse(reportContents[1]), reportContents[4]);
			}
		}
	}

	public void setClient(DoDoCaClient client) {
		this.client = client;
	}

	/**
	 * 处理状态报告
	 * 
	 * @throws Exception
	 */
	public void disposeStatusReport() {
		try {
			LOG.info("点点客状态报告Start");
			// 下载zip文件存放目录
			String zipDownLoadPath = System.getProperty("java.io.tmpdir")
					+ "DODOCA" + statusReportDir;
			// 解压zip文件之后存放目录
			String outputPath = System.getProperty("java.io.tmpdir")
					+ "DODOCA/unzip/"; // 输出路径（文件夹目录）
			String fileName = String.format("%s%sResponse.zip",
					statusReportFilePre, DateUtil.formatDate(Calendar
							.getInstance().getTime(), "yyyyMMddHH"));
			LOG.info("点点客状态文件处理:" + fileName);
			if (StringUtil.isNotEmptyString(fileName)
					&& fileName.toLowerCase().endsWith("response.zip")) {
				// 下载文件
				String zipFilePath = statusReportDir + "/" + fileName;
				boolean flag = FTPUtil.downFile(statusReportFtpConf,
						statusReportDir, fileName, zipDownLoadPath);
				if (flag) {
					File opFile = new File(
							outputPath
									+ DateUtil.formatDate(Calendar
											.getInstance().getTime(),
											DateUtil.PATTERN_yyyyMMddHHmmss));
					if (!opFile.exists()) {
						opFile.mkdir();
					}
					// 解压文件
					this.unZip(new File(zipDownLoadPath, fileName), opFile);
					// 处理文件
					File[] fs = opFile.listFiles();
					if (fs != null) {
						for (File file : fs) {
							disposeFile(file);
						}
					}
				} else {
					LOG.error("点点客下载文件失败:" + zipFilePath);
				}
			}
			LOG.info("点点客状态报告End");
		} catch (Exception e) {
			LOG.error("修改点点客状态出错:" + e.getMessage(), e);
		}
	}

	/**
	 * 处理点点客状态报告文件
	 * 
	 * @param file
	 * @throws Exception
	 */
	public void disposeFile(File file) throws Exception {
		if (file != null) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(file)));
				String reportString = null;
				while ((reportString = reader.readLine()) != null) {
					SmsStatusReport report = returnReport(reportString);
					List<SmsStatusReport> list = new ArrayList<SmsStatusReport>();
					list.add(report);
					this.onReport(list);
					LOG.info(JSONSerializer.toJSON(report));
				}
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
		}
	}

	/**
	 * 解压点点客状态文件
	 * 
	 * @param zipFile
	 *            状态文件
	 * @param opFile
	 *            解压以后存放目录
	 * @throws Exception
	 */
	public void unZip(File zipFile, File opFile) throws Exception {
		ZipInputStream zin = null;
		BufferedInputStream bin = null;
		try {
			if (!opFile.exists()) {
				opFile.mkdirs();
			}
			zin = new ZipInputStream(new FileInputStream(zipFile));// 输入源zip路径
			bin = new BufferedInputStream(zin);
			File fout = null;
			ZipEntry entry;
			while ((entry = zin.getNextEntry()) != null) {
				fout = new File(opFile, entry.getName());
				if (!fout.exists()) {
					(new File(fout.getParent())).mkdirs();
				}
				BufferedOutputStream bout = null;
				try {
					bout = new BufferedOutputStream(new FileOutputStream(fout));
					int b;
					while ((b = bin.read()) != -1) {
						bout.write(b);
					}
				} finally {
					if (bout != null) {
						bout.close();
					}
				}
			}

		} finally {
			if (bin != null) {
				bin.close();
			}
			if (zin != null) {
				zin.close();
			}
		}
	}

	public FtpConf getStatusReportFtpConf() {
		return statusReportFtpConf;
	}

	public void setStatusReportFtpConf(FtpConf statusReportFtpConf) {
		this.statusReportFtpConf = statusReportFtpConf;
	}

	public String getStatusReportDir() {
		return statusReportDir;
	}

	public void setStatusReportDir(String statusReportDir) {
		this.statusReportDir = statusReportDir;
	}

	public String getStatusReportFilePre() {
		return statusReportFilePre;
	}

	public void setStatusReportFilePre(String statusReportFilePre) {
		this.statusReportFilePre = statusReportFilePre;
	}

	public static void main(String[] args) throws Exception {
		DoDoCaSendJob job = new DoDoCaSendJob();
		SmsContent content = new SmsContent("尊敬的驴妈妈用户，[cswts@lvmama.com]，若您还有其他问题需咨询，请致电10106060转4转${employeeNum}【驴妈妈】    ", "15800926537");
		job.preDisposeSmsContent(content);
		System.out.println(content.getContent());
		/*FtpConf ftpConf = new FtpConf();
		ftpConf.setUrl("114.80.217.180");
		ftpConf.setPort(21);
		ftpConf.setUsername("virftp_EDM");
		ftpConf.setPassword("virftp_!QAZ@WSX");
		job.setStatusReportFtpConf(ftpConf);
		job.setStatusReportDir("/cs");
		job.setStatusReportFilePre("lvmamaly");
		job.disposeStatusReport();*/
		// System.out.println(String.format("%s%sResponse.zip","lvmamaly",
		// DateUtil.formatDate(Calendar.getInstance().getTime(),
		// "yyyyMMddHH")));
		/*
		 * try { DoDoCaSendJob job = new DoDoCaSendJob(); SmsStatusReport sms =
		 * job .returnReport(
		 * "001|20130131132400|13800210021|DELIVRD|%B6%CC%D0%C5%C4%DA%C8%DD1");
		 * System.out.println("sms:" + sms.getSerialId() + "======" +
		 * sms.getDate() + "====" + sms.getResult() + "===" + sms.getMemo()); }
		 * catch (Exception e) { e.printStackTrace(); }
		 */
	}

	@Override
	protected String getChannel() {
		return SEND_MSG_CHANNEL.DODOCA.toString();
	}

}