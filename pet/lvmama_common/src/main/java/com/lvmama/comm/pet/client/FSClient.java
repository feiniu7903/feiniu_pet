package com.lvmama.comm.pet.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.lvmama.comm.pet.po.pub.ComFSConfig;
import com.lvmama.comm.pet.po.pub.ComFSFile;
import com.lvmama.comm.pet.po.pub.ComFSServiceConfig;
import com.lvmama.comm.pet.service.pub.ComFSService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;

/**
 * 文件系统客户端接口实现类.
 * @author Libo Wang
 */
public class FSClient  {
	/**
	 * 日志.
	 */
	private static Log logger = LogFactory.getLog(FSClient.class);
	/**
	 * 文件系统Service.
	 */
	private ComFSService comFSRemoteService;
	
	/**
	 * 上传文件.
	 * 
	 * @param localFile
	 *            本地文件.
	 * @param serverType
	 *            服务类型.
	 */
	public Long uploadFile(String fileName, InputStream inputStream, String serverType,String subFolder) {
		Long result = 0L;
		boolean flag = false;
		if (inputStream != null && fileName != null) {
			ComFSServiceConfig comFSServiceConfig = comFSRemoteService
					.selectComFSServiceConfigByServerType(serverType);
			if (comFSServiceConfig != null) {
				FTPClient ftpClient = connectServer(comFSServiceConfig.getFsId());
				if (ftpClient != null) {
					try {
						//String dateStr = DateUtil.formatDate(new Date(), "yyyyMMdd");
						String dateStr = subFolder;
						String distFolder = comFSServiceConfig.getRootDir();
						distFolder = StringUtil.trimFirstAndLastChar(distFolder, '/');
						String[] distDirs = distFolder.split("/");
						for (String dir : distDirs) {
							if (!ftpClient.changeWorkingDirectory(dir)) {
								ftpClient.makeDirectory(dir);
								ftpClient.changeWorkingDirectory(dir);
							}
						}
						if (!ftpClient.changeWorkingDirectory(dateStr)) {
							ftpClient.makeDirectory(dateStr);
							ftpClient.changeWorkingDirectory(dateStr);
						}
						ComFSFile fsFile = new ComFSFile();
						String serverFileName = System.currentTimeMillis() + "-" + fileName;
						fsFile.setFsId(comFSServiceConfig.getFsId());
						fsFile.setFileName(fileName);
						fsFile.setServerFileName(serverFileName);
						fsFile.setFilePath(distFolder + "/" + dateStr);
						fsFile.setInsertedTime(new Date());
						flag = ftpClient.storeFile(new String(serverFileName.getBytes("UTF-8"),
								"ISO-8859-1"), inputStream);
						if (flag) {
							logger.info(serverFileName + " uploads success!");
							result = comFSRemoteService.insertComFSFile(fsFile);
						} else {
							logger.error(serverFileName + " uploads failed!");
						}
						ftpClient.logout();
						ftpClient.disconnect();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else {
				logger.info("Con't find FSServiceConfig serverType is " + serverType);
			}
		} else {
			logger.info("fileName or fileData is null,please double check");
		}
		return result;
	}
	
	/**
	 * 上传文件.
	 * 
	 * @param localFile
	 *            本地文件.
	 * @param serverType
	 *            服务类型.
	 */
	public Long uploadFile(String fileName, InputStream inputStream, String serverType) {
		Long result = 0L;
		boolean flag = false;
		if (inputStream != null && fileName != null) {
			ComFSServiceConfig comFSServiceConfig = comFSRemoteService
					.selectComFSServiceConfigByServerType(serverType);
			if (comFSServiceConfig != null) {
				FTPClient ftpClient = connectServer(comFSServiceConfig.getFsId());
				if (ftpClient != null) {
					try {
						String dateStr = DateUtil.formatDate(new Date(), "yyyyMMdd");
						String distFolder = comFSServiceConfig.getRootDir();
						distFolder = StringUtil.trimFirstAndLastChar(distFolder, '/');
						String[] distDirs = distFolder.split("/");
						for (String dir : distDirs) {
							if (!ftpClient.changeWorkingDirectory(dir)) {
								ftpClient.makeDirectory(dir);
								ftpClient.changeWorkingDirectory(dir);
							}
						}
						if (!ftpClient.changeWorkingDirectory(dateStr)) {
							ftpClient.makeDirectory(dateStr);
							ftpClient.changeWorkingDirectory(dateStr);
						}
						ComFSFile fsFile = new ComFSFile();
						String serverFileName = System.currentTimeMillis() + "-" + fileName;
						fsFile.setFsId(comFSServiceConfig.getFsId());
						fsFile.setFileName(fileName);
						fsFile.setServerFileName(serverFileName);
						fsFile.setFilePath(distFolder + "/" + dateStr);
						fsFile.setInsertedTime(new Date());
						flag = ftpClient.storeFile(new String(serverFileName.getBytes("UTF-8"),
								"ISO-8859-1"), inputStream);
						if (flag) {
							logger.info(serverFileName + " uploads success!");
							result = comFSRemoteService.insertComFSFile(fsFile);
						} else {
							logger.error(serverFileName + " uploads failed!");
						}
						ftpClient.logout();
						ftpClient.disconnect();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else {
				logger.info("Con't find FSServiceConfig serverType is " + serverType);
			}
		} else {
			logger.info("fileName or fileData is null,please double check");
		}
		return result;
	}
	
	/**
	 * 上传文件.
	 * 
	 * @param localFile
	 *            本地文件.
	 * @param serverType
	 *            服务类型.
	 * @throws IOException 
	 */
	public Long uploadFile(String fileName, byte[] fileData, String serverType) throws Exception {
		if (fileData != null && fileName != null) {
			InputStream input = new ByteArrayInputStream(fileData);
			Long fileId = uploadFile(fileName, input, serverType);
			input.close();
			return fileId;
		} else {
			logger.error("fileName or fileData is null,please double check");
			return 0L;
		}
	}
	
	/**
	 * 上传文件.
	 * @param localFile 本地文件.
	 * @param serverType 服务类型.
	 * @throws Exception 
	 */
	public Long uploadFile(File localFile, String serverType) throws Exception {
		Long result = 0L;
		if(localFile.exists()){
			InputStream input = new FileInputStream(localFile);
			Long fileId = uploadFile(localFile.getName(), input, serverType);
			input.close();
			return fileId;
		} else {
			logger.error("Local File " + localFile.getPath() + " does't exist!");
		}
		return result;
	}

	/**
	 * 下载文件.
	 * 
	 * @param fileId
	 *            文件ID.
	 */
	public ComFile downloadFile(Long fileId) {
		ComFile comFile = new ComFile();
		ComFSFile comFSFile = comFSRemoteService.selectComFSFileById(fileId);
		if (comFSFile != null) {
			Long fsId = comFSFile.getFsId();
			comFile.setFileName(comFSFile.getFileName());
			comFile.setFileData(new byte[0]);
			FTPClient ftpClient = connectServer(fsId);
			if (ftpClient != null) {
				try {
					if (ftpClient.changeWorkingDirectory(comFSFile.getFilePath())) {
						FTPFile[] fs = ftpClient.listFiles();
						for (FTPFile ff : fs) {
							if (ff.getName().equals(comFSFile.getServerFileName())) {
								OutputStream os = new ByteArrayOutputStream();
								ftpClient.retrieveFile(new String(ff.getName().getBytes("UTF-8"),
										"ISO-8859-1"), os);
								ByteArrayOutputStream bos = (ByteArrayOutputStream) os;
								comFile.setFileData(bos.toByteArray());
								comFile.setOutputStream(os);
								os.close();
								bos.close();
								ftpClient.logout();
								ftpClient.disconnect();
								logger.info("Download file : fileId " + fileId + " success!");
								return comFile;
							}
						}
					} else {
						logger.error("cd dir " + comFSFile.getFilePath() + " failed!");
					}
				} catch (Exception e) {
					logger.error(e);
				}
			}
		} else {
			logger.error("File does't exist FileId is " + fileId);
		}
		return comFile;
	}

	/**
	 * 下载文件.
	 * 
	 * @param fileName
	 *            文件名.
	 */
	public ComFile downloadFile(final Long fsId,final String dirName,final String filePath,final String fileName){
		ComFile comFile = new ComFile();
		comFile.setFileName(fileName);
		comFile.setFileData(new byte[0]);
		FTPClient ftpClient = connectServer(fsId);
		if (ftpClient != null) {
			try {
				if (ftpClient.changeWorkingDirectory(filePath)) {
					FTPFile[] fs = ftpClient.listFiles();
					for (FTPFile ff : fs) {
						if (ff.getName().equals(fileName)) {
							OutputStream os = new ByteArrayOutputStream();
							ftpClient.retrieveFile(new String(ff.getName()
									.getBytes("UTF-8"), "ISO-8859-1"), os);
							ByteArrayOutputStream bos = (ByteArrayOutputStream) os;
							comFile.setFileData(bos.toByteArray());
							comFile.setOutputStream(os);
							os.close();
							bos.close();
							ftpClient.logout();
							ftpClient.disconnect();
							logger.info("Download file : fileName " + fileName
									+ " success!");
							return comFile;
						}
					}
				} else {
					logger.error("cd dir " + filePath + " failed!");
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return comFile;
	}
	/**
	 * 连接到服务器
	 * @return ftpClient 连接服务器成功，null 连接服务器失败
	 */
	private FTPClient connectServer(Long fsId) {
		int reply;
		FTPClient ftpClient = null;
		ComFSConfig fsConfig = null; 
		try {
			fsConfig = comFSRemoteService.selectFSCofigById(fsId);
			if (fsConfig != null) {
				ftpClient = new FTPClient();
				ftpClient.setConnectTimeout(30000);
				ftpClient.setDefaultPort(fsConfig.getFsPort());				
				ftpClient.configure(getFtpConfig(fsConfig));
				ftpClient.connect(fsConfig.getFsIP());
				ftpClient.login(fsConfig.getFtpUsername(),fsConfig.getFtpPassword());
				reply = ftpClient.getReplyCode();
				ftpClient.setDataTimeout(12000);
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ftpClient.enterLocalPassiveMode();
				ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
				ftpClient.setControlEncoding("UTF-8");
				if (!FTPReply.isPositiveCompletion(reply)) {
					ftpClient.disconnect();
					logger.error(fsConfig.getFsIP() + " FTP rejected the connection!");
					ftpClient = null;
				} else {
					logger.info(fsConfig.getFsIP() + " FTP connects success!");
				}
			} else {
				ftpClient = null;
				logger.error("Con't find FSConfig fsId is " + fsId);
			}
		} catch (SocketException e) {
			ftpClient = null;
			logger.error("FTP upload Exception: " + StringUtil.printParam(fsConfig));
			logger.error(FSClient.class,e);
		} catch (IOException e) {
			ftpClient = null;
			logger.error("FTP upload Exception: " + StringUtil.printParam(fsConfig));
			logger.error(FSClient.class,e);
		}
		return ftpClient;
	}

	/**
	 * 设置FTP客服端的配置--一般可以不设置.
	 * @return ftpConfig
	 */
	private FTPClientConfig getFtpConfig(ComFSConfig fsConfig) {
		FTPClientConfig ftpConfig = new FTPClientConfig(fsConfig.getFtpOSType().toUpperCase());
		if(fsConfig.getFtpDefaultControlEncoding() != null){
			ftpConfig.setServerLanguageCode(fsConfig.getFtpDefaultControlEncoding());
		} else {
			ftpConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING);
		}
		return ftpConfig;
	}

	public ComFSService getComFSRemoteService() {
		return comFSRemoteService;
	}

	public void setComFSRemoteService(ComFSService comFSRemoteService) {
		this.comFSRemoteService = comFSRemoteService;
	}
}
