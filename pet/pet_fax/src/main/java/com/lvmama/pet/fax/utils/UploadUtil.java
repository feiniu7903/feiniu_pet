package com.lvmama.pet.fax.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.media.imageioimpl.plugins.tiff.TIFFImageReaderSpi;


public class UploadUtil {
	private static final Log log = LogFactory.getLog(UploadUtil.class);
	/**
	 * 解析tiff并上传图片文件，返回以逗号分隔的图片地址组合
	 * @param pageNum
	 * @param file
	 * @param filename
	 */
	public static String getPageNumByTiff(File file) {
		StringBuffer pageNum =new StringBuffer();
		ImageInputStream input = null;
		try {			
			String prefix=file.getName().substring(0,file.getName().lastIndexOf("."));
			
			IIORegistry iioreg = IIORegistry.getDefaultInstance();
			iioreg.registerApplicationClasspathSpis();
			ImageReaderSpi irs = new TIFFImageReaderSpi();
			ImageReader reader = irs.createReaderInstance();

			input = new FileImageInputStream(file);
			reader.setInput(input);
			int pages = reader.getNumImages(true);

			if (pages > 0) {
				for (int i = 0; i < pages; i++) {
					BufferedImage bi = reader.read(i);
					Image image = bi.getScaledInstance(bi.getWidth(),
							bi.getHeight(), Image.SCALE_DEFAULT);
					BufferedImage tag = new BufferedImage(bi.getWidth(),
							bi.getHeight(), BufferedImage.TYPE_INT_BGR);
					Graphics g = tag.getGraphics();
					g.drawImage(image, 0, 0, null);
					g.dispose();
					try {
						// ImageIO.write(tag, "GIF", new File("C:/"+i+".gif"));
						File imageFile = new File(prefix + i + ".jpg");
						ImageIO.write(tag, "JPG", imageFile);

						String uploadImg = UploadUtil.uploadFile(imageFile,
								imageFile.getName());
						pageNum.append(uploadImg);
						pageNum.append(",");
						imageFile.delete();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			//input.close();
		} catch (Exception e) {
			log.error("file:"+file.getAbsolutePath());
			e.printStackTrace();
		}finally{
			if(input!=null){
				try{
					input.close();
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
		if(pageNum.length()>0){
			pageNum.setLength(pageNum.length()-1);
		}
		return pageNum.toString();
	}
	
	public static void main(String[] args) {
		String file="d:/11.tif";
		File ff=new File(file);
		String result=uploadFile(ff,"aabbcc.tif");
		System.out.println("result::::::"+result);
		FileUtil.moveTifFile(ff, "e:/aabbccdd");
	}
	
	
	public static String uploadFile(File f, String filename) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd/");
		try {
			log.info(Constant.getUploadUrl());
			PostMethod filePost = new PostMethod(Constant.getUploadUrl());
			String fileName = sf.format(new Date()) + filename;
			log.info("fileName:" + fileName);
			Part[] parts = { new StringPart("fileName", fileName),
					new FilePart("ufile", f) };
			filePost.setRequestEntity(new MultipartRequestEntity(parts,
					filePost.getParams()));
			HttpClient client = new HttpClient();
			int status = client.executeMethod(filePost);
			if (status == 200) {
				log.info("upload success");
				return fileName;
			} else {
				log.error("ERROR, return: " + status);
			}
			filePost.releaseConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }
}
