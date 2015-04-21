package com.lvmama.pet.sweb.comment;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.service.comment.CmtSpecialSubjectService;
import com.lvmama.comm.utils.pic.UploadCtrl;
import com.lvmama.comm.vo.comment.CmtSpecialSubjectVO;


/**
 * 专题保存Action
 * @author yuzhizeng
 * 
 */
public class EditSpecialSubjectAction extends BackBaseAction {

	/**
	 * 序列
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 专题管理的逻辑层
	 */
	private CmtSpecialSubjectService cmtSpecialSubjectService;
	/**
	 * 专题管理标识
	 */
	private Long id;
	/**
	 * 需要编辑的专题管理
	 */
	private CmtSpecialSubjectVO cmtSpecialSubject;
 
	/**文件上传添加*/
	private File file;
	private String fileContentType;
	private String fileFileName;
	
	/**
	 * 保存
	 * @throws Exception 
	 */
	//@Action(value="/m/order/saveOrder",results=@Result(name="saveOrder",location="/m/buy/viewOrderInfo.do?orderId=${orderId}",type="redirect"))
	//@Action(value="/commentManager/saveSpecialSubject",results={@Result(location = "/WEB-INF/pages/back/comment/specialSubList.jsp")})
	@Action(value="/commentManager/saveSpecialSubject",results=@Result(name="success",location="/commentManager/querySpecialSubList.do",type="redirect"))
	public String save() throws Exception {
		
		if(file==null||StringUtils.isEmpty(fileContentType)||StringUtils.isEmpty(fileFileName)){
			throw new Exception("上传内容为空");
		}
		UploadCtrl uc = new UploadCtrl();
		if(uc.checkImgSize(file, 1024)){
			throw new Exception("图片大小需要小于1M");
		}
		String filename = uc.postToRemote(file, fileFileName);
		
		cmtSpecialSubject.setPic(filename);
		cmtSpecialSubjectService.save(cmtSpecialSubject);
		 
		return SUCCESS;
	}
	
	/**
	 * 上传图片
	 * @param imgsrc 图片地址
	 */
	public void addImage(final String imgsrc) {
		if (imgsrc == null || imgsrc.equals("")) {
			//alert("请选择要上传的图片");
			return;
		}
		cmtSpecialSubject.setPic(imgsrc);
	}

	public CmtSpecialSubjectVO getCmtSpecialSubject() {
		return cmtSpecialSubject;
	}

	public void setCmtSpecialSubjectService(
			CmtSpecialSubjectService cmtSpecialSubjectService) {
		this.cmtSpecialSubjectService = cmtSpecialSubjectService;
	}

	/*public List<CmtSpecialSubjectVO> getPictureList() {
		return pictureList;
	}*/

	public void setId(Long id) {
		this.id = id;
	}

	public CmtSpecialSubjectService getCmtSpecialSubjectService() {
		return cmtSpecialSubjectService;
	}

	public Long getId() {
		return id;
	}

	public void setCmtSpecialSubject(CmtSpecialSubjectVO cmtSpecialSubject) {
		this.cmtSpecialSubject = cmtSpecialSubject;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

}
