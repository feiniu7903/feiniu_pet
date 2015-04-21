package com.ejingtong.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 日志对象，以天为单位记录日志
 * @author xuqun
 *
 */
public class MLog {

	private String logFileName;
	private File logFile;
	
	public MLog(String logName){
		logFileName = logName;
		
		logFile = new File(logFileName);
		if(!logFile.getParentFile().exists()){
			logFile.getParentFile().mkdirs();
		}
		if(!logFile.exists()){
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void delete(){
		if(logFile != null && logFile.exists()){
			logFile.delete();
		}
	}
	
	// 写日志
	public void write(String strLog){
		//TODO 日志写入文件
		FileWriter writer = null;
		try {
			writer = new FileWriter(logFile,true);
//			writer.append(strLog);
			writer.write(strLog + "\r\n");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(null != writer){
					writer.flush();
					writer.close();
					writer = null;
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	//读日志
	public String read(){
		//读日志
		StringBuffer buffer = new StringBuffer();
		BufferedReader bufferReader = null;
		try {
//			reader = new FileReader(logFile);
			bufferReader = new BufferedReader(new FileReader(logFile));
			
			String temp=null;
			while((temp = bufferReader.readLine()) != null){
				buffer.append(temp);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(null != bufferReader){
				try {
					bufferReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("*********readEndTime:" + System.currentTimeMillis());
		return buffer.toString();
	}
	
	public File getLogFile(){
		return logFile;
	}
	
}
