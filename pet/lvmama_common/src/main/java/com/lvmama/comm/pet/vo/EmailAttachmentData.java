package com.lvmama.comm.pet.vo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;

/**
 * 邮件附件转换
 * @author ruanxiequan
 *
 */
public class EmailAttachmentData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3389938089378598551L;
	private String fileName;
	private String mimetype;
	private byte[] data;
	
	public EmailAttachmentData(File file){
		this.fileName=file.getName();
		this.data=toBytes(file);
		this.mimetype="application/octet-stream";
	}
	public EmailAttachmentData(String fileName,byte[] data,String mimetype){
		this.fileName=fileName;
		this.data=data;
		this.mimetype=mimetype;
	}
	public String getMimetype() {
		return mimetype;
	}
	public byte[] getData() {
		return data;
	}
	public String getFileName() {
		return fileName;
	}
	private byte[] toBytes(File file) {
		byte[] b = new byte[1024];
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			FileInputStream fi = new FileInputStream(file);
			int n;  
			while((n=fi.read(b))!=-1){
				bos.write(b,0,n);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}
}
