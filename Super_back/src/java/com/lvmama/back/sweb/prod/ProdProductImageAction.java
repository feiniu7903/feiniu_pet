package com.lvmama.back.sweb.prod;

import com.lvmama.back.web.upload.UploadCtrl;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.vo.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import java.io.File;
import java.util.List;

/**
 *销售产品类别
 * @author yuzhibing
 *
 */
@Results({
	@Result(name = "input", location = "/WEB-INF/pages/back/prod/product_image.jsp"),
	@Result(name = "auditingShow", location = "/WEB-INF/pages/back/prod/auditing/product_image_auditing_show.jsp")
	})
public class ProdProductImageAction extends ProdViewPageBaseAction{

	private ComPictureService comPictureService;
	private ComLogService comLogService;
	private List<ComPicture> pictureList;
	private File file;
	private String fileContentType;
	private String fileFileName;
	private String imgname;
	private String type;
	private Long pictureId;
	
	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
	
	public ProdProductImageAction() {
		super();
		setMenuType("image");		
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 4037997343826690344L;

    @Action(value="/prod/toProductImageAuditingShow")
    public String toProductImageAuditingShow(){
        this.goEdit();
        return "auditingShow";
    }

	@Override
	@Action(value="/prod/toProductImage")
	public String goEdit() {
		if(!doBefore()){
			return PRODUCT_EXCEPTION_PAGE;
		}
		pictureList = comPictureService.getPictureByPageId(productId);
		return goAfter();
	}
	
	/**
	 * 图片删除操作
	 */
	@Action("/prod/deleteImage")
	public void delete(){
		JSONResult result=new JSONResult();
		try{			
			ComPicture cp = comPictureService.getPictureByPK(pictureId);
			if(cp==null){
				throw new Exception("图片不存在");
			}
			getOperatorNameAndCheck();
			comPictureService.deletePicture(cp.getPictureId());
			comLogService.insert("PROD_PRODUCT_IMAGE_UPLOAD",cp.getPictureObjectId(),null,getOperatorNameAndCheck(),
					Constant.COM_LOG_PRODUCT_EVENT.deleteProductBigPicture.name(),
					"删除图片", null, "PROD_PRODUCT");
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	/**
	 * 图片移动
	 */
	@Action("/prod/moveImage")
	public void move(){
		JSONResult result=new JSONResult();
		try{
			Assert.isTrue((StringUtils.equals(type, "up")||StringUtils.equals(type, "down")),"参数不正确");
//			boolean f=viewPageService.changeSeq(pictureId,type);
			boolean f=comPictureService.changeSeq(pictureId,type);
			if(!f){
				throw new JSONResultException(-2, "没有找到排序的数据");
			}
		}catch(JSONResultException ex){
			result.raise(ex);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}

	/**
	 * 上传的icon,不能大于50K，上传的大图需要添加水印
	 */
	@Override
	@Action("/prod/saveImage")
	public void save() {
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(productId,"产品信息不存在");			
			if(file==null||StringUtils.isEmpty(fileContentType)||StringUtils.isEmpty(fileFileName)){
				throw new Exception("上传内容为空");
			}
			UploadCtrl uc=new UploadCtrl();
			if(StringUtils.equals(type, "ICON")){//上传的是icon需要小于50k
				if(uc.checkImgSize(file, 50)){
					throw new Exception("图片大小需要小于50K");
				}
				uc.processImg(file);
			}else if(StringUtils.equals(type, "BIG")){
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
			}else{
				throw new Exception("类型错误");
			}
			
			String filename=uc.postToRemote(file, fileFileName);
			
			if(StringUtils.equals(type, "ICON")){
				product=prodProductService.getProdProduct(productId);
				product.setSmallImage(filename);
				prodProductService.updateByPrimaryKey(product,getOperatorNameAndCheck());		
				productMessageProducer.sendMsg(MessageFactory.newProductCreateMessage(productId));
				/**20120307 zx add log*/
				comLogService.insert("PROD_PRODUCT_IMAGE_UPLOAD",productId,null,getOperatorNameAndCheck(),
						Constant.COM_LOG_PRODUCT_EVENT.uploadProductSmallPicture.name(),
						"上传了产品小图", null, "PROD_PRODUCT");
				/***/
				
			}else{
				checkViewPage(productId);		
				ComPicture picture = new ComPicture();
				picture.setPictureObjectId(productId);
				picture.setPictureObjectType("VIEW_PAGE");
				picture.setPictureName(imgname);
				picture.setPictureUrl(filename);
				picture.setIsNew(true);// 标识图片是新建产生的				
//				Long pk=viewPageService.saveViewPagePicture(picture,getOperatorNameAndCheck());
				Long pk=comPictureService.savePicture(picture);
				result.put("pictureId", pk);
				result.put("imgname", imgname);
				removeProductCache(productId);
				/**20120307 add log*/
				comLogService.insert("PROD_PRODUCT_IMAGE_UPLOAD",productId,pk,getOperatorNameAndCheck(),
						Constant.COM_LOG_PRODUCT_EVENT.uploadProductBigPicture.name(),
						"上传了产品大图", "图片名称为[ "+picture.getPictureName() +" ]", "PROD_PRODUCT");
				/***/
			}		
			result.put("icon", StringUtils.equals("ICON", type));
			result.put("filename", filename);
		}catch(Exception ex){
			result.raise(ex);
		}		
		result.output(getResponse());
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @param fileContentType the fileContentType to set
	 */
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/**
	 * @param fileFileName the fileFileName to set
	 */
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	
	/**
	 * @return the pictureList
	 */
	public List<ComPicture> getPictureList() {
		return pictureList;
	}

	/**
	 * @param imgname the imgname to set
	 */
	public void setImgname(String imgname) {
		this.imgname = imgname;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param pictureId the pictureId to set
	 */
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
