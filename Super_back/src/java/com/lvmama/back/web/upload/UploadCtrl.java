package com.lvmama.back.web.upload;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.api.Image;
import org.zkoss.zul.api.Textbox;

import com.lvmama.back.utils.ImageUtil;
import com.lvmama.back.utils.ZkMessage;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.RandomFactory;
import com.lvmama.comm.vo.Constant;

/**
 * 通用图片上传组件，可回传图片文件名，存放URL，如果传入Image对象则可以预览
 * 
 * @author Alex Wang
 * 
 */
public class UploadCtrl extends GenericForwardComposer {
	private static final long serialVersionUID = -5067328176187541336L;
	private boolean addWaterMark=false;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}
	/**
	 * 把上传后的URL置入传入的textbox
	 * 
	 * @param event
	 * @param furl
	 */
	public void onUpload(UploadEvent event, Textbox furl) {
		try{
			Media media = event.getMedia();
			String filename = media.getName();
 			InputStream is = media.getStreamData();
			File file = writeFile(is);
			if (file!=null) {
				if(checkImgSize(file,1024)){ 
					ZkMessage.showInfo("图片大小需小于1M"); 
					return; 
					}

				java.awt.Image image = ImageIO.read(file);
				int wh = image.getWidth(null);
				int ht = image.getHeight(null);
				if (wh * 1.0 / ht != 2.0) {
					ZkMessage.showInfo("该图片大小为(" + wh + "x" + ht
							+ "),请上传比例为2:1的图片");
					furl.setValue(null);
					return;
				}
				processWaterMark(file, addWaterMark, 580, 290, 430, 231);
				
				String url = postToRemote(file, filename); 
				System.out.println("+++++++:"+url); 
				if (url!=null) { 
					furl.setValue(url); 
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
 
	/**
	 * @param event
	 * @param furl
	 * @param addWaterMark 是否添加水印
	 */
	public void onUpload(UploadEvent event, Textbox furl,boolean addWaterMark) {
		this.addWaterMark=addWaterMark;
		onUpload(event,furl);
	}
	/**
	 * 把上传后的URL置入传入的Textbox，把预览图片置入传入的Image
	 * 
	 * @param event
	 * @param furl
	 * @param myimg
	 */
	public void onUpload(UploadEvent event, Textbox furl, Image myimg) {
		Media media = event.getMedia();
		if (media instanceof org.zkoss.image.Image) {
			myimg.setContent((org.zkoss.image.Image) media);
		}else{
			ZkMessage.showInfo("请选择图片文件");
			return;
		}
		onUpload(event,furl);
	}
	/**
	 * 把上传后的URL置入传入的第一个Textbox，把文件名置入传入的第二个textbox
	 * 
	 * @param event
	 * @param furl
	 * @param filename
	 */
	public void onUpload(UploadEvent event, Textbox furl, Textbox filename) {
		Media media = event.getMedia();
		if (media != null)
			filename.setValue(media.getName());
		onUpload(event,furl);
	}
	/**
	 * 把上传后的URL置入传入的textbox
	 * 200*100
	 * maxSize 50k
	 * @param event
	 * @param furl
	 */
	public void onUploadMaxSize50(UploadEvent event, Textbox furl) {
		try{
			Media media = event.getMedia();
			String filename = media.getName();
			InputStream is = media.getStreamData();
			File file = writeFile(is);
			if (file!=null) {
				if(checkImgSize(file,50)){
					ZkMessage.showInfo("图片大小需小于50K");
					return;
				}

				processImg(file);
				
				String url = postToRemote(file, filename);
				System.out.println("+++++++:"+url);
				if (url!=null) {
					furl.setValue(url);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param is
	 * @return
	 */
	private File writeFile(InputStream is) {
		String filename = System.getProperty("java.io.tmpdir")
			+System.getProperty("file.separator")
			+System.currentTimeMillis()
			+"_"
			+RandomFactory.generateMixed(5);
		try{
			File file =new File(filename);
			FileOutputStream fout = new FileOutputStream(file);
			byte buffer[] = new byte[1024];
			while (is.read(buffer)!=-1) {
				fout.write(buffer);
			}
			fout.close();
			System.out.println("Saved file to:" + file.getAbsolutePath());
			return file;
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把指定的文件上传到专用的静态文件服务器上，返回URL
	 * 
	 * @param file
	 * @return
	 */
	public String postToRemote(File f, String fileName) {
		/*try{
			String ext = filename.substring(filename.lastIndexOf('.')).toLowerCase();
			PostMethod filePost = new PostMethod(Constant.getInstance().getUploadUrl());
			String fileName = "super/"+DateUtil.getFormatDate(new Date(), "yyyy/MM/")+RandomFactory.generateMixed(5)+ext;
			Part[] parts = { new StringPart("fileName", fileName), new FilePart("ufile", f) };
			filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
			client.getHttpConnectionManager().getParams().setSoTimeout(30000);
			int status = client.executeMethod(filePost);
			if (status == 200) {
				System.out.println("upload success");
				return fileName;
			} else {
				System.out.println("ERROR, return: " + status);
			}
			}catch(IOException e) {
				e.printStackTrace();
			}
		return null;*/
		try {
			String ext = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();
			String fullFileName = "super/"+DateUtil.getFormatDate(new Date(), "yyyy/MM/")+RandomFactory.generateMixed(5)+ext;
			return com.lvmama.comm.utils.pic.UploadCtrl.postToRemote(f, fullFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将图片处理为200*100
	 * @param file
	 */
	public void processImg(File f){
		
		try {
            AffineTransform transform = new AffineTransform();
            BufferedImage bis = ImageIO.read(f);
            int w = bis.getWidth();
            int h = bis.getHeight();
            
            int nw = 200;
            int nh = 100;
            
            double sx = (double)nw / w;
            double sy = (double)nh / h;
            transform.setToScale(sx,sy);
            AffineTransformOp ato = new AffineTransformOp(transform, null);
            BufferedImage bid = new BufferedImage(nw, nh, BufferedImage.TYPE_3BYTE_BGR);
            ato.filter(bis,bid);
            ImageIO.write(bid, "jpeg", f);
        } catch(Exception e) {
            e.printStackTrace();
        }
		
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
//			System.out.println(size);
			if(size>picSize*1024){
				return true;
			}else
				return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 处理图片水印，如压缩尺寸长宽为0，则不改变图片大小
	 * @param f
	 * @param addWaterMark
	 * @param processWidth
	 * @param processHeight
	 */
	public void processWaterMark(File f,boolean addWaterMark,int processWidth,int processHeight,int waterMarkX,int waterMarkY){
		String targetImg = f.getAbsolutePath();
        //改变图片大小
         if(processWidth!=0&&processHeight!=0){
         	ImageUtil.resizeImage(targetImg, targetImg, processWidth, processHeight);
         }
         //設置產品水印
         if(addWaterMark){
         	String waterImg=application.getRealPath("")+File.separator+"img"+File.separator+"base"+File.separator;
         	File waterImgFile=null;
         	
         	waterImgFile=new File(waterImg+waterMarkX+"x"+waterMarkY+".png");
 			ImageUtil.pressImage(targetImg,waterImgFile , waterMarkX, waterMarkY, 1, 1);
         	
         }
	}
	/**
	 * 新添加，调用该方法一定会生成水印.水印目录由调用方传入
	 * @param f
	 * @param waterImg 水印的根地址
	 * @param processWidth
	 * @param processHeight
	 * @param waterMarkX
	 * @param waterMarkY
	 */
	public void processWaterMark(File f, String waterImg, int processWidth,
			int processHeight, int waterMarkX, int waterMarkY) {
		String targetImg = f.getAbsolutePath();
		// 改变图片大小
		if (processWidth != 0 && processHeight != 0) {
			ImageUtil.resizeImage(targetImg, targetImg, processWidth,
					processHeight);
		}
		// 設置產品水印
		// String
		// waterImg=application.getRealPath("")+File.separator+"img"+File.separator+"base"+File.separator;
		File waterImgFile = null;

		waterImgFile = new File(waterImg, waterMarkX + "x" + waterMarkY
				+ ".png");
		ImageUtil.pressImage(targetImg, waterImgFile, waterMarkX, waterMarkY,
				1, 1);
	}
	

}
