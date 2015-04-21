
package com.lvmama.passport.processor.impl.client.gmedia;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Base64编码
 * @author chenlinjun
 *
 */
public class Base64 {
	/**
	 * 编码
	 * 
	 * @param bstr
	 * @return String 10.
	 */
	public static String encode(byte[] bstr) {
		return new sun.misc.BASE64Encoder().encode(bstr);
	}

	/**
	 * 解码
	 * 
	 * @param str
	 * @return string
	 */
	public static byte[] decode(String str) {
		byte[] bt = null;
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			bt = decoder.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bt;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String aa = "AAAgIP//////////9VVVV/UpxfP3Bnz/8R7Fg/VnnQ/yqfDD9repJ/ZwrLvyLce/8XOcw/ECrbfwZgVT9tvST/BWr7v3py6f9GYpW/FwPMf3QXcz8A4xL/OrUuv2dujv8Qjq2/IoOF/ycyRr9eLKV/AAAAP/////////////////////";
		byte[] img= Base64.decode(aa);
		  ByteArrayInputStream bytIn = null;
		  FileOutputStream fileOut = null;
		  FileInputStream in = null;
		  try {
		   bytIn = new ByteArrayInputStream(img);
		   BufferedImage imgIn = ImageIO.read(bytIn);
		  // File file=File.createTempFile("tempFile", null);
		   fileOut = new FileOutputStream("D:/tttt3.gif");
		   ImageIO.write(imgIn, "GIF", fileOut);
//		   byte[] temp=new byte[(int)file.length()];
//		   in=new FileInputStream(file);
//		   in.read(temp);
		   //System.out.print(temp);
		  } catch (Exception e) {
		   e.printStackTrace();
		  }finally{
		   try{
			if(bytIn!=null)bytIn.close();
		    if(fileOut!=null) fileOut.close();
		    if(in!=null) in.close();
		   }catch(Exception e){
		    e.printStackTrace();
		   }
		  }
	}

}
