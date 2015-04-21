/**
 * 
 */
package com.lvmama.back.sweb.common;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.web.upload.UploadCtrl;
import com.lvmama.comm.pet.po.pub.ComAffix;
import com.lvmama.comm.pet.service.pub.ComAffixService;
import com.lvmama.comm.utils.DateUtil;

/**
 * 文件上传功能，按进来的分类读取该 分类下面的文件与描述信息
 * @author yangbin
 *
 */
@SuppressWarnings("serial")
@Results({
	@Result(name="error",location="/WEB-INF/pages/back/common/file_upload_error.jsp"),
	@Result(name="form",location="/WEB-INF/pages/back/common/file_upload.jsp"),
	@Result(name="success",location="/common/upload.do?objectId=${objectId}&objectType=${objectType}",type="redirect")
})
public class FileUploadAction  extends BaseAction{

	private ComAffixService comAffixService;
	private Long objectId;
	private String objectType;
	private File file;
	private String fileContentType;
	private String fileFileName;
	private ComAffix affix;
	
	public FileUploadAction()
	{
		super();
		affix=new ComAffix();
	}
	

//	@Action(value="/common/upload",interceptorRefs={@InterceptorRef(
//			params={"allowedTypes","image/bmp,image/png,image/gif,image/jpeg","maximumSize","1025956"},value="fileUpload"
//	)})
	@Action("/common/upload")
	public String execute()
	{
		
		//isLogined();
		if(getRequest().getMethod().equalsIgnoreCase("POST"))
		{
			return doUpload();
		}else
		{
			return entry();
		}
	}
	
	private String entry()
	{
		if(objectId==null||StringUtils.isEmpty(objectType))
			return ERROR;
		
		affix.setObjectId(objectId);
		affix.setObjectType(objectType);
		
		initPagination();
		pagination.setPerPageRecord(20);
		Map<String,Object> parameter=new HashMap<String, Object>();
		parameter.put("objectId", objectId);
		parameter.put("objectType", objectType);
		
		long count=comAffixService.selectCountByParam(parameter);
		pagination.setTotalRecords(count);
		if(count>0){
			parameter.put("skipResult", pagination.getFirstRow()-1);
			parameter.put("maxResult", pagination.getLastRow());
			pagination.setRecords(comAffixService.selectListByParam(parameter));
		}
		return "form";
	}
	
	/**
	 * 文件上传处理
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private String doUpload() {
		String name = fileFileName;
		int pos = name.lastIndexOf(".");
		String suffix ="";//允许没有后辍
		if(pos==-1){
			addFieldError("name", "文件类型错误");
			return ERROR;
		}else{
			suffix=name.substring(pos);
		}
		Date date = new Date();
		StringBuffer sb = new StringBuffer();
		sb.append(DateUtil.getFormatDate(date, "yyMMddHHmmssSSS"));
		sb.append(suffix);
		UploadCtrl uploadCtrl = new UploadCtrl();
 		String url = uploadCtrl.postToRemote(file, sb.toString());
 		try {
			objectId = affix.getObjectId();
			objectType = affix.getObjectType();
			affix.setUserId(this.getOperatorName());
			affix.setPath(url);
			//affix.setFileType(fileContentType);
			comAffixService.addAffix(affix, getOperatorName());

		} catch (Exception ex) {
			ex.printStackTrace();
			addFieldError("name", "保存错误");
			return entry();
		}
		return SUCCESS;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public void setFile(File file) {
		this.file = file;
	}

	

	public void setComAffixService(ComAffixService comAffixService) {
		this.comAffixService = comAffixService;
	}


	public ComAffix getAffix() {
		return affix;
	}

	public void setAffix(ComAffix affix) {
		this.affix = affix;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public Long getObjectId() {
		return objectId;
	}

	public String getObjectType() {
		return objectType;
	}
	
	
}
