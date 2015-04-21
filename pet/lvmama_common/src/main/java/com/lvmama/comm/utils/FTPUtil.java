package com.lvmama.comm.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

/**
 * ftp工具类
 * 
 * @author likun
 * @date 2013/12/2
 */
public class FTPUtil {
	// 编码
	public static final String EN_CODEING = "UTF-8";
	// 登录超时时间
	public static final int TIME_OUT = 10000;

	private static Logger logger = Logger.getLogger(FTPUtil.class);

	/**
	 * Ftp配置信息,包含url，端口，用户名以及登录密码
	 * 
	 * @author likun
	 * 
	 */
	public static class FtpConf {

		private String url;
		private int port;
		private String username;
		private String password;
		
		
		public FtpConf(String url, int port, String username, String password) {
			this.url = url;
			this.port = port;
			this.username = username;
			this.password = password;
		}

		public FtpConf(){
			
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

	public static FTPClient getFtpClient(FtpConf conf) throws Exception {
		int reply;
		FTPClient ftpClient = null;
		ftpClient = new FTPClient();
		// 此处设置是否开启协议命令监听器，可以看到socket发送到ftp的命令
		/*ftpClient.addProtocolCommandListener(new PrintCommandListener(
				new PrintWriter(System.out)));*/
		ftpClient.setConnectTimeout(30000);
		ftpClient.setDefaultPort(conf.getPort());
		ftpClient.connect(conf.getUrl());
		ftpClient.login(conf.getUsername(), conf.getPassword());
		reply = ftpClient.getReplyCode();
		ftpClient.setDataTimeout(TIME_OUT);
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
		ftpClient.setControlEncoding(EN_CODEING);
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftpClient.disconnect();
			ftpClient = null;
			logger.info("FTP connects fail!");
		} else {
			logger.info("FTP connects success!");
		}
		return ftpClient;
	}

	/**
	 * 公共加载资源文件来获取一个FtpConf对象,
	 * 
	 * @param ftpProperties
	 * @return
	 * @throws IOException
	 */
	public static FtpConf getFtpConf(String ftpProperties) throws IOException {
		FtpConf conf = new FtpConf();
		Properties ps = new Properties();
		ps.load(Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(ftpProperties));
		conf.setUrl(ps.getProperty("ftu_url"));
		conf.setPort(Integer.valueOf(ps.getProperty("ftp_port")));
		conf.setUsername(ps.getProperty("ftp_username"));
		conf.setPassword(ps.getProperty("ftp_password"));
		return conf;
	}

