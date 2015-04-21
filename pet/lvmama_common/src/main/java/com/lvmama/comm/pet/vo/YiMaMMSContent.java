package com.lvmama.comm.pet.vo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class YiMaMMSContent extends MMSContent {
	private static final long serialVersionUID = 8174070786560747180L;
	private static final Log LOG = LogFactory.getLog(SuZhouLeYuanMMSContent.class);
	
	/**
	 * Constructor
	 */
	protected YiMaMMSContent() {
		
	}
	
	public YiMaMMSContent(final byte[] data, final String mobile, final int priority,final String type, final Date date) throws Exception {
		
		if (null == date) {
			LOG.error("你要发送空短信？");
			throw new Exception("Null Sms Content.");
		}
		this.data = data;
		if (null == mobile || mobile.length() < 11 || mobile.length() > 1200) {
			LOG.error("非法的手机长度，手机号码长度必须介于11位到1200位之间");
			throw new Exception("手机号码长度必须介于11位到1200位之间");
		}
		if (mobile.length() != 11 && mobile.indexOf(SEPARATOR) == -1) {
			LOG.error("错误的手机号分割符，应该使用逗号分隔");
			throw new Exception("批量手机号请使用逗号分隔");
		}
		this.mobile = mobile;
		
		
		if (priority < 1 || priority > 10) {
			LOG.error("错误的优先级别，优先级别必须为1到10的整数！");
			throw new Exception("优先级别必须为1到10的整数");
		}
		this.priority = priority;
		
		this.type = type;
		this.sendDate = date;
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("创建MMSContent:" + toString());
		}		
	}
	
	/**
	 * Constructor
	 * @param texts
	 * @param pictures
	 * @param mids
	 * @param mobile
	 */
	public YiMaMMSContent(final String[] texts, final byte[][] pictures, final byte[][] mids, final String mobile) throws Exception {
		this(null, mobile, NORMAL_PRIORITY, "MMS_YIMA", new Date());
		
		File outfile = null;
		FileOutputStream fos = null;
		Map<String,String> parameters = new HashMap<String,String>();
		
		String baseFileName = getBaseFileName();
		String pathName = getBasePath(baseFileName);
		String imgFileName = baseFileName + ".wbmp";
		String smilFileName = baseFileName + ".smil";
		String zipFileName =  baseFileName + ".zip";
		
		outfile = new File(pathName);
		if (outfile.exists() || !outfile.isDirectory()) {
			if (outfile.mkdir() && LOG.isDebugEnabled()) {
				LOG.debug("创建了临时目录" + pathName);
			}			
		}
		
		try {
			outfile = new File(pathName + imgFileName); 
			fos = new FileOutputStream(outfile); 
			fos.write(pictures[0]); 
			fos.close();
			parameters.put("image", imgFileName);
		} catch (FileNotFoundException fnfe) {
			LOG.error("无法找到" + pathName + imgFileName + "文件。请确认目录是否存在或是否有足够的权限.错误信息:" + fnfe.getMessage());
			throw new Exception("创建图片文件失败!");
		} catch (IOException ioe) {
			LOG.error("创建文件" + pathName + imgFileName + "失败。请确认目录是否存在或是否有足够的权限.错误信息:" + ioe.getMessage());
			throw new Exception("创建图片文件失败!");			
		}
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("创建图片文件" + pathName + imgFileName + "成功!");
		}				
		
		try {
			outfile = new File(pathName + smilFileName); 
			fos = new FileOutputStream(outfile); 
			fos.write(composeMessage(getSmilFile() , parameters).getBytes()); 
			fos.close();
		} catch (FileNotFoundException fnfe) {
			LOG.error("无法找到" + pathName + smilFileName + "文件。请确认目录是否存在或是否有足够的权限.错误信息:" + fnfe.getMessage());
			throw new Exception("创建smil文件失败!");
		} catch (IOException ioe) {
			LOG.error("创建文件" + pathName + smilFileName + "失败。请确认目录是否存在或是否有足够的权限.错误信息:" + ioe.getMessage());
			throw new Exception("创建smil文件失败!");			
		} catch (Exception e) {
			LOG.error("发生了异常的错误，错误信息:" + e.getMessage());
			throw new Exception("发生了异常的错误");
		}
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("创建smil文件" + pathName + smilFileName + "成功!");
		}		
		
		zipFile(pathName, pathName + zipFileName);
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("创建压缩文件" + pathName + zipFileName + "成功!");
		}
		
		FileInputStream fs = new FileInputStream(pathName + zipFileName);
		byte[] content = new byte[fs.available()];
		fs.read(content, 0, content.length);
		fs.close();
		
		this.data = content;
		this.content = "我是彩信";
		
		outfile = new File(pathName);
		if (outfile.delete() && LOG.isDebugEnabled()) {
			LOG.debug("成功删除目录" + pathName);
		}		
	}
	

	
	protected String getSmilFile() {
		return "<smil xmlns=\"http://www.w3.org/2000/SMIL20/CR/Language\"><head><layout><root-layout width=\"208\" height=\"176\" /><region id=\"Image\" left=\"0\" top=\"0\" width=\"128\" height=\"128\" fit=\"hidden\" /><region id=\"Text\" left=\"0\" top=\"50\" width=\"128\" height=\"128\" fit=\"hidden\" /></layout></head><body><par dur=\"50000ms\"><img src=\"${image}\" region=\"Image\" /></par></body></smil>";
	}
	
	public static void main(String[] args) {
		try {
			java.io.FileInputStream fs = new java.io.FileInputStream("d:/dd.wbmp");
			byte[] Content=new byte[fs.available()];
			fs.read(Content);
			fs.close();
			new YiMaMMSContent(null, new byte[][]{Content}, null, "13917677725");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
}
