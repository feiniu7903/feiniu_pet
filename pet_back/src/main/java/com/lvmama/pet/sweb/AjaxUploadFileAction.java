package com.lvmama.pet.sweb;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.LogObject;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.pic.UploadCtrl;

public class AjaxUploadFileAction extends BackBaseAction {
	private File file;
	private String fileFileName;
	private String serverType;
	private FSClient fsClient;
	private ComLogService comLogRemoteService;
	/**
	 * 上传文件使用
	 */
	@Action(value="/ajax/file/upload",interceptorRefs ={
			@InterceptorRef(value="fileUpload",params={"maximumSize","20971520"})//20M			
			,@InterceptorRef(value="defaultStack")
	})
	public void upload(){
		JSONResult result=new JSONResult();
		try{
			if(StringUtils.isEmpty(serverType)){
				throw new IllegalArgumentException("服务类型不存在");
			}
			
			if(file.length()>18874368L){
				throw new IllegalArgumentException("文件不可以大于18M");
			}
			
			FileInputStream fin=new FileInputStream(file);
			Long pid=fsClient.uploadFile(fileFileName,fin, serverType);
			IOUtils.closeQuietly(fin);
			if(pid==0){
				throw new IllegalArgumentException("上传文件失败");
			}
			result.put("file", pid);
			String fileName = "";
			if(fileFileName.length() > 0) {
				int lastIndex = fileFileName.lastIndexOf(".");
				fileName = fileFileName.substring(0, lastIndex);
			}
			result.put("fileName", fileName);
			LogObject.addUploadFileLog(pid, file.getName(), this.getSessionUserNameAndCheck(),
					comLogRemoteService);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	/**
	 * 上传图片使用
	 */
	@Action(value="/ajax/image/upload",interceptorRefs ={
			@InterceptorRef(value="fileUpload",params={"maximumSize","10424410"})//20M			
			,@InterceptorRef(value="defaultStack")
	})
	public void updateImage(){
		JSONResult result = new JSONResult(getResponse());
		try{
			if(UploadCtrl.checkImgSize(file, 1024)){
				throw new Exception("图片大小需要小于1M");
			}
			
			String filename = UploadCtrl.postToRemote2(file,fileFileName);
			result.put("fullName", filename);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	/**
	 * 上传传真回传件使用
	 */
	@Action(value="/ajax/uploadFaxReceiveFile",interceptorRefs ={
			@InterceptorRef(value="fileUpload",params={"maximumSize","10424410"})//20M			
			,@InterceptorRef(value="defaultStack")
	})
	public void uploadFaxReceiveFile(){
		JSONResult result = new JSONResult(getResponse());
		try{
			String filename = UploadCtrl.postFaxFileToRemote(file,fileFileName);
			result.put("fullName", filename);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	public void setServerType(String serverType) {
		this.serverType = serverType;
	}
	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}
	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
	}	
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
}
