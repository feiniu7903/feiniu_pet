package com.lvmama.comm.utils.fax;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.vo.Constant;

public class FaxSender {
	
	private Log logger=LogFactory.getLog(FaxSender.class);
	public static String PNG = "png";
	private String file;
	private String telephone;
	private String serialno;
	private String fileType;
	private Long width = 700L;
	
	public FaxSender(String telephone, String serialno, String file,String fileType) {
		this.telephone = telephone;
		this.serialno = "NEW" + serialno;
		this.file = file;
		this.fileType=fileType;
	}
 
	public boolean send() {
		try {
			File f = new File(file);
			logger.info("file exsts: "+f.exists());
			logger.info("Send fax, telephone: " + telephone + ", serialno: "+serialno + ",width:"+this.width+", file: " + f.getAbsolutePath());
			PostMethod filePost = new PostMethod(Constant.getInstance().getFaxServerUrl());
			
			Part[] parts = { new StringPart("telephone", telephone),
					new StringPart("serialno", serialno),
					new StringPart("fileType", fileType),
					new StringPart("width", this.width+""),
					new FilePart("ufile1", f) };
			filePost.setRequestEntity(new MultipartRequestEntity(parts,
					filePost.getParams()));
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
			client.getHttpConnectionManager().getParams().setSoTimeout(30000);
			int status = client.executeMethod(filePost);
			try {
				f.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (status == 200) {
				return true;
			} else {
				logger.info("ERROR, return: " + status);
				return false;
			}
			
		} catch (FileNotFoundException e1) {
			throw new RuntimeException(e1);
		} catch (HttpException e1) {
			e1.printStackTrace();
			return false;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
	}
	
	public Long getWidth() {
		return width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}
 
}
