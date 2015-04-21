package com.lvmama.clutter.web.ebk;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import com.lvmama.clutter.model.ebk.CallbackMsg;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.comm.BaseAction;
import com.lvmama.comm.bee.service.ebkpush.IEbkPushService;
import com.lvmama.comm.pet.po.ebkpush.EbkPushMessage;
import com.lvmama.comm.utils.StringUtil;


@Namespace("/ebk_push/")
public class EBKLogAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8987326039972466457L;
	
	private String udid;
	

	
	private File file;
	
	private String fileFileName;

	private String fileContentType;
	
	private String msg;
	
	private String pushId;

	private IEbkPushService ebkPushService;
	
	
	

	
	@Action("/log/uploadLog")
	public void uploadLog(){
		if(!StringUtil.isEmptyString(pushId)){
			EbkPushMessage epm = ebkPushService.selectMessageByPK(Long.valueOf(pushId));
			if(epm!=null){
				File dir = new File(ClutterConstant.getPushLogDir()+udid);
				if(dir.exists()){
					dir.mkdirs();
				}
				File destFile = new File(ClutterConstant.getPushLogDir()+udid, this.getFileFileName());
				try {
					FileUtils.copyFile(file, destFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				epm.setIsSuccess("Y");
				epm.setCallBackTime(new Date());
				epm.setAddInfo(ClutterConstant.getPushLogDir()+udid+"/"+this.getFileFileName());
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
