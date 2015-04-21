package com.lvmama.pet.fax.servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xhtmlrenderer.simple.ImageRenderer;

import com.lvmama.pet.fax.dao.TrafaxStatusDao;
import com.lvmama.pet.fax.utils.Constant;
import com.lvmama.pet.fax.utils.MagickImageOp;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(UploadServlet.class);
	private String uploadPath = Constant.getTempDirectory();
	
	File tempPathFile;

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(4096);
			factory.setRepository(tempPathFile);
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(4194304);
			Map map = new HashMap();
			
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> i = items.iterator();
			String destFilePath = null;
			String fileName = "TEMP";
			String fieldName = null;
			FileItem fi = null;
			while (i.hasNext()) {
				fi = i.next();
				fieldName = fi.getFieldName();
				log.info("fieldName:"+fi.getFieldName());
				if (fieldName.matches("serialno")) {
					fileName=fieldName;
				}
				if (fieldName.matches("ufile.*")) {
					File saveFile = new File(uploadPath, fileName);
					log.info("Saved FAX FILE to "+saveFile.getAbsolutePath());
					fi.write(saveFile);
					
					if (Constant.isTraFaxServerSend()) {
						destFilePath = Constant.getFaxDirectory()+"/"+map.get("serialno");
						if(map.get("fileType") != null && "png".equalsIgnoreCase(map.get("fileType").toString())){
							destFilePath += ".png";
							int width = NumberUtils.toInt(map.get("width").toString());
							ImageRenderer.renderToImage(saveFile, destFilePath, width);
							map.put("filename", map.get("serialno")+".png");
							if(MagickImageOp.isWinOS()){
								String tiffImage = null;
								log.debug("width:"+width);
								if (width > 700) {
									tiffImage = MagickImageOp.getInstance().buildHTiff(destFilePath);
								} else {
									tiffImage = MagickImageOp.getInstance().buildVTiff(destFilePath);
								}
								map.put("filename", tiffImage.substring(tiffImage.lastIndexOf("/")+1));
								new File(destFilePath).delete();
							}
							saveFile.delete();
						}else {
							destFilePath += ".xls";
							File dest = new File(destFilePath);
							saveFile.renameTo(dest);
							map.put("filename", dest.getName());
						}
						log.info("destFilePath:"+destFilePath);
						TrafaxStatusDao statusDao = (TrafaxStatusDao)ServiceContext.getBean("trafaxStatusDao");
						statusDao.addFaxOut(map);
					}else{
						FaxWriter faxWriter = new FaxWriter();
						faxWriter.write(map, saveFile);
					}
				}else{
					log.info(fi.getFieldName()+"="+fi.getString());
					map.put(fieldName, fi.getString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public void init() throws ServletException {
		File uploadFile1 = new File(uploadPath);
		if (!uploadFile1.exists()) {
			uploadFile1.mkdirs();
		}
		
//		if(Constant.isImageMagick() && isWinOS()){
//			System.loadLibrary("TiffFaxDLL");
//		}
	}
}