package com.lvmama.comm.pet.vo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.FileUtil;

public class ComFile implements Serializable{
	
	private static final long serialVersionUID = -403578180953080397L;
	
	private static Log logger = LogFactory.getLog(ComFile.class);


	String fileName;
	
	byte[] fileData;
	
	OutputStream outputStream;
	
	File file;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public InputStream getInputStream() {
		return new ByteArrayInputStream(fileData);
	}
	
	public File getFile() {
		try{
			String abstractFileName = System.getProperty("java.io.tmpdir") + "/" + fileName;
			return FileUtil.writeBytesToFile(fileData, abstractFileName);
		}catch(Exception e){
			logger.error(e);
			return null;
		}
	}

	public void setFile(File file) {
		this.file = file;
	}
	
}
