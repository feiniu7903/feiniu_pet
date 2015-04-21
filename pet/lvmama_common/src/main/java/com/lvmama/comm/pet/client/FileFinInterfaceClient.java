package com.lvmama.comm.pet.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.fin.FinGLInterface;
import com.lvmama.comm.pet.po.fin.FinGLInterfaceReq;
import com.lvmama.comm.pet.po.fin.FinGLInterfaceRsp;
import com.lvmama.comm.pet.po.pub.ComFSFile;
import com.lvmama.comm.pet.po.pub.ComFSServiceConfig;
import com.lvmama.comm.pet.service.fin.FinGLService;
import com.lvmama.comm.pet.service.pub.ComFSService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.HttpsUtil.HttpResponseWrapper;
import com.lvmama.comm.vo.Constant;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class FileFinInterfaceClient {
	private static final Log log = LogFactory.getLog(FileFinInterfaceClient.class);
	public static final String KEY = "FIN_GL_INTERFACE_SEND_LOCK";
	XStream xStream = null;
	
	private  String contentType = "text/xml; charset=UTF-8";

	private  String requestCharacter = "UTF-8";

	// 入账成功类型
	private  final String STATUS_SUCCESS = "success";
	// 入账失败类型
	private  final String STATUS_FAIL = "fail";
	public static Object LOCKMEMCACHED = new Object();
	private Page pagination;
	
	private FSClient fsClient;
	/**
	 * 文件系统Service.
	 */
	private ComFSService comFSRemoteService;
	
	/**
	 * 对账接服务
	 */
	private FinGLService finGLService;
	/**
	 * 发送对账结果操作
	 * 
	 * @param httpResponseWrapper
	 */
	public void send() {
		HttpResponseWrapper httpResponseWrapper = null;
		try {
			if (isOnDoingMemCached(KEY)) {
				return;
			}
			if (null == xStream) {
				xStream = new XStream(new DomDriver("UTF-8"));
				xStream.processAnnotations(FinGLInterfaceReq.class);
				xStream.processAnnotations(FinGLInterfaceRsp.class);
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pageSize", 500);
			params.put("currentPage", 1);
			long count = 0;
			FinGLInterfaceReq req = new FinGLInterfaceReq();
			String outputFile = System.getProperty("java.io.tmpdir") + "/"+ "finInterface_"+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+ ".txt";
			do {
				StringBuffer sendStr = new StringBuffer();
				pagination = finGLService.queryForPage(params);
				count = pagination.getItems().size();
				if (null != pagination && null != pagination.getItems()&& pagination.getItems().size() > 0) {
					List<FinGLInterface> subList = pagination.getItems();
					Collections.sort(subList, new FinGLInterfaceComparator());
					int subCount = 0;
					do {
						try {
							List<FinGLInterface> finList = getBatchList(subList);
							subCount = finList.size();
							if(subCount>0){
								// 序列化对象
								req = new FinGLInterfaceReq();
								req.addList(finList);
								String xmlStr = xStream.toXML(req);
								sendStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
								sendStr.append(xmlStr.replace("__", "_").replaceAll("\r", "")
										.replaceAll("\n", "").replaceAll(">\\s+<", "><"));
								sendStr.append("\r\n");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					} while (subCount > 0);
					pagination.setCurrentPage(pagination.getCurrentPage() + 1);
					params.put("currentPage", pagination.getCurrentPage());
					writeFin(sendStr, outputFile);
				}
			} while (count > 0);
			// 发送报文
			Long fileId =fsClient.uploadFile(new File(outputFile), "FIN_SEND_FILE"); 
			ComFSFile file = comFSRemoteService.selectComFSFileById(fileId);
			Constant constant = Constant.getInstance();
			Map<String, String> map = new HashMap<String, String>();
			map.put("fileName", file.getFilePath()+"/"+file.getServerFileName());
			log.info("send u8 fileName is "+map.get("fileName"));
			try{
			httpResponseWrapper = HttpsUtil.requestPostFormResponse(constant.getValue("u8.interface.url"), map,requestCharacter);
			String returnStr = httpResponseWrapper.getResponseString();
			log.info("发送文件后返回信息："+returnStr);
			httpResponseWrapper.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			MemcachedUtil.getInstance().replace(KEY, 10*60*60, file.getServerFileName());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != httpResponseWrapper) {
				httpResponseWrapper.close();
			}
		}
	}
	
	public void receive(final String fileStr){
		try {
			log.info("u8_file ="+fileStr);
			String[] str = fileStr.split("/");
			ComFSServiceConfig comFSServiceConfig = comFSRemoteService.selectComFSServiceConfigByServerType(Constant.COM_FS_SERVER_TYPE.FIN_U8RETURN_FILE.name());
			if(StringUtil.isEmptyString(fileStr)){
				log.info("fin_u8's file is null");
			}else if(str.length!=2){
				log.info("fin_u8's file format is error");
			}
			if(null==comFSServiceConfig){
				log.info("fin_ui's serive config is null");
			}
			if(null!=MemcachedUtil.getInstance().get(KEY) && str[1].equals(MemcachedUtil.getInstance().get(KEY))){
				log.info("fin_u8's file name is different memcached's fileName="+MemcachedUtil.getInstance().get(KEY));
			}
			ComFSFile fsFile = new ComFSFile();
			fsFile.setFsId(comFSServiceConfig.getFsId());
			fsFile.setFileName(str[1]);
			fsFile.setServerFileName(str[1]);
			fsFile.setFilePath(comFSServiceConfig.getRootDir()+"/" + str[0]);
			fsFile.setInsertedTime(new Date());
			Long u8fileId = comFSRemoteService.insertComFSFile(fsFile);	
			ComFile returnFile =fsClient.downloadFile(u8fileId);
			InputStreamReader  insr = new InputStreamReader(returnFile.getInputStream());
			BufferedReader br = new BufferedReader(insr);
			String myreadline=null;
			if (null == xStream) {
				xStream = new XStream(new DomDriver("UTF-8"));
				xStream.processAnnotations(FinGLInterfaceReq.class);
				xStream.processAnnotations(FinGLInterfaceRsp.class);
			}
			while ((myreadline = br.readLine()) != null) {
				FinGLInterfaceRsp rst = (FinGLInterfaceRsp) xStream.fromXML(myreadline);
				if (null != rst && null != rst.getItem()&& rst.getItem().size() > 0) {
					parseXml(rst, new HashMap<String, Object>());
				}
			}
		} catch (IOException e) {
			log.error(e);
		}
		releaseMemCached(KEY);
	}
	
	private void parseXml(final FinGLInterfaceRsp rst,final Map<String,Object> paraMap){

		// 判断一个批次里是不是都成功，有一条失败，就通通不入库
		boolean successFlag = true;
		for (int i = 0; i < rst.getItem().size(); i++) {
			String statusCode = rst.getItem().get(i).getSucceed();
			if (!"0".equals(statusCode)) {
				successFlag = false;
				break;
			}
		}

		for (int i = 0; i < rst.getItem().size(); i++) {
			// 主键id
			String id = rst.getItem().get(i).getKey();
			// 返回状态
			String statusCode = rst.getItem().get(i).getSucceed();
			// 返回状态描述
			String statusDesc = rst.getItem().get(i).getDsc();
			String inoId = rst.getItem().get(i).getInoId();

			// 更新状态
			paraMap.put("id", id);
			if ("0".equals(statusCode)) {
				if (successFlag) {
					paraMap.put("receivablesStatus",STATUS_SUCCESS);
				} else {
					// 有一条失败的情况下，成功的数据也更新为失败
					paraMap.put("receivablesStatus",STATUS_FAIL);
					statusDesc = "同批次号其它数据有问题";
				}
			} else {
				paraMap.put("receivablesStatus", STATUS_FAIL);
			}
			paraMap.put("receivablesResult", statusDesc);
			paraMap.put("inoId", inoId);
			finGLService.updateStatus(paraMap);
		}
	
	}
	private  List<FinGLInterface> getBatchList(final List<FinGLInterface> list){
		List<FinGLInterface> subList =  new ArrayList<FinGLInterface>();
		if(list.isEmpty()){
			return subList;
		}
		String batchNo = list.get(0).getBatchNo();
		for(int i=0;i<list.size();i++){
			if(batchNo.equals(list.get(i).getBatchNo())){
				subList.add(list.get(i));
				list.remove(i);
				i--;
			}else{
				break;
			}
		}
		return subList;
	}
	public void writeFin(StringBuffer str, String fileName) {
		BufferedWriter writer =null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(fileName), true), "UTF-8"));
			writer.write(str.toString());
			writer.close();
		}catch(Exception e){} finally{
			IOUtils.closeQuietly(writer);
		}
	}

	public void inputstreamtofile(InputStream ins, File file){
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		}catch(Exception e){}finally {
			IOUtils.closeQuietly(ins);
			IOUtils.closeQuietly(os);
		}
	}
	public static boolean isOnDoingMemCached(String key) {
		synchronized(LOCKMEMCACHED){
			if(MemcachedUtil.getInstance().get(key) !=null){
				return true;			
			}else{
				//缓存设置有效时间为5分钟
				if(!MemcachedUtil.getInstance().set(key, 10*60*60, key)){
					log.error("请检查MemCached服务器或相应的配置文件！");
				}
				return false;
			}
		}
	}
	public static void releaseMemCached(String key) {
		synchronized(LOCKMEMCACHED){
			if(!MemcachedUtil.getInstance().remove(key)){
				log.error("请检查MemCached服务器或相应的配置文件！");
			}
		}
	}
	class FinGLInterfaceComparator implements Comparator{

		@Override
		public int compare(Object o1, Object o2) {
			FinGLInterface p0=(FinGLInterface)o1;  
			FinGLInterface p1=(FinGLInterface)o2;  
			int flag=p0.getBatchNo().compareTo(p1.getBatchNo());
			return flag;
		}
	}
	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}

	public void setComFSRemoteService(ComFSService comFSRemoteService) {
		this.comFSRemoteService = comFSRemoteService;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setFinGLService(FinGLService finGLService) {
		this.finGLService = finGLService;
	}
}
