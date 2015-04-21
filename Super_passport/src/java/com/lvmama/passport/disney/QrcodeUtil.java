package com.lvmama.passport.disney;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
public class QrcodeUtil {
	static QrcodeUtil qrcodeUtil ;
	public static QrcodeUtil init(){
		if(qrcodeUtil==null){
			qrcodeUtil = new QrcodeUtil();
		}
		return qrcodeUtil;
	}
	
	public File uploadQrCode(byte[] bytes,String attName){
		try{
			File file=writeFile(bytes,attName);
			return file;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private File writeFile(byte[] bytes,String attName) {
		String filename =System.getProperty("java.io.tmpdir")
				+System.getProperty("file.separator")
				+attName+".pdf";
		try{
			File file =new File(filename);
			FileOutputStream fout = new FileOutputStream(file);
			fout.write(bytes);
			fout.close();
			return file;
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}	

}
