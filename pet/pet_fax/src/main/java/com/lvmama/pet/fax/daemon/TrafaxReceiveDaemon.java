package com.lvmama.pet.fax.daemon;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lvmama.pet.fax.dao.EastfaxStatusDao;
import com.lvmama.pet.fax.dao.TrafaxStatusDao;
import com.lvmama.pet.fax.utils.Constant;
import com.lvmama.pet.fax.utils.FileUtil;
import com.lvmama.pet.fax.utils.UploadUtil;

public class TrafaxReceiveDaemon extends Thread {
	private static final Log log = LogFactory.getLog(TrafaxReceiveDaemon.class);
	private TrafaxStatusDao trafaxStatusDao;
		
	@Override
	public void run() {
		while (true) {
			try{
				processFaxRecv();
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				Thread.sleep(5000);
			}catch(Exception ex){}
		}
	}
	
	/**
	 * 删除已经通知的文件
	 */
	public void deleteNotifiedFile(){
		
	}
	
	public void processFaxRecv(){
		log.info("RECV FAX loop");
		HttpClient client = new HttpClient();
		//statusDao.updateNullCallerId();
		List<Map<String,Object>> list = trafaxStatusDao.queryAllUnnotified();
		for(Map<String,Object> map : list){
			String pageNum = null;
			Date recvTime = (Date)map.get("FaxRecvDateTime");
			File file = null;
			String filename = map.get("FaxID").toString();
			file = new File(Constant.getRecvDirectory()+"/"+filename);
			String fileURL = null; 
			if (file.exists()){
				fileURL = UploadUtil.uploadFile(file, file.getName());
				if(filename != null && (filename.toLowerCase().endsWith("tif")  || filename.toLowerCase().endsWith("tiff"))){
					pageNum = UploadUtil.getPageNumByTiff(file);
				}
			}else{
				log.error("file not exists: " + file.getAbsolutePath());
			}
			if(fileURL!=null){
				try{
					String callerId="";
					String barcode="";
					if (map.get("FaxCallerID")!=null) {
						callerId =map.get("FaxCallerID").toString().trim();
					}
					if (map.get("barcode")!=null) {
						barcode =map.get("barcode").toString().trim();
					}
					
					String url = "";
					if (trafaxStatusDao.isTdbiFaxOutByBarCode(barcode)){
						url = Constant.getVstFaxrecvUrl()+"?recvtime="+recvTime.getTime()+"&callerid="+callerId+"&barcode=" +barcode+"&url="+URLEncoder.encode(fileURL, "UTF-8")+"&pageNum="+pageNum;
					}else{
						url = Constant.getSuperFaxrecvUrl()+"?recvtime="+recvTime.getTime()+"&callerid="+callerId+"&barcode=" +barcode+"&url="+URLEncoder.encode(fileURL, "UTF-8")+"&pageNum="+pageNum;
					}
					
					log.info("RECV NOTIFY URL:" + url);
					GetMethod get = new GetMethod(url);
					int code = client.executeMethod(get);
		        	String str = get.getResponseBodyAsString();
		        	if (code==200 && str.startsWith("success")) {
		        		trafaxStatusDao.updateFaxRecvNotified(Long.parseLong(map.get("SID").toString()));
		        		log.info("添加传真回传成功: code=" + code + " str: " + str);
		            }else{
		            	log.error("添加传真回传失败: code=" + code + " str: " + str);
		            }
		        	get.releaseConnection();
					FileUtil.moveTifFile(file, getFaxInBak());
		        }catch(IOException e) {
		        	log.error("添加传真回传错误");
		        	e.printStackTrace();
		        }
			}else{
				log.info("UPLOAD FAILED: " + file.getAbsolutePath());
			}
		}
	}
	
	private String getFaxInBak(){
		File file = new File(Constant.getFaxSendBackDirectory());
		File inFile= new File(file.getParentFile(),"in");
		if(!inFile.exists()){
			inFile.mkdirs();
		}
		return inFile.getAbsolutePath();
	}
	
	public void setTrafaxStatusDao(TrafaxStatusDao trafaxStatusDao) {
		this.trafaxStatusDao = trafaxStatusDao;
	}

	public static void main(String args[]){
		ApplicationContext context = new ClassPathXmlApplicationContext( 
				new String[] {"applicationContext-beans.xml"}); 
				BeanFactory factory = (BeanFactory) context; 
				EastfaxStatusDao statusDao = (EastfaxStatusDao)factory.getBean("statusDao"); 
				
				
		TrafaxReceiveDaemon daemon = new TrafaxReceiveDaemon();
		//daemon.processFaxRecv(statusDao);
	}
}
