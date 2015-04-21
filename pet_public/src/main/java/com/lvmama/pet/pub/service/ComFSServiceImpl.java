package com.lvmama.pet.pub.service;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import com.lvmama.comm.pet.po.pub.ComFSConfig;
import com.lvmama.comm.pet.po.pub.ComFSFile;
import com.lvmama.comm.pet.po.pub.ComFSServiceConfig;
import com.lvmama.comm.pet.service.pub.ComFSService;
import com.lvmama.pet.pub.dao.ComFSDAO;

/**
 * 文件系统客户端接口实现类.
 * 
 * @author Libo Wang
 */
public class ComFSServiceImpl implements ComFSService {
	/**
	 * 日志.
	 */
	private static Log logger = LogFactory.getLog(ComFSServiceImpl.class);
	/**
	 * 文件系统DAO.
	 */
	private ComFSDAO comFSDAO;
	/**
	 * FTP 客户端代理.
	 */
	private static FTPClient ftpClient = null;

	

	/**
	 * 初始化ftpClient.
	 * 
	 * @return FTPClient.
	 */
	private synchronized static final FTPClient getClient() {
		if (null == ftpClient) {
			FTPClient ftp = new FTPClient();
			ftp.setConnectTimeout(3000);
			return ftp;
		}
		return ftpClient;
	}

	/**
	 * 连接到服务器
	 * 
	 * @return true 连接服务器成功，false 连接服务器失败
	 */
	public boolean connectServer(Long fsId) {
		boolean flag = true;
		int reply;
		try {
			ComFSConfig fsConfig = comFSDAO.selectFSCofigById(fsId);
			if (fsConfig != null) {
				ftpClient = getClient();
				ftpClient.setDefaultPort(fsConfig.getFsPort());
				ftpClient.configure(getFtpConfig(fsConfig));
				ftpClient.connect(fsConfig.getFsIP());
				ftpClient.login(fsConfig.getFtpUsername(), fsConfig.getFtpPassword());
				reply = ftpClient.getReplyCode();
				ftpClient.setDataTimeout(12000);
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ftpClient.enterLocalPassiveMode();
				ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
				ftpClient.setControlEncoding("UTF-8");
				if (!FTPReply.isPositiveCompletion(reply)) {
					ftpClient.disconnect();
					logger.info(fsConfig.getFsIP() + " FTP rejected the connection!");
					flag = false;
				} else {
					logger.info(fsConfig.getFsIP() + " FTP connects success!");
				}
			} else {
				logger.info("Con't find FSConfig fsId is " + fsId);
			}
		} catch (SocketException e) {
			flag = false;
			e.printStackTrace();
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 设置FTP客服端的配置--一般可以不设置.
	 * 
	 * @return ftpConfig
	 */
	private FTPClientConfig getFtpConfig(ComFSConfig fsConfig) {
		FTPClientConfig ftpConfig = new FTPClientConfig(fsConfig.getFtpOSType().toUpperCase());
		if (fsConfig.getFtpDefaultControlEncoding() != null) {
			ftpConfig.setServerLanguageCode(fsConfig.getFtpDefaultControlEncoding());
		} else {
			ftpConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING);
		}
		return ftpConfig;
	}

	public ComFSDAO getComFSDAO() {
		return comFSDAO;
	}

	public void setComFSDAO(ComFSDAO comFSDAO) {
		this.comFSDAO = comFSDAO;
	}
	
	public ComFSServiceConfig selectComFSServiceConfigByServerType(String serverType) {
		return comFSDAO.selectComFSServiceConfigByServerType(serverType);
	}
	/**
	 * 根据ID查询文件系统配置.
	 * @param id ID.
	 * @return ComFSConfig.
	 */
	public ComFSConfig selectFSCofigById(Long id) {
		return comFSDAO.selectFSCofigById(id);
	}
	/**
	 * 根据ID查询文件系统文件.
	 * @param id ID.
	 * @return ComFSFile.
	 */
	public ComFSFile selectComFSFileById(Long id) {
		return comFSDAO.selectComFSFileById(id);
	}
	/**
	 * 插入文件系统文件.
	 * @param ComFSFile ComFSFile.
	 * @return Long.
	 */
	public Long insertComFSFile(ComFSFile ComFSFile) {
		return comFSDAO.insertComFSFile(ComFSFile);
	}

}
