package com.lvmama.comm.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import com.lvmama.comm.vo.Constant;

public class UploadUtil {
	
	public static String uploadFile(File f, String filename) {
		try{
			String ext = filename.substring(filename.lastIndexOf('.')).toLowerCase();
//			PostMethod filePost = new PostMethod(Constant.getInstance().getUploadUrl());
			String fileName = "super/"+DateUtil.getFormatDate(new Date(), "yyyy/MM/")+RandomFactory.generateMixed(5)+ext;
			System.out.println("fileName:" + fileName);
//			Part[] parts = { new StringPart("fileName", fileName), new FilePart("ufile", f) };
//			filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
//			HttpClient client = new HttpClient();
//			client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
//			client.getHttpConnectionManager().getParams().setSoTimeout(30000);
//			int status = client.executeMethod(filePost);
			Map<String, File> requestFiles = new HashMap<String, File>();
			requestFiles.put("ufile", f);
			Map<String, String> requestParas = new HashMap<String, String>();
			requestParas.put("fileName", fileName);
			int status = HttpsUtil.requestPostUpload(Constant.getInstance().getUploadUrl(), requestFiles, requestParas).getStatusCodeAndClose();
			if (status == 200) {
				System.out.println("upload success");
				return fileName;
			} else {
				System.out.println("ERROR, return: " + status);
			}
			}catch(IOException e) {
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
