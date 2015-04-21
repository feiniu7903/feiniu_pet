package com.lvmama.comm.utils.pic;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.RandomFactory;
import com.lvmama.comm.vo.Constant;

/**
 * 通用图片上传组件
 * 
 * @author ranlongfei 2012-8-1
 * @version
 */
public class UploadCtrl {
	/**
	 * 文件路径
	 */
	private static final String FILE_PATH = "super/";

	/**
	 * 把指定的文件上传到专用的静态文件服务器上，返回URL
	 * 
	 * @author: ranlongfei 2012-8-1 下午3:11:55
	 * @param file
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String postToRemote(File file, String fileFullName) throws Exception {
//		PostMethod filePost = new PostMethod(Constant.getInstance().getUploadUrl());
//		Part[] parts = { new StringPart("fileName", fileFullName), new FilePart("ufile", file) };
//		filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
//		int status = new HttpClient().executeMethod(filePost);
		Map<String, File> requestFiles = new HashMap<String, File>();
		requestFiles.put("ufile", file);
		Map<String, String> requestParas = new HashMap<String, String>();
		requestParas.put("fileName", fileFullName);
		int status = HttpsUtil.requestPostUpload(Constant.getInstance().getUploadUrl(), requestFiles, requestParas).getStatusCodeAndClose();
		if (status == 200) {
			return fileFullName;
		} else {
			throw new Exception("上传失败，HttpStatus：" + status);
		}
	}

	/**
	 * 把指定的文件上传到专用的静态文件服务器上，返回URL
	 * 
	 * @author: ranlongfei 2012-8-1 下午6:12:32
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String postToRemote(File file) throws Exception {
		return postToRemote2(file, file.getName());
	}
	
	/**
	 * 由调用方指定文件名，根据文件字取后面的文件后辍
	 * @param file
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String postToRemote2(File file,String fileName)throws Exception{
		int subLength = fileName.lastIndexOf('.');
		String ext = "";
		if(subLength > 0) {
			ext = fileName.substring(subLength).toLowerCase();
		}
		String fileFullName = FILE_PATH + DateUtil.getFormatDate(new Date(), "yyyy/MM/") + RandomFactory.generateMixed(5) + ext;
		return postToRemote(file, fileFullName);
	}
	
	public static String postFaxFileToRemote(File file,String fileName) throws Exception{
		String fileFullName =  DateUtil.formatDate(new Date(), "yyyy/MM/dd/") + fileName;
		
		Map<String, File> requestFiles = new HashMap<String, File>();
		requestFiles.put("ufile", file);
		Map<String, String> requestParas = new HashMap<String, String>();
		requestParas.put("fileName", fileFullName);
		int status = HttpsUtil.requestPostUpload(Constant.getInstance().getFaxUploadUrl(), requestFiles, requestParas).getStatusCodeAndClose();
		if (status == 200) {
			return fileFullName;
		} else {
			throw new Exception("上传失败，HttpStatus：" + status);
		}
	}
	

	/**
	 * 将图片处理为200*100
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static void processImg(File f) throws IOException {
		AffineTransform transform = new AffineTransform();
		BufferedImage bis = ImageIO.read(f);
		int w = bis.getWidth();
		int h = bis.getHeight();

		int nw = 200;
		int nh = 100;

		double sx = (double) nw / w;
		double sy = (double) nh / h;
		transform.setToScale(sx, sy);
		AffineTransformOp ato = new AffineTransformOp(transform, null);
		BufferedImage bid = new BufferedImage(nw, nh, BufferedImage.TYPE_3BYTE_BGR);
		ato.filter(bis, bid);
		ImageIO.write(bid, "jpeg", f);
	}

	/**
	 * 限制图片的宽度和长度. 
	 * @param f    图片 
	 * @param width  宽度 
	 * @param height  高度 
	 * @return true  不合格 返回true ，合格返回false . 
	 * @throws IOException
	 */
	public static boolean checedImgWidthAndHeight(File f,int width,int height) throws IOException {
		BufferedImage bis = ImageIO.read(f);
		int w = bis.getWidth();
		int h = bis.getHeight();
		if(w > width ||  h > height ) {
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * 判断文件是否大于指定大小
	 * 
	 * @param file
	 * @return
	 */
	public static boolean checkImgSize(File f, int picSize) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);
			long size = fis.available();
			// System.out.println(size);
			if (size > picSize * 1024) {
				return true;
			} else
				return false;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 生成水印.水印目录由调用方传入
	 * 
	 * @param f
	 * @param waterImg
	 *            水印的根地址
	 * @param processWidth
	 * @param processHeight
	 * @param waterMarkX
	 * @param waterMarkY
	 */
	public static void processWaterMark(File f, String waterImg, int processWidth, int processHeight, int waterMarkX, int waterMarkY) {
		String targetImg = f.getAbsolutePath();
		// 改变图片大小
		if (processWidth != 0 && processHeight != 0) {
			ImageUtil.resizeImage(targetImg, targetImg, processWidth, processHeight);
		}
		// 设置产品水印
		// String
		// waterImg=application.getRealPath("")+File.separator+"img"+File.separator+"base"+File.separator;
		File waterImgFile = null;

		waterImgFile = new File(waterImg, waterMarkX + "x" + waterMarkY + ".png");
		ImageUtil.pressImage(targetImg, waterImgFile, waterMarkX, waterMarkY, 1, 1);
	}
	static class UTFPostMethod extends PostMethod{

		public UTFPostMethod() {
			super();
			// TODO Auto-generated constructor stub
		}

		public UTFPostMethod(String uri) {
			super(uri);
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getRequestCharSet() {
			// TODO Auto-generated method stub
			//return super.getRequestCharSet();
			return "UTF-8";
		}
		
	} 
	public static void main(String a[])throws Exception {
		File file = new File("E:\\workspace4\\maven.");
		int subLength = file.getName().lastIndexOf('.');
		System.out.println(subLength);
		String ext = file.getName().substring(subLength).toLowerCase();
		System.out.println(ext);
		file = new File("d:/中国文字.txt");
		//String name = postToRemote(file);
		PostMethod filePost = new UTFPostMethod("http://192.168.0.245/upaction");
//		filePost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"GBK");
		Part[] parts = { new StringPart("fileName", "super/中国文字.txt"), new FilePart("ufile", file) };
		filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
		int status = new HttpClient().executeMethod(filePost);
		if (status == 200) {
//			System.out.println(fileFullName);
			System.out.println("OK");
		} else {
			throw new Exception("上传失败，HttpStatus：" + status);
		}
	}
}
