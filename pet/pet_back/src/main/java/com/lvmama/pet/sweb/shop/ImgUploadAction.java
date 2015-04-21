package com.lvmama.pet.sweb.shop;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.RandomFactory;
import com.lvmama.comm.vo.Constant;
import com.opensymphony.xwork2.ActionSupport;

public class ImgUploadAction extends ActionSupport {
	private static final long serialVersionUID = -8864954272802650553L;
	
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
	public void uploadImgFile() throws IOException {
		getResponse().setContentType("text/html; charset=utf-8");

		try {
			if (this.imgFileFileName != null
					&& !"".equals(this.imgFileFileName)) {
				BufferedImage bis = ImageIO.read(imgFile);

				String url = Constant.getInstance().getPrefixPic();
				url += this.postToRemote(this.imgFile, this.imgFileFileName);
				this.imgVisitUrl = url.replace("pics", picSize + "/pics");

				getResponse()
				.getWriter()
				.write("{\"error\":0,\"url\":\"" + url + "\"}");
			}
		} catch (Exception ex) {
			getResponse()
			.getWriter()
			.write("{\"error\":1,\"message\":\"上传出错啦！\"}");
		}
	}

	/**
	 * 把指定的文件上传到专用的静态文件服务器上，返回URL
	 * 
	 * @param file
	 * @return
	 */
	public String postToRemote(File f, String filename) {
		try {
			String ext = filename.substring(filename.lastIndexOf('.'))
					.toLowerCase();
//			PostMethod filePost = new PostMethod(Constant.getInstance()
//					.getUploadUrl());
			String fileName = "super/"
					+ DateUtil.getFormatDate(new Date(), "yyyy/MM/")
					+ RandomFactory.generateMixed(5) + ext;
//			Part[] parts = { new StringPart("fileName", fileName),
//					new FilePart("ufile", f) };
//			filePost.setRequestEntity(new MultipartRequestEntity(parts,
//					filePost.getParams()));
//			HttpClient client = new HttpClient();
//			int status = client.executeMethod(filePost);
			Map<String, File> requestFiles = new HashMap<String, File>();
			requestFiles.put("ufile", f);
			Map<String, String> requestParas = new HashMap<String, String>();
			requestParas.put("fileName", fileName);
			int status = HttpsUtil.requestPostUpload(Constant.getInstance().getUploadUrl(), requestFiles, requestParas).getStatusCodeAndClose();
			if (status == 200) {
				return fileName;
			} else {
				System.out.println("ERROR, return: " + status);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
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
