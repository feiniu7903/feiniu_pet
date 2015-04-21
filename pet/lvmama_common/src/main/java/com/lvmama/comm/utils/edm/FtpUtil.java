package com.lvmama.comm.utils.edm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.Properties;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

/**
* Created by IntelliJ IDEA.
*
* @author leizhimin 2008-9-12 10:32:39
*/
public class FtpUtil {
     private static Logger logger = Logger.getLogger(FtpUtil.class);

        private static String userName;              //FTP 登录用户名
        private static String password;              //FTP 登录密码
        private static String ip;                    //FTP 服务器地址IP地址
        private static int port;                          //FTP 端口
        private static String distFolder;               //上传对应的文件夹
        private static Properties property = null;  //属性集
        private String configFile = "/edm_config.properties";    //配置文件的路径名
        private static FTPClient ftpClient = null; //FTP 客户端代理
        //FTP状态码
        public static int i = 1;

        /**
         * 上传单个文件，并重命名
         *
         * @param localFile--本地文件路径
         * @param distFolder--新的文件名,可以命名为空""
         * @return true 上传成功，false 上传失败
         */
        public String uploadFile(final String localFileName) {
                 logger.debug("开始上传EDM文件到服务器\r\n");
                boolean flag = true;
                String fileName=null;
                try {     
                          File localFile = new File(localFileName);
                        connectServer();
                        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                        ftpClient.enterLocalPassiveMode();
                        ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
                        InputStream input = new FileInputStream(localFile);
                        if (input == null) {
                             logger.debug("本地文件"+localFile.getPath()+"不存在!");
                        }
                        String dateStr = com.lvmama.comm.utils.DateUtil.getFormatDate(new java.util.Date(), "yyyyMMdd");
                        ftpClient.changeWorkingDirectory(distFolder);
                        if (!ftpClient.changeWorkingDirectory(dateStr)) {  
                             ftpClient.makeDirectory(dateStr);  
                             ftpClient.changeWorkingDirectory(dateStr);  
                        }  
                        flag = ftpClient.storeFile(localFile.getName(), input);
                        if (flag) {
                             fileName = distFolder+dateStr+"/"+localFile.getName();
                             logger.debug("上传文件成功！");
                        } else {
                             logger.debug("上传文件失败！");
                        }
                        input.close();
                } catch (IOException e) {
                        logger.debug("本地文件上传失败！IO ", e);
                } catch (Exception e) {
                          logger.debug("本地文件上传失败！", e);
                }
                closeConnect();// 关闭连接
                return fileName;
        }
        /**
         * 关闭连接
         */
        public void closeConnect() {
                try {
                        if (ftpClient != null) {
                                ftpClient.logout();
                                ftpClient.disconnect();
                        }
                } catch (Exception e) {
                	 logger.warn("关闭连接 "+e.getMessage().substring(100));
                }
        }

        /**
         * 设置配置文件
         *
         * @param configFile
         */
        public void setConfigFile(String configFile) {
                this.configFile = configFile;
        }

        /**
         * 设置传输文件的类型[文本文件或者二进制文件]
         *
         * @param fileType--BINARY_FILE_TYPE、ASCII_FILE_TYPE
         *
         */
        public void setFileType(int fileType) {
                try {
                        connectServer();
                        ftpClient.setFileType(fileType);
                } catch (Exception e) {
                	 logger.warn("传输文件出错 "+e.getMessage().substring(100));
                }
        }

        /**
         * 扩展使用
         *
         * @return ftpClient
         */
        protected FTPClient getFtpClient() {
                connectServer();
                return ftpClient;
        }

        /**
         * 设置参数
         *
         * @param configFile --参数的配置文件
         */
        private void setArg(String configFile) {
                property = new Properties();
                try {
                          property = new Properties();
                          property.load(FtpUtil.class.getResourceAsStream(configFile));
                        userName = property.getProperty("uploadusername");
                        password = property.getProperty("uploaduserpassword");
                        ip = property.getProperty("uploadIp");
                        port = Integer.parseInt(property.getProperty("port"));
                        distFolder =  property.getProperty("edmFtpRootDir");
                } catch (FileNotFoundException e1) {
                     logger.debug("配置文件 " + configFile + " 不存在！");
                } catch (IOException e) {
                     logger.debug("配置文件 " + configFile + " 无法读取！");
                }
        }

        /**
         * 连接到服务器
         *
         * @return true 连接服务器成功，false 连接服务器失败
         */
        public boolean connectServer() {
                boolean flag = true;
                        int reply;
                        try {
                                setArg(configFile);
                                ftpClient = getClient();
                                FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
                                conf.setServerLanguageCode("zh");
                                ftpClient.setControlEncoding("UTF-8");
                                ftpClient.setDefaultPort(port);
                                ftpClient.configure(getFtpConfig());
                                ftpClient.connect(ip);
                                ftpClient.login(userName, password);
                                ftpClient.setDefaultPort(port);
                                reply = ftpClient.getReplyCode();
                                ftpClient.setDataTimeout(12000);
                                if (!FTPReply.isPositiveCompletion(reply)) {
                                        ftpClient.disconnect();
                                         logger.debug("FTP 服务拒绝连接！");
                                        flag = false;
                                }
                                i++;
                        } catch (SocketException e) {
                                flag = false;
                                logger.debug("登录ftp服务器 " + ip + " 失败,连接超时！");
                        } catch (IOException e) {
                                flag = false;
                                logger.debug("登录ftp服务器 " + ip + " 失败，FTP服务器无法打开！");
                        }
                return flag;
        }

        /**
         * 进入到服务器的某个目录下
         *
         * @param directory
         */
        public void changeWorkingDirectory(String directory) {
                try {
                        connectServer();
                        ftpClient.changeWorkingDirectory(directory);
                } catch (IOException ioe) {
                       logger.warn("进入到服务器的目录出现IOException 错误"+ioe.getMessage().substring(100));
                }
        }

        /**
         * 设置FTP客服端的配置--一般可以不设置
         *
         * @return ftpConfig
         */
        private FTPClientConfig getFtpConfig() {
                FTPClientConfig ftpConfig = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
                ftpConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING);
                return ftpConfig;
        }


        /**
         * 在服务器上创建一个文件夹
         *
         * @param dir 文件夹名称，不能含有特殊字符，如 \ 、/ 、: 、* 、?、 "、 <、>...
         */
        public boolean makeDirectory(String dir) {
                connectServer();
                boolean flag = true;
                try {
                        // System.out.println("dir=======" dir);
                        flag = ftpClient.makeDirectory(dir);
                        if (flag) {
                             logger.debug("make Directory " + dir + " succeed");

                        } else {

                             logger.debug("make Directory " + dir + " false");
                        }
                } catch (Exception e) {
                     logger.debug("在服务器上创建文件夹失败!",e);
                }
                return flag;
        }
        private synchronized static final FTPClient getClient(){
                if(null == ftpClient){
                        return new FTPClient();
                }
                return ftpClient;
        }
}