package com.lvmama.comm.pet.vo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.lvmama.comm.pet.po.sms.SmsContent;

public abstract class MMSContent extends SmsContent {
	private static final long serialVersionUID = 2300735864050550625L;
	
	public static final int BUFFER = 1024 ;//缓存大小 
	
	/**
	 * 通过正则表达式替换文本内容
	 * @param template
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String composeMessage(String template, Map<String,String> data) throws Exception {   
		String regex = "\\$\\{(.+?)\\}";   
		Pattern pattern = Pattern.compile(regex);   
		Matcher matcher = pattern.matcher(template); 
		
		/*  
		 * sb用来存储替换过的内容，它会把多次处理过的字符串按源字符串序  
		 * 存储起来。  
		 */  
		StringBuffer sb = new StringBuffer();   
		while (matcher.find()) {   
			String name = matcher.group(1);//键名   
			String value = data.get(name).toString();//键值   
			if (value == null) {   
				value = "";   
			} else {    
				value = value.replaceAll("\\$", "\\\\\\$"); 
				//value = value.replaceAll("\\", "\\\\");
			}   
 
			matcher.appendReplacement(sb, value);   
		}   
		   
		matcher.appendTail(sb);   
		return sb.toString();   
	}
	

	
	public static void zipFile(String baseDir, String fileName)
			throws Exception {
		List<File> fileList = getSubFiles(new File(baseDir));
		ZipOutputStream zos = new ZipOutputStream(
				new FileOutputStream(fileName));
		ZipEntry ze = null;
		byte[] buf = new byte[BUFFER];
		int readLen = 0;
		for (File f : fileList) {
			ze = new ZipEntry(getAbsFileName(baseDir, f));
			ze.setSize(f.length());
			ze.setTime(f.lastModified());
			zos.putNextEntry(ze);
			InputStream is = new BufferedInputStream(new FileInputStream(f));
			while ((readLen = is.read(buf, 0, BUFFER)) != -1) {
				zos.write(buf, 0, readLen);
			}
			is.close();
		}
		zos.close();
	}
	
	/**
	 * 获取基础文件名
	 * @return
	 */
	protected static String getBaseFileName() {
		return (System.currentTimeMillis() * 1000 + (int) Math.ceil((Math.random() * 1000))) + "";
	}
	
	protected static String getBasePath(String baseFileName) {
		String basePath = System.getProperty("java.io.tmpdir") + baseFileName + File.separator;
		return basePath;
	}
	
	
	/**
	 * 给定根目录，返回另一个文件名的相对路径，用于zip文件中的路径.
	 * 
	 * @param baseDir
	 *            java.lang.String 根目录
	 * @param realFileName
	 *            java.io.File 实际的文件名
	 * @return 相对文件名
	 */
	protected static String getAbsFileName(String baseDir, File realFileName) {
		File real = realFileName;
		File base = new File(baseDir);
		String ret = real.getName();
		while (true) {
			real = real.getParentFile();
			if (real == null)
				break;
			if (real.equals(base))
				break;
			else
				ret = real.getName() + "/" + ret;
		}
		return ret;
	}
	
	
    /**
     * 获取子目录下的文件列表
     * @param baseDir 
     * @return
     */
	protected static List<File> getSubFiles(File baseDir) {
		List<File> ret = new ArrayList<File>();
		File[] tmp = baseDir.listFiles();
		for (int i = 0; i < tmp.length; i++) {
			if (tmp[i].isFile())
				ret.add(tmp[i]);
			if (tmp[i].isDirectory())
				ret.addAll(getSubFiles(tmp[i]));
		}
		return ret;
	}
}
