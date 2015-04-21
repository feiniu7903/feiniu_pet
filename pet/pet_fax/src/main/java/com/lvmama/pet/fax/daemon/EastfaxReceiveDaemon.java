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

import com.lvmama.pet.fax.dao.EastfaxStatusDao;
import com.lvmama.pet.fax.utils.Constant;
import com.lvmama.pet.fax.utils.UploadUtil;

public class EastfaxReceiveDaemon extends Thread {
	private static final Log log = LogFactory.getLog(EastfaxReceiveDaemon.class);
	private EastfaxStatusDao eastfaxStatusDao;

	@Override
	public void run() {
		while (true) {
			try{
				processFaxRecv();
			}catch(Exception e){
				try{
					Thread.sleep(5000);
				}catch(Exception ex){}
				e.printStackTrace();
			}
			try{
				Thread.sleep(5000);
			}catch(Exception ex){}
		}
	}
	
	public void notify(String serialno, String status) {
		String url = null;
		if (serialno.matches("^NEW.*")) {
			String s = serialno.substring(3);
			url = Constant.getSuperNotifyUrl()+"?serialno="+s+"&status="+status;
		}else{
			url = Constant.getNotifyUrl()+"?serialno="+serialno+"&status="+status;
		}
		log.info("notify URL:" + url);
		GetMethod get = new GetMethod(url);
		HttpClient client = new HttpClient();
		try{
			int code = client.executeMethod(get);
        	String str = get.getResponseBodyAsString();
        	if (code==200 && str.startsWith("success")) {
        		eastfaxStatusDao.setNotified(serialno);
        		log.info("SERIALNO: " + serialno + " noticed SUCCESS.");
            }else{
            	log.info("SERIALNO: " + serialno + " noticed FAILED.");
            }
        }catch(IOException e) {
        	log.info("SERIALNO: " + serialno + " noticed FAILED.");
        }
        // Process the data from the input stream.
        get.releaseConnection();
	}
	
	public void processFaxRecv(){
		log.info("RECV FAX loop");
		HttpClient client = new HttpClient();
		eastfaxStatusDao.updateNullCallerId();
		List<Map<String,Object>> list = eastfaxStatusDao.queryAllUnnotified();
		for(Map<String,Object> map : list){
			Date recvTime = (Date)map.get("CREATE_TIME");
			File file = null;
			if (Constant.isTraFaxServerRecv()) {
				String filename = map.get("FaxID").toString();
				file = new File(Constant.getRecvDirectory()+"/"+filename);
			}else{
				String tmp = map.get("RECV_TIME").toString().replace(":", "-").replace(" ", "-");
				String filename = map.get("CALLERID").toString()+"_"+ tmp+".PDF";
				file = new File(Constant.getRecvDirectory()+"/"+filename);
			}
			String fileURL = null; 
			if (file.exists()){
				fileURL = UploadUtil.uploadFile(file, file.getName());
			}else{
				log.info("file not exists: " + file.getAbsolutePath());
			}
			if(fileURL!=null){
				try{
					String url = Constant.getSuperFaxrecvUrl()+"?recvtime="+recvTime.getTime()+"&callerid="+map.get("CALLERID")+"&url="+URLEncoder.encode(fileURL, "UTF-8");
					log.info("RECV NOTIFY URL:" + url);
					GetMethod get = new GetMethod(url);
					int code = client.executeMethod(get);
		        	String str = get.getResponseBodyAsString();
		        	if (code==200 && str.startsWith("success")) {
		        		eastfaxStatusDao.updateFaxRecvNotified(Long.parseLong(map.get("RECV_ID").toString()));
		        		log.info("添加传真回传成功: code=" + code + " str: " + str);
		            }else{
		            	log.info("添加传真回传失败: code=" + code + " str: " + str);
		            }
		        	get.releaseConnection();
		        }catch(IOException e) {
		        	log.error("添加传真回传错误");
		        	e.printStackTrace();
		        }
			}else{
				log.info("UPLOAD FAILED: " + file.getAbsolutePath());
			}
		}
	}

	public void setEastfaxStatusDao(EastfaxStatusDao eastfaxStatusDao) {
		this.eastfaxStatusDao = eastfaxStatusDao;
	}
	
}
