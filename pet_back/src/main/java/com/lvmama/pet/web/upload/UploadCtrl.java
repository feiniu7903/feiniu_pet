package com.lvmama.pet.web.upload;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Textbox;

import com.lvmama.comm.utils.RandomFactory;
import com.lvmama.pet.utils.ZkMessage;

/**
 * 通用图片上传组件，可回传图片文件名，存放URL，如果传入Image对象则可以预览
 * 
 * @author Alex Wang
 * 
 */
public class UploadCtrl extends GenericForwardComposer<Button> {
	private static final long serialVersionUID = -5067328176187541336L;
	private static final Log LOG = LogFactory.getLog(UploadCtrl.class);

	public void doAfterCompose(Button comp) throws Exception {
		super.doAfterCompose(comp);
	}
	
	private String getWaterImg() {
		return application.getRealPath("") + File.separator	+ "img" + File.separator + "base" + File.separator;
	}

	/**
	 * 将图片上传到远程pic服务器
	 * @param event 上传事件
	 * @param furl 显示文件上传地址的文本框对象
	 * 
	 * 将图片上传到远程pic服务器，上传的图片将不会添加水印，但必须宽高比例为2:1
	 */
	public void onUpload(UploadEvent event, Textbox furl) {
		onUpload(event, furl, false, true);
	}

	/**
	 * 将图片上传到远程pic服务器
	 * @param event 上传事件
	 * @param furl 显示文件上传地址的文本框对象
	 * @param addWaterMark 是否添加水印，如果是true,将会对图片添加水印
	 * @param checkScale 是否要检查图片比例，如果是true,那么上传的图片宽高比例必须是2:1
	 */
	public void onUpload(UploadEvent event, Textbox furl, boolean addWaterMark, boolean checkScale) {
		try {
			Media media = event.getMedia();
			String filename = media.getName();
			InputStream is = media.getStreamData();
			File file = writeFile(is);
			if (file != null) {
				if (checkImgSize(file, 1024)) {
					ZkMessage.showInfo("图片大小需小于1M");
					return;
				}

				java.awt.Image image = ImageIO.read(file);
				int wh = image.getWidth(null);
				int ht = image.getHeight(null);
				
				//检查图片比例
				if (checkScale) {
					if (wh * 1.0 / ht != 2.0) {
						ZkMessage.showInfo("该图片大小为(" + wh + "x" + ht
								+ "),请上传比例为2:1的图片");
						furl.setValue(null);
						return;
					}
				}
				
				//添加水印
				if (addWaterMark) {
					com.lvmama.comm.utils.pic.UploadCtrl.processWaterMark(file, getWaterImg(), 580, 290, 430, 231);
				}
				

				String url = com.lvmama.comm.utils.pic.UploadCtrl.postToRemote(file, filename);
				LOG.info("+++++++:" + url);
				if (url != null) {
					furl.setValue(url);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 把上传后的URL置入传入的Textbox，把预览图片置入传入的Image
	 * 
	 * @param event 上传事件
	 * @param furl  显示文件上传地址的文本框对象
	 * @param myimg 显示预览图片的图片对象
	 */
	public void onUpload(UploadEvent event, Textbox furl, Image myimg) {
		Media media = event.getMedia();
		if (media instanceof org.zkoss.image.Image) {
			myimg.setContent((org.zkoss.image.Image) media);
		} else {
			ZkMessage.showInfo("请选择图片文件");
			return;
		}
		onUpload(event, furl);
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
		onUpload(event, furl);
	}

	/**
	 * 把上传后的URL置入传入的textbox 200*100 maxSize 50k
	 * 
	 * @param event
	 * @param furl
	 */
	public void onUploadMaxSize50(UploadEvent event, Textbox furl) {
		try {
			Media media = event.getMedia();
			String filename = media.getName();
			InputStream is = media.getStreamData();
			File file = writeFile(is);
			if (file != null) {
				if (checkImgSize(file, 50)) {
					ZkMessage.showInfo("图片大小需小于50K");
					return;
				}

				processImg(file);

				String url = com.lvmama.comm.utils.pic.UploadCtrl.postToRemote(file, filename);
				LOG.info("+++++++:" + url);
				if (url != null) {
					furl.setValue(url);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 上传文本文件到本地临时目录，并将地址显示在furl文本框中
	 * @param event 上传事件
	 * @param furl 显示文件上传地址的文本框对象
	 */
	public void onUploadLocalTxt(UploadEvent event, Textbox furl) {
		try{
			Media media = event.getMedia();
			byte[] bytes = media.getStringData().getBytes();
			File file = writeFile(bytes);
			if (file!=null) {
				String url = file.getAbsolutePath();
				LOG.info("+++++++:"+url);
				if (url!=null) {
					furl.setValue(url);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 上传文本文件到本地临时目录，并将地址显示在furl文本框中
	 * @param event 上传事件
	 * @param furl 显示文件上传地址的文本框对象
	 * @param filename 显示文件名
	 */
	public void onUploadLocalTxt(UploadEvent event, Textbox furl, Textbox filename) {
		Media media = event.getMedia();
		if (media != null)
			filename.setValue(media.getName());
		onUploadLocalTxt(event, furl);
	}	
	

	/**
	 * 本地上传文件流
	 * @param is 文件流
	 * @return 本地上传后的文件
	 */
	private File writeFile(InputStream is) {
		String filename = System.getProperty("java.io.tmpdir")
				+ System.getProperty("file.separator")
				+ System.currentTimeMillis() + "_"
				+ RandomFactory.generateMixed(5);
		try {
			File file = new File(filename);
			FileOutputStream fout = new FileOutputStream(file);
			byte buffer[] = new byte[1024];
			while (is.read(buffer) != -1) {
				fout.write(buffer);
			}
			fout.close();
			LOG.info("Saved file to:" + file.getAbsolutePath());
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 本地上传字节流
	 * @param bytes
	 * @return
	 */
	private File writeFile(byte[] bytes) {
		String filename = System.getProperty("java.io.tmpdir")
			+System.getProperty("file.separator")
			+System.currentTimeMillis()
			+"_"
			+RandomFactory.generateMixed(5);
		try{
			File file =new File(filename);
			FileOutputStream fout = new FileOutputStream(file);
			fout.write(bytes);
			fout.close();
			LOG.info("Saved file to:" + file.getAbsolutePath());
			return file;
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}	



	/**
	 * 将图片处理为200*100
	 * 
	 * @param file
	 */
	private void processImg(File f) {
		try {
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
			BufferedImage bid = new BufferedImage(nw, nh,
					BufferedImage.TYPE_3BYTE_BGR);
			ato.filter(bis, bid);
			ImageIO.write(bid, "jpeg", f);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 判断文件是否大于指定大小
	 * 
	 * @param file
	 * @return
	 */
	private boolean checkImgSize(File f, int picSize) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			long size = fis.available();
			fis.close();
			if (size > picSize * 1024) {
				return true;
			} else
				return false;
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return false;
	}
}
