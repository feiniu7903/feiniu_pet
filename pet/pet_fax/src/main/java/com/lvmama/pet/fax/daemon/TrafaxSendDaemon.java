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
import com.lvmama.pet.fax.utils.FileUtil;

public class TrafaxSendDaemon extends Thread {
	private static final Log log = LogFactory.getLog(TrafaxSendDaemon.class);
	private TrafaxStatusDao trafaxStatusDao;
	
	@Override
	public void run() {
		while (true) {
			try{
				checkDBList();
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				Thread.sleep(5000);
			}catch(Exception ex){}
		}
	}
	
	private void checkDBList() {
		log.info("SEND STATUS loop");
		List<Map<String,Object>> list = trafaxStatusDao.queryList();
		log.info("size:"+list.size());
		for(int i=0;i<list.size();i++) {
			Map<String, Object> map = list.get(i);
			String status = map.get("FaxStatus").toString();
			String orderno = map.get("TaskUniqueID").toString();
			String barcode = map.get("barcode").toString();
			/*
			 * trafax原值
			0:等待TraFax服务器读取…
			1:等待转换…
			2:打印转换完成，等待发送
			3:正在发送…
			4:发送成功
			5:发送失败
			 */
			String retStatus="99";
			if (status.equals("4")) {
				retStatus="1";
			}else{
				Object obj = map.get("ErrorMsg");
				if (obj!=null) {
					String errorMsg = obj.toString().trim();
					if (errorMsg.equals("") || errorMsg.equalsIgnoreCase("NULL")) {
						retStatus="99";
					}else if("Faxnumber format is not right".equals(errorMsg)) {
						retStatus="12";
					}else if("拨号错误".equals(errorMsg)) {
						retStatus="24";
					}else if("对方忙".equals(errorMsg)) {
						retStatus="29";
					}else if("对方未应答".equals(errorMsg)) {
						retStatus="30";
					}else if("呼叫已被远端传真机断开".equals(errorMsg)) {
						retStatus="31";
					}else if("通信失败:拨号超时".equals(errorMsg)) {
						retStatus="26";
					}else if("通信失败:拨号后检测到拨号音".equals(errorMsg)) {
						retStatus="23";
					}else if("无传真设备应答".equals(errorMsg)) {
						retStatus="25";
					}
				}
			}
//			String result="";
//			switch( Integer.parseInt(status) ) {
//			case 1: result="SUCCESS"; break;
//			case 3: result="SERVERCANCEL"; break;
//			case 11: result="WRONGFILE"; break;
//			case 12: result="WRONGNO"; break;
//			case 29: result="DESTBUSY"; break;
//			case 31: result="USERCANCEL"; break;
//			
//			default: result="UNKNOWN"; break;
//				case 1:result="成功"; break;
//				case 2:result="管理员删除"; break;
//				case 3:result="服务端取消"; break;
//				case 4:result="客户端取消"; break;
//				case 11:result="传真文件错误"; break;
//				case 12:result="传真号码错误"; break;
//				case 13:result="用户帐号错误"; break;
//				case 14:result="用户没有权限"; break;
//				case 21:result="Modem不支持传真操作"; break;
//				case 22:result="初始化Modem错误"; break;
//				case 23:result="没有拨号音"; break;
//				case 24:result="拨号错误"; break;
//				case 25:result="没有回铃音"; break;
//				case 26:result="长时间静音"; break;
//				case 27:result="协商传真通讯参数错误"; break;
//				case 28:result="错误的传真标识"; break;
//				case 29:result="对方占线"; break;
//				case 30:result="无人接听或是空号"; break;
//				case 31:result="用户取消"; break;
//				case 32:result="超时错误"; break;
//				case 99:result="未知错误"; break;
//			};
			notify(orderno, retStatus, barcode);
		}
	}
	
	public void notify(String serialno, String status, String barcode) {
		String url = null;
		if (serialno.matches("^NEW.*")) {
			String s = serialno.substring(3);
			url = Constant.getSuperNotifyUrl()+"?serialno="+s+"&status="+status+"&barcode="+barcode;
		}else if(serialno.matches("^VST.*")){
			String s = serialno.substring(3);
			url = Constant.getVstNotifyUrl()+"?serialno="+s+"&status="+status+"&barcode="+barcode;
		}else if(serialno.matches("^TEST.*")){
			trafaxStatusDao.setNotified(serialno);
			log.info("Test serialno="+serialno+"&status="+status);
		}else{
			url = Constant.getNotifyUrl()+"?serialno="+serialno+"&status="+status;
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
	            	FileUtil.moveTifFile(Constant.getFaxDirectory(),
	            			Constant.getFaxSendBackDirectory(), serialno+".tif");
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
