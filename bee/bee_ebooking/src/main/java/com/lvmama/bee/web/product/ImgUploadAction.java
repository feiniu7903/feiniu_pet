package com.lvmama.bee.web.product;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.utils.UploadUtil;
import com.lvmama.comm.vo.Constant;
public class ImgUploadAction extends BaseEbkProductAction {
	private static final long serialVersionUID = 920697011722287589L; 
	private String savePath = "/editor/upload"; 
	private File imgFile; 
	private String imgFileContentType; 
	private String imgFileFileName; 
	private String id; 
	private String imgTitle; 
	private String imgWidth; 
	private String imgHeight; 
	private String imgBorder; 
	private String picSize;
	
	private String saveUrl; 
	private String imgVisitUrl;

	@Action("/upload/uploadImg")
	public void uploadImgFile() throws IOException{
		try{
		if (this.imgFileFileName != null && !"".equals(this.imgFileFileName)) {
			BufferedImage bis = ImageIO.read(imgFile);
			
			if(bis.getHeight()*1.0/bis.getWidth()!=0.75 || checkImgSize(imgFile, 1024*1024)){
				throw new Exception();
			}
			String url = Constant.getInstance().getPrefixPic();
			url +=UploadUtil.uploadFile(this.imgFile, this.imgFileFileName);
			this.imgVisitUrl= url.replace("pics", picSize+"/pics");

			this.getResponse().getWriter().write(  "<html>" );      
		    this.getResponse().getWriter().write(  "<head>" );      
		    this.getResponse().getWriter().write(  "<title>error</title>" );      
		    this.getResponse().getWriter().write(  "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">" );      
		    this.getResponse().getWriter().write(  "</head>" );      
		    this.getResponse().getWriter().write(  "<body>" );      
		    this.getResponse().getWriter().write("<script type=\"text/javascript\">alert('上传成功');parent.KE.plugin[\"image\"].insert(\""+id+"\",\""+imgVisitUrl+"\",\""+imgTitle+"\",\""+imgWidth+"\",\""+imgHeight+"\",\""+imgBorder+"\");</script>");   
//		    System.out.println("<script type=\"text/javascript\">parent.KE.plugin[\"image\"].insert(\""+id+"\",\""+imgVisitUrl+"\",\""+imgTitle+"\",\""+imgWidth+"\",\""+imgHeight+"\",\""+imgBorder+"\");</script>");   
		    this.getResponse().getWriter().write(  "</body>" );      
		    this.getResponse().getWriter().write(  "</html>" );     

			
		} 
		}catch(Exception ex){
			this.getResponse().getWriter().write(  "<html>" );      
		    this.getResponse().getWriter().write(  "<head>" );      
		    this.getResponse().getWriter().write(  "<title>error</title>" );      
		    this.getResponse().getWriter().write(  "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">" );      
		    this.getResponse().getWriter().write(  "</head>" );      
		    this.getResponse().getWriter().write(  "<body>" );      
		    this.getResponse().getWriter().write("<script type=\"text/javascript\">alert('上传出错,可能是不符合上传要求');</script>");   
//		    System.out.println("<script type=\"text/javascript\">parent.KE.plugin[\"image\"].insert(\""+id+"\",\""+imgVisitUrl+"\",\""+imgTitle+"\",\""+imgWidth+"\",\""+imgHeight+"\",\""+imgBorder+"\");</script>");   
		    this.getResponse().getWriter().write(  "</body>" );      
		    this.getResponse().getWriter().write(  "</html>" );  
		}
		//return this.SUCCESS;
	}
	
	/**
	 * 判断文件是否大于指定大小
	 * @param file
	 * @return
	 */
	public boolean checkImgSize(File f,int picSize){
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			long size = fis.available();
			System.out.println(size);
			if(size>picSize*1024){
				return true;
			}else
				return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public String getImgFileContentType() {
		return imgFileContentType;
	}

	public void setImgFileContentType(String imgFileContentType) {
		this.imgFileContentType = imgFileContentType;
	}

	public String getImgFileFileName() {
		return imgFileFileName;
	}

	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImgTitle() {
		return imgTitle;
	}

	public void setImgTitle(String imgTitle) {
		this.imgTitle = imgTitle;
	}

	public String getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(String imgWidth) {
		this.imgWidth = imgWidth;
	}

	public String getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(String imgHeight) {
		this.imgHeight = imgHeight;
	}

	public String getImgBorder() {
		return imgBorder;
	}

	public void setImgBorder(String imgBorder) {
		this.imgBorder = imgBorder;
	}

	public String getSaveUrl() {
		return saveUrl;
	}

	public void setSaveUrl(String saveUrl) {
		this.saveUrl = saveUrl;
	}

	public String getImgVisitUrl() {
		return imgVisitUrl;
	}

	public void setImgVisitUrl(String imgVisitUrl) {
		this.imgVisitUrl = imgVisitUrl;
	}

	public String getPicSize() {
		return picSize;
	}

	public void setPicSize(String picSize) {
		this.picSize = picSize;
	}

}
