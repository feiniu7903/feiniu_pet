package com.lvmama.back.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {
	   /**  
     * 添加图片水印  
     * @param targetImg 目标图片路径，如：C:\\myPictrue\\1.jpg  
     * @param waterImg  水印图片路径，如：C:\\myPictrue\\logo.png  
     * @param x 水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间  
     * @param y 水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间  
     * @param alpha 透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)  
     * * @param quality  压缩清晰度 <b>建议为1.0</b>
     */  
	  public final static void pressImage(String targetImg, File waterImg, int x, int y, float alpha,float quality) {   
          try {   
              File file = new File(targetImg);   
              Image image = ImageIO.read(file);   
              int width = image.getWidth(null);   
              int height = image.getHeight(null);   
              BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);   
              Graphics2D g = bufferedImage.createGraphics();   
              g.drawImage(image, 0, 0, width, height, null);   
             
              Image waterImage = ImageIO.read(waterImg);    // 水印文件   
              int width_1 = waterImage.getWidth(null);   
              int height_1 = waterImage.getHeight(null);   
              g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));   
                 
              int widthDiff = width - width_1;   
              int heightDiff = height - height_1;   
              if(x < 0){   
                  x = widthDiff / 2;   
              }else if(x > widthDiff){   
                  x = widthDiff;   
              }   
              if(y < 0){   
                  y = heightDiff / 2;   
              }else if(y > heightDiff){   
                  y = heightDiff;   
              }   
              g.drawImage(waterImage, x, y, width_1, height_1, null); // 水印文件结束   
              g.dispose();   
              /** 压缩之后临时存放位置 */
              FileOutputStream out = new FileOutputStream(targetImg);

              JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
              JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(bufferedImage);
              /** 压缩质量 */
              jep.setQuality(quality, true);
              encoder.encode(bufferedImage, jep);
              out.close();
          } catch (IOException e) {   
              e.printStackTrace();   
          }   
  }   
	  /**   
	     * 把图片印刷到图片上
	     * @param pressImg -- 水印文件
	     * @param targetImg -- 目标文件
	     * @param x
	     * @param y
	     */   
	    public final static void pressImage(String targetImg, String pressImg) {    
	        try {    
	            File _file = new File(targetImg);    
	            Image src = ImageIO.read(_file);    
	            int wideth = src.getWidth(null);    
	            int height = src.getHeight(null);   
	            if(wideth>=200){
	            BufferedImage image = new BufferedImage(wideth, height,    
	                    BufferedImage.TYPE_INT_RGB);    
	            Graphics g = image.createGraphics();    
	            g.drawImage(src, 0, 0, wideth, height, null);    
	           
	            // 水印文件    
	            File _filebiao = new File(pressImg);    
	            Image src_biao = ImageIO.read(_filebiao);    
	            int wideth_biao = src_biao.getWidth(null);    
	            int height_biao = src_biao.getHeight(null);                               
	            g.drawImage(src_biao,wideth-wideth_biao,height_biao-35, wideth_biao, height_biao, null);               
	            g.dispose();    
	            FileOutputStream out = new FileOutputStream(targetImg);    
	            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);    
	            encoder.encode(image);    
	            out.close();
	            }
	        } catch (Exception e) {    
	            e.printStackTrace();    
	        }    
	    }    
    /**   
     * 把图片印刷到图片上
     * @param pressImg -- 水印文件
     * @param targetImg -- 目标文件
     * @param x
     * @param y
     */   
    public final static void pressImage(String pressImg,String targetImg,int x,int y) {    
        try {    
            File _file = new File(targetImg);    
            Image src = ImageIO.read(_file);    
            int wideth = src.getWidth(null);    
            int height = src.getHeight(null);   
            if(wideth>=200){
            BufferedImage image = new BufferedImage(wideth, height,    
                    BufferedImage.TYPE_INT_RGB);    
            Graphics g = image.getGraphics();    
            g.drawImage(src.getScaledInstance(wideth, height,  Image.SCALE_SMOOTH), 0, 0, wideth, height, null);    
           
            // 水印文件    
            File _filebiao = new File(pressImg);    
            Image src_biao = ImageIO.read(_filebiao);    
            int wideth_biao = src_biao.getWidth(null);    
            int height_biao = src_biao.getHeight(null);
            g.drawImage(src.getScaledInstance(wideth_biao, height_biao,  Image.SCALE_SMOOTH),wideth-x,height-y, wideth_biao, height_biao, null);               
            g.dispose();    
            FileOutputStream out = new FileOutputStream(targetImg);    
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);    
            encoder.encode(image);    
            out.close();
            }
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
    }    
   
    /**   
     * 打印文字水印图片
     * @param pressText--文字   
     * @param targetImg --目标图片   
     * @param fontName -- 字体名   
     * @param fontStyle -- 字体样式   
     * @param color -- 字体颜色   
     * @param fontSize --字体大小   
     * @param x --   偏移量   
     * @param y   
     */   
   
    public static void pressText(String pressText, String targetImg,    
            String fontName, int fontStyle, Color color, int fontSize, int x,    
            int y) {    
        try {    
            File _file = new File(targetImg);    
            Image src = ImageIO.read(_file);    
            int wideth = src.getWidth(null);    
            int height = src.getHeight(null);
            //当图片的宽度大于200的时候就进行打水印,否则不与进行,整个图片样式会被改变
            //huangl修改
            if(wideth>=200){
            	BufferedImage image = new BufferedImage(wideth, height,BufferedImage.TYPE_INT_RGB);    
                Graphics g = image.createGraphics();    
                g.drawImage(src, 0, 0, wideth, height, null);    
                g.setColor(color);    
                g.setFont(new Font(fontName, fontStyle, fontSize));            
       
                g.drawString(pressText, wideth - fontSize - x, height - fontSize  / 2 - y);    
                g.dispose();    
                FileOutputStream out = new FileOutputStream(targetImg);    
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);    
                encoder.encode(image);    
                out.close();    
       
            }
        } catch (Exception e) {    
        	e.printStackTrace();   
        }    
    }
    
    /**
     * 建立图片的缩略图
     * @param srcFile 原文件 (绝对地址)
     * @param destFile 目标文件 (绝对地址)
     * @param width 目标文件的宽度
     * @param height 目标文件的高度
     */
    public static void resizeImage(String srcFile,String destFile,int width,int height){
    	ImageScaleUtil is = new ImageScaleUtil();
    	File fIn = new File(srcFile);
    	File fOut = new File(destFile);
    	try{
	    	BufferedImage srcBufferImage = ImageIO.read(fIn);
	    	BufferedImage ret = is.imageZoomOut(srcBufferImage, width, height);
	    	ImageIO.write(ret, "JPG", fOut); // 设定输入文件类型是JPG
    	}catch(FileNotFoundException e){
    		e.printStackTrace(); 
    	}catch(IOException e){
    		e.printStackTrace();  
    	}
    }


    /**
     * 图像切割
     * 
     * @param srcImageFile
     *            源图像地址
     * @param descDir
     *            切片目标文件夹
     * @param destWidth
     *            目标切片宽度
     * @param destHeight
     *            目标切片高度
     */
    public static void cut(String srcImageFile, String descDir, int destWidth,
      int destHeight) {
     try {
      Image img;
      ImageFilter cropFilter;
      // 读取源图像
      BufferedImage bi = ImageIO.read(new File(srcImageFile));
      int srcWidth = bi.getHeight(); // 源图宽度
      int srcHeight = bi.getWidth(); // 源图高度
      if (srcWidth > destWidth && srcHeight > destHeight) {
       Image image = bi.getScaledInstance(srcWidth, srcHeight,
         Image.SCALE_DEFAULT);
       destWidth = 200; // 切片宽度
       destHeight = 150; // 切片高度
       int cols = 0; // 切片横向数量
       int rows = 0; // 切片纵向数量
       // 计算切片的横向和纵向数量
       if (srcWidth % destWidth == 0) {
        cols = srcWidth / destWidth;
       } else {
        cols = (int) Math.floor(srcWidth / destWidth) + 1;
       }
       if (srcHeight % destHeight == 0) {
        rows = srcHeight / destHeight;
       } else {
        rows = (int) Math.floor(srcHeight / destHeight) + 1;
       }
       // 循环建立切片
       // 改进的想法:是否可用多线程加快切割速度
       for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
         // 四个参数分别为图像起点坐标和宽高
         // 即: CropImageFilter(int x,int y,int width,int height)
         cropFilter = new CropImageFilter(j * 200, i * 150,
           destWidth, destHeight);
         img = Toolkit.getDefaultToolkit().createImage(
           new FilteredImageSource(image.getSource(),
             cropFilter));
         BufferedImage tag = new BufferedImage(destWidth,
           destHeight, BufferedImage.TYPE_INT_RGB);
         Graphics g = tag.getGraphics();
         g.drawImage(img, 0, 0, null); // 绘制缩小后的图
         g.dispose();
         // 输出为文件
         ImageIO.write(tag, "JPEG", new File(descDir
           + "pre_map_" + i + "_" + j + ".jpg"));
        }
       }
      }
     } catch (Exception e) {
      e.printStackTrace();
     }
    }
    /**
     * 图像类型转换
        * GIF->JPG GIF->PNG PNG->JPG PNG->GIF(X)
        */
    public static void convert(String source, String result) {
     try {
      File f = new File(source);
      f.canRead();
      f.canWrite();
      BufferedImage src = ImageIO.read(f);
      ImageIO.write(src, "JPG", new File(result));
     } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
     }
    }
   
    public static void main(String[] args) {
    	ImageUtil i = new ImageUtil();
    	//i.pressImage("D:\\printImgTest\\jingqu_detail_alpha_bg.png","D:\\printImgTest\\ff8080811b6e582e011b730d64d019e4_648x297[1].jpg", 96, 300,1f,0.95f);
    	//i.pressImage("D:\\printImgTest\\jingqu_detail_alpha_bg.png","D:\\printImgTest\\ff8080811b6e582e011b730d64d019e4_648x297[1].jpg");   ;
    }
}
