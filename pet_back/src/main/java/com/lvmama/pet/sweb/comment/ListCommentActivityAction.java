/**
 * 
 */
package com.lvmama.pet.sweb.comment;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.comment.CmtActivity;
import com.lvmama.comm.pet.service.comment.CmtActivityService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.UUIDGenerator;
import com.lvmama.comm.utils.pic.UploadCtrl;

/**
 * @author liuyi
 * 点评广告管理
 */
public class ListCommentActivityAction extends BackBaseAction {
	
	
	private CmtActivityService cmtActivityService;
	private List<CmtActivity> cmtActivityList;
	private String activityId;
	private CmtActivity currentActivity;

	/**
	 * 广告主题,广告宣传位置
	 */
	private String activitySubject;
	/**
	 * 链接
	 */
	private String url;
	/**
	 * 图片
	 */
	private String pic;
	/**
	 * 图片链接
	 */
	private String picUrl;
	/**
	 * 广告标题
	 */
	private String title;
	/**
	 * 广告内容
	 */
	private String content;
	
	/**文件上传添加*/
	private File file;
	private String fileContentType;
	private String fileFileName;
	
	private String currentPic;
	
	
	/**
	 * 查询活动
	 */
	@Action(value="/commentManager/listCommentActivity",results={@Result(location = "/WEB-INF/pages/back/comment/activityList.jsp")})
	public String listCommentActivity()
	{
		cmtActivityList = cmtActivityService.query();
		return SUCCESS;
	}
	
	
	/**
	 * 打开.点评活动编辑窗口
	 * @return
	 */
	@Action(value="/commentManager/openEditActivity",results={@Result(location = "/WEB-INF/pages/back/comment/editActivity.jsp")}) 
	public String openEditComment() {
		currentActivity = cmtActivityService.queryById(Long.parseLong(activityId));
		return SUCCESS;
	}
	
	
	@Action(value="/commentManager/saveActivity",results=@Result(name="success",location="/commentManager/listCommentActivity.do",type="redirect"))
	public String saveActivity() throws Exception {
		if((file==null||StringUtils.isEmpty(fileContentType)||StringUtils.isEmpty(fileFileName)) && StringUtils.isEmpty(currentPic)){
			throw new Exception("上传内容为空");
		}
		
		String filename = "";
		if(!StringUtils.isEmpty(fileFileName))
		{
			UploadCtrl uc = new UploadCtrl();
			if(uc.checkImgSize(getFile(), 1024)){
				throw new Exception("图片大小需要小于1M");
			}
//			String imgContextPath = Constant.getInstance().getUploadUrl();
//			String fileName = System.currentTimeMillis() + ".jpg";
//			String fileFullName = imgContextPath + placePhoto.getPlaceId().toString() + "/" + fileName;
//			placePhoto.setImagesUrl(UploadCtrl.postToRemote(placePhoto.getImagePath(), fileFullName));
			
			filename = new UUIDGenerator().generate().toString() + getSuffixName(fileFileName);
			filename = uc.postToRemote(getFile(), "super/"+DateUtil.getFormatDate(new Date(), "yyyy/MM/")+filename);
		}
		else if(!StringUtils.isEmpty(currentPic))
		{
			filename = currentPic;
		}
		else
		{
			throw new Exception("上传内容为空");
		}

		CmtActivity cmtActivity = new CmtActivity();
		cmtActivity.setActivityId(Long.parseLong(activityId));
		//cmtActivity.setActivitySubject(getActivitySubject());  不可更改
		cmtActivity.setContent(getContent());
		cmtActivity.setPic(filename);
		cmtActivity.setPicUrl(getPicUrl());
		cmtActivity.setTitle(getTitle());
		cmtActivity.setUrl(getUrl());
		cmtActivityService.update(cmtActivity);
		return SUCCESS;
	}
	

	public CmtActivityService getCmtActivityService() {
		return cmtActivityService;
	}

	public void setCmtActivityService(CmtActivityService cmtActivityService) {
		this.cmtActivityService = cmtActivityService;
	}

	public List<CmtActivity> getCmtActivityList() {
		return cmtActivityList;
	}

	public void setCmtActivityList(List<CmtActivity> cmtActivityList) {
		this.cmtActivityList = cmtActivityList;
	}


	public String getActivityId() {
		return activityId;
	}


	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}


	public CmtActivity getCurrentActivity() {
		return currentActivity;
	}


	public void setCurrentActivity(CmtActivity currentActivity) {
		this.currentActivity = currentActivity;
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


	public String getActivitySubject() {
		return activitySubject;
	}


	public void setActivitySubject(String activitySubject) {
		this.activitySubject = activitySubject;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getPic() {
		return pic;
	}


	public void setPic(String pic) {
		this.pic = pic;
	}


	public String getPicUrl() {
		return picUrl;
	}


	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * 获取后缀名
	 * @param filename 文件名 
	 * @return String
	 */
	private String getSuffixName(final String filename) {
		if (null != filename && filename.indexOf(".") != -1) {
			return filename.substring(filename.lastIndexOf("."));
		} else {
			return "";
		}
	}


	public String getCurrentPic() {
		return currentPic;
	}


	public void setCurrentPic(String currentPic) {
		this.currentPic = currentPic;
	}

}
