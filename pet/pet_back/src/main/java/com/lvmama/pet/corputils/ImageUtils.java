package com.lvmama.pet.corputils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * 图片工具类
 */
public class ImageUtils {

	private ImageUtils(){}
	
	private BufferedImage image = null;
	
	int oldWidth = 0;
	int oldHeight = 0;

	int newWidth = 0;
	int newHeight = 0;

	public void load(File imageFile) throws IOException {
		image = ImageIO.read(imageFile);
	}
	
	public void load1(String imagePath) throws IOException {
		URL url = new URL(imagePath);
		image = ImageIO.read(url);
	}
	
	public int getImageWidth() {
		return image.getWidth();
	}

	public int getImageHeight() {
		return image.getHeight();
	}

	//图片裁剪
	public void cutTo(int mex,int  mey, int selx,int  sely,int x,int  y,int tarWidth, int tarHeight) throws FileNotFoundException {
		if (image == null) {
			throw new FileNotFoundException(
					"image file not be load.please execute 'load' function agin.");
		}
		if(newWidth-oldWidth<0){
			mex = mex+Math.abs(newWidth-oldWidth)/2;
		}else{
			mex = mex-Math.abs(newWidth-oldWidth)/2;
		}
		mey = mey-Math.abs(newHeight-oldHeight)/2;
		//创建一个图片缓冲区
		BufferedImage image2 = new BufferedImage(tarWidth, tarHeight, BufferedImage.TYPE_INT_BGR);
		//获取图片处理对象
		Graphics graphics = image2.getGraphics();
		//填充背景色
		graphics.setColor(Color.BLACK);
		graphics.fillRect(1, 1, tarWidth - 1, tarHeight - 1);
		//设定边框颜色
		graphics.setColor(Color.BLACK);
		graphics.drawRect(0, 0, tarWidth - 1, tarHeight - 1);

		int x2=mex-selx;
		int y2=mey-sely;
		graphics.drawImage(image, x2, y2, null); // 绘制目标图
		graphics.dispose();
		image=image2;
		x=0;
		y=0;			

		// 剪裁
		this.image = image
				.getSubimage(x, y, tarWidth, tarHeight);


	}		

	/**
	 * 	//图片缩放 不生成新的图片
	 */
	public void zoomTo(int tarWidth, int tarHeight) {
		BufferedImage tagImage = new BufferedImage(tarWidth, tarHeight,
				BufferedImage.TYPE_INT_RGB); // 缩放图像
		Image image = this.image.getScaledInstance(tarWidth, tarHeight,
				Image.SCALE_SMOOTH);
		Graphics g = tagImage.getGraphics();
		g.drawImage(image, 0, 0, null); // 绘制目标图
		g.dispose();
		this.image = tagImage;

	}
	
	/**
	 * 	//图片旋转 不生成新的图片
	 */
	public void rotateTo(int tarWidth, int tarHeight, int imageRotate) {
		oldWidth = image.getWidth();
		oldHeight = image.getHeight();
		image = Rotate(image,imageRotate);

		newWidth = image.getWidth();
		newHeight = image.getHeight();
	}

    public static BufferedImage Rotate(Image src, int angel) {  
        int src_width = src.getWidth(null);  
        int src_height = src.getHeight(null);  
        // calculate the new image size  
        Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(  
                src_width, src_height)), angel);  
  
        BufferedImage res = null;  
        res = new BufferedImage(rect_des.width, rect_des.height,  
                BufferedImage.TYPE_INT_RGB);  
        Graphics2D g2 = res.createGraphics();  
        // transform  
        g2.translate((rect_des.width - src_width) / 2,  
                (rect_des.height - src_height) / 2);  
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);  
  
        g2.drawImage(src, null, null);  
        return res;  
    }  
  
    public static Rectangle CalcRotatedSize(Rectangle src, int angel) {  
        // if angel is greater than 90 degree, we need to do some conversion  
        if (angel >= 90) {  
            if(angel / 90 % 2 == 1){  
                int temp = src.height;  
                src.height = src.width;  
                src.width = temp;  
            }  
            angel = angel % 90;  
        }  
  
        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;  
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;  
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;  
        double angel_dalta_width = Math.atan((double) src.height / src.width);  
        double angel_dalta_height = Math.atan((double) src.width / src.height);  
  
        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha  
                - angel_dalta_width));  
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha  
                - angel_dalta_height));  
        int des_width = src.width + len_dalta_width * 2;  
        int des_height = src.height + len_dalta_height * 2;  
        return new java.awt.Rectangle(new Dimension(des_width, des_height));  
    } 
	
	/**
	 * 保存
	 * @param fileName
	 * @param formatName
	 * @throws IOException
	 */
	public void save(String fileName, String formatName) throws IOException {
		// 写文件
		FileOutputStream out = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(this.image, formatName, bos);// 输出到bos
			out = new FileOutputStream(fileName);
			out.write(bos.toByteArray()); // 写文件
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * //缩放图片 生成新的图片
	 */
	public static boolean zoomImage(String srcFile, String dstFile, int width,
			int height, String formatName) {
		try {
			ImageUtils zoom = new ImageUtils();
			zoom.load(new File(srcFile));
			zoom.zoomTo(width, height);
			zoom.save(dstFile, formatName);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private static ImageUtils fromImageFile(File file, String fileName) throws IOException {
		ImageUtils utils = new ImageUtils();
		if(fileName.indexOf("http://pic.lvmama.com")==-1){
			utils.load(file);
		}else{
			utils.load1(fileName);
		}
		return utils;
	}

	/**
	 * 加载图片
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static ImageUtils load(String fileName) throws IOException {
		File file = new File(fileName);
		return fromImageFile(file,fileName);
	}
	
	
//	public static void main(String[] args) {
//		String p =  "e:/090613221071ed5df7cf2bce72.jpg";
//		ImageUtils image , image2;
//		try {
//			image = ImageUtils.load(p);
//			//image.zoomImage(p, "e:/xxx.jpg", 500, 500, "jpg");
//			image.zoomTo(500, 500);
//			image.cutTo(50,50,100, 100);
//			image.save("e:/111.jpg", "jpg");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

}