	/**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @param ftpConf
	 *            FTP服务器配置信息
	 * @param path
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @param input
	 *            输入流
	 * @return 成功返回true，否则返回false
	 * @throws Exception
	 */
	public static boolean uploadFile(FtpConf ftpConf, String path,
			String filename, InputStream input) throws Exception {
		boolean success = false;
		FTPClient ftp = null;
		try {
			ftp = getFtpClient(ftpConf);
			if (!ftp.changeWorkingDirectory(path)) {
				ftp.makeDirectory(path);
				ftp.changeWorkingDirectory(path);
			}
			success = ftp.storeFile(filename, input);
			input.close();
			ftp.logout();
		} finally {
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					logger.error(ioe.getMessage(), ioe);
				}
			}
		}
		return success;
	}

	/**
	 * Description: 从FTP服务器下载文件
	 * 
	 * @param ftpConf
	 *            FTP服务器配置信息
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 * @throws Exception
	 */
	public static boolean downFile(FtpConf ftpConf, String remotePath,
			String fileName, String localPath) throws Exception {
		boolean success = false;
		FTPClient ftp = null;
		try {
			ftp = getFtpClient(ftpConf);
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			boolean fileexists = false;
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					File localPathFile = new File(localPath);
					if(!localPathFile.exists()){
						localPathFile.mkdirs();
					}
					File localFile = new File(localPath + "/" + ff.getName());

					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
					fileexists=true;
				}
			}
			ftp.logout();
			if(fileexists){
				success = true;
			}
		} finally {
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					logger.error(ioe.getMessage(), ioe);
				}
			}
		}
		return success;
	}

	/**
	 * Description: 从FTP服务器下载文件的内容
	 * 
	 * @param ftpConf
	 *            FTP服务器配置信息
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @return
	 * @throws Exception
	 */
	public static byte[] getFileData(FtpConf ftpConf, String remotePath)
			throws Exception {
		remotePath = remotePath.trim();
		logger.info("获取:" + remotePath + "文件的内容");
		byte bs[] = null;
		FTPClient ftp = null;
		try {
			ftp = getFtpClient(ftpConf);
			InputStream stream = ftp.retrieveFileStream(remotePath);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			byte[] temp = new byte[4048];
			int i = 0;
			if (stream != null) {
				while ((i = stream.read(temp)) != -1) {
					os.write(temp, 0, i);
				}
				bs = os.toByteArray();
			} else {
				return null;
			}
			ftp.logout();
		} finally {
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					logger.error(ioe.getMessage(), ioe);
				}
			}
		}
		return bs;
	}

	/**
	 * Description: 从FTP服务器下载文件的内容
	 * 
	 * @param ftpConf
	 *            FTP服务器配置信息
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @return
	 * @throws Exception
	 */
	public static List<String> getFileAllLine(FtpConf ftpConf, String remotePath)
			throws Exception {
		List<String> list = null;
		remotePath = remotePath.trim();
		logger.info("获取:" + remotePath + "文件的内容");
		FTPClient ftp = null;
		try {
			ftp = getFtpClient(ftpConf);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					ftp.retrieveFileStream(remotePath)));
			String s = null;
			list = new ArrayList<String>();
			while ((s = reader.readLine()) != null) {
				list.add(s);
			}
			ftp.logout();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					logger.error(ioe.getMessage(), ioe);
				}
			}
		}
		return list;
	}

	/**
	 * 获取ftp某个目录下面的所有文件
	 * 
	 * @param ftpConf
	 * @param remotePathDir
	 * @return
	 */
	public static List<String> getListFiles(FtpConf ftpConf,
			String remotePathDir) throws Exception {
		List<String> list = null;
		remotePathDir = remotePathDir.trim();
		logger.info("获取:" + remotePathDir + "文件的内容");
		FTPClient ftp = null;
		try {
			ftp = getFtpClient(ftpConf);
			ftp.changeWorkingDirectory(remotePathDir);
			String fileNames[] = ftp.listNames();
			if (fileNames != null) {
				list = new ArrayList<String>();
				for (String string : fileNames) {
					list.add(string);
				}
			}
			ftp.logout();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					logger.error(ioe.getMessage(), ioe);
				}
			}
		}
		return list;
	}

	/**
	 * 删除ftp上的文件
	 * 
	 * @param srcFname
	 * @return
	 */
	public static boolean removeFile(FtpConf ftpConf, String fileNmae) throws Exception {
		boolean flag = false;
		FTPClient ftp = null;
		try {
			ftp = getFtpClient(ftpConf);
			flag = ftp.deleteFile(fileNmae);
			ftp.logout();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					logger.error(ioe.getMessage(), ioe);
				}
			}
		}
		return flag;
	}

	public static void main(String[] args) throws Exception {
		FtpConf ftpConf = new FtpConf();
		/*ftpConf.setUrl("175.102.15.131");
		ftpConf.setPort(21);
		ftpConf.setUsername("lvmamaly");
		ftpConf.setPassword("HJ@fdfdf433@dfd");*/
		/*ftpConf.setUrl("114.80.217.180");
		ftpConf.setPort(21);
		ftpConf.setUsername("virftp_EDM");
		ftpConf.setPassword("virftp_!QAZ@WSX");*/
//		System.out.println(FTPUtil.getListFiles(ftpConf, "/report"));
//		System.out.println(FTPUtil.removeFile(ftpConf, "/cs/a.txt"));
		System.out.println(FTPUtil.downFile(ftpConf,"/report","lvmamaly2014011623Response.zip","D:/ddc"));;
		// testUF();
		// System.out.println(FTPUtil.getFileAllLine(EdmFtpUtil.getFtpConf(),
		// "/home/lv/group_631_6890.txt").size());
	}

}
