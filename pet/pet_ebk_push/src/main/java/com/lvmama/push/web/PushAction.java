package com.lvmama.push.web;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.StringUtils;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.bee.service.ebkpush.IEbkPushService;
import com.lvmama.comm.pet.po.ebkpush.EbkPushMessage;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.push.model.ClientSessionInfo;
import com.lvmama.push.model.SessionManager;
import com.lvmama.push.processer.CallbackMsg;
import com.lvmama.push.util.ConstantPush;


@Results({@Result(name = "userList", location = "/WEB-INF/pages/user/list.jsp"),
	@Result(name = "sessionList", location = "/WEB-INF/pages/session/list.jsp")})
public class PushAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8987326039972466457L;
	
	private String udid;
	
	private List<ClientSessionInfo> list;
	
	private File file;
	
	private String fileFileName;

	private String fileContentType;
	
	private String msg;
	
	private String pushId;

	private IEbkPushService ebkPushService;
	
	@Action("/push/sessionList")
	public String sessionList(){
			Map<String,ClientSessionInfo> map = SessionManager.getInstance().getSessions();
			 list = new ArrayList<ClientSessionInfo>();
			 for (Entry<String, ClientSessionInfo> b : map.entrySet()) {  
				 ClientSessionInfo csi= b.getValue();
				 list.add(csi);
			 }
		 // SessionManager.getInstance().getSessions().toArray(sessions);
		return "sessionList";
	}
	

	
	@Action("/log/uploadLog")
	public void uploadLog(){
		if(!StringUtil.isEmptyString(pushId)){
			EbkPushMessage epm = ebkPushService.selectMessageByPK(Long.valueOf(pushId));
			if(epm!=null){
				File dir = new File(ConstantPush.getPushLogDir()+udid);
				if(dir.exists()){
					dir.mkdirs();
				}
				File destFile = new File(ConstantPush.getPushLogDir()+udid, this.getFileFileName());
				try {
					FileUtils.copyFile(file, destFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				epm.setIsSuccess("Y");
				epm.setCallBackTime(new Date());
				epm.setAddInfo(ConstantPush.getPushLogDir()+udid+"/"+this.getFileFileName());
				ebkPushService.updateEbkPushMessage(epm);
				CallbackMsg msg = new CallbackMsg("0","success");
				JSONObject jsonObject = JSONObject.fromObject(msg);
				this.sendAjaxMsg(jsonObject.toString());
			} else {
				CallbackMsg msg = new CallbackMsg("-1","error");
				JSONObject jsonObject = JSONObject.fromObject(msg);
				this.sendAjaxMsg(jsonObject.toString());
			}
		} else {
			CallbackMsg msg = new CallbackMsg("-1","pushId is required");
			JSONObject jsonObject = JSONObject.fromObject(msg);
			this.sendAjaxMsg(jsonObject.toString());
		}
		
		
	}

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}
	
	public List<ClientSessionInfo> getList() {
		return list;
	}

	public void setList(List<ClientSessionInfo> list) {
		this.list = list;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}


	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getPushId() {
		return pushId;
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}



	public void setEbkPushService(IEbkPushService ebkPushService) {
		this.ebkPushService = ebkPushService;
	}

}
