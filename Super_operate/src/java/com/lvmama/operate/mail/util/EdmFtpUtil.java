package com.lvmama.operate.mail.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.lvmama.operate.mail.util.FTPUtil.FtpConf;

public class EdmFtpUtil {
	private static Logger logger = Logger.getLogger(EdmFtpUtil.class);
	private static FtpConf conf = null;

	public static FtpConf getFtpConf(){
		if(conf==null){
			synchronized(EdmFtpUtil.class){
				if(conf==null){
					Properties ps = new Properties();
					try {
						conf = new FtpConf();
						ps.load(Thread.currentThread().getContextClassLoader()
								.getResourceAsStream("edm_config.properties"));
						conf.setUrl(ps.getProperty("uploadIp"));
						conf.setPort(Integer.parseInt(ps.getProperty("port")));
						conf.setUsername(ps.getProperty("uploadusername"));
						conf.setPassword(ps.getProperty("uploaduserpassword"));
					} catch (IOException e) {
						logger.error("emd读取ftp配置文件出错");
						logger.error(e.getCause(),e);
					}
					
				}
			}
		}
		return conf;
	}
}
