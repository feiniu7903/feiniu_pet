package com.lvmama.pet.fax.daemon;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.pet.fax.dao.TrafaxStatusDao;
import com.lvmama.pet.fax.utils.Constant;

public class TrafaxErrorDaemon extends Thread {
	private static final Log log = LogFactory.getLog(TrafaxErrorDaemon.class);
	private TrafaxStatusDao trafaxStatusDao;
	
	@Override
	public void run() {
		while (true) {
			try{
				checkErrorDBList();
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				Thread.sleep(60000);
			}catch(Exception ex){}
		}
	}
	
	private void checkErrorDBList() {
		log.info("SEND ERROR Fax loop");
		List<Map<String,Object>> list = trafaxStatusDao.queryErrorList();
		log.info("size:"+list.size());
		for(int i=0;i<list.size();i++) {
			Map<String, Object> map = list.get(i);
			String orderno = map.get("TaskUniqueID").toString();
			String barcode = map.get("barcode").toString();
			notify(orderno, barcode);
		}
	}
	
	public void notify(String serialno, String barcode) {
		String url = null;
		if (serialno.matches("^NEW.*")) {
			String s = serialno.substring(3);
			url = Constant.getSuperNotifyErrorUrl()+"?serialno="+s+"&barcode="+barcode;
		}else if(serialno.matches("^VST.*")){
			String s = serialno.substring(3);
			url = Constant.getVstNotifyErrorUrl()+"?serialno="+s+"&barcode="+barcode;
		}else if(serialno.matches("^TEST.*")){
			trafaxStatusDao.setNotified(serialno);
			log.info("Test serialno="+serialno);
		}else{
		}
		if(StringUtils.isNotEmpty(url)){
			log.info("notify URL:" + url);
			GetMethod get = new GetMethod(url);
			HttpClient client = new HttpClient();
			try{
				int code = client.executeMethod(get);
	        	String str = get.getResponseBodyAsString();
	        	if (code==200 && str.startsWith("success")) {
	        		trafaxStatusDao.setNotified(serialno);
	            	log.info("SERIALNO: " + serialno + " noticed SUCCESS.");
	            }else{
	            	log.error("SERIALNO: " + serialno + " noticed FAILED.");
	            }
	        }catch(IOException e) {
	        	log.error("SERIALNO: " + serialno + " noticed FAILED.");
	        }
	        // Process the data from the input stream.
	        get.releaseConnection();
		}
	}

	public void setTrafaxStatusDao(TrafaxStatusDao trafaxStatusDao) {
		this.trafaxStatusDao = trafaxStatusDao;
	}

}
