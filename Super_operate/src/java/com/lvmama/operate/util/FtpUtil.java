package com.lvmama.operate.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.operate.mail.util.EdmFtpUtil;
import com.lvmama.operate.mail.util.FTPUtil;
import com.lvmama.operate.mail.util.HanQiResources;

/**
 * Created by IntelliJ IDEA.
 * 
 * @author leizhimin 2008-9-12 10:32:39
 */
public class FtpUtil {
	private static Logger logger = Logger.getLogger(FtpUtil.class);

	/**
	 * 上传单个文件，并重命名
	 * 
	 * @param localFile
	 *            --本地文件路径
	 * @param distFolder
	 *            --新的文件名,可以命名为空""
	 * @return true 上传成功，false 上传失败
	 */
	public String uploadFile(final String localFileName) {
		logger.info("开始上传EDM文件到服务器");
		String fileName = null;
		try {
			File localFile = new File(localFileName);
			if (!localFile.exists()) {
				logger.info("本地文件" + localFile.getPath() + "不存在!");
			}
			InputStream input = new FileInputStream(localFile);
			String dateStr = DateUtil.getFormatDate(new java.util.Date(),
					"yyyyMMdd");
			String path = HanQiResources.get("edmFtpRootDir") + dateStr;
			boolean flag = FTPUtil.uploadFile(EdmFtpUtil.getFtpConf(), path,
					localFile.getName(), input);
			if (flag) {
				fileName = path + "/" + localFile.getName();
				logger.info("上传文件成功！");
			} else {
				logger.info("上传文件失败！");
			}
		} catch (IOException e) {
			logger.info("本地文件上传失败！IO ", e);
		} catch (Exception e) {
			logger.info("本地文件上传失败！", e);
		}
		return fileName;
	}

	public byte[] readFile(String filepath) throws Exception {
		return FTPUtil.getFileData(EdmFtpUtil.getFtpConf(), filepath);
	}
	
	public List<String> getFileAllLine(String filepath) throws Exception {
		return FTPUtil.getFileAllLine(EdmFtpUtil.getFtpConf(), filepath);
	}

	public static void main(String[] args) {
		//System.out.println(new FtpUtil().uploadFile("E:/logs/lyapp.log"));
		
		try {
			p("/birthday/index.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void p(String ftpname) throws Exception {
		byte[] bs = new FtpUtil().readFile(ftpname);
		if (bs != null) {
			System.out.println(new String(bs));
		} else {
			System.out.println("文件不存在");
		}
	}
}