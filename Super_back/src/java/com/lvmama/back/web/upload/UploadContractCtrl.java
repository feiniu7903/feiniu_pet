package com.lvmama.back.web.upload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.api.Textbox;

import com.lvmama.back.utils.ZkMessage;

public class UploadContractCtrl extends UploadCtrl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3607830078081759932L;
	
	private static final Logger LOG = Logger.getLogger(UploadContractCtrl.class);
	public void onUpload(UploadEvent event, Textbox furl, Textbox filename) {
		try{
			Media media = event.getMedia();
			String fn = media.getName();
	 		byte[] bytes=null;
 			try{
 	 			InputStream is = media.getStreamData();
 	 			bytes=InputStreamToByte(is);
 			}catch(Exception e){
 				bytes = media.getStringData().getBytes();
 			}
 			File file = writeFile(bytes,fn);
			if (file!=null) {
			//	String url = postToRemote(file, fn); 
				String url = file.getAbsolutePath();
				if (url!=null) { 
					furl.setValue(url); 
				}else{
					url = file.getAbsolutePath();
					furl.setValue(url);
				}
				filename.setValue(fn);
			}
		}catch(Exception e) {
			LOG.warn("上传文件失败:\r\n"+e.getMessage());
			ZkMessage.showWarning("上传文件失败");
		}
	}
    private byte[] InputStreamToByte(InputStream is) throws IOException {  
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();  
        int ch;  
        while ((ch = is.read()) != -1) {  
         bytestream.write(ch);  
        }  
        byte imgdata[] = bytestream.toByteArray();  
        bytestream.close();  
        return imgdata;  
    }  
	private File writeFile(byte[] bytes,String fileName) {
		String filename = System.getProperty("java.io.tmpdir")
			+System.getProperty("file.separator")
			+fileName;
		try{
			File file =new File(filename);
			FileOutputStream fout = new FileOutputStream(file);
			fout.write(bytes);
			fout.close();
			return file;
		}catch(IOException e) {
			LOG.warn("上传文件写文件失败:\r\n"+e.getMessage());
		}
		return null;
	}	
}
