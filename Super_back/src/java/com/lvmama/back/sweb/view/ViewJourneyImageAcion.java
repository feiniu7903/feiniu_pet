package com.lvmama.back.sweb.view;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.back.sweb.prod.ProdViewPageBaseAction;
import com.lvmama.back.web.upload.UploadCtrl;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.view.ViewPageJourneyService;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.ResultHandle;
@Results({
	@Result(name = "toViewJourneyImages", location = "/WEB-INF/pages/back/view/view_journey_image.jsp"),
	@Result(name = "goAction", location ="/view/toViewJourneyImages.do?journeyId=${journeyId}",type="redirect")
	})
public class ViewJourneyImageAcion extends ProdViewPageBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ViewPageJourneyService viewPageJourneyService;
	private List<ComPicture> journeyImageList;
	private Long journeyId;
	private Long pictureId;
	
	/**文件上传添加*/
	private File file;
	private String fileContentType;
	private String fileFileName;
	private Long uploadJourneyPicJourneyId;
	private String productJourneyPicName;
	private Long uploadJourneyPicProductId;
	private ComPictureService comPictureService;
	/***/

	@Override
	public void save() {
	}
	
	/**
	 * 页面载入显示
	 * */
	@Override
	@Action(value="/view/toViewJourneyImages")
	public String goEdit() {
		journeyImageList = comPictureService.getPictureByObjectIdAndType(journeyId, "VIEW_JOURNEY");
		return "toViewJourneyImages";
	}

	/**
	 * 行程图片上传
	 * zx
	 */
	@Action("/view/uploadJourneyPic")
	public void uploadJourneyPicture(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(uploadJourneyPicJourneyId,"行程信息不存在");			
			if(file==null||StringUtils.isEmpty(fileContentType)||StringUtils.isEmpty(fileFileName)){
				throw new Exception("上传内容为空");
			}
			UploadCtrl uc=new UploadCtrl();
			if(uc.checkImgSize(file, 1024)){
				throw new Exception("图片大小需要小于1M");
			}
			StringBuffer sb=new StringBuffer();
			sb.append(getSession().getServletContext().getRealPath("/"));
			sb.append(File.separator);
			sb.append("img");
			sb.append(File.separator);
			sb.append("base");
			//uc.processWaterMark(file, sb.toString(), 580, 290, 430, 231);//添加水印
			
			String filename=uc.postToRemote(file, fileFileName);
			
			//对viewpage操作，让其有效或新建
			if(!viewPageService.isProductUsed(uploadJourneyPicProductId)){
				if(viewPageService.isProductUnUsed(uploadJourneyPicProductId)){
					viewPageService.updateValidByProductId(uploadJourneyPicProductId);
				}else{
					ViewPage vp=new ViewPage();
					vp.setProductId(uploadJourneyPicProductId);
					ResultHandle handle = viewPageService.addViewPage(vp);
					if(handle.isFail()){
						throw new IllegalArgumentException(handle.getMsg());
					}
				}
			}				
			ComPicture picture = new ComPicture();
			picture.setPictureObjectId(uploadJourneyPicJourneyId);
			picture.setPictureObjectType("VIEW_JOURNEY");
			picture.setPictureName(productJourneyPicName);
			picture.setPictureUrl(filename);
			picture.setIsNew(true);// 标识图片是新建产生的				
//			Long pk=viewPageService.saveViewPagePicture(picture,getOperatorNameAndCheck());
			Long pk = comPictureService.savePicture(picture);
			result.put("pictureId", pk);
			
			result.put("imgname", productJourneyPicName);
					
			result.put("icon", "BIG");
			result.put("filename", filename);
		}catch(Exception ex){
			result.raise(ex);
		}		
		result.output(getResponse());
	}
	
	/**
	 * 图片删除操作
	 */
	@Action("/view/toDeleteJourneyImages")
	public void delete(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(pictureId,"小贴士不存在");
			comPictureService.deletePicture(pictureId);
			result.put("pictureId", pictureId);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}

	public ViewPageJourneyService getViewPageJourneyService() {
		return viewPageJourneyService;
	}

	public void setViewPageJourneyService(
			ViewPageJourneyService viewPageJourneyService) {
		this.viewPageJourneyService = viewPageJourneyService;
	}

	public List<ComPicture> getJourneyImageList() {
		return journeyImageList;
	}

	public void setJourneyImageList(List<ComPicture> journeyImageList) {
		this.journeyImageList = journeyImageList;
	}

	public Long getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(Long journeyId) {
		this.journeyId = journeyId;
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

	public Long getUploadJourneyPicJourneyId() {
		return uploadJourneyPicJourneyId;
	}

	public void setUploadJourneyPicJourneyId(Long uploadJourneyPicJourneyId) {
		this.uploadJourneyPicJourneyId = uploadJourneyPicJourneyId;
	}

	public String getProductJourneyPicName() {
		return productJourneyPicName;
	}

	public void setProductJourneyPicName(String productJourneyPicName) {
		this.productJourneyPicName = productJourneyPicName;
	}

	public Long getUploadJourneyPicProductId() {
		return uploadJourneyPicProductId;
	}

	public void setUploadJourneyPicProductId(Long uploadJourneyPicProductId) {
		this.uploadJourneyPicProductId = uploadJourneyPicProductId;
	}

	public Long getPictureId() {
		return pictureId;
	}

	public void setPictureId(Long pictureId) {
		this.pictureId = pictureId;
	}

	public ComPictureService getComPictureService() {
		return comPictureService;
	}

	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}
}
