package com.lvmama.back;

import java.awt.Dimension;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.net.URLDecoder;

import com.lvmama.comm.utils.pdf.PdfUtil;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		File file = new File("d:/aabbcc.html");
		FileReader fr = new FileReader(file);
		char buf[]=new char[1024];
		int len;
		StringBuffer sb =new StringBuffer();
		while((len=fr.read(buf, 0, 1024))>0){
			sb.append(buf,0,len);
		}
		fr.close();
		
		ByteArrayOutputStream bos=PdfUtil.createPdfFile(sb.toString());
		FileOutputStream fos=new FileOutputStream(new File("d:/aa.pdf"));
		bos.writeTo(fos);
		bos.close();
		fos.close();
	}

}
