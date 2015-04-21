package com.lvmama.pet.fax.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.junit.Test;

import com.sun.media.imageioimpl.plugins.tiff.TIFFImageReaderSpi;

public class MagickImageInfoTest {

	static{
		//System.loadLibrary("TiffFaxDLL");
		//System.loadLibrary("TiffConvertDLL");
	}
	@Test
	public static void main(String[] args)throws Exception {
		String file="d:/aa.tiff";
		BufferedImage[] bi=null;
		IIORegistry iioreg=IIORegistry.getDefaultInstance();
		iioreg.registerApplicationClasspathSpis();
		ImageReaderSpi irs = new TIFFImageReaderSpi();
		ImageReader reader = irs.createReaderInstance();
		
		ImageInputStream input = new FileImageInputStream(new File(file));
		reader.setInput(input);
		
		int pages = reader.getNumImages(true);
		
		if(pages>0){
	         bi=new BufferedImage[pages];
	         for (int i = 0; i < pages; i++) {
	             System.out.println(i);
	             try {
	                 bi[i]=reader.read(i);
	             } catch (IOException e) {
	                 // TODO Auto-generated catch block
	                 e.printStackTrace();
	             }
	             Image image = bi[i].getScaledInstance(bi[i].getWidth(), bi[i].getHeight(), Image.SCALE_DEFAULT);
	             BufferedImage tag = new BufferedImage(bi[i].getWidth(), bi[i].getHeight(), BufferedImage.TYPE_INT_BGR);
	             Graphics g = tag.getGraphics();
	             g.drawImage(image, 0, 0, null);
	             g.dispose();
	             try {
	                 //ImageIO.write(tag, "GIF", new File("C:/"+i+".gif"));
	                 ImageIO.write(tag, "JPG", new File("C:/"+i+"11.JPG"));
	             } catch (IOException e) {
	                 // TODO Auto-generated catch block
	                 e.printStackTrace();
	             }
	         }
	     }
	}

}
