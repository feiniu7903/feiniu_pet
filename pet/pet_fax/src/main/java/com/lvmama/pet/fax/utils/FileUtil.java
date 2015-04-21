package com.lvmama.pet.fax.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtil {
	private static final Log log = LogFactory.getLog(FileUtil.class);
	/**
	 * 移动Tif文件
	 * @param 
	 * 		path 要移动文件的路径  
	 * @param
	 * 		fileName 要移动的文件名
	 * 
	 **/
	public static void moveTifFile(String sourcePath,String destPath,String fileName){
		String tifFileName=fileName;
		File sourceFile= new File(sourcePath+"/"+tifFileName);
		try{
			sourceFile.renameTo(new File(createFolder(destPath)+tifFileName));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 移动文件，不使用renameTo,使用copy再删除
	 * @param file
	 * @param destDir
	 */
	public static void moveTifFile(File file,String destDir){
		if(!file.exists()){
			return;
		}
		FileInputStream fin=null;
		FileOutputStream fos = null;
		boolean success=false;
		try {
			fin = new FileInputStream(file);
			fos = new FileOutputStream(new File(createFolder(destDir),file.getName()));
			IOUtils.copy(fin, fos);
			success=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(fin);
			IOUtils.closeQuietly(fos);
			if(success){
				boolean f=file.delete();
				if(!f){
					//log.info("删除失败:::::::::::::::"+file.getAbsolutePath());
					
					f=file.delete();
					if(!f){
						log.info("删除失败:::::::::::::::"+file.getAbsolutePath());
						
						
					}
				}
			}
		}
		
	}
	/**
	 * @param 
	 * 		basePath 给定的目录
	 * @return 
	 * 		返回一个以当天的日期命名的层级目录
	 *  	例如D:/FaxSend/2013/6/25
	 */
    public static String createFolder(String basePath) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date now = new Date();
		String directory = basePath+"/"+dateFormat.format(now)+"/";
		File dir = new File(directory);
		if(!dir.exists()){
			if(dir.mkdirs()){
				log.info("目录"+directory+"创建成功!");				
			}		
		}
		return directory;
    }
}
